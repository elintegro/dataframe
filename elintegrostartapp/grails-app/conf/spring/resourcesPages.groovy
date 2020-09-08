import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [
				"vueInitDataframe","vueElintegroProgressBarDataframe","vueFirstContainerResizeDataframe","vueFirstContainerDataframe",
				"vueOurWorkContainerDataframe","vueOurProcessContainerDataframe","vueCollaborationContainerResizeDataframe","vueCollaborationContainerDataframe",
				"vueOurFrameworkContainerResizeDataframe","vueOurFrameworkContainerDataframe","vueQuotesContainerDataframe","vueOurTechnologiesContainerDataframe","vueElintegroSignUpQuizDataframe","vueElintegroChangePasswordAfterSignUpDataframe","vueQuizPlaceholderContainerDataframe","vueFooterContainerDataframe","vueElintegroHomeDataframe","vueElintegroNavigationDrawerDataframe",
				"vueNewEmployeeBasicInformationDataframe","vueNewEmployeeUploadResumeDataframe","vueNewEmployeeApplicantEditSkillDataframe","vueNewEmployeeApplicantAddSkillDataframe"
				,"vueNewEmployeeSelfAssesmentDataframe","vueNewEmployeeThankYouMessageAfterSaveDataframe","vueNewEmployeeAddtionalQuestionsDataframe","vueNewEmployeeApplicantDataframe",
				"vueElintegroResetPasswordDataframe","vueElintegroUserProfileDataframe","vueElintegroProfileMenuDataframe","vueCareersPageButtonDataframe",
				"vueElintegroApplicantGeneralInformationDataframe","vueElintegroApplicantSelfAssessmentDataframe","vueElintegroApplicantCVDataframe",
				"vueElintegroApplicantQuestionAnswerDataframe","vueElintegroCommentPageForApplicantDataframe","vueElintegroApplicantDetailsDataframe",
				"vueTechnologiesDataframe","vueClientProjectDataframe","vueElintegroLoginDataframe","vueElintegroRegisterDataframe","vueCreateProjectForTranslationDataframe","vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe",
				"vueAddNewRecordForCurrentProjectDataframe","vueEditTranslatedRecordsOfGridDataframe","vueDeleteTranslatedRecordsOfGridDataframe","vueDialogBoxForNotLoggedInUserDataframe",
				"vueGridOfTranslatedTextDataframe","vueTranslatorDataframe","vueTranslatorAssistantAfterLoggedInDataframe","vueTranslatorAssistantBeforeLoggedInDataframe",
				"vueTranslatorAssistantDataframe","vueElintegroSubMenuDataframe","vueElintegroAboutUsMenuDataframe","vueElintegroAppsDataframe","vueGettingStartedDataframe", "vueCareersDataframe","vueElintegroApplicantsDataframe",
				"vueContactUsPageDataframe","vueElintegroNavigationFirstTwoButtonDataframe","vueElintegroNavigationButtonBeforeLoggedInDataframe","vueElintegroNavigationButtonAfterLoggedInDataframe","vueElintegroAppBarDataframe","vueElintegroLogoDataframe",
        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueElintegroContainerLayout" //outermost layout name
	}

}
