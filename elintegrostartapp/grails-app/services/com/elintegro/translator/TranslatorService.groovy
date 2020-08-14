package com.elintegro.translator

import com.elintegro.ref.Language
import grails.util.Holders
import javax.servlet.http.HttpSession
import org.springframework.web.context.request.RequestContextHolder;


class TranslatorService {


    HttpSession session = RequestContextHolder.currentRequestAttributes().getSession()

    def loadTexts(String fileName, String language, String projectName){
        def file = Holders.grailsApplication.config.images.storageLocation+"/images/"+"${projectName}"+"/"+fileName
        File sourceFile = new File(file);
        if(sourceFile.exists()) {
            try {
                def lines = sourceFile.readLines()
                def targetLabels = []
                lines.each { record ->
                    def a = validateTextRecord(record, fileName, language, projectName)
                    if (a == true) {
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
            }catch(Exception e){
                log.error("File not found exception"+e)
            }
        }
        else {
            log.error("File doesn't exist.")
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




}
