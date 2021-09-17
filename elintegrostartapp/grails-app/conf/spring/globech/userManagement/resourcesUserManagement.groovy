/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package spring.globech.contactManagement
import com.elintegro.erf.dataframe.vue.DataframeVue

import grails.util.Holders
beans {

    def contextPath = Holders.grailsApplication.config.rootPath
    vueEmployeeAddDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEmployeeAddDataframe']

        hql = "select employee.id, employee.positionStart, employee.role, employee.description, employee.person from Employee employee where employee.id=:id"

        ajaxSaveUrl = "userManagementForm/saveEmployee"

        dataframeLabelCode = "Employee.Registration"
        //These are values, that overrides the default ones
        childDataframes = ["vueEmployeeAddressDataframe"]
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
//        saveButtonAttr = " color='light-blue darken-2' dark"
        initOnPageLoad = false
        isGlobal = true
        route = true
        tab = true
        saveButton = true
        wrapInForm=true

        doBeforeSave = """params["facilityId"]= excon.getFromStore("vueAppNameDataframe","facilityId");\nparams["personId"] = excon.getFromStore("vueEmployeeContactDataframe", 'key');"""
        doAfterSave = """var params = response.params;\n

                         vueEmployeeAddDataframeVar.\$router.push('/');this.location.reload();"""
        addFieldDef =[
                "employee.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"person",
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueEmployeeContactDataframe")
                ],
                "employee.positionStart":[
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "employee.role"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "employee.role"
                        ,internationalize: true
                        ,"hql"          : "select role.id as id, role.authority as name from Role as role where isEmployee=true"

                        ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ],

                "employee.description":[
                        widget: "TextAreaWidgetVue"
                        ,"cssGridValues": ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],
        ]

        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueEmployeeAddDataframe, "vueEmployeeAddDataframe_tab_model","vueEmployeeAddressDataframe-tab-id");\n""", "cssGridValues": ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'],url: ""],
//                             Submit:[name:"submit", type: "button", script: "vueEmployeeAddDataframeVar.\$router.push('/');this.location.reload();"]
        ]
        currentFrameLayout = ref("vueEmployeeDataframeLayout")
    }

    vueEmployeeContactDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEmployeeContactDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.email, person.phone from Person as person where person.id=:id"

        initOnPageLoad = false
        createStore = true
        dataframeLabelCode = "Contact.Information"
//        childDataframes=["vueAddressDataframe"]
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        ajaxSaveUrl = "userManagementForm/saveContact"
        doAfterSave = """

                         excon.saveToStore("vueEmployeeContactDataframe","key", response.nodeId[0]);
                         excon.saveToStore('vueEmployeeAddDataframe','vueEmployeeAddDataframe_tab_model','vueEmployeeAddressDataframe-tab-id');"""
        addFieldDef =[

                "person.email":[
                        "widget"     : "EmailWidgetVue",
                        "cssGridValues": ['xs':'8', 'sm':'8', 'md':'8', 'lg':'8', 'xl':'8']
                ],
                "person.phone":[
                        "cssGridValues": ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
                ],

                "person.firstName":[
                        "validate":["rule":["v => !!v || 'FirstName is required'", "v => (v && v.length <= 20) || 'FirstName must be less than 20'"]],
                        "cssGridValues": ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
                ],
                "person.lastName":[
                        "validate":["rule":["v => !!v || 'LastName is required'", "v => (v && v.length <= 20) || 'LastName must be less than 20'"]],
                        "cssGridValues": ['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
                ],
                "person.mainPicture":[
                        "widget"      : "PictureDisplayWidgetVue",
                        "defaultValue":"default_profile.jpg",
                        "aspectRatio" :"2.5",
                        "cssGridValues": ['xs':'6', 'sm':'6', 'md':'6', 'lg':'4', 'xl':'4'],
                        "width"       :200,
                        "height"      :200]
        ]

        currentFrameLayout = ref("vueEmbeddedDataframeLayout")

    }
    vueEmployeeAddressDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEmployeeAddressDataframe']
        bean.autowire='byName'

        dataframeLabelCode = "Address.Information"
//		hql = "select address.id, address.addressLine, address.street from Address as address where address.id=:id"
        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip from Address as address where address.id=:id"


//        initOnPageLoad=true //false by default excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_display', false);
        doBeforeSave = "params['personId'] = excon.getFromStore('vueEmployeeContactDataframe','key');"
        doAfterSave = "excon.saveToStore('vueEmployeeAddDataframe','vueEmployeeAddDataframe_tab_model', 'vueEmployeeAddDataframe-tab-id');"

        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        ajaxSaveUrl = "userManagementForm/saveAddress"
        //These are default values, they are here to demonstrate how to overwrite it with different button combination, if required
        childDataframes = ["vueMapWidgetDataframe"]
        wrapInForm=false
        initOnPageLoad = false
        createStore = true
        addFieldDef = [
                "address.addressLine": [
                        "widget"   : "InputWidgetVue",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                ],

                "address.addressLine2": [
                        "widget"   : "InputWidgetVue",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                ],
                "address.postalZip": [
                        "widget"   : "InputWidgetVue"

                ],
                "validateWithGoogle":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"address.addressLine",
                        script       : """ this.updatedAddressValue = this.vueEmployeeAddressDataframe_address_addressLine;""",
                        "attr"       :"round right",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                ],
                "googleMap": [
                        "widget"      : "DataframeWidgetVue",
                        dataframe     : ref("vueMapWidgetDataframe"),
                        "attr"        :" @resultData='updateAddressFields'",
                        propPass      :[key:":addressValue", value:"updatedAddressValue"],
                        passValueAsProp : true,
                        "showInMap"   :true,
                        "name"        : "googleMap",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        "height"      :'500px'

                ]
        ]
        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueEmployeeAddDataframe, "vueEmployeeAddDataframe_tab_model","vueEmployeeContactDataframe-tab-id");\n""","cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'], url: ""] ]
        currentFrameLayout = ref("addressDataframeLayout")

    }

    vueEmployeeGridDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEmployeeGridDataframe']
