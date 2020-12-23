/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.service

import com.elintegro.erf.dataframe.*
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.dataframe.db.types.DataType
import com.elintegro.erf.dataframe.vue.DataframeConstants
import com.elintegro.model.DataframeResponse
import grails.util.Holders
import grails.web.servlet.mvc.GrailsParameterMap
import org.grails.core.DefaultGrailsDomainClass
import org.grails.web.json.JSONObject
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

import javax.servlet.http.HttpSession

class DataframeService implements  DataFrameInitialization/*, DataFrameCrud*/{
	Dataframe parent
	
	String hql
	ParsedHql parsedHql
	
	String divID
	String supportJScript
	
	Map<String, MetaField> fieldsMetadata
				
	Map<String, String> constraintsMetadata
	
	Map fields = [:] // Map of fields and their widgets, displayed on the page. Structured like: [field:<name>, properties:[widget:Textfield.class, width:50, div:"d1"]]
	
	/* This is a map of user defined fields or parameters of the existing fields*/
	Map addFieldDef
	
	
	String scripts = null
	String resultPage  = null
	String currentLayout = null
	
	def  grailsApplication
	
	//TODO: implement Story #1
	void init(){
		
		populateMetaData()
		
		buildDefaultWidgets()
		 
		buildCustomWidgets()
		
	}
		
	/**
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
		parsedHql = new ParsedHql(hql)
		Class clazz = grailsApplication.domainClasses.find { it.clazz.simpleName == parsedHql.fromStr }.clazz
		def resultData = getResultData(hql, clazz)
		for( metaField in getMetaDataFromFields(clazz, parsedHql.fieldStr)){
			fieldsMetadata.put(metaField.name , metaField)
		}
		
		//1. TODO: Retrieve Meta Data for each table from Hql expression from 1. Domain class and 2. from the DB
		
		//2. TODO: Fetch data from Hibernate (if applicable, please research ) 
		
		//3. TODO: Fetch metadata from JDBC		 
		 
		//4. Populate  
		

		parsedHql.fieldsArr.each{ String field ->
			MetaField metaField = new MetaField(field)
			
			// TODO fill MetaField with metadata using domainsClasses, 
			// TODO fill MetaField with metadata using other hibernate contexts
			// TODO fill MetaField with metadata using JDBC if all else fails
			
			fieldsMetadata.put(field, metaField)
			
		}
		
		
		//
		addBeanDefinedMetaData()
	}
	
	/**
	 * 
	 * @param hql
	 * @param clazz
	 * @return
	 * The method uses hql query and domain class as clazz and return the result data 
	 */
	Object getResultData(String hql, Class clazz){
		return  clazz.executeQuery(hql)
	} 
	
