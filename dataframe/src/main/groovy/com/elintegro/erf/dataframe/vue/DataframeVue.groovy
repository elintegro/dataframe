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
import com.elintegro.erf.layout.StandardLayout
import com.elintegro.erf.layout.abs.LayoutVue
import com.elintegro.erf.widget.vue.InputWidgetVue
import com.elintegro.erf.widget.vue.WidgetVue
import com.elintegro.utils.DataframeFileUtil
import grails.gsp.PageRenderer
import grails.util.Environment
import grails.util.Holders
import groovy.text.SimpleTemplateEngine
//import grails.validation.Validateable
import groovy.util.logging.Slf4j
import org.apache.commons.collections.map.LinkedMap
import org.apache.commons.lang.WordUtils
import org.grails.core.DefaultGrailsDomainClass
import org.hibernate.Query
import org.hibernate.Transaction
import org.hibernate.engine.spi.SessionFactoryImplementor
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory
import org.hibernate.hql.spi.QueryTranslator
import org.hibernate.hql.spi.QueryTranslatorFactory
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.ApplicationContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.orm.hibernate5.SessionFactoryUtils

import java.sql.*
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
	public static final String DASH = "-";
	public static final String UNDERSCORE = "_";
	public static final String DOT = ".";
	public static final String SESSION_PARAM_NAME_PREFIX = "session_"
	private String currentLanguage = ""
	List flexGridValues = []
	String currentRoute


	DataframeVue parent
	String dataframeName
	String dataframeNameLowercase = ""
	@OverridableByEditor
	String hql
	String sql //TODO: work around to support queries with associations, should be generated correctly out of hql, but for now if we have association in HQL we need to provide sql (outer join problem, ticket #22)
	String hqlForUpdate
	@OverridableByEditor
	boolean initOnPageLoad = true
	@OverridableByEditor
	boolean saveButton = true
	@OverridableByEditor
	boolean deleteButton = false
	@OverridableByEditor
	boolean submitButton = false
	@OverridableByEditor
	boolean insertButton = false
	boolean resetButton = false
	@OverridableByEditor
	boolean cancelButton = false
	@OverridableByEditor
	boolean wrapInForm = true
	boolean wrapInDiv = false
	boolean validateForm = false
	@OverridableByEditor
	boolean addButton = false
	@OverridableByEditor
	boolean createEnableDisableFunctionForAllFields = false
	boolean readonly = false
	@OverridableByEditor
	String doBeforeSave = ""
	@OverridableByEditor
	String doAfterSave = ""
	String doAfterReset = ""
	String doAfterRefresh = ""
	@OverridableByEditor
	String doBeforeDelete = "false" //by default delete is not allowed, unless somebody defined a script to turn it into true
	@OverridableByEditor
	String doAfterDelete = ""

	@OverridableByEditor //This wil make sure the PK field(s) are not presented (and it is a default) unless otherwise is specified by Dataframe descriptor
	Boolean hidePK = true

	@OverridableByEditor //This wil make sure the PK field(s) are not presented (and it is a default) unless otherwise is specified by Dataframe descriptor



	ParsedHql parsedHql
	public 	List pkFields = []
	def groovySql
	Map writableDomains = [:]
	Map defaultRecord = [:]
	@OverridableByEditor
	String initScripts="" // This parameter holds possible initialization scripts
	@OverridableByEditor
	String headerScripts = "" //This parameter holds possible javascript functions to be used during DataframeVue lifecicle
	@OverridableByEditor
	String displayType = "inline" //This parameter defines wether to display the dataframeVue as window or as popup
	@OverridableByEditor
	String insertHtmlTo = ""
	@OverridableByEditor
	String divIdToDisable = ""
	@OverridableByEditor
	String popUpTitle=""
	@OverridableByEditor
	Map summaryAfterSave = [showSummary: false]
	static int BIG_TEXT_FIELD_LENGTH = 255;

	String dataframeLabelCode = ""
	private StringBuilder saveScriptJs = new StringBuilder();
	private StringBuilder doAfterSaveStringBuilder = new StringBuilder();
	private StringBuilder doAfterDeleteStringBuilder = new StringBuilder();
