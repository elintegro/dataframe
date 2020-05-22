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
class PhoneNumberWidgetVue extends WidgetVue{

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String label = field.label
        return """
               <v-text-field
                 label="$label"
                 v-model="${getModelString(dataframe, field)}"
                 :rules = "${fldName}_rule"
                 ${isReadOnly?"readonly":''}
                 ${toolTip(field)}
                ></v-text-field>
               """
    }
    @Override
    protected String widgetValidationRule(Map field) {

        String errorMessageCode = field.errMessageCode?:"phone.validation.message"
        def phoneRegex = Holders.getGrailsApplication().getParentContext().getMessage("phone.validation.expression", null, Holders.grailsApplication.config.regex.phone?:null, LocaleContextHolder.getLocale());
        String errorMessage = getMessageSource().getMessage(errorMessageCode, null, errorMessageCode, LocaleContextHolder.getLocale())
        String rl = ""
        if (phoneRegex){
            field.put("regex",phoneRegex)
            String regex = "/${phoneRegex}/"
            rl = """ (v) => ${regex}.test(v) || '$errorMessage' """
        }
        return rl
    }


//    @Override
//    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) {
//        String errorMessage = field.errMessage?:"Phone Number must be valid"
//        def phoneRegex = Holders.getGrailsApplication().getParentContext().getMessage("phone.validation.expression", null, Holders.grailsApplication.config.regex.phone?:null, LocaleContextHolder.getLocale());
//        if (phoneRegex){
//            field.put("regex",phoneRegex)
//            String regex = "/${phoneRegex}/"
//            if (field?.validate){
//                def rule = field.validate.rule
//                rule.add('(v) => '+regex+".test(v) || '$errorMessage'")
//            }else {
//                field << ["validate":["rule":['(v) => '+regex+".test(v) || '$errorMessage'"]]]
//            }
//        }
//        String vueInstance = dataframe.dataframeName+"_instance"
////        return """this.$dataVariable = response['$key'];"""
//        return ""
//    }
       String unitTestForPhoneRegex(Locale locale){
       def phoneRegex = Holders.getGrailsApplication().getParentContext().getMessage("phone.validation.expression", null, null, locale);
       println(phoneRegex)
        return phoneRegex
    }
}
