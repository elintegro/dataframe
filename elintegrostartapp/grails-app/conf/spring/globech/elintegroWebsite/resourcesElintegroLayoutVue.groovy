package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
import grails.util.Holders

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
        layoutPlaceHolder = """<v-content><v-card  class="mx-auto overflow-hidden" >
                                  <vueSubContainerDataframe/>
                                     </v-card>
                                     
                              </v-content>"""
        children = ["midSectionLayout"]
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }

    elintegroNavigationButtonLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex> """
    }
    midSectionLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>
                                           <vueInitDataframe/>
                                          
                                           <router-view :key="\$route.fullPath"></router-view>

                                           </v-flex>"""
        isGlobal = true
    }
    subContainerLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex><v-app-bar flat color="white"  tabs style="z-index:99;">
                                   <v-app-bar-nav-icon @click.stop="drawer = !drawer" class="hidden-lg-and-up"></v-app-bar-nav-icon>
                           
                                   <v-toolbar-title style="position:relative;" ><vueElintegroLogoDataframe/></v-toolbar-title>
                                 
                                   <v-spacer></v-spacer>
                                 
      
                                        <div class="hidden-md-and-down"> <vueElintegroNavigationButtonDataframe/></div>
    

                               </v-app-bar>
                               
        <v-navigation-drawer
        v-model="drawer"
        app
        temporary
        width = "min-content"
      >
      <vueElintegroNavigationButtonDataframe/>
       

            
      </v-navigation-drawer>
                                 
                                    <midSectionLayout/>
                               


</v-flex>

                                     """
    }
    clientProjectPageDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="grey darken-3" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;">[DATAFRAME_LABEL]</v-toolbar-title>
                                  </v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                                  <v-card class ="clientProject">[clientProject]</v-card></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    contactUsPageDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container class="text-xs-center"> 
                                 <v-layout row child-flex justify-center align-center wrap>
                                 <v-flex xs12 sm12 md6 lg6 xl6><v-card><v-toolbar dark color="grey darken-3" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;">[DATAFRAME_LABEL]</v-toolbar-title>
                                  </v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>
                                 </v-layout></v-container>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueCareersDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="grey darken-3" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;">[DATAFRAME_LABEL]</v-toolbar-title>
                                 <v-spacer></v-spacer>
                                 <vueCareersPageButtonDataframe/>
                                  </v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
    vueNewEmployeeApplicantDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueNewEmployeeApplicantDataframe_tab_model">
                                    <v-tab style ="text-transform:capitalize;" ripple href="#vueNewEmployeeBasicInformationDataframe-tab-id">Basic Information</v-tab>
                                    <v-tab style ="text-transform:capitalize;" ripple href="#vueNewEmployeeUploadResumeDataframe-tab-id">Upload Resume</v-tab>
                                     <v-tab style ="text-transform:capitalize;" ripple href="#vueNewEmployeeSelfAssesmentDataframe-tab-id">Self Assesment</v-tab>
                                   <v-tab style ="text-transform:capitalize;" ripple href="#vueNewEmployeeAddtionalQuestionsDataframe-tab-id">Additional Questions</v-tab>
                                 </v-tabs>
                                 
                                  <v-tabs-items v-model="vueNewEmployeeApplicantDataframe_tab_model">
                                    <v-tab-item value="vueNewEmployeeBasicInformationDataframe-tab-id">
                                     <vueNewEmployeeBasicInformationDataframe/>
                                     </v-tab-item>
                                     <v-tab-item value="vueNewEmployeeUploadResumeDataframe-tab-id"><vueNewEmployeeUploadResumeDataframe/></v-tab-item>
                                     <v-tab-item value="vueNewEmployeeSelfAssesmentDataframe-tab-id"><vueNewEmployeeSelfAssesmentDataframe/></v-tab-item>
                                     <v-tab-item value="vueNewEmployeeAddtionalQuestionsDataframe-tab-id"><vueNewEmployeeAddtionalQuestionsDataframe/></v-tab-item>
                                     </v-tabs-items></v-card></v-flex>
                                 </v-flex>"""
    }

    vueGettingStartedPageDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                 <v-toolbar dark color="grey darken-3" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;">[DATAFRAME_LABEL]</v-toolbar-title></v-toolbar>
                                 <v-container>
                                 <v-row><v-col cols="4" class = "flex-grow-0 flex-shrink-0"><v-card class = "pa-3" outlined><h1>How to Choose an App Developer</h1></v-card></v-col>
                                 <v-col cols="1" style="min-width: 100px; max-width: 100%;"
                                 class="flex-grow-1 flex-shrink-0"><v-card class = "pa-4" outlined>
                                 <span style="padding-top:40%" class="video-2 w-video w-embed">
                                 <iframe  width="100%" height="500px" src="https://cdn.embedly.com/widgets/media.html?src=https%3A%2F%2Fwww.youtube.com%2Fembed%2FjFLFVlBbLNA%3Ffeature%3Doembed&display_name=YouTube&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DjFLFVlBbLNA&image=https%3A%2F%2Fi.ytimg.com%2Fvi%2FjFLFVlBbLNA%2Fhqdefault.jpg&key=96f1f04c5f4143bcb0f2e68c87d65feb&type=text%2Fhtml&schema=youtube" scrolling="no" title="YouTube embed" frameborder="0" allow="autoplay; fullscreen" allowfullscreen="true"></iframe>
                                 </span></v-card></v-col>
                                 </v-row>
       
                                 </v-container>
                                 [DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                                 
                                 </v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
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
