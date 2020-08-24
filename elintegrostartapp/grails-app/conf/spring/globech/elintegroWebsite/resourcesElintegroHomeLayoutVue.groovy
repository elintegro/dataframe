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
                                        </v-container>
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
                                        </v-container>
                                        <v-container class="our_process">
                                            <v-row class="text-center">
                                                <v-col cols="5"></v-col><v-row class="label" style = 'color: #29b6f6;font-size:14px;'>OUR PROCESS</v-row>
                                                
                                                 <v-row><v-col cols="2"></v-col>
                                                    <h2 class="heading-2" style ="font-size:45px;">You deserve factory-grade<br> efficiency</h2>
                                                </v-row>
                                                
                                                <v-row><v-col cols="2"></v-col>
                                                    <p>From the moment we receive your product specifications we take complete<br>
                                                    ownership over the engineering process.</p><br>
                                                    <p>Our team of specialists won’t rest until your product is fully tested, debugged and<br>
                                                    production ready.</p>
                                                </v-row>
                                                
                                            </v-row>    
                                                <v-row>
                                                        <v-img alt ="our_process"  src="assets/home/our_process.png"></v-img>
                                                </v-row>
                                            
                                           
                                        </v-container>
                                        
                                        
                                            
                                    </v-container>
                                    
                                    
                                
                                </v-card></v-flex>
                                
                                """
        flexGridValues = ['xs12', 'sm6', 'md4', 'lg4', 'xl4']
    }
}