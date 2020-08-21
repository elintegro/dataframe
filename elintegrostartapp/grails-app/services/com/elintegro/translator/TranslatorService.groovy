package com.elintegro.translator

import com.elintegro.ref.Language
import grails.converters.JSON
import grails.util.Holders

import javax.servlet.http.HttpSession
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class TranslatorService {

    def loadTexts(String key,String sourceText, String language, String projectName){

                            Project project = Project.findByName(projectName)
                            Text text = new Text()
                            text._key = key
                            text.text = sourceText
                            text.project = project
                            text.language = language
                            text.save(flush: true)

    }
    boolean validateTextRecord(String record,String fileName, String language, String projectName) {
        if (record?.indexOf("=") > 0){
            return true
        }
        log.warn("Translating project ${projectName} from ${language} , while loading file ${fileName}: the following line is corrupted: ${record} . The record will not be inserted into the database!")
        return false
    }
    def translationWithGoogle(def projectId, def sourceLanguage, def targetLanguage, HttpSession session) {
        Language language = Language.findByEname(sourceLanguage)
        String sourceLanguageCode = language.code
        Language language1 = Language.findByEname(targetLanguage)
        String targetLanguageCode = language1.code
        Project project = Project.findById(projectId)
        def sourceRecords = Text.findAllByProjectAndLanguage(project,sourceLanguage)
        session.setAttribute("TA_NUMBER_OF_RECORDS_TO_TRANSLATE",sourceRecords.size())
        long counter = 0
            for(Text record:sourceRecords){
                if(counter % 5 == 0) {
                    session.setAttribute("TA_NUMBER_OF_TRANSLATED_RECORDS", counter)
                }
                def translatedText = translateFromGoogle(sourceLanguageCode, targetLanguageCode, record.text)
                Text text = new Text()
                text._key = record._key
                text.text = translatedText
                text.language = targetLanguage
                text.project = project
                text.save(flush: true)
                counter++
            }


    }
    def translateFromGoogle(String langFrom, String langTo, String text)throws IOException{
        String urlStr = "https://script.google.com/macros/s/AKfycbzrh674fnfPJsjkfHP5NwvKG5brOg_46-X2a9o9voJ6tgjBtLo0/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        BufferedReader ine = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = ine.readLine()) != null) {
            response.append(inputLine);
        }
        ine.close();
        return response.toString();
    }

    def prepareTargetFile(def projectId , def targetLanguage, def fileName){
        Project project = Project.findById(projectId)
        def fileLocation = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${project.name}" + "/" + fileName
        File targetFile = new File(fileLocation)
        def  targetLabels = []
        def translatedRecords = Text.findAllByProjectAndLanguage(project,targetLanguage)
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
    def compressAllFilesInZIP(param){
        long projectId = param.projectId
        Project project = Project.findById(projectId)
        def counter = 0
        def resultData
        for (item in param.vueTranslatorDataframe_project_language_items) {
            Language language = Language.findByEname(item.language)
            def fileName = "messages_" + language.code + ".properties"
            def text = Text.findAll("from Text text where text.language = ${item.language} and project_id = ${project} and text._key != null and text.text != null ")
            if (text.size() != 0) {
                prepareTargetFile(projectId , item.language,fileName)
                counter = counter + 1
            }
        }
        if (counter >= 2) {
            def outputFileName = "${project.name}" + ".zip"
            def fileLocation = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${project.name}" + "/"
            def zipFileLocation = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${project.name}" + "/" + outputFileName
            createZipFile(fileLocation, zipFileLocation)
            resultData = [success: true, zipFileName: outputFileName, projectId: param.projectId]
             return resultData
        }
        else{
            resultData = [success:false,alert_type:"error",msg:"You must translate your file in 2 or more language to download Zip file."]
            return resultData
        }

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




}