//        dataframeLabelCode = "ContactManagement.Employees"
        dataframeLabelCode = "Employees"
        saveButton = false
        route = true
        initOnPageLoad = true
        doBeforeRefresh = """params['facilityId'] = excon.getFromStore("vueAppNameDataframe", 'facilityId');"""
//        doAfterSave = "excon.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
//        childDataframes = ["vueContactManagementDataframe"]


        addFieldDef =[
                "employee": [
                        widget            : "GridWidgetVue"
                        , name            : "employee"
                        , hidecolumn      : "contractId",
                        onButtonClick   : [
                                [
                                        'actionName': 'Actions', 'buttons': [
                                        [
                                                showAsDialog: true,
                                                tooltip     : [message: "tooltip.grid.edit", internationalization: true],
                                                refDataframe: ref("vueEmployeeEditDataframe"),
                                                vuetifyIcon : [name: "edit"],
                                                script      : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                        ],
                                        [
                                                deleteButton : true,
                                                tooltip      : [message: "tooltip.grid.delete", internationalization: true],
//                                        ajaxDeleteUrl: "/elintegrostartapp/applicationForm/ajaxExpire",
                                                refDataframe: ref("vueEmployeeEditDataframe"),
                                                vuetifyIcon  : [name: "delete"],
                                                script       : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                        ]
                                ]
                                ]
                        ]
                        , avatarAlias     : "Avatar" // should be same as alias name in hql
                        , onClick         : [showAsDialog: true, refDataframe: ref("vueEmployeeDetailDataframe")]
                        , hql             : """select  person.mainPicture as Avatar, person.firstName as Firstname, person.lastName as Lastname, employee.id as Id from Employee employee inner join employee.person person inner join employee.facility facility where facility.id=:facilityId"""
//                        , hql             : """select  person.id as ID, person.firstName as Firstname, person.lastName as Lastname, person.email as email from Employee employee inner join employee.person person inner join employee.facility facility where facility.id=:facilityId"""
                        , gridWidth       : 420
                        , showGridSearch          : true
                        , "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ]

        ]

        /*dataframeButtons = [ previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", url: ""] ,
                             Submit:[name:"submit", type: "button", script: "vueMedicationsGridDataframeVar.\$router.push('/');this.location.reload();"]
        ]*/
        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vueEmployeeDetailDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEmployeeDetailDataframe']

        dataframeLabelCode = "Employee.Details"
        hql = "select employee.id, employee.positionStart, employee.role, employee.description, employee.person from Employee employee where employee.id=:id"
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        saveButton = false
        wrapInForm = true
        tab = true
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueContactDetailDataframe", "vueAddressDetailDataframe"]

        addFieldDef = [
                "employee.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"id"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactDetailDataframe")
                ],
                "employee.positionStart":[
                        "readOnly"      : true,
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "employee.role"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "employee.role"
                        ,readOnly        : true
                        ,internationalize: true
                        ,"hql"          : "select role.id as id, role.authority as name from Role as role where isEmployee=true"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ],

                "employee.description":[
                        widget: "TextAreaWidgetVue"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],


        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']] ]
        currentFrameLayout = ref("vueEmployeeDetailDataframeLayout")
    }
    vueEmployeeEditDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueEmployeeEditDataframe']

        dataframeLabelCode = "Employee.Details"
        hql = "select employee.id, employee.positionStart, employee.role, employee.description, employee.person from Employee employee where employee.id=:id"
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        wrapInForm = true
        tab = true
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueContactEditDataframe", "vueAddressEditDataframe"]

        addFieldDef = [
                "employee.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"id",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactEditDataframe")
                ],
                "employee.positionStart":[
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "employee.role"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "employee.role"
                        ,internationalize: true
                        ,"hql"          : "select role.id as id, role.authority as name from Role as role where isEmployee=true"

                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ],

                "employee.description":[
                        widget: "TextAreaWidgetVue"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],


        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']] ]
        currentFrameLayout = ref("vueEmployeeEditDataframeLayout")
    }

