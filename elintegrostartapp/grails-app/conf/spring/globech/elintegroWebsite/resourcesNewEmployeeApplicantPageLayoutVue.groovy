package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
import grails.util.Holders

beans {
    vueNewEmployeeApplicantDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12 class="mt-9"><v-card round class="mt-9" >
                                <v-tabs  color="white" slider-color="yellow"  background-color="blue darken-2" v-model="vueNewEmployeeApplicantDataframe_tab_model">
                                    <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueNewEmployeeBasicInformationDataframe-tab-id">Basic Information</v-tab>
                                    <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueNewEmployeeUploadResumeDataframe-tab-id">Upload Resume</v-tab>
                                     <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueNewEmployeeSelfAssesmentDataframe-tab-id">Self Assesment</v-tab>
                                   <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueNewEmployeeAddtionalQuestionsDataframe-tab-id">Additional Questions</v-tab>
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


}