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
        addFieldDef = [
                "project.list":[
                        widget: "ComboboxVue"
                        , hql: """select project.id as id, project.name as name from Project as project """
                        ,"displayMember":"name"
                        , search:true

                ]
        ]
        dataframeButtons =[
                translation:[name:"translate",type: "link",attr: """style='background-color:#1976D2; color:white;' :disabled='disableWhenItemNotExist' """,route: true,routeIdScript: 0,refDataframe: ref("vueTranslatorDataframe"),flexGridValues:['xs12', 'sm12', 'md10', 'lg10', 'xl10'] ],
                createProject:[name: "createProject",type: "button",attr: """style='background-color:#1976D2; color:white;' """,showAsDialog: true,refDataframe: ref("vueCreateProjectForTranslationDataframe"),flexGridValues:['xs12', 'sm12', 'md2', 'lg2', 'xl2'] ]
        ]
        childDataframes = ['vueTranslatorDataframe','vueCreateProjectForTranslationDataframe']
        currentFrameLayout = ref("vueElintegroTranslatorAssistantDataframeLayout")
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
                "project.sourceLanguage":[
                        widget: "ComboboxVue"
                        ,initBeforePageLoad  :true
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,"valueMember":"id"
                        , search:true
                        ,multiple: false
                ],
                "project.sourceFile":[
                        widget: "FilesUploadWidgetVue"
                        ,ajaxFileSaveUrl: "${contextPath}/translatorAssistant/fileUpload"
                ]
        ]
        dataframeButtons=[
                save: [name: "save",type: "button",attr: """style='background-color:#1976D2; color:white;' """,flexGridValues:['xs12', 'sm12', 'md0', 'lg0', 'xl0'],script: """this.saveProject()"""]
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
                         var selectedProjectId;
                         var projectDetails = excon.getFromStore('vueTranslatorAssistantDataframe','vueTranslatorAssistantDataframe_project_list')
                         if(projectDetails.id == "" || projectDetails.id == undefined){
                            selectedProjectId = excon.getFromStore('vueTranslatorAssistantDataframe','currentProjectId')
                            allParams['projectId']= selectedProjectId;
                         }
                         else{
                                selectedProjectId = projectDetails.id
                                allParams['projectId']= selectedProjectId
                         } 
                         excon.saveToStore('vueTranslatorDataframe','projectId',selectedProjectId)"""
        hql = """select  project.name , project.sourceLanguage  from Project project where project.id=:projectId """
        addFieldDef =[
                "projectManager":[
                        "widget"     : "ButtonWidgetVue",
                        "insertBefore":"project.name",
                        script       : """this.\$router.push("/translator-assistant/0");""",
                        "attr"       :"style='background-color:#1976D2; color:white; text-transform: capitalize;'",
                        "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'],
                ],
                "project.name":[widget:"TextDisplayWidgetVue",displayWithLabel:true],
                "project.sourceLanguage":[widget:"TextDisplayWidgetVue",displayWithLabel:true],
                "project.languages":[
                        widget: "ComboboxVue"
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,internationalize: true
                        , search:true
                        ,multiple: true
                        ,valueMember:"id"
                        ,"flexGridValues":['xs12', 'sm12', 'md10', 'lg10', 'xl10'],
                ],
                "add":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"project.languages",
                        script       : """ this.addLanguage()""",
                        "attr"       :"style='background-color:#1976D2; color:white; margin-top:13px;'",
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
                downloadAllTranslatedFiles: [name: "downloadAllTranslatedFiles",type: "button",attr: """style='background-color:#1976D2; color:white;' v-show = 'showDownloadAllTranslatedFilesButton' """,script:""" this.downloadAllTranslatedFiles();""",flexGridValues:['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                addNewRecord: [name: "addNewRecord",type: "button",attr: """style='background-color:#1976D2; color:white;' """,showAsDialog: true, refDataframe: ref("vueAddNewRecordForCurrentProjectDataframe"),flexGridValues:['xs12', 'sm12', 'md6', 'lg6', 'xl6'],]
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
                save: [name: "save",type: "button",attr: """style='background-color:#1976D2; color:white;' """,flexGridValues:['xs12', 'sm12', 'md6', 'lg6', 'xl6'],script:"this.saveNewlyAddedRecord();"]
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
        addFieldDef = ["text.text":[ widget: "TextAreaWidgetVue"]]
        dataframeButtons=[
                save: [name: "save",type: "button",attr: """style='background-color:#1976D2; color:white;' """,flexGridValues:['xs12', 'sm12', 'md6', 'lg6', 'xl6'],script:"this.updateEditedTextInGrid();"]
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
                                                         ,ajaxDeleteUrl:"${contextPath}/translatorAssistant/deleteRecord"
                                                         ,doBeforeDelete:"""allParams['projectId'] =  excon.getFromStore('vueGridOfTranslatedTextDataframe','projectId');"""
                                                         ,doAfterDelete:"""self.vueGridOfTranslatedTextDataframe_fillInitData();"""
                                                         ,tooltip: [message:"tooltip.grid.delete",internationalization: true]
                                                         ,refDataframe: ref("vueDeleteTranslatedRecordsOfGridDataframe")
                                                         ,vuetifyIcon: [name: "delete"]
                                                        ]]]]
        ]]
        dataframeButtons = [translateWithGoogle: [name: "translateWithGoogle",type: "button",attr: """style='background-color:#1976D2; color:white;' v-show = 'vueGridOfTranslatedTextDataframe_button_translateWithGoogle' """,flexGridValues:['xs12', 'sm12', 'md0', 'lg0', 'xl0'],script: """this.retrieveTranslatedText()"""],
                            downloadTargetPropertyFile: [name: "downloadTargetPropertyFile",type: "button",attr: """style='background-color:#1976D2; color:white;' v-show = 'vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile' """,flexGridValues:['xs12', 'sm12', 'md0', 'lg0', 'xl0'],script: """this.downloadTargetFile()"""]
        ]
        childDataframes = ["vueEditTranslatedRecordsOfGridDataframe","vueDeleteTranslatedRecordsOfGridDataframe"]
        currentFrameLayout= ref("vueGridOfTranslatedTextDataframeLayout")

    }

    vueEditTranslatedRecordsOfGridDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEditTranslatedRecordsOfGridDataframe']
        saveButton = true
        initOnPageLoad = true
        putFillInitDataMethod = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
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
        dataframeButtons = [googleTranslate: [name: "googleTranslate",type: "button",attr: """style='background-color:#1976D2; color:white;'""",script: """ this.googleTranslateForEachRecord();""",flexGridValues:['xs12', 'sm12', 'md4', 'lg4', 'xl4']],
                            restore: [name: "restore",type: "button",attr:"""style='background-color:#1976D2; color:white;'""",script: """this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();""", flexGridValues:['xs12', 'sm12', 'md2', 'lg2', 'xl2']]
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

}