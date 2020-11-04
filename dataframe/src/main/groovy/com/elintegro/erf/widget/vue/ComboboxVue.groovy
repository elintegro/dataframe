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
import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.vue.DataframeConstants
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dfEditor.DfInstance
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.apache.commons.lang.StringUtils
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.springframework.context.i18n.LocaleContextHolder
import org.apache.commons.lang.WordUtils

/**
 * Created by kchapagain on Nov, 2018.
 */
@Slf4j
class ComboboxVue extends WidgetVue {

    //This assigns a new value and returns true if new value was different then the old one
    @Override
    boolean populateDomainInstanceValue(def domainInstance, DomainClassInfo domainClassInfo, String fieldName, Map field, def inputValue){
        if(isReadOnly(field)){
            return false
        }
        JSONArray selectedItems =  (inputValue.value instanceof JSONArray)?inputValue.value:convertToJSONArrayIfSingleJSONObject(inputValue.value)
        JSONArray availableItems = inputValue.items

        if(!domainClassInfo.isAssociation(fieldName)){ // this means we just want to apply description value to the text field without association with any other entity
            def oldfldVal = domainInstance."${fieldName}"
            if(oldfldVal == inputValue.value) return false
            domainInstance."${fieldName}" = inputValue.value
        }else if(domainClassInfo.isToMany(fieldName)){
            return saveHasManyAssociation(selectedItems, domainClassInfo.getRefDomainClass(fieldName), fieldName, domainInstance)
        }else if(domainClassInfo.isToOne(fieldName)){
            def oldfldVal = domainInstance."${fieldName}".value
            domainInstance."${fieldName}" = inputValue.value[0] //TODO: check if there is not exception in case of single choice
        }
        return true
    }

    private JSONArray convertToJSONArrayIfSingleJSONObject(JSONObject value){
        JSONArray jn = new JSONArray()
        jn.add(value)
        return jn
    }

