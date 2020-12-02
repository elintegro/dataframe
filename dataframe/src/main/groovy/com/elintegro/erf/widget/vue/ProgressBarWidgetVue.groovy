package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueStore

class ProgressBarWidgetVue extends WidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field) {


        return """
                <v-container>
                           <v-row class="fill-height" align-content="center" justify="center">
                              <v-col class="subtitle-1 text-center" cols="12">
                                Loading...
                              </v-col>
                              <v-col cols="12">
                                     <v-progress-linear
                                         color="#1976D2"
                                         v-model = 'state.progressBarValue.progressValue'
                                         height="25">
                                         <strong>{{ Math.ceil(state.progressBarValue.progressValue) }}%</strong>
                                     </v-progress-linear>
                              </v-col>
                           </v-row>
                </v-container>
               
        """
    }
    String getVueDataVariable(DataframeVue dataframe, Map field) {
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToState("progressBarValue",
                [
                        progressValue: '',
                ]
        )

        return """
               """

    }
}