//    #####################################For Provider ###################################################
    vueProviderAddDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueProviderAddDataframe']

        hql = "select provider.id, provider.person, provider.providerType from Provider provider where provider.id=:id"

        ajaxSaveUrl = "userManagementForm/saveProvider"

        dataframeLabelCode = "Provider.Registration"
        //These are values, that overrides the default ones
        childDataframes = ["vueProviderAddressDataframe"]
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
//        saveButtonAttr = " color='light-blue darken-2' dark"
        initOnPageLoad = false
        isGlobal = true
        route = true
        tab = true
        saveButton = true
        wrapInForm=true

        doBeforeSave = """params["facilityId"]= excon.getFromStore("vueAppNameDataframe","facilityId");\nparams["personId"] = excon.getFromStore("vueProviderContactDataframe", 'key');"""
        doAfterSave = """var params = response.params;\n

                         vueProviderAddDataframeVar.\$router.push('/');this.location.reload();"""
        addFieldDef =[
                "provider.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"person",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueProviderContactDataframe")
                ],
                "provider.providerType"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "provider.providerType"
                        ,internationalize: true
                        ,"hql"          : "select pType.id as id, pType.name as name from ProviderType as pType"

                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ]

        ]

        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueProviderAddDataframe, "vueProviderAddDataframe_tab_model","vueProviderAddressDataframe-tab-id");\n""", "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'],url: ""],
//                             Submit:[name:"submit", type: "button", script: "vueEmployeeAddDataframeVar.\$router.push('/');this.location.reload();"]
        ]
        currentFrameLayout = ref("vueProviderDataframeLayout")
    }

    vueProviderContactDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueProviderContactDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.email, person.phone from Person as person where person.id=:id"

        initOnPageLoad = false
        createStore = true
        dataframeLabelCode = "Contact.Information"
//        childDataframes=["vueAddressDataframe"]
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        ajaxSaveUrl = "userManagementForm/saveContact"
        doAfterSave = """

                         excon.saveToStore("vueProviderContactDataframe","key", response.nodeId[0]);
                         excon.saveToStore('vueProviderAddDataframe','vueProviderAddDataframe_tab_model','vueProviderAddressDataframe-tab-id');"""
        addFieldDef =[

                "person.email":[
                        "widget"     : "EmailWidgetVue",
                        "cssGridValues":['xs':'8', 'sm':'8', 'md':'8', 'lg':'8', 'xl':'8']
                ],
                "person.phone":[
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
                ],

                "person.firstName":[
                        "validate":["rule":["v => !!v || 'FirstName is required'", "v => (v && v.length <= 20) || 'FirstName must be less than 20'"]],
                        "cssGridValues":['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']

                ],
                "person.lastName":[
                        "validate":["rule":["v => !!v || 'LastName is required'", "v => (v && v.length <= 20) || 'LastName must be less than 20'"]],
                        "cssGridValues":['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
                ],
                "person.mainPicture":[
                        "widget"      : "PictureDisplayWidgetVue",
                        "defaultValue":"default_profile.jpg",
                        "aspectRatio" :"2.5",
                        "cssGridValues":['xs':'12', 'sm':'6', 'md':'6', 'lg':'4', 'xl':'4'],
                        "width"       :200,
                        "height"      :200]
        ]

        currentFrameLayout = ref("defaultDataframeLayout")

    }
    vueProviderAddressDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueProviderAddressDataframe']
        bean.autowire='byName'

        dataframeLabelCode = "Address.Information"
//		hql = "select address.id, address.addressLine, address.street from Address as address where address.id=:id"
        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip from Address as address where address.id=:id"


//        initOnPageLoad=true //false by default excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_display', false);
        doBeforeSave = "params['personId'] = excon.getFromStore('vueProviderContactDataframe','key');"
        doAfterSave = "excon.saveToStore('vueProviderAddDataframe','vueProviderAddDataframe_tab_model', 'vueProviderAddDataframe-tab-id');"

        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        ajaxSaveUrl = "userManagementForm/saveAddress"
        //These are default values, they are here to demonstrate how to overwrite it with different button combination, if required
        childDataframes = ["vueMapWidgetDataframe"]
        wrapInForm=false
        initOnPageLoad = false
        createStore = true
        addFieldDef = [
                "address.addressLine": [
                        "widget"   : "InputWidgetVue",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                ],

                "address.addressLine2": [
                        "widget"   : "InputWidgetVue",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                ],
                "address.postalZip": [
                        "widget"   : "InputWidgetVue"

                ],
                "validateWithGoogle":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"address.addressLine",
                        script       : """ this.updatedAddressValue = this.vueProviderAddressDataframe_address_addressLine;""",
                        "attr"       :"round right",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                ],
                "googleMap": [
                        "widget"      : "DataframeWidgetVue",
                        dataframe     : ref("vueMapWidgetDataframe"),
                        "attr"        :" @resultData='updateAddressFields'",
                        propPass      :[key:":addressValue", value:"updatedAddressValue"],
                        passValueAsProp : true,
                        "showInMap"   :true,
                        "name"        : "googleMap",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        "height"      :'500px'

                ]
        ]
        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueProviderAddDataframe, "vueProviderAddDataframe_tab_model","vueProviderContactDataframe-tab-id");\n""", "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'],url: ""] ]

        currentFrameLayout = ref("addressDataframeLayout")

    }

    vueProviderGridDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueProviderGridDataframe']
        //dataframeLabelCode = "ContactManagement.Providers"
        dataframeLabelCode = "Providers"
        saveButton = false
        route = true
        initOnPageLoad = true
        doBeforeRefresh = """params['facilityId'] = excon.getFromStore("vueAppNameDataframe", 'facilityId');"""
