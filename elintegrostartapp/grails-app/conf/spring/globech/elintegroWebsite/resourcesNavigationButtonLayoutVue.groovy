package spring.globech.elintegroWebsite

import com.elintegro.erf.layout.RowLayoutVue


beans {
    elintegroNavigationButtonLayout(RowLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-flex>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</v-flex> """
    }
}