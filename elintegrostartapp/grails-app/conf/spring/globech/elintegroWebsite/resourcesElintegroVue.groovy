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
//        childDataframes = ["vueLoginDataframe"]
        //Vue parameters
        isGlobal = true
        vueStore = ["state": "loggedIn: false,\n"]

        putFillInitDataMethod = false
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueElintegroAppBarDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroAppBarDataframe']
        saveButton = false
        wrapInForm=false

        initOnPageLoad = false
        isGlobal = true
        wrapButtons = false

        currentFrameLayout = ref("vueElintegroAppBarDataframeLayout")
    }
 /*   vueElintegroNavigationButtonDataframe(DataframeVue) { bean ->
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
                            login          : [name: "login", type: "link",showAsDialog: true,script:""" this.vueElintegroLoginDataframe_display = true; \n  drfExtCont.saveToStore('dataframeShowHideMaps','vueElintegroLoginDataframe_display', true);\n""",
                                              refDataframe: ref("vueElintegroLoginDataframe"), tooltip: [message: 'Login'], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            register       : [name: "register", type: "link", showAsDialog: true, attr:"text", script:""" this.vueElintegroRegisterDataframe_display = true;\n  drfExtCont.saveToStore('dataframeShowHideMaps','vueElintegroRegisterDataframe_display', true);\n""",
                                              refDataframe: ref("vueElintegroRegisterDataframe"), tooltip: [message: 'Register'], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        wrapButtons = false


        currentFrameLayout = ref("elintegroNavigationButtonLayout")
    }*/
    vueElintegroNavigationButtonDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationButtonDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [register       : [name: "register", type: "link", showAsDialog: true, attr:"text",
                                              refDataframe: ref("vueElintegroRegisterDataframe"), tooltip: [message: 'Register'], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            login          : [name: "login", type: "link",showAsDialog: true,
                                              refDataframe: ref("vueElintegroLoginDataframe"), tooltip: [message: 'Login'], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            contactUs      : [name: "contactUs", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueContactUsPageDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            carrers        : [name: "carrers", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueCareersDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            gettingStarted : [name: "gettingStarted", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueGettingStartedDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            technologies   : [name: "techonologies", type: "link",route:true, routeIdScript: "0", refDataframe: ref("vueTechnologiesDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            clientsProjects: [name: "clientsProjects", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueClientProjectDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            home           : [name: "home", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueElintegroBannerDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        wrapButtons = false


        currentFrameLayout = ref("elintegroNavigationButtonLayout")
    }
    vueElintegroNavigationButtonAfterLoggedInDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationButtonAfterLoggedInDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [myProfile       : [name: "profile", type: "link", showAsDialog: true,refDataframe: ref("vueElintegroProfileMenuDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            contactUs      : [name: "contactUs", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueContactUsPageDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            applicants     : [name: "applicants", type: "link",route: true, routeIdScript: "0", refDataframe: ref("vueElintegroApplicantsDataframe"), roles: "ROLE_ADMIN", accessType: "ifAnyGranted", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            carrers        : [name: "carrers", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueCareersDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            gettingStarted : [name: "gettingStarted", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueGettingStartedDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            technologies   : [name: "techonologies", type: "link",route:true, routeIdScript: "0", refDataframe: ref("vueTechnologiesDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            clientsProjects: [name: "clientsProjects", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueClientProjectDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            home           : [name: "home", type: "link",route: true,routeIdScript: "0", refDataframe: ref("vueElintegroBannerDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        wrapButtons = false


        currentFrameLayout = ref("elintegroNavigationButtonAfterLoggedInLayout")
    }

    vueElintegroNavigationDrawerDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationDrawerDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        currentFrameLayout = ref("vueElintegroNavigationDrawerDataframeLayout")

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
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
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
        //childDataframes = ["vueCareersPageButtonDataframe"]
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
//      hql = "select person.firstName, person.lastName, person.email, person.phone from Person person where person.id=:id"
        hql = "select person.firstName, person.lastName, person.email,person.phone,application.linkedin from Application application inner join application.applicant person where application.id=:id"
        initOnPageLoad = false
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        saveButton = false
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        isGlobal = true
        addFieldDef = [

                /*"person.firstName":["name":"firstName","type":"link","widget":"InputWidgetVue"],
                "person.lastName":["name":"lastName","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => (v && v.length <= 30)",message:"LastName.must.be.less.than.30"]],],
                "person.email":["name":"email","type":"link","widget":"EmailWidgetVue","validationRules":[[condition:"v => !!v", message: 'email.required.message']]],
                "person.phone":["name":"phone","type":"link","widget":"PhoneNumberWidgetVue","validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]]],
                "application.linkedin":["name":"linkedin","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => !!v", message: 'Linkedin.required.message']]],*/
                "person.availablePosition"  :[
                        "widget"             :"ComboboxVue"
//                        ,"name"              :"person.availablePosition"
                        ,internationalize    :true
                        ,initBeforePageLoad  :true
                        ,multiple            :true
                        ,"hql"               : "select position.id as id, position.name as name from Position as position"
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
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
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
                                      ,multiple:false
                                      ,editButton: true
                                      ,deleteButton:true  ],

                "application.resume":["name":"resume"
                                      ,"widget":"FilesUploadWidgetVue"
                                      ,valueMember: "resume"
                                      , ajaxFileSaveUrl: "${contextPath}/fileUpload/ajaxFileSave"
                                      , insertAfter: "application.resume"
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
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        tab = true
        saveButton = true
        flexGridValuesForSaveButton =['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        isGlobal = true
        doAfterSave = """
                         excon.saveToStore("vueNewEmployeeAddtionalQuestionsDataframe","key", response.nodeId[0]);
                         excon.saveToStore('vueNewEmployeeApplicantDataframe','vueNewEmployeeApplicantDataframe_tab_model','vueNewEmployeeAddtionalQuestionsDataframe-tab-id');"""

        dataframeButtons = [previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeUploadResumeDataframe-tab-id");
                                                                                \n""", url: ""]]

        currentFrameLayout = ref("vueNewEmployeeSelfAssesmentDataframeLayout")
    }

    vueNewEmployeeAddtionalQuestionsDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeAddtionalQuestionsDataframe']
        initOnPageLoad = false
        hql = "select person.description from Person person where person.id=:id"
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        saveButton = true
        tab = true
        flexGridValuesForSaveButton =['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        isGlobal = true

//        addFieldDef = [
//                "person.description":["name":"description","type":"link","widget":"TextAreaWidgetVue"]
//
//        ]
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
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        route = true
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
//        addFieldDef = [
//                "contactUs.name":[name: "name", widget: "InputWidgetVue", "placeHolder":"Enter your Name","flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
//                                  "validationRules":[[condition:"v => !!v" ,message:'Name.required.message'], [condition:"v => (v && v.length <= 30)",message:'Name.must.be.less.than.30']]],
//                "contactUs.email":[name:"email",widget: "EmailWidgetVue", "placeHolder":"Enter your email","validate":true]
//                "contactUs.phone":[name:"phone",widget: "PhoneNumberWidgetVue","required": "required","validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]]],
//                "contactUs.textOfMessage":[name: "textOfMessage", widget: "TextAreaWidgetVue","placeHolder":"Describe about yourself",
//                                           "required": "required","validationRules":[[condition:"v => !!v", message: 'Description.required.message']],]


//        ]

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
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        initOnPageLoad = false
        isGlobal = true

        boolean loginWithSpringSecurity = Holders.grailsApplication.config.loginWithSpringSecurity?true:false
        String loginAuthenticateUrl = loginWithSpringSecurity?"${contextPath}/login/authenticate" : "${contextPath}/login/loginUser"

        addFieldDef = ["user.password":["widget" : "PasswordWidgetVue", "name": "user.password", autoComplete:"on", "width":150]
                       ,"user.username":["widget" : "EmailWidgetVue",  "name": "user.username", autoComplete:"on", "width":150, "errMessage":"Username should be an email"]
                       ,"rememberMe":["widget" : "CheckboxWidgetVue", height : '30px']
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

        currentFrameLayout = ref("vueElintegroLoginDataframeLayout")
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
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        wrapInForm=true

        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doAfterSave = """ excon.saveToStore('vueElintegroNavigationButtonDataframe','responseData');\nexcon.saveToStore('dataframeShowHideMaps','vueElintegroRegisterDataframe_display', false);\n
                           """
        addFieldDef =[
                "user.email":[widget: "EmailWidgetVue", "placeHolder":"Enter your email","validationRules":[[condition:"v => !!v", message: 'email.required.message']],"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "user.firstName":[widget: "InputWidgetVue", "placeHolder":"Enter your Firstname"
                                  ,"validationRules":[[condition:"v => !!v",message:"FirstName.required.message"],[condition: "v => (v && v.length <= 30)",message:"FirstName.must.be.less.than.30"]]],
                "user.lastName":[widget: "InputWidgetVue", "placeHolder":"Enter your Lastname"
                                 ,"validationRules":[[condition:"v => !!v", message:"LastName.required.message"],[condition:"v => (v && v.length <= 30)", message:"LastName.must.be.less.than.30"]]]
                ,"user.password":[widget: "PasswordWidgetVue", "width":"150"
                                  ,"validationRules":[[condition: "v => !!v ",message:"Password.required.message"],[condition:"v => (v && v.length >= 8)",message:"Password.must.be.greater.than.8"]]]
                ,"password2":[widget: "PasswordWidgetVue", "width":"150", "insertAfter":"user.password"
                              ,"validationRules":[[condition:"v => !!(v==this.state.vueElintegroRegisterDataframe_user_password)",message:"Password.and.Confirm.Password."]]]
        ]

        currentFrameLayout = ref("defaultDialogBoxLayout")
    }
    vueElintegroProfileMenuDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroProfileMenuDataframe']
        hql = "select person.id, person.firstName, person.lastName,person.email, person.mainPicture from Person person where person.user = :session_userid"
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        createStore = true
        isGlobal = true
        saveButton = false
//        route = true
        wrapInForm = true
//        "url":"https://s3.us-east-2.amazonaws.com/elintegro1",
        addFieldDef = [
                "person.mainPicture": [
                        "widget" : "PictureDisplayWidgetVue",
                        "layout": "<v-layout align-center justify-center><v-avatar :size=\"90\" style='margin-top:0px;' color=\"grey lighten-4\">[FIELD_SCRIPT]</v-avatar></v-layout>",
                        "aspectRatio":"2.5",
                ],
                "person.firstName": [
                        "widget" : "InputWidgetVue",
                        "readOnly": true,
                        "height":"50px",
                ],
                "person.email"      : [
                        "widget"  : "EmailWidgetVue",
                        "readOnly": true,
                        "height"  : "50px"
                ],
                "person.id": [
                        "widget" : "InputWidgetVue",
                        "readOnly": true,
                        hide: true,
                ]

        ]
//        this.location.reload();
        dataframeButtons = [Logout     : [name: "logout", type: "button", url: "${contextPath}/logoff", "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'], script: "", callBackParams: [failureScript: """vueElintegroProfileMenuDataframeVar.\$router.push("/");this.location.reload();"""]],
                            editProfile: [name: 'editProfile', type: "button",showAsDialog: false, "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'], route: true, routeIdScript: "this.state.vueElintegroProfileMenuDataframe_person_id;", refDataframe: ref('vueElintegroUserProfileDataframe')]]
        currentFrameLayout = ref("vueElintegroProfileMenuDataframeLayout")
    }
    vueElintegroUserProfileDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroUserProfileDataframe']

        dataframeLabelCode = "User.Profile"
        hql = "select person.id, person.mainPicture,person.email, person.firstName, person.lastName, person.bday,  person.phone from Person as person where person.id=:id"
        saveButton = true
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        deleteButton = false
        flexGridValues = ['xs12', 'sm6', 'md6', 'lg6', 'xl4']
        wrapInForm=true
        childDataframes=["vueElintegroResetPasswordDataframe"]
        doAfterSave = """setTimeout(function(){ vueElintegroUserProfileDataframe.\$router.push('/');this.location.reload();}, 3000);"""
        route = true
        addFieldDef =[
                "person.id":[
                        widget: "NumberInputWidgetVue",
                        "required": "required"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6']],

                "person.firstName":[
                        widget: "InputWidgetVue",
                        "required": "required"],

                "person.lastName":[
                        widget: "InputWidgetVue"
                        ,"required": "required"
                ],
                "person.bday":[
                        widget: "DateWidgetVue"
                        ,"required": "required"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg12', 'xl4']],
                "person.email":[
                         widget: "EmailWidgetVue"
                        ,"required": "required"
                        ,readOnly: true
                        ,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "person.phone":[
                          widget: "PhoneNumberWidgetVue"
                         ,"required": "required"
                         ,"validate":["rule":["v => !!v || 'Phone Number is required'"]]
                ],
                "person.languages":[
                        widget: "ComboboxVue"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,"valueMember":"id"
                        , search:true
                        ,multiple: true
                ],

                "person.mainPicture":[
                        "widget" : "PictureDisplayWidgetVue",
                        "aspectRatio":"2.5",
                        "attr": "contain",
                        "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'],
                        "width":200,
                        "height":200],

                "person.uploadPicture":[
                        "widget" : "PictureUploadWidgetVue"
                        ,name:"propertyImages"
                        , valueMember: "mainPicture"
                        ,ajaxFileSaveUrl: "${contextPath}/fileUpload/ajaxFileSave"
                        ,insertAfter: "person.mainPicture"
                        ,multiple:false
                        ,editButton: true
                        ,deleteButton:true
                ]
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
        dataframeButtons = [ resetPassword: [name:"resetPassword", type: "button", url: "", showAsDialog: true, "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'], refDataframe: ref("vueElintegroResetPasswordDataframe")] ]

        currentFrameLayout = ref("vueElintegroUserProfileDataframeLayout")

    }
    vueElintegroResetPasswordDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroResetPasswordDataframe']

//		hql = "select user.password from User as user where user.id=:session_userid"

        initOnPageLoad=true //false by default

        //These are values, that overrides the default ones
        saveButton = false
        wrapInForm=true
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']

        //Javascript to run after the save:
        doAfterSave="""
//						jQuery('#').jqxWindow('close');
                       //alert("Data has been saved successfully for id = "+data.generatedKeys[0]);
                    """

        addFieldDef =[
                "user.password":[widget: "PasswordWidgetVue", "width":"150", "height":"25"],
                "password2":[widget: "PasswordWidgetVue", "width":"150", "height":"25"]
        ]
        dataframeButtons = [ Submit: [name:"submit", type: "button", url: "${contextPath}/register/resetUserPassword", doBeforeAjax: """var url = Dataframe.getUrl();
                                                                                                                            var t = url.searchParams.get("token"); 
                                                                                                                            if(t != undefined || t != null){ allParams['t']=t;}
                                                                                          allParams['vueElintegroResetPasswordDataframe_user_email']=jQuery("#vueElintegroUserProfileDataframe_person_email").val();
                                                                                         """, callBackParams:[successScript:"""if(data.redirect){window.location.href=data.redirectUrl;}
                                                                                                                               jQuery('#resetPassword-Div').jqxWindow('close');"""]],
                             Cancel:[name:"cancel", type:"button", script:"\$router.go(-1)"]]

        currentFrameLayout = ref("defaultDialogBoxLayout")
    }
    vueElintegroApplicantsDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantsDataframe']
        dataframeLabelCode = "Applicants Detail"
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        isGlobal = true
        saveButton = false
        route = true
        addFieldDef = [
                "applicant": [
                        widget            : "GridWidgetVue"
                        , name            : "applicant"

                        , hql             : """select application.id as Id, person.firstName as FirstName ,person.lastName as LastName,  person.email as Email, 
                                                person.phone as Phone from Application application inner join application.applicant person """
                        , gridWidth       : 820
                        , showGridSearch  : true
                        , internationalize: true
                        , sortable        : true
                        , onClick         :[showAsDialog: true, refDataframe: ref("vueElintegroApplicantDetailsDataframe"),MaxWidth:800]

                ]
        ]
        currentFrameLayout = ref("vueElintegroApplicantsDataframeLayout")

    }
    vueElintegroApplicantDetailsDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantDetailsDataframe']
        dataframeLabelCode = "Applicants Detail Information"
        tab = true
        childDataframes = ["vueElintegroApplicantGeneralInformationDataframe","vueElintegroApplicantSelfAssessmentDataframe","vueElintegroApplicantCVDataframe","vueElintegroApplicantQuestionAnswerDataframe"]
        currentFrameLayout = ref("vueElintegroApplicantDetailsDataframeLayout")
    }
    vueElintegroApplicantGeneralInformationDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantGeneralInformationDataframe']
        hql = "select application.id as Id,person.firstName as FirstName,person.lastName as LastName,person.email as Email,person.phone as Phone from Application application inner join application.applicant person where application.id=:id"
        tab = true
        initOnPageLoad = true
        putFillInitDataMethod = true
        doBeforeRefresh = """allParams['id'] = this.vueElintegroApplicantGeneralInformationDataframe_prop.key """
        flexGridValues = ['xs12', 'sm6', 'md6', 'lg6', 'xl6']

        currentFrameLayout = ref("appNameDataframeLayout")
    }
    vueElintegroApplicantSelfAssessmentDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantSelfAssessmentDataframe']
        tab = true
        currentFrameLayout = ref("appNameDataframeLayout")
    }
    vueElintegroApplicantCVDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantCVDataframe']
        tab = true
        currentFrameLayout = ref("appNameDataframeLayout")
    }
    vueElintegroApplicantQuestionAnswerDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantQuestionAnswerDataframe']
        tab = true
        currentFrameLayout = ref("appNameDataframeLayout")
    }
}
