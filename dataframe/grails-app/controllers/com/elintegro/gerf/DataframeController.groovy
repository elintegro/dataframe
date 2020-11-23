/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gerf

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.service.JavascriptService
import com.elintegro.erf.dataframe.service.TreeService
import grails.converters.JSON
import grails.test.mixin.gorm.Domain
import grails.util.Holders
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
//import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.ApplicationContext

/**
 * Created with IntelliJ IDEA.
 * User: Shai- Eugenel
 * Date: 11/15/12
 * Time: 2:52 AM
 * To change this template use File | Settings | File Templates.
 */
class DataframeController {

	ApplicationContext ctx;
	TreeService treeService;
	JavascriptService javascriptService;

	public static String TreeId_DELIMITER = "-";

	private void initCtx() {
		if (!ctx) {
			ctx = (ApplicationContext)/*ApplicationHolder.getApplication()*/ Holders.grailsApplication.getMainContext();
		}
	}

	public Dataframe getDataframe(params) {
		initCtx()
		Dataframe dataframe = getDataframeByName(params.dataframe)
		dataframe.init()
		return dataframe
	}

	public DataframeVue getDataframeVue(params) {
		initCtx()
		Dataframe dataframe = getDataframeByNameVue(params.dataframe)
		dataframe.init()
		return dataframe
	}

	public static Dataframe getDataframeByName(String dataframeName) {
		Dataframe dataframe = (Dataframe) Holders.grailsApplication.mainContext.getBean(dataframeName)
		return dataframe
	}

	public static DataframeVue getDataframeByNameVue(String dataframeName) {
		Dataframe dataframe = (DataframeVue) Holders.grailsApplication.mainContext.getBean(dataframeName)
		return dataframe
	}
	/**
	 * This method uses the parameter in the given hql of Dataframe object and retrieve the corresponding
	 * values from database. If there is no match for the parameter then a blank list will be return other
	 * wise the result list will return and render as json on this following method.
	 * @return
	 */
	def ajaxValues() {
		def jsonMap = ajaxValuesRaw()
		def converter = jsonMap as JSON
		converter.render(response)
	}
	/*def ajaxValuesVue(){
		def jsonMap = ajaxValuesRawVue()
		def converter = jsonMap as JSON
		converter.render(response)
	}*/

	def ajaxValuesRaw() {

		Dataframe dataframe = getDataframe(params)

		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//Actual data, retrieved from Database:
		//

		def userId = session.getAttribute("userid")
		if (userId == null) {
			session.setAttribute("userid", (long) grailsApplication.config.guestUserId)
		}
		userId = session.getAttribute("userid")

		def p = params
		def s = session

		def dfInstance = new DataframeInstance(dataframe, params)

		dfInstance.setSessionParameters(DataframeInstance.getSessionAtributes(session))

		def jsonMap = dfInstance.readAndGetJson()

		def inp = dfInstance.getFieldValuesAsKeyValueMap()
		return jsonMap
	}
	/*def ajaxValuesRawVue(){

		Dataframe dataframe = getDataframe(params)

		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//Actual data, retrieved from Database:
		//

		def userId = session.getAttribute("userid")
		if(userId == null){
			session.setAttribute("userid", (long)grailsApplication.config.guestUserId)
		}
		userId = session.getAttribute("userid")

		def p = params
		def s = session

		def dfInstance = new DataframeInstance(dataframe, params)

		dfInstance.setSessionParameters(DataframeInstance.getSessionAtributes(session))

		def jsonMap = dfInstance.readAndGetJson()

		def inp = dfInstance.getFieldValuesAsKeyValueMap()
		return jsonMap
	}*/
	/**
	 * This method uses the parameter in the given hql of Dataframe object and retrieve the corresponding
	 * values from database. If there is no match for the parameter then a blank list will be return other
	 * wise the result list will return and render as json on this following method.
	 * @return
	 */
	def ajaxDefaultData() {
		Dataframe dataframe = getDataframe(params)
		//def jsonMap = [:]

		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//Actual data, retrieved from Database:
		//

		def params_ = params;
		params_.put("treeFieldName", "MyTree");
		String parentFieldName = treeService.getParentField(params_);
		params.put("parentFieldName", parentFieldName);

		def dfInstance = new DataframeInstance(dataframe, params)
		def jsonMap = dfInstance.buildDefaultJson();

		render jsonMap as JSON
	}

