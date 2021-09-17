/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.layout.abs

import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.ViewRoutes
import com.elintegro.erf.widget.Widget
import com.elintegro.erf.widget.vue.WidgetVue
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.springframework.context.i18n.LocaleContextHolder

@Slf4j
class LayoutVue extends Layout {


    static Map layouts = [:]
    private String name

    final static String SPACE_SEPARATOR = " "
    int numberFields = 0

    int numberOfNonCustomFields = 0
    boolean layoutAddedToResultPage = false

    DataframeVue df
    LayoutVue parentLayout
    String layoutPlaceHolder = ""

    String layoutBeanName = ""

    Map params = [:]

    Map layoutButtons = [:]

    static def grailsApplication

    static final String DEFAULT_FIELD_LAYOUT="""
						<v-col cols='0' <%print gridValueString%> <%print flexAttr%> ><% print widget %></v-col>
	                     """
//    <div id='<% print divId %>'>
    static final String DEFAULT_DATAFRAME_LAYOUT = """
            <v-col cols='12' xs='12' sm='6' md='4' lg='4' xl='4'>[DATAFRAME_FIELD]</v-col>"""
    static final String ALL_OTHER_FIELDS = "[ALL_OTHER_FIELDS]";
    static final String ALL_OTHER_FIELDS_REGEXP = "\\[ALL_OTHER_FIELDS\\]";

    Map perFieldLayout = [:]

