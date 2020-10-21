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
                              if(currentUrl == defaultUrl){//TODO: Not sure if it is a good idea!
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
                            const res = responseData.data;
                            excon.saveToStore("vueInitDataframe", "loggedIn", res.loggedIn);
                            excon.saveToStore("loggedIn", res.loggedIn);
                            const personId = res.personId;
                            if(personId){
                                let userProfileMenu = excon.getFromStore("vueElintegroProfileMenuDataframe");
                                userProfileMenu.persisters.person.id = personId;
                                userProfileMenu.domain_keys.person.id = personId;
                                userProfile.namedParameters.session_userid.value = personId;
                                excon.saveToStore("vueElintegroProfileMenuDataframe", userProfileMenu);
                                let userProfile = excon.getFromStore("vueElintegroUserProfileDataframe");
                                userProfile.persisters.person.id = personId;
                                userProfile.domain_keys.person.id = personId;
                                userProfile.namedParameters.id.value = personId;
                                excon.saveToStore("vueElintegroUserProfileDataframe", userProfile);
                            }
//                            vueInitDataframeVar.\$store.state.vueInitDataframe = responseData.data;
//                            Vue.set(vueInitDataframeVar.\$store.state.vueInitDataframe, "key", '');
//                            Vue.set(vueInitDataframeVar.\$store.state.vueElintegroProfileMenuDataframe, "key", '');
                              var loggedIn = responseData.data.loggedIn
//                            vueInitDataframeVar.\$store.state.loggedIn = loggedIn;
                              var urlLocation = window.location.href;
                              if(loggedIn == false){
//                               vueInitDataframeVar.\$router.push("/");this.location.reload();
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
        computed = """ vueElintegroProfileMenuDataframe_person_fullName(){return excon.capitalize(this.state.persisters.person.firstName.value) + " " + excon.capitalize(this.state.persisters.person.lastName.value)},
                       vueElintegroProfileMenuDataframe_person_email(){return this.state.persisters.person.email.value}"""
    }
    vueElintegroUserProfileDataframe_script(VueJsEntity){bean ->
        created = """this.vueElintegroProfileMenuDataframeShow();"""

        methods = """vueElintegroProfileMenuDataframeShow : function(){
                  excon.setVisibility("vueElintegroProfileMenuDataframe",false)}
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
                      /* This method is an example of possibility to manipulate the objects on the Front-end*/
                      newEmployeeBasicInformation_doSomething(){
                       //Can use all javascript arsenal
                       console.log("Inside employeeinformation")
                       // Can create variabbles and access this.state.<dataframe> JSON objects
                       var details = this.state.vueNewEmployeeBasicInformationDataframe
                       console.log(details)
                       //Run existing methods, defined in Datfram descriptor and generated by the framework 
                       if (this.\$refs.vueNewEmployeeBasicInformationDataframe_form.validate()){
                       //Even call a backend!
                       axios({
                       method:'post',
                       url:'${contextPath}/EmployeeApplication/createApplicant',
                       data: allParams
                         }).then(function(responseData){
                          var response = responseData;
                          //Use excon object to store/retrieve data fro the store.state.<dataframe>....
                          excon.saveToStore("vueNewEmployeeBasicInformationDataframe","state",response.data.data)                                                                                                            
                          console.log(response)                            
                        });
                
                        excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeUploadResumeDataframe-tab-id"); 
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
                                    allParams['id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe','state.persisters.application.id.value')
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
    vueElintegroApplicantQuestionAnswerDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueElintegroApplicantQuestionAnswerDataframe:{handler: function(val, oldVal) {this.vueElintegroApplicantQuestionAnswerDataframe_fillInitData();}},"""
        computed = "refreshVueElintegroApplicantQuestionAnswerDataframe(){return this.vueElintegroApplicantQuestionAnswerDataframe_prop.key},"
    }
    vueNewEmployeeApplicantEditSkillDataframe_script(VueJsEntity){bean ->
        watch = """ refreshVueNewEmployeeApplicantEditSkillDataframe:{handler: function(val, oldVal) {this.vueNewEmployeeApplicantEditSkillDataframe_fillInitData();}},"""
        computed = "refreshVueNewEmployeeApplicantEditSkillDataframe(){return this.vueNewEmployeeApplicantEditSkillDataframe_prop.key},"
    }
}
