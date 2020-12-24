package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.DataMissingException
import com.elintegro.erf.dataframe.vue.DataframeVue
import org.springframework.context.i18n.LocaleContextHolder

/**
 * For now use a dataframe can contain tab view only without other fields
 */
class TabWidgetVue extends WidgetVue{
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        if(!field.dataframes) throw new DataMissingException("Tab view need a list of dataframes")

        return getHtmlStructure(dataframe, field)
    }

    private String getHtmlStructure(DataframeVue dataframe, Map field){

        StringBuilder tabHeaders = new StringBuilder()
        StringBuilder tabItems = new StringBuilder()
        String initialTabView = ""
        boolean first = true
        for(String dfr: field.dataframes){
            if(!dfr) continue
            Dataframe tabDfr = Dataframe.getDataframeByName(dfr)
            String dfrName = tabDfr.dataframeName
            String dfrLabel = tabDfr.messageSource.getMessage(tabDfr.dataframeLabelCode, null, dfrName, LocaleContextHolder.getLocale())
            tabHeaders.append(""" <v-tab style ="text-transform:capitalize; color:white;background-color:gray;border-top-left-radius: 45%;border-top-right-radius: 45%;" ripple href="#$dfrName-tab-id" @click.stop='onTabClick("$dfrName")'>${dfrLabel}</v-tab>\n""")
            tabItems.append(""" <v-tab-item value="$dfrName-tab-id" ><$dfrName :${dfrName}_prop='${dataframe.dataframeName}_prop'/></v-tab-item>\n""")
            dataframe.childDataframes.add(dfrName)
            if(first){
                initialTabView = "$dfrName-tab-id"
                first = false
            }
        }
        addClickMethodToTab(dataframe, field)
        setDefaultTabView(dataframe, field, initialTabView)
        String html = """<v-card round style ="overflow:hidden;" >
                                  <v-tabs hide-slider background-color="blue darken-2" v-model="state.${dataframe.dataframeName}_tab_model" active-class="cyan">
                                      ${tabHeaders.toString()}
                                      ${getCloseButtonHtml(field)}
                                  </v-tabs>
                                  <v-tabs-items v-model="state.${dataframe.dataframeName}_tab_model" >
                                       ${tabItems.toString()}
                                 </v-tabs-items></v-card>
                                """
        if(field?.layout){
            html = applyLayout(dataframe, field, html)
        }
        return html
    }

    private String getCloseButtonHtml(Map field){
        boolean showCloseButton = field.showCloseButton?:false
        if(showCloseButton){
            return """
                <v-flex class="text-right" style="align-self:center;"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe" style="color:white;"><v-icon medium >close</v-icon>
                                      </v-btn><span>Close</span></v-tooltip></v-flex>    
                """
        }

        return ""
    }

    private void addClickMethodToTab(DataframeVue dataframe, Map fieldProps){
        String onClickScript = fieldProps.onClickScript?:''
        dataframe.getVueJsBuilder().addToMethodScript("""
                onTabClick: function(val){
                    console.log(this.val);
                    $onClickScript
              },
        """)
    }

    private void setDefaultTabView(DataframeVue dataframe, Map field, String initialTabView){
        String defaultTabView = field.defaultTabView?:initialTabView
        dataframe.getVueJsBuilder().getVueStore().addToState("${dataframe.dataframeName}_tab_model", defaultTabView)
    }

}
