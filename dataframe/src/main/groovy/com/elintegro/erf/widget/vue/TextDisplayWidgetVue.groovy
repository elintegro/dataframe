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

import org.springframework.context.i18n.LocaleContextHolder
import org.apache.commons.lang.WordUtils

class TextDisplayWidgetVue extends WidgetVue{
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        if(field.hide && field.hide == true){
            return ""
        }
        String fldName = field.name
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        boolean isDynamic = field.isDynamic?true:false
        String elementId = field.elementId?:""
        String modelString = getFieldJSONModelNameVue(field)
        String onClick = field.onClick?:""
        boolean link = field.link?true:false
        String displayPlaceholder = ""
        if(!isDynamic){
            displayPlaceholder = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        } else if (isDynamic && labelCode){
            String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
            if(link){
                displayPlaceholder = "$label"+"\t:\t"+"""<a href='#' style="text-decoration:none !important;" @click = "${onClick}">{{$modelString}}</a>"""
            }else {
                displayPlaceholder = "$label" + "\t:\t" + "{{$modelString}}"
            }
        }else {
            displayPlaceholder = "{{$modelString}}"
        }
        return """<span id='$elementId'  ${getAttr(field)}>$displayPlaceholder</span>"""
    }
}
