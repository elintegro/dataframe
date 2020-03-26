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
import com.elintegro.erf.widget.Widget
import grails.gorm.transactions.Transactional
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.apache.commons.lang.ClassUtils
import org.apache.commons.lang.StringUtils
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.orm.hibernate.cfg.HibernatePersistentEntity
import org.grails.web.json.JSONArray
import org.hibernate.Query
import org.hibernate.Transaction
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.HttpSession

/**
 * This class holds and manages all transaction data for the records from the database and other sources. 
 * It is responsible for populating View with concrete data and also for  persisting it in the Database
 *
 * @author ELipkov1
 *
 */
@Slf4j
class DataframeInstance {
	private final Dataframe df
	private final Dataframe parentDf
	private final Map<String, String> requestParams
	private Map<String, Object> sessionParams
	private List results
	private def record
	private Map resultMap = [:];
	private Map<String, String> namedParmeters = [:]
	private Map savedDomainsMap = [:];
	private List<DataframeVue> hasManyDomainsList = []
	private def sessionHibernate
	private boolean insertOccured = false;
	public final String prefixForHistoryTable = "Ledger"

	private List<DataframeInstance> embeddetDfInst = new ArrayList<DataframeInstance>();
	private def embeddedDomainInstance = []
	boolean isDefault = false;
	private def grailsApplication

	public addEmbeddedDfInstance(DataframeInstance embDfInst){
		embeddetDfInst.add(embDfInst)
	}

	public DataframeInstance(Dataframe df, Map<String, String> requestParams){
		this.requestParams = requestParams
		this.df = df
//		sessionHibernate = SessionFactoryUtils.getSession(df.parsedHql.sessionFactory, true)
		sessionHibernate = df.parsedHql.sessionFactory.openSession()
	}

	public void setSessionParameters(Map<String, Object> sessionParams){
		this.sessionParams = sessionParams
	}

	public boolean isGood(){
		return (results && record && df.hql) || (!df.hql && df.fields)
	}

	def getDfRecord(){
		return record
	}

	def getFieldValue(Map field){
		return getFieldValue(field?.name)
	}

	def getFieldValue(String fieldName){
		return record?.getAt( df.getFieldIndexByName(fieldName))
	}

	public def getNamedParameters(){
		return namedParmeters
	}

	boolean isFieldExistInDb(Map mapFieldValue){
		if(!df.hql){
			return false;
		}
		String nm = mapFieldValue.name
		boolean t = df.parsedHql.indexOfFileds.containsKey(mapFieldValue.name)
		return df.parsedHql.indexOfFileds.containsKey(mapFieldValue.name)
	}

	/**
	 *
	 */
	public Map getAdditionalData(String fieldName, Map parameterMap){
		Map additionalData = [:]

		def widgetObj = df.getWidget(fieldName)
		def widgetObjDbg = df.fields.get(fieldName)?.get("widgetObject") //TODO: remove it 
		if(fieldName.contains("paymentMethods")){
			println "checking paymentMethods"
		}

		if(widgetObj){
			additionalData = widgetObj.loadAdditionalData(this, fieldName, parameterMap, sessionHibernate)
		}
		return additionalData
	}

	private void populateInstance(){

		if(!df.hql){
			return;
		}

		Query query;
		String sqlGenerated;

		try{
			sqlGenerated = df.parsedHql.getSqlTranslatedFromHql()
			query =  sessionHibernate.createQuery(df.hql);
			if(df.sql){
				log.debug(" SQL was defined in the bean: ${df.sql}") //TODO if specified, run this thing ...
			}
			log.debug(" hql = $df.hql \n sqlGenerated = $sqlGenerated \n sql = $df.sql \n")
		}catch(Exception e){
			log.error("Hql-Sql creation problem "+e+"\n hql = $df.hql \n sql = $df.sql\n")
		}

		//EU!!!! Here we put right key field value for retrieving right record from DB

		for (String parameter : query.getNamedParameters()) {

			//Get named parameters from Dataframe (Its a Map that keys are parameter name (like :ownerId, without :)
			// and the value of each element is Array of 2 elements: correspondent domain alias and field name
			def namedParam = df.getNamedParameter(parameter);
			String refDomainAlias =  namedParam[0];
			String refFieldName =  namedParam[1];
			//Build the name that suppose to be in the request with the value, coming from the front-end
			String buildParamName = Dataframe.buildFullFieldNameKeyParam(df, refDomainAlias, refFieldName, parameter);

			def namedParamvalue = null
			String paramStringValue1 = ""
			if(requestParams.containsKey(buildParamName) || requestParams.containsKey(parameter)){
				String paramStringValue = requestParams.get(buildParamName);
				paramStringValue1 = requestParams.get(parameter);
				namedParamvalue = df.getTypeCastValue2(refDomainAlias, refFieldName, paramStringValue1);
			}else{
				if(parameter.indexOf(Dataframe.SESSION_PARAM_NAME_PREFIX) > -1){
					paramStringValue1 = getSessionParamValue(parameter, sessionParams)
					namedParamvalue = df.getTypeCastValue2(refDomainAlias, refFieldName, paramStringValue1);
				}
			}
			namedParmeters.put(parameter, paramStringValue1)
			query.setParameter(parameter, namedParamvalue)
		}

/*		if(requestParams.containsKey(buildParamName)){
			String paramValue = requestParams.get(buildParamName) //EU!!! here we are trying to find a request param with the name <dataframe>-<paramName>, it is wrong!!!
			def value = df.getTypeCastValueFromHql(parameter, requestParams.get(parameter))
		}
*/
		namedParmeters.put("now", new Date())

		Transaction tx = sessionHibernate.beginTransaction()
		try{

			this.results = query.list()
			tx.commit();
			if(results){
				this.record = results[0] //TODO: its hard coded assumption we have only one record per Dataframe!
				calculateFieldValuesAsKeyValueMap();
				log.debug("");
			}else{
				isDefault = true
//				throw new DataframeException("No record found for the Dataframe");
			}

		}catch(Exception e){
			tx.rollback()
			log.error"Error: ${e.message}", e
		}

	}


