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

import com.elintegro.annotation.OverridableByEditor
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.dataframe.frontendlib.DataframeViewJqx
import com.elintegro.erf.layout.StandardLayout
import com.elintegro.erf.layout.abs.Layout

import com.elintegro.erf.widget.Widget
import com.elintegro.erf.widget.vue.InputWidgetVue
import grails.gsp.PageRenderer
import grails.util.Holders
//import grails.validation.Validateable
import groovy.text.SimpleTemplateEngine
import groovy.util.logging.Slf4j
import org.apache.commons.collections.map.LinkedMap
import org.apache.commons.lang.StringUtils
import org.grails.core.DefaultGrailsDomainClass
import org.hibernate.Query
import org.hibernate.Transaction
import org.hibernate.engine.spi.SessionFactoryImplementor
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory
import org.hibernate.hql.spi.QueryTranslator
import org.hibernate.hql.spi.QueryTranslatorFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.orm.hibernate5.SessionFactoryUtils
import org.hibernate.transform.AliasToEntityMapResultTransformer

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
public class Dataframe extends DataframeSuperBean implements Serializable, DataFrameInitialization{
	private static defaultWidget = new InputWidgetVue()
	public static final String DASH = "-";
	public static final String DOT = ".";
	public static final String UNDERSCORE = "_";
	public static final String SESSION_PARAM_NAME_PREFIX = "session_"
	private String currentLanguage = ""
	Dataframe parent
	String dataframeName
	String dataframeLabel = ""
	@OverridableByEditor
	String hql
	String sql //TODO: work around to support queries with associations, should be generated correctly out of hql, but for now if we have association in HQL we need to provide sql (outer join problem, ticket #22)
	String hqlForUpdate
	@OverridableByEditor
	boolean initOnPageLoad = false
	@OverridableByEditor
	boolean saveButton = true
	@OverridableByEditor
	boolean deleteButton = false
	@OverridableByEditor
	boolean submitButton = false
	@OverridableByEditor
	boolean insertButton = false
	@OverridableByEditor
	boolean wrapInForm = true
	boolean wrapInDiv = false
	boolean validateForm = false
	@OverridableByEditor
	boolean addButton = false
	@OverridableByEditor
	boolean createEnableDisableFunctionForAllFields = false
	@OverridableByEditor
	String doBeforeSave = ""
	@OverridableByEditor
	String doAfterSave = ""
	String doAfterRefresh = ""
	@OverridableByEditor
	String doBeforeDelete = "false" //by default delete is not allowed, unless somebody defined a script to turn it into true
	@OverridableByEditor
	String doAfterDelete = ""
	ParsedHql parsedHql
	public 	List pkFields = []
	def groovySql
	Map writableDomains = [:]
	Map defaultRecord = [:]
	@OverridableByEditor
	String initScripts="" // This parameter holds possible initialization scripts
	@OverridableByEditor
	String headerScripts = "" //This parameter holds possible javascript functions to be used during Dataframe lifecicle
	@OverridableByEditor
	String displayType = "inline" //This parameter defines wether to display the dataframe as window or as popup
	@OverridableByEditor
	String insertHtmlTo = ""
	@OverridableByEditor
	String divIdToDisable = ""
	@OverridableByEditor
	String popUpTitle=""
	@OverridableByEditor
	Map summaryAfterSave = [showSummary: false]
	static int BIG_TEXT_FIELD_LENGTH = 50;

	private StringBuilder saveScriptJs = new StringBuilder();
	private StringBuilder doAfterSaveStringBuilder = new StringBuilder();
	private StringBuilder doAfterDeleteStringBuilder = new StringBuilder();
	//TODO make it injected in Spring way!
	private DataframeView dataframeView = new DataframeViewJqx(this)


	String divID
	String supportJScriptSource

	// This is a default to use DataframeController to perform CRUD operations, could be overwritten in Dataframe bean definition to any other controller operation
	def ajaxUrl = "/seniara/dataframe/ajaxValues";
	def ajaxSaveUrl = "/seniara/dataframe/ajaxSave";
	//def ajaxDeleteUrl = "/seniara/dataframe/ajaxDelete"
	def ajaxDeleteUrl = "/seniara/dataframe/ajaxDeleteExpire";
	def ajaxInsertUrl = "/seniara/dataframe/ajaxInsert";
	def ajaxDefaultUrl = "/seniara/dataframe/ajaxDefaultData";
	def ajaxCreateUrl ="/seniara/dataframe/ajaxCreateNew"
	@OverridableByEditor
	Map dataframeButtons = [:];
	@OverridableByEditor
	Map insertFields=[:];
	Map dialogBoxActionParams=[:];
	List<Dataframe> refDataframes = new ArrayList<Dataframe>();

	def ajaxDynamicSelectionBuildUrl

	//TODO: This should be removed to the Widget class!
	def ajaxjQTreeLoadUrl = "/dataframe/ajaxjQTreeLoad"

	//this String URL is to refresh when updating dataframe
	//def dataFrameParamsToRefresh = null

	def connectingFieldName //The field name that other Dataframes are refferred by to this one, it must be equal Table alias

	private Map<String, Dataframe> embeddetDataframe = new LinkedMap<String, Dataframe>();

	//fieldName should be domain.fldName
	public void addEmbeddedDataframe(String fieldName, Dataframe embDf){
		embeddetDataframe.put(fieldName, embDf)
	}

