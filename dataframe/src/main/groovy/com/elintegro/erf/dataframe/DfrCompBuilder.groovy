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

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueStore
import grails.util.Holders

import javax.xml.crypto.Data

class DfrCompBuilder {

    public Map constructDfrComps(dataframes){
        StringBuilder vueRoutesSb = new StringBuilder()
        StringBuilder dfrCompToRegisterSb = new StringBuilder()
        StringBuilder vueComponentInitScriptsSb = new StringBuilder()
        StringBuilder vueGlobalCompScriptSb = new StringBuilder()
        Map vueStore = new HashMap()
        StringBuilder vueStateSb = new StringBuilder()
        StringBuilder vueMutationSb = new StringBuilder()
        StringBuilder vueStateGlobalSb = new StringBuilder()

//        List dataframesList = new ArrayList()
        for(String df: dataframes){
            dfrCompToRegisterSb.append(""+df.toLowerCase()+" : "+df+"Comp,\n")
            DataframeVue dataframe = DataframeVue.getDataframe(df)
//            dataframesList.add(dataframe)
            dataframe.getHtml()
//            initVueScript(df)
            if(dataframe.isGlobal){
                ResultPageHtmlBuilder.registeredComponents?.add(dataframe.dataframeName)
                vueGlobalCompScriptSb.append(dataframe.getJavascript()+"\n")
            }else {
                vueComponentInitScriptsSb.append(dataframe.getJavascript()+"\n")
            }
            vueStateSb.append(dataframe.getVueStoreScript().get("state"))
            vueMutationSb.append(dataframe.getVueStoreScript().get("mutation"))
            vueStateGlobalSb.append(dataframe.getVueStoreScript().get("globalState"))
            vueRoutesSb.append(dataframe.getVueRoutes())
        }

        String vueStoreScript = buildVueStoreScript(vueStateSb.toString(), vueMutationSb.toString(), vueStateGlobalSb.toString())
//                createStoreScript(dataframes, vueStoreSb)

        [vueGlobalCompScript: vueGlobalCompScriptSb.toString(), vueStore:vueStoreScript, dfrCompScript:vueComponentInitScriptsSb.toString(), vueRoutes:vueRoutesSb.toString(), dfrCompRegisterationScript: dfrCompToRegisterSb]

    }

    private String getEmbbeddedDataframeScripts(df, methodsListScript){
        List embeddedDataframesL = new ArrayList()
        if(df.embeddedDataframes.size()>0) {
            embeddedDataframesL.addAll(df.embeddedDataframes)
            for(String embDfString: embeddedDataframesL){
                DataframeVue embDfObj = DataframeVue.getDataframe(embDfString)
                getEmbbeddedDataframeScripts(embDfObj, methodsListScript)
            }
        }else{
            methodsListScript.append(df.getVueDataFillScript())
        }
    }

    public String buildVueStoreScript(String state, String mutation, String vueStateGlobal){
        StringBuilder stateSb = new StringBuilder()
        StringBuilder mutationSb = new StringBuilder()
        stateSb.append("state:{\n")
        stateSb.append("dataframeShowHideMaps: {\n")
        stateSb.append(vueStateGlobal)
        stateSb.append("},\n")
        stateSb.append(state)
        stateSb.append("dataframeBuffer: {\n") //for temporarily storing values like response data

        stateSb.append("},\n")
        stateSb.append("},\n") //End of State
        mutationSb.append("mutations:{\n")
        mutationSb.append(mutation)
        mutationSb.append("},\n")

        StringBuilder vueStoreSb = new StringBuilder()
        vueStoreSb.append(stateSb.toString())
        vueStoreSb.append(mutationSb.toString())

        return vueStoreSb.toString()
    }

    private static void createStoreScript( dataframes, StringBuilder vueStoreSb){
        StringBuilder stateSb = new StringBuilder()
        StringBuilder mutationSb = new StringBuilder()
        stateSb.append("state:{\n")
        mutationSb.append("mutations:{\n")
        for(String dfs: dataframes){
            DataframeVue df = DataframeVue.getDataframe(dfs)
            String dataframeName = df.dataframeName
            String dataframeStateNameString = "$dataframeName:{\nkey:'',\n}"
            if(df.createStore && df.vueStore.isEmpty() && !df.stateStringBuilder){
                stateSb.append(dataframeStateNameString)
                stateSb.append(",\n")
//                appendStateForChildrenDfr(stateSb, df)
            }else{
                if(!df.vueStore.isEmpty()){
//                    if(df.vueStore.state){
                        if(!stateSb.contains("$dataframeName")){
                            stateSb.append("$dataframeName:{\n")
                            stateSb.append("key:'',\n")
                            stateSb.append(df.vueStore.state)
                            stateSb.append("},\n")
                        }else{
                            String stateConstruct = "$dataframeName:{\nkey:'',\n" + df.vueStore.state + df.stateStringBuilder.toString() + "},\n"
                            int stInd = stateSb.indexOf("$dataframeStateNameString")
                            int ltInd = dataframeStateNameString.length() + 1
                            if(stInd > -1){
                                stateSb.replace(stInd, ltInd , stateConstruct)
                            }
                        }
//                    }

                    if(df.vueStore.mutations){
                        mutationSb.append(df.vueStore.mutations)
                    }
                }
            }
            stateSb.append(df.store.state?.toString())
            mutationSb.append(df.store.mutation?.toString())
        }
        stateSb.append(ResultPageHtmlBuilder.globalParametersInStore?.toString())
        stateSb.append("},\n") //End of State
        mutationSb.append("},\n")
        vueStoreSb.append(stateSb.toString())
        vueStoreSb.append(mutationSb.toString())

//        createStoreScriptMap(dataframes)
    }

    private static void createStoreScriptMap(List dataframeList){
        Map store = new HashMap()
       store.put("state", new HashMap())
        store.put("mutations", new ArrayList())
        Map state = store.get("state")
        List mutations = store.get("mutations")
        for(String dfs: dataframeList) {
            DataframeVue df = DataframeVue.getDataframe(dfs)
            String dataframeName = df.dataframeName
            if(df.createStore && df.vueStore.isEmpty()){
                if(!state.containsKey(dataframeName)){
                    Map stateMap = new HashMap()
                    state.put(dataframeName, stateMap)
                }
            }else{
                if(!df.vueStore.isEmpty()){
                    if(df.vueStore.state){
                        if(state.containsKey(dataframeName)){
                            List oldDataframeList = state.get(dataframeName) as List
                            oldDataframeList.add(df.vueStore.state)
                        }else{
                            state.put(dataframeName, df.vueStore.state)
                        }
                    }

                    if(df.vueStore.mutations){
                        mutations.add(df.vueStore.mutations)
                    }
                }
            }
        }

        println store.toString()

    }
// The following method puts child states inside parent state.
    private static appendStateForChildrenDfr(stateSb, df){
        List childrenDataframesL = new ArrayList()
        if(df.childrenDataframes.size()>0) {
            stateSb.append("$df.dataframeName:{\n")
            childrenDataframesL.addAll(df.childrenDataframes)
            for(String embDfString: childrenDataframesL){
                DataframeVue embDfObj = DataframeVue.getDataframe(embDfString)
                appendStateForChildrenDfr(stateSb, embDfObj)
            }
            stateSb.append("},\n")
        }else{
            stateSb.append("$df.dataframeName:{},\n")
        }
    }
}
