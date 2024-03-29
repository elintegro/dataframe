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
        layoutPlaceHolder = """

<div>
<v-flex>   

                                    <v-app-bar id="banner" class= "bannerSection" fixed  outline elevate-on-scroll  flat color="white"  tabs   >   
                                 
                                       <vueElintegroNavigationDrawerDataframe/>
                             
                                       <v-toolbar-title class="appBar">  
                                            <vueElintegroLogoDataframe/>
                                       </v-toolbar-title>
                                       
                                       <v-spacer></v-spacer>
                                       <vueAlertMsgDataframe/>
                                       <v-toolbar-items class="hidden-md-and-down prominent navigationItem">
                                            <vueElintegroNavigationFirstTwoButtonDataframe/>
                                            <vueElintegroAppsDataframe/>
                                            <vueElintegroNavigationButtonAfterLoggedInDataframe  v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
                                            <vueElintegroNavigationButtonBeforeLoggedInDataframe v-else/> 
                                            <vueElintegroLanguageSelectorDataframe/>
                                       </v-toolbar-items> 
                                        
                                       <vueInitDataframe/>
                              
                                    </v-app-bar>
                                   
        </v-flex>
        </div>
        """
    }
    vueElintegroNavigationDrawerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """  <div>
                                      <div style="text-align: left" class="hidden-lg-and-up">
                                          <v-app-bar-nav-icon class="toggle" style="background-size:auto;" @click="drawerVisible = true" > <v-img src="/assets/home/nav-toggle.png"></v-img></v-app-bar-nav-icon>
                                      </div>
                                       
                                      <div class="navigationDrawer"
                                          :style="{
                                                   width: drawerVisible ? 'min-content' : '0',
                                          }"
                                          >
                                         
                                          <v-row class="navigationDrawerContent">
                                              <h4 class="drawerContentWithHomeButton"><vueElintegroNavigationFirstTwoButtonDataframe/></h4>
                                              <h4 class="drawerContentWithAppsDataframe"><vueElintegroAppsDataframe/></h4>
                                              <h4 class="drawerContentWithNavigationButtonsBeforeOrAfterLoggedIn"><vueElintegroNavigationButtonAfterLoggedInDataframe  v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
                                                     <vueElintegroNavigationButtonBeforeLoggedInDataframe v-else/></h4>
                                              <h4 class="drawerContentWithLanguageSelector"><vueElintegroLanguageSelectorDataframe/></h4>
                                          </v-row>
                                      
                                      </div>
                                      <div class="drawer-mask"
                                          :style="{
                                                width: drawerVisible ? '100vw' : '0',
                                                opacity: drawerVisible ? '0.6' : '0',
                                          }"
                                          @click="drawerVisible = false"
                                      ></div>
        </div>"""
    }
    vueElintegroMidSectionLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex style="margin-top:100px;"><v-content>
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
        layoutPlaceHolder = """[logo]"""
    }
    vueElintegroLanguageSelectorDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """[languages]"""
    }
    vueElintegroProgressBarLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                              [DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                              </v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
    vueElintegroAppsDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ 
                                <v-row class="mx-0">
                                [DATAFRAME_SCRIPT]
                                     <v-menu offset-y tile z-index = 101 close-on-content-click>
                                         <template v-slot:activator="{ on, attrs }">
                                             <v-flex xs0 sm0 md0 lg0 xl0>
                                             <v-btn color="#212121" type="link" dark v-bind="attrs" v-on="on" text style="text-transform:capitalize;" class="navigation-hover">About Us</v-btn>
                                             </v-flex>
                                         </template>
                                         <v-list width="min-content" style=" margin-right: -30px;margin-left: -30px;">
                                             <v-list-item  @click="">
                                             <v-list-item-title><vueElintegroAboutUsMenuDataframe/></v-list-item-title>
                                             </v-list-item>
                                         </v-list>
                                     </v-menu>
                                     <v-menu offset-y tile z-index = 101 close-on-content-click>
                                         <template v-slot:activator="{ on, attrs }">
                                             <v-flex xs0 sm0 md0 lg0 xl0>
                                             <v-btn color="#212121" dark v-bind="attrs" v-on="on" text style="text-transform:capitalize;" class="navigation-hover" >Apps</v-btn>
                                             </v-flex>
                                         </template>
                                         <v-list width="min-content" style=" margin-right: -30px;margin-left: -30px;">
                                             <v-list-item  @click="">
                                             <v-list-item-title><vueElintegroSubMenuDataframe/></v-list-item-title>
                                             </v-list-item>
                                         </v-list>
                                     </v-menu>
                                </v-row>
                             """
    }
    vueElintegroSubMenuDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                  [DATAFRAME_SCRIPT]
                                  <div style="margin-top:-40px; height:125px;">[BUTTON_SCRIPT]</div>
                                  </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueElintegroAboutUsMenuDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                  [DATAFRAME_SCRIPT]
                                  <div style="margin-top:-40px; height:320px; margin-left:-4px;">[BUTTON_SCRIPT]</div>
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
        layoutPlaceHolder = """<v-container id="newEmployeeBasicInformation" class="newEmployeeBasicInformation" fluid>
                                [DATAFRAME_SCRIPT]
                                <v-flex class="text-right">[BUTTON_SCRIPT]</v-flex>
                              </v-container>"""
    }
    vueAddressDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>[DATAFRAME_LABEL][DATAFRAME_SCRIPT]<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[ALL_OTHER_BUTTONS][googleMap]</v-layout></v-container></v-card-actions></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueNewEmployeeUploadResumeDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container id="newEmployeeUploadResume" class="newEmployeeUploadResume" fluid>
                                    <v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>
                               </v-container>"""
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
        layoutPlaceHolder = """<v-container id="newEmployeeAdditionalQuestions" class="newEmployeeAdditionalQuestions" fluid>
                                    <v-flex xs12 sm12 md6 lg6 xl6>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>
                               </v-container>"""
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
    vueElintegroLoginTabDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ [tab]"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']

    }
    vueElintegroLoginDataframeLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ <v-flex xs12 sm12 md12 lg12 xl12>
                                  <v-container grid-list-sm><v-layout wrap class="px-4 pt-4 align-center text-end">
                                        [user.username][user.password]
                                        <v-layout row wrap class="text-center align-center">
                                            [login][orTextInLogin][logInWithGoogle][logInWithFacebook]
                                        </v-layout>
                                        [rememberMe][forgetPassword]
                                  </v-layout></v-container></v-flex>
                               """
    }
    vueElintegroLoginWithOTPDataframeLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                <v-container grid-list-sm><v-layout wrap class="pa-4 pt-4 text-center">
                                    [email][sendCode][verificationCode]
                                    <v-layout wrap class="text-start">
                                        [codeNotReceived][resendCode]
                                    </v-layout>
                                    [submit]
                                </v-layout></v-container></v-flex>
                              """
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
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                    <v-card round class='rounded-card' >
                                        <v-toolbar dark color="blue darken-2" class="mb-5">
                                            <v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                            <v-spacer></v-spacer>
                                            <v-tooltip bottom>
                                                <v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe">
                                                    <v-icon medium >close</v-icon>
                                                </v-btn><span>Close</span>
                                            </v-tooltip>
                                        </v-toolbar>
                                            <v-container grid-list-sm><v-layout wrap class="text-center px-4 pb-4">
                                                [user.email]
                                                [user.firstName][user.lastName]
                                                [user.password][password2]
                                                [saveButton]
                                            </v-layout></v-container>
                                    </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }

    vueElintegroAfterLoggedinDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card color="light-blue darken-2 mt-2" text id="vueElintegroAfterLoggedinDataframe_id" @click="vueElintegroProfileMenuDataframe_display = true;" ><v-layout row align-center justify-center> [person.mainPicture][REF_FIELD]</v-layout></v-card>"""
    }

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
        layoutPlaceHolder = """ [DATAFRAME_SCRIPT]"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']

    }
    vueElintegroApplicantGeneralInformationDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                              <v-container id="newEmployeeBasicInformation" class="applicantGeneralInformation" fluid>
                                [DATAFRAME_SCRIPT]
                                 <v-flex style="margin-top:-45px; margin-left: -1%;">
                                   <v-row>
                                    <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12"><span style='color:#757575;'>Selected Positions</span>:&nbsp; {{vueElintegroApplicantGeneralInformationDataframe_person_selectedposition}}</v-col>
                                   </v-row>
                                </v-flex>
                                <v-flex class="text-right">[BUTTON_SCRIPT]</v-flex>
                              </v-container>"""
    }
    vueElintegroApplicantQuestionAnswerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container id="applicantQuestionAnswer" class="applicantQuestionAnswer" fluid>
                                    <v-flex xs12 sm12 md6 lg6 xl6>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>
                               </v-container>"""
    }
    vueElintegroCommentPageForApplicantDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container id="applicantQuestionAnswer" class="applicantCommentPage" fluid>
                                    <v-flex xs12 sm12 md6 lg6 xl6>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>
                               </v-container>"""


    }

}
