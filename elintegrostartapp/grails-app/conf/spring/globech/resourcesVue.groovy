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

import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.erf.widget.vue.FKWidgetVue
import com.elintegro.erf.widget.vue.PictureDisplayWidgetVue
import grails.util.Holders

beans {

    def contextPath = Holders.grailsApplication.config.rootPath
    vueInitDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueInitDataframe']
        saveButton = false
        wrapInForm=false

        initOnPageLoad = false
//        childDataframes = ["vueLoginDataframe"]
        //Vue parameters
        isGlobal = true
        vueStore = ["state":"loggedIn: false,\n"]

        putFillInitDataMethod = false
        currentFrameLayout = ref("emptyDataframeLayout")
    }
    vueAppNameDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAppNameDataframe']
        saveButton = false
        wrapInForm=false

        hql = "select facility.id, facility.facilityName from Facility facility inner join facility.users as user where user.id=:session_userid"
        initOnPageLoad = true

        vueStore = ["state": "facilityName: '', \nfacilityId:'',\n"]
        doAfterRefresh = """excon.saveToStore("vueAppNameDataframe","facilityId", vueAppNameDataframeVar.vueAppNameDataframe_facility_id);"""
        addFieldDef =[
                "facility.facilityName":[
                        widget: "TextDisplayWidgetVue"
                ]
        ]
//        childDataframes = ["vueLoginDataframe"]
        //Vue parameters
        isGlobal = true
//        vueStore = ["state":"loggedIn: false,\n"]

        currentFrameLayout = ref("appNameDataframeLayout")
    }


    vueToolbarDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueToolbarDataframe']
        saveButton = false
        wrapInForm=false

        initOnPageLoad = false
//        childDataframes = ["vueLoginDataframe"]     script:"""this.\$router.push("/vueapplicationformdataframe");""" , roles: "ROLE_NURSE, ROLE_ADMIN", accessType: "ifAnyGranted"               nudge-right='220' nudge-right='220'   Register:[name:"Register", type:"link", script:"console.log('Register tab selected ');Vue.set(this.\$store.state.vueToolbarDataframe, 'myProperty_display', false);Vue.set(this.\$store.state.vueToolbarDataframe, 'financialData_display', false);Vue.set(this.\$store.state.vueToolbarDataframe, 'myRentals_display', true);"],
        //Vue parameters attr:"v-if='this.\$store.state.vueToolbarDataframe.newApplication_display'", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'],
        isGlobal = true
        createStore = true
        vueStore = ["state":"newApplication_display: true,\n"]
        wrapButtons = false

        dataframeButtons = [NewApplication:[name:"NewApplication", type:"link", route: true, routeIdScript: "0", refDataframe: ref("vueApplicationFormDataframe"), roles: "ROLE_NURSE, ROLE_ADMIN", accessType: "ifAnyGranted"],
                            Register:[name:"register", type:"link",showAsMenu:[attr:"open-on-hover offset-y right nudge-right=150  nudge-width='80px'", attachTo: "vueToolbarDataframe-Register"],  refDataframe: ref("vueRegisterMenuDataframe"), roles: "ROLE_ADMIN", accessType: "ifAnyGranted"],
                            UserManagement:[name:"UserManagement", type:"link", showAsMenu:[attr: "open-on-hover offset-x right nudge-right=300 nudge-width='50'", attachTo:"vueToolbarDataframe-UserManagement"], refDataframe: ref('vueUserManagementMenuDataframe'), roles: "ROLE_ADMIN, ROLE_SUPER_ADMIN", accessType: "ifAnyGranted"],
                            EventManagement:[name:"EventManagement", type:"link", showAsMenu:[attr: "open-on-hover offset-x right nudge-right=460 nudge-width='50'", attachTo:"vueToolbarDataframe-EventManagement"], refDataframe: ref('vueEventManagementMenuDataframe'), roles: "ROLE_NURSE, ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_CLIENT", accessType: "ifAnyGranted"],

        ]

        currentFrameLayout = ref("vueToolbarDataframeLayout")
    }

    vueEventManagementMenuDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEventManagementMenuDataframe']
        isGlobal = true
        saveButton = false

        initOnPageLoad = false
