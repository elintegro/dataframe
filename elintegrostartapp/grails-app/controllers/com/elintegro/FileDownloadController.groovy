package com.elintegro

import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.elintegrostartapp.hr.Images
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
                response.setContentType("application/octet-stream")
                response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
                def outputStream = response.getOutputStream()
                outputStream << file.bytes
                outputStream.flush()
                outputStream.close()


//                render(contentType: 'application/pdf', file: file, fileName: fileName, encoding: "UTF-8")
            }
            catch(Exception e){
                log.debug("Error downloading file" + e)
            }

        }
        else {
            log.error("Such file +$fileName+ doesn't exist.")
        }
    }

    def imagePreview(){
        def params = request.getJSON()
        def applicantId = params.id
        Set<Images> images = Application.findById(applicantId).images
        println(images)
        def imageName = images[0].name
        def imageUrl = Holders.grailsApplication.config.images.storageLocation + "/images/" + imageName
        def imageFile = new File(imageUrl)
        if (imageFile.exists()) {
            try {
                def extension = imageName - ~/.*(?<=\.)/
                response.setContentType("application/" + extension)
                response.setContentLength(imageFile.size().toInteger())
                OutputStream out = response.getOutputStream();
                out.write(imageFile.bytes);
                out.close();
            }catch(Exception e){
                log.error("Image couldn't be generated"+e)
            }
        }
        else{
            log.error("Image doesn't exist")
        }
    }
}