	public def static getSessionParamValue(String parameter, Map<String, Object> sessionParams) {
		String sessionParamName = parameter.substring(Dataframe.SESSION_PARAM_NAME_PREFIX.length())

		def sessionParamValue = null;

		//TODO: make it more generic, decouple this logic from Dataframe!
		if(!sessionParams.containsKey(sessionParamName) && "userid".equals(sessionParamName)){
			sessionParamValue = (long)Holders.grailsApplication.config.guestUserId //Guest user
		}

		if(sessionParams.containsKey(sessionParamName)){
			sessionParamValue = sessionParams.get(sessionParamName)
		}

		if(sessionParamValue == null){
			throw new DataframeException("No session parameter is defined: " + sessionParamName)
		}
		return sessionParamValue
	}

	/**   CRUD - RETRIEVE
	 * Using HQL, and parameters, read the data
	 * and return the map which can be converted to json on controller.
	 */
	public def readAndGetJson(){
		return retrieveAndGetJson()
	}

	/**   CRUD - RETRIEVE
	 * Using HQL, and parameters, read the data
	 * and return the map which can be converted to json on controller.
	 */
	public def buildDefaultJson(){

		//retrieving from DB!
		//populateInstance()

		Map jsonRet = [:];
		Map jsonMapDf = [:];
		Map jsonAdditionalData = [:];
		Map jsonDefaults = [:];

		//if( isGood()){
		//!!!!!!!!!!!!!!!!!!!!!!!!! main loop on fields
		df.fields.getList().each{ fieldName ->
			Map mapFieldValue = df.fields.get(fieldName)
			if(isFieldExistInDb(mapFieldValue)){

				def fldValue = mapFieldValue.defaultValue
				def fldDefaultValue = mapFieldValue.defaultValue
				jsonMapDf.put(fieldName, fldValue)
				jsonDefaults.put(fieldName, fldDefaultValue)

				//Additional data:
				//Map jsonAdditionalDataField = getAdditionalData(fieldName, jsonMapDf) //TODO: load defaultAdditional data!!!!
				//if(jsonAdditionalDataField){
				//	jsonAdditionalData.put(fieldName.replace(Dataframe.DOT,Dataframe.DASH), jsonAdditionalDataField)
				//}

			}
		}//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		//}
		jsonMapDf.put("keys", getNamedParameters())
		jsonMapDf.put("additionalData", jsonAdditionalData)

		//Replace default add datan with relev values
		jsonDefaults.put("keys", getNamedParameters())
		jsonDefaults.put("additionalData", jsonAdditionalData)

		//These are for refresh parent dataframe, if provided (aka requested):
		def parentDataframe = requestParams.get('parentDataframe')
		def parentNode = requestParams.get('parentNode')
		def parentLevel = requestParams.get('level')
		def parentFieldName = requestParams.get('parentFieldName')
		def parentNodeLevel = getLevelFromUiIdConstruct(parentNode)

		log.debug("\n *******   Request Params: when retrieved \n" + reqParamPrintout(requestParams) + "\n ***************\n")

		//TODO: 1 All this should be coming from additionalParameters jsonMapDf[additionalParams]
		//df.dataFrameParamsToRefresh = [parentNode:parentNode, parentDataframe:parentDataframe, level:parentLevel, parentFieldName:parentFieldName]
		def html = df.getHtml()
		jsonRet.put("html", html)
		jsonRet.put("parentDataframe", parentDataframe)
		jsonRet.put("parentNode", parentNode)
		jsonRet.put("level", parentLevel)
		jsonRet.put("parentFieldName", parentFieldName)
		//TODO: 1

		jsonRet.put("data", jsonMapDf)
		jsonRet.put("default",jsonDefaults)
		jsonRet.put("operation","R");
		jsonRet.put("dataframe",df.dataframeName);

		String domainAlias = df.parsedHql?.hqlDomains?.keySet()?.asList()?.get(0)

		jsonRet.put("dataFrameParamsToRefresh",
				[   'parentNode':parentNode,
					'parentNodeId':getIdFromUiIdConstruct(parentNode),
					'parentNodeLevel':getLevelFromUiIdConstruct(parentNode),
					'parentDataframe':parentDataframe,
					'level':parentLevel,
					'parentFieldName': Dataframe.buildFullFieldName_(df.dataframeName, domainAlias, parentFieldName)
				])

		return jsonRet
	}

	/**   CRUD - RETRIEVE
	 * Using HQL, and parameters, read the data
	 * and return the map which can be converted to json on controller.
	 */
	public def createAndGetJson(){
		isDefault = true
		return retrieveAndGetJson()
	}