//        childDataframes = ["vueLoginDataframe"]     script:"""this.\$router.push("/vueapplicationformdataframe");"""                nudge-right='220' nudge-right='220'   Register:[name:"Register", type:"link", script:"console.log('Register tab selected ');Vue.set(this.\$store.state.vueToolbarDataframe, 'myProperty_display', false);Vue.set(this.\$store.state.vueToolbarDataframe, 'financialData_display', false);Vue.set(this.\$store.state.vueToolbarDataframe, 'myRentals_display', true);"],
        //Vue parameters attr:"v-if='this.\$store.state.vueToolbarDataframe.newApplication_display'", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'],
        isGlobal = true
        createStore = true
        vueStore = ["state":"event_manager: true,\n"]

        dataframeButtons = [ReportClientEvent:[name:"ReportClientEvent", type:"link", "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], route: true, routeIdScript: "0", refDataframe: ref("vueRecordEventDataframe"), roles:["ROLE_ADMIN", "ROLE_NURSE", "ROLE_RECEPTIONIST", "ROLE_CARE_GIVER"], accessType: "ifAnyGranted"],
                            IssueRequest:[name:"IssueRequest", type:"link", attr: "open-on-hover", "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN", "ROLE_NURSE", "ROLE_RECEPTIONIST", "ROLE_CARE_GIVER", "ROLE_CLIENT"], accessType: "ifAnyGranted"],
                            ReportIncident:[name:"ReportIncident", type:"link", "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN", "ROLE_NURSE", "ROLE_RECEPTIONIST", "ROLE_CARE_GIVER"], accessType: "ifAnyGranted"]/*, attr:"text"*/
        ]
        currentFrameLayout = ref("vueUserManagementMenuDataframeLayout")
    }

    vueUserManagementMenuDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueUserManagementMenuDataframe']
        isGlobal = true
        saveButton = false
        dataframeButtons = [ApplicationManagement:[name:"ApplicationManagement", type:"link", "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], "route" : true, routeIdScript: "0", refDataframe: ref("vueApplicationManagementDataframe") , roles:["ROLE_ADMIN", "ROLE_RECEPTIONIST", "ROLE_CARE_GIVER"], accessType: "ifAnyGranted"],
                            ClientManagement:[name:"ClientManagement", type:"link", route: true, routeIdScript: "0", refDataframe: ref("vueClientGridDataframe"), "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN", "ROLE_RECEPTIONIST", "ROLE_CARE_GIVER", "ROLE_NURSE"], accessType: "ifAnyGranted"],
                            Employees:[name:"Employees", type:"link",  attr: "open-on-hover", route: true, routeIdScript: "0", refDataframe: ref("vueEmployeeGridDataframe"),"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],
                            Vendors:[name:"Vendors", type:"link",route: true, routeIdScript: "0", refDataframe: ref("vueVendorGridDataframe"), "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], attr:"text", roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],
                            ServiceProviders:[name:"ServiceProviders", type:"link", attr:"text",route: true, routeIdScript: "0", refDataframe: ref("vueProviderGridDataframe"), "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],
                            Users:[name:"Users", type:"link", attr:"text", "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_SUPER_ADMIN"], accessType: "ifAnyGranted"]
        ]
        currentFrameLayout = ref("vueUserManagementMenuDataframeLayout")
    }

    vueRegisterMenuDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueRegisterMenuDataframe']
        isGlobal = true
        saveButton = false

        dataframeButtons = [Administrator:[name:"Administrator", type:"link", "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],
//                            Client:[name:"Client", type:"link", attr: "text", route: true, routeIdScript: "0", refDataframe: ref("vueClientGridDataframe"), "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],

//                            Receptionist:[name:"Receptionist", type:"link",  "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], attr:"text",roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],
                            Employee:[name:"Employee", type:"link", route: true, routeIdScript: 0, refDataframe: ref("vueEmployeeAddDataframe"), "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], attr:"text",roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],
                            Provider:[name:"Provider", type:"link", route: true, routeIdScript: 0, refDataframe: ref("vueProviderAddDataframe"), attr:"text",  "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],
//                            Relative:[name:"Relative", type:"link", attr:"text", route: true, routeIdScript: "0", refDataframe: ref("vueRelativeGridDataframe"), "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], showAsDialog: true, roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"],
                            Vendors:[name:"Vendors", type:"link", route: true, routeIdScript: 0, refDataframe: ref("vueVendorAddDataframe"), attr:"text",  "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'], roles:["ROLE_ADMIN"], accessType: "ifAnyGranted"]
        ]
        currentFrameLayout = ref("vueRegisterMenuDataframeLayout")
    }

    vueUserProfileDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueUserProfileDataframe']

        dataframeLabelCode = "User.Profile"
        hql = "select person.id, person.mainPicture,person.email, person.firstName, person.lastName, person.bday,  person.phone from Person as person where person.id=:id"

        //These are values, that overrides the default ones
        saveButton = true
        deleteButton = false
        submitButton=false
        wrapInForm=true
        tab = true
//        isGlobal = true
//        createStore = true
//        childDataframes=["vueAddressDataframe"]

        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        doAfterSave = """setTimeout(function(){ vueUserProfileDataframeVar.\$router.push('/');this.location.reload();}, 3000);"""
        route = true
        addFieldDef =[
                "person.id":[
                        widget: "NumberInputWidgetVue",
                        "required": "required"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6']],

                "person.firstName":[
                        widget: "InputWidgetVue",
                        "required": "required"
                        ,"validate":["rule":["v => !!v || 'FirstName is required'", "v => (v && v.length <= 10) || 'FirstName must be less than 10'"]]
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']],

                "person.lastName":[
                        widget: "InputWidgetVue",
                        "required": "required"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']
                        ,"validate":["rule":["v => !!v || 'LastName is required'", "v => (v && v.length <= 10) || 'LastName must be less than 10'"]]
                ],
                "person.bday":[
                        widget: "DateWidgetVue",
                        "required": "required"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg12', 'xl4']],
                "person.email":[
                        widget: "EmailWidgetVue",
                        "required": "required"
                        ,readOnly: true
                        ,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        ,"validate":["rule":["v => !!v || 'E-mail is required'"]]
                ],
                "person.phone":[
                        widget: "PhoneNumberWidgetVue",
                        "required": "required"
                        ,"validate":["rule":["v => !!v || 'Phone Number is required'"]]
                ],
                "person.languages":[
                        widget: "MultiSelectComboboxVue"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl4']
                        , hql: """select language.id as id, language.ename as ename from Language as language"""
                        ,"displayMember":"ename"
                        ,"valueMember":"id"
                        , search:true
                ],

                "person.mainPicture":[
                        "widget" : "PictureDisplayWidgetVue",
                        "aspectRatio":"2.5",
                        "attr": "contain",
                        "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'],
                        "width":200,
                        "height":200],

                "person.uploadPicture":[
                        "widget" : "PictureUploadWidgetVue"
                        ,name:"propertyImages"
                        , valueMember: "mainPicture"
                        ,ajaxFileSaveUrl: "${contextPath}/fileUpload/ajaxFileSave"
                        ,insertAfter: "person.mainPicture"
                        ,multiple:false
                        ,editButton: true
                        ,deleteButton:true
                ]
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
        dataframeButtons = [ resetPassword: [name:"resetPassword", type: "button", url: "", route: true, "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'], refDataframe: ref("vueResetPasswordDfr")] ]

        currentFrameLayout = ref("vueUserProfileDataframeLayout")

    }

    vueResetPasswordDfr(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueResetPasswordDfr']

//		hql = "select user.password from User as user where user.id=:session_userid"

        initOnPageLoad=true //false by default

        //These are values, that overrides the default ones
        saveButton = false
        wrapInForm=true
        route=true

        //Javascript to run after the save:
        doAfterSave="""
//						jQuery('#').jqxWindow('close');
                       //alert("Data has been saved successfully for id = "+data.generatedKeys[0]);
                    """

        addFieldDef =[
                "user.password":[widget: "PasswordWidgetVue", "width":"150", "height":"25"],
                "password2":[widget: "PasswordWidgetVue", "width":"150", "height":"25"]
        ]
        dataframeButtons = [ Submit: [name:"submit", type: "button", url: "${contextPath}/register/resetUserPassword", doBeforeAjax: """var url = Dataframe.getUrl();
                                                                                                                            var t = url.searchParams.get("token"); 
                                                                                                                            if(t != undefined || t != null){ allParams['t']=t;}
                                                                                          allParams['resetPasswordDfr-user-email']=jQuery("#userProfileDataframe-person-email").val();
                                                                                         """, callBackParams:[successScript:"""if(data.redirect){window.location.href=data.redirectUrl;}
                                                                                                                               jQuery('#resetPassword-Div').jqxWindow('close');"""]],
                             Cancel:[name:"cancel", type:"button", script:"\$router.go(-1)"]]

        currentFrameLayout = ref("defaultDialogBoxLayout")
    }

    vueLoginNavigation(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueLoginNavigation']
        saveButton = false
        wrapInForm=false

        initOnPageLoad = false
//        childDataframes = ["vueLoginDataframe"]
        //Vue parameters
        isGlobal = true
        dataframeButtons = [
                Register:[name:"register", type:"link", "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'], showAsDialog: true, attr:"flat", script:""" this.vueRegisterDataframe_display = true;\n  excon.saveToStore('dataframeShowHideMaps','vueRegisterDataframe_display', true);\n""", refDataframe: ref("vueRegisterDataframe"), tooltip: [message: 'Register']],
                Login:[name:"login", type:"link", attr:"flat", "flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6'], showAsDialog: true,script:""" this.vueLoginDataframe_display = true; \n  excon.saveToStore('dataframeShowHideMaps','vueLoginDataframe_display', true);\n""", refDataframe: ref("vueLoginDataframe"), tooltip: [message: 'Login']]]
        currentFrameLayout = ref("loginLogoutNavigationLayout")
        createStore = true

    }

    vueAlertMsgDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAlertMsgDataframe']
        saveButton = false
        wrapInForm=false

        initOnPageLoad = false

        isGlobal = true

        currentFrameLayout = ref("emptyDataframeLayout")

        addFieldDef =[
                "alert":[widget: "SnackbarWidgetVue"
//                        ,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]

        ]

    }

    vueRegisterDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueRegisterDataframe']

        hql = "select user.email, user.password, user.firstName, user.lastName from User as user where user.id=:id"

        ajaxSaveUrl = "${contextPath}/register/register"

        dataframeLabelCode = "User.Registration"
        //These are values, that overrides the default ones
        saveButtonAttr = " color='light-blue darken-2' dark"
        initOnPageLoad = false
        isGlobal = true
        saveButton = true
        wrapInForm=true

        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doAfterSave = """ excon.saveToStore('vueLoginNavigation','responseData');\nexcon.saveToStore('dataframeShowHideMaps','vueRegisterDataframe_display', false);\n
                           """
        addFieldDef =[
                "user.email":[widget: "EmailWidgetVue", "placeHolder":"Enter your email","flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "user.firstName":[widget: "InputWidgetVue", "placeHolder":"Enter your Firstname","flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                                  ,"validate":["rule":["v => !!v || 'FirstName is required'", "v => (v && v.length <= 20) || 'FirstName must be less than 20'"]]],
                "user.lastName":[widget: "InputWidgetVue", "placeHolder":"Enter your Lastname","flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                                 ,"validate":["rule":["v => !!v || 'LastName is required'", "v => (v && v.length <= 20) || 'LastName must be less than 20'"]]]
                ,"user.password":[widget: "PasswordWidgetVue", "width":"150", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                                  ,"validate":["rule":["v => !!v || 'Password is required'", "v => (v && v.length >= 8) || 'Password must be greater than 8'"]]]
                ,"password2":[widget: "PasswordWidgetVue", "width":"150", "insertAfter":"user.password", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                              ,"validate":["rule":["v => !!(v==this.vueRegisterDataframe_user_password) || 'Password and Confirm Password must match'"]]]
        ]

        currentFrameLayout = ref("defaultDialogBoxLayout")
    }

    vueLoginDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueLoginDataframe']
        dataframeLabelCode = "User.Login"
        hql = "select user.username, user.password from User as user where user.id=:id"
        wrapInForm = true
        saveButton = false
        initOnPageLoad = false
        isGlobal = true

        boolean loginWithSpringSecurity = Holders.grailsApplication.config.loginWithSpringSecurity?true:false
        String loginAuthenticateUrl = loginWithSpringSecurity?"${contextPath}/login/authenticate" : "${contextPath}/login/loginUser"

        addFieldDef = ["user.password":["widget" : "PasswordWidgetVue", "name": "user.password", autoComplete:"on", "width":150,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']]
                       ,"user.username":["widget" : "EmailWidgetVue",  "name": "user.username", autoComplete:"on", "width":150, "errMessage":"Username should be an email","flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']]
                       ,"rememberMe":["widget" : "CheckboxWidgetVue", height : '30px', "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']]
        ]

        dataframeButtons = [ login:[name:"login", type: "button", url: "${loginAuthenticateUrl}", layout: "<v-flex xs12 sm12 md6 lg6 xl6 pa-0>[BUTTON_SCRIPT]</v-flex>", attr: """color='light-blue darken-2' dark style="width: 10px;" """, doBeforeSave:""" var elementId = '#loginDataframe';
                                                                                                                                allParams["username"] = this.vueLoginDataframe_user_username;
                                                                                                                               allParams["password"] = this.vueLoginDataframe_user_password;
                                                                                                                               allParams["remember-me"] = this.vueLoginDataframe_rememberMe;
                                                                                                                                """,
                                    /* url: "/elintegrostartapp/login/loginUser" name:"login", type: "button",attr: "color='cyan'", script: """layout: " layout: "<v-flex xs12 sm12 md6 lg6 xl6 style='margin-bottom:10px;'><v-layout column align-start justify-center>[BUTTON_SCRIPT]</v-layout></v-flex>",
//                                                                                                                               var url = "/elintegrostartapp/api/login";
//                                                                                                                               var url = "/elintegrostartapp/login/testLogin";
                                                                                                                       var url = "/elintegrostartapp/login/authenticate";

                                                                                                                       var elementId = '#loginDataframe';
                                                                                                                       var allParams ={};
                                                                                                                       allParams["username"] = this.vueLoginDataframe_user_username;
                                                                                                                       allParams["password"] = this.vueLoginDataframe_user_password;
                                                                                                                       allParams["remember_me"] = this.vueLoginDataframe_rememberMe;
                                                                                                                       axios.post(url, allParams).then(function(responseData) {
                                                                                                                        var response = responseData.data
                                                                                                                        if (response.success) {
                                                                                                                            if (response.msg) {
                                                                                                                                store.commit('alertMessage', {
                                                                                                                                    'snackbar': true,
                                                                                                                                    'alert_type': 'success',
                                                                                                                                    'alert_message': response.msg
                                                                                                                                });
                                                                                                                            }
                                                                                                                            this.location.reload();
                                                                                                                            console.log("Login Callback");
                                                                                                                            console.log(response);
                                                                                                                            //Dataframe.showHideDataframesBasedOnUserType(data);

                                                                                                                        } else {
                                                                                                                            if (response.msg) {
                                                                                                                                store.commit('alertMessage', {
                                                                                                                                    'snackbar': true,
                                                                                                                                    'alert_type': 'error',
                                                                                                                                    'alert_message': response.msg
                                                                                                                                })
                                                                                                                            }

                                                                                                                        }
                                                                                                                            }).catch(function(error){
                                                                                                                                                        console.log(error.response)
                                                                                                                            });
                                                                                                                  layout: "<v-flex xs12 sm12 md6 lg6 xl6> <v-layout column align-center justify-center>[BUTTON_SCRIPT]</v-layout></v-flex>", """,<v-flex xs12 sm12 md6 lg6 xl6><v-layout column align-center justify-center style='margin-top:8px;'>[BUTTON_SCRIPT]</v-layout></v-flex>*/
                                    callBackParams: [successScript: """
                                                          console.log("Login Callback");
                                                           this.location.reload();
                                                          //Dataframe.showHideDataframesBasedOnUserType(data);
                                                       """,
                                                     failureScript:""" if(!response.msg){ this.location.reload();}"""],"flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                             forgetPassword:[name: "forgetPassword", type: "link", attr:"style='margin-left:-3px;'", script:""" console.log("Forget Password Clicked");""", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'],
                                             layout: "<v-flex xs12 sm12 md6 lg6 xl6 style='margin-bottom:10px;'><v-layout column align-start justify-center>[BUTTON_SCRIPT]</v-layout></v-flex>"],
                             logInWithGoogle:[name: "logInWithGoogle", type: "image", attr:"style='margin-left:-3px;'", image:[url: "vueLoginDataframe.button.logInWithGoogle.imageUrl", width:'135px', height: '48px'], script:"""
//                                                                                             var url = "/elintegrostartapp/oauth/authenticate/google";
                                                                                             var url = "${contextPath}/springSecurityOAuth2/authenticate?provider=google";
                                                                                             var childWindow = window.open(url, "payment",  "width=500,height=500");
                                                                                             /*if(childWindow){
                                                                                                window.opener.location.reload();
                                                                                                close();
                                                                                             }*/
                                                                                              """, "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']],
                             logInWithFacebook:[name: "logInWithFacebook", type: "image", attr: "style=\"margin-top:3px;\"", image:[url: "vueLoginDataframe.button.logInWithFacebook.imageUrl", width: '135px', height: '43px'],script:"""
                                                                                             var provider = 'facebook';
                                                                                             var url = "${contextPath}/springSecurityOAuth2/authenticate?provider="+provider+"";
                                                                                             var childWindow = window.open(url, "payment",  "width=500,height=500");
                                                                                              """, "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']]

        ]

        currentFrameLayout = ref("vueLoginDataframeLayout")
    }

    vueAfterLoggedinDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAfterLoggedinDataframe']

        isGlobal = true
        saveButton = false
        wrapInForm = false
        initOnPageLoad = false
        onClick = [popup: true,attr:"nudge-left=\"20\" nudge-bottom='0px' left", attachTo:'vueAfterLoggedinDataframe-form', refDataframe:ref('vueProfileMenuDataframe')]
        hql = "select person.id, person.mainPicture from Person person where person.user = :session_userid"

        addFieldDef = [
                /*"person.firstName":
                        [
                                "widget" : "InputWidgetVue",
                                "readOnly": true,
                                "hide": true,
                                "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        ],*/
                "person.mainPicture": [
                        "widget" : "PictureDisplayWidgetVue",
                        "layout": "<v-layout align-center justify-center><v-avatar :size=\"avatarSize\" color=\"grey lighten-4\">[FIELD_SCRIPT]</v-avatar></v-layout>",
                        "aspectRatio":"2.5",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                ]
        ]
        currentFrameLayout = ref("vueAfterLoggedinDataframeLayout")

    }

    vueProfileMenuDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueProfileMenuDataframe']
        hql = "select person.id, person.firstName, person.lastName, person.mainPicture from Person person where person.user = :session_userid"

        createStore = true
        isGlobal = true
        saveButton = false
        wrapInForm = true
//        "url":"https://s3.us-east-2.amazonaws.com/elintegro1",
        addFieldDef = [
                "person.mainPicture": [
                        "widget" : "PictureDisplayWidgetVue",
                        "layout": "<v-layout align-center justify-center><v-avatar :size=\"90\" style='margin-top:0px;' color=\"grey lighten-4\">[FIELD_SCRIPT]</v-avatar></v-layout>",
                        "aspectRatio":"2.5",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                ],
                "person.firstName": [
                        "widget" : "InputWidgetVue",
                        "readOnly": true,
                        "height":"50px",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                ],
                "person.id": [
                        "widget" : "InputWidgetVue",
                        "readOnly": true,
                        hide: true,
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                ]

        ]
//        this.location.reload();
        dataframeButtons = [profile:[name:'Profile', type: "link", attr: "text small", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'], showAsDialog: false, route:true, routeIdScript:"this.vueProfileMenuDataframe_person_id;", refDataframe: ref('vueUserProfileDataframe')],
                            Logout:[name:"logout", type:"link", url:"${contextPath}/logoff", attr:"text small","flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6'], script:"", callBackParams:[failureScript:"""vueProfileMenuDataframeVar.\$router.push("/");this.location.reload();"""]]]
        currentFrameLayout = ref("vueProfileMenuDataframeLayout")
    }

    vueRecordEventDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueRecordEventDataframe']
        dataframeLabelCode = "Record.Event"
        hql = "select event.id, event.client,event.eventType, event.description,  event.images from Event event where event.id=:id"

        ajaxSaveUrl = "${contextPath}/applicationForm/saveEvent"
        ajaxUrl = "${contextPath}/dataframe/ajaxCreateNew"
        initOnPageLoad = true
        route = true
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        addFieldDef = [

                "event.description":[
                        widget: "TextAreaWidgetVue"
                        ,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']],
                "event.client": [
                        "widget":"ComboboxVue",
                        "name":"event.client",
                        "hql":"select client.id as id, person.firstName as firstName from Client as client inner join client.person person"
                        ,"displayMember":"firstName"
                        ,"valueMember":"id"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6']
                ],
                "event.eventType": [
                        "widget":"ComboboxVue",
                        "name":"event.eventType",
                        "hql":"select type.id as id, type.code as code, type.name as name from EventType as type"
                        ,"displayMember":"name"
                        ,"valueMember":"id"
                        ,"flexGridValues":['xs12', 'sm6', 'md6', 'lg6', 'xl6']
                ],
                "event.images":[
                        "widget" : "PictureUploadWidgetVue"
                        ,name:"images"
                        ,valueMember: "images"
                        ,ajaxFileSaveUrl: "${contextPath}/fileUpload/ajaxFileSave"
                        ,multiple:true
                        ,editButton: true
                        ,attr: " style=\"margin-top:30px;\""
                        ,deleteButton:true
                ]
        ]

        currentFrameLayout = ref("defaultRouteDataframeLayout")
    }

    vueRelativeGridDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueRelativeGridDataframe']
        dataframeLabelCode = "Relative.Lists"
        saveButton = false
        route = true
        initOnPageLoad = true
//        doBeforeRefresh = """allParams['contactType'] = this.\$route.params.routeId"""
//        doAfterSave = "excon.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
        childDataframes = ["vueContactManagementDataframe"]
        /*,onClick:[showAsDialog: false, refDataframe: ref("vuePrescribedMedicationsDataframe")]
        ,onButtonClick:[
                ['actionName':'Payment','buttons':[
                        [showAsDialog: true,
                         tooltip:[message: "tooltip.tenant.payment.info", internationalization: true],
                         image:[url:"/elintegro/assets/icons/data-viewer.png"], refDataframe: ref("vuePrescribedMedicationsDataframe"),
                         script:"Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                        ]
                ]
                ]

        ]*/

        addFieldDef =[
                "relative": [
                        widget            : "GridWidgetVue"
                        , name            : "relative"
                        , hidecolumn      : "contractId"

                        , onClick         : [showAsDialog: true, refDataframe: ref("vueContactManagementDataframe")]
                        , hql             : """select person.id as Id, person.firstName as Firstname, person.lastName as Lastname, person.email as email, person.phone as Phone from Relative relative inner join relative.person person"""
                        , gridWidth       : 420
                        , search          : true
                        , "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]

        ]

        /*dataframeButtons = [ previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", url: ""] ,
                             Submit:[name:"submit", type: "button", script: "vueMedicationsGridDataframeVar.\$router.push('/');this.location.reload();"]
        ]*/
        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vueContactManagementDetailDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactManagementDetailDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.email, person.phone from Person as person where person.id=:id"

        initOnPageLoad = true
        saveButton = false
        dataframeLabelCode = "Contact.Details"
//        childDataframes=["vueAddressDataframe"]
//        doAfterSave = "excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model','vueMedicalRecordDataframe-tab-id');"
        addFieldDef = [

                "person.email": [
                        "widget"        : "EmailWidgetVue",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "person.phone"       : [
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],

                "person.firstName"   : [
                        "validate": ["rule": ["v => !!v || 'FirstName is required'", "v => (v && v.length <= 20) || 'FirstName must be less than 20'"]],
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "person.lastName"    : [
                        "validate": ["rule": ["v => !!v || 'LastName is required'", "v => (v && v.length <= 20) || 'LastName must be less than 20'"]],
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "person.mainPicture" : [
                        "widget"        : "PictureDisplayWidgetVue",
                        "defaultValue"  : "`vueElintegroLogoDataframe(DataframeVue){bean ->\n" +
                                "        bean.parent = dataFrameSuper\n" +
                                "        bean.constructorArgs = ['vueElintegroLogoDataframe']\n" +
                                "        isGlobal = true\n" +
                                "        saveButton = false\n" +
                                "        addFieldDef =[\n" +
                                "                \"person.mainPicture\":[\n" +
                                "                        \"widget\": \"PictureDisplayWidgetVue\",\n" +
                                "                        \"defaultValue\"  : \"elintegro_logo.png\",\n" +
                                "                        flexGridValues: ['xs12', 'sm6', 'md1', 'lg4', 'xl4'],\n" +
                                "                        \"aspectRatio\":\"2.5\",\n" +
                                "                      \"height\":\"20px\",\n" +
                                "                        \"width\":\"300px\",\n" +
                                "                ]\n" +
                                "\n" +
                                "        ]\n" +
                                "        currentFrameLayout = ref(\"defaultDataframeLayout\")\n" +
                                "\n" +
                                "    }`.jpg",
                        "aspectRatio"   : "2.5",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "height"        : 200]
        ]

        currentFrameLayout = ref("vueContactDetailDataframeLayout")

    }
    vueContactManagementEditDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactManagementEditDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.email, person.phone from Person as person where person.id=:id"

        initOnPageLoad = true
        dataframeLabelCode = "Contact.Details"
//        childDataframes=["vueAddressDataframe"]
//        doAfterSave = "excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model','vueMedicalRecordDataframe-tab-id');"
        addFieldDef = [

                "person.email": [
                        "widget"        : "EmailWidgetVue",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "person.phone"       : [
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],

                "person.firstName"   : [
                        "validate": ["rule": ["v => !!v || 'FirstName is required'", "v => (v && v.length <= 20) || 'FirstName must be less than 20'"]],
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "person.lastName"    : [
                        "validate": ["rule": ["v => !!v || 'LastName is required'", "v => (v && v.length <= 20) || 'LastName must be less than 20'"]],
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "person.mainPicture" : [
                        "widget"        : "PictureDisplayWidgetVue",
                        "defaultValue"  : "default_profile.jpg",
                        "aspectRatio"   : "2.5",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "height"        : 200]
        ]

        currentFrameLayout = ref("vueContactDetailDataframeLayout")

    }
}
