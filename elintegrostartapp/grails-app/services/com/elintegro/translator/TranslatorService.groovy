package com.elintegro.translator

import com.elintegro.ref.Language
import grails.util.Holders

class TranslatorService {

    def loadTexts(String fileName, String language, String projectName){
        def file = Holders.grailsApplication.config.images.storageLocation+"/images/"+fileName
        File sourceFile = new File(file);
        def lines = sourceFile.readLines()
        def targetLabels = []
        lines.each { record ->
            def a =validateTextRecord( record,fileName,language,projectName)
            if(a==true) {
                String[] keyValue = record.split("=")
                if (keyValue.length == 2) {
                    String key = keyValue[0]
                    String sourceText = keyValue[1]
                    Project project = Project.findByName(projectName)
                    Text text = new Text()
                    text._key = key
                    text.text = sourceText
                    text.project = project
                    text.language = language
                    text.save(flush: true)
                }
            }
        }
    }
    boolean validateTextRecord(String record,String fileName, String language, String projectName) {
        if (record?.indexOf("=") > 0){
            return true
        }
        log.warn("Translating project ${projectName} from ${language} , while loading file ${fileName}: the following line is corrupted: ${record} . The record will not be inserted into the database!")
        return false
    }
    def translationWithGoogle(def projectId, def sourceLanguage, def targetLanguage) {
        Language language = Language.findByEname(sourceLanguage)
        String sourceLanguageCode = language.code
        Language language1 = Language.findByEname(targetLanguage)
        String targetLanguageCode = language1.code
        Project project = Project.findById(projectId)
        def sourceRecords = Text.findAllByProjectAndLanguage(project,sourceLanguage)
            sourceRecords.each { sourceRecord ->
                def translatedText = translateFromGoogle(sourceLanguageCode, targetLanguageCode, sourceRecord.text)
                Text text = new Text()
                text._key = sourceRecord._key
                text.text = translatedText
                text.language = targetLanguage
                text.project = project
                text.save(flush: true)
            }


    }
    def translateFromGoogle(String langFrom, String langTo, String text)throws IOException{
        String urlStr = "https://script.google.com/macros/s/AKfycbxEPInMGiFG6_a7VRgeaN1MbtfvGRZmjIm9A3g7-yC0vyGoIAYM/exec" +
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




}
