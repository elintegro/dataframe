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
import grails.util.Holders

import org.springframework.context.i18n.LocaleContextHolder
/**
 * Created by kchapagain on Oct, 2018.
 */
class EmailWidgetVue extends WidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String placeholder = field.placeholder?:"Enter your ${field.name}."
        return """
               <v-text-field
                 label="${getLabel(field)}"
                 placeholder = "${placeholder}"
                 v-model = "${getFieldJSONModelNameVue(field)}" 
                 ${validate(field)?":rules = '${fldName}_rule'":""}
                 ${isReadOnly?"readonly":''}
                 ${toolTip(field)}
                 style="width:${getWidth(field)}; height:${getHeight(field)};"   
                 ${getAttr(field)}
                ></v-text-field>
               """
    }

    @Override
    protected String widgetValidationRule(Map field) {

        String errorMessageCode = field.errMessageCode?:"email.validation.message"
        def emailRegex = Holders.grailsApplication.config.regex.email
        String errorMessage = getMessageSource().getMessage(errorMessageCode, null, errorMessageCode, LocaleContextHolder.getLocale())
        String rl = ""
        if (emailRegex){
            field.put("regex",emailRegex)
            String regex = "/${emailRegex}/"
            rl = """ (v) => ${regex}.test(v) || '$errorMessage' """
//                rule.add("""(v)=> /\\s/.test(v)?this.${dataVariable}.trim():''""")
        }
        return rl
    }
}
