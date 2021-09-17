/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development.
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works.
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.util.Holders
import org.apache.commons.lang.StringUtils
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

/**
 * Created by kchapagain on Dec, 2018.
 */
class PictureUploadWidgetVue extends WidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        registerComponent(dataframe)
        boolean multiple = field?.multiple?:false
        boolean editButton = field?.editButton?:false
        boolean deleteButton = field?.deleteButton?:false
        boolean addButton = field?.addButton?:true
        String primaryText = field?.primaryText?:""
        String browseText = field?.browseText?:"Browse picture(s)"
        String dragText = field?.dragText?:"Drag pictures"
        String dropText = field?.dropText?:"Drop your file here ..."
        String markIsPrimary = field?.markIsPrimary?:""
        String popupText = field?.popupText?:""
        String idUpload = field?.idUpload?:fldName
        String idEdit = field?.idEdit?:fldName
        def maxImage = field?.maxImage?:5
        boolean camera = field?.camera?:false
        boolean videoHeight = field?.videoHeight
        boolean videoWidth = field?.videoWidth
        boolean canvasHeight = field?.canvasHeight
        boolean canvasWidth = field?.canvasWidth
        return """
              <div ${getAttr(field)} style="display: block; max-width: fit-content; margin:0 auto;">
                   <div class="v-label theme--light">
                   ${getLabel(field)}
                    </div>
                   <v-image-upload
                      :multiple='$multiple'
                      @upload-success="${fldName}_handleImageUpload" 
                      @limit-exceeded="${fldName}_limitExceeded" 
                      ${deleteButton ? "@before-remove='${fldName}_beforeRemove'" : ""}
                      ${editButton ? "@edit-image='${fldName}_editImage'" : ""}
                      ${toolTip(field)}  
                        ${idUpload ? "id-upload=$idUpload" : ""}
                        ${idEdit ? "id-edit=$idEdit" : ""}
                        :max-image=$maxImage
                        :camera = '$camera'
                        ${videoHeight ? ":video-height=$videoHeight" : ""}
                        ${videoWidth ? ":video-width=$videoWidth" : ""}
                        ${canvasHeight ? ":canvas-height=$canvasHeight" : ""}
                        ${canvasWidth ? ":canvas-width=$canvasWidth" : ""}
                        primary-text = "$primaryText"
                        browse-text = "$browseText"
                        drag-text = "$dragText"
                        drop-text="$dropText"
                        mark-is-primary-text="$markIsPrimary"
                        popup-text="$popupText"
                        :show-delete='$deleteButton'
                        :show-edit='$editButton'
                        :show-add='$addButton'
                        :data-images="imageList"
                        :is-camera-disable = "${fldName}_cameraDisable"
                   ></v-image-upload>
                   <v-progress-circular
                        :size="140"
                        :width="6"
                        color="rgb(177,243,198)"
                        indeterminate
                        v-show="${fldName}_showLoader"
                        class="imageResizeLoader"
                   >Reducing...</v-progress-circular>
                   
              </div>
        """
    }

    @Override
    boolean populateDomainInstanceValue(Dataframe dataframe, def domainInstance, DomainClassInfo domainClassInfo, String fieldName, Map field, def inputValue){
        if(isReadOnly(field)){
            return false
        }
        JSONArray selectedItems =  convertToJSONArrayIfSingleJSONObject(inputValue)
        JSONArray availableItems = inputValue.items

        if(!domainClassInfo.isAssociation(fieldName) || domainClassInfo.isToOne(fieldName)){ // this means we just want to apply description value to the text field without association with any other entity
            if ((inputValue.value instanceof JSONArray) && (inputValue.value.size() > 0)){
                Map requestInputValue = ["value":inputValue.value[0].getAt("imageName")]
                super.populateDomainInstanceValue(dataframe, domainInstance, domainClassInfo, fieldName, field, requestInputValue)
            }else {
                def oldfldVal = domainInstance."${fieldName}"
                if(oldfldVal == inputValue.value) return false
                domainInstance."${fieldName}" = inputValue.value
            }
        }else if(domainClassInfo.isToMany(fieldName)){
            return saveHasManyAssociation(selectedItems, domainClassInfo.getRefDomainClass(fieldName), fieldName, domainInstance)
        }
        return true
    }

    private JSONArray convertToJSONArrayIfSingleJSONObject(JSONObject value) {
        JSONArray jn = new JSONArray()
        jn.add(value)
        return jn
    }

    private boolean saveHasManyAssociation(JSONArray inputValue, def refDomainClass, String fieldName, def domainInstance) {
        domainInstance?.(StringUtils.uncapitalize(fieldName))?.clear()
        //Here i have tried to save imageName,imageType,imageSize in Images table ,if we need to save other attributes(fields) in future, need to change this code accordingly.
        //Todo : Need to make this code more generic if possible so that we don't have to manually add hardcoded fields (like imageName,imageType.. )
        inputValue.value.each{val ->
            val.each {
                def newInstance = refDomainClass.newInstance()
                newInstance.name = it.imageName
                newInstance.imageType = it.imageType
                newInstance.imageSize = it.imageSize
                newInstance.save()
                //newly created image instance saving into cross table..
                domainInstance."addTo${fieldName.capitalize()}"(newInstance)
            }
        }
        return true
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        String valueScript = """"""
        return """
           ${fldName}_files: [],
           ${fldName}_imagesData: [],
"""

    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException {
        String fldName = dataVariable
        def defaultValue = field.defaultValue ?: ""
        String fillState = ""
        String genId = fldName + "-file"
        String domainAlias = getDataFrameDomainAlias(divId)
        String valueMember = field.valueMember
        String fieldNameToReload = dataframe.dataframeName + '.' + domainAlias + '.' + valueMember
        boolean deleteButton = field?.deleteButton
        boolean editButton = field?.editButton
        boolean markIsPrimary = field?.markIsPrimary?:false
        String fileSaveUrl = field.ajaxFileSaveUrl?:dataframe.ajaxFileSaveUrl
        String doAfterSave = field.doAfterSave?:""
        def imageMaxSize = Holders.grailsApplication.config.image.maxSize
        dataframe.getVueJsBuilder().addToDataScript("""imageList:[],\n ${fldName}_cameraDisable : false,\n ${fldName}_showLoader:false,\n """).addToCreatedScript("""this.${fldName}_computedFileUploadParams();\n""")
                .addToMethodScript("""
           ${fldName}_handleImageUpload: function(file, currentIndex, imageList){
                               if(file.size > 52428800){ //52428800 bytes = 50mb
                                  let response = {};
                                  response.msg = "Image size should be less than 50 MB"
                                  excon.showAlertMessage(response);
                                  this.imageList = [];
                                  this.${fldName}_imagesData = [];
                                  return false;
                               }
                               else if(file.size > 2097152){
                                   let confirmMsg = confirm("The picture is too big, would you like to reduce the size?");
                                   if(confirmMsg){
                                       this.${fldName}_showLoader = true;
                                       this.${fldName}_reduceImageSize(file, currentIndex, imageList)
                                   }
                                   else{
                                       this.imageList = [];
                                       this.${fldName}_imagesData = [];
                                       return false;
                                   }
                               }else{
                                    this.${fldName}_uploadImages(file, currentIndex, imageList)
                               }
           },\n  
           ${fldName}_uploadImages: function(file, currentIndex, imageList){
                            this.${fldName}_files.push(file);
                            let imageData = ${excon}.getImageDataInfo(file);
                            this.${fldName}_imagesData.push(imageData);
                            let stateVariable = excon.getFromStore("$dataframe.dataframeName");
                            stateVariable.${getFieldJSONNameVueWithoutState(field)} = this.${fldName}_imagesData;
                            excon.saveToStore("$dataframe.dataframeName", stateVariable);
                            this.${fldName}_cameraDisable  = true;
           },\n 
           ${fldName}_reduceImageSize: function(file, currentIndex, imageList){
                                var self = this;
                                var imageFile = file
                                
                                var options = {
                                    maxSizeMB: $imageMaxSize,
                                    useWebWorker: true,
                                    maxIteration: 50, 
                                }
                                imageCompression(imageFile, options)
                                    .then(function (compressedFile) {
                                        console.log(compressedFile); // write your own logic
                                        var newFile = new File([compressedFile], compressedFile.name);
                                        var reader = new FileReader();
                                        console.log(reader.readAsDataURL(compressedFile))
                                         reader.onloadend = function() {
                                             var base64data = reader.result; 
                                             self.${fldName}_showLoader = false;
                                             imageList[currentIndex].path = base64data;
                                             self.${fldName}_uploadImages(newFile, currentIndex, imageList)
                                         }
                                    })
                                    .catch(function (error) {
                                        self.${fldName}_showLoader = false;
                                        console.log(error.message);
                                });

           },\n      
           ${deleteButton ? """${fldName}_beforeRemove: function(currentIndex, beforeRemove){
                            var r = confirm("Remove image !");
                            if (r == true) {
                                beforeRemove()
                                if(typeof this.${fldName}_files[currentIndex] != 'undefined') {
                                    this.${fldName}_files.splice(currentIndex, 1);
                                    this.${fldName}_imagesData.splice(currentIndex, 1);
                                    excon.getFromStore("$dataframe.dataframeName").${getFieldJSONNameVueWithoutState(field)} = this.${fldName}_imagesData;
                                }
                                if(this.${fldName}_imagesData.length < 1){
                                   this.${fldName}_cameraDisable  = false;
                                }
                            } 
                    },\n  """ : ""}
           ${editButton ? """${fldName}_editImage: function(file, currentIndex, fileList){
                            let currentInd = currentIndex?currentIndex:0;
                            this.${fldName}_files[currentInd] = file;
                            let stateValues = excon.getFromStore("$dataframe.dataframeName");
                            let imageArray = stateValues.${getFieldJSONNameVueWithoutState(field)};
                            if(imageArray != null || imageArray != undefined){
                              imageArray[currentInd] = ${excon}.getImageDataInfo(file);
                            }else{
                                imageArray = [];
                                imageArray[currentInd] = ${excon}.getImageDataInfo(file);
                                stateValues.${getFieldJSONNameVueWithoutState(field)} = imageArray;
                                excon.saveToStore('$dataframe.dataframeName',stateValues);
                            }
                    },\n  """ : ""}
           ${markIsPrimary ? """${fldName}_markIsPrimary: function(currentIndex, fileList){
                            console.log('markIsPrimary data', index, fileList);
                    },\n  """ : ""}
           ${fldName}_limitExceeded: function(limitAmount){
                            alert('Limit exceeded');
                    },\n
           ${fldName}_ajaxFileSave: function(data, params){
                        let self = this;
                        var fileList = this.${fldName}_files;
                        if(fileList.length > 0){
                            var picData = new FormData();
                            picData.append('fileSize',fileList.length);
                            picData.append('fieldnameToReload','$fieldNameToReload');
                            jQuery.each(params, function (key, value) {
                                    picData.append(key,value);
                            });
                            picData.append('fldId','$fldName');
                            for (var i = 0; i < fileList.length; i++) {
                                picData.append("$genId["+i+"]", fileList[i]);
                            }
                            axios.post('${fileSaveUrl}', picData).then(response => {
                              console.log(response)
                              $doAfterSave
                              self.imageList = [];
                              self.${fldName}_imagesData = [];
                              self.${fldName}_cameraDisable = false;
                              self.${fldName}_files = []
                            }).catch(function (error) {
                                console.log(error);
                            });
                        } 
                          
                    },\n 
           ${fldName}_computedFileUploadParams() {
             var refParams = this.params;
             var ajaxFileUploadParams = refParams['ajaxFileSave'];
             if (ajaxFileUploadParams){
                    ajaxFileUploadParams.push({fieldName:'$fldName'})
                }else{
                    refParams['ajaxFileSave'] = [{fieldName:'$fldName'}];
                } 
           },\n                        
       """)
        return """this.$dataVariable = response['$key']?response['$key']:"$defaultValue\";
                """
    }

    String getCreatedScript(DataframeVue dataframe, Map field, String divId, String fldId, String key) {
        String fldName = dataframe.getDataVariableForVue(field)
        return """this.${fldName}_computedFileUploadParams();\n"""
    }

    private static String getDataFrameDomainAlias(String divId) {
        String[] fieldArr = divId.split("-")
        if (fieldArr.size() > 2) {
            return fieldArr[1]
        }
    }

    private static void registerComponent(DataframeVue dataframe){
        dataframe.vueJsBuilder.addToComponentScript("""
         'v-image-upload' : MultipleImageUploadWithWebCam,
        """)
    }
}
