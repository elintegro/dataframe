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

import com.elintegro.erf.dataframe.DFButton
import com.elintegro.erf.dataframe.DataframeView
import com.elintegro.erf.dataframe.OrderedMap
import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import org.apache.commons.lang.StringUtils
import org.springframework.context.i18n.LocaleContextHolder

/**
 * All this class does is generating the javascript functions, based on dataframe object, based on JQuery Jqx library
 *
 * If we need to use different technology, we need to implement DataframeView interface and build accordingly ...
 *
 *
 * @author ELipkov1
 *
 */
public class DataframeViewJqxVue implements DataframeView {

    DataframeVue dataframe;
    OrderedMap fields;
    String dataframeName;
//    private String vueComponentInitScripts = ""

    public DataframeViewJqxVue(DataframeVue dataframe){
        this.dataframe = dataframe;
        fields = dataframe.getFields();
        dataframeName = dataframe.dataframeName;

    }

    //Consider to move this one to JqxTreeWidget, since it is related to Tree Widget!
    public String getAjaxButtonScript(DFButton dfButton, DataframeVue refDataframe){

        String parentNamedParam = dataframe?.getNamedParameters()?dataframe?.getNamedParameters()?.toArray()[0].key:""; //TODO check if it is a right way to gewt parentNamed param!!!
        if(StringUtils.isEmpty(parentNamedParam)){
            parentNamedParam = 'id';
        }
        StringBuilder script = new StringBuilder()
        String dialogBoxId = ""
        if(dfButton.dialogBoxActionParams){
            dialogBoxId = "confirmationDialog-$dataframe.dataframeName-$dfButton.dialogBoxActionParams.buttonFor"
        }
        String hoverMessage = dfButton.hoverMessage?dataframe.messageSource.getMessage(dfButton.hoverMessage, null, org.apache.commons.lang.WordUtils.capitalizeFully(dfButton.name), LocaleContextHolder.getLocale()):""
        String buttonUrl = dfButton.url?:""
        String btnAjaxScript = ""
        String doBeforeAjax = dfButton.doBeforeAjax?:""
        if (!buttonUrl.isEmpty()){
            btnAjaxScript = getClickedButtonScript(dfButton)
        }
        if(dfButton.script.trim().length()>0){
            script.append("""
                        ${dataframeName}_${dfButton.name} : function(){
                                   ${dfButton.script}
                        },\n
                         """)

        } else {
            if(refDataframe){
                String refDfrName = refDataframe.dataframeName.toLowerCase()
                if(dfButton.route){
                    String routeIdScript = dfButton.routeIdScript
                    ResultPageHtmlBuilder.registeredComponents.add(refDataframe.dataframeName)
                    script.append("""${dataframeName}_${dfButton.name}: function(e){\n 
                         $doBeforeAjax
                         var routeId = ${routeIdScript?:""}
                         this.\$router.push({
                         name: '$refDfrName',
                         path: '$refDfrName',
                         params: {
                           $refDfrName: "test",
                           routeId: routeId
                         }
                       })
                       },\n""")
                }else {
                    script.append("""${dataframeName}_${dfButton.name}: function(){\n 
                                 //todo add if refDataframe exist but route is not defined. remove the following code if its scope is limited.
                                 this.${refDataframe.dataframeName}_display = true;
                                  Vue.set(this.\$store.state.dataframeShowHideMaps, "${refDataframe.dataframeName}_display", true);
                                 },\n""")
                }

            }else {
                script.append("""
                       ${dataframeName}_${dfButton.name}: function(){
                           var allParams = {'dataframe':'$dataframeName'};
                          $doBeforeAjax
                          $btnAjaxScript
                        },\n
				""")
            }

        }
        return script.toString();
    }

