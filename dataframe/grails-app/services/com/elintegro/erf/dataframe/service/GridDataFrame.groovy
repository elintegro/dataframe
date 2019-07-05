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

import grails.core.GrailsApplication
import grails.core.GrailsClass
import org.apache.commons.collections.map.LinkedMap

class GridDataFrame  {

	final static String DOMAIN_ENTRY = "domain", FIELDS_ENTRY = "fields", ALIAS_ENTRY = "alias", NAME_ENTRY = "name", DOMAIN_ALIAS_ENTRY = "domainalias"
	
	String name
	String gridIdInJS = "gridName" //The name of placeholder in supportJScriptSource for the grid name (or grid id). Default value is "gridName"
	String divIdInJS = "divId" //The name of placeholder in supportJScriptSource for the div Id. Default value is "divId"
	String hql
	String gridFieldStr
	String gridFromStr
	String[] gridFieldArr
	Map gridFields = new HashMap()
	GridDataFrame parent
	
	Map gridDomains = new HashMap()
	String gridFieldJavaScript
	GrailsClass[] domains
	Map domainsMap = [:]
	String scripts
	String supportJScriptSource
	
	String url
	boolean sortable = false
	Map gridProperties = ["width": 840, "sortable":true, "pageable": true, "autoheight": true]
	private boolean initialized = false
	
	//services:
	def javascriptService	
	GrailsApplication grailsApplication
	
	
	// bean definitions:
	/**
	 * 	Example of the value of addColumnLastDef:
	 *
	 * 	addColumnLastDef = [
			[column:"discount", values:[datafield:"'discount'","text":"'Discount'", "width": "90", "height": '25', "cellsformat": "'p'", "columntype": "'numberinput'", "cellsalign": "'right'", "initeditor": "discountEditor", "editable":"false"]],
			[column:"price",    values:["datafield":"'price'","text":"'Final Price'", "width": "90", "height": '25', "cellsformat": "'c2'", "columntype": "'numberinput'", "cellsalign": "'right'", "initeditor": "priceEditor", "editable":"false"]	]
		]
	*/
	def addColumnLastDef
	
	/**
	* 	Example of the value of addColumnFirstDef:
   *	addColumnFirstDef = [
	   [column:"discount", values:[datafield:"'discount'","text":"'Discount'", "width": "90", "height": '25', "cellsformat": "'p'", "columntype": "'numberinput'", "cellsalign": "'right'", "initeditor": "discountEditor", "editable":"false"]],
	   [column:"price",    values:["datafield":"'price'","text":"'Final Price'", "width": "90", "height": '25', "cellsformat": "'c2'", "columntype": "'numberinput'", "cellsalign": "'right'", "initeditor": "priceEditor", "editable":"false"]	]
   ]
   */
   def addColumnFirstDef

   /**
   * Example of the value of addColumnAfterDef:
   *
   * addColumnAfterDef = [
	  [column:"discount", after: "name",  values:[datafield:"'discount'","text":"'Discount'", "width": "90", "height": '25', "cellsformat": "'p'", "columntype": "'numberinput'", "cellsalign": "'right'", "initeditor": "discountEditor", "editable":"false"]],
	  [column:"price", after: "discount", values:["datafield":"'price'","text":"'Final Price'", "width": "90", "height": '25', "cellsformat": "'c2'", "columntype": "'numberinput'", "cellsalign": "'right'", "initeditor": "priceEditor", "editable":"false"]	]
	 ]
   */
  def addColumnAfterDef

