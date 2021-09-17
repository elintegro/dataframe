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
                            ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "meetTranslatorSubTitle":["widget":"TextDisplayWidgetVue"
                                       ,"name":"meetTranslatorSubTitle"
                                       ,elementId: "meetTranslatorSubTitle"
                                       ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
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
                            ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "projectList":[
                        widget: "ComboboxVue"
                        , hql: """select project.id as projectId , project.name as Name from Project project where project.users is empty"""
                        ,"displayMember":"Name"
                        ,attr: """ background-color='#EBF9FF !important' color='#2AB6F6' outlined """
                        , search:true
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ]
        ]
        dataframeButtons =[
                translation:[name:"translate",type: "link",attr: """style='background-color:#1976D2; color:white;'  :disabled='enableDisableTranstaleButtonComputed' """,script: """this.enterTranslatorPage();""","cssGridValues":['xs':'0', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4'] ],
                createProject:[name: "createProject",type: "button",attr: """style='background-color:#1976D2; color:white;'  """,showAsDialog: true,refDataframe: ref("vueCreateProjectForTranslationDataframe"),"cssGridValues":['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0'] ]
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
                                       ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "projectList":[
                        widget: "ComboboxVue"
                        , hql: """select project.id as projectId , project.name as Name , users.id as Id from Project project inner join project.users users where users.id = :session_userid"""
                        ,"displayMember":"Name"
                        ,attr: """ background-color='#EBF9FF !important' color='#2AB6F6' outlined """
                        , search:true
                        ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ]
        ]
        dataframeButtons =[
                translation:[name:"translate",type: "link",attr: """style='background-color:#1976D2; color:white;'  :disabled='enableDisableTranstaleButtonComputed' """,script: """this.enterTranslatorPage()""","cssGridValues":['xs':'0', 'sm':'4', 'md':'4', 'lg':'4', 'xl':'4'] ],
                createProject:[name: "createProject",type: "button",attr: """style='background-color:#1976D2; color:white;'  """,showAsDialog: true,refDataframe: ref("vueCreateProjectForTranslationDataframe"),"cssGridValues":['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0'] ]
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
                             ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "howYouDoParagraphOne":[
                        "widget":"TextDisplayWidgetVue"
                        ,"name":"howYouDoParagraphOne"
                        ,"cssGridValues":['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']
                ],
                "howYouDoParagraphTwo":[
                        "widget":"TextDisplayWidgetVue"
                        ,"name":"howYouDoParagraphTwo"
                        ,"cssGridValues":['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']
                ],
                "howYouDoParagraphThree":[
                        "widget":"TextDisplayWidgetVue"
                        ,"name":"howYouDoParagraphThree"
                        ,"cssGridValues":['xs':'0', 'sm':'0', 'md':'0', 'lg':'0', 'xl':'0']
                ],
                "translateApp":["widget":"ButtonWidgetVue"
                            ,"name":"translateApp"
                            ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
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
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "subscribeButton":["widget":"ButtonWidgetVue"
                                ,"name":"subscribeButton"
                                ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
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
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
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
                        ,"cssGridValues":['xs':'0', 'sm':'0', 'md':'6', 'lg':'6', 'xl':'6']
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
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
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
                         if(selected && selected.length != 0){
                             for(let val of selected){
                                notSelected = notSelected.filter(w => w.ename != val.language && w.ename != response.persisters.project.sourceLanguage.value)
                             }
                         }else{
                              notSelected = notSelected.filter(w => w.ename != response.persisters.project.sourceLanguage.value)
                         }
                         transits.notSelectedLanguages.items = notSelected;
                         excon.saveToStore('vueTranslatorDataframe','transits',transits);
                         """
        hql = """select  project.name , project.sourceLanguage  from Project project where project.id=:projectId """
        addFieldDef =[

                "project.name":[widget:"TextDisplayWidgetVue",isDynamic:true,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],
                "project.sourceLanguage":[
                        widget:"TextDisplayWidgetVue"
                        ,isDynamic:true
                        ,link : true
                        ,type: "link"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,onClick:"sourceText();" ],
                "notSelectedLanguages":[
                        widget: "ComboboxVue"
                        , hql: """select language.id as id, language.ename as ename  from Language as language"""
                        ,"displayMember":"ename"
                        ,internationalize: true
                        ,multiple: true
                        ,attr: """ outlined   background-color='#EBF9FF !important' color='#2AB6F6' """
                        ,valueMember:"id"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'8', 'lg':'9', 'xl':'9'],
                ],
                "add":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"notSelectedLanguages",
                        script       : """ this.addLanguage()""",
                        "attr"       :"style='background-color:#1976D2; color:white; margin-top:13px;'",
                        disabled     :"enableDisableAddButton",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'1', 'lg':'1', 'xl':'1'],
                ],
                "selectedLanguages":[
                        widget: "SimpleTableListWidgetVue"
                        , hql: """select text.language as language from Text text inner join text.project project  where project_id = :projectId and text.language != project.sourceLanguage group by language"""
                        ,"displayMember":"language"
                        ,internationalize: true
                        ,itemString : true
                        ,valueMember:"projectId"
                        ,OnClick:"translatedText(item)"
                ],
                "projectManager":[
                        "widget" : "ButtonWidgetVue",
                        icon : true,
                        script : """ this.\$router.push("/translator");""",
                        "attr" : """class="ma-2" color="blue" text icon""",
                        "iconAttr":"""x-large dark""",
                        vuetifyIcon : "arrow_back",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                ],
        ]
        dataframeButtons=[
                downloadAllTranslatedFiles: [name: "downloadAllTranslatedFiles",type: "button",attr: """style='background-color:#1976D2; color:white;'  v-show = 'showOrHideDownloadAllFilesButton' """,script:""" this.downloadAllTranslatedFiles();""","cssGridValues":['xs':'12', 'sm':'6', 'md':'12', 'lg':'6', 'xl':'6']],
                addNewRecord: [name: "addNewRecord",type: "button",attr: """style='background-color:#1976D2; color:white;'  """,showAsDialog: true, refDataframe: ref("vueAddNewRecordForCurrentProjectDataframe"),"cssGridValues":['xs':'12', 'sm':'6', 'md':'12', 'lg':'6', 'xl':'6']],
        ]
        childDataframes = ['vueAddNewRecordForCurrentProjectDataframe','vueGridOfSourceTextDataframe','vueGridOfTranslatedTextDataframe']
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
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        hql = """select project.id ,project.sourceLanguage from Project project where project.id = :projectId """
        addFieldDef = [
                "key":[widget:"InputWidgetVue" ,attr: """ autofocus outlined background-color='#EBF9FF !important' color='#2AB6F6' ""","validationRules":[[condition:"v => !!v",message:"key.required.message"],[condition:"v =>  (v && new RegExp(/^\\S+\$/).test(v))",message:"key.donot.have.space"]]],
                "sourceText":[ widget:"InputWidgetVue","validationRules":[[condition:"v => !!v",message:"text.required.message"]],attr: """ outlined background-color='#EBF9FF !important' color='#2AB6F6' """],
                "project.sourceLanguage":[widget:"TextDisplayWidgetVue",isDynamic:true,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],],
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
                save: [name: "save",type: "button",attr: """style='background-color:#1976D2; color:white;'  ""","cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],script:"this.saveNewlyAddedRecord();"]
        ]
        childDataframes = ['vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe']
        currentFrameLayout = ref("vueAddNewRecordForCurrentProjectDataframeLayout")
    }
    vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe']
        initOnPageLoad = true
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        saveButton = false
        doBeforeRefresh = """ params['projectId'] = excon.getFromStore('vueTranslatorDataframe','projectId');
                          """
        doAfterRefresh = """ params.transits.text.value = self.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_prop.parentData.Text;"""
        addFieldDef = ["text":[ widget: "TextAreaWidgetVue",attr: """ outlined   background-color='#EBF9FF !important' color='#2AB6F6' """]]
        dataframeButtons=[
                save: [name: "save",type: "button",attr: """style='background-color:#1976D2; color:white;'  ""","cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],script:"this.updateEditedTextInGrid();"]
        ]
        currentFrameLayout = ref("vueEditTextOfNewlyAddedRecordForCurrentProjectDataframeLayout")
    }
    vueGridOfSourceTextDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueGridOfSourceTextDataframe']
        saveButton = false
        initOnPageLoad = true
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        doBeforeRefresh = """
                           params['projectId'] =  excon.getFromStore('vueGridOfSourceTextDataframe','projectId');
                           params['sourceLanguage'] =excon.getFromStore('vueGridOfSourceTextDataframe','sourceLanguage'); """
        doAfterRefresh = """excon.saveToStore('vueGridOfSourceTextDataframe','gridTitleFromState',self.state.sourceLanguage)"""

        addFieldDef = ["originalSourceText":[
                widget: "GridWidgetVue"
                ,name: "originalSourceText"
                , hql             : """select text.id as Id, text._key as Key, text.text as Text from Text text where project_id =:projectId and text.language = :sourceLanguage and text._key != null"""
                , gridWidth       : 820
                , showGridSearch  : true
                , internationalize: true
                ,isDynamic        : true
                ,attr: """style="overflow-y:auto; max-height:500px;" dense  """
                ,labelStyle       :"""font-size:1.6rem !important; font-weight:600 !important;"""
                , sortable        : true
                ,onButtonClick   : [
                ['actionName': 'Actions', 'buttons': [
                        [
                                editButton: true
                                ,MaxWidth: 700
                                ,refreshInitialData:true
                                ,showAsDialog: true
                                ,refDataframe: ref("vueEditSourceRecordsOfGridDataframe")
                                ,tooltip     : [message: "tooltip.grid.edit", internationalization: true]
                                ,vuetifyIcon : [name: "edit"]
                        ],
                        [deleteButton:true
                         ,maxWidth:500
                         ,valueMember: 'Id'
                         ,ajaxDeleteUrl:"translatorAssistant/deleteRecord"
                         ,doBeforeDelete:"""params['projectId'] =  excon.getFromStore('vueGridOfSourceTextDataframe','projectId');"""
                         ,fieldType:"transits"
                         ,doAfterDelete:"""self.vueGridOfSourceTextDataframe_fillInitData();"""
                         ,tooltip: [message:"tooltip.grid.delete",internationalization: true]
                         ,refDataframe: ref("vueDeleteSourceRecordsOfGridDataframe")
                         ,vuetifyIcon: [name: "delete"]
                        ]
                ]]
        ]]]
        currentFrameLayout= ref("vueGridOfSourceTextDataframeLayout")
        childDataframes = ["vueEditSourceRecordsOfGridDataframe"]

    }
    vueEditSourceRecordsOfGridDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEditSourceRecordsOfGridDataframe']
        saveButton = true
        initOnPageLoad = false
        putFillInitDataMethod = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        doBeforeRefresh =  """
                            excon.setValuesForNamedParamsFromGrid({'targetDataframe': 'vueEditSourceRecordsOfGridDataframe',
                                                                    'namedParamKey': 'id', 
                                                                    'sourceDataframe': 'vueGridOfSourceTextDataframe', 
                                                                    'fieldName':'originalSourceText',
                                                                    'key': 'Id'} 
                                                                    );
                          """
        doAfterRefresh = """excon.saveToStore('vueEditSourceRecordsOfGridDataframe','textBeforeEditing',response.persisters.originalSourceText.text.value);"""
        doAfterSave = """ excon.saveToStore('vueEditSourceRecordsOfGridDataframe','textBeforeEditing',response.persisters.originalSourceText.text.value);
                          excon.refreshDataForGrid(response,'vueGridOfSourceTextDataframe', 'originalSourceText', 'U', 'transits');
                          excon.setVisibility("vueEditSourceRecordsOfGridDataframe", false);"""
        hql = """select  originalSourceText.id as Id, originalSourceText._key as Key, originalSourceText.text as Text from Text originalSourceText where originalSourceText.id=:id"""
        addFieldDef = [
                "originalSourceText._key":[widget:"InputWidgetVue"
                                       ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' disabled """,
                                       readOnly: true,],
                "originalSourceText.text":[widget:"TextAreaWidgetVue"
                                       ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,
                ]
        ]

        currentFrameLayout = ref("vueEditSourceRecordsOfGridDataframeLayout")
    }
    vueGridOfTranslatedTextDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueGridOfTranslatedTextDataframe']
        saveButton = false
        initOnPageLoad = true
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
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
                                     ,isDynamic        : true
                                     ,labelStyle       :"""font-size:1.6rem !important; font-weight:600 !important;"""
                                     ,attr: """style="overflow-y:auto; max-height:500px;" dense  """
                                     , sortable        : true
//                                     ,onClick :[showAsDialog: true,refreshInitialData:true,MaxWidth: 700,refDataframe: ref("vueEditTranslatedRecordsOfGridDataframe"),]
                                     ,onButtonClick   : [
                                                        ['actionName': 'Actions', 'buttons': [
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
        dataframeButtons = [translateWithGoogle: [name: "translateWithGoogle",type: "button",attr: """style='background-color:#1976D2; color:white;'  v-show = 'translateWithGoogleButtonShowHide' ""","cssGridValues":['xs':'12', 'sm':'12', 'md':'0', 'lg':'0', 'xl':'0'],script: """this.retrieveTranslatedText()"""],
                            downloadTargetPropertyFile: [name: "downloadTargetPropertyFile",type: "button",attr: """style='background-color:#1976D2; color:white;'  v-show = '!downLoadButtonShowHide' ""","cssGridValues":['xs':'12', 'sm':'12', 'md':'0', 'lg':'0', 'xl':'0'],script: """this.downloadTargetFile()"""]
        ]
        childDataframes = ["vueEditTranslatedRecordsOfGridDataframe","vueDeleteTranslatedRecordsOfGridDataframe","vueDialogBoxForNotLoggedInUserDataframe"]
        currentFrameLayout= ref("vueGridOfTranslatedTextDataframeLayout")

    }
    vueEditTranslatedRecordsOfGridDataframe(DataframeVue){ bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEditTranslatedRecordsOfGridDataframe']
        saveButton = false
        initOnPageLoad = false
        putFillInitDataMethod = true
        saveButtonAttr = """style='background-color:#1976D2; color:white;' """
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        cssGridValues = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        doBeforeRefresh =  """
                            excon.setValuesForNamedParamsFromGrid([{'targetDataframe': 'vueEditTranslatedRecordsOfGridDataframe',
                                                                    'namedParamKey': 'id', 
                                                                    'sourceDataframe': 'vueGridOfTranslatedTextDataframe', 
                                                                    'fieldName':'translatedText',
                                                                    'key': 'Id'},
                                                                    {'targetDataframe': 'vueEditTranslatedRecordsOfGridDataframe',
                                                                    'namedParamKey': 'Key',
                                                                    'sourceDataframe': 'vueGridOfTranslatedTextDataframe',
                                                                    'fieldName':'translatedText',
                                                                    'key': 'Key'}, 
                                                                    ]);
                           params['targetLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','targetLanguage');
                           params['sourceLanguage'] =excon.getFromStore('vueGridOfTranslatedTextDataframe','sourceLanguage'); 
                          """
        doAfterRefresh = """excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','textBeforeEditing',response.persisters.translatedText.text.value);"""
        hql = """select  translatedText._key as Key, translatedText.text as Text, nonTranslatedText.text as SourceText from Text translatedText, Text nonTranslatedText where translatedText._key =:Key and translatedText._key = nonTranslatedText._key and nonTranslatedText.language =:sourceLanguage and translatedText.language =:targetLanguage"""
        addFieldDef = [
                "translatedText._key":[widget:"InputWidgetVue"
                                       ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' disabled """,
                                       readOnly: true,],
                "nonTranslatedText.text":[widget:"InputWidgetVue",attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' disabled """,readOnly: true],
                "translatedText.text":[widget:"TextAreaWidgetVue"
                                       ,attr: """outlined background-color='#EBF9FF !important' color='#2AB6F6' """,
                ]
        ]
        dataframeButtons = [save: [name: "save",type: "button",attr: """style='background-color:#1976D2; color:white;' """,script: """ this.saveEditedRecordOfTranslatedText();""","cssGridValues":['xs':'12', 'sm':'12', 'md':'2', 'lg':'2', 'xl':'2']],
                            googleTranslate: [name: "googleTranslate",type: "button",attr: """style='background-color:#1976D2; color:white;' """,script: """ this.googleTranslateForEachRecord();""","cssGridValues":['xs':'12', 'sm':'12', 'md':'4', 'lg':'4', 'xl':'4']],
                            restore: [name: "restore",type: "button",attr:"""style='background-color:#1976D2; color:white;' """,script: """this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();""", "cssGridValues":['xs':'12', 'sm':'12', 'md':'2', 'lg':'2', 'xl':'2']],
        ]
        currentFrameLayout = ref("vueEditTranslatedRecordsOfGridDataframeLayout")
    }
    vueDeleteSourceRecordsOfGridDataframe(DataframeVue){bean->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueDeleteSourceRecordsOfGridDataframe']
        saveButton = false
        hql = "select text.id from Text text where text.id=:id"
        currentFrameLayout =ref("vueDeleteSourceRecordsOfGridDataframeLayout")
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
        dataframeButtons = [register: [name: "register",type: "button", showAsDialog: true, attr:"""style='background-color:#1976D2; color:white;' """, refDataframe: ref("vueElintegroRegisterDataframe"), tooltip: [message: 'Register'], "cssGridValues": ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']],
                            login: [name: "login",type:"button",showAsDialog: true,attr:"""style='background-color:#1976D2; color:white;' """,refDataframe: ref("vueElintegroLoginTabDataframe"),tooltip: [message: 'Login'], "cssGridValues":['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']]
        ]
        currentFrameLayout =ref("vueDialogBoxForNotLoggedInUserDataframeLayout")
    }

}