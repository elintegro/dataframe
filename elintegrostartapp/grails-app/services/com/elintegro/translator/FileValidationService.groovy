package com.elintegro.translator

import grails.util.Holders

import java.util.regex.Matcher
import java.util.regex.Pattern

class FileValidationService{
    boolean validateSourceFile(String projectName,String fileName) {
        def file = Holders.grailsApplication.config.images.storageLocation + "/images/" + "${projectName}" + "/" + fileName
        File sourceFile = new File(file)
        def lines = sourceFile.readLines()
        def count = 0
        lines.each { record ->
            if(count==0) {
                    String[] keyValue = record.split("=")
                    if (keyValue.length == 2) {
                        String key = keyValue[0]
                        // Pattern regExPattern = Pattern.compile(/([A-Z]|[a-z]|[0-9]|(\.|\-|\_)|\s)+/, Pattern.CASE_INSENSITIVE);
                        Pattern regExPattern = Pattern.compile(/^[a-zA-Z0-9\u0024@\u0024!%*?&#^-_. +]+\u0024/, Pattern.CASE_INSENSITIVE);
                        Matcher matcher = regExPattern.matcher(key);
                        if (!matcher.find()) { //Not English!
                            return count = 1
                        } else {
                            return true
                        }
                    }
                }
            }
        if(count==1){
            return false
        }else{
            return true
        }

    }
}