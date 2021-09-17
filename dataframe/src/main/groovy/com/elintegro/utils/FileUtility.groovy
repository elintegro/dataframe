package com.elintegro.utils

import org.grails.io.support.SpringIOUtils

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class FileUtility {


    public static String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".")
        if (lastIndexOf == -1) {
            return "" // empty extension
        }
        return fileName.substring(lastIndexOf).toLowerCase()
    }
    public static boolean deleteTempFile(tempFile){
        if (tempFile && tempFile.exists()){
            tempFile.delete()
            return true
        } else{
            return false
        }
    }

    public static boolean deleteTempFile(List<File> files){
        files.each {
            deleteTempFile(it)
        }
    }

    public static void fileCopy(File input, File output){
        SpringIOUtils.copy(input, output)
    }

    public static void createZipFile(String inputDir, String zipFilePath){
        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(zipFilePath))
        new File(inputDir).eachFile() { file ->
            if (file.isFile()){
                zipFile.putNextEntry(new ZipEntry(file.name))
                def buffer = new byte[file.size()]
                file.withInputStream {
                    zipFile.write(buffer, 0, it.read(buffer))
                }
                zipFile.closeEntry()
            }
        }
        zipFile.close()
    }

    public static boolean createDir(List<String> paths){
        paths.each {path ->
            createDir(path)
        }
        return true
    }

    public static boolean createDir(String path){
        File dir = new File(path)
        if (!dir.exists()){
            return dir.mkdirs()
        }
    }

    public static String getFileType(String imageName){
        String ext = getFileExtension(imageName)
        String fileType = ext.replace(".", '')
        return fileType
    }

    public static String getFileNameWithoutExtension(String name) {
        String fileName = name.replaceFirst("[.][^.]+\$", "")
        return fileName

    }

    public static appendToFilename(String filename, String stringToCon, String delimiter = ""){
        int dotIndex = filename.lastIndexOf(".")
        return filename.substring(0, dotIndex) + delimiter+ stringToCon + filename.substring(dotIndex)
    }

    public static String getFileName(File file){
        return file.getName()
    }

}
