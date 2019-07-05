import com.elintegro.erf.dataframe.vue.PageDFRegistryVue

beans {

	gcMainPageVue(PageDFRegistryVue){ bean ->
		pageName = "gcMainPageVue";
        /**IMPORTANT: Need to put child dataframe bbefore parent dataframes*/
		allDataframesList = [ "vueAppNameDataframe","vueRegisterDataframe", "vueMapWidgetDataframe","vueAddressDetailDataframe", "vueAddressEditDataframe",
							  "vueContactEditDataframe","vueContactDetailDataframe","vueContactManagementDetailDataframe","vueContactManagementEditDataframe",
							  "vueEmployeeDetailDataframe", "vueEmployeeEditDataframe", "vueEmployeeGridDataframe",
							  "vueProviderDetailDataframe", "vueProviderEditDataframe", "vueProviderGridDataframe",
							  "vueVendorDetailDataframe", "vueVendorEditDataframe", "vueVendorGridDataframe",
							  "vueClientDetailDataframe", "vueClientEditDataframe", "vueClientGridDataframe",
							  "vueEmployeeContactDataframe","vueEmployeeAddressDataframe", "vueEmployeeAddDataframe",
							  "vueProviderContactDataframe","vueProviderAddressDataframe", "vueProviderAddDataframe",
							  "vueVendorContactDataframe","vueVendorAddressDataframe", "vueVendorAddDataframe",
							  "vueRegisterMenuDataframe",
							  "vueMedicationsDetailDataframe", "vuePrescribedMedicationsDetailDataframe","vueMedicationsGridDetailDataframe", "vueMedicalRecordDetailDataframe",
							 "vueContactFormEditDataframe", "vueApplicationFormDetailDataframe","vueMedicationsEditDataframe", "vuePrescribedMedicationsEditDataframe", "vueMedicationsGridEditDataframe", "vueMedicalRecordEditDataframe", "vueApplicationFormEditDataframe",
							  "vueApplicationManagementDataframe", "vueUserManagementMenuDataframe",
							  "vueMedicationsDataframe", "vuePrescribedMedicationsDataframe","vueMedicationsGridDataframe", "vueMedicalRecordDataframe",
							  "vueAddressDataframe", "vueContactDataframe", "vueApplicationFormDataframe","vueRecordEventDataframe", "vueEventManagementMenuDataframe","vueToolbarDataframe", "vueInitDataframe", "vueUserProfileDataframe",
							  "vueProfileMenuDataframe", "vueAfterLoggedinDataframe", "vueAlertMsgDataframe", "vueLoginDataframe","vueLoginNavigation"

        ]
		dataframesToShowInMainPage = []
		containerLayout = "vueContainerLayout" //outermost layout name
	}

}
