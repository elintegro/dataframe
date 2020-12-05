package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.vue.DataframeConstants
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueStore
import grails.converters.JSON
import groovy.json.JsonSlurper
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.WordUtils

import org.grails.web.json.JSONObject
import org.json.simple.JSONArray
import org.springframework.context.i18n.LocaleContextHolder

class RadioButtonWidgetVue extends CollectionWidgetVue {

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getRadioButtons(dataframe, field, fldName, label)
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
    }

    private String getRadioButtons(DataframeVue dataframe, Map field, String fldName, String label){
        String onSelect = ""
        if(field.onSelect && field.onSelect.methodScript ){
            onSelect = " @change='${dataframe.dataframeName}_onSelect_$fldName' "
            dataframe.getVueJsBuilder().addToMethodScript("""${dataframe.dataframeName}_onSelect_$fldName: function(_params){
                            $field.onSelect.methodScript
             },\n """)
        }
        boolean isReadOnly = dataframe.isReadOnly(field)
        String displayMember = field.displayMember?:'name'
        String valueMember = field.valueMember?:'id'
        String itemsStr = getFieldJSONItems(field)
        String modelString = getFieldJSONModelNameVue(field)
        return """

            <v-radio-group v-model="${modelString}">
                    $label
                    <v-radio
                    v-for="(item,i) in $itemsStr"
                    :key="item.$valueMember"
                    :label = "item.$displayMember"
                    :value="item"
                    $onSelect
                    ></v-radio>
            </v-radio-group>
        """
    }
}