	/**
	 * 
	 * @param clazz
	 * @param fields
	 * @return
	 * The method uses domain class and comma separated fields and return list of meta data 
	 */
	List<MetaField> getMetaDataFromFields(Class clazz, String fields){
		String []metaFieldArr = fields.split(",")
		List<MetaField> metaFields = new ArrayList<MetaField>()
		def domain = new DefaultGrailsDomainClass(clazz)
		for(field in metaFieldArr){
			MetaField metaField = new MetaField(field)
			metaField.domain = clazz.getClass().getName()
			def property = domain.getPropertyByName(field) 
			metaField.dataType = MetaField.getDataType( property.getTypePropertyName() )
			metaFields.add(metaField)
		}
		return metaFields
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
	 * The method uses parsedHql and builds metafields, based on Meta Data from the datasource (database).
	 * STORY #2 (out of scope)
	 * 
	 * @return
	 */
	private void addBeanDefinedMetaData(){
		
		
		
		fieldsMetadata.each{
			
			def widget = (it.value.dataType == DataType.DataTypeEnum.BOOLEAN)?com.elintegro.erf.widget.JqCheckbox.class:com.elintegro.erf.widget.JqTextField.class
			switch(it.value.dataType){
				case (DataType.DataTypeEnum.BOOLEAN):
					widget = com.elintegro.erf.widget.JqCheckbox.class
				case (DataType.DataTypeEnum.DATE):
					println "@@@@@>>>>> Date-- Widget loading"
					widget = com.elintegro.erf.widget.JqDate.class					
				default:
					widget = com.elintegro.erf.widget.JqTextField.class
			}
			
			//Please complete this Map, look to the SuperField.groovy class, think what property could be used to define a property of the 
			//widget on the screen 
			fields.put(it.key, [length: it.value.length, pk:it.value.pk, widget: widget, /*...*/] )
		}
	}
	
	/**
	 * 
	 */
	private void buildDefaultWidgets(){
		//Run on the fieldMetaData
		//build fields map		
	}
	
	/**
	 *  
	 */
	private void buildCustomWidgets(){
		//Run on custom
		addFieldDef?.each{
			addField(it.field,it.prop)
		}

	}	



	/**
	 * Using HQL, and parameters, read the data 
	 * TODO - implement
	 */
	List  read(Map<String, String> params){
		List results = null
		
		return results
	}

	/**
	 * TODO - implement
	 */
	Map getJsonResult(List result){
		
		return[:]
	}

	/**
	 * TODO - implement
	 */
	void  delete(Map<String, String> params){
		
	}
		
	/**
	 * TODO - implement
	 */
	void  save(Map<String, String> params) throws DataManipulationException{
		
	}

	
	public addField(String column, Map<String, String> prop){
		if(prop && column){
			Map fieldPropes = fields?.get(column)
			fieldPropes?.putAll(prop)			
		}
	}

	
	/**
	 * 
	 */
	public String getJavascript(){
				
		if(!scripts){
			prepare(currentLayout)
		}
		return scripts 		
	} 

	
   public String getHtml(String layout){
	   if(!resultPage ||  !currentLayout || currentLayout != layout){
	   	prepare(layout)
	   }
	   return resultPage  
   }
  

   private String prepare(String layout){
	   
	   StringBuffer resultPageHtml = new StringBuffer()
	   StringBuffer resultPageJs = new StringBuffer()
	   //TODO: implement
	   //Run on each widget and gather all JavaScripts and add to this one
	   fields.each{field->
		   //TODO: Implement!
		   
		   
	   }
	   
	   currentLayout = layout
	   resultPage = resultPageHtml.toString()
	   scripts =  resultPageJs.toString()
	   return resultPage
   }

	public DataframeResponse saveRaw(JSONObject requestParams){

		Dataframe dataframe = Dataframe.getDataframeByName(requestParams)
		def dataframeInstance = new DataframeInstance(dataframe, requestParams)
		def operation = 'U'; //Update
		def result;

		try {
			result = dataframeInstance.save(true);
		}catch(Exception e){
			log.error("Failed to save data for dataframe ${dataframeInstance.df.dataframeName} Error : \n " + e)
		}

		if(dataframeInstance.isInsertOccured()){
			operation = "I";
		}

		Map savedResultMap = dataframeInstance.getSavedDomainsMap();

		savedResultMap.each { domainAlias, domainObj ->
			def domain = domainObj[0]
			def domainInstance = domainObj[1]
			boolean isInsertDomainInstance = domainObj[2]
			DomainClassInfo domainClassInfo = domain.get(DataframeConstants.PARSED_DOMAIN);
			domain?.domainKeys?.each{ key ->
				def keyValue = domainInstance."${key}"
				String myDomainAlias = domainClassInfo.getDomainAlias()
				def keyOldValue = requestParams?.domain_keys?."${myDomainAlias}"."${key}"
				def castedKeyOldValue = dataframe.getTypeCastValue2(myDomainAlias, key, keyOldValue)
				if(castedKeyOldValue == null ) {
					requestParams?.domain_keys?."${myDomainAlias}".put(key, keyValue)
				}else if(castedKeyOldValue != keyValue){
					//EU!!!
					throw new DataframeException("Save is trying to change Key Value (and it is not Insert!) Could be hacker's attack!")
				}
				domainObj.add(castedKeyOldValue)
			}
		}

		if (dataframeInstance.isMultipleDomainsToSave() && dataframeInstance.isInsertOccured()){
			//inter-domain relationship
			dataframeInstance.setInterDomainRelationships(savedResultMap)
		}

		MessageSource messageSource = dataframe.messageSource
		DataframeResponse response = new DataframeResponse()
		response.operation = operation //todo: make sure is this required in this node?
		response.data = requestParams
		response.data.put(response.operation, operation)
		String msg
		if(result) {
			msg = messageSource.getMessage("data.save.success", null, "save.success", LocaleContextHolder.getLocale())
		} else {
			response.success = false
			msg = messageSource.getMessage("data.save.not.valid", null, "data.not.valid", LocaleContextHolder.getLocale())
		}
		response.message = msg
		//response.dataframeInstance = dataframeInstance

		return response
	}


	def ajaxValuesRaw(JSONObject requestParams, HttpSession session) {
		Dataframe dataframe = Dataframe.getDataframeByName(requestParams)
		def userId = getSessionUserId(session)
		def dfInstance = new DataframeInstance(dataframe, requestParams)

		dfInstance.setSessionParameters(DataframeInstance.getSessionAtributes(session))

		def jsonMap = dfInstance.readAndGetJson()

		def inp = dfInstance.getFieldValuesAsKeyValueMap()
		return jsonMap
	}

	public static long getSessionUserId(def session) {
		def userId = session.getAttribute("userid")
		userId = userId?Long.valueOf(userId):userId
		if ( userId == null ) {
			userId = (long) Holders.grailsApplication.config.guestUserId
			session.setAttribute("userid", userId)
		}
		return userId
	}
}
