/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dfEditor

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.frontendlib.PageDFRegistry
import com.elintegro.erf.dataframe.service.MetaFieldService
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.hibernate.engine.query.spi.NamedParameterDescriptor
import org.hibernate.engine.query.spi.ParameterMetadata

@Slf4j
class DfEditor {
    Map<String, PageDFRegistry> pageDFRegistryMap
    Map<String, List<String>> dfTreeMap = [:]

    void init(){
        if (pageDFRegistryMap == null || pageDFRegistryMap?.size() == 0){
            this.pageDFRegistryMap = getBeansOfType(PageDFRegistry)
            setDfTreeMap()
        }
    }

    public static def getBeansOfType(Class<?> type){
        def beansObj = Holders.grailsApplication.mainContext.getBeansOfType(type)
        return beansObj
    }

    public setDfTreeMap(){

        pageDFRegistryMap.eachWithIndex{ key, pageDFRegistry, int i ->
            def pageName = pageDFRegistry.pageName
            List<String> pageDfsNames = []
            pageDFRegistry.pageDfs.each {df ->
                pageDfsNames.add(df.dataframeName)
            }
            dfTreeMap.put(pageName, pageDfsNames)
        }
    }

    public static def populateDfProperties(Dataframe df){
        def dfInstance = new DfInstance(df)
        return dfInstance.toMap()
    }

    public static def buildNamedParamsMetaData(query, String[] namedParameter){
        def namedParamMetadata = [:]
        ParameterMetadata paramMetadata = query.getParameterMetadata()
        namedParameter.each {param ->
            NamedParameterDescriptor namedParameterType = paramMetadata.getNamedParameterDescriptor(param)
            def expectedType = namedParameterType.expectedType
            def typeClass = expectedType.javaTypeDescriptor.getJavaTypeClass()
            def type = MetaFieldService.getTypePropertyName(typeClass)?.toUpperCase()
            namedParamMetadata.put(param, type)
        }
        return namedParamMetadata
    }

    public static def getTypeCastValue(def value, propValue){
        if (!value || !propValue){
            return
        }
        if (propValue instanceof String){
            return value
        }else if (propValue instanceof Boolean){
            return Boolean.parseBoolean(value)
        }else
            return value
    }


}
