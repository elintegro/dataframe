/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development.
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.PageDFRegistryVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.layout.abs.LayoutVue
import com.elintegro.erf.widget.vue.WidgetVue
import com.elintegro.utils.DataframeFileUtil
import grails.plugin.springsecurity.SpringSecurityService
import grails.util.Environment
import groovy.util.logging.Slf4j
import org.apache.tomcat.util.http.fileupload.FileUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder

import javax.persistence.MapsId
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Slf4j
class ResultPageHtmlBuilder {

    //PageDFRegistryVue gcMainPgObj
    Set<String> builtComponents = new HashSet()
    static Set<String> registeredComponents = null
    StringBuilder globalLayoutCompScriptSb = new StringBuilder()
    static StringBuffer globalParametersInStore = new StringBuffer()
    @Autowired
    SpringSecurityService springSecurityService

    ResultPageHtmlBuilder(){
//        registeredComponents = new HashSet<>()
    }
    public static constructFinalHtml(dataframes){
//        LayoutVue.constructFinalHtml(dataframes)
    }

    public Map<String, String> getFinalHtmlandScript(PageDFRegistryVue gcMainPgObj){
        ResultPageHtmlBuilder rsb = new ResultPageHtmlBuilder()
        String containerLayoutS = gcMainPgObj.containerLayout
        List dataframesL = gcMainPgObj.allDataframesList
        if(!containerLayoutS){
            throw new DataframeException("Name of Container Layout is Mandatory!")
        }
        if(dataframesL.isEmpty()){
            throw new DataframeException("List of dataframes not found!")
        }

        def script = rsb.buildFinalScript(gcMainPgObj)

        if (Environment.current == Environment.DEVELOPMENT) {
            DataframeFileUtil.writeStringIntoFile("./logs/AppDataframe.vue", script.toString())
        }

        return script
    }

