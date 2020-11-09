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
                            ,elementId: "translatorAssistant"
                            ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "project.list":[
                        widget: "ComboboxVue"
                        , hql: """select proj.id as projectId, proj.name as Name from Project proj where proj.id not in  (select pro.id from Project pro inner join pro.users u)"""
                        ,"displayMember":"Name"
                        ,attr: """ background-color='#EBF9FF !important' color='#2AB6F6' """
                        , search:true
                        ,flexGridValues: [ 'xs12' ,'sm12', 'md12', 'lg12', 'xl12']
                ]
        ]
        dataframeButtons =[
                translation:[name:"translate",type: "link",attr: """style='background-color:#2ab6f6; color:#1a1b1f;' :disabled='disableWhenItemNotExist' """,route: true,routeIdScript: 0,refDataframe: ref("vueTranslatorDataframe"),flexGridValues:['xs0', 'sm4', 'md4', 'lg4', 'xl4'] ],
                createProject:[name: "createProject",type: "button",attr: """style='background-color:#2ab6f6; color:#1a1b1f;' """,showAsDialog: true,refDataframe: ref("vueCreateProjectForTranslationDataframe"),flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'] ]
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
                                       ,elementId: "translatorAssistant"
                                       ,flexGridValues:['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "project.list":[
                        widget: "ComboboxVue"
                        , hql: """select project.id as projectId , project.name as Name , users.id as Id from Project project inner join project.users users where users.id = :session_userid"""
                        ,"displayMember":"Name"
                        ,attr: """ background-color='#EBF9FF !important' color='#2AB6F6' """
                        , search:true
                        ,flexGridValues: [ 'xs12' ,'sm12', 'md12', 'lg12', 'xl12']
                ]
        ]
        dataframeButtons =[
                translation:[name:"translate",type: "link",attr: """style='background-color:#2ab6f6; color:#1a1b1f;' :disabled='disableWhenItemNotExist' """,route: true,routeIdScript: 0,refDataframe: ref("vueTranslatorDataframe"),flexGridValues:['xs0', 'sm4', 'md4', 'lg4', 'xl4'] ],
                createProject:[name: "createProject",type: "button",attr: """style='background-color:#2ab6f6; color:#1a1b1f;' """,showAsDialog: true,refDataframe: ref("vueCreateProjectForTranslationDataframe"),flexGridValues:['xs0', 'sm0', 'md0', 'lg0', 'xl0'] ]
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
                                ,flexGridValues:['xs0', 'sm0', 'md6', 'lg6', 'xl6']
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
                        ,attr: """ outlined rounded background-color='#EBF9FF !important' color='#2AB6F6' """
                ],
                "project.sourceLanguage":[
                        widget: "ComboboxVue"
                        ,initBeforePageLoad  :true
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,"valueMember":"id"
                        , search:true
                        ,attr: """ outlined rounded background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,multiple: false
                        ,flexGridValues: [ 'xs12' ,'sm12', 'md12', 'lg12', 'xl12']
                ],
                "project.sourceFile":[
                        widget: "FilesUploadWidgetVue"
                        ,ajaxFileSaveUrl: "translatorAssistant/fileUpload"
                        ,attr: """ outlined rounded background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,doAfterSave:"""excon.showMessage(response,'vueCreateProjectForTranslationDataframe');"""
                        ,flexGridValues: [ 'xs12' ,'sm12', 'md12', 'lg12', 'xl12']
                ],
                "save":["widget":"ButtonWidgetVue"
                        ,"name":"save"
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
        flexGridValues =['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doBeforeRefresh = """
                         var projectDetails = excon.getFromStore('vueTranslatorDataframe','currentlySelectedProject')
                         var selectedProjectId = projectDetails.projectId
                         allParams['projectId']= selectedProjectId
                         excon.saveToStore('vueTranslatorDataframe','projectId',selectedProjectId)"""
        hql = """select  project.name , project.sourceLanguage  from Project project where project.id=:projectId """
        addFieldDef =[

                "project.name":[widget:"TextDisplayWidgetVue",displayWithLabel:true,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "project.sourceLanguage":[widget:"TextDisplayWidgetVue",displayWithLabel:true,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "project.languages":[
                        widget: "ComboboxVue"
                        , hql: """select language.id as id, language.ename as ename  from Language as language where language.ename not in (select text.language as language from Text as text where project_id =:projectId  group by language)"""
                        ,"displayMember":"ename"
                        ,internationalize: true
                        , search:true
                        ,multiple: true
                        ,attr: """ outlined   background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,valueMember:"id"
                        ,"flexGridValues":['xs12', 'sm12', 'md8', 'lg9', 'xl9'],
                ],
                "add":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"project.languages",
                        script       : """ this.addLanguage()""",
                        "attr"       :"style='background-color:#2ab6f6; color:white; margin-top:13px;'",
                        disabled     :"disableAddButtonWhenItemNotSelect",
                        "flexGridValues":['xs12', 'sm12', 'md1', 'lg1', 'xl1'],
                ],
                "project.language":[
                        widget: "ListWidgetVue"
                        , hql: """select text.language as language from Text text inner join text.project project  where project_id = :projectId and text.language != project.sourceLanguage group by language"""
                        ,"displayMember":"language"
                        ,internationalize: true
                        ,valueMember:"projectId"
                        ,OnClick:"translatedText(item)"

                ],
        ]
        dataframeButtons=[
                downloadAllTranslatedFiles: [name: "downloadAllTranslatedFiles",type: "button",attr: """style='background-color:#2ab6f6; color:white;' v-show = 'showDownloadAllTranslatedFilesButton' """,script:""" this.downloadAllTranslatedFiles();""",flexGridValues:['xs12', 'sm12', 'md12', 'lg6', 'xl6']],
                addNewRecord: [name: "addNewRecord",type: "button",attr: """style='background-color:#2ab6f6; color:white;' """,showAsDialog: true, refDataframe: ref("vueAddNewRecordForCurrentProjectDataframe"),flexGridValues:['xs12', 'sm12', 'md12', 'lg6', 'xl6']],
                projectManager: [name: "projectManager",type: "button",attr: """style='background-color:#2ab6f6; color:white; text-transform: capitalize;'""",script: """this.\$router.push("/translator-assistant/0");""",flexGridValues:['xs12', 'sm12', 'md12', 'lg6', 'xl6']]
        ]
        childDataframes = ['vueAddNewRecordForCurrentProjectDataframe','vueGridOfTranslatedTextDataframe']
        currentFrameLayout= ref("vueElintegroTranslatorDataframeLayout")

    }
    vueAddNewRecordForCurrentProjectDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAddNewRecordForCurrentProjectDataframe']
        initOnPageLoad = true
        putFillInitDataMethod = true
        saveButton = false
        doBeforeRefresh = """ allParams['projectId'] = excon.getFromStore('vueTranslatorDataframe','projectId')"""
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        hql = """select project.id ,project.sourceLanguage from Project project where project.id = :projectId """
        addFieldDef = [
                "project.key":[name: 'key', widget:"InputWidgetVue",attr: "autofocus","validationRules":[[condition:"v => !!v",message:"key.required.message"],[condition:"v =>  (v && new RegExp(/^\\S+\$/).test(v))",message:"key.donot.have.space"]]],
                "project.sourceText":[ widget:"InputWidgetVue","validationRules":[[condition:"v => !!v",message:"text.required.message"]]],
                "project.sourceLanguage":[widget:"TextDisplayWidgetVue",displayWithLabel:true,insertAfter:'project.text'],
                "textToTranslate":[
                          name: 'textToTranslate'
                        , widget: "GridWidgetVue"
                        , hql:"""select text.language as targetLanguage,text.text as text from Text text inner join text.project project  where project_id = :projectId and text.language != project.sourceLanguage  group by language"""
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
                save: [name: "save",type: "button",attr: """style='background-color:#2ab6f6; color:white;' """,flexGridValues:['xs12', 'sm12', 'md6', 'lg6', 'xl6'],script:"this.saveNewlyAddedRecord();"]
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
        doBeforeRefresh = """ allParams['projectId'] = excon.getFromStore('vueTranslatorDataframe','projectId');
                              this.state.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_text_text = this.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_prop.parentData.text;
                          """
        addFieldDef = ["text.text":[ widget: "TextAreaWidgetVue",attr: """ outlined   background-color='#EBF9FF !important' color='#2AB6F6' """]]
        dataframeButtons=[
                save: [name: "save",type: "button",attr: """style='background-color:#2ab6f6; color:white;' """,flexGridValues:['xs12', 'sm12', 'md6', 'lg6', 'xl6'],script:"this.updateEditedTextInGrid();"]
        ]
        currentFrameLayout = ref("vueEditTextOfNewlyAddedRecordForCurrentProjectDataframeLayout")
    }
    vueGridOfTranslatedTextDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueGridOfTranslatedTextDataframe']
        saveButton = false
        initOnPageLoad = true
        flexGridValues =['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doBeforeRefresh = """allParams['targetLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','targetLanguage');
                           allParams['projectId'] =  excon.getFromStore('vueGridOfTranslatedTextDataframe','projectId');
                           allParams['sourceLanguage'] =excon.getFromStore('vueGridOfTranslatedTextDataframe','sourceLanguage'); """
        doAfterRefresh = """self.buttonShowHide(response);"""
        addFieldDef = ["translatedText":[
                                     widget: "GridWidgetVue"
                                     ,name: "translatedText"
                                     , hql             : """select text.id as Id, text._key as Key, text.text as Text from Text text where project_id =:projectId and text.language = :targetLanguage and text._key != null"""
                                     , gridWidth       : 820
                                     , showGridSearch  : true
                                     , internationalize: true
                                     ,attr: """style="overflow-y:auto; max-height:500px;" """
                                     , sortable        : true
                                     ,onClick :[showAsDialog: true, refDataframe: ref("vueEditTranslatedRecordsOfGridDataframe"),]
                                     ,editButton: true
                                     ,onButtonClick   : [
                                                        ['actionName': 'Edit Text', 'buttons': [
                                                        [name        : "edit"
                                                        ,MaxWidth: 500
                                                        ,showAsDialog: true
                                                        ,tooltip     : [message: "tooltip.grid.edit", internationalization: true]
                                                        ,refDataframe: ref("vueEditTranslatedRecordsOfGridDataframe")
                                                        ,vuetifyIcon : [name: "edit"]
                                     ],
                                                        [deleteButton:true
                                                         ,maxWidth:500
                                                         ,valueMember: 'Id'
                                                         ,ajaxDeleteUrl:"translatorAssistant/deleteRecord"
                                                         ,doBeforeDelete:"""allParams['projectId'] =  excon.getFromStore('vueGridOfTranslatedTextDataframe','projectId');"""
                                                         ,doAfterDelete:"""self.vueGridOfTranslatedTextDataframe_fillInitData();"""
                                                         ,tooltip: [message:"tooltip.grid.delete",internationalization: true]
                                                         ,refDataframe: ref("vueDeleteTranslatedRecordsOfGridDataframe")
                                                         ,vuetifyIcon: [name: "delete"]
                                                        ]]]]
        ]]
        dataframeButtons = [translateWithGoogle: [name: "translateWithGoogle",type: "button",attr: """style='background-color:#2ab6f6; color:white;' v-show = 'vueGridOfTranslatedTextDataframe_button_translateWithGoogle' """,flexGridValues:['xs12', 'sm12', 'md0', 'lg0', 'xl0'],script: """this.retrieveTranslatedText()"""],
                            downloadTargetPropertyFile: [name: "downloadTargetPropertyFile",type: "button",attr: """style='background-color:#2ab6f6; color:white;' v-show = 'vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile' """,flexGridValues:['xs12', 'sm12', 'md0', 'lg0', 'xl0'],script: """this.downloadTargetFile()"""]
        ]
        childDataframes = ["vueEditTranslatedRecordsOfGridDataframe","vueDeleteTranslatedRecordsOfGridDataframe","vueDialogBoxForNotLoggedInUserDataframe"]
        currentFrameLayout= ref("vueGridOfTranslatedTextDataframeLayout")

    }

    vueEditTranslatedRecordsOfGridDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEditTranslatedRecordsOfGridDataframe']
        saveButton = true
        initOnPageLoad = true
        putFillInitDataMethod = true
        saveButtonAttr = """style='background-color:#2ab6f6; color:white;' """
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md2', 'lg2', 'xl2']
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doBeforeRefresh =  """allParams['id'] = this.vueEditTranslatedRecordsOfGridDataframe_prop.key"""
        doAfterRefresh = """excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','textBeforeEditing',response.vueEditTranslatedRecordsOfGridDataframe_text_text);"""
        doBeforeSave = """allParams['key_vueEditTranslatedRecordsOfGridDataframe_text_id_id'] = this.vueEditTranslatedRecordsOfGridDataframe_prop.key"""
        doAfterSave = """ excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                          excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','textBeforeEditing',response.params.vueEditTranslatedRecordsOfGridDataframe_text_text);
                          excon.refreshDataForGrid(response,'vueGridOfTranslatedTextDataframe', 'vueGridOfTranslatedTextDataframe_translatedText', 'U');
                      """
        hql = """select text.id as Id, text._key as Key, text.text as Text from Text text where text.id =:id"""
        addFieldDef = ["text._key":[readOnly: true]]
        dataframeButtons = [googleTranslate: [name: "googleTranslate",type: "button",attr: """style='background-color:#2ab6f6; color:white;'""",script: """ this.googleTranslateForEachRecord();""",flexGridValues:['xs12', 'sm12', 'md4', 'lg4', 'xl4']],
                            restore: [name: "restore",type: "button",attr:"""style='background-color:#2ab6f6; color:white;'""",script: """this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();""", flexGridValues:['xs12', 'sm12', 'md2', 'lg2', 'xl2']]
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
        dataframeButtons = [register: [name: "register",type: "button", showAsDialog: true, attr:"""style='background-color:#2ab6f6; color:white;'""", refDataframe: ref("vueElintegroRegisterDataframe"), tooltip: [message: 'Register'], "flexGridValues": ['xs6', 'sm6', 'md6', 'lg6', 'xl6']],
                            login: [name: "login",type:"button",showAsDialog: true,attr:"""style='background-color:#2ab6f6; color:white;'""",refDataframe: ref("vueElintegroLoginDataframe"),tooltip: [message: 'Login'], flexGridValues:['xs6', 'sm6', 'md6', 'lg6', 'xl6']]
        ]
        currentFrameLayout =ref("vueDialogBoxForNotLoggedInUserDataframeLayout")
    }

}