/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.layout.abs

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.widget.Widget
import grails.util.Holders
import groovy.util.logging.Slf4j

@Slf4j
abstract class Layout {
	

	static Map layouts = [:]
	
	private String name
	
	int numberFields = 0
	
	int numberOfNonCustomFields = 0
	
	Dataframe df

	String layoutPlaceHolder = ""

	String layoutBeanName = ""

	Map params = [:]

	static def grailsApplication
	
	static final String DEFAULT_FIELD_LAYOUT="""
						<label for='<% print field %>' id='label-<% print field %>'><% print label %></label>
						<span id='mandatory-<% print field %>'><% print mandatory %></span>
						<span id='widget-<% print field %>'><div id='<% print divId %>'><% print widget %></div></span>
						<span style='display:none;' id='message-<% print field %>'><% print errorMsg %></span><div id='button-<% print btnDivId %>'><% print btnWidget %></div><br>
	"""
	
	static final String ALL_OTHER_FIELDS = "[ALL_OTHER_FIELDS]";
	static final String ALL_OTHER_FIELDS_REGEXP = "\\[ALL_OTHER_FIELDS\\]";
	
	String fieldLayout = DEFAULT_FIELD_LAYOUT
	Map perFieldLayout = [:]
	
	String firstSectionBegin = "<table><tr><td>"
	String lastSectionEnd = "</td></tr></table>"
	String sectionDelimiter = "</td></tr><tr><td>"
	
	//Load all Layouts from the com.elintegro.erf.layout package: 
	static{
		
		//List<Class<?>> classLst = com.elintegro.utils.FactoryUtils.getClassesForPackage(Layout.class.package)
		
		//List classLst2 = new File("/com/elintegro/erf/layout")
				
		/*new File("/com/elintegro/erf/layout").eachFile(){file ->
			if(file.getName()){
				Class clazz = Class.forName(file.getName(), true, Thread.currentThread().contextClassLoader)
				Layout newLayoutObj = clazz.newInstance()
				newLayoutObj.setLayoutName()
				layouts.put(file.getName(), newLayoutObj)
			}				
			*/	
			println("Inside Layout")
		}				
	
		
	/**
	 * The layouts are equal if thir names are equal and placeHolder is null (standard layouts)
	 * or names are equal and placeholders are equal (custom layouts)
	 * @param compLayout
	 * @return
	 */
	final boolean equals(Layout compLayout){
		boolean ret = false
		
		if(this.name == name && !layoutPlaceHolder){
			ret = true
		}else if(this.name == name && layoutPlaceHolder == layoutPlaceHolder){
			ret = true
		}		
		return ret		
	}
	
	abstract void applyLayoutForField(StringBuffer resultPage, Widget widget, String widgetForm, String fieldName, int seq) throws LayoutException
	
	void applyLayout(StringBuffer resultPage, Widget widget, String widgetForm, String fieldName, int seq) throws LayoutException {
		
		if(seq == 0){//I'm calling for the first time (field)
			resultPage.append(firstSectionBegin).append(this.layoutPlaceHolder)
		}
		
		def retInd = applyLayoutForCustomPlaceholder(resultPage, widget, widgetForm, fieldName, seq)
		
		if(retInd < 0){
			numberOfNonCustomFields++
			applyLayoutForField(resultPage, widget, widgetForm, fieldName, seq)
		}
		
		if(seq == this.numberFields-1){ //I'm calling for the last time
			numberOfNonCustomFields = 0
			resultPage.append(lastSectionEnd)
		}		
	}
	
	int applyLayoutForCustomPlaceholder(StringBuffer resultPage, Widget widget, String widgetForm, String fieldName, int seq){
		String fieldNamePlaceHolder = "[$fieldName]"
		int indOf = resultPage.toString().indexOf(fieldNamePlaceHolder)
		if(indOf > 0){//then replace
			resultPage.replace(indOf, indOf+fieldNamePlaceHolder.length(),widgetForm)
		}
		applyDataframeLayout(resultPage)
		return indOf
	}
	
			
	abstract String setLayoutName()
	
	String getLayoutName(){return name}

	private Layout(String name){
		this.name = name
	}
	
	//DEFAULT LAYOUT
	public Layout(){
		
	}

	//DEFAULT LAYOUT
	public Layout(Dataframe df){
		this.df = df		
	}

		
	public static <T extends Layout> Layout getLayout(String name, T layoutClass){
		if(layouts.containsKey(name)){
			Layout ly = layouts.get(name)
			if(ly){
				return ly
			}else{// it was not initialized!
			
			}
		}else{// Layout does not exist
			//TOD: log an exception and use standard
						
		}
		
	}
	
	void addFieldLayout(String fieldName, String fldLayout){
         perFieldLayout.put(fieldName,  fldLayout)		
	}
	
	boolean isNonCustomFieldFirstTime(){
		numberOfNonCustomFields == 1
	}
	
	static void removeAllOtherFieldsPlaceholder(StringBuffer resultPageHtml){
		int indexofAllOtherFields = resultPageHtml.indexOf(Layout.ALL_OTHER_FIELDS);
		while(indexofAllOtherFields > -1){
			resultPageHtml.delete(indexofAllOtherFields, indexofAllOtherFields +  Layout.ALL_OTHER_FIELDS.length());
			indexofAllOtherFields = resultPageHtml.indexOf(Layout.ALL_OTHER_FIELDS);
		}
		
	}

	static void placeField(StringBuffer resultPage, String toAppend){
		int indOfOtherField = resultPage.indexOf(Layout.ALL_OTHER_FIELDS);
		if(indOfOtherField > -1){
			resultPage.insert(indOfOtherField, toAppend);
		}else{
			resultPage.append(toAppend);
		}		
	}

	static void applyButtonPlaceholder(StringBuffer resultPage, String toAppend, String btnString){
		String toReplace = toAppend.substring(toAppend.indexOf("["), toAppend.indexOf("]")+1)
		if(toAppend.indexOf(toReplace) > -1){
			toAppend = toAppend.replace(toReplace,btnString)
		}
		resultPage.append(toAppend)
	}

	static void applyButtonPlaceholderInDiv(StringBuffer resultPage, String btnName, String btnString, String dataframeName){
		String buttonRegex = "([\\[]${dataframeName}.dataframeButtons.${btnName})(\\w+)?(\\])"
		String buttonPlaceHolder = resultPage.find(buttonRegex)
		if (buttonPlaceHolder){
			int indOf = resultPage.toString().indexOf(buttonPlaceHolder)
			if (indOf > 0){
				resultPage.replace(indOf, indOf+buttonPlaceHolder.length(),btnString)
			}
		}
	}

	public static void applyDataframeLayout(StringBuffer resultPage){

		String dataframeRegexPattern = "([\\[]Dataframe.)(\\w+)?(\\])"
		String dataframeNamePlaceHolder = resultPage.find(dataframeRegexPattern)
		if (dataframeNamePlaceHolder){
			String placeHolderName = dataframeNamePlaceHolder.toString().replace("[","").replace("]","")
            String dataframeName = placeHolderName.split("\\.")[1]
			int indOf = resultPage.toString().indexOf(dataframeNamePlaceHolder)
			if ((indOf > 0) && dataframeName){
				Dataframe df = (Dataframe)Holders.grailsApplication.mainContext.getBean(dataframeName)
				resultPage.replace(indOf, indOf+dataframeNamePlaceHolder.length(),df.getHtml())
			}
		}
	}

}//End of class
