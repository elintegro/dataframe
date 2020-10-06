package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.ColumnLayout
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
import grails.util.Holders

beans{
    vueElintegroTranslatorAssistantDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text">[DATAFRAME_LABEL]</v-toolbar-title>
                                  </v-toolbar><vueTranslatorAssistantAfterLoggedInDataframe v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
                                  <vueTranslatorAssistantBeforeLoggedInDataframe v-else/>
                                  [DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                                  </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueTranslatorAssistantBeoforeAndAfterLoggedInDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
    vueCreateProjectForTranslationDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl2><v-card round class='rounded-card' ><v-toolbar dark color="light-blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl2']
    }
    vueElintegroTranslatorDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text">[DATAFRAME_LABEL]</v-toolbar-title>
                                  </v-toolbar>
                                  <v-row>
                                  <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4">[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-col>
                                  <v-col cols="12" xs="12" sm="12" md="8" xl="8" lg="8"><vueGridOfTranslatedTextDataframe v-if="isHidden"/></v-col></v-row>
                                  </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    vueAddNewRecordForCurrentProjectDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' >
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeVueAddNewRecordForCurrentProjectDataframe()"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>[DATAFRAME_SCRIPT]<v-card-actions class = "justify-center">[BUTTON_SCRIPT]</v-card-actions></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueEditTextOfNewlyAddedRecordForCurrentProjectDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' >
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeVueEditTextOfNewlyAddedRecordForCurrentProjectDataframe()"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>[DATAFRAME_SCRIPT]<v-card-actions class = "justify-center">[BUTTON_SCRIPT]</v-card-actions></v-card></v-flex> """
    }
    vueGridOfTranslatedTextDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                 [DATAFRAME_SCRIPT]
                                 <v-card-actions class = "justify-center">[BUTTON_SCRIPT]</v-card-actions>
                                 <vueElintegroProgressBarDataframe v-if="progressBarEnable"/>
                                 </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']


    }

    vueEditTranslatedRecordsOfGridDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' >
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeVueEditTranslatedRecordsOfGridDataframe();"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueDeleteTranslatedRecordsOfGridDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' >
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closevueDeleteTranslatedRecordsOfGridDataframe();"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueDialogBoxForNotLoggedInUserDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                               <v-row><v-col cols="12" xs="0" sm="0" md="3" xl="3" lg="3"></v-col><v-col cols="12" xs="0" sm="0" md="6" xl="6" lg="6"><v-card><v-row><v-card-actions class="justify-center"><h1 style="margin-left:20px;">You must be logged in to continue...</h1></v-card-actions></v-row><br><v-row><v-col cols="12" style="margin-left:10px;">[BUTTON_SCRIPT]</v-col></v-row></v-card></v-col><v-col  cols="12" xs="0" sm="0" md="3" xl="3" lg="3"></v-col></v-row>
                                </v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
}