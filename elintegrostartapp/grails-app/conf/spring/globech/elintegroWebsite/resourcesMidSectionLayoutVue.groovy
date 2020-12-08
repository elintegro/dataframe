package spring.globech.elintegroWebsite

import com.elintegro.erf.layout.RowLayoutVue


beans {
    midSectionLayout(RowLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex>
                                           <vueInitDataframe/>
                                          
                                           <router-view :key="\$route.fullPath"></router-view>

                                           </v-flex>"""
        isGlobal = true
    }
}

