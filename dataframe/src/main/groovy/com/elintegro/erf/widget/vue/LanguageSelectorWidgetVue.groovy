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

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.converters.JSON
import org.apache.commons.lang.WordUtils
import org.grails.web.json.JSONObject
import org.springframework.context.i18n.LocaleContextHolder

class LanguageSelectorWidgetVue extends CollectionWidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getCombobox(dataframe, field, fldName, label)
        return html
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        def search = field?.search
        String validationString = ""
        if(validate(field)){
            String validationRules = validationRules(field)
            validationString = """ ${dataVariable}_rule: $validationRules,\n"""
        }
        return """
                  ${search?"${dataVariable}_search:null,\n":""}
                   $validationString
                """

    }

    boolean setPersistedValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        Map additionalData = loadAdditionalData(dfInstance, fieldProps, fieldName, additionalDataRequestParamMap, sessionHibernate)
        if(additionalData && additionalData.containsKey("items")){
            List items = (List)additionalData.items
            String defaultLanguage = 'English'
            jData?.persisters?."${domainAlias}"."${fieldName}".value = getValueList(fieldProps, items, [defaultLanguage], fieldName)
            jData?.persisters?."${domainAlias}"."${fieldName}".items = items
        }
    }

    boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
//        super.setTransientValueToResponse(jData, value, domainAlias, fieldName)
        Map additionalData = loadAdditionalData(dfInstance, fieldProps, fieldName, additionalDataRequestParamMap, sessionHibernate)
        if(additionalData && additionalData.containsKey("items")){
            List items = (List)additionalData.items
            String defaultLanguage = 'English'
            jData?.transits?."${fieldName}".value = getValueList(fieldProps, items, [defaultLanguage], fieldName)
            jData?.transits?."${fieldName}".items = items
        }
    }

    private List getValueList(Map fieldProps, List items, Object value, String fieldName){
        List valueList = new ArrayList()
        String displayMember = fieldProps.displayMember?:fieldName
        if(items && value){
            for(int j=0; j<items.size(); j++){
                Map item = (Map) items[j]
                for(int k=0; k<value.size(); k++){
                    if(item."$displayMember" == value[k]){//check if same item and add to list
                        valueList.add(item)
                        continue;
                    }
                }
            }
        }
        return valueList
    }

    private String getCombobox(DataframeVue dataframe, Map field, String fldName, String label){
        String onSelect = ""
        if(field.onSelect && field.onSelect.methodScript ){
            onSelect = " @change='${dataframe.dataframeName}_onSelect_$fldName' "
            dataframe.getVueJsBuilder().addToMethodScript("""${dataframe.dataframeName}_onSelect_$fldName: function(_params){
                            $field.onSelect.methodScript
             },\n """)
        }
        String defaultLanguage = field.defaultLanguage?:"English"
        dataframe.getVueJsBuilder().addToDataScript("""defaultLanguage:'$defaultLanguage',""")
        boolean isReadOnly = dataframe.isReadOnly(field)
        String typeString = ""
        if(!isSearchable(field)){
            typeString = """type="button" """
        }
        String multiple = field.multiple?"multiple":''
        String displayMember = field.displayMember?:'name'
        String valueMember = field.valueMember?:'id'
        String itemsStr = getFieldJSONItems(field)
        String modelString = getFieldJSONModelNameVue(field)
        """
            <v-row style="align-items:baseline;">
                  <v-col cols="2">
                        <v-icon style='color:#1976D2;'>translate</v-icon>
                  </v-col>
                  <v-col cols="6">
                      <v-select
                          class = "underLine"
                          v-model = "defaultLanguage" 
                          :items="$itemsStr"
                          ${validate(field)?":rules = '${fldName}_rule'":""}
                          label="$label"
                          ${isDisabled(dataframe, field)?":disabled=true":""}
                          item-text="${displayMember}"
                          item-value="${valueMember}"
                          hide-no-data
                          hide-selected
                          ${isReadOnly?"readonly":''}
                          ${toolTip(field)}
                           $onSelect
                          $typeString
                         ${getAttr(field)}
                      ></v-select>
                  </v-col>
            </v-row>
        """
    }
}