  /**
  * 	Example of the value of addPropertiesToColumnDef:
  *	addPropertiesToColumnDef = [
	 [column:"discount", values:[datafield:"'discount'","text":"'Discount'", "width": "90", "height": '25', "cellsformat": "'p'", "columntype": "'numberinput'", "cellsalign": "'right'", "initeditor": "discountEditor", "editable":"false"]],
	 [column:"price",    values:["datafield":"'price'","text":"'Final Price'", "width": "90", "height": '25', "cellsformat": "'c2'", "columntype": "'numberinput'", "cellsalign": "'right'", "initeditor": "priceEditor", "editable":"false"]	]
	]
  */
  def addPropertiesToColumnDef

  
  /**
  * 	Example of the value of addPropertiesToGridDef:
  *	addPropertiesToGridDef =
	 ["width":"900", "sortable":"false", "pagesizeoptions": "['3', '6', '9']", "columnsresize":"true", "rowsheight": "60"]
  */
  def addPropertiesToGridDef

 
   def editableFieldsDef  		//example: editableFieldsDef = ["field1", "field2"]
   def readOnlyFieldsDef		//example: editableFieldsDef = ["field1", "field2"]
   def sortableFieldsDef		//example: editableFieldsDef = ["field1", "field2"]
   def unsortableFieldsDef		//example: editableFieldsDef = ["field1", "field2"]
   def filterableFieldsDef		//example: editableFieldsDef = ["field1", "field2"]
   def nonfilterableFieldsDef	//example: editableFieldsDef = ["field1", "field2"]
   def groupableFieldsDef		//example: editableFieldsDef = ["field1", "field2"]
   def nongroupableFieldsDef	//example: editableFieldsDef = ["field1", "field2"]

	//End of bean definitions
		
	Map<String, Map<String, String>> gridColumns
	List<String> gridColumnsList = new ArrayList<String>()
	
	String supportJScript = ""
	
	GridDataFrame(){		
		println "Costructing GridDataFrame! grails grailsm App Context "		
	}
	
	GridDataFrame(String name, String url, String hql, GrailsClass[] domainsClasses, JavascriptService javascriptService){
		
		this.javascriptService = javascriptService
		this.name  = name
		this.url = url
		this.hql = hql.trim()
/*		def orderRequestDomain = domainsClasses.find{
			def nm = it.getShortName()
			if(nm == "OrderRequest"){
				return it
			}
		}
		orderRequestDomain.class.declaredFields.each{
			println it
		}*/
		
		init(domainsClasses)
	}

	
	
	
	void setSupportJScript(String addScripts){
		supportJScript = addScripts
	}
	
	
	
	def afterPropertiesSet(){
		println "after Property set has been called"
	}
	
	
	/**
	 * Overrides columns, properties built by default, based on HQL and Meta data
	 * @param domainsClasses
	 * @return
	 */
	
	def init(GrailsApplication grailsApplication){
		GrailsClass[] domainsClasses = grailsApplication.domainClasses
		init(domainsClasses)
	}
	
	def init(GrailsClass[] domainsClasses){
			
		if(initialized == false){
							
			this.domains = domainsClasses
			populateDomainsMap()
			parseHQL()
			
			addColumnLastDef?.each{
				addColumnLast(it.column,it.values)
			}
			addColumnFirstDef?.each{
				addColumnFirst(it.column,it.values)
			}
			addPropertiesToColumnDef?.each{
				addPropertiesToColumn(it.column,it.values)
			}
			
			addColumnAfterDef?.each{
				addColumnAfter(yt.after, it.column,it.values)
			}
			
			addPropertiesToGrid(addPropertiesToGridDef)
					
			setEditableFields(editableFieldsDef)
			setReadOnlyFieldsDef(readOnlyFieldsDef)
			setSortableFieldsDef(sortableFieldsDef)
			setUnsortableFieldsDef(unsortableFieldsDef)
			setFilterableFieldsDef(filterableFieldsDef)
			setNonfilterableFieldsDef(nonfilterableFieldsDef)
			setGroupableFieldsDef(groupableFieldsDef)
			setNongroupableFieldsDef(nongroupableFieldsDef)
			
			
			def contents =  javascriptService.getJavaScript(supportJScriptSource)
			if(contents){
				//contents = javascriptService.replaceVariables(contents, "gridName", gridName) // this is a way to define template a variable and its value
				def divId = getDivId()
				contents = javascriptService.replaceVariables(contents, [(gridIdInJS): name, (divIdInJS):divId]) // this is a way to define bunch of template variables and values, in the javascript you can use them as $varName (for example var tt = something_$varName, it will be substituted with the defined  here value
				contents = javascriptService.applyMLPlaceholders(contents) // substitutes all ~{message.code} with the localized texts (make sure it is defined in messages.properties files)
				setSupportJScript(contents)
			}
			
			initialized = true
		}
	}
			