    //For Vue
    StringBuilder compRegScript = new StringBuilder()
    List childLayouts = [] // Add all the children's layout names
    List childDataframes = [] //Add all the components to register under this layouts component
    boolean isGlobal = false // Whether or not to register component globally
    static Map defaultCssGridValues = Holders.grailsApplication.config.vue.cssGridValues.Default?:[xs:'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
    static Map defaultButtonCssGridValues = ['xs':'12', 'sm':'12', 'md':'4', 'lg':'4', 'xl':'4']
    Map cssGridValues = defaultCssGridValues
    boolean componentRegistered = false //Set once the component is registered

    boolean route = false
    String currentRoute = ""
    String dataframeName = "dataframe"
    boolean wrapInForm = true
    String wrapperLayoutForFieldsInGroupOpenTag = ""
    String wrapperLayoutForFieldsInGroupCloseTag = ""
    String errorContainerHtml = ""
    //private StringBuilder constructFieldsStringBuilder = new StringBuilder()
    protected StringBuilder remainingFieldsStringBuilder = new StringBuilder() //[ALL_OTHER_FIELDS] construct string
    protected String dataframeLayout = layoutPlaceHolder
    private openingTagAttached = false
    int fieldCount = 0
    static Set<String> builtComponents = new HashSet()

    LayoutVue (){
        String defaultRoute = (layoutBeanName.replaceAll("vue","").replaceAll("Dataframe","").replaceAll("Layout", "").split(/(?=[A-Z])/).join("-")).toLowerCase();
        currentRoute = currentRoute?:defaultRoute
    }

    public Map constructLayoutComponents(){

        StringBuilder initHtml = new StringBuilder()
        StringBuilder globalComponentScript = new StringBuilder()
        StringBuilder vueRoutes = new StringBuilder()
        String finalLayoutScript = constructFinalLayoutScript(initHtml, globalComponentScript, vueRoutes)
        [initHtml: initHtml.toString(), layoutCompScript: finalLayoutScript, globalComponentScript:globalComponentScript.toString(), vueRoutes:vueRoutes.toString()]
    }

    private String constructFinalLayoutScript(StringBuilder initHtml, StringBuilder globalComponentScript, StringBuilder vueRoutes){
        String wrapperLayout = layoutPlaceHolder
        StringBuilder resultPageScript = new StringBuilder()
        if(childLayouts.isEmpty()){
            return ""
        }
        for(String ch : childLayouts){
            if(ch.trim() != "") {
                LayoutVue layoutObj = getLayoutVue(ch)
                layoutObj.prepareVueLayout(resultPageScript, globalComponentScript, vueRoutes)
            }
        }
        initHtml.append(wrapperLayout)
        builtComponents = new HashSet<>()
        return resultPageScript.toString()
    }

    public def prepareVueLayout( StringBuilder resultPageScript, StringBuilder globalComponentScript, StringBuilder vueRoutes){
        if(childLayouts.isEmpty()){
            constructComponentScript(resultPageScript, globalComponentScript, vueRoutes)
            return
        }
        for(String ch : childLayouts){
            if(builtComponents.contains(ch)){
                continue
            }
            if(ch.trim() != ""){
                if(!ResultPageHtmlBuilder.registeredComponents.contains(ch) && !isGlobal) {
                    compRegScript.append(VueJsBuilder.createCompRegistrationString(ch))
                    ResultPageHtmlBuilder.registeredComponents.add(ch)
                }
                LayoutVue layoutObj = getLayoutVue(ch)
                layoutObj.prepareVueLayout(resultPageScript, globalComponentScript, vueRoutes)
            }
        }
        constructComponentScript(resultPageScript, globalComponentScript, vueRoutes)
    }

    private void constructComponentScript(StringBuilder resultPageScript, StringBuilder globalComponentScript, StringBuilder vueRoutes){
        StringBuilder compBuilder = new StringBuilder()
//        String layoutPlaceHolder = disObj.layoutPlaceHolder
        String layoutName = layoutBeanName
        String formatPlaceholder = "["+layoutName + "]"
/*
        if(routeMap){
            vueRoutes.append(ViewRoutes.constructRoute(routeMap, layoutName))
            componentRegistered = true
            isGlobal = false
        }
*/
        if(isGlobal){
            compBuilder.append("Vue.component('${layoutName}',{\n")
            compBuilder.append("name: '${layoutName}',\n")
            componentRegistered = true
        }else{
            componentRegistered = false
            compBuilder.append("const ${layoutName}Comp = {\n")
        }
        compBuilder.append("template:`")
        compBuilder.append(layoutPlaceHolder)
        compBuilder.append("`,\n")
        compBuilder.append("components:{\n") //register embedded components
        compBuilder.append(compRegScript.toString())
        compRegScript.setLength(0) //Resetting compRegScript for another layout obj
        if(!childDataframes.isEmpty()){
            for(String compS : childDataframes){
                if(!ResultPageHtmlBuilder.registeredComponents.contains(compS)){
                    compBuilder.append(VueJsBuilder.createCompRegistrationString(compS))
                    ResultPageHtmlBuilder.registeredComponents.add(compS)
                }
            }
        }
        compBuilder.append("},\n")
        if(isGlobal){
            compBuilder.append("})\n")
            globalComponentScript.append(compBuilder.toString())
        }else{
            compBuilder.append("}\n")
            resultPageScript.append(compBuilder.toString())
        }
        builtComponents.add(layoutName) // Add the built components to this List. Remove later if not used.
    }

    void applyLayoutForField(StringBuilder resultPagehtml, StringBuilder fieldsHtmlBuilder, String widgetForm, String fieldName, String fldNameAlias, String childDataframe) throws LayoutException{
        String eitherFieldNamePlaceHolder = "[$fieldName]"
        String childFieldNamePlaceHolder = childDataframe.trim()?"[$childDataframe]":"[EMBEDDED_DATAFRAME_SCRIPT]"
        String orFieldNamePlaceHolder    = fldNameAlias?"[$fldNameAlias]":"[alias.$fieldName]"

        if(resultPagehtml.contains(childFieldNamePlaceHolder)){
            int i = resultPagehtml.indexOf(childFieldNamePlaceHolder)
            resultPagehtml.replace(i, i + childFieldNamePlaceHolder.length(), widgetForm)

        } else if(resultPagehtml.contains(eitherFieldNamePlaceHolder)){
            int i = resultPagehtml.indexOf(eitherFieldNamePlaceHolder)
            resultPagehtml.replace(i, i+ eitherFieldNamePlaceHolder.length(), widgetForm)
        } else if(resultPagehtml.contains(orFieldNamePlaceHolder)){
            int i = resultPagehtml.indexOf(orFieldNamePlaceHolder)
            resultPagehtml.replace(i, i + orFieldNamePlaceHolder.length(), widgetForm)
        } else {
            remainingFieldsStringBuilder.append(widgetForm)
        }
        fieldCount++
        if(fieldCount == numberFields){ // last field
            String dataframePlaceholder = "[$dataframeName]"
            if(resultPagehtml.contains(LayoutPlaceholder.DATAFRAME_SCRIPT) || resultPagehtml.contains(dataframePlaceholder)){
                fieldsHtmlBuilder.append(remainingFieldsStringBuilder.toString())
            } else {
                String remainingFieldsPlaceholder = LayoutPlaceholder.ALL_OTHER_FIELDS
                if(resultPagehtml.contains(remainingFieldsPlaceholder)){
                    int i = resultPagehtml.indexOf(remainingFieldsPlaceholder)
                    resultPagehtml.replace(i, i + remainingFieldsPlaceholder.length(), remainingFieldsStringBuilder.toString())
                }

                fieldsHtmlBuilder.append(dataframeLayout)
            }
            remainingFieldsStringBuilder.setLength(0)
            fieldCount = 0
        }
    }
    void applyLayout(String widgetForm, String fieldName) throws LayoutException {

        applyLayoutForField(widgetForm, fieldName)

    }

    private String getWrapperLayoutForFieldsInGroupOpenTag(){
        String fieldLayout = ""
        if(!wrapperLayoutForFieldsInGroupOpenTag.trim() || !wrapperLayoutForFieldsInGroupOpenTag.contains(dataframeName+'-form')){
            if(wrapInForm){
                wrapperLayoutForFieldsInGroupOpenTag = "<v-col xs='12' sm='12' md='12' lg='12' xl='12'><v-form  ref='${dataframeName}_form'><v-row wrap>\n"    // TODO change all other instances of formId
            }else{
                wrapperLayoutForFieldsInGroupOpenTag = "<div ref='${dataframeName}_form'><v-row wrap>\n"
            }
        }

        return wrapperLayoutForFieldsInGroupOpenTag
    }

    private String getWrapperLayoutForFieldsInGroupCloseTag(){
        if(!wrapperLayoutForFieldsInGroupCloseTag.trim()){
            if(wrapInForm) {
                wrapperLayoutForFieldsInGroupCloseTag = "</v-row></v-form></v-col>\n"
            }else{
                wrapperLayoutForFieldsInGroupCloseTag = "</v-row></div>\n"
            }
        }

        return wrapperLayoutForFieldsInGroupCloseTag
    }

    private String getErrorContainerHtml(){
        if(!errorContainerHtml){
            errorContainerHtml = " <div id='$dataframeName-errorContainer'></div>\n"
        }
        return errorContainerHtml
    }

    public void applyLayoutForDataframe(StringBuilder resultPageHtml, StringBuilder fieldsHtmlBuilder, String dataframeHtmltoAppend, DataframeVue df){

        String dfrHtml = fieldsHtmlBuilder.toString()?:""
        String formattedDfName = df.dataframeName?"["+df.dataframeName+"]":""
        String refFieldPlaceholder = "[REF_FIELD]"
        String defaultMarkupPlaceholder = LayoutPlaceholder.DATAFRAME_SCRIPT
        String layoutPlaceHolder = this.layoutPlaceHolder.toString()

        if(!dataframeLayout){
            dataframeLayout = layoutPlaceHolder
        }
        if(df.dataframeName == "vueApplicationFormDataframe"){
            println "helo"
        }

        if(resultPageHtml.contains(defaultMarkupPlaceholder)){
            formattedDfName = defaultMarkupPlaceholder
        }
        if(resultPageHtml.contains(formattedDfName)){

            StringBuilder sb = new StringBuilder()
            sb.append(getWrapperLayoutForFieldsInGroupOpenTag())
            sb.append(fieldsHtmlBuilder.toString())
            sb.append(getErrorContainerHtml())
            sb.append(dataframeHtmltoAppend)
            sb.append(getWrapperLayoutForFieldsInGroupCloseTag())
            int i = resultPageHtml.indexOf(formattedDfName)
            resultPageHtml.replace(i, i+formattedDfName.length(), sb.toString())
        }else{
            if(resultPageHtml.contains(refFieldPlaceholder)){
                int k = resultPageHtml.indexOf(refFieldPlaceholder)
                resultPageHtml.replace(k, k+refFieldPlaceholder.length(), dataframeHtmltoAppend)
            }
        }
        insertDataframeLabel(resultPageHtml, df)
    }

    private void insertDataframeLabel(StringBuilder resultPageHtml, DataframeVue df){


        String dfrLabel = df.messageSource.getMessage(df.dataframeLabelCode, null, df.dataframeLabelCode, LocaleContextHolder.getLocale())
        String defaultMarkupPlaceholder = LayoutPlaceholder.DATAFRAME_LABEL
        if(resultPageHtml.contains(defaultMarkupPlaceholder)) {
            dfrLabel = dfrLabel.trim() ?: ""
            int i = resultPageHtml.indexOf(defaultMarkupPlaceholder)
            if (dfrLabel) {
                dfrLabel = "<v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>" + dfrLabel + "</v-card-title>"
            }
            resultPageHtml.replace(i, i+ defaultMarkupPlaceholder.length(), dfrLabel)

        }
    }

    public void applyLayoutForButton(StringBuilder resulthtml, StringBuilder remainingbuttons, boolean wrapButtons){
        String allOtherButtons = "[ALL_OTHER_BUTTONS]"
        String formattedDfName = LayoutPlaceholder.BUTTON_SCRIPT
        String wrap = wrapButtons?"wrap":""
        if(resulthtml.contains(formattedDfName)){
            int index = resulthtml.indexOf(formattedDfName)
            remainingbuttons.insert(0, "<v-card-actions><v-row justify='center' $wrap pa-2>\n")
            remainingbuttons.append("</v-row></v-card-actions>\n")
            remainingbuttons.append(" <font color='red'><div id='$dataframeName-errorContainer'></div></font>\n");
            replaceInStringBuilder(resulthtml, formattedDfName, remainingbuttons.toString())
        } else {
            if(remainingbuttons.length() > 0){
                replaceInStringBuilder(resulthtml, allOtherButtons, remainingbuttons.toString())
            }
        }
    }

    public void applyLayoutForButton(StringBuilder dataframeHtml, StringBuilder remainingButtons, btnName, btnScript){
        String saveButton = "[saveButton]"
        String insertButton = "[insertButton]"
        String deleteButton = "[deleteButton]"

        String fldPlaceholder = "["+ btnName.trim() +"]"

        int stInd = dataframeHtml.indexOf(fldPlaceholder )

        String dfrHtml
        if(stInd>0){
            int endInd = stInd + fldPlaceholder.length()
            dataframeHtml.replace(stInd, endInd, btnScript)
        }
        boolean dataframeRightToLeftLanguage = Holders.grailsApplication.config.dataframe.right_to_left_language
        if(dataframeRightToLeftLanguage){
            remainingButtons.insert(0,btnScript)
        } else {
            remainingButtons.append(btnScript)
        }

    }

    private void replaceInStringBuilder(StringBuilder replaceIn, String replaceText, String replaceValue){
        int stInd = replaceIn.indexOf(replaceText)
        if(stInd>0){
            int endInd = stInd + replaceText.length()
            if(replaceValue){
                replaceIn.replace(stInd, endInd, replaceValue)
            }else {
                replaceIn.replace(stInd, endInd,"")
            }
        }
    }
    public static String convertListToString(cssGridValues){
        if(cssGridValues == null || cssGridValues.isEmpty()){
            return ""
        }
        StringBuilder sb = new StringBuilder();
        for (String s : cssGridValues) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString()
    }

    public static String convertListToString(Map cssGridValues){
        if(!cssGridValues){
            return ""
        }
        List gridKeys = ['xs', 'sm', 'md', 'lg', 'xl']
        StringBuilder sb = new StringBuilder();
        for(String key: gridKeys){
            if(cssGridValues.get(key)){
                sb.append(key);
                sb.append("='");
                sb.append(cssGridValues.get(key));
                sb.append("'");
                sb.append(" ");
            }
        }
        return sb.toString()
    }

    int applyLayoutForCustomPlaceholder(StringBuffer resultPage, WidgetVue widget, String widgetForm, String fieldName, int seq){
        String fieldNamePlaceHolder = "[$fieldName]"
        int indOf = resultPage.toString().indexOf(fieldNamePlaceHolder)
        if(indOf > 0){//then replace
            resultPage.replace(indOf, indOf+fieldNamePlaceHolder.length(),widgetForm)
        }
        applyDataframeLayout(resultPage)
        return indOf
    }

    @Override
    void applyLayoutForField(StringBuffer resultPage, Widget widget, String widgetForm, String fieldName, int seq) throws LayoutException {

    }

    private static String wrapWithTag(tag, tagAttr, String html){
        String headTag = tag.toString()
        String tailTag = "</" + headTag.substring(1)
        return formatHeadTag(headTag, tagAttr) + html + tailTag
    }

    private static String wrapWithTag(tag, tagAttr, StringBuilder html){
        String headTag = tag.toString()
        String tailTag = "</" + headTag.substring(1)
        html.insert(0,formatHeadTag(headTag, tagAttr))
        html.append(tailTag)
    }

    private static String formatHeadTag(headTag, tagAttr){
        String splitString = headTag.substring(0,headTag.length()-1)
        String finalHeadTag = splitString + SPACE_SEPARATOR + tagAttr + ">"
        return finalHeadTag
    }

    public static LayoutVue getLayoutVue(String layoutName){
        LayoutVue layoutVue = (LayoutVue)Holders.grailsApplication.mainContext.getBean(layoutName)
        return layoutVue
    }

    private static String addWrapperClasses(resultPage){
        List contentAttrList = [""]
        String contentAttr = convertListToString(contentAttrList)
        wrapWithTag("<v-content>", contentAttr, resultPage)
    }

    private static String addNavigationPanel(resultPage){
        String toolbarTitle = wrapWithTag("<v-toolbar-title>","class='display-3'","Globe Chalet")
        String toolBarWrapper = wrapWithTag("<v-toolbar>","prominent extended",toolbarTitle)
        String wrapWithCard = wrapWithTag("<v-card>","color='grey lighten-4' text height='200px' tile",toolBarWrapper)
        resultPage.insert(0,wrapWithCard)
    }

    private static String addFooterPanel(resultPage){

        String footerHtml = wrapWithTag("<v-footer>","app","")
        resultPage.append(footerHtml)
    }

    String setLayoutName(){

    }

    String getLayoutName(){return name}

    private LayoutVue(String name){
        this.name = name
    }

    //DEFAULT LAYOUT
    public LayoutVue(DataframeVue df){
        this.df = df
    }


    public static <T extends LayoutVue> LayoutVue getLayout(String name, T layoutClass){
        if(layouts.containsKey(name)){
            LayoutVue ly = layouts.get(name)
            if(ly){
                return ly
            }else{// it was not initialized!

            }
        }else{// Layout does not exist
            //TOD: log an exception and use standard

        }

    }

    void addFieldLayout(String fieldName, String fldLayout){
        perFieldLayout.put(fieldName,  fldLayout)
    }

    boolean isNonCustomFieldFirstTime(){
        numberOfNonCustomFields == 1
    }

    static void removeAllOtherFieldsPlaceholder(StringBuffer resultPageHtml){
        int indexofAllOtherFields = resultPageHtml.indexOf(LayoutVue.ALL_OTHER_FIELDS);
        while(indexofAllOtherFields > -1){
            resultPageHtml.delete(indexofAllOtherFields, indexofAllOtherFields +  LayoutVue.ALL_OTHER_FIELDS.length());
            indexofAllOtherFields = resultPageHtml.indexOf(LayoutVue.ALL_OTHER_FIELDS);
        }

    }

    static void placeField(StringBuffer resultPage, String toAppend){
        int indOfOtherField = resultPage.indexOf(LayoutVue.ALL_OTHER_FIELDS);
        if(indOfOtherField > -1){
            resultPage.insert(indOfOtherField, toAppend);
        }else{
            resultPage.append(toAppend);
        }
    }

    static void applyButtonPlaceholder(StringBuffer resultPage, String toAppend, String btnString){
        String toReplace = toAppend.substring(toAppend.indexOf("["), toAppend.indexOf("]")+1)
        if(toAppend.indexOf(toReplace) > -1){
            toAppend = toAppend.replace(toReplace,btnString)
        }
        resultPage.append(toAppend)
    }

    static void applyButtonPlaceholderInDiv(StringBuffer resultPage, String btnName, String btnString, String dataframeName){
        String buttonRegex = "([\\[]${dataframeName}.dataframeButtons.${btnName})(\\w+)?(\\])"
        String buttonPlaceHolder = resultPage.find(buttonRegex)
        if (buttonPlaceHolder){
            int indOf = resultPage.toString().indexOf(buttonPlaceHolder)
            if (indOf > 0){
                resultPage.replace(indOf, indOf+buttonPlaceHolder.length(),btnString)
            }
        }
    }

    public static void applyDataframeLayout(StringBuffer resultPageHtml, DataframeVue df){

        String currentLayoutName = df.currentFrameLayout.layoutBeanName
        String formattedDfName = df.dataframeName?"["+df.dataframeName+"]":""
        if(currentLayoutName.equalsIgnoreCase("defaultDataframeLayout")){
            String markupPlaceholder = LayoutPlaceholder.DATAFRAME_SCRIPT
            int stInd = resultPageHtml.indexOf(markupPlaceholder)
            if(stInd>0){
                int endInd = stInd + markupPlaceholder.length()
                resultPageHtml.replace(stInd, endInd, df.getHtml())
            }
        }else {
            int stInd = resultPageHtml.indexOf(formattedDfName)
            if(stInd>0){
                int endInd = stInd + formattedDfName.length()
                resultPageHtml.replace(stInd, endInd, df.getHtml())
            }
        }
    }

}//End of class
