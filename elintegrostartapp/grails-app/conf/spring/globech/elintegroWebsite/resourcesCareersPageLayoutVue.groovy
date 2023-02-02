package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
import grails.util.Holders

beans {
    vueCareersDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12 class="career-page-container">
                                    <v-card flat style="z-index:100;">
                                        <v-toolbar dark color="blue darken-2" height="100">
                                        <v-toolbar-title class="white--text">[DATAFRAME_LABEL]</v-toolbar-title>
                                        <v-spacer></v-spacer>
                                        </v-toolbar>
                                            <v-container style = "background-color: #fafbfd">
                                                 <v-row class = "justify-center"><h1 style="color: #2c3442;"><b>[careersHeader]</b></h1></v-row>
                                                 <v-row class = "justify-center pa-2">[BUTTON_SCRIPT]</v-row>  
                                                 <v-row class = "justify-center">[careersDescription]</v-row>
                                            </v-container>
                                    </v-card>
                               </v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }



}