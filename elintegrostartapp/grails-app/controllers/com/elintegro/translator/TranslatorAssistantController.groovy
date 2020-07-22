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
        Project project = Project.findById(selectedLanguage.projectId)
        println(selectedLanguage)
        for(item in selectedLanguage.vueTranslatorDataframe_project_languages){
            Text text = new Text()
            text.project = project
            text.language = item.ename
            text.save(flush:true)
        }

     render(sucess:true)
    }
}