	/**
	 *
	 */
	private def populateDomainsMap(){
		if(domains && domains.size() > 0){
			
			for(GrailsClass domain: domains){
				def sn = domain.getShortName()
				domainsMap.put(sn, [(DOMAIN_ENTRY): domain, (FIELDS_ENTRY):[:]])
			}
		}
	}
	
	/**
	 *
	 */
	String getJqScripts(){
		
		init(grailsApplication)
		
		StringBuffer gridColumnsBuff = new StringBuffer()

		def jsGridClmnVar = getGridColumnsJSVar()
		
			
		def gridProp = getGridPropertiesJS()
			
		scripts = """
			<script>
                \$(document).ready(function () {
                // THIS IS THE GERF GRID
            var url = "$url";
            var datafields = $gridFieldJavaScript;
            var gridColumns = $jsGridClmnVar;
            // prepare the data
            var source =
            {
                datatype: "json",
                datafields: datafields,
                id: 'id',
                url: url,
                root: 'data',
                data: {'dataframe': 'participantsGrid', 'keys':'id=1'}
            };
            var dataAdapter = new \$.jqx.dataAdapter(source);

            \$("#$divId").jqxGrid(
                    {
                        width: $gridProperties.width,
                        source: dataAdapter,
                        columnsresize: true,
                        columns: gridColumns,
                        autoheight:true
                    });
        });
			</script>
		"""

	return scripts
	}
	
	private def getFromMap = { fromStr->
		String[] tables = fromStr.split(',')
		Map res = new LinkedMap()
		for(String tbl: tables){
			String[] tblDtl = tbl.trim().split(/\sas\s/)
			if(tblDtl.length == 1){
				tblDtl = tbl.trim().split(/\s/)
			}
			res.put(tblDtl.length>1?tblDtl[1]:tblDtl[0], tblDtl[0])
		}
		return res
	}

	private def fillFieldMap = { fldStr, domainMap ->
		gridFieldArr = fldStr.split(',')
		int i = 0
		
				
		StringBuffer dataFields = new StringBuffer()
		dataFields.append("[")
		for(String fld: gridFieldArr){
			String[] fldDtl = fld.trim().split(/\sas\s/)
			if(fldDtl.length == 1){
				fldDtl = fld.trim().split(/\s/)
			}
			def key = fldDtl[0].trim()
			def fldDtl2 = key.split(/\./)
			def domAlias = fldDtl2.length>1?fldDtl2[0]:""
			def fldName = fldDtl2.length>1?fldDtl2[1]:key
			if( fldName.isInteger() && domAlias.isInteger() ){
				key = fldDtl[1].trim()
				fldName = key
				domAlias = "gen";
			}
			//res.put(fldDtl[0], ["$ALIAS_ENTRY":fldDtl.length>1?fldDtl[1]:fldDtl[0],"$NAME_ENTRY":fldDtl2.length>1?fldDtl2[1]:fldDtl2[0] , "$DOMAIN_ALIAS_ENTRY": domAlias, "$DOMAIN_ENTRY":domainMap[domAlias] ])
			gridFields.put(key, [(ALIAS_ENTRY):fldDtl.length>1?fldDtl[1]:key,(NAME_ENTRY):fldName , (DOMAIN_ALIAS_ENTRY): domAlias, (DOMAIN_ENTRY):gridDomains.get(domAlias) ])
			gridFieldArr[i] = key
			if("id" != fldName){
				dataFields.append('{ name: "'+fldName+'" },')
			}
			
			buildGridColumn(fldName, key)
			
			i++
		}
		dataFields.append("];")
		gridFieldJavaScript = dataFields.toString()
		
	}

		
	private def parseHQL(){
		if(hql){
			def HqlFldMatcher = (hql =~ /^(?i)select\s(.+?)\s(?i)from\s.*/)
			def HqlFromMatcher = (hql =~ /.*(?i)from\s(.+?)\s(?i)where\s.*/)
			gridFieldStr = HqlFldMatcher.matches()?HqlFldMatcher[0][1]:""//
			gridFromStr = HqlFromMatcher.matches()?HqlFromMatcher[0][1]:""//
			gridDomains = getFromMap(gridFromStr)
			fillFieldMap(gridFieldStr, gridDomains)
			
		}
	}
	
