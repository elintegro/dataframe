package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.util.Holders


beans{
    def contextPath = Holders.grailsApplication.config.rootPath
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
                         ,attr: """ style='color:#29b6f6;' """
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
                         ,attr: """ style='color:#29b6f6;' """
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

}