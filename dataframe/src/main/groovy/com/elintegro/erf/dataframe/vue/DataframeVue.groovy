/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development.
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works.
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.vue

import com.elintegro.annotation.OverridableByEditor
import com.elintegro.erf.dataframe.*
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.layout.abs.Layout
import com.elintegro.erf.layout.abs.LayoutVue
import com.elintegro.erf.widget.vue.InputWidgetVue
import com.elintegro.erf.widget.vue.WidgetVue
import com.elintegro.utils.DataframeFileUtil
import com.elintegro.utils.StringUtil
import grails.util.Holders
import groovy.util.logging.Slf4j

//import grails.validation.Validateable
import org.apache.commons.lang.WordUtils
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.orm.hibernate5.SessionFactoryUtils

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.Map.Entry
/**
 *This class along with its subsidaries is responsible for retrieve a and provide Meta data for the Dataframe.
 *
 *  Metadata is collected from Dataframe bean, and defined HQL/SQL (by retrieving all possible information from the database) and
 *  from other bean definitions, provided by developer
 *
 * @author Eugenelip
 *
 */
@Slf4j
public class DataframeVue extends Dataframe implements Serializable, DataFrameInitialization{
	private static defaultWidget = new InputWidgetVue()
	DataframeVue parent

	private String currentLanguage = ""
	List flexGridValues = []
	String currentRoute


//Parameters required by VueJs
	String vueRoutes = ""
	//TODO make it injected in Spring way!
	private DataframeView dataframeView = new DataframeViewJqxVue(this)
	boolean route = false // set to make dfr route(change in url) or dynamic
	boolean tab = false //Is tab view active in dataframe
	List<String> childDataframes = new ArrayList<>()
//	VueStore store = new VueStore()
	String mainNamedParamKey = "" //Specify the main named parameter key if other than id. EX: owner.id:ownerId => specify ownerId
	boolean isGlobal = false // Whether or not to register the component globally
	boolean componentRegistered = false //Set once the component is registered
	boolean createStore = true
	boolean putFillInitDataMethod = false
	String externalTemplateId = ""
	Map<String, Object> vueStore = new HashMap<String, Object>();//For creating vue store
	StringBuilder stateStringBuilder = new StringBuilder()// Remove after enchancing vueStore implementation
	Map onClick = new HashMap() //For onCLick on the surface of dataframe
	String doBeforeRefresh = ""
	List flexGridValuesForSaveButton = []
	String layoutForSaveButton = ""
	List flexGridValuesForInsertButton = []
	List flexGridValuesForResetButton = []
	List flexGridValuesForCancelButton = []
	List flexGridValuesForDeleteButton = []
	String layoutForInsertButton = ""
	String layoutForResetButton = ""
	String layoutForCancelButton = ""
	String layoutForDeleteButton=""
	String saveButtonAttr = ""
	String saveButtonAlignment
	String insertButtonAlignment
	String insertButtonAttr = ""
	String resetButtonAlignment
	String resetButtonAttr = ""
	String cancelButtonAttr = ""
	String deleteButtonAttr = ""
	String vueSaveVariablesScriptString = ""
	boolean wrapButtons = true

	// For vue store
	private VueJsBuilder vueJsBuilder
	private Map vueStoreScriptMap

	@OverridableByEditor
	boolean initOnVueComponentLoad = true

	public DataframeVue(){}
	public DataframeVue(def dataframeName){
		super(dataframeName)
		if(dataframeName == null)
			throw new DataframeException("Dataframe name not set.")
//		log.debug("dataframe created:" +dataframeName);
		this.dataframeName = dataframeName
		this.dataframeNameLowercase = dataframeName?dataframeName.toLowerCase():""
		this.dataframeView.dataframeName = dataframeName

		flexGridValues = flexGridValues?:LayoutVue.defaultGridValues

		String defaultRoute
		defaultRoute = (dataframeName.replaceAll("vue","").replaceAll("Dataframe","").split(/(?=[A-Z])/).join("-")).toLowerCase();
		currentRoute = currentRoute?:defaultRoute
	}

	void init(){
		super.init()
		vueJsBuilder = new VueJsBuilder(this)
	}

