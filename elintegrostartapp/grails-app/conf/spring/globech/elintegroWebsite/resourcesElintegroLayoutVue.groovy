package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
import grails.util.Holders
import org.h2.table.Column

beans {
    def contextPath = Holders.grailsApplication.config.rootPath
    emptyDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</div>"""
    }
    vueElintegroContainerLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div id='app' ><v-app class="app" style="background-color:#fff;"><sectionLayout/></v-app></div>"""
        children = ["sectionLayout"]
    }
    sectionLayout(RowLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-content>
                                  <vueElintegroNavigationLayout/>
                                  <vueElintegroMidSectionLayout/>
                                  <vueElintegroFooterLayout/>
                              </v-content>"""
        children = ["vueElintegroNavigationLayout","vueElintegroMidSectionLayout","vueElintegroFooterLayout"]
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    vueElintegroNavigationLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex><v-app-bar fixed  dense  outline elevate-on-scroll scroll-target="#scrolling-techniques-7" flat color="white"  tabs >
                                   <vueElintegroNavigationDrawerDataframe/>
                                   <v-toolbar-title style="position:relative;padding-top:50px;" ><vueElintegroLogoDataframe/></v-toolbar-title>
                                 
                                   <v-spacer></v-spacer>
                                 
                                        <div class="hidden-md-and-down"><vueElintegroAppBarDataframe/></div>
                                         
                                         <vueInitDataframe/>

                                         </v-app-bar>
                                        
         </v-flex>"""
    }
    vueElintegroNavigationDrawerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>
                                     <v-app-bar-nav-icon @click.stop="drawer = !drawer" class="hidden-lg-and-up"></v-app-bar-nav-icon>
                                     <v-navigation-drawer v-model="drawer"
                                         app
                                         temporary
                                         width = "min-content">
                                     <v-list>    
                                      <v-list-item><vueElintegroNavigationFirstTwoButtonDataframe/></v-list-item>
                                      <v-list-item style="margin-left:5%;position:fixed;height:auto;"><vueElintegroAppsDataframe/> </v-list-item>
                                      <v-list-item style="margin-top: 40%;" ><vueElintegroNavigationButtonAfterLoggedInDataframe  v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
                                         <vueElintegroNavigationButtonBeforeLoggedInDataframe v-else/></v-list-item>
                                      <v-list-item style="margin-top:-12%;" ><vueElintegroLanguageSelectorDataframe/></v-list-item>
                                     </v-list>
                                     </v-navigation-drawer>
       </div>"""
    }
    vueElintegroMidSectionLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex style="margin-top:70px;"><v-content>
                             <router-view :key="\$route.fullPath"></router-view>
                             </v-content>
                                </v-flex>
                            """

    }
    vueElintegroFooterLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ """
    }
    vueElintegroLogoDataframeLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>[DATAFRAME_SCRIPT]</v-flex>"""
    }
    vueElintegroAppBarDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>
                                <v-toolbar-items >
                                <v-row style="align-items:baseline;padding-top:6%">
                                  <vueElintegroNavigationFirstTwoButtonDataframe/>
                                  <div style="padding: 40px 0px 0px 0px;"><vueElintegroAppsDataframe/></div>
                                  <vueElintegroNavigationButtonAfterLoggedInDataframe  v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
                                  <vueElintegroNavigationButtonBeforeLoggedInDataframe v-else/> 
                                  <div><vueElintegroLanguageSelectorDataframe/></div>
                                </v-row> 
                                </v-toolbar-items>
        </v-flex> """

    }
    vueElintegroLanguageSelectorDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs 0 sm0 md0 lg0 xl0>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""
    }
    vueElintegroProgressBarLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                        <v-container >
                                                <v-row class="fill-height" align-content="center" justify="center">
                                                  <v-col class="subtitle-1 text-center" cols="12">
                                                    Loading...
                                                  </v-col>
                                                  <v-col cols="12">
                                                       <v-progress-linear
                                                              color="#1976D2"
                                                              v-model = this.progressBarValue
                                                              height="25">
                                                              <strong>{{ Math.ceil(this.progressBarValue) }}%</strong>
                                                       </v-progress-linear>
                                                  </v-col>
                                                </v-row>
                                        </v-container>
                              </v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
    vueElintegroAppsDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ <div >
                                     <v-menu offset-y tile z-index = 101 close-on-content-click>
                                         <template v-slot:activator="{ on, attrs }">
                                             <v-btn color="#1976D2" dark v-bind="attrs" v-on="on" text style="text-transform:capitalize;">About Us</v-btn>
                                         </template>
                                         <v-list width="min-content" style=" margin-right: -39px;margin-left: -37px;">
                                             <v-list-item  @click="">
                                             <v-list-item-title><vueElintegroAboutUsMenuDataframe/></v-list-item-title>
                                             </v-list-item>
                                         </v-list>
                                     </v-menu>
                                     <v-menu offset-y tile z-index = 101 close-on-content-click>
                                         <template v-slot:activator="{ on, attrs }">
                                             <v-btn class="ma-md-0"color="#1976D2" dark v-bind="attrs" v-on="on" text style="text-transform:capitalize;margin-top:10px;">Apps</v-btn>
                                         </template>
                                         <v-list width="min-content" style=" margin-right: -39px;margin-left: -37px;">
                                             <v-list-item  @click="">
                                             <v-list-item-title><vueElintegroSubMenuDataframe/></v-list-item-title>
                                             </v-list-item>
                                         </v-list>
                                     </v-menu>
                                </div>
                             """

    }
    vueElintegroSubMenuDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                  [DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                                  </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueTechnologiesDataframeDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text">[DATAFRAME_LABEL]</v-toolbar-title>
                                  </v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueNewEmployeeBasicInformationDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_SCRIPT]<v-flex class="text-right">[BUTTON_SCRIPT]</v-flex></div>"""
    }
    vueNewEmployeeUploadResumeDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""
    }
    vueNewEmployeeSelfAssesmentDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                 [DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                                 </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueNewEmployeeApplicantEditSkillDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12 ><v-card round class='rounded-card' color="default"  style="overflow: hidden;">
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>
                                [DATAFRAME_SCRIPT]
                                <v-flex class="text-center">[BUTTON_SCRIPT]</v-flex></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueNewEmployeeApplicantAddSkillDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12 ><v-card round class='rounded-card' color="default"  style="overflow: hidden;">
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>
                                [DATAFRAME_SCRIPT]
                                <v-flex class="text-center">[BUTTON_SCRIPT]</v-flex></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }

    vueNewEmployeeAddtionalQuestionsDataframeLayout(ColumnLayoutVue) {bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md6 lg6 xl6>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""
    }
    vueNewEmployeeThankYouMessageAfterSaveDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                               <v-row><v-col cols="4"></v-col><v-col cols="4"><v-card-actions class="justify-center"><h1>Thank You !!!</h1></v-card-actions></v-col><v-col cols="4"></v-col></v-row>
                               <v-row><v-col cols="4"></v-col><v-col cols="4"><v-flex class="text-center"><h3>{{vueNewEmployeeThankYouMessageAfterSaveDataframe_person_fullName}}, your application has been received.</h3></v-flex></v-col><v-col cols="4"></v-col></v-row>
                                <v-row><v-col cols="4"></v-col><v-col cols="4"><v-flex class="text-center"><h3>We will contact you shortly.</h3></v-flex></v-col><v-col cols="4"></v-col></v-row>
                                <v-row><v-col cols="4"></v-col><v-col cols="4"><v-flex class="text-center"><h5>If you have additional questions, please send us an email to contact:<h4>hr@elintegro.com</h4></h5></v-flex></v-col><v-col cols="4"></v-col></v-row>
                             <v-flex>[BUTTON_SCRIPT]</v-flex>
                                </v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueElintegroLoginDataframeLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class="rounded-card" style="width:320px; border-radius:10px;"><v-toolbar dark color="blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar>[DATAFRAME_SCRIPT]<v-flex class="text-center">[BUTTON_SCRIPT]</v-flex></v-card></v-flex>"""
    }
    vueElintegroForgetPasswordDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex><v-row><v-col cols="12" xs="0" sm="0" md="4" xl="4" lg="4"></v-col>
                              <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" style="padding-top:5%;"> <v-card>[DATAFRAME_SCRIPT]<v-card-actions class="justify-center">[BUTTON_SCRIPT]</v-card-actions></v-card></v-col>
                               <v-col cols="12" xs="0" sm="0" md="4" xl="4" lg="4"></v-col></v-row></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }

    vueElintegroRegisterDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' ><v-toolbar dark color="blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar>[DATAFRAME_SCRIPT]<v-flex class="text-right">[BUTTON_SCRIPT]</v-flex></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }

    vueElintegroAfterLoggedinDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card color="light-blue darken-2 mt-2" text id="vueElintegroAfterLoggedinDataframe_id" @click="vueElintegroProfileMenuDataframe_display = true;" ><v-layout row align-center justify-center> [person.mainPicture][REF_FIELD]</v-layout></v-card>"""
    }
//    vueElintegroProfileMenuDataframeLayout(ColumnLayoutVue){bean ->
//        layoutBeanName = bean.name
//        layoutPlaceHolder = """<v-card color="default" text id="vueElintegroProfileMenuDataframe-id" style="overflow: hidden;"><v-form  ref='vueElintegroProfileMenuDataframe_form'><v-container class="pa-2"><v-layout wrap><v-flex xs12 sm12 md12 lg12 xl12><v-subheader class="subheading">Hello, {{vueElintegroProfileMenuDataframe_person_fullName}}</v-subheader></v-flex>[person.mainPicture]</v-layout></v-container></v-form>[BUTTON_SCRIPT]</v-card>"""
//    }
    vueElintegroProfileMenuDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12 style="width:250px;" > <v-card round class='rounded-card' color="default" text id="vueElintegroProfileMenuDataframe-id" style="overflow: hidden;">
                                <v-form  ref='vueElintegroProfileMenuDataframe_form'>
                                <v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon></v-btn><span>Close</span></v-tooltip>
                                <v-flex>[person.mainPicture]</v-flex>
                                <v-flex><v-card-actions class="justify-center"><h3>{{vueElintegroProfileMenuDataframe_person_fullName}}</h3></v-card-actions><v-card-actions class="justify-center">{{vueElintegroProfileMenuDataframe_person_email}}</v-card-actions></v-flex>
                                <v-spacer></v-spacer></v-form><br><v-flex class="text-center">[BUTTON_SCRIPT]</v-flex></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueElintegroUserProfileDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round width='fit-content'><v-toolbar dark color="light-blue darken-2"><v-toolbar-title class="white--text">My Profile</v-toolbar-title><v-spacer></v-spacer></v-toolbar>[vueElintegroUserProfileDataframe]<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[ALL_OTHER_BUTTONS]</v-layout></v-container></v-card-actions></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg3', 'xl2']
    }
    vueElintegroApplicantsDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;">[DATAFRAME_LABEL]</v-toolbar-title>
                                  </v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                                  <v-card class ="applicant">[applicant]</v-card></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueElintegroApplicantDetailsDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12 ><v-card round style ="overflow:hidden;" >
                                  <v-tabs color="white" slider-color="yellow"  background-color="blue darken-2" v-model="vueElintegroApplicantDetailsDataframe_tab_model">
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroApplicantGeneralInformationDataframe-tab-id">General Information</v-tab>
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroApplicantSelfAssessmentDataframe-tab-id">Self Assessment</v-tab>
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroApplicantCVDataframe-tab-id">CV</v-tab>
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroApplicantQuestionAnswerDataframe-tab-id">Questions/Answers</v-tab>
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroCommentPageForApplicantDataframe-tab-id">Comment</v-tab>
                                      <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                      </v-btn><span>Close</span></v-tooltip></v-flex>    
                                  </v-tabs>
                                  <v-tabs-items v-model="vueElintegroApplicantDetailsDataframe_tab_model" >
                                      <v-tab-item value="vueElintegroApplicantGeneralInformationDataframe-tab-id" ><vueElintegroApplicantGeneralInformationDataframe :vueElintegroApplicantGeneralInformationDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                      <v-tab-item value="vueElintegroApplicantSelfAssessmentDataframe-tab-id"><vueElintegroApplicantSelfAssessmentDataframe :vueElintegroApplicantSelfAssessmentDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                      <v-tab-item value="vueElintegroApplicantCVDataframe-tab-id"><vueElintegroApplicantCVDataframe :vueElintegroApplicantCVDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                      <v-tab-item value="vueElintegroApplicantQuestionAnswerDataframe-tab-id"><vueElintegroApplicantQuestionAnswerDataframe :vueElintegroApplicantQuestionAnswerDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                      <v-tab-item value="vueElintegroCommentPageForApplicantDataframe-tab-id"><vueElintegroCommentPageForApplicantDataframe :vueElintegroCommentPageForApplicantDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                 </v-tabs-items></v-card>
                                </v-flex>"""
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']

    }
    vueElintegroApplicantGeneralInformationDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_SCRIPT]
                          <v-flex style="margin-top:-50px; margin-left: 1%;"><v-row>
                          <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12"><span style='color:#757575;'>Selected Positions</span>:&nbsp; {{vueElintegroApplicantGeneralInformationDataframe_person_selectedposition}}</v-col>
                          </v-row>
                          </v-flex>
                          <v-flex class="text-right">[BUTTON_SCRIPT]</v-flex></div>"""
    }
    vueElintegroApplicantQuestionAnswerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md6 lg6 xl6>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""
    }
    vueElintegroCommentPageForApplicantDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md6 lg6 xl6>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""


    }













    /*
    buttonTechnologiesLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex> <router-view name='vueTechnologiesDataframe'/>

                                           </v-flex>"""
    }*/
//    buttonLayout(ColumnLayoutVue){bean ->
//        layoutBeanName = bean.name
//        layoutPlaceHolder = """<v-flex >[home] [clientsProjects] [technologies] [gettingStarted]</v-flex>
//                                   <v-flex>[carrers] [contactUs] [login] [register] </v-flex>"""
//    }


}