	/**
	 * This method uses the parameter in the given hql of Dataframe object and retrieve the corresponding
	 * values from MetaData. If there is no match for the parameter then a blank list will be return other
	 * wise the result list will return and render as json on this following method.
	 * @return
	 */
	def ajaxCreateNew() {
		Dataframe dataframe = getDataframe(params)
		def userId = session.getAttribute("userid")
		if (userId == null) {
			session.setAttribute("userid", (long) grailsApplication.config.guestUserId)
		}
		def dfInstance = new DataframeInstance(dataframe, params)
		dfInstance.setSessionParameters(DataframeInstance.getSessionAtributes(session))
		def jsonMap = dfInstance.createAndGetJson()
		if (dataframe.insertHtmlTo && dataframe.divIdToDisable) {
			jsonMap.put('insertHtmlTo', dataframe.insertHtmlTo)
			jsonMap.put('divIdToDisable', dataframe.divIdToDisable)
			jsonMap.put('popUpTitle', dataframe.popUpTitle)
		}
		render jsonMap as JSON
	}


	public static long getSessionUserId(def session) {
		String userId = session.getAttribute("userid")
		if ( userId == null ) {
			session.setAttribute("userid", (long) Holders.grailsApplication.config.guestUserId)
		}
		return Long.valueOf(userId)
	}


	/**
	 * This method uses the parameter to update/insert the fields mention on hql of the Dataframe
	 * object.Return a json as per the success or failure of the save.
	 * @return
	 */
//	def ajaxSave(){
//		ajaxSave(params)
//
//	}
	def ajaxSave(){
		def resultData = ajaxSaveRaw();
		resultData.remove("dfInstance")
		def converter = resultData as JSON
		converter.render(response)
	}

	def ajaxSaveRaw(){
		def _params = request.getJSON()
		Dataframe dataframe = getDataframe(_params)
		def dfInstance = new DataframeInstance(dataframe, _params)
		def operation = 'U'; //Update
		def result;

		try {
			result = dfInstance.save(true);
		}catch(Exception e){
			log.error("Failed to save data for dataframe ${dfInstance.df.dataframeName} Error : \n " + e)
		}

		if(dfInstance.isInsertOccured()){
			operation = "I";
		}

		def resultData
		def generatedKeys = [:]
		def generatedKeysArr = []

		Map savedResultMap = dfInstance.getSavedDomainsMap();

		Map<String, Map> resultAlias = [:]
		savedResultMap.each { domainAlias, domainInstance ->
//			savedResultMap.each { domainAlias, domainObj ->
			//this.writableDomains.put(domainAlias, ["parsedDomain": field.domain, "queryDomain":null, "keys":[], "domainAlias": domainAlias])
//			def doamin = domainObj[0]
//			def domainInstance = domainObj[1]
//			doamin?.keys?.each{ key ->
//				def keyValue = domainInstance."${key}"
//				generatedKeys.put("${doamin.parsedDomain}_${key}", keyValue)
//				generatedKeysArr.add(keyValue)
//			}


			Map record = [:];
			def properties = getAllProperties(domainInstance)
			properties.each { fieldName, value ->
				String alias = dataframe.getFieldAlias(domainAlias, fieldName)
				if (alias){
					record.put(alias, value);
				}
			}
			resultAlias.put(String.valueOf(domainAlias), record)
		}

/*
		result?.each{ record->
			def _id = record.id
			generatedKeys.add record.id
		}
*/
		//TODO: why do we need this? May be it is failing render process?
		_params.remove("controller")
		_params.remove("action")

		MessageSource messageSource = dataframe.messageSource

		if(result) {
			String msg = messageSource.getMessage("data.save.success", null, "save.success", LocaleContextHolder.getLocale())
			resultData = ['success': true, 'msg': msg, generatedKeys: generatedKeys, nodeId: generatedKeysArr, operation: operation, newData: resultAlias, params: _params, dfInstance: dfInstance]
		} else {
			String msg = messageSource.getMessage("data.save.not.valid", null, "data.not.valid", LocaleContextHolder.getLocale())
			resultData = ['msg': msg, 'success': false]
		}
		return resultData
	}

