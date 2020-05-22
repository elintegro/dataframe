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


import com.elintegro.erf.dataframe.vue.DataframeVue
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
						<v-flex <%print gridValueString%> ><% print widget %></v-flex>
	"""
//    <div id='<% print divId %>'>
    static final String DEFAULT_DATAFRAME_LAYOUT = """
            <v-flex xs12 sm6 md4 lg4 xl4>[DATAFRAME_FIELD]</v-flex>"""
    static final String ALL_OTHER_FIELDS = "[ALL_OTHER_FIELDS]";
    static final String ALL_OTHER_FIELDS_REGEXP = "\\[ALL_OTHER_FIELDS\\]";

    Map perFieldLayout = [:]

    //For Vue
    StringBuilder compRegScript = new StringBuilder()
    List children = [] // Add all the children's layout names
    List childDataframes = [] //Add all the components to register under this layouts component
    boolean isGlobal = false // Whether or not to register component globally
    static List defaultGridValues = Holders.grailsApplication.config.vue.flexGridValues.Default
    static List defaultButtonGridValues = ['xs12', 'sm12', 'md4', 'lg4', 'xl4']
    List flexGridValues = defaultGridValues
    boolean componentRegistered = false //Set once the component is registered

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



    //Load all Layouts from the com.elintegro.erf.layout package:
    static{

        //List<Class<?>> classLst = com.elintegro.utils.FactoryUtils.getClassesForPackage(Layout.class.package)

        //List classLst2 = new File("/com/elintegro/erf/layout")

        /*new File("/com/elintegro/erf/layout").eachFile(){file ->
            if(file.getName()){
                Class clazz = Class.forName(file.getName(), true, Thread.currentThread().contextClassLoader)
                Layout newLayoutObj = clazz.newInstance()
                newLayoutObj.setLayoutName()
                layouts.put(file.getName(), newLayoutObj)
            }
            */
        println("Inside Layout")
    }


    /**
     * The layouts are equal if thir names are equal and placeHolder is null (standard layouts)
     * or names are equal and placeholders are equal (custom layouts)
     * @param compLayout
     * @return
     */
    final boolean equals(LayoutVue compLayout){
        boolean ret = false

        if(this.name == name && !layoutPlaceHolder){
            ret = true
        }else if(this.name == name && layoutPlaceHolder == layoutPlaceHolder){
            ret = true
        }
        return ret
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
                wrapperLayoutForFieldsInGroupOpenTag = "<v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='${dataframeName}_form'><v-container grid-list-xl fluid><v-layout wrap>\n"    // TODO change all other instances of formId
            }else{
                wrapperLayoutForFieldsInGroupOpenTag = "<div ref='${dataframeName}_form'><v-container grid-list-xl fluid><v-layout wrap>\n"
            }
        }

        return wrapperLayoutForFieldsInGroupOpenTag
    }

    private String getWrapperLayoutForFieldsInGroupCloseTag(){
        if(!wrapperLayoutForFieldsInGroupCloseTag.trim()){
            if(wrapInForm) {
                wrapperLayoutForFieldsInGroupCloseTag = "</v-layout></v-container></v-form></v-flex>\n"
            }else{
                wrapperLayoutForFieldsInGroupCloseTag = "</v-layout></v-container></div>\n"
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

    private String setDataframeFields(String dfrHtml, String formattedDfName, String defaultMarkupPlaceholder, String dataframeHtmltoAppend, DataframeVue df){


        StringBuilder sb = new StringBuilder()
        sb.append(getWrapperLayoutForFieldsInGroupOpenTag())
        sb.append(dfrHtml)
        sb.append(getErrorContainerHtml())
        sb.append(dataframeHtmltoAppend)
        sb.append(getWrapperLayoutForFieldsInGroupCloseTag())
        dfrHtml = this.layoutPlaceHolder.replace(formattedDfName, sb.toString())
        dfrHtml = insertDataframeLabel(dfrHtml, df)
        return dfrHtml
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
            remainingbuttons.insert(0, "<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center $wrap pa-2>\n")
            remainingbuttons.append("</v-layout></v-container></v-card-actions>\n")
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
            if(!dataframeRightToLeftLanguage){
                remainingButtons.insert(0,btnScript)
            }
        else {
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
    void applyLayout(StringBuffer resultPage, WidgetVue widget, String widgetForm, String fieldName, int seq) throws LayoutException {

        if(seq == 0){//I'm calling for the first time (field)
            resultPage.append(wrapWithTag("<v-flex>", convertListToString(flexGridValues), widgetForm))
        }

//        def retInd = applyLayoutForCustomPlaceholder(resultPage, widget, widgetForm, fieldName, seq)

        if(retInd < 0){
            numberOfNonCustomFields++
            applyLayoutForField(resultPage, widget, widgetForm, fieldName, seq)
        }

        if(seq == this.numberFields-1){ //I'm calling for the last time
            numberOfNonCustomFields = 0
//            resultPage.append(lastSectionEnd)
        }
    }

    public static String convertListToString(flexGridValues){
        if(flexGridValues == null || flexGridValues.isEmpty()){
            return ""
        }
        StringBuilder sb = new StringBuilder();
        for (String s : flexGridValues) {
            sb.append(s);
            sb.append(" ");
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

    public static LayoutVue getLayoutVue(containerLayoutStr){
        LayoutVue layoutVue = (LayoutVue)Holders.grailsApplication.mainContext.getBean(containerLayoutStr)
        return layoutVue
    }

//    Older layout bbuilder methods
   /* public static String constructFinalHtml(dataframes){
        StringBuilder resultPageHtml = new StringBuilder();
        for(def df : dataframes) {
            DataframeVue dataframe = (DataframeVue)Holders.grailsApplication.mainContext.getBean(df)
            applyFinalLayout(resultPageHtml, dataframe)
//            resultPageHtml.append(dataframe.getComponentName().toString()+"\n")
        }
//        addWrapperClasses(resultPageHtml)
//        addNavigationPanel(resultPageHtml)
//        addFooterPanel(resultPageHtml)
//        wrapWithTag("<v-app>","",resultPageHtml)
//        wrapWithTag("<div>","id='app'",resultPageHtml)

        return resultPageHtml.toString()

    }

    private static String applyFinalLayout(resultPageHtml, df){
        StringBuilder constructedLayoutHtml = new StringBuilder()
        String formattedDfName = df.dataframeName?"["+df.dataframeName+"]":""
//        Set<String> layoutsSet = new HashSet()
        def dfrFrameLayout = df.currentFrameLayout
//        String fullLayout = addAllParentLayouts(df.currentFrameLayout, constructedLayoutHtml, resultPageHtml)
        String fullLayout = addAllParentLayouts(dfrFrameLayout, constructedLayoutHtml, resultPageHtml)
        if(resultPageHtml == null || resultPageHtml.length() == 0){
            resultPageHtml.append(fullLayout)
        }
        String currentLayoutName = dfrFrameLayout.layoutBeanName
        if(currentLayoutName.equalsIgnoreCase("defaultDataframeLayout")){
            String markupPlaceholder = "[DATAFRAME_MARKUP]"
            int stInd = resultPageHtml.indexOf(markupPlaceholder)
            if(stInd>0){
                int endInd = stInd + markupPlaceholder.length()
                resultPageHtml.replace(stInd, endInd, df.getHtml())
//                resultPageHtml.replace(stInd, endInd, df.getComponentName())
            }
        }else {
            int stInd = resultPageHtml.indexOf(formattedDfName)
            if(stInd>0){
                int endInd = stInd + formattedDfName.length()
                resultPageHtml.replace(stInd, endInd, df.getHtml())
//                resultPageHtml.replace(stInd, endInd, df.getComponentName())
            }
        }

    }

    private static String addAllParentLayouts(currentLayout, constructedLayoutHtml, resultPageHtml){
        LayoutVue parentLayout = currentLayout?.parentLayout
        if(!parentLayout){
            return constructedLayoutHtml.toString()
        }
        
        String dataframeLayoutName = currentLayout.layoutBeanName
        String formattedDataframeLayoutName = "["+dataframeLayoutName + "]"
//        layoutsSet.add(formattedDataframeLayoutName)
        String parentLayoutPlaceholder = parentLayout.layoutPlaceHolder?:""
        int stInd = parentLayoutPlaceholder.indexOf(formattedDataframeLayoutName)
        int endInd = stInd + formattedDataframeLayoutName.length()

        if(constructedLayoutHtml){
            int sI = resultPageHtml.indexOf(formattedDataframeLayoutName)
            if(sI>0){
                int eI = sI + formattedDataframeLayoutName.length()
                resultPageHtml.replace(sI,eI, constructedLayoutHtml.toString())
                println("Adding Layout: "+dataframeLayoutName)
                return resultPageHtml.toString()
            }else{
                if(stInd>0){
                    parentLayoutPlaceholder = parentLayoutPlaceholder.replace(formattedDataframeLayoutName, constructedLayoutHtml.toString())
                }
                constructedLayoutHtml = new StringBuilder()
                constructedLayoutHtml.append(parentLayoutPlaceholder)
                addAllParentLayouts(parentLayout, constructedLayoutHtml, resultPageHtml)
            }
        }else{
            String currentLayoutPlaceholder = currentLayout.layoutPlaceHolder
            *//*if(stInd>0){
//                int endInd = stInd + formattedDataframeLayoutName.length()
                parentLayoutPlaceholder = parentLayoutPlaceholder.replace(formattedDataframeLayoutName, currentLayoutPlaceholder)
            }*//*
            *//*constructedLayoutHtml.append(parentLayoutPlaceholder)*//*
            constructedLayoutHtml.append(currentLayoutPlaceholder)
            addAllParentLayouts(currentLayout, constructedLayoutHtml, resultPageHtml)
        }
    }*/

//    Newer component bbased layout bubilder methods


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
    public LayoutVue(){

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
//                resultPageHtml.replace(stInd, endInd, df.getComponentName())
            }
        }else {
            int stInd = resultPageHtml.indexOf(formattedDfName)
            if(stInd>0){
                int endInd = stInd + formattedDfName.length()
                resultPageHtml.replace(stInd, endInd, df.getHtml())
//                resultPageHtml.replace(stInd, endInd, df.getComponentName())
            }
        }
    }

}//End of class
