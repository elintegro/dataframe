package com.elintegro.translator

class TranslatorAssistantController {

    def saveProjectData(){
        def param = request.getJSON()
        println(param)
        Project project = new Project()
        project.name = param.vueCreateProjectForTranslationDataframe_project_name
        project.sourceLanguage = param.vueCreateProjectForTranslationDataframe_project_sourceLanguage.ename
        project.sourceFile= param.vueCreateProjectForTranslationDataframe_project_sourceFile
        project.save(flush:true)
    }
    def addLanguage(){
        def selectedLanguage = request.getJSON()
        println(selectedLanguage)
        for(item in selectedLanguage.vueTranslatorDataframe_target_language){
            Text text = new Text()
            text.language = item.ename
            text.save(flush:true)
        }

     render(sucess:true)
    }
}
