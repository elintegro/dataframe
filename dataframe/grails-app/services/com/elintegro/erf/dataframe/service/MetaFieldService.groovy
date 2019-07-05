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

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.db.fields.FKMetaData
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.dataframe.db.types.DataType
import grails.plugin.cache.Cacheable
import grails.util.GrailsNameUtils
import groovy.util.logging.Slf4j
import org.apache.commons.lang.ClassUtils
import org.grails.core.DefaultGrailsDomainClass
import org.grails.core.exceptions.InvalidPropertyException
import org.grails.datastore.mapping.model.types.*
import org.grails.orm.hibernate.cfg.GrailsDomainBinder
import org.grails.orm.hibernate.cfg.HibernatePersistentEntity
import org.hibernate.persister.entity.AbstractEntityPersister
import org.springframework.orm.hibernate5.SessionFactoryUtils

import java.sql.*

@Slf4j
public class MetaFieldService {

	static final int DEFAULT_PK_FIELD_LENGTH = 8; 
	static final String MY_SQL_AUTO_INCREMENT = "auto_increment";
	static final String MYSQL_PRIMARY_KEY = "PRI";


	def appContextService
	def grailsApplication
	def sessionFactory
	def grailsDomainClassMappingContext


	Map<String, Map<String, MetaField>> dbMetaData = new HashMap<String, Map<String, MetaField>>();
	Map<String, Map<String, MetaField>> dfMetaData = new HashMap<String, Map<String, MetaField>>();
	
	/**
	 *
	 * @param parsedHql
	 * @return
	 * The method uses ParsedHql object(with hql setted)  to build meta data from the selection of fields in hql query.
	 */
	public List<MetaField> getMetaDataFromFields(ParsedHql parsedHql, String labelPrefix){
		return getMetaDataFromFields(parsedHql, labelPrefix, false);
	}
	
	public List<MetaField> getMetaDataFromFields(ParsedHql parsedHql, String labelPrefix, boolean reload){
		if (!dfMetaData.containsKey(labelPrefix) || reload){			
			List<MetaField> metaFields = new ArrayList<MetaField>()
			parsedHql.fields.each{key, fieldProps ->
				def fieldName = fieldProps.name
				metaFields.add(getMetaField(fieldProps, fieldName, labelPrefix));
			}			
			dfMetaData.get(labelPrefix, metaFields);
		}
		return dfMetaData.get(labelPrefix)
	}

			
	public Map<String, MetaField> getMetaTable(String table){
		if(!dbMetaData.containsKey(table)){
			loadTableMetaFields(table);
		}
		if(!dbMetaData.containsKey(table)){
			throw new DataframeException(String.format("Table  %s does not exist in the database ", table));
		}
		return dbMetaData.get(table);		
	}
	
	public MetaField getMetaColumn(String table, String column){
		Map<String, MetaField> metaTable = getMetaTable(table);
		if(!metaTable.containsKey(column)){
			throw new DataframeException(String.format("Column %s of table  %s does not exist in the dataframe or database ", column, table));
		}
		return metaTable.get(column);
	}

