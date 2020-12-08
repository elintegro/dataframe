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
        String idUpload = field?.idUpload
        String idEdit = field?.idEdit
        def maxImage = field?.maxImage?:5
        boolean camera = field?.camera?:false
        boolean videoHeight = field?.videoHeight
        boolean videoWidth = field?.videoWidth
        boolean canvasHeight = field?.canvasHeight
        boolean canvasWidth = field?.canvasWidth
        return """
              <div ${getAttr(field)} style="display: flex;">
               <v-image-upload
                  :multiple=$multiple
                  @upload-success="${fldName}_uploadImages" 
                  @limit-exceeded="${fldName}_limitExceeded" 
                  ${deleteButton ? "@before-remove='${fldName}_beforeRemove'" : ""}
                  ${editButton ? "@edit-image='${fldName}_editImage'" : ""}
                  ${toolTip(field)}  
                    ${idUpload ? "id-upload=$idUpload" : ""}
                    ${idEdit ? "edit-upload=$idEdit" : ""}
                    :max-image=$maxImage
                    :camera = $camera
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
                    :show-delete=$deleteButton
                    :show-edit=$editButton
                    :show-add=$addButton
            ></v-image-upload>
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

        if(!domainClassInfo.isAssociation(fieldName)){ // this means we just want to apply description value to the text field without association with any other entity
            def oldfldVal = domainInstance."${fieldName}"
            if(oldfldVal == inputValue.value) return false
            domainInstance."${fieldName}" = inputValue.value
        }else if(domainClassInfo.isToMany(fieldName)){
            return saveHasManyAssociation(selectedItems, domainClassInfo.getRefDomainClass(fieldName), fieldName, domainInstance)
        }else if(domainClassInfo.isToOne(fieldName)){
            def oldfldVal = domainInstance."${fieldName}".value
            domainInstance."${fieldName}" = inputValue.value[0] //TODO: check if there is not exception in case of single choice
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
        dataframe.getVueJsBuilder().addToCreatedScript("""this.${fldName}_computedFileUploadParams();\n""")
                .addToMethodScript("""
           ${fldName}_uploadImages: function(file, currentIndex, imageList){
                        this.${fldName}_files.push(file);
                        let imageData = ${excon}.getImageDataInfo(file);
                        this.${fldName}_imagesData.push(imageData);
                        let stateVariable = excon.getFromStore("$dataframe.dataframeName");
                        stateVariable.${getFieldJSONNameVueWithoutState(field)} = this.${fldName}_imagesData;
                        excon.saveToStore("$dataframe.dataframeName", stateVariable);
                        
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
                            } 
                    },\n  """ : ""}
           ${editButton ? """${fldName}_editImage: function(file, currentIndex, fileList){
                            this.${fldName}_files[currentIndex] = file;
                            excon.getFromStore("$dataframe.dataframeName").${getFieldJSONNameVueWithoutState(field)}[currentIndex] = ${excon}.getImageDataInfo(file);
                    },\n  """ : ""}
           ${markIsPrimary ? """${fldName}_markIsPrimary: function(currentIndex, fileList){
                            console.log('markIsPrimary data', index, fileList);
                    },\n  """ : ""}
           ${fldName}_limitExceeded: function(limitAmount){
                            alert('Limit exceeded : '+limitAmount);
                    },\n
           ${fldName}_ajaxFileSave: function(data, params){
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
                            axios.post('${field.ajaxFileSaveUrl}', picData).then(response => {
                              console.log(response)
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
