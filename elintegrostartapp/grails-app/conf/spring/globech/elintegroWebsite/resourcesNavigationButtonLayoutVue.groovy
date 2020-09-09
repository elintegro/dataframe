package spring.globech.elintegroWebsite

import com.elintegro.erf.layout.RowLayoutVue


beans {
    elintegroNavigationButtonLayout(RowLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<div>[BUTTON_SCRIPT][DATAFRAME_SCRIPT]</div> """
    }
    elintegroNavigationButtonAfterLoggedInLayout(RowLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<div>[DATAFRAME_SCRIPT][BUTTON_SCRIPT]</div> """
    }
}