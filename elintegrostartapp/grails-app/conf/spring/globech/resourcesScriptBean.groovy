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
    def defaultUrl = Holders.grailsApplication.config.grails.serverURL
    vueInitDataframe_script(VueJsEntity) { bean ->
        created = """this.setInitPageValues();
                     this.setupHomePage()
                    """

        methods =
                """  setupHomePage: function(){
                          var currentUrl = window.location.href;
                          var defaultUrl = '${defaultUrl}/#/';
                          if(sessionStorage.initialRefresh == null || sessionStorage.initialRefresh == undefined || sessionStorage.initialRefresh == true){
                          if(currentUrl == defaultUrl){
                            let homePage = "vueElintegroBannerDataframe";
                            let routeId = 0;
                            this.\$router.push({
                                  name: homePage,
                                path: homePage,
                                params: {
                                      routeId: routeId
                                }
                            })
                            }
                            sessionStorage.initialRefresh = false;
                          }//End of if
                     }
               ,\nsetInitPageValues:function(){
                                               
                                                axios.get('${contextPath}/login/getUserInfo').then(function (responseData) {
                                                     excon.saveToStore("vueInitDataframe", "key", '');
                                                     excon.saveToStore("vueElintegroProfileMenuDataframe", "key", '');
                                                     excon.saveToStore("vueInitDataframe", "loggedIn", responseData.data.loggedIn);
                                                     excon.saveToStore("loggedIn", responseData.data.loggedIn);
//                                                     vueInitDataframeVar.\$store.state.vueInitDataframe = responseData.data;
//                                                     Vue.set(vueInitDataframeVar.\$store.state.vueInitDataframe, "key", '');
//                                                     Vue.set(vueInitDataframeVar.\$store.state.vueElintegroProfileMenuDataframe, "key", '');
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
    vueElintegroRegisterDataframe_script(VueJsEntity) { bean ->
        data = "vueElintegroRegisterDataframe_display:true,\n checkboxSelected: [],\n"
    }

//    vueRegisterDataframe_script(VueJsEntity) { bean ->
//        data = "vueRegisterDataframe_display:true,\n checkboxSelected: [],\n"
//    }

//    vueProfileMenuDataframe_script(VueJsEntity) { bean ->
//        computed = """ vueProfileMenuDataframe_person_fullName(){return excon.capitalize(this.vueProfileMenuDataframe_person_firstName) + " " + excon.capitalize(this.vueProfileMenuDataframe_person_lastName)}"""
//    }
    vueElintegroProfileMenuDataframe_script(VueJsEntity) { bean ->
        computed = """ vueElintegroProfileMenuDataframe_person_fullName(){return excon.capitalize(this.state.vueElintegroProfileMenuDataframe_person_firstName) + " " + excon.capitalize(this.state.vueElintegroProfileMenuDataframe_person_lastName)},
                       vueElintegroProfileMenuDataframe_person_email(){return this.state.vueElintegroProfileMenuDataframe_person_email}"""
    }
    vueElintegroUserProfileDataframe_script(VueJsEntity){bean ->
        def imagePath = Holders.grailsApplication.config.images.storageLocation + "/"
        created = """this.vueElintegroProfileMenuDataframeShow();"""

        methods = """vueElintegroProfileMenuDataframeShow : function(){
                  excon.setVisibility("vueElintegroProfileMenuDataframe",false)
                  },\n
                  
                  editProfile : function(){
                                  var allParams = this.state;
                                  allParams['dataframe'] = 'vueNewEmployeeBasicInformationDataframe';
                                  var self = this;
                                  var imageName = this.state.vueElintegroUserProfileDataframe_propertyImages
                                  var imageUrl = '$imagePath' + imageName
                                  if (this.\$refs.vueElintegroUserProfileDataframe_form.validate()){
                                     axios({
                                           method:'post',
                                           url:'${contextPath}/ProfileDetail/editProfileData',
                                           data: allParams
                                     }).then(function(responseData){
                                         var response = responseData;
                                         self.vueElintegroUserProfileDataframe_propertyImages_ajaxFileSave(response,allParams);
                                          setTimeout(function(){this.location.reload();}, 2000);
                                         });
                                  }   
                                  else{
                                       alert("Error in saving")
                                  }
                  
                  }
                  
                  """
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
        computed = """ callInitMethod(){  const data = excon.getFromStore('vueMedicalRecordDetailDataframe', 'key');
                                      return (data!='' && data!= undefined)?data:null},"""
    }

    vueMedicationsGridEditDataframe_script(VueJsEntity){bean -> /* watch = """ vueApplicationFormDetailDataframe_prop: { deep:true, handler: function(){ this.vueApplicationFormDetailDataframe_fillInitData(); } },"""*/
        watch = """ callInitMethod:{handler: function(val, oldVal) {this.vueMedicationsGridEditDataframe_fillInitData();}},"""
        computed = """ callInitMethod(){  const data = excon.getFromStore('vueMedicalRecordEditDataframe', 'key');
                                      return (data!='' && data!= undefined)?data:null},"""
    }
    vueRegisterMenuDataframe_script(VueJsEntity){bean ->newEmployeeBasicInformation()

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

    vueElintegroNavigationDrawerDataframe_script(VueJsEntity){bean ->
        data = """drawer: false, group: null,"""
    }
    vueNewEmployeeApplicantDataframe_script(VueJsEntity){bean->
        data = "vueNewEmployeeApplicantDataframe_tab_model : this.tabValue,\nvueNewEmployeeApplicantDataframe_display: true, \n"
        computed = """tabValue(){return this.\$store.state.vueNewEmployeeApplicantDataframe.vueNewEmployeeApplicantDataframe_tab_model}"""
        watch = """ tabValue:{handler: function(val, oldVal) {this.vueNewEmployeeApplicantDataframe_tab_model = val;}},"""
    }
    vueNewEmployeeBasicInformationDataframe_script(VueJsEntity){bean ->
        methods = """  
                      newEmployeeBasicInformation(){
                      console.log("Inside employeeinformation")
                       var details = this.state.vueNewEmployeeBasicInformationDataframe
                       console.log(details)
                       var allParams = this.state;
                       //allParams['firstName'] = this.state.vueNewEmployeeBasicInformationDataframe_person_firstName;
                       //allParams['lastName'] = this.state.vueNewEmployeeBasicInformationDataframe_person_lastName;
                       //allParams['email'] = this.state.vueNewEmployeeBasicInformationDataframe_person_email;
                       //allParams['phone'] = this.state.vueNewEmployeeBasicInformationDataframe_person_phone;
                       //allParams['linkedin'] = this.state.vueNewEmployeeBasicInformationDataframe_application_linkedin;
                       //allParams['availablePosition'] = this.state.vueNewEmployeeBasicInformationDataframe_person_availablePosition;
                       allParams['dataframe'] = 'vueNewEmployeeBasicInformationDataframe';
                       console.log(allParams)
                       
                       console.log("do you see all params?")
                       if (this.\$refs.vueNewEmployeeBasicInformationDataframe_form.validate()){
                       axios({
                       method:'post',
                       url:'${contextPath}/EmployeeApplication/createApplicant',
                       data: allParams
                         }).then(function(responseData){
                          var response = responseData;
                          //excon.saveToStore("vueNewEmployeeUploadResumeDataframe","vueNewEmployeeUploadResumeDataframe_resume_id",response.data.id)
                          //Here is I put generated keys to the Vue component Store of this Vue component (dataframe) in order to other dataframes be 
                          // able to use them to complete the data for the same records...  
                          //excon.saveToStore("vueNewEmployeeUploadResumeDataframe","key_person_id",response.data.person_id)
                          //excon.saveToStore("vueNewEmployeeUploadResumeDataframe","key_application_id",response.data.application_id)
                          excon.saveToStore("vueNewEmployeeBasicInformationDataframe","key_person_id",response.data.person_id)
                          excon.saveToStore("vueNewEmployeeBasicInformationDataframe","key_application_id",response.data.application_id)                                                                              
                          console.log(response)                            
                });
                
                       excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeUploadResumeDataframe-tab-id"); 
                  }   
                  else{
                      alert("Error in saving")
                  }
                   }
                          """
    }
    vueNewEmployeeUploadResumeDataframe_script(VueJsEntity){
        methods = """
                 newEmployeeUploadResume(){
                      var allParams = this.state;
                      var avatar = [];
                      var pictures =  this.vueNewEmployeeUploadResumeDataframe_images_files;
                      for(var i=0; i< pictures.length; i++){
                         avatar[i] = pictures[i].name;
                      }
                      allParams['vueNewEmployeeUploadResumeDataframe_avatar'] = avatar;
                      var doc = [];
                      var files = this.vueNewEmployeeUploadResumeDataframe_resume_files;
                      for(var i=0; i< files.length; i++){
                         doc[i] = files[i].name;
                      }
                      allParams['vueNewEmployeeUploadResumeDataframe_resume'] = doc;
                      allParams['vueNewEmployeeUploadResumeDataframe_application_id'] = excon.getFromStore("vueNewEmployeeBasicInformationDataframe","key_application_id")                                                                              
                      var self = this;
                      if (this.\$refs.vueNewEmployeeUploadResumeDataframe_form.validate()){
                          axios({
                              method:'post',
                              url:'${contextPath}/EmployeeApplication/applicantDocuments',
                              data: allParams
                          }).then(function(responseData){
                              var response = responseData;
                              excon.saveToStore("vueNewEmployeeUploadResumeDataframe","key_vueNewEmployeeUploadResumeDataframe_application_id_id", response.data['application_id']);
                              self.vueNewEmployeeUploadResumeDataframe_images_ajaxFileSave(response,allParams);
                              self.vueNewEmployeeUploadResumeDataframe_resume_ajaxFileSave(response,allParams);
                              excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeSelfAssesmentDataframe-tab-id");
  
                          });
                          
               
                      }  
                      else{
                           alert("Error in saving")
                      }
                     
                 }
        """
    }
    vueNewEmployeeSelfAssesmentDataframe_script(VueJsEntity){
        created = """this.fillApplicationSkillTable();"""
        methods = """
                 fillApplicationSkillTable(){  
                 var details = this.state.vueNewEmployeeSelfAssesmentDataframe
                 console.log(details);
                 var allParams = {};
                       var self = this;
                       allParams['id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe','key_vueNewEmployeeUploadResumeDataframe_application_id_id')
                       
                       allParams['dataframe'] = 'vueNewEmployeeSelfAssesmentDataframe';
                       console.log(allParams)
                       axios({
                       method:'post',
                       url:'${contextPath}/EmployeeApplication/initiateSkillSet',
                       data: allParams
                         }).then(function(responseData){
                         self.vueNewEmployeeSelfAssesmentDataframe_fillInitData();
                         
                          var response = responseData;
                          console.log(response)
                        
                });
                 
                  }
                  """
    }
    vueNewEmployeeApplicantAddSkillDataframe_script(VueJsEntity){bean ->
        methods = """addNewSkill(){
                                    var details = this.state.vueNewEmployeeApplicantAddSkillDataframe;                           
                                    var details = this.state.vueNewEmployeeApplicantAddSkillDataframe
                                    console.log(details);
                                    var allParams = this.state;
                                    var self = this;
                                    allParams['id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe','key_vueNewEmployeeUploadResumeDataframe_application_id_id')
                                    allParams['vueNewEmployeeApplicantAddSkillDataframe_application_id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe','key_vueNewEmployeeUploadResumeDataframe_application_id_id')
                                    allParams['dataframe'] = 'vueNewEmployeeApplicantAddSkillDataframe';
                                    console.log(allParams)
                                             
                                    axios({
                                           method:'post',
                                           url:'${contextPath}/EmployeeApplication/addNewSkillSet',
                                            data: allParams
                                    }).then(function(responseData){
                                                                   var response = responseData.data;
                                                                   excon.setVisibility("vueNewEmployeeApplicantAddSkillDataframe", false);
                                                                   excon.refreshDataForGrid(response,'vueNewEmployeeSelfAssesmentDataframe', 'vueNewEmployeeSelfAssesmentDataframe_applicationSkill', 'I');                                                 
                                                                   console.log(response)                      
                                                                   });
                  }"""

    }
    vueNewEmployeeThankYouMessageAfterSaveDataframe_script(VueJsEntity) { bean ->
        computed = """ vueNewEmployeeThankYouMessageAfterSaveDataframe_person_fullName(){return excon.capitalize(this.state.vueNewEmployeeThankYouMessageAfterSaveDataframe_person_firstName) + " " + excon.capitalize(this.state.vueNewEmployeeThankYouMessageAfterSaveDataframe_person_lastName)}"""
    }

    vueElintegroApplicantDetailsDataframe_script(VueJsEntity){bean->
        data = "vueElintegroApplicantDetailsDataframe_tab_model : this.tabValue,\nvueElintegroApplicantDetailsDataframe_display: true, \n"
        computed = """tabValue(){return this.\$store.state.vueElintegroApplicantDetailsDataframe.vueElintegroApplicantDetailsDataframe_tab_model}"""
        watch = """ tabValue:{handler: function(val, oldVal) {this.vueElintegroApplicantDetailsDataframe_tab_model = val;}},"""
    }
    vueElintegroApplicantGeneralInformationDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueElintegroApplicantGeneralInformationDataframe:{handler: function(val, oldVal) {this.vueElintegroApplicantGeneralInformationDataframe_fillInitData();}},"""
        computed = "refreshVueElintegroApplicantGeneralInformationDataframe(){return this.vueElintegroApplicantGeneralInformationDataframe_prop.key},"
    }
    vueElintegroApplicantSelfAssessmentDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueElintegroApplicantSelfAssessmentDataframe:{handler: function(val, oldVal) {this.vueElintegroApplicantSelfAssessmentDataframe_fillInitData();}},"""
        computed = "refreshVueElintegroApplicantSelfAssessmentDataframe(){return this.vueElintegroApplicantSelfAssessmentDataframe_prop.key},"
    }
    vueElintegroApplicantCVDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueElintegroApplicantCVDataframe:{handler: function(val, oldVal) {this.vueElintegroApplicantCVDataframe_fillInitData();}},"""
        computed = "refreshVueElintegroApplicantCVDataframe(){return this.vueElintegroApplicantCVDataframe_prop.key},"
    }
    vueElintegroApplicantQuestionAnswerDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueElintegroApplicantQuestionAnswerDataframe:{handler: function(val, oldVal) {this.vueElintegroApplicantQuestionAnswerDataframe_fillInitData();}},"""
        computed = "refreshVueElintegroApplicantQuestionAnswerDataframe(){return this.vueElintegroApplicantQuestionAnswerDataframe_prop.key},"
    }
    vueElintegroCommentPageForApplicantDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueElintegroCommentPageForApplicantDataframe:{handler: function(val, oldVal) {this.vueElintegroCommentPageForApplicantDataframe_fillInitData();}},"""
        computed = "refreshVueElintegroCommentPageForApplicantDataframe(){return this.vueElintegroCommentPageForApplicantDataframe_prop.key},"
    }

    vueNewEmployeeApplicantEditSkillDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueNewEmployeeApplicantEditSkillDataframe:{handler: function(val, oldVal) {this.vueNewEmployeeApplicantEditSkillDataframe_fillInitData();}},"""
        computed = "refreshVueNewEmployeeApplicantEditSkillDataframe(){return this.vueNewEmployeeApplicantEditSkillDataframe_prop.key},"
    }
    vueElintegroApplicantCVDataframe_script(VueJsEntity){ bean ->
        def pathForPdf = Holders.grailsApplication.config.images.defaultImagePathForPdf
        def pathForExcel = Holders.grailsApplication.config.images.defaultImagePathForExcel
        def pathForDocFile = Holders.grailsApplication.config.images.defaultImagePathForDocFile
        def pathForCsvFile =  Holders.grailsApplication.config.images.defaultImagePathForCsvFile
        methods ="""afterRefreshing(response){
              
                                 var params = response;
                                 var fileName = params.vueElintegroApplicantCVDataframe_files_fileName;
                                  var extension = fileName.split('.').pop();
                                  if(extension == 'pdf'){
                                    var defaultImageUrlForPdf = '${pathForPdf}'
                                    excon.saveToStore('vueElintegroApplicantCVDataframe','vueElintegroApplicantCVDataframe_files_fileName','${pathForPdf}')   
                                  }
                                  else if(extension == 'xlsx' || extension == 'xlsm' || extension == 'xlsb' || extension == 'xltx'){
                                       excon.saveToStore('vueElintegroApplicantCVDataframe','vueElintegroApplicantCVDataframe_files_fileName','${pathForExcel}')   

                                  }
                                  else if(extension == 'doc' || extension == 'docx'){
                                      excon.saveToStore('vueElintegroApplicantCVDataframe','vueElintegroApplicantCVDataframe_files_fileName','${pathForDocFile}')   
                                  }
                                  else if(extension == 'csv' || extension == 'CSV'){
                                      excon.saveToStore('vueElintegroApplicantCVDataframe','vueElintegroApplicantCVDataframe_files_fileName','${pathForCsvFile}')   
                                  }
                                  
                                  excon.saveToStore('vueElintegroApplicantCVDataframe','vueElintegroApplicantCVDataframe_files_fileName_name',fileName)
                                  
                                  var applicantId = response.vueElintegroApplicantCVDataframe_application_id;
                                  var imageSrc = "/fileDownload/imagePreview/"+applicantId;
                                  excon.saveToStore('vueElintegroApplicantCVDataframe','vueElintegroApplicantCVDataframe_images_name',imageSrc);    
                                 
                                  },\n
                              
        """
    }
    vueElintegroCommentPageForApplicantDataframe_script(VueJsEntity){bean ->
        methods ="""addCommentsForApplicant(){
                                        
                                    var allParams = this.state;
                                    var self = this;
                                    allParams['dataframe'] = 'vueElintegroCommentPageForApplicantDataframe';
                                    axios({
                                           method:'post',
                                           url:'${contextPath}/EmployeeApplication/addComment',
                                           data: allParams
                                    }).then(function(responseData){
                                                                   var response = responseData.data;
                                                                   self.vueElintegroCommentPageForApplicantDataframe_fillInitData()
                                                                   });
                  }"""
    }
    vueTranslatorAssistantDataframe_script(VueJsEntity) {
        data = """itemExists:false,"""
        watch = """itemExistOrNot:function(val){ if(val == true){this.itemExists = true}else{this.itemExists = false}}"""
        computed = """ itemExistOrNot(){
                                            var items = excon.getFromStore('vueTranslatorAssistantDataframe','vueTranslatorAssistantDataframe_project_list')
                                            if(items !=null){
                                            return true;
                                            }
                                            else{
                                            return false;
                                            }
                                      }
                                 """
    }
    vueCreateProjectForTranslationDataframe_script(VueJsEntity){bean->
        methods ="""saveProject(){
                    var allParams = this.state;
                    var self = this;
                    allParams['dataframe'] = 'vueCreateProjectForTranslationDataframe';
                                    axios({
                                           method:'post',
                                           url:'${contextPath}/translatorAssistant/saveProjectData',
                                           data: allParams
                                    }).then(function(responseData){
                                                                   var response = responseData.data;
                                                                   self.vueCreateProjectForTranslationDataframe_project_sourceFile_ajaxFileSave(response,allParams);
                                                                   excon.saveToStore('vueTranslatorAssistantDataframe','vueTranslatorAssistantDataframe_project_list',response.params.name);
                                                                   excon.saveToStore('vueTranslatorAssistantDataframe','currentProjectId',response.params.id)
                                                                   excon.setVisibility('vueCreateProjectForTranslationDataframe',false);
                                                                   });
                    }"""
    }
    vueTranslatorDataframe_script(VueJsEntity){ bean ->
        data = """isHidden : false """
        methods = """ addLanguage(){
                                    var allParams = this.state;
                                    var self = this;
                                    allParams['dataframe'] = 'vueTranslatorDataframe';
                                    allParams['projectId'] =Number(this.state.keys.projectId);
                                    axios({
                                           method:'post',
                                           url:'${contextPath}/translatorAssistant/addLanguage',
                                           data: allParams
                                    }).then(function(responseData){
                                                                   self.vueTranslatorDataframe_fillInitData()
                                                                   var response = responseData.data;
                                                                   });
                                    },\n
                                    translatedText(params){
                                        var previouslyClickedValue = excon.getFromStore('vueGridOfTranslatedTextDataframe','targetLanguage')
                                        if(previouslyClickedValue == ""){
                                        this.isHidden = !this.isHidden
                                        }else{
                                        this.isHidden = true
                                        }
                                        excon.saveToStore('vueGridOfTranslatedTextDataframe','targetLanguage',params.language)
                                        excon.saveToStore('vueGridOfTranslatedTextDataframe','projectId',this.state.keys.projectId)
                                        excon.saveToStore('vueGridOfTranslatedTextDataframe','sourceLanguage',this.state.vueTranslatorDataframe_project_sourceLanguage)

}

       """
    }
    vueGridOfTranslatedTextDataframe_script(VueJsEntity){ bean ->
        data = """vueGridOfTranslatedTextDataframe_button_translateWithGoogle:true,vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile:false"""
        watch = """ refreshVueGridOfTranslatedTextDataframe:{handler: function(val, oldVal) {this.vueGridOfTranslatedTextDataframe_fillInitData();}},"""
        computed = """refreshVueGridOfTranslatedTextDataframe(){var targetLanguage = excon.getFromStore('vueGridOfTranslatedTextDataframe','targetLanguage');
                            return targetLanguage;}"""
        methods = """
                  buttonShowHide(response){
                                        var retrivedData = response.additionalData.vueGridOfTranslatedTextDataframe_translatedText.dictionary;
                                        if(retrivedData.length > 1){
                                        this.vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile=true;
                                        this.vueGridOfTranslatedTextDataframe_button_translateWithGoogle=false;
                                        }
                                        else{
                                        this.vueGridOfTranslatedTextDataframe_button_translateWithGoogle=true;
                                        this.vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile=false;
                                        }
                                    },\n
                                    retrieveTranslatedText(){
                                         var allParams = this.state;
                                         var self = this;
                                         axios({
                                              method:'post',
                                              url:'${contextPath}/translatorAssistant/translateWithGoogle',
                                              data: allParams
                                         }).then(function(responseData){
                                              self.vueGridOfTranslatedTextDataframe_fillInitData();
                                              self.vueGridOfTranslatedTextDataframe_button_translateWithGoogle=false;
                                              var response = responseData.data;
                                         });
                                    },\n
                                    downloadTargetFile(){
                                    var allParams = this.state;
                                    var self = this;
                                    //var fileName = 
                                    var fileURL = '/translatorAssistant/downloadTargetFile/'+allParams.projectId+allParams.targetLanguage
                                    var fileLink = document.createElement('a');
                                    fileLink.href = fileURL;
                                    //fileLink.setAttribute('download');
                                    document.body.appendChild(fileLink);
                                    fileLink.click();
                                    }
        """

    }
    vueEditTranslatedRecordsOfGridDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueEditTranslatedRecordsOfGridDataframe:{handler: function(val, oldVal) {this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();}},"""
        computed = "refreshVueEditTranslatedRecordsOfGridDataframe(){return this.vueEditTranslatedRecordsOfGridDataframe_prop.key},"
        methods ="""
                    googleTranslateForEachRecord(){
                    var allParams = this.state;
                    var self = this;
                    allParams['sourceLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','sourceLanguage');
                    allParams['targetLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','targetLanguage');
                    allParams['projectId'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','projectId');
                    allParams['dataframe'] = 'vueEditTranslatedRecordsOfGridDataframe';

                     axios({
                                           method:'post',
                                           url:'${contextPath}/translatorAssistant/translateEachRecordWithGoogle',
                                           data: allParams
                                    }).then(function(responseData){
                                                                   var response = responseData.data;
                                                                   excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','vueEditTranslatedRecordsOfGridDataframe_text_text', response.translatedText); 
                                                                   });
                    },\n
                    confirmationMessage(){
                     var result = confirm("Are you sure want to abandon the changes?");
                     if(result){
                                excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                     }
                     return false;
                    }
                    """
    }
}