	/**
	 *This method uses meta field to add the widget and put the meta field in fields.Key on fields will be
	 *dataframename.fieldname
	 */
	protected void buildDefaultWidget( MetaField fld){

		def widget = null
		def dataType = fld.dataType.toString().toUpperCase()
		def fieldName =fld.name;

		if(fld.isFK()){
			widget = "FKWidgetVue"
		}else {
			switch (dataType) {
				case "INT":
				case "SMALLINT":
				case "BIGINT":
				case "DOUBLE":
				case "SHORT":
					widget = "NumberInputWidgetVue";
					break;
				case "TINYINT":
				case "BOOLEAN":
					widget = "CheckboxWidgetVue";
					break;
				case "DATE":
				case "DATETIME":
					widget = "DateWidgetVue";
					break;
				case "VARCHAR":
					if (fld.name.toLowerCase().indexOf("email") >= 0) {
						widget = "EmailWidgetVue";
					}
					else if(fld.name.toLowerCase().indexOf("phone") >= 0){
						widget = "PhoneNumberWidgetVue"
					}
					else if(fld.length > BIG_TEXT_FIELD_LENGTH){
						widget = "TextAreaWidgetVue";
					}
					else{
						widget = "InputWidgetVue";
					}
					break;
				default :
					widget = "InputWidgetVue";
					break;
			}
		}

		def metaFieldMap = fld.toMap()
		metaFieldMap.put("widget",widget)
		log.info "fld: $fld.name widget:$widget datatype: $fld.dataType"
		fields.put(buildFullFieldName(fld), metaFieldMap )
	}

	/*
	 * TODO: refactor it! we need use Widget concept to bring the data and also be ready to do it each time a field that dependent on this one changed
	 *This method uses the params and return the list for the combobox option/radio buttons.
	 *The result format is id of the result object will be on key on value will on the key of value.
	 */
	public List getSelectOptionList(def params){
		Map field = fields.dataMap.get(params.fieldName)
		Connection con = SessionFactoryUtils.getDataSource(sessionFactory).getConnection()
		PreparedStatement  pstmt =  con.prepareStatement(hqlToSql(field.dictionary))
		if(params.dependsOn == 'Yes'){
			pstmt.setString(1,params.id)
		}
		ResultSet rs = pstmt.executeQuery()
		List result=[]
		while(rs.next()){
			def object =[:]
			object.put("key", rs.getString(1))
			object.put("value", rs.getString(2))
			result.add(object)
		}
		return result
	}

	public Map getVueStoreScript(){
		return vueStoreScriptMap
	}

	public String getVueSaveVariables(){

		return vueSaveVariablesScriptString
	}

	public String getComponentName(String propsString = ""){
		String componentName = ""
		if(route){
			componentName = "<router-view name='${dataframeName}'></router-view>"
		}else{
			propsString = (propsString != "")?propsString:""
			componentName = "<${dataframeName} ref='${dataframeName}_ref' :${dataframeName}_prop='${dataframeName}_data' $propsString></${dataframeName}>"
		}
		return componentName
	}

	public String getVueRoutes(){
		return vueRoutes
	}


	/**
	 * Load Dataframe that was defined defined by this reference and also return it to the caller  and put it to the refrence Dataframe local collector.
	 * @param dfb
	 * @return
	 */
	private DataframeVue loadReferenceDataframe(DFButton dfb) {
		DataframeVue referenceDataframeBean = DataframeVue.getDataframeBeanFromReference(dfb.refDataframe)
		this.refDataframes.add(referenceDataframeBean)
		return referenceDataframeBean;
	}

