package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.widget.vue.GridWidgetVue
import com.elintegro.erf.widget.vue.TextAreaWidgetVue
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
//        vueStore = ["state": "loggedIn: false,\n"]

        putFillInitDataMethod = false
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueElintegroProgressBarDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ["vueElintegroProgressBarDataframe"]
        saveButton = false
        wrapInForm=false
        initOnPageLoad = false
        isGlobal = true
        addFieldDef = ["progressBar":[widget: "ProgressBarWidgetVue","cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']]]
        currentFrameLayout = ref("vueElintegroProgressBarLayout")

    }
    vueElintegroNavigationFirstTwoButtonDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationFirstTwoButtonDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [
                             home : [name: "home",
                                     type: "link",
                                     attr:  "style='color:#212121;'",
                                     classNames: 'navigation-hover',
                                     route: true,routeName: "home", refDataframe: ref("vueElintegroHomeDataframe"),
                                     "cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']
                                    ]
                            ]
        currentFrameLayout = ref("elintegroNavigationButtonFirstLayout")
    }
    vueElintegroNavigationButtonBeforeLoggedInDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationButtonBeforeLoggedInDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [register       : [name: "register", type: "link", showAsDialog: true, attr:  " style='color:#1976D2;'" , classNames:"navigation-hover-underline",
                                              refDataframe: ref("vueElintegroRegisterDataframe"), tooltip: [message: 'Register'],"cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            login          : [name: "login",  type: "link", showAsDialog: true,attr:"style='color:#1976D2;'", classNames:"navigation-hover-underline",
                                              refDataframe: ref("vueElintegroLoginTabDataframe"), tooltip: [message: 'Login'],"cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            contactUs      : [name: "contactUs", type: "link",attr:"style='color:#212121;'",   classNames: 'navigation-hover', route: true,routeName: "contact-us", refDataframe: ref("vueContactUsPageDataframe"),"cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            careers        : [name: "careers", type: "link",attr:" style='color:#212121;'",  classNames: 'navigation-hover',  route: true,routeName: "careers", refDataframe: ref("vueCareersDataframe"),"cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                           ]
        wrapButtons = false


        currentFrameLayout = ref("elintegroNavigationButtonLayout")
    }
    vueElintegroLanguageSelectorDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroLanguageSelectorDataframe']
        initOnPageLoad = true
        def languageCodeFromConfigFile = Holders.grailsApplication.config.application.languages
        def languageCode = languageCodeFromConfigFile.replace('"""','')
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        doAfterRefresh = """self.changeSelectedLanguageValue(response);"""
        addFieldDef = [
                "languages":[
                        widget: "LanguageSelectorWidgetVue"
                        ,"cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']
                        , hql: """select language.id as id,language.code as code, language.ename as ename from Language as language where language.code in (${languageCode})"""
                        ,"displayMember":"ename"
                        ,"valueMember":"id"
                        , search:true
                        ,defaultLanguage :"English"
                        ,attr: """style='max-width:min-content;margin-top=-2%;'"""
                        ,imageIcon : true
                        ,onSelect:[methodScript:"this.selectedLanguage(_params);"]
                ],]
        currentFrameLayout = ref("vueElintegroLanguageSelectorDataframeLayout")

    }
    vueElintegroNavigationButtonAfterLoggedInDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationButtonAfterLoggedInDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [myProfile       : [name: "profile", type: "link",attr:"style='color:#1976D2;'", showAsDialog: true,refDataframe: ref("vueElintegroProfileMenuDataframe"),"cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            contactUs      : [name: "contactUs", type: "link",attr:"style='color:#212121;'",   classNames: 'navigation-hover', route: true,routeName: "contact-us", refDataframe: ref("vueContactUsPageDataframe"), "cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            applicants     : [name: "applicants", type: "link",attr:"style='color:#1976D2;'",route: true, routeName: "0", refDataframe: ref("vueElintegroApplicantsDataframe"), roles: "ROLE_ADMIN", accessType: "ifAnyGranted", "cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            careers        : [name: "careers", type: "link",attr:"style='color:#212121;'",  classNames: 'navigation-hover',  route: true,routeName: "careers", refDataframe: ref("vueCareersDataframe"), "cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            ]
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
                        "url"         : "assets/home/logo.png",
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        "attr"        : " contain ",
//                        "height"      : "64",
//                        "width"       : "200",
//                        "padding-left ": "75px",
//                        "width"    :  "25%"
//                        "padding"      : "100",
//                        "margin-left" : "85"
                        //"min-width"   : "40"

                ]

        ]
        currentFrameLayout = ref("vueElintegroLogoDataframeLayout")

    }


    vueClientProjectDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueClientProjectDataframe']
        dataframeLabelCode = "Clients & Projects Details"
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        //isGlobal = true
        saveButton = false
        initOnPageLoad = true
        route = true
        addFieldDef =[
                "clientProject": [
                        widget            : "GridWidgetVue"
                        , name            : "clientProject"

                        ,hql             : """select clientProject.id as Id ,clientProject.clientName as Clientname ,clientProject.projectName as Projectname,  clientProject.logo as Logo, 
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
    vueElintegroAppsDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroAppsDataframe']
        dataframeLabelCode = "our.application"
        isGlobal = true
        saveButton= false
        initOnPageLoad = false

        currentFrameLayout = ref("vueElintegroAppsDataframeLayout")
    }
    vueElintegroAboutUsMenuDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroAboutUsMenuDataframe']
        isGlobal = true
        saveButton= false
        initOnPageLoad = false
        dataframeButtons = [
                            ourWork  : [name: "ourWork", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('our_work');""" , "cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            ourProcess  : [name: "ourProcess", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('our_process');""","cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            collaboration:[name: "collaboration", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('collaboration');""","cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            ourFramework:[name: "ourFramework", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('our_framework');""","cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            testimonials:[name:"testimonials",type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('Quotes')""","cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            ourClientsProjects:[name: "ourClientsProjects", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('ourClientsProjects');""","cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            technologiesInUse:[name: "technologiesInUse", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('our_Technologies');""","cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                            contactQuiz:[name: "contactQuiz", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('quiz_placeholder');""","cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],

        ]

        currentFrameLayout = ref("vueElintegroAboutUsMenuDataframeLayout")

    }

    vueElintegroSubMenuDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroSubMenuDataframe']
        isGlobal = true
        saveButton= false
        initOnPageLoad = false
        dataframeButtons = [
                        ecommerce  : [name: "ecommerce", type: "link",attr:"style='color:#1976D2;'",route: true,routeName: 0,script: """this.ecommerceApp();""", "cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                        translator : [name: "translator", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",route: true,routeName: "translator", refDataframe: ref("vueTranslatorAssistantDataframe"),"cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                        quizzable  : [name: "quizzable", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.quizzableApp();""", "cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
                        //showApplicant  : [name: "showApplicant", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",route: true,routeName: 0,refDataframe: ref("vueAddMapWidgetDataframe"), "cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']],
        ]

        currentFrameLayout = ref("vueElintegroSubMenuDataframeLayout")

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
                "java"      : ["widget": "PictureDisplayWidgetVue", "url": "assets/java.PNG", "aspectRatio":"1.5"],
                "javascript": ["widget": "PictureDisplayWidgetVue", "url": "assets/javascript.PNG", "aspectRatio":"1.5"],
                "grails"    : ["widget": "PictureDisplayWidgetVue", "url": "assets/grailsphoto.PNG", "aspectRatio":"1.5"],
                "vuejs"     : ["widget": "PictureDisplayWidgetVue", "url": "assets/vuejs.PNG", "aspectRatio":"1.0"],
                "kafka"     : ["widget": "PictureDisplayWidgetVue", "url": "assets/kafka.PNG", "aspectRatio":"1.0"],
                "oracle"    : ["widget": "PictureDisplayWidgetVue", "url": "assets/oracle.PNG", "aspectRatio":"1.0"],
                "nodejs"    : ["widget": "PictureDisplayWidgetVue", "url": "assets/nodejs.PNG", "aspectRatio":"1.0"],
                "kubernetes": ["widget": "PictureDisplayWidgetVue", "url": "assets/kubernetes.PNG", "aspectRatio":"1.0"],
                "mysql"     : ["widget": "PictureDisplayWidgetVue", "url": "assets/mysql.PNG", "aspectRatio":"1.0"],

        ]
        currentFrameLayout = ref("vueTechnologiesDataframeDataframeLayout")


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
        addFieldDef=[
                        "careersHeader":["widget":"TextDisplayWidgetVue"
                                 ,"name":"careersHeader"
                                 ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        ],
                        "careersDescription":["widget"      : "PictureDisplayWidgetVue"
                                              ,"attr"       : " contain class='careersDescriptionPicture' "
                                              ,url: "assets/image_Careers.png"
                                              ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                ]]
        dataframeButtons = [registerForNewEmployee: [name: "register", type: "button",route: true,routeName: "new-employee-applicant", attr:" width= '100' style='background-color:#1976D2; color:white;' ", refDataframe: ref("vueNewEmployeeApplicantDataframe"),
                                                     "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']]]
        currentFrameLayout = ref("vueCareersDataframeLayout")
    }
    vueNewEmployeeApplicantDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeApplicantDataframe']
        dataframeLabelCode = "New Employee Applicant Registration"
        initOnPageLoad = false
        saveButton = false
        wrapInForm = false
        route = true
        addFieldDef = [
                "tab":[
                        widget: "TabWidgetVue",
                        dataframes : ['vueNewEmployeeBasicInformationDataframe','vueNewEmployeeUploadResumeDataframe','vueNewEmployeeSelfAssesmentDataframe','vueNewEmployeeAddtionalQuestionsDataframe']
                        ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,flexAttr: "pa-1"
                        ,disableTabs : true
                        ,tabAttr:""" class='new-employee-tab-attr' ripple """
                        ,tabsAttr:"""hide-slider active-class="blue darken-2"  """
                        ,cardAttr:""" round style ="overflow:hidden;"  """

                ]
        ]
//        childDataframes = ['vueNewEmployeeBasicInformationDataframe','vueNewEmployeeUploadResumeDataframe','vueNewEmployeeSelfAssesmentDataframe','vueNewEmployeeAddtionalQuestionsDataframe']
        currentFrameLayout = ref("vueElintegroApplicantDetailsDataframeLayout")
    }
    vueNewEmployeeBasicInformationDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeBasicInformationDataframe']
        hql = "select person.firstName, person.lastName, person.email, person.phone, application.linkedin, application.availablePositions from Application application inner join application.applicant person where application.id=:id"
        initOnPageLoad = false
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        saveButton = true
        requiresConfirmationMessage = false
        dataframeLabelCode = """Basic.information"""
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']

        doAfterSave = """
                        excon.goToTab("vueNewEmployeeApplicantDataframe", "vueNewEmployeeUploadResumeDataframe");
                      """

        doBeforeSave = """
                       """

        addFieldDef = [

                "person.firstName":["name":"firstName","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => !!v", message: 'FirstName.required.message']],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "person.lastName":["name":"lastName","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => !!v", message: 'LastName.required.message'],[condition:"v => (v && v.length <= 30)",message:"LastName.must.be.less.than.30"]],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "person.email":["name":"email","type":"link","widget":"EmailWidgetVue","validationRules":[[condition:"v => !!v", message: 'email.required.message']],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "person.phone":[
                        "name":"phone",
                        "type":"link",
                        "widget":"PhoneNumberWidgetVue",
                        "validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "application.linkedin":["name":"linkedin","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => !!v", message: 'Linkedin.required.message']],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "application.availablePositions"  :[
                        widget             :"ComboboxVue"
                        ,internationalize    :true
                        ,initBeforePageLoad  :true
                        ,multiple            :true
                        ,"hql"               : "select position.id as id, position.name as name from Position as position"
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                        , search:true
                        ,attr: """ outlined   background-color='#EBF9FF !important' color='#2AB6F6' """

                ],


        ]

        currentFrameLayout = ref("vueNewEmployeeBasicInformationDataframeLayout")

    }

    vueNewEmployeeUploadResumeDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeUploadResumeDataframe']
        initOnPageLoad = false
        hql = "select application.id, application.files from Application application where application.id=:id"
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        saveButton = true
        dataframeLabelCode = """Upload.resume"""
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        cssGridValuesForSaveButton = ['xs':'3', 'sm':'3', 'md':'6', 'lg':'6', 'xl':'6']
        tab = true
        isGlobal = false
        requiresConfirmationMessage = false
        doBeforeSave = """
              const applicationId = excon.getFromStore("vueNewEmployeeBasicInformationDataframe", "domain_keys.application.id")  
              params.persisters.application.id.value = applicationId;
              params.namedParameters.id.value = applicationId;
              params.domain_keys.application.id = applicationId;    
        """
        doAfterSave = """
                         excon.goToTab("vueNewEmployeeApplicantDataframe", "vueNewEmployeeSelfAssesmentDataframe");
                      """
        addFieldDef = [
              /* please put application.images in above hql
               "application.images":["name":"images"
                                      ,"widget":"PictureUploadWidgetVue"
                                      ,ajaxFileSaveUrl: "fileUpload/ajaxFileSave"
                                      ,multiple:true
                                      ,editButton: true
                                      ,deleteButton:true
                                      ,camera:false
                ],*/

                "application.files":["name":"files"
                                      ,"widget":"FilesUploadWidgetVue"
                                      , ajaxFileSaveUrl: "fileUpload/ajaxFileSave"
                                      ,multiple:true
                                      ,validationRules:[[condition:"v => !!v && (v && v.length > 0)",message:"application.files.required.message"]]
                                      ,attr: """ outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                                      ,"accept":".pdf,.docx,.doc"

                                     ]
        ]

        dataframeButtons = [ previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeBasicInformationDataframe-tab-id");\n""",
                                        "cssGridValues" : ['xs':'9', 'sm':'9', 'md':'6', 'lg':'6', 'xl':'6'],url: ""]]

        currentFrameLayout = ref("vueNewEmployeeUploadResumeDataframeLayout")
    }
    vueNewEmployeeSelfAssesmentDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeSelfAssesmentDataframe']
        initOnPageLoad = false
        putFillInitDataMethod = true
        dataframeLabelCode = """Self.assessment"""
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        tab = true
        requiresConfirmationMessage = false
        saveButton = false
        cssGridValuesForSaveButton = ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