	public def retrieveAndGetJson(){
		if (!isDefault){
			//retrieving from DB!
			populateInstance()
		}
		Map jsonRet = [:];
		Map jsonMapDf = [:];
		Map jsonAdditionalData = [:];
		Map jsonDefaults = [:];
		Map additionalDataRequestParamMap = [:];

		additionalDataRequestParamMap.putAll(this.requestParams)
		additionalDataRequestParamMap.putAll(this.resultMap)

		if( isGood() || isDefault){
			//!!!!!!!!!!!!!!!!!!!!!!!!! main loop on fields
			df.fields.getList().each{ fieldName ->
				Map mapFieldValue = df.fields.get(fieldName)
				if(isFieldExistInDb(mapFieldValue)){
					if (!isDefault){
						def fldValue = getFieldValue(mapFieldValue)
//						jsonMapDf.put(fieldName, fldValue)
						jsonMapDf.put(fieldName.replace(Dataframe.DOT,DataframeVue.UNDERSCORE), fldValue) //Chnaged Dot to Underscore for vue
					}
					def fldDefaultValue = mapFieldValue.defaultValue
//					jsonDefaults.put(fieldName, fldDefaultValue)
					jsonDefaults.put(fieldName.replace(Dataframe.DOT,DataframeVue.UNDERSCORE), fldDefaultValue) //Chnaged Dot to Underscore for vue
					//Additional data:
				}

				//Try to load Additional Data:
				Map jsonAdditionalDataField = getAdditionalData(fieldName, additionalDataRequestParamMap);
				if(jsonAdditionalDataField){
//					if (mapFieldValue.externalDomainField && !isDefault){
//						jsonMapDf.put(fieldName, jsonAdditionalDataField)
//					}
//					jsonAdditionalData.put(fieldName.replace(Dataframe.DOT,Dataframe.DASH), jsonAdditionalDataField);
					jsonAdditionalData.put(fieldName.replace(Dataframe.DOT,DataframeVue.UNDERSCORE), jsonAdditionalDataField); // Changed Dash to underscore for vue
				}

			}//!!!!!

		}
		addKeyDataForNamedParameters(jsonMapDf) // adding key- fields for vue js
		jsonMapDf.put("keys", getNamedParameters())
		jsonMapDf.put("additionalData", jsonAdditionalData)

		//Replace default add datan with relev values
		jsonDefaults.put("keys", getNamedParameters())
		jsonDefaults.put("additionalData", jsonAdditionalData)

		//These are for refresh parent dataframe, if provided (aka requested):
		def parentDataframe = requestParams.get('parentDataframe')
		def parentNode = requestParams.get('parentNode')
		def parentLevel = requestParams.get('level')
		def parentFieldName = requestParams.get('parentFieldName')
		def parentNodeLevel = getLevelFromUiIdConstruct(parentNode)

		log.debug("\n *******   Request Params: when retrieved \n" + reqParamPrintout(requestParams) + "\n ***************\n")

		//TODO: 1 All this should be coming from additionalParameters jsonMapDf[additionalParams]
		//df.dataFrameParamsToRefresh = [parentNode:parentNode, parentDataframe:parentDataframe, level:parentLevel, parentFieldName:parentFieldName]
		def html = df.getHtml()
		jsonRet.put("html", html)
		jsonRet.put("parentDataframe", parentDataframe)
		jsonRet.put("parentNode", parentNode)
		jsonRet.put("level", parentLevel)
		jsonRet.put("parentFieldName", parentFieldName)
		jsonRet.put("dataframe",df.dataframeName);
		//TODO: 1
		if (isDefault){
			jsonRet.put("data",jsonDefaults)
		}else {
			jsonRet.put("data", jsonMapDf)
		}
		jsonRet.put("default",jsonDefaults)
		jsonRet.put("operation","R");

		String domainAlias = df.hql?df.parsedHql?.hqlDomains?.keySet()?.asList()?.get(0):"";

		jsonRet.put("dataFrameParamsToRefresh",
				[   'parentNode':parentNode,
					'parentNodeId':getIdFromUiIdConstruct(parentNode),
					'parentNodeLevel':getLevelFromUiIdConstruct(parentNode),
					'parentDataframe':parentDataframe,
					'level':parentLevel,
					'parentFieldName': Dataframe.buildFullFieldName_(df.dataframeName, domainAlias, parentFieldName)
				])

		return jsonRet
	}

	private void addKeyDataForNamedParameters(jsonMapDf){
		def namedParameters = getNamedParameters()
		namedParameters.each {key, value ->
			if(df.getNamedParameter(key)){
				def namedParam = df.getNamedParameter(key)
				String refDomainAlias =  namedParam[0];
				String refFieldName =  namedParam[1];
				String keyNamedParam = DataframeVue.buildFullFieldNameKeyParamWithDot(df, refDomainAlias, refFieldName, key);
				jsonMapDf.put(keyNamedParam, value)
			}

		}
	}

	public static String reqParamPrintout(Map requestParams) {
		StringBuilder sb_ = new StringBuilder();
		requestParams.each{ k,v ->
			sb_.append("${k} = ${v} \n");
		}
		return sb_.toString();
	}

	/**
	 * CRUD - CREATE or UPDATE
	 * @param params
	 * @return
	 * @throws DataManipulationException
	 */

	@Transactional
	public List commit() throws DataManipulationException {
		for ( Map.Entry<String, Object> domainInstanceName : savedDomainsMap.entrySet()) {
			def doaminToSave = domainInstanceName.value
			doaminToSave.save()
		}
	}


	public List save() throws DataManipulationException{
		return save(true)
	}

