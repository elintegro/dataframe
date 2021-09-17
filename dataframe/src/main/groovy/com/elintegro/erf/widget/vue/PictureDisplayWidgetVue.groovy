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

import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueStore
import grails.util.Holders
import org.grails.web.json.JSONObject

class PictureDisplayWidgetVue extends WidgetVue{
    @Override
    String getHtml(DataframeVue dataframe, Map field) {

        String fldParam     = dataframe.getDataVariableForVue(field)
        String height       = getHeight(field)
        String width        = getWidth(field)
        String aspectRatio  = field.aspectRatio?field.aspectRatio:"2.75"
        String heightString = height?"height=$height":""
        String widthString  = width?"""width=$width """:""
        String modelString = getFieldJSONModelNameVue(field)
        String pictureTitle = field.pictureTitle?:""
        boolean containsLoader = field?.containsLoader?:false
        String loaderType = field?.loaderType?:"circular"
        String loaderAttrs = field?.loaderAttrs?:""
        String loader = """ <template v-if="$containsLoader" v-slot:placeholder>
                                <v-row
                                    class="fill-height ma-0"
                                    align="center"
                                    justify="center"
                                >
                                    <v-progress-$loaderType
                                        ${loaderAttrs}
                                    ></v-progress-$loaderType>
                                </v-row>
                             </template>
                           """
        String pictureTitleCode = pictureTitle?"""<div class="d-flex justify-end mx-3 mb-3">
                                                  <span class="my-span">
                                                   $pictureTitle
                                                  </span>
                                                </div>""":""
        boolean pictureButton = field.pictureButton?:false
        String setButtonAttr = field.setButtonAttr?:""
        String setIconAttr = field.setIconAttr?:""
        String setMarginPaddingForPictureButton = field.setMarginPaddingForPictureButton?:" mx-3 mb-3"
        if(pictureButton){
            String refHtml = ""
            def onClick = field.onClick
            if(onClick && field.get('onClick')){
                if(onClick.refDataframe){
                    refHtml = getRefHtml(onClick as Map, dataframe)
                }
            }
            String html =  """ <v-img
                                   id = "$fldParam"
                                   :src="$modelString"
                                   :alt = "${modelString}_alt"
                                   aspect-ratio="$aspectRatio"
                                   ${toolTip(field)} 
                                   $heightString
                                   $widthString
                                   ${getAttr(field)} 
                                >
                                   ${loader}
                                   <div class="d-flex justify-end ${setMarginPaddingForPictureButton}">
                                       $refHtml<v-btn $setButtonAttr>
                                            <v-img $setIconAttr></v-img>
                                       </v-btn>
                                   </div> 
                                </v-img>
                           """
            return html
        }
        String html =  """
                            <v-img
                               id = "$fldParam"
                               :src="$modelString"
                               :alt = "${modelString}_alt"
                               aspect-ratio="$aspectRatio"
                               ${toolTip(field)} 
                               $heightString
                               $widthString
                               ${getAttr(field)} 
                            >
                               $pictureTitleCode
                               ${loader} 
                            </v-img>
                       """
        if(field?.layout){
            html = applyLayout(dataframe, field, html)
        }
        return html
    }
//    lazy-src="$imgUrl"
    String getStateDataVariable(DataframeVue dataframe, Map field) {
        String fldParam = dataframe.getDataVariableForVue(field)
        String defImgUrl = getDefaultImageName()
        String url = field.url?:defImgUrl
        String alt = field.alt?:defImgUrl
        String imgUrl =  getImageUrl(field)
        return """$fldParam:'$url',\n
                  ${fldParam}_alt:'$alt',\n"""

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
        String api = fieldProps.api?:"fileDownload/fileDownload"
        String url = value?(api + "/" + value):defImgUrl
        String alt = value?:defImgUrl
        return url
    }

    private String getDefaultImageName(){
//        def imgUrl = field.url?field.url: Holders.config.aws.s3.defaultS3Url
//        String defaultImageName = field.defaultValue?field.defaultValue: Holders.config.aws.s3.defaultImageValue
        /*if("localhost".equals(field.url)){
            return "/" + defaultImageName
        }*/
        return Holders.config.images.defaultImagePath
    }

    public Object getInitValues(DataframeVue df, Map fieldProps){
        return fieldProps.url?:getDefaultImageName()
    }
    String pictureButtonCode(DataframeVue dataframe, Map field,String html){
        String setButtonAttr = field.setButtonAttr?:""
        String setIconAttr = field.setIconAttr?:""
        html ="""<div class="d-flex justify-end mx-3 mb-3">
                        <v-btn $setButtonAttr><v-img $setIconAttr></v-img></v-btn>
                  </div> 
                """

     }
    private String getRefHtml(Map onClick, DataframeVue dataframe){
        StringBuilder sb = new StringBuilder()
        DataframeVue refDataframe = DataframeVue.getDataframeBeanFromReference(onClick.refDataframe)
        String refDataframeName = refDataframe.dataframeName
        String dialogBoxWidth = onClick.dialogBoxWidth?:"initial"
        VueStore store = dataframe.getVueJsBuilder().getVueStore()
        store.addToDataframeVisibilityMap("${refDataframeName} : false,\n")
        dataframe.getVueJsBuilder().addToDataScript(" ${refDataframeName}_data:{key:'', refreshGrid:true},\n")
        def maxWidth = onClick.dialogBoxMaxWidth?:"500px"
        if(onClick.showAsDialog){
            sb.append("""<v-dialog v-model="visibility.${refDataframeName}" width='$dialogBoxWidth' max-width='$maxWidth'><v-card>""")
            sb.append(refDataframe.getComponentName("resetForm=true "))
            sb.append("""</v-card></v-dialog>""")
        } else {
            sb.append(refDataframe.getComponentName("resetForm=true"))
        }

        return sb.toString()
    }

}
