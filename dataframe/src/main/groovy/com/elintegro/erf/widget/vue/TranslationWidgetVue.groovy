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

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.DataframeVue
import org.springframework.context.i18n.LocaleContextHolder

class TranslationWidgetVue extends WidgetVue {

    private final static String translator = "translator"

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        if(field.hide && field.hide == true){
            return ""
        }
        String fldName = getFieldName(dataframe, field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String autoComplete = field.autoComplete?:'off'
        String clearable = field.clearable?"clearable":""
        String placeholder = field.placeholder?:""
        String iconColor = field.iconColor?:"white"
        String fieldType = "textarea" //textarea||text
        if (field?.type?.equals("text")){
            fieldType = "text-field"
        }
        String translateTextCode = field?.translateTextCode?:"translator.widget.text"
        addMethodsToScript(dataframe, field)
        String translateText = getMessageSource().getMessage(translateTextCode, null, fldName, LocaleContextHolder.getLocale())
        String floatingIcon = """<template v-slot:append>
                <v-fade-transition leave-absolute>
                  <v-btn color="$iconColor" class="iconColorTranslate" fab dark small absolute bottom right
                    v-tooltip="{content: '$translateText'}"
                    @click.stop='${fldName}_method'
                    >
                   <v-icon>translate</v-icon>
                  </v-btn>
                </v-fade-transition>
              </template>"""
        String html = """<v-$fieldType
            label="${getLabel(field)}"
            v-model = "${getFieldJSONModelNameVue(field)}"
            ${validate(field)?":rules = '${fldName}_rule'":""}
            ${isDisabled(dataframe, field)?":disabled = true":""}
            ${isReadOnly?"readonly":''}
            ${toolTip(field)}
            style="width:${getWidth(field)}; height:${getHeight(field)};"
            autocomplete = $autoComplete
            placeholder = "$placeholder"
            $clearable
            auto-grow
            ${getAttr(field)}
            ${fillProps(field)}
          >$floatingIcon</v-$fieldType>
            """
        if(field?.layout){
            html = applyLayout(dataframe, field, html)
        }
        return html
    }

    Map getStateDataVariablesMap(DataframeVue dataframe, Map field){
        Map domainFieldMap = dataframe.domainFieldMap
        Map fldJSON = getDomainFieldJsonMap(dataframe, field)
        if (fldJSON){
            fldJSON.put(translator, ["entityId":null, "fieldName":"", "entityName":""])
        }
        return domainFieldMap
    }

    private void addMethodsToScript(DataframeVue dataframe, Map fieldProps){
        String fldName = getFieldName(dataframe, fieldProps)
        String jsonFieldName = getFieldJSONNameVue(fieldProps)
        Map onClick = fieldProps.onClick
        if (!onClick){
            throw new DataframeException("Provide on click data in the format: onClick:['refDataframe':ref('Dataframe Name'), 'script':'optional']")
        }
        String script = ""
        if(onClick && onClick.refDataframe){
            DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
            script = onClick?.script?:"""excon.refreshTranslatorField('${refDataframe.dataframeName}', this.${jsonFieldName}.$translator);"""
        }
        dataframe.getVueJsBuilder().addToMethodScript("""  
                               ${fldName}_method: function(){
                                        $script
                               },\n""")
    }

}
