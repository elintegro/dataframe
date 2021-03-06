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

import com.elintegro.annotation.OverridableByEditor
import com.elintegro.erf.dataframe.DFButton
import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.ScriptBuilder
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore
import com.elintegro.erf.widget.Widget
import grails.util.Holders
import grails.util.Environment
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.web.json.JSONObject
import org.springframework.context.ApplicationContext
import org.springframework.context.i18n.LocaleContextHolder

/**
 * This abstract could classdefines a main method for each widget;
 * TODO: consider to turn it into the Interface if not implementation is required here
 *
 * @author Eugenelip
 *
 */
abstract class WidgetVue extends Widget<DataframeVue>{
    /** Dependency injection for the springSecurityService. */
    public static final String items = "items"
    public static final String excon = "excon"

    //This assigns a new value and returns true if new value was different then the old one
    @Override
    boolean populateDomainInstanceValue(Dataframe dataframe, def domainInstance, DomainClassInfo domainMetaData, String fieldName, Map field, def inputValue){
        if(inputValue.value == null || inputValue.value == "") return true

        if(isReadOnly(field)){
            return false
        }
        def oldfldVal = domainInstance."${fieldName}"
        String myDomainAlias = domainMetaData.getDomainAlias()
        def newValue = dataframe.getTypeCastValue2(myDomainAlias, fieldName, inputValue.value)
        if(oldfldVal == newValue){
            return false
        }
        if(isMandatory(field) && !newValue){
            return false
        }
        domainInstance."${fieldName}" = newValue
        return true
    }

