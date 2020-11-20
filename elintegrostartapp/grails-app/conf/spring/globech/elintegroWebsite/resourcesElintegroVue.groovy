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
    vueElintegroProgressBarDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ["vueElintegroProgressBarDataframe"]
        saveButton = false
        wrapInForm=false
        initOnPageLoad = false
        isGlobal = true
        currentFrameLayout = ref("vueElintegroProgressBarLayout")

    }
    vueElintegroNavigationFirstTwoButtonDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationFirstTwoButtonDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [
                             home : [name: "home", type: "link",attr:"style='color:#1976D2;'",route: true,routeIdScript: "0", refDataframe: ref("vueElintegroHomeDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        currentFrameLayout = ref("elintegroNavigationButtonLayout")
    }
    vueElintegroNavigationButtonBeforeLoggedInDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroNavigationButtonBeforeLoggedInDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = true
        dataframeButtons = [register       : [name: "register", type: "link", showAsDialog: true, attr:"style='color:#1976D2;'",
                                              refDataframe: ref("vueElintegroRegisterDataframe"), tooltip: [message: 'Register'], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            login          : [name: "login", type: "link",showAsDialog: true,attr:"style='color:#1976D2;'",
                                              refDataframe: ref("vueElintegroLoginDataframe"), tooltip: [message: 'Login'], "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            contactUs      : [name: "contactUs", type: "link",attr:"style='color:#1976D2;'",route: true,routeIdScript: "0", refDataframe: ref("vueContactUsPageDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            careers        : [name: "careers", type: "link",attr:"style='color:#1976D2;'",route: true,routeIdScript: "0", refDataframe: ref("vueCareersDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
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
                        ,"flexGridValues":['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                        , hql: """select language.id as id,language.code as code, language.ename as ename from Language as language where language.code in (${languageCode})"""
                        ,"displayMember":"ename"
                        ,"valueMember":"ename"
                        , search:true
                        ,attr: """style='max-width:min-content;margin-top=-2%;'"""
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
        dataframeButtons = [myProfile       : [name: "profile", type: "link",attr:"style='color:#1976D2;'", showAsDialog: true,refDataframe: ref("vueElintegroProfileMenuDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            contactUs      : [name: "contactUs", type: "link",attr:"style='color:#1976D2;'",route: true,routeIdScript: "0", refDataframe: ref("vueContactUsPageDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            applicants     : [name: "applicants", type: "link",attr:"style='color:#1976D2;'",route: true, routeIdScript: "0", refDataframe: ref("vueElintegroApplicantsDataframe"), roles: "ROLE_ADMIN", accessType: "ifAnyGranted", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            careers        : [name: "careers", type: "link",attr:"style='color:#1976D2;'",route: true,routeIdScript: "0", refDataframe: ref("vueCareersDataframe"), "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
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
                        "url"         : "assets/home/logo.jpg",
                        flexGridValues: ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "attr"        : " contain ",
                        "height"      : "auto",
                        "width"       : "200",
                        //"min-width"   : "40"

                ]

        ]
        currentFrameLayout = ref("vueElintegroLogoDataframeLayout")

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
                        ,onButtonClick : [
                        ['actionName': 'Edit client Project', 'buttons': [
                                [name : "edit"
                                 ,MaxWidth: 500
                                 ,showAsDialog: true
                                 ,tooltip : [message: "tooltip.grid.edit", internationalization: true]
                                 ,refDataframe: ref("clientProjectEditDataframe")
                                 ,vuetifyIcon : [name: "edit"]
                                 ,refreshInitialData:true
                                ]]]]

                ]
        ]
        childDataframes = ['clientProjectEditDataframe']
        currentFrameLayout = ref("clientProjectPageDataframeLayout")

    }
    clientProjectEditDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['clientProjectEditDataframe']
        hql = """select clientProject.id, clientProject.clientName, clientProject.projectName, clientProject.logo, clientProject.description, clientProject.linkToWebsite from ClientProject clientProject where clientProject.id=:id"""

        initOnPageLoad = true
        putFillInitDataMethod = true
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        saveButton = true
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']

        doAfterSave = """
excon.refreshDataForGrid(response,'vueClientProjectDataframe', 'clientProject', 'U', "transits");
"""

        doBeforeSave = """
"""
        doBeforeRefresh=""""""
        currentFrameLayout = ref("defaultDataframeLayout")

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
        dataframeButtons = [contactQuiz:[name: "contactQuiz", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('quiz_placeholder');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            technologiesInUse:[name: "technologiesInUse", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('our_Technologies');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ourClientsProjects:[name: "ourClientsProjects", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('ourClientsProjects');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            testimonials:[name:"testimonials",type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('Quotes')""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ourFramework:[name: "ourFramework", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('our_framework');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            collaboration:[name: "collaboration", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('collaboration');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ourProcess  : [name: "ourProcess", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",script: """this.scrollTo('our_process');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ourWork  : [name: "ourWork", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('our_work');""" , "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]

        currentFrameLayout = ref("vueElintegroAboutUsMenuDataframeLayout")

    }

    vueElintegroSubMenuDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroSubMenuDataframe']
        isGlobal = true
        saveButton= false
        initOnPageLoad = false
        dataframeButtons = [
//                quizzable  : [name: "quizzable", type: "link",attr:"style='color:#1976D2;'",script: """this.quizzableApp();""", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            translator  : [name: "translator", type: "link",attr:"style='color:#1976D2;margin-top:-15px;'",route: true,routeIdScript: 0, refDataframe: ref("vueTranslatorAssistantDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ecommerce  : [name: "ecommerce", type: "link",attr:"style='color:#1976D2;'",route: true,routeIdScript: 0,script: """this.ecommerceApp();""", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]

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
        childDataframes = ['vueNewEmployeeBasicInformationDataframe','vueNewEmployeeUploadResumeDataframe','vueNewEmployeeSelfAssesmentDataframe','vueNewEmployeeAddtionalQuestionsDataframe']
        currentFrameLayout = ref("vueNewEmployeeApplicantDataframeLayout")
    }
    vueNewEmployeeBasicInformationDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeBasicInformationDataframe']
        hql = "select Person.firstName, Person.lastName, Person.email, Person.phone, application.linkedin, application.availablePositions from Application application inner join application.applicant Person where application.id=:id"
        initOnPageLoad = false
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        saveButton = true
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']

        doAfterSave = """
                                 
                        excon.goToTab("vueNewEmployeeApplicantDataframe", "vueAddressDataframe");
                      """

        doBeforeSave = """
                       """

        addFieldDef = [

                   "Person.phone":["name":"phone","type":"link","widget":"PhoneNumberWidgetVue",validate: true],
                    "application.availablePositions"  :[
                        "widget"             :"ComboboxVue"
                        ,internationalize    :true
                        ,initBeforePageLoad  :true
                        ,multiple            :true
                        ,"hql"               : "select position.id as id, position.name as name from Position as position"
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                        , search:true

                ],


        ]

        currentFrameLayout = ref("vueNewEmployeeBasicInformationDataframeLayout")

    }
    vueAddressDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAddressDataframe']
        bean.autowire='byName'

        dataframeLabelCode = "Address.Information"
//		hql = "select address.id, address.addressLine, address.street from Address as address where address.id=:id"
        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip from Address as address where address.id=:id"
        doBeforeSave = """var domainKeys = excon.getFromStore('vueNewEmployeeBasicInformationDataframe','domain_keys');
                          params['personId'] = domainKeys.Person.id """
        doAfterSave = "excon.goToTab('vueNewEmployeeApplicantDataframe','vueNewEmployeeUploadResumeDataframe');"

        childDataframes =["vueMapWidgetDataframe"]
        ajaxSaveUrl = "applicationForm/saveAddress"
        flexGridValuesForSaveButton = ['xs4', 'sm4', 'md4', 'lg4', 'xl4']
        deleteButton = false
        insertButton=false
        saveButton = true
        wrapInForm=false
        initOnPageLoad = false
        createStore = true
        addFieldDef = [
                "address.addressLine": [
                        "widget"   : "InputWidgetVue",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],

                ],

                "address.addressLine2": [
                        "widget"   : "InputWidgetVue",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],

                ],
                "address.postalZip": [
                        "widget"   : "InputWidgetVue"

                ],
                "validateWithGoogle":[
                        "widget"     : "ButtonWidgetVue",
                        insertAfter: "address.addressLine",
                        script       : """ this.updatedAddressValue = this.state.persisters.address.addressLine.value;""",
                        "flexGridValues":['xs4', 'sm4', 'md4', 'lg4', 'xl4'],
                ],
                "googleMap": [
                        "widget"      : "DataframeWidgetVue",
                        dataframe     : ref("vueMapWidgetDataframe"),
                        "attr"        :" @resultData='updateAddressFields'",
                        props      :[key:":addressValue", value:"updatedAddressValue"],
                        passValueAsProp : true,
                        "showInMap"   :true,
                        "name"        : "googleMap",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "height"      :'500px'

                ]
        ]
        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueApplicationFormDataframe, "vueApplicationFormDataframe_tab_model","vueApplicationFormDataframe-tab-id");\n""", flexGridValues: ['xs4', 'sm4', 'md4', 'lg4', 'xl4'], url: ""] ]

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
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "height"   :'500px'

                ]
        ]

        currentFrameLayout = ref("defaultDataframeLayout")
    }
    vueNewEmployeeUploadResumeDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeUploadResumeDataframe']
        initOnPageLoad = false
        hql = "select application.id, application.images,  application.files from Application application where application.id=:id"
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        saveButton = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        flexGridValuesForSaveButton =['xs3', 'sm3', 'md6', 'lg6', 'xl6']
        tab = true
        isGlobal = false
        //Was in >>>>>>> EWEB-68-refactoring
        //isGlobal = true
        doBeforeSave = """
            //Take key fields values from previous dataframe and apply them for the key field of this dataframe to update the record, rather then insert a new one.                          
            excon.matchKeysFromDataframeTo("vueNewEmployeeBasicInformationDataframe","vueNewEmployeeUploadResumeDataframe");
              const applicantId = excon.getFromStore("vueNewEmployeeBasicInformationDataframe", "domain_keys.application.id")  
              params.persisters.application.id.value = applicantId;
              params.namedParameters.id.value = applicantId;
              params.domain_keys.application.id = applicantId;    
        """
        doAfterSave = """
                         excon.goToTab("vueNewEmployeeApplicantDataframe", "vueNewEmployeeSelfAssesmentDataframe");
                      """
        addFieldDef = [
                "application.images":["name":"images"
                                      ,"widget":"PictureUploadWidgetVue"
                                      ,ajaxFileSaveUrl: "fileUpload/ajaxFileSave"
                                      ,multiple:true
                                      ,editButton: true
                                      ,deleteButton:true  ],

                "application.files":["name":"files"
                                      ,"widget":"FilesUploadWidgetVue"
                                      , ajaxFileSaveUrl: "fileUpload/ajaxFileSave"
                                      ,multiple:true
                                      ,"accept":"image/*,.pdf,.docx,.doc"

                                     ]
        ]

        dataframeButtons = [ previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeBasicInformationDataframe-tab-id");\n""",
                                        flexGridValues: ['xs9', 'sm9', 'md6', 'lg6', 'xl6'],url: ""]]
//                             next:[name:"next", type: "button",script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeSelfAssesmentDataframe-tab-id");\n""",
//                                   flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'],url: ""]]

        currentFrameLayout = ref("vueNewEmployeeUploadResumeDataframeLayout")
    }
    vueNewEmployeeSelfAssesmentDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeSelfAssesmentDataframe']
        initOnPageLoad = false
        putFillInitDataMethod = true
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        tab = true
        saveButton = false
        flexGridValuesForSaveButton =['xs6', 'sm6', 'md6', 'lg6', 'xl6']
        doBeforeRefresh = """params['id']= excon.getFromStore('vueNewEmployeeUploadResumeDataframe','key_vueNewEmployeeUploadResumeDataframe_application_id_id');
                             """
        doAfterSave = """
                         excon.saveToStore("vueNewEmployeeAddtionalQuestionsDataframe","key", response.nodeId[0]);
                         excon.saveToStore('vueNewEmployeeApplicantDataframe','vueNewEmployeeApplicantDataframe_tab_model','vueNewEmployeeAddtionalQuestionsDataframe-tab-id');"""
        addFieldDef =[
                "applicationSkill":[ widget          : "GridWidgetVue"

                                    ,name            : "applicationSkill"
                                    ,hql             : """select application.id as AppId,applicationSkill.id as Id, applicationSkill.skill as Skill,applicationSkill.level as Level, applicationSkill.comment as Comment from ApplicationSkill applicationSkill inner join applicationSkill.application application where application.id=:id"""
                                    ,internationalize: true
                                    ,onClick :[showAsDialog: true, refDataframe: ref("vueNewEmployeeApplicantEditSkillDataframe"),]
                                    ,editButton: true
                                    ,onButtonClick   : [
                                                ['actionName': 'Edit Skill', 'buttons': [
                                                        [name        : "edit"
                                                         ,MaxWidth: 500
                                                        ,showAsDialog: true
                                                        ,tooltip     : [message: "tooltip.grid.edit", internationalization: true]
                                                        ,refDataframe: ref("vueNewEmployeeApplicantEditSkillDataframe")
                                                        ,vuetifyIcon : [name: "edit"]
                                                        ]]]]
]
        ]
        dataframeButtons = [
                next:[name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"excon.saveToStore('vueNewEmployeeApplicantDataframe','vueNewEmployeeApplicantDataframe_tab_model','vueNewEmployeeAddtionalQuestionsDataframe-tab-id');",flexGridValues: ['xs4', 'sm4', 'md4', 'lg4', 'xl4'], url:""],
                addSkill:[name:"addSkill",type:"button",attr: """style='background-color:#1976D2; color:white;' """,showAsDialog:true,refDataframe: ref("vueNewEmployeeApplicantAddSkillDataframe"),flexGridValues: ['xs4', 'sm4', 'md4', 'lg4', 'xl4']],
                previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeUploadResumeDataframe-tab-id");
                                                                                \n""",flexGridValues: ['xs4', 'sm4', 'md4', 'lg4', 'xl4'], url: ""]
        ]
        childDataframes = ['vueNewEmployeeApplicantEditSkillDataframe','vueNewEmployeeApplicantAddSkillDataframe']

        currentFrameLayout = ref("vueNewEmployeeSelfAssesmentDataframeLayout")
    }
    vueNewEmployeeApplicantEditSkillDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeApplicantEditSkillDataframe']
        saveButton = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        initOnPageLoad = true
        putFillInitDataMethod = true
        doBeforeRefresh = """params['id']= this.vueNewEmployeeApplicantEditSkillDataframe_prop.key"""
        doBeforeSave = """params['key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id'] = this.vueNewEmployeeApplicantEditSkillDataframe_prop.key"""
        doAfterSave = """ excon.setVisibility("vueNewEmployeeApplicantEditSkillDataframe", false);
                          excon.refreshDataForGrid(response,'vueNewEmployeeSelfAssesmentDataframe', 'vueNewEmployeeSelfAssesmentDataframe_applicationSkill', 'U');
                      """
        hql = "select applicationSkill.id as Id, applicationSkill.skill as Skill, applicationSkill.level as Level, applicationSkill.comment as Comment  from ApplicationSkill applicationSkill where applicationSkill.id=:id"
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        addFieldDef = ["applicationSkill.skill":[readOnly: true],"applicationSkill.level":["max":10,  "validationRules":[[condition:"v => (v && new RegExp('^([0-9]|1[0])\$').test(v))",message:"digits.not.valid"]], script: """setTimeout(function(){ alert("Hello"); }, 5000);"""]]
        currentFrameLayout = ref("vueNewEmployeeApplicantEditSkillDataframeLayout")
    }
    vueNewEmployeeApplicantAddSkillDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeApplicantAddSkillDataframe']
        saveButton = false
        initOnPageLoad = true
        putFillInitDataMethod = true
        hql = "select applicationSkill.id as Id, applicationSkill.skill as Skill,applicationSkill.level as Level, applicationSkill.comment as Comment from ApplicationSkill applicationSkill inner join applicationSkill.application application where application.id=:id"
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        addFieldDef = ["applicationSkill.level":["max":10,  "validationRules":[[condition:"v => (v && new RegExp('^([0-9]|1[0])\$').test(v))",message:"digits.not.valid"]]]]
        dataframeButtons = [save: [name:"save", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""this.addNewSkill();""",flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'],url: ""]]
        currentFrameLayout = ref("vueNewEmployeeApplicantAddSkillDataframeLayout")
    }

    vueNewEmployeeAddtionalQuestionsDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeAddtionalQuestionsDataframe']
        initOnPageLoad = false
        doBeforeSave = """params['key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe','key_vueNewEmployeeUploadResumeDataframe_application_id_id');"""
        hql = "select application.id as Id, application.question1, application.question2 from Application application where application.id=:id"
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        saveButton = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        tab = true
        flexGridValuesForSaveButton =['xs3', 'sm3', 'md6', 'lg6', 'xl6']
        doAfterSave = """self.\$router.push("/vueNewEmployeeThankYouMessageAfterSaveDataframe/0");"""
        dataframeButtons = [
                previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeSelfAssesmentDataframe-tab-id");\n""",
                           flexGridValues: ['xs9', 'sm9', 'md6', 'lg6', 'xl6'],url: ""]]

        currentFrameLayout = ref("vueNewEmployeeAddtionalQuestionsDataframeLayout")
    }
    vueNewEmployeeThankYouMessageAfterSaveDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeThankYouMessageAfterSaveDataframe']
        saveButton = false
        route = true
        doBeforeRefresh= """params['id'] = excon.getFromStore('vueNewEmployeeAddtionalQuestionsDataframe','key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id');"""
        hql = "select person.firstName, person.lastName from Application application inner join application.applicant person where application.id=:id"
        currentFrameLayout = ref("vueNewEmployeeThankYouMessageAfterSaveDataframeLayout")

    }



    vueContactUsPageDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactUsPageDataframe']
        dataframeLabelCode = "Contact Us"
        hql = "select contactUs.name , contactUs.email,contactUs.phone,contactUs.textOfMessage from ContactUs contactUs where contactUs.id=:id"
        isGlobal = true
        saveButton = true
        saveButtonAttr = """ style='background-color:#1976D2; color:white;' """
        initOnPageLoad = false
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        route = true
        currentRoute = 'contact-us'
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        addFieldDef = [
                "contactUs.phone":[name:"phone",widget: "PhoneNumberWidgetVue",validate: true]]

        //  dataframeButtons = [Submit: [name: "submit", type: "link", url:"ElintegroWebsite/ContactUs","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]


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

        addFieldDef = ["user.password":["widget" : "PasswordWidgetVue", "name": "user.password", autoComplete:"on", "width":150]
                       ,"user.username":["widget" : "EmailWidgetVue",  "name": "user.username", autoComplete:"on", "width":150, "errMessage":"Username should be an email"]
                       ,"rememberMe":["widget" : "CheckboxWidgetVue", height : '30px']
        ]

        dataframeButtons = [ login:[name:"login", type: "button", layout: "<v-flex xs12 sm12 md6 lg6 xl6 pa-0>[BUTTON_SCRIPT]</v-flex>", attr: """color='blue darken-2' dark style="width: 10px; margin-left:65px;" """,
                script:"this.login();",
                                                 "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                             forgetPassword:[name: "forgetPassword", type: "button", attr:"""style="background-color:#1976D2; color:white; margin-left:2px;" """, script:""" console.log("Forget Password Clicked");""", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'],
                                             layout: "<v-flex xs12 sm12 md6 lg6 xl6 style='margin-bottom:10px;'><v-layout column align-start justify-center>[BUTTON_SCRIPT]</v-layout></v-flex>"],
                             logInWithGoogle:[name: "logInWithGoogle", type: "image", attr:"style='margin-left:-3px;'", image:[url: "vueLoginDataframe.button.logInWithGoogle.imageUrl", width:'135px', height: '48px'], script:"""
//                                                                                             var url = "/elintegrostartapp/oauth/authenticate/google";
                                                                                             var url = "springSecurityOAuth2/authenticate?provider=google";
                                                                                             var childWindow = window.open(url, "payment",  "width=500,height=500");
                                                                                             /*if(childWindow){
                                                                                                window.opener.location.reload();
                                                                                                close();
                                                                                             }*/
                                                                                              """, "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                             logInWithFacebook:[name: "logInWithFacebook", type: "image", attr: "style=\"margin-top:3px;\"", image:[url: "vueLoginDataframe.button.logInWithFacebook.imageUrl", width: '135px', height: '43px'],script:"""
                                                                                             var provider = 'facebook';
                                                                                             var url = "springSecurityOAuth2/authenticate?provider="+provider+"";
                                                                                             var childWindow = window.open(url, "payment",  "width=500,height=500");
                                                                                              """, "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']]

        ]

        currentFrameLayout = ref("vueElintegroLoginDataframeLayout")
    }
    vueElintegroForgetPasswordDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroForgetPasswordDataframe']
        hql = "select user.email from User as user where user.id=:id"
        dataframeLabelCode = "Forget.Password"
        initOnPageLoad = false
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        saveButton = false
        currentRoute = 'forget-password'
        route = true
        isGlobal = true
        doAfterRefresh = """excon.setVisibility('vueElintegroLoginDataframe',false);"""
        addFieldDef =[
                "user.email":[widget: "EmailWidgetVue",attr: "autofocus", "placeHolder":"Enter your email","validationRules":[[condition:"v => !!v", message: 'email.required.message']]],
        ]
        dataframeButtons = [submit: [name: "submit", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script: """this.forgotPassword();""", "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']]]
        currentFrameLayout = ref("vueElintegroForgetPasswordDataframeLayout")
    }
    vueElintegroChangeForgotPasswordDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroChangeForgotPasswordDataframe']
        dataframeLabelCode = "Forget.Password"
        initOnPageLoad = false
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        saveButton = false
        currentRoute = 'change-forget-password'
        route = true
        isGlobal = true
        addFieldDef =[
                "newPassword":[name:"newPassword"
                               ,widget:"PasswordWidgetVue"
                               ,"validationRules":[[condition: "v => !!v ",message:"Password.required.message"],[condition:"v => (v && new RegExp('^(?=.*?[#?!@%^&*-])').test(v))",message:"password.contain.special.character"]
                                                   ,[condition:"v => (v && v.length >= 8)",message:"Password.must.be.greater.than.8"]]],
                "confirmPassword":[name:"confirmPassword"
                                   ,widget:"PasswordWidgetVue"
                                   , "insertAfter":"newPassword"
                                   ,"validationRules":[[condition:"v => !!(v==this.state.vueElintegroChangeForgotPasswordDataframe_newPassword)",message:"Password.and.Confirm.Password."]]],
        ]
        dataframeButtons = [submit: [name: "submit", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script: """this.changeForgotPassword();""", "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']]]
        currentFrameLayout = ref("vueElintegroForgetPasswordDataframeLayout")
    }


    vueElintegroRegisterDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroRegisterDataframe']

        hql = "select user.email, user.password, user.firstName, user.lastName from User as user where user.id=:id"

        ajaxSaveUrl = "register/register"

        dataframeLabelCode = "User.Registration"
        //These are values, that overrides the default ones
        saveButtonAttr = " color='blue darken-2' dark"
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
                              ,"validationRules":[[condition:"v => !!(v == excon.getStateDataframeFieldValue(this, 'user', 'password') )",message:"Password.and.Confirm.Password."]]]
        ]

        currentFrameLayout = ref("vueElintegroRegisterDataframeLayout")
    }
    vueElintegroProfileMenuDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroProfileMenuDataframe']
        hql = "select person.id, person.firstName, person.lastName,person.email, person.mainPicture from Person person where person.user = :session_userid"
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        createStore = true
        isGlobal = true
        saveButton = false
        initOnPageLoad=true
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
        dataframeButtons = [Logout     : [name: "logout", type: "button",attr: """style='background-color:#1976D2; color:white;' """, url: "logoff", "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'], script: "", callBackParams: [failureScript: """vueElintegroProfileMenuDataframeVar.\$router.push("/");this.location.reload();"""]],
                            editProfile: [name: 'editProfile', type: "button",attr: """style='background-color:#1976D2; color:white;' """,showAsDialog: false, "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'], route: true, routeIdScript: "this.state.persisters.person.id.value;", refDataframe: ref('vueElintegroUserProfileDataframe')]]
        currentFrameLayout = ref("vueElintegroProfileMenuDataframeLayout")
    }
    vueElintegroUserProfileDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroUserProfileDataframe']

        dataframeLabelCode = "User.Profile"
        hql = "select person.id, person.mainPicture,person.email, person.firstName, person.lastName, person.bday, person.phone, language.ename, address.addressText from Person as person inner join person.languages language inner join person.addresses address where person.id=:id"
//        hql = "select person.id, person.mainPicture,person.email, person.firstName, person.lastName, person.bday, person.phone, person.languages from Person as person where person.id=:id"
        saveButton = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
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
                "language.ename":[
                        widget: "ComboboxVue"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,"valueMember":"id"
                        , search:true
                        ,multiple: true
                ],

                "address.addressText":[
                        widget: "ListWidgetVue"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']
                        , hql: """select address.id as id, address.addressText as addres from Address as address"""
                        ,"displayMember":"addres"
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

                "uploadPicture":[
                        "widget" : "PictureUploadWidgetVue"
                        , valueMember: "mainPicture"
                        ,ajaxFileSaveUrl: "fileUpload/ajaxFileSave"
                        ,insertAfter: "person.mainPicture"
                        ,multiple:false
                        ,editButton: true
                        ,deleteButton:true
                ]
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
        dataframeButtons = [ resetPassword: [name:"resetPassword", type: "button",attr: """style='background-color:#1976D2; color:white;' """, url: "", showAsDialog: true, "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'], refDataframe: ref("vueElintegroResetPasswordDataframe")] ]

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
        childDataframes = ["vueElintegroApplicantGeneralInformationDataframe","vueElintegroApplicantSelfAssessmentDataframe","vueElintegroApplicantCVDataframe","vueElintegroApplicantQuestionAnswerDataframe","vueElintegroCommentPageForApplicantDataframe"]
        currentFrameLayout = ref("vueElintegroApplicantDetailsDataframeLayout")
    }
    vueElintegroApplicantGeneralInformationDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantGeneralInformationDataframe']
        hql = "select application.id as Id,person.firstName as FirstName,person.lastName as LastName,person.email as Email,person.phone as Phone from Application application inner join application.applicant person where application.id=:id"
        tab = true
        saveButton = false
        readonly = true
        initOnPageLoad = true
        putFillInitDataMethod = true
        doBeforeRefresh = """allParams['id'] = this.vueElintegroApplicantGeneralInformationDataframe_prop.key """
        flexGridValues = ['xs12', 'sm6', 'md6', 'lg6', 'xl6']
        addFieldDef = ["person.phone":[name: "phone","validationRules":[[condition:"v => !!v", message: 'Phone.is.required']]],
                       "person.selectedPosition":[widget: "ListWidgetVue"
                                                 ,hql:"select application.id as Id, availablePositions.name as Name from Application application  inner join application.availablePositions as availablePositions where application.id=:id"
                                                 ,"displayMember":"Name"
                                                 ,internationalize: true
                                                 ,valueMember:"id"
                                                 ,attr: """v-show = false """
                       ]
        ]
        dataframeButtons = [next: [name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantSelfAssessmentDataframe-tab-id");
                                                                                \n""",flexGridValues: ['xs6', 'sm6', 'md6', 'lg6', 'xl6'], url: ""]]

        currentFrameLayout = ref("vueElintegroApplicantGeneralInformationDataframeLayout")
    }
    vueElintegroApplicantSelfAssessmentDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantSelfAssessmentDataframe']
        tab = true
        saveButton = false
        doBeforeRefresh = """allParams['id'] = this.vueElintegroApplicantSelfAssessmentDataframe_prop.key"""
        initOnPageLoad = true
        putFillInitDataMethod = true
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        readonly = true
        addFieldDef =[
                "applicationSkill":[ widget          : "GridWidgetVue"

                                     ,name            : "applicationSkill"
                                     ,hql             : """select application.id as AppId,applicationSkill.id as Id, applicationSkill.skill as Skill,applicationSkill.level as Level, applicationSkill.comment as Comment from ApplicationSkill applicationSkill inner join applicationSkill.application application where application.id=:id"""
                                     ,internationalize: true

                ]
        ]
        dataframeButtons = [next: [name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantCVDataframe-tab-id");
                                                                                \n""", flexGridValues:['xs3', 'sm3', 'md6', 'lg6', 'xl6']],
                            previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantGeneralInformationDataframe-tab-id");
                                                                                \n""", flexGridValues:['xs9', 'sm9', 'md6', 'lg6', 'xl6']]
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
        doBeforeRefresh = """allParams['id'] = this.vueElintegroApplicantCVDataframe_prop.key"""
        doAfterRefresh = """self.afterRefreshing(response);"""
        hql = "select application.id as Id, files.fileName, images.name from Application application inner join application.files as files inner join application.images as images where application.id=:id"
        addFieldDef = [
                "files.fileName":[
                                  name:"fileName"
                                 ,widget: "FilesDisplayWidgetVue"
                                 ,"aspectRatio":"1"
                                 ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6']
                                 ,"height":100
                                 ,"width":100],
                "images.name":[
                                "widget" : "PictureDisplayWidgetVue",
                                "aspectRatio":"2.5",
                                "attr": "contain",
                                "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'],
                                "width":200,
                                "height":200]
                ]

        dataframeButtons = [next: [name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantQuestionAnswerDataframe-tab-id");
                                                                                \n""", flexGridValues:['xs3', 'sm3', 'md6', 'lg6', 'xl6']],
                            previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantSelfAssessmentDataframe-tab-id");
                                                                                \n""", flexGridValues: ['xs9', 'sm9', 'md6', 'lg6', 'xl6']]
        ]
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueElintegroApplicantQuestionAnswerDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroApplicantQuestionAnswerDataframe']
        tab = true
        readonly = true
        initOnPageLoad = true
        putFillInitDataMethod = true
        doBeforeRefresh = """allParams['id'] = this.vueElintegroApplicantQuestionAnswerDataframe_prop.key"""
        saveButton = false
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        hql = "select application.id as Id, application.question1, application.question2 from Application application where application.id=:id"
        dataframeButtons = [ next: [name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroCommentPageForApplicantDataframe-tab-id");
                                                                                \n""", flexGridValues:['xs3', 'sm3', 'md6', 'lg6', 'xl6']],
                             previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """, script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantCVDataframe-tab-id");
                                                                                \n""", flexGridValues: ['xs9', 'sm9', 'md6', 'lg6', 'xl6']]
        ]
        currentFrameLayout = ref("vueElintegroApplicantQuestionAnswerDataframeLayout")
    }
    vueElintegroCommentPageForApplicantDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroCommentPageForApplicantDataframe']
        tab = true
        initOnPageLoad = true
        putFillInitDataMethod = true
        doBeforeRefresh = """allParams['id'] = this.vueElintegroCommentPageForApplicantDataframe_prop.key"""
        saveButton = false
        hql="select application.id,application.comments,application.lastComment from Application application where application.id=:id"
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']

        addFieldDef =[
                "application.comments":[ widget: "TextAreaWidgetVue",
                             name:"Comments",
                             readOnly: true,



                            ],
                "application.lastComment":[ widget:"TextAreaWidgetVue",
                                name: "Comment"]
        ]
        dataframeButtons = [  save: [name:"save",type:"button",script: """this.addCommentsForApplicant(); """ ,flexGridValues: ['xs6', 'sm6', 'md6', 'lg6', 'xl6']],
                              previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model","vueElintegroApplicantQuestionAnswerDataframe-tab-id");
                                                                                \n""", flexGridValues: ['xs6', 'sm6', 'md6', 'lg6', 'xl6']]
        ]
        currentFrameLayout = ref("vueElintegroCommentPageForApplicantDataframeLayout")


    }
}