	public List save(boolean doCommit) throws DataManipulationException{
		List doaminInstancesForSave = [];

		//Run save for embedded DFs first:
		for( Map.Entry<String, DataframeVue> emdDfEntry: df.getEmbeddedDataframe()){
			DataframeVue embDf = emdDfEntry.getValue();
			def key = emdDfEntry.getKey()
			def dfInstance = new DataframeInstance(embDf, requestParams)
			def res = dfInstance.save();
			embeddedDomainInstance.add(res)
			// =====   Assign new key values to key fields here if insert happened
			embDf.parsedHql.getNamedParameters().each{paramName, paramData ->
				String domainAlias = paramData[0]
				String dbFieldname = paramData[1];
				def domainMap = embDf.parsedHql.hqlDomains.get(domainAlias);
				def domainShortName = domainMap.key;
//					String keyFldNameToPopulateWithValue = Dataframe.buildFullFieldNameKeyParam(embDf, domainAlias, dbFieldname, paramName);
				String keyFldNameToPopulateWithValue = DataframeVue.buildFullFieldNameKeyParam(embDf, domainAlias, dbFieldname, paramName);//Changed for vue
				String keyCurrentValue = requestParams.get(keyFldNameToPopulateWithValue);
				//Finding new Value:
				res.each{ domainInst ->
					String domainShortNameFromInstance = getMyDomaunShortName(domainInst);
					if(domainShortNameFromInstance.equals(domainShortName)){
						String newValue = String.valueOf(domainInst."${dbFieldname}");
						if(!newValue.equals(keyCurrentValue)){
							requestParams.put(keyFldNameToPopulateWithValue, newValue);
						}
					}
				}
			}
			//TODO: bellow code may me needed in future for onetomany and bidirectional relational domain save
//				String fieldName = embDf.getFieldNameSplitDot(key);
//				def prop = df.getPersistentPropertyByName(fieldName)
//                if (df.metaFieldService.isOneToMany(prop) && df.metaFieldService.isBidirectional(prop)){
//					hasManyDomainsList.add(embDf)
//				}else {
//				}

		}

		df.writableDomains.each{ domainName, domain ->

			PersistentEntity queryDomain = null;
			DomainClassInfo parsedDomain = domain.get("parsedDomain");
			String domainAlias =  parsedDomain.getDomainAlias();

			if(parsedDomain != null){
				queryDomain =  parsedDomain.getValue();
			}else{
				throw new DataframeException("Cannot find domain to save a record for Datafame ${df.dataframeName}");
			}

			//String doaminFullName = df.dataframeName + Dataframe.DASH + domainAlias;
			String doaminFullName = domainAlias;
			if(!(savedDomainsMap.containsKey(doaminFullName))){
				//2. Get domain Key fields to decide if this insert or update
				Map keysNamesAndValue = [:];
				boolean isInsert  = isInsertDomainInstance(keysNamesAndValue, domain);
				def domainInstance = null;
				def historyDomainInstance = null
				if(isInsert){ //Create new domain Instance
					insertOccured = true;
					domainInstance = createNewDomainInstance(keysNamesAndValue, queryDomain);
				}else{ //Retrieve domain Instance
					domainInstance = retrieveDomainInstanceForUpdate(keysNamesAndValue, queryDomain);
					historyDomainInstance = createNewDomainInstanceForHistoryDomain(keysNamesAndValue, queryDomain)
				}

				if(applyNewValuesToDomainInstanceAndSave(domainInstance, requestParams, keysNamesAndValue, domain, historyDomainInstance, doCommit)){
					doaminInstancesForSave.add(domainInstance);
					savedDomainsMap.put(doaminFullName, domainInstance);
				}
			}
		}//End of df.writableDomains.each{

		return doaminInstancesForSave;

		//TODO: bellow code may me needed in future for one-to-many and bidirectional relational domain save
//		if (hasManyDomainsList.size() > 0){
//			hasManyDomainsList.each { embdfr ->
//				String domainAlias = embdfr.hql?embdfr.parsedHql?.hqlDomains?.keySet()?.asList()?.get(0):""
//				def parentFieldName = df.hql?df.parsedHql?.hqlDomains?.keySet()?.asList()?.get(0):""
//				def keyFldNameToPopulateWithValue = Dataframe.buildFullFieldName_(embdfr.dataframeName, domainAlias, parentFieldName)
//				String keyCurrentValue = requestParams.get(keyFldNameToPopulateWithValue)
//				def parentNameParams = df.parsedHql.getNamedParameters().values().toArray()[0]
//				def dbFieldname = parentNameParams[1]
//				doaminInstancesForSave.each{ domainInst ->
//					String newValue = String.valueOf(domainInst."${dbFieldname}")
//					if(!newValue.equals(keyCurrentValue)){
//						requestParams.put(keyFldNameToPopulateWithValue, newValue)
//					}
//				}
//				def dfInstance1 = new DataframeInstance(embdfr, requestParams)
//				def res = dfInstance1.save()
//
//			}
//		}
	}



	public boolean isInsertOccured() {
		return insertOccured;
	}


	public def getSavedDomainsMap(){
		return savedDomainsMap;
	}


	private def createNewDomainInstance(Map keysNamesAndValue, PersistentEntity domain){
		def newInstance =  domain.newInstance()
		//Populate the keys TODO: do we really need it?

		return newInstance
	}
	private def createNewDomainInstanceForHistoryDomain(Map keysNamesAndValue, PersistentEntity domain){
		Class domainName = constructHistoryDomainName(domain)
		def newInstance = null
		if(domainName){
			newInstance =  domainName.newInstance()
		}

		return newInstance
	}

	private def constructHistoryDomainName(PersistentEntity domain){
		String parentClassNameAsString = domain.javaClass.getSimpleName()
		String constructedHistoryClassName = prefixForHistoryTable+parentClassNameAsString
		Class domainName = Holders.grailsApplication.domainClasses.find { it.clazz.simpleName == constructedHistoryClassName }?.clazz
		def originalClass = Holders.grailsApplication.getClassForName(constructedHistoryClassName)
//        def grailsApplication = ApplicationArtefactHandler.application
//        def domainDescriptor = Holders.grailsApplication.getArtefact(DomainClassArtefactHandler.TYPE, constructedHistoryClassName)
//        def d = new DefaultGrailsDomainClass(domainName)
		/* println "@!@&@&@&@&&@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&@"
 //        originalClass.domainClass.getProperties().each(){
             println "${it.name} if of type ${it.type}"
         }*/
		return domainName
	}

	//If it is by id, it will be only 1 parameter and it will be long!
	private Long getIdValue(Map keysNamesAndValue){
		if(keysNamesAndValue.size() == 1){
			Map.Entry entry = keysNamesAndValue.entrySet().iterator().next();
			try{
				return Long.valueOf(entry.value);
			}catch(Exception e){
				return null;
			}

		}
		return null;
	}

	public def retrieveDomainInstanceForUpdate(Map keysNamesAndValue, PersistentEntity domainClass){
		def currentInstance = null

		Long idValue = getIdValue(keysNamesAndValue)

		if(idValue != null && idValue > 0){ //Retrieve by ID
			currentInstance = domainClass.newInstance().get(idValue)
		}else{ //It should be GrailsDomainClass instead of PersistentEntity for the following part to work.
			String domainFullClassName = domainClass.getName()
			int indexOfLastDot = domainFullClassName.lastIndexOf(".")
			String domainClassName = domainFullClassName.substring(indexOfLastDot+1, domainFullClassName.length())
			StringBuilder wherepart = new StringBuilder()
			Map quryParamsMap = [:]
			int i = 1
			keysNamesAndValue.each{name, value ->
				wherepart.append(" m.${name}=:${name} ")
				if(i < keysNamesAndValue.size()){
					wherepart.append(" and ")
				}
				quryParamsMap.put(name, value)
			}
//			try {
				currentInstance = domainClass.find("from $domainClassName as m where $wherepart", quryParamsMap)
//			}
//			catch (Exception ee){
//				log.error("Problem finding domain class $domainClassName")
//			}
		}
		return currentInstance
	}

