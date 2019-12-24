import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [
				"vueSubContainerDataframe","vueTechnologiesDataframe","vueElintegroNavigationButtonDataframe","vueElintegroLogoDataframe","vueElintegroBannerDataframe",
        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueElintegroContainerLayout" //outermost layout name
	}

}
