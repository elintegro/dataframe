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

import com.elintegro.erf.dataframe.service.MetaFieldService
import com.elintegro.payment.paymentEnums.PaymentMethods
import com.elintegro.utils.DateTimdataframe
import groovy.util.logging.Slf4j
import org.hibernate.Query
import org.hibernate.Transaction
import org.hibernate.transform.AliasToEntityMapResultTransformer

/**
 * This class holds and manages all transaction data for the records from the database and other sources. 
 * It is responsible for populating View with concrete data and also for  persisting it in the Database
 *
 * @author ELipkov1
 *
 */
@Slf4j
class DbResult {

	private final Map<String, String> requestParams
	private Map<String, String> namedParmeters = [:]
	private Map<String, String> resultMap = [:]
	private List results
	private def record
	private def dbSession
	String hql
	String generatedSql
	String sql
	ParsedHql parsedHql

	public DbResult(String hql, Map<String, String> requestParams, def dbSession){
		this.requestParams = requestParams
		this.hql = hql
		this.dbSession = dbSession
		this.parsedHql = parsedHql
	}

	public DbResult(String hql, Map<String, String> requestParams, def dbSession, ParsedHql parsedHql){
		this.requestParams = requestParams
		this.hql = hql
		this.dbSession = dbSession
		this.parsedHql = parsedHql
	}

	public boolean isGood(){
		return results && record
	}

	def getRecord(){
		return record
	}

	private def getNamedParameters(String name){
		return namedParmeters
	}

	boolean isFieldExist(String fieldName){
		return requestParams.containsKey(fieldName)
	}

	private void populateInstance(){

		try{
				this.results = getHqlResult(hql, requestParams)
				log.debug("Debug: result values " + results?.size())

				if(results){
					this.record = results[0]
				}else{
					throw new DataframeException("No record found for the Dataframe");
				}

		}catch(Exception e){
				log.error(e)
				log.error("Exception when executing hql $hql with parameters $requestParams")
				throw new DataframeException("Exception when executing hql $hql with parameters $requestParams ", e);
		}
	}

	/**   CRUD - RETRIEVE
	 * Using HQL, and parameters, read the data
	 * and return the map which can be converted to json on controller.
	 */
	public Map getResultMap(){

		//populateInstance()
		List results = getHqlResult(hql, requestParams)

		Map jsonRet = getJsonMap(results)

		return jsonRet
	}


	/**   CRUD - RETRIEVE
	 * Using HQL, and parameters, read the data
	 * and return the map which can be converted to json on controller.
	 */
	public Map getResultMapOfMaps(){

		List results = getHqlResult(hql, requestParams)

		Map jsonRet = getJsonMap(results)
		
		return jsonRet
	}


	public List getResultList(){

			List results = getHqlResult(hql, requestParams)

			return results
	}

	Map<String, ?> getJsonMap(List<?> results){
		Map<String, ?> jsonResults =[:]
		for(def res : results){
			jsonResults.put(res.get(0), res)
		}
		return jsonResults
	}

	Map<String, ?> getJsonMapOfMap(List<?> results, String[] fields){
		Map<String, ?> jsonResults =[:]
		for(def res : results){
			jsonResults.put(res.get(0), res)
		}
		return jsonResults
	}


	/**
	 *
	 * @param queryHql
	 * @param inputParams
	 * @return
	 */
	List getHqlResult(def queryHql, Map inputParams){
		List results = null
		Transaction tx = dbSession.beginTransaction()
		Query query =  dbSession.createQuery(queryHql);

		log.debug("query:"+query);

		for (String parameter : query.getNamedParameters()) {
			if(parameter.indexOf("session_") > -1){
				def sessionParamValue = DataframeInstance.getSessionParamValue(parameter, inputParams)
				sessionParamValue = getNamedTypeCastValue(parameter, sessionParamValue)
				query.setParameter(parameter, sessionParamValue)
			}else {
				if(!inputParams.containsKey(parameter)){
					throw new DataframeException(String.format("No parameter %s provided for query %s", parameter, query));
				}
				def keyValue = inputParams.get(parameter) //TODO what if the parameter namespace is not respected?
				namedParmeters.put(parameter, keyValue)
				keyValue = getNamedTypeCastValue(parameter, keyValue)
				query.setParameter(parameter, keyValue)
				log.debug("parameter:"+parameter );
			}
		}

		log.debug("before query execute:" );

		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		results = query.list()

		log.debug("results:"+results );
		tx.commit();
//		dbSession.close();
		return results
	}

