package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
import grails.util.Holders

beans {
    vueCareersDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="grey darken-3" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text">[DATAFRAME_LABEL]</v-toolbar-title>
                                 <v-spacer></v-spacer>
                                 <vueCareersPageButtonDataframe/>
                                  </v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }



}