//	private StringBuilder additionalParametersScript = new StringBuilder()
	String vueRoutes = ""
	//TODO make it injected in Spring way!
	private DataframeView dataframeView = new DataframeViewJqxVue(this)


	String divID
	String supportJScriptSource

	String contextPath = Holders.grailsApplication.config.rootPath
	// This is a default to use DataframeController to perform CRUD operations, could be overwritten in Dataframe bean definition to any other controller operation
	def ajaxUrl = "${contextPath}/dataframe/ajaxValues";
	def ajaxSaveUrl = "${contextPath}/dataframe/ajaxSave";
	//def ajaxDeleteUrl = "/ayalon/dataframe/ajaxDelete"
	def ajaxDeleteUrl = "${contextPath}/dataframe/ajaxDeleteExpire";
	def ajaxInsertUrl = "${contextPath}/dataframe/ajaxInsert";
	def ajaxDefaultUrl = "${contextPath}/dataframe/ajaxDefaultData";
	def ajaxCreateUrl ="${contextPath}/dataframe/ajaxCreateNew"
	@OverridableByEditor
	Map dataframeButtons = [:];
	@OverridableByEditor
	Map insertFields=[:];
	Map dialogBoxActionParams=[:];
	List<DataframeVue> refDataframes = new ArrayList<DataframeVue>();
	Map<String,String> ajaxUrlParams = new HashMap<>()
	def ajaxDynamicSelectionBuildUrl

	//TODO: This should be removed to the Widget class!
	def ajaxjQTreeLoadUrl = "${contextPath}/dataframe/ajaxjQTreeLoad"

	//this String URL is to refresh when updating dataframe
	//def dataFrameParamsToRefresh = null

	def connectingFieldName //The field name that other Dataframes are refferred by to this one, it must be equal Table alias

	private Map<String, DataframeVue> embeddetDataframe = new LinkedMap<String, DataframeVue>();
	List<String> embeddedDataframes = new ArrayList<>()
	List<String> childrenDataframes = new ArrayList<>()
	//fieldName should be domain.fldName
	public void addEmbeddedDataframe(String fieldName, DataframeVue embDf){
		embeddetDataframe.put(fieldName, embDf)
	}

	public Map<String, DataframeVue> getEmbeddedDataframe(){
		return embeddetDataframe;
	}


	def void addButton(String name, String type, String url, String script){
		DFButton button = new DFButton()
		button.name = name
		button.type = type
		button.script = script
		button.url = url
		dataframeButtons.put(name, button)
	}

	def void addButton(){
		addButton("", "", "", "")
	}

	public def getAjaxjQTreeLoadUrl(){
		return servletContext.contextPath+ajaxjQTreeLoadUrl
	}
	Map<String, MetaField> fieldsMetadata

	Map<String, String> constraintsMetadata
	Map<String, WidgetVue> widgets = [:]
	//TODO: logger!!! Make it work Logger log = Logger.getInstance(DataframeVue.class)


	//Map fields = [:] // Map of fields and their widgets, displayed on the page. Structured like: [field:<name>, properties:[widget:Textfield.class, width:50, div:"d1"]]

	/* This is a map of user defined fields or parameters of the existing fields*/
	@OverridableByEditor
	Map addFieldDef =[:]  //add a field to the end of the fields
	Map addFieldBeforeDef=[:]   //add a field before specific field
	OrderedMap fields = new OrderedMap()
	def resultData

//Parameters required by VueJs
	String scripts = ""
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
	Map<String, String> vueStore = new HashMap<String, String>();//For creating vue store
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
	/*private void createVueJsEntityObj(){
		vuejsEntity = new VueJsEntity()
		vuejsEntity.setAllEntities(vujsLifecycleEntities)
	}*/
	String resultPage  = null
	String currentFldLayout = null
	@OverridableByEditor
	LayoutVue currentFrameLayout

	PageRenderer groovyPageRenderer
	def templateEngine = new SimpleTemplateEngine()

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
		if(fieldsMetadata == null || fieldsMetadata.size() == 0 || parsedHql == null){
			populateMetaData()
		}

		if(fields.size()==0){
			buildCustomWidgets()
		}
		vueJsBuilder = new VueJsBuilder(this)
	}


	public String getFormId(){
		return "${dataframeName}-form"

	}
	/**
	 * TODO: this method is a MESS!!!
	 *
	 * Should be carefully refactored!!!
	 *
	 * STORY #1 implementation:
	 * Retrieves from the domain classes and from the database all information to populate fieldsMetadata and constraintsMetadata
	 *
	 * Also builds default fields Map and columnOrder Set, based on fieldsMetadata. Webapp Developer will have power to override or enhance this info in the bean definition
	 * (resourses.groovy) or/and in the controller
	 *
	 * @param domainsClasses domain classes of the application
	 */
	void populateMetaData(/* GrailsClass[] domainsClasses */){

		fieldsMetadata = new HashMap<String, MetaField>()
		parsedHql = new ParsedHql(hql, grailsApplication, sessionFactory)

		List<MetaField> metFields = metaFieldService.getMetaDataFromFields(parsedHql, dataframeName)

		for( MetaField metaField in metFields){
			fieldsMetadata.put(metaField.name , metaField)
			metaField.addFieldDef = addFieldDef
			buildDefaultWidget(metaField)
			addWritebleDomains(metaField)
			this.defaultRecord.put(metaField.name, metaField.defaultValue)
		}

		//find all keys and set them to respective writable domain for future use (in saving dataInstances)
		addKeysToWritableDomain()

		buildCustomWidgets()
	}

	private void addKeysToWritableDomain() {

		this.writableDomains.each{ domainName, domainMap ->
			Set keys = domainMap.get("keys")
			parsedHql.namedParameters.each{parmName, parValStringArr ->
				if(domainName.equalsIgnoreCase(parValStringArr[0])){
					keys.add(parmName)
				}
			}
			domainMap.put("keys", keys)
			domainMap.put("alias", "")

		}
	}

	private void addWritebleDomains(MetaField field){
		if(!field.isReadOnly()){
			String domainAlias = field.domain.getDomainAlias()
			this.writableDomains.put(domainAlias, ["parsedDomain": field.domain, "queryDomain":null, "keys":[], "domainAlias": domainAlias])
		}
	}

	/**
	 *
	 * @param columnName
	 * @param stmt
	 * @return
	 * The method uses database table column name and Statement object and return whether it is unsigned or not
	 */
	boolean isUnsigned(String columnName, Statement stmt){
		ResultSet rs = stmt.executeQuery(parsedHql.getSqlString())
		ResultSetMetaData rsmd = rs.getMetaData()
		for(int i=1; i<=  rsmd.getColumnCount(); i++){
			if(rsmd.getColumnName(i).equals(columnName))
				return  !rsmd.isSigned(i)
		}
		return false
	}

	/**
	 * The method uses the domain class and builds a list of Meta Data using all persistent properties
	 */
