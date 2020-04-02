import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [
				"vueInitDataframe","vueSubContainerDataframe","vueElintegroBannerDataframe","vueNewEmployeeBasicInformationDataframe",
				"vueNewEmployeeUploadResumeDataframe","vueNewEmployeeSelfAssesmentDataframe","vueNewEmployeeAddtionalQuestionsDataframe",
				"vueNewEmployeeApplicantDataframe","vueCareersPageButtonDataframe","vueTechnologiesDataframe",
				"vueClientProjectDataframe","vueGettingStartedDataframe", "vueCareersDataframe","vueContactUsPageDataframe",
				"vueElintegroLoginDataframe","vueElintegroRegisterDataframe","vueElintegroNavigationButtonDataframe","vueElintegroLogoDataframe",
        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueElintegroContainerLayout" //outermost layout name
	}

}
