/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.widget

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.vue.DataframeConstants
import com.elintegro.erf.dataframe.vue.DataframeVue
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.web.json.JSONObject

/**
 * This abstract could classdefines a main method for each widget;
 * TODO: consider to turn it into the Interface if not implementation is required here
 *
 * @author Eugenelip
 *
 */
abstract class Widget<T> implements DataframeConstants{

	abstract  String  getHeaderScript(T dataframe, Map info, String divId)
	abstract  String  getBodyScript(T dataframe, Map info)
	abstract  String  getHtml(T dataframe, Map field)
	abstract  String getEnabledDisabledFunction(T dataframe, Map field)
	abstract String getValueSetter(T dataframe, Map field, String divId, String fldId, String key)
	abstract boolean populateDomainInstanceValue(def domainInstance, DomainClassInfo domainMetaData, String fieldName, Map field, def inputValue)
	abstract boolean setPersistedValueToResponse(JSONObject inputValue, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap)
	abstract boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap)
	public static final int ONE_SIMBOL_WITH = 6;

	public Map loadAdditionalData(DataframeInstance dataframeInst, String fieldName, Map inputData, def dbSession){
		return [:]
	}

	public def jQTreeHqlMap = null;
	public treeName;


	String getValueScript(Dataframe dataframe, Map field, String divId, String fldId, String key){
		return """""";
	}


	/**
	 * TODO Explain
	 * This method must do the following:
	 *
	 * */
	Map getDictionary(def dataframeName, def fieldName, def keys){


	}

	/**
	 * TODO: explain 
	 * This method must do the following:
	 *
	 * */
	Map getTreeMap(){
		return jQTreeHqlMap
	}

	/**
	 * TODO: This method shoudl not exist!!!! Remove it and find where to store treeMap definition, probably on the bean level (Dataframe!)
	 * This method must do the following:
	 *
	 * */
	Map setTreeMap(def field, String fieldName){
		jQTreeHqlMap =  field.get("treeMap");
		treeName = fieldName;
		if(jQTreeHqlMap == null){
			throw new Exception("Treee map has not been defined for the widget "+field.name)
		}
	}


	public boolean getBooleanAttributeFromField(String attributeName, Map fieldMap, boolean defaultVal){
		boolean value = defaultVal
		if(fieldMap.containsKey(attributeName)){
			def attrib = fieldMap.get(attributeName)
			value = (attrib == null)?defaultVal:Boolean.valueOf(attrib);
		}
		return value
	}

	public static boolean isReadOnly(Map field){
		if (field?.readOnly){
			return true
		}
		return false
	}

	public static boolean isMandatory(Map field){
		if (field?.notNull){
			return true
		}
		return false
	}


}
