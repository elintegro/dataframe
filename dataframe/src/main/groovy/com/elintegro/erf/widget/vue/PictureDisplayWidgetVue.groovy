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

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.vue.DataframeVue
import grails.util.Holders

class PictureDisplayWidgetVue extends WidgetVue{
    @Override
    String getHtml(DataframeVue dataframe, Map field) {

        String fldParam     = dataframe.getDataVariableForVue(field)
        String height       = getHeight(field)
        String width        = getWidth(field)
        String aspectRatio  = field.aspectRatio?field.aspectRatio:"2.75"
        String heightString = height?"height=$height":""
        String widthString  = width?"""width=$width """:""
        String modelString = getModelString(dataframe, field)
        String html =  """<v-img
           id = "$fldParam"
          :src="$modelString"
          
          :alt = "${modelString}_alt"
          aspect-ratio="$aspectRatio"
          ${toolTip(field)} 
           $heightString
           $widthString
          ${getAttr(field)} ></v-img>"""
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

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{

/*
        return """this.$dataVariable = response['$key']?"$imgUrl"+response['$key']:"$defImgUrl";\n
                  this.${dataVariable}_alt = response['$key']?response['$key']:"$defImgUrl";"""
*/
        return ""
    }

    String getVueSaveVariables(DataframeVue dataframe, Map field){
        return """"""
    }



    private String getDefaultImageName(){
//        def imgUrl = field.url?field.url: Holders.config.aws.s3.defaultS3Url
//        String defaultImageName = field.defaultValue?field.defaultValue: Holders.config.aws.s3.defaultImageValue
        /*if("localhost".equals(field.url)){
            return "/" + defaultImageName
        }*/
        return Holders.config.images.defaultImagePath
    }
}
