package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DomainClassInfo
import com.elintegro.erf.dataframe.vue.DataframeVue
import org.apache.commons.lang.StringUtils
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

class FilesUploadWidgetVue extends com.elintegro.erf.widget.vue.WidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field){
        String fldName = dataframe.getDataVariableForVue(field)
        String label = field.label
        boolean multiple = field?.multiple
        String attr = field?.attr
        String modelString = getModelString(dataframe, field)

        return """
              <div $attr>
               <v-file-input
                  label = "$label"
                  :multiple = $multiple
                  @change = "${fldName}_uploadFile"
                  ${toolTip(field)}
                >
               </v-file-input></div>
               """

    }
    @Override
    boolean populateDomainInstanceValue(def domainInstance, DomainClassInfo domainClassInfo, String fieldName, Map field, def inputValue){
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
    private JSONArray convertToJSONArrayIfSingleJSONObject(JSONObject value){
        JSONArray jn = new JSONArray()
        jn.add(value)
        return jn
    }
    //	saves onetomany and manytomany
    private boolean saveHasManyAssociation(JSONArray inputValue, def refDomainClass, String fieldName, def domainInstance) {
        def oldfldVal = domainInstance."${fieldName}"
//        if (oldfldVal) {
//            JSONArray oldfldValArr = new JSONArray(domainInstance."${fieldName}")
//            if (isSelectionEqualsToOld(oldfldVal, inputValue)) {
//                return false
//            }
//        }
        domainInstance?.(StringUtils.uncapitalize(fieldName))?.clear()
        //Here i have tried to save fileName,fileType,fileStorageSize in Files table ,if we need to save other attributes(fields) in future, change this code accordingly.
        //Todo : Need to make this code more generic if possible so that we don't have to manually add hardcoded fields (like fileName,fileType.. )
        inputValue.value.each{val ->
            val.each {
                //fileName, fileType, fileStorageSize saving into Files table
                def newInstance = refDomainClass.newInstance()
                newInstance.fileName = it.fileName
                newInstance.fileStorageSize = it.fileStorageSize
                newInstance.fileType = it.fileType
                newInstance.save()
                //newly created file instance saving into cross table..
                domainInstance."addTo${fieldName.capitalize()}"(newInstance)
            }
        }
        return true
    }
    String getVueDataVariable(DataframeVue dataframe, Map field){
        String fldName = dataframe.getDataVariableForVue(field)
        return """
           ${fldName}_files: [],
            """
    }
    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        String fldName = dataVariable
        def defaultValue = field.defaultValue?:""
        def doAfterSave = field.doAfterSave?:""
        String genId = fldName+"-file"
        dataframe.getVueJsBuilder().addToCreatedScript("""this.${fldName}_computedFileUploadParams();\n""")
                .addToMethodScript("""
                   ${fldName}_uploadFile: function(event){
                              this.${fldName}_files =  event;
                              var fileToUpload =  this.${fldName}_files;
                              let fileArray = [];
                              for(let i = 0; i < fileToUpload.length; i++){
                                  let fileData = {};
                                  fileData["fileName"] = fileToUpload[i].name; 
                                  fileData["fileType"] = fileToUpload[i].type;
                                  fileData["fileStorageSize"] = fileToUpload[i].size;
                                  fileArray.push(fileData);
                              }
                              let stateVariable = excon.getFromStore("$dataframe.dataframeName");
                              stateVariable.${getFieldJSONNameVueWithoutState(field)} = fileArray;
                              excon.saveToStore("$dataframe.dataframeName", stateVariable)
                              },\n
                             ${fldName}_ajaxFileSave: function(data, allParams){
                              var fileList = this.${fldName}_files;
                              if(fileList.length > 0){                                                                        
                              var fileData = new FormData();
                              for (var i = 0; i < fileList.length; i++) {
                              fileData.append('fileName',fileList[i].name);
                              fileData.append('fileStorageSize',fileList[i].size);                              
                              fileData.append('fileLastModified',fileList[i].lastModified);
                              fileData.append('fileLastModifiedDate',fileList[i].lastModifiedDate);
                              fileData.append('fileWebKitRelativePath',fileList[i].webKitRelativePath);
                              fileData.append('fileType',fileList[i].type);                              
                              fileData.append("$genId["+i+"]", fileList[i]);
                            }
                            fileData.append('fileSize',fileList.length);
                            fileData.append('fldId','$fldName');
                            if(data.params != null){
                            fileData.append('allParams',data.params.id);}
                            }
                            let self = this;
                              axios({ method:'post',
                                        url:'${field.ajaxFileSaveUrl}',
                                        data:fileData 
                                 }).then(response => {
                                            ${doAfterSave}
                                            console.log(response)
                                                    })
                                  .catch(function(error){
                                                console.log(error)
                                                });                      
                   },
                   
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
}