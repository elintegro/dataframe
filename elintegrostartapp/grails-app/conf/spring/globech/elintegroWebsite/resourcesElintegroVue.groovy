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
                             home           : [name: "home", type: "link",attr:"style='color:#1976D2;'",route: true,routeIdScript: "0", refDataframe: ref("vueElintegroHomeDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
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
                        "url"         : "${contextPath}/assets/elintegro_logo.png",
                        flexGridValues: ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "attr"        : " contain ",
                        "height"      : "auto",
                        "width"       : "200",
                        //"min-width"   : "40"

                ]

        ]
        currentFrameLayout = ref("vueElintegroLogoDataframeLayout")

    }
    vueElintegroHomeDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroHomeDataframe']
        isGlobal = true
        saveButton = false
        initOnPageLoad = false
        route = true
        currentRoute = 'home'
        childDataframes=['vueFirstContainerDataframe','vueOurWorkContainerDataframe','vueOurProcessContainerDataframe',
                         "vueCollaborationContainerDataframe",'vueOurFrameworkContainerDataframe',
                         'vueQuotesContainerDataframe','vueOurTechnologiesContainerDataframe',
                         'vueQuizPlaceholderContainerDataframe','vueFooterContainerDataframe']
        currentFrameLayout = ref("vueElintegroHomeDataframeLayout")

    }
    vueFirstContainerDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueFirstContainerDataframe']
        saveButton = false
        doAfterRefresh = """self.displayText();"""
        addFieldDef = [
                        'hey':[ "widget":"TextDisplayWidgetVue"
                                ,"name":"hey"
                                ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                                ],
                        'LooksLike':["widget":"TextDisplayWidgetVue"
                                     ,"name":"LooksLike"
                                     ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                                     ],
                        'Built':["widget":"TextDisplayWidgetVue"
                                 ,"name":"Built"
                                 ,elementId: 'text'
                                 ,attr: """ style='color:#29b6f6;' """
                                 ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                                 ],
                        "buildsData":[
                                "widget":"TextDisplayWidgetVue"
                                ,name:"buildsData"
                                ,attr: """v-show = false"""
                                ,elementId:'buildsData'
                                ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                        ],
                        "AnyApps":["widget":"TextDisplayWidgetVue"
                                   ,"name":"AnyApps"
                                   ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                                   ],
                        "Yet":["widget":"TextDisplayWidgetVue"
                               ,"name":"Yet"
                               ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                               ],
                        "youWont":["widget":"TextDisplayWidgetVue"
                                            ,"name":"youWont"
                                            ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                                            ,attr: """ style ='color: black; font-size: 27px;font-family:sans-serif;' """
                                    ],
                        "buildApp":["widget":"ButtonWidgetVue"
                                    ,"name":"buildApp"
                                    ,attr: """ style="background-color: #29b6f6; color:white;font-family:sans-serif; font-size:inherit; padding-top:28px; padding-bottom:28px; padding-right:35px; padding-left:35px;" """
                                    ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                                    ,script: """ this.scrollToQuiz('coachClone');"""
                                    ]

        ]
        childDataframes = ['vueFirstContainerResizeDataframe']
        currentFrameLayout = ref('vueFirstContainerDataframeLayout')

    }
    vueFirstContainerResizeDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueFirstContainerResizeDataframe']
        saveButton = false
        doAfterRefresh = """self.displayTextResize();"""
        addFieldDef = [
                'hey':[ "widget":"TextDisplayWidgetVue"
                        ,"name":"hey"
                        ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                'LooksLike':["widget":"TextDisplayWidgetVue"
                             ,"name":"LooksLike"
                             ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                'Built':["widget":"TextDisplayWidgetVue"
                         ,"name":"Built"
                         ,elementId: 'textResize'
                         ,attr: """ style='color:#29b6f6;' """
                         ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                "buildsData":[
                        "widget":"TextDisplayWidgetVue"
                        ,name:"buildsData"
                        ,attr: """v-show = false"""
                        ,elementId:'buildsDataResize'
                        ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                "AnyApps":["widget":"TextDisplayWidgetVue"
                           ,"name":"AnyApps"
                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                "Yet":["widget":"TextDisplayWidgetVue"
                       ,"name":"Yet"
                       ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                "youWont":["widget":"TextDisplayWidgetVue"
                           ,"name":"youWont"
                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                           ,attr: """ style ='color: black; font-size: 27px;font-family:sans-serif;' """
                ],
                "buildApp":["widget":"ButtonWidgetVue"
                            ,"name":"buildApp"
                            ,attr: """ style="background-color: #29b6f6; color:white;font-family:sans-serif; font-size:inherit; padding-top:28px; padding-bottom:28px; padding-right:35px; padding-left:35px;" """
                            ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                            ,script: """ this.scrollToQuiz('coachClone');"""
                ]

        ]
        currentFrameLayout = ref('vueFirstContainerResizeDataframeLayout')

    }
    vueOurWorkContainerDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueOurWorkContainerDataframe']
        saveButton = false
        addFieldDef =[
                labelOurWork:["widget":"TextDisplayWidgetVue"
                              ,"name":"labelOurWork"
                              ,attr: """ style = 'color: #29b6f6;font-size:14px;' """
                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                weDeliver:["widget":"TextDisplayWidgetVue"
                              ,"name":"weDeliver"
                              ,attr: """ style ="font-size:45px;" """
                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                WeDeliverTextParagraphOne:["widget":"TextDisplayWidgetVue"
                           ,"name":"WeDeliverTextParagraphOne"
                           ,attr: """ style ="font-size:18px;" """
                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                WeDeliverTextParagraphTwo:["widget":"TextDisplayWidgetVue"
                                           ,"name":"WeDeliverTextParagraphTwo"
                                           ,attr: """ style ="font-size:18px;" """
                                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        currentFrameLayout = ref("vueOurWorkContainerDataframeLayout")
    }
    vueOurProcessContainerDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs =['vueOurProcessContainerDataframe']
        saveButton = false
        addFieldDef = [
                labelOurProcess:["widget":"TextDisplayWidgetVue"
                              ,"name":"labelOurProcess"
                              ,attr: """ style = 'color: #29b6f6;font-size:14px;' """
                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                youDeserve:["widget":"TextDisplayWidgetVue"
                                 ,"name":"youDeserve"
                                 ,attr: """ style ="font-size:45px;" """
                                 ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                efficiency:["widget":"TextDisplayWidgetVue"
                            ,"name":"efficiency"
                            ,attr: """ style ="font-size:45px;" """
                            ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourProcessTextOne: ["widget":"TextDisplayWidgetVue"
                            ,"name":"ourProcessTextOne"
                            ,attr: """ style="font-size: large;font-family: sans-serif;" """
                            ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourProcessTextTwo: ["widget":"TextDisplayWidgetVue"
                                    ,"name":"ourProcessTextTwo"
                                    ,attr: """ style="font-size: large;font-family: sans-serif;" """
                                    ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourProcessTextThree: ["widget":"TextDisplayWidgetVue"
                                    ,"name":"ourProcessTextThree"
                                    ,attr: """ style="font-size: large;font-family: sans-serif;" """
                                    ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourProcessTextFour: ["widget":"TextDisplayWidgetVue"
                                    ,"name":"ourProcessTextFour"
                                    ,attr: """ style="font-size: large;font-family: sans-serif;" """
                                    ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        currentFrameLayout = ref("vueOurProcessContainerDataframeLayout")
    }
    vueCollaborationContainerDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueCollaborationContainerDataframe']
        saveButton = false
        addFieldDef =[
                labelCollaboration:["widget":"TextDisplayWidgetVue"
                                 ,"name":"labelCollaboration"
                                 ,attr: """ style = 'color: #29b6f6;font-size:14px;' """
                                 ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                agilityAsService:["widget":"TextDisplayWidgetVue"
                                    ,"name":"agilityAsService"
                                    ,attr: """ style ="font-size:45px;" """
                                    ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                collaborationFirstParagraph:["widget":"TextDisplayWidgetVue"
                                  ,"name":"collaborationFirstParagraph"
                                  ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                collaborationSecondParagraph:["widget":"TextDisplayWidgetVue"
                                             ,"name":"collaborationSecondParagraph"
                                             ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        childDataframes = ['vueCollaborationContainerResizeDataframe']
        currentFrameLayout = ref("vueCollaborationContainerDataframeLayout")
    }
    vueCollaborationContainerResizeDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueCollaborationContainerResizeDataframe']
        saveButton = false
        addFieldDef =[
                labelCollaboration:["widget":"TextDisplayWidgetVue"
                                    ,"name":"labelCollaboration"
                                    ,attr: """ style = 'color: #29b6f6;font-size:14px;' """
                                    ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                agilityAsService:["widget":"TextDisplayWidgetVue"
                                  ,"name":"agilityAsService"
                                  ,attr: """ style ="font-size:45px;" """
                                  ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                collaborationFirstParagraph:["widget":"TextDisplayWidgetVue"
                                             ,"name":"collaborationFirstParagraph"
                                             ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                collaborationSecondParagraph:["widget":"TextDisplayWidgetVue"
                                              ,"name":"collaborationSecondParagraph"
                                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        currentFrameLayout = ref("vueCollaborationContainerResizeDataframeLayout")
    }
    vueOurFrameworkContainerDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueOurFrameworkContainerDataframe']
        saveButton = false
        addFieldDef = [
                labelOurFrameWork:["widget":"TextDisplayWidgetVue"
                                    ,"name":"labelOurFrameWork"
                                    ,attr: """ style = 'color: #29b6f6;font-size:14px;' """
                                    ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                QualityBuilt:["widget":"TextDisplayWidgetVue"
                                   ,"name":"QualityBuilt"
                                   ,attr: """ style ="font-size:45px;" """
                                   ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourFrameworkTextFirstParagraph:["widget":"TextDisplayWidgetVue"
                                   ,"name":"ourFrameworkTextFirstParagraph"
                                   ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourFrameworkTextSecondParagraph:["widget":"TextDisplayWidgetVue"
                                   ,"name":"ourFrameworkTextSecondParagraph"
                                   ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                buttonDataframeOnGithub:["widget":"ButtonWidgetVue"
                                   ,"name":"buttonDataframeOnGithub"
                                   ,attr: """ style="background-color: #29b6f6; color:white;"  """
                                   ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        childDataframes =['vueOurFrameworkContainerResizeDataframe']
        currentFrameLayout = ref("vueOurFrameworkContainerDataframeLayout")
    }
    vueOurFrameworkContainerResizeDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueOurFrameworkContainerResizeDataframe']
        saveButton = false
        addFieldDef =[
                ourFrameworkTextFirstParagraph:["widget":"TextDisplayWidgetVue"
                                                ,"name":"ourFrameworkTextFirstParagraph"
                                                ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourFrameworkTextSecondParagraph:["widget":"TextDisplayWidgetVue"
                                                 ,"name":"ourFrameworkTextSecondParagraph"
                                                 ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                buttonDataframeOnGithub:["widget":"ButtonWidgetVue"
                                         ,"name":"buttonDataframeOnGithub"
                                         ,attr: """ style="background-color: #29b6f6; color:white;font-family:sans-serif; font-size:inherit; padding-top:28px; padding-bottom:28px; padding-right:35px; padding-left:35px;" """
                                         ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ]
        ]
        currentFrameLayout = ref("vueOurFrameworkContainerResizeDataframeLayout")

    }
    vueQuotesContainerDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueQuotesContainerDataframe']
        saveButton = false
        addFieldDef = [
                InitialQuote:["widget":"TextDisplayWidgetVue"
                                   ,"name":"InitialQuote"
                                   ,attr: """ style="padding-left:5%;font-family: sans-serif;font-size:36px;" """
                                   ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                nameOfPerson:["widget":"TextDisplayWidgetVue"
                              ,"name":"nameOfPerson"
                              ,attr: """ style="padding-left:5%;" """
                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                jobTitle:["widget":"TextDisplayWidgetVue"
                              ,"name":"jobTitle"
                              ,attr: """ style="padding-left:5%;" """
                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        currentFrameLayout = ref("vueQuotesContainerDataframeLayout")
    }
    vueOurTechnologiesContainerDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueOurTechnologiesContainerDataframe']
        saveButton = false
        addFieldDef = [
                labelOurTechnologies:["widget":"TextDisplayWidgetVue"
                                   ,"name":"labelOurTechnologies"
                                   ,attr: """ style = 'color: #29b6f6;font-size:14px;' """
                                   ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                thisIsHow:["widget":"TextDisplayWidgetVue"
                                      ,"name":"thisIsHow"
                                      ,attr: """ style ="font-size:45px;" """
                                      ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        currentFrameLayout = ref("vueOurTechnologiesContainerDataframeLayout")

    }
    vueQuizPlaceholderContainerDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueQuizPlaceholderContainerDataframe']
        saveButton = false
        addFieldDef = [
                quizLabel:["widget":"TextDisplayWidgetVue"
                              ,"name":"quizLabel"
                              ,attr: """ style = 'color:#ffffff;font-size:14px;' """
                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                tellUs:["widget":"TextDisplayWidgetVue"
                           ,"name":"tellUs"
                           ,attr: """ style ="font-size:45px;color:#ffffff;" """
                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                need:["widget":"TextDisplayWidgetVue"
                           ,"name":"need"
                           ,attr: """ style ="font-size:45px;color:#ffffff;" """
                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                contactQuiz:["widget":"TextDisplayWidgetVue"
                           ,"name":"contactQuiz"
                           ,attr: """ style="color:black;padding:25%" """
                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        childDataframes = ['vueElintegroSignUpQuizDataframe']
        currentFrameLayout = ref("vueQuizPlaceholderContainerDataframeLayout")
    }
    vueElintegroSignUpQuizDataframe(DataframeVue){ bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroSignUpQuizDataframe']
        saveButton = false
        initOnPageLoad = false
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        hql = """select  application.leadDescription, application.leadStage, application.leadBudget, person.firstName, person.lastName, person.email , person.phone from Application application inner join application.applicant person  where application.id=:id"""
        addFieldDef = ["application.leadDescription":[
                widget:"ComboboxVue"
                ,internationalize    :true
                ,initBeforePageLoad  :true
                ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadDescription'"""
                ,"displayMember": "Answer"
                ,"valueMember"  : "id"
                ,search:true],
                       "application.leadStage":[
                               widget:"ComboboxVue"
                               ,internationalize    :true
                               ,initBeforePageLoad  :true
                               ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadStage'"""
                               ,"displayMember": "Answer"
                               ,"valueMember"  : "id"
                               ,search:true],
                       "application.leadBudget":[
                               widget:"ComboboxVue"
                               ,initBeforePageLoad  :true
                               ,internationalize    :true
                               ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadBudget'"""
                               ,"displayMember": "Answer"
                               ,"valueMember"  : "id"
                               ,search:true],
                       "person.phone":["name":"phone","type":"link","widget":"PhoneNumberWidgetVue",validate: true],

        ]
        dataframeButtons = [
                submit: [name: "submit", type: "link",attr: """style='background-color:#1976D2; color:white;' """,script: """this.saveSignUpForm()""", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        currentFrameLayout = ref("vueElintegroSignUpQuizDataframeLayout")

    }
    vueElintegroChangePasswordAfterSignUpDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroChangePasswordAfterSignUpDataframe']
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        currentRoute = "change-password"
        saveButton = false
        initOnPageLoad = false
        route = true
        addFieldDef = ["currentPassword":[name:"currentPassword",widget:"PasswordWidgetVue"],
                       "newPassword":[name:"newPassword"
                                      ,widget:"PasswordWidgetVue"
                                      ,"validationRules":[[condition: "v => !!v ",message:"Password.required.message"],[condition:"v => (v && new RegExp('^(?=.*?[#?!@%^&*-])').test(v))",message:"password.contain.special.character"]
                                                          ,[condition:"v => (v && v.length >= 8)",message:"Password.must.be.greater.than.8"]]],
                       "confirmPassword":[name:"confirmPassword"
                                          ,widget:"PasswordWidgetVue"
                                          , "insertAfter":"newPassword"
                                          ,"validationRules":[[condition:"v => !!(v==this.state.vueElintegroChangePasswordAfterSignUpDataframe_newPassword)",message:"Password.and.Confirm.Password."]]],
        ]
        dataframeButtons = [submit: [name: "submit", type: "link",attr: """style='background-color:#1976D2; color:white;' """,script: """this.saveSignUpForm()""", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        currentFrameLayout = ref("vueElintegroChangePasswordAfterSignUpDataframeLayout")
    }

    vueFooterContainerDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueFooterContainerDataframe']
        saveButton = false
        addFieldDef = [
                footerCopyright:["widget":"TextDisplayWidgetVue"
                             ,"name":"footerCopyright"
                             ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                footerPrivacy:["widget":"TextDisplayWidgetVue"
                                 ,"name":"footerPrivacy"
                                 ,attr: """ style="color:black;" """
                                 ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ]
        ]
        currentFrameLayout = ref("vueFooterContainerDataframeLayout")
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
        dataframeButtons = [contactQuiz:[name: "contactQuiz", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('quiz_placeholder');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            technologiesInUse:[name: "technologiesInUse", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('our_Technologies');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ourClientsProjects:[name: "ourClientsProjects", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('ourClientsProjects');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ourFramework:[name: "ourFramework", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('our_framework');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            collaboration:[name: "collaboration", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('collaboration');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ourProcess  : [name: "ourProcess", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('our_process');""","flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            ourWork  : [name: "ourWork", type: "link",attr:"style='color:#1976D2;'",script: """this.scrollTo('our_work');""" , "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]

        currentFrameLayout = ref("vueElintegroSubMenuDataframeLayout")

    }

    vueElintegroSubMenuDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroSubMenuDataframe']
        isGlobal = true
        saveButton= false
        initOnPageLoad = false
        dataframeButtons = [quizzable  : [name: "quizzable", type: "link",attr:"style='color:#1976D2;'",script: """this.quizzableApp();""", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
                            translator  : [name: "translator", type: "link",attr:"style='color:#1976D2;'",route: true,routeIdScript: 0, refDataframe: ref("vueTranslatorAssistantDataframe"),"flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']],
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
//      hql = "select person.firstName, person.lastName, person.email, person.phone from Person person where person.id=:id"
        hql = "select person.firstName, person.lastName, person.email,person.phone,application.linkedin from Application application inner join application.applicant person where application.id=:id"
        initOnPageLoad = false
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        saveButton = false
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        addFieldDef = [

//                "person.firstName":["name":"firstName","type":"link","widget":"InputWidgetVue"],
//                "person.lastName":["name":"lastName","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => (v && v.length <= 30)",message:"LastName.must.be.less.than.30"]],],
//                "person.email":["name":"email","type":"link","widget":"EmailWidgetVue","validationRules":[[condition:"v => !!v", message: 'email.required.message']]],
//                "person.phone":["name":"phone","type":"link","widget":"PhoneNumberWidgetVue","validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]]],
                   "person.phone":["name":"phone","type":"link","widget":"PhoneNumberWidgetVue",validate: true],
//                "application.linkedin":["name":"linkedin","type":"link","widget":"InputWidgetVue","validationRules":[[condition:"v => !!v", message: 'Linkedin.required.message']]],
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
                next:[name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:'this.newEmployeeBasicInformation()',
                      flexGridValues: ['xs12', 'sm12', 'md1', 'lg1', 'xl1'], url:""]]


        currentFrameLayout = ref("vueNewEmployeeBasicInformationDataframeLayout")

    }
    vueNewEmployeeUploadResumeDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeUploadResumeDataframe']
        initOnPageLoad = false
        //hql = "select application.id,application.resume,application.images from Application application where application.id=:id"
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        saveButton = false
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        flexGridValuesForSaveButton =['xs3', 'sm3', 'md6', 'lg6', 'xl6']
        tab = true
        isGlobal = false
        doBeforeSave = """
            //Take key fields values from previous dataframe and apply them for the key field of this dataframe to update the record, rather then insert a new one.                          
            excon.matchKeysFromDataframeTo("vueNewEmployeeBasicInformationDataframe","vueNewEmployeeUploadResumeDataframe");
        """
        addFieldDef = [
                "application.images":["name":"images"
                                      ,"widget":"PictureUploadWidgetVue"
                                      ,ajaxFileSaveUrl: "${contextPath}/fileUpload/ajaxFileSave"
                                      ,multiple:true
                                      ,editButton: true
                                      ,valueMember:"avatar"
                                      ,deleteButton:true
                                      ,"accept":"image/*"
                                       ],

                "application.resume":["name":"resume"
                                      ,"widget":"FilesUploadWidgetVue"
                                      ,valueMember: "resume"
                                      ,ajaxFileSaveUrl: "${contextPath}/fileUpload/ajaxFileSave"
                                      ,multiple:true
                                      ,"accept":".pdf,.docx,.doc,.csv"

                                     ]
        ]

        dataframeButtons = [
                next:[name:"next", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""this.newEmployeeUploadResume()""",
                      flexGridValues:['xs3', 'sm3', 'md6', 'lg6', 'xl6'],url: ""],
                previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeBasicInformationDataframe-tab-id");\n""",
                                        flexGridValues: ['xs9', 'sm9', 'md6', 'lg6', 'xl6'],url: ""]]

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
        doBeforeRefresh = """allParams['id']= excon.getFromStore('vueNewEmployeeUploadResumeDataframe','key_vueNewEmployeeUploadResumeDataframe_application_id_id');
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
        doBeforeRefresh = """allParams['id']= this.vueNewEmployeeApplicantEditSkillDataframe_prop.key"""
        doBeforeSave = """allParams['key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id'] = this.vueNewEmployeeApplicantEditSkillDataframe_prop.key"""
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
        addFieldDef = ["applicationSkill.skill":[widget: "InputWidgetVue",attr: "autofocus"],
                       "applicationSkill.level":["max":10,  "validationRules":[[condition:"v => (v && new RegExp('^([0-9]|1[0])\$').test(v))",message:"digits.not.valid"]]]]
        dataframeButtons = [save: [name:"save", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""this.addNewSkill();""",flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'],url: ""]]
        currentFrameLayout = ref("vueNewEmployeeApplicantAddSkillDataframeLayout")
    }
    vueNewEmployeeAddtionalQuestionsDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeAddtionalQuestionsDataframe']
        initOnPageLoad = false
        doBeforeSave = """allParams['key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe','key_vueNewEmployeeUploadResumeDataframe_application_id_id');"""
        hql = "select application.id as Id, application.question1, application.question2 from Application application where application.id=:id"
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        saveButton = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        tab = true
        flexGridValuesForSaveButton =['xs3', 'sm3', 'md6', 'lg6', 'xl6']
        doAfterSave = """self.\$router.push("/thank-you-message/0");"""
        dataframeButtons = [
                previous: [name:"previous", type: "button",attr: """style='background-color:#1976D2; color:white;' """,script:"""Vue.set(this.\$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model","vueNewEmployeeSelfAssesmentDataframe-tab-id");\n""",
                           flexGridValues: ['xs9', 'sm9', 'md6', 'lg6', 'xl6'],url: ""]]

        currentFrameLayout = ref("vueNewEmployeeAddtionalQuestionsDataframeLayout")
    }
    vueNewEmployeeThankYouMessageAfterSaveDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewEmployeeThankYouMessageAfterSaveDataframe']
        saveButton = false
        currentRoute = 'thank-you-message'
        route = true
        doBeforeRefresh= """allParams['id'] = excon.getFromStore('vueNewEmployeeAddtionalQuestionsDataframe','key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id');"""
        doAfterRefresh = """setTimeout(function(){ self.\$router.push("/home/0");this.location.reload();}, 10000);"""
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
                       ,"user.username":["widget" : "EmailWidgetVue",attr: "autofocus", "name": "user.username", autoComplete:"on", "width":150, "errMessage":"Username should be an email"]
                       ,"rememberMe":["widget" : "CheckboxWidgetVue", height : '30px']
        ]

        dataframeButtons = [ login:[name:"login", type: "button", url: "${loginAuthenticateUrl}", layout: "<v-flex xs12 sm12 md6 lg6 xl6 pa-0>[BUTTON_SCRIPT]</v-flex>", attr: """color='blue darken-2' dark style="width: 10px; margin-left:65px;" """, doBeforeSave:""" var elementId = '#vueElintegroLoginDataframe';
                                     allParams["username"] = this.state.vueElintegroLoginDataframe_user_username;
                                     allParams["password"] = this.state.vueElintegroLoginDataframe_user_password;
                                     allParams["remember-me"] = this.state.vueElintegroLoginDataframe_rememberMe;
                                      """,
                                    callBackParams: [successScript: """
                                                          console.log("Login Callback");
                                                           this.location.reload();
                                                          //Dataframe.showHideDataframesBasedOnUserType(data);
                                                       """,
                                                     failureScript:"""if(response.success == false){
                                                                         response['alert_type'] = 'error';
                                                                         var responseData = {data:response};
                                                                         excon.showMessage(responseData,'vueElintegroLoginDataframe');
                                                                         setTimeout(function(){excon.setVisibility('vueElintegroLoginDataframe', false);this.location.reload();}, 6000);} 
                                                                         if(!response.msg){ this.location.reload();}"""],"flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                             forgetPassword:[name: "forgetPassword", type: "link", attr:"""style='color:#1976D2;margin-left:2px;' """, route: true,refDataframe: ref("vueElintegroForgetPasswordDataframe"),routeIdScript: "0", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'],
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

        ajaxSaveUrl = "${contextPath}/register/register"

        dataframeLabelCode = "User.Registration"
        //These are values, that overrides the default ones
        saveButtonAttr = " color='blue darken-2' dark"
        initOnPageLoad = false
        isGlobal = true
        saveButton = true
        flexGridValues = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        wrapInForm=true

        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doAfterSave = """ self.showAlertMessageToUser(response);"""
        addFieldDef =[
                "user.email":[widget: "EmailWidgetVue",attr: "autofocus", "placeHolder":"Enter your email","validationRules":[[condition:"v => !!v", message: 'email.required.message']],"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "user.firstName":[widget: "InputWidgetVue", "placeHolder":"Enter your Firstname"
                                  ,"validationRules":[[condition:"v => !!v",message:"FirstName.required.message"],[condition: "v => (v && v.length <= 30)",message:"FirstName.must.be.less.than.30"]]],
                "user.lastName":[widget: "InputWidgetVue", "placeHolder":"Enter your Lastname"
                                 ,"validationRules":[[condition:"v => !!v", message:"LastName.required.message"],[condition:"v => (v && v.length <= 30)", message:"LastName.must.be.less.than.30"]]]
                ,"user.password":[widget: "PasswordWidgetVue", "width":"150",
                                  "validationRules":[[condition: "v => !!v ",message:"Password.required.message"],[condition:"v => (v && new RegExp('^(?=.*?[#?!@%^&*-])').test(v))",message:"password.contain.special.character"],
                                  [condition:"v => (v && v.length >= 8)",message:"Password.must.be.greater.than.8"]]]
                ,"password2":[widget: "PasswordWidgetVue", "width":"150", "insertAfter":"user.password"
                              ,"validationRules":[[condition:"v => !!(v==this.state.vueElintegroRegisterDataframe_user_password)",message:"Password.and.Confirm.Password."]]]
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
        doAfterRefresh = """var imgSrc = "profileDetail/imageData"; excon.saveToStore('vueElintegroProfileMenuDataframe','vueElintegroProfileMenuDataframe_person_mainPicture', imgSrc);"""

//        route = true
        wrapInForm = true
//        "url":"https://s3.us-east-2.amazonaws.com/elintegro1",
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
        dataframeButtons = [Logout     : [name: "logout", type: "button",attr: """style='background-color:#1976D2; color:white;' """, url: "${contextPath}/logoff", "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'], script: "", callBackParams: [failureScript: """vueElintegroProfileMenuDataframeVar.\$router.push("/home/0");this.location.reload();"""]],
                            editProfile: [name: 'editProfile', type: "button",attr: """style='background-color:#1976D2; color:white;' """,showAsDialog: false, "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'], route: true, routeIdScript: "this.state.vueElintegroProfileMenuDataframe_person_id;", refDataframe: ref('vueElintegroUserProfileDataframe')]]
        currentFrameLayout = ref("vueElintegroProfileMenuDataframeLayout")
    }
    vueElintegroUserProfileDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroUserProfileDataframe']

        dataframeLabelCode = "User.Profile"
        hql = "select person.id, person.mainPicture,person.email, person.firstName, person.lastName, person.bday,  person.phone,person.mainPicture from Person as person where person.id=:id"
        saveButton = false
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        deleteButton = false
        flexGridValues = ['xs12', 'sm6', 'md6', 'lg6', 'xl4']
        wrapInForm=true
        childDataframes=["vueElintegroResetPasswordDataframe"]
        doAfterRefresh = """var imgSrc = "profileDetail/imageData"; excon.saveToStore('vueElintegroUserProfileDataframe','vueElintegroUserProfileDataframe_person_mainPicture', imgSrc);"""
        route = true
        currentRoute = 'user-profile'
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
                        ,"locale":"en"
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
                        ,"validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]]
                ],
                "person.languages":[
                        widget: "ComboboxVue"
                        ,initBeforePageLoad  :true
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
        dataframeButtons = [submit: [name:"save",type: "button",attr: """style='background-color:#1976D2; color:white;' """,script: "this.editProfile();"],
         resetPassword: [name:"resetPassword", type: "button",attr: """style='background-color:#1976D2; color:white;' """, url: "", showAsDialog: true, "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'], refDataframe: ref("vueElintegroResetPasswordDataframe")] ]

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
        dataframeButtons = [ Submit: [name:"submit", type: "button",attr:"""style='background-color:#1976D2; color:white;'""" , url: "${contextPath}/register/resetUserPassword", doBeforeAjax: """var url = Dataframe.getUrl();
                                                                                                                            var t = url.searchParams.get("token"); 
                                                                                                                            if(t != undefined || t != null){ allParams['t']=t;}
                                                                                          allParams['vueElintegroResetPasswordDataframe_user_email']=jQuery("#vueElintegroUserProfileDataframe_person_email").val();
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
        currentRoute = 'applicants'
        addFieldDef = [
                "applicant": [
                        widget            : "GridWidgetVue"
                        , name            : "applicant"

                        , hql             : """select application.id as Id, person.firstName as FirstName ,person.lastName as LastName,  person.email as Email, 
                                                person.phone as Phone from Application application inner join application.applicant person"""
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
