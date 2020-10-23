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

import com.elintegro.erf.dataframe.vue.DataframeConstants
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.widget.Widget
import grails.gorm.transactions.Transactional
import grails.test.mixin.gorm.Domain
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.apache.commons.lang.ClassUtils
import org.apache.commons.lang.StringUtils
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.orm.hibernate.cfg.HibernatePersistentEntity
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
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
class DataframeInstance implements DataframeConstants{
	private final Dataframe df
	private final Dataframe parentDf
	private final Map<String, String> requestParams
	private Map<String, Object> sessionParams
	private List results
	private def record
	private Map resultMap = [:];
	private JSONObject jData
	private Map<String, String> namedParmeters = [:]
	private Map savedDomainsMap = [:];
	private List<DataframeVue> hasManyDomainsList = []
	def sessionHibernate
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
		sessionHibernate = df.sessionFactory.openSession()
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
		return df.parsedHql.indexOfFileds.containsKey(mapFieldValue.name)
	}

	/**
	 *
	 */
	public Map getAdditionalData(String fieldName, Map parameterMap){
		Map additionalData = [:]

		def widgetObj = df.getWidget(fieldName)

/*  //TODO: remove it
        def widgetObjDbg = df.fields.get(fieldName)?.get("widgetObject")
		if(fieldName.contains("paymentMethods")){
			println "checking paymentMethods"
		}
*/
		additionalData = widgetObj?.loadAdditionalData(this, fieldName, parameterMap, sessionHibernate)
		return additionalData
	}

	private void populateInstance(){

		if(!df.hql){
			return;
		}

		Query query = createSQLQuery()

		setNamedParametersFromRequestOrSession(query)

		Transaction tx = sessionHibernate.beginTransaction()
		try{

			this.results = query.list()
			tx.commit();
			if(results && results .size() > 0){
				this.record = results[0] //TODO: its hard coded assumption we have only one record per Dataframe!
				calculateFieldValuesAsKeyValueMap();
				log.debug("");
			}else{
				isDefault = true
				//throw new DataframeException(df, "No record found for the Dataframe");
			}

		}catch(Exception e){
			tx.rollback()
			throw new DataframeException(df, "Error: ${e.message}", e )		}
	}

	private Query createSQLQuery() {
		Query query;
		String sqlGenerated;

		try {
			sqlGenerated = df.parsedHql.getSqlTranslatedFromHql()
			query = sessionHibernate.createQuery(df.hql);
			if (df.sql) {
				log.debug(" SQL was defined in the bean: ${df.sql}") //TODO if specified, run this thing ...
			}
			log.debug(" hql = $df.hql \n sqlGenerated = $sqlGenerated \n sql = $df.sql \n")
		} catch (Exception e) {
			throw new DataframeException(df, "Hql-Sql creation problem " + e + "\n hql = $df.hql \n sql = $df.sql\n");
		}
		return query
	}

	private void setNamedParametersFromRequestOrSession(Query query) {
		for (String parameter : query.getNamedParameters()) {

			def namedParam = df.getNamedParameter(parameter);
			String refDomainAlias = namedParam[0];
			String refFieldName = namedParam[1];

			def namedParameterFromRequestJsonValue = getNamedParameterFromRequestJson(requestParams, parameter, refDomainAlias, refFieldName)

			namedParmeters.put(parameter, namedParameterFromRequestJsonValue.toString())
			//TODO: check if the string represantation works corrrectly for each value type!
			query.setParameter(parameter, namedParameterFromRequestJsonValue)
		}

		namedParmeters.put("now", new Date())
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
			}
		}

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

		jData = new JSONObject(df.domainFieldMap)

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
				Map fieldProps = df.fields.get(fieldName)
				String myDomainAlias = null
				convertPersisters(fieldProps, fieldName)
				//Try to load Additional Data:
				convertAdditionalData(fieldName, fieldProps)

			}
		}
		addKeyDataForNamedParameters(jsonMapDf) // adding key- fields for vue js
		jsonMapDf.put("keys", getNamedParameters())
		jsonMapDf.put("additionalData", jsonAdditionalData)

		//Replace default add datan with relev values
		jsonDefaults.put("keys", getNamedParameters())
		jsonDefaults.put("additionalData", jsonAdditionalData)

		//These are for refresh parent dataframe, if provided (aka requested):
		def parentDataframe = requestParams?.parentDataframe
		def parentNode = requestParams?.parentNode
		def parentLevel = requestParams?.level
		def parentFieldName = requestParams?.parentFieldName
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
			//jsonRet.put("data", jsonMapDf)
			jsonRet.put("data", jData)
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

	private void convertAdditionalData(String fieldName, Map fieldProps) {
		Map jsonAdditionalData, additionalDataRequestParamMap
		Map jsonAdditionalDataField = getAdditionalData(fieldName, additionalDataRequestParamMap);
		if (jsonAdditionalDataField) {
			//TODO: next line we will remove!
			jsonAdditionalData.put(fieldName.replace(Dataframe.DOT, DataframeVue.UNDERSCORE), jsonAdditionalDataField);

			//Populate Items with the additional data this widget requires data from
			setFieldItems(jData, fieldProps, jsonAdditionalData)
		}
	}

	private void convertPersisters(Map fieldProps, String fieldName) {
		Map additionalDataRequestParamMap
		String myDomainAlias
		if (isFieldExistInDb(fieldProps)) {
			if (!isDefault) {
				def fldValue = getFieldValue(fieldProps)
//						jsonMapDf.put(fieldName, fldValue)
				//jsonMapDf.put(fieldName.replace(Dataframe.DOT,DataframeVue.UNDERSCORE), fldValue) //Chnaged Dot to Underscore for vue

				myDomainAlias = fieldProps.get(DataframeConstants.FIELD_PROP_DOMAIN_ALIAS)
				Widget widget = fieldProps.get(DataframeConstants.FIELD_PROP_WIDGET_OBJECT)
				String persistentDomainFieldName = fieldProps.get(DataframeConstants.FIELD_PROP_NAME)
				widget.setPersistedValueToResponse(jData, fldValue, myDomainAlias, persistentDomainFieldName, additionalDataRequestParamMap)

			}
			//def fldDefaultValue = fieldProps.defaultValue
//					jsonDefaults.put(fieldName, fldDefaultValue)

			//jsonDefaults.put(fieldName.replace(Dataframe.DOT,DataframeVue.UNDERSCORE), fldDefaultValue) //Chnaged Dot to Underscore for vue
			//Additional data:
		}
	}

	private void addKeyDataForNamedParameters(jsonMapDf){
		def namedParameters = getNamedParameters()
		namedParameters.each {key, value ->
			if(df.getNamedParameter(key)){
				def namedParam = df.getNamedParameter(key)
				String refDomainAlias =  namedParam[0];
				String refFieldName =  namedParam[1];
				String keyNamedParam = DataframeVue.buildFullFieldNameKeyParamWithDot(df, refDomainAlias, refFieldName, key);
//				jsonMapDf.put(keyNamedParam, value)
				jsonMapDf.put("$refDomainAlias",["$refFieldName": value, "$key":value]) //todo check if this works for all cases

				jData?."${DOMAIN_KEYS}".putAll(jsonMapDf)
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
	public boolean commit() throws DataManipulationException {
		boolean ret = true
		for ( Map.Entry<String, Object> domainInstanceName : savedDomainsMap.entrySet()) {
			def domainToSave = domainInstanceName.value[1]
			ret = domainToSave.save() && ret
		}
		return ret
	}


	public boolean save() throws DataManipulationException{
		return save(true)
	}

	public boolean save(boolean doCommit) throws DataManipulationException{
		saveEmbeddedDataframe()
		return saveWritableDomains(doCommit)
	}

	private boolean saveWritableDomains(boolean doCommit){
		List domainInstancesForSave = []
		df.writableDomains.each { domainName, domain ->

			DomainClassInfo domainClassInfo = domain.get(DataframeConstants.PARSED_DOMAIN);

			if (!(savedDomainsMap.containsKey(domainClassInfo.getDomainAlias()))) {
				//2. Get domain Key fields to decide if this insert or update
				boolean isInsert = isInsertDomainInstance(domainClassInfo);
				def domainInstance = null;
				def historyDomainInstance = null
				if (isInsert) { //Create new domain Instance
					domainInstance = createNewDomainInstance(domainClassInfo);
				} else { //Retrieve domain Instance
					domainInstance = retrieveDomainInstanceForUpdate(domainClassInfo);
					historyDomainInstance = createNewDomainInstanceForHistoryDomain(domainClassInfo)
				}

				if (applyNewValuesToDomainInstance(domainInstance, requestParams, domainClassInfo, historyDomainInstance)) {
					if (commitDomainInstance(domainInstance, doCommit)) {
						//domainInstancesForSave.add(domainInstance);
						//TODO: see if we can use one of those structures instead of 2 of them!!! - domainInstancesForSave we use in controller, so once refactoring, we will
						//use savedDomainsMap, retrieving from domainInstance and deprcate domainInstancesForSave
						savedDomainsMap.put(domainClassInfo.getDomainAlias(), [domain, domainInstance, isInsert]);
					}
				}
			}
		}
		return true
	}

	private boolean commitDomainInstance(domainInstance, isChanged){
		if(isChanged){
			try{
//				historyDomainInstance.close()
				domainInstance.save(flush: true, failOnError:true)
				return true
			}catch(Throwable ee){
				println(ee.getMessage())
				log.error("Error saving " + domainInstance.toString() + "for params: " + keysNamesAndValue + " : "+ee.getMessage())
				return false
			}
		}else{
			log.warn("Somebody hit the save button but never changed a thing, so we will not invoke save ! ")
			return true
		}

		return false
	}
	private void saveEmbeddedDataframe() {
//Run save for embedded DFs first:
		for (Map.Entry<String, DataframeVue> emdDfEntry : df.getEmbeddedDataframe()) {
			DataframeVue embDf = emdDfEntry.getValue();
			def key = emdDfEntry.getKey()
			def dfInstance = new DataframeInstance(embDf, requestParams)
			def res = dfInstance.save();
			embeddedDomainInstance.add(res)
			// =====   Assign new key values to key fields here if insert happened
			embDf.parsedHql.getNamedParameters().each { paramName, paramData ->
				String domainAlias = paramData[0]
				String dbFieldname = paramData[1];
				def domainMap = embDf.parsedHql.hqlDomains.get(domainAlias);
				def domainShortName = domainMap.key;
//					String keyFldNameToPopulateWithValue = Dataframe.buildFullFieldNameKeyParam(embDf, domainAlias, dbFieldname, paramName);
				String keyFldNameToPopulateWithValue = DataframeVue.buildFullFieldNameKeyParam(embDf, domainAlias, dbFieldname, paramName);//Changed for vue
				String keyCurrentValue = requestParams.get(keyFldNameToPopulateWithValue);
				//Finding new Value:
				res.each { domainInst ->
					String domainShortNameFromInstance = getMyDomainShortName(domainInst);
					if (domainShortNameFromInstance.equals(domainShortName)) {
						String newValue = String.valueOf(domainInst."${dbFieldname}");
						if (!newValue.equals(keyCurrentValue)) {
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
	}


	public boolean isInsertOccured() {
		return insertOccured
	}


	public def getSavedDomainsMap(){
		return savedDomainsMap
	}

	public def isMultipleDomainsToSave(){
		return (savedDomainsMap?.size() > 1)
	}

	public boolean setInterDomainRelationships(Map savedDomainMap) {
		if (!isMultipleDomainsToSave() || !isInsertOccured()) {return false}
		for(JoinParsed join : df?.parsedHql?.joins){
			def srcDomain = savedDomainMap.get(join.sourceDomain)
			def defSrcDomainInstance = srcDomain[1]
			def targetDomainInstance = savedDomainMap.get(join.targetDomain)
			defSrcDomainInstance."${join.sourceField}" = targetDomainInstance[1]
			defSrcDomainInstance.save(flush: true)
		}
		return true
	}

	private def createNewDomainInstance(DomainClassInfo  domainClassInfo) {
		PersistentEntity queryDomain = domainClassInfo.getValue();
		def newInstance = null
		try {

			newInstance = queryDomain.newInstance()
		}catch(Exception ee){
			log.error("What is it " + ee)
		}
		if(!newInstance){
			throw new DataframeException(df, "Error creating a new instance ${domainClassInfo.domainAlias}")
		}
		return newInstance
	}
	private def createNewDomainInstanceForHistoryDomain(DomainClassInfo  domainClassInfo){
		PersistentEntity domain = domainClassInfo.getValue();
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

	public def retrieveDomainInstanceForUpdate(DomainClassInfo domainClassInfo){
		PersistentEntity domainClass = domainClassInfo.getValue()
		def currentInstance = null

		String domainAlias = domainClassInfo.getDomainAlias()
		JSONObject domainKeys = requestParams?.domain_keys?."${domainAlias}"


		StringBuilder findFuncName = new StringBuilder();
		StringBuilder arguments = new StringBuilder();

		int lastIndex = 1
		domainKeys.each{key, value ->
			//This is the most common case:
			lastIndex++
			String keyField = key.toString().toLowerCase().capitalize()
			if(domainKeys.size() == 1 && (key == "id" || key == "Id") && !value && !StringUtils.isEmpty(value.toString()) && !value.toString().equalsIgnoreCase("new")){
				return domainClass.newInstance().get(value)
			}else{
				arguments.append(value)
				if(findFuncName.size() == 0){
					findFuncName.append("findBy").append(keyField)
				}else {
					findFuncName.append("And").append(keyField)
					if(lastIndex < domainKeys.size()-1) arguments.append(",")
				}
			}
		}
//		findFuncName.append(arguments)
//		return domainClass.newInstance()."${findFuncName.toString()}" //Todo see why this is not working
		return domainClassInfo.clazz."${findFuncName.toString()}"(arguments.toString())
	}

	public boolean isInsertDomainInstance(DomainClassInfo domainClassInfo) {

		String domainAlias = domainClassInfo.getDomainAlias()
		Map domainKeys = requestParams?.domain_keys?."${domainAlias}"


		for(def value:  domainKeys.values()){
			if(!value || StringUtils.isEmpty(value.toString()) || value.toString().toLowerCase() == "new"){
				insertOccured = true
				return true
			}
		}
		return insertOccured;
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

	private Set<String> constructKeyFieldName(def domain){
		Set keysNamedParams = domain.get("domainKeys")
		String domainAlias = domain.get("domainAlias")
		Set<String> keyFieldName = new HashSet<String>()
		String dataframeName = df.dataframeName
		keysNamedParams.each { param ->
			keyFieldName.add(dataframeName+df.UNDERSCORE+domainAlias + df.UNDERSCORE + param)
		}
		return keyFieldName
	}


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

	public boolean isParameterRelatedToDomain(HibernatePersistentEntity domainClass, String myDomainAlias, String paramName){

		if(paramName.startsWith("parent")){
			return false;
		}
		String paramNameTocheck = paramName;
		String fieldName = df.getFieldNameFromFieldId(paramNameTocheck);
		String domainAliasFromParam = df.getFieldDomainFromFieldId(paramNameTocheck);

		if(myDomainAlias.indexOf(domainAliasFromParam) > -1){

//			boolean hasPersistantProperty = domainClass.hasPersistentProperty(fieldName);
			boolean hasPersistantProperty = hasPersistentProperty(fieldName, domainClass)
			//todo: remove ispers as I thought it is not necessary, so check and add accordingly as required
//			boolean ispers = hasPersistantProperty?domainClass.getPropertyByName(fieldName)?.isPersistent():false;
//			if(hasPersistantProperty || ispers){
			if(hasPersistantProperty){
				return true;
			}
		}
		return false
	}

	private static getMyDomainShortName(def domainInstance){
		def clazz = domainInstance.class;
//		def domainClass = new DefaultGrailsDomainClass(clazz);
//		return domainClass.shortName;
		return ClassUtils.getShortClassName(clazz);
	}

	public boolean applyNewValuesToDomainInstance(def domainInstance, JSONObject requestParams, DomainClassInfo domainClassInfo, def historyDomainInstance){

		boolean isChanged = false
		boolean isValidate = true
		HibernatePersistentEntity hibernetPersistentEntity = domainClassInfo.getValue();
		String myDomainName = getMyDomainShortName(domainInstance)
		String myDomainAlias = domainClassInfo.getDomainAlias()

		if( StringUtils.isEmpty(myDomainAlias)){
			throw new DataframeException(" Trying to save Instance of ${myDomainName} for dataframe ${df.dataframeName}, that does not suppose to have this domain for saving!");
		}

		//*************   MAIN LOOP on PARAMETERS!!!! **************
		Map persisters = requestParams?.persisters?."${myDomainAlias}"
		persisters?.each() { fieldName, inputValue ->

			Widget widget = df.getFieldWidget(myDomainAlias, fieldName)
			Map field = df.getFieldByDomainAliasAndFieldName(myDomainAlias, fieldName)
            saveHistoryDomain(historyDomainInstance, domainInstance, domainClassInfo, fieldName, hibernetPersistentEntity, inputValue)
			//TODO: refactor it, please!!!

			isValidate = fieldValidate(field, inputValue)
			if (!isValidate){
				//todo: check validation for field latest grails version 3.3.0
				throw new DataframeException("Field validation Failed for: "+fieldName)
			}
			/**
			 * This is the heart of this method: applying the value to the domain for saving later!
			 */
			isChanged = widget.populateDomainInstanceValue(domainInstance, domainClassInfo, fieldName, field, inputValue) || isChanged

		} //persisters.each() { fieldName, fieldValue ->
		return isChanged
	}//End of method applyNewValuesToDomainInstance

	private boolean saveHistoryDomain(def historyDomainInstance, def domainInstance, DomainClassInfo domainClassInfo, String fieldName, HibernatePersistentEntity hibernetPersistentEntity, def inputValue){
//            Checking if historyDomainInstance is null and setting the values of parentId and last updated timestamp of parent table
		if(historyDomainInstance){
			if(fieldName == "id"){
				historyDomainInstance. parentId = domainInstance."${fieldName}"
				if(hasPersistentProperty("lastUpdated", hibernetPersistentEntity)){
					historyDomainInstance.lastUpdated = domainInstance.lastUpdated
				}
			}
		}

		//boolean isParameterRelatedToDomain = this.isParameterRelatedToDomain(domainClass, myDomainAlias, paramName)
		//if(isParameterRelatedToDomain){
		PersistentProperty prop = domainClassInfo.getPropertyByName(fieldName)
		def oldfldVal = domainInstance."${fieldName}";
		if(historyDomainInstance){
			if(!(df.metaFieldService.isAssociation(prop))) {
				historyDomainInstance."${fieldName}" = oldfldVal //Saving the old data to history table
			}else{
				if((df.metaFieldService.isManyToMany(prop)) || (df.metaFieldService.isOneToMany(prop))){
					def refDomainClass = domainClassInfo.getRefDomainClass(fieldName)
					def savedInstance = saveHasManyAssociation(inputValue,refDomainClass,fieldName,historyDomainInstance)
					historyDomainInstance = savedInstance
				}
			}
			//                Saving the historyDomainInstance
			historyDomainInstance.save(failOnError: true)
		}
	}
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
		//def formattedJSON = paramValue
		JSONArray jsonArray = new JSONArray(paramValue);
		domainInstance?.(StringUtils.uncapitalize(fieldName))?.clear()
		jsonArray.each{val ->
			val.each{
				if(it.key == "id"){
					def refDomainObj  = refDomainClass.get(Long.valueOf(it.value))
					String fn = fieldName.capitalize()
					String fn1 = fieldName.capitalize()
					domainInstance."addTo${fieldName.capitalize()}"(refDomainObj)

				}
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
		//Widget t = df.getWidget(fieldnameToReload);
		def result  = df.getWidget(fieldnameToReload)?.loadAdditionalData(df, fieldnameToReload, inputData, sessionHibernate)
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

	public boolean fieldValidate(Map field, def fldVal){

		def fldValStr = fldVal?.value?.toString()
		if(field.get("notNull") && fldValStr == null){
			if(field.get("pk")) return true
			return false
		}
		if (field.containsKey("regex")){
			String regex = field.get("regex")?.toString()?.replaceAll("/","")
			if (!fldValStr.find(regex)){
				return false
			}
		}
		return true
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


	public def getNamedParameterFromRequestJson(JSONObject requestParams, String namedParameter, String refDomainAlias, String refFieldName){

		//Serach for Session:
		if(namedParameter.indexOf(Dataframe.SESSION_PARAM_NAME_PREFIX) > -1){
			String paramStringValue = getSessionParamValue(namedParameter, sessionParams)
			return typeCastNamedParameterValue(refDomainAlias, refFieldName, paramStringValue);
		}

		if(!requestParams) return null

		//Search in request parameters:
		if (requestParams.containsKey(namedParameter)){
			return typeCastNamedParameterValue(refDomainAlias, refFieldName, requestParams.get(namedParameter))
		}

		//Search in request parameters:
//		Map requestNamedParams = requestParams.request_parameters?."${namedParameter}"
//		if(requestNamedParams?.containsKey(namedParameter)){return requestNamedParams.get(namedParameter)}

		//Search in Persisters:
		def pField = getPersisterField(requestParams, refDomainAlias, refFieldName)
		if(pField) {return typeCastNamedParameterValue(refDomainAlias, refFieldName, pField)}

		//Search in transits:
		def tField = getTransitField(requestParams, namedParameter)
		if(tField) {return typeCastNamedParameterValue(refDomainAlias, refFieldName, tField)}

		//Search in domain Keys:
		Map domainKeys = requestParams.domain_keys?."${refDomainAlias}"
		if(domainKeys?.containsKey(refFieldName)){
//			return typeCastNamedParameterValue(refDomainAlias, refFieldName, domainKeys.get(refFieldName).value)
			return typeCastNamedParameterValue(refDomainAlias, refFieldName, domainKeys.get(refFieldName))
		}

		//Serch in old style requestparameters for back compitability:
		String buildParamName = Dataframe.buildFullFieldNameKeyParam(df, refDomainAlias, refFieldName, namedParameter);
		if(requestParams.containsKey(buildParamName)){
			return typeCastNamedParameterValue(refDomainAlias, refFieldName, requestParams.get(buildParamName))
		}

		return null
	}//End of method applyNewValuesToDomainInstance

	private def typeCastNamedParameterValue(refDomainAlias, refFieldName, paramValue){
		return df.getTypeCastValue2(refDomainAlias, refFieldName, paramValue);
	}

	static def getPersisterField(JSONObject requestParams, String domainAlias, String fieldName){
		Map persisters = getPersisters(requestParams)
		if(persisters?.containsKey(domainAlias)) {
			Map persistersDomain = persisters?."${domainAlias}"
			if (persistersDomain?.containsKey(fieldName)) {
				return persistersDomain.get(fieldName)?.value
			}
		}
		return null
	}
	static Map getPersisters(JSONObject requestParams){
		return requestParams.persisters
	}

	static String getTransitField(JSONObject requestParams, String fieldName){
		Map transits = getTransits(requestParams)
		if(transits?.containsKey(fieldName)){
			return transits.get(fieldName).value
		}
		return null
	}

	static Map getTransits(JSONObject requestParams){
		return requestParams.transits
	}

	static String getDomainKeyField(JSONObject requestParams, String domainAlias, String fieldName){
		Map domainKeys = getDomainKeys(requestParams)
		if(domainKeys?.containsKey(domainAlias)) {
			Map domainKeyDomain = domainKeys?."${domainAlias}"
			if (domainKeyDomain?.containsKey(fieldName)) {
				return domainKeyDomain.get(fieldName)?.value
			}
		}
		return null
	}
	static Map getDomainKeys(JSONObject requestParams){
		return requestParams.domain_keys
	}

	public static boolean setFieldItems(JSONObject jData, Map field, Map additionlData){
		String fldDomain = (field.domain?.domainAlias?.size() > 0) ? field.domain.domainAlias : ""
		String fieldType = field.containsKey("domain") ? PERSISTERS : TRANSITS
		if(jData.containsKey(fieldType)){
			JSONObject node = jData.get(fieldType)
			JSONObject fieldNode = null
			if(fieldType == PERSISTERS){
				if(node.containsKey(fldDomain)) {
					JSONObject domainNode = node.get(fldDomain)
					fieldNode = domainNode.containsKey(field.name) ? domainNode.get(field.name) : null
				}
			}else{
				fieldNode = node.containsKey(field.name)? node.get(field.name) : null
			}
			if(fieldNode){
				fieldNode = additionlData
			}
		}
		return false;
	}

}