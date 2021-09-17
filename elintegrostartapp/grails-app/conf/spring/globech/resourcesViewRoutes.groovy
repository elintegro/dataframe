package spring.globech

import com.elintegro.erf.dataframe.vue.ViewRoutes

beans {
    /**
     * Note: concat "Comp" in the component for all dataframes and layouts
     */
    allDataframeRoutes(ViewRoutes){
        routes= """[
              {path:"/", name:"sectionLayout", component: sectionLayoutComp},\n
              {path:"/home", name:"vueElintegroHomeDataframe",component:vueElintegroHomeDataframeComp},\n
              {path:"/contact-us", name:"vueContactUsPageDataframe",component:vueContactUsPageDataframeComp},\n
              {path:"/careers", name:"vueCareersDataframe",component:vueCareersDataframeComp},\n
              {path:"/terms-and-condition", name:"vueTermAndConditionDataframe",component:vueTermAndConditionDataframeComp},\n
              {path:"/translator", name:"vueTranslatorAssistantDataframe",component:vueTranslatorAssistantDataframeComp},\n
              {path:"/translate",name:"vueTranslatorDataframe", component: vueTranslatorDataframeComp},\n
              {path:"/new-employee-applicant", name:"vueNewEmployeeApplicantDataframe",component:vueNewEmployeeApplicantDataframeComp},\n
              {path:"/client-project", name:"vueClientProjectDataframe",component:vueClientProjectDataframeComp},\n
              {path:"/forget-password", name:"vueElintegroForgetPasswordDataframe",component:vueElintegroForgetPasswordDataframeComp},\n
              {path:"/change-forget-password", name:"vueElintegroChangeForgotPasswordDataframe",component:vueElintegroChangeForgotPasswordDataframeComp},\n
              {path:"/login-page", name:"vueDialogBoxForNotLoggedInUserDataframe",component:vueDialogBoxForNotLoggedInUserDataframeComp},\n
              {path:"/thank-you-message", name:"vueNewEmployeeThankYouMessageAfterSaveDataframe",component:vueNewEmployeeThankYouMessageAfterSaveDataframeComp},\n
              {path:"/applicants", name:"vueElintegroApplicantsDataframe",component:vueElintegroApplicantsDataframeComp},\n
                
        ]"""
    }
}
