package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.util.Holders

beans {
    def contextPath = Holders.grailsApplication.config.rootPath

    vueInitDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueInitDataframe']
        saveButton = false
        wrapInForm = false

        initOnPageLoad = false
//        componentsToRegister = ["vueLoginDataframe"]
        //Vue parameters
        isGlobal = true
        vueStore = ["state": "loggedIn: false,\n"]

        putFillInitDataMethod = false
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueElintegroNavigationButtonDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationButtonDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [home           : [name: "home", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueElintegroBannerDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            clientsProjects: [name: "clientsProjects", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueClientProjectDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            technologies   : [name: "techonologies", type: "link",route:true, routeIdScript: "0", refDataframe: ref("vueTechnologiesDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            gettingStarted : [name: "gettingStarted", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueGettingStartedDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            carrers        : [name: "carrers", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueCareersDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            contactUs      : [name: "contactUs", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueContactUsPageDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            login          : [name: "login", type: "link",showAsDialog: true,
                                              refDataframe: ref("vueElintegroLoginDataframe"), tooltip: [message: 'Login'], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            register       : [name: "register", type: "link", showAsDialog: true, attr:"text",
                                              refDataframe: ref("vueElintegroRegisterDataframe"), tooltip: [message: 'Register'], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        wrapButtons = false


        currentFrameLayout = ref("elintegroNavigationButtonLayout")
    }
    vueSubContainerDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueSubContainerDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        currentFrameLayout = ref("subContainerLayout")

    }

    vueElintegroLogoDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroLogoDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        addFieldDef = [
                "logo": [
                        "widget"      : "PictureDisplayWidgetVue",
                        "url"         : "${contextPath}/assets/elintegro_logo.png",
                        flexGridValues: ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "attr"        : " contain ",
                        "height"      : "auto",
                        "width"       : "200",
                        //"min-width"   : "40"

                ]

        ]
        currentFrameLayout = ref("appNameDataframeLayout")

    }
    vueElintegroBannerDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroBannerDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        addFieldDef = [
                "banner": [
                        "widget"      : "PictureDisplayWidgetVue",
                        "url"         : "${contextPath}/assets/elintegro_banner.jpg",
                        flexGridValues: ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],


                ]
//                "person.firstName":[
//                        widget: "InputWidgetVue",
//                        "required": "required"
//                        ,"validate":["rule":["v => !!v || 'FirstName is required'", "v => (v && v.length <= 10) || 'FirstName must be less than 10'"]]
//                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']],
//
// yesko dataframe ma arko kei use garera background image ma banner dina paryo vane layout ma gayera background image dine
//                or class banayera class ko properties css ma lekhne
        ]
        currentFrameLayout = ref("appNameDataframeLayout")

    }
    vueClientProjectDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueClientProjectDataframe']
        dataframeLabelCode = "Clients & Projects Details"
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        route = true
        addFieldDef =[
                "clientProject": [
                        widget            : "GridWidgetVue"
                        , name            : "clientProject"

                        ,hql             : """select clientProject.clientName as Clientname ,clientProject.projectName as Projectname,  clientProject.logo as Logo, 
                                                clientProject.description as Description,clientProject.linkToWebsite as LinkToWebsite from ClientProject clientProject"""


//                      , hql             : """select (clientProject.clientName || ' ' ||clientProject.projectName) as Clientproject,  clientProject.logo as Logo,
//                                                clientProject.description  as Description,clientProject.linkToWebsite as LinkToWebsite from ClientProject clientProject"""
//                      , hql             : """select concat(clientProject.clientName, ' ',clientProject.projectName) as Clientproject,  clientProject.logo as Logo,
//                                                clientProject.description as Description,clientProject.linkToWebsite as LinkToWebsite from ClientProject clientProject"""
                        , gridWidth       : 820
                        , search          : true
                        ,internationalize: true
                        ,manageFields     :[linkToWebsite: [type: 'link', script: '']]
                        ,avatarAlias      :'Logo'
                        ,avatarWidth      :'400'
                        ,avatarHeight     :'auto'
                        ,url:'/'
                        , "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']

                ]
        ]
        currentFrameLayout = ref("clientProjectPageDataframeLayout")

    }
    vueTechnologiesDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueTechnologiesDataframe']
        dataframeLabelCode = "Technologies"
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        addFieldDef = [
                "java"      : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/java.PNG", "aspectRatio":"1.5"],
                "javascript": ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/javascript.PNG", "aspectRatio":"1.5"],
                "grails"    : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/grailsphoto.PNG", "aspectRatio":"1.5"],
                "vuejs"     : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/vuejs.PNG", "aspectRatio":"1.0"],
                "kafka"     : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/kafka.PNG", "aspectRatio":"1.0"],
                "oracle"    : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/oracle.PNG", "aspectRatio":"1.0"],
                "nodejs"    : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/nodejs.PNG", "aspectRatio":"1.0"],
                "kubernetes": ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/kubernetes.PNG", "aspectRatio":"1.0"],
                "mysql"     : ["widget": "PictureDisplayWidgetVue", "url": "${contextPath}/assets/mysql.PNG", "aspectRatio":"1.0"],

        ]
        currentFrameLayout = ref("defaultRouteDataframeLayout")


    }
    vueGettingStartedDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueGettingStartedDataframe']
        dataframeLabelCode = "Getting Started"
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        currentFrameLayout = ref("vueGettingStartedPageDataframeLayout")
    }
    vueCareersDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueCareersDataframe']
        dataframeLabelCode = "Careers"
        // isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        //componentsToRegister = ["vueCareersPageButtonDataframe"]
        currentFrameLayout = ref("vueCareersDataframeLayout")
    }
    vueCareersPageButtonDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueCareersPageButtonDataframe']
        dataframeLabelCode = "Careers"
        saveButton = false
        initOnPageLoad = false
        isGlobal = true
        dataframeButtons = [registerForNewEmployee: [name: "register", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueNewEmployeeApplicantDataframe"),
                                                     "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        currentFrameLayout = ref("emptyDataframeLayout")

    }
    vueNewEmployeeApplicantDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeApplicantDataframe']
        dataframeLabelCode = "New Employee Applicant Registration"
        initOnPageLoad = false
        saveButton = false
        isGlobal = true
        route = true
        currentFrameLayout = ref("vueNewEmployeeApplicantDataframeLayout")
    }
    vueNewEmployeeBasicInformationDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeBasicInformationDataframe']
