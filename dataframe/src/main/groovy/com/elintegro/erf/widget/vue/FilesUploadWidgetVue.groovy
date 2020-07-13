package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.DataframeVue

class FilesUploadWidgetVue extends com.elintegro.erf.widget.vue.WidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field){
        String fldName = dataframe.getDataVariableForVue(field)
        String label = field.label
        boolean multiple = field?.multiple
//        boolean deleteButton = field?.deleteButton
        String attr = field?.attr
        String modelString = getModelString(dataframe, field)

        return """
              <div $attr>
               <v-file-input
                  label = "$label"
                  multiple
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
        String genId = fldName+"-file"
       dataframe.getVueJsBuilder().addToCreatedScript("""this.${fldName}_computedFileUploadParams();\n""")
               .addToMethodScript("""
                   ${fldName}_uploadFile: function(event){
                              //TODO: for multi file this should be array and not sinfle file, change this code accordingly
                              this.${fldName}_files =  event;
                              //this.state.$fldName = fileToUpload.name; //TODO: once we find out why the v-model cannot get state.<fldName>, this line could be deleted!
                              var fileToUpload =  this.${fldName}_files;
                              this.state.$fldName = fileToUpload[0].name;
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
                            }
                              fetch('${field.ajaxFileSaveUrl}',
                                 { method:'POST',
                                   body:fileData 
                                 }).then(response => {
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