	/**
	 * This method uses the parameter to get the objects mention on hql of Dataframe object
	 * and delete that object.
	 * @return
	 */
	def ajaxDelete(){

		Dataframe dataframe = getDataframe(params)
		def dfInstance = new DataframeInstance(dataframe, params)
		def result = dfInstance.delete()

		def resultData = ['error': true,  'operation':'D']
		if(result){
			resultData = ['success': true,  'operation':'D', 'deleted_records':result]
		}

		def converter = resultData as JSON
		converter.render(response)

		return false
	}

	/**
	 *
	 * @return
	 */
	def ajaxDeleteExpire() {

		Dataframe dataframe = getDataframe(params)
		def dfInstance = new DataframeInstance(dataframe, params)

		def result = dfInstance.deleteExpire()

		def resultData
		def parentid = params.parentId
		def currentLevel =  dataframe.getFieldValueFromParametersAndName(params, "level")

		def ids = []
		ids.add params.parentNode

		resultData = ['success': true,'level':currentLevel,'nodeId':result, 'key':params.parentNode, 'deletedId':params.id,'parentNodeId':params.parentNodeId, 'parentLevel':params.parentLevel, 'parentFieldName':params.parentFieldName,  operation:'E']
		def converter = resultData as JSON
		converter.render(response)
		return false
	}


	/**
	 *
	 * @return
	 */
	def ajaxLoadAdditionalDataForField(){
		//I expect that params contains all fields of the dataframe plus tname of the field
		//Who needs to be refreshed (reloaded)
		def reloadingField = params.reload
		if(reloadingField){
			Dataframe dataframe = getDataframe(params)
			def dfInstance = new DataframeInstance(dataframe, params) // get instance but not reload: we will use parameters for the values!
			def result = dfInstance.reloadField(reloadingField, params)
			def converter = result as JSON
			converter.render(response)
		}
		return false
	}

	// TODO      ajaxjQTreeLoad is a widget-specific conroller and shouldn't be in dataframe controller,
	//          it shoudld be part of some kind of jqtree controller, so move it
	//          Better idea: create refresh call for a widget, if implemented there will be ajax call
	//          that will trigger ajaxRefresh() method in this class that will be dinamically calling all relevant Widget's methods and gaining
	//           refresh data ... something like this
	/**
	 *
	 * @return
	 */
	def ajaxjQTreeLoad(){
		List results = []

		Map tt = params;

		Dataframe dataframe = getDataframe(params)

		def parentNode = params.key

		def operation = params.operation

		String level = _params.get(dataframe.dataframeName+"_level")


		Long parentKeyValue = getIdFromUiIdConstruct(parentNode)

		int t = 0;

		int currentLevel
		if(params.level != null && params.level != 'null' && params.level != 'undefined'){
			currentLevel = Integer.valueOf(params.level);
		}else{
			currentLevel = 0;
		}

		def p = params.leveldepth
		def treeFieldName = params.treefield
		def isDelOperation = params.isRemoval==null ? false : Boolean.parseBoolean(params.isRemoval)
		def jQTreeHqlMap = dataframe.getFieldByName(treeFieldName).get("treeMap");


		int levelDepth
		if(params.leveldepth != null || params.leveldepth != 'null' || params.leveldepth != 'undefined'){
			levelDepth = 0;// We have not specified level depth, so we want all levels to be retrieved
		}else{
			levelDepth = Integer.valueOf(params.leveldepth);
		}

		long nodeId
		String nodeIdStr = params.nodeId
		if(nodeIdStr != null && nodeIdStr != 'null' && nodeIdStr != 'undefined' && nodeIdStr != ""){
			String[] nodeIdArr = nodeIdStr.split(",")
			if(nodeIdArr != null && nodeIdArr.size() >0){
				nodeIdStr = nodeIdArr[0]
			}
			try{
				nodeId = Long.valueOf(nodeIdStr);
			}catch(NumberFormatException e){
				nodeId = 0;
			}
		}else{
			nodeId = 0;
		}

		if(nodeId == 0 || operation == 'D' || operation == 'E'){
			results.addAll(getLevelList(currentLevel, dataframe, jQTreeHqlMap, parentKeyValue, levelDepth))
		}else{
			results.addAll(getNodeData2(currentLevel, dataframe, jQTreeHqlMap, parentKeyValue, nodeId, parentNode, isDelOperation))
		}

		render results as JSON
	}

