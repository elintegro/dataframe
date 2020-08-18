package com.elintegro.translator

import grails.util.Holders

import java.util.regex.Matcher
import java.util.regex.Pattern

class FileValidationService{
    def translatorService
    def validateSourceFile(String projectName,String fileName,String sourceLanguage) {
        def file = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${projectName}" + "/" + fileName
        File sourceFile = new File(file)
        def lines = sourceFile.readLines()
        def N = 0 //not valid records
        def V = 0 // valid records
        lines.each { record ->
            String[] keyValue = record.split("=")
            if (keyValue.length == 2) {
                String key = keyValue[0]
                String text = keyValue[1]
                Pattern regExPattern = Pattern.compile(/^[a-zA-Z0-9\u0024@\u0024!%*&#^-_. +]+\u0024/, Pattern.CASE_INSENSITIVE);
                Matcher matcher = regExPattern.matcher(key);
                if (!matcher.find()) { //Not English!
                    N++
                } else {
                    translatorService.loadTexts(key, text, sourceLanguage, projectName)
                    V++
                }
            }
        }
        def msg
        def alert_type
        if (V == 0) {//if all records are invalid
            msg = "“ ${fileName} is not in valid format. No records were uploaded. Please check that all the records in the file has the following format: \n" +
                    "\n" +
                    "KEY=TEXT\n" +
                    "\n" +
                    "Where key should have english letters and some special characters like . - _"
            alert_type = "error"
            return [msg: msg, alert_type: alert_type]
        } else if ( V == N+V) { //if all record are valid
            msg = "“File ${fileName} was successfully uploaded. ${V} records are saved."
            alert_type = "success"
            return [msg: msg, alert_type: alert_type]
        } else {//if part of the records are valid
            msg = "“${N} records out of ${N + V} were excluded. Please check that all the records in the file has the following format: \n" +
                    "\n" +
                    "KEY=TEXT\n" +
                    "\n" +
                    "Where key should have English letters and some special characters like . - _"
            alert_type = "info"
            return [msg: msg, alert_type: alert_type]

        }
    }
}