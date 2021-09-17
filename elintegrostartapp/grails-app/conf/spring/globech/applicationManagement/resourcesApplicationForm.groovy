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
    vueApplicationFormDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueApplicationFormDataframe']

        dataframeLabelCode = "Other.Information"
        hql = "select app.id, app.applicant from Application as app where app.id=:id"

//        app.referredByPerson, app.referredByOrganisation, app.signedBy,   app.applicationDate,
        initOnPageLoad = false

        ajaxSaveUrl = "applicationForm/save"
        //These are values, that overrides the default ones
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
        deleteButton = false
        wrapInForm=true
        route = true
//        tab = true
        doAfterSave = """var params = response.params;\n
                         excon.saveToStore("vueContactDataframe","key", params["key-vueContactDataframe-person-id-id"]);
                         excon.saveToStore("vueApplicationFormDataframe","vueApplicationFormDataframe_tab_model",'vueAddressDataframe-tab-id');
                         """

//        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        childDataframes=["vueAddressDataframe", "vueMedicalRecordDataframe", "vueMedicationsGridDataframe"]

//        doAfterSave = """setTimeout(function(){ this.location.reload();}, 3000);"""
//        route = true
        addFieldDef =[
                /*"app.referredByPerson":[widget: "InputWidgetVue"
                                 , name:"referredByPerson",
                                 "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "app.referredByOrganisation":[widget: "InputWidgetVue"
                                        , name:"referredByOrganisation",
                                       "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "app.signedBy":[widget: "InputWidgetVue"
                                        , name:"signedBy",
                                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "app.applicationDate":[widget: "DateWidgetVue"
                                        , name:"applicationDate",
                                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
*/
                "app.applicant":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"applicant",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe":ref("vueContactDataframe")
                ]
                /*,"app.medicalRecord":[widget: "DataframeWidgetVue"
                                         , name:"medicalRecord"
                                         , valueMember: "medicalRecord",
                                         "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                                         ,"dataframe":ref("vueMedicalRecordDataframe")
                 ],
                 "address":
                        [
                                "widget" : "DataframeWidgetVue",
                                dataframe: ref("vueAddressDataframe"),
                                "valueMember" : "address",
                                "name": "address",
                                "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],

                        ]
*/
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']] ]
        currentFrameLayout = ref("vueApplicationFormDataframeLayout")
    }

    vueMedicalRecordDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicalRecordDataframe']

        dataframeLabelCode = "Medical.Record"
        hql = "select record.medicareNo, record.physician, record.otherRequirements, record.person from MedicalRecord as record where record.id=:id"

        ajaxSaveUrl = "applicationForm/saveMedicalRecord"
        initOnPageLoad = false
        //These are values, that overrides the default ones
        deleteButton = false
        wrapInForm=true
        tab = true
//        isGlobal = true
//        createStore = true
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        doBeforeSave = "params['vueMedicalRecordDataframe-record-person'] = excon.getFromStore('vueContactDataframe', 'key'); \nparams['applicationId'] = excon.getFromStore('vueApplicationFormDataframe', 'key');"
        doAfterSave = "excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model', 'vueMedicationsGridDataframe-tab-id');\n"


