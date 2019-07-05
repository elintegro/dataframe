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

import grails.util.Holders
import org.apache.commons.collections.map.LinkedMap
import org.apache.commons.lang.StringUtils
import org.grails.core.DefaultGrailsDomainClass
import org.grails.orm.hibernate.cfg.HibernatePersistentEntity
import org.hibernate.Query
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory
import org.hibernate.hql.spi.QueryTranslator
import org.hibernate.hql.spi.QueryTranslatorFactory
import org.hibernate.persister.entity.SingleTableEntityPersister
import org.hibernate.type.Type

import java.util.regex.Matcher
import java.util.regex.Pattern

class ParsedHql {

	String hql
	String sqlString
	String fieldStr
	String fromStr
	String[] fieldsArr
	Map fields = new LinkedHashMap()
	Map aliasDomainFields = [:]
	Map<String, DomainClassInfo> hqlDomains = [:]
	Map hqlDomainsNameMap = [:]
	Map indexOfFileds = [:]
	def  grailsApplication
	def sessionFactory
	Map namedParameters = [:]


	ParsedHql(String hql, def grailsApplication, def sessionFactory){
		this.hql = hql?hql.trim().replaceAll("(?!.)\\s", ""):"";
		this.grailsApplication=grailsApplication
		this.sessionFactory = sessionFactory
		parseHQLInit()
	}

	private def parseHQLInit(){

		if(hql){
            def session = sessionFactory.openSession()
//            def session = SessionFactoryUtils.getSession(sessionFactory, true)
			Query query =  session.createQuery(hql);
			for (String parameterName : query.getNamedParameters()) {

				Pattern hqlNamedParameterPattern = Pattern.compile("\\s*\\w+\\.\\w+\\s*=\\s*:\\s*${parameterName}");
				// in case you would like to ignore case sensitivity,
				// you could use this statement:
				// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
				Matcher matcher = hqlNamedParameterPattern.matcher(hql);
				// check all occurance
				String namedParameterExtract = null;
				while (matcher.find()) {
				  namedParameterExtract = matcher.group()
				}
				
				//This is for straight forward Where expressions, mostly for those of entity.id = :id to be able to save new record (insert operation)
				//If namedParameterExtract is null this probably more complex expression and is a filter, we do not need to add to the named parameters and deal with it when inserting 
				if(namedParameterExtract != null){
					// now create a new pattern and matcher to replace whitespace with tabs
					Pattern replace = Pattern.compile("\\s*=\\s*:${parameterName}");
					Matcher matcher2 = replace.matcher(namedParameterExtract);
					String namedParameterDb = matcher2.replaceAll("");

					if(namedParameterDb == null ||  namedParameterDb.size() == 0){ //Empty
						throw new DataframeException("Naming parameters are not specified properly: " + parameterName);
					}

					String[] namedParameter = namedParameterDb.split("\\.")
					if(namedParameter == null || namedParameter.length != 2){
						throw new DataframeException("Naming parameters are not specified properly: should be <domain>.<field> and in fact it is: " + namedParameterDb);
					}
					for(int i = 0; i < namedParameter.size(); i++){
						namedParameter[i] = namedParameter[i].trim()
					}
					namedParameters.put(parameterName.trim(), namedParameter)
				}
				
			}

			def HqlFldMatcher = (hql =~ /^(?i)select\s(.+?)\s(?i)from\s.*/)
			def HqlFromMatcher = (hql =~ /.*(?i)from\s(.+?)\s(?i)where\s.*/)
			fieldStr = HqlFldMatcher.matches()?HqlFldMatcher[0][1]:""//
			fromStr = HqlFromMatcher.matches()?HqlFromMatcher[0][1]:""//
			if(fromStr.length()==0){
				HqlFromMatcher = (hql =~ /.*(?i)from\s(.*)/)
				fromStr = HqlFromMatcher.matches()?HqlFromMatcher[0][1]:""//
			}
			if(fromStr.matches(/.*(?i)join\s(.+?).*/)){
				fromStr = getReplacedJoinString(fromStr)
			}

			QueryTranslatorFactory ast = new ASTQueryTranslatorFactory();
			QueryTranslator queryTranslator = ast.createQueryTranslator( hql , hql , Collections.EMPTY_MAP, sessionFactory, null );
			queryTranslator.compile( Collections.EMPTY_MAP, true );
			def retTypes = queryTranslator.getReturnTypes()
			sqlString = queryTranslator.getSQLString()
					
			getFromMap(fromStr);

			//def qIdent = queryTranslator.getQueryIdentifier()
			//def metaClass = queryTranslator.getMetaClass()
			//def metaProp = queryTranslator.getMetaPropertyValues()
			//def columns = queryTranslator.getColumnNames() //This brings just coded sql fields, not too meaningful
			//def aliases = queryTranslator.getReturnAliases() //Just nambers
			
						
			fillFieldMap(fieldStr, retTypes)

		}
	}

