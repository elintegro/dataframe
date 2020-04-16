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
                      allParams = {};
                      this.${fldName}_files.push(event);                      
                      allParams =  this.${fldName}_files;
                      this.${fldName}_ajaxFileSave(allParams);
                   },
                   ${fldName}_ajaxFileSave: function(allParams){
                      var fileList = this.${fldName}_files;
                       if(fileList.length > 0){
                              var firstFile = fileList[0];
                              var firstFileName = firstFile.name;
                              this.state.${fldName} = firstFile.name;
            
                              var fileData = new FormData();
                              fileData.append('fileSize',fileList.length);
                              fileData.append('fldId','$fldName');           
                              fileData.append('fileName',firstFileName);
                              fileData.append("$genId[0]", fileList[0]);
            
                              fetch('${field.ajaxFileSaveUrl}',
                                 { method:'POST',
                                   body:fileData 
                                 }).then(response => {
                                            console.log(response)
                                                    })
                                  .catch(function(error){
                                                console.log(error)
                                                });                      
                        }
                   },\n
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

