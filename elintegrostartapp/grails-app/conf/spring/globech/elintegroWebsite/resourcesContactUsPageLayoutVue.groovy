package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
import grails.util.Holders

beans {

    contactUsPageDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container class="text-xs-center"> 
                                 <v-layout row child-flex justify-center align-center wrap>
                                 <v-flex xs12 sm12 md6 lg6 xl6>
                                    <v-card color="#fafbfd">
                                        <v-toolbar dark color="blue darken-2" class="mb-7" height="100">
                                            <v-spacer></v-spacer>
                                            <v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                            <v-spacer></v-spacer>
                                        </v-toolbar>
                                        [DATAFRAME_SCRIPT]
                                        <v-flex xs12 sm12 md12 lg12 xl12 class="text-center">
                                            [BUTTON_SCRIPT]
                                        </v-flex>
                                    </v-card>
                                 </v-flex></v-layout></v-container>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
}