    public String getRefDataframeHtml(DFButton dfButton, DataframeVue refDataframe, VueJsBuilder vueJsBuilder){
        StringBuilder resultPageHtml = new StringBuilder()
        String refDataframeName = refDataframe?.dataframeName?:""
        if(!refDataframe || dataframeName==refDataframeName){
            return resultPageHtml.toString()
        }

        dataframe.childrenDataframes.add(refDataframeName)
        VueStore store = vueJsBuilder.getVueStore()
        store.addToShowHideParamNames("${refDataframeName}_display : false,\n")

        vueJsBuilder.addToDataScript("${refDataframeName}_display : false,\n")
        vueJsBuilder.addToDataScript(" ${refDataframeName}_data:{key:''},\n")

        if(dfButton.showAsDialog){
            resultPageHtml.append("""<v-dialog v-model="${refDataframeName}_display" width='initial' max-width='800px'>""")
            resultPageHtml.append(refDataframe.getComponentName("resetForm=true"))
//            resultPageHtml.append("""<component :is='${refDataframeName.toLowerCase()}' ref='${refDataframeName.toLowerCase()}_ref' :${refDataframeName}_prop="${refDataframeName}_data" :key='randomKey'></component>""")
            resultPageHtml.append("""</v-dialog>""")
        } else if(dfButton.showAsMenu && dfButton.showAsMenu.attr){
            String attr = dfButton.showAsMenu.attr?:"left"
            String attachTo = dfButton.showAsMenu.attachTo?:"$dataframeName-id"
            resultPageHtml.append("""<v-menu v-model="${refDataframeName}_display" :close-on-content-click="false" z-index='99' max-width="200" :nudge-width="200" $attr attach="$attachTo">""")
            resultPageHtml.append(refDataframe.getComponentName(""))
            resultPageHtml.append("""</v-menu>""")
        }else{
            resultPageHtml.append("""<div v-show="${refDataframeName}_display " max-width="500px">""")
            resultPageHtml.append(refDataframe.getComponentName(":key='randomKey'"))
            resultPageHtml.append("""</div>""")
        }
        //Add computed and watch scripts for dialog box
        String computedScript = """check${refDataframeName}CloseButton: function(){return this.\$store.state.dataframeShowHideMaps.${refDataframeName}_display}, \n"""
        String watchScript = """check${refDataframeName}CloseButton:{handler: function(val, oldVal) {
                               this.${refDataframeName}_display = this.\$store.state.dataframeShowHideMaps.${refDataframeName}_display;}}, \n """

        vueJsBuilder.addToComputedScript("""randomKey: function(){drfExtCont.generateRandom();},\n""")
        vueJsBuilder.addToComputedScript(computedScript)
        vueJsBuilder.addToWatchScript(watchScript)

        return resultPageHtml.toString()
    }

    public String getRefDataframeHtmlForOnClick(Map fields, VueJsBuilder vueJsBuilder){
        StringBuilder resultPageHtml = new StringBuilder()
        DataframeVue refDataframe = fields.refDataframe?:null
        String refDataframeName = refDataframe?.dataframeName?:""
        if(!refDataframe || dataframeName==refDataframeName){
            return resultPageHtml.toString()
        }

        dataframe.childrenDataframes.add(refDataframeName)
//        resultPageHtml.append("<v-divider></v-divider>")
        vueJsBuilder.addToDataScript("${refDataframeName}_display : false,\n")
        vueJsBuilder.addToDataScript(" ${refDataframeName}_data:null,\n")

        String attachTo = fields.attachTo?:"$dataframeName-id"
        String attr = fields.attr?:"left"
        if(fields.popup) {
            resultPageHtml.append("""<v-menu v-model="${refDataframeName}_display" :close-on-content-click="false" z-index='99' max-width="200" :nudge-width="200" $attr attach="$attachTo">""")
            resultPageHtml.append(refDataframe.getComponentName(""))
            resultPageHtml.append("""</v-menu>""")
        }
        return resultPageHtml.toString()
    }

    public String getRefreshFunctionName(){
        //return "refresh_${dataframeName}"
        return "Dataframe.${dataframeName}.refresh"
    }

    public String getClickedButtonScript(DFButton dfButton){
        String doBeforeSave = dfButton.doBeforeSave?:""
        String callBackScriptS = ""
        String callBackSuccessScriptS = ""
        String callBackFailureScript = ""
        if(dataframe.initOnPageLoad){
            callBackScriptS = "if(response.data != null && response.data != '' && response.data  != undefined){${dataframeName}Var.${dataframeName}_populateJSONData(response.data);}"
        }
        if(dfButton.callBackParams && dfButton.callBackParams.successScript){
            callBackSuccessScriptS = dfButton.callBackParams.successScript
        }
        if(dfButton.callBackParams && dfButton.callBackParams.failureScript){
            callBackFailureScript = dfButton.callBackParams.failureScript
        }
        return """
                 allParams['id'] = 1;
                 ${dataframe.getVueSaveVariables()}
                 $doBeforeSave
 if (this.\$refs.${dataframeName}_form.validate()) {
                axios({
                    method:'post',
                    url:'$dfButton.url',
                    params: allParams
                }).then(function (responseData) {
                        var response = responseData.data
                        if(response.success){
                        if(response.msg){
                           store.commit('alertMessage', {'snackbar':true, 'alert_type':'success', 'alert_message':response.msg});
                        }
                         ${callBackScriptS}
                         $callBackSuccessScriptS
                        }else{
                          if(!response.error){
                        $callBackFailureScript
                          }
                        if(response.msg){
                            store.commit('alertMessage', {'snackbar':true, 'alert_type':'error', 'alert_message':response.msg})
                         }
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
}
             \n
			"""
    }
}