//        doAfterSave = "excon.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
//        childDataframes = ["vueContactManagementDataframe"]


        addFieldDef =[
                "provider": [
                        widget            : "GridWidgetVue"
                        , name            : "provider"
                        , hidecolumn      : "contractId",
                        onButtonClick   : [
                                [
                                        'actionName': 'Actions', 'buttons': [
                                        [
                                                showAsDialog: true,
                                                tooltip     : [message: "tooltip.grid.edit", internationalization: true],
                                                refDataframe: ref("vueProviderEditDataframe"),
                                                vuetifyIcon : [name: "edit"],
                                                script      : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                        ],
                                        [
                                                deleteButton : true,
                                                tooltip      : [message: "tooltip.grid.delete", internationalization: true],
//                                        ajaxDeleteUrl: "/elintegrostartapp/applicationForm/ajaxExpire",
                                                refDataframe: ref("vueProviderEditDataframe"),
                                                vuetifyIcon  : [name: "delete"],
                                                script       : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                        ]
                                ]
                                ]
                        ]
                        , onClick         : [showAsDialog: true, refDataframe: ref("vueProviderDetailDataframe")]
                        , hql             : """select  person.firstName as Firstname, person.lastName as Lastname, provider.id as Id from Provider provider inner join provider.person person inner join provider.facility facility where facility.id=:facilityId"""
                        , gridWidth       : 420
                        , showGridSearch          : true
                        , "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ]

        ]

        /*dataframeButtons = [ previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", url: ""] ,
                             Submit:[name:"submit", type: "button", script: "vueMedicationsGridDataframeVar.\$router.push('/');this.location.reload();"]
        ]*/
        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vueProviderDetailDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueProviderDetailDataframe']

        //dataframeLabelCode = "ContactManagement.Profider.details"
        dataframeLabelCode = "Provider.Details"

        hql = "select provider.id, provider.providerType, provider.person from Provider provider where provider.id=:id"
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        saveButton = false
        wrapInForm = true
        tab = true
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueContactDetailDataframe", "vueAddressDetailDataframe"]

        addFieldDef = [
                "provider.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"id",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactDetailDataframe")
                ],
                "provider.providerType"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "provider.providerType"
                        ,readOnly        : true
                        ,internationalize: true
                        ,"hql"          : "select pType.id as id, pType.name as name from ProviderType as pType"

                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ]
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']] ]
        currentFrameLayout = ref("vueProviderDetailDataframeLayout")
    }
    vueProviderEditDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueProviderEditDataframe']

        dataframeLabelCode = "Provider.Details"
        hql = "select provider.id, provider.providerType, provider.person from Provider provider where provider.id=:id"
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        wrapInForm = true
        tab = true
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueContactEditDataframe", "vueAddressEditDataframe"]

        addFieldDef = [
                "provider.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"id",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactEditDataframe")
                ],
                "provider.providerType"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "provider.providerType"
                        ,internationalize: true
                        ,"hql"          : "select pType.id as id, pType.name as name from ProviderType as pType"

                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ],

        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']] ]
        currentFrameLayout = ref("vueProviderEditDataframeLayout")
    }


