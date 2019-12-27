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
                                    <v-card  class="mx-auto overflow-hidden" >
                                           <vueNavigationDataframe/>
                                     </v-card>
                              </v-content>"""
        children = ["midSectionLayout"]
        flexGridValues = ['xs12', 'sm12', 'md12', 'lg12', 'xl12']
    }
    navigationLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>
                                    <v-app-bar flat color="white"  tabs style="z-index:99;">
                                        <v-app-bar-nav-icon @click.stop="drawer=!drawer" class="hidden-lg-and-up"></v-app-bar-nav-icon>
                                        <v-toolbar-title style="position:relative;" ><vueLogoDataframe/></v-toolbar-title>
                                        <v-spacer></v-spacer>
                                        <div class="hidden-md-and-down"><vueNavigationButtonDataframe/></div>
                                    </v-app-bar>
                                    <v-navigation-drawer v-model="drawer" app temporary  width = "min-content">
                                        <vueNavigationButtonDataframe/>
                                    </v-navigation-drawer>
                                    <midSectionLayout/>
                            </v-flex>"""
    }
    elintegroNavigationButtonLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex> """
    }
    midSectionLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>
                                     <vueInitDataframe/>
                                     <router-view :key="\$route.fullPath"></router-view>
                               </v-flex>"""
        isGlobal = true
    }
}