	//SELECT tc.TABLE_NAME, kcu.COLUMN_NAME, kcu.REFERENCED_TABLE_NAME, kcu.REFERENCED_COLUMN_NAME, tc.CONSTRAINT_NAME, kcu.ORDINAL_POSITION, kcu.POSITION_IN_UNIQUE_CONSTRAINT
	@Cacheable("loadFKMetaData")
	private def loadFKMetaData(Connection con, String schema, String table){
		Map FKConstraints = [:]
		def params = [schema: schema, table: table]
		Statement stmt = con.createStatement()

		final ResultSet rs =stmt.executeQuery( getFKMetadataSQL(schema, table))

		try {
			while (rs.next()) {
				FKMetaData fkMeta = new FKMetaData(
						schema: schema
						, tableName: rs.getString("TABLE_NAME")
						, columnName: rs.getString("COLUMN_NAME")
						, referencedTableName: rs.getString("REFERENCED_TABLE_NAME")
						, referencedColumnName: rs.getString("REFERENCED_COLUMN_NAME")
						, constraintName: rs.getString("CONSTRAINT_NAME")
						, ordinalPosition: rs.getInt("ORDINAL_POSITION")
						, positionInUniqueConstraint: rs.getInt("POSITION_IN_UNIQUE_CONSTRAINT")
				);

				FKConstraints.put(fkMeta.getKey(), fkMeta)
			}
		}catch(Exception ee){
			log.debug("SQL Exception, when retrieving FK metadata  : " + ee)
		} finally {
			rs.close();
			return FKConstraints;
		}
	}
	/*
	 * Cretae MetaField's map for a table from the schema in the Database: TODO: make it separate for different DBs (now it is for mySql)
	 */
	private void loadTableMetaFields(String table) {

		Map<String, MetaField> tableMetaData = new HashMap<String, MetaField>();
		dbMetaData.put(table, tableMetaData);

		String sql = getMetaSqlForTable(table);
		Connection con = SessionFactoryUtils.getDataSource(sessionFactory).getConnection()

		Statement stmt = con.createStatement()

		try {

			ResultSet rs = stmt.executeQuery(sql)
		
			while (rs.next()) {
				MetaField mtf = getMetaFieldFromDb(rs, table);
				tableMetaData.put( mtf.columnName, mtf);
				
			}		
		} catch (SQLException e ) {
			throw new DataframeException(String.format("SQL Problem to build Meta Data for the table %s : Exception: %s", table, e));
		} catch (Exception e ) {
			throw new DataframeException(String.format("Problem to build Meta Data for the table %s : Exception: %s", table, e));
		} finally {
			if (stmt != null) { stmt.close(); }
		}

		//Enhance Table metaData with FK if exists
		String schema = con.getMetaData().getAt("database")
		Map FKConstraints = loadFKMetaData(con, schema, table)
		Map tableMetaData_ = dbMetaData.get(table);
		tableMetaData_.each {field, metaField ->
			String fkMetaKey = FKMetaData.buildKey(schema, table, field)
			if(FKConstraints.containsKey(fkMetaKey)){
				MetaField mf = metaField
				mf.fk = true
				mf.setFkMetaData(FKConstraints.get(fkMetaKey))
			}

		}
	}

	private String getMetaSqlForTable(String tableName){
		return "${MetaField.metaSqlField}\n${MetaField.metaSqlFrom}\n WHERE TABLE_NAME = '$tableName' ";
	}
	
	/**
	 *
	 * @param metaField
	 * @param property
	 * @param stmt
	 * The method uses meta field object ,grails  property object, field name as in the hql, tablename and Statement object and builds the meta filed object.
	 */
	private MetaField getMetaFieldFromDb(ResultSet rs, String tableName){


		MetaField metaField = new MetaField();
			metaField.tableName = tableName;
			metaField.columnName = rs.getString("COLUMN_NAME");
			metaField.defaultValue = rs.getString("COLUMN_DEFAULT");
			metaField.notNull = rs.getString("IS_NULLABLE").equals("NO");
			try{
				metaField.length = rs.getBigDecimal("CHARACTER_MAXIMUM_LENGTH");
			}catch(Exception e){
				log.error("Problem to convert a ength of %s for table %s into BigDecimal", metaField.columnName, tableName);
			}
			
			metaField.charset = rs.getString("CHARACTER_SET_NAME");
			metaField.collation = rs.getString("COLLATION_NAME");
			def pkVAl = rs.getString("COLUMN_KEY");
			metaField.pk = MYSQL_PRIMARY_KEY.equals(rs.getString("COLUMN_KEY")); //TODO: its MySql specific, it should be db indepandant
			
			if(metaField.pk){
				metaField.length = DEFAULT_PK_FIELD_LENGTH;
			}
									
			String autoIncr = rs.getString("EXTRA");
			metaField.autoincrement = MY_SQL_AUTO_INCREMENT.equals(autoIncr);
			metaField.comment = rs.getString("COLUMN_COMMENT");
			metaField.nPrecision = rs.getInt("NUMERIC_PRECISION");
			metaField.nScale = rs.getInt("NUMERIC_SCALE");
			metaField.columnType = rs.getString("COLUMN_TYPE");
			if((metaField.length == null || metaField.length == 0) && metaField.columnType != null && metaField.columnType.length() > 0 && metaField.columnType.indexOf('(') > 0){
				int openBr = metaField.columnType.indexOf('(');
				int closeBr = metaField.columnType.indexOf(')');
				String fldLength = metaField.columnType.substring(openBr + 1, closeBr);
				metaField.length = BigDecimal.valueOf(Integer.valueOf(fldLength));
			}
			
			if(metaField.length == null){
				log.debug("");
			}
			return metaField;
	}
			
