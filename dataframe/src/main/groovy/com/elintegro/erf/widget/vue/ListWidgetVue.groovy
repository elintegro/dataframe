package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.DbResult
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.converters.JSON
import org.apache.commons.lang.WordUtils
import org.springframework.context.i18n.LocaleContextHolder

class ListWidgetVue extends CollectionWidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        def fldNameDefault = WordUtils.capitalizeFully(fldName);
        String labelCode = field.labelCode ?: fldName
        String label = getMessageSource().getMessage(labelCode, null, fldNameDefault, LocaleContextHolder.getLocale())
        String html = getHtmlStructure(dataframe, field, fldName, label)
        return html
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        def search = field?.search
        return """
                  ${search?"${dataVariable}_search:null,\n":""}
                """

    }

    private String getHtmlStructure(DataframeVue dataframe, Map field, String fldName, String label) {
        String displayMember = field.displayMember ?: 'name'
//        String modelString = getModelString(dataframe, field)
        String modelString = ''
        String itemsStr = getFieldJSONItems(field)
        boolean itemString = field.itemString?:false
        modelString = itemString?itemsStr:getFieldJSONModelNameVue(field)
        String onClick = field.OnClick?:""
        """
           <v-list ${isDisabled(dataframe, field) ? "disabled" : ""} flat ${getAttr(field)}>
                  <v-subheader>$label</v-subheader>
                  <v-list-item-group color="#1976D2">
                       <v-list-item  v-for="(item, i) in ${modelString}" :key="i">
                          <v-list-item-content>
                               <v-list-item-title v-model = "item.$displayMember" v-text="item.${displayMember}" @click="${onClick}">
                               </v-list-item-title>
                          </v-list-item-content>
                       </v-list-item>
                  </v-list-item-group>
           </v-list>
        """
    }
}
