package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.VueJsEntity
import com.elintegro.erf.layout.ColumnLayoutVue

beans{
    vueElintegroHomeDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name

        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-row>
                                    <v-container class = "mainContainer" fluid>
                                         <br><br><vueFirstContainerDataframe/>
                                         <vueOurWorkContainerDataframe/>
                                         <vueOurProcessContainerDataframe/> 
                                         <vueCollaborationContainerDataframe/>
                                         <vueOurFrameworkContainerDataframe/>
                                         <vueQuotesContainerDataframe/>
                                         <vueOurTechnologiesContainerDataframe/>
                                         <vueQuizPlaceholderContainerDataframe/>
                                    </v-container>
                                    <v-container class="footerContainer" fluid>
                                        <vueFooterContainerDataframe/>
                                    </v-container>     
                                    </v-row>
                                </v-flex>
                                
                                
                                """
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    vueFirstContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name

        layoutPlaceHolder = """<v-container id ="our_home" class = "our_home" fluid>
                                    <v-flex xs12 sm12 md12 lg12 x12>
                                        <v-row><v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                             <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4" align-self = 'center'>
                                                <v-row>
                                                    <h2 class="heading-2">[build]</h2><div class='console-underscore' id='console'>&#95;</div>[buildData]
                                                </v-row>
                                                <v-row>
                                                   <p class="paragraph">[youWont]
                                                   </p>
                                                </v-row>
                                                <v-row class="buildApp">
                                                    [buildApp]
                                                </v-row>
                                            </v-col>

                                            <v-col cols="12" xs="0" sm="1" md="0" xl="0" lg="0"></v-col> 
                                            <v-col cols="12" xs="12" sm="12" md="6" xl="6" lg="6" class="text-center">
                                               <v-img alt ="our_home"  src="assets/home/mob.png"></v-img>

                                            </v-col>
                                            <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>        
                                        </v-row>
                                    </v-flex>
                                </v-container>

                            """
    }
    vueOurWorkContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                <v-container id ="our_work" class = "our_work" fluid>
                                    <v-flex xs12 sm12 md12 lg12 x12>
                                        <v-row><v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                             <v-col cols="12" xs="10" sm="10" md="5" xl="5" lg="5" align-self = 'center'>
                                                        <v-row>
                                                            <h2 class="heading-2">[weDeliver]</h2>
                                                        </v-row><br>
                                                        <v-row>
                                                                <p class="paragraph">
                                                                    [WeDeliverTextParagraphOne]

                                                                </p>
                                                        </v-row>
                                             </v-col>
                                             <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col> 
                                             <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" class="text-center">
                                                <v-img alt ="our_frameworks"  src="assets/home/weDeliver.png"></v-img>
                                             </v-col>
                                             <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>  
                                        </v-row>
                                    </v-flex>    
                                </v-container>
                            """
    }
    vueOurProcessContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ 
                                <v-container id="our_process" class="our_process" fluid>
                                       <v-flex xs12 sm12 md12 lg12 x12>
                                            <v-row>
                                                 <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                 <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" class="text-center">
                                                    <v-img alt ="our_frameworks"  src="assets/home/factoryGrade.png"></v-img>
                                                 </v-col>
                                                 <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col> 
                                                 <v-col cols="12" xs="10" sm="10" md="5" xl="5" lg="5" align-self = 'center'>
                                                       <v-row >
                                                            <h2 class="heading-2">[youDeserve]</h2>
                                                       </v-row><br>
                                                       <v-row>
                                                                <p class="paragraph">[ourProcessTextOne]
                                                                </p><p class="paragraph">[ourProcessTextTwo]
                                                                </p>
                                                       </v-row>
                                                 </v-col>
                                                 <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>  
                                            </v-row>       
                                       </v-flex>
                                </v-container>
                                
                                """
    }
    vueCollaborationContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""
                             <v-container id="collaboration" class="collaboration" fluid>
                                 <v-flex xs12 sm12 md12 lg12 x12>
                                      <v-row>
                                            <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                            <v-col cols="12" xs="10" sm="10" md="5" xl="5" lg="5" align-self = 'center'>
                                                <v-row>
                                                    <h2 class="heading-2">[agilityAsService]</h2><br>
                                                </v-row><br>
                                                <v-row>
                                                    <p class="paragraph">[collaborationFirstParagraph]
                                                    </p><p class="paragraph">[collaborationSecondParagraph]
                                                    </p>
                                                </v-row>
                                            </v-col>
                                            <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col> 
                                            <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" class="text-center">

                                                        <v-img alt ="our_frameworks"  src="assets/home/qualityBuilt.png"></v-img>

                                            </v-col>
                                            <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>  
                                      </v-row>       
                                 </v-flex>
                             </v-container>
                            
                            """
    }
    vueOurFrameworkContainerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                   <v-container id="our_framework" class = "our_framework" fluid>
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                <v-row>
                                                    <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col> 
                                                       <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" class="text-center">

                                                                <v-img alt ="our_frameworks"  src="assets/home/agilityAsService.png"></v-img>

                                                    </v-col>
                                                    <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col> 
                                                       <v-col cols="12" xs="10" sm="10" md="5" xl="5" lg="5" align-self = 'center'>
                                                        <v-row><h2 class="heading-2">[QualityBuilt]</h2></v-row><br>
                                                        <v-row>
                                                                <p class="paragraph">[ourFrameworkTextFirstParagraph]
                                                                </p><p class="paragraph">[ourFrameworkTextSecondParagraph]
                                                                </p><br>
                                                        </v-row>
                                                    </v-col>
                                                    <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>        
                                                </v-row>
                                            </v-flex>
                                   </v-container>
                                """
    }
    vueQuotesContainerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                            <v-container id="Quotes" class="Quotes" fluid>
                                <v-flex xs12 sm12 md12 lg12 x12>
                                    <v-row>
                                        <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col> 
                                            <v-col class = "midColumn" cols="12" xs="12" sm="12" md="10" xl="10" lg="10">
                                                <v-row><h1 class="heading">[customerSay]</h1></v-row>
                                                <v-row>
                                                    [DATAFRAME_SCRIPT]
                                                </v-row>
                                            </v-col>
                                        <v-col cols="12" xs="0" sm="0" md="0" xl="0" lg="0"></v-col>        
                                    </v-row>
                                </v-flex>
                            </v-container>
                            
                            """
    }
    vueOurTechnologiesContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                          <v-container id="our_Technologies" class="our_Technologies" fluid>
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                <v-row class ="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12">
                                                   <h2 class="heading-2">[thisIsHow]</h2></v-col>
                                                </v-row><br><br>
                                                <v-row>
                                                    <v-col cols="2"></v-col>
                                                        <v-col cols="2" class="images">    
                                                        <v-img src="assets/home/java.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col>
                                                        <v-col cols="2"  class="images">
                                                        <v-img src="assets/home/nodejs.png"></v-img>
                                                    </v-col> 
                                                    <v-col cols="1"></v-col> 
                                                        <v-col cols="2"  class="images">
                                                        <v-img src="assets/home/mysql.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="2"></v-col> 
                                                </v-row>
                                                <v-row>
                                                    <v-col cols="2"></v-col>
                                                        <v-col cols="2" class="images">
                                                        <v-img src="assets/home/vue.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col>
                                                        <v-col cols="2" class="images">
                                                        <v-img src="assets/home/grails.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col> 
                                                        <v-col cols="2"  class="images">
                                                        <v-img src="assets/home/kubernetes.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col>
                                                </v-row>
                                                <v-row>
                                                    <v-col cols="2"></v-col> 
                                                    <v-col cols="2" class="images">
                                                        <v-img src="assets/home/kafka.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col> 
                                                        <v-col cols="2" class="images">
                                                        <v-img src="assets/home/oracle.png"></v-img>
                                                    </v-col> 
                                                    <v-col cols="1"></v-col> 
                                                        <v-col cols="2" class="images">
                                                        <v-img src="assets/home/javascript.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="2"></v-col> 
                                                </v-row>
                                            </v-flex>
                                         </v-container>
                            """
    }
    vueQuizPlaceholderContainerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                            <v-container id="quiz_placeholder" class="Quiz_Placeholder" fluid>
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                               <v-row></v-row><br><br><br>
                                               <v-row>
                                                 <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                                    <v-col class="columnLeft" cols="12" xs="10" sm="10" md="4" xl="4" lg="4" align-self = 'center'>
                                                        <v-row></v-row>
                                                        <v-row><h2 class="heading-1">[letsTalk]</h2></v-row>
                                                    </v-col>
                                                 <v-col cols="12" xs="0" sm="0" md="2" xl="2" lg="2"></v-col>
                                                    <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4">
                                                       <v-card class="v-card">
                                                            <vueElintegroSignUpQuizDataframe/>
                                                       </v-card>
                                                    </v-col>
                                                <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                </v-row>
                                               <br><br><br><v-row></v-row>
                                            </v-flex>
                                         </v-container>
                            
                            """
    }
    vueFooterContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                    <v-container class="footer" fluid>
                                        <v-row>
                                            <v-col class="photoCol" cols="12" xs= "12" sm="12" md="6" lg="6">
                                                <v-img src="assets/home/plant.png"></v-img>
                                            </v-col>
                                            <v-col cols="12" xs= "12" sm="12" md="12" lg="12">    
                                                <v-row class = "footerTexts" align="center" justify="end"
                                                >
                                                    [footerPrivacy][termAndConditions]
                                                </v-row>
                                            </v-col>
                                        </v-row>                  
                                    </v-container>
                                
                            """
    }
    vueFooterPrivacyDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12 ><v-card round class='rounded-card' color="default"  style="overflow: hidden;">
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>
                                <v-row>
                                <v-row class="privacyPolicyHeadingText">[privacyPolicyHeading]</v-row>
                                <v-row>[loremEpsumText]</v-row>
                                </v-row>
                                [DATAFRAME_SCRIPT]
                                <v-flex class="text-center">[BUTTON_SCRIPT]</v-flex></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueTermAndConditionDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12 ><v-card round class='rounded-card' color="default"  style="overflow: hidden;">
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>
                                <v-row>
                                <v-row class="termAndConditionHeadinggText">[termAndConditionHeading]</v-row>
                                <v-row>[loremEpsumText]</v-row>
                                </v-row>
                                [DATAFRAME_SCRIPT]
                                <v-flex class="text-center">[BUTTON_SCRIPT]</v-flex></v-card></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueElintegroSignUpQuizDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>[DATAFRAME_SCRIPT]<v-card-actions class="justify-center">[BUTTON_SCRIPT]</v-card-actions></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
    vueElintegroChangePasswordAfterSignUpDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex><v-row><v-col cols="12" xs="0" sm="0" md="4" xl="4" lg="4"></v-col>
                              <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" style="padding-top:5%;"> <v-card>[DATAFRAME_SCRIPT]<v-card-actions class="justify-center">[BUTTON_SCRIPT]</v-card-actions></v-card></v-col>
                               <v-col cols="12" xs="0" sm="0" md="4" xl="4" lg="4"></v-col></v-row></v-flex>"""

        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
}