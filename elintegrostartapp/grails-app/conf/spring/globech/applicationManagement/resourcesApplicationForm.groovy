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

        ajaxSaveUrl = "${contextPath}/applicationForm/save"
        //These are values, that overrides the default ones
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
        deleteButton = false
        wrapInForm=true
        route = true
//        tab = true
        doAfterSave = """var params = response.params;\n
                         drfExtCont.saveToStore("vueContactDataframe","key", params["key-vueContactDataframe-person-id-id"]);
                         drfExtCont.saveToStore("vueApplicationFormDataframe","vueApplicationFormDataframe_tab_model",'vueAddressDataframe-tab-id');
                         """

        vueStore = ["state":"vueApplicationFormDataframe_tab_model: 'vueApplicationFormDataframe-tab-id',\n"]
        componentsToRegister=["vueAddressDataframe", "vueMedicalRecordDataframe", "vueMedicationsGridDataframe"]

//        doAfterSave = """setTimeout(function(){ this.location.reload();}, 3000);"""
//        route = true
        addFieldDef =[
                /*"app.referredByPerson":[widget: "InputWidgetVue"
                                 , name:"referredByPerson",
                                 "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "app.referredByOrganisation":[widget: "InputWidgetVue"
                                        , name:"referredByOrganisation",
                                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "app.signedBy":[widget: "InputWidgetVue"
                                        , name:"signedBy",
                                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "app.applicationDate":[widget: "DateWidgetVue"
                                        , name:"applicationDate",
                                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
*/
                "app.applicant":[
                        widget: "DataframeWidgetVue"
                        , name:"applicant"
                        , valueMember:"applicant",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        ,"dataframe":ref("vueContactDataframe")
                ]
                /*,"app.medicalRecord":[widget: "DataframeWidgetVue"
                                         , name:"medicalRecord"
                                         , valueMember: "medicalRecord",
                                         "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                                         ,"dataframe":ref("vueMedicalRecordDataframe")
                 ],
                 "address":
                        [
                                "widget" : "DataframeWidgetVue",
                                dataframe: ref("vueAddressDataframe"),
                                "valueMember" : "address",
                                "name": "address",
                                "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],

                        ]
*/
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
//        dataframeButtons = [ previous: [name:"previous", type: "button", script:"this.\$store.state.vueApplicationFormDataframe.vueApplicationFormDataframe_tab_model  = 'vueMedicalRecordDataframe-tab-id'", url: "", "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']] ]
        currentFrameLayout = ref("vueApplicationFormDataframeLayout")
    }

    vueMedicalRecordDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicalRecordDataframe']

        dataframeLabelCode = "Medical.Record"
        hql = "select record.medicareNo, record.physician, record.otherRequirements, record.person from MedicalRecord as record where record.id=:id"

        ajaxSaveUrl = "${contextPath}/applicationForm/saveMedicalRecord"
        initOnPageLoad = false
        //These are values, that overrides the default ones
        deleteButton = false
        wrapInForm=true
        tab = true
//        isGlobal = true
//        createStore = true
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        doBeforeSave = "allParams['vueMedicalRecordDataframe-record-person'] = drfExtCont.getFromStore('vueContactDataframe', 'key'); \nallParams['applicationId'] = drfExtCont.getFromStore('vueApplicationFormDataframe', 'key');"
        doAfterSave = "drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model', 'vueMedicationsGridDataframe-tab-id');\n"