	/**
	 *
	 * @param level
	 * @param dataframe
	 * @param jQTreeHqlMap
	 * @param parentKeyValue
	 * @param recurciveDepth
	 * @return
	 */
	def getLevelList(int level, Dataframe dataframe, def jQTreeHqlMap, def parentKeyValue, int recurciveDepth){
		def results = []

		long totalLevels =  jQTreeHqlMap.size()
		if(recurciveDepth == 0){
			recurciveDepth = totalLevels //TODO test it!
		}
		def currentLevelObject = jQTreeHqlMap.get("level_"+level)
		def icon = currentLevelObject?.get("icon");

		//TODO remove!!!
		def hhh = currentLevelObject?.get("hql")

		def sessionAttributes = DataframeInstance.getSessionAtributes(session)

		def currentLevelResult = dataframe.getHqlResult(currentLevelObject?.get("hql"), parentKeyValue, sessionAttributes);
		int currRecDepth = recurciveDepth - 1;
		currentLevelResult?.each {object->
			def element = getElement(object, icon, level, parentKeyValue)
			if(currentLevelObject.get("detailDataFrame") instanceof String){
				String formNodeKeyForVue = currentLevelObject.get("detailDataFrame") + "_" + object[0] //items.key for vueTreeWidget
				element.put("nodeKey",formNodeKeyForVue)
			}
			if(totalLevels > Long.valueOf(level+1) && currRecDepth > 0){
				List children = getLevelList(level+1, dataframe, jQTreeHqlMap, element.value, currRecDepth)
				if(children != null && children.size()>0){
					element.put("items", children)
				}
			}

			results.add(element)
		}

		return results
	}

	/**
	 *
	 * @param level
	 * @param dataframe
	 * @param jQTreeHqlMap
	 * @param parentKeyValue
	 * @param nodeId
	 * @return
	 *
	 */
	def getNodeData(int level, Dataframe dataframe, def jQTreeHqlMap, def parentKeyValue, long nodeId){
		def results = []
		def currentLevelObject = jQTreeHqlMap.get("level_"+level)
		def icon = currentLevelObject.get("icon");
		def currentLevelResult = dataframe.getHqlResult(currentLevelObject.get("hql"), parentKeyValue, DataframeInstance.getSessionAtributes(session));
		currentLevelResult?.each {object->
			def element = getElement(object, icon, level, parentKeyValue)
			if(nodeId == element.value){
				results.add(getElement(object, icon, level, parentKeyValue))
			}
		}

		return results
	}

	/**
	 *
	 * @param level
	 * @param dataframe
	 * @param jQTreeHqlMap
	 * @param parentKeyValue
	 * @param nodeId
	 * @param parentTreeId
	 * @param isDelOperation
	 * @return
	 */
	def getNodeData2(int level, Dataframe dataframe, def jQTreeHqlMap, def parentKeyValue, long nodeId, String parentTreeId, isDelOperation){
		def results = []
		def currentLevelObject = jQTreeHqlMap.get("level_"+level)
		def icon = currentLevelObject.get("icon");
		def hql = currentLevelObject.get("hql");
		def currentLevelResult = dataframe.getHqlResult(currentLevelObject.get("hql"), parentKeyValue, DataframeInstance.getSessionAtributes(session));
		currentLevelResult?.each {object->
			def element = getElement(object, icon, level, parentKeyValue)
			if(isDelOperation){
				if(nodeId != element.value){
					results.add(getElementWithParentTreeId(object, icon, level, parentKeyValue, parentTreeId))
				}
			}
			else{
				if(nodeId == element.value){
					results.add(getElementWithParentTreeId(object, icon, level, parentKeyValue, parentTreeId))
				}
			}
		}

		return results
	}


