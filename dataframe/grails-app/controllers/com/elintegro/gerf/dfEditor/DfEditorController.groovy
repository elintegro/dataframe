/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gerf.dfEditor

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dfEditor.DfEditor
import grails.converters.JSON
import grails.spring.BeanBuilder
import grails.util.Environment
import grails.util.Holders
import org.apache.commons.lang.StringUtils
import org.grails.core.exceptions.GrailsConfigurationException
import org.grails.spring.DefaultRuntimeSpringConfiguration
import org.grails.spring.RuntimeSpringConfigUtilities
import org.hibernate.Query
import org.springframework.beans.factory.support.BeanDefinitionRegistry


class DfEditorController {
    def sessionFactory
    def dfEditorService

    def index() { }

    private static Dataframe getDataframe(String dataframeName){
        Dataframe df = (Dataframe)Holders.grailsApplication.mainContext.getBean(dataframeName)
        return df
    }


    def editorValues(){
        String page = params?.page
        String dataframeName = params?.dataframe
        Dataframe df = getDataframe(dataframeName)
        def resultData = DfEditor.populateDfProperties(df)
        if (resultData){
            resultData= ['success': true, pageName:page, dataframe: dataframeName, data:resultData]
        }else {
            resultData = ['msg': 'Data is unable to extract.', 'success': false]
        }
        def converter = resultData as JSON
        converter.render(response)
    }

    def hqlResult(){
        def namedParameter
        def sql
        def status = 200
        def hql = params.hql
        def noRecords=0;
        def data=[];
        String message = ""
        def isnamedParameter = params?.isnamedParameter?:false
        def maxSize = Holders.grailsApplication.config.garils.hql.test.maxSize?:10
        String dataframeName = params?.dataframe
        Query query;
        try{
            if(StringUtils.isEmpty(hql)) {
                throw new Exception("Hql is required")
            }
            def session = sessionFactory.openSession()
            query =  session.createQuery(hql);
            namedParameter = query.getNamedParameters()
            if (namedParameter && !isnamedParameter){
                isnamedParameter = true
                def paramMetadata = DfEditor.buildNamedParamsMetaData(query, namedParameter)
                render([isnamedParameter:isnamedParameter, namedParameter:paramMetadata] as JSON)
                return
            }
            Dataframe df = getDataframe(dataframeName)
            if (!hql.equals(df?.hql)){
                df.hql = hql
                df.parsedHql = null
                df.init()
            }
            if (namedParameter){
                namedParameter.each { parameter ->
                    if (params.containsKey(parameter) && params.get(parameter)){
                        def paramValue = params.get(parameter)
                        def namedParam = df.getNamedParameter(parameter);
                        String refDomainAlias =  namedParam[0];
                        String refFieldName =  namedParam[1];
                        def namedParamValue = df.getTypeCastValue2(refDomainAlias, refFieldName, paramValue);
                        query.setParameter(parameter, namedParamValue)
                    }else {
                        throw new Exception("Please provide named parameters")
                    }
                }
            }
            sql = df.parsedHql.getSqlTranslatedFromHql()
            query.setMaxResults(maxSize)
            Iterator iterator=query.iterate();
            while(iterator.hasNext()) {
                noRecords++;
                data.push(iterator.next())
            }
        }
        catch(Exception e)
        {
            message = e.getMessage();
            flash.message = message
            status=500
            log.error("Error while converting ",e)
        }
        def map = [sql:sql,status:status, data:data, isnamedParameter:false, namedParameter:namedParameter]
        render (template: "/dfHqlTest",model:map);
    }

    def updateDataframe(){
        def resultData
        String dataframeName = params.dataframe
        Dataframe df = getDataframe(dataframeName)
        if (!df && !(Environment.current == Environment.DEVELOPMENT)){
            resultData = ['msg': "Unable to update dataframe.", 'success': false]
            render(resultData as JSON)
            return
        }
        def changedPropertyMap = [:]
        validateAndBuildBeanParameter(df, changedPropertyMap, params)
        if (changedPropertyMap.isEmpty()){
            resultData = ['msg': "Properties trying to save are same.", 'success': false]
            render(resultData as JSON)
            return
        }
//        Check if the parameters in hql are valid
        try{
            def parsedHql = new ParsedHql(params.hql,df.grailsApplication, df.sessionFactory)
        }catch(Exception e){
            log.debug(e.getMessage())
            resultData = ['msg': e.getLocalizedMessage(), 'success': false]
            render(resultData as JSON)
            return
        }

        try {
            dfEditorService.updateBean(df, 'grails-app/conf/spring/resourcesEditor.groovy', changedPropertyMap)
            dfEditorService.updateExistingBeans(params)

//            dfEditorService.writeChangedBeanProperties(df, 'src/main/groovy/com/elintegro/editor/Editor.groovy', changedPropertyMap)
            resultData = ['success': true]
        }catch (e){
            resultData = ['msg': "${e?.getMessage()}", 'success': false]
        }
        render(resultData as JSON)
    }

    def processBeanDefinitionRegistry(){
        def springConfig = new DefaultRuntimeSpringConfiguration()
        def application = Holders.grailsApplication
        def context = application.mainContext
        def beanResources = context.getResource(RuntimeSpringConfigUtilities.SPRING_RESOURCES_GROOVY)
        if (beanResources?.exists()) {
            def gcl = new GroovyClassLoader(application.classLoader)
            try {
                RuntimeSpringConfigUtilities.reloadSpringResourcesConfig(springConfig, application, gcl.parseClass(new GroovyCodeSource(beanResources.URL)))
            } catch (Throwable e) {
                log.error("Error loading spring/resources.groovy file: ${e.message}", e)
                throw new GrailsConfigurationException("Error loading spring/resources.groovy file: ${e.message}", e)
            }
        }
        def bb = new BeanBuilder(null, springConfig, application.classLoader)
        bb.registerBeans((BeanDefinitionRegistry)grailsApplication.getMainContext())
    }

    private static validateAndBuildBeanParameter(Dataframe dataframe, Map changedPropertyMap, requestParams){
        def dfInstance = DfEditor.populateDfProperties(dataframe)
        def dfProperties = dataframe.properties
        requestParams.each {key, value ->
            if (dfInstance.containsKey(key)){
                def propValue = dfProperties.get(key)
                def typeCastValue = DfEditor.getTypeCastValue(value, propValue)
                Boolean isChangedProperties = isChangedProperties(typeCastValue, propValue)
                if (isChangedProperties){
                    changedPropertyMap.put(key, value)
                }
            }
        }
    }

    private static Boolean isChangedProperties(typeCastValue, propValue){
        if (!propValue.equals(typeCastValue)){
            return true
        }else {
            return false
        }
    }

}
