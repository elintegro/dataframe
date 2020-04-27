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
    vueNewEmployeeUploadResumeDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""
    }
    vueNewEmployeeSelfAssesmentDataframeLayout(ColumnLayoutVue) {bean ->
    layoutBeanName = bean.name
    layoutPlaceHolder = """<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""
}
    vueNewEmployeeAddtionalQuestionsDataframeLayout(ColumnLayoutVue) {bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex>"""
    }
    vueElintegroLoginDataframeLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class="rounded-card" style="width:320px; border-radius:10px;"><v-toolbar dark color="light-blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar>[DATAFRAME_SCRIPT]<v-layout align-content-space-around row wrap align-center>[BUTTON_SCRIPT]</v-layout></v-card></v-flex>"""
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
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="grey darken-3" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;">[DATAFRAME_LABEL]</v-toolbar-title>
                                  </v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                                  <v-card class ="applicant">[applicant]</v-card></v-card></v-flex>"""
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
