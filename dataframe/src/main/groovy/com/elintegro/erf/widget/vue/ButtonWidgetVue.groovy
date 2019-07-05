/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import com.elintegro.erf.dataframe.vue.VueStore


class ButtonWidgetVue extends WidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        String label = field.label
        String validate = field?.validate
        def fldMetadata = dataframe.fieldsMetadata.get(field.name)
        String attr = field.attr?:""
        def disabled = field.disabled == null? false : field.disabled;
        def onClick = field.onClick
        String refHtml = ""

        if(onClick && field.get('onClick')){
            if(onClick.refDataframe){
                DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
                String refDataframeName = refDataframe.dataframeName
                if(onClick.showAsDialog){
                    VueStore store = dataframe.getVuejsBuilder().getVueStore()
                    store.addToShowHideParamNames("${refDataframeName}_display : true,\n")
                    dataframe.getVuejsBuilder().addToDataScript("${refDataframeName}_display:false,\n")
                    dataframe.getVuejsBuilder().addToComputedScript("""check${refDataframeName}CloseButton: function(){return this.\$store.state.dataframeShowHideMaps.${refDataframeName}_display}, \n""")
                    dataframe.getVuejsBuilder().addToWatchScript("""check${refDataframeName}CloseButton:{handler: function(val, oldVal) {
                               this.${refDataframeName}_display = this.\$store.state.dataframeShowHideMaps.${refDataframeName}_display;}}, \n """)

                 refHtml = getRefHtml(refDataframe, dataframe)
                }
            }
        }
        String script = field.script?:""
        dataframe.getVuejsBuilder().addToMethodScript("""  
                               ${fldName}_method: function(addressValue){
                                        $script
                               },\n""")
                     //Add security access for the button
        String ret = wrapWithSpringSecurity(field, """<v-flex xs12 sm6 md6 lg12 xl4><v-btn $attr ${toolTip(field)} :disabled="$disabled" id='$fldName' @click.prevent='${fldName}_method'>${label}</v-btn>$refHtml</v-flex>\n""")
        return ret;
    }


    String getVueDataVariable(DataframeVue dataframe, Map field) {
        return """"""

    }

    private String getRefHtml(refDataframe, DataframeVue dataframe){
        StringBuilder sb = new StringBuilder()
        String refDataframeName = refDataframe.dataframeName
        dataframe.getVuejsBuilder().addToDataScript(" ${refDataframeName}_data:{key:'', refreshGrid:true},\n")

        sb.append("""<v-dialog v-model="${refDataframeName}_display" width='initial' max-width='500px'>""")
        sb.append(refDataframe.getComponentName(""))
        sb.append("""</v-dialog>""")

        return sb.toString()
    }

}