	public Map<String, Dataframe> getEmbeddedDataframe(){
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
	Map<String, Widget> widgets = [:]
	//TODO: logger!!! Make it work Logger log = Logger.getInstance(Dataframe.class)


	//Map fields = [:] // Map of fields and their widgets, displayed on the page. Structured like: [field:<name>, properties:[widget:Textfield.class, width:50, div:"d1"]]

	/* This is a map of user defined fields or parameters of the existing fields*/
	@OverridableByEditor
	Map addFieldDef =[:]  //add a field to the end of the fields
	Map addFieldBeforeDef=[:]   //add a field before specific field
	OrderedMap fields = new OrderedMap()
	def resultData


	String scripts = null
	String resultPage  = null
	String currentFldLayout = null
	@OverridableByEditor
	Layout currentFrameLayout

	//def appContextService
	//def  grailsApplication
	//def sessionFactory
	//def messageSource

	PageRenderer groovyPageRenderer
	def templateEngine = new SimpleTemplateEngine()

	/*	def DEFAULT_LAYOUT="""
	 <label for='<% print field %>' id='label-<% print field %>'><% print label %></label>
	 <span id='mandatory-<% print field %>'><% print mandatory %></span>
	 <span id='widget-<% print field %>'><div id='<% print divId %>'><% print widget %></div></span>
	 <span style='display:none;' id='message-<% print field %>'><% print errorMsg %></span><br>
	 """*/
	public Dataframe(){}
	public Dataframe(def dataframeName){
		if(dataframeName == null)
			throw new DataframeException("Dataframe name not set.")
//		log.debug("dataframe created:" +dataframeName);
		this.dataframeName = dataframeName
		this.dataframeView.dataframeName = dataframeName
	}



	void init(){
		if(fieldsMetadata == null || fieldsMetadata.size() == 0 || parsedHql == null){
			populateMetaData()
		}

		if(fields.size()==0){
			buildCustomWidgets()
		}

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
			String domainAlias = field.domain.get("domainAlias");
			this.writableDomains.put(domainAlias, ["parsedDomain": field.domain, "queryDomain":null, "keys":[], "domainAlias": domainAlias])
		}
	}

	/**
	 *
	 * @param hql
	 * @param clazz
	 * @return
	 * The method uses hql query and domain class as clazz and return the result data
	 */
	/*	Object getResultData(String hql, Class clazz){
	 return  clazz.executeQuery(hql)
	 }
	 */



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


	/**
	 *This method uses meta field to add the widget and put the meta field in fields.Key on fields will be
	 *dataframename.fieldname
	 */
/*	private void buildDefaultWidget( MetaField fld){

		def widget = null
		def dataType = fld.dataType.toString().toUpperCase()
		def fieldName =fld.name;


		switch (dataType) {
			case "INT":
			case "SMALLINT":
			case "BIGINT":
			case "DOUBLE":
			case "SHORT":
				widget = "JqxNumberInputWidget";
				break;
			case "TINYINT":
			case "BOOLEAN":
				widget = "JqxCheckBoxWidget";
				break;
			case "DATE":
			case "DATETIME":
				widget = "JqxDatetimeInput";
				break;
			case "VARCHAR":
				if(fld.length > BIG_TEXT_FIELD_LENGTH){
					widget = "JqxTextArea";
				}else{
					widget = "JqxInputWidget";
				}
				break;
			default :
				widget = "JqxInputWidget";
				break;
		}

		def metaFieldMap = fld.toMap()
		metaFieldMap.put("widget",widget)
		log.info "fld: $fld.name widget:$widget datatype: $fld.dataType"
		fields.put(buildFullFieldName(fld), metaFieldMap )

	}*/

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

	List getHqlResult(def queryHql){

		def keyValue = 0
		Map sessionAttributes = new HashMap()
		List results = null
		def sessionHibernate = sessionFactory.openSession()
		Transaction tx = sessionHibernate.beginTransaction()

		Query query =  sessionHibernate.createQuery(queryHql);
		query = constructQuery(query, keyValue, sessionAttributes)
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		results = query.list()
		return results
	}

	List  getHqlResult(def queryHql, def keyValue, Map sessionAttributes){
		List results = null
		def sessionHibernate = sessionFactory.openSession()
		Transaction tx = sessionHibernate.beginTransaction()

		Query query =  sessionHibernate.createQuery(queryHql);
		Query query1 = constructQuery(query, keyValue, sessionAttributes)
		results = query1.list()

		tx.commit();
		sessionhibernate.close();
		return results
	}

