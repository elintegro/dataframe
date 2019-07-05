/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.frontendlib

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.vue.DataframeVue
import groovy.util.logging.Slf4j

@Slf4j
class PageDFRegistry {
	
	String pageName;
	List<Dataframe> pageDfs;
//	PageDFRegistryVue pageDfsVue;
	Map<String, Dataframe> mapPageDfs = null;
		
	public getDfByName(String dataframeName){
		
		def  df =  getDfMap().get(dataframeName);
		if(df){
			df.init()
		}else{
			log.error("dataframe with name $dataframeName does not exists");
		}		
		return df;
		
	}
			
	public Map getDfMap(){
		Map res = [:];
		for(Dataframe df: pageDfs){
			res.put("${df.dataframeName}".toString(), df);
		}
		return res;

	}

	/**
	 *  
	 * @return
	 */
	public String getJavaScripts(){
		StringBuilder sb = new StringBuilder();
		for(Dataframe df: pageDfs){
			sb.append(df.getJavascript());
		}
		return sb.toString();
	}
	public String getScriptsVue(){
		StringBuilder sb = new StringBuilder();
		for(DataframeVue df: pageDfsVue){
			sb.append(df.getJavascript());
		}
		return sb.toString();
	}
}
