package com.elintegro.erf.widget.vue

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.util.Holders
import org.grails.web.json.JSONObject

class FilesDisplayWidgetVue extends WidgetVue {
    def contextPath = Holders.grailsApplication.config.rootPath
    @Override
    String getHtml(DataframeVue dataframe, Map field){
        String height       = getHeight(field)
        String width        = getWidth(field)
        String aspectRatio  = field.aspectRatio?field.aspectRatio:"2.75"
        String heightString = height?"height=$height":""
        String widthString  = width?"""width=$width """:""
        String fldName =  getFieldName(dataframe, field)

        String fldParam     = dataframe.getDataVariableForVue(field)
        String modelString = getFieldJSONModelNameVue(field)
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

    boolean setPersistedValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalData, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        jData?.persisters?."${domainAlias}"."${fieldName}".value = getUrl(fieldProps, value)
    }

    boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalData, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        jData?.transits?."${fieldName}".value = getUrl(fieldProps, value)
    }

    private String getUrl(Map fieldProps, def value){
        String defImgUrl = fieldProps.url?:getDefaultImageName()
        def imageUrl = Holders.grailsApplication.config.images.storageLocation + "/images/"
//        String api = fieldProps.api?:"fileDownload/fileDownload"
//        String url = value?(api + "/" + value):defImgUrl
        String url = value?:defImgUrl
        String alt = value?:defImgUrl
        return url
    }
    private String getDefaultImageName(){

        return Holders.config.images.defaultImagePathForPdf
    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        String fldName = dataVariable
        String modelString = getFieldJSONModelNameVue(field)
        dataframe.getVueJsBuilder().addToMethodScript("""
               ${fldName}_url:function(){
                                    var fileName = this.${modelString}_name;
                                    var fileURL = '/fileDownload/fileDownload/'+fileName
                                    var fileLink = document.createElement('a');
                                    fileLink.href = fileURL;
                                    fileLink.setAttribute('download', this.${modelString}_name);
                                    document.body.appendChild(fileLink);
                                    fileLink.click();
               }
          
        """)
         return ""
    }
    public Object getInitValues(DataframeVue df, Map fieldProps){
        return fieldProps.url?:getDefaultImageName()
    }
}

