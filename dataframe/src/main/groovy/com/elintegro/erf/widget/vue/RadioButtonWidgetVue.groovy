package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import org.apache.commons.lang.WordUtils

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
        Map fldJSON = getDomainFieldJsonMap(dataframe, field)
        fldJSON?.put("items", res)
        return domainFieldMap
    }

    private String getRadioButtons(DataframeVue dataframe, Map field, String fldName, String label){
        String onSelect = ""
        VueJsBuilder store = dataframe.getVueJsBuilder()
        if(field.onSelect && field.onSelect.methodScript ){
            onSelect = " @change='${dataframe.dataframeName}_onSelect_$fldName' "
            store.addToMethodScript("""${dataframe.dataframeName}_onSelect_$fldName: function(_params){
                            $field.onSelect.methodScript
             },\n """)
        }
        boolean isReadOnly = dataframe.isReadOnly(field)
        String displayMember = field.displayMember?:'name'
        String valueMember = field.valueMember?:'id'
        String radioGroupAttr = field.radioGroupAttr?:""
        String itemsStr = getFieldJSONItems(field)
        String modelString = getFieldJSONModelNameVue(field)
        store.addToMethodScript("""${fldName}_selectedItem : function(selectedItem){
                                                if(selectedItem instanceof Object){
                                                     this.${modelString}=this.${itemsStr}.find(i => i.$valueMember === selectedItem.$valueMember);
                                                }
                                         },\n""")
        return """

            <v-radio-group v-model="${modelString}" :value-comparator="${fldName}_selectedItem(${modelString})" $radioGroupAttr>
                    $label
                    <v-radio
                    v-for="(item,i) in $itemsStr"
                    :key="item.$valueMember"
                    :label = "item.$displayMember"
                    :value="item"
                    ${getAttr(field)}
                    $onSelect
                    ></v-radio>
            </v-radio-group>
        """
    }
}
