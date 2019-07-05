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

import org.springframework.context.i18n.LocaleContextHolder

import java.util.regex.Matcher
import java.util.regex.Pattern

class JavascriptService {
	
	def messageSource
	def servletContext

	
	def JavascriptService(){
		println "JavascriptService is loading!"
	}
	
	 String replaceVariables(String contents, String varName, String variableVal) {
		contents = contents.replaceAll(/[$]$varName/, variableVal)
		return contents
	}

	 String replaceVariables(String contents, Map var) {
		var.each {
				def varName = it.key
				def varVal = it.value
				contents = contents.replaceAll(/[$]$varName/, varVal)			
			}		
		 return contents
	}
	
	
	/**
	 * Replaces i18n placeholders in a String
	 * @param contents
	 * @return
	 */
	 def applyMLPlaceholders(String contents) {
		Pattern jpattern = Pattern.compile(/\~\{(.*?)\}/);
		Matcher jmatcher = jpattern.matcher(contents);
		boolean found = false;
		Set usedCodes = []
		while (jmatcher.find()) {
			def mg = jmatcher.group().trim()
			def mlCode = mg.substring(2, mg.length()-1)
			if( !usedCodes.contains(mlCode) ){				
				def mlText =  messageSource.getMessage(mlCode, null, "", LocaleContextHolder.getLocale())
				usedCodes.add(mg)
				contents = contents.replaceAll(/\~\{$mlCode\}/, mlText)
			}
		}
		//usedCodes.each {it.value.put("text", message(code: it.value.get("code")))}
		return contents
	}

	def getJavaScript(String jsName){
		def jsContent
		if (jsName){
			String absolutePath = servletContext.getRealPath("/");
			def file = new File(absolutePath +jsName)
			jsContent = file.getText()
		}
		return jsContent	
	}

	 
	 
	 
}