//      hql = "select person.firstName, person.lastName, person.contactEmail, person.phone from Person person where person.id=:id"
        hql = "select person.firstName, person.lastName, person.contactEmail,person.phone,application.linkedin from Application application inner join application.applicant person where application.id=:id"
        initOnPageLoad = false
        saveButton = false
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md1', 'lg1', 'xl1']
        isGlobal = true
        addFieldDef = [

                "person.firstName":["name":"firstName","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => !!v", message: 'FirstName.required.message'],[condition:"v => (v && v.length <= 30)",message:"FirstName.must.be.less.than.30"]],"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                "person.lastName":["name":"lastName","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => !!v", message: 'LastName.required.message'],[condition:"v => (v && v.length <= 30)",message:"LastName.must.be.less.than.30"]],"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                "person.contactEmail":["name":"email","type":"link","widget":"EmailWidgetVue","validationRules":[[condition:"v => !!v", message: 'Email.required.message']],"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                "person.phone":["name":"phone","type":"link","widget":"PhoneNumberWidgetVue","validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]],"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                "application.linkedin":["name":"linkedin","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => !!v", message: 'Linkedin.required.message']],"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                "person.availablePosition"  :[
                        "widget"             :"ComboboxVue"
                        ,"name"              :"person.availablePosition"
                        ,internationalize    :true
                        ,initBeforePageLoad  :true
                        ,multiple            :true
                        ,"hql"               : "select position.id as id, position.name as name from Position as position"

                        ,"flexGridValues"    : ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                        , search:true

                ],


        ]
        dataframeButtons = [
                next:[name:"next", type: "button",script:'this.newEmployeeBasicInformation()',
                      flexGridValues: ['xs12', 'sm12', 'md1', 'lg1', 'xl1'], url:""]]


        currentFrameLayout = ref("emptyDataframeLayout")

    }
    vueNewEmployeeUploadResumeDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeUploadResumeDataframe']
        initOnPageLoad = false
        hql = "select application.id, application.avatar, application.resume from Application application where application.id=:id"
        saveButton = true
        saveButtonAttr = " align='right' "
        flexGridValuesForSaveButton =['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        tab = true
        isGlobal = true

        doBeforeSave = """allParams['vueNewEmployeeUploadResumeDataframe_application_id'] = this.state.vueNewEmployeeUploadResumeDataframe_resume_id;
                          allParams['key_vueNewEmployeeUploadResumeDataframe_application_id_id'] = this.state.vueNewEmployeeUploadResumeDataframe_resume_id;
                           """
        doAfterSave = """
                         excon.saveToStore("vueNewEmployeeUploadResumeDataframe","key", response.nodeId[0]);
                         excon.saveToStore('vueNewEmployeeApplicantDataframe','vueNewEmployeeApplicantDataframe_tab_model','vueNewEmployeeSelfAssesmentDataframe-tab-id');
                      """
        addFieldDef = [
                "application.avatar":["name":"avatar"
                                      ,"widget":"PictureUploadWidgetVue"
                                      ,ajaxFileSaveUrl: "${contextPath}/fileUpload/ajaxFileSave"
                                      ,"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                                      ,multiple:false
                                      ,editButton: true
                                      ,deleteButton:true  ],

                "application.resume":["name":"resume"
                                      ,"widget":"FilesUploadWidgetVue"
                                      ,valueMember: "resume"
                                      , ajaxFileSaveUrl: "${contextPath}/fileUpload/ajaxFileSave"
                                      , insertAfter: "application.resume"
                                      ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                                      ,multiple:false
                                      ,"accept":"image/*,.pdf,.docx,.doc"

                                     ]
        ]

        dataframeButtons = [ previous: [name:"previous", type: "button",script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeBasicInformationDataframe-tab-id");\n""",
                                        flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'],url: ""]]
//                             next:[name:"next", type: "button",script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeSelfAssesmentDataframe-tab-id");\n""",
//                                   flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'],url: ""]]

        currentFrameLayout = ref("vueNewEmployeeUploadResumeDataframeLayout")
    }
    vueNewEmployeeSelfAssesmentDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeSelfAssesmentDataframe']
        initOnPageLoad = false
        tab = true
        saveButton = true
        flexGridValuesForSaveButton =['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        isGlobal = true
        doAfterSave = """
                         excon.saveToStore("vueNewEmployeeAddtionalQuestionsDataframe","key", response.nodeId[0]);
                         excon.saveToStore('vueNewEmployeeApplicantDataframe','vueNewEmployeeApplicantDataframe_tab_model','vueNewEmployeeAddtionalQuestionsDataframe-tab-id');"""

        dataframeButtons = [previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeUploadResumeDataframe-tab-id");
                                                                                \n""",flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'], url: ""]]

        currentFrameLayout = ref("vueNewEmployeeSelfAssesmentDataframeLayout")
    }

    vueNewEmployeeAddtionalQuestionsDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeAddtionalQuestionsDataframe']
        initOnPageLoad = false
        hql = "select person.description from Person person where person.id=:id"
        saveButton = true
        tab = true
        flexGridValuesForSaveButton =['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        isGlobal = true

        addFieldDef = [
                "person.description":["name":"description","type":"link","widget":"TextAreaWidgetVue","flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']]

        ]
        dataframeButtons = [
                previous: [name:"previous", type: "button",script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeSelfAssesmentDataframe-tab-id");\n""",
                           flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'],url: ""]]

        currentFrameLayout = ref("vueNewEmployeeAddtionalQuestionsDataframeLayout")
    }



    vueContactUsPageDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactUsPageDataframe']
        dataframeLabelCode = "Contact Us"
        hql = "select contactUs.name , contactUs.email,contactUs.phone,contactUs.textOfMessage from ContactUs contactUs where contactUs.id=:id"
        isGlobal = true
        saveButton = true
        initOnPageLoad = false
        route = true
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        addFieldDef = [
                "contactUs.name":[name: "name", widget: "InputWidgetVue", "placeHolder":"Enter your Name","flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                                  "validationRules":[[condition:"v => !!v" ,message:'Name.required.message'], [condition:"v => (v && v.length <= 30)",message:'Name.must.be.less.than.30']]],
                "contactUs.email":[name:"email",widget: "EmailWidgetVue", "placeHolder":"Enter your email","validationRules":[[condition:"v => !!v", message: 'Email.required.message']],"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "contactUs.phone":[name:"phone",widget: "PhoneNumberWidgetVue","required": "required","validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]],"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "contactUs.textOfMessage":[name: "textOfMessage", widget: "TextAreaWidgetVue","placeHolder":"Describe about yourself","flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                                           "required": "required","validationRules":[[condition:"v => !!v", message: 'Description.required.message']],]


        ]

        //  dataframeButtons = [Submit: [name: "submit", type: "link", url:"${contextPath}/ElintegroWebsite/ContactUs","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]



        currentFrameLayout = ref("contactUsPageDataframeLayout")
    }
    vueElintegroLoginDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroLoginDataframe']
        dataframeLabelCode = "User.Login"
        hql = "select user.username, user.password from User as user where user.id=:id"
        wrapInForm = true
        saveButton = false
        initOnPageLoad = false
        isGlobal = true

        boolean loginWithSpringSecurity = Holders.grailsApplication.config.loginWithSpringSecurity?true:false
        String loginAuthenticateUrl = loginWithSpringSecurity?"${contextPath}/login/authenticate" : "${contextPath}/login/loginUser"

        addFieldDef = ["user.password":["widget" : "PasswordWidgetVue", "name": "user.password", autoComplete:"on", "width":150,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']]
                       ,"user.username":["widget" : "EmailWidgetVue",  "name": "user.username", autoComplete:"on", "width":150, "errMessage":"Username should be an Email","flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']]
                       ,"rememberMe":["widget" : "CheckboxWidgetVue", height : '30px', "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']]
        ]

        dataframeButtons = [ login:[name:"login", type: "button", url: "${loginAuthenticateUrl}", layout: "<v-flex xs12 sm12 md6 lg6 xl6 pa-0>[BUTTON_SCRIPT]</v-flex>", attr: """color='light-blue darken-2' dark style="width: 10px;" """, doBeforeSave:""" var elementId = '#vueElintegroLoginDataframe';
                                     allParams["username"] = this.state.vueElintegroLoginDataframe_user_username;
                                     allParams["password"] = this.state.vueElintegroLoginDataframe_user_password;
                                     allParams["remember-me"] = this.state.vueElintegroLoginDataframe_rememberMe;
                                      """,
                                    callBackParams: [successScript: """
                                                          console.log("Login Callback");
                                                           this.location.reload();
                                                          //Dataframe.showHideDataframesBasedOnUserType(data);
                                                       """,
                                                     failureScript:""" if(!response.msg){ this.location.reload();}"""],"flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                             forgetPassword:[name: "forgetPassword", type: "link", attr:"style='margin-left:-3px;'", script:""" console.log("Forget Password Clicked");""", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'],
                                             layout: "<v-flex xs12 sm12 md6 lg6 xl6 style='margin-bottom:10px;'><v-layout column align-start justify-center>[BUTTON_SCRIPT]</v-layout></v-flex>"],
                             logInWithGoogle:[name: "logInWithGoogle", type: "image", attr:"style='margin-left:-3px;'", image:[url: "vueLoginDataframe.button.logInWithGoogle.imageUrl", width:'135px', height: '48px'], script:"""
//                                                                                             var url = "/elintegrostartapp/oauth/authenticate/google";
                                                                                             var url = "${contextPath}/springSecurityOAuth2/authenticate?provider=google";
                                                                                             var childWindow = window.open(url, "payment",  "width=500,height=500");
                                                                                             /*if(childWindow){
                                                                                                window.opener.location.reload();
                                                                                                close();
                                                                                             }*/
                                                                                              """, "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                             logInWithFacebook:[name: "logInWithFacebook", type: "image", attr: "style=\"margin-top:3px;\"", image:[url: "vueLoginDataframe.button.logInWithFacebook.imageUrl", width: '135px', height: '43px'],script:"""
                                                                                             var provider = 'facebook';
                                                                                             var url = "${contextPath}/springSecurityOAuth2/authenticate?provider="+provider+"";
                                                                                             var childWindow = window.open(url, "payment",  "width=500,height=500");
                                                                                              """, "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']]

        ]

        currentFrameLayout = ref("vueLoginDataframeLayout")
    }

    vueElintegroRegisterDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroRegisterDataframe']

        hql = "select user.email, user.password, user.firstName, user.lastName from User as user where user.id=:id"

        ajaxSaveUrl = "${contextPath}/register/register"

        dataframeLabelCode = "User.Registration"
        //These are values, that overrides the default ones
        saveButtonAttr = " color='light-blue darken-2' dark"
        initOnPageLoad = false
        isGlobal = true
        saveButton = true
        wrapInForm=true

        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doAfterSave = """ excon.saveToStore('vueElintegroNavigationButtonDataframe','responseData');\nexcon.saveToStore('dataframeShowHideMaps','vueElintegroRegisterDataframe_display', false);\n
                           """
        addFieldDef =[
                "user.email":[widget: "EmailWidgetVue", "placeHolder":"Enter your email","validationRules":[[condition:"v => !!v", message: 'Email.required.message']],"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "user.firstName":[widget: "InputWidgetVue", "placeHolder":"Enter your Firstname","flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                                  ,"validationRules":[[condition:"v => !!v",message:"FirstName.required.message"],[condition: "v => (v && v.length <= 30)",message:"FirstName.must.be.less.than.30"]]],
                "user.lastName":[widget: "InputWidgetVue", "placeHolder":"Enter your Lastname","flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                                 ,"validationRules":[[condition:"v => !!v", message:"LastName.required.message"],[condition:"v => (v && v.length <= 30)", message:"LastName.must.be.less.than.30"]]]
                ,"user.password":[widget: "PasswordWidgetVue", "width":"150", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                                  ,"validationRules":[[condition: "v => !!v ",message:"Password.required.message"],[condition:"v => (v && v.length >= 8)",message:"Password.must.be.greater.than.8"]]]
                ,"password2":[widget: "PasswordWidgetVue", "width":"150", "insertAfter":"user.password", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                              ,"validationRules":[[condition:"v => !!(v==this.state.vueElintegroRegisterDataframe_user_password)",message:"Password.and.Confirm.Password."]]]
        ]

        currentFrameLayout = ref("defaultDialogBoxLayout")
    }

}
