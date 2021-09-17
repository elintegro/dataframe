package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.vue.DataframeConstants
import com.elintegro.erf.dataframe.vue.DataframeVue
import org.apache.commons.lang.StringUtils
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.springframework.context.i18n.LocaleContextHolder

abstract class CollectionWidgetVue extends WidgetVue {
    //This assigns a new value and returns true if new value was different then the old one
    public String getHtml(DataframeVue dataframe, Map field){
        return ""
    }
    @Override
    boolean populateDomainInstanceValue(Dataframe dataframe, def domainInstance, DomainClassInfo domainClassInfo, String fieldName, Map field, def inputValue){
        if(isReadOnly(field)) return false
        if(!inputValue || !inputValue.value) return false

        JSONArray selectedItems =  (inputValue.value instanceof JSONArray)?inputValue.value:convertToJSONArrayIfSingleJSONObject(inputValue.value)

        if(!domainClassInfo.isAssociation(fieldName) || domainClassInfo.isToOne(fieldName)){ // this means we just want to apply description value to the text field without association with any other entity
            if (selectedItems.size() > 0){
                Map requestInputValue = ["value":""]
                if (field?.valueMember){
                    requestInputValue.value = selectedItems[0].getAt(field?.valueMember)
                }else {
                    requestInputValue.value = inputValue.value[0] //TODO: check if there is not exception in case of single choice
                }
                super.populateDomainInstanceValue(dataframe, domainInstance, domainClassInfo, fieldName, field, requestInputValue)
            }else {
                def oldfldVal = domainInstance."${fieldName}"
                if(oldfldVal == inputValue.value) return false
                domainInstance."${fieldName}" = inputValue.value
            }
        }else if(domainClassInfo.isToMany(fieldName)){
            return saveHasManyAssociation(selectedItems, domainClassInfo.getRefDomainClass(fieldName), fieldName, domainInstance)
        }
        return true
    }

    private JSONArray convertToJSONArrayIfSingleJSONObject(JSONObject value){
        JSONArray jn = new JSONArray()
        jn.add(value)
        return jn
    }

    boolean setPersistedValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        Map additionalData = loadAdditionalData(dfInstance, fieldProps, fieldName, additionalDataRequestParamMap, sessionHibernate)
        if(additionalData && additionalData.containsKey("items")){
            List items = (List)additionalData.items
            jData?.persisters?."${domainAlias}"."${fieldName}".value = getValue(fieldProps, value, fieldName)
            jData?.persisters?."${domainAlias}"."${fieldName}".items = items
        }
    }

    boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