	/**
	 *
	 * @param hql
	 * @return
	 * The method uses hql to convert that hql query to  sql query.
	 */
	public String getSqlTranslatedFromHql(){
		return sqlString;
	}

	
	public String getReplacedJoinString(String hqlString){
		/*hqlString = hqlString.replaceAll(/(?i)left\s{1,3}(?i)outer\s{1,3}(?i)join\s{1,3}(?i)fetch\s/,",")
		hqlString = hqlString.replaceAll(/(?i)right\s{1,3}(?i)outer\s{1,3}(?i)join\s{1,3}(?i)fetch\s/,",")
		hqlString = hqlString.replaceAll(/(?i)left\s{1,3}(?i)outer\s{1,3}(?i)join\s/,",")
		hqlString = hqlString.replaceAll(/(?i)right\s{1,3}(?i)outer\s{1,3}(?i)join\s/,",")
		hqlString = hqlString.replaceAll(/(?i)left\s{1,3}(?i)join\s{1,3}(?i)fetch\s/,",")
		hqlString = hqlString.replaceAll(/(?i)right\s{1,3}(?i)join\s{1,3}(?i)fetch\s/,",")
		hqlString = hqlString.replaceAll(/(?i)inner\s{1,3}(?i)join\s{1,3}(?i)fetch\s/,",")
		hqlString = hqlString.replaceAll(/(?i)full\s{1,3}(?i)join\s{1,3}(?i)fetch\s/,",")
		hqlString = hqlString.replaceAll(/(?i)left\s{1,3}(?i)join\s/,",")
		hqlString = hqlString.replaceAll(/(?i)right\s{1,3}(?i)join\s/,",")
		hqlString = hqlString.replaceAll(/(?i)inner\s{1,3}(?i)join\s/,",")*/
		hqlString = hqlString.replaceAll(/(?i)((\s+on|with\s+)(.*?)(\sjoin)\s*)/,", ")
		hqlString = hqlString.replaceAll(/(?i)\s*((left|right)?\s*(inner|outer)?\s*(join)\s*(fetch)?)/,", ")
		hqlString = hqlString.replaceAll(/(?i)\s+(on|with|where|group by|order by)(.|\n)*/,"")

		return hqlString;
	}

	private void getFromMap(String fromStr){
		
		String[] tables = fromStr.split(',')
		Map res = new LinkedMap()
		int i = 0

		 String parentDomain
		for(String tbl: tables){
			String[] tblDtl = tbl.trim().split(/\sas\s/)
			if(tblDtl.length == 1){
				tblDtl = tbl.trim().split(/\s/)
			}
			String doaminAlias = tblDtl.length>1?tblDtl[1]:tblDtl[0];
			String tableName = tblDtl[0];
			String domainName = tblDtl[0];
			DomainClassInfo dci = getDomainFromPath(domainName, doaminAlias, tableName, parentDomain)
			String domainShortName = dci.getSimpleDomainName();
			hqlDomains.put(doaminAlias, dci);
			hqlDomainsNameMap.put(domainShortName, dci);
			if(!parentDomain){
				parentDomain = domainShortName
			}
		}
	}
	

