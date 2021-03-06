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
import org.springframework.orm.hibernate3.SessionFactoryUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

class ParsedHql {

	static final int HQL_WHOLE_MATCH = 0
	static final int HQL_FIELDS_CLAUSE = 1
	static final int HQL_FROM_CLAUSE = 2
	//static final int HQL_JOIN_CLAUSE = 3
	static final int HQL_WHERE_CLAUSE = 3


	String hql
	String sqlString

	String fieldStr
	String fromStr
	String fromStr_
	String joinStr
	List<JoinParsed> joins = []
	String groupByStr
	String orderByStr
	String whereStr
	String groupBy
	String orderBy

	String selectStr
	String whereClauseStr
	String joinClauseStr
	String groupByClauseStr
	String orderByClauseStr



	String[] fieldsArr
	Map fields = new LinkedHashMap()
	Map aliasDomainFields = [:]
	Map<String, DomainClassInfo> hqlDomains = [:]
	Map hqlDomainsNameMap = [:]
	Map indexOfFileds = [:]
	def  grailsApplication
	def sessionFactory
	Map namedParameters = [:]
	//def hqlExtractRegex = /(?i)select\s+(?<select>.+?)(?i)from\s+(?<from>.+?)(?<join>(?:(?i)right\s+|(?i)left\s+)?(?:(?i)outer\s+|(?i)inner\s+)?(?i)join\s+(?:.+))(?i)where\s+(?<where>.+)/
	final static String baseRegexPart = /^select\s+(?<select>.+)from\s+(?<from>.+)/
	final static String joinRegexLeftRightPart = /(?:\s+right\s+|\s+left\s+)/
	final static String joinRegexInnerOuterPart = /(?:\s+outer\s+|\s+inner\s+)/
	final static String joinRegexPart = /(?:join\s+(?<joincond>.+))/
	final static String whereRegexPart = /(?:where\s+(?<where>.+))/
	final static String groupbyRegexPart = /(?<groupby>group\s+by\s+(?<groupbyClause>.+))/
	final static String orderbyRegexPart = /(?<orderby>order\s+by\s+(?<orderbyClause>.+))/
	def hqlExtractRegex;

	//def hqlExtractRegex = /select\s+(?<select>.+?)from\s+(?<from>.+?)(?<join>(?:right\s+|left\s+)?(?:outer\s+|inner\s+)?join\s+(?:.+))?(?:where\s+(?<where>.+?))?/
	Pattern hqlPattern
	def joinRegexClause = /((?i)right\s+|(?i)left\s+)?((?i)outer\s+|(?i)inner\s+)?(?i)join\s+/
	String dataframeName

	ParsedHql(){
		print "Emty constructor, called for tests!"
	}

	private void init(String hql, def grailsApplication, def sessionFactory, String dataframeName){
		this.hql = hql?hql.trim().replaceAll("(?!.)\\s", ""):"";
		this.grailsApplication=grailsApplication
		this.sessionFactory = sessionFactory
		this.dataframeName = dataframeName
		parseHQLInit()
	}

	ParsedHql(String hql, def grailsApplication, def sessionFactory){
		init(hql, grailsApplication, sessionFactory, "unknown")
	}

	ParsedHql(String hql, def grailsApplication, def sessionFactory, String dataframeName){
		init(hql, grailsApplication, sessionFactory, dataframeName)
	}

	private buildRegexToParseHql(){
		StringBuilder hqlExtractRegexSb = new StringBuilder();
		hqlExtractRegexSb.append(baseRegexPart)

		//join
		String joinExist = hql.toLowerCase().find(/\s+join\s+/)
		if(joinExist) {
			hqlExtractRegexSb.append(/(?<join>/)
			String joinRigntLeftExist = hql.toLowerCase().find(joinRegexLeftRightPart)
			if (joinRigntLeftExist) {
				hqlExtractRegexSb.append(joinRegexLeftRightPart)
			}
			String joinInnerOuterExist = hql.toLowerCase().find(joinRegexInnerOuterPart)
			if (joinInnerOuterExist) {
				hqlExtractRegexSb.append(joinRegexInnerOuterPart)
			}
			hqlExtractRegexSb.append(joinRegexPart).append(/)/)
		}

		String whereExists = hql.toLowerCase().find(/\s+where\s+/)
		int whereInd = 0
		if(whereExists){
			hqlExtractRegexSb.append(whereRegexPart)
			whereInd = hql.toLowerCase().indexOf(whereExists)
		}

		String groupByClause = hql.toLowerCase().find(/group\s+by/)
		int groupByInd = 0
		if(groupByClause){
			groupByInd = hql.toLowerCase().indexOf(groupByClause)
			if(groupByInd > whereInd){
				hqlExtractRegexSb.append(groupbyRegexPart)
			}else{
				throw new DataframeException(String.format("Dataframe %s: Group by clause should be after WHERE clause and before ORDER BY clause in dataframe's hql", dataframeName))
			}
		}

		String orderByClause = hql.toLowerCase().find(/order\s+by/)
		if(orderByClause){
			int orderByInd = hql.toLowerCase().indexOf(orderByClause)
			if(orderByInd > whereInd && orderByInd > groupByInd ){
				hqlExtractRegexSb.append(orderbyRegexPart)
			}else{
				throw new DataframeException(String.format("Dataframe %s: Order by clause should be after WHERE and Group BY clauses in dataframe's hql", dataframeName))
			}
		}
		hqlExtractRegex = hqlExtractRegexSb.append("\$").toString()

		hqlPattern = Pattern.compile(hqlExtractRegex, Pattern.CASE_INSENSITIVE);
	}