	private boolean isInsertDomainInstance(Map keysNamesAndValue, Map domain) {
		Map inputData = requestParams
		Set keys = domain.get("keys")
		String keyFieldName = constructKeyFieldName(domain, inputData)
		boolean isInsert = false
		if(requestParams.containsKey(keyFieldName)){
			String keyFyullParamValue = requestParams.get(keyFieldName);
			keysNamesAndValue.put(keyFieldName, keyFyullParamValue);
			if(StringUtils.isEmpty(keyFyullParamValue) || "[New]".equalsIgnoreCase(keyFyullParamValue) || "undefined".equals(keyFyullParamValue)){
				requestParams.put(keyFieldName, null);
				isInsert = true;
			}
		}
		/*keys.each {keyName ->
			//String buildParamName = Dataframe.buildKeyFieldParam(df.dataframeName, keyName)
			def namedParam = df.getNamedParameter(keyName);
			String refDomainAlias =  namedParam[0];
			String refFieldName =  namedParam[1];
			def pkField = getPkField();

			if(pkField.equals(refFieldName)){
				keyNamedParam = Dataframe.buildFullFieldNameKeyParam(df, refDomainAlias, refFieldName, keyName); //This is child key param name
			}else {
				String keyFieldName = dataframeName+"-"+domainAlias + "-" + pkField
			}
			String keyRefParam = Dataframe.buildFullFieldNameRefParam(df, keyName);//This is a full name, suppose to be in each dataframe, parent or child

			String parentKeyParamValue = null;
			String keyFyullParamValue = null;

			if(requestParams.containsKey(keyNamedParam)){
				keyFyullParamValue = requestParams.get(keyNamedParam);
				keysNamesAndValue.put(keyNamedParam, keyFyullParamValue);
				if(StringUtils.isEmpty(keyFyullParamValue) || "[New]".equalsIgnoreCase(keyFyullParamValue) || "undefined".equals(keyFyullParamValue)){
					requestParams.put(keyNamedParam, null);
					isInsert = true;
				}
			}
		}*/

		isInsert = isInsert || (keysNamesAndValue.size() < keys.size())
		return isInsert
	}

	private Map<String, String> getParentRefNames(DataframeVue refDataframe) {
		Map domain = refDataframe.parsedHql.getHqlDomains();
		Set keys = domain.get("keys")
		Map<String, String> joinFieldsMapping = new HashMap<String, String>();
		keys.each {keyName ->
			def namedParam = refDataframe.getNamedParameter(keyName);
			String refDomainAlias =  namedParam[0];
			String refFieldName =  namedParam[1];
		}
		return joinFieldsMapping;
	}

	private def getPkField(){
		def pkField = df.fieldsMetadata.getAt("id")
		if(pkField && pkField.isPk()){
			pkField = pkField.name
		}else {
			df.fieldsMetadata.each {
				if(it.getValue().pk){
					pkField = it.getValue().name
				}
			}
		}
		return pkField?:""

	}

//	Refactored for Vue. The original method is commented
	public Map getKeysAndValues(Map domain, Map inputData) {
//		Map inputData = requestParams
		String keyFieldName = constructKeyFieldName(domain, inputData)

		Map keysNamesAndValue = [:]
//		keyFieldName = keyFieldName.replace("-",".")
		if(inputData.containsKey(keyFieldName)){
			String paramValue = inputData.get(keyFieldName)
			keysNamesAndValue.put(keyFieldName, paramValue)
		}else{
			getKeysNamesAndValueForPk(keysNamesAndValue, domain, inputData)
		}
		return keysNamesAndValue
	}

	private String constructKeyFieldName(def domain, Map inputData){
		Set keys = domain.get("keys")
		String keyFieldName = ""
		String dataframeName = df.dataframeName
		String domainAlias = domain.get("domainAlias")
		String pkField = getPkField();
		if(keys.size() == 0){
			keyFieldName = dataframeName+df.UNDERSCORE+domainAlias + df.UNDERSCORE + pkField
		}
		else {
			keys.each {keyName ->
				def namedParam = df.getNamedParameter(keyName);
				String refDomainAlias =  namedParam[0];
				String refFieldName =  namedParam[1];
				if(domainAlias == refDomainAlias){
					if(domainAlias.equals(refDomainAlias) && pkField.equals(refFieldName)){
						keyFieldName = Dataframe.buildFullFieldNameKeyParam(df, refDomainAlias, refFieldName, keyName); //This is child key param name
					}else {
						keyFieldName = dataframeName+df.UNDERSCORE+domainAlias +df.UNDERSCORE + pkField
					}
				}
			}
		}
		return keyFieldName

	}

	/*private Map getKeysAndValues(Map domain) {
		Map keysNamesAndValue = [:]
		Set keys = domain.get("keys")
		keys?.each {keyName ->
			String buildParamName = Dataframe.buildFullFieldNameKeyParam(df, keyName);
			if(requestParams.containsKey(buildParamName)){
				String paramValue = requestParams.get(buildParamName)
				keysNamesAndValue.put(keyName, paramValue)
			}
		}
		return keysNamesAndValue
	}*/

	private getNewValueForParameter(String parameterName, Map allParams) {
		if(!df.getParsedHql().namedParameters.containsKey(parameterName)){
			throw new DataManipulationException("Parameter required: ${parameterName}, but not found in parameter map");
		}

		String[] namingParamValue = (String[])df.getParsedHql().namedParameters.get(parameterName)

		String parFullName = df.getFieldId(namingParamValue[0], namingParamValue[1])

		if(!allParams.containsKey(parFullName)){
			throw new DataManipulationException("Cannot identify the record for update: dataframe = ${df.dataframeName} with parameter ${parFullName}");
		}

		def paramValue = allParams.get(parFullName)
		return paramValue
	}

