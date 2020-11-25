package spring.globech.elintegroWebsite

import com.elintegro.erf.layout.RowLayoutVue


beans {
    elintegroNavigationButtonFirstLayout(RowLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-row class="mx-0">[DATAFRAME_SCRIPT][home]</v-row> """
    }
    elintegroNavigationButtonLayout(RowLayoutVue){bean->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-row class="mx-0">[DATAFRAME_SCRIPT][careers][contactUs][login][register]</v-row> """
    }
    elintegroNavigationButtonAfterLoggedInLayout(RowLayoutVue){ bean ->
        layoutBeanName = bean.name
        layoutPlaceHolder ="""<v-row class="mx-0">[DATAFRAME_SCRIPT][careers][applicants][contactUs][myProfile]</v-row> """
    }
}