	private def parseHQLInit(){

		if(hql){
			buildRegexToParseHql()
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

			//TODO: this code should be depricated and extractParts method will do the job!
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

			if (!extractParts(hql)){
				throw new DataframeException("There is a parsing error of hql: ${hql}")
			}

			//TODO: END of the depricated fragment

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


	public boolean extractParts(String hql) throws DataframeException{
		try {
			String hqlTemp = hql.replaceAll(/(\s+)/, " ");

			Matcher hqlMatcher = hqlPattern.matcher(hqlTemp);
			boolean isMatchs = hqlMatcher.matches();

			if (isMatchs && hqlMatcher?.hasGroup()) {
				//TODO" fieldStr_ and fieldStr_ should be replaced to fieldStr fieldStr
				if(hqlMatcher.groupCount() < 2) {
					throw new DataframeException(dataframeName, "Select statement must have at least both Select and From clauses!")
				}

				fieldStr = extractClause(hqlMatcher,"select")
				fromStr_ = extractClause(hqlMatcher,"from")
				whereStr = extractClause(hqlMatcher,"where")
				whereClauseStr = extractClause(hqlMatcher,"whereClause")
				joinStr = extractClause(hqlMatcher, "join")
				joinClauseStr = extractClause(hqlMatcher, "joinClause")
				groupByStr = extractClause(hqlMatcher, "groupby")
				groupByClauseStr = extractClause(hqlMatcher, "groupbyClause")
				orderByStr = extractClause(hqlMatcher, "orderby")
				orderByClauseStr = extractClause(hqlMatcher, "orderbyClause")

				extractJoins(joinStr)

				//fromStr = fromStr_

				return true
			}
		}catch(IllegalStateException exp){
			throw new DataframeException("No match found for sql ${hql} Exception: ${exp}")
		}
		return false
	}

	String extractClause(Matcher hqlMatcher, String clause){
		return extractClause(hqlMatcher, clause, false)
	}
	String extractClause(Matcher hqlMatcher, String clause, boolean mandatory){
		try {
			return hqlMatcher.group(clause)?.trim()
		}catch(IllegalArgumentException iae){
			if(mandatory) {
				throw new DataframeException(dataframeName, "The ${clause} clause is mandatory!", iae)
			}else{
				return null
			}
		}
	}

	public extractJoins(String joinStr){
		if(StringUtils.isEmpty(joinStr)){return}

		String[] joinsStringArr = joinStr.split(joinRegexClause)
		String joinStr_ = joinStr
		joinsStringArr?.each{ joinClause ->
			if(!StringUtils.isEmpty(joinClause)) {
				int joinClauseInd = joinStr_.indexOf(joinClause)
				String joinElementWordWithOption = joinStr_.substring(0, joinClauseInd) //TODO: maybe -1 here?
				JoinParsed jp =new JoinParsed(joinElementWordWithOption, joinClause, this)
				joins.add(jp)
				joinStr_ = joinStr_.substring(joinElementWordWithOption.length() + joinClause.length())
				fromStr_ += ",${jp.targetDomain} ${jp.targetDomain}"
			}
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
			String domainAlias = tblDtl.length>1?tblDtl[1]:tblDtl[0];
			String tableName = tblDtl[0];
			String domainName = tblDtl[0];
			DomainClassInfo dci = getDomainFromPath(domainName, domainAlias, tableName, parentDomain)
			String domainShortName = dci.getSimpleDomainName();
			hqlDomains.put(domainAlias, dci);
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
				def domain = hqlDomains.values().first()
				String domainAlias = domain.getDomainAlias();
				fields.put(fldkey, ["domain": domain, "alias": alias, "domainAlias" : domainAlias, "name" : fieldName, "tableName" : fldOwner, "fldNmAlias": fldNmAlias])
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
