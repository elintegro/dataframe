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

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.db.fields.MetaField
import grails.converters.JSON
import grails.util.Holders
//import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.ApplicationContext

class TreeService {

	ApplicationContext ctx
	/**
	 * We look for the field of the entity of the one above of the provided level
	 * If the provided level is the highest one in the hierarchy, this method returns empty string
	 * params should have 3 entries:
	 * level - for the parent entity level (that one we are trying create an Entity for)
	 * dataframe - where this Tree lives
	 * treeFieldName - the field name of the Tree Control
	 *
	 * */
	public String getParentField(params){
		def jsonMap = [:];
		String level  = params.level;
		Dataframe df = getDataframe(params);
		
		Dataframe parentDf = df.parent;
		
		MetaField field = df.getFieldByName(params.treeFieldName);
		Map treeMap = field?field.get("treeMap"):"";
		int levelInt = Integer.valueOf(level) + 1;
		String levelStr = "level_" + String.valueOf(levelInt);
		if(levelInt < treeMap.size() && treeMap.contains(levelStr)){
			def levelMap = treeMap.get(levelStr)
			String hql = levelMap.getAt("hql");
			jsonMap = [parentFieldName:ParsedHql.getParentFieldName(hql)]
		}
			return jsonMap as JSON;
	}

	
	private void initCtx(){
		if(!ctx){
			ctx = (ApplicationContext)/*ApplicationHolder.getApplication().*/Holders.grailsApplication.getMainContext();
		}
	}
	
	private Dataframe getDataframe(params){
		initCtx()
		Dataframe dataframe = (Dataframe) ctx.getBean(params.dataframe)
		dataframe.init()
		return dataframe
	}

}
