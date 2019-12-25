import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [
				"vueNavigationDataframe","vueClientProjectDataframe","vueNavigationButtonDataframe","vueLogoDataframe","vueElintegroBannerDataframe",
        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueElintegroContainerLayout" //outermost layout name
	}

}
