package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.vue.DataframeVue
import org.apache.commons.lang.WordUtils
import org.springframework.context.i18n.LocaleContextHolder

class LanguageSelectorWidgetVue extends WidgetVue {

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode?:fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getCombobox(dataframe, field, fldName, label)
        return html
    }
    private String getCombobox(DataframeVue dataframe, Map field, String fldName, String label){
        String onSelect = ""
        if(field.onSelect && field.onSelect.methodScript ){
            onSelect = " @change='${dataframe.dataframeName}_onSelect_$fldName' "
            dataframe.getVueJsBuilder().addToMethodScript("""${dataframe.dataframeName}_onSelect_$fldName: function(_params){
                            $field.onSelect.methodScript
             },\n """)
        }
        dataframe.getVueJsBuilder().addToDataScript(""" select:'English',items: ['English','Chinese','Nepali','Hebrew','Russian',],""")

        """
          <v-select
              v-model = "select" 
              :items = "items"
              label="$label"
              small-chips
              hide-no-data
              hide-selected
              ${toolTip(field)}
              $onSelect
        ></v-select>
        """
    }

}
