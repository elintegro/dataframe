import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [
				"vueInitDataframe","vueNavigationDataframe","vueElintegroBannerDataframe","vueTechnologiesDataframe","vueClientProjectDataframe",
				"vueGettingStartedDataframe","vueCareersDataframe","vueContactUsDataframe","vueLoginDataframe","vueRegisterDataframe",
				"vueNavigationButtonDataframe","vueLogoDataframe",
        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueElintegroContainerLayout" //outermost layout name
	}

}