	public def getFieldType(String domainName, String fieldName){
		def domain = domainsMap.get(domainName)?.get(DOMAIN_ENTRY)
		def fieldMap = domainsMap.get(domainName)?.get(FIELDS_ENTRY)
		if(!fieldMap || fieldMap.size == 0){
			def prop = domain?.getProperties()
			prop?.each {p->
				fieldMap.put(p.name, p)
			}
			
		}
		
		def fldProp = fieldMap?.get(fieldName)
		return fldProp?.type.getProperties().get("simpleName")
	}
		
	
	def getJsonResult = {resFromDB ->
		def jsonRet = resFromDB.collect {unit ->
			Map ret = new HashMap()
			for(int i =0; i< unit.length; i++){
				
				def fld = gridFieldArr[i]
				def fld1 = gridFields.get(fld)
				String fld2 = fld1.get(NAME_ENTRY)
				
				//(i==0)?ret.put("id", unit[0]):ret.put(fld2, unit[i])
				ret.put(fld2, unit[i])
			}
			return ret
		}
		return jsonRet
	}
	
	/**
	 * This builds gridColumn, based on Meta data information
	 * Developer can easily to enchance it with gridManipulation methods
	 * @param column
	 * @return
	 */
	private buildGridColumn(String column, String fieldName){
		if(!gridColumns || !gridColumnsList){
			 gridColumns = new LinkedMap<String, Map<String, String>>()
			 gridColumnsList = new ArrayList<String>()
		}
		
		Map<String, String> columnMap = new LinkedMap<String, String>()
		def title = column.substring(0,1).toUpperCase() + column.substring(1)
		columnMap.put("text", "'$title'")
		columnMap.put("datafield", "'$column'")
		columnMap.put("width", "120")
		def dmn = gridFields.get(fieldName).get(DOMAIN_ENTRY)
		def typeForName = getFieldType(dmn, column)
		if("boolean" == typeForName){
			columnMap.put("columntype", "'checkbox'")
		}
		// { text: 'Name', datafield: 'name', width: 120 },
		
		gridColumns?.put(column, columnMap)
		gridColumnsList?.add(column)
		
	}


	public addPropertyToGrid(String propName, String propValue){
		gridProperties.put(propName, propValue)
	}
	

 
	
	public addPropertiesToGrid(Map<String, String> prop){
		if(prop) gridProperties.putAll(prop)
	}

	
	
	public addPropertyToColumn(String column, String propName, String propValue){
		gridColumns?.get(column)?.put(propName, propValue)
	}
	


	public addPropertiesToColumn(String column, Map<String, String> prop){
		if(prop && column){
			gridColumns?.get(column)?.putAll(prop)
		}
	}


	public addColumnLast(String column, Map<String, String> prop){
		if(prop && column){
			gridColumns?.put(column, prop)
			if(!gridColumnsList.contains(column)){
				gridColumnsList?.add(column)
			}
		}
	}

	public addColumnFirst(String column, Map<String, String> prop){
		if(prop && column){
			gridColumns?.put(column, prop)
			if(!gridColumnsList.contains(column)){
				gridColumnsList?.add(0, column)
			}
		}
	}

	/**
	 *
	 */
	public addColumnAfter(String preceedingColumn, String column, Map<String, String> prop){
		if(prop && column && preceedingColumn){
			gridColumns?.put(column, prop)
			int indx = gridColumnsList?.indexOf(preceedingColumn)
			if(indx<0) indx = gridColumnsList?.size()
			if(!gridColumnsList.contains(column)){
				gridColumnsList?.add(indx, column)
			}
		}
	}


	public removeColumn(String column, Map<String, String> prop){
		gridColumns?.remove(column, prop)
		if(!gridColumnsList.contains(column)){
			gridColumnsList?.remove(column)
		}
	}



	public moveColumnAfter(String preceedingColumn, String column){
		int indx = gridColumnsList?.indexOf(preceedingColumn)
		int indxOld = gridColumnsList?.indexOf(column)
		if(indx>=0 && indxOld >=0 && indx != indxOld){
			gridColumnsList?.remove(indxOld)			
			gridColumnsList?.add(indx, column)
		}
	}

		
	public String getGridColumnsJSVar(){
		return getGridColumnsJSVar(false)
	}