//        doAfterSave = """setTimeout(function(){ this.location.reload();}, 3000);"""
        addFieldDef =[
                "record.person":[
                        widget     : "FKWidgetVue"
                        , name     :"person"
                        , valueMember:"person",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        ,"parent"  :ref("vueContactDataframe")
                ]
        ]

        /*doAfterRefresh = """var currentlocation = this.location.href;
                             this.location.href = currentlocation + 'vueuserprofiledataframe'; """*/
        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""drfExtCont.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueAddressDataframe-tab-id");
                                                                                \n""",flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'], url: ""] ]

        currentFrameLayout = ref("formDataframeLayout")
    }

    vuePrescribedMedicationsDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vuePrescribedMedicationsDataframe']

        hql = "select prescribedMed.id as Id, prescribedMed.medication, prescribedMed.dosage as Dosage, prescribedMed.units as Units, prescribedMed.frequencyUnit as Frequency_Units, prescribedMed.frequency as Frequency, prescribedMed.start as Start, prescribedMed.expiration as Expiration from PrescribedMedication as prescribedMed where prescribedMed.id=:id"

        dataframeLabelCode = "Add.Medications"
        initOnPageLoad = false
        ajaxSaveUrl = "${contextPath}/applicationForm/saveMedications"
        doAfterSave = """drfExtCont.saveToStore('dataframeShowHideMaps', 'vuePrescribedMedicationsDataframe_display', false);\ndrfExtCont.saveToStore('vueMedicationsGridDataframe', 'key', response.nodeId[0]);\n
                          drfExtCont.saveToStore("dataframeBuffer","savedResponseData", responseData);"""
        doBeforeSave = "allParams['medicalRecordId'] = drfExtCont.getFromStore('vueMedicalRecordDataframe', 'key');"

        addFieldDef = [
                "prescribedMed.medication"     : [
                        widget          : "DataframeWidgetVue"
                        ,name           : "medication"
                        ,valueMember    : "id"
                        ,"flexGridValues": ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                        ,"dataframe"    : ref("vueMedicationsDataframe")
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

        currentFrameLayout = ref("prescribedMedicationDataframeLayout")
    }

    vueMedicationsGridDataframe(DataframeVue){ bean ->

        bean.parent = dataFrameSuper
        bean.constructorArgs = ['vueMedicationsGridDataframe']

        hql = "select record.medications from MedicalRecord as record where record.id=:id"

        dataframeLabelCode = "Add.Medications"
//        doAfterSave = "vueMedicationsGridDataframeVar.\$router.push(\"/\");this.location.reload();drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model', 'vueAddressDataframe-tab-id');\n"
        saveButton = false
        initOnPageLoad = false
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        doBeforeRefresh = """allParams['id'] = drfExtCont.getFromStore('vueMedicalRecordDataframe', 'key');"""
        doAfterSave = "drfExtCont.saveToStore('vuePrescribedMedicationsDataframe_display', false);\n"
        componentsToRegister = ["vuePrescribedMedicationsDataframe"]
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
                        ,"flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12']
                ],
                "addMedication":[
                        "widget"     : "ButtonWidgetVue",
                        "insertAfter":"record.medications",
                        onClick      :[showAsDialog: true, refDataframe: ref("vuePrescribedMedicationsDataframe")],
                        script       : """ this.vuePrescribedMedicationsDataframe_display = true;""",
                        "attr"       :"round"
                ],
        ]

        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""drfExtCont.saveToStore("vueApplicationFormDataframe", "vueApplicationFormDataframe_tab_model","vueMedicalRecordDataframe-tab-id");
                                                                                \n""", flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'],url: ""] ,
                             Submit:[name:"submit", type: "button", classNames: "right", flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'],script: "vueMedicationsGridDataframeVar.\$router.push('/');window.location.reload();"]
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
        hql = "select person.firstName, person.lastName, person.id, person.contactEmail, person.phone from Person as person where person.id=:id"

        initOnPageLoad = false
        createStore = true
        saveButton = false
        dataframeLabelCode = "Contact.Information"
//        componentsToRegister=["vueAddressDataframe"]
//        doAfterSave = "drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model','vueMedicalRecordDataframe-tab-id');"
        addFieldDef =[

                "person.contactEmail":[
                        "widget"     : "EmailWidgetVue",
                        "flexGridValues":['xs8', 'sm8', 'md8', 'lg8', 'xl8']
                ],
                "person.phone":[
                        "flexGridValues":['xs12', 'sm12', 'md6', 'lg6', 'xl6']
                ],

                "person.firstName":[
                        "validate":["rule":["v => !!v || 'FirstName is required'", "v => (v && v.length <= 20) || 'FirstName must be less than 20'"]],
                        "flexGridValues":['xs6', 'sm6', 'md6', 'lg6', 'xl6']
                ],
                "person.lastName":[
                        "validate":["rule":["v => !!v || 'LastName is required'", "v => (v && v.length <= 20) || 'LastName must be less than 20'"]],
                        "flexGridValues":['xs6', 'sm6', 'md6', 'lg6', 'xl6']
                ],
                "person.mainPicture":[
                        "widget"      : "PictureDisplayWidgetVue",
                        "defaultValue":"default_profile.jpg",
                        "aspectRatio" :"2.5",
                        "flexGridValues":['xs12', 'sm6', 'md6', 'lg4', 'xl4'],
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


//        initOnPageLoad=true //false by default drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_display', false);
        doBeforeSave = "allParams['personId'] = drfExtCont.getFromStore('vueContactDataframe','key');"
        doAfterSave = "drfExtCont.saveToStore('vueApplicationFormDataframe','vueApplicationFormDataframe_tab_model', 'vueMedicalRecordDataframe-tab-id');"

        componentsToRegister =["vueMapWidgetDataframe"]
        ajaxSaveUrl = "${contextPath}/applicationForm/saveAddress"
        flexGridValuesForSaveButton = ['xs12', 'sm12', 'md6', 'lg6', 'xl6']
        //These are default values, they are here to demonstrate how to overwrite it with different button combination, if required
        deleteButton = false
        insertButton=false
        wrapInForm=false
        initOnPageLoad = false
        createStore = true
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
                        script       : """ this.updatedAddressValue = this.vueAddressDataframe_address_addressLine;""",
                        "attr"       :"round right",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                ],
                "googleMap": [
                        "widget"      : "DataframeWidgetVue",
                        dataframe     : ref("vueMapWidgetDataframe"),
                        "attr"        :" @resultData='updateAddressFields'",
                        propPass      :[key:":addressValue", value:"updatedAddressValue"],
                        passValueAsProp : true,
                        "showInMap"   :true,
                        "name"        : "googleMap",
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "height"      :'500px'

                ]
        ]
        dataframeButtons = [ previous: [name:"previous", type: "button", script:"""Vue.set(this.\$store.state.vueApplicationFormDataframe, "vueApplicationFormDataframe_tab_model","vueApplicationFormDataframe-tab-id");\n""", flexGridValues: ['xs12', 'sm12', 'md6', 'lg6', 'xl6'], url: ""] ]

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
                        "flexGridValues":['xs12', 'sm12', 'md12', 'lg12', 'xl12'],
                        "height"   :'500px'

                ]
        ]

        currentFrameLayout = ref("defaultDataframeLayout")
    }
}
