package com.elintegro.translator

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
    def translate() {
        def projectName = "dom"
        def sourceLanguage = "fr"
        def targetLanguage = "en"
        Project project = Project.findByName(projectName)
        def sourceRecords = Text.findAllByProjectAndLanguage(project,"French")
        sourceRecords.each {  sourceRecord ->
            def translatedText= translateFromGoogle(sourceLanguage,targetLanguage,sourceRecord.text)
            Text text1 = new Text()
            text1._key = sourceRecord._key
            text1.text = translatedText
            text1.language = "English"
            text1.project = project
            text1.save(flush:true)
        }
    }
    def  translateFromGoogle(String langFrom, String langTo, String text)throws IOException{
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
