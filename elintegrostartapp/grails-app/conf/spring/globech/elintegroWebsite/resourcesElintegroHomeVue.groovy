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
        doAfterRefresh = """self.changeWords();"""
        addFieldDef = [
                "build":["widget":"TextDisplayWidgetVue"
                            ,"name":"build"
                            ,elementId: "build"
                            ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "apps":["widget":"TextDisplayWidgetVue"
                         ,"name":"apps"
                         ,elementId: "apps"
                         ,flexGridValues:['xs12', 'sm12', 'md1', 'lg1', 'xl1']
                ],
                'buildData':[ "widget":"TextDisplayWidgetVue"
                            ,"name":"buildData"
                            ,attr: """v-show = false"""
                            ,elementId:'buildData'
                            ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                        ],
                "youWont":["widget":"TextDisplayWidgetVue"
                           ,"name":"youWont"
                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                "buildApp":["widget":"ButtonWidgetVue"
                            ,"name":"buildApp"
                            ,flexGridValues:['xs0', 'sm0', 'md6', 'lg6', 'xl6']
                            ,script: """ this.scrollToQuiz('coachClone');"""
                ]

        ]
        currentFrameLayout = ref('vueFirstContainerDataframeLayout')

    }
    vueOurWorkContainerDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueOurWorkContainerDataframe']
        saveButton = false
        addFieldDef =[

                weDeliver:["widget":"TextDisplayWidgetVue"
                           ,"name":"weDeliver"
                           ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                WeDeliverTextParagraphOne:["widget":"TextDisplayWidgetVue"
                                           ,"name":"WeDeliverTextParagraphOne"
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
                youDeserve:["widget":"TextDisplayWidgetVue"
                            ,"name":"youDeserve"
                            ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],

                ourProcessTextOne: ["widget":"TextDisplayWidgetVue"
                                    ,"name":"ourProcessTextOne"
                                    ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourProcessTextTwo: ["widget":"TextDisplayWidgetVue"
                                    ,"name":"ourProcessTextTwo"
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

                agilityAsService:["widget":"TextDisplayWidgetVue"
                                  ,"name":"agilityAsService"
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

        currentFrameLayout = ref("vueCollaborationContainerDataframeLayout")
    }
    vueOurFrameworkContainerDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueOurFrameworkContainerDataframe']
        saveButton = false
        addFieldDef = [

                QualityBuilt:["widget":"TextDisplayWidgetVue"
                              ,"name":"QualityBuilt"
                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourFrameworkTextFirstParagraph:["widget":"TextDisplayWidgetVue"
                                                ,"name":"ourFrameworkTextFirstParagraph"
                                                ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                ourFrameworkTextSecondParagraph:["widget":"TextDisplayWidgetVue"
                                                 ,"name":"ourFrameworkTextSecondParagraph"
                                                 ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ]

        ]

        currentFrameLayout = ref("vueOurFrameworkContainerDataframeLayout")
    }
    vueQuotesContainerDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueQuotesContainerDataframe']
        saveButton = false
        flexGridValues= ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        addFieldDef = [
                customerSay:["widget":"TextDisplayWidgetVue"
                              ,"name":"customerSay"
                              ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
                quotes: ["widget":"CarouselWidgetVue"
                            ,"name":"quotes"
                            ,"height":"250"
                            ,hql:""" select testimonials.id as id ,testimonials.quote as quote, testimonials.name as name , testimonials.title as title, testimonials.customerImage as customerImage from Testimonials testimonials  """
                            ,"content":""" 
                                    <v-card
                                         height="100%"
                                         tile
                                         align="center"
                                         justify="center"
                                         class="contentQuotes"
                                         flat
                                    >
                                         <v-row
                                            align="center"
                                            justify="center"
                                         >
                                            <v-col cols="12" xs="1"  sm="1" md="1" lg="1" xl="1" align="center" justify="center"><v-img src="assets/home/inverted-comma.png"></v-img></v-col>
                                            <v-col cols="12" xs="12" sm="12" md="12" lg="12" xl="12" align="center" justify="center">{{item.quote}}</v-col>
                                            <v-col cols="12" xs="12" sm="12" md="12" lg="12" xl="12" align="center" justify="center">
                                                
                                            </v-col>
                                            <v-col cols="12" xs="12" sm="12" md="12" lg="12" xl="12" align="center" justify="center">{{item.name}},  {{item.title}}</v-col>
                                         </v-row>
                                    </v-card>
                                    """
                        ],

        ]
        currentFrameLayout = ref("vueQuotesContainerDataframeLayout")
    }
    vueOurTechnologiesContainerDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueOurTechnologiesContainerDataframe']
        saveButton = false
        addFieldDef = [

                thisIsHow:["widget":"TextDisplayWidgetVue"
                           ,"name":"thisIsHow"
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
                letsTalk:["widget":"TextDisplayWidgetVue"
                             ,"name":"letsTalk"
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
        hql = """select  lead.leadDescription, lead.leadStage, lead.leadBudget,lead.nameOfProject, lead.descriptionOfProject,lead.deadline, person.firstName, person.lastName, person.email , person.phone from Lead lead inner join lead.applicant person  where lead.id=:id"""
        addFieldDef = [
                        "lead.leadDescription":[
                                widget:"ComboboxVue"
                                ,internationalize    :true
                                ,initBeforePageLoad  :true
                                ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadDescription'"""
                                ,"displayMember": "Answer"
                                ,"valueMember"  : "id"
                                ,search:true],
                       "lead.leadStage":[
                               widget:"ComboboxVue"
                               ,internationalize    :true
                               ,initBeforePageLoad  :true
                               ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadStage'"""
                               ,"displayMember": "Answer"
                               ,"valueMember"  : "id"
                               ,search:true],
                       "lead.leadBudget":[
                               widget:"ComboboxVue"
                               ,initBeforePageLoad  :true
                               ,internationalize    :true
                               ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadBudget'"""
                               ,"displayMember": "Answer"
                               ,"valueMember"  : "id"
                               ,search:true],
                       "lead.nameOfProject":[
                               widget:"InputWidgetVue"
                               ,name: "nameOfProject"
                               ],
                       "lead.descriptionOfProject":[
                               widget:"TextAreaWidgetVue"
                               ,name: "descriptionOfProject"
                       ],
                       "lead.deadline":[
                               widget:"DateWidgetVue"
                               ,name: "deadline"
                       ],

                       "person.phone":["name":"phone","widget":"PhoneNumberWidgetVue",validate: true],

        ]
        dataframeButtons = [
                submit: [name: "submit", type: "link",attr: """style='background-color:#2ab6f6; color:#1a1b1f;' """,script: """this.saveSignUpForm()""", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
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
                footerPrivacy:["widget":"ButtonWidgetVue"
                               ,"name":"footerPrivacy"
                               ,"attr":"""small text tile"""
                               ,onClick:[showAsDialog: true, refDataframe: ref("vueFooterPrivacyDataframe")]
                               ,script: """excon.setVisibility('vueFooterPrivacyDataframe',true);"""
                               ,flexGridValues:['xs12', 'sm4', 'md4', 'lg4', 'xl4'],
                ],
                termAndConditions:["widget":"ButtonWidgetVue"
                               ,"name":"termAndConditions"
                               ,"attr":"""small text tile"""
                               ,onClick:[showAsDialog: true, refDataframe: ref("vueTermAndConditionDataframe")]
                               ,script: """excon.setVisibility('vueTermAndConditionDataframe',true);"""
                               ,flexGridValues:['xs12', 'sm4', 'md4', 'lg4', 'xl4'],
                ]
        ]
        childDataframes = ["vueFooterPrivacyDataframe","vueTermAndConditionDataframe"]
        currentFrameLayout = ref("vueFooterContainerDataframeLayout")
    }
    vueFooterPrivacyDataframe(DataframeVue){ bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueFooterPrivacyDataframe']
        saveButton = false
        initOnPageLoad = false
        addFieldDef = [
                privacyPolicyHeading:["widget":"TextDisplayWidgetVue"
                                      ,"name":"privacyPolicyHeading"
                                      ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                loremEpsumText:["widget":"TextDisplayWidgetVue"
                          ,"name":"loremEpsumText"
                          ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        currentFrameLayout = ref("vueFooterPrivacyDataframeLayout")
    }
    vueTermAndConditionDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueTermAndConditionDataframe']
        saveButton = false
        initOnPageLoad = false
        addFieldDef = [
                termAndConditionHeading:["widget":"TextDisplayWidgetVue"
                                      ,"name":"termAndConditionHeading"
                                      ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                loremEpsumText:["widget":"TextDisplayWidgetVue"
                                ,"name":"loremEpsumText"
                                ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        currentFrameLayout = ref("vueTermAndConditionDataframeLayout")


    }

}