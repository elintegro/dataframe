/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql
import grails.converters.JSON
import grails.util.Holders
import org.springframework.context.i18n.LocaleContextHolder
import org.apache.commons.lang.WordUtils

/**
 * Created by kchapagain on Nov, 2018.
 */
class ComboboxVue extends WidgetVue {

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String validate = field?.validate
        def disabled = field.disabled == null? false : field.disabled
        def search = field?.search
        String displayMember = field.displayMember?:'name'
        String onSelect = ""
        if(field.onSelect && field.onSelect.methodScript ){
            onSelect = " @change='${dataframe.dataframeName}_onSelect' "
            dataframe.getVueJsBuilder().addToMethodScript("""${dataframe.dataframeName}_onSelect: function(e){
                            $field.onSelect.methodScript
             },\n """)
        }
        return """
            <v-combobox
          v-model="$fldName"
          :items="${fldName}_items"
          ${validate?":rules = '${fldName}_rule'":""}
          label="$label"
          ${disabled?":disabled=true":""}
          ${search?"::search-input.sync= '${fldName}_search'":""}
          item-text="${displayMember}"
          small-chips
          hide-selected
          ${isReadOnly?"readonly":''}
          ${toolTip(field)}
           $onSelect
        ></v-combobox>
        """
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        def _params = [:]
        def dfInstance = new DataframeInstance(dataframe, _params)
        def inputData = [:]
        String fieldnameToReload = dataVariable.replace("_", ".")
        def search = field?.search

