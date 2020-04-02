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
        String fldName = getFieldName(dataframe, field)
        def onClick = field.onClick
        String refHtml = ""

        if(onClick && field.get('onClick')){
            if(onClick.refDataframe){
                refHtml = getRefHtml(onClick, dataframe)
            }
        }
        String script = field.script?:""
        dataframe.getVueJsBuilder().addToMethodScript("""  
                               ${fldName}_method: function(addressValue){
                                        $script
                               },\n""")
        //Add security access for the button
        String ret = wrapWithSpringSecurity(field, """$refHtml<v-btn ${getAttr(field)} ${toolTip(field)} :disabled="${isDisabled(dataframe, field)}" id='$fldName' @click.stop='${fldName}_method'>${getLabel(field)}</v-btn>\n""")
        return ret;
    }


    String getVueDataVariable(DataframeVue dataframe, Map field) {
        return """"""

    }

    private String getRefHtml(Map onClick, DataframeVue dataframe){
        StringBuilder sb = new StringBuilder()
        DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
        String refDataframeName = refDataframe.dataframeName
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToDataframeVisibilityMap("${refDataframeName} : false,\n")
        dataframe.getVueJsBuilder().addToDataScript(" ${refDataframeName}_data:{key:'', refreshGrid:true},\n")
        if(onClick.showAsDialog){
            dataframe.getVueJsBuilder().addToComputedScript(""" visibility(){ return this.\$store.getters.getVisibilities;},\n""")
            sb.append("""<v-dialog v-model="visibility.${refDataframeName}" width='initial' max-width='500px'>""")
            sb.append(refDataframe.getComponentName("resetForm=true "))
            sb.append("""</v-dialog>""")
        } else {
            sb.append(refDataframe.getComponentName("resetForm=true"))
        }

        return sb.toString()
    }

}
