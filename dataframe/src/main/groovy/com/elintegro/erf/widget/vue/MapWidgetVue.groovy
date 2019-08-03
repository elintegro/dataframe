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
import com.elintegro.erf.dataframe.vue.VueJsBuilder

class MapWidgetVue extends WidgetVue{
/** Need to tansfer addressValue as prop in the component tag*/
    @Override
    String getHtml(DataframeVue dataframe, Map field) {
        String fldName = dataframe.getDataVariableForVue(field)
        String height = field.height?:'200px'
        String width = field.width?:'auto'
        String html = """ <div class="google-map" id="$fldName" style="height: $height; width: $width; position: relative; overflow: hidden;"></div> """
        if(field?.layout){
            html = applyLayout(dataframe, field, html)
        }
        return html
    }

    String getVueDataVariable(DataframeVue dataframe, Map field) {
        String dataVariable = dataframe.getDataVariableForVue(field)
        String dataframeName = dataframe.dataframeName
        VueJsBuilder vueJsBuilder = dataframe.getVueJsBuilder()
                .addToCreatedScript(""" this.callValidateMethod(this.addressValue);\n""")
                .addToWatchScript(""" addressValue: function(){this.callValidateMethod(this.addressValue);}""")
                .addToPropsScript("'addressValue'")
                .addToMethodScript("""  
                               callValidateMethod: function(addressValue){
                                        if(addressValue != "" && addressValue!= null && addressValue != undefined){
                                           this.validateGoogleAddress(addressValue);
                                        }
                               },

                              validateGoogleAddress: function(addressValue ){
                                 var googleAddressObject = "";
                                 try {
                                     var geocoder = new google.maps.Geocoder();
                                     var address = addressValue;
                                     geocoder.geocode( { 'address': address}, function(results, status) {
                                         if (status == 'OK') {
                                             ${dataframeName}Var.afterAddressValidationSuccess(results);
                                         } else {
                                             alert('Geocode was not successful for the following reason: ' + status);
                                         }
                                     });
                                 }catch(e){
                                     console.log("Error Occurred"+e);
                                 }
                                 return googleAddressObject;
                              },

                              afterAddressValidationSuccess : function(results){
                                this.mapFunctions(results); // populate data to data obbject
                                if("$field.showInMap"){
                                   this.markInMap(results);
                                }
                             },

                             markInMap: function(results,resMap){
                             var elm = document.getElementById("vueMapWidgetDataframe_googleMap");
                                var map = new google.maps.Map(elm, {
                                           zoom: 8,
                                           center: {lat: -34.397, lng: 150.644}
                                           });
                                map.setCenter(results[0].geometry.location);

                                     var marker = new google.maps.Marker({
                                        map: map,
                                        position: results[0].geometry.location
                                      });
                             },
                             
                             mapFunctions: function(results){
                                  this.\$emit("resultData", results);
                             },\n
                             """)
        return """$dataVariable:\"\",\n"""

    }

    String getValueSetter(DataframeVue dataframe, Map field, String divId, String dataVariable, String key) throws DataframeException{
        def defaultValue = field.defaultValue?:""
        String fillState = ""
        return """this.$dataVariable = response['$key']?response['$key']:"$defaultValue\";
                   this.validateGoogleAddress(this.addressValue);
                """
    }
}