//        super.setTransientValueToResponse(jData, value, domainAlias, fieldName)
        Map additionalData = loadAdditionalData(dfInstance, fieldProps, fieldName, additionalDataRequestParamMap, sessionHibernate)
        if(additionalData && additionalData.containsKey("items")){
            List items = (List)additionalData.items
            jData?.transits?."${fieldName}".value = getValue(fieldProps, value, fieldName)
            jData?.transits?."${fieldName}".items = items
        }
    }

    private List getValueList(Map fieldProps, List items, Object value, String fieldName){
        List valueList = null
        String displayMember = fieldProps.displayMember?:fieldName
        if(items && value){
            valueList = new ArrayList()
            for(int j=0; j<items.size(); j++){
                Map item = (Map) items[j]
                for(int k=0; k<value.size(); k++){
                    if(item."$displayMember" == value[k]?."$displayMember"){//check if same item and add to list
                        valueList.add(item)
                        continue;
                    }
                }
            }
        }
        return valueList
    }

    //todo: how to create value list without being dependent on displayMember and valueMember
    private static def getValue(Map fieldProps, Object value, String fieldName){
        List valueList = []
        String displayMember = fieldProps.displayMember?:fieldName
        String valueMember = fieldProps.valueMember?:fieldName
        if (value instanceof Collection<?>){
            for(int k=0; k<value.size(); k++){
                valueList.add([(displayMember):value[k]?."$displayMember", (valueMember):value[k]?."$valueMember"])
            }
            return valueList
        }
        return value?[(displayMember):value?."$displayMember", (valueMember):value?."$valueMember"]:null;
    }

    private boolean isSelectionEqualsToOld(Collection array1, Collection array2){
        boolean isEqual = true;
        if (array1.size() != array2.size()) {
            return false;
        } else {
            Set<Object> s1 = getSetOfIds(array1)
            Set<Object> s2 = getSetOfIds(array2)
            return isSetsEqual(s1, s2)
        }

        return isEqual
    }

    private Set<Object> getSetOfIds(Collection ar1){
        Set<Object> set1 = new HashSet()
        ar1.each{el -> set1.add(el.id)}
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
        addAssociationToDomainInstance(inputValue, refDomainClass, fieldName, domainInstance)
        return true
    }

    private boolean addAssociationToDomainInstance(JSONArray inputValue, def refDomainClass, String fieldName, def domainInstance){
        domainInstance?.(StringUtils.uncapitalize(fieldName))?.clear()
        inputValue.each{val ->
            val.each{
                if(it.key == "id"){// todo change this to value member?
                    def refDomainObj  = refDomainClass.get(Long.valueOf(it.value))
                    String fn = fieldName.capitalize()
                    domainInstance."addTo${fieldName.capitalize()}"(refDomainObj)
                }
            }
        }
    }

    protected Map generateInitialData(DataframeVue dataframe, Map field){

        if(!isInitBeforePageLoad(field)){
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
        String defaultValue = field.defaultValue
        result = getHqlData(dataframe, field, defaultValue, valueMember, displayMember)
 /*       if (domainAlias && dataframe.writableDomains) {
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
        }*/
        return result
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
                    Map value1 = [(valueMember):it.getAt(valueMember), (displayMember):getMessageSource().getMessage(displayMemberValue, null, displayMemberValue, LocaleContextHolder.getLocale())]
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

    private Map getEnumData(Map field, Class enumClass, String defaultValue){
        String displayMember = field.displayMember?:"name"
        String valueMember = field.valueMember?:"id"
        def enumList = getEnumListFromForClass(enumClass)
        List res = new ArrayList(enumList.size())
        def selMap = null
        enumList.each {
            String displayValue = it?."$displayMember"
            if(field.internationalize){
                displayValue = getMessageSource().getMessage(displayValue, null, displayValue, LocaleContextHolder.getLocale())
            }
            if (defaultValue && defaultValue.equals(displayValue)){
                selMap = [(displayMember):displayValue, (valueMember):it?."$valueMember"]
            }
            res.push([(displayMember):displayValue, (valueMember):it?."$valueMember"])
        }
        field.put("isEnumType", true)
        return [keys:enumList, result: res, selectedData: selMap]

    }

    public Map loadAdditionalData(DataframeInstance dfInst, Map fieldProps, String fieldnameToReload, Map inputData, def session){
        Dataframe df = dfInst.df;
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
            Map sessionParams = dfInst.sessionParams
            if (sessionParams){
                inputData << sessionParams
            }
            ParsedHql parsedHql = fieldProps?.get(DataframeConstants.FIELD_PROP_REF_FIELD_PARSED_HQL)
            DbResult dbRes = new DbResult(wdgHql, inputData, session, parsedHql);
            List resultList = dbRes.getResultList()
            List res = new ArrayList(resultList.size())
            String valueMember = fieldProps.valueMember?:"id"
            String displayMember = fieldProps.displayMember?:"name"
            if(fieldProps.internationalize){
                resultList.each {
                    Map value1 = [(valueMember):it.getAt(valueMember), (displayMember):getMessageSource().getMessage(it.getAt("$displayMember"), null, it.getAt("$displayMember"), LocaleContextHolder.getLocale())]
                    res.push(value1)
                }
            }else {
                res = resultList
            }
            fieldProps["dictionary"] = res
        }
//        Map result = buildData(dfInst, fieldProps, fieldnameToReload)
        return [items:fieldProps.dictionary]
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

}
