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
import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.vue.DataframeConstants
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dfEditor.DfInstance
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.apache.commons.lang.StringUtils
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.springframework.context.i18n.LocaleContextHolder
import org.apache.commons.lang.WordUtils

/**
 * Created by kchapagain on Nov, 2018.
 */
@Slf4j
class ComboboxVue extends CollectionWidgetVue {

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getHtmlStructure(dataframe, field, fldName, label)
        return html
    }

    Map getStateDataVariablesMap(DataframeVue dataframe, Map field){
        Map result = generateInitialData(dataframe, field)

        String valueMember = field.valueMember?:"id"
        List keys=[]
        List res
        Map selMap = [:]
        selMap.put(valueMember,'')
        if(result && result.size() > 0){
            keys = result.keys
            res = result.result
            selMap = result.selectedMap
        }else{
            log.error("This data is empty, please check data is provided in underliing database or enum class or other datasource. Check descriptor of Dataframe ${dataframe.dataframeName}")
        }

        Map domainFieldMap = dataframe.domainFieldMap
        Map fldJSON = null
        if(dataframe.isDatabaseField(field)){ //Put it to PERSISTERS section
            Map persisters = domainFieldMap.get(Dataframe.PERSISTERS)
            Map domainJSON = persisters.get(field.get(Dataframe.FIELD_PROP_DOMAIN_ALIAS))
            fldJSON = domainJSON.get(field.get(Dataframe.FIELD_PROP_NAME))
        }else{//Put it to TRANSITS section
            Map transits = domainFieldMap.get(Dataframe.TRANSITS)
            fldJSON = transits.get(field.get(Dataframe.FIELD_PROP_NAME))
        }
        fldJSON?.put("items", res)

        return domainFieldMap
/*

        return [$dataVariable:${selMap?selMap as JSON:"\"\""},\n
                  ${dataVariable}_items:${res as JSON} ,\n
                  ${dataVariable}_keys:${keys as JSON},\n
                """
*/

    }

    private String getHtmlStructure(DataframeVue dataframe, Map field, String fldName, String label){
        String onSelect = ""
        if(field.onSelect && field.onSelect.methodScript ){
            onSelect = " @change='${dataframe.dataframeName}_onSelect_$fldName' "
            dataframe.getVueJsBuilder().addToMethodScript("""${dataframe.dataframeName}_onSelect_$fldName: function(_params){
                            $field.onSelect.methodScript
             },\n """)
        }
        boolean isReadOnly = dataframe.isReadOnly(field)
        String typeString = ""
        if(!isSearchable(field)){
            typeString = """type="button" """
        }
        String multiple = field.multiple?"multiple":''
        String displayMember = field.displayMember?:'name'
        String valueMember = field.valueMember?:'id'
        String itemsStr = getFieldJSONItems(field)
        String modelString1 = getModelString(dataframe, field) //TODO: decide which one is correct?
        String modelString = getFieldJSONModelNameVue(field)
        String dataVariable = dataframe.getDataVariableForVue(field)//TODO: may be we do not need it!
        return """
            <v-combobox
                  v-model = "${modelString}"  
                  :items="${itemsStr}"
                  ${validate(field)?":rules = '${fldName}_rule'":""}
                  label="$label"
                  ${isDisabled(dataframe, field)?":disabled=true":""}
                  item-text="${displayMember}"
                  item-value="${valueMember}"
                  small-chips
                  $multiple
                  hide-no-data
                  hide-selected
                  ${isReadOnly?"readonly":''}
                  ${toolTip(field)}
                   $onSelect
                  $typeString
                  ${getAttr(field)}
            ></v-combobox>
        """
    }
}
