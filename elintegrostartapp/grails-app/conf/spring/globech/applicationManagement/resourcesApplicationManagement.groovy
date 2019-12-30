/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package spring.globech.applicationManagement

import com.elintegro.erf.dataframe.vue.DataframeVue

import grails.util.Holders
beans {

    def contextPath = Holders.grailsApplication.config.rootPath

    vueApplicationManagementDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueApplicationManagementDataframe']
//        hql = "select app.firstName, person.lastName, person.id, person.contactEmail, person.phone from Person as person where person.id=:id"

        initOnPageLoad = false
        createStore = true
        saveButton = false
        route = true
        initOnPageLoad = true
        //dataframeLabelCode = "ContactManagement.Applicants"
        dataframeLabelCode = "Applicants"
//        componentsToRegister=["vueAddressDataframe"]
//        doAfterSave = "drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model','vueMedicalRecordDataframe-tab-id');"
//        ,onClick:[showAsDialog: false, refDataframe: ref("vueRegisterDataframe")]
        /*,onButtonClick:[
                ['actionName':'Payment','buttons':[
                        [showAsDialog: true,
                         tooltip:[message: "tooltip.tenant.payment.info", internationalization: true],
                         image:[url:"/elintegro/assets/icons/data-viewer.png"], refDataframe: ref("vueRegisterDataframe"),
                         script:"Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                        ]
                ]
                ]

        ]*/

        addFieldDef = [
                "applications": [
                        widget            : "GridWidgetVue"
                        , name            : "applications"
                        , deleteButton    : [valueMember: "id", message: "Are you sure?", ajaxDeleteUrl: ""]
                        , onClick         : [showAsDialog: true, refDataframe: ref("vueApplicationFormDetailDataframe")]
                        , onButtonClick   : [
                        [
                                'actionName': 'Actions', 'buttons': [
                                [
                                        showAsDialog: true,
                                        tooltip     : [message: "tooltip.grid.edit", internationalization: true],
                                        refDataframe: ref("vueApplicationFormEditDataframe"),
                                        vuetifyIcon : [name: "edit"],
                                        script      : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                ],
                                [
                                        deleteButton : true,
                                        tooltip      : [message: "tooltip.grid.delete", internationalization: true],
//                                        ajaxDeleteUrl: "/elintegrostartapp/applicationForm/ajaxExpire",
                                        refDataframe: ref("vueApplicationFormEditDataframe"),
                                        vuetifyIcon  : [name: "delete"],
                                        script       : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                ]
                        ]
                        ]

                ]

                        , hql             : """select  apcant.firstName as Firstname, apcant.lastName as Lastname,status.name as Status, app.id as Id from Application app inner join app.applicant apcant inner join app.status status"""
                        , gridWidth       : 420
                        , showGridSearch  : true
                        , "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]
        ]

        currentFrameLayout = ref("defaultDataframeLayout")


    }

    vueApplicationFormDetailDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueApplicationFormDetailDataframe']

        dataframeLabelCode = "Application.Details"
//        hql = "select app.id as Id, apcant.firstName as Firstname, apcant.lastName as Lastname, apcant.phone as Phone_Number, apcant.contactEmail as Email from Application app inner join app.applicant apcant where app.id=:id"
        hql = "select app.id, app.createTime, app.applicant, app.medicalRecord from Application as app where app.id=:id"
//        app.referredByPerson, app.referredByOrganisation, app.signedBy,   app.applicationDate,
        initOnPageLoad = true

        //These are values, that overrides the default ones
        deleteButton = false
        saveButton = false
        wrapInForm = true
        tab = true
//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        componentsToRegister=["vueContactDetailDataframe", "vueMedicationsGridDetailDataframe"]

        addFieldDef = [
                "app.createTime"     : [
                        name            : "createTime",
                        attr            : " left",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "app.applicant"    : [
                        widget          : "DataframeWidgetVue"
                        , name          : "applicant"
                        , valueMember   : "id",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        , "dataframe"   : ref("vueContactDetailDataframe")
                ],
                "app.medicalRecord": [
                        widget          : "DataframeWidgetVue"
                        , name          : "medicalRecord"
                        , valueMember   : "id",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        , "dataframe"   : ref("vueMedicalRecordDetailDataframe")
                ]


        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']] ]
        currentFrameLayout = ref("vueFormDetailDataframeLayout")
    }

    vueAddressDetailDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAddressDetailDataframe']
        bean.autowire='byName'

        dataframeLabelCode = "Address.Information"
//        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip from Address as address where address.id=:id"
        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip from Person person inner join person.mainAddress as address where person.id=:personId"


        componentsToRegister =["vueMapWidgetDataframe"]
        //These are default values, they are here to demonstrate how to overwrite it with different button combination, if required
        saveButton = false
        wrapInForm=false
        initOnPageLoad = false
        putFillInitDataMethod = true
        doBeforeRefresh = "allParams['personId'] = drfExtCont.getFromStore('vueContactDetailDataframe', 'key');"
        addFieldDef = [
                "address.addressLine": [
                        "widget"   : "InputWidgetVue",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],

                ],

                "address.addressLine2": [
                        "widget"   : "InputWidgetVue",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],

                ],
                "address.postalZip": [
                        "widget"   : "InputWidgetVue"

                ]/*,

                "googleMap": [
                        "widget"      : "DataframeWidgetVue",
                        dataframe     : ref("vueMapWidgetDataframe"),
                        "attr"        :" @resultData='updateAddressFields'",
                        propPass      :[key:":addressValue", value:"updatedAddressValue"],
                        valueMember   : "id",
                        passValueAsProp : true,
                        "showInMap"   :true,
                        "name"        : "googleMap",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "height"      :'500px'

                ]*/
        ]

        currentFrameLayout = ref("addressDetailDataframeLayout")

    }
    vueMedicalRecordDetailDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicalRecordDetailDataframe']

        dataframeLabelCode = "Medical.Record"
        hql = "select record.medicareNo, record.physician, record.otherRequirements from MedicalRecord as record where record.id=:id"

        initOnPageLoad = true
        //These are values, that overrides the default ones
        wrapInForm = true
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doBeforeRefresh = "allParams['recordId'] = drfExtCont.getFromStore('vueMedicalRecordDetailDataframe', 'key');"
        saveButton = false
        addFieldDef = [:

                       /*"record.medications": [widget            : "GridWidgetVue"
                                              , name            : "record.medications"
                                              , hidecolumn      : "contractId"

                                              , onClick         : [showAsDialog: true, refDataframe: ref("vuePrescribedMedicationsDetailDataframe")]
                                              , hql             : """select medication.id as Id, medication.dosage as Dosage, medication.units as Units, medication.frequency as Frequency,medication.start as Start, medication.expiration as Expiration
                                                                         from MedicalRecord record inner join record.medications medication where record.id=:id"""
                                              , gridWidth       : 420
                                              , search          : false
                                              , "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                       ]*/
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/

        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vueMedicationsGridDetailDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicationsGridDetailDataframe']

//        hql = "select record.medications from MedicalRecord as record where record.id=:id"

        dataframeLabelCode = "Medications"
//        doAfterSave = "vueMedicationsGridDataframeVar.\$router.push(\"/\");this.location.reload();drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model', 'vueAddressDataframe-tab-id');\n"
        saveButton = false
        initOnPageLoad = false
        putFillInitDataMethod = true
        doBeforeRefresh = """allParams['id'] = drfExtCont.getFromStore('vueMedicalRecordDetailDataframe', 'key');"""
//        doAfterSave = "drfExtCont.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
        componentsToRegister = ["vuePrescribedMedicationsDetailDataframe"]
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
                "record.medications": [
                        widget            : "GridWidgetVue"
                        , name            : "record.medications"
                        , hidecolumn      : "contractId"

                        , onClick         : [showAsDialog: true, refDataframe: ref("vuePrescribedMedicationsDetailDataframe")]
                        , hql             : """select medication.id as Id, medication.dosage as Dosage, medication.frequency as Frequency,medication.start as Start, medication.expiration as Expiration
                                                                  from MedicalRecord record inner join record.medications medication where record.id=:id"""
                        , gridWidth       : 420
                        , search          : false
                        , "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]

        ]

        /*dataframeButtons = [ previous: [name:"previous", type: "button", script:"""drfExtCont.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", url: ""] ,
                             Submit:[name:"submit", type: "button", script: "vueMedicationsGridDataframeVar.\$router.push('/');this.location.reload();"]
        ]*/
        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vuePrescribedMedicationsDetailDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vuePrescribedMedicationsDetailDataframe']

        hql = "select prescribedMed.id as Id, prescribedMed.medication, prescribedMed.dosage as Dosage, prescribedMed.units as Units, prescribedMed.frequencyUnit as Frequency_Units, prescribedMed.frequency as Frequency, prescribedMed.start as Start, prescribedMed.expiration as Expiration from PrescribedMedication as prescribedMed where prescribedMed.id=:id"

        dataframeLabelCode = "Medication.Detail"
        initOnPageLoad = true
        saveButton = false
//        ajaxSaveUrl = "/elintegrostartapp/applicationForm/saveMedications"
        /* doAfterSave = """drfExtCont.saveToStore('dataframeShowHideMaps', 'vuePrescribedMedicationsDataframe_display', false);\ndrfExtCont.saveToStore('vueMedicationsGridDataframe', 'key', response.nodeId[0]);\n
                           drfExtCont.saveToStore("dataframeBuffer","savedResponseData", responseData);"""
         doBeforeSave = "allParams['medicalRecordId'] = drfExtCont.getFromStore('vueMedicalRecordDataframe', 'key');"*/

        addFieldDef = [
                "prescribedMed.medication"     : [
                        widget          : "DataframeWidgetVue"
                        ,name           : "medication"
                        ,valueMember    : "id"
                        ,"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        ,"dataframe"    : ref("vueMedicationsDetailDataframe")
                ],

                "prescribedMed.dosage"     : [
                        name           : "dosage",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],

                "prescribedMed.frequencyUnit"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "frequencyUnit"
                        ,"hql"          : "select pf.id as id, pf.name as name from FrequencyUnit as pf"

                        ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ],

                "prescribedMed.units"     : [
                        name           : "frequency",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "prescribedMed.frequency"     : [
                        name           : "frequency",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "prescribedMed.start"          : [
                        widget          : "DateWidgetVue"
                        ,name           : "start",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "prescribedMed.expiration"     : [
                        widget          : "DateWidgetVue"
                        ,name           : "expiration",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ]

        ]

        currentFrameLayout = ref("defaultDialogBoxLayout")
    }

    vueMedicationsDetailDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicationsDetailDataframe']

        hql = "select medication.code, medication.name, medication.description from Medication as medication where medication.id=:id"


        saveButton = false
        initOnPageLoad = true

        currentFrameLayout = ref("vueEmbeddedDataframeLayout")
    }

    vueApplicationFormEditDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueApplicationFormEditDataframe']

        dataframeLabelCode = "Edit.Application"
//        hql = "select app.id, app.applicant from Application as app where app.id=:id"
        hql = "select app.id, app.createTime, app.applicant, app.medicalRecord from Application as app where app.id=:id"
//        app.referredByPerson, app.referredByOrganisation, app.signedBy,   app.applicationDate,

        ajaxSaveUrl = "${contextPath}/applicationForm/save"
        //These are values, that overrides the default ones
//        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        deleteButton = false
        wrapInForm = true
        tab = true

        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        componentsToRegister=["vueContactFormEditDataframe","vueMedicationsGridEditDataframe"]

//        doAfterSave = """setTimeout(function(){ this.location.reload();}, 3000);"""
//        route = true
        addFieldDef = [
                "app.createTime"     : [
                        name            : "createTime",
                        attr            : " left",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "app.applicant"    : [
                        widget          : "DataframeWidgetVue"
                        , name          : "applicant"
                        , valueMember   : "id",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        , "dataframe"   : ref("vueContactFormEditDataframe")
                ],
                "app.medicalRecord": [
                        widget          : "DataframeWidgetVue"
                        , name          : "medicalRecord"
                        , valueMember   : "id",
                        "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        , "dataframe"   : ref("vueMedicalRecordEditDataframe")
                ]


        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """ expire:[name:"expire", type: "button", url: "/elintegrostartapp/applicationForm/expire"]*/
        dataframeButtons = [
                makeClient: [name:"makeClient", type: "button", doBeforeAjax:"""allParams["facilityId"]= drfExtCont.getFromStore("vueAppNameDataframe","facilityId");\nallParams["id"] = drfExtCont.getFromStore('vueApplicationFormEditDataframe', 'key');""", flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'], url: "/elintegrostartapp/applicationForm/makeClient"],


        ]
        currentFrameLayout = ref("vueFormEditDataframeLayout")
    }
    vueContactFormEditDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactFormEditDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.contactEmail, person.phone from Person as person where person.id=:id"

        initOnPageLoad = true
        saveButton = false
//        dataframeLabelCode = "Contact Information"
//        componentsToRegister=["vueAddressDataframe"]
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
//        doAfterSave = "drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model','vueMedicalRecordDataframe-tab-id');"
        addFieldDef = [

                "person.contactEmail": [
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

        currentFrameLayout = ref("vueContactFormDataframeLayout")

    }
    vueContactDetailDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactDetailDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.contactEmail, person.phone from Person as person where person.id=:id"

        initOnPageLoad = true
        saveButton = false
//        dataframeLabelCode = "Contact.Information"
//        componentsToRegister=["vueAddressDataframe"]
//        doAfterSave = "drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model','vueMedicalRecordDataframe-tab-id');"
        addFieldDef = [

                "person.contactEmail": [
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
    vueContactEditDataframe(DataframeVue) { bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactEditDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.contactEmail, person.phone from Person as person where person.id=:id"

        initOnPageLoad = true
        saveButton = true
//        dataframeLabelCode = "Contact.Information"
//        componentsToRegister=["vueAddressDataframe"]
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
//        doAfterSave = "drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model','vueMedicalRecordDataframe-tab-id');"
        addFieldDef = [

                "person.contactEmail": [
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

        currentFrameLayout = ref("vueContactEditDataframeLayout")

    }
    vueAddressEditDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAddressEditDataframe']
        bean.autowire='byName'

        dataframeLabelCode = "Address.Information"
//		hql = "select address.id, address.addressLine, address.street from Address as address where address.id=:id"
        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip from Person person inner join person.mainAddress as address where person.id=:personId"


//        initOnPageLoad=true //false by default drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_display', false);
        doBeforeSave = "allParams['personId'] = drfExtCont.getFromStore('vueContactDataframe','key');"

        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        doBeforeRefresh = "allParams['personId'] = drfExtCont.getFromStore('vueContactEditDataframe', 'key');"
        componentsToRegister =["vueMapWidgetDataframe"]
        //These are default values, they are here to demonstrate how to overwrite it with different button combination, if required
        deleteButton = false
        wrapInForm=false
        initOnPageLoad = false
        putFillInitDataMethod = true
        addFieldDef = [
                "address.addressLine": [
                        "widget"   : "InputWidgetVue",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],

                ],

                "address.addressLine2": [
                        "widget"   : "InputWidgetVue",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],

                ],
                "address.postalZip": [
                        "widget"   : "InputWidgetVue"

                ],
                "validateWithGoogle":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"address.addressLine",
                        script       : """ this.updatedAddressValue = this.vueAddressEditDataframe_address_addressLine;""",
                        "attr"       :"round right",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                ],
                "googleMap": [
                        "widget"      : "DataframeWidgetVue",
                        dataframe     : ref("vueMapWidgetDataframe"),
                        "attr"        :" @resultData='updateAddressFields'",
                        propPass      :[key:":addressValue", value:"updatedAddressValue"],
                        valueMember   :"id",
                        passValueAsProp : true,
                        "showInMap"   :true,
                        "name"        : "googleMap",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "height"      :'500px'

                ]
        ]

        currentFrameLayout = ref("addressEditDataframeLayout")

    }
    vueMedicalRecordEditDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicalRecordEditDataframe']

        dataframeLabelCode = "Medical.Record"
        hql = "select record.medicareNo, record.physician, record.otherRequirements from MedicalRecord as record where record.id=:id"

        initOnPageLoad = true
        //These are values, that overrides the default ones
        wrapInForm = true

        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        saveButton = true
        addFieldDef = [

                :
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/

        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vueMedicationsGridEditDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicationsGridEditDataframe']

//        hql = "select record.medications from MedicalRecord as record where record.id=:id"

        dataframeLabelCode = "Medications"
//        doAfterSave = "vueMedicationsGridDataframeVar.\$router.push(\"/\");this.location.reload();drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model', 'vueAddressDataframe-tab-id');\n"
        saveButton = false
        initOnPageLoad = false
        putFillInitDataMethod = true
        doBeforeRefresh = """allParams['id'] = drfExtCont.getFromStore('vueMedicalRecordEditDataframe', 'key');"""
//        doAfterSave = "drfExtCont.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
        componentsToRegister = ["vuePrescribedMedicationsEditDataframe"]
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
                "record.medications": [
                        widget            : "GridWidgetVue"
                        , name            : "record.medications"
                        , hidecolumn      : "contractId"
                        , onButtonClick   : [
                        [
                                'actionName': 'Actions', 'buttons': [
                                [
                                        showAsDialog: true,
                                        tooltip     : [message: "tooltip.grid.edit", internationalization: true],
                                        refDataframe: ref("vuePrescribedMedicationsEditDataframe"),
                                        vuetifyIcon : [name: "edit"],
                                        script      : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                ],
                                [
                                        deleteButton : true,
                                        tooltip      : [message: "tooltip.grid.delete", internationalization: true],
//                                        ajaxDeleteUrl: "/elintegrostartapp/applicationForm/ajaxExpire",
                                        refDataframe: ref("vuePrescribedMedicationsEditDataframe"),
                                        vuetifyIcon  : [name: "delete"],
                                        script       : "Vue.set(this.\$store.state.vueUnitDataframe.unit_tenants_grid, 'key', data.id);"
                                ]
                        ]
                        ]
                ]
                        , hql             : """select medication.id as Id, medication.dosage as Dosage, medication.frequency as Frequency,medication.start as Start, medication.expiration as Expiration
                                                                  from MedicalRecord record inner join record.medications medication where record.id=:id"""
                        , gridWidth       : 420
                        , search          : false
                        , "flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ]/*,
                "addMedication":[
                        "widget": "ButtonWidgetVue",
                        "insertAfter":"record.medications",
                        onClick:[showAsDialog: true, refDataframe: ref("vuePrescribedMedicationsEditDataframe")],
                        script: """ this.vuePrescribedMedicationsDataframe_display = true;""",
                        "attr":"round"
                ],*/
        ]

        /*dataframeButtons = [ previous: [name:"previous", type: "button", script:"""drfExtCont.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", url: ""] ,
                             Submit:[name:"submit", type: "button", script: "vueMedicationsGridDataframeVar.\$router.push('/');this.location.reload();"]
        ]*/
        currentFrameLayout = ref("defaultDataframeLayout")
    }

    vuePrescribedMedicationsEditDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vuePrescribedMedicationsEditDataframe']

        hql = "select prescribedMed.id as Id, prescribedMed.medication, prescribedMed.dosage as Dosage, prescribedMed.units as Units, prescribedMed.frequencyUnit as Frequency_Units, prescribedMed.frequency as Frequency, prescribedMed.start as Start, prescribedMed.expiration as Expiration from PrescribedMedication as prescribedMed where prescribedMed.id=:id"

        dataframeLabelCode = "Edit.Medication"
        initOnPageLoad = true
        saveButton = true
        ajaxSaveUrl = "${contextPath}/applicationForm/saveMedications"
        doAfterSave = """drfExtCont.saveToStore('dataframeShowHideMaps', 'vuePrescribedMedicationsEditDataframe_display', false);\ndrfExtCont.saveToStore('vueMedicationsGridEditDataframe', 'key', response.nodeId[0]);\n
                          drfExtCont.saveToStore("dataframeBuffer","savedResponseData", responseData);"""
        doBeforeSave = "allParams['medicalRecordId'] = drfExtCont.getFromStore('vueMedicalRecordEditDataframe', 'key');"

         addFieldDef = [
                "prescribedMed.medication"     : [
                        widget          : "DataframeWidgetVue"
                        ,name           : "medication"
                        ,valueMember    : "id"
                        ,"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        ,"dataframe"    : ref("vueMedicationsEditDataframe")
                ],

                "prescribedMed.dosage"     : [
                        name           : "dosage",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],

                "prescribedMed.frequencyUnit"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "frequencyUnit"
                        ,"hql"          : "select pf.id as id, pf.name as name from FrequencyUnit as pf"

                        ,"flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ],

                "prescribedMed.units"     : [
                        name           : "frequency",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "prescribedMed.frequency"     : [
                        name           : "frequency",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "prescribedMed.start"          : [
                        widget          : "DateWidgetVue"
                        ,name           : "start",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],
                "prescribedMed.expiration"     : [
                        widget          : "DateWidgetVue"
                        ,name           : "expiration",
                        "flexGridValues": ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ]

        ]

        currentFrameLayout = ref("defaultDialogBoxLayout")
    }

    vueMedicationsEditDataframe(DataframeVue) { bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicationsEditDataframe']

        hql = "select medication.code, medication.name, medication.description from Medication as medication where medication.id=:id"


        saveButton = false
        initOnPageLoad = true

        currentFrameLayout = ref("vueEmbeddedDataframeLayout")
    }
}