/*
	List<MetaField> getDomainMetaData(Class clazz){
		List<MetaField> metaFields = new ArrayList<MetaField>()
		def domain = new DefaultGrailsDomainClass(clazz)
		for(property in domain.persistentProperties){
			if(!property.isAssociation()){
				MetaField metaField = new MetaField(property.getFieldName())
				metaField.domain = clazz.getClass().getName()
				metaField.dataType = MetaField.getDataType( property.getTypePropertyName() )
				metaFields.add(metaField)
			}
		}
		return metaFields
	}
*/


	/**
	 *This method uses meta field to add the widget and put the meta field in fields.Key on fields will be
	 *dataframename.fieldname
	 */
	private void buildDefaultWidget( MetaField fld){

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
					if(fld.length > BIG_TEXT_FIELD_LENGTH){
						widget = "TextAreaWidgetVue";
					}
					else if (fld.name.toLowerCase().indexOf("email") >= 0) {
						widget = "EmailWidgetVue";
					}
					else if(fld.name.toLowerCase().indexOf("phone") >= 0){
						widget = "PhoneNumberWidgetVue"
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
		//metaFieldMap.put("fk",fld.fk)
		//metaFieldMap.put("fkMetaData",fld.fkMetaData)

		log.info "fld: $fld.name widget:$widget datatype: $fld.dataType"
		//TODO: see if we can get rid of this map and use only MetaField object instead...
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

	/**
	 *This method add the custom fields to the fileds maps.
	 */
	private void buildCustomWidgets(){

		addFieldDef?.each{key, value->
			addField(key, value)
		}
	}

	List  getHqlResult(def queryHql, def keyValue, Map sessionAttributes){
		List results = null
		def sessionHibernate = sessionFactory.openSession()
		Transaction tx = sessionHibernate.beginTransaction()
		Query query =  sessionHibernate.createQuery(queryHql);
		for (String parameter : query.getNamedParameters()) {
			if(parameter.indexOf("session_") > -1){
				def sessionParamValue = DataframeInstance.getSessionParamValue(parameter, sessionAttributes)
				query.setParameter(parameter, sessionParamValue)
			}else{
				query.setParameter(parameter, keyValue)
			}
		}
		results = query.list()
		tx.commit();
		sessionHibernate.close();
		return results
	}

	/**
	 * This method uses the field name as per the hql and the corresponding value and return the type
	 * cast value.
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public Object getTypeCastValueFromHql(String fieldName, def value){
		def result = value
		parsedHql.hqlDomains.each(){domainAlias, domain ->
			def prop = domain?.value?.getPersistentProperty(fieldName)
			def typeName = prop?.getTypePropertyName()
			if(prop){
				result= DbResult.getTypeCastValue( value, prop)
				return result
			}
		}
		return result
	}


	/**
	 * This method uses the result data and builds  json format from that.
	 * @return
	 */
	public Map getJsonDefaultResult(){

		Map jsonRet = [:]

		fields.dataMap.each{ fieldKey, fieldObject ->
			MetaField field = this.fieldsMetadata.get(fieldObject.name)
			if(!field){
				log.error("Carramba!!!!")

			}
			jsonRet.put(fieldKey , field.defaultValue)
		}
		return jsonRet
	}

	public int getFieldIndexByName(String name){
		return parsedHql.indexOfFileds.get(name)
	}


	def insertGetIds(Map<String, String> params) {

		List result = insert(params)
		List ret = []
		result.each {
			ret.add(it.id)

		}
		ret
	}

	public def getTypeCastValue2(refDomainAlias, refFieldName, paramStringValue){
		def resValue = null;
		//1. Get domain class
		def domain = this.parsedHql?.hqlDomains?.get(refDomainAlias)
		if(domain == null){
			log.error("No doamin $refDomainAlias found");
		}
		//2. Get relevant field:
//		def prop = domain?.value?.getPersistentProperty(refFieldName)
		def prop = domain?.value?.getPropertyByName(refFieldName)
		if(prop){
			resValue  = DbResult.getTypeCastValue( paramStringValue, prop)
		}
		return resValue;
	}

	public void addField(String fieldName, Map<String, Object> fieldProp){

		if(fieldProp && fieldName){
			Map dbMetaFieldPropes = fields?.get(buildFullFieldName(dataframeName, fieldName))
			if(dbMetaFieldPropes == null){//There is no Database field like this, this field is independent one, we adding name property in it if does not defined like in the key
				if(!fieldProp.containsKey("name")){
					fieldProp.put("name", fieldName);
				}
				fieldProp.put("externalDomainField",true)
				if(fieldProp.insertAfter){
					String formattedInsertAfterKey = buildFullFieldName(dataframeName, fieldProp.insertAfter)
					if(fields.getList().contains(formattedInsertAfterKey)){
						fields.insertAfter(buildFullFieldName(dataframeName, fieldName), fieldProp, formattedInsertAfterKey)
					}
				}else if(fieldProp.insertBefore){
					String formattedInsertBeforeKey = buildFullFieldName(dataframeName, fieldProp.insertBefore)
					if(fields.getList().contains(formattedInsertBeforeKey)){
						fields.insert(buildFullFieldName(dataframeName, fieldName), fieldProp, formattedInsertBeforeKey)
					}
				}else{
					fields.put(buildFullFieldName(dataframeName, fieldName), fieldProp)
				}
			}else{
				fieldProp.put("name", dbMetaFieldPropes.get("name"));

				/*if(dbMetaFieldPropes.containsKey("name") && fieldProp.containsKey("name")){
				 if(!dbMetaFieldPropes.get("name").equals(fieldProp.get("name"))){
				 fieldProp.put("name", dbMetaFieldPropes.get("name"));
				 }
				 }
				 */

				dbMetaFieldPropes?.putAll(fieldProp)

			}
		}
	}


	/**
	 * This method builds the javascript and return it.
	 */
	public String getJavascript(){

		if(!scripts){
			prepare(/*currentFldLayout, currentFrameLayout*/) // TODO by Shai: resolve overloading ambiguity issue when both currentFldLayout, currentFrameLayout are null.
//			def generateDataframeOutputEnv =  grailsApplication.config.generateDataframeOutputEnv
//			generateDataframeOutputEnv.each( element ->

			if(Environment.current == Environment.DEVELOPMENT){

				DataframeFileUtil.writeStringIntoFile("AppDataframe.vue", scripts.toString())
			}
		}
		return scripts
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
	 * This method uses the layout(template) to get the build Html for the dataframe using all meta fields in hql.
	 */
	public String getHtml(String fieldLayout, String frameLayout){

		if(!resultPage ||  !currentFldLayout || currentFldLayout != fieldLayout || !currentFrameLayout || currentFrameLayout != frameLayout){
			prepare(fieldLayout, frameLayout)
		}
		return getResultPage_()
	}

	public String getHtml(String fieldLayout){

		if(!resultPage ||  !currentFldLayout || currentFldLayout != fieldLayout){
			prepare(fieldLayout, currentFrameLayout)
		}
		return getResultPage_()
	}

	public String preparejQTreeHtml(){

	}

	public String getHtml(){
		def lhcLocale = LocaleContextHolder.getLocale()
		boolean refresh = false
//		componentRegistered = false
		if(lhcLocale.baseLocale.language != currentLanguage){
			refresh = true
			currentLanguage = lhcLocale.baseLocale.language
		}
		return getHtml(refresh)
	}

	public String getHtml(boolean refresh){

		if(!resultPage ||  !currentFldLayout  || refresh){
			prepare(currentFldLayout, currentFrameLayout)

		}
		return getResultPage_()
	}

	String getResultPage_(){
		//TODO: Put SPRING_SECURITY based filter in here
		return resultPage
	}

	public String getJSListOfKeyFields() {
		StringBuilder sb2 = new StringBuilder()
		sb2.append(" var fullKeyFields = new Object(); \n");
		if(parsedHql != null && parsedHql.namedParameters != null ){
			for( Map.Entry entry: parsedHql.namedParameters){
				String refFieldName_ = buildFullFieldNameKeyParam(this, entry.key);
				sb2.append("            fullKeyFields['${entry.key}'] = '${refFieldName_}'; \n")
			}
		}
		String sb2_ = sb2.toString()
		return sb2_
	}

	public String buildDivParams(){
		return """jQuery('#$dataframeName-div').find(':input, input:text, input:password, input:file, select, textarea').not("input[type=button]").each(function(){ 
                       var attributeName = jQuery(this).attr("name");
                       allParams[attributeName]=jQuery(this).val(); 
            });"""
	}

	/**
	 * Overloading prepare(String fieldLayout, String frameLayout)
	 * @param fieldLayout
	 * @param frameLayout
	 * @return
	 */
	private String prepare(){
		LayoutVue defLayout = new StandardLayout()
		return prepare(null, defLayout)
	}

	/**
	 * Overloading (String fieldLayout, String frameLayout)
	 * @param fieldLayout
	 * @param frameLayout
	 * @return
	 */
	private String prepare(String frameLayout){
		LayoutVue defLayout = new StandardLayout()
		return prepare(frameLayout, null)
	}

	/**
	 * Prepare DataFrame to display
	 * @param fieldLayout
	 * @param frameLayout
	 * @return
	 */
	private String prepare(String fieldLayout, String frameLayoutName){

		if(frameLayoutName.indexOf(".")<0){
			frameLayoutName = "com.elintegro.erf.layout."+frameLayoutName
		}
		LayoutVue frameLayout = Class.forName(frameLayoutName)
		return prepare(fieldLayout, frameLayout)
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
	 * Prepare DataFrame to display
	 * @param fieldLayout
	 * @param frameLayout
	 * @return
	 */
	private String prepare(String fieldLayout, LayoutVue frameLayout){

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

		//creating method for each dataframes for populating data
		vueDataFillScript.append("${dataframeName}_populateJSONData: function(response){\n")
		List fldsList = fields.getList()
		int seq = 0
		int fieldCount = 0;

		for(String key: fields.getList()){
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
			vueSaveVariables.append("allParams[\"$it\""+"] = this.${convertToDataVariableFromat(it)};\n")
			vueFileSaveVariables.append("allParams[\"$it\""+"] = response.nodeId[0];\n")
			vueDataFillScript.append("this.${convertToDataVariableFromat(it)} = response['${convertToReturnedDataVariableFromat(it)}']?response['${convertToReturnedDataVariableFromat(it)}']:\"\"\n")
		}

		vueDataFillScript.append("},\n") //closing populateJSONData() method

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
			vueJsBuilder.addToCreatedScript("this.${dataframeName}_fillInitData();\n")
		}
		if(putFillInitDataMethod){
			vueJsBuilder.addToWatchScript(""" ${dataframeName}_prop: {
                             deep:true,
                             handler: function(val, oldVal){
                                  if(val.refreshInitialData){
                                     this.${dataframeName}_fillInitData();
                                  } else {
                                      console.log("${dataframeName}_prop has refreshInitialData as false or undefined. Could not refresh.");
                                  }
                             }
                     },\n""")
			vueJsBuilder.addToMethodScript(getJsonDataFillScript(this))
			vueJsBuilder.addToMethodScript(vueDataFillScript.toString())
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
		vueStateVariable.append(widget.getStateDataVariable(this, field))
		vueDataFillScript.append(widget.getValueSetter(this, field, divId, fldId, key))
		vueDataFillScript.append("\n")
		vueSaveVariables.append(widget.getVueSaveVariables(this, field))
/*
		if (field?.validate) {
			String valKey = getDataVariableForVue(field) + "_rule"
			vueDataVariable.append("$valKey : ${field.validate.rule},\n")
		}
*/
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
				vueJsBuilder.addToComponentScript(VueJsBuilder.createCompRegistrationString(compS))
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
//				buttonHtmlStringBuilder.append(applyLayoutForButton("<v-btn type='button' class='text-capitalize' id='$dataframeName-save' @click='${dataframeName}_save' $saveButtonAttr>${saveButtonLabel}</v-btn>\n", layoutForSaveButton, saveGridValueString))     // TODO  i18n
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
				//buttonHtmlStringBuilder.append(applylayoutforbutton("<v-btn type='button' class='text-capitalize' id='$dataframename-insert' @click='${dataframename}_insert' $insertbuttonattr>${insertbuttonlabel}</v-btn>\n", layoutforinsertbutton, insertgridvaluestring))
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
			//buttonHtmlStringBuilder.append(applylayoutforbutton("<v-btn type='button' class='text-capitalize' id='$dataframename-insert' @click='${dataframename}_insert' $insertbuttonattr>${insertbuttonlabel}</v-btn>\n", layoutforinsertbutton, insertgridvaluestring))
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

/*		if (ajaxCreateUrl){
			def ajaxScripts = getAjaxDefaultValueScripts()
			resultPageJs.append(ajaxScripts)
		}*/
		for(Entry<String, DFButton> entry: dataframeButtons.entrySet()){
			DFButton btn = (DFButton)entry.value;
			btn.name = entry.key;
			String btnScript = getBtnWidget(btn)

			this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, btn.name, btnScript)
			//buttonHtmlStringBuilder.append(btnString)

			DataframeVue refDf
			if(btn.refDataframe){
				refDf = loadReferenceDataframe(btn)
			}


			refDataframeHtmlStringBuilder.append(dataframeView.getRefDataframeHtml(btn, refDf, vueJsBuilder))
			vueJsBuilder.addToMethodScript(dataframeView.getAjaxButtonScript(btn, refDf))
		}

		this.currentFrameLayout.applyLayoutForButton(resultHtml, remainingButtons, wrapButtons)
		/*buttonHtmlStringBuilder.append("</v-layout></v-container></v-card-actions>\n")
		buttonHtmlStringBuilder.append(" <font color='red'><div id='$dataframeName-errorContainer'></div></font>\n");*/

	}

	private void constructVueComponent(VueJsBuilder vueJsBuilder, String dfrHtml){
		VueJsEntity vueJsEntity = getvueJsEntity(dataframeName+"_script")// get Bbean name for vue js Entity

		externalTemplateId?vueJsBuilder.addToTemplateScript("#$externalTemplateId"):vueJsBuilder.addToTemplateScript(dfrHtml)
		vueJsBuilder.addToDataScript("overlay_dataframe:false,\n ${dataframeName}_save_loading:false,\nnamedParamKey:'',\n params:{},\n")
		if(tab){
			vueJsBuilder.addToDataScript("${dataframeName}_tab_model : '',\n")
					.addToMethodScript(""" tabClicked:function(){${putFillInitDataMethod?"this.${dataframeName}_fillInitData();":""}},\n""")
		}
		if(vueJsEntity.data){
			vueJsBuilder.addToDataScript(vueJsEntity.data)

		}
		vueJsBuilder.addToPropsScript(""" '${dataframeName}_prop' """)
		if(vueJsEntity.props){
			vueJsBuilder.addToPropsScript(vueJsEntity.props)

		}
		addVueComponents(vueJsBuilder)

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
		String state = vueStore1.buildState(dataframeName)
		String mutation = vueStore1.getMutation()
		String getters = vueStore1.getGetters()
		String globalState = vueStore1.getGlobalState()
		this.vueStoreScriptMap = ["state":state, getters:getters, "mutation":mutation, "globalState": globalState]

	}
	private String getStateAccessor(){
		return """
                state(){
                   return this.\$store.getters.getState('$dataframeName');
                },
               """
	}

	private String getStateSetter(){
		return """ updateState: function(response){
                    this.\$store.commit("updateState", response)
                },
               """
	}
	public String getJsonDataFillScript(df){
		String dataframeName = df.dataframeName
		StringBuilder allParamsSb = new StringBuilder()
		String namedParamKey = mainNamedParamKey?:"id"
		if(route){
			allParamsSb.append("allParams['$namedParamKey'] = this.\$route.params.routeId?this.\$route.params.routeId:1;")
		}else{
			allParamsSb.append("""allParams["$namedParamKey"] = eval(this.namedParamKey);\n""")
		}
		if(!ajaxUrlParams.isEmpty()){
			for(Map.Entry entry: ajaxUrlParams){
				allParamsSb.append("allParams['$entry.key'] = '$entry.value';\n")
			}
		}
		String updateStoreScriptcaller = ""
		if(createStore){
//			updateStoreScriptcaller = """ const stateVar = "${dataframeName}Var.\$store.state";\n excon.updateStoreState(resData, stateVar,${dataframeName}Var);"""
		}
		return """
             ${dataframeName}_fillInitData: function(){
                excon.saveToStore('$dataframeName','doRefresh',false);
                let allParams = {};\n
                const propData = this.${dataframeName}_prop;
                 if(propData){
                    allParams = propData; 
                    if(this.namedParamKey == '' || this.namedParamKey == undefined){
                        this.namedParamKey = "this.${dataframeName}_prop.key?this.${dataframeName}_prop.key:this.\$store.state.${dataframeName}.key"; 
                    }
                 }
                ${allParamsSb.toString()}
                allParams['dataframe'] = '$dataframeName';
                $doBeforeRefresh
                this.overlay_dataframe = true;
                let self = this;
                axios.get('$df.ajaxUrl', {
                    params: allParams
                }).then(function (responseData) {
                        let resData = responseData.data;
                        let response = resData?resData.data:'';
                       if(response != null && response != '' && response  != undefined){
                           response["stateName"] = "$dataframeName";
                           ${dataframeName}Var.updateState(response);
                           ${dataframeName}Var.${dataframeName}_populateJSONData(response);
                        }
                        $doAfterRefresh 
                   self.overlay_dataframe = false;
                  ${updateStoreScriptcaller} 
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
             },\n
              """
	}

	public String getSaveDataScript(df, vueSaveVariables, vueFileSaveVariables){
		String dataframeName = df.dataframeName
		StringBuilder embdSaveParms = new StringBuilder("")
		if(embeddedDataframes.size()>0){
			embeddedDataframes.each{
				if(it.trim() != ""){
					embdSaveParms.append("""if(this.\$refs.hasOwnProperty("${it}_ref") && this.\$refs.${it}_ref){for(var a in this.\$refs.${it}_ref.\$data){\n
                                              var dashA = a.split('_').join('-');
                                              allParams[dashA] = this.\$refs.${it}_ref.\$data[a];\n}}\n""")
				}
			}
		}
		String addKeyToVueStore
		if(!putFillInitDataMethod){
			addKeyToVueStore = """var nodeArr = response.nodeId; if(nodeArr && Array.isArray(nodeArr) && nodeArr.length){excon.saveToStore("$dataframeName", "key", response.nodeId[0]);}\n"""
		}
		doAfterSaveStringBuilder.append("""
                    var ajaxFileSave = ${dataframeName}Var.params.ajaxFileSave;
                    if(ajaxFileSave){
                       for(let i in ajaxFileSave) {
                         const value = ajaxFileSave[i];
                         $vueFileSaveVariables
  						 self[value.fieldName+'_ajaxFileSave'](responseData, allParams); 	
					   }
                    } 
                  $addKeyToVueStore
				""")
		return """
              ${dataframeName}_save: function(){
                  let allParams = this.state;                                    
                  $vueSaveVariables
                  ${embdSaveParms?.toString()}
                  ${doBeforeSave}
                  allParams['dataframe'] = '$dataframeName';
                  console.log(allParams)
                  if (this.\$refs.${dataframeName}_form.validate()) {
                      this.${dataframeName}_save_loading = true;
                      const self = this;
                      axios({
                          method:'post',
                          url:'$df.ajaxSaveUrl',
                          data: allParams
                      }).then(function (responseData) {
                        self.${dataframeName}_save_loading = false;
                        var response = responseData.data;
                        ${doAfterSaveStringBuilder.toString()}
                        excon.showAlertMessage(response);
			            	if(response.success) {
                               ${doAfterSave}
                        	}
                      }).catch(function (error) {
                        self.${dataframeName}_save_loading = false;
                              console.log(error);
                      });
                  }

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
                      
                      var allParams = {'dataframe':'$dataframeName'};
                          ${vueSaveVariables}
                       ${doBeforeDelete}
                       if(!confirm("${messageSource.getMessage("delete.confirm.message", null, "delete.confirm.message", LocaleContextHolder.getLocale())}"))return
                       const self = this;
                       axios({
                           method:'post',
                           url:'$ajaxDeleteUrl',
                           params: allParams
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
				if(it.trim()!="" && !registeredComponents.contains(it)){
					vueJsBuilder.addToComponentScript(VueJsBuilder.createCompRegistrationString(it))
				}
			}
		}

	}

	public void createVueStore(VueJsBuilder vueJsBuilder){
		VueStore vStore = vueJsBuilder.getVueStore()
		Map initValues = this.vueStore
		if(initValues && initValues.state){
			vStore.addToState(initValues.state)
		}

		vStore.addToState("key:''\n,")
		vStore.addToState("doRefresh: {},\n")
		vStore.addToState("newData: {},\n")
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
		//String btnStringWithLayoutAndSpringSecurity = WidgetVue.wrapWithSpringSecurity( btn, btnStringWithLayout)
		return btnStringWithLayoutAndSpringSecurity
		//return btnStringWithLayout
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
	/**
	 *
	 * @param hql
	 * @return
	 * The method uses hql to convert that hql query to  sql query.
	 */
	String hqlToSql(def hql){
		SessionFactoryImplementor factory = (SessionFactoryImplementor) sessionFactory;
		QueryTranslatorFactory ast = new ASTQueryTranslatorFactory();
		QueryTranslator queryTranslator = ast.createQueryTranslator( hql , hql , Collections.EMPTY_MAP, factory, null );
		queryTranslator.compile( Collections.EMPTY_MAP, true );
		/*
		 def retTypes = queryTranslator.getReturnTypes()
		 def qIdent = queryTranslator.getQueryIdentifier()
		 def metaClass = queryTranslator.getMetaClass()
		 def metaProp = queryTranslator.getMetaPropertyValues()
		 def columns = queryTranslator.getColumnNames()
		 def aliases = queryTranslator.getReturnAliases()
		 */
		return queryTranslator.getSQLString();
	}

	/**
	 * Retrieves the hqlForUpdate - if unspecified (as in most cases it shouldn't be, simply returns the hql query starting from the 'from' party
	 * @author Shai
	 * */
	String getHqlForUpdate(){
		if(!hqlForUpdate){
			int from = hql.indexOf('from')
			hqlForUpdate = hql.substring(from)
		}
		return hqlForUpdate
	}

	Map<String, Object> getFieldByName(String name){
		return fields.get(name)
	}

	public OrderedMap getFields(){
		return fields;
	}


	public static DataframeVue  getDataframeBeanFromReference(org.springframework.beans.factory.config.RuntimeBeanReference dataframeRef){
		ApplicationContext ctx = Holders.getApplicationContext()
		DataframeVue detailDataframeBean = (DataframeVue) ctx.getBean(dataframeRef.getBeanName());
		detailDataframeBean.init()
		return detailDataframeBean;
	}


	public static StringBuffer getParamOfDataframeFields(def detailDataframeBean){
		def paramList = new StringBuffer()
		paramList.append("[")
		int ii = 1, iii = 1;

		def nmParams = detailDataframeBean?.parsedHql?.namedParameters;

		if("ownerDataFrame".equals(detailDataframeBean.dataframeName)){
			System.out.println();
		}

		for( Map.Entry entry: nmParams){
			def paramName = entry.key;
			paramList.append("'$paramName'")
			if(iii < nmParams.size()){
				paramList.append(",")
			}
			iii++
		}

		paramList.append("]")
		return paramList
	}


	public String getDivId(String key){
		String divId  =   key+"-Div"
		divId = divId.replace(DOT, DASH)
		return divId
	}

	public String getDataVariableForVue(Map field){
		String res = buildKeyFieldParam(dataframeName, field);
		return res
	}

	public boolean isReadOnly(Map field){
		if (field?.readOnly || this.readonly){
			return true
		}
		return false
	}

	public static String buildFullFieldNameKeyParam(Dataframe df, String domainAlias , String dbFieldName, String namedParameter){
		StringBuilder sb = new StringBuilder();
		sb.append("key").append(UNDERSCORE).append(df.dataframeName).append(UNDERSCORE).append(domainAlias).append(UNDERSCORE);
		sb.append(dbFieldName).append(UNDERSCORE).append(namedParameter);
		return sb.toString();
	}
	public static String buildKeyFieldParam(dataframeName, Map field){
		String fieldnameStr = field.name.replace(DOT, UNDERSCORE);
		def doaminNameStr = (field.domainAlias == null)?field.domain?.key:field.domainAlias;
		if(fieldnameStr.indexOf(UNDERSCORE) <= 0 && !"".equals(doaminNameStr) && doaminNameStr != null){
			fieldnameStr = "${doaminNameStr}${UNDERSCORE}${fieldnameStr}";
		}
		return "$dataframeName${UNDERSCORE}$fieldnameStr";
	}

	public static String buildKeyFieldParam(String dataframeName, String domainAlias, String fieldName){
		return "$dataframeName${UNDERSCORE}$domainAlias${UNDERSCORE}$fieldName";
	}
	public static String buildFullFieldNameKeyParamWithDot(Dataframe df, String domainAlias , String dbFieldName, String namedParameter){
		StringBuilder sb = new StringBuilder();
		sb.append("key").append(DOT).append(df.dataframeName).append(DOT).append(domainAlias).append(DOT);
		sb.append(dbFieldName).append(DOT).append(namedParameter);
		return sb.toString();
	}

	public String buildFullFieldName(MetaField field){
		return buildFullFieldName(dataframeName, field.domainAlias , field.name);
	}


	public static String buildFullFieldName(String dataframeName, String alias, String fieldName){
		return "${dataframeName}${DataframeVue.DOT}${alias}${DataframeVue.DOT}${fieldName}";
	}

	public static String buildFullFieldName(String dataframeName, String fieldName){
		return buildKeyFieldParamForMetaData(dataframeName, fieldName);
	}

	//This is when fieldName actually comprises from domain.fieldName
	public static String buildKeyFieldParamForMetaData(String dataframeName, String fieldName){
		return "$dataframeName${DataframeVue.DOT}$fieldName";
	}

	/**
	 *
	 */
	public static String buildFullFieldNameKeyParam(DataframeVue df, String namedParameter){
		String domainName = df.getNamedParamDomainAlias(namedParameter);
		String fieldName = df.getNamedParamFieldName(namedParameter);
		StringBuilder sb = new StringBuilder();
		sb.append("key").append(UNDERSCORE).append(df.dataframeName).append(UNDERSCORE).append(domainName).append(UNDERSCORE);
		sb.append(fieldName).append(UNDERSCORE).append(namedParameter);
		return sb.toString();
	}

	public String getFieldNameFromFieldId(String fieldId){
		String[] fieldArr = fieldId.split(UNDERSCORE);
		def keyWords = ["key", "ref", "parent"];
		boolean keyWordTrue = fieldArr[0] in keyWords;
		if(keyWordTrue && fieldArr.size() > 3){
			return fieldArr[3];
		}
		return fieldArr[fieldArr.size() - 1];
	}

	public String getFieldNameSplitDot(String fieldId){
		String[] fieldArr = fieldId.split("\\.");
		def keyWords = ["key", "ref", "parent"];
		boolean keyWordTrue = fieldArr[0] in keyWords;
		if(keyWordTrue && fieldArr.size() > 3){
			return fieldArr[3];
		}
		return fieldArr[fieldArr.size() - 1];
	}

	public String getFieldDoaminFromFieldId(String fieldId){
		String[] fieldArr = fieldId.split(UNDERSCORE);

		def keyWords = ["key", "ref", "parent"];
		boolean keyWordTrue = fieldArr[0] in keyWords;
		if(keyWordTrue && fieldArr.size() >= 3){
			return fieldArr[2]; //key-<dataframe>-<domain>- ...
		}

		if(fieldArr.size() == 3){
			return fieldArr[1];
		}
		return "";
	}

	public String getFieldDataframeFromFieldId(String fieldId){
		String[] fieldArr = fieldId.split(UNDERSCORE);
		if(fieldArr.size() == 3){
			return fieldArr[0];
		}
		return "";
	}

	public String getFieldValueFromParametersAndName(Map parameters, Map field){
		parameters.each{ key, value ->
			String fullNameKey = getFieldId(field);
			if(key == fullNameKey ){
				return value
			}
		}
		return ""
	}

	public String toString(){
		return this.dataframeName + "  HQL: " + this.hql
	}

	public String getFieldValueFromParametersAndName(Map parameters, String fieldShortName){
		parameters.each{ key, value ->
			if(key == fieldShortName ){
				return value
			}
		}
		return ""
	}

	public class Field{
		static final String WIDGET_NAME="name"
		static final String WIDGET_COLUMN_NAME = "columnName"
		static final String WIDGET_DOMAIN = "domain"
		static final String WIDGET_DOMAIN_ALIAS = "domainAlias"
		static final String WIDGET_TABLE_NAME = "tableName"
		static final String WIDGET_DOMAIN_CLASS = "domainClass"
		static final String WIDGET_ALIAS = "alias"
		static final String WIDGET_TABLE_ALIAS = "tableAlias"
		static final String WIDGET_LENGTH = "length"
		static final String WIDGET_DATA_TYPE = "dataType"
		static final String WIDGET_DEFAULT_VALUE = "defaultValue"
		static final String WIDGET_PK = "pk"
		static final String WIDGET_NOT_NULL = "notNull"
		static final String WIDGET_UNSIGNED = "unsigned"
		static final String WIDGET_AUTOINCREMENT = "autoincrement"
		static final String WIDGET_ZEROFILL = "zerofill"
		static final String WIDGET_charset=null
		static final String WIDGET_COLLABORATION = "collation"
		static final String WIDGET_COMMENT = "comment"
		static final String WIDGET_LABEL_CODE = "labelCode"
		static final String WIDGET_VALUE = "value"
		static final String WIDGET_WIDGET = "widget"
		static final String WIDGET_HQL = "hql"
		static final String WIDGET_WIDGET_OBJECT = "widgetObject"

	}

	public Set<Entry> getNamedParameters(){
		return this.parsedHql?.namedParameters?.entrySet();
	}

	public Set<Entry> getKeysOfNamedParameters(){
		return this.parsedHql?.namedParameters?.keySet();
	}

	public String[] getNamedParameter(String namedParam){
		return this.parsedHql?.namedParameters?.get(namedParam);
	}

	public static String getDataFrameDomainAlias(String customFieldName){
		String[] fieldArr = customFieldName.split("\\.")
		if (fieldArr.size() == 3){
			return fieldArr[1]
		}
	}

	String getNamedParamDomainAlias(String namedParam){
		def nmParamDomAl = getNamedParameter(namedParam);
		if(nmParamDomAl != null){
			return nmParamDomAl[0];
		}
		return null;
	}

	String getNamedParamFieldName(String namedParam){
		def nmParamDomAl = getNamedParameter(namedParam);
		if(nmParamDomAl != null){
			return nmParamDomAl[1];
		}
		return null;
	}
	public static String extractSimpleFieldName(String dataframeName,String fullFieldName, String domainAlias){
		List keyToRemove = [dataframeName,domainAlias]
		List fieldArrList = (ArrayList)Arrays.asList(fullFieldName.split("\\."))
		fieldArrList.removeAll(keyToRemove)
		return fieldArrList[0].toString()
	}

	public String getDomainAlais(){
		def nameParams = parsedHql.getNamedParameters().values().toArray()[0]
		def domainalais = nameParams[0]
		if (domainalais){return domainalais}
		return null
	}

	public static getAliasFieldKey(String domainAlias, String fieldName){
		return domainAlias + DOT + fieldName;
	}

	public String getFieldAlias(String domainAlias, String fieldName){
		return parsedHql.aliasDomainFields.get(getAliasFieldKey(domainAlias, fieldName));
	}

	public Boolean isWrapInDiv(){
		if (this.wrapInDiv){
			return true
		}else {
			return false
		}

	}

/*
	public def getPersistentPropertyByName(String fieldName){
		def parentdomainalais = getDomainAlais()
		def domainMapp = writableDomains.get(parentdomainalais)
		def domainClass = domainClassFromParseDomain(domainMapp)
		def prop = domainClass.getPropertyByName(fieldName)
		return prop
	}

	public static def domainClassFromParseDomain(Map domainMapp){
		Map parsedDomain = domainMapp.get("parsedDomain")
		def domainClass = parsedDomain.get("value")
		return domainClass
	}
*/
}
