import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [
                 //Global dataframes...
				"vueInitDataframe", "vueAlertMsgDataframe", "vueElintegroProgressBarDataframe","vueElintegroLanguageSelectorDataframe","vueDummyDataframe",

				//Dataframes used in Home page...
				"vueFirstContainerDataframe","vueOurWorkContainerDataframe","vueOurProcessContainerDataframe","vueCollaborationContainerDataframe",
				"vueOurFrameworkContainerDataframe","vueQuotesContainerDataframe","vueOurTechnologiesContainerDataframe","vueElintegroSignUpQuizDataframe",
				"vueElintegroChangePasswordAfterSignUpDataframe","vueQuizPlaceholderContainerDataframe","vueFooterPrivacyDataframe","vueTermAndConditionDataframe",
				"vueFooterContainerDataframe","vueElintegroHomeDataframe",

				//Dataframes used in navigation drawer (navigation buttons)...
				"vueElintegroNavigationDrawerDataframe",

				//Dataframes used in new employee registration flow of careers page...
				"vueNewEmployeeBasicInformationDataframe","vueAddressDataframe","vueNewEmployeeUploadResumeDataframe","vueNewEmployeeApplicantEditSkillDataframe",
				 "vueNewEmployeeApplicantAddSkillDataframe","vueNewEmployeeSelfAssesmentDataframe","vueNewEmployeeThankYouMessageAfterSaveDataframe",
				 "vueNewEmployeeAddtionalQuestionsDataframe","vueNewEmployeeApplicantDataframe",

				//Dataframes used in user profile...
				"vueElintegroResetPasswordDataframe","vueElintegroUserProfileDataframe","vueElintegroProfileMenuDataframe",

				//Dataframes used in applicant details page (Admin only has access on them)...
				"vueElintegroApplicantGeneralInformationDataframe","vueElintegroApplicantSelfAssessmentDataframe","vueElintegroApplicantCVDataframe",
				"vueElintegroApplicantQuestionAnswerDataframe","vueElintegroCommentPageForApplicantDataframe","vueElintegroApplicantDetailsDataframe",

				//Dataframes used in client project and technologies page
				"vueTechnologiesDataframe","vueClientProjectDataframe",

				//Dataframes used in login/registration flow...
				"vueElintegroForgetPasswordDataframe","vueElintegroChangeForgotPasswordDataframe","vueElintegroLoginDataframe","vueElintegroLoginWithOTPDataframe","vueElintegroLoginTabDataframe","vueElintegroRegisterDataframe",

				//Dataframes used in translator assistant application...
				"vueCreateProjectForTranslationDataframe","vueEditTextOfNewlyAddedRecordForCurrentProjectDataframe",
				"vueAddNewRecordForCurrentProjectDataframe","vueEditTranslatedRecordsOfGridDataframe","vueDeleteTranslatedRecordsOfGridDataframe","vueDeleteSourceRecordsOfGridDataframe",
				"vueDialogBoxForNotLoggedInUserDataframe","vueGridOfTranslatedTextDataframe","vueEditSourceRecordsOfGridDataframe","vueGridOfSourceTextDataframe","vueTranslatorDataframe","vueHowYouDoDataframe","vueNewsLetterDataframe",
				"vueTranslatorAssistantAfterLoggedInDataframe","vueTranslatorAssistantBeforeLoggedInDataframe","vueMeetTranslatorAssistantIntroDataframe",
				"vueTranslatorAssistantDataframe",

				//Dataframes used in navigation buttons...
				"vueElintegroSubMenuDataframe","vueElintegroAboutUsMenuDataframe","vueElintegroAppsDataframe","vueGettingStartedDataframe", "vueCareersDataframe",
				"vueElintegroApplicantsDataframe","vueContactUsPageDataframe","vueElintegroNavigationFirstTwoButtonDataframe",
				"vueElintegroNavigationButtonBeforeLoggedInDataframe","vueElintegroNavigationButtonAfterLoggedInDataframe","vueElintegroLogoDataframe",
        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueElintegroContainerLayout" //outermost layout name
	}

}