//        doAfterSave = """setTimeout(function(){ this.location.reload();}, 3000);"""
        addFieldDef =[
                "record.person":[
                        widget     : "FKWidgetVue"
                        , name     :"person"
                        , valueMember:"person",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"parent"  :ref("vueContactDataframe")
                ]
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueAddressDataframe-tab-id");
                                                                                \n""","cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'], url: ""] ]

        currentFrameLayout = ref("formDataframeLayout")
    }

    vuePrescribedMedicationsDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vuePrescribedMedicationsDataframe']

        hql = "select prescribedMed.id as Id, prescribedMed.medication, prescribedMed.dosage as Dosage, prescribedMed.units as Units, prescribedMed.frequencyUnit as Frequency_Units, prescribedMed.frequency as Frequency, prescribedMed.start as Start, prescribedMed.expiration as Expiration from PrescribedMedication as prescribedMed where prescribedMed.id=:id"

        dataframeLabelCode = "Add.Medications"
        initOnPageLoad = false
        ajaxSaveUrl = "applicationForm/saveMedications"
        doAfterSave = """excon.saveToStore('dataframeShowHideMaps', 'vuePrescribedMedicationsDataframe_display', false);\nexcon.saveToStore('vueMedicationsGridDataframe', 'key', response.nodeId[0]);\n
                          excon.saveToStore("dataframeBuffer","savedResponseData", responseData);"""
        doBeforeSave = "params['medicalRecordId'] = excon.getFromStore('vueMedicalRecordDataframe', 'key');"

        addFieldDef = [
                "prescribedMed.medication"     : [
                        widget          : "DataframeWidgetVue"
                        ,name           : "medication"
                        ,valueMember    : "id"
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                        ,"dataframe"    : ref("vueMedicationsDataframe")
                ],

                "prescribedMed.dosage"     : [
                        name           : "dosage",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
                ],

                "prescribedMed.frequencyUnit"  :[
                        "widget"        : "ComboboxVue"
                        ,"name"         : "frequencyUnit"
                        ,"hql"          : "select pf.id as id, pf.name as name from FrequencyUnit as pf"

                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
                        ,"displayMember": "name"
                        ,"valueMember"  : "id"
                ],

                "prescribedMed.frequency"     : [
                        name           : "frequency",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
                ],
                "prescribedMed.start"          : [
                        widget          : "DateWidgetVue"
                        ,name           : "start",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
                ],
                "prescribedMed.expiration"     : [
                        widget          : "DateWidgetVue"
                        ,name           : "expiration",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
                ]

        ]

        currentFrameLayout = ref("prescribedMedicationDataframeLayout")
    }

    vueMedicationsGridDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicationsGridDataframe']

        hql = "select record.medications from MedicalRecord as record where record.id=:id"

        dataframeLabelCode = "Add.Medications"
//        doAfterSave = "vueMedicationsGridDataframeVar.\$router.push(\"/\");this.location.reload();excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model', 'vueAddressDataframe-tab-id');\n"
        saveButton = false
        initOnPageLoad = false
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        doBeforeRefresh = """params['id'] = excon.getFromStore('vueMedicalRecordDataframe', 'key');"""
        doAfterSave = "excon.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
        childDataframes = ["vuePrescribedMedicationsDataframe"]
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
                "record.medications":  [
                        widget      :"GridWidgetVue"
                        ,name       :"record.medications"
                        ,hidecolumn : "contractId"
                        ,deleteButton:[valueMember: "id", message:"Are you sure?", ajaxDeleteUrl:""]
                        ,showRefreshMethod: true

                        ,hql        : """select medication.id as Id, medication.dosage as Dosage, medication.units as Units, medication.frequencyUnit as Frequency_Unit, medication.frequency as Frequency,medication.start as Start, medication.expiration as Expiration
                                                                  from PrescribedMedication as medication, MedicalRecord record where record.id=:id"""
                        ,gridWidth  :420
                        ,search     :false
                        ,"cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12']
                ],
                "addMedication":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"record.medications",
                        onClick      :[showAsDialog: true, refDataframe: ref("vuePrescribedMedicationsDataframe")],
                        script       : """ this.vuePrescribedMedicationsDataframe_display = true;""",
                        "attr"       :"round"
                ],
        ]

        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""excon.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'],url: ""] ,
                             Submit:[name:"submit", type: "button", classNames: "right", "cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'],script: "vueMedicationsGridDataframeVar.\$router.push('/');window.location.reload();"]
        ]
        currentFrameLayout = ref("formDataframeLayout")
    }

    vueMedicationsDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicationsDataframe']

        hql = "select medication.code, medication.name, medication.description from Medication as medication where medication.id=:id"


        saveButton = false
        initOnPageLoad = false

        currentFrameLayout = ref("vueEmbeddedDataframeLayout")
    }
    vueContactDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueContactDataframe']
        hql = "select person.firstName, person.lastName, person.id, person.email, person.phone from Person as person where person.id=:id"

        initOnPageLoad = false
        createStore = true
        saveButton = false
        dataframeLabelCode = "Contact.Information"
//        childDataframes=["vueAddressDataframe"]
//        doAfterSave = "excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model','vueMedicalRecordDataframe-tab-id');"
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
                        "cssGridValues":['xs':'12', 'sm':'6', 'md':'6', 'lg':'6', 'xl':'6'],
                        "width"       :200,
                        "height"      :200]
        ]

        currentFrameLayout = ref("vueEmbeddedDataframeLayout")

    }

    vueAddressDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueAddressDataframe']
        bean.autowire='byName'

        dataframeLabelCode = "Address.Information"
//		hql = "select address.id, address.addressLine, address.street from Address as address where address.id=:id"
        hql = "select address.addressLine, address.addressLine2, address.id,  address.addressText, address.apartment, address.street, address.cityString, address.countryString, address.postalZip from Address as address where address.id=:id"


//        initOnPageLoad=true //false by default excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_display', false);
        doBeforeSave = "params['personId'] = excon.getFromStore('vueContactDataframe','key');"
        doAfterSave = "excon.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model', 'vueMedicalRecordDataframe-tab-id');"

        childDataframes =["vueMapWidgetDataframe"]
        ajaxSaveUrl = "applicationForm/saveAddress"
        cssGridValuesForSaveButton = ['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6']
        //These are default values, they are here to demonstrate how to overwrite it with different button combination, if required
        deleteButton = false
        insertButton=false
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
                        script       : """ this.updatedAddressValue = this.vueAddressDataframe_address_addressLine;""",
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
        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueApplicationFormDataframe, "vueApplicationFormDataframe_tab_model","vueApplicationFormDataframe-tab-id");\n""","cssGridValues":['xs':'12', 'sm':'12', 'md':'6', 'lg':'6', 'xl':'6'], url: ""] ]

        currentFrameLayout = ref("addressDataframeLayout")

    }

    vueMapWidgetDataframe(DataframeVue){bean ->
        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMapWidgetDataframe']


        initOnPageLoad = false
        saveButton = false
        addFieldDef = [
                "googleMap": [
                        "widget"   : "MapWidgetVue",
                        "showInMap":true,
                        "name"     : "googleMap",
                        "cssGridValues":['xs':'12', 'sm':'12', 'md':'12', 'lg':'12', 'xl':'12'],
                        "height"   :'500px'

                ]
        ]

        currentFrameLayout = ref("defaultDataframeLayout")
    }
}