//    #####################################For Vendor #########################################################
    vueVendorAddDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueVendorAddDataframe']

        hql = "select vendor.id, vendor.person, vendor.description from Vendor vendor where vendor.id=:id"

        ajaxSaveUrl = "userManagementForm/saveVendor"

        dataframeLabelCode = "Vendor.Registration"
        //These are values, that overrides the default ones
        childDataframes = ["vueVendorAddressDataframe"]
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
//        saveButtonAttr = " color='light-blue darken-2' dark"
        initOnPageLoad = false
        isGlobal = true
        route = true
        tab = true
        saveButton = true
        wrapInForm=true

        doBeforeSave = """params["facilityId"]= excon.getFromStore("vueAppNameDataframe","facilityId");\nparams["personId"] = excon.getFromStore("vueVendorContactDataframe", 'key');"""
        doAfterSave = """var params = response.params;\n

                         vueVendorAddDataframeVar.\$router.push('/');this.location.reload();"""
        addFieldDef =[
                "vendor.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"person",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueVendorContactDataframe")
                ],

                "vendor.description":[
                        widget: "TextAreaWidgetVue"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],
        ]

        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueVendorAddDataframe, "vueVendorAddDataframe_tab_model","vueVendorAddressDataframe-tab-id");\n""", "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'],url: ""],
//                             Submit:[name:"submit", type: "button", script: "vueEmployeeAddDataframeVar.\$router.push('/');this.location.reload();"]
        ]
        currentFrameLayout = ref("vueVendorDataframeLayout")
    }

    vueVendorContactDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueVendorContactDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.email, person.phone from Person as person where person.id=:id"

        initOnPageLoad = false
        createStore = true
        dataframeLabelCode = "Contact.Information"
//        childDataframes=["vueAddressDataframe"]
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        ajaxSaveUrl = "userManagementForm/saveContact"
        doAfterSave = """

                         excon.saveToStore("vueVendorContactDataframe","key", response.nodeId[0]);
                         excon.saveToStore('vueVendorAddDataframe','vueVendorAddDataframe_tab_model','vueVendorAddressDataframe-tab-id');"""
        addFieldDef =[

                "person.email":[
                        "widget"     : "EmailWidgetVue",
                        "cssGridValues":['xs':'8', 'sm':'8', 'md':'8', 'lg':'8', 'xl':'8']
                ],
                "person.phone":[
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
                ],

                "person.firstName":[
                        "validate":["rule":["v => !!v || 'FirstName is required'", "v => (v && v.length <= 20) || 'FirstName must be less than 20'"]],
                        "cssGridValues":['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
                ],
                "person.lastName":[
                        "validate":["rule":["v => !!v || 'LastName is required'", "v => (v && v.length <= 20) || 'LastName must be less than 20'"]],
                        "cssGridValues":['xs':'6', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6']
                ],
                "person.mainPicture":[
                        "widget"      : "PictureDisplayWidgetVue",
                        "defaultValue":"default_profile.jpg",
                        "aspectRatio" :"2.5",
                        "cssGridValues":['xs':'12', 'sm':'6', 'md':'6', 'lg':'4', 'xl':'4'],
                        "width"       :200,
                        "height"      :200]
        ]

        currentFrameLayout = ref("vueEmbeddedDataframeLayout")

    }
    vueVendorAddressDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueVendorAddressDataframe']
        bean.autowire='byName'

        dataframeLabelCode = "Address.Information"
//		hql = "select address.id, address.addressLine, address.street from Address as address where address.id=:id"
        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip from Address as address where address.id=:id"


//        initOnPageLoad=true //false by default excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_display', false);
        doBeforeSave = "params['personId'] = excon.getFromStore('vueVendorContactDataframe','key');"
        doAfterSave = "excon.saveToStore('vueVendorAddDataframe','vueVendorAddDataframe_tab_model', 'vueVendorAddDataframe-tab-id');"

        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        ajaxSaveUrl = "userManagementForm/saveAddress"
        //These are default values, they are here to demonstrate how to overwrite it with different button combination, if required
        childDataframes = ["vueMapWidgetDataframe"]
        wrapInForm=false
        initOnPageLoad = false
        createStore = true
        addFieldDef = [
                "address.addressLine": [
                        "widget"   : "InputWidgetVue",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                ],

                "address.addressLine2": [
                        "widget"   : "InputWidgetVue",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                ],
                "address.postalZip": [
                        "widget"   : "InputWidgetVue"

                ],
                "validateWithGoogle":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"address.addressLine",
                        script       : """ this.updatedAddressValue = this.vueVendorAddressDataframe_address_addressLine;""",
                        "attr"       :"round right",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                ],
                "googleMap": [
                        "widget"      : "DataframeWidgetVue",
                        dataframe     : ref("vueMapWidgetDataframe"),
                        "attr"        :" @resultData='updateAddressFields'",
                        propPass      :[key:":addressValue", value:"updatedAddressValue"],
                        passValueAsProp : true,
                        "showInMap"   :true,
                        "name"        : "googleMap",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        "height"      :'500px'

                ]
        ]
        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueVendorAddDataframe, "vueVendorAddDataframe_tab_model","vueVendorContactDataframe-tab-id");\n""", "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'], url: ""] ]

        currentFrameLayout = ref("addressDataframeLayout")

    }

    vueVendorGridDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueVendorGridDataframe']
        //dataframeLabelCode = "ContactManagement.Vendors"
        dataframeLabelCode = "Vendors"
        saveButton = false
        route = true
        initOnPageLoad = true
        doBeforeRefresh = """params['facilityId'] = excon.getFromStore("vueAppNameDataframe", 'facilityId');"""
