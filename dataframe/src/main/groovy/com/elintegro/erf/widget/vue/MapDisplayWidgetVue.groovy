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
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.ParsedHql
import com.elintegro.erf.dataframe.db.fields.MetaField
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.dataframe.vue.VueJsBuilder
import org.grails.web.json.JSONObject

class MapDisplayWidgetVue extends WidgetVue{

    private static final String latitude = "Latitude"
    private static final String longitude = "Longitude"

    boolean setPersistedValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataRequestParamMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        Map additionalData = loadMapsData(dfInstance, fieldProps, additionalDataRequestParamMap)
        if(additionalData.containsKey(items)){
            jData?.persisters?."${domainAlias}"."${fieldName}"."${items}" = additionalData."${items}"
        }
       return true
    }

    boolean setTransientValueToResponse(JSONObject jData, def value, String domainAlias, String fieldName, Map additionalDataMap, DataframeInstance dfInstance, Object sessionHibernate, Map fieldProps){
        Map additionalData = loadMapsData(dfInstance, fieldProps, additionalDataMap)
        if(additionalData.containsKey(items)){
            jData?.transits?."${fieldName}"."${items}" = additionalData."${items}"
        }
        return true
    }

    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = getFieldName(dataframe, field)
        String html = """ <div class="google-map" id="$fldName" style="height: ${getHeight(field, '200px')}; width: ${getWidth(field)}; position: relative; overflow: hidden;"></div> """
        if(field?.layout){
            html = applyLayout(dataframe, field, html)
        }
        setMapsStateScript(dataframe, field)
        return html
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        if (field?.hql){
            ParsedHql parsedHql = new ParsedHql(field.hql, dataframe.grailsApplication, dataframe.sessionFactory)
            List<MetaField> fieldMetaData = dataframe.metaFieldService.getMetaDataFromFields(parsedHql, field.name);
            field.put("gridMetaData", fieldMetaData);
            field.put("parsedHql", parsedHql);
            if (!isContainsLongLatAlias(fieldMetaData)){
                throw new DataframeException("Hql doesn't contains field alias values $longitude or $latitude")
            }
        }
        return """$dataVariable:\"\",\n"""

    }

    Map getStateDataVariablesMap(DataframeVue dataframe, Map field){
        List mapItems = []
        if (isInitBeforePageLoad(field)){
            mapItems = getMapsData(dataframe, field, [:])
        }
        Map fldJSON = getDomainFieldJsonMap(dataframe, field)
        fldJSON?.put(items, mapItems)
        return dataframe.domainFieldMap
    }


    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        def defaultValue = field.defaultValue?:""
        return """this.$dataVariable = response['$key']?response['$key']:"$defaultValue\";
                """
    }

    private static boolean isContainsLongLatAlias(List<MetaField> fieldMetaData){
        boolean isLong = false
        boolean isLat = false
        for (MetaField metaField : fieldMetaData){
            if (metaField.alias.equals(longitude)){
                isLong = true
            }else if (metaField.alias.equals(latitude)){
                isLat = true
            }
        }
        return isLong && isLat
    }

    private static Map loadMapsData(DataframeInstance dataframeInst, Map fieldProps, Map inputData){
        Map result = [:]
        DataframeVue dataframe = dataframeInst.df;
        GridWidgetVue.createInputParamsData(dataframeInst, inputData)
        result.put(items, getMapsData(dataframe, fieldProps, inputData))
        return result
    }

    private static List getMapsData(DataframeVue dataframe, Map fieldProps, Map inputData){
        List resultList = []
        String wdgHql = fieldProps?.hql;
        if(wdgHql){
            resultList = GridWidgetVue.getResultList(fieldProps, dataframe, wdgHql, inputData)
        }
        return resultList
    }

    private void setMapsStateScript(DataframeVue dataframe, Map field){
        String fldName = getFieldName(dataframe, field)
        Map center = field.center?:[lat: 45.508888, lng: -73.561668]
        String itemsStr = getFieldJSONItems(field)
        String displayMember = field?.displayMember
        int zoom = field?.zoom?:8
        String mapTypeId = field?.mapTypeId?:"ROADMAP"
        String addListenerScript = field?.addListenerScript
        String markerClickEvent = """google.maps.event.addListener(marker, 'click', (function (marker, i) {
                                             return function () {
                                                 infowindow.setContent(locations[i]['${displayMember}']);
                                                 infowindow.open(map, marker);
                                             }
                                         })(marker, i));"""
        VueJsBuilder store = dataframe.getVueJsBuilder()
                .addToMethodScript("""  
                               initializeMaps: function(){
                                     let locations = this.$itemsStr;
                                     let map = new google.maps.Map(document.getElementById('$fldName'), {
                                         zoom: $zoom,
                                         center: new google.maps.LatLng(${center.lat}, ${center.lng}),
                                         mapTypeId: google.maps.MapTypeId.$mapTypeId
                                     });
                                     let infowindow = new google.maps.InfoWindow();
                                     let markerBounds = new google.maps.LatLngBounds();
                                     let marker, i;
                                     for (i = 0; i < locations.length; i++) {
                                         let latlng = new google.maps.LatLng(locations[i]['${latitude}'], locations[i]['${longitude}']);
                                         marker = new google.maps.Marker({
                                             position: latlng,
                                             map: map
                                         });
                                         markerBounds.extend(latlng);
                                         ${displayMember?markerClickEvent:""}
                                         $addListenerScript   
                                     }
                                     map.fitBounds(markerBounds);
                               },
                             """)
        if (isInitBeforePageLoad(field)){
            store.addToMountedScript("""this.initializeMaps();\n""")
        }
    }
}
