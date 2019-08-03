/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package spring.globech

import com.elintegro.erf.dataframe.vue.VueJsEntity
import grails.util.Holders

beans {

    def contextPath = Holders.grailsApplication.config.rootPath
    vueInitDataframe_script(VueJsEntity) { bean ->
        created = """this.checkIfPopupWindow();this.setInitPageValues();\n"""

        methods =
                """  checkIfPopupWindow: function(){
                          var url = window.location.href;
//                           var t = url.searchParams.get("reloadPage"); 
//                           if(url){
//                               window.opener.location.reload();
//                               close();
//                           }
               },\nsetInitPageValues:function(data){
                                               
                                                axios.get('${contextPath}/login/getUserInfo').then(function (responseData) {
                                                     drfExtCont.saveToStore("vueInitDataframe", "key", '');
                                                     drfExtCont.saveToStore("vueProfileMenuDataframe", "key", '');
                                                     drfExtCont.saveToStore("vueInitDataframe", "loggedIn", responseData.data.loggedIn);
                                                     drfExtCont.saveToStore("loggedIn", responseData.data.loggedIn);
//                                                     vueInitDataframeVar.\$store.state.vueInitDataframe = responseData.data;
//                                                     Vue.set(vueInitDataframeVar.\$store.state.vueInitDataframe, "key", '');
//                                                     Vue.set(vueInitDataframeVar.\$store.state.vueProfileMenuDataframe, "key", '');
                                                       var loggedIn = responseData.data.loggedIn
//                                                     vueInitDataframeVar.\$store.state.loggedIn = loggedIn;
                                                       var urlLocation = window.location.href;
                                                       if(loggedIn == false){
//                                                        vueInitDataframeVar.\$router.push("/");this.location.reload();
                                                       }
                                                     
                                                 }).catch(function (error) {
                                                     console.log(error);
                                                 });
                                               } ,  \n
                                                                                                     
                                """
    }

    vueToolbarDataframe_script(VueJsEntity) { bean ->
        data = "newApplication_display:true,\n"

    }

    loginNavigationVue_script(VueJsEntity) { bean ->
        data = "loginNavigationVue_show:true,"
        watch = """registerStateChanged:{handler: function(response, oldVal) {
                                       this.enableDisableNotification(response.data);
                                       }},
               loginClose:{handler: function(response, oldVal) {
                                       this.vueLoginDataframe_display = false;
                                       }}"""
        computed = """registerStateChanged() {return this.\$store.state.loginNavigationVue;},\nloginClose() {return this.\$store.state.vueInitDataframe.vueLoginDataframe_display;}
                                              """
        methods =
                """  enableDisableNotification:function(data){
                                                if(data.success){
                                                    this.vueRegisterDataframe_display = false;
                                                }
                                               } ,  \n
                                               """
    }


    vueAddressDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
        methods = """updateAddressFields(result){
                    console.log("updateAddressFields.");
                        var results = result[0].address_components;
                        jQuery(results).each(function(index){
                                         let typeString = jQuery(this)[0].types[0];
                                         let nameString = jQuery(this)[0].long_name;
                                         if(typeString =="subpremise"){
                                         console.log("Apartment:"+typeString+"::::::"+nameString);
                                         vueAddressDataframeVar.vueAddressDataframe_address_apartment = nameString;
                                         }
                                         if(typeString =="street_address"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                         vueAddressDataframeVar.vueAddressDataframe_address_area = nameString;
                                         }
                                         if(typeString =="route"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                          vueAddressDataframeVar.vueAddressDataframe_address_street = nameString;
                                         }
                                         if(typeString =="locality"){
                                         console.log("City:"+typeString+"::::::"+nameString);
                                         vueAddressDataframeVar.vueAddressDataframe_address_cityString = nameString;
                                         }
                                         if(typeString =="country"){
                                           console.log("Country:"+typeString+"::::::"+ nameString);
                                          vueAddressDataframeVar.vueAddressDataframe_address_countryString = nameString;
                                         }
                                         if(typeString =="postal_code"){
                                          vueAddressDataframeVar.vueAddressDataframe_address_postalZip = nameString;
                                         }
                                         if(typeString =="street_number"){
                                         console.log("street_number:"+typeString+"::::::"+nameString);
                                          jQuery("#addressDataframe-address-streetNbr").val(nameString);
                                         }
                                     });
                                     vueAddressDataframeVar.vueAddressDataframe_address_addressText = result[0].formatted_address;
                                     vueAddressDataframeVar.vueAddressDataframe_address_addressLine = result[0].formatted_address;
                    },"""
    }

    vueLoginDataframe_script(VueJsEntity) { bean ->
        methods = """dialogBoxClose(){
                    console.log("login dataframe close button.");
                    },"""
    }

    vueAfterLoggedinDataframe_script(VueJsEntity) { bean ->
        data = "avatarSize : 40,\n"
        created = "this.populateAfterLoggedIn();\n"
        methods = """populateAfterLoggedIn: function(){
                            var authType = this.\$store.state.vueInitDataframe.authentication
                            var name = "";
                            var imgUrl = "";
                            if(authType == 'oauth'){
                                var details = this.\$store.state.vueInitDataframe
                               this.vueAfterLoggedinDataframe_person_firstName = details.name;
                                this.vueAfterLoggedinDataframe_person_mainPicture = details.imageUrl;
                            }else{
                var allParams = {};
                allParams["id"] = eval(this.namedParamKey);
                allParams['dataframe'] = 'vueAfterLoggedinDataframe';
                axios.get('${contextPath}/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    if(responseData == undefined ||  responseData.data == undefined || responseData.data.data == undefined){
                        console.log("Error login for the user");
                        store.commit('alertMessage', {'snackbar':true, 'alert_type':'error', 'alert_message':"Login failed"})
                    }
                    var response = responseData.data.data;
                    imgUrl = response['vueAfterLoggedinDataframe.person.mainPicture'] ?  '/images/'+response['vueAfterLoggedinDataframe.person.mainPicture'] : "${contextPath}/assets/default_profile.jpg";
                    vueAfterLoggedinDataframeVar.vueAfterLoggedinDataframe_person_mainPicture = imgUrl;
                }).catch(function(error) {
                    console.log(error);
                });
                            }
                            
                     },"""
    }

    vueRegisterDataframe_script(VueJsEntity) { bean ->
        data = "vueRegisterDataframe_display:true,\n checkboxSelected: [],\n"
    }

    vueProfileMenuDataframe_script(VueJsEntity) { bean ->
        computed = """ vueProfileMenuDataframe_person_fullName(){return drfExtCont.capitalize(this.vueProfileMenuDataframe_person_firstName) + " " + drfExtCont.capitalize(this.vueProfileMenuDataframe_person_lastName)}"""
    }

    vueMapWidgetDataframe_script(VueJsEntity) { bean ->
        data = "vueRegisterDataframe_display:true,\n checkboxSelected: [],\n"
    }

    vueApplicationFormDataframe_script(VueJsEntity) { bean ->
        data = "vueApplicationFormDataframe_tab_model : this.tabValue,\nvueApplicationFormDataframe_display: true, \n"
        computed = """tabValue(){return this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model}"""
        watch = """ tabValue:{handler: function(val, oldVal) {this.vueApplicationFormDataframe_tab_model = val;}},"""

    }

    vueMedicalRecordDataframe_script(VueJsEntity){bean->
        computed = """ 
                      closePrescriptionMedication(){
                                      return this.\$store.state.vuePrescribedMedicationsDataframe_display;
                                      },\n"""
        watch = """
                    closePrescriptionMedication:{handler: function(val, oldVal) {
                             this.vuePrescribedMedicationsDataframe_display = false;}},\n"""

    }

    vueMedicationsGridDataframe_script(VueJsEntity){bean ->
        created = "this.getDefaultDataHeaders(); "
    }
    vueMedicationsGridDetailDataframe_script(VueJsEntity){bean -> /* watch = """ vueApplicationFormDetailDataframe_prop: { deep:true, handler: function(){ this.vueApplicationFormDetailDataframe_fillInitData(); } },"""*/
        watch = """ callInitMethod:{handler: function(val, oldVal) {this.vueMedicationsGridDetailDataframe_fillInitData();}},"""
        computed = """ callInitMethod(){  const data = drfExtCont.getFromStore('vueMedicalRecordDetailDataframe', 'key');
                                      return (data!='' && data!= undefined)?data:null},"""
    }

    vueMedicationsGridEditDataframe_script(VueJsEntity){bean -> /* watch = """ vueApplicationFormDetailDataframe_prop: { deep:true, handler: function(){ this.vueApplicationFormDetailDataframe_fillInitData(); } },"""*/
        watch = """ callInitMethod:{handler: function(val, oldVal) {this.vueMedicationsGridEditDataframe_fillInitData();}},"""
        computed = """ callInitMethod(){  const data = drfExtCont.getFromStore('vueMedicalRecordEditDataframe', 'key');
                                      return (data!='' && data!= undefined)?data:null},"""
    }
    vueRegisterMenuDataframe_script(VueJsEntity){bean ->

        methods = """ showContactDetails: function(dfrName, contactType){
                         routeId = contactType?contactType:""
                         dfrName = dfrName.toLowerCase();
                         this.\$router.push({
                         name: dfrName,
                         path: dfrName,
                         params: {
                           dfrName: "test",
                           routeId: contactType
                         }
                       })
                      }"""
    }
    vueEmployeeAddressDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
        methods = """updateAddressFields(result){
                    console.log("updateAddressFields.");
                        var results = result[0].address_components;
                        jQuery(results).each(function(index){
                                         let typeString = jQuery(this)[0].types[0];
                                         let nameString = jQuery(this)[0].long_name;
                                         if(typeString =="subpremise"){
                                         console.log("Apartment:"+typeString+"::::::"+nameString);
                                         vueEmployeeAddressDataframeVar.vueEmployeeAddressDataframe_address_apartment = nameString;
                                         }
                                         if(typeString =="street_address"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                         vueEmployeeAddressDataframeVar.vueEmployeeAddressDataframe_address_area = nameString;
                                         }
                                         if(typeString =="route"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                          vueEmployeeAddressDataframeVar.vueEmployeeAddressDataframe_address_street = nameString;
                                         }
                                         if(typeString =="locality"){
                                         console.log("City:"+typeString+"::::::"+nameString);
                                         vueEmployeeAddressDataframeVar.vueEmployeeAddressDataframe_address_cityString = nameString;
                                         }
                                         if(typeString =="country"){
                                           console.log("Country:"+typeString+"::::::"+ nameString);
                                          vueEmployeeAddressDataframeVar.vueEmployeeAddressDataframe_address_countryString = nameString;
                                         }
                                         if(typeString =="postal_code"){
                                          vueEmployeeAddressDataframeVar.vueEmployeeAddressDataframe_address_postalZip = nameString;
                                         }
                                         if(typeString =="street_number"){
                                         console.log("street_number:"+typeString+"::::::"+nameString);
                                          jQuery("#addressDataframe-address-streetNbr").val(nameString);
                                         }
                                     });
                                     vueEmployeeAddressDataframeVar.vueEmployeeAddressDataframe_address_addressText = result[0].formatted_address;
                                     vueEmployeeAddressDataframeVar.vueEmployeeAddressDataframe_address_addressLine = result[0].formatted_address;
                    },"""
    }
    vueProviderAddressDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
        methods = """updateAddressFields(result){
                    console.log("updateAddressFields.");
                        var results = result[0].address_components;
                        jQuery(results).each(function(index){
                                         let typeString = jQuery(this)[0].types[0];
                                         let nameString = jQuery(this)[0].long_name;
                                         if(typeString =="subpremise"){
                                         console.log("Apartment:"+typeString+"::::::"+nameString);
                                         vueProviderAddressDataframeVar.vueProviderAddressDataframe_address_apartment = nameString;
                                         }
                                         if(typeString =="street_address"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                         vueProviderAddressDataframeVar.vueProviderAddressDataframe_address_area = nameString;
                                         }
                                         if(typeString =="route"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                          vueProviderAddressDataframeVar.vueProviderAddressDataframe_address_street = nameString;
                                         }
                                         if(typeString =="locality"){
                                         console.log("City:"+typeString+"::::::"+nameString);
                                         vueProviderAddressDataframeVar.vueProviderAddressDataframe_address_cityString = nameString;
                                         }
                                         if(typeString =="country"){
                                           console.log("Country:"+typeString+"::::::"+ nameString);
                                          vueProviderAddressDataframeVar.vueProviderAddressDataframe_address_countryString = nameString;
                                         }
                                         if(typeString =="postal_code"){
                                          vueProviderAddressDataframeVar.vueProviderAddressDataframe_address_postalZip = nameString;
                                         }
                                         if(typeString =="street_number"){
                                         console.log("street_number:"+typeString+"::::::"+nameString);
                                          jQuery("#addressDataframe-address-streetNbr").val(nameString);
                                         }
                                     });
                                     vueProviderAddressDataframeVar.vueProviderAddressDataframe_address_addressText = result[0].formatted_address;
                                     vueProviderAddressDataframeVar.vueProviderAddressDataframe_address_addressLine = result[0].formatted_address;
                    },"""
    }
    vueVendorAddressDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
        methods = """updateAddressFields(result){
                    console.log("updateAddressFields.");
                        var results = result[0].address_components;
                        jQuery(results).each(function(index){
                                         let typeString = jQuery(this)[0].types[0];
                                         let nameString = jQuery(this)[0].long_name;
                                         if(typeString =="subpremise"){
                                         console.log("Apartment:"+typeString+"::::::"+nameString);
                                         vueVendorAddressDataframeVar.vueVendorAddressDataframe_address_apartment = nameString;
                                         }
                                         if(typeString =="street_address"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                         vueVendorAddressDataframeVar.vueVendorAddressDataframe_address_area = nameString;
                                         }
                                         if(typeString =="route"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                          vueVendorAddressDataframeVar.vueVendorAddressDataframe_address_street = nameString;
                                         }
                                         if(typeString =="locality"){
                                         console.log("City:"+typeString+"::::::"+nameString);
                                         vueVendorAddressDataframeVar.vueVendorAddressDataframe_address_cityString = nameString;
                                         }
                                         if(typeString =="country"){
                                           console.log("Country:"+typeString+"::::::"+ nameString);
                                          vueVendorAddressDataframeVar.vueVendorAddressDataframe_address_countryString = nameString;
                                         }
                                         if(typeString =="postal_code"){
                                          vueVendorAddressDataframeVar.vueVendorAddressDataframe_address_postalZip = nameString;
                                         }
                                         if(typeString =="street_number"){
                                         console.log("street_number:"+typeString+"::::::"+nameString);
                                          jQuery("#addressDataframe-address-streetNbr").val(nameString);
                                         }
                                     });
                                     vueVendorAddressDataframeVar.vueVendorAddressDataframe_address_addressText = result[0].formatted_address;
                                     vueVendorAddressDataframeVar.vueVendorAddressDataframe_address_addressLine = result[0].formatted_address;
                    },"""
    }
    vueAddressDetailDataframe_script(VueJsEntity) { bean ->

        watch = """ refreshVueAddressDataframe:{handler: function(val, oldVal) {this.vueAddressDetailDataframe_fillInitData();}},"""
        computed = "refreshVueAddressDataframe(){return this.\$store.state.vueContactDetailDataframe.key},"
        methods = """updateAddressFields(result){
                    console.log("updateAddressFields.");
                        var results = result[0].address_components;
                        jQuery(results).each(function(index){
                                         let typeString = jQuery(this)[0].types[0];
                                         let nameString = jQuery(this)[0].long_name;
                                         if(typeString =="subpremise"){
                                         console.log("Apartment:"+typeString+"::::::"+nameString);
                                         vueAddressDetailDataframeVar.vueAddressDetailDataframe_address_apartment = nameString;
                                         }
                                         if(typeString =="street_address"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                         vueAddressDetailDataframeVar.vueAddressDetailDataframe_address_area = nameString;
                                         }
                                         if(typeString =="route"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                          vueAddressDetailDataframeVar.vueAddressDetailDataframe_address_street = nameString;
                                         }
                                         if(typeString =="locality"){
                                         console.log("City:"+typeString+"::::::"+nameString);
                                         vueAddressDetailDataframeVar.vueAddressDetailDataframe_address_cityString = nameString;
                                         }
                                         if(typeString =="country"){
                                           console.log("Country:"+typeString+"::::::"+ nameString);
                                          vueAddressDetailDataframeVar.vueAddressDetailDataframe_address_countryString = nameString;
                                         }
                                         if(typeString =="postal_code"){
                                          vueAddressDetailDataframeVar.vueAddressDetailDataframe_address_postalZip = nameString;
                                         }
                                         if(typeString =="street_number"){
                                         console.log("street_number:"+typeString+"::::::"+nameString);
                                          jQuery("#addressDataframe-address-streetNbr").val(nameString);
                                         }
                                     });
                                     vueAddressDetailDataframeVar.vueAddressDetailDataframe_address_addressText = result[0].formatted_address;
                                     vueAddressDetailDataframeVar.vueAddressDetailDataframe_address_addressLine = result[0].formatted_address;
                    },"""
    }
    vueAddressEditDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
        watch = """ refreshVueAddressDataframe:{handler: function(val, oldVal) {this.vueAddressEditDataframe_fillInitData();}},"""
        computed = "refreshVueAddressDataframe(){return this.\$store.state.vueContactEditDataframe.key},"
        methods = """updateAddressFields(result){
                    console.log("updateAddressFields.");
                        var results = result[0].address_components;
                        jQuery(results).each(function(index){
                                         let typeString = jQuery(this)[0].types[0];
                                         let nameString = jQuery(this)[0].long_name;
                                         if(typeString =="subpremise"){
                                         console.log("Apartment:"+typeString+"::::::"+nameString);
                                         vueAddressEditDataframeVar.vueAddressEditDataframe_address_apartment = nameString;
                                         }
                                         if(typeString =="street_address"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                         vueAddressEditDataframeVar.vueAddressEditDataframe_address_area = nameString;
                                         }
                                         if(typeString =="route"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                          vueAddressEditDataframeVar.vueAddressEditDataframe_address_street = nameString;
                                         }
                                         if(typeString =="locality"){
                                         console.log("City:"+typeString+"::::::"+nameString);
                                         vueAddressEditDataframeVar.vueAddressEditDataframe_address_cityString = nameString;
                                         }
                                         if(typeString =="country"){
                                           console.log("Country:"+typeString+"::::::"+ nameString);
                                          vueAddressEditDataframeVar.vueAddressEditDataframe_address_countryString = nameString;
                                         }
                                         if(typeString =="postal_code"){
                                          vueAddressEditDataframeVar.vueAddressEditDataframe_address_postalZip = nameString;
                                         }
                                         if(typeString =="street_number"){
                                         console.log("street_number:"+typeString+"::::::"+nameString);
                                          jQuery("#addressDataframe-address-streetNbr").val(nameString);
                                         }
                                     });
                                     vueAddressEditDataframeVar.vueAddressEditDataframe_address_addressText = result[0].formatted_address;
                                     vueAddressEditDataframeVar.vueAddressEditDataframe_address_addressLine = result[0].formatted_address;
                    },"""
    }
}
