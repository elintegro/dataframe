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
import com.elintegro.erf.dataframe.ScriptBuilder

class VueJsBuilder implements ScriptBuilder<DataframeVue>{

    Map methods = [:]
    VueJsEntity vuejsLifecycleEntity = null;
    DataframeVue df
    private VueStore vueStore
    private StringBuffer computedScriptSbf = new StringBuffer()
    private StringBuffer dataScriptStringbf = new StringBuffer()
    private StringBuffer watchScriptSbf = new StringBuffer()
    private StringBuffer componentScriptSbf = new StringBuffer()
    private StringBuffer createdScriptSbf = new StringBuffer()
    private StringBuffer methodScriptSbf = new StringBuffer()
    private StringBuffer propsScriptSbf = new StringBuffer()
    private StringBuffer propsAttrString = new StringBuffer()
    private StringBuffer templateScriptSbf = new StringBuffer()
    private String finalComponentInitScript

    VueJsBuilder(DataframeVue df){
        vuejsLifecycleEntity = new VueJsEntity();
        vueStore = new VueStore()
        this.df = df
    }

    public void addToTemplateScript(script){
     templateScriptSbf.append(script)
    }

    public void addToDataScript(script){
        dataScriptStringbf.append(script)

    }

    public void addToCreatedScript(script){
        createdScriptSbf.append(script)

    }

    public void addToComponentScript(script){
        componentScriptSbf.append(script)
    }

    public void addToMethodScript(script){
        if(script.trim()){
            methodScriptSbf.append(script)
            def index = script.lastIndexOf(",")
            if(index >= 0){
                def s = script.substring(index+1).trim()
                if("" != s){
                    methodScriptSbf.append(",")
                }
            }else {
                methodScriptSbf.append(",")
            }

        }

    }

    public String buildTemplateScript(){
        if(templateScriptSbf.length() == 0){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        sb.append("template:`")
        sb.append(templateScriptSbf.toString())
        sb.append("`,\n")
        return sb.toString()
    }

    public String buildCreatedScript(){
        if(createdScriptSbf.length() == 0){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        sb.append("created () {\n")
        sb.append(createdScriptSbf.toString())
        sb.append("},\n")
        return sb.toString()
    }

    public void onMount(){

    }

    public String buildMethodScript(){
        if(methodScriptSbf.length() == 0){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        sb.append("methods:{\n")
        sb.append(methodScriptSbf.toString())
        sb.append("},\n")
        return sb.toString()
    }

    public void watch(){

    }

    public String buildComponentScript(){
        if(componentScriptSbf.length() == 0){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        sb.append("components:{\n")
        sb.append(componentScriptSbf.toString())
        sb.append("},\n")
        return sb.toString()
    }

    public void addToComputedScript(String script){
        computedScriptSbf.append(script)

    }

    public void addToWatchScript(String script){
        watchScriptSbf.append(script)
    }

    public String buildDataScript(){
        StringBuilder sb = new StringBuilder()
        sb.append("data: function() {return {\n")
        sb.append(dataScriptStringbf.toString())
        sb.append("}\n},\n")
        return sb.toString()
    }

    public String buildComputedScript(){
        if(computedScriptSbf.length() == 0){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        sb.append("computed: {\n")
        sb.append(computedScriptSbf.toString())
        sb.append("\n},\n") // computed bblock closedreturn computedScriptSbf.toString()
        return sb.toString()
    }

    public String buildWatchScript(){
        if(watchScriptSbf.length() == 0){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        sb.append("watch: {\n")
        sb.append(watchScriptSbf.toString())
        sb.append("\n},\n") // computed bblock closedreturn computedScriptSbf.toString()
        return sb.toString()
    }

    public String getComputedScript(){
        return computedScriptSbf.toString()
    }

    public void addToPropsScript(script){
        if(script.trim()){
            propsScriptSbf.append(script)
            def index = script.lastIndexOf(",")
            if(index >= 0){
                def s = script.substring(index+1).trim()
                if("" != s){
                    propsScriptSbf.append(",")
                }
            }else {
                propsScriptSbf.append(",")
            }

        }
    }

    public String buildPropsScript(){
        if(propsScriptSbf.length() == 0){
            return ""
        }
        StringBuilder sb = new StringBuilder()
        sb.append("props: [\n")
        sb.append(propsScriptSbf.toString())
        sb.append("\n],\n") // computed bblock closedreturn computedScriptSbf.toString()
        return sb.toString()
    }

    public void addToPropsAttrString(script){
      propsAttrString.append(script)
    }

    public String getPropsAttrString(){
          return propsAttrString.toString()
    }

    public String createComponent(){

    }

    public String createInstance(){

    }

    public static String createCompRegistrationString(component){
        return ""+component.toLowerCase()+" : "+component+"Comp,\n"
    }

    public void setFinalComponentInitScript(String finalComponentInitScript){
        this.finalComponentInitScript = finalComponentInitScript
    }
    public String getFinalComponentInitScript(){
        return finalComponentInitScript
    }

    @Override
    public String getFinalbuildScript(DataframeVue df) {

        StringBuffer vueCompBuilder = new StringBuffer() //test for using localized components
        String dataframeName = df.dataframeName
        if(df.isGlobal){
            vueCompBuilder.append("Vue.component('${dataframeName}',{\n")
            vueCompBuilder.append("name: '$dataframeName',\n")
            df.componentRegistered = true
        }else{
            df.componentRegistered = false
            vueCompBuilder.append("var ${dataframeName}Comp = {\n")
        }
        vueCompBuilder.append(buildTemplateScript())
        vueCompBuilder.append(buildDataScript())
        vueCompBuilder.append(buildPropsScript())
        vueCompBuilder.append(buildComponentScript())
        vueCompBuilder.append(buildCreatedScript())
        vueCompBuilder.append(buildComputedScript())
        vueCompBuilder.append(buildWatchScript())
        vueCompBuilder.append(buildMethodScript())
        if(df.isGlobal){
            vueCompBuilder.append("})\n") //End of Comp Registration
        }else{
            vueCompBuilder.append("}\n")
        }
        // vueCompBuilder Ends here

        return vueCompBuilder.toString()

    }

    public VueStore getVueStore(){
        return vueStore
    }
}
