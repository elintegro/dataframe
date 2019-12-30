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
import com.elintegro.erf.dataframe.vue.DataframeVue

/**
 * Created by kchapagain on Dec, 2018.
 */
class PictureUploadWidgetVue extends WidgetVue{
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        String label = field.label
        boolean multiple = field?.multiple
        boolean editButton = field?.editButton
        boolean deleteButton = field?.deleteButton
        String attr = field?.attr
        return """
              <div $attr>
               <v-eutil-image-upload 
                  ${multiple?":multiple= 'true'":""} 
                  @upload-success="${fldName}_uploadImages" 
                  ${deleteButton?"@before-remove='${fldName}_beforeRemove'":""}
                  ${editButton?"@edit-image='${fldName}_editImage'":""}
                  ${editButton?":edit-button=true":""}
                  ${deleteButton?":delete-button=true":""}
                  ${toolTip(field)}  
                >
               </v-eutil-image-upload></div>
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
        boolean editButton = field?.editButton
        dataframe.getVueJsBuilder().addToCreatedScript("""this.${fldName}_computedFileUploadParams();\n""")
                .addToMethodScript("""
           ${fldName}_uploadImages: function(event){
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
           ${editButton ? """${fldName}_editImage: function(event){
                            var detailData = event.detail;
                            this.${fldName}_files = detailData[3];
                    },\n  """ : ""}
           ${fldName}_ajaxFileSave: function(data, allParams){
                        var fileList = this.${fldName}_files;
                        if(fileList.length > 0){
                            var picData = new FormData();
                            picData.append('fileSize',fileList.length);
                            picData.append('fieldnameToReload','$fieldNameToReload');
                            jQuery.each(allParams, function (key, value) {
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
