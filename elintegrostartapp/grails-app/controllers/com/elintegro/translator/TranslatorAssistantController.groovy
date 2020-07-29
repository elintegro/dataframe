package com.elintegro.translator

import com.elintegro.gc.FileUploadController
import com.elintegro.ref.Language
import grails.converters.JSON
import grails.util.Holders


class TranslatorAssistantController {
    def translatorService

    def saveProjectData() {
        def param = request.getJSON()
        println(param)
        Project project = new Project()
        project.name = param.vueCreateProjectForTranslationDataframe_project_name
        project.sourceLanguage = param.vueCreateProjectForTranslationDataframe_project_sourceLanguage.ename
        project.sourceFile = param.vueCreateProjectForTranslationDataframe_project_sourceFile
        project.save(flush: true)
        def resultData = [sucess: true, newData: project, params: project]
        render(resultData as JSON)
    }

    def fileUpload() {
        def projectId = params.allParams
        Project project = Project.findById(projectId)
        String projectName = project.name
        String language = project.sourceLanguage
        String fileName = project.sourceFile
        def result = new FileUploadController().ajaxFileSaveWithParams(params, projectName)
        translatorService.loadTexts(fileName, language, projectName)
        render(success: true)

    }

    def addLanguage() {
        def selectedLanguage = request.getJSON()
        Project project = Project.findById(selectedLanguage.projectId)
        println(selectedLanguage)
        for (item in selectedLanguage.vueTranslatorDataframe_project_languages) {
            if (item.ename != selectedLanguage.vueTranslatorDataframe_project_sourceLanguage) {
                Text text = new Text()
                text.project = project
                text.language = item.ename
                text.save(flush: true)
            }
        }

        render(sucess: true)
    }

    def translateWithGoogle() {
        def param = request.getJSON()
        translatorService.translationWithGoogle(param.projectId, param.sourceLanguage, param.targetLanguage)
        render(success: true)

    }

    def downloadTargetFile() {
        def projectDetails = params.id
        String[] str = projectDetails.split('(?=[A-Z])')
        def projectId = str[0]
        def targetLanguage = str[1]
        Language language = Language.findByEname(targetLanguage)
        Project project = Project.findById(projectId)
        def fileName = "messages_" + language.code + ".properties"
        def fileLocation = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${project.name}" + "/" + fileName
        File targetFile = new File(fileLocation)
        def  targetLabels = []
        def translatedRecords = Text.findAllByProjectAndLanguage(project, targetLanguage)
        for(item in translatedRecords) {
            if (item._key != null && item.text != null) {
                targetLabels.add(item._key + "=" + item.text)
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
        for(int i = 0; i<targetLabels.size();i++) {
            String val =  targetLabels.get(i);
            writer.write(val+"\n");
        }
        writer.close();
        if (targetFile.exists()) {
            try {
                render(contentType: 'application/octet-stream', file: targetFile, fileName: fileName, encoding: "UTF-8")
            }
            catch (Exception e) {
                log.debug("Error downloading file" + e)
            }

        } else {
            log.error("Such file +$fileName+ doesn't exist.")
        }
    }
     def translateEachRecordWithGoogle(){
         def param = request.getJSON()
         Language language = Language.findByEname(param.sourceLanguage)
         String sourceLanguageCode = language.code
         Language language1 = Language.findByEname(param.targetLanguage)
         String targetLanguageCode = language1.code
         Project project = Project.findById(param.projectId)
         Text text = Text.findByProjectAndLanguageAnd_key(project,param.sourceLanguage,param.vueEditTranslatedRecordsOfGridDataframe_text__key)
         def translatedText = translatorService.translateFromGoogle(sourceLanguageCode, targetLanguageCode, text.text)
         def resultData = [success: true,translatedText:translatedText]
         render(resultData as JSON)
     }

}

