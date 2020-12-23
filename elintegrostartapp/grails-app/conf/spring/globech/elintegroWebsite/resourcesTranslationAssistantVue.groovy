package spring.globech.elintegroWebsite

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.widget.vue.GridWidgetVue
import com.elintegro.erf.widget.vue.TextAreaWidgetVue
import grails.util.Holders

beans{
    def contextPath = Holders.grailsApplication.config.rootPath
    vueTranslatorAssistantDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueTranslatorAssistantDataframe']
        dataframeLabelCode ="Translator Assistant"
        saveButton = false
        initOnPageLoad = true
        route = true
        childDataframes = [
                           "vueTranslatorAssistantAfterLoggedInDataframe"
                           ,"vueTranslatorAssistantBeforeLoggedInDataframe"
                           ]
        currentFrameLayout = ref("vueElintegroTranslatorAssistantDataframeLayout")
    }
    vueMeetTranslatorAssistantIntroDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMeetTranslatorAssistantIntroDataframe']
        saveButton = false
        isGlobal = true
        addFieldDef =[
                "meetTranslatorTitle":["widget":"TextDisplayWidgetVue"
                            ,"name":"meetTranslatorTitle"
                            ,elementId: "meetTranslatorTitle"
                            ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "meetTranslatorSubTitle":["widget":"TextDisplayWidgetVue"
                                       ,"name":"meetTranslatorSubTitle"
                                       ,elementId: "meetTranslatorSubTitle"
                                       ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],

        ]
        currentFrameLayout = ref("vueMeetTranslatorAssistantIntroDataframeLayout")
    }
    vueTranslatorAssistantBeforeLoggedInDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueTranslatorAssistantBeforeLoggedInDataframe']
        saveButton = false
        initOnPageLoad = true
        addFieldDef = [
                "translatorAssistant":["widget":"TextDisplayWidgetVue"
                            ,"name":"translatorAssistant"
                            ,elementId: "translatorAssistantBeforeLoggedIn"
                            ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "projectList":[
                        widget: "ComboboxVue"
                        , hql: """select project.id as projectId , project.name as Name from Project project where project.users is empty"""
                        ,"displayMember":"Name"
                        ,attr: """ background-color='#EBF9FF !important' color='#2AB6F6' """
                        , search:true
                        ,flexGridValues: [ 'xs12' ,'sm12', 'md12', 'lg12', 'xl12']
                ]
        ]
        dataframeButtons =[
                translation:[name:"translate",type: "link",attr: """style='background-color:#1976D2; color:white;'  :disabled='enableDisableTranstaleButtonComputed' """,script: """this.enterTranslatorPage();""",flexGridValues:['xs0', 'sm4', 'md4', 'lg4', 'xl4'] ],
                createProject:[name: "createProject",type: "button",attr: """style='background-color:#1976D2; color:white;'  """,showAsDialog: true,refDataframe: ref("vueCreateProjectForTranslationDataframe"),flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'] ]
        ]
        childDataframes = ['vueTranslatorDataframe','vueCreateProjectForTranslationDataframe']
        currentFrameLayout = ref("vueTranslatorAssistantBeoforeAndAfterLoggedInDataframeLayout")
    }
    vueTranslatorAssistantAfterLoggedInDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueTranslatorAssistantAfterLoggedInDataframe']
        saveButton = false
        initOnPageLoad = true
        addFieldDef = [
                "translatorAssistant":["widget":"TextDisplayWidgetVue"
                                       ,"name":"translatorAssistant"
                                       ,elementId: "translatorAssistantAfterLoggedIn"
                                       ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "projectList":[
                        widget: "ComboboxVue"
                        , hql: """select project.id as projectId , project.name as Name , users.id as Id from Project project inner join project.users users where users.id = :session_userid"""
                        ,"displayMember":"Name"
                        ,attr: """ background-color='#EBF9FF !important' color='#2AB6F6' """
                        , search:true
                        ,flexGridValues: [ 'xs12' ,'sm12', 'md12', 'lg12', 'xl12']
                ]
        ]
        dataframeButtons =[
                translation:[name:"translate",type: "link",attr: """style='background-color:#1976D2; color:white;'  :disabled='enableDisableTranstaleButtonComputed' """,script: """this.enterTranslatorPage()""",flexGridValues:['xs0', 'sm4', 'md4', 'lg4', 'xl4'] ],
                createProject:[name: "createProject",type: "button",attr: """style='background-color:#1976D2; color:white;'  """,showAsDialog: true,refDataframe: ref("vueCreateProjectForTranslationDataframe"),flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'] ]
        ]
        childDataframes = ['vueTranslatorDataframe','vueCreateProjectForTranslationDataframe']
        currentFrameLayout = ref("vueTranslatorAssistantBeoforeAndAfterLoggedInDataframeLayout")
    }
    vueHowYouDoDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueHowYouDoDataframe']
        saveButton = false
        isGlobal = true
        addFieldDef = [
                "howYouDo":["widget":"TextDisplayWidgetVue"
                             ,"name":"howYouDo"
                             ,elementId: "howYouDo"
                             ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "howYouDoParagraphOne":[
                        "widget":"TextDisplayWidgetVue"
                        ,"name":"howYouDoParagraphOne"
                        ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                "howYouDoParagraphTwo":[
                        "widget":"TextDisplayWidgetVue"
                        ,"name":"howYouDoParagraphTwo"
                        ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                "howYouDoParagraphThree":[
                        "widget":"TextDisplayWidgetVue"
                        ,"name":"howYouDoParagraphThree"
                        ,flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0']
                ],
                "translateApp":["widget":"ButtonWidgetVue"
                            ,"name":"translateApp"
                            ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]
        ]
        currentFrameLayout = ref("vueHowYouDoDataframeLayout")
    }
    vueNewsLetterDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueNewsLetterDataframe']
        saveButton = false
        initOnPageLoad = true
        isGlobal = true
        addFieldDef =[
                "subscribe":["widget":"TextDisplayWidgetVue"
                        ,"name":"subscribe"
                        ,elementId: "subscribe"
                        ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "subscribeButton":["widget":"ButtonWidgetVue"
                                ,"name":"subscribeButton"
                                ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]
        ]

        currentFrameLayout = ref("vueNewsLetterDataframeLayout")
    }
    vueCreateProjectForTranslationDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueCreateProjectForTranslationDataframe']
        dataframeLabelCode = "New.Project"
        initOnPageLoad = false
        saveButton = false
        flexGridValues =['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        hql = "select project.id , project.name, project.sourceLanguage, project.sourceFile from Project as project"
        addFieldDef=[
                "project.name":[
                        widget: "InputWidgetVue"
                        ,attr: """ outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                ],
                "project.sourceLanguage":[
                        widget: "ComboboxVue"
                        ,initBeforePageLoad  :true
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,"valueMember":"id"
                        ,attr: """ outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,multiple: false
                ],
                "project.sourceFile":[
                        widget: "FilesUploadWidgetVue"
                        ,ajaxFileSaveUrl: "translatorAssistant/fileUpload"
                        ,attr: """ outlined background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,doAfterSave:"""excon.showAlertMessage(response.data);"""
                ],
                "save":["widget":"ButtonWidgetVue"
                        ,"name":"save"
                        ,attr: """style='background-color:#1976D2; color:white;' """
                        ,script: """let timeOut = 6000;this.saveProject(timeOut);"""
                        ,flexGridValues:['xs0', 'sm0', 'md6', 'lg6', 'xl6']
                ]
        ]
        currentFrameLayout = ref("vueCreateProjectForTranslationDataframeLayout")
    }
    vueTranslatorDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueTranslatorDataframe']
        dataframeLabelCode = "Translator.Page"
        initOnPageLoad = true
        saveButton = false
        route = true
        vueStore = [state: [currentlySelectedProject:[:]]]
        flexGridValues =['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doBeforeRefresh = """
                         var projectDetails = excon.getFromStore('vueTranslatorDataframe','currentlySelectedProject')
                         var selectedProjectId = Number(projectDetails.projectId)
                         params['projectId']= selectedProjectId
                         params.namedParameters.projectId.value = selectedProjectId;
                         params.domain_keys.project.id = selectedProjectId;    
                         excon.saveToStore('vueTranslatorDataframe','projectId',selectedProjectId)"""
        doAfterRefresh = """
                         var transits = excon.getFromStore('vueTranslatorDataframe','transits')
                         let notSelected = transits.notSelectedLanguages.items;
                         let selected = transits.selectedLanguages.items;
                         if(selected){
                             for(let val of selected){
                                notSelected = notSelected.filter(w => w.ename != val.language)
                             }
                         }
                         transits.notSelectedLanguages.items = notSelected;
                         excon.saveToStore('vueTranslatorDataframe','transits',transits);
                         """
        hql = """select  project.name , project.sourceLanguage  from Project project where project.id=:projectId """
        addFieldDef =[

                "project.name":[widget:"TextDisplayWidgetVue",isDynamic:true,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "project.sourceLanguage":[widget:"TextDisplayWidgetVue",isDynamic:true,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "notSelectedLanguages":[
                        widget: "ComboboxVue"
                        , hql: """select language.id as id, language.ename as ename  from Language as language"""
                        ,"displayMember":"ename"
                        ,internationalize: true
                        ,multiple: true
                        ,attr: """ outlined   background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,valueMember:"id"
                        ,"flexGridValues":['xs12', 'sm12', 'md8', 'lg9', 'xl9'],
                ],
                "add":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"notSelectedLanguages",
                        script       : """ this.addLanguage()""",
                        "attr"       :"style='background-color:#1976D2; color:white; margin-top:13px;'",
                        disabled     :"enableDisableAddButton",
                        "flexGridValues":['xs12', 'sm12', 'md1', 'lg1', 'xl1'],
                ],
                "selectedLanguages":[
                        widget: "ListWidgetVue"
                        , hql: """select text.language as language from Text text inner join text.project project  where project_id = :projectId and text.language != project.sourceLanguage group by language"""
                        ,"displayMember":"language"
                        ,internationalize: true
                        ,itemString : true
                        ,valueMember:"projectId"
                        ,OnClick:"translatedText(item)"

                ],
        ]
        dataframeButtons=[
                downloadAllTranslatedFiles: [name: "downloadAllTranslatedFiles",type: "button",attr: """style='background-color:#1976D2; color:white;'  v-show = 'showOrHideDownloadAllFilesButton' """,script:""" this.downloadAllTranslatedFiles();""",flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                addNewRecord: [name: "addNewRecord",type: "button",attr: """style='background-color:#1976D2; color:white;'  """,showAsDialog: true, refDataframe: ref("vueAddNewRecordForCurrentProjectDataframe"),flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                projectManager: [name: "projectManager",type: "button",attr: """style='background-color:#1976D2; color:white; text-transform: capitalize;'""",script: """this.\$router.push("/translator-assistant/0");""",flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']]
        ]
        childDataframes = ['vueAddNewRecordForCurrentProjectDataframe','vueGridOfTranslatedTextDataframe']
        currentFrameLayout= ref("vueElintegroTranslatorDataframeLayout")

    }
    vueAddNewRecordForCurrentProjectDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAddNewRecordForCurrentProjectDataframe']
        initOnPageLoad = true
        putFillInitDataMethod = false
        saveButton = false
        doBeforeRefresh = """ let projectId = excon.getFromStore('vueTranslatorDataframe','projectId');
                              params['projectId'] = projectId;
                              params.domain_keys.project.id = projectId;
                              params.namedParameters.projectId.value = projectId;
                              params.persisters.project.id.value = projectId;
                          """
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        hql = """select project.id ,project.sourceLanguage from Project project where project.id = :projectId """
        addFieldDef = [
                "key":[widget:"InputWidgetVue" ,attr: """ autofocus outlined background-color='#EBF9FF !important' color='#2AB6F6' ""","validationRules":[[condition:"v => !!v",message:"key.required.message"],[condition:"v =>  (v && new RegExp(/^\\S+\$/).test(v))",message:"key.donot.have.space"]]],
                "sourceText":[ widget:"InputWidgetVue","validationRules":[[condition:"v => !!v",message:"text.required.message"]],attr: """ outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "project.sourceLanguage":[widget:"TextDisplayWidgetVue",isDynamic:true,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],],
                "textToTranslate":[
                          name: 'textToTranslate'
                        , widget: "GridWidgetVue"
                        , hql:"""select text.language as TargetLanguage,text.text as Text from Text text inner join text.project project  where project_id = :projectId and text.language != project.sourceLanguage  group by language"""
                        , internationalize: true
                        , sortable        : true
                        ,editButton       : true
                        ,editableField    : "Text"
                        ,onButtonClick   : [
                        ['actionName': 'Edit/Translate', 'buttons': [
                                [ name        : "edit"
                                 ,MaxWidth: 500
                                 ,showAsDialog: true
                                 ,refDataframe: ref('vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe')
                                 ,tooltip     : [message: "tooltip.grid.edit", internationalization: true]
                                 ,vuetifyIcon : [name: "edit"]
                                ],
                                [
                                        name: "translate"
                                        ,MaxWidth: 500
                                        ,tooltip: [message: "tooltip.grid.translate",internationalization: true]
                                        ,vuetifyIcon: [name: "translate"]
                                        ,script: "this.translateText(dataRecord);"
                                ]
                        ]]
                ]]


        ]
        dataframeButtons=[
                save: [name: "save",type: "button",attr: """style='background-color:#1976D2; color:white;'  """,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12'],script:"this.saveNewlyAddedRecord();"]
        ]
        childDataframes = ['vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe']
        currentFrameLayout = ref("vueAddNewRecordForCurrentProjectDataframeLayout")
    }
    vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe']
        initOnPageLoad = true
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        saveButton = false
        doBeforeRefresh = """ params['projectId'] = excon.getFromStore('vueTranslatorDataframe','projectId');
                          """
        doAfterRefresh = """ params.transits.text.value = self.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_prop.parentData.Text;"""
        addFieldDef = ["text":[ widget: "TextAreaWidgetVue",attr: """ outlined   background-color='#EBF9FF !important' color='#2AB6F6' """]]
        dataframeButtons=[
                save: [name: "save",type: "button",attr: """style='background-color:#1976D2; color:white;'  """,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12'],script:"this.updateEditedTextInGrid();"]
        ]
        currentFrameLayout = ref("vueEditTextOfNewlyAddedRecordForCurrentProjectDataframeLayout")
    }
    vueGridOfTranslatedTextDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueGridOfTranslatedTextDataframe']
        saveButton = false
        initOnPageLoad = true
        flexGridValues =['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doBeforeRefresh = """params['targetLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','targetLanguage');
                           params['projectId'] =  excon.getFromStore('vueGridOfTranslatedTextDataframe','projectId');
                           params['sourceLanguage'] =excon.getFromStore('vueGridOfTranslatedTextDataframe','sourceLanguage'); """

        addFieldDef = ["translatedText":[
                                     widget: "GridWidgetVue"
                                     ,name: "translatedText"
                                     , hql             : """select text.id as Id, text._key as Key, text.text as Text from Text text where project_id =:projectId and text.language = :targetLanguage and text._key != null"""
                                     , gridWidth       : 820
                                     , showGridSearch  : true
                                     , internationalize: true
                                     ,attr: """style="overflow-y:auto; max-height:500px;" """
                                     , sortable        : true
//                                     ,onClick :[showAsDialog: true,refreshInitialData:true,MaxWidth: 700,refDataframe: ref("vueEditTranslatedRecordsOfGridDataframe"),]
                                     ,editButton: true
                                     ,onButtonClick   : [
                                                        ['actionName': 'Edit/Delete Text', 'buttons': [
                                                        [name        : "edit"
                                                         ,editButton: true
                                                        ,MaxWidth: 700
                                                        ,refreshInitialData:true
                                                        ,showAsDialog: true
                                                        ,tooltip     : [message: "tooltip.grid.edit", internationalization: true]
                                                        ,refDataframe: ref("vueEditTranslatedRecordsOfGridDataframe")
                                                        ,vuetifyIcon : [name: "edit"]
                                     ],
                                                        [deleteButton:true
                                                         ,maxWidth:500
                                                         ,valueMember: 'Id'
                                                         ,ajaxDeleteUrl:"translatorAssistant/deleteRecord"
                                                         ,doBeforeDelete:"""params['projectId'] =  excon.getFromStore('vueGridOfTranslatedTextDataframe','projectId');"""
                                                         ,fieldType:"transits"
                                                         ,doAfterDelete:"""self.vueGridOfTranslatedTextDataframe_fillInitData();"""
                                                         ,tooltip: [message:"tooltip.grid.delete",internationalization: true]
                                                         ,refDataframe: ref("vueDeleteTranslatedRecordsOfGridDataframe")
                                                         ,vuetifyIcon: [name: "delete"]
                                                        ]]]]
        ]]
        dataframeButtons = [translateWithGoogle: [name: "translateWithGoogle",type: "button",attr: """style='background-color:#1976D2; color:white;'  v-show = 'translateWithGoogleButtonShowHide' """,flexGridValues:['xs12', 'sm12', 'md0', 'lg0', 'xl0'],script: """this.retrieveTranslatedText()"""],
                            downloadTargetPropertyFile: [name: "downloadTargetPropertyFile",type: "button",attr: """style='background-color:#1976D2; color:white;'  v-show = '!downLoadButtonShowHide' """,flexGridValues:['xs12', 'sm12', 'md0', 'lg0', 'xl0'],script: """this.downloadTargetFile()"""]
        ]
        childDataframes = ["vueEditTranslatedRecordsOfGridDataframe","vueDeleteTranslatedRecordsOfGridDataframe","vueDialogBoxForNotLoggedInUserDataframe"]
        currentFrameLayout= ref("vueGridOfTranslatedTextDataframeLayout")

    }
    vueEditTranslatedRecordsOfGridDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEditTranslatedRecordsOfGridDataframe']
        saveButton = true
        initOnPageLoad = false
        putFillInitDataMethod = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md2', 'lg2', 'xl2']
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doBeforeRefresh =  """ let textId = self.vueEditTranslatedRecordsOfGridDataframe_prop.key;
                              params['id'] = textId;
                            params.domain_keys.translatedText.id = textId;
                            params.persisters.translatedText.id.value = textId;
                            params.namedParameters.id.value = textId;
                          """
        doAfterRefresh = """excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','textBeforeEditing',response.persisters.translatedText.text.value);"""
        doBeforeSave = """params['key_vueEditTranslatedRecordsOfGridDataframe_text_id_id'] = self.vueEditTranslatedRecordsOfGridDataframe_prop.key"""
        doAfterSave = """ excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                          excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','textBeforeEditing',response.persisters.translatedText.text.value);
                          excon.refreshDataForGrid(response,'vueGridOfTranslatedTextDataframe', 'translatedText', 'U', 'transits');
                      """
        hql = """select translatedText.id as Id, translatedText._key as Key, translatedText.text as Text from Text translatedText where translatedText.id =:id"""
        addFieldDef = [
                "translatedText._key":[widget:"InputWidgetVue"
                             ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,
                             readOnly: true,],
                "translatedText.text":[widget:"TextAreaWidgetVue"
                             ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,
                             ]
        ]
        dataframeButtons = [googleTranslate: [name: "googleTranslate",type: "button",attr: """style='background-color:#1976D2; color:white;' """,script: """ this.googleTranslateForEachRecord();""",flexGridValues:['xs12', 'sm12', 'md4', 'lg4', 'xl4']],
                            restore: [name: "restore",type: "button",attr:"""style='background-color:#1976D2; color:white;' """,script: """this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();""", flexGridValues:['xs12', 'sm12', 'md2', 'lg2', 'xl2']]
        ]
        currentFrameLayout = ref("vueEditTranslatedRecordsOfGridDataframeLayout")
    }
    vueDeleteTranslatedRecordsOfGridDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueDeleteTranslatedRecordsOfGridDataframe']
        saveButton = false
        hql = "select text.id from Text text where text.id=:id"
        currentFrameLayout =ref("vueDeleteTranslatedRecordsOfGridDataframeLayout")
    }
    vueDialogBoxForNotLoggedInUserDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueDialogBoxForNotLoggedInUserDataframe']
        saveButton = false
        route = true
        currentRoute = 'login-page'
        dataframeButtons = [register: [name: "register",type: "button", showAsDialog: true, attr:"""style='background-color:#1976D2; color:white;' """, refDataframe: ref("vueElintegroRegisterDataframe"), tooltip: [message: 'Register'], "flexGridValues": ['xs6', 'sm6', 'md6', 'lg6', 'xl6']],
                            login: [name: "login",type:"button",showAsDialog: true,attr:"""style='background-color:#1976D2; color:white;' """,refDataframe: ref("vueElintegroLoginDataframe"),tooltip: [message: 'Login'], flexGridValues:['xs6', 'sm6', 'md6', 'lg6', 'xl6']]
        ]
        currentFrameLayout =ref("vueDialogBoxForNotLoggedInUserDataframeLayout")
    }

}