	/**
	 *
	 * @param domain
	 * @param field
	 * @param domainClass
	 * @return
	 * The method uses domain class name and field name used in hql query to retrieve the meta data.
	 */
	private MetaField getMetaField(def fieldprops, String fieldName, String labelPrefix){
		//fields.put(fldkey, ["domain" : hqlDomains[fldOwner], "alias" : alias, "domainAlias" : fldOwner, "name" : fieldName, "fldNmAlias": fldNmAlias])
		fieldName = fieldName.replaceAll("\\s+","")
				
		MetaField metaField = new MetaField(fieldName)
				
		metaField.domain = fieldprops.domain
		metaField.domainAlias = fieldprops?.domainAlias
		
		def domain = metaField.domain?.value;
//		Class clazz = domain?.clazz
		Class clazz = domain.getJavaClass()
		def hibernateMetaClass = sessionFactory.getClassMetadata(clazz)
		def tableName=hibernateMetaClass.getTableName()
		metaField.tableName = tableName;
		if(tableName){
			metaField.tableName = tableName;
		}else{
			metaField.tableName = metaField.domain.tablename; //TODO: we might not need it ...
		}

		metaField.alias = fieldprops.get("alias");
		metaField.fldNmAlias = fieldprops.get("fldNmAlias");
		def persistentEntity = domain
		def props = persistentEntity.persistentProperties
		def simpleFieldName		
		if(fieldName.indexOf(".")>-1){
			def data = fieldName.trim().split(/\./)
			simpleFieldName = data[0].trim()
		}else{
			simpleFieldName = fieldName
		}
							
		def property
		try{
			def result = GrailsDomainBinder.getMapping(domain).columns
			property = persistentEntity.getPropertyByName(simpleFieldName)
			metaField.columnName = metaField.domain.getDBFieldName(simpleFieldName)

		}catch(InvalidPropertyException e){
			log.error(e)
		}		
				
		metaField.unsigned = false

		if(hasProperty(simpleFieldName, domain)){
			metaField.domainClass = property.type
			def className = property.type.name.split(/\./)
			metaField.dataType = MetaField.getDataType(getTypePropertyName(property.type)) //TODo Database specific!!!
			metaField.domainDataType = getTypePropertyName(property.type)
			 
			metaField.labelCode = buildFieldLabelCode(fieldprops, fieldName, labelPrefix) //TODO - not a good place to calculate the label, it should be
		}

		if(isAssociation(property)){
			if(property instanceof ManyToOne || property instanceof OneToOne){
				MetaField metaDbField = getMetaColumn(metaField.tableName, metaField.columnName)
				metaField.fk = metaDbField.fk
				metaField.fkMetaData = metaDbField.fkMetaData
//				def subDomain = new DefaultGrailsDomainClass(property.type)
				metaField.domainClass = property.type
				metaField.dataType = DataType.ASSOCIATION
			}
		}else{
		
			fillMetaPropertiesFromDb(metaField);

		}
        log.info "metafield:" + metaField
		return metaField
	}





