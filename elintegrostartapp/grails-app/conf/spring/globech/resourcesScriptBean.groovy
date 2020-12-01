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
                          var a = currentUrl.split('#')
                          if(currentUrl == defaultUrl || a[1] == '/'){
                            let homePage = "vueElintegroHomeDataframe";
                            let routeId = 0;
                            this.\$router.push({
                                  name: homePage,
                                path: '/',
                                params: {
                                   routeId: ""
                                }
                            })
                          }
                     }
               ,\nsetInitPageValues:function(){
                                               
                       axios.get('login/getUserInfo').then(function (responseData) {
                            const res = responseData.data;
                            excon.saveToStore("vueInitDataframe", "loggedIn", res.loggedIn);
                            excon.saveToStore("loggedIn", res.loggedIn);
                            const personId = res.personId;
                            if(personId){
                                let userProfileMenu = excon.getFromStore("vueElintegroProfileMenuDataframe");
                                userProfileMenu.persisters.person.id = personId;
                                userProfileMenu.domain_keys.person.id = personId;
                                userProfileMenu.namedParameters.session_userid.value = personId;
                                excon.saveToStore("vueElintegroProfileMenuDataframe", userProfileMenu);
                                let userProfile = excon.getFromStore("vueElintegroUserProfileDataframe");
                                userProfile.persisters.person.id = personId;
                                userProfile.domain_keys.person.id = personId;
                                userProfile.namedParameters.id.value = personId;
                                excon.callApi('dataframe/ajaxValues', 'POST', userProfile).then((response)=>{
                                    const profileData = response.data.data;
                                    excon.saveToStore("vueElintegroUserProfileDataframe", profileData);
                                });
                            }
                            var loggedIn = responseData.data.loggedIn
                            var urlLocation = window.location.href;
                            if(loggedIn == true && urlLocation.includes('change-forget-password') == true){
                                excon.redirectPage(vueInitDataframeVar,'home');
                            }
                            if(loggedIn == true && urlLocation.includes('login-page') == true){
                                      axios.get('translatorAssistant/getProjectDetailsFromSessionAfterLoggedIn').then(function (responseData) {
                                          var response = responseData.data;
                                          if(response.success == true){
                                             excon.saveToStore('vueTranslatorDataframe','currentlySelectedProject',response.projectDetails);
                                             excon.showMessage(responseData,'vueTranslatorDataframe');
                                             excon.redirectPage(vueInitDataframeVar,'translator');
                                          }
                                      })
                            }
                              if(loggedIn == false){
//                               vueInitDataframeVar.\$router.push("/");this.location.reload();
                              }
                            
                        }).catch(function (error) {
                            console.log(error);
                        });
                      } ,  \n
                                                                            
        """
    }

    vueFirstContainerDataframe_script(VueJsEntity){bean ->
        methods = """
                          scrollToQuiz(){
                            let element = document.getElementById('quiz_placeholder');
                             element.scrollIntoView({ behavior: 'smooth' });
                    },\n

                    changeWords(){
                            var text = document.getElementById("buildData").innerHTML;
                            var words = text.split(',');
                            var con = document.getElementById('console');
                            var letterCount = 1;
                            var x = 1;
                            var waiting = false;
                            var target = document.getElementById("build");
                            window.setInterval(function() {
                                if (letterCount === 2 && waiting === false) {
                                  waiting = true;
                                  target.innerHTML = words[0].substring(0, letterCount)
                                  window.setTimeout(function() {
                                        var usedWord = words.shift();
                                        words.push(usedWord);
                                        x = 1;
                                        letterCount += x;
                                        waiting = false;
                                  }, 1000)
                                }else if (letterCount === words[0].length + 1 && waiting === false) {
                                      waiting = true;
                                      window.setTimeout(function() {
                                        x = -1;
                                        letterCount += x;
                                        waiting = false;
                                      }, 1000)
                                }else if (waiting === false) {
                                      target.innerHTML = words[0].substring(0, letterCount)
                                      letterCount += x;
                                      }
                            }, 120)
                    },\n           

                    """
    }

    vueElintegroAboutUsMenuDataframe_script(VueJsEntity){ bean ->
        methods = """scrollTo(param){
           
            if(param =='ourClientsProjects'){
                 excon.redirectPage(this,"client-project");
            }else{
                let element = document.getElementById(param);
                if(element != null){
                    switch(param){
                    case 'our_work' :
                        element.scrollIntoView({ behavior: 'smooth' });
                        break;
                    case 'our_process' : 
                        element.scrollIntoView({ behavior: 'smooth' });
                        break;
                    case 'collaboration' : 
                        element.scrollIntoView({ behavior: 'smooth' });
                        break;
                    case 'our_framework' : 
                        element.scrollIntoView({ behavior: 'smooth' });
                        break;
                    case 'Quotes' :
                        element.scrollIntoView({ behavior: 'smooth' });
                        break;    
                    case 'our_Technologies' :
                        element.scrollIntoView({ behavior: 'smooth' });
                        break;      
                    case 'quiz_placeholder'  :
                        element.scrollIntoView({ behavior: 'smooth' });
                        break;
                    default : 
                        excon.redirectPage(this,'home');                                
                    }
                }else{
                       excon.redirectPage(this,'home');
                       this.\$nextTick(()=> window.document.getElementById(param).scrollIntoView()); 
                }   
            }
  
            
        }
        """
    }

    vueToolbarDataframe_script(VueJsEntity) { bean ->
        data = "newApplication_display:true,\n"

    }
    vueElintegroProgressBarDataframe_script(VueJsEntity){bean ->
//        data = """progressBarValue:'',"""
//        watch =  """progressBarValueChanged:{handler: function(val, oldVal) {this.progressBarValue = val;}},\n"""
        computed = """progressBarValue(){ 
                                          var progressValue = this.state.progressValue;
                                          if(progressValue == undefined || progressValue == null || progressValue == ''){
                                             return 0;
                                          }else{
                                              return progressValue; 
                                          }
                                        },\n"""
    }

/*
    loginNavigationVue_script(VueJsEntity) { bean ->
        data = "loginNavigationVue_show:true,"
        watch = """registerStateChanged:{handler: function(response, oldVal) {
                                       this.enableDisableNotification(response.data);
                                       }},
               loginClose:{handler: function(response, oldVal) {
                                       this.vueLoginDataframe_display = false;
                                       }}"""
        computed = """registerStateChanged() {return this.\$store.state.loginNavigationVue;},\n
                loginClose() {return this.\$store.state.vueInitDataframe.vueLoginDataframe_display;}
                                              """
        methods =
                """  enableDisableNotification:function(data){
                                                if(data.success){
                                                    this.vueRegisterDataframe_display = false;
                                                }
                                               } ,  \n
                                               """
    }
*/


    vueAddressDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
        methods = """updateAddressFields(result){
                    console.log("updateAddressFields.");
                        var results = result[0].address_components;
                        for(var i = 0; i < results.length; i++){
                                         let typeString = results[i].types[0];
                                         let nameString = results[i].long_name;
                                         if(typeString =="subpremise"){
                                         console.log("Apartment:"+typeString+"::::::"+nameString);
                                         vueAddressDataframeVar.state.persisters.address.apartment.value = nameString;
                                         }
                                         if(typeString =="street_address"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                         vueAddressDataframeVar.state.persisters.address.addressText.value = nameString;
                                         }
                                         if(typeString =="route"){
                                         console.log("Street:"+typeString+"::::::"+nameString);
                                          vueAddressDataframeVar.state.persisters.address.street.value = nameString;
                                         }
                                         if(typeString =="locality"){
                                         console.log("City:"+typeString+"::::::"+nameString);
                                         vueAddressDataframeVar.state.persisters.address.cityString.value = nameString;
                                         }
                                         if(typeString =="country"){
                                           console.log("Country:"+typeString+"::::::"+ nameString);
                                          vueAddressDataframeVar.state.persisters.address.countryString.value = nameString;
                                         }
                                         if(typeString =="postal_code"){
                                          vueAddressDataframeVar.state.persisters.address.postalZip.value = nameString;
                                         }
                                         if(typeString =="street_number"){
                                         console.log("street_number:"+typeString+"::::::"+nameString);
                                          jQuery("#addressDataframe-address-streetNbr").val(nameString);
                                         }
                                     };
                                     vueAddressDataframeVar.state.persisters.address.addressText.value = result[0].formatted_address;
                                     vueAddressDataframeVar.state.persisters.address.addressLine.value = result[0].formatted_address;
                    },"""
    }

/*
    vueLoginDataframe_script(VueJsEntity) { bean ->
        methods = """dialogBoxClose(){
                    console.log("login dataframe close button.");
                    },"""
    }
*/
    vueElintegroForgetPasswordDataframe_script(VueJsEntity){bean ->
        methods = """
                   forgotPassword(){
                                  var params = this.state;
                                  if(this.state.persisters.user.email.value == "" || this.state.persisters.user.email.value  == null || this.state.persisters.user.email.value  == undefined){
                                       var response = {};
                                       response['alert_type'] = 'error'
                                       response['msg'] = 'You must enter your email.';
                                       var responseData = {data:response};
                                       excon.showMessage(responseData,'vueElintegroForgetPasswordDataframe');
                                  }else{
                                        params['email'] = this.state.persisters.user.email.value ;
                                        var self = this;
                                        excon.callApi('register/forgotPassword', 'post', params).then(function(responseData){
                                                var response = responseData.data;
                                                excon.showMessage(responseData,'vueElintegroForgetPasswordDataframe');
                                                if(response.success == true){
                                                  setTimeout(function(){excon.redirectPage(self,"home");},6000);
                                                }else{
                                                     setTimeout(function(){excon.setVisibility('vueElintegroRegisterDataframe',true);},4000);
                                                }
                                        })
                                  }
                   },\n
                  """
    }
    vueElintegroChangeForgotPasswordDataframe_script(VueJsEntity){bean ->
        methods = """
                   changeForgotPassword(){
                                    var params = this.state;
                                    params['dataframe'] = 'vueElintegroChangeForgotPasswordDataframe';
                                    var self = this;
                                    var currentLocation = window.location.href;
                                    var location = currentLocation.split("/change-forget-password/0?")
                                    params['token'] = location[1]
                                    excon.callApi('register/changeForgotPassword', 'post', params).then(function(responseData){
                                           var response = responseData.data;
                                           excon.showMessage(responseData,'vueElintegroChangeForgotPasswordDataframe');
                                           if(response.success == true){
                                             excon.setVisibility('vueElintegroLoginDataframe',true);
                                           }else{
                                                alert("Failed to change password.");
                                           }
                                           
                                    })
                   }
                  """
    }

/*
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
                var params = {};
                params["id"] = eval(this.namedParamKey);
                params['dataframe'] = 'vueAfterLoggedinDataframe';
                excon.callApi('dataframe/ajaxValues', 'get', params).then(function(responseData) {
                    if(responseData == undefined ||  responseData.data == undefined || responseData.data.data == undefined){
                        console.log("Error login for the user");
                        store.commit('alertMessage', {'snackbar':true, 'alert_type':'error', 'alert_message':"Login failed"})
                    }
                    var response = responseData.data.data;
                    imgUrl = response['vueAfterLoggedinDataframe.person.mainPicture'] ?  '/images/'+response['vueAfterLoggedinDataframe.person.mainPicture'] : "assets/default_profile.jpg";
                    vueAfterLoggedinDataframeVar.vueAfterLoggedinDataframe_person_mainPicture = imgUrl;
                }).catch(function(error) {
                    console.log(error);
                });
                            }
                            
                     },"""
    }
