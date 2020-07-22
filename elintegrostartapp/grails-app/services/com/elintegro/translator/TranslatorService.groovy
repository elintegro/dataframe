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




}
