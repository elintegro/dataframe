import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [

				"vueInitDataframe","vueElintegroProgressBarDataframe","vueFirstContainerDataframe",
				"vueOurWorkContainerDataframe","vueOurProcessContainerDataframe","vueCollaborationContainerDataframe",
				"vueOurFrameworkContainerDataframe","vueQuotesContainerDataframe","vueOurTechnologiesContainerDataframe","vueElintegroSignUpQuizDataframe","vueElintegroChangePasswordAfterSignUpDataframe","vueQuizPlaceholderContainerDataframe","vueFooterPrivacyDataframe","vueTermAndConditionDataframe","vueFooterContainerDataframe","vueElintegroHomeDataframe","vueElintegroNavigationDrawerDataframe",



				"vueNewEmployeeBasicInformationDataframe","vueNewEmployeeUploadResumeDataframe","vueNewEmployeeApplicantEditSkillDataframe","vueNewEmployeeApplicantAddSkillDataframe"
				,"vueNewEmployeeSelfAssesmentDataframe","vueNewEmployeeThankYouMessageAfterSaveDataframe","vueNewEmployeeAddtionalQuestionsDataframe","vueNewEmployeeApplicantDataframe",
				"vueElintegroResetPasswordDataframe","vueElintegroUserProfileDataframe","vueElintegroProfileMenuDataframe","vueCareersPageButtonDataframe",
				"vueElintegroApplicantGeneralInformationDataframe","vueElintegroApplicantSelfAssessmentDataframe","vueElintegroApplicantCVDataframe",
				"vueElintegroApplicantQuestionAnswerDataframe","vueElintegroCommentPageForApplicantDataframe","vueElintegroApplicantDetailsDataframe",
				"vueTechnologiesDataframe","clientProjectEditDataframe","vueClientProjectDataframe","vueElintegroForgetPasswordDataframe","vueElintegroChangeForgotPasswordDataframe","vueElintegroLoginDataframe","vueElintegroRegisterDataframe","vueCreateProjectForTranslationDataframe","vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe",
				"vueAddNewRecordForCurrentProjectDataframe","vueEditTranslatedRecordsOfGridDataframe","vueDeleteTranslatedRecordsOfGridDataframe","vueDialogBoxForNotLoggedInUserDataframe",
				"vueGridOfTranslatedTextDataframe","vueTranslatorDataframe","vueHowYouDoDataframe","vueNewsLetterDataframe","vueTranslatorAssistantAfterLoggedInDataframe","vueTranslatorAssistantBeforeLoggedInDataframe",
				"vueMeetTranslatorAssistantIntroDataframe","vueTranslatorAssistantDataframe","vueElintegroSubMenuDataframe","vueElintegroAboutUsMenuDataframe","vueElintegroAppsDataframe","vueGettingStartedDataframe", "vueCareersDataframe","vueElintegroApplicantsDataframe",
				"vueContactUsPageDataframe","vueElintegroNavigationFirstTwoButtonDataframe","vueElintegroNavigationButtonBeforeLoggedInDataframe","vueElintegroNavigationButtonAfterLoggedInDataframe","vueElintegroAppBarDataframe","vueElintegroLogoDataframe",
        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueElintegroContainerLayout" //outermost layout name
	}

}
