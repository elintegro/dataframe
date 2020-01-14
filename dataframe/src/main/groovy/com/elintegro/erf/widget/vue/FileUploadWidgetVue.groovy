package com.elintegro.erf.widget.vue
import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.DataframeVue

class FileUploadWidgetVue extends WidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String label = field.label
        boolean multiple = field?.multiple
        boolean deleteButton = field?.deleteButton
        String attr = field?.attr
        return """
              <div $attr>
               <v-file-input 
                  label = "Upload Your Resume Here"
                  ${multiple?":multiple= 'true'":""} 
                  @upload-success="${fldName}_uploadFiles" 
                  ${deleteButton?"@before-remove='${fldName}_beforeRemove'":""}
                  ${deleteButton?":delete-button=true":""}
                  ${toolTip(field)}  
                >
               </v-file-input></div>
               """
    }
    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        String valueScript = """"""
        return """
           ${fldName}_files: [],
"""

    }
    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        String fldName = dataVariable
        def defaultValue = field.defaultValue?:""
        String fillState = ""
        String genId = fldName+"-file"
        String domainAlias= getDataFrameDomainAlias(divId)
        String valueMember = field.valueMember
        String fieldNameToReload = dataframe.dataframeName+'.'+domainAlias+'.'+valueMember
        boolean deleteButton = field?.deleteButton
        dataframe.getVueJsBuilder().addToCreatedScript("""this.${fldName}_computedFileUploadParams();\n""")
                .addToMethodScript("""
           ${fldName}_uploadFiles: function(event){
                        var detailData = event.detail;
                        var fileList = detailData[3];
                        this.${fldName}_files = fileList; 
                    },\n
           ${deleteButton?"""${fldName}_beforeRemove: function(event){
                            var detailData = event.detail;
                            var beforeRemove = detailData[1];
                            var r = confirm("Remove image !");
                            if (r == true) {
                                beforeRemove()
                                this.${fldName}_files = detailData[2];
                            } 
                    },\n  """:""}
          
           ${fldName}_ajaxFileSave: function(data, allParams){
                        var fileList = this.${fldName}_files;
                        if(fileList.length > 0){
                            var fileData = new FormData();
                            fileData.append('fileSize',fileList.length);
                            fileData.append('fieldnameToReload','$fieldNameToReload');
                            jQuery.each(allParams, function (key, value) {
                                    fileData.append(key,value);
                            });
                            fileData.append('fldId','$fldName');
                            for (var i = 0; i < fileList.length; i++) {
                                fileData.append("$genId["+i+"]", fileList[i]);
                            }
                            axios.post('${field.ajaxFileSaveUrl}', fileData).then(response => {
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

    String getCreatedScript(DataframeVue dataframe, Map field, String divId, String fldId, String key){
        String fldName = dataframe.getDataVariableForVue(field)
        return """this.${fldName}_computedFileUploadParams();\n"""
    }

    private static String getDataFrameDomainAlias(String divId){
        String[] fieldArr = divId.split("-")
        if (fieldArr.size() > 2){
            return fieldArr[1]
        }
    }


}