	private DomainClassInfo getDomainFromPath(String path, String domainAlias, String tableName, def parentDomain){
		String[] domainPaths = path.split(/\./)
		DefaultGrailsDomainClass dom
			if(domainPaths.length == 1 ){//Just a Domain name without path
				dom = grailsApplication.domainClasses.find {
					it.clazz.simpleName == domainPaths[0]
				}
			}else if(domainPaths.length > 2){//Full path
				dom = grailsApplication.domainClasses.find {
					it.clazz.name == path
				}
			}
			if(!dom && domainPaths.length == 2){//What if the domain specified as dbinstance.Domain?
                  /**Here, "parentDomain" contains the main domain class name of an association hql,
				   * It is used with the assumption that the domain class used in save, query operation for an association is the main class itself not the associated domain class*/
				 if(parentDomain){
					 dom = grailsApplication.domainClasses.find { it.clazz.simpleName.equalsIgnoreCase(parentDomain) }

				String parentDomainAlias = domainPaths[0];
				String asstionName = domainPaths[1];
				def ascDomainClass = Holders.grailsApplication.mappingContext.getPersistentEntity(dom.clazz.name)
				def prop = ascDomainClass.getPropertyByName(asstionName)
				def typeName = prop.associatedEntity.getJavaClass().getName()
				dom = grailsApplication.domainClasses.find { it.clazz.name == typeName }
				 }

			}

			if(dom){
				SingleTableEntityPersister persister = sessionFactory.getClassMetadata(dom.clazz)
				DomainClassInfo domainClassInfo = new DomainClassInfo(dom.clazz, domainAlias, tableName, persister)

				//return  [key: domainClazz.getSimpleName(), value: domain, domainAlias: domainAlias,  tablename: tableName, clazz:clazz];
				return domainClassInfo
			}

	}

	/**
	 * The method uses fieldStr and build fields map .
	 */
	private def fillFieldMap = { fldStr, types ->
				
		def fieldArr = fldStr.split(',')

		def domains = hqlDomains.keySet() as String[]
		def index = 0
		for( String field in fieldArr){
			//field = field.replaceAll("\\s+","")
			Type fieldRetType = types[index]

			
			String[] fldDtl = field.trim().split(/\s*(?i)(\s+as\s+|\s)\s*/)
			String alias = ""
			String fldNmAlias = ""
			if(isAliasDefined(fldDtl)){
				alias = fldDtl[1].replaceAll("\\s+","")
				field = fldDtl[0].replaceAll("\\s+","")
				fldNmAlias = alias
			}else if(fldDtl.length == 1){
				alias = fldDtl[0].replaceAll("\\s+","")
				field = fldDtl[0].replaceAll("\\s+","")
			}else{
				throw new DataframeException("Problem with HQL for the %s, a field %s is not defined well", hql, field);
			}

			String[] data = field.split(/\./)
			if(data == null || data.length < 2){
				throw new Exception("Please provide domain to the field $field ")
			}

			String fldOwner = data[0].replaceAll("\\s+","")
			String fldkey = ""
			if (isAliasDefined(fldDtl)){
				fldkey = StringUtils.uncapitalize(alias)
			}else {
				fldkey = StringUtils.uncapitalize(data[1])
			}
			String fieldName = ""
			if(field.indexOf(".")>-1 && hqlDomains[fldOwner] != null){
				fieldName = field.substring(field.indexOf(".")+1);
				fields.put(fldkey, ["domain" : hqlDomains[fldOwner], "alias" : alias, "domainAlias" : fldOwner, "name" : fieldName, "fldNmAlias": fldNmAlias])
				indexOfFileds.put(field.substring(field.indexOf(".")+1), index)
			}else{
				fieldName = field.trim();
//				def doamin = hqlDomains[ve[0]]
				def domain = hqlDomains.values().first()
				String doaminAlias = domain.get("domainAlias");
				fields.put(fldkey, ["domain": domain, "alias": alias, "domainAlias" : doaminAlias, "name" : fieldName, "tableName" : fldOwner, "fldNmAlias": fldNmAlias])
				indexOfFileds.put(field.trim(), index)
			}
			aliasDomainFields.put(Dataframe.getAliasFieldKey(fldOwner, fieldName), alias);
			index++
		}
	}

	/**
	 * This method extracts a field name based on first condition in the where clause of a tree level HQL
	 */
	static String getParentFieldName(String hql){
		String ret = ""
		def HqlFromMatcher = (hql =~ /.*(?i)where\s(.+?)\..*/)
		ret = HqlFromMatcher.matches()?HqlFromMatcher[0][1]:""
		return ret;
	}

	private static def isAliasDefined(def fldDtl){
		return fldDtl.length == 2
	}

}
