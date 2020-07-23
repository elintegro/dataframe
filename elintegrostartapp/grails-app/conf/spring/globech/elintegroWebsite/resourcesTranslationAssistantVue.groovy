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
                translation:[name:"translate",type: "link",attr: """style='background-color:#1976D2; color:white;' """,route: true,routeIdScript: 0,refDataframe: ref("vueTranslatorDataframe"),flexGridValues:['xs12', 'sm12', 'md10', 'lg10', 'xl10'] ],
                createProject:[name: "createProject",type: "button",attr: """style='background-color:#1976D2; color:white;' """,showAsDialog: true,refDataframe: ref("vueCreateProjectForTranslationDataframe"),flexGridValues:['xs12', 'sm12', 'md2', 'lg2', 'xl2'] ]
        ]
        childDataframes = ['vueTranslatorDataframe','vueCreateProjectForTranslationDataframe','vueGridOfTranslatedTextDataframe']
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
        doBeforeRefresh = """var projectDetails = excon.getFromStore('vueTranslatorAssistantDataframe','vueTranslatorAssistantDataframe_project_list')
                         allParams['projectId']= projectDetails.id """
        hql = """select  project.name , project.sourceLanguage  from Project project where project.id=:projectId """
        addFieldDef =[
                "project.name":[widget:"InputWidgetVue",readOnly:true],
                "project.sourceLanguage":[widget:"InputWidgetVue",readOnly:true],
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
                        "flexGridValues":['xs12', 'sm12', 'md1', 'lg1', 'xl1'],
                ],
                "project.language":[
                        widget: "ListWidgetVue"
                        , hql: """select text.language as language from Text text  where project_id = :projectId group by language"""
                        ,"displayMember":"language"
                        ,internationalize: true
                        ,valueMember:"projectId"
                        ,OnClick:"translatedText(item)"

                ],
        ]
        childDataframes = ['vueGridOfTranslatedTextDataframe']
        currentFrameLayout= ref("vueElintegroTranslatorDataframeLayout")

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
        addFieldDef = ["translatedText":[
                                     widget: "GridWidgetVue"
                                     ,name: "translatedText"
                                     , hql             : """select text._key as Key, text.text as Text from Text text where project_id =:projectId and text.language = :targetLanguage"""
                                     , gridWidth       : 820
                                     , showGridSearch  : true
                                     , internationalize: true
                                     , sortable        : true]]
        dataframeButtons = [translateWithGoogle: [name: "translateWithGoogle",type: "button",attr: """style='background-color:#1976D2; color:white;' v-show = 'vueGridOfTranslatedTextDataframe_button_translateWithGoogle' """,flexGridValues:['xs12', 'sm12', 'md0', 'lg0', 'xl0'],script: """this.retrieveTranslatedText()"""]
        ]
        currentFrameLayout= ref("vueGridOfTranslatedTextDataframeLayout")

    }

}