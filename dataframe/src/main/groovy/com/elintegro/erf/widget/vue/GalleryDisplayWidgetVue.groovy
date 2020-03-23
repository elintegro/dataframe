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
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.vue.DataframeVue

/**
 * Created by kchapagain on Jan, 2019.
 */
class GalleryDisplayWidgetVue extends WidgetVue{
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        boolean isReadOnly = dataframe.isReadOnly(field)
        def previewHeight = field?.previewHeight?:216
        def previewWidth = field?.previewWidth?:346
        String modelString = getModelString(dataframe, field)
        return """
            <viewer 
            :images="${modelString}_images"
            >
            <img style="padding: 1px;" v-for="src in ${modelString}_thumbnailImages" :src="src" :key="src" :height="$previewHeight" :width="$previewWidth">
            </viewer>
        """
    }

    String getStateDataVariable(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        return """
                  
                  ${fldName}_images:[],\n
                  ${fldName}_thumbnailImages:[],\n
               """

    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        String fullFieldName = key.replace(Dataframe.DOT,Dataframe.DASH)

/*
        return """
               var fullFieldName = '$fullFieldName';
               var orginalImages = response.additionalData[fullFieldName]['orginalImages'];
               var thumbnailImages = response.additionalData[fullFieldName]['thumbnailImages'];
               this.${dataVariable}_images = orginalImages;
               this.${dataVariable}_thumbnailImages = thumbnailImages;
              """
*/
        return ""
    }

    public Map loadAdditionalData(DataframeInstance dfInst, String fieldnameToReload, Map inputData, def session){
        Dataframe df = dfInst.df;
        Map fieldProps = df.fields.get(fieldnameToReload)
        Map result = buildData(dfInst, inputData, fieldProps, fieldnameToReload)
        return result
    }

    private static def buildData(DataframeInstance dfInst, Map inputData, Map fieldProps, String fieldnameToReload){
        def selectedValue = null
        Dataframe df = dfInst.df
        Map info = dfInst.getDomainInstanceInfo(fieldnameToReload, inputData)
        if (info?.simpleFieldName && info?.isParameterRelatedToDomain){
            def prop = info.queryDomain.getPropertyByName(info.simpleFieldName)
            def refFieldValues = info.domainInstance?info.domainInstance."${info.simpleFieldName}":null
            if (refFieldValues && df.metaFieldService.isAssociation(prop)){
                String displayMember = fieldProps.displayMember
                def selectedValList = []
                refFieldValues.each{obj ->
                    selectedValList.add(obj."$displayMember")
                }
                selectedValue = selectedValList
            }
        }
        return [orginalImages:selectedValue, thumbnailImages:selectedValue]
    }
}