	/**
	 *
	 * @return
	 */
	public String getGridPropertiesJS(){
				StringBuffer gridProp = new StringBuffer()
					gridProperties.collect{ prop ->
						def ky = prop.key
								def vl = prop.value
								gridProp.append("$ky: $vl ,")
					}
				return gridProp.toString()
			}
		

	/**
	 *
	 * @param isIdIncluded
	 * @return
	 */
	public String getGridColumnsJSVar(boolean isIdIncluded){
		StringBuffer gridClmn = new StringBuffer()
		if(gridColumns){
			gridClmn.append("[")
			gridColumnsList.collect{ clmn ->
				if(clmn != "id" || isIdIncluded){
					if("[" != gridClmn.toString()){
						gridClmn.append(", ")
					}
					gridClmn.append("{")
					int ii = 0
					gridColumns.get(clmn).collect{clmnProp ->
						def ky = clmnProp.key
						def vl = clmnProp.value
						if(ii != 0){
							gridClmn.append(", ")
						}
						gridClmn.append(" $ky: $vl")
						ii++
					}
					gridClmn.append(" }")
				}
			}
			gridClmn.append("];")
		}
		
		def ret = (gridClmn.toString() == null || gridClmn.toString() == "")?[]:gridClmn.toString()
		return ret
	}

	
	/**
	 * This method sets the certain grid property in mode "true", according to 2nd, "property" parameter, all fields are set to false mode, and the the specified fields are set to true mode
	 * use it when most of the fields are false, others - true
	 * use this after all columns are added to the grid, otherwise new fields will be in defualt state
	 * @param fields - fields to set to a specified mode
	 * @param property - property to set to true mode, possible properties:
	 *	sortable               false
	 *  filterable             false
	 *  groupable			   false
	 *  editable			   false
	 * @return void
	 */
	

	def setPropertyFields2True(fields, property){
		if(fields!=null && property!=null){
			this.addPropertiesToGrid([(property):"true"])
			gridColumns.each { field->
				addPropertiesToColumn(field.key, [(property):"false"])
			}
			fields.each{ field->
				addPropertiesToColumn(field, [(property):"true"])
			}
		}
	}
		
	/**
	* This method sets the certain grid property in true mode, all fields will remain in the default "true" mode, and the the specified fields will be set to in "false" mode
	* use it when most of the fields for this property should be true, others - false
	* @param fields - fields to set to read-only mode
	* @return void
	*/

   def setPropertyFields2False(fields, property){
	   if(fields!=null && property!=null){
		   this.addPropertiesToGrid([(property):"true"])
		   fields.each{ field->
			   addPropertiesToColumn(field, [(property):"false"])
		   }
	   }
   }

	
   /**
   * This method sets the grid in editable mode, all fields in read only mode, and the the specified fields in editable mode
   * use it when most of the fields are read only, others - editable
   * use this after all coumns are added to the grid, otherwise they will be in default, editable mode
   * @param fields - fields to set to editable mode
   * @return void
   */

  def setEditableFields(fields){
	  setPropertyFields2True(fields, "editable")
  }

  
  /**
  * This method sets the grid in editable mode, all fields will remain in the default editable mode, and the the specified fields in readonly mode
  * use it when most of the fields are editable, others - read-only
  * @param fields - fields to set to read-only mode
  * @return void
  */

 def setReadOnlyFields(fields){
	setPropertyFields2False(fields, "editable")
 }


 def setSortableFields(fields){
	 setPropertyFields2True(fields, "sortable")
 }


 def setUnsortableFields(fields){
   setPropertyFields2False(fields, "sortable")
 }


 def setFilterableFields(fields){
	 setPropertyFields2True(fields, "filterable")
 }


 def setNonfilterableFields(fields){
   setPropertyFields2False(fields, "filterable")
 }


 def setGroupableFields(fields){
	 setPropertyFields2True(fields, "groupable")
 }


 def setNongroupableFields(fields){
   setPropertyFields2False(fields, "groupable")
 }			

 def isInitialized(){
	 return initialized
 }

 def forceInitialization(){
	 initialized = false
 }

 def getDivId(){
	 "jqxgrid_$name"
 }
  
}