	String getFieldsTmp(domain, fieldHibernetName) {

		def domainClazz = grailsApplication.getClassForName(domain.getJavaClass().name)
		def clazzMetadata = sessionFactory.getClassMetadata(domainClazz)
		//AbstractEntityPersister abstractEntityPersister = (AbstractEntityPersister) clazzMetadata

/*
		def grailsDomainClazz = new DefaultGrailsDomainClass(domain.Clazz)
		def grailsDomainClazzProperties = domainClazz.getProperties()
*/

		//def fieldDbName = abstractEntityPersister.getPropertyColumnNames(fieldHibernetName)

		String[] fieldDbNameArr = clazzMetadata.getPropertyColumnNames(fieldHibernetName)
		String fieldDbName = fieldHibernetName;
		if (fieldDbNameArr != null && fieldDbNameArr.size() > 0) {
			fieldDbName = fieldDbNameArr[0]
			if(fieldDbNameArr.size() > 1){
				log.debug("getPropertyColumnNames returns more then 1 value!: " + org.springframework.util.StringUtils.arrayToCommaDelimitedString(fieldDbNameArr))
			}
		}

		return fieldDbName
}



	private String buildFieldLabelCode(def fieldProps, String fieldName, String labelPrefix ) {
		StringBuilder sb = new StringBuilder()
		sb.append(labelPrefix)
		sb.append(".")
		sb.append(fieldProps.domainAlias)
		sb.append(".")
		sb.append(fieldName)
		return sb.toString()
	}

		
	private void fillMetaPropertiesFromDb(MetaField metaField){
		MetaField metaDbField = getMetaColumn(metaField.tableName, metaField.columnName)
		//Merge between those two:
		metaField.merge(metaDbField)
		
	}
	
	public List<String> getPk(List<MetaField> fldmetaData){
		List<String> res = new ArrayList<String>();
		for (MetaField fld: fldmetaData){
			if(fld.isPk()){
				res.add(fld.name);
			}
		}
		return res;		
	}

    private static Boolean hasProperty(String propertyName, HibernatePersistentEntity domainClass){
        if (domainClass.propertiesByName.get(propertyName)){
            return true
        }
        return false
    }

	public static String getTypePropertyName(Class<?> aClass){
		String shortTypeName = getShortClassName(aClass)
		return shortTypeName.substring(0,1).toLowerCase(Locale.ENGLISH) + shortTypeName.substring(1)
	}

	public static String getShortClassName(Class<?> aClass){
		return ClassUtils.getShortClassName(aClass)
	}

	public static boolean isEnumType(def prop){
		if (prop?.type?.getEnumConstants()){ //todo: find special condition to check enum
			return true
		}
		return false
	}

	public static boolean isAssociation(def obj){
		if (obj instanceof Association){return true}
		return false
	}

	public static boolean isManyToMany(def obj){
		if (obj instanceof ManyToMany){return true}
		return false
	}

	public static boolean isOneToMany(def obj){
		if (obj instanceof OneToMany){return true}
		return false
	}

	public static boolean isOneToOne(def obj){
		if (obj instanceof OneToOne){return true}
		return false
	}

	public static boolean isManyToOne(def obj){
		if (obj instanceof ManyToOne){return true}
		return false
	}

	public static boolean isBidirectional(def obj){
		if (isAssociation(obj) && (obj.associatedEntity != null) && (obj.referencedPropertyName != null)){return true}
		return false
	}

	static String buildKey(String shema, String tableName, String columnName){
		return String.format("%s-%s-%s", shema, tableName, columnName)
	}

	private static getFKMetadataSQL(String schema, String table) {
					"""
							SELECT tc.TABLE_NAME, kcu.COLUMN_NAME, kcu.REFERENCED_TABLE_NAME, kcu.REFERENCED_COLUMN_NAME, tc.CONSTRAINT_NAME, kcu.ORDINAL_POSITION, kcu.POSITION_IN_UNIQUE_CONSTRAINT
							FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc
							JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu ON tc.CONSTRAINT_NAME = kcu.CONSTRAINT_NAME AND tc.TABLE_NAME = kcu.TABLE_NAME AND tc.TABLE_SCHEMA = kcu.TABLE_SCHEMA
							WHERE tc.CONSTRAINT_SCHEMA = '${schema}' AND tc.TABLE_NAME = '${table}' AND tc.CONSTRAINT_TYPE = 'FOREIGN KEY'
							ORDER BY CONSTRAINT_NAME, POSITION_IN_UNIQUE_CONSTRAINT
					"""
	}
}

