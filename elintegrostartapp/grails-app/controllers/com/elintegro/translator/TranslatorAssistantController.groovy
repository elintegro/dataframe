package com.elintegro.translator

import com.elintegro.gc.FileUploadController
import com.elintegro.ref.Language
import grails.converters.JSON
import grails.util.Holders

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class TranslatorAssistantController {
    def translatorService
    def springSecurityService

    def saveProjectData() {
        def param = request.getJSON()
        def currentUser = springSecurityService.currentUser
        println(currentUser)
        Project project = new Project()
        project.name = param.vueCreateProjectForTranslationDataframe_project_name
        project.sourceLanguage = param.vueCreateProjectForTranslationDataframe_project_sourceLanguage.ename
        project.sourceFile = param.vueCreateProjectForTranslationDataframe_project_sourceFile
        if(currentUser){
            project.addToUsers(currentUser)
        }

        project.save(flush: true)
        def resultData = [sucess: true, newData: project, params: project]
        render(resultData as JSON)
    }
    def userInfo(){
        def currentUser = springSecurityService.currentUser
        if(currentUser){
        render(currentUser as JSON)
    }
        else{
            render(success:false)
        }
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
        prepareTargetFile(param)
        render(success: true)

    }
    def intermediateRequest(){
        def param = request.getJSON()
        Project project = Project.findById(param.projectId)
        def totalRecords = Text.countByProjectAndLanguage(project,param.sourceLanguage)
        def translatedRecords = Text.countByProjectAndLanguage(project,param.targetLanguage)
        def progress = (translatedRecords / totalRecords)*100
        render progress
    }
    def prepareTargetFile(param){
        Language language = Language.findByEname(param.targetLanguage)
        Project project = Project.findById(param.projectId)
        def fileName = "messages_" + language.code + ".properties"
        def fileLocation = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${project.name}" + "/" + fileName
        File targetFile = new File(fileLocation)
        def  targetLabels = []
        def translatedRecords = Text.findAllByProjectAndLanguage(project, param.targetLanguage)
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
    def translateNewlyAddedRecord(){
        def param = request.getJSON()
        Language language = Language.findByEname(param.vueAddNewRecordForCurrentProjectDataframe_project_sourceLanguage)
        String sourceLanguageCode = language.code
        Language language1 = Language.findByEname(param.targetLanguage)
        String targetLanguageCode = language1.code
        def translatedText = translatorService.translateFromGoogle(sourceLanguageCode, targetLanguageCode, param.vueAddNewRecordForCurrentProjectDataframe_project_sourceText )
        def dataAfterTranslation = [targetLanguage:param.targetLanguage, text:translatedText]
        def resultData = [success: true, newData: [textToTranslate:dataAfterTranslation],params:param]
        render(resultData as JSON)
    }
    def saveNewlyAddedRecord(){
        def param = request.getJSON()
        println(param)
        Project project = Project.findById(param.vueAddNewRecordForCurrentProjectDataframe_project_id)
        Text text = Text.findByProjectAnd_keyAndTextAndLanguage(project,param.vueAddNewRecordForCurrentProjectDataframe_key,param.vueAddNewRecordForCurrentProjectDataframe_project_sourceText,param.vueAddNewRecordForCurrentProjectDataframe_project_sourceLanguage)
        if(text == null) {
            Text newText = new Text()
            newText.project = project
            newText._key = param.vueAddNewRecordForCurrentProjectDataframe_key
            newText.text = param.vueAddNewRecordForCurrentProjectDataframe_project_sourceText
            newText.language = param.vueAddNewRecordForCurrentProjectDataframe_project_sourceLanguage
            newText.save(flush: true)
        }
        for(item in param.vueAddNewRecordForCurrentProjectDataframe_textToTranslate_items){
            Text text1 = new Text()
            text1.project = project
            text1.language = item.targetLanguage
            text1._key = param.vueAddNewRecordForCurrentProjectDataframe_key
            text1.text = item.text
            text1.save(flush: true)
        }
        def resultData = [success: true, params: param]
        render(resultData as JSON)
    }
    def compressAllFilesInZip() {
        def param = request.getJSON()
        def projectId = param.projectId
        Project project = Project.findById(projectId)
        def outputFileName = "${project.name}"+".zip"
        def fileLocation = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${project.name}" + "/"
        def zipFileLocation = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${project.name}" + "/"+outputFileName
        createZipFile( fileLocation,zipFileLocation)
        def resultData = [success: true, zipFileName:outputFileName,projectId:param.projectId]
        render(resultData as JSON)
    }

    public static void createZipFile(String inputDir, String zipFilePath){
        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(zipFilePath))
        new File(inputDir).eachFile() { file ->
            if (file.isFile()){
                zipFile.putNextEntry(new ZipEntry(file.name))
                def buffer = new byte[file.size()]
                file.withInputStream {
                    zipFile.write(buffer, 0, it.read(buffer))
                }
                zipFile.closeEntry()
            }
        }
        zipFile.close()
    }
    def downloadZipFile(){
        def projectId = params.id
        Project project = Project.findById(projectId)
        def zipFileName = "${project.name}"+".zip"
        def zipFileLocation = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${project.name}" + "/"+ zipFileName
        File zipFile = new File(zipFileLocation)
        if (zipFile.exists()) {
            try {
                render(contentType: 'application/octet-stream', file: zipFile, fileName: zipFileName, encoding: "UTF-8")
            }
            catch (Exception e) {
                log.debug("Error downloading file" + e)
            }

        } else {
            log.error("Such file +$zipFileName+ doesn't exist.")
        }


    }
    def deleteRecord(){
        def params = request.getJSON()
        String recordId = params["id"]
        String projectId =params["projectId"]
        Project project = Project.findById(projectId)
        Text text = Text.findByProjectAndId(project,recordId)
        text.delete(flush: true)
        render(success:true)


    }


}