    @Override
    boolean setPersistedValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        jData?.persisters?."${domainAlias}"."${fieldName}".value = value
    }

    @Override
    boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        jData?.transits?."${fieldName}".value = value
    }

    String getVuePropVariable(DataframeVue dataframe, Map field) {
        return """ '${dataframe.dataframeName}_data' """

    }

    protected String getFieldType(Map fieldProps){
        return fieldProps.containsKey("domain") ? PERSISTERS : TRANSITS
    }

    public String getFieldJSONNameVue(Map field){
            String fldDomainAndDot = (field.domain?.domainAlias?.size() > 0) ? "${field.domain.domainAlias}${DOT}" : ""
            String fieldType = field.containsKey("domain") ? PERSISTERS : TRANSITS
            return "state.${fieldType}${DOT}${fldDomainAndDot}${field.name}";
    }

    public String getFieldJSONModelNameVue(Map field){
        return "${getFieldJSONNameVue(field)}.value";
    }
    public String getFieldJSONNameVueWithoutState(Map field){
        String fldDomainAndDot = (field.domain?.domainAlias?.size() > 0) ? "${field.domain.domainAlias}${DOT}" : ""
        String fieldType = field.containsKey("domain") ? PERSISTERS : TRANSITS
        return "${fieldType}${DOT}${fldDomainAndDot}${field.name}.value";
    }
    public String getFieldJSONItems(Map field){
        String fldDomainAndDot = (field.domain?.domainAlias?.size() > 0) ? "${field.domain.domainAlias}${DOT}" : ""
        return "${getFieldJSONNameVue(field)}${DOT}${items}";
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        String validationString = """ ${dataVariable}_rule: "",\n"""
        if(validate(field)){
            String validationRules = validationRules(field)
            //TODO: Here we need to use right variable from our state structure! And need to check in any Widget!
            validationString = """ ${dataVariable}_rule: $validationRules,\n"""
        }
        return """$validationString"""
    }

    //EU!!! This is where the DVue component variable is created TODO: depricate this and build the new one, using JSON
    String getStateDataVariable(DataframeVue dataframe, Map field){

        String dataVariable = dataframe.getDataVariableForVue(field)
        return """$dataVariable:\"\",\n"""
    }

    //This method may return additional data for each Widget, by deafault it returns empty Map
    Map getStateDataVariablesMap(DataframeVue dataframe, Map field){
        return [:]
    }

    String[] getStateDataProps(DataframeVue dataframe, Map field){
        return [field.domain?.key, field.name, field.defaultValue]
    }


    String getVueSaveVariables(DataframeVue dataframe, Map field){
        return ""
    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        return ""
    }

    protected String applyLayout(DataframeVue df, Map field, String html){
        String layout = field.layout
        if(layout.contains("[FIELD_SCRIPT]")){
            html = layout.replace("[FIELD_SCRIPT]", html)
        }

        return html

    }

    String getComputedMethods(DataframeVue dataframe, Map field, String divId, String fldId, String key){
        return """"""
    }
    String getCreatedScript(DataframeVue dataframe, Map field, String divId, String fldId, String key){
        return """"""
    }
    protected boolean isSearchable(Map field){
        if(field && field.search){
            return true
        }
        return false
    }

    public String getFlexAttr(DataframeVue dataframe, Map field){
        return field.flexAttr?:""
    }

    String getEmbdDfrName(){
        return ""
    }
    /**
     * The javascript command that sets the value for each widget.
     * By default sets the value of a textfield
     * The name of the json array must be json: this is a convention, Dataframe class counts on this
     *
     * */

    @Override
    String getHeaderScript(DataframeVue dataframe, Map info, String divId) {
        return null
    }

    @Override
    String getBodyScript(DataframeVue dataframe, Map info) {
        return null
    }

    @Override
    String getEnabledDisabledFunction(DataframeVue dataframe, Map field) {
        return null
    }

    String getValueScript(DataframeVue dataframe, Map field, String divId, String fldId, String key){
        return """""";
    }

    @Override
    public Object getInitValues(DataframeVue df, Map field){
        return ""
    }

    String getMethodsScript(DataframeVue dataframe, Map field, String divId, String fldId, String key){
        return """""";
    }

    public static toolTip(def option){
        Map tooltip = option?.tooltip
        if (tooltip){
            String message = tooltip?.message
            if (tooltip?.internationalization){
                message = Holders.getGrailsApplication().getParentContext().getMessage(message, null, message, LocaleContextHolder.getLocale())
            }
            String tooltipOptions = tooltip?.options
            return """
             v-tooltip="{
                content: '$message',
                ${tooltipOptions?:''}
            }"
        """
        }else {
            return """"""
        }
    }

    public constructBodyScript(VueJsBuilder scriptBuilder, DataframeVue dataframe, Map field, String divId, String fldId, String key){
        scriptBuilder.addToComputedScript(getComputedMethods(dataframe, field, divId, fldId, key))
        scriptBuilder.addToMethodScript(getMethodsScript(dataframe, field, divId, fldId, key))
        scriptBuilder.addToCreatedScript(getCreatedScript(dataframe, field, divId, fldId, key))
        getWidgetScript(scriptBuilder,dataframe, field)
    }

    void getWidgetScript(VueJsBuilder scriptBuilder, DataframeVue dataframe, Map field){

    }

    public static String wrapWithSpringSecurity(DFButton field, String ret){
        return wrapWithSpringSecurity(field.roles, field.accessType, ret)
    }

    public static String wrapWithSpringSecurity(Map field, String ret){
        return wrapWithSpringSecurity(field.roles, field.accessType, ret)
    }


    public static String wrapWithSpringSecurityVue(DFButton field, String ret){
        return wrapWithSpringSecurityVue(field.roles, field.accessType, ret)
    }

    public static String wrapWithSpringSecurityVue(Map field, String ret){
        return wrapWithSpringSecurityVue(field.roles, field.accessType, ret)
    }


    /*
        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR">
                secure stuff here
        </sec:ifAnyGranted>

        <sec:ifAllGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR">
            secure stuff here
        </sec:ifAllGranted>
    */
    public static String wrapWithSpringSecurity(String roles, String accessType, String ret){
        if(roles){
            accessType = accessType? accessType : "ifAnyGranted"
            String secTag ="sec:" + accessType
            ret = "<" + secTag + " roles=\"" + roles + "\">\n" + ret +  "\n</" + secTag +">"
        }
        return ret
    }