	public boolean isParameterRelatedToDomain(HibernatePersistentEntity doaminClass, String myDomainAlias, String paramName){

		if(paramName.startsWith("parent")){
			return false;
		}
		String paramNameTocheck = paramName;
		String fieldName = df.getFieldNameFromFieldId(paramNameTocheck);
		String domainAliasFromParam = df.getFieldDoaminFromFieldId(paramNameTocheck);

		if(myDomainAlias.indexOf(domainAliasFromParam) > -1){

//			boolean hasPersistantProperty = doaminClass.hasPersistentProperty(fieldName);
			boolean hasPersistantProperty = hasPersistentProperty(fieldName, doaminClass)
			//todo: remove ispers as I thought it is not necessary, so check and add accordingly as required
//			boolean ispers = hasPersistantProperty?doaminClass.getPropertyByName(fieldName)?.isPersistent():false;
//			if(hasPersistantProperty || ispers){
			if(hasPersistantProperty){
				return true;
			}
		}
		return false
	}

	private static getMyDomaunShortName(def domainInstance){
		def clazz = domainInstance.class;
//		def domainClass = new DefaultGrailsDomainClass(clazz);
//		return domainClass.shortName;
		return ClassUtils.getShortClassName(clazz);
	}

	public boolean applyNewValuesToDomainInstanceAndSave(def domainInstance, Map requestParams, Map keysNamesAndValue, Map domainMap, def historyDomainInstance, boolean doCommit){

		boolean isChanged = false
		boolean isValidate = true
//		def clazz = domainInstance.class
//		def domainClass = new DefaultGrailsDomainClass(clazz)
		DomainClassInfo parsedDomain = domainMap.get("parsedDomain")
		HibernatePersistentEntity domainClass = parsedDomain.getValue();
//		def domainClass = new HibernatePersistentEntity(clazz, Holders.grailsApplication.mappingContext)
//		String myDomainName = domainClass.shortName
		String myDomainName = getMyDomaunShortName(domainInstance)

		String myDomainAlias = parsedDomain.getDomainAlias()

		if( StringUtils.isEmpty(myDomainAlias)){
			throw new DataframeException(" Trying to save Instance of ${myDomainName} for dataframe ${df.dtataframename}, that does not suppose to have this domain for saving!");
		}

		//*************   MAIN LOOP on PARAMETERS!!!! **************
		requestParams.each() { paramName, paramValue ->

			String fieldName = df.getFieldNameFromFieldId(paramName);
//            Checking if historyDomainInstance is null and setting the values of parentId and last updated timestamp of parent table
			if(historyDomainInstance){
				if(fieldName == "id"){
					historyDomainInstance. parentId = domainInstance."${fieldName}"
					if(hasPersistentProperty("lastUpdated", domainClass)){
						historyDomainInstance.lastUpdated = domainInstance.lastUpdated
					}
				}
			}

			boolean isParameterRelatedToDoamin = isParameterRelatedToDomain(domainClass, myDomainAlias, paramName)
			if(isParameterRelatedToDoamin){
				def prop = domainClass.getPropertyByName(fieldName)
				def oldfldVal = domainInstance."${fieldName}";
				if(historyDomainInstance){
					if(!(df.metaFieldService.isAssociation(prop))) {
						historyDomainInstance."${fieldName}" = oldfldVal //Saving the old data to history table
					}else{
						if((df.metaFieldService.isManyToMany(prop)) || (df.metaFieldService.isOneToMany(prop))){
							def refDomainClass = prop.associatedEntity.getJavaClass()
							def savedInstance = saveHasManyAssociation(paramValue,refDomainClass,fieldName,historyDomainInstance)
							historyDomainInstance = savedInstance
						}
					}
					//                Saving the historyDomainInstance
					historyDomainInstance.save(failOnError: true)
				}
				def fldVal = null
				if(!(df.metaFieldService.isAssociation(prop))){

					fldVal = DbResult.getTypeCastValue( paramValue, prop)
					if(oldfldVal == null || !oldfldVal.equals(fldVal)){
						if(fldVal != null){
							isValidate = fieldValidate(fieldName,fldVal?.toString())
							if (!isValidate){
								//todo: check validation for field latest grails version 3.3.0
//								throw new DataframeException("Field validation Failed")
							}
							domainInstance."${fieldName}" = fldVal
						}
						isChanged = true
					}
				}else {

					if(paramName.startsWith("ref")){ //I'm looking into
						paramValue = getReferencedFieldValue(requestParams, paramName);
					}

					try{
//						if(StringUtils.isEmpty(paramValue)){
//							fldVal = 0L;
//						}else{
						if((df.metaFieldService.isManyToMany(prop)) || (df.metaFieldService.isOneToMany(prop))){
							def refDomainClass = prop.associatedEntity.getJavaClass()
							def savedInstance = saveHasManyAssociation(paramValue,refDomainClass,fieldName,domainInstance)
							domainInstance = savedInstance
							isChanged = true

						}else{


							if(StringUtils.isEmpty(paramValue)){
								fldVal = 0L;
							}else {
								fldVal = Long.valueOf(paramValue)//df.getTypeCastValue( paramValue, prop)
							}
							if(oldfldVal == null || !oldfldVal.id.equals(fldVal)){
								def nestedInstance = prop.getType().get(Long.valueOf(fldVal))
								domainInstance."${fieldName}" = nestedInstance
								isChanged = true
							}
						}

//						}

					}catch(Exception e){
						log.debug"Debugging: ${e.message}", e
						throw new DataframeException(" Converting parameter value of ${paramValue} to Long was failed");
					}

//					boolean qq = (oldfldVal == null || !oldfldVal.id.equals(fldVal) && !(prop.isManyToMany() || prop.isOneToMany()));


				}
			}

		} //requestParams.each() { fieldName, fieldValue ->
		//		applying Insert Fields
		if(isInsertOccured()){
			df.insertFields.each{fldName,fldValue->
				def fldList = fldName.toString().split('\\.',-1)
				def domain = fldList.getAt(0)
				def field =  fldList.getAt(1)
				def valueToSave
				if(fldValue.indexOf("session_") > -1){
					valueToSave = getSession().getAttribute(fldValue.toString().substring(fldValue.indexOf("_") + 1))?:(long)Holders.grailsApplication.config.guestUserId

				}else{
					valueToSave = fldValue
				}
				try{
//					if(domainClass.hasPersistentProperty(field)){
					if(hasPersistentProperty(field, domainClass)){
						def propr = domainClass.getPropertyByName(field)
						if(!(df.metaFieldService.isAssociation(propr))){
							def fldVal = DbResult.getTypeCastValue( valueToSave, propr)
							domainInstance."$field" = fldVal
						}else{
//							def clz = propr.referencedPropertyType
							def clz = propr.associatedEntity.getJavaClass()
							def associationClass = clz.findById(Long.valueOf(valueToSave))
							if(associationClass){
								if(propr.getType()== Set){
									field = field.toLowerCase().capitalize()
									domainInstance."addTo${field}"(associationClass)
								}else{
									def fldVal = propr.getType().get(Long.valueOf(valueToSave))
									domainInstance."$field" = fldVal
								}
							}else{
								log.error("Error: No field found for Id: "+valueToSave+" in Domain: "+domainClass.getShortName())
							}
						}

					}else{
						log.error("Error: Field "+field+" not available for domain: "+domainClass.getShortName())
					}
				}catch(Exception e){
					log.error("Error: Saving Insert Data, Failed with exception: "+e)
				}
			}
		}

		if(isChanged){
			try{
//				historyDomainInstance.close()
				if(doCommit) {
					domainInstance.save(flush: true)
				}
				return true
			}catch(Throwable ee){
				println "error----------"+ee
				log.error("Error saving " + domainInstance.toString() + "for params: " + keysNamesAndValue)
				return false
			}
		}else{
			log.warn("Somebody hit the save button but never changed a thing, so we will not invoke save ! ")
			return true
		}

		return false
	}//End of method applyNewValuesToDomainInstance

