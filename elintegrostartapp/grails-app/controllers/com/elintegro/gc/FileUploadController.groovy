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

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.gerf.DataframeController
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured


@Secured(["permitAll"])
class FileUploadController {

    def fileUploadService
    def index() {}

    def ajaxFileSave(){
        println(params)
        println(params['fileName'])
        def fldId = params.fldId
        ArrayList inputFile = []
        def imagePath =[:]
        Integer fileArraySize = params["fileArraySize"] as Integer
        if(fileArraySize != 0 && fileArraySize > 0){
            for(int i=0; i<fileArraySize; i++){
                inputFile.add(params."$fldId-file[$i]")

            }

            //String storagePath = "/OwnerData/images/"
//            fileUploadTest(inputFile)
             imagePath = fileUploadService.imageSave(inputFile, request)
            if (imagePath){
                imagePath.put('success',true)
                log.debug("Image successfully saved in : $imagePath")
            }else {
                imagePath = [:]
                imagePath.put('success',false)
            }
        }
        render imagePath as JSON
    }

/*
    def saveFileRecord(imagePath){
        ArrayList<Map> urlList = imagePath.localUrlList
        if(!imagePath.s3UrlList.isEmpty()){
            urlList = imagePath.s3UrlList
        }
        def savedObj = []
        try {
            DataframeController dataframeController = new DataframeController()
            Dataframe df = dataframeController.getDataframe(params)
            String fieldnameToReload = params.fieldnameToReload
            DataframeInstance dfInst = new DataframeInstance(df, params)
            String domainAlias= Dataframe.getDataFrameDomainAlias(fieldnameToReload)
            def domainInstance = null
            boolean isParameterRelatedToDomain = false
            def queryDomain = null
            String simpleFieldName = ""
            if (domainAlias && df.writableDomains) {
                Map domain = df.writableDomains.get(domainAlias)
                queryDomain = dfInst.getPersistentEntityFromDomainMap(domain)
                Map keysNamesAndValue = [:];
                getKeysNamesAndValueForPk(keysNamesAndValue, domain, params, dfInst);
                if (!keysNamesAndValue.isEmpty()){
                    domainInstance = dfInst.retrieveDomainInstanceForUpdate(keysNamesAndValue, queryDomain);
                }
                simpleFieldName = Dataframe.extractSimpleFieldName(df.dataframeName,fieldnameToReload,domainAlias)
                isParameterRelatedToDomain = dfInst.isParameterRelatedToDomain(queryDomain, domainAlias, simpleFieldName)
            }
            if (simpleFieldName && isParameterRelatedToDomain){
                def prop = queryDomain.getPropertyByName(simpleFieldName)
                if (df.metaFieldService.isAssociation(prop)){
                    def clz = prop.associatedEntity.getJavaClass()
                    urlList.each {
                        def instance = clz.newInstance()
                        bindData(instance, it)
                        instance.save(flush: true)
                        savedObj.add(instance)
                        domainInstance."addTo${simpleFieldName.capitalize()}"(instance)
                    }
                }else{
                    urlList.each {
                        domainInstance."$simpleFieldName" = it.name
                    }
                }
                domainInstance.save(flush: true)
            }
            return true
        }catch(e){
            log.info("Failed to save file info.")
            savedObj.each {
                it.delete(flush: true)
            }
            if(!imagePath.s3UrlList.isEmpty()){
                urlList.each {
                    fileUploadService.deleteImageFromS3(it.url)
                }
            }
            return false
        }

    }
*/

/*
    public def getKeysNamesAndValueForPk(keysNamesAndValue, domain, Map inputData, DataframeInstance dfInst) {

        String keyFieldName = dfInst.constructKeyFieldName(domain, inputData)
        if(inputData.containsKey(keyFieldName)){
            String paramValue = inputData.get(keyFieldName)
            keysNamesAndValue.put(keyFieldName, paramValue)
        }
    }
*/

    def ajaxFileDelete(){
        def fileName = params.fileName
        fileUploadService.imageSave(fileName)
    }

    def fileUploadTest(inputFile){
        def hel = System.getenv("CATALINA_HOME")
//        def homeDir = new File(System.getProperty("CATALINA_HOME")).getPath()
        def homeDir1 = new File(System.getProperty("upload.location")).getPath()
        String filePath = grailsApplication.config.images.storageLocation
        System.setProperty("prop_dir", "webapps/uploads");
        String prop_dir = System.getProperty("prop_dir","/tmp/images");
        File saveLocation = new File(homeDir1+ "/"+ filePath)
        String totalPath = saveLocation.getAbsolutePath()
//        createDirectoryinserver()
        if( !saveLocation.exists() ){
            if(saveLocation.mkdirs()){
                log.debug("SUCCESS: temporary storage location created in local")
            }else{
                log.debug( "FAILED: couldn't create temporary storage location.")
                return
            }
        }
        if(!inputFile?.isEmpty()) {
            inputFile.each {
                String docName = it?.getOriginalFilename()
                String path = "${totalPath}/${docName}"
                File fl = new File(path)
                it.transferTo(fl)
            }
        }
    }

/*
    def createDirectoryinserver(){
        System.setProperty("prop_dir", "var/webapps/image");
        String prop_dir = System.getProperty("prop_dir","/tmp/images");

        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "SET");
        Map<String, String> env = pb.environment();
        env.put("env_dir", "/uploadEnv/image");
        Process process = pb.start();

        String env_dir = System.getenv("env_dir");

    }
*/

/*    def getFile(){
        def params = params
        String fileName = params.fileName

    }*/
}