	def getNamedTypeCastValue(parameter, def paramValue){
		String[] namedParam = this.parsedHql?.namedParameters?.get(parameter);
		if(namedParam && namedParam.length > 1){
			String refDomainAlias =  namedParam[0];
			String refFieldName =  namedParam[1];
			def domain = this.parsedHql?.hqlDomains?.get(refDomainAlias)
//				def prop = domain?.value?.getPersistentProperty(refFieldName)
			def prop = domain?.value?.getPropertyByName(refFieldName)
			if(prop){
				paramValue = getTypeCastValue(paramValue, prop);
			}
		}
		return paramValue
	}

	/**
	 * This method uses the string value and the GrailsDomainClassProperty and returns a type cast
	 * value as per the property data type.If the value is null then return null.
	 * @param value
	 * @param property
	 * @return
	 */
	public static def getTypeCastValue(def value, def property){
		if(value == null || value == "") return null
//		def typeName = property.getTypePropertyName()
		def typeName = MetaFieldService.getTypePropertyName(property.type)
		DateTimdataframe dateTimdataframe = new DateTimdataframe();
		if("string".indexOf(typeName.toLowerCase())>-1){
			return value as String
		}else if("date".indexOf(typeName.toLowerCase())>-1){
			return dateTimdataframe.getDateTimeFieldsFromStringTime(value)
		}else if("double".indexOf(typeName.toLowerCase())>-1){
			return Double.valueOf(value)
		}else if("float".indexOf(typeName.toLowerCase())>-1){
			return Float.valueOf(value)
		}else if("short".indexOf(typeName.toLowerCase())>-1){
			return Short.valueOf(value)
		}else if("int".indexOf(typeName.toLowerCase())>-1){
			return Integer.valueOf(value)
		}else if("long".indexOf(typeName.toLowerCase())>-1){
			return  Long.valueOf(value)
		}else if("boolean".indexOf(typeName.toLowerCase())>-1){
			return  Boolean.valueOf(value)
		}else if (MetaFieldService.isAssociation(property)){ //TODO: modified for non association property
			def currentInstance = property.associatedEntity.getJavaClass().newInstance().get(value)
			return 	currentInstance
		}else if (MetaFieldService.isEnumType(property)){
			def enumClass = property.type
			def enumValue = enumClass.getByType(value)
			return enumValue
		}else{
			return value
		}
	}

	/**
	 * This method returns some MetaData, based on the retrieved results
	 * It might be useful, but we do not use it now, we have full meta data from MetaFieldService
	 *
	 * @param resultList
	 * @return
	 */
	public Map extractMetaDataFromResults(List resultList){
		def mapResultMetaData = [:]
		if(resultList.size() > 0){
			int columnNumbers = resultList.get(0).size();
			Set missingColumns = [];
			int missingColumnsCounter = extractMetaDataFromFirstRecord(resultList, missingColumns, mapResultMetaData, dataframe);
			int currRec = 1;
			while (missingColumnsCounter > 0 && (currRec < resultList.size() - 1)){
				missingColumnsCounter = extractMetaDataFromRecords(resultList, currRec, missingColumns, mapResultMetaData, dataframe);
				currRec++;
			}
		}
		return mapResultMetaData;
	}

	private int extractMetaDataFromRecords(List resultList, int currRec, Set missingColumns, Map metaData, Dataframe df) {
		Set newMissingColumns = [];
		resultList.get(currRec).each{ fieldName, fieldValue ->
			if(missingColumns.contains(fieldName)){

				def metaDataField = [:]
				metaDataField.put("name", fieldName)
				if(fieldValue){
					addMetaDataFieldType(fieldName, fieldValue, metaDataField, df);
					metaData.put(fieldName, metaDataField);
				}else {
					newMissingColumns.add(fieldName)
				}
			}
		}
		missingColumns = newMissingColumns;
		return missingColumns.size()
	}


	/**
	 *
	 * @param resultList
	 * @param missingColumns
	 * @param metaData
	 * @param df
	 * @return
	 */
	private int extractMetaDataFromFirstRecord(List resultList, Set missingColumns, Map metaData, Dataframe df) {
		resultList.get(0).each{ fieldName, fieldValue ->
			def metaDataField = [:]
			def columnMap = [:]


			metaDataField.put("name", fieldName)


			if(fieldValue){
				addMetaDataFieldType(fieldName, fieldValue, metaDataField, df);

			}else {
				metaDataField.put("type", "string")
				missingColumns.add(fieldName);
			}

			metaData.put(fieldName, metaDataField);

		}
		return missingColumns.size();
	}


}
