package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.ColumnLayout
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
import grails.util.Holders

beans{
    vueElintegroTranslatorAssistantDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                    <v-container class="translatorAssistantContainer" fluid>
                                       <vueMeetTranslatorAssistantIntroDataframe/>
                                       <vueTranslatorAssistantAfterLoggedInDataframe v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
                                       <vueTranslatorAssistantBeforeLoggedInDataframe v-else/>
                                       <vueHowYouDoDataframe/>
                                       <vueNewsLetterDataframe/>
                                       <vueQuizPlaceholderContainerDataframe/> 
                                    </v-container>
                                    <v-container class="footerContainer" fluid>
                                        <vueFooterContainerDataframe/>
                                    </v-container>   
                               </v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueMeetTranslatorAssistantIntroDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                               <v-container class="vueMeetTranslatorAssistantIntroDataframeLayout" fluid>
                                    <v-flex xs12 sm12 md12 lg12 x12>
                                        <v-parallax src="assets/home/rectangle.png" height="120">
                                            <v-row  align="center" justify="center">
                                                <v-col class="text-center" cols="12">
                                                    <h1>[meetTranslatorTitle]</h1>
                                                    <h4>[meetTranslatorSubTitle]</h4>
                                                </v-col>
                                            </v-row>
                                        </v-parallax>
                                    </v-flex>     
                               </v-container>
                            """
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueTranslatorAssistantBeoforeAndAfterLoggedInDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container class="translatorAssistantBeoforeAndAfterLoggedInDataframeLayout" fluid>
                                  <v-flex xs12 sm12 md12 lg12 x12>
                                        <v-row>
                                            <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                            <v-col  cols="12" xs="10" sm="10" md="10" xl="10" lg="10">
                                                <table class="translationAssistantBorder">
                                                    <tr>
                                                        <v-row>
                                                            <h1>[translatorAssistant]</h1>
                                                        </v-row>
                                                    </tr>
                                                    <tr>
                                                        <v-row class="divider"></v-row>
                                                        <v-row class="tranlatorIcon">
                                                            <v-flex xs8 sm6 md6 lg6 xl6>
                                                                <v-img src="assets/home/translatorIcon.png"></v-img>
                                                            </v-flex>    
                                                        </v-row>
                                                        <v-row class="comboboxAndButtons">
                                                            [DATAFRAME_SCRIPT]
                                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                                [BUTTON_SCRIPT]
                                                            </v-flex>    
                                                        </v-row>
                                                    </tr>
                                                </table>    
                                            </v-col>
                                            <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                        </v-row>    
                                  </v-flex>
                                </v-container>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
    vueCreateProjectForTranslationDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl2>
                                   <v-card round class='rounded-card' >
                                        <v-toolbar dark color="#2ab6f6">
                                            <v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                            <v-spacer></v-spacer>
                                            <v-tooltip bottom>
                                                <v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe">
                                                    <v-icon medium >close</v-icon>
                                                </v-btn><span>Close</span>
                                            </v-tooltip>
                                        </v-toolbar>
                                        <v-row class="pa-8">
                                            [project.name] [project.sourceLanguage] [project.sourceFile][save]
                                        </v-row>    
                                   </v-card>
                               </v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl2']
    }
    vueElintegroTranslatorDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                    <v-container class="translatorAssistantContainer" fluid>
                                        <vueMeetTranslatorAssistantIntroDataframe/>
                                        <v-container class="translatorPageContainer" fluid>
                                            <v-row>
                                                <v-col  cols="12" xs="12" sm="12" md="4" xl="4" lg="4">
                                                    <v-card class="borderInTranslation"> 
                                                         <v-row class="px-6 mx-0 listTable">
                                                                [DATAFRAME_SCRIPT]
                                                            <v-row class="mx-0 pa-1">
                                                                <v-container grid-list-md fluid><v-layout wrap class="text-left">
                                                                [addNewRecord][downloadAllTranslatedFiles][projectManager]
                                                            </v-layout></v-container></v-row> 
                                                        </v-row>
                                                    </v-card>
                                                </v-col>
                                                <v-col cols="12" xs="12" sm="12" md="8" xl="8" lg="8"><vueGridOfTranslatedTextDataframe v-if="isHidden"/><vueGridOfSourceTextDataframe v-if="showGridOfSourceText"/></v-col>
                                            </v-row>
                                        </v-container>
                                        <vueHowYouDoDataframe/>
                                       <vueNewsLetterDataframe/>
                                       <vueQuizPlaceholderContainerDataframe/> 
                                    </v-container>
                                    <v-container class="footerContainer" fluid>
                                        <vueFooterContainerDataframe/>
                                    </v-container>   
                                    </v-container>
                                 </v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    vueAddNewRecordForCurrentProjectDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                    <v-card round class='rounded-card' >
                                        <v-flex class="text-right">
                                            <v-tooltip bottom>
                                                <v-btn icon target="_blank" slot="activator" @click.prevent="closeVueAddNewRecordForCurrentProjectDataframe()">
                                                    <v-icon medium >close</v-icon>
                                                </v-btn>
                                                <span>Close</span>
                                            </v-tooltip>
                                        </v-flex>
                                        <v-row class="px-8">
                                           [project.sourceLanguage][key][sourceText][textToTranslate]
                                        </v-row>
                                        <v-row class="pa-8" style="text-align: center;">[save]</v-row>
                                    </v-card>
                                 </v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueEditTextOfNewlyAddedRecordForCurrentProjectDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                    <v-card round class='rounded-card'>
                                        <v-flex class="text-right">
                                            <v-tooltip bottom>
                                                <v-btn icon target="_blank" slot="activator" @click.prevent="closeVueEditTextOfNewlyAddedRecordForCurrentProjectDataframe()">
                                                    <v-icon medium >close</v-icon>
                                                </v-btn><span>Close</span>
                                            </v-tooltip>
                                        </v-flex>
                                        <v-row class="px-1">[DATAFRAME_SCRIPT]</v-row>
                                        <v-row class="pa-1" style="text-align: center;">[save]</v-row>
                                    </v-card>
                              </v-flex> """
    }
    vueGridOfSourceTextDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card class="borderInTranslation">
                                 [DATAFRAME_SCRIPT]
                                 <v-card-actions class = "justify-center">[BUTTON_SCRIPT]</v-card-actions>
                                 </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    vueDeleteSourceRecordsOfGridDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card class="borderInTranslation">
                                 [DATAFRAME_SCRIPT]
                                 <v-card-actions class = "justify-center">[BUTTON_SCRIPT]</v-card-actions>
                                 </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']


    }
    vueGridOfTranslatedTextDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card class="borderInTranslation">
                                    <v-container grid-list-md fluid>
                                        <v-layout wrap class="text-center">
                                                 [translatedText][translateWithGoogle][downloadTargetPropertyFile]
                                                 <vueElintegroProgressBarDataframe v-if="progressBarEnable"/>
                                        </v-layout>
                                    </v-container></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']


    }
    vueHowYouDoDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                <v-container class="howYouDoSection" fluid>
                                    <v-flex xs12 sm12 md12 lg12 x12>
                                        <v-row>
                                            <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                            <v-col cols="12" xs="10" sm="10" md="5" xl="5" lg="5" align-self = 'center'>
                                                 <v-row><h1 class="heading-2">[howYouDo]</h1></v-row><br>
                                                <v-row>
                                                    <p class="paragraph">
                                                        [howYouDoParagraphOne]
                                                    </p>
                                                    <p class="paragraph">
                                                        [howYouDoParagraphTwo]
                                                    </p> 
                                                    <p class="paragraph">   
                                                        [howYouDoParagraphThree]
                                                    </p>
                                                </v-row><br>
                                                <v-row class="translateApp">
                                                        [translateApp]
                                                </v-row>
                                            </v-col>    
                                            <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col> 
                                            <v-col cols="12" xs="12" sm="0" md="4" xl="4" lg="4">
                                                <v-row class="imageRow">
                                                    <v-img alt ="howYouDo" class="image1" src="assets/home/laptoPhoto1.png"></v-img>
                                                    <v-img alt ="howYouDo"  class= "image2" src="assets/home/laptoPhoto2.png"></v-img>
                                                </v-row>
                                             </v-col>
                                             <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                        </v-row>    
                                    </v-flex>
                                </v-container>
                                """
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    vueNewsLetterDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container class= "newsLetter" fluid>
                                    <v-flex xs12 sm12 md12 lg12 x12>
                                        <v-row></v-row><br>
                                        <v-row>
                                            <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                            <v-col cols="12" xs="10" sm="10" md="5" xl="5" lg="5" align-self = 'center'>
                                                <v-row><h1 class="heading-1">[subscribe]</h1></v-row>
                                            </v-col>
                                            <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                            <v-col cols="12" xs="12" sm="12" md="5" xl="5" lg="5" align-self = 'center'>    
                                               <v-row class="subscribeButton">
                                                    [subscribeButton]
                                               </v-row>  
                                            </v-col>
                                            <v-col cols="12" xs="0" sm="0" md="0" xl="0" lg="0"></v-col>    
                                        </v-row>    
                                    </v-flex>
                                </v-container> """
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    vueEditTranslatedRecordsOfGridDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                  <v-card round class='rounded-card'>
                                       <v-flex class="text-right">
                                            <v-tooltip bottom>
                                                   <v-btn icon target="_blank" slot="activator" @click.prevent="closeVueEditTranslatedRecordsOfGridDataframe();"><v-icon medium >close</v-icon>
                                                   </v-btn><span>Close</span>
                                            </v-tooltip>
                                       </v-flex>
                                       <v-row class="px-8">[Key][SourceText][Text]</v-row>[BUTTON_SCRIPT]
                                  </v-card>
                               </v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueEditSourceRecordsOfGridDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                  <v-card round class='rounded-card'>
                                       <v-flex class="text-right">
                                            <v-tooltip bottom>
                                                   <v-btn icon target="_blank" slot="activator" @click.prevent="closeVueEditSourceRecordsOfGridDataframe();"><v-icon medium >close</v-icon>
                                                   </v-btn><span>Close</span>
                                            </v-tooltip>
                                       </v-flex>
                                       <v-row class="px-8">[Key][Text]</v-row>[BUTTON_SCRIPT]
                                  </v-card>
                               </v-flex>"""
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