//        params['applicationId']= excon.getFromStore('vueNewEmployeeBasicInformationDataframe','domain_keys.application.id');
        doBeforeRefresh = """
                             excon.setValuesForNamedParams({'targetDataframe': 'vueNewEmployeeSelfAssesmentDataframe', 
                                                            'namedParamKey': 'applicationId', 
                                                            'sourceDataframe': 'vueNewEmployeeBasicInformationDataframe', 
                                                            'fieldName':'application',
                                                            'key': 'id'});
                             """
        doAfterSave = """
                         excon.saveToStore("vueNewEmployeeAddtionalQuestionsDataframe","key", response.nodeId[0]);
                         excon.saveToStore('vueNewEmployeeApplicantDataframe','vueNewEmployeeApplicantDataframe_tab_model','vueNewEmployeeAddtionalQuestionsDataframe-tab-id');"""
        addFieldDef =[
                "applicationSkill":[ widget          : "GridWidgetVue"

                                    ,name            : "applicationSkill"
                                    ,hql             : """select application.id as AppId,applicationSkill.id as Id, applicationSkill.skill as Skill,applicationSkill.level as Level, applicationSkill.comment as Comment from ApplicationSkill applicationSkill inner join applicationSkill.application application where application.id=:applicationId"""
                                    ,internationalize: true
//                                    ,onClick :[showAsDialog: true, refDataframe: ref("vueNewEmployeeApplicantEditSkillDataframe"),]
                                    ,editButton: true
                                    ,itemKey: "Id"
                                    ,onButtonClick   : [
                                                ['actionName': 'Edit Skill', 'buttons': [
                                                        [name        : "edit"
                                                         ,MaxWidth: 500
                                                        ,showAsDialog: true
                                                        ,refreshInitialData:true
                                                        ,tooltip     : [message: "tooltip.grid.edit", internationalization: true]
                                                        ,refDataframe: ref("vueNewEmployeeApplicantEditSkillDataframe")
                                                        ,vuetifyIcon : [name: "edit"]
                                                        ]]]]
]
        ]
        dataframeButtons = [
                next:[name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"excon.saveToStore('vueNewEmployeeApplicantDataframe','vueNewEmployeeApplicantDataframe_tab_model','vueNewEmployeeAddtionalQuestionsDataframe-tab-id');","cssGridValues" : ['xs':'4', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4'], url:""],
                addSkill:[name:"addSkill",type:"button",attr: """style='background-color:#1976D2; color:white;' """,showAsDialog:true,refDataframe: ref("vueNewEmployeeApplicantAddSkillDataframe"),"cssGridValues" : ['xs':'4', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4']],
                previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeUploadResumeDataframe-tab-id");
                                                                                \n""","cssGridValues" : ['xs':'4', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4'], url: ""]
        ]
        childDataframes = ['vueNewEmployeeApplicantEditSkillDataframe','vueNewEmployeeApplicantAddSkillDataframe']

        currentFrameLayout = ref("vueNewEmployeeSelfAssesmentDataframeLayout")
    }
    vueNewEmployeeApplicantEditSkillDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeApplicantEditSkillDataframe']
        saveButton = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        initOnPageLoad = false
        putFillInitDataMethod = true
        doBeforeRefresh = """
                             excon.setValuesForNamedParamsFromGrid({'targetDataframe': 'vueNewEmployeeApplicantEditSkillDataframe', 
                                                            'namedParamKey': 'id', 
                                                            'sourceDataframe': 'vueNewEmployeeSelfAssesmentDataframe', 
                                                            'fieldName':'applicationSkill',
                                                            'key': 'Id'});
"""
//        doBeforeSave = """params['key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id'] = self.vueNewEmployeeApplicantEditSkillDataframe_prop.key"""
        doAfterSave = """ excon.setVisibility("vueNewEmployeeApplicantEditSkillDataframe", false);
                          excon.refreshDataForGrid(response,'vueNewEmployeeSelfAssesmentDataframe', 'applicationSkill', 'U', 'transits');
                      """
        hql = "select applicationSkill.id as Id, applicationSkill.skill as Skill, applicationSkill.level as Level, applicationSkill.comment as Comment  from ApplicationSkill applicationSkill where applicationSkill.id=:id"
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        addFieldDef = ["applicationSkill.skill":[widget: "InputWidgetVue",readOnly: true,attr: """ outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                       "applicationSkill.level":["max":10,  "validationRules":[[condition:"v => (v && new RegExp('^([0-9]|1[0])\$').test(v))",message:"digits.not.valid"]],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """, script: """"""],
                       "applicationSkill.comment":[widget: "InputWidgetVue",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """]
                        ]
        currentFrameLayout = ref("vueNewEmployeeApplicantEditSkillDataframeLayout")
    }
    vueNewEmployeeApplicantAddSkillDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeApplicantAddSkillDataframe']
        saveButton = false
        initOnPageLoad = false
        putFillInitDataMethod = true
        hql = "select applicationSkill.id as Id, applicationSkill.skill as Skill,applicationSkill.level as Level, applicationSkill.comment as Comment from ApplicationSkill applicationSkill inner join applicationSkill.application application where application.id=:applicationId"
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        addFieldDef = ["applicationSkill.skill":[widget: "InputWidgetVue",attr: """ autofocus outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                       "applicationSkill.level":["max":10,  "validationRules":[[condition:"v => (v && new RegExp('^([0-9]|1[0])\$').test(v))",message:"digits.not.valid"]],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                        "applicationSkill.comment":[widget: "InputWidgetVue",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """]]
        dataframeButtons = [save: [name:"save", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""this.addNewSkill();""","cssGridValues" : ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'],url: ""]]
        currentFrameLayout = ref("vueNewEmployeeApplicantAddSkillDataframeLayout")
    }

    vueNewEmployeeAddtionalQuestionsDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeAddtionalQuestionsDataframe']
        initOnPageLoad = false
        dataframeLabelCode = """Additional.questions"""
        doBeforeSave = """var applicationId = excon.getFromStore('vueNewEmployeeBasicInformationDataframe','domain_keys.application.id');
                       params['applicationId'] = applicationId;
                       params.persisters.application.id.value = applicationId;
                       params.namedParameters.applicationId.value = applicationId;
                       params.domain_keys.application.id = applicationId; """
        hql = "select application.id as Id, application.question1, application.question2 from Application application where application.id=:applicationId"
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        saveButton = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        tab = true
        cssGridValuesForSaveButton = ['xs':'4', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4']
        doAfterSave = """self.\$router.push("/thank-you-message");"""
        addFieldDef = [
                "application.question1":[widget:"TextAreaWidgetVue"
                                         ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,],
                "application.question2":[widget:"TextAreaWidgetVue"
                                         ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,]
        ]
        dataframeButtons = [
                previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeSelfAssesmentDataframe-tab-id");\n""",
                           "cssGridValues" : ['xs':'9', 'sm':'9', 'md':'6', 'lg':'6', 'xl':'6'],url: ""]]

        currentFrameLayout = ref("vueNewEmployeeAddtionalQuestionsDataframeLayout")
    }
    vueNewEmployeeThankYouMessageAfterSaveDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeThankYouMessageAfterSaveDataframe']
        saveButton = false
        initOnPageLoad = true
        currentRoute = 'thank-you-message'
        route = true
//        params['applicationId'] = excon.getFromStore('vueNewEmployeeBasicInformationDataframe','domain_keys.application.id');
        doBeforeRefresh= """
                             excon.setValuesForNamedParams({'targetDataframe': 'vueNewEmployeeThankYouMessageAfterSaveDataframe', 
                                                            'namedParamKey': 'id', 
                                                            'sourceDataframe': 'vueNewEmployeeBasicInformationDataframe', 
                                                            'fieldName':'application',
                                                            'key': 'id'});
"""
        doAfterRefresh = """setTimeout(function(){ self.\$router.push("/home");window.location.reload();}, 10000);"""
        hql = "select person.firstName, person.lastName from Application application inner join application.applicant person where application.id=:id"
        currentFrameLayout = ref("vueNewEmployeeThankYouMessageAfterSaveDataframeLayout")

    }

    vueContactUsPageDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactUsPageDataframe']
        dataframeLabelCode = "Contact Us"
        hql = "select contactUs.name , contactUs.email,contactUs.phone,contactUs.textOfMessage from ContactUs contactUs where contactUs.id=:id"
        isGlobal = false
        saveButton = true
        saveButtonAttr = """ style='background-color:#1976D2; color:white;' """
        initOnPageLoad = false
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        route = true
        currentRoute = 'contact-us'
        doAfterSave = """excon.showAlertMessage(response);excon.refreshPage;"""
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        addFieldDef = [
                "contactUs.name":["name":"Name","widget":"InputWidgetVue",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "contactUs.email":["name":"Name","widget":"InputWidgetVue",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "contactUs.phone":[name:"phone",widget: "PhoneNumberWidgetVue","validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "contactUs.textOfMessage":["name":"Name","widget":"TextAreaWidgetVue",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],]

        //  dataframeButtons = [Submit: [name: "submit", type: "link", url:"ElintegroWebsite/ContactUs","cssGridValues": ['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']]]


        currentFrameLayout = ref("contactUsPageDataframeLayout")
    }
    vueElintegroLoginTabDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroLoginTabDataframe']
        initOnPageLoad = false
        saveButton = false
        isGlobal = true
        addFieldDef = [
                "tab":[
                        widget: "TabWidgetVue",
                        dataframes : ['vueElintegroLoginDataframe','vueElintegroLoginWithOTPDataframe']
                        ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,flexAttr: "pa-0"
                        ,showCloseButton: true
                        ,tabAttr:""" class='login-tab-attr' ripple """
                        ,tabsAttr:"""hide-slider active-class="blue darken-2"  """
                        ,cardAttr:""" round style ="overflow:hidden;"  """

                ]
        ]
        currentFrameLayout = ref("vueElintegroLoginTabDataframeLayout")
    }
    vueElintegroLoginDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroLoginDataframe']
        dataframeLabelCode = "User.Login"
        hql = "select user.username, user.password from User as user where user.id=:id"
        wrapInForm = true
        saveButton = false
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        initOnPageLoad = false
        isGlobal = true

        addFieldDef = ["user.password":["widget" : "PasswordWidgetVue", "name": "user.password", autoComplete:"on", "width":150, attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' ""","cssGridValues" : ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']]
                       ,"user.username":["widget" : "EmailWidgetVue",attr: "autofocus outlined background-color='#EBF9FF !important' color='#2AB6F6' ", "name": "user.username", autoComplete:"on", "width":150,placeholder:"Enter your email", "errMessage":"Username should be an email","cssGridValues" : ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']]
                       ,"rememberMe":["widget" : "CheckboxWidgetVue","cssGridValues" : ['xs':'8', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6'],attr: """ class='mt-5' """]
                       ,"orTextInLogin":["widget" :"TextDisplayWidgetVue",name: "orTextInLogin",attr: """ style='color:gray;' """, "cssGridValues" : ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],
        ]

        dataframeButtons = [logInWithGoogle:[name: "logInWithGoogle",type: "image", image:[url: "vueLoginDataframe.button.logInWithGoogle.imageUrl",width:'100%', height: '100%'], script:"""
//                                                                                             var url = "elintegrostartapp/oauth/authenticate/google";
                                                                                             var url = "springSecurityOAuth2/authenticate?provider=google";
                                                                                             var childWindow = window.open(url, "payment",  "width=500,height=500");
                                                                                             /*if(childWindow){
                                                                                                window.opener.location.reload();
                                                                                                close();
                                                                                             }*/
                                                                                              ""","cssGridValues" : ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']],
                             logInWithFacebook:[name: "logInWithFacebook",type: "image", image:[url: "vueLoginDataframe.button.logInWithFacebook.imageUrl",width:'100%', height: '100%'],script:"""
                                                                                             var provider = 'facebook';
                                                                                             var url = "springSecurityOAuth2/authenticate?provider="+provider+"";
                                                                                             var childWindow = window.open(url, "payment",  "width=500,height=500");
                                                                                              ""","cssGridValues" : ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']],
                            login:[name:"login", type: "button", attr: """color='blue darken-2' dark width='100%' """,
                                   script:"this.login();","cssGridValues" : ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                                   ],
                            forgetPassword:[name: "forgetPassword", type: "link", attr:"""style='color:#1976D2;' """,script: """excon.setVisibility('vueElintegroLoginTabDataframe',false);this.\$router.push("forget-password");""", "cssGridValues" : ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']]
                        ]

        currentFrameLayout = ref("vueElintegroLoginDataframeLayout")
    }
    vueElintegroLoginWithOTPDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroLoginWithOTPDataframe']
        currentRoute = 'login-with-otp'
        initOnPageLoad = false
        dataframeLabelCode = """Login.with.otp"""
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        saveButton = false
        isGlobal = true
        addFieldDef = [
                "email":[name:"email",widget: "InputWidgetVue",placeholder: "Enter your email here.",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6'"""],
                "sendCode":[widget: "ButtonWidgetVue"
                            ,insertAfter: "email"
                            ,attr: """style='background-color:#1976D2; color:white;text-transform:capitalize;width:100%;' v-show = 'showHideSendCodeButton' """
                            ,script: """this.sendVerificationCode();"""],
                "verificationCode":[name: "verificationCode", widget:"InputWidgetVue",placeholder: "Enter the verification code you received.",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' v-if = 'showThisFieldAfterCodeSent'"""],
                "codeNotReceived":[widget: "TextDisplayWidgetVue",isDynamic:false,attr: """v-show='showThisFieldAfterCodeSent'""", "cssGridValues" : ['xs':'9', 'sm':'9', 'md':'9', 'lg':'9', 'xl':'9']],
                "resendCode":[widget: "ButtonWidgetVue"
                              ,insertAfter: "codeNotReceived"
                              ,attr: """style='background-color:white;color:#1976D2; text-transform:capitalize;margin-left:-20px;margin-top:-5px;' text v-show='showThisFieldAfterCodeSent' """
                              ,script: """this.resendVerificationCode();"""
                              ,"cssGridValues" : ['xs':'3', 'sm':'3', 'md':'3', 'lg':'3', 'xl':'3']]]
        dataframeButtons = [submit: [name: "submit", type: "button",attr: """flex-right style='background-color:#1976D2; color:white;' v-show='showThisFieldAfterCodeSent' """,script: """this.loginWithVerificationCode();""", "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']]]
        currentFrameLayout = ref("vueElintegroLoginWithOTPDataframeLayout")

    }
    vueElintegroForgetPasswordDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroForgetPasswordDataframe']
        hql = "select user.email from User as user where user.id=:id"
        dataframeLabelCode = "Forget.Password"
        initOnPageLoad = false
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        saveButton = false
        currentRoute = 'forget-password'
        route = true
        //isGlobal = true
        doAfterRefresh = """excon.setVisibility('vueElintegroLoginDataframe',false);"""
        addFieldDef =[
                "user.email":[widget: "EmailWidgetVue",attr: "autofocus outlined background-color='#EBF9FF !important' color='#2AB6F6'", "placeHolder":"Enter your email","validationRules":[[condition:"v => !!v", message: 'email.required.message']]],
        ]
        dataframeButtons = [submit: [name: "submit", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script: """this.forgotPassword();""", "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']]]
        currentFrameLayout = ref("vueElintegroForgetPasswordDataframeLayout")
    }
    vueElintegroChangeForgotPasswordDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroChangeForgotPasswordDataframe']
        dataframeLabelCode = "Forget.Password"
        initOnPageLoad = false
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        saveButton = false
        currentRoute = 'change-forget-password'
        route = true
        //isGlobal = true
        addFieldDef =[
                "newPassword":[name:"newPassword"
                               ,widget:"PasswordWidgetVue"
                               ,"validationRules":[[condition: "v => !!v ",message:"Password.required.message"],[condition:"v => (v && new RegExp('^(?=.*?[#?!@%^&*-])').test(v))",message:"password.contain.special.character"]
                                                   ,[condition:"v => (v && v.length >= 8)",message:"Password.must.be.greater.than.8"]],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "confirmPassword":[name:"confirmPassword"
                                   ,widget:"PasswordWidgetVue"
                                   , "insertAfter":"newPassword"
                                   ,"validationRules":[[condition:"v => !!(v==this.state.transits.newPassword.value)",message:"Password.and.Confirm.Password."]],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
        ]
        dataframeButtons = [submit: [name: "submit", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script: """this.changeForgotPassword();""", "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']]]
        currentFrameLayout = ref("vueElintegroForgetPasswordDataframeLayout")
    }

    vueElintegroRegisterDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroRegisterDataframe']

        hql = "select user.email, user.password, user.firstName, user.lastName from User as user where user.id=:id"

        ajaxSaveUrl = "register/register"

        dataframeLabelCode = "User.Registration"
        //These are values, that overrides the default ones
        saveButtonAttr = " color='blue darken-2' dark width=100% "
        initOnPageLoad = false
        isGlobal = true
        saveButton = true
        cssGridValues = ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
        wrapInForm=true

        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        doAfterSave = """ excon.setVisibility('vueElintegroRegisterDataframe',false);"""
        addFieldDef =[
                "user.email":[widget: "EmailWidgetVue", "placeHolder":"Enter your email",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' ""","validationRules":[[condition:"v => !!v", message: 'email.required.message']],"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],
                "user.firstName":[widget: "InputWidgetVue", "placeHolder":"Enter your Firstname",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                                  ,"validationRules":[[condition:"v => !!v",message:"FirstName.required.message"],[condition: "v => (v && v.length <= 30)",message:"FirstName.must.be.less.than.30"]]],
                "user.lastName":[widget: "InputWidgetVue", "placeHolder":"Enter your Lastname",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                                 ,"validationRules":[[condition:"v => !!v", message:"LastName.required.message"],[condition:"v => (v && v.length <= 30)", message:"LastName.must.be.less.than.30"]]]
                ,"user.password":[widget: "PasswordWidgetVue", "width":"150",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                                  ,"validationRules":[[condition: "v => !!v ",message:"Password.required.message"],[condition:"v => (v && v.length >= 8)",message:"Password.must.be.greater.than.8"]]]
                ,"password2":[widget: "PasswordWidgetVue", "width":"150", "insertAfter":"user.password",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                              ,"validationRules":[[condition:"v => !!(v == excon.getStateDataframeFieldValue(this, 'user', 'password') )",message:"Password.and.Confirm.Password."]]]
        ]

        currentFrameLayout = ref("vueElintegroRegisterDataframeLayout")
    }
    vueElintegroProfileMenuDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroProfileMenuDataframe']
        hql = "select person.id, person.firstName, person.lastName,person.email, person.mainPicture from Person person where person.user = :session_userid"
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        createStore = true
        isGlobal = true
        saveButton = false
        initOnPageLoad=true
        wrapInForm = true
        addFieldDef = [
                "person.mainPicture": [
                        "widget" : "PictureDisplayWidgetVue",
                        "layout": "<v-layout align-center justify-center><v-avatar :size=\"90\" style='margin-top:0px;' color=\"grey lighten-4\">[FIELD_SCRIPT]</v-avatar></v-layout>",
                        "aspectRatio":"1.0",
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
        dataframeButtons = [Logout     : [name: "logout", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script: "this.logout();", "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],
                            editProfile: [name: 'editProfile', type: "button",attr: """style='background-color:#1976D2; color:white;' """,showAsDialog: false, "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'], route: true, routeName: "this.state.persisters.person.id.value;", refDataframe: ref('vueElintegroUserProfileDataframe')]]
        currentFrameLayout = ref("vueElintegroProfileMenuDataframeLayout")
    }
    vueElintegroUserProfileDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroUserProfileDataframe']

        dataframeLabelCode = "User.Profile"
        hql = "select person.id, person.mainPicture,person.email, person.firstName, person.lastName, person.bday, person.phone, person.languages from Person as person where person.id=:id"
//        hql = "select person.id, person.mainPicture,person.email, person.firstName, person.lastName, person.bday, person.phone, person.languages from Person as person where person.id=:id"
        saveButton = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        deleteButton = false
        cssGridValues =  ['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'4']
        wrapInForm=true
        childDataframes=["vueElintegroResetPasswordDataframe"]
        doBeforeSave = """if(params.transits.uploadPicture.value){
                            params.persisters.person.mainPicture.value = params.transits.uploadPicture.value[0].imageName;
                          }
                          else if(params.persisters.person.mainPicture.value != "assets/default_profile.jpg"){
                                 let imageName = params.persisters.person.mainPicture.value.replace('fileDownload/fileDownload/','');
                                 params.persisters.person.mainPicture.value = imageName;
                          }
                          else{
                               params.persisters.person.mainPicture.value = '';
                          }
                         
        """
        doAfterSave = """setTimeout(function(){excon.refreshPage();}, 1000);"""
        route = true
        addFieldDef =[
                "person.id":[
                        widget: "NumberInputWidgetVue",
                        "required": "required"
                        ,"cssGridValues" :['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']],

                "person.firstName":[
                        widget: "InputWidgetVue",
                        "required": "required",
                        attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,
                        "validationRules":[[condition:"v => !!v", message:"FirstName.required.message"]],
                ],

                "person.lastName":[
                        widget: "InputWidgetVue"
                        ,"required": "required"
                        ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,"validationRules":[[condition:"v => !!v", message:"LastName.required.message"]]
                ],
                "person.bday":[
                        widget: "DateWidgetVue"
                        ,"required": "required"
                        ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,"cssGridValues" :['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'4']],
                "person.email":[
                         widget: "EmailWidgetVue"
                        ,"required": "required"
                        ,readOnly: true
                         ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "person.phone":[
                          widget: "PhoneNumberWidgetVue"
                         ,"required": "required"
                          ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                         ,"validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]],
                ],
                "person.languages":[
                        widget: "ComboboxVue"
                        ,"cssGridValues" :['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'4']
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,"valueMember":"id"
                        , search:true
                        ,multiple: true
                        ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                ],

                "person.mainPicture":[
                        "widget" : "PictureDisplayWidgetVue",
                        "aspectRatio":"2.5",
                        "attr": "contain",
                        "cssGridValues" :['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6'],
                        "width":200,
                        "height":200],

                "uploadPicture":[
                        "widget" : "PictureUploadWidgetVue"
                        , valueMember: "mainPicture"
                        ,ajaxFileSaveUrl: "fileUpload/ajaxFileSave"
                        ,insertAfter: "person.mainPicture"
                        ,multiple:false
                        ,editButton: true
                        ,deleteButton:true
                        ,camera: true
                ]

        ]

        dataframeButtons = [ resetPassword: [name:"resetPassword", type: "button",attr: """style='background-color:#1976D2; color:white;'  """, url: "", showAsDialog: true, "cssGridValues" :['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6'], refDataframe: ref("vueElintegroResetPasswordDataframe")] ]

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
        cssGridValues = ['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']

        //Javascript to run after the save:
        doAfterSave="""
//						jQuery('#').jqxWindow('close');
                       //alert("Data has been saved successfully for id = "+data.generatedKeys[0]);
                    """

        addFieldDef =[
                "user.password":[widget: "PasswordWidgetVue", "width":"150", "height":"25"],
                "password2":[widget: "PasswordWidgetVue", "width":"150", "height":"25"]
        ]
        dataframeButtons = [ Submit: [name:"submit", type: "button",attr:"""style='background-color:#1976D2; color:white;'""" , url: "register/resetUserPassword", doBeforeAjax: """var url = Dataframe.getUrl();
                                                                                                                            var t = url.searchParams.get("token"); 
                                                                                                                            if(t != undefined || t != null){ params['t']=t;}
                                                                                          params['vueElintegroResetPasswordDataframe_user_email']=jQuery("#vueElintegroUserProfileDataframe_person_email").val();
                                                                                         """, callBackParams:[successScript:"""if(data.redirect){window.location.href=data.redirectUrl;}
                                                                                                                               jQuery('#resetPassword-Div').jqxWindow('close');"""]],
                             Cancel:[name:"cancel", type:"button",attr: """style='background-color:#1976D2; color:white;' """, script:"\$router.go(-1)"]]

        currentFrameLayout = ref("defaultDialogBoxLayout")
    }

    vueElintegroApplicantsDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantsDataframe']
        dataframeLabelCode = "Applicants Detail"
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        isGlobal = false
        saveButton = false
        initOnPageLoad = true
        route = true
        currentRoute = 'applicants'
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
                        , onClick         :[showAsDialog: true ,refreshInitialData: true, refDataframe: ref("vueElintegroApplicantDetailsDataframe"),MaxWidth:800]

                ]
        ]
        childDataframes = ["vueElintegroApplicantDetailsDataframe"]
        currentFrameLayout = ref("vueElintegroApplicantsDataframeLayout")

    }
    vueElintegroApplicantDetailsDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantDetailsDataframe']
        dataframeLabelCode = "Applicants Detail Information"
        initOnPageLoad = false
//        tab = true
        addFieldDef = [
                "tab":[
                        widget: "TabWidgetVue",
                        dataframes : ["vueElintegroApplicantGeneralInformationDataframe","vueElintegroApplicantSelfAssessmentDataframe","vueElintegroApplicantCVDataframe","vueElintegroApplicantQuestionAnswerDataframe","vueElintegroCommentPageForApplicantDataframe"]
                        ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,flexAttr: "pa-0"
                        ,showCloseButton:true
                ]
        ]
//        childDataframes = ["vueElintegroApplicantGeneralInformationDataframe","vueElintegroApplicantSelfAssessmentDataframe","vueElintegroApplicantCVDataframe","vueElintegroApplicantQuestionAnswerDataframe","vueElintegroCommentPageForApplicantDataframe"]
        currentFrameLayout = ref("vueElintegroApplicantDetailsDataframeLayout")
    }
    vueElintegroApplicantGeneralInformationDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantGeneralInformationDataframe']
        hql = "select application.id as Id,person.firstName as FirstName,person.lastName as LastName,person.email as Email,person.phone as Phone, application.availablePositions from Application application inner join application.applicant person where application.id=:applicationId"
        tab = true
        saveButton = false
        readonly = true
        initOnPageLoad = true
        dataframeLabelCode = """General.information"""
        putFillInitDataMethod = true
        doBeforeRefresh = """
                             excon.setValuesForNamedParamsFromGrid({'targetDataframe': 'vueElintegroApplicantGeneralInformationDataframe', 
                                                            'namedParamKey': 'applicationId', 
                                                            'sourceDataframe': 'vueElintegroApplicantsDataframe', 
                                                            'fieldName':'applicant',
                                                            'key': 'Id'});
                          """
        cssGridValues = ['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
        addFieldDef = ["person.firstName":["name":"firstName","type":"link","widget":"InputWidgetVue",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                       "person.lastName":["name":"lastName","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => (v && v.length <= 30)",message:"LastName.must.be.less.than.30"]],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                       "person.email":["name":"email","type":"link","widget":"EmailWidgetVue","validationRules":[[condition:"v => !!v", message: 'email.required.message']],attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                       "person.phone":[name: "phone",attr:"""outlined background-color='#EBF9FF !important' color='#2AB6F6'""", "validationRules":[[condition:"v => !!v", message: 'Phone.is.required']]],
                       "application.availablePositions":[widget: "ListWidgetVue"
                                                 ,hql:"select pos.id as id, pos.name as name from Position pos"
                                                 ,"displayMember":"name"
                                                 ,internationalize: true
                                                 ,valueMember:"id"
                                                 ,attr: """v-show = false """
                       ]
        ]
        dataframeButtons = [next: [name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantSelfAssessmentDataframe-tab-id");
                                                                                \n""","cssGridValues" :['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6'], url: ""]]

        currentFrameLayout = ref("vueElintegroApplicantGeneralInformationDataframeLayout")
    }
    vueElintegroApplicantSelfAssessmentDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantSelfAssessmentDataframe']
        tab = true
        saveButton = false
        doBeforeRefresh = """
                             excon.setValuesForNamedParamsFromGrid({'targetDataframe': 'vueElintegroApplicantSelfAssessmentDataframe', 
                                                            'namedParamKey': 'applicationId', 
                                                            'sourceDataframe': 'vueElintegroApplicantsDataframe', 
                                                            'fieldName':'applicant',
                                                            'key': 'Id'});
"""
        initOnPageLoad = true
        dataframeLabelCode = """Self.assessment"""
        putFillInitDataMethod = true
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        readonly = true
        addFieldDef =[
                "applicationSkill":[ widget          : "GridWidgetVue"

                                     ,name            : "applicationSkill"
                                     ,hql             : """select application.id as AppId,applicationSkill.id as Id, applicationSkill.skill as Skill,applicationSkill.level as Level, applicationSkill.comment as Comment from ApplicationSkill applicationSkill inner join applicationSkill.application application where application.id=:applicationId"""
                                     ,internationalize: true

                ]
        ]
        dataframeButtons = [next: [name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantCVDataframe-tab-id");
                                                                                \n""", "cssGridValues" : ['xs':'3', 'sm':'3', 'md':'6', 'lg':'6', 'xl':'6']],
                            previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantGeneralInformationDataframe-tab-id");
                                                                                \n""", "cssGridValues" : ['xs':'9', 'sm':'9', 'md':'6', 'lg':'6', 'xl':'6']]
        ]
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueElintegroApplicantCVDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantCVDataframe']
        tab = true
        saveButton = false
        initOnPageLoad = true
        putFillInitDataMethod = true
        dataframeLabelCode = """applicant.cv"""
        doBeforeRefresh = """
                             excon.setValuesForNamedParamsFromGrid({'targetDataframe': 'vueElintegroApplicantCVDataframe', 
                                                            'namedParamKey': 'applicationId', 
                                                            'sourceDataframe': 'vueElintegroApplicantsDataframe', 
                                                            'fieldName':'applicant',
                                                            'key': 'Id'});
"""
        doAfterRefresh = """self.afterRefreshing(response);"""
        hql = "select application.id as Id, files.fileName, images.name from Application application inner join application.files as files inner join application.images as images where application.id=:applicationId"
        addFieldDef = [
                "files.fileName":[
                                  name:"fileName"
                                 ,widget: "FilesDisplayWidgetVue"
                                 ,"aspectRatio":"1"
                                 ,"cssGridValues" : ['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
                                 ,"height":100
                                 ,"width":100],
                "images.name":[
                                "widget" : "PictureDisplayWidgetVue",
                                "aspectRatio":"2.5",
                                "attr": "contain",
                                "cssGridValues" : ['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6'],
                                "width":200,
                                "height":200]
                ]

        dataframeButtons = [next: [name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantQuestionAnswerDataframe-tab-id");
                                                                                \n""", "cssGridValues" : ['xs':'3', 'sm':'3', 'md':'6', 'lg':'6', 'xl':'6']],
                            previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantSelfAssessmentDataframe-tab-id");
                                                                                \n""", "cssGridValues" : ['xs':'9', 'sm':'9', 'md':'6', 'lg':'6', 'xl':'6']]
        ]
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueElintegroApplicantQuestionAnswerDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantQuestionAnswerDataframe']
        tab = true
        readonly = true
        initOnPageLoad = true
        dataframeLabelCode = """Questions.answers"""
        putFillInitDataMethod = true
        doBeforeRefresh = """
                             excon.setValuesForNamedParamsFromGrid({'targetDataframe': 'vueElintegroApplicantQuestionAnswerDataframe', 
                                                            'namedParamKey': 'applicationId', 
                                                            'sourceDataframe': 'vueElintegroApplicantsDataframe', 
                                                            'fieldName':'applicant',
                                                            'key': 'Id'});
"""
        saveButton = false
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        hql = "select application.id as Id, application.question1, application.question2 from Application application where application.id=:applicationId"
        addFieldDef = [
                "application.question1":[widget:"TextAreaWidgetVue"
                                         ,readOnly: true
                                         ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,],
                "application.question2":[widget:"TextAreaWidgetVue"
                                         ,readOnly: true
                                         ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,]
        ]
        dataframeButtons = [ next: [name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroCommentPageForApplicantDataframe-tab-id");
                                                                                \n""", "cssGridValues" : ['xs':'3', 'sm':'3', 'md':'6', 'lg':'6', 'xl':'6']],
                             previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantCVDataframe-tab-id");
                                                                                \n""", "cssGridValues" : ['xs':'9', 'sm':'9', 'md':'6', 'lg':'6', 'xl':'6']]
        ]
        currentFrameLayout = ref("vueElintegroApplicantQuestionAnswerDataframeLayout")
    }
    vueElintegroCommentPageForApplicantDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroCommentPageForApplicantDataframe']
        tab = true
        initOnPageLoad = true
        putFillInitDataMethod = true
        dataframeLabelCode = """Comment.page"""
        doBeforeRefresh = """
                             excon.setValuesForNamedParamsFromGrid({'targetDataframe': 'vueElintegroCommentPageForApplicantDataframe', 
                                                            'namedParamKey': 'applicationId', 
                                                            'sourceDataframe': 'vueElintegroApplicantsDataframe', 
                                                            'fieldName':'applicant',
                                                            'key': 'Id'});
"""
        saveButton = false
        hql="select application.id,application.comments,application.lastComment from Application application where application.id=:applicationId"
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        addFieldDef =[
                "application.comments":[ widget: "TextAreaWidgetVue",
                             name:"Comments",
                             readOnly: true,
                             attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                            ],
                "application.lastComment":[ widget:"TextAreaWidgetVue",
                                name: "Comment"
                                ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                ]
        ]
        dataframeButtons = [  save: [name:"save",type:"button",attr: """style='background-color:#1976D2; color:white;' """, script: """this.addCommentsForApplicant(); """ ,"cssGridValues" : ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']],
                              previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantQuestionAnswerDataframe-tab-id");
                                                                                \n""", "cssGridValues" : ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']]
        ]
        currentFrameLayout = ref("vueElintegroCommentPageForApplicantDataframeLayout")


    }
   /* vueAddressDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAddressDataframe']
        bean.autowire='byName'
        dataframeLabelCode = "Address.Information"
//        hql = "select address.id, address.addressLine, address.street from Address as address where address.id=:id"
        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip, address.longitude, address.latitude from Address as address where address.id=:id"
        doBeforeSave = """var domainKeys = excon.getFromStore('vueNewEmployeeBasicInformationDataframe','domain_keys');
                          params['personId'] = domainKeys.person.id """
        doAfterSave = "excon.goToTab('vueNewEmployeeApplicantDataframe','vueNewEmployeeUploadResumeDataframe');"
        ajaxSaveUrl = "applicationForm/saveAddress"
        cssGridValuesForSaveButton = ['xs':'4', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4']
        childDataframes =["vueMapWidgetDataframe"]
        deleteButton = false
        insertButton=false
        saveButton = true
        wrapInForm=false
        initOnPageLoad = false
        createStore = true
        addFieldDef = [
                "address.addressLine": [
                        "widget"   : "InputWidgetVue",
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                ],

                "address.addressLine2": [
                        "widget"   : "InputWidgetVue",
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                ],
                "address.postalZip": [
                        "widget"   : "InputWidgetVue"

                ],
                "validateWithGoogle":[
                        "widget"     : "ButtonWidgetVue",
                        insertAfter: "address.addressLine",
                        script       : """ this.updatedAddressValue = this.state.persisters.address.addressLine.value;""",
                        "cssGridValues" : ['xs':'4', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4'],
                ],
                "address.longitude":[
                        "widget"   : "InputWidgetVue"
                ],
                "address.latitude":[
                        "widget"   : "InputWidgetVue"
                ],
                "googleMap": [
                        "widget"      : "DataframeWidgetVue",
                        dataframe     : ref("vueMapWidgetDataframe"),
                        "attr"        :" @resultData='updateAddressFields'",
                        props      :[key:":addressValue", value:"updatedAddressValue"],
                        passValueAsProp : true,
                        "showInMap"   :true,
                        "name"        : "googleMap",
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        "height"      :'500px'

                ]
        ]
        dataframeButtons = [ previous: [name:"previous", type: "button",script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeBasicInformationDataframe-tab-id");\n""" , "cssGridValues" : ['xs':'4', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4'], url: ""] ]

        currentFrameLayout = ref("vueAddressDataframeLayout")

    }
        vueMapWidgetDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMapWidgetDataframe']
        initOnPageLoad = false
        saveButton = false
        addFieldDef = [
                "googleMap": [
                        "widget"   : "MapWidgetVue",
                        "showInMap":true,
                        "name"     : "googleMap",
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        "height"   :'500px'

                ]
        ]

        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vueAddMapWidgetDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAddMapWidgetDataframe']
        initOnPageLoad = true
        saveButton = false
        route = true
        doAfterRefresh="""self.initializeMaps();\n"""
        addFieldDef = [
                "googleMap": [
                        "widget" : "MapDisplayWidgetVue",
                        "hql":"select address.id as Id, address.addressLine as AddressLine, address.longitude as Longitude, address.latitude as Latitude from Address as address ",
                        "showInMap":true,
                        "name" : "googleMap",
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        "height" :'500px',
                        "initBeforePageLoad":true,
                        "displayMember":"AddressLine",

                ]
        ]

        currentFrameLayout = ref("defaultDataframeLayout")
    }*/
}