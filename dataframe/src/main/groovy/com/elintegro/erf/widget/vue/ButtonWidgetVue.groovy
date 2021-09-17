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
import org.springframework.context.i18n.LocaleContextHolder


class ButtonWidgetVue extends WidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def onClick = field.onClick
        def disabled = field.disabled
        boolean icon = field.icon?:false
        boolean imageIcon = field.imageIcon?:false
        String imageIconAttr = field.imageIconAttr
        String iconAttr = field.iconAttr
        String vuetifyIcon = field.vuetifyIcon
        String iconScript = """<v-icon $iconAttr>$vuetifyIcon</v-icon>"""
        String imageUrl = field.imageUrl
        String loader = """ <template v-slot:placeholder>
                                        <v-row
                                          class="fill-height ma-0"
                                          align="center"
                                          justify="center"
                                        >
                                          <v-progress-circular
                                            indeterminate
                                            color="#ffc526"
                                          ></v-progress-circular>
                                        </v-row>
                                        </template>
                           """
        String imageIconScript = """ <v-img src="$imageUrl" $imageIconAttr>
                                        ${loader}
                                    </v-img>
                                 """
        boolean imageHasLabel = field.imageHasLabel?:false
        String imageLabelAttr = field.imageLabelAttr?:""
        String refHtml = ""

        if(onClick && field.get('onClick')){
            if(onClick.refDataframe){
                refHtml = getRefHtml(onClick as Map, dataframe)
            }
        }
        addMethodsToScript(dataframe, field)
        //Add security access for the button
        String ret = ""
        if(icon && vuetifyIcon){
            ret = wrapWithSpringSecurity(field, """$refHtml<v-btn ${getAttr(field)} ${toolTip(field)} :disabled="$disabled" id='$fldName' @click.stop='${fldName}_method'>${iconScript}</v-btn>\n""")
        }else if(imageIcon && imageUrl){
            ret = wrapWithSpringSecurity(field, """$refHtml<v-btn ${getAttr(field)} ${toolTip(field)} :disabled="$disabled" id='$fldName' @click.stop='${fldName}_method'>${imageIconScript} <div v-if='${imageHasLabel}' ${imageLabelAttr}>${getLabel(field)}</div></v-btn>\n""")
        } else{
            ret = wrapWithSpringSecurity(field, """$refHtml<v-btn ${getAttr(field)} ${toolTip(field)} :disabled="$disabled" id='$fldName' @click.stop='${fldName}_method'>${getLabel(field)}</v-btn>\n""")
        }
        return ret;
    }


    private void addMethodsToScript(DataframeVue dataframe, Map fieldProps){
        String fldName = getFieldName(dataframe, fieldProps)
        String script = fieldProps.script?:""
        dataframe.getVueJsBuilder().addToMethodScript("""  
                               ${fldName}_method: function(){
                                        $script
                               },\n""")
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        return """"""

    }

    private String getRefHtml(Map onClick, DataframeVue dataframe){
        StringBuilder sb = new StringBuilder()
        DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
        String refDataframeName = refDataframe.dataframeName
        String dialogBoxWidth = onClick.dialogBoxWidth?:"initial"
        String dialogAttrs = onClick.dialogAttrs?:""
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToDataframeVisibilityMap("${refDataframeName} : false,\n")
        dataframe.getVueJsBuilder().addToDataScript(" ${refDataframeName}_data:{key:'', refreshGrid:true},\n")
        def maxWidth = onClick.dialogBoxMaxWidth?:"500px"
        if(onClick.showAsDialog){
            sb.append("""<v-dialog v-model="visibility.${refDataframeName}" width='$dialogBoxWidth' max-width='$maxWidth' ${dialogAttrs}><v-card>""")
            sb.append(refDataframe.getComponentName("resetForm=true "))
            sb.append("""</v-card></v-dialog>""")
        } else {
            sb.append(refDataframe.getComponentName("resetForm=true"))
        }

        return sb.toString()
    }

}
