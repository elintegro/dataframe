package com.elintegro.translator

import grails.util.Holders

class TranslatorService {

    def loadTexts(String fileName, String language, String projectName){
        def file = Holders.grailsApplication.config.images.storageLocation+"/images/"+fileName
        File sourceFile = new File(file);
        BufferedReader br = new BufferedReader(new FileReader(sourceFile));
        String line = null;
        while ( (line = br.readLine()) != null){
            String[] keyValue = line.split("=");
            if(keyValue.length == 2){
                String key = keyValue[0];
                String sourceText = keyValue[1];
                Project project = Project.findByName(projectName)
                Text text = new Text()
                text._key = key
                text.text = sourceText
                text.project = project
                text.language = language
                text.save(flush:true)
            }
        }


    }


}
