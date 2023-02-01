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

    def defaultUrl = Holders.grailsApplication.config.grails.serverURL
    String staticHomePageUrl = Holders.grailsApplication.config.static_home_page_url
    vueInitDataframe_script(VueJsEntity) { bean ->
        created = """this.setInitPageValues();
                     this.setupHomePage()
                    """

        methods =
                """  setupHomePage: function(){
                          let staticPageUrl = '${staticHomePageUrl}'
                          var currentUrl = window.location.href;
                          var defaultUrl = '${defaultUrl}/#/';
                          var a = currentUrl.split('#')
                          if(currentUrl == defaultUrl || a[1] == '/'){
                            window.open(staticPageUrl, "_self");
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
//                                userProfileMenu.persisters.person.mainPicture.value = 'profileDetail/imageData'
                                userProfileMenu.domain_keys.person.id = personId;
                                userProfileMenu.namedParameters.session_userid.value = personId;
                                excon.saveToStore("vueElintegroProfileMenuDataframe", userProfileMenu);
                                let userProfile = excon.getFromStore("vueElintegroUserProfileDataframe");
                                userProfile.persisters.person.id = personId;
//                                userProfile.persisters.person.mainPicture.value = 'profileDetail/imageData'
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
                                             excon.showAlertMessage(response);
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

    vueElintegroNavigationFirstTwoButtonDataframe_script(VueJsEntity){bean ->
        methods = """
           redirectToHome(){
                let staticPageUrl = '${staticHomePageUrl}'
                window.open(staticPageUrl, "_self");
           },\n"""

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


        },\n
        """
    }

    vueToolbarDataframe_script(VueJsEntity) { bean ->
        data = "newApplication_display:true,\n"

    }
    vueQuotesContainerDataframe_script(VueJsEntity) { bean ->

        data = """
 texts: [
          {
            src: '/assets/home/comma.png',
            title : "From concept to development, it was a pleasure to work with Elintegro. They delivered my product on time and on budget. I’d definitely hire them again.",
            person : "Drasko Raicevic,Quickbody Fitness"
          },
          {
            src: '/assets/home/comma.png',
            title : "The Dating app Elintegro developed for me was perfect. It was exactly what I needed, and more.",
            person : "Lev, Matchmaker"
          },


 ],
 \n


        """
    }

    vueElintegroProgressBarDataframe_script(VueJsEntity){bean ->
        computed = """progressBarValue(){
                                          var progressValue = this.state.progressValue;
                                          if(progressValue == undefined || progressValue == null || progressValue == ''){
                                             return 0;
                                          }else{
                                              return progressValue;
                                          }
                                        },\n"""
    }

    vueAddressDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
        methods = """updateAddressFields(result){
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
                                     vueAddressDataframeVar.state.persisters.address.longitude.value = result[0].geometry.location.lng();
                                     vueAddressDataframeVar.state.persisters.address.latitude.value = result[0].geometry.location.lat();
                    },\n"""
    }


    vueElintegroForgetPasswordDataframe_script(VueJsEntity){bean ->
        methods = """
                   forgotPassword(){
                                  var params = this.state;
                                  if(this.state.persisters.user.email.value == "" || this.state.persisters.user.email.value  == null || this.state.persisters.user.email.value  == undefined){
                                       var response = {};
                                       response['alert_type'] = 'error'
                                       response['msg'] = 'You must enter your email.';
                                       var responseData = {data:response};
                                       excon.showAlertMessage(responseData);
                                  }else{
                                        params['email'] = this.state.persisters.user.email.value ;
                                        var self = this;
                                        excon.callApi('register/forgotPassword', 'post', params).then(function(responseData){
                                                var response = responseData.data;
                                                excon.showAlertMessage(response);
                                                if(response.success == true){
                                                  setTimeout(function(){excon.redirectPage(self,"home");},3000);
                                                }else{
                                                     setTimeout(function(){excon.setVisibility('vueElintegroRegisterDataframe',true);},3000);
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
                                           excon.showAlertMessage(response);
                                           if(response.success == true){
                                             excon.setVisibility('vueElintegroLoginDataframe',true);
                                           }else{
                                                alert("Failed to change password.");
                                           }

                                    })
                   },\n
                  """
    }

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
                                           excon.showAlertMessage(response.data);
                                           excon.setVisibility('vueElintegroLoginDataframe',false)
                                           window.location.reload();
                                    })
                    },\n"""
    }
    vueElintegroLoginWithOTPDataframe_script(VueJsEntity){bean ->
        data = """showThisFieldAfterCodeSent : false,"""
        computed = """showHideSendCodeButton(){
                                              let stateValues = excon.getFromStore('vueElintegroLoginWithOTPDataframe');
                                             if(stateValues.transits.email.value  && this.showThisFieldAfterCodeSent == false){ return true;} else{return false;}},\n"""
        methods = """sendVerificationCode(){
                              let params = this.state;
                              let currentUrl = window.location.href;
                              let splittedCurrentUrl = currentUrl.split("#");
                              params['currentRoute'] = splittedCurrentUrl[1]
                              params['dataframe'] = 'vueElintegroLoginWithOTPDataframe';
                              let self = this;
                              excon.callApi('login/sendVerificationCodeForLoginWithOTP', 'post', params).then(function(responseData){
                                    let response = responseData.data;
                                    excon.saveToStore('vueElintegroLoginWithOTPDataframe','currentLocationUrl',response.currentRoute);
                                    if(response.success == true){
                                         self.showThisFieldAfterCodeSent = true;
                                         excon.showAlertMessage(response);
                                    }
                                    else if(response.success == false && response.askForRegistration == true){
                                          let stateValues = response.params
                                          stateValues['currentLocationUrl'] = response.currentRoute;
                                          excon.saveToStore('vueElintegroLoginWithOTPDataframe',stateValues);
                                          let confirmMessage = confirm(response.msg);
                                          if(confirmMessage){
                                                  let self1 = self;
                                                  let params = response.params;
                                                  excon.callApi('login/sendVerificationCodeAfterRegisterConfirmedWithOTP', 'post', params).then(function(responseData){
                                                              let response = responseData.data;
                                                              if(response.success == true){
                                                                 excon.saveToStore('vueElintegroLoginWithOTPDataframe','currentLocationUrl',response.currentRoute);
                                                                 self1.showThisFieldAfterCodeSent = true;
                                                                 excon.showAlertMessage(response);
                                                              }
                                                  })
                                          }else{
                                               excon.setVisibility('vueElintegroLoginWithOTPDataframe',false);
                                               excon.refreshPage();
                                          }
                                    }
                                    else{
                                        excon.setVisibility('vueElintegroLoginWithOTPDataframe',false);
                                        excon.showAlertMessage(response);
                                    }

                              })
                 },\n
                 loginWithVerificationCode(){
                              var params = this.state;
                              params['dataframe'] = 'vueElintegroLoginWithOTPDataframe';
                              params['currentLocationUrl'] = excon.getFromStore('vueElintegroLoginWithOTPDataframe','currentLocationUrl');

                              var self = this;
                              excon.callApi('login/loginWithOTP', 'post', params).then(function(responseData){
                                 var response = responseData.data;
                                 if(response.success == true){
                                     excon.setVisibility('vueElintegroLoginWithOTPDataframe',false);
                                     self.\$router.push(response.currentLocationUrl)
                                     window.location.reload();
                                 }else if(response.success == false && response.incorrectVerificationCode != true){
                                     excon.setVisibility('vueElintegroLoginWithOTPDataframe',false);
                                     setTimeout(function(){excon.refreshPage();}, 2000);
                                 }
                                 excon.showAlertMessage(response);
                              })
                 },\n
                 resendVerificationCode(){
                              var params = this.state;
                              params['dataframe'] = 'vueElintegroLoginWithOTPDataframe';
                              let currentUrl = window.location.href;
                              let splittedCurrentUrl = currentUrl.split("#");
                              params['currentRoute'] = splittedCurrentUrl[1]
                              var self = this;
                              excon.callApi('login/resendOTPcodeAndLink', 'post', params).then(function(responseData){
                                console.log(responseData);
                                var response = responseData.data;
                                excon.showAlertMessage(response);
                              })
                 },\n
                 """
    }

    vueElintegroProfileMenuDataframe_script(VueJsEntity) { bean ->
        computed = """ vueElintegroProfileMenuDataframe_person_fullName(){return excon.capitalize(this.state.persisters.person.firstName.value) + " " + excon.capitalize(this.state.persisters.person.lastName.value)},\n
                       vueElintegroProfileMenuDataframe_person_email(){return this.state.persisters.person.email.value},\n"""
        methods = """logout: function(){
                                     const self =this;
                                     let params={};
                                     excon.callApiWithQuery('logoff', 'POST', params).then((response)=>{
                                         self.\$router.push("/")
                                         window.location.reload();
                                    })
                    },\n"""
    }
    vueElintegroUserProfileDataframe_script(VueJsEntity){bean ->
        created = """this.vueElintegroProfileMenuDataframeShow();"""

        methods = """vueElintegroProfileMenuDataframeShow : function(){
                  excon.setVisibility("vueElintegroProfileMenuDataframe",false)},\n
                  """
    }
    vueMapWidgetDataframe_script(VueJsEntity) { bean ->
        data = "vueRegisterDataframe_display:true,\n checkboxSelected: [],\n"
    }

    vueEmployeeAddressDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
        methods = """updateAddressFields(result){
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
                    },\n"""
    }

    vueAddressDetailDataframe_script(VueJsEntity) { bean ->

/*
        watch = """ refreshVueAddressDataframe:{handler: function(val, oldVal) {this.vueAddressDetailDataframe_fillInitData();}},"""
        computed = "refreshVueAddressDataframe(){return this.\$store.state.vueContactDetailDataframe.key},"
*/
        methods = """updateAddressFields(result){
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
                    },\n"""
    }
    vueAddressEditDataframe_script(VueJsEntity) { bean ->

        data = """ updatedAddressValue:'', \n """
/*
        watch = """ refreshVueAddressDataframe:{handler: function(val, oldVal) {this.vueAddressEditDataframe_fillInitData();}},"""
        computed = "refreshVueAddressDataframe(){return this.\$store.state.vueContactEditDataframe.key},"
*/
        methods = """updateAddressFields(result){
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
                    },\n"""
    }
    vueElintegroSignUpQuizDataframe_script(VueJsEntity){bean ->
        methods = """saveSignUpForm(){
                                    var params = this.state;
                                    params['dataframe'] = 'vueElintegroSignUpQuizDataframe';
                                    var self = this;
                                    excon.callApi('register/createLeadUser', 'post', params).then(function(responseData){
                                           excon.showAlertMessage(responseData.data);
                                           window.location.reload();
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
                                    if(this.\$refs.vueElintegroChangePasswordAfterSignUpDataframe_form.validate()){
                                        excon.callApi('register/changePassword', 'post', params).then(function(responseData){
                                               var response = responseData.data;
                                               excon.showAlertMessage(response);
                                               if(response.success == true){
                                                  setTimeout(function(){window.open("/","_self");}, 2000);
                                               }else{
                                                    setTimeout(function(){window.location.reload();},2000);
                                               }

                                        })
                                    }


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
        data = """drawerVisible: false, group: null,"""
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
                     },\n


        """
    }
    vueNewEmployeeBasicInformationDataframe_script(VueJsEntity){bean ->
        methods = """
                      /* This method is an example of possibility to manipulate the objects on the Front-end*/
                      newEmployeeBasicInformation_doSomething(){
                       //Can use all javascript arsenal
                       // Can create variabbles and access this.state.<dataframe> JSON objects
                       var details = this.state.vueNewEmployeeBasicInformationDataframe
                       //Run existing methods, defined in Datfram descriptor and generated by the framework
                       if (this.\$refs.vueNewEmployeeBasicInformationDataframe_form.validate()){
                       //Even call a backend!
                       excon.callApi('EmployeeApplication/createApplicant', 'post', params).then(function(responseData){
                          var response = responseData;
                          //Use excon object to store/retrieve data fro the store.state.<dataframe>....
                          excon.saveToStore("vueNewEmployeeBasicInformationDataframe","state",response.data.data)
                        });

                        excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeUploadResumeDataframe-tab-id");
                        }
                     },\n
                     """
    }
    vueNewEmployeeSelfAssesmentDataframe_script(VueJsEntity){
        created = """this.fillApplicationSkillTable();"""
        methods = """
                 fillApplicationSkillTable(){
                 var details = this.state.vueNewEmployeeSelfAssesmentDataframe
                 var params = {};
                       var self = this;
                            params['id']= excon.getFromStore('vueNewEmployeeBasicInformationDataframe','domain_keys.application.id');
                       params['dataframe'] = 'vueNewEmployeeSelfAssesmentDataframe';
                       excon.callApi('EmployeeApplication/initiateSkillSet', 'post', params).then(function(responseData){
                         self.vueNewEmployeeSelfAssesmentDataframe_fillInitData();
                          var response = responseData;
                });

                  },\n
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
                                    });
                  },\n"""

    }
    vueNewEmployeeThankYouMessageAfterSaveDataframe_script(VueJsEntity) { bean ->
        computed = """ vueNewEmployeeThankYouMessageAfterSaveDataframe_person_fullName(){return excon.capitalize(this.state.persisters.person.firstName.value) + " " + excon.capitalize(this.state.persisters.person.lastName.value)},\n"""
    }

    vueElintegroApplicantGeneralInformationDataframe_script(VueJsEntity){bean ->
          computed ="""vueElintegroApplicantGeneralInformationDataframe_person_selectedposition(){
                                        var positions = [];
                                        var items = this.state.persisters.application.availablePositions.value;
                                        if(!items) return;
                                        for(i=0;i<items.length;i++){
                                           positions[i] = items[i].name;
                                        }
                                        var selectedPosition = positions.join(",\t\t");
                                        return selectedPosition;},\n"""
    }

    vueElintegroApplicantCVDataframe_script(VueJsEntity){ bean ->
        def pathForPdf = Holders.grailsApplication.config.images.defaultImagePathForPdf
        def pathForExcel = Holders.grailsApplication.config.images.defaultImagePathForExcel
        def pathForDocFile = Holders.grailsApplication.config.images.defaultImagePathForDocFile
        def pathForCsvFile =  Holders.grailsApplication.config.images.defaultImagePathForCsvFile
        methods ="""afterRefreshing(response){

                                 let params = response;
                                 if(params.persisters){
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
                                      stateValues.persisters.files.fileName.value_name = fileName;
                                      excon.saveToStore('vueElintegroApplicantCVDataframe',stateValues);
                                  }else{
                                  console.log("File not found...")
                                  }

                                  },\n

        """
    }
    vueElintegroCommentPageForApplicantDataframe_script(VueJsEntity){bean ->
        methods ="""addCommentsForApplicant(){

                                    var params = this.state;
                                    var self = this;
                                    params['dataframe'] = 'vueElintegroCommentPageForApplicantDataframe';
                                    excon.callApi('EmployeeApplication/addComment', 'post', params).then(function(responseData){
                                                                   var response = responseData.data;
                                                                   self.vueElintegroCommentPageForApplicantDataframe_fillInitData()
                                    });
                  },\n"""
    }
    vueTranslatorAssistantAfterLoggedInDataframe_script(VueJsEntity) {
        computed = """ enableDisableTranstaleButtonComputed(){if(this.state.transits.projectList.value){return false}else{return true;};},\n"""
        methods = """enterTranslatorPage(){
                     excon.saveToStore('vueTranslatorDataframe','currentlySelectedProject',this.state.transits.projectList.value);
                     excon.redirectPage(this,'translator')

                 },\n"""
    }
    vueTranslatorAssistantBeforeLoggedInDataframe_script(VueJsEntity) {
        computed = """ enableDisableTranstaleButtonComputed(){if(this.state.transits.projectList.value){return false}else{return true;};},\n"""
        methods = """enterTranslatorPage(){
                     excon.saveToStore('vueTranslatorDataframe','currentlySelectedProject',this.state.transits.projectList.value);
                     excon.redirectPage(this,'translator')

                 },\n"""
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
                                                     excon.showAlertMessage(response);
                                                     excon.setVisibility('vueCreateProjectForTranslationDataframe', false);
                                    });
                    },\n"""
    }
    vueTranslatorDataframe_script(VueJsEntity){ bean ->
        data = """isHidden : false, showGridOfSourceText : false """
        computed = """showOrHideDownloadAllFilesButton(){if(this.state.transits.selectedLanguages.items && this.state.transits.selectedLanguages.items.length > 1){return true;}return false;},\n
                      enableDisableAddButton(){if(this.state.transits.notSelectedLanguages.value){return false;}else{return true}},\n
                   """
        methods = """addLanguage(){
                                    var params = this.state;
                                    var self = this;
                                    params['dataframe'] = 'vueTranslatorDataframe';
                                    params['projectId'] =Number( excon.getFromStore('vueTranslatorDataframe','projectId'));
                                    const notSelectedLanguages = params.transits.notSelectedLanguages;
                                    excon.callApi('translatorAssistant/addLanguage', 'post', params).then(function(responseData){
                                                  self.vueTranslatorDataframe_fillInitData();

                                    });
                             },\n
                             sourceText(){
                                     if(this.isHidden == true){
                                            this.isHidden = false;
                                     }
                                     let stateValuesForTranslatedGridDataframe = excon.getFromStore('vueGridOfTranslatedTextDataframe')
                                    if(stateValuesForTranslatedGridDataframe.targetLanguage){
                                        document.getElementById(stateValuesForTranslatedGridDataframe.targetLanguage).style.backgroundColor = "white";
                                    }
                                    let stateValues = excon.getFromStore('vueGridOfSourceTextDataframe')
                                    stateValues['projectId'] = this.state.projectId;
                                    stateValues['sourceLanguage'] = this.state.persisters.project.sourceLanguage.value;
                                    excon.saveToStore('vueGridOfSourceTextDataframe',stateValues);
                                    excon.fillInitialData(stateValues);
                                    this.showGridOfSourceText = true;
                             }, \n
                             translatedText(params){
                                        if(this.showGridOfSourceText == true){
                                            this.showGridOfSourceText = false;
                                        }
                                        let stateValues = excon.getFromStore('vueGridOfTranslatedTextDataframe')
                                        let previouslyClickedValue = stateValues.targetLanguage
                                        document.getElementById(params.language).style.backgroundColor = "#3B8AD9";
                                        if(previouslyClickedValue && previouslyClickedValue != params.language)
                                        {
                                            document.getElementById(previouslyClickedValue).style.backgroundColor = "white";
                                        }
                                        if(previouslyClickedValue == ""){
                                            this.isHidden = !this.isHidden
                                        }else{
                                            this.isHidden = true
                                        }
                                        stateValues.targetLanguage = params.language;
                                        stateValues.gridTitleFromState = params.language;
                                        stateValues['projectId'] = this.state.projectId;
                                        stateValues['sourceLanguage'] = this.state.persisters.project.sourceLanguage.value;
                                        excon.saveToStore('vueGridOfTranslatedTextDataframe',stateValues);
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
                                                                     excon.showAlertMessage(response);
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
                    translateText(param){
                                        excon.saveToStore('vueAddNewRecordForCurrentProjectDataframe','textToTranslate_selectedrow',param)
                                        var params = this.state;
                                        params['targetLanguage'] = param.TargetLanguage;
                                        params['dataframe'] = 'vueAddNewRecordForCurrentProjectDataframe';
                                        excon.callApi('translatorAssistant/translateNewlyAddedRecord', 'post', params).then(function(responseData){
                                                 var response = responseData.data;
                                                 excon.refreshDataForGrid(response,'vueAddNewRecordForCurrentProjectDataframe', 'textToTranslate', 'U', 'transits');
                                        })
                    },\n
                    saveNewlyAddedRecord(){
                                       var params = this.state;
                                       var self = this;
                                       if(this.state.transits.key.value != "" && this.state.transits.sourceText.value != ""){
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
                                    if(this.state.transits.key.value != "" && this.state.transits.sourceText.value != ""){
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
        methods = """
                   updateEditedTextInGrid(){
                                      var translatedData = {text:{value:this.state.transits.text.value}, language:{value:this.vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe_prop.parentData.TargetLanguage}};
                                      var response = {persisters:{textToTranslate:translatedData}};
                                      excon.refreshDataForGrid(response,'vueAddNewRecordForCurrentProjectDataframe', 'textToTranslate', 'U', 'transits');
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
        data = """progressBarEnable:false,"""
        computed = """downLoadButtonShowHide(){
                          var retrivedData = this.state.transits.translatedText.items;
                          if(retrivedData.length > 1 && this.progressBarEnable == false){
                             return false;
                          }
                          else{
                              return true;
                          }
        },\n
                       translateWithGoogleButtonShowHide(){
                          var retrivedData = this.state.transits.translatedText.items;
                          if(retrivedData.length > 1 || this.progressBarEnable == true){
                             return false;
                          }
                          else{
                              return true;
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
                                                      excon.saveToStore('vueElintegroProgressBarDataframe', 'progressBarValue', {'progressValue':response,'progressText':'Translating...'})
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
    vueEditSourceRecordsOfGridDataframe_script(VueJsEntity){bean ->
        methods = """closeVueEditSourceRecordsOfGridDataframe(){
                     let stateValuesForEditSourceRecordsOfGridDataframe = excon.getFromStore('vueEditSourceRecordsOfGridDataframe')
                     var textBeforeEditing = stateValuesForEditSourceRecordsOfGridDataframe.textBeforeEditing;
                     var textAfterEditing = stateValuesForEditSourceRecordsOfGridDataframe.persisters.originalSourceText.text.value;
                     if(textBeforeEditing != textAfterEditing){
                       var result = confirm("Are you sure want to abandon the changes?");
                        if(result){
                                stateValuesForEditSourceRecordsOfGridDataframe.persisters.originalSourceText.text.value = textBeforeEditing;
                                excon.saveToStore('vueEditSourceRecordsOfGridDataframe',stateValuesForEditSourceRecordsOfGridDataframe);
                                excon.setVisibility("vueEditSourceRecordsOfGridDataframe", false);
                        }
                         return false;
                     }
                          excon.setVisibility("vueEditSourceRecordsOfGridDataframe", false);

                    },\n
                  """
    }
    vueEditTranslatedRecordsOfGridDataframe_script(VueJsEntity){bean ->
        methods ="""saveEditedRecordOfTranslatedText(){
                       var params = this.state;
                       var self = this;
                       excon.callApi('translatorAssistant/saveEditedRecordOfTranslatedText', 'post', params).then(function(responseData){
                          let response = responseData.data;
                          excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                          excon.showAlertMessage(response)
                          excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe','textBeforeEditing',response.persisters.translatedText.text.value);
                          excon.refreshDataForGrid(response,'vueGridOfTranslatedTextDataframe', 'translatedText', 'U', 'transits');
                       });
                    },\n
                    googleTranslateForEachRecord(){
                    var params = this.state;
                    var self = this;
                    params['sourceLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','sourceLanguage');
                    params['targetLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','targetLanguage');
                    params['projectId'] = excon.getFromStore('vueGridOfTranslatedTextDataframe','projectId');
                    params['dataframe'] = 'vueEditTranslatedRecordsOfGridDataframe';
                    excon.callApi('translatorAssistant/translateEachRecordWithGoogle', 'post', params).then(function(responseData){
                                                                   let response = responseData.data;
                                                                   let stateValuesForEditTranslatedRecordsOfGridDataframe = excon.getFromStore('vueEditTranslatedRecordsOfGridDataframe')
                                                                   stateValuesForEditTranslatedRecordsOfGridDataframe.persisters.translatedText.text.value = response.translatedText
                                                                   excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe', stateValuesForEditTranslatedRecordsOfGridDataframe);
                                                                   });
                    },\n
                    closeVueEditTranslatedRecordsOfGridDataframe(){
                     let stateValuesForEditTranslatedRecordsOfGridDataframe = excon.getFromStore('vueEditTranslatedRecordsOfGridDataframe')
                     var textBeforeEditing = stateValuesForEditTranslatedRecordsOfGridDataframe.textBeforeEditing;
                     var textAfterEditing = stateValuesForEditTranslatedRecordsOfGridDataframe.persisters.translatedText.text.value;
                     if(textBeforeEditing != textAfterEditing){
                       var result = confirm("Are you sure want to abandon the changes?");
                        if(result){
                                stateValuesForEditTranslatedRecordsOfGridDataframe.persisters.translatedText.text.value = textBeforeEditing;
                                excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe',stateValuesForEditTranslatedRecordsOfGridDataframe);
                                excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                        }
                         return false;
                     }
                          excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);

                    },\n
                    """
    }
    vueTermAndConditionDataframe_script(VueJsEntity){bean->
        data = " privacyPolicyContent:'', "
        methods = """    termsAndConditions(){
                            var params = this.state;
                            var self = this;
                            params['dataframe'] = 'vueFooterContainerDataframe';
                            excon.callApi('profileDetail/termAndConditions', 'post', params).then(function(responseData){
                                                  self.privacyPolicyContent = responseData.data
                                    });
                        }
                    """
    }

}
