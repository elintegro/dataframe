package com.elintegro

class FileDownloadController {

    def index() {}

    def fileDownload() {
        def fileName = params.fileName
        def fileLocation = params.fileLocation
        def actualFile = fileLocation + fileName
        File newFile = new File(actualFile)
        def filePath = newFile.getAbsolutePath() //I am saving files on tomcat.
        def file = new File(filePath)
        if (file.exists()) {
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "filename=${file.getName()}")
            response.setCharacterEncoding("UTF-8")
            def outputStream = response.getOutputStream()
            outputStream << file.bytes
            outputStream.flush()
            outputStream.close()

//            render(contentType: 'application/octet-stream', file: file, fileName: fileName, encoding: "UTF-8")
        } else {
            //handle file not found messages.
        }
    }
}