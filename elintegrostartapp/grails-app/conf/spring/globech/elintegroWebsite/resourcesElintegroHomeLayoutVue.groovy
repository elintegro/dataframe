package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.VueJsEntity
import com.elintegro.erf.layout.ColumnLayoutVue

beans {
    vueElintegroHomeDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name

        layoutPlaceHolder = """
                                 <v-row>
                                    <div class = "mainContainer">
                                         <br><br><vueFirstContainerDataframe />
                                         <vueOurWorkContainerDataframe/>
                                         <vueOurProcessContainerDataframe/> 
                                         <vueCollaborationContainerDataframe/>
                                         <vueOurFrameworkContainerDataframe/>
                                         <vueQuotesContainerDataframe/>
                                         <vueOurTechnologiesContainerDataframe/>
                                         <vueQuizPlaceholderContainerDataframe/>
                                    </div>
                                    <v-container class="footerContainer" fluid>
                                        <vueFooterContainerDataframe/>
                                    </v-container>     
                                 </v-row>
                              
                                
                                
                                """
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    vueFirstContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name

        layoutPlaceHolder = """
                                    <v-flex xs12 sm12 md12 lg12 x12 id ="our_home" class = "our_home">
                                   
                                        <v-row >
                           
                                        <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"> 
                                        
                                        </v-col>
                                             <v-col class="homeAnimationColumn" cols="12" xs="9" sm="5" md="5" xl="5" lg="5" align-self = 'center'>
                                                <v-row>
                                                    <h2 class="heading-2">[build]</h2>
                                                    <br>[apps][buildData]
                                                </v-row>
                                                <v-row>
                                                   <p class="paragraph">[youWont]
                                                   </p>
                                                </v-row>
                                                <v-row class="buildApp">
                                                    [buildApp]
                                                </v-row>
                                            </v-col>
                                            
                                            <v-col cols="12" xs="12" sm="5" md="5" xl="5" lg="5" class="text-center">
                                               <v-img alt ="our_home"  src="assets/home/newmobile.png"></v-img>
                                            </v-col>
                                               <v-col cols="12" xs="0" sm="0" md="0" xl="1" lg="1"> </v-col>
                                        </v-row>
                                    </v-flex>
                               

                            """
    }
    vueOurWorkContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ 
                                       
                                        <v-row   class="webSection our_work" id ="our_work">
                                         <v-col cols="12" xs="10" sm="1" md="1" xl="1" lg="1" > </v-col>
                                             <v-col cols="12" xs="10" sm="5" md="5" xl="5" lg="5" data-aos="fade-up">
                                                         <v-img alt ="our_frameworks"  src="assets/home/webMobile.png"></v-img>
                                             </v-col>
                                             <v-col cols="12" xs="10" sm="5" md="5" xl="5" lg="5" align-self="center" data-aos="fade-up" >
                                             
                                                    <h2 class="heading-1 ">[weDeliver]</h2>
                                                                <p class="paragraph" >
                                                                    [WeDeliverTextParagraphOne]
                                                                </p>
                                             </v-col>
                                            <img alt ="our_frameworks"  src="assets/home/webmobilebg3.png" class="webSectionBackgroundImage" ></img>
                                        </v-row>
                            """
    }
    vueOurProcessContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ 
                               
                                       <v-flex xs12 sm12 md12 lg12 x12 id="our_process" class="our_process">
                                            <v-row>
                                            <v-col cols="12" xs="10" sm="1" md="1" xl="1" lg="1"> </v-col>
                                                 <v-col cols="12" xs="10" sm="5" md="5" xl="5" lg="5" align-self = 'center' data-aos="fade-up">
                                                       <v-row >
                                                            <h2 class="heading-1" >[youDeserve]</h2>
                                                       </v-row>
                                                       <v-row>
                                                                <p class="paragraph">[ourProcessTextOne]
                                                                </p><p class="paragraph para">[ourProcessTextTwo]
                                                                </p>
                                                       </v-row>   
                                                 </v-col>
                                                  <v-col cols="12" xs="10" sm="1" md="1" xl="1" lg="1" class="about-us-margin-right"> </v-col>
                                                <v-col cols="12" xs="12" sm="5" md="5" xl="5" lg="5" class="text-center" data-aos="fade-up" >
                                                    <v-img alt ="our_frameworks"  src="assets/home/aboutus.png" style= "width:100%"></v-img>
                                                 </v-col>
                                             
                                            </v-row>       
                                       </v-flex>
                                
                                
                                """
    }
    vueCollaborationContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                             
                                 <v-flex xs12 sm12 md12 lg12 x12 id="collaboration" class="collaboration">
                                      <v-row class= "webSection">
                                            <v-col cols="12" xs="10" sm="1" md="1" xl="1" lg="1"> </v-col>
                                            <v-col cols="12" xs="10" sm="5" md="5" xl="5" lg="5" align-self = 'center' data-aos="fade-up">
                                                  <v-img alt ="our_frameworks"  src="assets/home/appimg.png"></v-img>
                                            </v-col>
                                          
                                            <v-col cols="12" xs="12" sm="5" md="5" xl="5" lg="5" class="text-center" align-self = 'center' data-aos="fade-up">
                                                    <v-row>               
                                                    <h2 class="heading-1">[agilityAsService]</h2>
                                                </v-row>
                                                <v-row>
                                                    <p class="paragraph">[collaborationFirstParagraph]
                                                    </p><p class="paragraph">[collaborationSecondParagraph]
                                                    </p>
                                                </v-row>
                                            </v-col>
                                            <v-col cols="12" xs="10" sm="1" md="1" xl="1" lg="1"> </v-col>
                                      </v-row>       
                                 </v-flex>
                            
                            
                            """
    }
    vueOurFrameworkContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                 
                                            <v-flex xs12 sm12 md12 lg12 x12 id="our_framework" class = "our_framework" data-aos="fade-up">
                                                <v-row class="qualityBuilt">
                                                       <v-col cols="12" xs="10" sm="1" md="1" xl="1" lg="1"> </v-col>
                                                       <v-col cols="12" xs="12" sm="5" md="5" xl="5" lg="5" class="text-center" align-self = 'center'>
                                                              <v-row><h2 class="heading-1">[QualityBuilt]</h2></v-row>
                                                        <v-row>
                                                                <p class="paragraph">[ourFrameworkTextFirstParagraph]
                                                                </p><p class="paragraph">[ourFrameworkTextSecondParagraph]
                                                                </p><br>
                                                        </v-row>
                                                                

                                                    </v-col>
                                                   
                                                       <v-col cols="12" xs="10" sm="5" md="5" xl="5" lg="5" align-self = 'center'>
                                                      <v-img alt ="our_frameworks"  src="assets/home/careerimg.png"></v-img>
                                                    </v-col>
                                                          <img alt ="our_frameworks"  src="assets/home/careerbg.png" class="careerBackground" ></img>
                                                           <v-col cols="12" xs="10" sm="10" md="5" xl="1" lg="1"> </v-col>
                                                </v-row>
                                            </v-flex>
                                   
                                """
    }
    vueQuotesContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                <div  class="Quotes pt-10" >
                                <div class=" flex-column" data-aos="fade-up">
                                  <h1 class="heading">What our customers say about us</h1>
                                  <v-row justify="center">
                                  <v-col cols="12" xs="8" sm="10" md="10" xl="10" lg="7" >    
                                  <v-card elevation="12" class="QuotesPadding " >        
                                    <v-carousel
                                        cycle
                                        height="300"
                                        class=' pa-0'
                                        hide-delimiter-background
                                       
                                        show-arrows
                                      >
                                        <v-carousel-item
                                        class = "comma pr-16" 
                                           v-for="(text,i) in texts"
                                          :key="i"
                                        >

                                        <v-row >
                                        <v-col cols='12' sm="1" md="1" xl="1" lg="1"></v-col>
                                        <v-col cols="12" sm="1" md="1" xl="1" lg="1" class='mt-4'>
                                        <img :src="text.src"></img>
                                        </v-col>
                                        <v-col cols="10" sm="10" md="10" xl="10" lg="10" class="pa-0"></v-col>
                                        </v-row>
                                         <v-row>
                                            <v-col cols="12" sm="2" md="2" xl="2" lg="2" class="pa-0"></v-col>
                                            <v-col cols="12" sm="8" md="8" xl="8" lg="8"class="pa-0">
                                              <div class="text-h5 commas">
                                                {{ text.title}}
                                              </div>
                                              <div class="text-h6 blue--text commas">
                                                {{ text.person}}
                                              </div>
                                               <v-col cols="12"sm="2" md="2" xl="2" lg="2" class="pa-0"></v-col>
                                             </v-col>

                                            </v-row> 
                                        </v-carousel-item>
                                      </v-carousel>

                                      </v-card>
                                      </v-col>
                                      </v-row>
                                      </v-col>
                                    </div>                    
                                  </div>
                            
                            """
    }

    vueOurTechnologiesContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """                  
 <v-row> 
 <v-col cols="12"  md="1" xl="1" lg="1"></v-col>
      <v-col cols="12"  md="10" xl="10" lg="10">
                                              <div class="tech-section py-5">
        <div class="container-fluid section-padding">
            <div class="row">
                <div class="col-md-12">
                    <div class="tech-heading">
                        <h4>This is how we do it</h4>
                    </div>
                    <div class="tech-slider">
                        <div class="owl-carousel owl-theme owl-loaded">
                            <div class="owl-stage-outer">
                                <div class="owl-stage">
                                    <div class="owl-item">
                                        <img src="assets/home/slider1.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider2.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider3.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider4.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider5.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider6.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider7.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider3.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider2.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider5.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider6.png" class="img-fluid" alt="" />
                                    </div>
                                    <div class="owl-item">
                                        <img src="assets/home/slider7.png" class="img-fluid" alt="" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </v-col>
      <v-col cols="12"  md="1" xl="1" lg="1"></v-col>
     </v-row>                       
                            """
    }
    vueQuizPlaceholderContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                           <div id="quiz_placeholder" class="Quiz_Placeholder" data-aos="fade-up">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                               <v-row>
                                                 <v-col cols="12" xs="1" sm="1" md="3" xl="1" lg="3"></v-col>
                                                
                                                   <v-col cols="12" xs="10" sm="10" md="7" xl="10" lg="7">
                                                     <v-row align="center"  justify="center">  <h2 class="heading-1">[letsTalk]</h2> </v-row>              
                                                     <div class="contactAfter">
                                                      <img alt ="our_frameworks"  src="assets/home/contact-before.png" class="contactAfterTop" ></img>
                                                      <img alt ="our_frameworks"  src="assets/home/contact-after.png" class="contactAfterBottom" ></img>
                                                      </div>
                                                       <v-card class="quiz-card-container" align="center"  justify="center">
                                                        <v-row>
                                                            <vueElintegroSignUpQuizDataframe/>
                                                            </v-row>
                                                      </v-card>
                                                   </v-col>
                                                <v-col cols="12" xs="1" sm="1" md="4" xl="1" lg="2"></v-col>
                                               </v-row>
                                            </v-flex>
                                         </div>
                            
                            """
    }
    vueFooterContainerDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                  <footer class="footer-section">
                <div class="col-12">
                    <div class="footer">
                        <ul>
                            <li> [footerPrivacy] </li>
                            <li> [termAndConditions] </li>
                        </ul>
                    </div>
         </div>
    </footer>
                                
                            """
    }
    vueTermAndConditionDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>
                                        <div class="mx-6 my-2">
                                            <div v-html='privacyPolicyContent'></div>
                                        </div>
                                    </v-flex>
                            """
    }
    vueFooterPrivacyDataframeLayout(ColumnLayoutVue) { bean ->
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
    vueElintegroSignUpQuizDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container grid-list-sm fluid>
                                        <v-layout wrap class="text-center">
                                             [lead.leadDescription][lead.leadStage][lead.leadBudget]
                                             [lead.nameOfProject] [lead.deadline]
                                             [lead.descriptionOfProject]
                                             [person.firstName] [person.lastName]
                                             [person.email] [person.phone]
                                             [submit]
                                        </v-layout>
                               </v-container>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
    vueElintegroChangePasswordAfterSignUpDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex><v-row><v-col cols="12" xs="0" sm="0" md="4" xl="4" lg="4"></v-col>
                              <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" style="padding-top:5%;"> <v-card>[DATAFRAME_SCRIPT]<v-card-actions class="justify-center">[BUTTON_SCRIPT]</v-card-actions></v-card></v-col>
                               <v-col cols="12" xs="0" sm="0" md="4" xl="4" lg="4"></v-col></v-row></v-flex>"""

        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
}