/*
    <sec-if-all-granted :user="user" :roles="roles">
        <p>Stuff to show if user has all roles</p>
    </sec-if-all-granted>

*/


    public static String wrapWithSpringSecurityVue(String roles, String accessType, String ret){
        if(roles){
            Date now = new Date();
            Math.random()
            String hashId =  "h" + Math.random() + now.getTime()
            accessType = accessType? accessType : "ifAnyGranted"
            String secTag = SecurityTag.valueOf(accessType).getName()
            ret = getSecTagWithIDAttrib(secTag,  hashId) + " :user=\"user\" :roles=\"" + roles + "\">\n" + ret +  "\n" + getHashIdEndMark(hashId) + "\n</" + secTag +">"
        }
        return ret
    }

    public static String getSecTagWithIDAttrib(String secTag, String hashId){
        return "<" + secTag + getHashIdAttribute(hashId)
    }

    public static String getHashIdEndMark(String hashId){
        return "<!--"+ hashId + "-->"
    }
    public static String getHashIdAttribute(String hashId){
        return " :id=\"" + hashId + "\""
    }

    public static enum SecurityTag {

        ifAllGranted ("sec-if-all-granted"),
        ifAnyGranted ("sec-if-any-granted"),
        ifNoGranted ("sec-if-no-granted")

        public static List<SecurityTag> getAllTags(){
            return [ifAllGranted, ifAnyGranted, ifNoGranted]
        }

        private String name


        SecurityTag(String name) {
            this.name = name

        }

        public String getName() {
            return name
        }

    }

    protected DataframeVue getReferenceDataframe(def refDataframe){
        if(!refDataframe){
            println "RefDataframe doesnot exist but is required."
            return null
        }
        DataframeVue.getDataframeBeanFromReference(refDataframe)
    }

    protected static def getMessageSource(){
        ApplicationContext ctx = Holders.getApplicationContext()
        def msgSrc = ctx.getBean("messageSource");
        return msgSrc ;
    }

    protected String getImageUrl(field){

        def contextPath = Holders.config.images
        def s3ContextPath = Holders.config.aws.s3
        def baseUrl = Holders.config.grails.serverURL
        String imgUrl =""
        def homeDir = ""
        /*if(System.getProperty("prop_dir")){
            homeDir = new File(System.getProperty("prop_dir"))?.getPath()
        }*/
        String filePath = contextPath.imageDirectory
        if(contextPath.saveLocation.local){
            if(Environment.current == Environment.DEVELOPMENT){
                imgUrl = contextPath.storageLocation + "/" +filePath
            }else{
                imgUrl = baseUrl + "/" + filePath
            }
        }else if(contextPath.saveLocation.s3){
            imgUrl = s3ContextPath.defaultS3Url
        }
        if(field.url){
            imgUrl = field.url
        }

        imgUrl = checkImageUrlFormat(imgUrl)
        return imgUrl
    }

    protected String checkImageUrlFormat(imgUrl){
        def slashInx = imgUrl.lastIndexOf("/")
        if(slashInx <= 0 || imgUrl.substring(imgUrl.lastIndexOf("/")+1)){
            return imgUrl + "/"
        }
        return imgUrl
    }

    protected getModelString(DataframeVue dataframe, Map field){
        return "state."+getFieldName(dataframe, field)
    }
    protected String getFieldName(DataframeVue dataframe, Map field){
        return dataframe.getDataVariableForVue(field)
    }

    protected boolean validate(Map field){
        boolean applyRule = false
        applyRule = applyRule || field.validate
        applyRule = applyRule || isMandatory(field)
        applyRule = applyRule || field.validationRules

        return applyRule
    }

    protected String validationRules(Map field){
        StringBuilder rules = new StringBuilder()
        rules.append("[")
        if(isMandatory(field)){
            String message = getMessageSource().getMessage("default.required.message", [field.label?:null] as Object[], "default.required.message", LocaleContextHolder.getLocale())
            rules.append("""  v => !!v || '$message', """)
        }

        if(field.validationRules){
            def rulesList = field.validationRules
            rulesList.each{
                rules.append(""" ${it.condition} """)
                if(it.message){
                    String message = getMessageSource().getMessage(it.message, null, it.message, LocaleContextHolder.getLocale())
                    rules.append(" || '"+message+"' ")
                }
                    rules.append(""" , """)

            }
        }

        if(field.validate || isMandatory(field))
            rules.append(widgetValidationRule(field))

        rules.append("]")
        return rules.toString()
    }

    protected String widgetValidationRule(Map field){
        return ""
    }
    protected boolean isDisabled(DataframeVue dataframe, Map field){

        def fldMetadata = dataframe.fieldsMetadata.get(field.name)
        boolean isPk = fldMetadata?fldMetadata.pk:false
        def disabled = field.disabled == null? false : field.disabled;
        return  (isPk == true)? true: disabled;
    }

    protected String getHeight(Map field, String _default = "auto"){
        return field.height?:_default
    }

    protected String getWidth(Map field, String _default = "auto"){
        return field.width?:_default
    }
    protected String getMaxWidth(Map field, String _default = "500px"){
        return field.MaxWidth?:_default
    }


    protected String getAttr(Map field){
        return field.attr?:""
    }

    protected String getLabel(Map field){
        String mandatory = isMandatory(field)?" *":""
        String label = field.label + mandatory
        return label
    }

    protected static Map getDomainFieldJsonMap(DataframeVue dataframe, Map field){
        Map domainFieldMap = dataframe.domainFieldMap
        Map fieldJSON = [:]
        if(dataframe.isDatabaseField(field)){
            Map persisters = domainFieldMap.get(Dataframe.PERSISTERS)
            Map domainJSON = persisters.get(field.get(Dataframe.FIELD_PROP_DOMAIN_ALIAS))
            fieldJSON = domainJSON.get(field.get(Dataframe.FIELD_PROP_NAME))
        }else{
            Map transits = domainFieldMap.get(Dataframe.TRANSITS)
            fieldJSON = transits.get(field.get(Dataframe.FIELD_PROP_NAME))
        }
        return fieldJSON
    }

    protected static boolean isInitBeforePageLoad(Map field){
        if (field?.initBeforePageLoad){
            return true
        }
        return false
    }

}