        String domainAlias= Dataframe.getDataFrameDomainAlias(fieldnameToReload)
        List resultList = []
        List keys = []
        List res
        def selMap= [:]
        String valueMember = field.valueMember?:"id"
        String displayMember = field.displayMember?:"name"
        if (domainAlias && dataframe.writableDomains) {
            Map domain = dataframe.writableDomains.get(domainAlias)
            def queryDomain = DataframeInstance.getPersistentEntityFromDomainMap(domain)
            String simpleFieldName = Dataframe.extractSimpleFieldName(dataframe.dataframeName,fieldnameToReload,domainAlias)
            if (simpleFieldName) {

                def prop = queryDomain.getPropertyByName(simpleFieldName)
                String defaultValue = field.defaultValue
                if(field.isEnumType){
                    if(field.enumClassName){
                        try {
                            Class enumClass = Class.forName(field.enumClassName)
                            def exClass = Holders.grailsApplication.getClassForName(field.enumClassName)
                            keys = enumClass.getTypes()
                            res = new ArrayList(keys.size())
                            keys.each {
                                String displayValue = it
                                if(field.internationalize){
                                    displayValue = getMessageSource().getMessage(it, null, it, LocaleContextHolder.getLocale())
                                }
                                Map prepEnum = ["$valueMember":it, "$displayMember":displayValue]
                                if (defaultValue && defaultValue.equals(it)){
                                    selMap = prepEnum
                                }
                                res.push(prepEnum)
                            }
                            field.put("isEnumType", true)
                        } catch(ClassNotFoundException e){
                            e.printStackTrace()
                        }
                    }
                } else {

                    if(dataframe.metaFieldService.isEnumType(prop)){
                        def enumClass = prop.type
//                    res = enumClass.getDescs()
                        keys = enumClass.getTypes()
                        res = new ArrayList(keys.size())
                        keys.each {
                            String displayValue = it
                            if(field.internationalize){
                                displayValue = getMessageSource().getMessage(it, null, it, LocaleContextHolder.getLocale())
                            }
                            Map prepEnum = ["$valueMember":it, "$displayMember":displayValue]
                            if (defaultValue && defaultValue.equals(it)){
                                selMap = prepEnum
                            }
                            res.push(prepEnum)
                        }
                        field.put("isEnumType", true)
                    } else {

                        String wdgHql = field?.hql
                        if(wdgHql){
                            resultList = dataframe.getHqlResult(wdgHql)
                        }
                        res = new ArrayList(resultList.size())
                        if(field.internationalize){
                            resultList.each {
                                def displayMemberValue = it.getAt("$displayMember")
                                Map value1 = ["$valueMember":it.getAt("id"), "$displayMember":getMessageSource().getMessage(displayMemberValue, null, displayMemberValue, LocaleContextHolder.getLocale())]
                                if (defaultValue && defaultValue.equals(displayMemberValue)){
                                    selMap = value1
                                }
                                res.push(value1)
                            }
                        }else {
                            res = resultList
                        }
                    }
                }
            }
        }
        /*if (field.isEnumType) {
            resultList = enumClass.getDescs()
            keys = enumClass.getTypes()
        }*/
        dataframe.getVueJsBuilder().addToWatchScript("""$dataVariable: function(e){
                        if(e){
                            drfExtCont.saveToStore("${dataframe.dataframeName}","$dataVariable",e.$valueMember?e.$valueMember:'' )
                        }
             },\n """)
        return """$dataVariable:${selMap?selMap as JSON:"\"\""},\n
                  ${dataVariable}_items:${res as JSON} ,\n
                  ${dataVariable}_keys:${keys as JSON},\n
                  ${search?"${dataVariable}_search:null,\n":""}"""

    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        def dictionary = field?.dictionary
        String fldName = dataframe.getDataVariableForVue(field)
        String fullFieldName = key.replace(Dataframe.DOT,Dataframe.DASH)
        String displayMember = field.displayMember
        if(!field){
            throw new DataframeException(" Fields are empty for the Dataframe :${dataframe.dataframeName}")
        }
        if(!(field.hql || dictionary || field?.isEnumType)){
            throw new DataframeException(" Hql field is empty for the Dataframe :${dataframe.dataframeName}")
        }
        return """
               var fullFieldName = '$fullFieldName';
               var data = response.additionalData[fullFieldName]['dictionary'];
               var keys = response.additionalData[fullFieldName]['keys'];
               var selectedData = response.additionalData[fullFieldName]['selectedData'];
               this.$dataVariable = selectedData;
               this.${dataVariable}_items = data;
              """
    }

    @Override
    String getVueSaveVariables(DataframeVue dataframe, Map field) {
        String valueMember = field.valueMember?:"id"
        String dataVariable = dataframe.getDataVariableForVue(field)
        String thisFieldName = dataframe.getFieldId(field)
        boolean isEnumType = field?.isEnumType
//        if (isEnumType || field?.dictionary){
//            super.getVueSaveVariables(dataframe, field)
//        }else {
        return """allParams['$thisFieldName'] = this.$dataVariable.$valueMember; \n"""
//        }
    }

    public Map loadAdditionalData(DataframeInstance dfInst, String fieldnameToReload, Map inputData, def session){
        Map result=[:]
        Dataframe df = dfInst.df;
        Map fieldProps = df.fields.get(fieldnameToReload)

        String wdgHql = fieldProps?.hql

        if(wdgHql){
            ParsedHql parsedHql = new ParsedHql(wdgHql, df.grailsApplication, df.sessionFactory);
            DbResult dbRes = new DbResult(wdgHql, inputData, session, parsedHql);
            List resultList = dbRes.getResultList()
            List res = new ArrayList(resultList.size())
            String valueMember = fieldProps.valueMember?:"id"
            String displayMember = fieldProps.displayMember?:"name"
            if(fieldProps.internationalize){
                resultList.each {
                    Map value1 = ["$valueMember":it.getAt("id"), "$displayMember":getMessageSource().getMessage(it.getAt("$displayMember"), null, it.getAt("$displayMember"), LocaleContextHolder.getLocale())]
                    res.push(value1)
                }
            }else {
                res = resultList
            }
            fieldProps["dictionary"] = res
            result = buildData(dfInst, inputData, fieldProps, fieldnameToReload)
        }
        return result
    }


    private static def buildData(DataframeInstance dfInst, Map inputData, Map fieldProps, String fieldnameToReload){
        def dictionary = fieldProps.dictionary
        def selectedValue = null
        Dataframe df = dfInst.df
        String simpleFieldName = ""
        List keys = null
        String domainAlias= Dataframe.getDataFrameDomainAlias(fieldnameToReload)
        def domainInstance = null
        boolean isParameterRelatedToDomain = false
        def queryDomain = null
        if (domainAlias && df.writableDomains) {
            Map domain = df.writableDomains.get(domainAlias)
            queryDomain = dfInst.getPersistentEntityFromDomainMap(domain)
            Map keysNamesAndValue = [:];
//            keysNamesAndValue = dfInst.getKeysAndValues(domain, inputData)
            dfInst.getKeysNamesAndValueForPk(keysNamesAndValue, domain, inputData);
            if (!keysNamesAndValue.isEmpty()){
                domainInstance = dfInst.retrieveDomainInstanceForUpdate(keysNamesAndValue, queryDomain);
            }
            simpleFieldName = Dataframe.extractSimpleFieldName(df.dataframeName,fieldnameToReload,domainAlias)
            isParameterRelatedToDomain = dfInst.isParameterRelatedToDomain(queryDomain, domainAlias, simpleFieldName)
        }
        if (simpleFieldName && isParameterRelatedToDomain){
            def prop = queryDomain.getPropertyByName(simpleFieldName)
            def refFieldValues = domainInstance?domainInstance."$simpleFieldName":null
            if (refFieldValues && df.metaFieldService.isAssociation(prop)){
                String valueMember = fieldProps.valueMember
                String displayMember = fieldProps.displayMember
                def selMap= [:]
                if (valueMember){
                    selMap.put(valueMember, refFieldValues."$valueMember")
                }
                if (displayMember){
                    String value1 = refFieldValues."$displayMember"
                    if(fieldProps.internationalize){
                        value1 = getMessageSource().getMessage(refFieldValues."$displayMember", null, refFieldValues."$displayMember", LocaleContextHolder.getLocale())
                    }
                    selMap.put(displayMember, value1)
                }
                selectedValue = selMap
            }else if (df.metaFieldService.isEnumType(prop)){
                def enumClass = prop.type
                dictionary = enumClass.getDescs()
                keys = enumClass.getTypes()
                if (refFieldValues){
                    selectedValue = enumClass."$refFieldValues".getDesc()
                }
            }
        }
        return [keys:keys, dictionary:dictionary, selectedData:selectedValue]
    }

}
