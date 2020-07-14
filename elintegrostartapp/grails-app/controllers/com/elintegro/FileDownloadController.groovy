package com.elintegro

import grails.util.Holders

class FileDownloadController {

    def index() {}

    def fileDownload() {
        def fileName = params.id+"."+params.format
        def fileLocation = Holders.grailsApplication.config.images.storageLocation+"/images/"
        def fileUrl = fileLocation + fileName
        def file = new File(fileUrl)
        if (file.exists()) {

            try {
                render(contentType: 'application/octet-stream', file: file, fileName: fileName, encoding: "UTF-8")
            }
            catch(Exception e){
                log.debug("Error downloading file" + e)
            }

        }
        else {
            log.error("Such file +$fileName+ doesn't exist.")
        }
    }
//    Another method:::
//    fileDownload(){
//        if (file.exists()) {
//            def extension = fileName - ~/.*(?<=\.)/
//            Map fileExtensionContentTypeMap = Holders.getFlatConfig().get('grails.mime.types') as Map
//            String contentType
//            if(fileExtensionContentTypeMap.containsKey(extension))
//            {
//                contentType = fileExtensionContentTypeMap.get(extension)
//            }
//            else {
//                contentType = "application/octet-stream"
//            }
//            response.setContentType(contentType)
//            try {
//                response.setContentType("application/octet-stream")
//                response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
//                def outputStream = response.getOutputStream()
//                outputStream << file.bytes
//                outputStream.flush()
//                outputStream.close()
//
//
//            }
}