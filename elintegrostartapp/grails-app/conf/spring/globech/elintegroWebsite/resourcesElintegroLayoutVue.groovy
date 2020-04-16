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
        layoutPlaceHolder = """<v-flex>
                       [DATAFRAME_SCRIPT]
                       <v-row style="align-items:center;">
                       <v-col cols="6" class="text-left"><span class="layout justify left">[BUTTON_SCRIPT]</span></v-col>
                       <v-col cols="6"class="text-left"><span class="layout justify left">[saveButton] </span></v-col>
                       </v-row>
                       </v-flex>"""
    }
    vueNewEmployeeSelfAssesmentDataframeLayout(ColumnLayoutVue) {bean ->
    layoutBeanName = bean.name
    layoutPlaceHolder = """<v-flex>
                       [DATAFRAME_SCRIPT]
                       <v-row style="align-items:center;">
                       <v-col cols="6" class="text-left"><span class="layout justify left">[BUTTON_SCRIPT]</span></v-col>
                       <v-col cols="6"class="text-left"><span class="layout justify left">[saveButton] </span></v-col>
                       </v-row>
                       </v-flex>"""
}
    vueNewEmployeeAddtionalQuestionsDataframeLayout(ColumnLayoutVue) {bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>
                       [DATAFRAME_SCRIPT]
                       <v-row style="align-items:center;">
                       <v-col cols="6" class="text-left"><span class="layout justify left">[BUTTON_SCRIPT]</span></v-col>
                       <v-col cols="6"class="text-left"><span class="layout justify left">[saveButton] </span></v-col>
                       </v-row>
                       </v-flex>"""
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
