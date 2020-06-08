package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.util.Holders

class FilesDisplayWidgetVue extends WidgetVue {
    @Override
    String getHtml(DataframeVue dataframe, Map field){
        String modelString = getModelString(dataframe, field)
        String height       = getHeight(field)
        String width        = getWidth(field)
        String aspectRatio  = field.aspectRatio?field.aspectRatio:"2.75"
        String heightString = height?"height=$height":""
        String widthString  = width?"""width=$width """:""
        String fldName =  getFieldName(dataframe, field)

        String fldParam     = dataframe.getDataVariableForVue(field)
        String html = """<div @click.stop="${fldName}_url">
                     <v-img
                     id = "$fldParam"
                     :src="$modelString"
                     :alt = "${modelString}_alt"
                     aspect-ratio="$aspectRatio"
                     ${toolTip(field)} 
                     $heightString
                      $widthString
                      ${getAttr(field)} >
                      </v-img>
                      <span>{{${modelString}_name}}</span>
                      </div>  
                     """
        if(field?.layout){
            html = applyLayout(dataframe, field, html)
        }

        return html

    }
    String getStateDataVariable(DataframeVue dataframe, Map field) {
        String fldParam = dataframe.getDataVariableForVue(field)
        String defImgUrl = getDefaultImageName()
        String url = field.url?:defImgUrl
        String alt = field.alt?:defImgUrl
        String imgUrl =  getImageUrl(field)
        return """$fldParam:'$url',\n
                  ${fldParam}_alt:'$alt', \n"""

    }
    private String getDefaultImageName(){

        return Holders.config.images.defaultImagePathForPdf
    }
    private String getFileUrl(){
        String defaultFileLocation = Holders.config.images.storageLocation
        String urlForFile = defaultFileLocation+'/'
        return urlForFile
    }
    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        String fldName = dataVariable
        File f = new File(fileUrl)
        String absolute = f.getAbsolutePath()
        dataframe.getVueJsBuilder().addToMethodScript("""
               ${fldName}_url:function(){
                         var fileLocation = '$absolute'
                         var fileName = this.state.${fldName}_name;
                         var urlForFile = fileLocation + fileName
                         window.open(urlForFile,"_blank")
                         console.log(urlForFile)
                         return urlForFile
                         }
          
            """)
         return ""
    }
}

