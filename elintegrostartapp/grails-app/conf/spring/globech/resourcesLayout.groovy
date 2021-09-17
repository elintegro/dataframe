package spring.globech

import com.elintegro.erf.layout.ColumnLayoutVue
import com.elintegro.erf.layout.RowLayoutVue

beans {

    defaultColumnLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-layout
          column
          wrap
          class="my-5"
          align-center
        ></v-layout>"""
    }
    defaultRowLayout(RowLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-layout
          row
          wrap
          class="my-5"
          justify-center
        >[DATAFRAME_SCRIPT]</v-layout>"""
    }
    vueContainerLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div id='app'><v-app><sectionlayout3/></v-app></div>"""
        childLayouts = ["sectionLayout3"]
    }
    appNameDataframeLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<span>[DATAFRAME_SCRIPT]</span>"""
    }
    footerLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-footer app>Copyright @ Elintegro Inc., Elintegro Start App project</v-footer>"""
    }
    columnSpacerLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs1 md1 lg1 xl1></v-flex>"""
    }
    defaultDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>[DATAFRAME_LABEL][DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'4', 'xl':'4']
    }

    defaultDfrButtonLayout(RowLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-card-actions slot="dfButtons">[BUTTON_SCRIPT]</v-card-actions>"""
    }
    defaultDialogBoxLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' ><v-toolbar dark color="light-blue darken-2"><v-toolbar-title>[DATAFRAME_LABEL]</v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'4', 'xl':'4']
    }

    defaultRouteDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;">[DATAFRAME_LABEL]</v-toolbar-title>
                                  </v-toolbar>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6', 'lg':'4', 'xl':'4']
    }

    vueEmbeddedDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_LABEL]<v-layout row wrap justify-end align-start> [DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-layout></div>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'3', 'xl':'2']
    }

    treeWidgetDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex><v-card style="overflow-x:auto">[vueOwnerTreeDataframe]</v-card></v-flex>"""
    }
    emptyDataframeLayout(ColumnLayoutVue){bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<div>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</div>"""
    }

    formDataframeLayout(ColumnLayoutVue){ bean->
        layoutBeanName = bean.name
        layoutPlaceHolder = """<v-flex xs12 sm12 md12 lg12 xl12><v-card>[DATAFRAME_LABEL][DATAFRAME_SCRIPT]<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap>[previous][ALL_OTHER_BUTTONS]</v-layout></v-container></v-card-actions></v-card></v-flex>"""
        cssGridValues = ['xs':'12', 'sm':'6','md':'4', 'lg':'4', 'xl':'4']
    }
}
