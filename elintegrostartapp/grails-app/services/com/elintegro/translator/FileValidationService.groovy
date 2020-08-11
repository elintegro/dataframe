package com.elintegro.translator

import grails.util.Holders

import java.util.regex.Matcher
import java.util.regex.Pattern

class FileValidationService{
    boolean validateSourceFile(String projectName,String fileName) {
        def file = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${projectName}" + "/" + fileName
        File sourceFile = new File(file)
        def lines = sourceFile.readLines()
            lines.each { record ->
                String[] keyValue = record.split("=")
                if (keyValue.length == 2) {
                    String key = keyValue[0]
                   // Pattern regExPattern = Pattern.compile(/([A-Z]|[a-z]|[0-9]|(\.|\-|\_)|\s)+/, Pattern.CASE_INSENSITIVE);
                    Pattern regExPattern = Pattern.compile(/[a-zA-Z0-9.-_]+/, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = regExPattern.matcher(key);
                    if (!matcher.find()) { //Not English!
                        return false
                    }else{
                        return true
                    }
                }
            }

    }
}