	private Query constructQuery(def query, def keyValue, def sessionAttributes){
		for (String parameter : query.getNamedParameters()) {
			if(parameter.indexOf("session_") > -1){
				def sessionParamValue = DataframeInstance.getSessionParamValue(parameter, sessionAttributes)
				query.setParameter(parameter, sessionParamValue)
			}else{
				query.setParameter(parameter, keyValue)
			}
		}

		return query
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
				fields.put(buildFullFieldName(dataframeName, fieldName), fieldProp)
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
		}
		return scripts
	}

	/**
	 * This method uses the layout(template) to get the build Html for the dataframe using all meta fields in hql.
	 */
	public String getHtml(String fieldLayout, String frameLayout){

		if(!resultPage ||  !currentFldLayout || currentFldLayout != fieldLayout || !currentFrameLayout || currentFrameLayout != frameLayout){
			prepare(fieldLayout, frameLayout)
		}
		return resultPage
	}

	public String getHtml(String fieldLayout){

		if(!resultPage ||  !currentFldLayout || currentFldLayout != fieldLayout){
			prepare(fieldLayout, currentFrameLayout)
		}
		return resultPage
	}

	public String preparejQTreeHtml(){

	}

	public String getHtml(){
		def lhcLocale = LocaleContextHolder.getLocale()
		boolean refresh = false
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
		return resultPage
	}


	/**
	 * This method return the ajax script for saving the objects as per hql.
	 * @return
	 */
	private String getAjaxSaveScripts(){
		StringBuilder additionalParameters = new StringBuilder();

		this.currentFrameLayout.numberFields = fields.getList().size()
		getAdditionalDataFromAjax(additionalParameters)
		String additionalParametersStr = additionalParameters.toString()
		doAfterSaveStringBuilder.append(doAfterSave)
		def ajaxSavescripts = """
			<script>
			/**
			* This method was generataed by: Dataframe class by getAjaxSaveScripts() method
			*/
			jQuery(document).ready(function(){

           Dataframe.${dataframeName}_save = function(){
                 //EU!!!-save
                             var allParams = {'dataframe':'$dataframeName'};
                 if($wrapInDiv){
                   $saveScriptJs
                 }else{   
                  //Add form parameters if current dataframe under the form
                  jQuery.each(jQuery('#$dataframeName-form').serializeArray(), function(i, field) {
                                             allParams[field.name] = field.value;
                                 });
                 }    
                         
                 $additionalParametersStr
             
                 //Eu!! TODO - restore validation!
                 //var validForm = jQuery('#$dataframeName-form').valid();
                    //if(jQuery('#$dataframeName-form').valid()){
                   Dataframe.validForSave = true;
                 ${doBeforeSave}
                 if(Dataframe.validForSave){
                     jQuery.ajax({
                   url: '$ajaxSaveUrl',
                   data: allParams,
                   type: 'POST',
                   error: function(data) {
                    alert("Error saving!");
                   },
                   success: function(data) {
                    if(data.success) {
                     //alert(" id Fields inside callback function: = "+idField);
                         if(data.msg){
                             Dataframe.notification(data.msg, "info");
                         }
                     Dataframe.${dataframeName}.ajaxFileSave?Dataframe.${dataframeName}.ajaxFileSave():"";
                        ${doAfterSaveStringBuilder}
                     if(${summaryAfterSave.showSummary}){
                          var fieldLists = ${summaryAfterSave.fieldLists};
                          data["fieldListFromHql"] = fieldLists;
                          Dataframe.showSummary(data);
                     }
                   }else {
                       if(data.msg){
                            Dataframe.notification(data.msg, "error");
                       }
                   }
                   }
                  });
                 }
                 
           }
			jQuery('body').on('click','#$dataframeName-save', function(){
                  
                if(${!dialogBoxActionParams.isEmpty() && dialogBoxActionParams.containsKey('saveButton')}){
                     var confirmationDivId = "confirmationDialog-${dataframeName}-save";
                     Dataframe.showDialogBox(confirmationDivId);
                }else{
                 Dataframe.${dataframeName}_save();
                }
             
		
		});
		});
		</script>
		"""
	}

	protected void getAdditionalDataFromAjax(StringBuilder additionalParameters){
		for(String key: fields.getList()){
			Map field = fields.dataMap.get(key)
			Widget widgetObj = getWidget(field)

			if (field.containsKey("dataframe")){
				Dataframe refDataframe = getDataframeBeanFromReference(field.dataframe)
				refDataframe.getAdditionalDataFromAjax(additionalParameters)
			}
			String getValuePart = widgetObj?.getValueScript(this, field, getDivId(key), getFieldId(field), key);
			if(!StringUtils.isEmpty(getValuePart)){
				additionalParameters.append("\n allParams['"+getFieldId(field)+"'] = "+ getValuePart +";")
			}
		}

	}

	/**
	 * This method returns the ajax script for prepare the form to insert.
	 * @return
	 */
	private String getAjaxInsertScripts(){

		return """
			<script>
			/**
			* This method was generataed by: Dataframe class by getAjaxInsertScripts() method
			*/
			jQuery(document).ready(function(){

                 Dataframe.${dataframeName}_insert = function(){
                       var formParams = jQuery('#${dataframeName}-form');
                       var formParamsReady = Dataframe.getFormparams(formParams);
                       Dataframe.showDataframe(Dataframe.${dataframeName}.parentDataframe, '${dataframeName}');
                       Dataframe.${dataframeName}.defaultData["isDefault"] = true;
                       Dataframe.${dataframeName}.initValues(Dataframe.${dataframeName}.defaultData);
                       Dataframe.initHiddenValuesForInsert(Dataframe.${dataframeName}.dataFrameParamsToRefresh, '${dataframeName}', formParamsReady);
                 }
                 jQuery('body').on('click', '#${dataframeName}-insert', function(){
                         if(${!dialogBoxActionParams.isEmpty() && dialogBoxActionParams.containsKey('insertButton')}){
                              var confirmationDivId = "confirmationDialog-${dataframeName}-insert";
                              Dataframe.showDialogBox(confirmationDivId);
                         }else{
                          Dataframe.${dataframeName}_insert();
                         }
                  });

		});
		</script>
		"""
	}


	/**
	 * This method return the ajax script for deleting the objects as per hql.
	 */
	private String getAjaxDeleteScripts(){
		doAfterDeleteStringBuilder.append(doAfterDelete)
		def ajaxDeleteScripts = """
			<script>

			/**
			* Source genereted in: Dataframe class by getAjaxDeleteScripts() method
			*/
			jQuery(document).ready(function(){
                  Dataframe.${dataframeName}_delete = function(){
                  
                        var allParams = {'dataframe':'$dataframeName'};
                        var idField = jQuery('#$dataframeName-id').val();
                        var formParams = jQuery('#$dataframeName-form');
                        var serializedParams = formParams.serialize();
                        var parentLevel = Dataframe.${dataframeName}.dataFrameParamsToRefresh.level-1;
                        var parentNodeLevel = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentNodeLevel 
                        var parentFldName = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentFieldName; //parent
                        var parentNode = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentNode; //1
                        var parentNodeId = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentNodeId; //1
                        var allowDelete = $doBeforeDelete;
                  
                        if(!allowDelete){
                          alert("Cannot delete this entity!");
                          return;
                        }
                  
                        serializedParams = 'id='+idField+'&'+serializedParams+'&dataframe=$dataframeName'+'&parentFieldName='+parentFldName + '&parentNode='+parentNode +'&parentLevel='+parentNodeLevel+'&parentNodeId='+parentNodeId;
                        jQuery.ajax({
                        url: '$ajaxDeleteUrl',
                        data: serializedParams,
                        type: 'POST',
                        success: function(data) {
                                               jQuery('#treeWidgetDataframe-tree').jqxTree('refresh');
                          if(data.success){
                             if(data.msg){
                                Dataframe.notification(data.msg, "info");
                              }
                             ${doAfterDeleteStringBuilder}
                           }
                           if(data.msg){
                            Dataframe.notification(data.msg, "error");
                           }
                   
                           try{
                            jQuery('#$dataframeName-form')[0].reset();
                           }catch(e){
                            alert("error!!!" + e);
                           }
                          }
                         
                       });

                   }
     
			       jQuery('body').on('click', '#$dataframeName-delete', function(){
                      if(${!dialogBoxActionParams.isEmpty() && dialogBoxActionParams.containsKey('deleteButton')}){
                            var confirmationDivId = "confirmationDialog-${dataframeName}-delete";
                            Dataframe.showDialogBox(confirmationDivId);
                       }else{
                        Dataframe.${dataframeName}_delete();
                       }
               
		            });
		});
		</script>
		"""
		return ajaxDeleteScripts
	}

	/**
	 * This method return the ajax script for read the object as per the hql.
	 */
	public String getRefreshFunctionName(){
		return dataframeView.getRefreshFunctionName()
	}


	/**
	 * This method return the ajax script for read the object as per the hql. TODO: Move it to DataframeViewJqx, pls
	 */
	public String getRefreshFunction(){

//		def appName = grails.util.Metadata.current.'app.name'
//		def ajaxUrl="/$appName/dataframe/ajaxValues" //TODO: its a default value, should be picket from resource.groovy bean!!!
		def ajaxUrl= ajaxUrl //TODO: its a default value, should be picket from resource.groovy bean!!!
		String refreshFuncName =  getRefreshFunctionName()

		String sb2_ = getJSListOfKeyFields();


		String loadPopupString = (displayType == "popup" && insertHtmlTo && divIdToDisable)?"""  																								 
																								 Dataframe.loadPopup('$insertHtmlTo','$divIdToDisable','$popUpTitle');
																							""":"""""";

		def ajaxscripts = """
		
            if(!('${dataframeName}' in Dataframe)){
                Dataframe.${dataframeName} = {};
            }

			/**
			* JS Source generated in: Dataframe class by getRefreshFunction() method
			*/
			${refreshFuncName} = function(params){
					
                var allParams = {'dataframe':'$dataframeName'};  // TODO automatically add the id's for this dataframe
                jQuery.each(params, function(key,value){
										allParams[key]=value;
									});
				jQuery.ajax({
				url: '$ajaxUrl',
				data: allParams,
				type: 'POST',
				success: function(returnedData) { 
					try{
//					        var dataSize = returnedData.data.additionalData["${dataframeName}"].data.size();
							Dataframe.${dataframeName}.currentData = returnedData.data;
							Dataframe.${dataframeName}.defaultData = returnedData.default;
							//Dataframe.${dataframeName}.defaultData = defaultRec;
							Dataframe.${dataframeName}.parentDataframe = returnedData.parentDataframe;
							Dataframe.${dataframeName}.dataFrameParamsToRefresh = returnedData.dataFrameParamsToRefresh;
							Dataframe.showDataframe(returnedData.parentDataframe, '${dataframeName}');
							Dataframe.${dataframeName}.initValues(returnedData.data);
							${sb2_}							
							jQuery.each(returnedData.data.keys, function(key, value){
									if(key != 'now'){
										//TODO: please consider to remove it: we are going to use full key field name, like key-<df>-<domainAlias>-<fieldName>-<namedParam>
										Dataframe.setHiddenField(key, value, "${dataframeName}");
										var keyFldName = fullKeyFields[key];
										if(keyFldName.startsWith("key-") ){
											Dataframe.setHiddenField(keyFldName, value, "${dataframeName}");
										}
									}
							});

							if(!(returnedData.dataFrameParamsToRefresh.parentFieldName == '' || returnedData.dataFrameParamsToRefresh.parentFieldName == 'undefined' || returnedData.dataFrameParamsToRefresh.parentFieldName == undefined)){
								var parentFldName = '${dataframeName}-' + returnedData.dataFrameParamsToRefresh.parentFieldName;
								Dataframe.setHiddenField(parentFldName, returnedData.dataFrameParamsToRefresh.parentNodeId, "${dataframeName}");
								Dataframe.setHiddenField('${dataframeName}-parentFieldName', returnedData.dataFrameParamsToRefresh.parentFieldName, "${dataframeName}");
							}

							Dataframe.setHiddenField('${dataframeName}-level', returnedData.dataFrameParamsToRefresh.level, "${dataframeName}");
							Dataframe.setHiddenField('${dataframeName}-parentNode', returnedData.dataFrameParamsToRefresh.parentNode, "${dataframeName}");
							Dataframe.setHiddenField('${dataframeName}-parentNodeId', returnedData.dataFrameParamsToRefresh.parentNodeId, "${dataframeName}");
							Dataframe.setHiddenField('${dataframeName}-parentNodeLevel', returnedData.dataFrameParamsToRefresh.parentNodeLevel, "${dataframeName}");
							
                           //if(jQuery('#$dataframeName-div').is(':visible')){
						   $loadPopupString						   	
						   ${doAfterRefresh}
	                }catch(e){
							alert(" FE: Error while refreshing Dataframe: ${dataframeName}, error is: " + e);
					}
			}
			});
		}

		"""
		// load the dataframe if initOnPageLoad=true (defaults to false)
		if(this.initOnPageLoad){
			/*            ajaxscripts += """
			 \$(document).ready(function() {
			 var params = {};
			 Dataframe.${dataframeName}.initValues(params);
			 });
			 """ */
		}

		return ajaxscripts
	}

	private String getAjaxDefaultValueScripts(){
		def ajaxDefaultScripts = """
			<script>

			/**
			* Source genereted in: Dataframe class by getAjaxDefaultValueScripts() method
			*/
			jQuery(document).ready(function(){

			jQuery('#$dataframeName-addDefault').click(function(params){
                  var parentLevel = Dataframe.${dataframeName}.dataFrameParamsToRefresh.level;
                  var parentNodeLevel = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentNodeLevel
                  var parentFldName = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentFieldName; //parent
                  var parentFieldName = parentFldName.split('-').pop()
                  var parentNode = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentNode; //1
                  var parentNodeId = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentNodeId; //1
                  var parentDataframe = Dataframe.${dataframeName}.dataFrameParamsToRefresh.parentDataframe
                  var allParams = {'dataframe':'$dataframeName','level':parentLevel,'parentNodeLevel':parentNodeLevel,'parentFieldName':parentFieldName,'parentNode':parentNode,'parentNodeId':parentNodeId,'parentDataframe':parentDataframe};

                 jQuery.each(jQuery('#$dataframeName-form').serializeArray(), function(i, field) {
                      allParams[field.name] = field.value;
                 });
                  jQuery.ajax({
						url: '$ajaxCreateUrl',
						data: allParams,
						type: 'POST',
						success:  function(returnedData){
						      Dataframe.returnedRefreshData(returnedData);
						      Dataframe.initHiddenValuesForInsert(Dataframe.${dataframeName}.dataFrameParamsToRefresh, '${dataframeName}', allParams);
						}
					});

		});
		});
		</script>
		"""
		return ajaxDefaultScripts
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
		Layout defLayout = new StandardLayout()
		return prepare(null, defLayout)
	}

	/**
	 * Overloading prepare(String fieldLayout, String frameLayout)
	 * @param fieldLayout
	 * @param frameLayout
	 * @return
	 */
	private String prepare(String frameLayout){
		Layout defLayout = new StandardLayout()
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
		Layout frameLayout = Class.forName(frameLayoutName)
		return prepare(fieldLayout, frameLayout)
	}

	/**
	 * Load Dataframe that was defined defined by this reference and also return it to the caller  and put it to the refrence Dataframe local collector.
	 * @param dfb
	 * @return
	 */
	private Dataframe loadReferenceDataframe(DFButton dfb) {
		Dataframe referenceDataframeBean = Dataframe.getDataframeBeanFromReference(dfb.refDataframe)
		this.refDataframes.add(referenceDataframeBean)
		return referenceDataframeBean;
	}

	/**
	 * Prepare DataFrame to display
	 * @param fieldLayout
	 * @param frameLayout
	 * @return
	 */
	private String prepare(String fieldLayout, Layout frameLayout){


		init();

		StringBuffer jsValidteRules = new StringBuffer()
		jsValidteRules.append(initScripts)
		jsValidteRules.append("<script>jQuery(document).ready(function() {")
		constraintsMetadata.each(){name, regex->
			def javascriptvalidtor = """
                   jQuery.validator.addMethod("regex_$name", function(value, element, param) {
					    return value.match($regex) != null;
				    });
                   """
			jsValidteRules.append(javascriptvalidtor)
		}

		currentFldLayout = fieldLayout
		if(frameLayout != null){
			currentFrameLayout = frameLayout
		}

		jsValidteRules.append("});</script>")
		scripts  = jsValidteRules.toString()
		//layout = layout?:""
		StringBuffer resultPageHtml = new StringBuffer()


		this.currentFrameLayout.numberFields = fields.getList().size()


		//resultPageHtml.append(layout)
		StringBuffer resultPageJs = new StringBuffer()
		StringBuffer jsValidteFormRules = new StringBuffer()
		StringBuffer jsValidteFormMessages = new StringBuffer()
		StringBuffer headerScript = new StringBuffer()
		StringBuffer bodyScript = new StringBuffer()
		resultPageHtml.append("<div id='$dataframeName-div' style='display:none;'>")
		if(wrapInForm){
			resultPageHtml.append("<form id='${formId}' >")    // TODO change all other instances of formId
		}else if (wrapInDiv){
			saveScriptJs.append(buildDivParams())
		}
		resultPageJs.append("<script>jQuery(document).ready(function () {")

		if(ajaxUrl){
			def refreshFuction = getRefreshFunction()
			resultPageJs.append(refreshFuction)
		}

		if(dataframeButtons){
			for(Entry<String, DFButton> entry: dataframeButtons.entrySet()){

				def dfb = (com.elintegro.erf.dataframe.DFButton)entry.value;
				dfb.name = (String)entry.key;
				def refDf
				if(dfb.refDataframe){
					refDf = loadReferenceDataframe(dfb)
				}
				def buttonScript = dataframeView.getAjaxButtonScript(dfb, refDf);
				if(buttonScript){
					resultPageJs.append(buttonScript)
				}
			}
		}

		StringBuilder loadPopupStrBld = new StringBuilder();
		if(insertHtmlTo){
			loadPopupStrBld.append("Dataframe.${dataframeName}.insertHtmlTo = '$insertHtmlTo';\n");
		}
		if(divIdToDisable){
			loadPopupStrBld.append("Dataframe.${dataframeName}.divIdToDisable = '$divIdToDisable';\n");
		}

		if(displayType){
			loadPopupStrBld.append("Dataframe.${dataframeName}.displayType = '$displayType';\n");
		}

		String loadPopupString = loadPopupStrBld.toString();

		resultPageJs.append("""
		// initialize dataframe namespace
		if (window['Dataframe'] == undefined){
			Dataframe = {};
		}
		if(!('${dataframeName}' in Dataframe)){
			Dataframe.${dataframeName} = {};
			Dataframe.${dataframeName}.currentData = {};
		}

        Dataframe.${dataframeName}.insertHtml = function(html){				 
                jQuery("#$insertHtmlTo").html(html);
		}

		$loadPopupString

		Dataframe.${dataframeName}.initValues = function(json){
			if(json == undefined){
				alert("default data has not been formed, please ask developers to fix it!");	
			}

""")
		if (wrapInDiv){
			resultPageJs.append("""Dataframe.${dataframeName}.wrapInDiv = true;""")
		}

		List fldsList = fields.getList()
		int seq = 0

		for(String key: fields.getList()){
			// TODO  make sure the javascript sourcecode aligns
			Map field = fields.dataMap.get(key)

			if(field.get("widget") != null){

				Widget widget = getWidget(field)

				//TODO: this is for debugging!
				if(field.get("widget") == "JqxComboboxWidget"){
					log.debug("Taking care of combobox!")
				}else if(field.get("widget") == "JqxTreeWidget"){
					widget.setTreeMap(field, key) //TODO: put it somewhere else!!!! no state in Widget beans!
					log.debug("Taking care of tree!")
				}else if(field.get("widget") == "DataframeWidget"){
					log.debug("Taking care of Dataframe!")
				}

				def divId  =   getDivId(key)
				if(fieldLayout == null)
					fieldLayout = Layout.DEFAULT_FIELD_LAYOUT

				def fldName = field.name;
				field.labelCode = field.labelCode?field.labelCode:fldName
				def fldNameDefault = org.apache.commons.lang.WordUtils.capitalizeFully(fldName);
				def fldId =  getFieldId(field)
				def lhcLocale = LocaleContextHolder.getLocale()
				String btnDivId = "";
				String btnWidget = "";
				for(Entry<String, DFButton> entry: dataframeButtons.entrySet()){
					DFButton btn = (DFButton)entry.value;
					btn.name = entry.key;
					if(btn.refField && (field.alias.toString().equals(btn.refField.toString()))){
						btnDivId =  getDivId(btn.refField.trim())
						btnWidget = getBtnWidget(btn)
					}

				}
				def label = field.fldNmAlias?:messageSource.getMessage(field.labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
				if (field?.labelDisabled){
					label = ""
				}
				def errorMsg = messageSource.getMessage("error.$field.labelCode", null, "Error", LocaleContextHolder.getLocale())
				def binding = [divId: divId, field: fldName.replace(".", "-"), label: label,mandatory:field.notNull?"*":"",
							   widget: widget.getHtml(this, field), // TODO: if you need to pass any extra params to getHtml (such as which is the current field etc.) do so
							   errorMsg: errorMsg, btnDivId: btnDivId, btnWidget: btnWidget]
				def template = templateEngine.createTemplate(fieldLayout).make(binding)
				String fieldName = field?.externalDomainField?"${fldName}":"${field.domainAlias}.${fldName}"
				//Applying Layout:
				this.currentFrameLayout.applyLayout(resultPageHtml, widget, template.toString(), fieldName, seq)
				seq++

				//				resultPageJs.append("jQuery('#$fldId-preview').html('');")
//				saveScriptJs.append( "  allParams['${getFieldId(field)}'] =" + widget.generateValueGetterScript(this, field, divId, fldId, key))

				headerScript.append(widget.getElementAttributeSetter(divId, field, fldId))
				// TODO:
				// 1. create a separate method to call for the particular widget's javascript
				// 2. create a mechanism in which if a widget appears twice, the javascript code is customized for each widget OR if it's the same for all widgets, that it appears twice (need to create a JavascriptCodeManager that checks if a piece of code already exists, using hashing.

				// temporary for now:

				headerScript.append(widget.getHeaderScript(this, field, divId))
				//This generates javascript to set the value for the field:
				resultPageJs.append(widget.getValueSetter(this, field, divId, fldId, key))
				bodyScript.append(widget.getBodyScript(this, field))

				if(createEnableDisableFunctionForAllFields || field.createEnableDisableFunction){
					bodyScript.append("\n")
					bodyScript.append(widget.getEnabledDisabledFunction(this,field))
				}

				/*if(constraintsMetadata?.get(field.name) != null){
					def rule="""
							'$fldId': { regex_$field.name: true },
						"""
					jsValidteFormRules.append(rule)
					def message = """
							'$fldId': { regex_$field.name: "$errorMsg" },
						"""
					jsValidteFormMessages.append(message)
				}*/

				if(field.validate){
					String message = field.validate.message?:""
					String action =  field.validate.action?:""
					String rule =  field.validate.rule?:""
					String rules = """{ input: '#$fldId', message: '$message', action: '$action', rule: '$rule'},"""
					jsValidteFormRules.append(rules)
				}

			}else{
				log.error("No widget for the field $field.name")
			}

		}//End of for loop for fields

		Layout.removeAllOtherFieldsPlaceholder(resultPageHtml);
		resultPageJs.append("}\n") //End of initValues
		if(headerScripts){
			resultPageJs.append(headerScripts)
			resultPageJs.append("\n")
		}
		resultPageJs.append(bodyScript.toString())
		resultPageJs.append("""\nif('$displayType'=='popup' && '$insertHtmlTo' && '$divIdToDisable'){
              jQuery('#$insertHtmlTo').on('close', function(event){
                   event.preventDefault();
                   jQuery('#$insertHtmlTo').hide();
                   jQuery('#$divIdToDisable').css('pointer-events','auto');
              });
              }\n""")
		resultPageJs.append("""  });</script>\n""")

		if(ajaxSaveUrl){
			if(saveButton){
				String saveButtonLabel = messageSource.getMessage("${dataframeName}.button.save", null, messageSource.getMessage("button.save", null, "Save", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
				resultPageHtml.append(" <input type='button' class ='jqxButton' value='${saveButtonLabel}' id='$dataframeName-save'/>\n")     // TODO  i18n
			}
			def ajaxSaveScript = getAjaxSaveScripts()
			resultPageJs.append(ajaxSaveScript)
		}

		if(ajaxInsertUrl){
			if(insertButton){
				String insertButtonLabel = messageSource.getMessage("${dataframeName}.button.insert", null, messageSource.getMessage("button.insert", null, "Insert", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
				resultPageHtml.append(" <input type='button' class ='jqxButton' value='${insertButtonLabel}' id='$dataframeName-insert'/>\n")
			}
			def ajaxInsertScript = getAjaxInsertScripts()
			resultPageJs.append(ajaxInsertScript)
		}


		if(ajaxDeleteUrl){
			if(deleteButton){
				String deleteButtonLabel = messageSource.getMessage("${dataframeName}.button.delete", null, messageSource.getMessage("button.delete", null, "Delete", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
				resultPageHtml.append(" <input type='button' class ='jqxButton' value='${deleteButtonLabel}' id='$dataframeName-delete'/>\n")  // TODO  i18n
			}
			def ajaxDeleteScript = getAjaxDeleteScripts()
			resultPageJs.append(ajaxDeleteScript)
		}

		if (ajaxCreateUrl){
			def ajaxScripts = getAjaxDefaultValueScripts()
			resultPageJs.append(ajaxScripts)
		}


		def rules = jsValidteFormRules.toString()
		def msgs = jsValidteFormMessages.toString()
		//resultPageJs.append("</script>")
		//				<script>
//				jQuery(document).ready(function(){
//				jQuery('#$dataframeName-form').validate({
//					rules:{$rules},
//					messages:{$msgs}
//				});
//				});
//				</script>
		if(validateForm){
//		if(ajaxSaveUrl && wrapInForm){
			def ajaxValidationscripts = """
				<script>
                    jQuery(document).ready(function(){
                    jQuery('#$dataframeName-div').jqxValidator({
                                hintType: "label",
                                rules: [
                         $rules
                    ]});
                    });
                </script>
			"""

			resultPageJs.append(ajaxValidationscripts)
//		}
		}
		resultPageJs.append(headerScript.toString())
		// append button only if applicable

		if(submitButton){
			String submitButtonLabel = messageSource.getMessage("${dataframeName}.button.submit", null, messageSource.getMessage("button.submit", null, "Submit", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
			resultPageHtml.append(" <input type='submit' class ='jqxButton' value='${submitButtonLabel}' id='$dataframeName-submit'/>\n")   // TODO  i18n
		}

		if (addButton){
			String addButtonLabel = messageSource.getMessage("${dataframeName}.button.add", null, messageSource.getMessage("button.add", null, "Add Button", LocaleContextHolder.getLocale()), LocaleContextHolder.getLocale())
			resultPageHtml.append(" <input type='button' class ='jqxButton' value='${addButtonLabel}' id='$dataframeName-addDefault'/>\n")
		}

		for(Entry<String, DFButton> entry: dataframeButtons.entrySet()){
			DFButton btn = (DFButton)entry.value;
			btn.name = entry.key;
			String btnString = getBtnWidget(btn)
			if(!(btn.refField || btn.refInDivId)) {
				if(btn.buttonLayoutPlaceholder && btn.buttonLayoutPlaceholder?.trim()){
					Layout.applyButtonPlaceholder(resultPageHtml, btn.buttonLayoutPlaceholder, btnString)
				}else {
					resultPageHtml.append(btnString)
				}
			}else if (btn.refInDivId){
				Layout.applyButtonPlaceholderInDiv(resultPageHtml, btn.name, btnString, dataframeName)
			}
			if(btn.dialogBoxActionParams){
				def dialogBoxActionParams = btn.dialogBoxActionParams
				Widget dialogBoxWidget = getWidget(dialogBoxActionParams)
				resultPageHtml.append(dialogBoxWidget.getHtml(this,dialogBoxActionParams))
				String divId = "confirmationDialog-$dataframeName-$dialogBoxActionParams.buttonFor"
				resultPageJs.append(dialogBoxWidget.getHeaderScript(this,dialogBoxActionParams,divId))
			}
		}

		resultPageHtml.append(" <div id='$dataframeName-errorContainer'></div>\n");


		if(wrapInForm){
			resultPageHtml.append("</form>")
		}

		if(dialogBoxActionParams){
			for(Entry entry: dialogBoxActionParams.entrySet()){
				Map mapFromSet = new HashMap()
				String key = entry.getKey()
				mapFromSet.put(key,entry.getValue())
				def actionKey = mapFromSet.get(key)
				Widget dialogBoxWidget = getWidget(actionKey)
				resultPageHtml.append(dialogBoxWidget.getHtml(this,actionKey))
				String divId = "confirmationDialog-$dataframeName-${actionKey.get('buttonFor')}"
				resultPageJs.append(dialogBoxWidget.getHeaderScript(this,actionKey,divId))
			}
		}
		resultPageHtml.append("</div>")

		currentFldLayout = fieldLayout
		resultPage = resultPageHtml.toString()
		scripts +=  resultPageJs.toString()
		return resultPage
	}

	public String getBtnWidget(DFButton btn){
		String buttonLabel = messageSource.getMessage("${dataframeName}.button.${btn.name}", null, btn.name, LocaleContextHolder.getLocale())
		String btnString="";
		String style= btn.style?:""
		if ("link".equals(btn.type)) {
			btnString = " <a href='${btn.url}' style='${style}' value='${buttonLabel}' id='${dataframeName}-${btn.name}'>$buttonLabel</a>"
		}else{
			if(btn.image && btn.image.showIcon){
				String imgUrl =  messageSource.getMessage(btn.image.url, null, buttonLabel, LocaleContextHolder.getLocale())
//				   	  btnString = """<div class ='jqxButton' style='margin:1px 2px 1px 9px; width: 80px; display:"inline"' id='${dataframeName}-${btn.name}'><img height="${btn.image.height?:20}" width="${btn.image.width?:25}" src="$imgUrl"/><div class='buttonValue'>$buttonLabel</div></div>\n"""
				btnString = """<button class ='jqxButton' style="display:inline-block; ${style}" id='${dataframeName}-${btn.name}'>
                                  <img height="${btn.image.height?:20}" width="${btn.image.width?:25}" src="$imgUrl"/>
                                  </button>"""
			}else{
				btnString=" <input type='button' style='${style}' class ='jqxButton' value='${buttonLabel}' id='${dataframeName}-${btn.name}'/>"

			}
		}
		return btnString.toString()
	}

	public Widget getWidget(String fieldName ){
		Map field = fields.get(fieldName)
		return getWidget(field)
	}

	public Widget getWidget(Map field ){
		String widgetName = field.widget
		if(field.containsKey(Field.WIDGET_WIDGET_OBJECT) && field.get(Field.WIDGET_WIDGET_OBJECT) != null){
			return field.get(Field.WIDGET_WIDGET_OBJECT)
		}else{

			Widget widgetObject = defaultWidget
			if(widgetName?.trim()){
				log.debug("Found widget for $field.name != $widgetName")
				widgetObject = (Widget) Holders.grailsApplication.mainContext.getBean(widgetName)
			}

			field.put("widgetObject", widgetObject)

			return widgetObject
		}
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


	public static Dataframe  getDataframeBeanFromReference(org.springframework.beans.factory.config.RuntimeBeanReference dataframeRef){
		ApplicationContext ctx = Holders.getApplicationContext()
		Dataframe detailDataframeBean = (Dataframe) ctx.getBean(dataframeRef.getBeanName());
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

	public String getFieldId(Map field){
		String res = buildKeyFieldParam(dataframeName, field);
		return res
	}

	public static String buildKeyFieldParam(dataframeName, Map field){
		String fieldnameStr = field.name.replace(DOT, DASH);
		def doaminNameStr = (field.domainAlias == null)?field.domain?.key:field.domainAlias;
		if(fieldnameStr.indexOf(DASH) <= 0 && !"".equals(doaminNameStr) && doaminNameStr != null){
			fieldnameStr = "${doaminNameStr}${DASH}${fieldnameStr}";
		}
		return "$dataframeName${Dataframe.DASH}$fieldnameStr";
	}

	public String buildFullFieldName(MetaField field){
		return buildFullFieldName(dataframeName, field.domainAlias , field.name);
	}


	public static String buildFullFieldName(String dataframeName, String alias, String fieldName){
		return "${dataframeName}${Dataframe.DOT}${alias}${Dataframe.DOT}${fieldName}";
	}


	public static String buildFullFieldName_(String dataframeName, String alias, String fieldName){
		return "${dataframeName}${Dataframe.DASH}${alias}${Dataframe.DASH}${fieldName}";
	}

	public static String buildFullFieldName(String dataframeName, String fieldName){
		return buildKeyFieldParamForMetaData(dataframeName, fieldName);
	}


	//This is when fieldName actually comprises from domain.fieldName
	public static String buildKeyFieldParamForMetaData(String dataframeName, String fieldName){
		return "$dataframeName${Dataframe.DOT}$fieldName";
	}

	//This is when fieldName actually comprises from domain.fieldName
	public static String buildKeyFieldParamForMetaData_(String dataframeName, String fieldName){
		if(fieldName.contains(".")){
			fieldName = fieldName.trim().replace(".", "-")
		}
		return "${dataframeName}${Dataframe.DASH}${fieldName}";
	}

	/**
	 *
	 */
	public static String buildFullFieldNameKeyParam(Dataframe df, String namedParameter){
		String domainName = df.getNamedParamDomainAlias(namedParameter);
		String fieldName = df.getNamedParamFieldName(namedParameter);
		StringBuilder sb = new StringBuilder();
		sb.append("key").append(DASH).append(df.dataframeName).append(DASH).append(domainName).append(DASH);
		sb.append(fieldName).append(DASH).append(namedParameter);
		return sb.toString();
	}

	/**
	 *
	 */
	public static String buildFullFieldNameRefParam(Dataframe df, String namedParameter){
		String domainName = df.getNamedParamDomainAlias(namedParameter);
		String fieldName = df.getNamedParamFieldName(namedParameter);
		StringBuilder sb = new StringBuilder();
		sb.append(df.dataframeName).append(DASH).append(domainName).append(DASH);
		sb.append(fieldName);
		return sb.toString();
	}


	/**
	 *
	 */
	public static String buildFullFieldNameParentParam(Dataframe df, String namedParameter){
		String domainName = df.getNamedParamDomainAlias(namedParameter);
		String fieldName = df.getNamedParamFieldName(namedParameter);
		StringBuilder sb = new StringBuilder();
		sb.append("parent").append(DASH).append(df.dataframeName).append(DASH).append(df.getNamedParamDomainAlias(namedParameter)).append(DASH);
		sb.append(df.getNamedParamFieldName(namedParameter));
		return sb.toString();
	}


	public static String buildFullFieldNameKeyParam(Dataframe df, String domainAlias , String dbFieldName, String namedParameter){
		StringBuilder sb = new StringBuilder();
		sb.append("key").append(UNDERSCORE).append(df.dataframeName).append(UNDERSCORE).append(domainAlias).append(UNDERSCORE);
		sb.append(dbFieldName).append(UNDERSCORE).append(namedParameter);
		return sb.toString();
	}

	public String getFieldNameFromFieldId(String fieldId){
		String[] fieldArr = fieldId.split(DASH);
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
		String[] fieldArr = fieldId.split(DASH);

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
		String[] fieldArr = fieldId.split(DASH);
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

	public static String getWidgetElementSearchByIdExpression(dataframe, fieldProps){
		return '#$' + buildKeyFieldParam(dataframe.dataframeName, fieldProps)
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
}