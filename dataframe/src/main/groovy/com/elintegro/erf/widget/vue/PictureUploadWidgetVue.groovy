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
class PictureUploadWidgetVue extends WidgetVue{
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        boolean multiple = field?.multiple
        boolean editButton = field?.editButton
        boolean deleteButton = field?.deleteButton
        return """
              <div ${getAttr(field)}>
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
                        var imageList = detailData[3];
                        this.${fldName}_files = imageList;
                        let imageArray = [];
                        for(let i = 0; i < imageList.length; i++){
                            let imageData = {};
                            imageData["imageName"] = imageList[i].name; 
                            imageData["imageSize"] = imageList[i].size;
                            imageData["imageType"] = imageList[i].type;
                            imageArray.push(imageData);
                        }
                        let stateVariable = excon.getFromStore("$dataframe.dataframeName");
                        stateVariable.${getFieldJSONNameVueWithoutState(field)} = imageArray;
                        excon.saveToStore("$dataframe.dataframeName", stateVariable)
                        
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