    private Map<String, String> buildFinalScript(PageDFRegistryVue gcMainPgObj){
        String containerLayoutS = gcMainPgObj.containerLayout
        List dataframesL = gcMainPgObj.allDataframesList
        registeredComponents = new HashSet<>()
        Map layoutStructM = constructLayoutComps(containerLayoutS)
        Map dfrComps = constructDfrComps(dataframesL)
        String initHmtl = layoutStructM.initHtml
        StringBuilder finalScriptSb = new StringBuilder()
        finalScriptSb.append("<script>\n")
//        finalScriptSb.append("<script type='text/babel'>\n")
        //Initialize Store
        finalScriptSb.append("let store = new Vuex.Store({\n")
        finalScriptSb.append(dfrComps.vueStore)
        finalScriptSb.append("})\n")

        finalScriptSb.append(dfrComps.vueGlobalCompScript) //append globbal dataframe components
        finalScriptSb.append(globalLayoutCompScriptSb.toString()) // append global layout components
        finalScriptSb.append(dfrComps.dfrCompScript) // append other dfr components
        finalScriptSb.append(layoutStructM.layoutCompScript) // append other layout components
        //Initialize i18n

        finalScriptSb.append(""" const i18n = new VueI18n({
                                 locale:'${LocaleContextHolder.getLocale().getLanguage()}',
                                 messages
                           });""")
//Initialize Router
        finalScriptSb.append("const router = new VueRouter({\n")// Initialize Router
//        finalScriptSb.append("mode:'history',\n")
        finalScriptSb.append(" routes: [\n")
//        finalScriptSb.append("{path: '/:routeId', components: {default: $defaultComp, $dfrComps.dfrCompRegisterationScript}},\n")
//        finalScriptSb.append(vueRoutes.toString()) // from layouts
        finalScriptSb.append(dfrComps.vueRoutes) //from dfrs
        finalScriptSb.append("]\n")
        finalScriptSb.append("})\n")
//Initialize main app
        finalScriptSb.append("var app = new Vue ({\nel:'#app',\n") // Vue Instance
        finalScriptSb.append("router,\n") //Inject router to vue instance
        finalScriptSb.append("store,\n") //Inject store
        finalScriptSb.append("i18n,\n") //Inject i18n
        finalScriptSb.append("vuetify: new Vuetify(),\n") //Inject vuetify
        finalScriptSb.append("data(){ return {\n")
        finalScriptSb.append("drawer : null,\n") //Insert some external data
        finalScriptSb.append("}\n},\n") // data addition completed
        finalScriptSb.append("created () {\n")
//        finalScriptSb.append("this.\$vuetify.rtl=true;\n")
        finalScriptSb.append("},\n")
        finalScriptSb.append("components:{\n")
        String layoutCompS = constructCompRegistrationForMainLayout(gcMainPgObj)
        finalScriptSb.append("$layoutCompS") // layout main component registration
        finalScriptSb.append(getMainPgVueCompRegistrationString(gcMainPgObj)) // dfr components registration
        finalScriptSb.append("\n},\n")// components registration completed
        finalScriptSb.append("methods:{\n")
        finalScriptSb.append("""
                   
                    refreshDataForGrid: function(response, fldName, operation = "U"){
                       
                          const newData = response.newData;
                          if(!newData) return;
                          const selectedRow = this[fldName +'_selectedrow'];
                          const editedIndex = this.state[fldName +'_items'].indexOf(selectedRow);
                          let row = {};
                          for(let key in newData){
                              let dataMap = newData[key];
                              for(let j in dataMap){
                                 if(selectedRow){
                                    if (key in selectedRow) {
                                      row[key] = dataMap[j];
                                    }
                                 } else {
                                    row[key] = dataMap[j];
                                 }
                                  
                              }
                          }
                          if (operation==="I") {
                              this.state[fldName +'_items'].push(row)
                          } else {
                              Object.assign(this.state[fldName +'_items'][editedIndex], row)
                          }
//                          this.gridDataframes[refreshParams.dataframe] = false; 
                },

         """)
        finalScriptSb.append("}, \n") //methods end
        finalScriptSb.append("})")
        finalScriptSb.append("</script>")
        return [initHtml:initHmtl, finalScript:finalScriptSb.toString()]
    }
    public constructLayoutComps(String contLytStr){

        LayoutVue contLytObj = LayoutVue.getLayoutVue(contLytStr.trim())
        StringBuilder initHtml = new StringBuilder()
        String finalLayoutScript = constructFinalLayoutScript(contLytObj, initHtml)
        [initHtml: initHtml.toString(), layoutCompScript: finalLayoutScript]
    }

    private static String constructCompRegistrationForMainLayout(PageDFRegistryVue gcMainPgObj){
        def containerLayoutS = gcMainPgObj.containerLayout

        LayoutVue contLytObj = LayoutVue.getLayoutVue(containerLayoutS)
        if(!contLytObj.children){
            return ""
        }
        StringBuilder ltSb = new StringBuilder()
        int index = 0;
        for(String ltS : contLytObj.children){
            LayoutVue lytT = LayoutVue.getLayoutVue(ltS)
            if(!registeredComponents.contains(ltS)){
                ltSb.append(VueJsBuilder.createCompRegistrationString(ltS, index))
                index++;
                registeredComponents.add(ltS)
            }
        }

        return ltSb.toString()
    }

    private String constructFinalLayoutScript(LayoutVue contLytObj, StringBuilder initHtml){
        List children = contLytObj.children
        String wrapperLayout = contLytObj.layoutPlaceHolder
        StringBuilder resultPageScript = new StringBuilder()
        if(children.isEmpty()){
            return ""
        }
        for(String ch : children){
            if(ch.trim() != "") {
                LayoutVue layoutObj = LayoutVue.getLayoutVue(ch)
                prepareVueLayout(layoutObj.children, resultPageScript, layoutObj)
            }
        }
        initHtml.append(wrapperLayout)
        return resultPageScript.toString()
    }

