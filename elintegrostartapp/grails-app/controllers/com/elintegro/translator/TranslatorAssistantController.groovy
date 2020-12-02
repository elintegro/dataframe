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
    def fileValidationService

    def saveProjectData() {
        def param = request.getJSON()
        Project projectAlreadyExist = Project.findByNameAndSourceLanguageAndSourceFile(param.persisters.project.name.value, param.persisters.project.sourceLanguage.value.ename, param.persisters.project.sourceFile.value[0].fileName)
        if (projectAlreadyExist == null) {
            Project projectNameAlreadyTaken = Project.findByName(param.persisters.project.name.value)
            if (projectNameAlreadyTaken != null && projectNameAlreadyTaken.name == param.persisters.project.name.value) {
                def result = [success: false, alert_type: "error", msg: "Project name is already taken."]
                render(result as JSON)
            } else {
                def currentUser = springSecurityService.currentUser
                Project project = new Project()
                project.name = param.persisters.project.name.value
                project.sourceLanguage =  param.persisters.project.sourceLanguage.value.ename
                project.sourceFile =  param.persisters.project.sourceFile.value[0].fileName
                if (currentUser) {
                    project.addToUsers(currentUser)
                }

                project.save(flush: true)
                def resultData = [success: true, newData: project, params: project,msg:""]
                render(resultData as JSON)
            }
        }
        else{
            def result = [success:false,alert_type:"error",msg:"This project is already created."]
            render (result as JSON)
        }
    }

    def fileUpload() {
        def projectId = params.params
        Project project = Project.findById(projectId)
        String projectName = project.name
        String sourceLanguage = project.sourceLanguage
        String fileName = project.sourceFile
        def result = new FileUploadController().ajaxFileSaveWithParams(params, projectName)
        def validateEnglish = fileValidationService.validateSourceFile(projectName,fileName,sourceLanguage)
        render (validateEnglish as JSON)

    }

    def addLanguage() {
        def selectedLanguage = request.getJSON()
        Project project = Project.findById(selectedLanguage.projectId)
        println(selectedLanguage)
        for (item in selectedLanguage.transits.notSelectedLanguages.value) {
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
        translatorService.translationWithGoogle(param.projectId, param.sourceLanguage, param.targetLanguage, session)
        render(success: true)

    }
    def intermediateRequest(){
        def totalRecordsToTranslate =  session.getAttribute("TA_NUMBER_OF_RECORDS_TO_TRANSLATE")
        def translatedRecords = session.getAttribute("TA_NUMBER_OF_TRANSLATED_RECORDS")
        def progress
        if(translatedRecords!=0 && translatedRecords!=null && totalRecordsToTranslate !=0 && totalRecordsToTranslate!=null){
         progress = (translatedRecords / totalRecordsToTranslate) * 100
        }
        else{
            progress = 0
        }
        render progress
    }


    def downloadTargetFile() {
        def currentUser = springSecurityService.currentUser
        if(currentUser){
                def projectDetails = params.id
                String[] str = projectDetails.split('(?=[A-Z])')
                def projectId = str[0]
                def targetLanguage = str[1]
                Language language = Language.findByEname(targetLanguage)
                Project project = Project.findById(projectId)
                def fileName = "messages_" + language.code + ".properties"
                translatorService.prepareTargetFile(projectId, targetLanguage, fileName)
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
        }else{
            render(view: '/error')

        }
    }
    def saveProjectDetailsInSessionForNotLoggedInUser(){
        def param = request.getJSON()
        session.setAttribute("CURRENTLY_SELECTED_PROJECT",param.projectDetails)
        def resultData = [success:true]
        render(resultData as JSON)
    }
    def getProjectDetailsFromSessionAfterLoggedIn(){
        def currentUser = springSecurityService.currentUser
        def projectDetails = session.getAttribute("CURRENTLY_SELECTED_PROJECT");
        Project project = Project.findByIdAndName(projectDetails.projectId,projectDetails.Name)
        if(currentUser){
            project.addToUsers(currentUser)
            project.save(flush:true)
        }

        def result = [success: true,projectDetails:projectDetails,alert_type:"success",msg:"Please click Download to download your file(s)."]
        render(result as JSON)
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
        def currentUser = springSecurityService.currentUser
        if (currentUser) {
            def fileCompression = translatorService.compressAllFilesInZIP(param)
            render(fileCompression as JSON)
        }
        else{
            render(view: '/error')
        }
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

