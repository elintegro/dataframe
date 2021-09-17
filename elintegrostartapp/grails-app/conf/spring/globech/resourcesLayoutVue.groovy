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

import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue

beans {


    /*Layout test for component and router based layout design*/


    sectionLayout3(RowLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>
                                   
                                     <navigationlayout/>
                                     <vuealertmsgdataframe/>
                                     <midsectionlayout/>
                                     <footerlayout/>
                               </div>"""
        childLayouts = ["navigationLayout", "midSectionLayout", "footerLayout"]
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }
    /*<v-card color="light-blue darken-2" text><v-layout row align-start justify-end>
    <v-flex grow> <vuetoolbardataframe/> </v-flex>
            <v-flex shrink> <vueafterloggedindataframe v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
    <vueloginnavigation v-else/></v-flex>
        </v-layout></v-card>*/
    navigationLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """

<v-toolbar color="light-blue darken-2" dark  tabs style="z-index:99;">
                                   <v-toolbar-side-icon></v-toolbar-side-icon>
                                   <v-toolbar-title class="display-1 thin  amber--text text--lighten-1" dark> logo  </v-toolbar-title>
                                 
                                   <v-spacer></v-spacer>
                                   <vueNavigationButtonDataframe/>
                                   <vueElintegroNavigationLogoDataframe/>
                                   <vueInitDataframe/>
                               </v-toolbar>
                   
                               """

        isGlobal = true
    }

    vueToolbarDataframeLayout(RowLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-toolbar-items dark class="hidden-xs-and-down">
                                    <v-card v-if="this.\$store.state.vueInitDataframe.loggedIn" color="light-blue darken-2 ma-2" text>[BUTTON_SCRIPT][REF_FIELD]
                                                 </v-card><v-spacer style=" width: 100px; "></v-spacer>
                                                 <vueafterloggedindataframe  v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
                                                 <vueloginnavigation v-else/>                                  
                                </v-toolbar-items>"""
    }

    loginLogoutNavigationLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card style="height:inherit;" color="light-blue darken-2" text><v-container fill-height grid-list-xl><v-layout row align-center justify-center>[BUTTON_SCRIPT][REF_FIELD]</v-layout></v-container></v-card>"""
    }

/*<v-tabs slot="extension" color="transparent" fixed-tabs slider-color="yellow">
      <v-tab ripple href="#vueOwnerTreeDataframe-tab-id">My Properties</v-tab><v-tab ripple href="#vueUserProfileDataframe-tab-id">Financial Data</v-tab><v-tab ripple href="#vueUserProfileDataframe-tab-id">My Profile</v-tab></v-tabs>*/


    midSectionLayout(RowLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-content class="body-1 text-capitalize"><v-container fluid><v-layout row align-start justify-start>
                                    <columnspacerlayout/>
                                      <mainsectionlayout />
                                     <columnspacerlayout/>
                                     </v-layout></v-container></v-content>"""
        childLayouts = ["columnSpacerLayout", "mainSectionLayout"]
    }
    mainSectionLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-layout wrap row align-start justify-start><v-content><v-container grid-list-md><v-layout row wrap justify-center>
                                                                <v-flex xs2 order-lg1></v-flex>
                                                                     <v-flex lg8 md8 xs8 order-lg2>
                                                                     <router-view :key="\$route.fullPath"></router-view>
                                                                     <mainpagemessagelayout v-if="!this.\$store.state.vueInitDataframe.loggedIn"/>
                                        </v-flex>
                                        <v-flex xs2 order-lg3></v-flex>
                                        </v-layout></v-container></v-content></v-layout>"""
//        childDataframes = ["vueApplicationFormDataframe"]
        childLayouts = ["mainPageMessageLayout"]
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }
//            <router-view name='vueapplicationformdataframe'/>
//
//    <vueapplicationformdataframe v-if="this.\$store.state.vueToolbarDataframe.newApplication_display == true"/>
    mainPageMessageLayout(RowLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-container fluid><v-layout wrap column>
                                <span align="center" class="headline">Welcome to Elintegro Start App. </br>Please Register or Login, in order to start working with the application. Or, start building on top of it, to create your next masterpiece.</span>
                                </v-layout></v-container>"""
    }
    myPropertySectionLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-layout wrap row align-start justify-start><v-flex xs12 sm12 md12 lg3 xl3><v-container fluid><v-layout wrap column><vueownertreedataframe/></v-layout></v-container></v-flex><v-flex xs12 sm12 md12 lg7 xl7><v-content><v-container grid-list-md><v-layout row wrap justify-center>
                                        <router-view name='vuepropertydataframe'/>
                                        <router-view :key="\$route.fullPath"></router-view>
                                         <router-view name='vueownerdataframe'/>
                                        </v-layout></v-container></v-content></v-flex></v-layout>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }


    sidebarLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-navigation-drawer permanent fixed app v-model="drawer">Elintegro Start App</v-navigation-drawer>"""
    }


    vueUserProfileDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round width='fit-content'><v-toolbar dark color="light-blue darken-2"><v-toolbar-title class="white--text">My Profile</v-toolbar-title><v-spacer></v-spacer></v-toolbar>[vueUserProfileDataframe]<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[ALL_OTHER_BUTTONS][saveButton]</v-layout></v-container></v-card-actions></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }


    vueAfterLoggedinDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card color="light-blue darken-2 mt-2" text id="vueAfterLoggedinDataframe-id" @click="vueProfileMenuDataframe_display = true;" ><v-layout row align-center justify-center> [person.mainPicture][REF_FIELD]</v-layout></v-card>"""
    }

    vueProfileMenuDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card color="default" text id="vueProfileMenuDataframe-id" style="overflow: hidden;"><v-form  ref='vueProfileMenuDataframe_form'><v-container class="pa-2"><v-layout wrap><v-flex xs12 sm12 md12 lg12 xl12><v-subheader class="subheading">Hello, {{vueProfileMenuDataframe_person_fullName}}</v-subheader></v-flex>[person.mainPicture]</v-layout></v-container></v-form>[BUTTON_SCRIPT]</v-card>"""
    }


    vueLoginDataframeLayout(RowLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class="rounded-card" style="width:320px; border-radius:10px;"><v-toolbar dark color="light-blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar>[DATAFRAME_SCRIPT]<v-layout align-content-space-around row wrap align-center>[BUTTON_SCRIPT]</v-layout></v-card></v-flex>"""
    }

    vueRegisterDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card'><v-toolbar dark color="light-blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }

    vueUserManagementMenuDataframeLayout(RowLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card color="default" text><v-layout row align-center justify-center>[BUTTON_SCRIPT][REF_FIELD]</v-layout></v-card>"""

    }

    vueRegisterMenuDataframeLayout(RowLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card color="default" text><v-layout row align-center justify-center>[BUTTON_SCRIPT][REF_FIELD]</v-layout></v-card>"""

    }

    vueApplicationFormDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueApplicationFormDataframe_tab_model">
                                    <v-tab ripple href="#vueApplicationFormDataframe-tab-id">Contact Information</v-tab>
                                    <v-tab ripple href="#vueAddressDataframe-tab-id">Address Information</v-tab>
                                    <v-tab ripple href="#vueMedicalRecordDataframe-tab-id">Medical Record</v-tab>
                                    <v-tab ripple href="#vueMedicationsGridDataframe-tab-id">Medications</v-tab>
                               </v-tabs>
                               <v-tabs-items v-model="vueApplicationFormDataframe_tab_model">
                               <v-tab-item value="vueApplicationFormDataframe-tab-id">
                                        <v-form  ref='vueApplicationFormDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactDataframe]</v-layout></v-container>
                                         </v-form>[BUTTON_SCRIPT]
                                   </v-tab-item>
                                   <v-tab-item value="vueAddressDataframe-tab-id"><vueaddressdataframe/></v-tab-item>
                                    <v-tab-item value="vueMedicalRecordDataframe-tab-id"><vuemedicalrecorddataframe/></v-tab-item>
                                   <v-tab-item value="vueMedicationsGridDataframe-tab-id"><vuemedicationsgriddataframe/></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }


    vueContactDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_LABEL]<v-container><v-layout row wrap justify-end align-end> [DATAFRAME_SCRIPT]<div>[address]</div>[BUTTON_SCRIPT]</v-layout></v-container></div>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }

    prescribedMedicationDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class="rounded-card" style="width:500px; border-radius:10px;"><v-toolbar dark color="light-blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar>[vueMedicationsDataframe][DATAFRAME_SCRIPT]<v-layout ma-2 align-content-space-around row wrap align-center>[BUTTON_SCRIPT]</v-layout></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'4', 'xl':'4']
    }


    addressDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>[DATAFRAME_LABEL][DATAFRAME_SCRIPT]<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[previous][ALL_OTHER_BUTTONS][googleMap]</v-layout></v-container></v-card-actions></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'4', 'xl':'4']
    }
    vueFormDetailDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueApplicationFormDetailDataframe_tab_model">
                                    <v-tab ripple href="#vueApplicationFormDetailDataframe-tab-id">Contact Information</v-tab>
                                    <v-tab ripple href="#vueMedicalRecordDetailDataframe-tab-id">Medical Record</v-tab>
                                    <v-tab ripple href="#vueMedicationsGridDetailDataframe-tab-id">Medications</v-tab>
                                    <v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueApplicationFormDetailDataframe_tab_model">
                               <v-tab-item value="vueApplicationFormDetailDataframe-tab-id">
                                        <v-form  ref='vueApplicationFormDetailDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[app.createTime][vueContactDetailDataframe]</v-layout></v-container>
                                         </v-form>
                                   </v-tab-item>
                                    <v-tab-item value="vueMedicalRecordDetailDataframe-tab-id">[vueMedicalRecordDetailDataframe]</v-tab-item>
                                   <v-tab-item value="vueMedicationsGridDetailDataframe-tab-id"><vuemedicationsgriddetaildataframe/></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }


    vueFormEditDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueApplicationFormEditDataframe_tab_model">
                                    <v-tab ripple href="#vueApplicationFormEditDataframe-tab-id">Contact Information</v-tab>
                                    <v-tab ripple href="#vueMedicalRecordEditDataframe-tab-id">Medical Record</v-tab>
                                    <v-tab ripple href="#vueMedicationsGridEditDataframe-tab-id">Medications</v-tab>
                                    <v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueApplicationFormEditDataframe_tab_model">
                               <v-tab-item value="vueApplicationFormEditDataframe-tab-id">
                                        <v-form  ref='vueApplicationFormEditDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[app.createTime][vueContactFormEditDataframe]</v-layout></v-container>
                                         </v-form><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[ALL_OTHER_BUTTONS][saveButton]</v-layout></v-container></v-card-actions>
                                   </v-tab-item>
                                    <v-tab-item value="vueMedicalRecordEditDataframe-tab-id">[vueMedicalRecordEditDataframe]</v-tab-item>
                                   <v-tab-item value="vueMedicationsGridEditDataframe-tab-id"><vuemedicationsgrideditdataframe/></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
    vueContactFormDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_LABEL]<v-layout row wrap justify-center><v-flex xs12 sm12 md8 lg8 xl8><v-layout row wrap justify-left> [ALL_OTHER_FIELDS]</v-layout></v-flex><v-flex xs12 sm12 md4 lg4 xl4>[person.mainPicture]</v-flex></v-layout></div>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }
    vueContactDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_LABEL]<v-layout row wrap justify-center><v-flex xs12 sm12 md8 lg8 xl8><v-layout row wrap justify-left> [ALL_OTHER_FIELDS]</v-layout></v-flex><v-flex xs12 sm12 md4 lg4 xl4>[person.mainPicture]</v-flex>[ALL_OTHER_BUTTONS]</v-layout></div>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }

    vueContactDetailDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_LABEL]<v-layout row wrap justify-center><v-flex xs12 sm12 md8 lg8 xl8><v-layout row wrap justify-left> [ALL_OTHER_FIELDS]</v-layout></v-flex><v-flex xs12 sm12 md4 lg4 xl4>[person.mainPicture]</v-flex></v-layout></div>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }
    vueContactEditDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_LABEL]<v-layout row wrap justify-center><v-flex xs12 sm12 md8 lg8 xl8><v-form ref="vueContactEditDataframe_form"><v-layout row wrap justify-left> [ALL_OTHER_FIELDS]</v-layout></v-form></v-flex><v-flex xs12 sm12 md4 lg4 xl4>[person.mainPicture]</v-flex>[ALL_OTHER_BUTTONS]</v-layout></div>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }

    vueContactDetailDataframeLayout1(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card round class='rounded-card'><v-toolbar dark color="light-blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title><v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                   </v-btn><span>Close</span></v-tooltip></v-toolbar>
                                   <v-container grid-list-xl fluid><v-layout row wrap justify-center><v-flex xs12 sm12 md8 lg8 xl8><v-layout row wrap justify-left> [ALL_OTHER_FIELDS]</v-layout></v-flex><v-flex xs12 sm12 md4 lg4 xl4>[person.mainPicture]</v-flex>[BUTTON_SCRIPT]</v-layout></v-container></v-card>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }
    addressDetailDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>[DATAFRAME_LABEL][DATAFRAME_SCRIPT]<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap></v-layout></v-container></v-card-actions></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'4', 'xl':'4']
    }
    addressEditDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>[DATAFRAME_LABEL][DATAFRAME_SCRIPT]<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[ALL_OTHER_BUTTONS][googleMap]</v-layout></v-container></v-card-actions></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'4', 'xl':'4']
    }
    vueEmployeeDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueEmployeeAddDataframe_tab_model">
                                    <v-tab ripple href="#vueEmployeeContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueEmployeeAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueEmployeeAddDataframe-tab-id">Employee Info</v-tab>
                                 </v-tabs>
                               <v-tabs-items v-model="vueEmployeeAddDataframe_tab_model">
                               <v-tab-item value="vueEmployeeContactDataframe-tab-id">
                                     [vueEmployeeContactDataframe]
                                   </v-tab-item>
                                    <v-tab-item value="vueEmployeeAddressDataframe-tab-id"><vueemployeeaddressdataframe/></v-tab-item>
                                    <v-tab-item value="vueEmployeeAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout column wrap  justify-center>
                                    
                                        <v-form  ref='vueEmployeeAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                     <v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[previous][ALL_OTHER_BUTTONS]</v-layout>
                                     </v-container></v-card-actions></v-layout></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
    vueEmployeeDetailDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueEmployeeDetailDataframe_tab_model">
                                    <v-tab ripple href="#vueEmployeeContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueEmployeeAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueEmployeeAddDataframe-tab-id">Employee Info</v-tab>
                                   <v-spacer></v-spacer> <v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueEmployeeDetailDataframe_tab_model">
                               <v-tab-item value="vueEmployeeContactDataframe-tab-id">
                                     
                                        <v-form  ref='vueEmployeeDetailDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactDetailDataframe]</v-layout></v-container>
                                         </v-form>

                                   </v-tab-item>
                                    <v-tab-item value="vueEmployeeAddressDataframe-tab-id"><vueaddressdetaildataframe/></v-tab-item>
                                    <v-tab-item value="vueEmployeeAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout row wrap justify-center>
                                    
                                        <v-form  ref='vueEmployeeAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                    </v-layout></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
    vueEmployeeEditDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueEmployeeEditDataframe_tab_model">
                                    <v-tab ripple href="#vueEmployeeContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueEmployeeAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueEmployeeAddDataframe-tab-id">Employee Info</v-tab>
                                    <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueEmployeeEditDataframe_tab_model">
                               <v-tab-item value="vueEmployeeContactDataframe-tab-id">
                                    
                                        <v-form  ref='vueEmployeeEditDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactEditDataframe]</v-layout></v-container>
                                         </v-form>

                                   </v-tab-item>
                                    <v-tab-item value="vueEmployeeAddressDataframe-tab-id"><vueaddresseditdataframe/></v-tab-item>
                                    <v-tab-item value="vueEmployeeAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout row wrap justify-center>
                                    
                                        <v-form  ref='vueEmployeeAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                    </v-layout> <v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[ALL_OTHER_BUTTONS]</v-layout></v-container></v-card-actions></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }


    vueProviderDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueProviderAddDataframe_tab_model">
                                    <v-tab ripple href="#vueProviderContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueProviderAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueProviderAddDataframe-tab-id">Provider Info</v-tab>
                                 </v-tabs>
                               <v-tabs-items v-model="vueProviderAddDataframe_tab_model">
                               <v-tab-item value="vueProviderContactDataframe-tab-id">
                                     [vueProviderContactDataframe]
                                   </v-tab-item>
                                    <v-tab-item value="vueProviderAddressDataframe-tab-id"><vueprovideraddressdataframe/></v-tab-item>
                                    <v-tab-item value="vueProviderAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout row wrap justify-center>
                                    <v-flex xs12 sm12 md12 lg12 xl12>
                                        <v-form  ref='vueProviderAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                    </v-flex>
                                    </v-layout> <v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[previous][ALL_OTHER_BUTTONS]</v-layout></v-container></v-card-actions></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
    vueProviderDetailDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueProviderDetailDataframe_tab_model">
                                    <v-tab ripple href="#vueProviderContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueProviderAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueProviderAddDataframe-tab-id">Provider Info</v-tab>
                                   <v-spacer></v-spacer> <v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueProviderDetailDataframe_tab_model">
                               <v-tab-item value="vueProviderContactDataframe-tab-id">
                                     
                                        <v-form  ref='vueProviderDetailDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactDetailDataframe]</v-layout></v-container>
                                         </v-form>

                                   </v-tab-item>
                                    <v-tab-item value="vueProviderAddressDataframe-tab-id"><vueaddressdetaildataframe/></v-tab-item>
                                    <v-tab-item value="vueProviderAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout row wrap justify-center>
                                    
                                    <v-flex xs12 sm12 md12 lg12 xl12>
                                        <v-form  ref='vueProviderAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                         </v-flex>
                                    </v-layout></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
    vueProviderEditDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueProviderEditDataframe_tab_model">
                                    <v-tab ripple href="#vueProviderContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueProviderAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueProviderAddDataframe-tab-id">Provider Info</v-tab>
                                    <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueProviderEditDataframe_tab_model">
                               <v-tab-item value="vueProviderContactDataframe-tab-id">
                                    
                                        <v-form  ref='vueProviderEditDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactEditDataframe]</v-layout></v-container>
                                         </v-form>

                                   </v-tab-item>
                                    <v-tab-item value="vueProviderAddressDataframe-tab-id"><vueaddresseditdataframe/></v-tab-item>
                                    <v-tab-item value="vueProviderAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout row wrap justify-center>
                                    
                                    <v-flex xs12 sm12 md12 lg12 xl12>
                                        <v-form  ref='vueProviderAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                         </v-flex>
                                    </v-layout> <v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[ALL_OTHER_BUTTONS]</v-layout></v-container></v-card-actions></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }


    vueVendorDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueVendorAddDataframe_tab_model">
                                    <v-tab ripple href="#vueVendorContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueVendorAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueVendorAddDataframe-tab-id">Vendor Info</v-tab>
                                 </v-tabs>
                               <v-tabs-items v-model="vueVendorAddDataframe_tab_model">
                               <v-tab-item value="vueVendorContactDataframe-tab-id">
                                     [vueVendorContactDataframe]
                                   </v-tab-item>
                                    <v-tab-item value="vueVendorAddressDataframe-tab-id"><vuevendoraddressdataframe/></v-tab-item>
                                    <v-tab-item value="vueVendorAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout row wrap justify-center>
                                    <v-flex xs12 sm12 md12 lg12 xl12>
                                        <v-form  ref='vueVendorAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                    </v-flex>
                                    </v-layout> <v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[previous][ALL_OTHER_BUTTONS]</v-layout></v-container></v-card-actions></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
    vueVendorDetailDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueVendorDetailDataframe_tab_model">
                                    <v-tab ripple href="#vueVendorContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueVendorAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueVendorAddDataframe-tab-id">Vendor Info</v-tab>
                                   <v-spacer></v-spacer> <v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueVendorDetailDataframe_tab_model">
                               <v-tab-item value="vueVendorContactDataframe-tab-id">
                                     
                                        <v-form  ref='vueVendorDetailDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactDetailDataframe]</v-layout></v-container>
                                         </v-form>

                                   </v-tab-item>
                                    <v-tab-item value="vueVendorAddressDataframe-tab-id"><vueaddressdetaildataframe/></v-tab-item>
                                    <v-tab-item value="vueVendorAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout row wrap justify-center>
                                    
                                    <v-flex xs12 sm12 md12 lg12 xl12>
                                        <v-form  ref='vueVendorAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                         </v-flex>
                                    </v-layout></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
    vueVendorEditDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueVendorEditDataframe_tab_model">
                                    <v-tab ripple href="#vueVendorContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueVendorAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-tab ripple href="#vueVendorAddDataframe-tab-id">Vendor Info</v-tab>
                                    <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueVendorEditDataframe_tab_model">
                               <v-tab-item value="vueVendorContactDataframe-tab-id">
                                    
                                        <v-form  ref='vueVendorEditDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactEditDataframe]</v-layout></v-container>
                                         </v-form>

                                   </v-tab-item>
                                    <v-tab-item value="vueVendorAddressDataframe-tab-id"><vueaddresseditdataframe/></v-tab-item>
                                    <v-tab-item value="vueVendorAddDataframe-tab-id">[DATAFRAME_LABEL]<v-layout row wrap justify-center>
                                    
                                    <v-flex xs12 sm12 md12 lg12 xl12>
                                        <v-form  ref='vueVendorAddDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[ALL_OTHER_FIELDS]</v-layout></v-container>
                                         </v-form>
                                         </v-flex>
                                    </v-layout> <v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2>[ALL_OTHER_BUTTONS]</v-layout></v-container></v-card-actions></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }


    vueClientDetailDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueClientDetailDataframe_tab_model">
                                    <v-tab ripple href="#vueClientContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueClientAddressDataframe-tab-id">Address Info</v-tab>
                                   <v-spacer></v-spacer> <v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueClientDetailDataframe_tab_model">
                               <v-tab-item value="vueClientContactDataframe-tab-id">
                                     
                                        <v-form  ref='vueClientDetailDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactDetailDataframe]</v-layout></v-container>
                                         </v-form>

                                   </v-tab-item>
                                    <v-tab-item value="vueClientAddressDataframe-tab-id"><vueaddressdetaildataframe/></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
    vueClientEditDataframeLayout(ColumnLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="light-blue darken-2" dark slider-color="yellow" v-model="vueClientEditDataframe_tab_model">
                                    <v-tab ripple href="#vueClientContactDataframe-tab-id">Contact Info</v-tab>
                                    <v-tab ripple href="#vueClientAddressDataframe-tab-id">Address Info</v-tab>
                                    <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                             </v-btn><span>Close</span></v-tooltip>    
                                 </v-tabs>
                               <v-tabs-items v-model="vueClientEditDataframe_tab_model">
                               <v-tab-item value="vueClientContactDataframe-tab-id">
                                    
                                        <v-form  ref='vueClientEditDataframe_form'>
                                            <v-container grid-list-xl fluid><v-layout row wrap align-center justify-center>[vueContactEditDataframe]</v-layout></v-container>
                                         </v-form>

                                   </v-tab-item>
                                    <v-tab-item value="vueClientAddressDataframe-tab-id"><vueaddresseditdataframe/></v-tab-item>
                               </v-tabs-items></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']

    }
}
