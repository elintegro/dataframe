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
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore
import com.elintegro.utils.DataframeFileUtil
import grails.util.Environment
import grails.util.Holders

import javax.xml.crypto.Data

class DfrCompBuilder {

    public Map constructDfrComps(List dataframes){
        StringBuilder vueRoutesSb = new StringBuilder()
        StringBuilder dfrCompToRegisterSb = new StringBuilder()
        StringBuilder vueComponentInitScriptsSb = new StringBuilder()
        StringBuilder vueGlobalCompScriptSb = new StringBuilder()
        Map vueStore = new HashMap()
        StringBuilder vueStateSb = new StringBuilder()
        StringBuilder vueGettersSb = new StringBuilder()
        StringBuilder vueMutationSb = new StringBuilder()
        StringBuilder vueActionsSb = new StringBuilder()
        StringBuilder vueStateGlobalSb = new StringBuilder()

        for(String df: dataframes){
            DataframeVue dataframe = DataframeVue.getDataframe(df)
            dataframe.getHtml()
            if(dataframe.isGlobal){
                ResultPageHtmlBuilder.registeredComponents?.add(dataframe.dataframeName)
                vueGlobalCompScriptSb.append(dataframe.getJavascript()+"\n")
            }else {
                dfrCompToRegisterSb.append(VueJsBuilder.createCompRegistrationString(df))
                vueComponentInitScriptsSb.append(dataframe.getJavascript()+"\n")
            }
            vueStateSb.append(dataframe.getVueStoreScript().get("state"))
            vueGettersSb.append(dataframe.getVueStoreScript().get("getters"))
            vueMutationSb.append(dataframe.getVueStoreScript().get("mutation"))
            vueActionsSb.append(dataframe.getVueStoreScript().get("actions"))
            vueStateGlobalSb.append(dataframe.getVueStoreScript().get("globalState"))
            vueRoutesSb.append(dataframe.getVueRoutes())

            if (Environment.current == Environment.DEVELOPMENT) {
                StringBuilder initDataframeVars = new StringBuilder()
                initDataframeVars.append("\n================    State: \n").append(vueStateSb)
                        .append("\n================    Getters: \n").append(vueGettersSb)
                        .append("\n================    Mutations: \n").append(vueMutationSb)
                        .append("\n================    Actions: \n").append(vueActionsSb)
                        .append("\n================    Global State: \n").append(vueStateGlobalSb)
                DataframeFileUtil.writeStringIntoFile("./logs/${dataframe.dataframeName}/${dataframe.dataframeName}-init-store-state.vue", initDataframeVars.toString())
            }
        }

        String vueStoreScript = buildVueStoreScript(vueStateSb.toString(), vueGettersSb.toString(), vueMutationSb.toString(), vueActionsSb.toString(), vueStateGlobalSb.toString())
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

    public String buildVueStoreScript(String state, String getters, String mutations, String actions, String vueStateGlobal){
        StringBuilder stateSb = new StringBuilder()
        StringBuilder gettersSb = new StringBuilder()
        StringBuilder mutationSb = new StringBuilder()
        StringBuilder actionsSb = new StringBuilder()
        stateSb.append("state:{\n")
        stateSb.append("visibility: {\n")
        stateSb.append(vueStateGlobal)
        stateSb.append("},\n")
        stateSb.append(state)
        stateSb.append("dataframeBuffer: {\n") //for temporarily storing values like response data

        stateSb.append("},\n")
        stateSb.append("},\n") //End of State
        stateSb.append("getters: {\n")
        gettersSb.append(VueStore.createStoreGetterScript())
        gettersSb.append(getters)
        gettersSb.append("},\n") //End of Getters
        mutationSb.append("mutations:{\n")
        mutationSb.append(VueStore.createStoreSetterScript())
        mutationSb.append(mutations)
        mutationSb.append("},\n")
        actionsSb.append("actions:{\n")
        actionsSb.append(VueStore.createStoreActionsScript())
        actionsSb.append(actions)
        actionsSb.append("},\n")

        StringBuilder vueStoreSb = new StringBuilder()
        vueStoreSb.append(stateSb.toString())
        vueStoreSb.append(gettersSb.toString())
        vueStoreSb.append(mutationSb.toString())
        vueStoreSb.append(actionsSb.toString())

        return vueStoreSb.toString()
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