*/
    vueElintegroLoginDataframe_script(VueJsEntity) { bean ->
        boolean loginWithSpringSecurity = Holders.grailsApplication.config.loginWithSpringSecurity?true:false
        String loginAuthenticateUrl = loginWithSpringSecurity?"login/authenticate" : "login/loginUser"

        methods = """login: function(){
                                     var elementId = '#vueElintegroLoginDataframe';
                                     let params = {};
                                     params["username"] = this.state.persisters.user.username.value;
                                     params["password"] = this.state.persisters.user.password.value;
                                     params["remember-me"] = this.state.transits.rememberMe.value;
                                     excon.callApiWithQuery('$loginAuthenticateUrl', 'POST', params).then((response)=>{
                                           window.location.reload();
                                    })
                    }"""
    }
    vueElintegroRegisterDataframe_script(VueJsEntity) { bean ->
        data = "vueElintegroRegisterDataframe_display:true,\n checkboxSelected: [],\n"
        methods = """
                   showAlertMessageToUser(response){
                   if(response.success == true){
                         response['alert_type'] = 'success'
                         var responseData = {data:response}
                         excon.showMessage(responseData,'vueElintegroRegisterDataframe');
                         setTimeout(function(){excon.setVisibility('vueElintegroRegisterDataframe', false);}, 6000);
                   }else{
                         response['alert_type'] = 'error';
                         var responseData = {data:response}
                         excon.showMessage(response,'vueElintegroRegisterDataframe');
                         setTimeout(function(){excon.setVisibility('vueElintegroRegisterDataframe', false);}, 6000);
                   }
        },\n """
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
        methods = """logout: function(){
                                     const self =this;
                                     let params={};
                                     excon.callApiWithQuery('logoff', 'POST', params).then((response)=>{
                                         self.\$router.push("/")
                                         window.location.reload();
                                    })
                    }"""
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

/*
    vueApplicationFormDataframe_script(VueJsEntity) { bean ->
        data = "vueApplicationFormDataframe_tab_model : this.tabValue,\nvueApplicationFormDataframe_display: true, \n"
        computed = """tabValue(){return this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model}"""
        watch = """ tabValue:{handler: function(val, oldVal) {this.vueApplicationFormDataframe_tab_model = val;}},"""

    }
*/

/*
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
    vueMedicationsGridDetailDataframe_script(VueJsEntity){bean -> */
/* watch = """ vueApplicationFormDetailDataframe_prop: { deep:true, handler: function(){ this.vueApplicationFormDetailDataframe_fillInitData(); } },"""*//*

        watch = """ callInitMethod:{handler: function(val, oldVal) {this.vueMedicationsGridDetailDataframe_fillInitData();}},"""
        computed = """ callInitMethod(){  const data = excon.getFromStore('vueMedicalRecordDetailDataframe', 'key');
                                      return (data!='' && data!= undefined)?data:null},"""
    }

    vueMedicationsGridEditDataframe_script(VueJsEntity){bean -> */
/* watch = """ vueApplicationFormDetailDataframe_prop: { deep:true, handler: function(){ this.vueApplicationFormDetailDataframe_fillInitData(); } },"""*//*

        watch = """ callInitMethod:{handler: function(val, oldVal) {this.vueMedicationsGridEditDataframe_fillInitData();}},"""
        computed = """ callInitMethod(){  const data = excon.getFromStore('vueMedicalRecordEditDataframe', 'key');
                                      return (data!='' && data!= undefined)?data:null},"""
    }
*/
/*
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
*/
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
/*
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
*/
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
    vueElintegroSignUpQuizDataframe_script(VueJsEntity){bean ->
        methods = """saveSignUpForm(){
                                    var params = this.state;
                                    params['dataframe'] = 'vueElintegroSignUpQuizDataframe';
                                    var self = this;
                                    excon.callApi('register/createLeadUser', 'post', params).then(function(responseData){
                                           console.log(responseData);
                                           excon.showMessage(responseData,'vueElintegroSignUpQuizDataframe');
                                           setTimeout(function(){window.location.reload();}, 6000);
                                    })
                                    },\n"""
    }
    vueElintegroChangePasswordAfterSignUpDataframe_script(VueJsEntity){bean ->
        methods = """changePasswordAfterSignedUp(){
                                    var params = this.state;
                                    params['dataframe'] = 'vueElintegroChangePasswordAfterSignUpDataframe';
                                    var self = this;
                                    var currentLocation = window.location.href;
                                    var location = currentLocation.split("/change-password/0?")
                                    params['token'] = location[1]
                                    excon.callApi('register/changePassword', 'post', params).then(function(responseData){
                                           var response = responseData.data;
                                           excon.showAlertMessage(response);
                                           if(response.success == true){
                                              setTimeout(function(){window.open("/","_self");}, 2000);
                                           }else{
                                                setTimeout(function(){window.location.reload();},3000);
                                           }
                                           
                                    })


                                    },\n"""
    }
    vueElintegroLanguageSelectorDataframe_script(VueJsEntity){bean ->
        methods = """
                   selectedLanguage(params){
                             var url = 'languageTranslate/languageTranslator/'+params
                             var link = document.createElement('a');
                             link.href = url;
                             document.body.appendChild(link);
                             link.click();
                   },\n
                   changeSelectedLanguageValue(params){
                           console.log(params);
                           var currentUrl = window.location.href;
                           var splittedCurrentUrl = currentUrl.split("#");
                           var replacedCurrentUrl = splittedCurrentUrl[0].replace('$defaultUrl/','');
                           if(replacedCurrentUrl != null || replacedCurrentUrl != undefined || replacedCurrentUrl != ""){
                                 var langCode = replacedCurrentUrl.replace("?lang=",'');
                                 var langItems = this.state.transits.languages.items;
                                 for(i=0;i<langItems.length;i++){
                                     if(langCode == langItems[i].code){
                                        this.defaultLanguage = langItems[i].ename;
                                     }
                                 }
                           }
                   },\n
                           
                  """
    }

    vueElintegroNavigationDrawerDataframe_script(VueJsEntity){bean ->
        data = """drawer: false, group: null,"""
    }
    vueElintegroSubMenuDataframe_script(VueJsEntity){bean->
        methods = """
                     quizzableApp(){
                             if(this.\$store.state.vueInitDataframe.loggedIn){
                                  var params = this.state;
                                  params['dataframe'] = 'vueElintegroSubMenuDataframe';
                                  excon.callApi('quizzableLogin/quizzableLoginFromElintegro', 'post', params).then(function(response){
                                           var token = response.data.accessToken
                                           var serverUrl = response.data.serverUrl
                                           window.open(serverUrl+'/login/authenticateWithToken/'+token,'_blank')
                                  });
                             }
                             else{
                                  
                                 window.open('https://quizzable.elintegro.com/','_blank');
                             }
                     },\n
                     ecommerceApp(){
                             var self = this;
                             if(this.\$store.state.vueInitDataframe.loggedIn){
                             
                                  var params = this.state;
                                  params['dataframe'] = 'vueElintegroSubMenuDataframe';
                                  excon.callApi('ELcommerceLogin/elCommerceLoginFromElintegro', 'post', params).then(function(response){
                                           var token = response.data.accessToken
                                           var serverUrl = response.data.serverUrl
                                           window.open(serverUrl+'elintegro_ELcommerce/authenticateWithToken/'+token,'_blank')
                                  });
                             
                             }
                             else{
                                 excon.redirectPage(self,"login-page")
                             }
                     }
        

        """
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
                       excon.callApi('EmployeeApplication/createApplicant', 'post', params).then(function(responseData){
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
                 var params = {};
                       var self = this;
                       params['id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe','key_vueNewEmployeeUploadResumeDataframe_application_id_id')
                       
                       params['dataframe'] = 'vueNewEmployeeSelfAssesmentDataframe';
                       excon.callApi('EmployeeApplication/initiateSkillSet', 'post', params).then(function(responseData){
                         self.vueNewEmployeeSelfAssesmentDataframe_fillInitData();
                         
                          var response = responseData;
                          console.log(response)
                        
                });
                 
                  }
                  """
    }
    vueNewEmployeeApplicantAddSkillDataframe_script(VueJsEntity){bean ->
        methods = """addNewSkill(){
                                    
                                    var params = this.state;
                                    params['applicationId']= excon.getFromStore('vueNewEmployeeBasicInformationDataframe','domain_keys.application.id');
                                    var self = this;
                                    excon.callApi('EmployeeApplication/addNewSkillSet', 'post', params).then(function(responseData){
                                           var response = responseData.data;
                                           excon.setVisibility("vueNewEmployeeApplicantAddSkillDataframe", false);
                                           excon.refreshDataForGrid(response,'vueNewEmployeeSelfAssesmentDataframe', 'applicationSkill', 'I','transits');                                                 
                                           console.log(response)                      
                                    });
                  }"""

    }
    vueNewEmployeeThankYouMessageAfterSaveDataframe_script(VueJsEntity) { bean ->
        computed = """ vueNewEmployeeThankYouMessageAfterSaveDataframe_person_fullName(){return excon.capitalize(this.state.persisters.person.firstName.value) + " " + excon.capitalize(this.state.persisters.person.lastName.value)}"""
    }

    vueElintegroApplicantDetailsDataframe_script(VueJsEntity){bean->
        data = "vueElintegroApplicantDetailsDataframe_tab_model : this.tabValue,\nvueElintegroApplicantDetailsDataframe_display: true, \n"
        computed = """tabValue(){return this.\$store.state.vueElintegroApplicantDetailsDataframe.vueElintegroApplicantDetailsDataframe_tab_model}"""
        watch = """ tabValue:{handler: function(val, oldVal) {this.vueElintegroApplicantDetailsDataframe_tab_model = val;}},"""
    }
    vueElintegroApplicantGeneralInformationDataframe_script(VueJsEntity){bean ->
          computed ="""vueElintegroApplicantGeneralInformationDataframe_person_selectedposition(){ 
                                        var positions = [];
                                        var items = this.state.transits.selectedPosition.value;
                                        if(!items) return;
                                        for(i=0;i<items.length;i++){
                                           positions[i] = items[i].Name;
                                        }
                                        var selectedPosition = positions.join(",\t\t");
                                        return selectedPosition;}"""
    }

    vueElintegroApplicantCVDataframe_script(VueJsEntity){ bean ->
        def pathForPdf = Holders.grailsApplication.config.images.defaultImagePathForPdf
        def pathForExcel = Holders.grailsApplication.config.images.defaultImagePathForExcel
        def pathForDocFile = Holders.grailsApplication.config.images.defaultImagePathForDocFile
        def pathForCsvFile =  Holders.grailsApplication.config.images.defaultImagePathForCsvFile
        methods ="""afterRefreshing(response){
              
                                 let params = response;
                                 let fileName = params.persisters.files.fileName.value;
                                  let extension = fileName.split('.').pop();
                                  let stateValues = excon.getFromStore("vueElintegroApplicantCVDataframe");
                                  if(extension == 'pdf'){
                                    let defaultImageUrlForPdf = '${pathForPdf}'
                                    stateValues.persisters.files.fileName.value = '${pathForPdf}';
                                  }
                                  else if(extension == 'xlsx' || extension == 'xlsm' || extension == 'xlsb' || extension == 'xltx'){
                                    stateValues.persisters.files.fileName.value = '${pathForExcel}';
                                  }
                                  else if(extension == 'doc' || extension == 'docx'){
                                    stateValues.persisters.files.fileName.value = '${pathForDocFile}';
                                  }
                                  else if(extension == 'csv' || extension == 'CSV'){
                                    stateValues.persisters.files.fileName.value = '${pathForCsvFile}';
                                  }
                                  excon.saveToStore('vueElintegroApplicantCVDataframe','vueElintegroApplicantCVDataframe_files_fileName_name',fileName)
                                  
                                  excon.saveToStore('vueElintegroApplicantCVDataframe',stateValues);  
                                 
                                  },\n
                              
        """
/*
                                  var applicantId = response.domain_keys.application.id;
                                  var imageSrc = "fileDownload/imagePreview/"+applicantId;
                                  stateValues.persisters.images.name.value = imageSrc;
*/
    }
    vueElintegroCommentPageForApplicantDataframe_script(VueJsEntity){bean ->
        methods ="""addCommentsForApplicant(){
                                        
                                    var params = this.state;
                                    var self = this;
                                    params['dataframe'] = 'vueElintegroCommentPageForApplicantDataframe';
                                    exon.callApi('EmployeeApplication/addComment', 'post', params).then(function(responseData){
                                                                   var response = responseData.data;
                                                                   self.vueElintegroCommentPageForApplicantDataframe_fillInitData()
                                    });
                  }"""
    }
    vueTranslatorAssistantAfterLoggedInDataframe_script(VueJsEntity) {
        data = """disableWhenItemNotExist:true,"""
/*
        watch = """enableDisableTranstaleButtonComputed:{handler:function(val,oldVal){this.disableWhenItemNotExist = excon.enableDisableButton('vueTranslatorAssistantAfterLoggedInDataframe',val); excon.saveToStore('vueTranslatorDataframe','currentlySelectedProject',val) }}"""
        computed = """ enableDisableTranstaleButtonComputed(){return this.state.transits.projectList.value;}"""
*/
    }
    vueTranslatorAssistantBeforeLoggedInDataframe_script(VueJsEntity) {
        data = """disableWhenItemNotExist:true,"""
/*
        watch = """enableDisableTranstaleButtonComputed:{handler:function(val,oldVal){this.disableWhenItemNotExist = excon.enableDisableButton('vueTranslatorAssistantBeforeLoggedInDataframe',val); excon.saveToStore('vueTranslatorDataframe','currentlySelectedProject',val) }}"""
        computed = """ enableDisableTranstaleButtonComputed(){return this.state.transits.projectList.value;}"""
*/
    }
    vueCreateProjectForTranslationDataframe_script(VueJsEntity){bean->
        methods ="""saveProject(timeOut){
                    var params = this.state;
                    var self = this;
                    params['dataframe'] = 'vueCreateProjectForTranslationDataframe';
                                    excon.callApi('translatorAssistant/saveProjectData', 'post', params).then(function(responseData){
                                                     var response = responseData.data;
                                                     if(response.success == true){
                                                              var currentlySaveProject = {Name:response.params.name,projectId:response.params.id}
                                                              self.vueCreateProjectForTranslationDataframe_project_sourceFile_ajaxFileSave(response,params);
                                                               let stateVariable;
                                                               if(self.\$store.state.vueInitDataframe.loggedIn){
                                                                    stateVariable = excon.getFromStore("vueTranslatorAssistantAfterLoggedInDataframe");
                                                                    stateVariable.transits.projectList.value = currentlySaveProject;
                                                                    excon.saveToStore("vueTranslatorAssistantAfterLoggedInDataframe", stateVariable)
                                                               }else{
                                                                    stateVariable = excon.getFromStore("vueTranslatorAssistantBeforeLoggedInDataframe");
                                                                    stateVariable.transits.projectList.value = currentlySaveProject;
                                                                    excon.saveToStore("vueTranslatorAssistantBeforeLoggedInDataframe", stateVariable)
                                                               }
                                                     }
                                                     excon.showMessage(responseData,'vueCreateProjectForTranslationDataframe');
                                                     setTimeout(function(){excon.setVisibility('vueCreateProjectForTranslationDataframe', false);}, timeOut);
                                    });
                    }"""
    }
    vueTranslatorDataframe_script(VueJsEntity){ bean ->
        data = """isHidden : false ,showDownloadAllTranslatedFilesButton:false,disableAddButtonWhenItemNotSelect:true,"""
/*
        watch = """ showOrHideDownloadAllFilesButton:{handler: function(val){ if(val == true){this.showDownloadAllTranslatedFilesButton = true;}else{this.showDownloadAllTranslatedFilesButton = false;}}},\n
                    enableDisableAddButton:{handler: function(val,oldVal){this.disableAddButtonWhenItemNotSelect = excon.enableDisableButton('vueTranslatorDataframe',val);}},\n
                    """
        computed = """showOrHideDownloadAllFilesButton(){if(this.state.transits.selectedLanguages.value && this.state.transits.selectedLanguages.value.length > 1){return true;}return false;},\n
                      enableDisableAddButton(){return this.state.transits.notSelectedLanguages.value;},\n
                   """
*/
        methods = """addLanguage(){
                                    var params = this.state;
                                    var self = this;
                                    params['dataframe'] = 'vueTranslatorDataframe';
                                    params['projectId'] =Number( excon.getFromStore('vueTranslatorDataframe','projectId'));
                                    excon.callApi('translatorAssistant/addLanguage', 'post', params).then(function(responseData){
                                                  self.vueTranslatorDataframe_fillInitData()
                                                  var response = responseData.data;
                                    });
                             },\n
                             translatedText(params){
                                        let stateValues = excon.getFromStore('vueGridOfTranslatedTextDataframe')
                                        let previouslyClickedValue = stateValues.targetLanguage
                                        if(previouslyClickedValue == ""){
                                            this.isHidden = !this.isHidden
                                        }else{
                                            this.isHidden = true
                                        }
                                        stateValues.targetLanguage = params.language;
                                        excon.saveToStore('vueGridOfTranslatedTextDataframe',stateValues);
                                        excon.saveToStore('vueGridOfTranslatedTextDataframe','projectId',this.state.projectId)
                                        excon.saveToStore('vueGridOfTranslatedTextDataframe','sourceLanguage',this.state.persisters.project.sourceLanguage.value)
                                        excon.fillInitialData(stateValues);

                             },\n
                             
                             downloadAllTranslatedFiles(){
                                       var params = this.state;
                                       var self = this;
                                       if(this.\$store.state.vueInitDataframe.loggedIn){
                                                         excon.callApi('translatorAssistant/compressAllFilesInZip', 'post', params).then(function(responseData){
                                                                var response = responseData.data;
                                                                if(response.success == true){
                                                                    var fileURL = 'translatorAssistant/downloadZipFile/'+response.projectId
                                                                    var fileLink = document.createElement('a');
                                                                    fileLink.href = fileURL;
                                                                    document.body.appendChild(fileLink);
                                                                    fileLink.click();
                                                                }else{
                                                                     excon.showMessage(responseData,'vueTranslatorDataframe');
                                                                }      
                                                        });
                                       }
                                       else{ 
                                            params['projectDetails'] = excon.getFromStore('vueTranslatorDataframe','currentlySelectedProject');
                                            excon.callApi('translatorAssistant/saveProjectDetailsInSessionForNotLoggedInUser', 'post', params).then(function(responseData){
                                                 excon.redirectPage(self,"login-page");
                                            })
                                       }                 
                                        
                                        
                             },\n


       """
    }
    vueAddNewRecordForCurrentProjectDataframe_script(VueJsEntity){ bean ->
        methods = """
                    translateText(params){
                                        excon.saveToStore('vueAddNewRecordForCurrentProjectDataframe','vueAddNewRecordForCurrentProjectDataframe_textToTranslate_selectedrow',params)
                                        var params = this.state;
                                        params['targetLanguage'] = params.targetLanguage;
                                        params['dataframe'] = 'vueAddNewRecordForCurrentProjectDataframe';
                                        excon.callApi('translatorAssistant/translateNewlyAddedRecord', 'post', params).then(function(responseData){
                                                 var response = responseData.data;
                                                 excon.refreshDataForGrid(response,'vueAddNewRecordForCurrentProjectDataframe', 'vueAddNewRecordForCurrentProjectDataframe_textToTranslate', 'U'); 
                                        })
                    },\n
                    saveNewlyAddedRecord(){
                                       var params = this.state;
                                       var self = this;
                                       if(this.state.vueAddNewRecordForCurrentProjectDataframe_key != "" && this.state.vueAddNewRecordForCurrentProjectDataframe_text != ""){
                                           excon.callApi('translatorAssistant/saveNewlyAddedRecord', 'post', params).then(function(responseData){
                                               var response = responseData.data;
                                               excon.setVisibility('vueAddNewRecordForCurrentProjectDataframe',false);
                                           })
                                      }
                                      else{
                                            alert("Key and Source Text shouldn't be empty.");
                                      }
                    
                    },\n
                    closeVueAddNewRecordForCurrentProjectDataframe(){
                                    var params = this.state;
                                    if(this.state.vueAddNewRecordForCurrentProjectDataframe_key != "" && this.state.vueAddNewRecordForCurrentProjectDataframe_text != ""){
                                       var confirmMessage = confirm("Are you sure want to abandon the changes?");
                                       if(confirmMessage){
                                                excon.setVisibility("vueAddNewRecordForCurrentProjectDataframe", false);
                                       }return false;
                                    }
                                    else{
                                       excon.setVisibility("vueAddNewRecordForCurrentProjectDataframe", false);
                                    }
                    },\n
                   
        """
    }
    vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_script(VueJsEntity){ bean ->
/*
        watch = """ refreshVueEditTextOfNewlyAddedRecordForCurrentProjectDataframe:{handler: function(val, oldVal) {this.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_fillInitData();}},"""
        computed = """refreshVueEditTextOfNewlyAddedRecordForCurrentProjectDataframe(){var textToEdit = this.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_prop.parentData.text;
                            return textToEdit;}"""
*/
        methods = """
                   updateEditedTextInGrid(){
                                      var translatedData = {text:this.state.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_text_text, targetLanguage:this.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_prop.parentData.targetLanguage};
                                      var response = {newData:{textToTranslate:translatedData}};
                                      excon.refreshDataForGrid(response,'vueAddNewRecordForCurrentProjectDataframe', 'vueAddNewRecordForCurrentProjectDataframe_textToTranslate', 'U'); 
                                      excon.setVisibility("vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe", false);
                   },\n
                   closeVueEditTextOfNewlyAddedRecordForCurrentProjectDataframe(){
                                     if(this.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_prop.parentData.text != this.state.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_text_text){
                                       var confirmMessage = confirm("Are you sure want to abandon the changes?");
                                       if(confirmMessage){
                                                excon.saveToStore("vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe","vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_text_text",this.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_prop.parentData.text )
                                                excon.setVisibility("vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe", false);
                                       }return false;
                                    }
                                    else{
                                       excon.setVisibility("vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe", false);
                                    }
                                     
                   },\n
                  """

    }
    vueGridOfTranslatedTextDataframe_script(VueJsEntity){ bean ->
        data = """showTranslateWithGoogleButton:true, showDownloadTargetPropertyFileButton:false, progressBarEnable:false,"""
//        watch = """ refreshGridOfTranslatedTextDataframe:{deep:true,handler: function(val,oldVal) {this.vueGridOfTranslatedTextDataframe_fillInitData();}},"""
        computed = """buttonShowHide(){
                                       var retrivedData = this.state.transits.translatedText.items;
                                       if(retrivedData.length > 1){
                                          
                                          return true;
                                       }
                                       else{
                                            return false; 
                                       }
                                      },\n"""
        methods = """ retrieveTranslatedText(){
                                         this.progressBarEnable = true;
                                         this.showTranslateWithGoogleButton = false;
                                         var params = this.state;
                                         var self = this;
                                         var myVar = setInterval(function(){
                                         excon.callApi('translatorAssistant/intermediateRequest', 'post', params).then(function(responseData){
                                                      var response = Math.round(responseData.data);
                                                      console.log(response);
                                                      let stateValuesForProgressBar = excon.getFromStore('vueElintegroProgressBarDataframe');
                                                      stateValuesForProgressBar['progressValue'] = response;
                                                      excon.saveToStore('vueElintegroProgressBarDataframe',stateValuesForProgressBar);
                                                      excon.fillInitialData(stateValuesForProgressBar);
                                                      if(self.progressBarEnable == false){clearInterval(myVar)}
                                               });
                                         } ,1000);
                                         
                                         excon.callApi('translatorAssistant/translateWithGoogle', 'post', params).then(function(responseData){
                                              self.vueGridOfTranslatedTextDataframe_fillInitData();
                                              var response = responseData.data;
                                              self.progressBarEnable = false;
                                         });
                                    },\n
                                    downloadTargetFile(){
                                            var params = this.state;
                                            var self = this;
                                            if(this.\$store.state.vueInitDataframe.loggedIn){
                                                    var fileURL = 'translatorAssistant/downloadTargetFile/'+params.projectId+params.targetLanguage
                                                    var fileLink = document.createElement('a');
                                                    fileLink.href = fileURL;
                                                    document.body.appendChild(fileLink);
                                                    fileLink.click();
                                            }
                                            else{
                                                      params['projectDetails'] = excon.getFromStore('vueTranslatorDataframe','currentlySelectedProject');
                                                      excon.callApi('translatorAssistant/saveProjectDetailsInSessionForNotLoggedInUser', 'post', params).then(function(responseData){
                                                              excon.redirectPage(self,"login-page");
                                                      })
                                           }
                                    },\n
        """

    }
    vueEditTranslatedRecordsOfGridDataframe_script(VueJsEntity){bean ->
/*
        watch = """ refreshVueEditTranslatedRecordsOfGridDataframe:{handler: function(val, oldVal) {this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();}},"""
        computed = "refreshVueEditTranslatedRecordsOfGridDataframe(){return this.vueEditTranslatedRecordsOfGridDataframe_prop.key},"
*/
        methods ="""
                    googleTranslateForEachRecord(){
                    var params = this.state;
                    var self = this;
                    params['sourceLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','sourceLanguage');
                    params['targetLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','targetLanguage');
                    params['projectId'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','projectId');
                    params['dataframe'] = 'vueEditTranslatedRecordsOfGridDataframe';
                    excon.callApi('translatorAssistant/translateEachRecordWithGoogle', 'post', params).then(function(responseData){
                                                                   var response = responseData.data;
                                                                   excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','vueEditTranslatedRecordsOfGridDataframe_text_text', response.translatedText); 
                                                                   });
                    },\n
                    closeVueEditTranslatedRecordsOfGridDataframe(){
                     var textBeforeEditing = excon.getFromStore('vueEditTranslatedRecordsOfGridDataframe','textBeforeEditing')
                     var textAfterEditing = this.state.vueEditTranslatedRecordsOfGridDataframe_text_text;
                     if(textBeforeEditing != textAfterEditing){
                       var result = confirm("Are you sure want to abandon the changes?");
                        if(result){
                                excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','vueEditTranslatedRecordsOfGridDataframe_text_text',textBeforeEditing)
                                excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                        }
                         return false;
                     }
                          excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                     
                    }
                    """
    }

}
