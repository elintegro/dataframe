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

/**
 * Created by kchapagain on Oct, 2018.
 */
class DateWidgetVue extends WidgetVue {

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String locale = field.locale?:"he"
        String localeString = locale?"locale='$locale'":""
        String dateFormatPlaceholder = getMessageSource().getMessage("date.format.hint", null, "date.format.hint", LocaleContextHolder.getLocale())
        String menuAttr = field.menuAttr?:""
//        :nudge-right="40"
//
        return """
                    <v-menu
                        ref="${fldName}_menu"
                        v-model="${fldName}_menu"
                        :close-on-content-click="false"
                        :return-value.sync="$fldName"
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="${getWidth(field, "200")}px"
                        style="width:${getWidth(field)}; height:${getHeight(field)};"
                        $menuAttr
                >
                <template v-slot:activator="{ on }">
                    <v-text-field
                            v-model="${fldName}_formatted"
                            label="${getLabel(field)}"
                            ${validate(field)?":rules = '${fldName}_rule'":""}
                            v-on="on"
                            hint="$dateFormatPlaceholder"
                            persistent-hint
                            prepend-icon="event"
                            @click:prepend="onIconClick_$fldName"
                            readonly
                            id="${fldName}_id"
                            
                            ${toolTip(field)}
                    ></v-text-field>
                </template>
                ${isReadOnly?"":"""<v-date-picker $localeString v-model="$fldName" ${getAttr(field)} no-title scrollable @input="\$refs.${fldName}_menu.save($fldName)"></v-date-picker>"""}
                </v-menu>
                """
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
       dataframe.getVueJsBuilder().addToMethodScript("""formatDate: function(date) {
               if (!date) return null;
               var d = new Date(date);
               this.$dataVariable = d.toISOString();
               return d.format("dd/mm/yyyy")
                          },\n
                    parseDate: function(date) {
                                if (!date) return null
                                  const [month, day, year] = date.split('/')
                                  var al = `\${year}-\${month.padStart(2, '0')}-\${day.padStart(2, '0')}`
                                  return al
                             },\n
                             onIconClick_$dataVariable:function(e){
                                \$('#${dataVariable}_id').addClass('date-menu-position');
                                this.${dataVariable}_menu = !this.${dataVariable}_menu;
                             },\n""")
        dataframe.getVueJsBuilder().addToComputedScript(""" 
        ${dataVariable}_formatted() {
            if (!this.$dataVariable) return null;
            var d = new Date(this.$dataVariable);
            this.$dataVariable = d.toISOString();
            return d.format("dd/mm/yyyy")
        },\n""")
        String fromSuper = super.getVueDataVariable(dataframe, field)
        return """$fromSuper
                  ${dataVariable}_menu:'',\n"""

    }

}