//        doAfterSave = "excon.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
//        childDataframes = ["vueContactManagementDataframe"]


        addFieldDef =[
                "vendor": [
                        widget            : "GridWidgetVue"
                        , name            : "vendor"
                        , hidecolumn      : "contractId",
                        onButtonClick   : [
                                [
                                        'actionName': 'Actions', 'buttons': [
                                        [
                                                showAsDialog: true,
                                                tooltip     : [message: "tooltip.grid.edit", internationalization: true],
                                                refDataframe: ref("vueVendorEditDataframe"),
                                                vuetifyIcon : [name: "edit"],
                                                script      : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                        ],
                                        [
                                                deleteButton : true,
                                                tooltip      : [message: "tooltip.grid.delete", internationalization: true],
//                                        ajaxDeleteUrl: "/elintegrostartapp/applicationForm/ajaxExpire",
                                                refDataframe: ref("vueVendorEditDataframe"),
                                                vuetifyIcon  : [name: "delete"],
                                                script       : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                        ]
                                ]
                                ]
                        ]
                        , onClick         : [showAsDialog: true, refDataframe: ref("vueVendorDetailDataframe")]
                        , hql             : """select  person.firstName as Firstname, person.lastName as Lastname, vendor.id as Id from Vendor vendor inner join vendor.person person inner join vendor.facilities facility where facility.id=:facilityId"""
                        , gridWidth       : 420
                        , showGridSearch          : true
                        , "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ]

        ]

        /*dataframeButtons = [ previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", url: ""] ,
                             Submit:[name:"submit", type: "button", script: "vueMedicationsGridDataframeVar.\$router.push('/');this.location.reload();"]
        ]*/
        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vueVendorDetailDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueVendorDetailDataframe']

        dataframeLabelCode = "Vendor.Details"
        hql = "select vendor.id, vendor.person, vendor.description from Vendor vendor where vendor.id=:id"
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        saveButton = false
        wrapInForm = true
        tab = true
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueContactDetailDataframe", "vueAddressDetailDataframe"]

        addFieldDef = [
                "vendor.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"id",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactDetailDataframe")
                ],

                "vendor.description":[
                        widget: "TextAreaWidgetVue"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],

        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']] ]
        currentFrameLayout = ref("vueVendorDetailDataframeLayout")
    }
    vueVendorEditDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueVendorEditDataframe']

        dataframeLabelCode = "Vendor.Details"
        hql = "select vendor.id, vendor.person, vendor.description from Vendor vendor where vendor.id=:id"
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        wrapInForm = true
        tab = true
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueContactEditDataframe", "vueAddressEditDataframe"]

        addFieldDef = [
                "vendor.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"id",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactEditDataframe")
                ],

                "vendor.description":[
                        widget: "TextAreaWidgetVue"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']],
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']] ]
        currentFrameLayout = ref("vueVendorEditDataframeLayout")
    }

