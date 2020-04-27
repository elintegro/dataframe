package spring.globech.elintegroWebsite

import com.elintegro.erf.layout.RowLayoutVue

beans {
    subContainerLayout(RowLayoutVue) { bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex><v-app-bar flat color="white"  tabs style="z-index:99;">
                                   <v-app-bar-nav-icon @click.stop="drawer = !drawer" class="hidden-lg-and-up"></v-app-bar-nav-icon>
                           
                                   <v-toolbar-title style="position:relative;" ><vueElintegroLogoDataframe/></v-toolbar-title>
                                 
                                   <v-spacer></v-spacer>
                                 
                                        <div class="hidden-md-and-down"><vueElintegroNavigationButtonAfterLoggedInDataframe  v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
                                         <vueElintegroNavigationButtonDataframe v-else/></div>
    

                               </v-app-bar>
                               
        <v-navigation-drawer
        v-model="drawer"
        app
        temporary
        width = "min-content"
      >
      <vueElintegroNavigationButtonAfterLoggedInDataframe v-if="this.\$store.state.vueInitDataframe.loggedIn"/>
      <vueElintegroNavigationButtonDataframe v-else/>
       

            
      </v-navigation-drawer>
                                 
                                    <midSectionLayout/>
                               


</v-flex>

                                     """
    }

}