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
        addFieldDef =[
                "target.language":[
                        widget: "ComboboxVue"
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,internationalize: true
                        , search:true
                        ,multiple: true
                        ,valueMember:"id"
                ]
        ]
        currentFrameLayout=ref("vueElintegroTranslatorDataframeLayout")

    }
}