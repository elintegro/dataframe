package spring.globech.elintegroWebsite
import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue
beans {
    emptyDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</div>"""
    }
    vueElintegroContainerLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div id='app' ><v-app class="app" style="background-color:#fff;"><sectionLayout/></v-app></div>"""
        children = ["sectionLayout"]
    }
    sectionLayout(RowLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-content>
                                  <v-container fluid pa-0>
                                     <navigationLayout/>
                                     <midSectionLayout/>
                                   </v-container>
                               </v-content>"""
        children = ["navigationLayout","midSectionLayout"]
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    navigationLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-toolbar flat color="white"  tabs style="z-index:99;">
                            
                                   <v-toolbar-side-icon ></v-toolbar-side-icon>
                                   <v-toolbar-title style="position:fixed;" ><vueElintegroLogoDataframe/></v-toolbar-title>
                                 
                                   <v-spacer></v-spacer>
                                   <vueElintegroNavigationButtonDataframe/>
                                   <vueInitDataframe/>
                               </v-toolbar>"""
        isGlobal = true
    }
    elintegroNavigationButtonLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT] [REF_FIELD]</v-flex> """
    }
    midSectionLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div class="first-banner" style="min-width:1084px">
                                           <vueElintegroBannerDataframe/>
                                           </div>"""
        isGlobal = true
    }
    /*
    buttonTechnologiesLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex> <router-view name='vueTechnologiesDataframe'/>

                                           </v-flex>"""
    }*/
//    buttonLayout(ColumnLayoutVue){bean ->
//        layoutBeanName = bean.name
//        layoutPlaceHolder = """<v-flex >[home] [clientsProjects] [technologies] [gettingStarted]</v-flex>
//                                   <v-flex>[carrers] [contactUs] [login] [register] </v-flex>"""
//    }


}
