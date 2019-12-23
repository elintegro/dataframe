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
        layoutPlaceHolder = """<v-content><v-card  class="mx-auto overflow-hidden">
                                  
                                     <navigationLayout/>
                                     <midSectionLayout/>
                               </v-card></v-content>"""
        children = ["navigationLayout","midSectionLayout"]
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    navigationLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-app-bar flat color="white"  tabs style="z-index:99;">
                                   <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
                           
                                   <v-toolbar-title style="position:relative;" ><vueElintegroLogoDataframe/></v-toolbar-title>
                                 
                                   <v-spacer></v-spacer>
                                   <v-navigation-drawer
        v-model="drawer"
        absolute
        bottom
        temporary
      >
                                         <vueElintegroNavigationButtonDataframe/>
      </v-navigation-drawer>

                               </v-app-bar>"""
        isGlobal = true
    }
    elintegroNavigationButtonLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex> """
    }
    midSectionLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex style="min-width:1084px">
                                           <vueElintegroBannerDataframe/>
                                           </v-flex>"""
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
