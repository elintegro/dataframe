import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [
				"vueRefactorDataframe","vueInitDataframe","vueElintegroBannerDataframe","vueElintegroNavigationDrawerDataframe"
				,"vueElintegroAppBarDataframe","vueElintegroLogoDataframe","vueElintegroAppBarDataframe","vueElintegroNavigationButtonDataframe"
        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueElintegroContainerLayout" //outermost layout name
	}

}