	/**
	 * Prepare DataFrame internal context to be ready for work
	 * @param fieldLayout
	 * @param frameLayout
	 * @return
	 */
	protected String prepare(String fieldLayout, Layout frameLayout){
		init();
		currentFldLayout = fieldLayout
		if(frameLayout != null){
			currentFrameLayout = frameLayout
		}
		StringBuilder fieldsHtmlBuilder = new StringBuilder()
		this.currentFrameLayout.numberFields = fields.getList().size()
		this.currentFrameLayout.setDataframeName(dataframeName)
		List flexGridValuesFromDescriptor = flexGridValues?: LayoutVue.defaultGridValues
		String gridValueInString = LayoutVue.convertListToString(flexGridValuesFromDescriptor)

		StringBuilder vueDataVariable = new StringBuilder()
		StringBuilder vueStateVariable = new StringBuilder()
		StringBuilder vueDataFillScript = new StringBuilder()
		StringBuilder vueSaveVariables = new StringBuilder()
//		StringBuilder embbeddedCompScripts = new StringBuilder()
		StringBuilder resultPageHtml = new StringBuilder()
		resultPageHtml.append(this.currentFrameLayout?.layoutPlaceHolder?:"")

		List fldsList = fields.getList()
		int seq = 0
		int fieldCount = 0;
		List fieldList = fields.getList()
		for(String key: fieldList){
			// TODO  make sure the javascript sourcecode aligns
			Map field = fields.dataMap.get(key)

			if(field.containsKey("widget")  && field.get("widget") != null){

				(fieldLayout, fieldCount) = buildWidget(field, key, fieldLayout, resultPageHtml, fieldsHtmlBuilder, fieldCount, vueDataVariable, vueStateVariable, vueDataFillScript, vueSaveVariables, vueJsBuilder)

			}else{
				log.error("No widget for the field $field.name")
			}

		}//End of for loop for fields

		// adding key- fields for vue
		List<String> keyFieldNames = getKeyFieldNameForNamedParameter(this)
		StringBuilder vueFileSaveVariables = new StringBuilder()
		keyFieldNames.each {
			vueStateVariable.append("${convertToDataVariableFromat(it)}"+":\"\",\n")
			vueSaveVariables.append("params[\"$it\""+"] = this.${convertToDataVariableFromat(it)};\n")
			vueFileSaveVariables.append("params[\"$it\""+"] = response.namedParameters.id.value;\n")
			vueDataFillScript.append("this.${convertToDataVariableFromat(it)} = response['${convertToReturnedDataVariableFromat(it)}']?response['${convertToReturnedDataVariableFromat(it)}']:\"\"\n")
		}
		StringBuilder buttonHtmlStringBuilder = new StringBuilder()
		StringBuilder refDataframeHtmlStringBuilder = new StringBuilder() //Holds html for dialog or InDiVHtml
		if(vueSaveVariablesScriptString.isEmpty()){
			vueSaveVariablesScriptString = vueSaveVariables.toString()
		}
		checkComponentsToRegister()
//        StringBuilder remainingButtons = new St
		addButtonsToDataframe(resultPageHtml, vueJsBuilder, buttonHtmlStringBuilder, refDataframeHtmlStringBuilder, keyFieldNames, vueSaveVariables.toString(), vueFileSaveVariables.toString(), new StringBuilder()) //Adding bbuttons to dataframe

		if(!onClick.isEmpty()){
			refDataframeHtmlStringBuilder.append(dataframeView.getRefDataframeHtmlForOnClick(onClick, vueJsBuilder))
		}
		//		Add section for dialog box or insert div
		this.currentFrameLayout.applyLayoutForDataframe(resultPageHtml, fieldsHtmlBuilder,  refDataframeHtmlStringBuilder.toString(), this);
		this.currentFrameLayout.applyLayoutForButton(resultPageHtml, buttonHtmlStringBuilder, wrapButtons);

		vueJsBuilder.addToDataScript(vueDataVariable.toString())
		vueJsBuilder.getVueStore().addToState(vueStateVariable.toString())
		if(initOnPageLoad){
			putFillInitDataMethod = true
			vueJsBuilder.addToMountedScript("this.${dataframeName}_fillInitData();\n")
		}
		if(putFillInitDataMethod){
			vueJsBuilder.addToWatchScript(""" ${dataframeName}_prop: {
                             immediate:true,
                             deep:true,
                             handler: function(val, oldVal){
                                  if(val && val.refreshInitialData){
                                     this.${dataframeName}_fillInitData();
                                  } else {
                                      console.log("${dataframeName}_prop has refreshInitialData as false or undefined. Could not refresh.");
                                  }
                             }
                     },\n""")
			vueJsBuilder.addToMethodScript(getJsonDataFillScript(this))
			vueJsBuilder.addToMethodScript(populateJSONDataScript(vueDataFillScript))
		}
		constructVueComponent(vueJsBuilder, resultPageHtml.toString())
//		vueStoreScript = vueJsBuilder.getVueStore().buildVueStoreScript()
		currentFldLayout = fieldLayout
		scripts = vueJsBuilder.getFinalbuildScript(this)

		DataframeFileUtil.writeStringIntoFile("./logs/${dataframeName}/${dataframeName}-script.vue", scripts.toString())

		resultPage = getComponentName("")
		return resultPage
	}

