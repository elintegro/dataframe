package com.elintegro

import grails.util.Holders

class FileDownloadController {

    def index() {}

    def fileDownload() {
        def params = request.getJSON()
        def fileName = params.fileName
        def fileLocation = params.fileLocation
        def actualFile = fileLocation + fileName
        File newFile = new File(actualFile)
        def filePath = newFile.getAbsolutePath() //I am saving files on tomcat.
        def file = new File(filePath)
        if (file.exists()) {
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
            try {
//                response.setContentType("application/octet-stream")
//                response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
//                def outputStream = response.getOutputStream()
//                outputStream << file.bytes
//                outputStream.flush()
//                outputStream.close()


                render(contentType: 'application/pdf', file: file, fileName: fileName, encoding: "UTF-8")
            }
            catch(Exception e){
                log.debug("Error downloading file" + e)
            }

        }
        else {
            log.error("Such file +$fileName+ doesn't exist.")
        }
    }
}