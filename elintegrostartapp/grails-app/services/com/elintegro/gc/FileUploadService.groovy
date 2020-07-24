/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gc

import grails.converters.JSON
import grails.util.Holders
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletRequest
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FileUploadService {

    def awsS3Service
    def serviceMethod() {

    }
    private static final String SEPARATOR = "/"

    public def imageSave(def inputFile, HttpServletRequest request,String projectName){

//        String filePath = request.getServletContext().getRealPath(Holders.grailsApplication.config.images.storageLocation)
        String filePath = Holders.grailsApplication.config.images.storageLocation + "/" + Holders.grailsApplication.config.images.imageDirectory + "/" + "${projectName}"
//        Path folder = Paths.get(System.getProperty("upload.location"));
        def testPath = request.getServletContext()
        File saveLocation = new File(filePath)
        String filesAbsolutePath = saveLocation.getAbsolutePath()
        boolean saveToLocal = Holders.grailsApplication.config.images.saveLocation.local
        boolean saveToS3 = Holders.grailsApplication.config.images.saveLocation.s3

        if( !saveLocation.exists() ){
            if(saveLocation.mkdirs()){
                log.debug("SUCCESS: temporary storage location created in local")
            }else{
                log.debug( "FAILED: couldn't create temporary storage location.")
            }
        }

        ArrayList<Map> s3Url1 = [] //TODO check if the arrayList decraration is correct
        ArrayList<Map> localUrl1 = []
        // Store file
        if(!inputFile?.isEmpty()){
            inputFile.each{
                String docName = generateFileName(it)
                if(saveToS3){
                    File multiPartToFile = convertMutlipartToNormalFile(it)
                    String returnUrl = imageSaveToS3(multiPartToFile,"${docName}")
//                    fl.delete()
                    s3Url1.add([url:returnUrl, name:docName])
                }
                if (saveToLocal){
//                    String returnUrl = imageSaveToS3(multiPartToFile,"${docName}")
//                    String path = "${filesAbsolutePath}/${docName}"
                   // File fl = new File(path)
//                    it.transferTo(fl) //Todo make it working for saving files locally
                    File file = new File(filePath, "${docName}");

                    try  {
                        InputStream input = it.getInputStream()
                        Files.copy(input, file.toPath());
                    }catch(Exception e){
                        log.debug(e.getMessage())
                    }
                    localUrl1.add([url:  "${saveLocation}/${docName}", name: docName])
//                    s3Url1.add([url:returnUrl, name:docName])
                }

            }
            return [localUrlList:localUrl1, s3UrlList:s3Url1]
        }else{
            log.debug("File: ${inputFile.inspect()} is empty")
            return null
        }
    }
    public static File convertMutlipartToNormalFile(MultipartFile file) throws IOException {
        File fl = new File(file.getOriginalFilename());
        fl.createNewFile();
        FileOutputStream fos = new FileOutputStream(fl);
        fos.write(file.getBytes());
        fos.close();
        return fl;
    }
    public String imageSaveToS3(def inputFile, String filePath){
        String bucketName = Holders.grailsApplication.config.aws.s3.buckets.imageUpload
        String url = awsS3Service.uploadImage(inputFile, filePath, bucketName)
        return url
    }

    private static String generateFileName(def inputFile){
//        Random randomGenerator = new Random()
//        int randomInt = randomGenerator.nextInt(1000000)
//        def docName = randomInt+inputFile?.getOriginalFilename()
        return inputFile?.getOriginalFilename()
    }

    public boolean deleteImageFromLocal(String imageName, String imageUploadPath){
        try{
            if(imageName && imageName!=""){
                def String productImagePath = imageUploadPath+imageName
                log.debug("Image Path" + productImagePath)
                File imageFile = new File(productImagePath)
                imageFile.delete()
            }
        }catch(e){
            log.error("Error when trying to delete the image, exception is : " + e)
        }

        return true
    }
//TODO put correct regex inside contains to check s3Url
    public boolean deleteImageFromS3(String fileName) {
        String bucketName = Holders.grailsApplication.config.aws.s3.buckets.imageUpload

        try{
            if(fileName.contains("https://") && fileName.contains("s3.amazonaws.com")){
                awsS3Service.deleteImageWithS3Url(fileName)
            }else{
                awsS3Service.deleteImageWithFileName(bucketName,fileName)
            }
        }catch(e){
            log.error("Error when trying to delete the image, exception is : "+ e)
        }

        return true
    }
}