	private static Boolean hasPersistentProperty(String propertyName, HibernatePersistentEntity domainClass){
		/**commented code put here because may be it will required */
//		for (String propertyNames:domainClass.persistentPropertyNames){
//			if (propertyNames.equals(propertyName)) return true
//		}
		for (PersistentProperty persistentProperty:domainClass.persistentProperties){
			String propertyNames = persistentProperty.name
			if (propertyNames.equals(propertyName)) return true
		}
		return false
	}

//	saves onetomany and manytomany
	private def saveHasManyAssociation(paramValue,refDomainClass,fieldName,domainInstance){
		def formattedJSON = "["+paramValue+"]"
		JSONArray jsonArray = new JSONArray(formattedJSON);
		domainInstance?.(StringUtils.uncapitalize(fieldName))?.clear()
		jsonArray.each{val ->
			val.each{
				def refDomainObj  = refDomainClass.get(Long.valueOf(it.value))
				domainInstance."addTo${fieldName.toLowerCase().capitalize()}"(refDomainObj)
			}

		}
		return domainInstance
	}

	private HttpSession getSession(){
		return RequestContextHolder.requestAttributes?.getSession()
	}

	/**
	 * CRUD - Logical Delete
	 * Deleting a record by updating ExpDate of it to now (Expire)
	 * The record is not physically deleted from the database
	 * @return instances that successfully expired are returned
	 */
	public List deleteExpire() throws DataManipulationException{
		def expiredInstances =[]
		df.writableDomains.each{ domainName, domain ->
			//1. Get domain Data
			PersistentEntity queryDomain = null
			DomainClassInfo parsedDomain = domain.get("parsedDomain");
			if(parsedDomain != null){
				queryDomain = parsedDomain.getValue()
			}else{
				throw new DataframeException("Cannot find domain to save a record for Datafame ${df.dataframeName}")
			}

//				if(queryDomain.hasPersistentProperty("expDate")){
			if(hasPersistentProperty("expDate", queryDomain)){
				//2. Get domain Key fields to decide if this insert or update
				Map keysNamesAndValue = getKeysAndValues(domain, requestParams)
				def domainInstance = null
				domainInstance = retrieveDomainInstanceForUpdate(keysNamesAndValue, queryDomain)

				domainInstance.expDate = new Date();
				domainInstance.save(flush:true)
				expiredInstances.add(domainInstance.id)
			}else{
				log.error("Cannot expire record of a Domain that does not have expDate field! ${domainName}")
			}


		}//End of df.writableDomains.each{
		return expiredInstances
	}


	/**
	 *
	 * This method uses the parameter and find the corresponding
	 * object as per the hql and delete that object.
	 * Unlike deleteExpire it is physically deleting the rrecord from the database
	 * @return returns the parameters that used to find deleted record
	 * (in most cases it will be IF)
	 */
	def delete(){
		Map<String, String> namedParmeter = [:]
		String hql = df.hql
		Query query =  sessionHibernate.createQuery(hql);
		System.out.println("all params for delete:" + requestParams);
		for (String parameter : query.getNamedParameters()) {
			namedParmeter.put(parameter, requestParams.get(parameter))
			requestParams.remove(parameter)
		}

		df.parsedHql.hqlDomains.each(){domainAlias, domain ->
			def searchInstance = domain.newInstance(namedParmeter)
			def instance = domain.find(searchInstance)
			if(instance != null){
				instance.delete(flush: true)
				return namedParmeter
			}

		}

		return null
	}

	/**
	 *
	 * This method uses the parameter and make the server side validation using the given regex constraint.
	 * Return true if all validation passed and return false if any validation failed.
	 */
	private boolean validationCheck(){
		df.constraintsMetadata.each(){name, regex->
			regex = regex.replace("\$","")
			def value = requestParams.get('name')
			if( value != regex )
				return false
		}
		return true
	}

	public def reloadField(String fieldnameToReload, Map inputData){

		Widget t = df.getWidget(fieldnameToReload);

		def result  = df.getWidget(fieldnameToReload).loadAdditionalData(df, fieldnameToReload, inputData, sessionHibernate)

		return result
	}