    private def prepareVueLayout(children, resultPageScript, disObj){
        if(children.isEmpty()){
            constructSectionalComponent(resultPageScript, disObj)
            return
        }
        for(String ch : children){
            if(builtComponents.contains(ch)){
                continue
            }
            if(ch.trim() != ""){
                LayoutVue layoutObj = LayoutVue.getLayoutVue(ch)
                if(!registeredComponents.contains(ch) && !layoutObj.isGlobal) {
                    disObj.compRegScript.append(VueJsBuilder.createCompRegistrationString(ch))
                    registeredComponents.add(ch)
                }
                List childs = layoutObj.children
                prepareVueLayout(childs, resultPageScript, layoutObj)
            }
        }
        constructSectionalComponent(resultPageScript, disObj)
    }

    private String getMainPgVueCompRegistrationString(PageDFRegistryVue gcMainPgObj){
        def mainPageDataframeList= gcMainPgObj.dataframesToShowInMainPage
        if(!mainPageDataframeList){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        for(String s : mainPageDataframeList){
            if(!builtComponents.contains(s)){
                DataframeVue dfrT = DataframeVue.getDataframe(s) // todo check if the component is Layout or Dataframe first
                if(!registeredComponents.contains(s)){
                    sb.append(VueJsBuilder.createCompRegistrationString(s.trim()))
                    registeredComponents.add(s)
                }
            }
        }

        return sb.toString()
    }

    private void constructSectionalComponent(StringBuilder resultPageScript, LayoutVue disObj){
        StringBuilder compBuilder = new StringBuilder()
        String layoutPlaceHolder = disObj.layoutPlaceHolder
        String layoutName = disObj.layoutBeanName
        String formatPlaceholder = "["+layoutName + "]"
        if(disObj.isGlobal){
            compBuilder.append("Vue.component('${layoutName}',{\n")
            compBuilder.append("name: '${layoutName}',\n")
            disObj.componentRegistered = true
        }else{
            disObj.componentRegistered = false
            compBuilder.append("const ${layoutName}Comp = {\n")
        }
        compBuilder.append("template:`")
        compBuilder.append(layoutPlaceHolder)
        compBuilder.append("`,\n")
        compBuilder.append("components:{\n") //register embedded components
        compBuilder.append(disObj.compRegScript.toString())
        disObj.compRegScript.setLength(0) //Resetting compRegScript for another layout obj
        if(!disObj.childDataframes.isEmpty()){
            for(String compS : disObj.childDataframes){
                DataframeVue lytT = DataframeVue.getDataframe(compS) // todo check if component is Layout or Dataframe first
                if(!registeredComponents.contains(compS)){
                    compBuilder.append(VueJsBuilder.createCompRegistrationString(compS))
//                    lytT.componentRegistered = true
                    registeredComponents.add(compS)
                }
            }
        }
        compBuilder.append("},\n")
        if(disObj.isGlobal){
            compBuilder.append("})\n")
            globalLayoutCompScriptSb.append(compBuilder.toString())
        }else{
            compBuilder.append("}\n")
            resultPageScript.append(compBuilder.toString())
        }
        builtComponents.add(layoutName) // Add the built components to this List. Remove later if not used.
    }

    private Map constructDfrComps(dataframes){
        DfrCompBuilder dfrCompBuilder = new DfrCompBuilder()
        return dfrCompBuilder.constructDfrComps(dataframes)
    }


    public String applySecurityFilter(String resultPage /*, SpringSecurityService springSecurityService*/){


        List<String> roles = getRoles()

        List<WidgetVue.SecurityTag> tags = WidgetVue.SecurityTag.getAllTags()


        for(WidgetVue.SecurityTag tag: tags){
            resultPage = removeSecuritySections(resultPage, tag, roles)
        }

        return resultPage


    }

    private String removeSecuritySections(String resultPage, WidgetVue.SecurityTag secTag, List<String> userRoles) {

        String seTagName = secTag.name
        String secTagEndStr = "</" + seTagName + ">"
        String secTagStartStr = "<" + seTagName
        String resultSecSection = new String(resultPage)
        int secTagStartPos = resultPage.indexOf(secTagStartStr)

        while (secTagStartPos > -1) {
            StringBuilder sbResult = new StringBuilder()
            sbResult.append(resultSecSection.substring(0, secTagStartPos))
            //LOOKING FOR THE ID:
            int idPosition = resultSecSection.indexOf(":id=\"", secTagStartPos)
            int idPositionEnd = resultSecSection.indexOf("\"", idPosition + 6)
            //Get the hashId
            String hashId = resultSecSection.substring(idPosition+5, idPositionEnd)
            //Find the closing tag for this hashId
            int closingTagMarkPos = resultSecSection.indexOf(WidgetVue.getHashIdEndMark(hashId), idPositionEnd)
            int afterClosingTagPosition = resultSecSection.indexOf(secTagEndStr, closingTagMarkPos) + secTagEndStr.length() +1
            int securedSectionStartPos =  resultSecSection.indexOf(">", idPositionEnd)

            String securitySectionWithTags = resultSecSection.substring(secTagStartPos, afterClosingTagPosition - 1)

/*
            Document doc = Jsoup.parse(securitySectionWithTags);
            Element securityElement = doc.select(seTagName).first()
            String userRolesStr1 = securityElement.attr(":roles")
*/

            String userRolesStr = securitySectionWithTags.substring(securitySectionWithTags.indexOf(":roles=\"") + 8, securitySectionWithTags.indexOf("\">")  )

            String rolesStr = userRolesStr.replaceAll("[\\[|\\]]","")
            String[] rolesInAttr = rolesStr?.split("\\s*,\\s*")
            if(rolesInAttr.length > 1){
                log.debug("Stop")
            }
            //String hashId = securityElement.attr(":id")
            List<String> rolesInAttribute = rolesInAttr?.toList()

            boolean sectionAllowed = ifSectionAllowed(userRoles, secTag, rolesInAttribute)

            if (sectionAllowed) {
                String securitySection = resultSecSection.substring(securedSectionStartPos + 1, closingTagMarkPos - 1 )
                // append the section content, excluding the security tags
                sbResult.append(securitySection)
            }

            sbResult.append(resultSecSection.substring(afterClosingTagPosition-1))
            resultSecSection = sbResult.toString()
            secTagStartPos = resultSecSection.indexOf(secTagStartStr)

        }//End of while of security tags

        return resultSecSection
    }

    public String toText(Elements elms){
        StringBuilder sb = new StringBuilder()
        elms.each { elem->
            if(elem.hasText()){
                sb.append(elem.text())
            }
            Elements chElems = elem.children()
            if( chElems ){
                sb.append(toText(chElems))
            }

        }
        return sb.toString()
    }


    public boolean ifSectionAllowed(List<String> userRoles, WidgetVue.SecurityTag secTag, List<String> rolesInAttribute){
        Set<String> userRolesSet = new HashSet<String>(userRoles);
        if(secTag == WidgetVue.SecurityTag.ifAnyGranted){
            for(String actionRole: rolesInAttribute){
                if(userRolesSet.contains(actionRole)){
                    return true
                }
            }
            return false
        }else if(secTag == WidgetVue.SecurityTag.ifAllGranted){
            for(String actionRole: rolesInAttribute){
                if(!userRolesSet.contains(actionRole)){
                    return false
                }
            }
            return true
        }else if(secTag == WidgetVue.SecurityTag.ifNoGranted){
            for(String actionRole: rolesInAttribute){
                if(userRolesSet.contains(actionRole)){
                    return false
                }
            }
            return true
        }
    }

    private List<String> getRoles(){
        def principal = springSecurityService?.getPrincipal()
        List<String> roleNames = principal?.authorities*.authority
        return roleNames
    }

    public class Element_{
        Element el
        boolean allowed = true
        public Element_(Element el){
            this.el = el
        }
        void setNotAllowed(){
            allowed = false
        }
    }


}