	private List buildWidget(Map field, String key, String fieldLayout, StringBuilder resultPageHtml, StringBuilder fieldsHtmlBuilder, int fieldCount, StringBuilder vueDataVariable, StringBuilder vueStateVariable, StringBuilder vueDataFillScript, StringBuilder vueSaveVariables, VueJsBuilder vueJsBuilder) {
		WidgetVue widget = getWidget(field)

		def divId = getDivId(key)
		if (fieldLayout == null)
			fieldLayout = LayoutVue.DEFAULT_FIELD_LAYOUT

		def fldName = field.name;
		field.labelCode = field.labelCode ? field.labelCode : fldName
		def fldNameDefault = WordUtils.capitalizeFully(fldName);
		def fldId = getDataVariableForVue(field)
		def lhcLocale = LocaleContextHolder.getLocale()
		String btnDivId = "";
		String btnWidget = "";
		List flexGridValues = field.flexGridValues ?:flexGridValues?: LayoutVue.defaultGridValues
		//Was in EWEB-68-refactoring; TODO check which line is correct!
		//List flexGridValues = field.flexGridValues ?: LayoutVue.defaultGridValues

		String gridValueString = LayoutVue.convertListToString(flexGridValues)
		def label = field.fldNmAlias ?: messageSource.getMessage(field.labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
		if (field?.labelDisabled) {
			label = ""
		}

		field.put('label', label)
		def errorMsg = messageSource.getMessage("error.$field.labelCode", null, "Error", LocaleContextHolder.getLocale())
		def binding = [divId   : divId, field: fldName.replace(".", "-"), label: label, mandatory: field.notNull ? "*" : "", gridValueString: gridValueString,
					   widget  : widget.getHtml(this, field), // TODO: if you need to pass any extra params to getHtml (such as which is the current field etc.) do so
					   flexAttr:widget.getFlexAttr(this, field), errorMsg: errorMsg, btnDivId: btnDivId, btnWidget: btnWidget]
		def template = templateEngine.createTemplate(fieldLayout).make(binding)
		String fieldName = field?.externalDomainField ? "${fldName}" : "${field.domainAlias}.${fldName}"
		String childDataframe = ""
		if (field.get("dataframe") != null) {
			childDataframe = field.get("dataframe").beanName
		}
		//Applying Layout:
		String fldNameAlias = field?.alias ?: ""

		//Do not display ID field in most of the cases
		boolean isPKAndHide = field.containsKey("pk") && field.get("pk") && hidePK

		boolean isFKAndHide = field.containsKey("fk") && field.get("fk")

		String templateToShow = !isPKAndHide && !isFKAndHide? template.toString(): ""
		this.currentFrameLayout.applyLayoutForField(resultPageHtml, fieldsHtmlBuilder, templateToShow, fldName, fldNameAlias, childDataframe)

		fieldCount++;

		vueDataVariable.append(widget.getVueDataVariable(this, field))
		//We refactoring this concept! Instead of vueStateVAriable being formed as string, we will add whatever needed to DomainFieldMap and it will be converted to JSON file for
		//transmitting Data between Front-end and Back-end
		//TODO: So this methid should be depricated as soon as new concept works!
		//vueStateVariable.append(widget.getStateDataVariable(this, field))
		widget.getStateDataVariablesMap(this, field) //This method will enhance domainFieldMap with required info, specificaly "items" for dictionary
		String valueSetterString = widget.getValueSetter(this, field, divId, fldId, key)
		if (!StringUtil.isNullOrEmpty(valueSetterString)){
			vueDataFillScript.append(valueSetterString)
			vueDataFillScript.append("\n")
		}
		vueSaveVariables.append(widget.getVueSaveVariables(this, field))
		return [fieldLayout, fieldCount]
	}

	public VueJsBuilder getVueJsBuilder(){
		return vueJsBuilder
	}
	private void checkComponentsToRegister(){

		if(!childDataframes.isEmpty()){
			for(String compS : childDataframes){
				if(compS.isEmpty()) continue

				if(!embeddedDataframes.contains(compS)){
					embeddedDataframes.add(compS)
				}
				DataframeVue dataframe = DataframeVue.getDataframe(compS)
				if(!dataframe.isGlobal){
					vueJsBuilder.addToComponentScript(VueJsBuilder.createCompRegistrationString(compS))
				}
				ResultPageHtmlBuilder.registeredComponents.add(compS)
			}
		}
	}

	private addButtonsToDataframe(StringBuilder resultHtml, VueJsBuilder vueJsBuilder, StringBuilder buttonHtmlStringBuilder, StringBuilder refDataframeHtmlStringBuilder, List<String> keyFieldNames, String vueSaveVariables, String vueFileSaveVariables, StringBuilder remainingButtons){

		if(ajaxSaveUrl){
			if(saveButton){
				String saveButtonAttr = saveButtonAttr?:""
				String saveButtonAlignment = saveButtonAlignment?:"right"
				List gridvaluesSave = flexGridValuesForSaveButton?:LayoutVue.defaultButtonGridValues
				String saveGridValueString = LayoutVue.convertListToString(gridvaluesSave)
				String saveButtonLabel = messageSource.getMessage("${dataframeName}.button.save", null, messageSource.getMessage("button.save", null, "Save", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
				String btnScript = applyLayoutForButton("<v-btn type='button' class='text-capitalize $saveButtonAlignment' id='$dataframeName-save' @click='${dataframeName}_save' $saveButtonAttr :loading='${dataframeName}_save_loading' >${saveButtonLabel}</v-btn>\n", layoutForSaveButton, saveGridValueString)     // TODO  i18n
				vueJsBuilder.addToMethodScript(getSaveDataScript(this, vueSaveVariables, vueFileSaveVariables))
				this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, "saveButton", btnScript)
			}
		}
		if(ajaxInsertUrl){
			if(insertButton){
				String insertButtonAttr = insertButtonAttr?:""
				List gridvaluesInsert = flexGridValuesForInsertButton?:LayoutVue.defaultButtonGridValues
				String insertGridValueString = LayoutVue.convertListToString(gridvaluesInsert)

				String insertButtonAlignment = insertButtonAlignment?:"left"
				String insertButtonLabel = messageSource.getMessage("${dataframeName}.button.insert", null, messageSource.getMessage("button.insert", null, "Insert", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
				String btnScript = applyLayoutForButton("<v-btn type='button' class='text-capitalize $insertButtonAlignment' id='$dataframeName-insert' @click='${dataframeName}_insert' $insertButtonAttr>${insertButtonLabel}</v-btn>\n", layoutForInsertButton, insertGridValueString)
				vueJsBuilder.addToMethodScript(getResetScript(this, keyFieldNames))
				this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, "insertButton", btnScript)
			}
		}
		if(resetButton){
			String resetButtonAttr = resetButtonAttr?:""
			List gridvaluesReset = flexGridValuesForResetButton?:LayoutVue.defaultButtonGridValues
			String resetGridValueString = LayoutVue.convertListToString(gridvaluesReset)

			String resetButtonAlignment = resetButtonAlignment?:"left"
			String resetButtonLabel = messageSource.getMessage("${dataframeName}.button.reset", null, messageSource.getMessage("button.reset", null, "Reset", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
			String btnScript = applyLayoutForButton("<v-btn type='button' class='text-capitalize $resetButtonAlignment' id='$dataframeName-reset' @click='${dataframeName}_reset' $resetButtonAttr>${resetButtonLabel}</v-btn>\n", layoutForResetButton, resetGridValueString)
			vueJsBuilder.addToMethodScript(getResetScript(this, keyFieldNames))
			this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, "resetButton", btnScript)
		}
		if(cancelButton){
			String cancelButtonAttr = cancelButtonAttr?:" left"
			List gridvaluesCancel = flexGridValuesForCancelButton?:LayoutVue.defaultButtonGridValues
			String cancelGridValueString = LayoutVue.convertListToString(gridvaluesCancel)
			String cancelButtonLabel = messageSource.getMessage("${dataframeName}.button.cancel", null, messageSource.getMessage("button.cancel", null, "Cancel", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
			String btnScript = applyLayoutForButton("<v-btn type='button' class='text-capitalize' id='$dataframeName-cancel' @click='${dataframeName}_cancel' $cancelButtonAttr>${cancelButtonLabel}</v-btn>\n", layoutForCancelButton, cancelGridValueString)     // TODO  i18n
			vueJsBuilder.addToMethodScript(getCancelDataScript(this))
			this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, "cancelButton", btnScript)
		}
		if(ajaxDeleteUrl){
			if(deleteButton){
				String deleteButtonAttr = deleteButtonAttr?:" left"
				List gridValuesDelete = flexGridValuesForDeleteButton?:LayoutVue.defaultButtonGridValues
				String deleteGridValueString = LayoutVue.convertListToString(gridValuesDelete)
				String deleteButtonLabel = messageSource.getMessage("${dataframeName}.button.delete", null, messageSource.getMessage("button.delete", null, "Delete", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
				String btnScript = applyLayoutForButton("<v-btn type='button' class='text-capitalize' id='$dataframeName-delete' @click='${dataframeName}_delete' $deleteButtonAttr>${deleteButtonLabel}</v-btn>\n", layoutForDeleteButton, deleteGridValueString)     // TODO  i18n
				vueJsBuilder.addToMethodScript(getDeleteDataScript(this, vueSaveVariables))
				this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, "deleteButton", btnScript)
			}
		}

		for(Entry<String, DFButton> entry: dataframeButtons.entrySet()){
			DFButton btn = (DFButton)entry.value;
			btn.name = entry.key;
			String btnScript = getBtnWidget(btn)

			this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, btn.name, btnScript)

			DataframeVue refDf
			if(btn.refDataframe){
				refDf = loadReferenceDataframe(btn)
			}


			refDataframeHtmlStringBuilder.append(dataframeView.getRefDataframeHtml(btn, refDf, vueJsBuilder))
			vueJsBuilder.addToMethodScript(dataframeView.getAjaxButtonScript(btn, refDf))
		}

		this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, wrapButtons)
	}

	private void constructVueComponent(VueJsBuilder vueJsBuilder, String dfrHtml){
		VueJsEntity vueJsEntity = getvueJsEntity(dataframeName+"_script")// get Bbean name for vue js Entity

		externalTemplateId?vueJsBuilder.addToTemplateScript("#$externalTemplateId"):vueJsBuilder.addToTemplateScript(dfrHtml)
		//EU!!! Here we are adding to data!
		vueJsBuilder.addToDataScript("overlay_dataframe:false,\n ${dataframeName}_save_loading:false,\nnamedParamKey:'',\n params:{},\n")

		if(vueJsEntity.data){
			vueJsBuilder.addToDataScript(vueJsEntity.data)

		}
		vueJsBuilder.addToPropsScript(""" '${dataframeName}_prop' """)
		if(vueJsEntity.props){
			vueJsBuilder.addToPropsScript(vueJsEntity.props)

		}
		addVueComponents(vueJsBuilder)

		if(vueJsEntity.beforeCreated){
			vueJsBuilder.addToBeforeCreatedScript(vueJsEntity.beforeCreated)
		}

		vueJsBuilder.addToCreatedScript("${dataframeName}Var = this;\n")
				.addToComputedScript("""
                                      checkResetFormProp: function(){
                                                  if(this.\$props.resetForm){
                                                      this.\$refs.${dataframeName}_form.reset()
                                                  }
                                     },
""")

		if(vueJsEntity.created){
			vueJsBuilder.addToCreatedScript(vueJsEntity.created)
		}
		if(vueJsEntity.mounted){
			vueJsBuilder.addToMountedScript(vueJsEntity.mounted)
		}
		vueJsBuilder.addToComputedScript(getStateAccessor())
		if(vueJsEntity.computed){
			vueJsBuilder.addToComputedScript(vueJsEntity.computed)
		}
		if(vueJsEntity.watch){
			vueJsBuilder.addToWatchScript(vueJsEntity.watch)
		}
		vueJsBuilder.addToMethodScript(getStateSetter())
		vueJsBuilder.addToMethodScript("""
                                     closeDataframe: function(){
                                          excon.setVisibility("${dataframeName}", false);
                                    },
                                     """)
		if(vueJsEntity.methods){
			vueJsBuilder.addToMethodScript(vueJsEntity.methods)
		}

		if(route){
			vueRoutes = "{ path: '/$currentRoute/:routeId',name:'${dataframeName}' , component: ${dataframeName}Comp },\n"
			componentRegistered = true
			isGlobal = false
			ResultPageHtmlBuilder.registeredComponents.add(dataframeName)
		}

		createVueStore(vueJsBuilder) //create vueStore
		VueStore vueStore1 = vueJsBuilder.getVueStore()

		//NEW
		String state = vueStore1.buildStateJSON(this)

		String mutation = vueStore1.getMutation()
		String getters = vueStore1.getGetters()
		String actions = vueStore1.getActions()
		String globalState = vueStore1.getGlobalState()
		this.vueStoreScriptMap = ["state":state, getters:getters, "mutation":mutation, "actions":actions, "globalState": globalState]

	}
	private String getStateAccessor(){
		return """ 
				visibility(){ return this.\$store.getters.getVisibilities;},
                state(){ return this.\$store.getters.getState('$dataframeName'); },
               """
	}

	private String getStateSetter(){
		return """ 
               """
	}
	/**
	 * Use 'self' instead of 'this' because scope of 'this' is different in callbacks
	 * @param df
	 * @return
	 */
	public String getJsonDataFillScript(df){
		String dataframeName = df.dataframeName
		return """
             ${dataframeName}_fillInitData: function(){
                 const self = this;
                 let params = this.state;    
                 if(!params) return;
                 params["url"] =  '$df.ajaxUrl';
                 params["doBeforeRefresh"] = function(){params['callApi'] = true;\n ${doBeforeRefresh}};                               
                 params["doAfterRefresh"] = function(response){${doAfterRefresh}};                               
				 excon.refreshData(params);
             },\n
              """
	}
	//todo:Make sure we need this method
	private String populateJSONDataScript(vueDataFillScript){
		//creating method for each dataframes for populating data
		if (!StringUtil.isNullOrEmpty(vueDataFillScript.toString())){
			StringUtil.insert(vueDataFillScript, "${dataframeName}_populateJSONData: function(response){\n")
			vueDataFillScript.append("},\n") //closing populateJSONData() method
		}
		return vueDataFillScript.toString()
	}

	/**
	 * Use 'self' instead of 'this' inside doBeforeSave and doAfterSave because of scope of 'this' issue.
	 * @param df
	 * @param vueSaveVariables
	 * @param vueFileSaveVariables
	 * @return
	 */
	public String getSaveDataScript(df, vueSaveVariables, vueFileSaveVariables){
		String dataframeName = df.dataframeName
		StringBuilder embdSaveParms = new StringBuilder("")
		if(embeddedDataframes.size()>0){
			embeddedDataframes.each{
				if(it.trim() != ""){
					embdSaveParms.append("""if(this.\$refs.hasOwnProperty("${it}_ref") && this.\$refs.${it}_ref){for(var a in this.\$refs.${it}_ref.\$data){\n
                                              var dashA = a.split('_').join('-');
                                              params[dashA] = this.\$refs.${it}_ref.\$data[a];\n}}\n""")
				}
			}
		}
		doAfterSaveStringBuilder.append("""
                    var ajaxFileSave = ${dataframeName}Var.params.ajaxFileSave;
                    if(ajaxFileSave){
                       for(let i in ajaxFileSave) {
                         const value = ajaxFileSave[i];
                         $vueFileSaveVariables
  						 self[value.fieldName+'_ajaxFileSave'](response, params); 	
					   }
                    } 
				""")
		return """
              ${dataframeName}_save: function(){
                  const self = this;
                  let params = this.state;                                    
                 params["url"] =  '$df.ajaxSaveUrl';
                 params["doBeforeSave"] = function(params){params['callApi'] = true;\n${doBeforeSave} }
                 params["doAfterSave"] = function(response){ 
								 ${doAfterSave} 
                                 ${doAfterSaveStringBuilder}
							     if(response.domain_keys){
								    excon.saveToStore("${dataframeName}", "domain_keys", response.domain_keys);
   								 }		
                 }
				 excon.saveData(params);
              },\n"""
	}

	public String getResetScript(df, List<String> keyFieldNames){

		String dataframeName = df.dataframeName
		StringBuilder embdDfrs = new StringBuilder("")
		if(embeddedDataframes.size()>0) {
			embeddedDataframes.each {
				if (it.trim() != "") {
					embdDfrs.append("""embdDfrs.push('${it}')\n""")
				}
			}
		}
		StringBuilder keyFieldNamesInsertBuilder = new StringBuilder()
		keyFieldNames.each {
			keyFieldNamesInsertBuilder.append("""this.${convertToDataVariableFromat(it)} = null;\n""")
		}
		return """
               
              ${dataframeName}_reset: function(){
                 this.\$refs.${dataframeName}_form.reset()
                 const self = this;
                 var embdDfrs = []
                 ${embdDfrs.toString()}
                 if(embdDfrs){
                    for(var em in embdDfrs){
                         var emS = embdDfrs[em] + "_ref"
                         var emF = embdDfrs[em] + "_form"
                         if(eval("this.\$refs."+emS)){ 
                             for(var a in eval("this.\$refs."+emS+".\$data")){
                                 eval("this.\$refs."+emS+".\$refs."+emF).reset();
                             }
                         }
                     }
                 }
                ${keyFieldNamesInsertBuilder.toString()}  
                $doAfterReset 
             
               },\n"""
	}

	private String getCancelDataScript(){

		return """
                 ${dataframeName}_cancel: function(){
                    this.\$refs.${dataframeName}_form.reset();
                    Vue.set();
                  }
                  """
	}

	private String getDeleteDataScript(Dataframe df, String vueSaveVariables){
		return """
                ${dataframeName}_delete: function(){
                      
                      var params = {'dataframe':'$dataframeName'};
                          ${vueSaveVariables}
                       ${doBeforeDelete}
                       if(!confirm("${messageSource.getMessage("delete.confirm.message", null, "delete.confirm.message", LocaleContextHolder.getLocale())}"))return
                       const self = this;
                       axios({
                           method:'post',
                           url:'$ajaxDeleteUrl',
                           params: params
                       }).then(function (responseData) {
                
                       ${doAfterDelete}
                       }
                }
               """
	}

	private String getButtonScript(btn, refDf){
		String name = refDf.dataframeName
		return """${dataframeName}_${btn.name}: function(){\n 
                         this.\$router.push({
                         name: '$name',
                         path: '$dataframeName',
                         params: {
                           $name: "test"
                         }
                       })
                       },\n"""
	}

	private void addVueComponents(VueJsBuilder vueJsBuilder){
		Set<String> registeredComponents = ResultPageHtmlBuilder.registeredComponents
		if(childrenDataframes){
			childrenDataframes.each{
				if(it && it.trim()!="" && !registeredComponents.contains(it)){
					DataframeVue dataframe = DataframeVue.getDataframe(it)
					if(!dataframe.isGlobal)
					vueJsBuilder.addToComponentScript(VueJsBuilder.createCompRegistrationString(it))
				}
			}
		}

	}

	public void createVueStore(VueJsBuilder vueJsBuilder){
		VueStore vStore = vueJsBuilder.getVueStore()
		Map initValues = this.vueStore
		if(initValues && initValues.state) {
			for (String key : initValues.state.keySet()) {
				Object value = initValues.state.get(key)
				vStore.addToState(key, value)
			}
		}
	}

	public List getKeyFieldNameForNamedParameter(df){
		List<String> keyFieldNames = new ArrayList<>()
		def namedParameters = df.parsedHql.namedParameters
		namedParameters.each {key, namedParam ->
			String refDomainAlias =  namedParam[0];
			String refFieldName =  namedParam[1];
			String keyNamedParam = Dataframe.buildFullFieldNameKeyParam(df, refDomainAlias, refFieldName, key);
			keyFieldNames.add(keyNamedParam)
		}
		return keyFieldNames
	}

	private String convertToDataVariableFromat(it){
		return it.replace("-","_")
	}
	private static String convertToReturnedDataVariableFromat(it){
		return it.replace("-",".")
	}
	public String getBtnWidget(DFButton btn){
		String buttonLabel = messageSource.getMessage("${dataframeName}.button.${btn.name}", null, btn.name, LocaleContextHolder.getLocale())
		String btnString="";
		String style= btn.style?:""
		String attr = btn.attr?:""
		String layout = btn.layout?:""
		String classNames = btn.classNames?:""
		String height = ""
		String width = ""
		List flexGridValues = btn.flexGridValues?:LayoutVue.defaultButtonGridValues

		if(btn.image){
			height = btn.image?.height?:50
			width = btn.image?.width?:75

		}
		String imgUrl = btn.image?.url? messageSource.getMessage(btn.image.url, null, buttonLabel, LocaleContextHolder.getLocale()):""
		if ("link".equals(btn.type)) {
			btnString=" <v-btn href='${btn.url}' class='text-capitalize $classNames' text id='$dataframeName-${btn.name}' @click.prevent='${dataframeName}_${btn.name}' $attr>${buttonLabel}</v-btn>\n"
		}else if("image".equals(btn.type)){
			btnString=" <input type='image' src='$imgUrl' id='$dataframeName-${btn.name}' alt='${buttonLabel}' @click.prevent='${dataframeName}_${btn.name}' $attr height='$height' width='$width' />\n"
		}else{
			if(btn.image && btn.image.showIcon){
				btnString=""" <v-btn ${WidgetVue.toolTip(btn)} class='text-capitalize $classNames' type='button' id='$dataframeName-${btn.name}' @click.prevent='${dataframeName}_${btn.name}' $attr><img height="${btn.image.height ?: 20}" width="${btn.image.width ?: 25}" src="$imgUrl"/></v-btn>\n"""
			}else{
				btnString=" <v-btn ${WidgetVue.toolTip(btn)} class='text-capitalize $classNames' type='button' id='$dataframeName-${btn.name}' @click.stop='${dataframeName}_${btn.name}' $attr>${buttonLabel}</v-btn>\n"
			}
		}

		String gridValueString = LayoutVue.convertListToString(flexGridValues)
		String btnStringWithLayout = applyLayoutForButton(btnString.toString(), layout, gridValueString)
		String btnStringWithLayoutAndSpringSecurity = WidgetVue.wrapWithSpringSecurityVue( btn, btnStringWithLayout)
		return btnStringWithLayoutAndSpringSecurity
	}

	private String applyLayoutForButton(String btnString, String LayoutString, String gridValueString){
		btnString = "<v-flex $gridValueString>"+btnString+"</v-flex>"
		if(LayoutString.contains("[BUTTON_SCRIPT]")){
			btnString = LayoutString.replace("[BUTTON_SCRIPT]", btnString)
		}

		return btnString

	}
	public WidgetVue getWidget(String fieldName ){
		Map field = fields.get(fieldName)
		return getWidget(field)
	}

	public WidgetVue getWidget(Map field ){
		String widgetName = field.widget
		if(field.containsKey(Field.WIDGET_WIDGET_OBJECT) && field.get(Field.WIDGET_WIDGET_OBJECT) != null){
			return field.get(Field.WIDGET_WIDGET_OBJECT)
		}else{

			WidgetVue widgetObject = defaultWidget
			if(widgetName?.trim()){
				log.debug("Found widget for $field.name != $widgetName")
				widgetObject = (WidgetVue) Holders.grailsApplication.mainContext.getBean(widgetName)
			}

			field.put("widgetObject", widgetObject)

			return widgetObject
		}
	}

	public static DataframeVue getDataframe(dataframeNameS){
		DataframeVue refDataframe = (DataframeVue)Holders.grailsApplication.mainContext.getBean(dataframeNameS)
		return refDataframe
	}

	public static VueJsEntity getvueJsEntity(vueJsEntityS){
		VueJsEntity vueJsEntity
		try {
			vueJsEntity = (VueJsEntity) Holders.grailsApplication.mainContext.getBean(vueJsEntityS)
		}catch(NoSuchBeanDefinitionException e){
			vueJsEntity = new VueJsEntity()
		}
		return vueJsEntity
	}

	public String getDataVariableForVue(Map field){
		String res = buildKeyFieldParam(dataframeName, field);
		return res
	}

    //Deprecate this one as soon as the getFieldJSONModelNameVue is ready and in use
	public String getFieldModelNameVue(Map field){
		String fieldnameStr = field.name.replace(DOT, UNDERSCORE);
		def domainNameStr = field.domain?.key
		if(fieldnameStr.indexOf(UNDERSCORE) <= 0 && !"".equals(domainNameStr) && domainNameStr != null){
			fieldnameStr = "${domainNameStr}${UNDERSCORE}${fieldnameStr}";
		}
		return "$dataframeName${UNDERSCORE}$fieldnameStr";
	}

	//TODO: EU!!! find right field name JSON!
	public String getFieldJSONModelNameVue(Map field){
		String fldDomainAndDot = (field.domain?.key?.size() > 0) ? "${field.domain.key}${DOT}" : ""
		return "${PERSISTERS}${DOT}${fldDomainAndDot}${field.name}.value";
	}

	public String getFieldJSONModelTransitNameVue(Map field){
		return "${TRANSITS}${DOT}${field.name}.value";
	}
}
