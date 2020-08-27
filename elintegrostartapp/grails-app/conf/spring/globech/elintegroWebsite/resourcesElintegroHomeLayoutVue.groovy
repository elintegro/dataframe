package spring.globech.elintegroWebsite

import com.elintegro.erf.layout.ColumnLayoutVue

beans{
    vueElintegroHomeDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12>
                                    <v-container>
                                        <v-container class="firstContainer"
                                            <v-row><v-col cols="1"></v-col>
                                                <v-col cols="10"><div class = "hidden-sm-and-down"><h1 style ='font-size: 70px;font-family:sans-serif;'>Hey! <br>Looks Like You Haven't <br>
                                                <span class="text-span" style ='color: #29b6f6;'>Built </span>Any Apps With Us<br> 
                                                Yet...</h1></div></v-col><v-col cols="1"></v-col>
                                                <v-flex><div class = "hidden-md-and-up"><div class="text-center"><h1 style ='font-size: 70px;'>Hey! <br>Looks Like You Haven't <br>
                                                <span class="text-span" style ='color: #29b6f6;'>Built </span>Any Apps With Us 
                                                Yet...</h1></div></div></v-flex>
                                            </v-row>
                                            <v-row><v-flex xs12 sm12 md12 lg12 x12><div class="text-center">
                                                    <span style ='color: black; font-size: 37px;font-family:sans-serif;'>But, we can develop your app for you - so you won't have to!</span></div></v-flex>
                                            </v-row><br><br> 
                                            <v-row><v-flex xs12 sm12 md12 lg12 x12 class = "text-center">  
                                                    <v-btn style="background-color: #29b6f6; color:white;font-family:sans-serif; font-size:inherit; padding-top:28px; padding-bottom:28px; padding-right:35px; padding-left:35px;">Build your first app</v-btn></v-flex>
                                            </v-row>
                                        </v-container><br><br>
                                        <v-container class = "our_work">
                                            <v-row class="pa-md-0" style="padding:10%"><v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                                <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4" >
                                                    <v-row class="label" style = 'color: #29b6f6;font-size:14px;'>OUR WORK</v-row>
                                                    <v-row>
                                                        <h2 class="heading-2" style ="font-size:45px;">We deliver</h2>
                                                    </v-row>
                                                    <v-row>
                                                            <p>Everything from stand alone algorithms, up to fully fledged applications.<br> 
                                                            We’re quick to adapt to your needs and have never been halted by complexity.
                                                            </p>
                                                    </v-row></v-col>
                                                <v-col cols="12" xs="1" sm="1" md="2" xl="2" lg="2"></v-col> 
                                                <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4">
                                                     <v-img alt ="Our_work"  src="assets/home/our_work.svg"></v-img>
                                                </v-col>
                                             <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>      
                                            </v-row>
                                        </v-container><br><br>
                                        <v-container class="our_process">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                <v-row class="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12" class="label" style = 'color: #29b6f6;font-size:14px;'>OUR PROCESS</v-col>
                                                </v-row>
                                                <v-row class ="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12"><h2 class="heading-2" style ="font-size:45px;">You deserve factory-grade<br> efficiency</h2></v-col>
                                                </v-row>
                                                <v-row class="text-center" >
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12" style="font-size: large;font-family: sans-serif;"><p>From the moment we receive your product specifications we take complete
                                                   <br>ownership over the engineering process.<br>
                                                   Our team of specialists won’t rest until your product is fully tested, debugged and<br>
                                                   production ready.</p></v-col>
                                                </v-row>
                                                <v-row class="text-center">
                                                   <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col><v-col cols="12" xs="12" sm="12" md="10" xl="10" lg="10"><v-img alt ="our_process" src="assets/home/our_process.png" style="background-color:#E6E6E6"></v-img></v-col><v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                </v-row>
                                            </v-flex>
                                        </v-container><br>
                                        <v-container class="collaboration">
                                            <v-flex xs12 sm12 md12 lg12 x12 class="hidden-sm-and-down">
                                                <v-row>
                                                    <v-col cols="1"></v-col><v-col cols="5">
                                                       <v-img alt ="Collaboration"  src="assets/home/collaboration.svg"></v-img>
                                                    </v-col>
                                                    <v-col cols="1" ></v-col>
                                                    <v-col cols="4" style="padding-top:5%;">
                                                        <v-row class="label" style = 'color: #29b6f6;font-size:14px;'>COLLABORATION</v-row>
                                                        <v-row>
                                                            <h2 class="heading-2" style ="font-size:45px;">Agility as a Service</h2>
                                                        </v-row>
                                                        <v-row style="font-size:104%; font-family:sans-serif;">
                                                                <p>We believe that better solutions evolve through collaboration and continual improvement.
                                                                </p><p>We work in pre-planned sprints and will involve you in an efficient iterative process, designed to gradually achieve perfection.
                                                                </p>
                                                        </v-row>
                                                    </v-col> 
                                                </v-row>       
                                             <v-col cols="1"></v-col>    
                                            </v-flex>
                                            <v-flex xs12 sm12 md12 lg12 x12 class="hidden-md-and-up">
                                                <v-row>
                                                    <v-col cols="12" style="padding:8%;">
                                                        <v-row class="label" style = 'color: #29b6f6;font-size:14px;'>COLLABORATION</v-row>
                                                        <v-row>
                                                            <h2 class="heading-2" style ="font-size:45px;">Agility as a Service</h2>
                                                        </v-row>
                                                        <v-row style="font-size:118%; font-family:sans-serif;">
                                                                <p>We believe that better solutions evolve through collaboration and continual improvement.
                                                                </p><p>We work in pre-planned sprints and will involve you in an efficient iterative process, designed to gradually achieve perfection.
                                                                </p>
                                                        </v-row>
                                                    </v-col> 
                                                    <v-col cols="12">
                                                       <v-img alt ="Collaboration"  src="assets/home/collaboration.svg"></v-img>
                                                    </v-col>
                                                </v-row>       
                                            </v-flex>
                                        </v-container><br>
                                        <v-container class = "our_framework">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                <v-row>
                                                    <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col> 
                                                    <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" style="padding:8%" class="pa-md-0">
                                                        <v-row class="label" style = 'color: #29b6f6;font-size:14px;'>OUR FRAMEWORK</v-row>
                                                        <v-row><h2 class="heading-2" style ="font-size:45px;">Quality built-in</h2></v-row>
                                                        <v-row style="font-size:118%; font-family:inherit;" class="hidden-md-and-up">
                                                                <p>Dataframe, our in-house development framework, allows us to maximize code efficiency, while producing virtually buglesss apps. 
                                                                </p><p>Dataframe is an open-source, Agile-oriented development framework, used and co-developed by an international community. Dataframe will soon be accessible via an open API.
                                                                </p><br>
                                                                <v-btn style="background-color: #29b6f6; color:white;">Dataframe On Github</v-btn>
                                                        </v-row>
                                                        <v-row style="font-family:inherit;" class="hidden-sm-and-down">
                                                                <p>Dataframe, our in-house development framework, allows us to maximize code efficiency, while producing virtually buglesss apps. 
                                                                </p><p>Dataframe is an open-source, Agile-oriented development framework, used and co-developed by an international community. Dataframe will soon be accessible via an open API.
                                                                </p><br>
                                                                <v-btn style="background-color: #29b6f6; color:white;">Dataframe On Github</v-btn>
                                                        </v-row>
                                                    </v-col>
                                                    <v-col cols="12" xs="0" sm="0" md="2" xl="2" lg="2"></v-col> 
                                                    <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" class="text-center">
                                                                <v-img alt ="our_frameworks"  src="assets/home/our_framework.svg"></v-img>
                                                    </v-col>
                                                    <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>        
                                                </v-row>
                                            </v-flex>
                                        </v-container><br><br><br>
                                        
                                        <v-container class="Quotes">
                                             <v-flex xs12 sm12 md12 lg12 x12>
                                                 <v-row>
                                                    <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                                    <v-col cols ="12" xs="10" sm="10" md="10" xl="10" lg="10" style="background-color:#E1F5FE; border-radius:20px;"><v-row></v-row><br><br><br>
                                                        <v-row> <v-col cols="12" xs="0" sm="0" md="2" xl="2" lg="2"></v-col>
                                                          <v-col cols="12" xs="10" sm="10" md="7" xl="7" lg="7" class="text-center" style="padding-left:5%;"><h1 style="font-family: sans-serif;font-size:36px;"><p id="quotes">“From concept to development, it  was a pleasure to work with Elintegro.They delivered my product on time and on budget. I’d definitely hire them again”</p></h1></v-col>
                                                          <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                        </v-row>
                                                        <v-row><v-col cols="12" xs="10" sm="10" md="10" xl="10" lg="10" style = "font-family: sans-serif;font-size:14px;" class="text-center"><span id = "nameOfPerson"style="padding-left:5%;">Drasko Raicevic,</span></v-col></v-row>
                                                        <v-row><v-col cols="12" xs="10" sm="10" md="10" xl="10" lg="10" style = "font-family: sans-serif;font-size:14px;"class="text-center"><span id = "jobTitle" style="padding-left:5%;">Quickbody Fitness</span></v-col></v-row><br>
                                                        
                                                        <v-row><v-col cols="12" xs="1" sm="1" md="3" xl="3" lg="3"></v-col><v-col cols="12" xs="3" sm="3" md="2" xl="2" lg="2"><a v-on:click="hello('coachClone');"><v-img id = "coachClone"src="assets/home/coachClone.png" style="background-color:#E1F5FE;opacity: 0.25;"></v-img></a></v-col><v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col><v-col cols="12" xs="3" sm="3" md="2" xl="2" lg="2"><a v-on:click="hello('weBus');"><v-img id= "weBus" src="assets/home/weBus.jpg" style="background-color:#E1F5FE;opacity: 0.25;"></v-img></a></v-col><v-col cols="12" xs="2" sm="2" md="2" xl="2" lg="2"></v-col></v-row>
                                                    
                                                    <br><br><v-row></v-row></v-col>
                                                    <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                 </v-row>
                                             </v-flex>       
                                        </v-container><br>
                                        <v-container class="our_Technologies">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                                 <v-row class="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12" class="label" style = 'color: #29b6f6;font-size:14px;'>OUR TECHNOLOGIES</v-col>
                                                </v-row>
                                                <v-row class ="text-center">
                                                   <v-col cols="12" xs="12" sm="12" md="12" xl="12" lg="12"><h2 class="heading-2" style ="font-size:45px;">This is how we do it</h2></v-col>
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
                                        </v-container><br>
                                        <v-container class="Quiz Placeholder" style ="background-color:#01579b;max-width:100%;">
                                            <v-flex xs12 sm12 md12 lg12 x12>
                                               <v-row></v-row><br><br><br>
                                               <v-row>
                                                 <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                                    <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4" style="padding-top: 6%; padding-left:8%;">
                                                        <v-row class="label" style = 'color:#ffffff;font-size:14px;'>CONTACT</v-row>
                                                        <v-row><h2 class="heading-2" style ="font-size:45px;color:#ffffff;">Tell us what you<br> need</h2></v-row>
                                                    </v-col>
                                                 <v-col cols="12" xs="0" sm="0" md="1" xl="1" lg="1"></v-col>
                                                    <v-col cols="12" xs="10" sm="10" md="4" xl="4" lg="4" style="padding:6%" class="pa-md-0">
                                                       <v-card style="height: 300px;color:#f1f1f1">
                                                            <v-card-actions class="justify-center" style="border:0px;"><p style="color:black;padding:25%">Contact quiz placeholder here</p></v-card-actions>
                                                       </v-card>
                                                    </v-col>
                                                <v-col cols="12" xs="1" sm="1" md="2" xl="2" lg="2"></v-col>
                                                </v-row>
                                               <br><br><br><v-row></v-row>
                                            </v-flex>
                                        </v-container><br>
                                        <v-container class="footer" style="background-color:white;max-width:100%">
                                          <v-flex xs12 sm12 md12 lg12 x12>
                                            <v-row></v-row><br>
                                            <v-row style="margin-top:-60px;" class="ma-md-0">
                                                <v-col cols="12" xs="1" sm="1" md="1" xl="1" lg="1"></v-col>
                                                    <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4" class="text-center"><p>Copyright © 2020 Elintegro Inc. All rights reserved.</p></v-col>
                                                <v-col cols="12" xs="0" sm="0" md="3" xl="3" lg="3"></v-col>
                                                    <v-col cols="12" xs="12" sm="12" md="2" xl="2" lg="2" style="margin-top:-40px; text-align:center" class='ma-md-0' ><a href="#" class="link" style="color:black;">Privacy Policy</a></v-col>
                                                    <v-col cols="12" xs="1" sm="1" md="2" xl="2" lg="2"></v-col>
                                            </v-row>
                                          </v-flex>  
                                        </v-container>
                                    </v-container>
                                    [DATAFRAME_SCRIPT][BUTTON_SCRIPT]
                                </v-flex>
                                
                                """
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
}