//    #######################################For Client ########################################################
    vueClientDetailDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueClientDetailDataframe']

        dataframeLabelCode = "Client.Details"
        hql = "select client.id, client.person from Client client where client.id=:id"
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        saveButton = false
        wrapInForm = true
        tab = true
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueContactDetailDataframe", "vueAddressDetailDataframe"]

        addFieldDef = [
                "client.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"id",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactDetailDataframe")
                ]

        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']] ]
        currentFrameLayout = ref("vueClientDetailDataframeLayout")
    }
    vueClientEditDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueClientEditDataframe']

        dataframeLabelCode = "Client.Details"
        hql = "select client.id, client.person from Client client where client.id=:id"
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        wrapInForm = true
        tab = true
//        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueContactEditDataframe", "vueAddressEditDataframe"]

        addFieldDef = [
                "client.person":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"id",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactEditDataframe")
                ]
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']] ]
        currentFrameLayout = ref("vueClientEditDataframeLayout")
    }
    vueClientGridDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueClientGridDataframe']
        //dataframeLabelCode = "ContactManagement.Clients"
        dataframeLabelCode = "Clients"

        saveButton = false
        route = true
        initOnPageLoad = true
//        doBeforeRefresh = """params['contactType'] = this.\$route.params.routeId"""
//        doAfterSave = "excon.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
//        childDataframes = ["vueContactManagementDataframe"]

        doBeforeRefresh = """params['facilityId'] = excon.getFromStore("vueAppNameDataframe", 'facilityId');"""
        addFieldDef =[
                "client": [
                        widget            : "GridWidgetVue"
                        , name            : "client"
                        , hidecolumn      : "contractId",
                        onButtonClick   : [
                                [
                                        'actionName': 'Actions', 'buttons': [
                                        [
                                                showAsDialog: true,
                                                tooltip     : [message: "tooltip.grid.edit", internationalization: true],
                                                refDataframe: ref("vueClientEditDataframe"),
                                                vuetifyIcon : [name: "edit"],
                                                script      : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                        ],
                                        [
                                                deleteButton : true,
                                                tooltip      : [message: "tooltip.grid.delete", internationalization: true],
//                                        ajaxDeleteUrl: "/elintegrostartapp/applicationForm/ajaxExpire",
                                                refDataframe: ref("vueClientEditDataframe"),
                                                vuetifyIcon  : [name: "delete"],
                                                script       : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                        ]
                                ]
                                ]
                        ]
                        , onClick         : [showAsDialog: true, refDataframe: ref("vueClientDetailDataframe")]
                        , hql             : """select  person.firstName as Firstname, person.lastName as Lastname, client.id as Id from Client client inner join client.person person inner join client.facility facility where facility.id=:facilityId"""
                        , gridWidth       : 420
                        , showGridSearch  : true
                        , "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ]

        ]

        /*dataframeButtons = [ previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", url: ""] ,
                             Submit:[name:"submit", type: "button", script: "vueMedicationsGridDataframeVar.\$router.push('/');this.location.reload();"]
        ]*/
        currentFrameLayout = ref("defaultDataframeLayout")
    }
}
