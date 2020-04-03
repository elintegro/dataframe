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
                                 <v-container class="firstContainer">
                                 <v-row>
                                 <v-col cols="4"><v-card-actions class="justify-center"><h1 style="margin-top:50%;">How to Choose an App Developer</h1></v-card-actions></v-col>
                                 <v-col cols="1" style="min-width: 100px; max-width: 100%;"
                                 class="flex-grow-1 flex-shrink-0"><v-card outlined>
                                 <span style="padding-top:40%" class="video-2 w-video w-embed">
                                 <iframe  width="100%" height="500px" src="https://cdn.embedly.com/widgets/media.html?src=https%3A%2F%2Fwww.youtube.com%2Fembed%2FjFLFVlBbLNA%3Ffeature%3Doembed&display_name=YouTube&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DjFLFVlBbLNA&image=https%3A%2F%2Fi.ytimg.com%2Fvi%2FjFLFVlBbLNA%2Fhqdefault.jpg&key=96f1f04c5f4143bcb0f2e68c87d65feb&type=text%2Fhtml&schema=youtube" scrolling="no" title="YouTube embed" frameborder="0" allow="autoplay; fullscreen" allowfullscreen="true"></iframe>
                                 </span></v-card></v-col>
                                 </v-row><br><br>
                                 <v-row><v-col cols="4"></v-col><v-col cols="4"><v-img height ="90px" width="56px" src="assets/123.svg"></v-col></v-img>
                                 
                                 </v-row><br><br>
                                 </v-container>
                                 <v-spacer></v-spacer>
                                 
                                 <v-container class="solutionPage">
                                 <v-container class="secondContainer">
                                 <v-row>
                                 <v-col ><v-card-actions class ="justify-center"><h1><b>Solutions</b></h1></v-card-actions></v-col>
                                 </v-row>
                                 <v-row>
                                 <v-col cols="6"><v-img src="assets/FirstPagePhoto.svg"></v-img></v-col>
                                 <v-col cols="6"><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Full Stack Web App</b></h1></v-card-actions><br><p>A Full stack developer is an engineer who can design and develop an end-to-end application independently by handling all the work of coding, databases, servers and platforms. Full stack projects can be further classified as web stack, mobile stack or native application stack depending on the solution stack being used.</p></v-col>
                                 </v-row>
                                 </v-container>
                                 <v-spacer></v-spacer>
                                 <v-container class="thirdContainer">
                                 <v-row>
                                 <v-col cols="6"><h1><b>Development</b></h1 style="color: #2c3442;"><br><p>Web development is the work involved in developing a web site for the Internet (World Wide Web) or an intranet (a private network). Web development can range from developing a simple single static page of plain text to complex web-based internet applications (web apps), electronic businesses, and social network services.</p></v-col>
                                 <v-col cols="6"><v-img src="assets/DevelopmentPageFirstPhoto.svg"></v-img></v-col>
                                 </v-row>
                                 </v-container>
                                 <v-spacer></v-spacer>
                                 <v-container class="fourthContainer">
                                 <v-row>
                                 <v-col cols="6"><v-img src="assets/SaasPageFirstPhoto.svg"></v-img></v-col>
                                 <v-col cols="6"><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Mobile Development (PWA) and SaaS develoment</b></h1></v-card-actions><br><p>Mobile app development and design Services are being used highly every day and the company is generating profits of these mobile apps platforms to maintain their customers and improve their business.</p></v-col>
                                 </v-row>
                                 </v-container>
                                 </v-container><v-spacer></v-spacer>
                                 
                                 <v-container class="strategySession" style = "background-color: #f1f0ec;">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Schedule a Strategy Session</b></h1></v-card-actions></v-col></v-row>
                                 <v-row><v-col><v-card-actions class="justify-center"><v-btn  style="background-color: #2c3442; color:white; border-radius:5px; width:25%; height:45px; ">Click Here</v-btn></v-card-actions></v-col></v-row>
                                 <v-row><v-col><v-card-actions class="justify-center"><p>We are happy to meet with you and discuss your project or idea in details. This session would help you and us to understand better the problem and possible solutions. Such session are highly beneficial and provide you with entry point for your project.</p></v-card-actions></v-col></v-row>
                                 </v-container>
                                 
                                 <v-container class="methodology">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Methodology</b></h1></v-card-actions></v-col></v-row>
                                 <v-row>
                                 <v-col cols="3"><v-img src="assets/onBudget.svg"></v-img></v-col>
                                 <v-col cols="3"><v-img src="assets/fixedPrice.svg"></v-img></v-col>
                                 <v-col cols="3"><v-img src="assets/agile.svg"></v-img></v-col>
                                 <v-col cols="3"><v-img src="assets/onTIme.svg"></v-img></v-col>
                                 </v-row>
                                 <v-row>
                                 <v-col cols="3"><v-card-actions class="justify-center">On Budget</v-card-actions></v-col>
                                 <v-col cols="3"><v-card-actions class="justify-center">Fixed Price</v-card-actions></v-col>
                                 <v-col cols="3"><v-card-actions class="justify-center">Agile</v-card-actions></v-col>
                                 <v-col cols="3"><v-card-actions class="justify-center">On Time</v-card-actions></v-col>
                                 </v-row>
                                 </v-container>
                                 
                                 <v-container class="team" style="background-image:url('assets/groupBackground.svg');">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Team</b></h1></v-card-actions></v-col></v-row>
                                 <v-row>
                                 <v-col cols="3"><v-img height="150px" width="150px" src="assets/eugene.png"></v-img></v-col><v-col cols="3"><h2><b>Eugene Lipkovich</b></h2>Team leader</v-col>
                                 <v-col cols="3"><v-img height="150px" width="150px" src="assets/sampleImage.png"></v-img></v-col><v-col cols="3"><h2><b>Kumar Chapagain</b></h2>Developer</v-col>                                
                                 </v-row>
                                 <v-row>
                                 <v-col cols="3"><v-img height="150px" width="150px" src="assets/shai.png"></v-img></v-col><v-col cols="3"><h2><b>Shai Lipkovich</b></h2>Team leader</v-col>
                                 <v-col cols="3"><v-img height="150px" width="150px" src="assets/sampleImage.png"></v-img></v-col><v-col cols="3"><h2><b>Prakash Subedi</b></h2>Developer</v-col>                                
                                 </v-row>
                                 <v-row>
                                 <v-col cols="3"><v-img height="150px" width="150px" src="assets/sampleImage.png"></v-img></v-col><v-col cols="3"><h2><b>Nagendra Saragadam</b></h2>Developer</v-col>
                                 <v-col cols="3"><v-img height="150px" width="150px" src="assets/sampleImage.png"></v-img></v-col><v-col cols="3"><h2><b>Nagendra Saragadam</b></h2>Developer</v-col>         
                                 </v-row>
                                 </v-container>
                                 
                                 <v-container class="technology">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Technology</b></h1></v-card-actions></v-col></v-row>
                                 <v-row><v-col cols="4"><v-img src="assets/java.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/javascript.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/grails.svg"></v-img></v-col></v-row>
                                 <v-row><v-col cols="4"><v-img src="assets/vueJS.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/kafka.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/oracle.svg"></v-img></v-col></v-row>
                                 <v-row><v-col cols="4"><v-img src="assets/nodeJS.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/kubernetes.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/mySql.svg"></v-img></v-col></v-row>
                                 </v-container>
                                 
                                 <v-container class="clients">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Clients</b></h1></v-card-actions></v-col></v-row>
                                 <v-row><v-col cols="6"><v-img src="assets/globeChalet.svg"></v-img></v-col>      <v-col cols="6"><v-img src="assets/coachClone.svg"></v-img></v-col></v-row>
                                 </v-container>
                                 
                                 <v-container class="careers">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Careers</b></h1></v-card-actions></v-col></v-row>
                                 <v-row>
                                 <v-col cols="5"><p>Solving new challenges ?</p><br><p>Do you enjoy and familiar in programming in Java and/or Python and/or PHP and/or NodeJS and/or Javascript and/or Angular/React/Vue ?</p><br>
                                 <p>Are you a quick learner, interested in the next big challenge in your career ?</p><br><p>If so,then....</p><br><p>Apply for full-time employment/sub-contract or internship.</p></v-col>
                                 <v-col cols="2"></v-col>
                                 <v-col cols="5"><v-img src="assets/careersPagePhoto.svg"></v-img></v-col>
                                 </v-row>
                                 </v-container>
                                 
                                 <v-container class="aboutUs" style="background-image:url('assets/groupBackground.svg'); padding:70px;">                                 
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>About Us</b></h1></v-card-actions></v-col></v-row>
                                 <v-row><v-col><v-card-actions class="justify-center"><p>We build and design beautiful Mobile Applications, Web Dashboards, SaaS Websites and Wordpress 
                                 websites for companies and entrepreneurs who are looking to receive their software on time and on budget.
                                 For those who are interested in cost overruns or headaches dealing with rookie developers who will disappoint them, we normally recommend a freelance website such as upwork.</p></v-card-actions></v-col></v-row>
                                 </v-container>
                                 
                                 <v-container class="contactUs" style = "background-color: #f1f0ec;">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Contact Us</b></h1></v-card-actions></v-col></v-row>
                                 <v-row no-gutters><v-col cols="2"></v-col><v-col cols="6"><v-text-field single-line outlined placeholder="Your email address"></v-text-field></v-col><v-col cols="2"><v-btn  style="background-color: #2c3442; color:white; height:55px; width:100%; border-top-right-radius:15px; border-bottom-right-radius:15px; border-top-left-radius:0px; border-bottom-left-radius:0px; ">Send</v-btn></v-col><v-col cols="2"></v-col></v-row>
                                 <v-row><v-col><v-card-actions class="justify-center"><p>Fill you email or phone and we will get back to you.</p></v-card-actions></v-col></v-row>
                                 </v-container>
                                 
                                   
                                 <v-container class="footerPage" style = "background-color: #f1f0ec; margin-top:100px;">
                                 
                                 <v-container class="contactDetails">
                                 <v-row>
                                 <v-col cols="2"><v-img src="assets/elintegroLogo.png"></v-img></v-col>
                                 <v-col cols="1"></v-col>
                                 <v-col cols="6">
                                 <v-row><v-card-actions class="justify-center>"<h3 style="color: #2c3442;"><b>CONTACT INFO</b></h3></v-card-actions></v-row>
                                 <v-row><v-col cols="1"><v-img src="assets/location.svg"></v-img></v-col> <v-col cols="5">Locations:<br>Monetreal, Canada,Tel-Aviv, Israel,Kathmandu,Nepal</v-col></v-row>
                                 <v-row><v-col cols="1"><v-img src="assets/phone.svg"></v-img></v-col> <v-col cols="5">Phone:<br>USA:+1 (603) 329-3195<br>CANADA:+1 (438)792 1079<br>UK:+44 (1273) 93 0312<br>IL:+972 (54) 557 9687 </v-col></v-row>
                                 <v-row><v-col cols="1"><v-img src="assets/atTheRateIcon.svg"></v-img></v-col> <v-col cols="5">Email:<br>elintegroinc@gmail.com</v-col></v-row>
                                 <v-row><v-col cols="1"><v-img src="assets/linkIcon.svg"></v-img></v-col> <v-col cols="5">Website:<br>http://elintegro.com</v-col></v-row>
                                 </v-col>
                                 <v-col cols="3">
                                 <v-row><v-card-actions class="justify-center"><h3 style="color: #2c3442;"><b>SERVICES</b></h3></v-card-actions></v-row>
                                 <v-row><a href="#" style="color:inherit;">Mobile Applications</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Web Applications</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Custom Software</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Wordpress Sites</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Traffic Generation for Websites</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Sales Conversion for Websites</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Copy Writing for Websites</a></v-row><br>   
                                 <v-row><a href="#" style="color:inherit;">Emotional and Practical Support for Entrepreneurs</a></v-row>
                                 </v-col>
                                 </v-row><br><br>
                                 </v-container>
                                 
                                 <v-container class="menuButtons">
                                 <v-row>
                                 <v-col cols="7">
                                 <v-row><h3 style="color: #2c3442; margin-left:15px;"><b>NEWSLETTER</b></h3><br><br><br></v-row>
                                 <v-row no-gutters>
                                 <v-col cols="5"><v-text-field single-line outlined placeholder="Your email address"></v-text-field></v-col> <v-col cols="2"><v-btn  style="background-color: #2c3442; color:white; height:55px; width:70%; border-top-right-radius:15px; border-bottom-right-radius:15px; border-top-left-radius:0px; border-bottom-left-radius:0px;">Send</v-btn></v-col>
                                 </v-row>                      
                                 </v-col>
                                 <v-col cols="5">
                                 <v-row><v-col cols="1"><a href="http://localhost:8099/#/vueElintegroBannerDataframe/0" style="color:inherit">Home</a></v-col><v-col cols="2"></v-col> <v-col cols="1"><a href="http://localhost:8099/#/vueTechnologiesDataframe/0" style="color:inherit">Technologies</a></v-col><v-col cols="2"></v-col> <v-col cols="1"><a href="http://localhost:8099/#/vueCareersDataframe/0" style="color:inherit">Careers</a></v-col></v-row>
                                 <v-row><v-col cols="1"><a href="http://localhost:8099/#/vueClientProjectDataframe/0" style="color:inherit">Clients</a></v-col> <v-col cols="2"></v-col> <v-col cols="1"><a href="http://localhost:8099/#/vueGettingStartedDataframe/0" style="color:inherit">Getting Started</a></v-col> <v-col cols="2"></v-col> <v-col cols="1"><a href="http://localhost:8099/#/vueContactUsPageDataframe/0" style="color:inherit">Contact Us</a></v-col></v-row>
                                 </v-col>                                 
                                 </v-row><br><br><br>
                                 <v-row><v-img src="assets/straightLine.svg"></v-img></v-row><br><br><br>
                                 <v-row><span>&copy COPYRIGHT - 2019 ELINTEGRO INC. ALL RIGHTS RESERVED</span></v-row><br><br>
                                 <v-row>
                                 <p>We build and design beautiful Mobile Applications, Web Dashboards, SaaS Websites and Wordpress 
                                 websites for companies and entrepreneurs who are looking to receive their software on time and on budget.
                                 For those who are interested in cost overruns or headaches dealing with rookie developers who will disappoint them, 
                                 we normally recommend a freelance website such as upwork.
                                 </p>
                                 </v-row>
                                 </v-container>
                                 
                                 
                                 </v-container>
                                 
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
