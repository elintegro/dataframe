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

class CheckboxWidgetVue extends WidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String required = field.required?"required":""
        String onChange = ''
        if(field.onChange){
            onChange = "@change='${fldName}_onChange'"
            dataframe.getVueJsBuilder().addToMethodScript(""" ${fldName}_onChange: function() { \n$field.onChange\n},\n""")
        }
        String modelString = getModelString(dataframe, field)
        return """<v-checkbox
      v-model = "$modelString" 
      ${validate(field)?":rules = '${fldName}_rule'":""}
      ${isDisabled(dataframe, field)?":disabled = true":""}
      label="${getLabel(field)}"
      $required
      ${isReadOnly?"readonly":''}
      ${toolTip(field)}  
      ${getAttr(field)}
      style="width:${getWidth(field)}; height:${getHeight(field)};"
      $onChange
     ></v-checkbox>"""
    }

    String getStateDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        boolean  checked = field.defaultValue || field.checked
        checked = checked?:false
        return """$dataVariable: $checked,\n"""

    }
}
