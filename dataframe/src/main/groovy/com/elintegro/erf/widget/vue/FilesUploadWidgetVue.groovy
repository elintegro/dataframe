package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.DataframeVue


class FilesUploadWidgetVue extends WidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field){
        String fldName = dataframe.getDataVariableForVue(field)
        String label = field.label
//        boolean multiple = field?.multiple
//        boolean deleteButton = field?.deleteButton
        String attr = field?.attr
        String accept = field?.accept
        String acceptedLine = accept?"${accept}":'""'

        return """
              <div $attr>
               <v-file-input 
                  label = $label
                  v-model="${fldName}"
                 
                  @change = "${fldName}_uploadFile"
                  ${toolTip(field)}  
                  accept = ${acceptedLine}
                >
               </v-file-input></div>
               """
    }
    String getVueDataVariable(DataframeVue dataframe, Map field){
        String fldName = dataframe.getDataVariableForVue(field)
        return """
           ${fldName}: [],
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
                              var fileToUpload =  this.${fldName};
                              this.state.$fldName = fileToUpload.name; //TODO: once we find out why the v-model cannot get state.<fldName>, this line could be deleted!
                              var fileToUpload =  this.${fldName};                                                                        
                              var fileData = new FormData();
                              fileData.append('fileName',fileToUpload.name);
                              fileData.append('fileSize',fileToUpload.size);                              
                              fileData.append('fileLastModified',fileToUpload.lastModified);
                              fileData.append('fileLastModifiedDate',fileToUpload.lastModifiedDate);
                              fileData.append('fileWebKitRelativePath',fileToUpload.webKitRelativePath);
                              fileData.append('fileType',fileToUpload.type);                              
                              fileData.append('fldId','$fldName');          
                              fileData.append('fileArraySize',1); //TODO: for multi file put the right number here! 
                              fileData.append("$genId[0]", fileToUpload); //TODO: for multi file this should be array and not single file, change this code accordingly
            
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
                         } 
                         else{
                                  refParams['ajaxFileSave'] = [{fieldName:'$fldName'}];
                         } 
                 },\n 

       """)

        return """this.$dataVariable = response['$key']?response['$key']:"$defaultValue\";
                """
    }
}