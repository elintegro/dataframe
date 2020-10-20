package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueStore

class RadioButtonWidgetVue extends WidgetVue {
    String getHtml(DataframeVue dataframe, Map field) {

        return """
                 <v-radio-group v-model="radioGroup">
                       <v-radio
                          v-for="(item,i) in items"
                          :key="i"
                          :label = " item.option"
                          :value="i"
                       ></v-radio>
                 </v-radio-group>  
               """
    }
    String getVueDataVariable(DataframeVue dataframe, Map field) {

        return """
           radioGroup:null,
            items:[
                    {option:'Option 1'}, {option:'option 2'} , {option:'option 3'},{option:'option 4'}
                  ],
                """

    }

}
