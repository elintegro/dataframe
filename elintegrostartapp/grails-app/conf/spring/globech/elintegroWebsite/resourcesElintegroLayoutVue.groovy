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
        layoutPlaceHolder = """<v-content><v-card  class="mx-auto overflow-hidden" >
                                  <vueSubContainerDataframe/>
                                     </v-card>
                                     
                              </v-content>"""
        children = ["midSectionLayout"]
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }

    elintegroNavigationButtonLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex> """
    }
    midSectionLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>
                                           <vueElintegroBannerDataframe/>
                                           </v-flex>"""
        isGlobal = true
    }
    subContainerLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex><v-app-bar flat color="white"  tabs style="z-index:99;">
                                   <v-app-bar-nav-icon @click.stop="drawer = !drawer" class="hidden-lg-and-up"></v-app-bar-nav-icon>
                           
                                   <v-toolbar-title style="position:relative;" ><vueElintegroLogoDataframe/></v-toolbar-title>
                                 
                                   <v-spacer></v-spacer>
                                 
      
                                        <div class="hidden-md-and-down"> <vueElintegroNavigationButtonDataframe/></div>
    

                               </v-app-bar>
                               
        <v-navigation-drawer
        v-model="drawer"
        app
        temporary
        width = "min-content"
      >
      <vueElintegroNavigationButtonDataframe/>
       

            
      </v-navigation-drawer>
                                 
                                    <midSectionLayout/>
                               


</v-flex>

                                     """
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
