package spring.globech.elintegroWebsite

import com.elintegro.erf.layout.ColumnLayoutVue

beans{
    vueElintegroHomeDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                    <v-container>
                                        <v-container class="firstContainer"
                                            <v-row><v-col cols="1"></v-col>
                                                <h1 style ='font-size: 81px;'>Hey! <br>Looks Like You Haven't <br>
                                                <span class="text-span" style ='color: #29b6f6;'>Built </span>Any Apps With Us<br> 
                                                Yet...</h1></v-row>
                                                <v-row><v-col cols="2"></v-col>
                                                    <h2 style ='color: black; font-size: 27px;'>But, we can develop your app for you - so you won't have to!</h2>
                                                </v-row> 
                                                <v-row><v-col cols="5"></v-col><v-spacer></v-spacer></v-row>   
                                                <v-row><v-col cols="5"></v-col><v-flex><v-btn style="background-color: #29b6f6; color:white;">
                                                    Build your first app
                                                </v-btn></v-flex></v-row>
                                        </v-container><br>
                                        <v-container class = "our_work">
                                            <v-row><v-col cols="1"></v-col>
                                                <v-col cols="4">
                                                    <v-row class="label" style = 'color: #29b6f6;font-size:14px;'>OUR WORK</v-row>
                                                    <v-row>
                                                        <h2 class="heading-2" style ="font-size:45px;">We deliver</h2>
                                                    </v-row>
                                                    <v-row>
                                                            <p>Everything from stand alone algorithms, up to fully fledged applications. 
                                                            </p><p>We’re quick to adapt to your needs and have never been halted by complexity.
                                                            </p>
                                                    </v-row></v-col>
                                                <v-col cols="2"></v-col> 
                                                <v-col cols="4">
                                                            <v-img alt ="Our_work"  src="assets/home/our_work.svg"></v-img>
                                                </v-col>
                                             <v-col cols="1"></v-col>       
                                            </v-row>
                                        </v-container><br>
                                        <v-container class="our_process">
                                            <v-flex>
                                            <v-row><v-col cols="5"></v-col><v-col cols="2" class="label" style = 'color: #29b6f6;font-size:14px;'>OUR PROCESS</v-col><v-col cols="5"></v-col></v-row>
                                            
                                            <v-row>
                                            <v-col cols="3"></v-col>
                                            <v-col cols="6"><v-row class ="text-center"><h2 class="heading-2" style ="font-size:45px;">You deserve factory-grade<br> efficiency</h2></v-row></v-col>
                                            <v-col cols="3"></v-col>
                                            </v-row>
                                            
                                            <v-row>
                                            <v-col cols="3"></v-col>
                                            <v-col cols="6" ><v-row class="text-center" style="font-size: large;font-family: sans-serif;"><p>From the moment we receive your product specifications we take complete
                                            ownership over the engineering process.</p>
                                            <p>Our team of specialists won’t rest until your product is fully tested, debugged and
                                            production ready.</p></v-row>
                                            </v-col>
                                            <v-col cols="3"></v-col>
                                            </v-row>
                                            
                                            
                                            <v-row>
                                            <v-col cols="1"></v-col><v-col cols="10" ><v-img alt ="our_process" src="assets/home/our_process.png" style="background-color:#E6E6E6"></v-img></v-col><v-col cols="1"></v-col>
                                            </v-row>
                                            
                                            </v-flex>
                                        </v-container><br>
                                        <v-container class="collaboration">
                                            <v-row><v-col cols="1"></v-col>
                                                <v-col cols="4">
                                                   <v-img alt ="Collaboration"  src="assets/home/collaboration.svg"></v-img>
                                                </v-col>
                                                <v-col cols="2"></v-col> 
                                                <v-col cols="4">
                                                    <v-row class="label" style = 'color: #29b6f6;font-size:14px;'>COLLABORATION</v-row>
                                                    <v-row>
                                                        <h2 class="heading-2" style ="font-size:45px;">Agility as a Service</h2>
                                                    </v-row>
                                                    <v-row>
                                                            <p>We believe that better solutions evolve through collaboration and continual improvement.
                                                            </p><p>We work in pre-planned sprints and will involve you in an efficient iterative process, designed to gradually achieve perfection.
                                                            </p>
                                                    </v-row></v-col>
                                             <v-col cols="1"></v-col>       
                                            </v-row>
                                        </v-container><br>
                                        <v-container class = "our_framework">
                                            <v-row><v-col cols="1"></v-col>
                                                <v-col cols="4">
                                                    <v-row class="label" style = 'color: #29b6f6;font-size:14px;'>OUR FRAMEWORK</v-row>
                                                    <v-row><h2 class="heading-2" style ="font-size:45px;">Quality built-in</h2></v-row>
                                                    <v-row>
                                                            <p>Dataframe, our in-house development framework, allows us to maximize code efficiency, while producing virtually buglesss apps. 
                                                            </p><p>Dataframe is an open-source, Agile-oriented development framework, used and co-developed by an international community. Dataframe will soon be accessible via an open API.
                                                            </p><br>
                                                            <v-btn style="background-color: #29b6f6; color:white;">Dataframe On Github</v-btn>
                                                    </v-row></v-col>
                                                <v-col cols="2"></v-col> 
                                                <v-col cols="4">
                                                            <v-img alt ="our_frameworks"  src="assets/home/our_framework.svg"></v-img>
                                                </v-col>
                                            <v-col cols="1"></v-col>       
                                            </v-row>
                                        </v-container><br>
                                        
                                        <v-container class="Quotes">
                                            <v-row>
                                            <v-col cols="1"></v-col>
                                            <v-col cols ="10" style="background-color:#E1F5FE; border-radius:20px;"><v-row></v-row><br><br><br>
                                                <v-row ><v-col cols="2"></v-col><v-col cols="7"><v-card-actions class="justify-center"><h1 style="font-family: sans-serif;font-size:36px;"><p>“From concept to development, it was a pleasure to work with Elintegro.They delivered my product on time and on budget. I’d definitely hire them again”</p></h1></v-card-actions></v-col><v-col cols="1"></v-col></v-row>
                                                
                                                <v-row><v-col cols="5"></v-col><v-col cols="2" style = "font-family: sans-serif;font-size:14px;">Drasko Raicevic,</v-col><v-col cols="3"></v-col></v-row>
                                                <v-row><v-col cols="5"></v-col><v-col cols="2" style = "font-family: sans-serif;font-size:14px;">Quickbody Fitness</v-col><v-col cols="3"></v-col></v-row><br>
                                                <v-row><v-col cols="3"></v-col><v-col cols="2"><a><v-img src="assets/home/coachClone.png" style="background-color:#E1F5FE;"></v-img></a></v-col><v-col cols="1"></v-col><v-col cols="2"><a><v-img src="assets/home/weBus.jpg" style="background-color:#E1F5FE;"></v-img></a></v-col><v-col cols="2"></v-col></v-row>
                                            
                                            </v-col>
                                            <v-col cols="1"></v-col>
                                            </v-row>
                                        </v-container><br>
                                        <v-container class="our_Technologies">
                                            <v-flex>
                                                <v-row><v-col cols="5"></v-col><v-col cols="2" class="label" style = 'color: #29b6f6;font-size:14px;'>OUR TECHNOLOGIES</v-col><v-col cols="5"></v-col></v-row>
                                                <v-row>
                                                <v-col cols="4"></v-col>
                                                <v-col cols="6"><v-row class ="text-center"><h2 class="heading-2" style ="font-size:45px;">This is how we do it</h2></v-row></v-col>
                                                <v-col cols="3"></v-col>
                                                </v-row>
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
                                            <v-flex >
                                               <v-row></v-row><br><br><br>
                                               <v-row>
                                                 <v-col cols="1"></v-col>
                                                    <v-col cols="4" style="align-self: center;">
                                                        <v-row class="label" style = 'color:#ffffff;font-size:14px;'>CONTACT</v-row>
                                                        <v-row><h2 class="heading-2" style ="font-size:45px;color:#ffffff;">Tell us what you<br> need</h2></v-row>
                                                    </v-col>
                                                 <v-col cols="1"></v-col>
                                                    <v-col cols="5">
                                                       <V-card style="height: 300px;color:#f1f1f1">
                                                            <v-card-actions class="justify-center"><p style="color:black;padding:25%">Contact quiz placeholder here</p></v-card-actions>
                                                       </v-card>
                                                    </v-col>
                                                <v-col cols="1"></v-col>
                                                </v-row>
                                               <br><br><br><v-row></v-row>
                                            </v-flex>
                                        </v-container><br>
                                        <v-container class="footer" style="background-color:white;max-width:100%">
                                            <v-row></v-row><br>
                                            <v-row>
                                                <v-col cols="1"></v-col>
                                                    <v-col cols="4"><p>Copyright © 2020 Elintegro Inc. All rights reserved.</p></v-col>
                                                <v-col cols="5"></v-col>
                                                    <v-col cols="2"><a href="#" class="link" style="color:black;">Privacy Policy</a></v-col>
                                                
                                            </v-row>
                                            
                                        </v-container>
                                    </v-container>
                                    
                                </v-card></v-flex>
                                
                                """
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
}