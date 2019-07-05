/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.db.fields

import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.db.types.DataType
import org.grails.datastore.mapping.model.PersistentEntity

/**
 * 
 * @author Eugenel
 *
 */

class MetaField {
	Class domainClass = null; // if not null, then this is a simple field
	String name;  		//Name from domain
	String columnName;  	//Column name in underlying table
	DomainClassInfo domain;		//Domain name from HQL
	String domainAlias;  //Table Alias from HQL
	String tableName;    //Table Name from underlying SQL
	//Class domainClass 	//TODO domainClass
	String alias;
	String fldNmAlias; //column alias from hql
	String tableAlias; 	//alias from underlying SQL
	BigDecimal length;
	


	DataType dataType;
	String domainDataType;
	
	String labelCode;
	String columnType;
	
	//Database fields:
	String defaultValue;
	boolean pk;
	boolean fk = false
	FKMetaData fkMetaData = null

	boolean notNull;
	boolean unsigned;
	boolean autoincrement;
	boolean zerofill;
	String charset;
	String collation;
	String comment;
	String type;
	int nScale = 0;
	int nPrecision;	
	
	//TODO: extract it for MySQL, the SQL may be different for other DBs ...
	static String metaSqlField = "SELECT COLUMN_NAME, COLUMN_DEFAULT, IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_SET_NAME, COLLATION_NAME, COLUMN_TYPE, COLUMN_KEY, EXTRA, COLUMN_COMMENT, NUMERIC_PRECISION, NUMERIC_SCALE "
	static String metaSqlFrom = " FROM INFORMATION_SCHEMA.COLUMNS "

	//Map specificProperties
	Map addFieldDef = [:];
	
	boolean isReadOnly(){
		return (addFieldDef.containsKey("readonly") && addFieldDef.get("readonly")) 
	}
	
	MetaField(name){
		this.name=name
	}
	  
	MetaField(){
	}
		
	public String getMetaSql(String tableName, String colname){
		"${MetaField.metaSqlField}\n${MetaField.metaSqlFrom}\n WHERE TABLE_NAME = '$tableName' AND COLUMN_NAME='$colname'"
	  }

	public String getMetaFKSql(String tableName, String colname){
		"${MetaField.metaSqlField}\n${MetaField.metaSqlFrom}\n WHERE TABLE_NAME = '$tableName' AND COLUMN_NAME='$colname'"
	}

	public boolean isFK(){
		return fk
	}

	public FKMetaData getFKMetaData(){
		return fkMetaData
	}


	/**
	 * 
	 * @param typeName
	 * @return
	 * The methods uses string data type name  and return enum object for that respective type.
	 */
	static  DataType getDataType(String typeName){
		if("string".indexOf(typeName.toLowerCase())>-1){
			return DataType.VARCHAR
		}else if("date".indexOf(typeName.toLowerCase())>-1){
			return DataType.DATE
		}else if("double".indexOf(typeName.toLowerCase())>-1){
			return DataType.DOUBLE
		}else if("float".indexOf(typeName.toLowerCase())>-1){
			return DataType.DOUBLE
		}else if("int".indexOf(typeName.toLowerCase())>-1){
			return DataType.BIGINT
		}else if("short".indexOf(typeName.toLowerCase())>-1){
			return DataType.SHORT
		}else if("long".indexOf(typeName.toLowerCase())>-1){
			return DataType.BIGINT
		}else if("boolean".indexOf(typeName.toLowerCase())>-1){
			return DataType.BOOLEAN
		}else if("longtext".indexOf(typeName.toLowerCase())>-1){
			return DataType.LONGTEXT

		}else{
			return DataType.ASSOCIATION
		}
	}
	
	@Override
	public String toString() {
		return String.format("TableName: %s Column Name: %s   DataType: %s    DomainClass: %s", tableName, columnName , dataType, domainClass?.name);
	}
	
	//TODO: please finish implementation
	public Map toMap() {
		return [name: name, columnName: columnName, domain: domain, domainAlias: domainAlias, tableName: tableName, domainClass: domainClass,
			alias: alias, fldNmAlias:fldNmAlias, tableAlias: tableAlias,  length: length, dataType: dataType, defaultValue: defaultValue, pk: pk, notNull: notNull,
			unsigned: unsigned, autoincrement: autoincrement, zerofill: zerofill, charset: charset, collation: collation, comment: comment, labelCode: labelCode
				//,fk: fk, fkMetaData: fkMetaData
		]
	}

	void merge(MetaField metaDbField){
		this.defaultValue = metaDbField.defaultValue;
		this.notNull = metaDbField.notNull;
		this.length = metaDbField.length;
		this.charset = metaDbField.charset;
		this.collation = metaDbField.collation;
		this.pk = metaDbField.pk;
		this.autoincrement = metaDbField.autoincrement;
		this.comment = metaDbField.comment;
		this.nPrecision = metaDbField.nPrecision;
		this.nScale = metaDbField.nScale;
		this.columnType = metaDbField.columnType;
		this.length = metaDbField.length;
		this.fk = metaDbField.fk;
		this.fkMetaData = metaDbField.fkMetaData;
	}

}