	/**
	 *
	 * @param object
	 * @param icon
	 * @param level
	 * @param parentKeyValue
	 * @return
	 */
	def getElement(def object, def icon, def level, def parentKeyValue){
		def element = [:]
		def elementId = object[0];
//		def label = object[1];
		String label = constructLabelForTreeNode(object)
		element.put("label", label)
		element.put("id", getUiIdConstruct(level,elementId))
		element.put("parentId", parentKeyValue)
		element.put("value", elementId)
		element.put("icon", icon)
		return element
	}

	/**
	 *
	 * @param object
	 * @param icon
	 * @param level
	 * @param parentKeyValue
	 * @param parentTreeId
	 * @return
	 */
	def getElementWithParentTreeId(def object, def icon, def level, def parentKeyValue, def parentTreeId){
		def element = [:]
		def elementId = object[0];
//		def label = object[1];
		String label = constructLabelForTreeNode(object)
		element.put("label", label)
		element.put("id", getUiIdConstruct(level,elementId))
		element.put("parentId", parentKeyValue)
		element.put("value", elementId)
		element.put("icon", icon)
		element.put("parentTreeId", parentTreeId)
		return element
	}
	private String constructLabelForTreeNode(object){

		def val = object[0]
		try{
			if(object[3] != null){
				if(object[2] !=null && object[1]!= null){
					val = object[1]+" "+ object[2]
				}else{
					val = object[3]
				}
			}else if(object[1] != null){
				val = object[1]
			}
		}catch (ArrayIndexOutOfBoundsException e){
			try{
				if(object[2] !=null && object[1]!= null){
					val = object[1]+" "+ object[2]
				}else{
					val = object[1]
				}
			}catch(ArrayIndexOutOfBoundsException ae){
				if(object[1] != null){
					val = object[1]
				}
			}
		}
		return val
	}
	/**
	 *
	 * @param level
	 * @param elementId
	 * @return
	 */
	String getUiIdConstruct(def level, def elementId){
		return "$level$TreeId_DELIMITER$elementId";
	}

	/**
	 *
	 * @param uiId
	 * @return
	 */
	long getIdFromUiIdConstruct(String uiId){
		String resStr = "0";
		long res = 0;

		if(uiId != null && !"null".equals(uiId) ){
			String[] arr = uiId.split(TreeId_DELIMITER)
			if(arr!=null && arr.size() == 2){
				resStr = arr[1];
			}else if(arr!=null && arr.size() == 1){
				resStr = arr[0];
			}
		}
		try{
			res = Long.valueOf(resStr)
		}catch(Exception e){
		}
		return res
	}

	/**
	 *
	 * @param uiId
	 * @return
	 */
	int getLevelFromUiIdConstruct(def uiId){
		if(uiId != null){
			String[] arr = uiId.split(TreeId_DELIMITER)
			if(arr!=null && arr.size() == 2){
				return  Integer.valueOf(arr[0])
			}else if(arr!=null && arr.size() == 1){
				return  0
			}
		}
		return 0
	}

	public static Map getAllProperties(def instance){
		return instance.class.declaredFields.findAll { !it.synthetic }.collectEntries {
			[ (it.name):instance."$it.name" ]
		}
	}

	public static Map formatResult(def _params, DataframeInstance dfInstance, Dataframe dataframe, def result){

		Map savedResultMap = dfInstance.getSavedDomainsMap();

		Map<String, Map> resultAlias = [:]
		savedResultMap.each { domainAlias, domainInstance ->
			Map record = [:];
			def properties = getAllProperties(domainInstance)
			properties.each { fieldName, value ->
				String alias = dataframe.getFieldAlias(String.valueOf(domainAlias), String.valueOf(fieldName))
				if (alias){
					record.put(alias, value);
				}
			}
			resultAlias.put(String.valueOf(domainAlias), record)
		}

		def generatedKeys = []
		result.each{ record->
			def _id = record.id
			generatedKeys.add record.id
		}
		_params.remove("controller")
		_params.remove("action")
		return [_params: _params, generatedKeys:generatedKeys, resultAlias:resultAlias, result: result]
	}


}
