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

import com.elintegro.erf.dataframe.vue.DataframeVue

class InputWidgetVue extends WidgetVue {

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        if(field.hide && field.hide == true){
            return ""
        }
        String fldName = dataframe.getDataVariableForVue(field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String validate = field?.validate
        String attr = field.attr?:""
        def width = field.width?:'auto'
        def height = field.height?:'30px'
        String mandatory = field.notNull?" *":""
        String label = field.label + mandatory
        String autoComplete = field.autoComplete?:'off'
        String clearable = field.clearable?"clearable":""
        def fldMetadata = dataframe.fieldsMetadata.get(field.name)
        def disabled = field.disabled == null? false : field.disabled;
        disabled = (fldMetadata?.pk == true)? true: disabled;
        String html = """<v-text-field
            label="$label"
            v-model = "$fldName" 
            ${validate?":rules = '${fldName}_rule'":""}
            ${disabled?":disabled = true":""}
            ${isReadOnly?"readonly":''}
            ${toolTip(field)}
            style="width:$width; height:$height;"
            background-color="white"
            autocomplete = $autoComplete
            $clearable
            $attr
          ></v-text-field>"""
        if(field?.layout){
            html = applyLayout(dataframe, field, html)
        }
        return html
    }

}
