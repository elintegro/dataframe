package com.elintegro.translator

import com.elintegro.gc.FileUploadController
import grails.converters.JSON


class TranslatorAssistantController {
    def translatorService

    def saveProjectData(){
        def param = request.getJSON()
        println(param)
        Project project = new Project()
        project.name = param.vueCreateProjectForTranslationDataframe_project_name
        project.sourceLanguage = param.vueCreateProjectForTranslationDataframe_project_sourceLanguage.ename
        project.sourceFile= param.vueCreateProjectForTranslationDataframe_project_sourceFile
        project.save(flush:true)
        def resultData = [sucess: true, newData:project, params:project]
        render(resultData as JSON)
    }

    def fileUpload(){
        def projectId = params.allParams
        def result = new FileUploadController().ajaxFileSaveWithParams(params)
        Project project = Project.findById(projectId)
        String projectName = project.name
        String language = project.sourceLanguage
        String fileName = project.sourceFile
        translatorService.loadTexts(fileName,language,projectName)
        render(success:true)

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
 def translate(){
     translatorService.translate()
 }

}
