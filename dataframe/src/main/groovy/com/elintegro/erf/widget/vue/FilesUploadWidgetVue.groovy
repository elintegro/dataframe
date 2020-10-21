package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.DataframeVue

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
                              let stateVariable = excon.getFromStore("$dataframe.dataframeName");
                              stateVariable.${getFieldJSONNameVueWithoutState(field)} = fileToUpload[0].name;
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