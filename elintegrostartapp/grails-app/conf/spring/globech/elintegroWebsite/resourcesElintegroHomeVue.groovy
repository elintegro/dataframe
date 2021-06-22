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
                         'vueQuotesContainerDataframe','vueOurTechnologiesContainerDataframe'
                         ]
        currentFrameLayout = ref("vueElintegroHomeDataframeLayout")

    }
    vueFirstContainerDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueFirstContainerDataframe']
        saveButton = false
        initOnPageLoad = true
        doAfterRefresh = """self.changeWords(response);"""
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
                            ,attr: """ """
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
        initOnPageLoad = true
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
                                            <v-col cols="2" xs="2"  sm="1" md="1" lg="1" xl="1" align="left"><v-img src="assets/home/comma.png"></v-img></v-col>
                                            <v-col cols="12" xs="12" sm="12" md="12" lg="12" xl="12" align="center" justify="center">{{item.quote}}</v-col>
                                            <v-col cols="0" xs="0" sm="12" md="12" lg="12" xl="12" align="center" justify="center">
                                                
                                            </v-col>
                                            <v-col cols="12" xs="2" sm="12" md="12" lg="12" xl="12" align="center" justify="center">{{item.name}},  {{item.title}}</v-col>
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
        isGlobal = true
        addFieldDef = [
                letsTalk:["widget":"TextDisplayWidgetVue"
                             ,"name":"letsTalk"
                             ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'],
                ],
        ]
        currentFrameLayout = ref("vueQuizPlaceholderContainerDataframeLayout")
    }
    vueElintegroSignUpQuizDataframe(DataframeVue){ bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroSignUpQuizDataframe']
        saveButton = false
        initOnPageLoad = false
        isGlobal = true
        hql = """select  lead.leadDescription, lead.leadStage, lead.leadBudget, lead.nameOfProject, lead.deadline, lead.descriptionOfProject, person.firstName, person.lastName, person.email , person.phone from Lead lead inner join lead.applicant person  where lead.id=:id"""
        addFieldDef = [
                        "lead.leadDescription":[
                                widget:"ComboboxVue"
                                ,internationalize    :true
                                ,initBeforePageLoad  :true
                                ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadDescription'"""
                                ,"displayMember": "Answer"
                                ,"valueMember"  : "id"
                                ,attr: """ solo attach auto-select-first flat id="vueElintegroSignUpQuizDataframe-leadDescription" append-icon="mdi-chevron-down" class='quiz-signup' """
                                ,search:true
                                ,"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                       "lead.leadStage":[
                               widget:"ComboboxVue"
                               ,internationalize    :true
                               ,initBeforePageLoad  :true
                               ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadStage'"""
                               ,"displayMember": "Answer"
                               ,"valueMember"  : "id"
                               ,attr: """solo attach auto-select-first flat append-icon="mdi-chevron-down" class='quiz-signup' """
                               ,search:true
                               ,"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                       "lead.leadBudget":[
                               widget:"ComboboxVue"
                               ,initBeforePageLoad  :true
                               ,internationalize    :true
                               ,"hql"               : """select answer.id as id , answer.answerKey as Answer from AnswerTable answer inner join answer.question question where question.questionName = 'leadBudget'"""
                               ,"displayMember": "Answer"
                               ,"valueMember"  : "id"
                               ,attr: """solo attach auto-select-first flat append-icon="mdi-chevron-down"  class='quiz-signup' """

                               ,search:true
                               ,"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                       "lead.nameOfProject":[
                               widget:"InputWidgetVue"
                               ,name: "nameOfProject"
                               ,attr: """solo attach  auto-select-first class='quiz-signup' """
                               ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                               ],
                       "lead.deadline":[
                                widget:"DateWidgetVue"
                                ,name: "deadline"
                                ,attr: """solo attach  class='quiz-signup' """
                                ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                       ],
                       "lead.descriptionOfProject":[
                               widget:"TextAreaWidgetVue"
                               ,name: "descriptionOfProject"
                               ,attr: """solo attach   class='quiz-signup' """
                               ,"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                       ],
                       "person.firstName":[
                                widget:"InputWidgetVue"
                                ,name: "firstName"
                                ,attr: """solo attach auto-select-first class='quiz-signup' """
//                                ,"validationRules":[[condition:"v => !!v", message:"FirstName.required.message"]]
                                ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                       ],
                       "person.lastName":[
                                widget:"InputWidgetVue"
                                ,name: "lastName"
                                ,attr: """solo attach auto-select-first flat  class='quiz-signup' """
                                ,"validationRules":[[condition:"v => !!v", message:"LastName.required.message"]]
                                ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                       ],
                       "person.email":[
                               widget:"EmailWidgetVue"
                               ,name: "email"
                               ,attr: """solo attach auto-select-first flat  class='quiz-signup' """
                               ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                       ],
                       "person.phone":[
                               "name":"phone"
                               ,"widget":"PhoneNumberWidgetVue"
                               ,"validationRules":[[condition:"v => !!v", message: 'Phone.required.message'],[condition: "v => /[0-9]/.test(v)",message: "Only.numbers.are.allowed."],[condition:"v => (v && v.length >= 10 && v.length <= 15)",message:"Phone.number.must.be.between.10.and.15"]]
                               ,attr: """solo attach auto-select-first flat  class='quiz-signup' """
                               ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']],

        ]
        dataframeButtons = [
                submit: [name: "submit",
                         type: "button",
                         classNames : "form-submission-button" ,
                         script : """this.saveSignUpForm()""",
                         "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12' ]
                ]
        ]
        currentFrameLayout = ref("vueElintegroSignUpQuizDataframeLayout")

    }
    vueElintegroChangePasswordAfterSignUpDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueElintegroChangePasswordAfterSignUpDataframe']
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        currentRoute = "change-password"
        saveButton = false
        initOnPageLoad = false
        isGlobal = true
        route = true
        addFieldDef = ["currentPassword":[name:"currentPassword",widget:"PasswordWidgetVue",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                       "newPassword":[name:"newPassword"
                                      ,widget:"PasswordWidgetVue"
                                      ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                                      ,"validationRules":[[condition: "v => !!v ",message:"Password.required.message"],[condition:"v => (v && new RegExp('^(?=.*?[#?!@%^&*-])').test(v))",message:"password.contain.special.character"]
                                                          ,[condition:"v => (v && v.length >= 8)",message:"Password.must.be.greater.than.8"]]],
                       "confirmPassword":[name:"confirmPassword"
                                          ,widget:"PasswordWidgetVue"
                                          , "insertAfter":"newPassword"
                                          ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                                          ,"validationRules":[[condition:"v => !!(v==this.state.transits.newPassword.value)",message:"Password.and.Confirm.Password."]]],
        ]
        dataframeButtons = [submit: [name: "submit", type: "link",attr: """style='background-color:#1976D2; color:white;' """,script: """this.changePasswordAfterSignedUp()""", "flexGridValues": ['xs0', 'sm0', 'md0', 'lg0', 'xl0']]]
        currentFrameLayout = ref("vueElintegroChangePasswordAfterSignUpDataframeLayout")
    }

    vueFooterContainerDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueFooterContainerDataframe']
        saveButton = false
        isGlobal = true
        addFieldDef = [
                footerPrivacy:["widget":"ButtonWidgetVue"
                               ,"name":"footerPrivacy"
                               ,"attr":""" plain class='fontOfPrivacy'"""
                               ,onClick:[showAsDialog: true, refDataframe: ref("vueFooterPrivacyDataframe")]
                               ,script: """excon.setVisibility('vueFooterPrivacyDataframe',true);"""
                               ,flexGridValues:['xs12', 'sm4', 'md4', 'lg4', 'xl4'],
                ],
                termAndConditions:["widget":"ButtonWidgetVue"
                               ,"name":"termAndConditions"
                               ,"attr":"""class='fontOfPrivacy'"""
                               ,script: """excon.redirectPage(this,"terms-and-condition");"""
                               ,flexGridValues:['xs12', 'sm4', 'md4', 'lg4', 'xl4'],
                ]
        ]
        currentFrameLayout = ref("vueFooterContainerDataframeLayout")
    }
    vueFooterPrivacyDataframe(DataframeVue){ bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueFooterPrivacyDataframe']
        saveButton = false
        initOnPageLoad = false
        isGlobal = true
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
        isGlobal = true
        initOnPageLoad = true
        doAfterRefresh = "self.termsAndConditions();"
        route = true
        currentRoute = "terms-and-condition"
        currentFrameLayout = ref("vueTermAndConditionDataframeLayout")


    }

}