	/**
	 *
	 * @param level
	 * @param elementId
	 * @return
	 */
	static String getUiIdConstruct(def level, def elementId){
		return "$level${Dataframe.DASH}$elementId";
	}

	/**
	 *
	 * @param uiId
	 * @return
	 */
	static long getIdFromUiIdConstruct(String uiId){
		if(!StringUtils.isEmpty(uiId)){
			String[] arr = uiId.split(Dataframe.DASH)
			if(arr!=null && arr.size() == 2){
				return  Long.valueOf(arr[1]);
			}else if(arr!=null && arr.size() == 1){
				return  Long.valueOf(arr[0]);
			}
		}
		return 0
	}

	/**
	 *
	 * @param uiId
	 * @return
	 */
	static int getLevelFromUiIdConstruct(def uiId){
		if(!StringUtils.isEmpty(uiId)){
			String[] arr = uiId.split(Dataframe.DASH)
			if(arr!=null && arr.size() == 2){
				return  Integer.valueOf(arr[0])
			}else if(arr!=null && arr.size() == 1){
				return  0
			}
		}
		return 0
	}


	public static String getReferencedFieldName(Map requestedParams, String refParamName) {
		if(requestedParams.containsKey(refParamName)){
			return  requestedParams.get(refParamName);
		}
		return "";
	}

	public static String getReferencedFieldValue(Map requestedParams, String refParamName) {
		String keyFldName = getReferencedFieldName(requestedParams, refParamName);
		if(!StringUtils.isEmpty(keyFldName) &&  requestedParams.containsKey(keyFldName)){
			return requestedParams.get(keyFldName);
		}
		return "";
	}


	public static putReferencedFieldValue(Map requestedParams, String refParamName, String value) {
		String keyFldName = getReferencedFieldName(requestedParams, refParamName);
		if(!StringUtils.isEmpty(keyFldName)){
			requestedParams.put(keyFldName, value);
		}
	}

	public static Map getSessionAtributes(javax.servlet.http.HttpSession httpSession) {
		def sessionAttributes = [:]
		for( String attrName: httpSession.getAttributeNames()){
			sessionAttributes.put(attrName, httpSession.getAttribute(attrName))
		}
		return sessionAttributes
	}

	public Map getFieldValuesAsKeyValueMap(){
		return resultMap;
	}

	private Map calculateFieldValuesAsKeyValueMap(){
		resultMap = [:]
		if(this.record){
			List lst = df.fields.getDbList()
			int recSize = record.size()
			for(int j = 0; j < recSize; j++){
				def fldName = lst.get(j)
				def val = this.record.getAt(j);
				resultMap.put(fldName, val);
			}
		}
	}

	public boolean fieldValidate(String fieldName, String fldVal){
		boolean validateFlag = true
		df?.fields?.getList()?.each {it ->
			Map field = df?.fields?.dataMap?.get(it)
			if (field?.name?.equals(fieldName)){
				if (field.containsKey("regex")){
					String regex = field.get("regex")?.toString()?.replaceAll("/","")
					if (!fldVal.find(regex)){
						validateFlag = false
					}
				}
			}
		}
		return validateFlag
	}

	public def getQueryDomainInstance(Map domain){
		PersistentEntity queryDomain = getPersistentEntityFromDomainMap(domain)
		Map keyNameValue = [:]
		isInsertDomainInstance(keyNameValue, domain)
		return retrieveDomainInstanceForUpdate(keyNameValue, queryDomain);
	}

	public static PersistentEntity getPersistentEntityFromDomainMap(Map domain){
		PersistentEntity queryDomain = null;
		DomainClassInfo parsedDomain = domain.get("parsedDomain")
		queryDomain = parsedDomain.getValue();
		return queryDomain
	}

	public def getKeysNamesAndValueForPk(keysNamesAndValue, domain, Map inputData) {
		Set keys = domain.get("keys")
		keys.each { keyName ->
			def namedParam = getNamedParameters()
			String keyFyullParamValue = null;
			if (inputData.containsKey(keyName) && namedParam.containsKey(keyName)) {
				keyFyullParamValue = inputData.get(keyName);
				keysNamesAndValue.put(keyName, keyFyullParamValue);
			}else if(keyName.indexOf("session_") > -1){
				def sessionParamValue = DataframeInstance.getSessionParamValue(keyName, namedParam)
				keysNamesAndValue.put(keyName, sessionParamValue)
			}
		}
	}

	public Map getDomainInstanceInfo(fieldnameToReload, inputData){
		String domainAlias= Dataframe.getDataFrameDomainAlias(fieldnameToReload)
		def queryDomain = null
		def domainInstance = null
		String simpleFieldName = ""
		boolean isParamRelatedToDomain = false
		if (domainAlias && df.writableDomains) {
			Map domain = df.writableDomains.get(domainAlias)
			queryDomain = getPersistentEntityFromDomainMap(domain)
			Map keysNamesAndValue = [:];
			getKeysNamesAndValueForPk(keysNamesAndValue, domain, inputData);
			if (!keysNamesAndValue.isEmpty()){
				domainInstance = retrieveDomainInstanceForUpdate(keysNamesAndValue, queryDomain);
			}
			simpleFieldName = Dataframe.extractSimpleFieldName(df.dataframeName,fieldnameToReload,domainAlias)
			isParamRelatedToDomain = isParameterRelatedToDomain(queryDomain, domainAlias, simpleFieldName)
			return [queryDomain:queryDomain,domainInstance:domainInstance,
					simpleFieldName:simpleFieldName,
					isParameterRelatedToDomain:isParamRelatedToDomain]
		}
	}

//	public def getKeysNamesAndValueForPk(keysNamesAndValue, domain, Map inputData) {
//
//		String keyFieldName = constructKeyFieldName(domain, inputData)
//		String formatKeyFieldName = keyFieldName.replace(df.DASH, df.DOT)
// 		if(inputData.containsKey(formatKeyFieldName)){
//			String paramValue = inputData.get(formatKeyFieldName)
//			keysNamesAndValue.put(keyFieldName, paramValue)
//		}
//	}

}