package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.VueJsEntity
import com.elintegro.erf.layout.ColumnLayoutVue

beans{
    vueElintegroHomeDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                    <v-container>
                                         <br><br><vueFirstContainerDataframe/><br><br>
                                         <vueOurWorkContainerDataframe/><br><br>
                                         <vueOurProcessContainerDataframe/><br>  
                                         <vueCollaborationContainerDataframe/><br>
                                         <vueOurFrameworkContainerDataframe/><br><br><br>
                                         <vueQuotesContainerDataframe/><br>
                                         <vueOurTechnologiesContainerDataframe/><br>
                                         <vueQuizPlaceholderContainerDataframe/><br>
                                         <vueFooterContainerDataframe/>   
                                    </v-container>
                                </v-flex>
                                
                                """
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
    vueFirstContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container>
                                    <v-row><v-col cols="1"></v-col>
                                        <v-col cols="10"><div class = "hidden-sm-and-down"><h1 style ='font-size: 70px;font-family:sans-serif;'>
                                            [hey][LooksLike]<v-row style="padding-left: 12px;margin-right: 190px;">[Built][AnyApps]</v-row>[Yet]
                                        </h1></div></v-col><v-col cols="1"></v-col>
                                        <vueFirstContainerResizeDataframe/>    
                                    </v-row>
                                    <v-row class="text-center">[youWont]</v-row><br><br>
                                    <v-row class="text-center">[buildApp]</v-row>
                                   
                                </v-container>

                            """
    }
    vueFirstContainerResizeDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                     <v-flex><div class = "hidden-md-and-up"><div class="text-center"><h1 style ='font-size: 63px;'>
                                            [hey][LooksLike]<v-row>[Built][AnyApps]</v-row>[Yet]  
                                        </h1></div></div></v-flex>      
                            """
    }
    vueOurWorkContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                <v-container id ="our_work" class = "our_work">
                                    <v-row class="pa-md-0" style="padding:10%"><v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                         <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4" >
                                                    <v-row class="label" >[labelOurWork]</v-row>
                                                    <v-row>
                                                        <h2 class="heading-2">[weDeliver]</h2>
                                                    </v-row>
                                                    <v-row>
                                                            <p>
                                                                [WeDeliverTextParagraphOne]<br>
                                                                [WeDeliverTextParagraphTwo]
                                                            </p>
                                                    </v-row></v-col>
                                                <v-col cols="12" xs="1" sm="1" md="2" xl="2" lg="2"></v-col> 
                                         <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4">
                                                     <v-img alt ="Our_work"  src="assets/home/our_work.svg"></v-img>
                                         </v-col>
                                         <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>        
                                    </v-row>
                                </v-container>
                            """
    }
    vueOurProcessContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """ 
                                <v-container id="our_process" class="our_process">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                <v-row class="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12" class="label">[labelOurProcess]</v-col>
                                                </v-row>
                                                <v-row class ="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12"><h2 class="heading-2">[youDeserve][efficiency]</h2></v-col>
                                                </v-row>
                                                <v-row class="text-center" >
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12" ><p>[ourProcessTextOne]
                                                   [ourProcessTextTwo]<br>
                                                   [ourProcessTextThree]
                                                   [ourProcessTextFour]</p></v-col>
                                                </v-row>
                                                <v-row class="text-center">
                                                   <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col><v-col cols="12" xs="12" sm="12" md="10" xl="10" lg="10"><v-img alt ="our_process" src="assets/home/our_process.png" style="background-color:#E6E6E6"></v-img></v-col><v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                </v-row>
                                            </v-flex>
                                </v-container>
                                
                                """
    }
    vueCollaborationContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""
                             <v-container id="collaboration" class="collaboration">
                                            <v-flex xs12 sm12 md12 lg12 x12 class="hidden-sm-and-down">
                                                <v-row>
                                                    <v-col cols="1"></v-col><v-col cols="5">
                                                       <v-img alt ="Collaboration"  src="assets/home/collaboration.svg"></v-img>
                                                    </v-col>
                                                    <v-col cols="1" ></v-col>
                                                    <v-col cols="4" style="padding-top:5%;">
                                                        <v-row class="label" >[labelCollaboration]</v-row>
                                                        <v-row>
                                                            <h2 class="heading-2">[agilityAsService]</h2>
                                                        </v-row>
                                                        <v-row style="font-size:104%; font-family:sans-serif;">
                                                                <p>[collaborationFirstParagraph]
                                                                </p><p>[collaborationSecondParagraph]
                                                                </p>
                                                        </v-row>
                                                    </v-col> 
                                                </v-row>       
                                             <v-col cols="1"></v-col>    
                                            </v-flex>
                                            <vueCollaborationContainerResizeDataframe/>
                                        </v-container>
                            
                            """
    }
    vueCollaborationContainerResizeDataframeLayout(ColumnLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                            <v-flex xs12 sm12 md12 lg12 x12 class="hidden-md-and-up">
                                                <v-row>
                                                    <v-col cols="12" style="padding:8%;">
                                                        <v-row class="label" style = 'color: #29b6f6;font-size:14px;'>[labelCollaboration]</v-row>
                                                        <v-row>
                                                            <h2 class="heading-2">[agilityAsService]</h2>
                                                        </v-row>
                                                        <v-row style="font-size:118%; font-family:sans-serif;">
                                                                <p>[collaborationFirstParagraph]
                                                                </p><p>[collaborationSecondParagraph]
                                                                </p>
                                                        </v-row>
                                                    </v-col> 
                                                    <v-col cols="12">
                                                       <v-img alt ="Collaboration"  src="assets/home/collaboration.svg"></v-img>
                                                    </v-col>
                                                </v-row>       
                                            </v-flex>
                            """
    }
    vueOurFrameworkContainerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                   <v-container id="our_framework" class = "our_framework">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                <v-row>
                                                    <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col> 
                                                    <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" style="padding:8%" class="pa-md-0">
                                                        <v-row class="label" >[labelOurFrameWork]</v-row>
                                                        <v-row><h2 class="heading-2" style ="font-size:45px;">[QualityBuilt]</h2></v-row>
                                                        <v-row style="font-size:118%; font-family:inherit;" class="hidden-md-and-up">
                                                                <p>[ourFrameworkTextFirstParagraph]
                                                                </p><p>[ourFrameworkTextSecondParagraph]
                                                                </p><br>
                                                                <v-row class="text-center">[buttonDataframeOnGithub]</v-row>
                                                        </v-row>
                                                        <vueOurFrameworkContainerResizeDataframe/>
                                                    </v-col>
                                                    <v-col cols="12" xs="0" sm="0" md="2" xl="2" lg="2"></v-col> 
                                                    <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" class="text-center">
                                                                <v-img alt ="our_frameworks"  src="assets/home/our_framework.svg"></v-img>
                                                    </v-col>
                                                    <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>        
                                                </v-row>
                                            </v-flex>
                                        </v-container>
                                """
    }
    vueOurFrameworkContainerResizeDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""
                                     <v-row style="font-family:inherit;" class="hidden-sm-and-down">
                                                                <p>[ourFrameworkTextFirstParagraph]
                                                                </p><p>[ourFrameworkTextSecondParagraph]
                                                                </p><br>
                                                                [buttonDataframeOnGithub]
                                                        </v-row>
                            """
    }
    vueQuotesContainerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                            <v-container id="Quotes" class="Quotes">
                                             <v-flex xs12 sm12 md12 lg12 x12>
                                                 <v-row>
                                                    <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                                    <v-col cols ="12" xs="10" sm="10" md="10" xl="10" lg="10" style="background-color:#E1F5FE; border-radius:20px;"><v-row></v-row><br><br><br>
                                                        <v-row> <v-col cols="12" xs="0" sm="0" md="2" xl="2" lg="2"></v-col>
                                                          <v-col cols="12" xs="10" sm="10" md="7" xl="7" lg="7" class="text-center"><h1><p id="quotes">[InitialQuote]</p></h1></v-col>
                                                          <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                        </v-row>
                                                        <v-row><v-col cols="12" xs="10" sm="10" md="10" xl="10" lg="10" style = "font-family: sans-serif;font-size:14px;" class="text-center" id = "nameOfPerson">[nameOfPerson]</v-col></v-row>
                                                        <v-row><v-col cols="12" xs="10" sm="10" md="10" xl="10" lg="10" style = "font-family: sans-serif;font-size:14px;"class="text-center" id = "jobTitle">[jobTitle]</v-col></v-row><br>
                                                        
                                                        <v-row><v-col cols="12" xs="1" sm="1" md="3" xl="3" lg="3"></v-col><v-col cols="12" xs="3" sm="3" md="2" xl="2" lg="2"><a v-on:click="hello('coachClone');"><v-img id = "coachClone"src="assets/home/coachClone.png" style="background-color:#E1F5FE;opacity: 0.25;"></v-img></a></v-col><v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col><v-col cols="12" xs="3" sm="3" md="2" xl="2" lg="2"><a v-on:click="hello('weBus');"><v-img id= "weBus" src="assets/home/weBus.jpg" style="background-color:#E1F5FE;opacity: 0.25;"></v-img></a></v-col><v-col cols="12" xs="2" sm="2" md="2" xl="2" lg="2"></v-col></v-row>
                                                    
                                                    <br><br><v-row></v-row></v-col>
                                                    <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                 </v-row>
                                             </v-flex>       
                                        </v-container>
                            
                            """
    }
    vueOurTechnologiesContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                          <v-container id="our_Technologies" class="our_Technologies">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                 <v-row class="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12" class="label">[labelOurTechnologies]</v-col>
                                                </v-row>
                                                <v-row class ="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12"><h2 class="heading-2">[thisIsHow]</h2></v-col>
                                                </v-row><br><br>
                                                <v-row>
                                                    <v-col cols="1"></v-col>
                                                    <v-col cols="3" style="align-self: center;">    
                                                        <v-img src="assets/home/java.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col>
                                                        <v-col cols="2" style="align-self: center;"><v-img src="assets/home/javascript.png"></v-img>
                                                    </v-col> 
                                                    <v-col cols="1"></v-col> 
                                                        <v-col cols="3" style="align-self: center;"><v-img src="assets/home/grails.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col> 
                                                </v-row>
                                                 <v-row>
                                                    <v-col cols="1"></v-col>
                                                        <v-col cols="2" style="align-self: center;">
                                                        <v-img src="assets/home/vue.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col>
                                                        <v-col cols="3" style="align-self: center;"><v-img src="assets/home/kafka.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col> 
                                                        <v-col cols="3" style="align-self: center;"><v-img src="assets/home/oracle.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col>
                                                </v-row>
                                                 <v-row>
                                                    <v-col cols="1"></v-col> 
                                                    <v-col cols="3" style="align-self: center;">
                                                        <v-img src="assets/home/nodejs.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col> 
                                                        <v-col cols="2" style="align-self: center;"><v-img src="assets/home/kubernetes.png"></v-img>
                                                    </v-col> 
                                                    <v-col cols="1"></v-col> 
                                                        <v-col cols="3" style="align-self: center;"><v-img src="assets/home/mysql.png"></v-img>
                                                    </v-col>
                                                    <v-col cols="1"></v-col> 
                                                </v-row>
                                            </v-flex>
                                         </v-container>
                            """
    }
    vueQuizPlaceholderContainerDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                            <v-container id="quiz_placeholder" class="Quiz Placeholder" style ="background-color:#01579b;max-width:100%;">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                               <v-row></v-row><br><br><br>
                                               <v-row>
                                                 <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                                    <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4" style="padding-top: 6%; padding-left:8%;">
                                                        <v-row class="label">[quizLabel]</v-row>
                                                        <v-row><h2 class="heading-2">[tellUs]<br>[need]</h2></v-row>
                                                    </v-col>
                                                 <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                    <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4" style="padding:6%" class="pa-md-0">
                                                       <v-card style="color:#f1f1f1">
                                                            <vueElintegroSignUpQuizDataframe/>
                                                       </v-card>
                                                    </v-col>
                                                <v-col cols="12" xs="1" sm="1" md="2" xl="2" lg="2"></v-col>
                                                </v-row>
                                               <br><br><br><v-row></v-row>
                                            </v-flex>
                                         </v-container>
                            
                            """
    }
    vueFooterContainerDataframeLayout(ColumnLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """
                                    <v-container class="footer" style="background-color:white;max-width:100%">
                                              <v-flex xs12 sm12 md12 lg12 x12>
                                                <v-row></v-row><br>
                                                <v-row style="margin-top:-60px;" class="ma-md-0">
                                                    <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                                        <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" class="text-center"><p>[footerCopyright]</p></v-col>
                                                    <v-col cols="12" xs="0" sm="0" md="3" xl="3" lg="3"></v-col>
                                                        <v-col cols="12" xs="12" sm="12" md="2" xl="2" lg="2" style="margin-top:-40px; text-align:center" class='ma-md-0' ><a href="#" class="link">[footerPrivacy]</a></v-col>
                                                        <v-col cols="12" xs="1" sm="1" md="2" xl="2" lg="2"></v-col>
                                                </v-row>
                                               </v-flex>  
                                         </v-container>
                                
                            """
    }
    vueElintegroSignUpQuizDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>[DATAFRAME_SCRIPT]<v-card-actions class="justify-center">[BUTTON_SCRIPT]</v-card-actions></v-flex>"""
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']

    }
}