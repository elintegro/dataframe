package com.elintegro

import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.elintegrostartapp.hr.Images
import grails.util.Holders

class FileDownloadController {

    def index() {}

    def fileDownload() {
        def fileName = params.id+"."+params.format
        def fileUrl = getUrlByExtensionFor(fileName)
        def file = new File(fileUrl)
        if (file.exists()) {

            try {
                render(contentType: 'application/octet-stream', file: file, fileName: fileName, encoding: "UTF-8")
            }
            catch (Exception e) {
                log.debug("Error downloading file" + e)
            }

        } else {
            log.error("Such file +$fileName+ doesn't exist.")
            render ""
        }
    }

    private String getUrlByExtensionFor(String fileName){

        if(!fileName) return ""

        def extension = fileName - ~/.*(?<=\.)/
        if(extension == 'pdf'){
            return Holders.grailsApplication.config.images.defaultImagePathForPdf
        }
        else if(extension == 'xlsx' || extension == 'xlsm' || extension == 'xlsb' || extension == 'xltx'){
            return Holders.grailsApplication.config.images.defaultImagePathForExcel
        }
        else if(extension == 'doc' || extension == 'docx'){
            return Holders.grailsApplication.config.images.defaultImagePathForDocFile
        }
        else if(extension == 'csv' || extension == 'CSV'){
            return Holders.grailsApplication.config.images.defaultImagePathForCsvFile
        } else { // For actual images
            return Holders.grailsApplication.config.images.storageLocation+"/images/" + fileName
        }
    }

    def imagePreview() {

        def applicantId = params.id
        Set<Images> images = Application.findById(applicantId).images
        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                def imageName = images[i].name
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
                    } catch (Exception e) {
                        log.error("Image couldn't be generated" + e)
                    }
                } else {
                    log.error("Image file doesn't exist")
                }
            }
        }
        else{
            log.error("There is no image for this applicant.")
        }
    }
}