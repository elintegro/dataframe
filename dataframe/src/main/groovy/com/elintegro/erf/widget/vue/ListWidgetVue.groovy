package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.converters.JSON
import org.apache.commons.lang.WordUtils
import org.springframework.context.i18n.LocaleContextHolder

class ListWidgetVue extends WidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode ?: fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getList(dataframe, field, fldName, label)
        return html
    }
    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        def search = field?.search
        String validationString = ""
        if(validate(field)){
            String validationRules = validationRules(field)
            validationString = """ ${dataVariable}_rule: $validationRules,\n"""
        }
        return """
                  ${search?"${dataVariable}_search:null,\n":""}
                   $validationString
                """

    }

    String getStateDataVariable(DataframeVue dataframe, Map field){

        String dataVariable = dataframe.getDataVariableForVue(field)
        Map result = generateInitialData(dataframe, field)
        String valueMember = field.valueMember?:"id"
        List keys=[]
        List res
        Map selMap = [:]
        selMap.put(valueMember,'')
        if(result){
            keys = result.keys
            res = result.result
            selMap = result.selectedMap
        }
        return """$dataVariable:${selMap?selMap as JSON:"\"\""},\n
                  ${dataVariable}_items:${res as JSON} ,\n
                  ${dataVariable}_keys:${keys as JSON},\n
                """
    }
    private Map generateInitialData(DataframeVue dataframe, Map field){

        if(!field.initBeforePageLoad){
            return [keys: [], result:[], selectedData: [:]]
        }
        String displayMember = field.displayMember?:"name"
        String valueMember = field.valueMember?:"id"
        Map result = [:]
        String enumFileName = field.enumFileName // Requires full path name 'com.elintegro.Questionaire.<Classname>'
        if(enumFileName){
            result = getFromEnumFile(field, enumFileName)
        } else {
            result = getFromDomainClass(dataframe, field, valueMember, displayMember)
        }
        return result
    }

    private Map getFromEnumFile(Map field, String enumFileName){

        Map result
        try {
            String defaultValue = field.defaultValue
            Class enumClass = Class.forName(enumFileName)
//                def exClass = Holders.grailsApplication.getClassForName(field.enumClassName)
            result = getEnumData(field, enumClass, defaultValue)
        } catch(ClassNotFoundException e){
            e.printStackTrace()
        }

        return result
    }

    private Map getFromDomainClass(DataframeVue dataframe, Map field, String valueMember, String displayMember){

        Map result
        String dataVariable = dataframe.getDataVariableForVue(field)
        String fieldnameToReload = dataVariable.replace("_", ".")
        String domainAlias= Dataframe.getDataFrameDomainAlias(fieldnameToReload)
        if (domainAlias && dataframe.writableDomains) {
            Map domain = dataframe.writableDomains.get(domainAlias)
            def queryDomain = DataframeInstance.getPersistentEntityFromDomainMap(domain)
            String simpleFieldName = Dataframe.extractSimpleFieldName(dataframe.dataframeName,fieldnameToReload,domainAlias)
            if (simpleFieldName) {
                def prop = queryDomain.getPropertyByName(simpleFieldName)
                String defaultValue = field.defaultValue
                if(dataframe.metaFieldService.isEnumType(prop)){
                    def enumClass = prop.type
//                    res = enumClass.getDescs()
                    result = getEnumData(field, enumClass, defaultValue)
                } else {
                    result = getHqlData(dataframe, field, defaultValue, valueMember, displayMember)
                }

            }
        }
        return result
    }

    private Map getEnumData(Map field, Class enumClass, String defaultValue){
        String displayMember = field.displayMember?:"name"
        String valueMember = field.valueMember?:"id"
        List keys = enumClass.getTypes()
        List res = new ArrayList(keys.size())
        Map selMap =[:]
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

        return [keys:keys, result: res, selectedData: selMap]

    }

    private Map getHqlData(DataframeVue dataframe, Map field, String defaultValue, String valueMember, String displayMember){

        List keys=[]
        List res
        Map selMap = [:]
        String wdgHql = field?.hql
        List resultList = []
        if(wdgHql){
            resultList = dataframe.getHqlResult(wdgHql)
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

        return [keys:keys, result:res, selectedData: selMap]
    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        def dictionary = field?.dictionary
        String fullFieldName = key.replace(Dataframe.DOT,Dataframe.DASH)
        if(!field){
            throw new DataframeException(" Fields are empty for the Dataframe :${dataframe.dataframeName}")
        }
        if(!(field.hql || dictionary || field?.isEnumType || field.enumFileName)){
            throw new DataframeException(" Hql field is empty for the Dataframe :${dataframe.dataframeName}")
        }
/*
        return """
               var fullFieldName = '$fullFieldName';
               if(!\$.isEmptyObject(response.additionalData)){
               var data = response.additionalData[fullFieldName]['dictionary'];
               var keys = response.additionalData[fullFieldName]['keys'];
               var selectedData = response.additionalData[fullFieldName]['selectedData'];
               this.$dataVariable = (selectedData!=null || selectedData!=undefined || selectedData != {})?selectedData:'';
               this.${dataVariable}_items = data;
               }
              """
*/
        return ""
    }

    @Override
    String getVueSaveVariables(DataframeVue dataframe, Map field) {
        String valueMember = field.valueMember?:"id"
        String dataVariable = dataframe.getDataVariableForVue(field)
        boolean isEnumType = field?.isEnumType
//        if (isEnumType || field?.dictionary){
//            super.getVueSaveVariables(dataframe, field)
//        }else {

        //Since we assign to the allParams storage values, that were predefined in retrieve, we do not need to assign a value to the allParams field entry.
        //
        return """
            //allParams['$dataVariable'] = this.state.$dataVariable?this.state.$dataVariable.$valueMember:''; \n
            if(!this.state.$dataVariable){allParams['$dataVariable'] = '';}
        """
//        }
    }

    public Map loadAdditionalData(DataframeInstance dfInst, String fieldnameToReload, Map inputData, def session){
        Dataframe df = dfInst.df;
        Map fieldProps = df.fields.get(fieldnameToReload)
        String enumFileName = fieldProps.enumFileName
        if(enumFileName){
            Map enumData = getFromEnumFile(fieldProps, enumFileName)
            return [keys:enumData.keys, dictionary: enumData.result, selectedData: enumData.selectedData]
        }
        String wdgHql = fieldProps?.hql

        Map result=[:]
        if(wdgHql && !fieldProps?.initBeforePageLoad){
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


    private def buildData(DataframeInstance dfInst, Map inputData, Map fieldProps, String fieldnameToReload){
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
            try {
                queryDomain = dfInst.getPersistentEntityFromDomainMap(domain)
                Map keysNamesAndValue = [:];
//            keysNamesAndValue = dfInst.getKeysAndValues(domain, inputData)
                dfInst.getKeysNamesAndValueForPk(keysNamesAndValue, domain, inputData);
                if (!keysNamesAndValue.isEmpty()) {
                    domainInstance = dfInst.retrieveDomainInstanceForUpdate(keysNamesAndValue, queryDomain);
                }
                simpleFieldName = Dataframe.extractSimpleFieldName(df.dataframeName, fieldnameToReload, domainAlias)
                isParameterRelatedToDomain = dfInst.isParameterRelatedToDomain(queryDomain, domainAlias, simpleFieldName)
            } catch(Exception){
                throw  new DataframeException("Writable Domain doesnot contain domain for alias"+ domainAlias)
            }
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
                    if (fieldProps.internationalize){
                        selectedValue = enumClass."$refFieldValues".getType()
                        Map enumMap = getEnumData(fieldProps, enumClass, selectedValue)
                        dictionary = enumMap.result
                        selectedValue = enumMap.selectedData
                    }else {
                        selectedValue = enumClass."$refFieldValues".getDesc()
                    }
                }
            }
        }
        return [keys:keys, dictionary:dictionary, selectedData:selectedValue]
    }

    private String getList(DataframeVue dataframe, Map field, String fldName, String label) {
        boolean isReadOnly = dataframe.isReadOnly(field)
        String displayMember = field.displayMember ?: 'name'
        String valueMember = field.valueMember ?: 'id'
        String modelString = getModelString(dataframe, field)
        String onClick = field.OnClick?:""
        """
           <v-list ${isDisabled(dataframe, field) ? "disabled" : ""} flat ${getAttr(field)}>
                  <v-subheader>$label</v-subheader>
                  <v-list-item-group color="#1976D2">
                       <v-list-item  v-for="(item, i) in ${modelString}_items" :key="i">
                          <v-list-item-content>
                               <v-list-item-title v-model = "$modelString" v-text="item.${displayMember}" @click="${onClick}">
                               </v-list-item-title>
                          </v-list-item-content>
                       </v-list-item>
                  </v-list-item-group>
           </v-list>
        """
    }
}
