/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.DFButton
import com.elintegro.erf.dataframe.ScriptBuilder
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore
import com.elintegro.erf.widget.Widget
import grails.util.Holders
import grails.util.Environment
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

    String getVuePropVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        return """ '${dataframe.dataframeName}_data' """

    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        return """$dataVariable:\"\",\n"""

    }

    String getVueSaveVariables(DataframeVue dataframe, Map field){
        String thisFieldName = dataframe.getFieldId(field)
        String dataVariable = dataframe.getDataVariableForVue(field)
//        String dataVariable = dataframe.getDataVariableForVueCapitalized(field)
        return """allParams['$thisFieldName'] = this.$dataVariable;\n"""
    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        def defaultValue = field.defaultValue?:""
        String fillState = ""
        return """this.$dataVariable = response['$key']?response['$key']:"$defaultValue\";
                """
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

    String getEmbeddedCompScript(){
        return ""
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

/*    protected DataframeVue getDataframe(dataframeName){
        DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(dataframeName)
        return refDataframe
    }*/

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

/*
    public static String getHashIdSpan(String hashId){
        return "<span " + getHashIdAttribute(hashId) + "/>"
    }
*/


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
}
