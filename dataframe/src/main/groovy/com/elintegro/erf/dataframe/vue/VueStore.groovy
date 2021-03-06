/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.utils.DataframeFileUtil
import com.elintegro.utils.MapUtil
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import org.grails.web.json.JSONObject

class VueStore {

    private StringBuilder state = null
    private Map stateMap = null
    private StringBuilder mutation = null
    private StringBuilder getter = null
    private StringBuilder actions = null
    private StringBuilder dataframeVisibilityMap = new StringBuilder()

    VueStore(){
        state = new StringBuilder()
        stateMap = new HashMap()
        getter = new StringBuilder()
        mutation = new StringBuilder()
        actions = new StringBuilder("")
    }

    String getState(){
        return state.toString()
    }

    void addToState(def value){
        state.append(value)
    }
    void addToState(String key, Object value){
        stateMap.put(key, value);
    }

    String getDataframeVisibilityMap(){

        return dataframeVisibilityMap.toString()
    }

    void addToDataframeVisibilityMap(String value){
        dataframeVisibilityMap.append(value)
    }

    public String getGlobalState(){
        StringBuilder sbb = new StringBuilder()
        sbb.append(dataframeVisibilityMap.toString())

        return sbb.toString()
    }

/*
    String buildState(dataframeName){
        if(state.length() == 0){
            return ""
        }
        StringBuilder sbb = new StringBuilder()
        sbb.append("""$dataframeName: {\n""")
        sbb.append(state.toString())
        sbb.append("""},\n""")

        return sbb.toString()
    }
*/

    String buildStateJSON(DataframeVue dataframe){
        StringBuilder sbb = new StringBuilder()
        sbb.append("""$dataframe.dataframeName: \n""")
        dataframe.domainFieldMap["dataframe"] = dataframe.dataframeName
        dataframe.domainFieldMap.putAll(stateMap)
        sbb.append(MapUtil.convertMapToJSONString(dataframe.domainFieldMap))
        sbb.append(""",\n""")
        sbb.append(state?state.toString():"")

/*
        if(state != null && state.length() > 0) {
            sbb.append(state.toString())
        }
        sbb.append("""},\n""")
*/
        return sbb.toString()
    }

    String getMutation() {
        return mutation.toString()
    }


    void addToMutation(String value){
        mutation.append(value)
    }

    String getActions() {
        return actions.toString()
    }


    void addToActions(String value){
        actions.append(value)
    }

    String getGetters(){
        return getter.toString()
    }

    void addToGetters(String value){
        getter.append(value)
    }
    /*String buildMutation(dataframeName){
        if(mutation.length() == 0){
            return ""
        }
        StringBuilder sbb = new StringBuilder()
        sbb.append("""$dataframeName: {\n""")
        sbb.append(mutation.toString())
        sbb.append("""},\n""")

        return sbb.toString()
    }*/

    public static String createStoreGetterScript() {
        return """
             getState: (state) => (stateVar) => {
                return state[stateVar]; 
             },
             getVisibility: (state) => (dataframeName) => {
                return state.visibility[dataframeName]; 
             },
             getVisibilities: (state) => {
                return state['visibility']; 
             },
             """
    }
    public static String createStoreSetterScript(){
        return """
                 
             setVisibility(state, dataframeName){
                return state.visibility[dataframeName] = true; 
             },
             unsetVisibility(state, dataframeName){
                return state.visibility[dataframeName] = false; 
             },
        updateData(state, response){
            if(!response){ return }
          const dataframe = response?response.dataframe:"";
          if(dataframe){
           state[dataframe]["persisters"] = response.persisters;   
           state[dataframe]["transits"] = response.transits;   
           state[dataframe]["domain_keys"] = response.domain_keys;   
          }
        },
               """
    }
    public static String createStoreActionsScript(){
        return """
    refreshData : ({commit},params) => {
       params["doBeforeRefresh"](params); 
       if(!params.callApi) return;
       excon.callApi(params.url, "POST", params).then((response) =>{
          commit("updateData",response.data.data);
          params["doAfterRefresh"](response.data.data); 
       }) 
      .catch(function (error) {
          console.log(error);
      });
    },
    saveData : ({commit}, params) => {
       params["doBeforeSave"](params); 
       if(!params.callApi) return;
       excon.callApi(params.url, "POST", params).then((response) =>{
          params["doAfterSave"](response.data.data); 
          if(params.showAlertMessage == true){
             excon.showAlertMessage(response.data);
          }
       }) 
      .catch(function (error) {
          console.log(error);
      });
    },
"""
    }
}