    @Override
    boolean setPersistedValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap){
        jData?.persisters?."${domainAlias}"."${fieldName}".value = value
        if(additionalDataRequestParamMap.containsKey("items")){
            jData?.persisters?."${domainAlias}"."${fieldName}".items = additionalDataRequestParamMap.items
        }
    }


    boolean isSelectionEqualsToOld(JSONArray jarr1, JSONArray jarr2){
        boolean isEqual = true;
        if (jarr1.size() != jarr2.size()) {
            return false;
        } else {
            Set<Object> s1 = getSetOfIds(jarr1)
            Set<Object> s2 = getSetOfIds(jarr2)
            return isSetsEqual(s1, s2)
        }

        return isEqual
    }

    Set<Object> getSetOfIds(JSONArray ar1){
        Set<Object> set1 = new HashSet()
        ar1.each{el -> set1.add(el.value.id)}
        return set1
    }
    private boolean isSetsEqual(Set s1, Set s2){
        s1.each{ el->
            if(!s2.contains(el)){
                return false
            }
        }
        s2.each{ el->
            if(!s1.contains(el)){
                return false
            }
        }
        return true
    }

    //	saves onetomany and manytomany
    private boolean saveHasManyAssociation(JSONArray inputValue, def refDomainClass, String fieldName, def domainInstance) {
        def oldfldVal = domainInstance."${fieldName}"
        if (oldfldVal) {
            JSONArray oldfldValArr = new JSONArray(domainInstance."${fieldName}")
            if (isSelectionEqualsToOld(oldfldVal, inputValue)) {
                return false
            }
        }
        domainInstance?.(StringUtils.uncapitalize(fieldName))?.clear()
        inputValue.each{val ->
            val.each{
                if(it.key == "id"){
                    def refDomainObj  = refDomainClass.get(Long.valueOf(it.value))
                    String fn = fieldName.capitalize()
                    domainInstance."addTo${fieldName.capitalize()}"(refDomainObj)
                }
            }
        }
        return true
    }

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getCombobox(dataframe, field, fldName, label)
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

    Map getStateDataVariablesMap(DataframeVue dataframe, Map field){

        //TODO: no need
        //String dataVariable = dataframe.getDataVariableForVue(field)

        Map result = generateInitialData(dataframe, field)

        String valueMember = field.valueMember?:"id"
        List keys=[]
        List res
        Map selMap = [:]
        selMap.put(valueMember,'')
        if(result && result.size() > 0){
            keys = result.keys
            res = result.result
            selMap = result.selectedMap
        }else{
            log.error("This data is empty, please check data is provided in underliing database or enum class or other datasource. Check descriptor of Dataframe ${dataframe.dataframeName}")
        }

        Map domainFieldMap = dataframe.domainFieldMap
        Map fldJSON = null
        if(dataframe.isDatabaseField(field)){ //Put it to PERSISTERS section
            Map persisters = domainFieldMap.get(Dataframe.PERSISTERS)
            Map domainJSON = persisters.get(field.get(Dataframe.FIELD_PROP_DOMAIN_ALIAS))
            fldJSON = domainJSON.get(field.get(Dataframe.FIELD_PROP_NAME))
        }else{//Put it to TRANSITS section
            Map transits = domainFieldMap.get(Dataframe.TRANSITS)
            fldJSON = transits.get(field.get(Dataframe.FIELD_PROP_NAME))
        }
        fldJSON?.put("items", res)

        return domainFieldMap
/*

        return [$dataVariable:${selMap?selMap as JSON:"\"\""},\n
                  ${dataVariable}_items:${res as JSON} ,\n
                  ${dataVariable}_keys:${keys as JSON},\n
                """
*/

    }

    boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap){
        super.setTransientValueToResponse(jData, value, domainAlias, fieldName, additionalDataRequestParamMap)
        if(additionalDataRequestParamMap.containsKey("items")){
            jData?.transits?."${fieldName}".items = additionalDataRequestParamMap.items
        }
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

        Map result = [:]
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
        def enumList = getEnumListFromForClass(enumClass)
        List res = new ArrayList(enumList.size())
        def selMap = null
        enumList.each {
            String displayValue = it?."$displayMember"
            if(field.internationalize){
                displayValue = getMessageSource().getMessage(it, null, it, LocaleContextHolder.getLocale())
            }
            if (defaultValue && defaultValue.equals(displayValue)){
                selMap = displayValue
            }
            res.push(displayValue)
        }
        field.put("isEnumType", true)
        return [keys:enumList, result: res, selectedData: selMap]

    }

    private Map getEnumDataBackup(Map field, Class enumClass, String defaultValue){
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
        return ""
    }

    @Override
    String getVueSaveVariables(DataframeVue dataframe, Map field) {
        String valueMember = field.valueMember?:"id"
        String dataVariable = dataframe.getDataVariableForVue(field)
        boolean isEnumType = field?.isEnumType
        return """            
            if(!this.state.$dataVariable){allParams['$dataVariable'] = '';}
                """
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

        if(wdgHql) {
            //TODO: check if this condition correct: if this is true, no reload will happen, so where we are reloading?
            if (!fieldProps?.containsKey(DataframeConstants.FIELD_PROP_REF_FIELD_PARSED_HQL)){
                fieldProps?.put(DataframeConstants.FIELD_PROP_REF_FIELD_PARSED_HQL,  new ParsedHql(wdgHql, df.grailsApplication, df.sessionFactory))
            }
            ParsedHql parsedHql = fieldProps?.get(DataframeConstants.FIELD_PROP_REF_FIELD_PARSED_HQL)
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
        }
        Map result = buildData(dfInst, fieldProps, fieldnameToReload)
        return result
    }

    private def buildData(DataframeInstance dfInst, Map fieldProps, String fieldnameToReload){
        def dictionary = fieldProps.dictionary
        def selectedValue = null
        DataframeVue df = dfInst.df
        String simpleFieldName = ""
        List keys = null
        String dataVariable = df.getDataVariableForVue(fieldProps)
        String domainAlias= Dataframe.getDataFrameDomainAlias(dataVariable.replace("_", "."))
        def domainInstance = null
        String valueMember = fieldProps.valueMember
        String displayMember = fieldProps.displayMember
        if (domainAlias && df.writableDomains) {
            Map domain = df.writableDomains.get(domainAlias)
            DomainClassInfo domainClassInfo = domain.get(DataframeConstants.PARSED_DOMAIN);
            def queryDomain = domainClassInfo.getValue()
            try {
                domainInstance = dfInst.retrieveDomainInstanceForUpdate(domainClassInfo);
                simpleFieldName = Dataframe.extractSimpleFieldName(df.dataframeName, fieldnameToReload, domainAlias)
                if (simpleFieldName){
                    def prop = queryDomain.getPropertyByName(simpleFieldName)
                    def refFieldValues = getFieldValue(domainInstance, simpleFieldName)
                    if (df.metaFieldService.isAssociation(prop)){
                        if (refFieldValues){
                            if (fieldProps?.multiple){
                                selectedValue = getMultipleSelectedData(refFieldValues, valueMember, displayMember)
                            }else {
                                selectedValue = getSingleSelectedData(refFieldValues, valueMember, displayMember, fieldProps.internationalize)
                            }
                        }
                    }else if (df.metaFieldService.isEnumType(prop)){
                        def enumClass = prop.type
                        dictionary = getEnumDictionary(enumClass, displayMember)
                        if (refFieldValues){
                            selectedValue = enumClass."$refFieldValues"."$displayMember"
                        }
                    }
                }
            } catch(Exception e){
                throw  e
            }
        }
        return [keys:keys, items:dictionary, "value":selectedValue]
    }

    private static def getFieldValue(def domainInstance, fieldName){
        def refFieldValues = domainInstance?."$fieldName"
        return refFieldValues
    }

    private static List getMultipleSelectedData(def refFieldValues, String valueMember, String displayMember){
        List selectedValList = []
        refFieldValues.each{obj ->
            def selMap= [:]
            selMap.put(valueMember, obj."$valueMember")
            selMap.put(displayMember, obj."$displayMember")
            selectedValList.add(selMap)
        }
        return selectedValList
    }

    private static Map getSingleSelectedData(def refFieldValues, String valueMember, String displayMember, def internationalize = false){
        def selMap= [:]
        if (valueMember){
            selMap.put(valueMember, refFieldValues."$valueMember")
        }
        if (displayMember){
            String value1 = refFieldValues."$displayMember"
            if(internationalize){
                value1 = getMessageSource().getMessage(refFieldValues."$displayMember", null, refFieldValues."$displayMember", LocaleContextHolder.getLocale())
            }
            selMap.put(displayMember, value1)
        }
        return selMap
    }

    private static List getEnumDictionary(def enumClass, String displayMember){
        def enumList = getEnumListFromForClass(enumClass)
        List enumDictionary = []
        enumList.each {
            enumDictionary.add(it?."$displayMember")
        }
        return enumDictionary
    }

    private static def getEnumListFromForClass(def enumClass){
        return Arrays.asList(enumClass.values())
    }

    private def buildDataBackup(DataframeInstance dfInst, Map inputData, Map fieldProps, String fieldnameToReload){
        def dictionary = fieldProps.dictionary
        def selectedValue = null
        DataframeVue df = dfInst.df
        String simpleFieldName = ""
        List keys = null
        String dataVariable = df.getDataVariableForVue(fieldProps)
        String fieldnameload = dataVariable.replace("_", ".")
        String domainAlias= Dataframe.getDataFrameDomainAlias(dataVariable.replace("_", "."))
        def domainInstance = null
        boolean isParameterRelatedToDomain = false
        def queryDomain = null
        if (domainAlias && df.writableDomains) {
            Map domain = df.writableDomains.get(domainAlias)
            DomainClassInfo domainClassInfo = domain.get(DataframeConstants.PARSED_DOMAIN);
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
                throw  new DataframeException("Writable Domain does not contain domain for alias"+ domainAlias)
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

    private String getCombobox(DataframeVue dataframe, Map field, String fldName, String label){
        String onSelect = ""
        if(field.onSelect && field.onSelect.methodScript ){
            onSelect = " @change='${dataframe.dataframeName}_onSelect_$fldName' "
            dataframe.getVueJsBuilder().addToMethodScript("""${dataframe.dataframeName}_onSelect_$fldName: function(_params){
                            $field.onSelect.methodScript
             },\n """)
        }
        boolean isReadOnly = dataframe.isReadOnly(field)
        String typeString = ""
        if(!isSearchable(field)){
            typeString = """type="button" """
        }
        String multiple = field.multiple?"multiple":''
        String displayMember = field.displayMember?:'name'
        String valueMember = field.valueMember?:'id'
        String itemsStr = getFieldJSONItems(field)
        String modelString1 = getModelString(dataframe, field) //TODO: decide which one is correct?
        String modelString = getFieldJSONModelNameVue(field)
        String dataVariable = dataframe.getDataVariableForVue(field)//TODO: may be we do not need it!
        return """
            <v-combobox
                  v-model = "${modelString}"  
                  :items="${itemsStr}"
                  ${validate(field)?":rules = '${fldName}_rule'":""}
                  label="$label"
                  ${isDisabled(dataframe, field)?":disabled=true":""}
                  item-text="${displayMember}"
                  item-value="${valueMember}"
                  small-chips
                  $multiple
                  hide-no-data
                  hide-selected
                  ${isReadOnly?"readonly":''}
                  ${toolTip(field)}
                   $onSelect
                  $typeString
            ></v-combobox>
        """
    }
}
