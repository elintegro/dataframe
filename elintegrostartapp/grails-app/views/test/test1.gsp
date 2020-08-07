<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Elintegro Start App</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="https://qa.elintegro.com/assets/elintegro_logo.png" type="image/x-icon">
    <link rel="stylesheet" href="/assets/vuejs/tooltip.css?compile=false"/>
    <script type="text/javascript" src="/assets/jquery/jquery-1.11.2.js?compile=false"></script>
    <script type="text/javascript" src="/assets/jquery/dateformat.js?compile=false"></script>
    <link rel="stylesheet" href="/assets/vuejs/vue-material.css?compile=false"/>
    <link rel="stylesheet" href="//cdn.materialdesignicons.com/3.9.97/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="/assets/vuejs/vuetify-v2.0.5.css?compile=false"/>
    <link rel="stylesheet" href="/assets/vuejs/gc-vue.css?compile=false"/>
</head>
<body>
<style>
.viewer-canvas {
    background-color: black;
}

.v-table__overflow {
    /*overflow-x: inherit;*/
    overflow-y: inherit;
}

.hidden {
    display: none;
}
</style>
<div id="dfr"></div>
<div id='app'>
    <v-app class="app" style="background-color:#fff;">
        <sectionLayout/>
    </v-app>
</div>
<script type="text/javascript" src="/assets/vuejs/vue.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vue-router.min.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vuex.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vue-i18n.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vuetify-v2.0.5.js?compile=false"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-resource@1.5.1"></script>
<script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.js"></script>
<script src="https://unpkg.com/popper.js"></script>
<script src="https://unpkg.com/v-tooltip"></script>
<script type="text/javascript" src="/assets/vuejs/v-eutil.min.js?compile=false"></script>
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBAAxHUhrxxl_2ZSpVtGeMX-1Fs83tunNU"></script>
<script type="text/javascript" src="/assets/erf/i18Messages.js?compile=false"></script>
<script type="text/javascript" src="/assets/erf/erfVueController.js?compile=false"></script>
<script type="text/javascript" src="/assets/vuejs/vuex.js?compile=false"></script>
<script>
    let store = new Vuex.Store({
        state: {
            visibility: {
                vueNewEmployeeApplicantEditSkillDataframe: false,
                vueNewEmployeeApplicantEditSkillDataframe: false,
                vueNewEmployeeApplicantAddSkillDataframe: false,
                vueElintegroResetPasswordDataframe: false,
                vueElintegroUserProfileDataframe: false,
                vueNewEmployeeApplicantDataframe: false,
                vueEditTranslatedRecordsOfGridDataframe: false,
                vueEditTranslatedRecordsOfGridDataframe: false,
                vueTranslatorDataframe: false,
                vueCreateProjectForTranslationDataframe: false,
                vueTranslatorAssistantDataframe: false,
                vueElintegroApplicantDetailsDataframe: false,
                vueClientProjectDataframe: false,
                vueElintegroBannerDataframe: false,
                vueElintegroRegisterDataframe: false,
                vueElintegroLoginDataframe: false,
                vueContactUsPageDataframe: false,
                vueCareersDataframe: false,
                vueGettingStartedDataframe: false,
                vueTechnologiesDataframe: false,
                vueElintegroProfileMenuDataframe: false,
                vueContactUsPageDataframe: false,
                vueElintegroApplicantsDataframe: false,
                vueCareersDataframe: false,
                vueGettingStartedDataframe: false,
                vueTechnologiesDataframe: false,
            },
            vueInitDataframe: {
                loggedIn: false,
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroBannerDataframe: {
                vueElintegroBannerDataframe_banner: '/assets/elintegro_banner.jpg',

                vueElintegroBannerDataframe_banner_alt: '/assets/default_profile.jpg',
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroNavigationDrawerDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueNewEmployeeBasicInformationDataframe: {
                vueNewEmployeeBasicInformationDataframe_person_firstName: "",
                vueNewEmployeeBasicInformationDataframe_person_lastName: "",
                vueNewEmployeeBasicInformationDataframe_person_email: "",
                vueNewEmployeeBasicInformationDataframe_person_phone: "",
                vueNewEmployeeBasicInformationDataframe_application_linkedin: "",
                vueNewEmployeeBasicInformationDataframe_person_availablePosition: "",

                vueNewEmployeeBasicInformationDataframe_person_availablePosition_items: [{
                    "id": 1,
                    "name": "Back-end Java Developer"
                }, {
                    "id": 2,
                    "name": "Front-end Developer"
                }, {
                    "id": 3,
                    "name": "Product Owner"
                }, {
                    "id": 4,
                    "name": "Scrum Master"
                }, {
                    "id": 5,
                    "name": "Site Adminstrator"
                }, {
                    "id": 6,
                    "name": "Developer"
                }],

                vueNewEmployeeBasicInformationDataframe_person_availablePosition_keys: [],

                key_vueNewEmployeeBasicInformationDataframe_application_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueNewEmployeeUploadResumeDataframe: {
                vueNewEmployeeUploadResumeDataframe_images: "",
                vueNewEmployeeUploadResumeDataframe_resume: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueNewEmployeeApplicantEditSkillDataframe: {
                vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id: "",
                vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_skill: "",
                vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_level: "",
                vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_comment: "",
                key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueNewEmployeeApplicantAddSkillDataframe: {
                vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_id: "",
                vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_skill: "",
                vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_level: "",
                vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_comment: "",
                key_vueNewEmployeeApplicantAddSkillDataframe_application_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueNewEmployeeSelfAssesmentDataframe: {
                vueNewEmployeeSelfAssesmentDataframe_applicationSkill_grid: {},
                vueNewEmployeeSelfAssesmentDataframe_applicationSkill_grid: {},

                vueNewEmployeeSelfAssesmentDataframe_applicationSkill_headers: [],
                vueNewEmployeeSelfAssesmentDataframe_applicationSkill_items: [],
                vueNewEmployeeSelfAssesmentDataframe_applicationSkill_selectedrow: {},
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueNewEmployeeThankYouMessageAfterSaveDataframe: {
                vueNewEmployeeThankYouMessageAfterSaveDataframe_person_firstName: "",
                vueNewEmployeeThankYouMessageAfterSaveDataframe_person_lastName: "",
                key_vueNewEmployeeThankYouMessageAfterSaveDataframe_application_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueNewEmployeeAddtionalQuestionsDataframe: {
                vueNewEmployeeAddtionalQuestionsDataframe_application_id: "",
                vueNewEmployeeAddtionalQuestionsDataframe_application_question1: "",
                vueNewEmployeeAddtionalQuestionsDataframe_application_question2: "",
                key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueNewEmployeeApplicantDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroResetPasswordDataframe: {
                vueElintegroResetPasswordDataframe_user_password: "",
                vueElintegroResetPasswordDataframe_password2: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroUserProfileDataframe: {
                vueElintegroUserProfileDataframe_person_id: "",
                vueElintegroUserProfileDataframe_person_mainPicture: '/assets/default_profile.jpg',

                vueElintegroUserProfileDataframe_person_mainPicture_alt: '/assets/default_profile.jpg',
                vueElintegroUserProfileDataframe_propertyImages: "",
                vueElintegroUserProfileDataframe_person_email: "",
                vueElintegroUserProfileDataframe_person_firstName: "",
                vueElintegroUserProfileDataframe_person_lastName: "",

                vueElintegroUserProfileDataframe_person_phone: "",
                vueElintegroUserProfileDataframe_person_languages: "",

                vueElintegroUserProfileDataframe_person_languages_items: [{
                    "ename": "English",
                    "id": 1
                }, {
                    "ename": "Russian",
                    "id": 2
                }, {
                    "ename": "French",
                    "id": 3
                }, {
                    "ename": "Hebrew",
                    "id": 4
                }, {
                    "ename": "Arabic",
                    "id": 5
                }, {
                    "ename": "Spanish",
                    "id": 6
                }, {
                    "ename": "Nepali",
                    "id": 7
                }],

                vueElintegroUserProfileDataframe_person_languages_keys: [],

                key_vueElintegroUserProfileDataframe_person_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroProfileMenuDataframe: {
                vueElintegroProfileMenuDataframe_person_id: "",
                vueElintegroProfileMenuDataframe_person_firstName: "",
                vueElintegroProfileMenuDataframe_person_lastName: "",
                vueElintegroProfileMenuDataframe_person_email: "",
                vueElintegroProfileMenuDataframe_person_mainPicture: '/assets/default_profile.jpg',

                vueElintegroProfileMenuDataframe_person_mainPicture_alt: '/assets/default_profile.jpg',
                key_vueElintegroProfileMenuDataframe_person_user_session_userid: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueCareersPageButtonDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroApplicantGeneralInformationDataframe: {
                vueElintegroApplicantGeneralInformationDataframe_application_id: "",
                vueElintegroApplicantGeneralInformationDataframe_person_firstName: "",
                vueElintegroApplicantGeneralInformationDataframe_person_lastName: "",
                vueElintegroApplicantGeneralInformationDataframe_person_email: "",
                vueElintegroApplicantGeneralInformationDataframe_person_phone: "",
                key_vueElintegroApplicantGeneralInformationDataframe_application_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroApplicantSelfAssessmentDataframe: {

                vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_headers: [],
                vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_items: [],
                vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_selectedrow: {},
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroApplicantCVDataframe: {
                vueElintegroApplicantCVDataframe_application_id: "",
                vueElintegroApplicantCVDataframe_files_fileName: 'assets/defaultPdfIcon.PNG',

                vueElintegroApplicantCVDataframe_files_fileName_alt: 'assets/defaultPdfIcon.PNG',
                vueElintegroApplicantCVDataframe_images_name: '/assets/default_profile.jpg',

                vueElintegroApplicantCVDataframe_images_name_alt: '/assets/default_profile.jpg',
                key_vueElintegroApplicantCVDataframe_application_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroApplicantQuestionAnswerDataframe: {
                vueElintegroApplicantQuestionAnswerDataframe_application_id: "",
                vueElintegroApplicantQuestionAnswerDataframe_application_question1: "",
                vueElintegroApplicantQuestionAnswerDataframe_application_question2: "",
                key_vueElintegroApplicantQuestionAnswerDataframe_application_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroCommentPageForApplicantDataframe: {
                vueElintegroCommentPageForApplicantDataframe_application_id: "",
                vueElintegroCommentPageForApplicantDataframe_application_comments: "",
                vueElintegroCommentPageForApplicantDataframe_application_lastComment: "",
                key_vueElintegroCommentPageForApplicantDataframe_application_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroApplicantDetailsDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueTechnologiesDataframe: {
                vueTechnologiesDataframe_java: '/assets/java.PNG',

                vueTechnologiesDataframe_java_alt: '/assets/default_profile.jpg',
                vueTechnologiesDataframe_javascript: '/assets/javascript.PNG',

                vueTechnologiesDataframe_javascript_alt: '/assets/default_profile.jpg',
                vueTechnologiesDataframe_grails: '/assets/grailsphoto.PNG',

                vueTechnologiesDataframe_grails_alt: '/assets/default_profile.jpg',
                vueTechnologiesDataframe_vuejs: '/assets/vuejs.PNG',

                vueTechnologiesDataframe_vuejs_alt: '/assets/default_profile.jpg',
                vueTechnologiesDataframe_kafka: '/assets/kafka.PNG',

                vueTechnologiesDataframe_kafka_alt: '/assets/default_profile.jpg',
                vueTechnologiesDataframe_oracle: '/assets/oracle.PNG',

                vueTechnologiesDataframe_oracle_alt: '/assets/default_profile.jpg',
                vueTechnologiesDataframe_nodejs: '/assets/nodejs.PNG',

                vueTechnologiesDataframe_nodejs_alt: '/assets/default_profile.jpg',
                vueTechnologiesDataframe_kubernetes: '/assets/kubernetes.PNG',

                vueTechnologiesDataframe_kubernetes_alt: '/assets/default_profile.jpg',
                vueTechnologiesDataframe_mysql: '/assets/mysql.PNG',

                vueTechnologiesDataframe_mysql_alt: '/assets/default_profile.jpg',
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueClientProjectDataframe: {

                vueClientProjectDataframe_clientProject_headers: [],
                vueClientProjectDataframe_clientProject_items: [],
                vueClientProjectDataframe_clientProject_selectedrow: {},
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueCreateProjectForTranslationDataframe: {
                vueCreateProjectForTranslationDataframe_project_id: "",
                vueCreateProjectForTranslationDataframe_project_name: "",
                vueCreateProjectForTranslationDataframe_project_sourceLanguage: "",

                vueCreateProjectForTranslationDataframe_project_sourceLanguage_items: [{
                    "ename": "English",
                    "id": 1
                }, {
                    "ename": "Russian",
                    "id": 2
                }, {
                    "ename": "French",
                    "id": 3
                }, {
                    "ename": "Hebrew",
                    "id": 4
                }, {
                    "ename": "Arabic",
                    "id": 5
                }, {
                    "ename": "Spanish",
                    "id": 6
                }, {
                    "ename": "Nepali",
                    "id": 7
                }],

                vueCreateProjectForTranslationDataframe_project_sourceLanguage_keys: [],

                vueCreateProjectForTranslationDataframe_project_sourceFile: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueEditTranslatedRecordsOfGridDataframe: {
                vueEditTranslatedRecordsOfGridDataframe_text_id: "",
                vueEditTranslatedRecordsOfGridDataframe_text__key: "",
                vueEditTranslatedRecordsOfGridDataframe_text_text: "",
                key_vueEditTranslatedRecordsOfGridDataframe_text_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueGridOfTranslatedTextDataframe: {
                vueGridOfTranslatedTextDataframe_translatedText_grid: {},
                vueGridOfTranslatedTextDataframe_translatedText_grid: {},

                vueGridOfTranslatedTextDataframe_translatedText_headers: [],
                vueGridOfTranslatedTextDataframe_translatedText_items: [],
                vueGridOfTranslatedTextDataframe_translatedText_selectedrow: {},
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueTranslatorDataframe: {
                vueTranslatorDataframe_project_name: "",
                vueTranslatorDataframe_project_sourceLanguage: "",
                vueTranslatorDataframe_project_languages: "",

                vueTranslatorDataframe_project_languages_items: [],

                vueTranslatorDataframe_project_languages_keys: [],

                vueTranslatorDataframe_add: "",
                vueTranslatorDataframe_project_language: "",

                vueTranslatorDataframe_project_language_items: [],

                vueTranslatorDataframe_project_language_keys: [],

                key_vueTranslatorDataframe_project_id_projectId: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueTranslatorAssistantDataframe: {
                vueTranslatorAssistantDataframe_project_list: "",

                vueTranslatorAssistantDataframe_project_list_items: [],

                vueTranslatorAssistantDataframe_project_list_keys: [],

                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroSubMenuDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroAppsDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueGettingStartedDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueCareersDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroApplicantsDataframe: {
                vueElintegroApplicantsDataframe_applicant_grid: {},

                vueElintegroApplicantsDataframe_applicant_headers: [],
                vueElintegroApplicantsDataframe_applicant_items: [],
                vueElintegroApplicantsDataframe_applicant_selectedrow: {},
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueContactUsPageDataframe: {
                vueContactUsPageDataframe_contactUs_name: "",
                vueContactUsPageDataframe_contactUs_email: "",
                vueContactUsPageDataframe_contactUs_phone: "",
                vueContactUsPageDataframe_contactUs_textOfMessage: "",
                key_vueContactUsPageDataframe_contactUs_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroLoginDataframe: {
                vueElintegroLoginDataframe_user_username: "",
                vueElintegroLoginDataframe_user_password: "",
                vueElintegroLoginDataframe_rememberMe: false,
                key_vueElintegroLoginDataframe_user_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroRegisterDataframe: {
                vueElintegroRegisterDataframe_user_email: "",
                vueElintegroRegisterDataframe_user_password: "",
                vueElintegroRegisterDataframe_password2: "",
                vueElintegroRegisterDataframe_user_firstName: "",
                vueElintegroRegisterDataframe_user_lastName: "",
                key_vueElintegroRegisterDataframe_user_id_id: "",
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroNavigationFirstTwoButtonDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroNavigationButtonBeforeLoggedInDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroNavigationButtonAfterLoggedInDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroAppBarDataframe: {
                key: '',
                doRefresh: {},
                newData: {},
            },
            vueElintegroLogoDataframe: {
                vueElintegroLogoDataframe_logo: '/assets/elintegro_logo.png',

                vueElintegroLogoDataframe_logo_alt: '/assets/default_profile.jpg',
                key: '',
                doRefresh: {},
                newData: {},
            },
            dataframeBuffer: {},
        },
        getters: {

            getState: (state)=>(stateVar)=>{
                return state[stateVar];
            }
            ,
            getVisibility: (state)=>(dataframeName)=>{
                return state.visibility[dataframeName];
            }
            ,
            getVisibilities: (state)=>{
                return state['visibility'];
            }
            ,
        },
        mutations: {

            setVisibility(state, dataframeName) {
                return state.visibility[dataframeName] = true;
            },
            unsetVisibility(state, dataframeName) {
                return state.visibility[dataframeName] = false;
            },
            updateState(state, response) {
                if (!response) {
                    return
                }

                if (typeof response === 'object' || response instanceof Map) {
                    if (!response.stateName) {
                        console.log("Error: statename missing")
                    }
                    let stateVar = state[response.stateName];
                    if (!stateVar) {
                        console.log("Error: state variable missing for this dataframe")
                    }
                    for (let i in response) {
                        console.log(i);
                        if (i === 'additionalData') {
                            const additionalData = response[i];
                            Object.keys(additionalData).forEach(function(key) {
                                const dafrKey = additionalData[key];
                                if (dafrKey.hasOwnProperty('data')) {
                                    if (dafrKey.data.hasOwnProperty('additionalData') && dafrKey.data.additionalData.data) {// Todo make recursive for handling inner additonial datas for embedded dfrs
                                    } else {}
                                } else {
                                    if (dafrKey) {
                                        const dictionary = dafrKey['dictionary'];
                                        stateVar[key + '_items'] = dictionary;
                                        const headers = dafrKey['headers'];
                                        if (headers) {
                                            stateVar[key + '_headers'] = dafrKey['headers'];
                                        } else {
                                            const selectedData = dafrKey['selectedData'];
                                            stateVar[key] = selectedData;
                                        }
                                    }
                                }

                            });
                        } else {
                            Vue.set(stateVar, i, response[i]);
                        }
                    }
                } else {
                    console.log("PupulateState() method only works for object or map as of now");
                }
            },
        },
    })
    Vue.component('vueInitDataframe', {
        name: 'vueInitDataframe',
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueInitDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueInitDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueInitDataframe-errorContainer'></div></font>
</div>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueInitDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueInitDataframe_prop', ],
        created() {
            vueInitDataframeVar = this;
            this.setInitPageValues();
            this.setupHomePage()
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueInitDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueInitDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueInitDataframe", false);
            },
            setupHomePage: function() {
                var currentUrl = window.location.href;
                var defaultUrl = 'http://localhost:8099/#/';
                if (sessionStorage.initialRefresh == null || sessionStorage.initialRefresh == undefined || sessionStorage.initialRefresh == true) {
                    if (currentUrl == defaultUrl) {
                        let homePage = "vueElintegroBannerDataframe";
                        let routeId = 0;
                        this.$router.push({
                            name: homePage,
                            path: homePage,
                            params: {
                                routeId: routeId
                            }
                        })
                    }
                    sessionStorage.initialRefresh = false;
                }
                //End of if
            },
            setInitPageValues: function() {

                axios.get('/login/getUserInfo').then(function(responseData) {
                    excon.saveToStore("vueInitDataframe", "key", '');
                    excon.saveToStore("vueElintegroProfileMenuDataframe", "key", '');
                    excon.saveToStore("vueInitDataframe", "loggedIn", responseData.data.loggedIn);
                    excon.saveToStore("loggedIn", responseData.data.loggedIn);
                    //                                                     vueInitDataframeVar.$store.state.vueInitDataframe = responseData.data;
                    //                                                     Vue.set(vueInitDataframeVar.$store.state.vueInitDataframe, "key", '');
                    //                                                     Vue.set(vueInitDataframeVar.$store.state.vueElintegroProfileMenuDataframe, "key", '');
                    var loggedIn = responseData.data.loggedIn
                    //                                                     vueInitDataframeVar.$store.state.loggedIn = loggedIn;
                    var urlLocation = window.location.href;
                    if (loggedIn == false) {//                                                        vueInitDataframeVar.$router.push("/");this.location.reload();
                    }

                }).catch(function(error) {
                    console.log(error);
                });
            },

        },
    })

    Vue.component('vueElintegroNavigationDrawerDataframe', {
        name: 'vueElintegroNavigationDrawerDataframe',
        template: `<div>
                                     <v-app-bar-nav-icon @click.stop="drawer = !drawer" class="hidden-lg-and-up"></v-app-bar-nav-icon>
                                     <v-navigation-drawer v-model="drawer"
                                         app
                                         temporary
                                         width = "min-content">
                                         <vueElintegroNavigationFirstTwoButtonDataframe/>
                                         <div style="margin-right:95px;margin-bottom: -20px;"><vueElintegroAppsDataframe/></div>
                                         <vueElintegroNavigationButtonAfterLoggedInDataframe  v-if="this.$store.state.vueInitDataframe.loggedIn"/>
                                         <vueElintegroNavigationButtonBeforeLoggedInDataframe v-else/>
                                     </v-navigation-drawer>
       </div>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueElintegroNavigationDrawerDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                drawer: false,
                group: null,
            }
        },
        props: ['vueElintegroNavigationDrawerDataframe_prop', ],
        created() {
            vueElintegroNavigationDrawerDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroNavigationDrawerDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroNavigationDrawerDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroNavigationDrawerDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroProfileMenuDataframe', {
        name: 'vueElintegroProfileMenuDataframe',
        template: `<v-flex xs12 sm12 md12 lg12 xl12 style="width:250px;" > <v-card round class='rounded-card' color="default" text id="vueElintegroProfileMenuDataframe-id" style="overflow: hidden;">
                                <v-form  ref='vueElintegroProfileMenuDataframe_form'>
                                <v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon></v-btn><span>Close</span></v-tooltip>
                                <v-flex>
						<v-flex xs12 sm12 md12 lg12 xl12  ><v-layout align-center justify-center><v-avatar :size="90" style='margin-top:0px;' color="grey lighten-4"><v-img
           id = "vueElintegroProfileMenuDataframe_person_mainPicture"
          :src="state.vueElintegroProfileMenuDataframe_person_mainPicture"

          :alt = "state.vueElintegroProfileMenuDataframe_person_mainPicture_alt"
          aspect-ratio="1.0"

           height=auto
           width=auto
           ></v-img></v-avatar></v-layout></v-flex>
	</v-flex>
                                <v-flex><v-card-actions class="justify-center"><h3>{{vueElintegroProfileMenuDataframe_person_fullName}}</h3></v-card-actions><v-card-actions class="justify-center">{{vueElintegroProfileMenuDataframe_person_email}}</v-card-actions></v-flex>
                                <v-spacer></v-spacer></v-form><br><v-flex class="text-center"><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md12 lg12 xl12 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroProfileMenuDataframe-editProfile' @click.stop='vueElintegroProfileMenuDataframe_editProfile' style='background-color:#1976D2; color:white;' >Edit Profile</v-btn>
</v-flex><v-flex xs12 sm12 md12 lg12 xl12 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroProfileMenuDataframe-Logout' @click.stop='vueElintegroProfileMenuDataframe_Logout' style='background-color:#1976D2; color:white;' >Logout</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroProfileMenuDataframe-errorContainer'></div></font>
</v-flex></v-card></v-flex>`,
        data: function() {
            return {
                vueElintegroUserProfileDataframe_data: {
                    key: ''
                },
                vueElintegroProfileMenuDataframe_person_id_rule: [v=>!!v || 'Id is required', ],
                vueElintegroProfileMenuDataframe_person_firstName_rule: [v=>!!v || 'Firstname is required', ],
                vueElintegroProfileMenuDataframe_person_lastName_rule: [v=>!!v || 'Lastname is required', ],
                vueElintegroProfileMenuDataframe_person_email_rule: [v=>!!v || 'Email is required', (v)=>/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Email is not valid.'],
                overlay_dataframe: false,
                vueElintegroProfileMenuDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroProfileMenuDataframe_prop', ],
        created() {
            this.vueElintegroProfileMenuDataframe_fillInitData();
            vueElintegroProfileMenuDataframeVar = this;
        },
        computed: {
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroProfileMenuDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroProfileMenuDataframe');
            },
            vueElintegroProfileMenuDataframe_person_fullName() {
                return excon.capitalize(this.state.vueElintegroProfileMenuDataframe_person_firstName) + " " + excon.capitalize(this.state.vueElintegroProfileMenuDataframe_person_lastName)
            },
            vueElintegroProfileMenuDataframe_person_email() {
                return this.state.vueElintegroProfileMenuDataframe_person_email
            }
        },
        watch: {
            vueElintegroProfileMenuDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroProfileMenuDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroProfileMenuDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {

            vueElintegroProfileMenuDataframe_Logout: function() {
                var allParams = {
                    'dataframe': 'vueElintegroProfileMenuDataframe'
                };

                allParams['id'] = 1;
                allParams['vueElintegroProfileMenuDataframe_person_email'] = this.state.vueElintegroProfileMenuDataframe_person_email;
                allParams['email'] = this.state.vueElintegroProfileMenuDataframe_person_email;
                allParams["key_vueElintegroProfileMenuDataframe_person_user_session_userid"] = this.key_vueElintegroProfileMenuDataframe_person_user_session_userid;

                if (this.$refs.vueElintegroProfileMenuDataframe_form.validate()) {
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/logoff',
                        params: allParams
                    }).then(function(responseData) {
                        var response = responseData.data
                        if (response.success) {
                            if (response.msg) {
                                store.commit('alertMessage', {
                                    'snackbar': true,
                                    'alert_type': 'success',
                                    'alert_message': response.msg
                                });
                            }
                            if (response.data != null && response.data != '' && response.data != undefined) {
                                self.vueElintegroProfileMenuDataframe_populateJSONData(response.data);
                            }

                        } else {
                            if (!response.error) {
                                vueElintegroProfileMenuDataframeVar.$router.push("/home/0");
                                this.location.reload();
                            }
                            if (response.msg) {
                                store.commit('alertMessage', {
                                    'snackbar': true,
                                    'alert_type': 'error',
                                    'alert_message': response.msg
                                })
                            }
                        }
                    }).catch(function(error) {
                        console.log(error);
                    });
                }

            },

            vueElintegroProfileMenuDataframe_editProfile: function(_param) {

                var routeId = this.state.vueElintegroProfileMenuDataframe_person_id;
                this.$router.push({
                    name: 'vueElintegroUserProfileDataframe',
                    path: 'vueElintegroUserProfileDataframe',
                    params: {
                        vueElintegroUserProfileDataframe: "test",
                        routeId: routeId
                    }
                })
            },

            vueElintegroProfileMenuDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroProfileMenuDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroProfileMenuDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroProfileMenuDataframe_prop.key?this.vueElintegroProfileMenuDataframe_prop.key:this.$store.state.vueElintegroProfileMenuDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroProfileMenuDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroProfileMenuDataframe";
                        vueElintegroProfileMenuDataframeVar.updateState(response);
                        vueElintegroProfileMenuDataframeVar.vueElintegroProfileMenuDataframe_populateJSONData(response);
                    }
                    var imgSrc = "profileDetail/imageData";
                    excon.saveToStore('vueElintegroProfileMenuDataframe', 'vueElintegroProfileMenuDataframe_person_mainPicture', imgSrc);
                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroProfileMenuDataframe_populateJSONData: function(response) {

                this.key_vueElintegroProfileMenuDataframe_person_user_session_userid = response['key_vueElintegroProfileMenuDataframe_person_user_session_userid'] ? response['key_vueElintegroProfileMenuDataframe_person_user_session_userid'] : ""
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroProfileMenuDataframe", false);
            },
        },
    })

    Vue.component('vueCareersPageButtonDataframe', {
        name: 'vueCareersPageButtonDataframe',
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueCareersPageButtonDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueInitDataframe-errorContainer'></div>
<div v-show="visibility.vueNewEmployeeApplicantDataframe" max-width="500px"><router-view name='vueNewEmployeeApplicantDataframe'></router-view></div></v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueCareersPageButtonDataframe-registerForNewEmployee' @click.prevent='vueCareersPageButtonDataframe_registerForNewEmployee' >Apply for a Position </v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueCareersPageButtonDataframe-errorContainer'></div></font>
</div>`,
        data: function() {
            return {
                vueNewEmployeeApplicantDataframe_data: {
                    key: ''
                },
                overlay_dataframe: false,
                vueCareersPageButtonDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueCareersPageButtonDataframe_prop', ],
        created() {
            vueCareersPageButtonDataframeVar = this;
        },
        computed: {
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueCareersPageButtonDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueCareersPageButtonDataframe');
            },

        },
        methods: {
            vueCareersPageButtonDataframe_registerForNewEmployee: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueNewEmployeeApplicantDataframe',
                    path: 'vueNewEmployeeApplicantDataframe',
                    params: {
                        vueNewEmployeeApplicantDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueCareersPageButtonDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroSubMenuDataframe', {
        name: 'vueElintegroSubMenuDataframe',
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                  <v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroSubMenuDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueElintegroSubMenuDataframe-errorContainer'></div>
<div v-show="visibility.vueTranslatorAssistantDataframe" max-width="500px"><router-view name='vueTranslatorAssistantDataframe'></router-view></div></v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroSubMenuDataframe-translator' @click.prevent='vueElintegroSubMenuDataframe_translator' style='color:#1976D2;'>translator</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroSubMenuDataframe-quizzable' @click.prevent='vueElintegroSubMenuDataframe_quizzable' style='color:#1976D2;'>quizzable</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroSubMenuDataframe-errorContainer'></div></font>

                                  </v-card></v-flex>`,
        data: function() {
            return {
                vueTranslatorAssistantDataframe_data: {
                    key: ''
                },
                overlay_dataframe: false,
                vueElintegroSubMenuDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroSubMenuDataframe_prop', ],
        created() {
            vueElintegroSubMenuDataframeVar = this;
        },
        computed: {
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroSubMenuDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroSubMenuDataframe');
            },

        },
        methods: {

            vueElintegroSubMenuDataframe_quizzable: function() {
                window.open('https://quizzable.elintegro.com/', '_blank');
            },

            vueElintegroSubMenuDataframe_translator: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueTranslatorAssistantDataframe',
                    path: 'vueTranslatorAssistantDataframe',
                    params: {
                        vueTranslatorAssistantDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroSubMenuDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroAppsDataframe', {
        name: 'vueElintegroAppsDataframe',
        template: ` <div class="text-center" >
                                 <v-menu offset-y tile z-index = 101 close-on-content-click>
                                     <template v-slot:activator="{ on, attrs }">
                                         <v-btn color="#1976D2" dark v-bind="attrs" v-on="on" text style="text-transform:capitalize;">Apps</v-btn>
                                     </template>
                                     <v-list width="min-content" style="margin-left:-10px;">
                                         <v-list-item  @click="">
                                         <v-list-item-title><vueElintegroSubMenuDataframe/></v-list-item-title>
                                         </v-list-item>
                                     </v-list>
                                 </v-menu>
        </div>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueElintegroAppsDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroAppsDataframe_prop', ],
        created() {
            vueElintegroAppsDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroAppsDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroAppsDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroAppsDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroLoginDataframe', {
        name: 'vueElintegroLoginDataframe',
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card round class="rounded-card" style="width:320px; border-radius:10px;"><v-toolbar dark color="blue darken-2"><v-toolbar-title><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>User Login</v-card-title></v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroLoginDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  >
               <v-text-field
                 label="Username"
                 placeholder = "Enter your username."
                 v-model="state.vueElintegroLoginDataframe_user_username"



                 style="width:150; height:30px;"
                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            v-model="state.vueElintegroLoginDataframe_user_password"
            :append-icon="vueElintegroLoginDataframe_user_password_show ? 'visibility_off' : 'visibility'"
            :rules = 'vueElintegroLoginDataframe_user_password_rule'
            :type="vueElintegroLoginDataframe_user_password_show ? 'text' : 'password'"
            name="input-10-1"
            label="Password *"
            autocomplete = on
            counter

            @click:append="vueElintegroLoginDataframe_user_password_show = !vueElintegroLoginDataframe_user_password_show"

            style="width:150; height:30px;"

          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-checkbox
      v-model = "state.vueElintegroLoginDataframe_rememberMe"


      label="Remember Me"




      style="width:auto; height:30px;"

     ></v-checkbox></v-flex>
	 <div id='vueElintegroLoginDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-layout align-content-space-around row wrap align-center><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md6 lg6 xl6 > <input type='image' src='/assets/icons/facebook_signin.png' id='vueElintegroLoginDataframe-logInWithFacebook' alt='logInWithFacebook' @click.prevent='vueElintegroLoginDataframe_logInWithFacebook' style="margin-top:3px;" height='43px' width='135px' />
</v-flex><v-flex xs12 sm12 md6 lg6 xl6 > <input type='image' src='/assets/icons/google_signin.png' id='vueElintegroLoginDataframe-logInWithGoogle' alt='logInWithGoogle' @click.prevent='vueElintegroLoginDataframe_logInWithGoogle' style='margin-left:-3px;' height='48px' width='135px' />
</v-flex><v-flex xs12 sm12 md6 lg6 xl6 style='margin-bottom:10px;'><v-layout column align-start justify-center><v-flex xs12 sm12 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroLoginDataframe-forgetPassword' @click.stop='vueElintegroLoginDataframe_forgetPassword' style="background-color:#1976D2; color:white; margin-left:2px;" >forgetPassword</v-btn>
</v-flex></v-layout></v-flex><v-flex xs12 sm12 md6 lg6 xl6 pa-0><v-flex xs12 sm12 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroLoginDataframe-login' @click.stop='vueElintegroLoginDataframe_login' color='blue darken-2' dark style="width: 10px; margin-left:65px;" >login</v-btn>
</v-flex></v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroLoginDataframe-errorContainer'></div></font>
</v-layout></v-card></v-flex>`,
        data: function() {
            return {
                vueElintegroLoginDataframe_user_password_rule: [v=>!!v || 'Password is required', ],

                vueElintegroLoginDataframe_user_password_show: false,
                overlay_dataframe: false,
                vueElintegroLoginDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroLoginDataframe_prop', ],
        created() {
            vueElintegroLoginDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroLoginDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroLoginDataframe');
            },

        },
        methods: {

            vueElintegroLoginDataframe_login: function() {
                var allParams = {
                    'dataframe': 'vueElintegroLoginDataframe'
                };

                allParams['id'] = 1;
                allParams['vueElintegroLoginDataframe_user_username'] = this.state.vueElintegroLoginDataframe_user_username;
                allParams['email'] = this.state.vueElintegroLoginDataframe_user_username;
                allParams["key_vueElintegroLoginDataframe_user_id_id"] = this.key_vueElintegroLoginDataframe_user_id_id;

                var elementId = '#vueElintegroLoginDataframe';
                allParams["username"] = this.state.vueElintegroLoginDataframe_user_username;
                allParams["password"] = this.state.vueElintegroLoginDataframe_user_password;
                allParams["remember-me"] = this.state.vueElintegroLoginDataframe_rememberMe;

                if (this.$refs.vueElintegroLoginDataframe_form.validate()) {
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/login/authenticate',
                        params: allParams
                    }).then(function(responseData) {
                        var response = responseData.data
                        if (response.success) {
                            if (response.msg) {
                                store.commit('alertMessage', {
                                    'snackbar': true,
                                    'alert_type': 'success',
                                    'alert_message': response.msg
                                });
                            }

                            console.log("Login Callback");
                            this.location.reload();
                            //Dataframe.showHideDataframesBasedOnUserType(data);

                        } else {
                            if (!response.error) {
                                if (!response.msg) {
                                    this.location.reload();
                                }
                            }
                            if (response.msg) {
                                store.commit('alertMessage', {
                                    'snackbar': true,
                                    'alert_type': 'error',
                                    'alert_message': response.msg
                                })
                            }
                        }
                    }).catch(function(error) {
                        console.log(error);
                    });
                }

            },

            vueElintegroLoginDataframe_forgetPassword: function() {
                console.log("Forget Password Clicked");
            },

            vueElintegroLoginDataframe_logInWithGoogle: function() {

                //                                                                                             var url = "/elintegrostartapp/oauth/authenticate/google";
                var url = "/springSecurityOAuth2/authenticate?provider=google";
                var childWindow = window.open(url, "payment", "width=500,height=500");
                /*if(childWindow){
                                                                                                window.opener.location.reload();
                                                                                                close();
                                                                                             }*/

            },

            vueElintegroLoginDataframe_logInWithFacebook: function() {

                var provider = 'facebook';
                var url = "/springSecurityOAuth2/authenticate?provider=" + provider + "";
                var childWindow = window.open(url, "payment", "width=500,height=500");

            },

            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroLoginDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroRegisterDataframe', {
        name: 'vueElintegroRegisterDataframe',
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' ><v-toolbar dark color="blue darken-2"><v-toolbar-title><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>User Registration</v-card-title></v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroRegisterDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  >
               <v-text-field
                 label="Email *"
                 placeholder = "Enter your email."
                 v-model="state.vueElintegroRegisterDataframe_user_email"
                 :rules = 'vueElintegroRegisterDataframe_user_email_rule'


                 style="width:auto; height:30px;"
                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            v-model="state.vueElintegroRegisterDataframe_user_password"
            :append-icon="vueElintegroRegisterDataframe_user_password_show ? 'visibility_off' : 'visibility'"
            :rules = 'vueElintegroRegisterDataframe_user_password_rule'
            :type="vueElintegroRegisterDataframe_user_password_show ? 'text' : 'password'"
            name="input-10-1"
            label="Password *"
            autocomplete = off
            counter

            @click:append="vueElintegroRegisterDataframe_user_password_show = !vueElintegroRegisterDataframe_user_password_show"

            style="width:150; height:30px;"

          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            v-model="state.vueElintegroRegisterDataframe_password2"
            :append-icon="vueElintegroRegisterDataframe_password2_show ? 'visibility_off' : 'visibility'"
            :rules = 'vueElintegroRegisterDataframe_password2_rule'
            :type="vueElintegroRegisterDataframe_password2_show ? 'text' : 'password'"
            name="input-10-1"
            label="Confirm Password"
            autocomplete = off
            counter

            @click:append="vueElintegroRegisterDataframe_password2_show = !vueElintegroRegisterDataframe_password2_show"

            style="width:150; height:30px;"

          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            label="Firstname *"
            v-model = "state.vueElintegroRegisterDataframe_user_firstName"
            :rules = 'vueElintegroRegisterDataframe_user_firstName_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            label="Lastname *"
            v-model = "state.vueElintegroRegisterDataframe_user_lastName"
            :rules = 'vueElintegroRegisterDataframe_user_lastName_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>
	 <div id='vueElintegroRegisterDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-flex class="text-right"><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md12 lg12 xl12 ><v-btn type='button' class='text-capitalize right' id='vueElintegroRegisterDataframe-save' @click='vueElintegroRegisterDataframe_save'  color='blue darken-2' dark :loading='vueElintegroRegisterDataframe_save_loading' >Submit</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroRegisterDataframe-errorContainer'></div></font>
</v-flex></v-card></v-flex>`,
        data: function() {
            return {
                vueElintegroRegisterDataframe_user_email_rule: [v=>!!v || 'Email is required', v=>!!v || 'Email is required.', (v)=>/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Email is not valid.'],
                vueElintegroRegisterDataframe_user_password_rule: [v=>!!v || 'Password is required', v=>!!v || 'Password is required.', v=>(v && new RegExp('^(?=.*?[#?!@%^&*-])').test(v)) || 'Enter at least one special character.', v=>(v && v.length >= 8) || 'Password must be greater than 8.', ],

                vueElintegroRegisterDataframe_user_password_show: false,
                vueElintegroRegisterDataframe_password2_rule: [v=>!!(v == this.state.vueElintegroRegisterDataframe_user_password) || 'Password and Confirm Password must be match.', ],

                vueElintegroRegisterDataframe_password2_show: false,
                vueElintegroRegisterDataframe_user_firstName_rule: [v=>!!v || 'Firstname is required', v=>!!v || 'First name is required.', v=>(v && v.length <= 30) || 'First name must be less than 30.', ],
                vueElintegroRegisterDataframe_user_lastName_rule: [v=>!!v || 'Lastname is required', v=>!!v || 'Last name is required.', v=>(v && v.length <= 30) || 'Last name must be less than 30.', ],
                overlay_dataframe: false,
                vueElintegroRegisterDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueElintegroRegisterDataframe_display: true,
                checkboxSelected: [],
            }
        },
        props: ['vueElintegroRegisterDataframe_prop', ],
        created() {
            vueElintegroRegisterDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroRegisterDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroRegisterDataframe');
            },

        },
        methods: {

            vueElintegroRegisterDataframe_save: function() {
                let allParams = this.state;
                allParams['vueElintegroRegisterDataframe_user_email'] = this.state.vueElintegroRegisterDataframe_user_email;
                allParams['email'] = this.state.vueElintegroRegisterDataframe_user_email;
                allParams["key_vueElintegroRegisterDataframe_user_id_id"] = this.key_vueElintegroRegisterDataframe_user_id_id;

                allParams['dataframe'] = 'vueElintegroRegisterDataframe';
                console.log(allParams)
                if (this.$refs.vueElintegroRegisterDataframe_form.validate()) {
                    this.vueElintegroRegisterDataframe_save_loading = true;
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/register/register',
                        data: allParams
                    }).then(function(responseData) {
                        self.vueElintegroRegisterDataframe_save_loading = false;
                        var response = responseData.data;

                        var ajaxFileSave = vueElintegroRegisterDataframeVar.params.ajaxFileSave;
                        if (ajaxFileSave) {
                            for (let i in ajaxFileSave) {
                                const value = ajaxFileSave[i];
                                allParams["key_vueElintegroRegisterDataframe_user_id_id"] = response.nodeId[0];

                                self[value.fieldName + '_ajaxFileSave'](responseData, allParams);
                            }
                        }
                        var nodeArr = response.nodeId;
                        if (nodeArr && Array.isArray(nodeArr) && nodeArr.length) {
                            excon.saveToStore("vueElintegroRegisterDataframe", "key", response.nodeId[0]);
                        }

                        excon.showAlertMessage(response);
                        if (response.success) {
                            excon.saveToStore('vueElintegroNavigationButtonDataframe', 'responseData');
                            excon.saveToStore('dataframeShowHideMaps', 'vueElintegroRegisterDataframe_display', false);

                            excon.setVisibility("vueElintegroRegisterDataframe", false);
                        }
                    }).catch(function(error) {
                        self.vueElintegroRegisterDataframe_save_loading = false;
                        console.log(error);
                    });
                }

            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroRegisterDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroNavigationFirstTwoButtonDataframe', {
        name: 'vueElintegroNavigationFirstTwoButtonDataframe',
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroNavigationFirstTwoButtonDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueElintegroNavigationFirstTwoButtonDataframe-errorContainer'></div>
<div v-show="visibility.vueClientProjectDataframe" max-width="500px"><router-view name='vueClientProjectDataframe'></router-view></div><div v-show="visibility.vueElintegroBannerDataframe" max-width="500px"><router-view name='vueElintegroBannerDataframe'></router-view></div></v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationFirstTwoButtonDataframe-home' @click.prevent='vueElintegroNavigationFirstTwoButtonDataframe_home' style='color:#1976D2;'>Home</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationFirstTwoButtonDataframe-clientsProjects' @click.prevent='vueElintegroNavigationFirstTwoButtonDataframe_clientsProjects' style='color:#1976D2;'>Clients & Projects</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroNavigationFirstTwoButtonDataframe-errorContainer'></div></font>
</div> `,
        data: function() {
            return {
                vueClientProjectDataframe_data: {
                    key: ''
                },
                vueElintegroBannerDataframe_data: {
                    key: ''
                },
                overlay_dataframe: false,
                vueElintegroNavigationFirstTwoButtonDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroNavigationFirstTwoButtonDataframe_prop', ],
        created() {
            this.vueElintegroNavigationFirstTwoButtonDataframe_fillInitData();
            vueElintegroNavigationFirstTwoButtonDataframeVar = this;
        },
        computed: {
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroNavigationFirstTwoButtonDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroNavigationFirstTwoButtonDataframe');
            },

        },
        watch: {
            vueElintegroNavigationFirstTwoButtonDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroNavigationFirstTwoButtonDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroNavigationFirstTwoButtonDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {
            vueElintegroNavigationFirstTwoButtonDataframe_clientsProjects: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueClientProjectDataframe',
                    path: 'vueClientProjectDataframe',
                    params: {
                        vueClientProjectDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueElintegroNavigationFirstTwoButtonDataframe_home: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueElintegroBannerDataframe',
                    path: 'vueElintegroBannerDataframe',
                    params: {
                        vueElintegroBannerDataframe: "test",
                        routeId: routeId
                    }
                })
            },

            vueElintegroNavigationFirstTwoButtonDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroNavigationFirstTwoButtonDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroNavigationFirstTwoButtonDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroNavigationFirstTwoButtonDataframe_prop.key?this.vueElintegroNavigationFirstTwoButtonDataframe_prop.key:this.$store.state.vueElintegroNavigationFirstTwoButtonDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroNavigationFirstTwoButtonDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroNavigationFirstTwoButtonDataframe";
                        vueElintegroNavigationFirstTwoButtonDataframeVar.updateState(response);
                        vueElintegroNavigationFirstTwoButtonDataframeVar.vueElintegroNavigationFirstTwoButtonDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroNavigationFirstTwoButtonDataframe_populateJSONData: function(response) {},
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroNavigationFirstTwoButtonDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroNavigationButtonBeforeLoggedInDataframe', {
        name: 'vueElintegroNavigationButtonBeforeLoggedInDataframe',
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroNavigationButtonBeforeLoggedInDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueElintegroNavigationFirstTwoButtonDataframe-errorContainer'></div>
<v-dialog v-model="visibility.vueElintegroRegisterDataframe"   width='initial' max-width='500px'><vueElintegroRegisterDataframe ref='vueElintegroRegisterDataframe_ref' :vueElintegroRegisterDataframe_prop='vueElintegroRegisterDataframe_data' resetForm=true></vueElintegroRegisterDataframe></v-dialog><v-dialog v-model="visibility.vueElintegroLoginDataframe"   width='initial' max-width='500px'><vueElintegroLoginDataframe ref='vueElintegroLoginDataframe_ref' :vueElintegroLoginDataframe_prop='vueElintegroLoginDataframe_data' resetForm=true></vueElintegroLoginDataframe></v-dialog><div v-show="visibility.vueContactUsPageDataframe" max-width="500px"><router-view name='vueContactUsPageDataframe'></router-view></div><div v-show="visibility.vueCareersDataframe" max-width="500px"><router-view name='vueCareersDataframe'></router-view></div><div v-show="visibility.vueGettingStartedDataframe" max-width="500px"><router-view name='vueGettingStartedDataframe'></router-view></div><div v-show="visibility.vueTechnologiesDataframe" max-width="500px"><router-view name='vueTechnologiesDataframe'></router-view></div></v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center  pa-2>
<v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonBeforeLoggedInDataframe-technologies' @click.prevent='vueElintegroNavigationButtonBeforeLoggedInDataframe_technologies' style='color:#1976D2;'>technologies</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonBeforeLoggedInDataframe-gettingStarted' @click.prevent='vueElintegroNavigationButtonBeforeLoggedInDataframe_gettingStarted' style='color:#1976D2;'>Getting Started</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonBeforeLoggedInDataframe-careers' @click.prevent='vueElintegroNavigationButtonBeforeLoggedInDataframe_careers' style='color:#1976D2;'>Careers</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonBeforeLoggedInDataframe-contactUs' @click.prevent='vueElintegroNavigationButtonBeforeLoggedInDataframe_contactUs' style='color:#1976D2;'>Contact Us</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonBeforeLoggedInDataframe-login' @click.prevent='vueElintegroNavigationButtonBeforeLoggedInDataframe_login' style='color:#1976D2;'>Login</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonBeforeLoggedInDataframe-register' @click.prevent='vueElintegroNavigationButtonBeforeLoggedInDataframe_register' style='color:#1976D2;'>Register</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroNavigationButtonBeforeLoggedInDataframe-errorContainer'></div></font>
</div> `,
        data: function() {
            return {
                vueElintegroRegisterDataframe_data: {
                    key: ''
                },
                vueElintegroLoginDataframe_data: {
                    key: ''
                },
                vueContactUsPageDataframe_data: {
                    key: ''
                },
                vueCareersDataframe_data: {
                    key: ''
                },
                vueGettingStartedDataframe_data: {
                    key: ''
                },
                vueTechnologiesDataframe_data: {
                    key: ''
                },
                overlay_dataframe: false,
                vueElintegroNavigationButtonBeforeLoggedInDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroNavigationButtonBeforeLoggedInDataframe_prop', ],
        created() {
            this.vueElintegroNavigationButtonBeforeLoggedInDataframe_fillInitData();
            vueElintegroNavigationButtonBeforeLoggedInDataframeVar = this;
        },
        computed: {
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroNavigationButtonBeforeLoggedInDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroNavigationButtonBeforeLoggedInDataframe');
            },

        },
        watch: {
            vueElintegroNavigationButtonBeforeLoggedInDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroNavigationButtonBeforeLoggedInDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroNavigationButtonBeforeLoggedInDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {
            vueElintegroNavigationButtonBeforeLoggedInDataframe_register: function() {

                //todo add if refDataframe exist but route is not defined. remove the following code if its scope is limited.
                excon.setVisibility("vueElintegroRegisterDataframe", true);
            },
            vueElintegroNavigationButtonBeforeLoggedInDataframe_login: function() {

                //todo add if refDataframe exist but route is not defined. remove the following code if its scope is limited.
                excon.setVisibility("vueElintegroLoginDataframe", true);
            },
            vueElintegroNavigationButtonBeforeLoggedInDataframe_contactUs: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueContactUsPageDataframe',
                    path: 'vueContactUsPageDataframe',
                    params: {
                        vueContactUsPageDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueElintegroNavigationButtonBeforeLoggedInDataframe_careers: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueCareersDataframe',
                    path: 'vueCareersDataframe',
                    params: {
                        vueCareersDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueElintegroNavigationButtonBeforeLoggedInDataframe_gettingStarted: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueGettingStartedDataframe',
                    path: 'vueGettingStartedDataframe',
                    params: {
                        vueGettingStartedDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueElintegroNavigationButtonBeforeLoggedInDataframe_technologies: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueTechnologiesDataframe',
                    path: 'vueTechnologiesDataframe',
                    params: {
                        vueTechnologiesDataframe: "test",
                        routeId: routeId
                    }
                })
            },

            vueElintegroNavigationButtonBeforeLoggedInDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroNavigationButtonBeforeLoggedInDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroNavigationButtonBeforeLoggedInDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroNavigationButtonBeforeLoggedInDataframe_prop.key?this.vueElintegroNavigationButtonBeforeLoggedInDataframe_prop.key:this.$store.state.vueElintegroNavigationButtonBeforeLoggedInDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroNavigationButtonBeforeLoggedInDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroNavigationButtonBeforeLoggedInDataframe";
                        vueElintegroNavigationButtonBeforeLoggedInDataframeVar.updateState(response);
                        vueElintegroNavigationButtonBeforeLoggedInDataframeVar.vueElintegroNavigationButtonBeforeLoggedInDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroNavigationButtonBeforeLoggedInDataframe_populateJSONData: function(response) {},
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroNavigationButtonBeforeLoggedInDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroNavigationButtonAfterLoggedInDataframe', {
        name: 'vueElintegroNavigationButtonAfterLoggedInDataframe',
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroNavigationButtonAfterLoggedInDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueElintegroNavigationButtonAfterLoggedInDataframe-errorContainer'></div>
<v-dialog v-model="visibility.vueElintegroProfileMenuDataframe"   width='initial' max-width='500px'><vueElintegroProfileMenuDataframe ref='vueElintegroProfileMenuDataframe_ref' :vueElintegroProfileMenuDataframe_prop='vueElintegroProfileMenuDataframe_data' resetForm=true></vueElintegroProfileMenuDataframe></v-dialog><div v-show="visibility.vueContactUsPageDataframe" max-width="500px"><router-view name='vueContactUsPageDataframe'></router-view></div><div v-show="visibility.vueElintegroApplicantsDataframe" max-width="500px"><router-view name='vueElintegroApplicantsDataframe'></router-view></div><div v-show="visibility.vueCareersDataframe" max-width="500px"><router-view name='vueCareersDataframe'></router-view></div><div v-show="visibility.vueGettingStartedDataframe" max-width="500px"><router-view name='vueGettingStartedDataframe'></router-view></div><div v-show="visibility.vueTechnologiesDataframe" max-width="500px"><router-view name='vueTechnologiesDataframe'></router-view></div></v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center  pa-2>
<v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonAfterLoggedInDataframe-technologies' @click.prevent='vueElintegroNavigationButtonAfterLoggedInDataframe_technologies' style='color:#1976D2;'>technologies</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonAfterLoggedInDataframe-gettingStarted' @click.prevent='vueElintegroNavigationButtonAfterLoggedInDataframe_gettingStarted' style='color:#1976D2;'>Getting Started</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonAfterLoggedInDataframe-careers' @click.prevent='vueElintegroNavigationButtonAfterLoggedInDataframe_careers' style='color:#1976D2;'>Careers</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonAfterLoggedInDataframe-contactUs' @click.prevent='vueElintegroNavigationButtonAfterLoggedInDataframe_contactUs' style='color:#1976D2;'>Contact Us</v-btn>
</v-flex><v-flex xs0 sm0 md0 lg0 xl0 > <v-btn href='null' class='text-capitalize ' text id='vueElintegroNavigationButtonAfterLoggedInDataframe-myProfile' @click.prevent='vueElintegroNavigationButtonAfterLoggedInDataframe_myProfile' style='color:#1976D2;'>Profile</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroNavigationButtonAfterLoggedInDataframe-errorContainer'></div></font>
</div> `,
        data: function() {
            return {
                vueElintegroProfileMenuDataframe_data: {
                    key: ''
                },
                vueContactUsPageDataframe_data: {
                    key: ''
                },
                vueElintegroApplicantsDataframe_data: {
                    key: ''
                },
                vueCareersDataframe_data: {
                    key: ''
                },
                vueGettingStartedDataframe_data: {
                    key: ''
                },
                vueTechnologiesDataframe_data: {
                    key: ''
                },
                overlay_dataframe: false,
                vueElintegroNavigationButtonAfterLoggedInDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroNavigationButtonAfterLoggedInDataframe_prop', ],
        created() {
            this.vueElintegroNavigationButtonAfterLoggedInDataframe_fillInitData();
            vueElintegroNavigationButtonAfterLoggedInDataframeVar = this;
        },
        computed: {
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroNavigationButtonAfterLoggedInDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroNavigationButtonAfterLoggedInDataframe');
            },

        },
        watch: {
            vueElintegroNavigationButtonAfterLoggedInDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroNavigationButtonAfterLoggedInDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroNavigationButtonAfterLoggedInDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {
            vueElintegroNavigationButtonAfterLoggedInDataframe_myProfile: function() {

                //todo add if refDataframe exist but route is not defined. remove the following code if its scope is limited.
                excon.setVisibility("vueElintegroProfileMenuDataframe", true);
            },
            vueElintegroNavigationButtonAfterLoggedInDataframe_contactUs: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueContactUsPageDataframe',
                    path: 'vueContactUsPageDataframe',
                    params: {
                        vueContactUsPageDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueElintegroNavigationButtonAfterLoggedInDataframe_applicants: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueElintegroApplicantsDataframe',
                    path: 'vueElintegroApplicantsDataframe',
                    params: {
                        vueElintegroApplicantsDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueElintegroNavigationButtonAfterLoggedInDataframe_careers: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueCareersDataframe',
                    path: 'vueCareersDataframe',
                    params: {
                        vueCareersDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueElintegroNavigationButtonAfterLoggedInDataframe_gettingStarted: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueGettingStartedDataframe',
                    path: 'vueGettingStartedDataframe',
                    params: {
                        vueGettingStartedDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueElintegroNavigationButtonAfterLoggedInDataframe_technologies: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueTechnologiesDataframe',
                    path: 'vueTechnologiesDataframe',
                    params: {
                        vueTechnologiesDataframe: "test",
                        routeId: routeId
                    }
                })
            },

            vueElintegroNavigationButtonAfterLoggedInDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroNavigationButtonAfterLoggedInDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroNavigationButtonAfterLoggedInDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroNavigationButtonAfterLoggedInDataframe_prop.key?this.vueElintegroNavigationButtonAfterLoggedInDataframe_prop.key:this.$store.state.vueElintegroNavigationButtonAfterLoggedInDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroNavigationButtonAfterLoggedInDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroNavigationButtonAfterLoggedInDataframe";
                        vueElintegroNavigationButtonAfterLoggedInDataframeVar.updateState(response);
                        vueElintegroNavigationButtonAfterLoggedInDataframeVar.vueElintegroNavigationButtonAfterLoggedInDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroNavigationButtonAfterLoggedInDataframe_populateJSONData: function(response) {},
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroNavigationButtonAfterLoggedInDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroAppBarDataframe', {
        name: 'vueElintegroAppBarDataframe',
        template: `<v-flex>
                                <v-toolbar-items >
                                  <vueElintegroNavigationFirstTwoButtonDataframe/>
                                  <div style="padding: 40px 0px 0px 0px;"><vueElintegroAppsDataframe/></div>
                                  <vueElintegroNavigationButtonAfterLoggedInDataframe  v-if="this.$store.state.vueInitDataframe.loggedIn"/>
                                  <vueElintegroNavigationButtonBeforeLoggedInDataframe v-else/>
                                </v-toolbar-items>
        </v-flex> `,
        data: function() {
            return {
                overlay_dataframe: false,
                vueElintegroAppBarDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroAppBarDataframe_prop', ],
        created() {
            vueElintegroAppBarDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroAppBarDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroAppBarDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroAppBarDataframe", false);
            },
        },
    })

    Vue.component('vueElintegroLogoDataframe', {
        name: 'vueElintegroLogoDataframe',
        template: `<span><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroLogoDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-img
           id = "vueElintegroLogoDataframe_logo"
          :src="state.vueElintegroLogoDataframe_logo"

          :alt = "state.vueElintegroLogoDataframe_logo_alt"
          aspect-ratio="2.75"

           height=auto
           width=200
           contain  ></v-img></v-flex>
	 <div id='vueElintegroBannerDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
</span>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueElintegroLogoDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroLogoDataframe_prop', ],
        created() {
            vueElintegroLogoDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroLogoDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroLogoDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroLogoDataframe", false);
            },
        },
    })

    const vueElintegroBannerDataframeComp = {
        template: `<span><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroBannerDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-img
           id = "vueElintegroBannerDataframe_banner"
          :src="state.vueElintegroBannerDataframe_banner"

          :alt = "state.vueElintegroBannerDataframe_banner_alt"
          aspect-ratio="2.75"

           height=auto
           width=auto
           ></v-img></v-flex>
	 <div id='vueElintegroBannerDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
</span>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueElintegroBannerDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroBannerDataframe_prop', ],
        created() {
            vueElintegroBannerDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroBannerDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroBannerDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroBannerDataframe", false);
            },
        },
    }

    const vueNewEmployeeBasicInformationDataframeComp = {
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueNewEmployeeBasicInformationDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            label="First Name *"
            v-model = "state.vueNewEmployeeBasicInformationDataframe_person_firstName"
            :rules = 'vueNewEmployeeBasicInformationDataframe_person_firstName_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            label="Last Name *"
            v-model = "state.vueNewEmployeeBasicInformationDataframe_person_lastName"
            :rules = 'vueNewEmployeeBasicInformationDataframe_person_lastName_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  >
               <v-text-field
                 label="Email *"
                 placeholder = "Enter your email."
                 v-model="state.vueNewEmployeeBasicInformationDataframe_person_email"
                 :rules = 'vueNewEmployeeBasicInformationDataframe_person_email_rule'


                 style="width:auto; height:30px;"
                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  >
               <v-text-field
                 label="Phone"
                 v-model="state.vueNewEmployeeBasicInformationDataframe_person_phone"
                 :rules = "vueNewEmployeeBasicInformationDataframe_person_phone_rule"


                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            label="Linkedin"
            v-model = "state.vueNewEmployeeBasicInformationDataframe_application_linkedin"




            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  >
            <v-combobox
          v-model = "state.vueNewEmployeeBasicInformationDataframe_person_availablePosition"
          :items="state.vueNewEmployeeBasicInformationDataframe_person_availablePosition_items"

          label="Available Positions"

          item-text="name"
          item-value="id"
          small-chips
          multiple
          hide-no-data
          hide-selected




        ></v-combobox>
        </v-flex>
	 <div id='vueNewEmployeeBasicInformationDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-flex class="text-right"><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md1 lg1 xl1 > <v-btn  class='text-capitalize ' type='button' id='vueNewEmployeeBasicInformationDataframe-next' @click.stop='vueNewEmployeeBasicInformationDataframe_next' style='background-color:#1976D2; color:white;' >next</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueNewEmployeeBasicInformationDataframe-errorContainer'></div></font>
</v-flex></div>`,
        data: function() {
            return {
                vueNewEmployeeBasicInformationDataframe_person_firstName_rule: [v=>!!v || 'First Name is required', ],
                vueNewEmployeeBasicInformationDataframe_person_lastName_rule: [v=>!!v || 'Last Name is required', ],
                vueNewEmployeeBasicInformationDataframe_person_email_rule: [v=>!!v || 'Email is required', (v)=>/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Email is not valid.'],
                vueNewEmployeeBasicInformationDataframe_person_phone_rule: [(v)=>/^(([(]?(\d{2,4})[)]?)|(\d{2,4})|([+1-9]+\d{1,2}))?[-\s]?(\d{2,3})?[-\s]?((\d{7,8})|(\d{3,4}[-\s]\d{3,4}))$/.test(v) || 'Phone number must be valid.'],

                vueNewEmployeeBasicInformationDataframe_person_availablePosition_search: null,

                overlay_dataframe: false,
                vueNewEmployeeBasicInformationDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueNewEmployeeBasicInformationDataframe_prop', ],
        created() {
            vueNewEmployeeBasicInformationDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueNewEmployeeBasicInformationDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueNewEmployeeBasicInformationDataframe');
            },

        },
        methods: {

            vueNewEmployeeBasicInformationDataframe_next: function() {
                this.newEmployeeBasicInformation()
            },

            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueNewEmployeeBasicInformationDataframe", false);
            },

            newEmployeeBasicInformation() {
                console.log("Inside employeeinformation")
                var details = this.state.vueNewEmployeeBasicInformationDataframe
                console.log(details)
                var allParams = this.state;
                //allParams['firstName'] = this.state.vueNewEmployeeBasicInformationDataframe_person_firstName;
                //allParams['lastName'] = this.state.vueNewEmployeeBasicInformationDataframe_person_lastName;
                //allParams['email'] = this.state.vueNewEmployeeBasicInformationDataframe_person_email;
                //allParams['phone'] = this.state.vueNewEmployeeBasicInformationDataframe_person_phone;
                //allParams['linkedin'] = this.state.vueNewEmployeeBasicInformationDataframe_application_linkedin;
                //allParams['availablePosition'] = this.state.vueNewEmployeeBasicInformationDataframe_person_availablePosition;
                allParams['dataframe'] = 'vueNewEmployeeBasicInformationDataframe';
                console.log(allParams)

                console.log("do you see all params?")
                if (this.$refs.vueNewEmployeeBasicInformationDataframe_form.validate()) {
                    axios({
                        method: 'post',
                        url: '/EmployeeApplication/createApplicant',
                        data: allParams
                    }).then(function(responseData) {
                        var response = responseData;
                        //excon.saveToStore("vueNewEmployeeUploadResumeDataframe","vueNewEmployeeUploadResumeDataframe_resume_id",response.data.id)
                        //Here is I put generated keys to the Vue component Store of this Vue component (dataframe) in order to other dataframes be
                        // able to use them to complete the data for the same records...
                        //excon.saveToStore("vueNewEmployeeUploadResumeDataframe","key_person_id",response.data.person_id)
                        //excon.saveToStore("vueNewEmployeeUploadResumeDataframe","key_application_id",response.data.application_id)
                        excon.saveToStore("vueNewEmployeeBasicInformationDataframe", "key_person_id", response.data.person_id)
                        excon.saveToStore("vueNewEmployeeBasicInformationDataframe", "key_application_id", response.data.application_id)
                        console.log(response)
                    });

                    excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeUploadResumeDataframe-tab-id");
                } else {
                    alert("Error in saving")
                }
            },
        },
    }

    const vueNewEmployeeUploadResumeDataframeComp = {
        template: `<v-flex><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueNewEmployeeUploadResumeDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md6 lg6 xl6  >
              <div >
               <v-eutil-image-upload
                  :multiple= 'true'
                  @upload-success="vueNewEmployeeUploadResumeDataframe_images_uploadImages"
                  @before-remove='vueNewEmployeeUploadResumeDataframe_images_beforeRemove'
                  @edit-image='vueNewEmployeeUploadResumeDataframe_images_editImage'
                  :edit-button=true
                  :delete-button=true

                >
               </v-eutil-image-upload></div>
               </v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  >
              <div null>
               <v-file-input
                  label = "Resume"
                  multiple
                  @change = "vueNewEmployeeUploadResumeDataframe_resume_uploadFile"

                >
               </v-file-input></div>
               </v-flex>
	 <div id='vueNewEmployeeUploadResumeDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs9 sm9 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueNewEmployeeUploadResumeDataframe-previous' @click.stop='vueNewEmployeeUploadResumeDataframe_previous' style='background-color:#1976D2; color:white;' >previous</v-btn>
</v-flex><v-flex xs3 sm3 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueNewEmployeeUploadResumeDataframe-next' @click.stop='vueNewEmployeeUploadResumeDataframe_next' style='background-color:#1976D2; color:white;' >next</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueNewEmployeeUploadResumeDataframe-errorContainer'></div></font>
</v-flex>`,
        data: function() {
            return {

                vueNewEmployeeUploadResumeDataframe_images_files: [],

                vueNewEmployeeUploadResumeDataframe_resume_files: [],
                overlay_dataframe: false,
                vueNewEmployeeUploadResumeDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueNewEmployeeUploadResumeDataframe_tab_model: '',
            }
        },
        props: ['vueNewEmployeeUploadResumeDataframe_prop', ],
        created() {
            this.vueNewEmployeeUploadResumeDataframe_images_computedFileUploadParams();
            this.vueNewEmployeeUploadResumeDataframe_resume_computedFileUploadParams();
            vueNewEmployeeUploadResumeDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueNewEmployeeUploadResumeDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueNewEmployeeUploadResumeDataframe');
            },

        },
        methods: {

            vueNewEmployeeUploadResumeDataframe_images_uploadImages: function(event) {
                var detailData = event.detail;
                var fileList = detailData[3];
                this.vueNewEmployeeUploadResumeDataframe_images_files = fileList;
                excon.saveToStore('vueNewEmployeeUploadResumeDataframe', 'vueNewEmployeeUploadResumeDataframe_images', fileList[0].name);
            },

            vueNewEmployeeUploadResumeDataframe_images_beforeRemove: function(event) {
                var detailData = event.detail;
                var beforeRemove = detailData[1];
                var r = confirm("Remove image !");
                if (r == true) {
                    beforeRemove()
                    this.vueNewEmployeeUploadResumeDataframe_images_files = detailData[2];
                }
            },

            vueNewEmployeeUploadResumeDataframe_images_editImage: function(event) {
                var detailData = event.detail;
                this.vueNewEmployeeUploadResumeDataframe_images_files = detailData[3];
            },

            vueNewEmployeeUploadResumeDataframe_images_ajaxFileSave: function(data, allParams) {
                var fileList = this.vueNewEmployeeUploadResumeDataframe_images_files;
                if (fileList.length > 0) {
                    var picData = new FormData();
                    picData.append('fileSize', fileList.length);
                    picData.append('fileName', fileList[0].name);
                    picData.append('fieldnameToReload', 'vueNewEmployeeUploadResumeDataframe.application.avatar');
                    jQuery.each(allParams, function(key, value) {
                        picData.append(allParams, value);
                    });
                    picData.append('fldId', 'vueNewEmployeeUploadResumeDataframe_images');
                    for (var i = 0; i < fileList.length; i++) {
                        picData.append("vueNewEmployeeUploadResumeDataframe_images-file[" + i + "]", fileList[i]);
                    }
                    axios.post('/fileUpload/ajaxFileSave', picData).then(response=>{
                            console.log(response)
                        }
                    ).catch(function(error) {
                        console.log(error);
                    });
                }

            },

            vueNewEmployeeUploadResumeDataframe_images_computedFileUploadParams() {
                var refParams = this.params;
                var ajaxFileUploadParams = refParams['ajaxFileSave'];
                if (ajaxFileUploadParams) {
                    ajaxFileUploadParams.push({
                        fieldName: 'vueNewEmployeeUploadResumeDataframe_images'
                    })
                } else {
                    refParams['ajaxFileSave'] = [{
                        fieldName: 'vueNewEmployeeUploadResumeDataframe_images'
                    }];
                }
            },

            vueNewEmployeeUploadResumeDataframe_resume_uploadFile: function(event) {
                //TODO: for multi file this should be array and not sinfle file, change this code accordingly
                this.vueNewEmployeeUploadResumeDataframe_resume_files = event;
                //this.state.vueNewEmployeeUploadResumeDataframe_resume = fileToUpload.name; //TODO: once we find out why the v-model cannot get state.<fldName>, this line could be deleted!
                var fileToUpload = this.vueNewEmployeeUploadResumeDataframe_resume_files;
                this.state.vueNewEmployeeUploadResumeDataframe_resume = fileToUpload[0].name;
            },

            vueNewEmployeeUploadResumeDataframe_resume_ajaxFileSave: function(data, allParams) {
                var fileList = this.vueNewEmployeeUploadResumeDataframe_resume_files;
                if (fileList.length > 0) {
                    var fileData = new FormData();
                    for (var i = 0; i < fileList.length; i++) {
                        fileData.append('fileName', fileList[i].name);
                        fileData.append('fileStorageSize', fileList[i].size);
                        fileData.append('fileLastModified', fileList[i].lastModified);
                        fileData.append('fileLastModifiedDate', fileList[i].lastModifiedDate);
                        fileData.append('fileWebKitRelativePath', fileList[i].webKitRelativePath);
                        fileData.append('fileType', fileList[i].type);
                        fileData.append("vueNewEmployeeUploadResumeDataframe_resume-file[" + i + "]", fileList[i]);
                    }
                    fileData.append('fileSize', fileList.length);
                    fileData.append('fldId', 'vueNewEmployeeUploadResumeDataframe_resume');
                    if (data.params != null) {
                        fileData.append('allParams', data.params.id);
                    }
                }
                fetch('/fileUpload/ajaxFileSave', {
                    method: 'POST',
                    body: fileData
                }).then(response=>{
                        console.log(response)
                    }
                ).catch(function(error) {
                    console.log(error)
                });
            },

            vueNewEmployeeUploadResumeDataframe_resume_computedFileUploadParams() {
                var refParams = this.params;
                var ajaxFileUploadParams = refParams['ajaxFileSave'];
                if (ajaxFileUploadParams) {
                    ajaxFileUploadParams.push({
                        fieldName: 'vueNewEmployeeUploadResumeDataframe_resume'
                    })
                } else {
                    refParams['ajaxFileSave'] = [{
                        fieldName: 'vueNewEmployeeUploadResumeDataframe_resume'
                    }];
                }
            },

            vueNewEmployeeUploadResumeDataframe_next: function() {
                this.newEmployeeUploadResume()
            },

            vueNewEmployeeUploadResumeDataframe_previous: function() {
                Vue.set(this.$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeBasicInformationDataframe-tab-id");

            },

            tabClicked: function() {},
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueNewEmployeeUploadResumeDataframe", false);
            },

            newEmployeeUploadResume() {
                var allParams = this.state;
                var avatar = [];
                var pictures = this.vueNewEmployeeUploadResumeDataframe_images_files;
                for (var i = 0; i < pictures.length; i++) {
                    avatar[i] = pictures[i].name;
                }
                allParams['vueNewEmployeeUploadResumeDataframe_avatar'] = avatar;
                var doc = [];
                var files = this.vueNewEmployeeUploadResumeDataframe_resume_files;
                for (var i = 0; i < files.length; i++) {
                    doc[i] = files[i].name;
                }
                allParams['vueNewEmployeeUploadResumeDataframe_resume'] = doc;
                allParams['vueNewEmployeeUploadResumeDataframe_application_id'] = excon.getFromStore("vueNewEmployeeBasicInformationDataframe", "key_application_id")
                var self = this;
                if (this.$refs.vueNewEmployeeUploadResumeDataframe_form.validate()) {
                    axios({
                        method: 'post',
                        url: '/EmployeeApplication/applicantDocuments',
                        data: allParams
                    }).then(function(responseData) {
                        var response = responseData;
                        excon.saveToStore("vueNewEmployeeUploadResumeDataframe", "key_vueNewEmployeeUploadResumeDataframe_application_id_id", response.data['application_id']);
                        self.vueNewEmployeeUploadResumeDataframe_images_ajaxFileSave(response, allParams);
                        self.vueNewEmployeeUploadResumeDataframe_resume_ajaxFileSave(response, allParams);
                        excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeSelfAssesmentDataframe-tab-id");

                    });

                } else {
                    alert("Error in saving")
                }

            },
        },
    }

    const vueNewEmployeeApplicantEditSkillDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12 ><v-card round class='rounded-card' color="default"  style="overflow: hidden;">
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>
                                <v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueNewEmployeeApplicantEditSkillDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Skill *"
            v-model = "state.vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_skill"
            :rules = 'vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_skill_rule'

            readonly

            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Level" type="number"
            v-model = "state.vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_level"
            :rules = 'vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_level_rule'


            min=0
            max=10
            step=0

            style="width:auto; height:50px;"

          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Comment"
            v-model = "state.vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_comment"




            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>
	 <div id='vueNewEmployeeApplicantEditSkillDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>

                                <v-flex class="text-center"><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md4 lg4 xl4 ><v-btn type='button' class='text-capitalize right' id='vueNewEmployeeApplicantEditSkillDataframe-save' @click='vueNewEmployeeApplicantEditSkillDataframe_save' style='background-color:#1976D2; color:white;'  :loading='vueNewEmployeeApplicantEditSkillDataframe_save_loading' >Submit</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueNewEmployeeApplicantEditSkillDataframe-errorContainer'></div></font>
</v-flex></v-card></v-flex>`,
        data: function() {
            return {
                vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_skill_rule: [v=>!!v || 'Skill is required', ],
                vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_level_rule: [v=>(v && new RegExp('^([0-9]|1[0])$').test(v)) || 'Please enter the digits between 0 - 10.', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                overlay_dataframe: false,
                vueNewEmployeeApplicantEditSkillDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueNewEmployeeApplicantEditSkillDataframe_prop', ],
        created() {
            this.vueNewEmployeeApplicantEditSkillDataframe_fillInitData();
            vueNewEmployeeApplicantEditSkillDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueNewEmployeeApplicantEditSkillDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueNewEmployeeApplicantEditSkillDataframe');
            },
            refreshVueNewEmployeeApplicantEditSkillDataframe() {
                return this.vueNewEmployeeApplicantEditSkillDataframe_prop.key
            },
        },
        watch: {
            vueNewEmployeeApplicantEditSkillDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueNewEmployeeApplicantEditSkillDataframe_fillInitData();
                    } else {
                        console.log("vueNewEmployeeApplicantEditSkillDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            refreshVueNewEmployeeApplicantEditSkillDataframe: {
                handler: function(val, oldVal) {
                    this.vueNewEmployeeApplicantEditSkillDataframe_fillInitData();
                }
            },
        },
        methods: {

            vueNewEmployeeApplicantEditSkillDataframe_save: function() {
                let allParams = this.state;
                allParams["key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id"] = this.key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id;

                allParams['key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id'] = this.vueNewEmployeeApplicantEditSkillDataframe_prop.key
                allParams['dataframe'] = 'vueNewEmployeeApplicantEditSkillDataframe';
                console.log(allParams)
                if (this.$refs.vueNewEmployeeApplicantEditSkillDataframe_form.validate()) {
                    this.vueNewEmployeeApplicantEditSkillDataframe_save_loading = true;
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/dataframe/ajaxSave',
                        data: allParams
                    }).then(function(responseData) {
                        self.vueNewEmployeeApplicantEditSkillDataframe_save_loading = false;
                        var response = responseData.data;

                        var ajaxFileSave = vueNewEmployeeApplicantEditSkillDataframeVar.params.ajaxFileSave;
                        if (ajaxFileSave) {
                            for (let i in ajaxFileSave) {
                                const value = ajaxFileSave[i];
                                allParams["key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id"] = response.nodeId[0];

                                self[value.fieldName + '_ajaxFileSave'](responseData, allParams);
                            }
                        }
                        null

                        excon.showAlertMessage(response);
                        if (response.success) {
                            excon.setVisibility("vueNewEmployeeApplicantEditSkillDataframe", false);
                            excon.refreshDataForGrid(response, 'vueNewEmployeeSelfAssesmentDataframe', 'vueNewEmployeeSelfAssesmentDataframe_applicationSkill', 'U');

                        }
                    }).catch(function(error) {
                        self.vueNewEmployeeApplicantEditSkillDataframe_save_loading = false;
                        console.log(error);
                    });
                }

            },

            vueNewEmployeeApplicantEditSkillDataframe_fillInitData: function() {
                excon.saveToStore('vueNewEmployeeApplicantEditSkillDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueNewEmployeeApplicantEditSkillDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueNewEmployeeApplicantEditSkillDataframe_prop.key?this.vueNewEmployeeApplicantEditSkillDataframe_prop.key:this.$store.state.vueNewEmployeeApplicantEditSkillDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueNewEmployeeApplicantEditSkillDataframe';
                allParams['id'] = this.vueNewEmployeeApplicantEditSkillDataframe_prop.key
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueNewEmployeeApplicantEditSkillDataframe";
                        vueNewEmployeeApplicantEditSkillDataframeVar.updateState(response);
                        vueNewEmployeeApplicantEditSkillDataframeVar.vueNewEmployeeApplicantEditSkillDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueNewEmployeeApplicantEditSkillDataframe_populateJSONData: function(response) {

                this.key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id = response['key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id'] ? response['key_vueNewEmployeeApplicantEditSkillDataframe_applicationSkill_id_id'] : ""
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueNewEmployeeApplicantEditSkillDataframe", false);
            },
        },
    }

    const vueNewEmployeeApplicantAddSkillDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12 ><v-card round class='rounded-card' color="default"  style="overflow: hidden;">
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex>
                                <v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueNewEmployeeApplicantAddSkillDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Skill *"
            v-model = "state.vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_skill"
            :rules = 'vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_skill_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Level" type="number"
            v-model = "state.vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_level"
            :rules = 'vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_level_rule'


            min=0
            max=10
            step=0

            style="width:auto; height:50px;"

          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Comment"
            v-model = "state.vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_comment"




            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>
	 <div id='vueNewEmployeeApplicantAddSkillDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>

                                <v-flex class="text-center"><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueNewEmployeeApplicantAddSkillDataframe-save' @click.stop='vueNewEmployeeApplicantAddSkillDataframe_save' style='background-color:#1976D2; color:white;' >save</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueNewEmployeeApplicantAddSkillDataframe-errorContainer'></div></font>
</v-flex></v-card></v-flex>`,
        data: function() {
            return {
                vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_skill_rule: [v=>!!v || 'Skill is required', ],
                vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_level_rule: [v=>(v && new RegExp('^([0-9]|1[0])$').test(v)) || 'Please enter the digits between 0 - 10.', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                overlay_dataframe: false,
                vueNewEmployeeApplicantAddSkillDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueNewEmployeeApplicantAddSkillDataframe_prop', ],
        created() {
            this.vueNewEmployeeApplicantAddSkillDataframe_fillInitData();
            vueNewEmployeeApplicantAddSkillDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueNewEmployeeApplicantAddSkillDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueNewEmployeeApplicantAddSkillDataframe');
            },

        },
        watch: {
            vueNewEmployeeApplicantAddSkillDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueNewEmployeeApplicantAddSkillDataframe_fillInitData();
                    } else {
                        console.log("vueNewEmployeeApplicantAddSkillDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {

            vueNewEmployeeApplicantAddSkillDataframe_save: function() {
                this.addNewSkill();
            },

            vueNewEmployeeApplicantAddSkillDataframe_fillInitData: function() {
                excon.saveToStore('vueNewEmployeeApplicantAddSkillDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueNewEmployeeApplicantAddSkillDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueNewEmployeeApplicantAddSkillDataframe_prop.key?this.vueNewEmployeeApplicantAddSkillDataframe_prop.key:this.$store.state.vueNewEmployeeApplicantAddSkillDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueNewEmployeeApplicantAddSkillDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueNewEmployeeApplicantAddSkillDataframe";
                        vueNewEmployeeApplicantAddSkillDataframeVar.updateState(response);
                        vueNewEmployeeApplicantAddSkillDataframeVar.vueNewEmployeeApplicantAddSkillDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueNewEmployeeApplicantAddSkillDataframe_populateJSONData: function(response) {

                this.key_vueNewEmployeeApplicantAddSkillDataframe_application_id_id = response['key_vueNewEmployeeApplicantAddSkillDataframe_application_id_id'] ? response['key_vueNewEmployeeApplicantAddSkillDataframe_application_id_id'] : ""
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueNewEmployeeApplicantAddSkillDataframe", false);
            },
            addNewSkill() {
                var details = this.state.vueNewEmployeeApplicantAddSkillDataframe;
                var details = this.state.vueNewEmployeeApplicantAddSkillDataframe
                console.log(details);
                var allParams = this.state;
                var self = this;
                allParams['id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe', 'key_vueNewEmployeeUploadResumeDataframe_application_id_id')
                allParams['vueNewEmployeeApplicantAddSkillDataframe_application_id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe', 'key_vueNewEmployeeUploadResumeDataframe_application_id_id')
                allParams['dataframe'] = 'vueNewEmployeeApplicantAddSkillDataframe';
                console.log(allParams)

                axios({
                    method: 'post',
                    url: '/EmployeeApplication/addNewSkillSet',
                    data: allParams
                }).then(function(responseData) {
                    var response = responseData.data;
                    excon.setVisibility("vueNewEmployeeApplicantAddSkillDataframe", false);
                    excon.refreshDataForGrid(response, 'vueNewEmployeeSelfAssesmentDataframe', 'vueNewEmployeeSelfAssesmentDataframe_applicationSkill', 'I');
                    console.log(response)
                });
            },
        },
    }

    const vueNewEmployeeSelfAssesmentDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                 <v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueNewEmployeeSelfAssesmentDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-card v-show="vueNewEmployeeSelfAssesmentDataframe_applicationSkill_display"><v-divider/>


       <v-data-table
            :headers="state.vueNewEmployeeSelfAssesmentDataframe_applicationSkill_headers"
            :items="state.vueNewEmployeeSelfAssesmentDataframe_applicationSkill_items"
            :items-per-page="-1"

            hide-default-footer

    >


        <template slot="item" slot-scope="props">
          <tr @click.stop="vueNewEmployeeSelfAssesmentDataframe_applicationSkill_showDetailvueNewEmployeeApplicantEditSkillDataframe(props.item)" :key="props.item.AppId">

<td class='hidden text-start'>{{ props.item.AppId }}</td>
<td class='hidden text-start'>{{ props.item.Id }}</td>
<td class='text-start'>{{ props.item.Skill }}</td>
<td class='text-start'>{{ props.item.Level }}</td>
<td class='text-start'>{{ props.item.Comment }}</td>
<td class='text-start layout' @click.stop=''><v-icon small
             v-tooltip="{
                content: 'Edit row',

            }"
         class="mr-2"  @click.stop=" vueNewEmployeeSelfAssesmentDataframe_applicationSkill_editmethod(props.item);">edit</v-icon></td>
          </tr>
        </template>

    </v-data-table></v-card>
        <v-dialog v-model="visibility.vueNewEmployeeApplicantEditSkillDataframe" width='auto' max-width='500px' ><component :is='vueNewEmployeeApplicantEditSkillDataframe_comp' ref='vueNewEmployeeApplicantEditSkillDataframe_ref' :vueNewEmployeeApplicantEditSkillDataframe_prop="vueNewEmployeeApplicantEditSkillDataframe_data"></component></v-dialog><v-dialog v-model="visibility.vueNewEmployeeApplicantEditSkillDataframe" width='auto' max-width='500' ><component :is='vueNewEmployeeApplicantEditSkillDataframe_comp' ref='vueNewEmployeeApplicantEditSkillDataframe_ref' :vueNewEmployeeApplicantEditSkillDataframe_prop="vueNewEmployeeApplicantEditSkillDataframe_data"></component></v-dialog>
</v-flex>
	 <div id='vueNewEmployeeSelfAssesmentDataframe-errorContainer'></div>
<v-dialog v-model="visibility.vueNewEmployeeApplicantAddSkillDataframe"   width='initial' max-width='500px'><vueNewEmployeeApplicantAddSkillDataframe ref='vueNewEmployeeApplicantAddSkillDataframe_ref' :vueNewEmployeeApplicantAddSkillDataframe_prop='vueNewEmployeeApplicantAddSkillDataframe_data' resetForm=true></vueNewEmployeeApplicantAddSkillDataframe></v-dialog></v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs4 sm4 md4 lg4 xl4 > <v-btn  class='text-capitalize ' type='button' id='vueNewEmployeeSelfAssesmentDataframe-previous' @click.stop='vueNewEmployeeSelfAssesmentDataframe_previous' style='background-color:#1976D2; color:white;' >previous</v-btn>
</v-flex><v-flex xs4 sm4 md4 lg4 xl4 > <v-btn  class='text-capitalize ' type='button' id='vueNewEmployeeSelfAssesmentDataframe-addSkill' @click.stop='vueNewEmployeeSelfAssesmentDataframe_addSkill' style='background-color:#1976D2; color:white;' >Add Skill</v-btn>
</v-flex><v-flex xs4 sm4 md4 lg4 xl4 > <v-btn  class='text-capitalize ' type='button' id='vueNewEmployeeSelfAssesmentDataframe-next' @click.stop='vueNewEmployeeSelfAssesmentDataframe_next' style='background-color:#1976D2; color:white;' >next</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueNewEmployeeSelfAssesmentDataframe-errorContainer'></div></font>

                                 </v-card></v-flex>`,
        data: function() {
            return {
                vueNewEmployeeApplicantEditSkillDataframe_data: {
                    key: '',
                    refreshGrid: true,
                    parentData: {}
                },
                vueNewEmployeeApplicantEditSkillDataframe_comp: '',
                vueNewEmployeeApplicantEditSkillDataframe_data: {
                    key: '',
                    refreshGrid: true,
                    parentData: {}
                },
                vueNewEmployeeApplicantEditSkillDataframe_comp: '',
                vueNewEmployeeApplicantAddSkillDataframe_data: {
                    key: ''
                },

                drag: '',

                gridDataframes: {
                    vueNewEmployeeApplicantEditSkillDataframe_display: false,
                    vueNewEmployeeApplicantEditSkillDataframe_display: false,
                },
                overlay_dataframe: false,
                vueNewEmployeeSelfAssesmentDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueNewEmployeeSelfAssesmentDataframe_tab_model: '',
            }
        },
        props: ['vueNewEmployeeSelfAssesmentDataframe_prop', ],
        components: {
            'vueNewEmployeeApplicantEditSkillDataframe': vueNewEmployeeApplicantEditSkillDataframeComp,
            'vueNewEmployeeApplicantAddSkillDataframe': vueNewEmployeeApplicantAddSkillDataframeComp,
        },
        created() {
            vueNewEmployeeSelfAssesmentDataframeVar = this;
            this.fillApplicationSkillTable();
        },
        computed: {
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            vueNewEmployeeSelfAssesmentDataframe_applicationSkill_display: function() {
                if (this.state.vueNewEmployeeSelfAssesmentDataframe_applicationSkill_items.length) {
                    return true;
                }
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueNewEmployeeSelfAssesmentDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueNewEmployeeSelfAssesmentDataframe');
            },

        },
        watch: {
            vueNewEmployeeSelfAssesmentDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueNewEmployeeSelfAssesmentDataframe_fillInitData();
                    } else {
                        console.log("vueNewEmployeeSelfAssesmentDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {
            vueNewEmployeeApplicantEditSkillDataframe_updateStore: function(data) {
                Vue.set(this.vueNewEmployeeApplicantEditSkillDataframe_data, 'parentData', data);
            },
            vueNewEmployeeSelfAssesmentDataframe_applicationSkill_showDetailvueNewEmployeeApplicantEditSkillDataframe: function(dataRecord) {

                this.vueNewEmployeeApplicantEditSkillDataframe_updateStore(dataRecord);
                this.vueNewEmployeeApplicantEditSkillDataframe_comp = "";
                this.vueNewEmployeeApplicantEditSkillDataframe_comp = "vueNewEmployeeApplicantEditSkillDataframe";
                var key = dataRecord.id ? dataRecord.id : (dataRecord.Id | dataRecord.ID);
                Vue.set(this.vueNewEmployeeApplicantEditSkillDataframe_data, 'key', key);
                Vue.set(this.vueNewEmployeeApplicantEditSkillDataframe_data, 'refreshInitialData', false);
                excon.saveToStore('vueNewEmployeeSelfAssesmentDataframe', 'vueNewEmployeeSelfAssesmentDataframe_applicationSkill_selectedrow', dataRecord);
                excon.setVisibility("vueNewEmployeeApplicantEditSkillDataframe", true);

            },
            vueNewEmployeeApplicantEditSkillDataframe_updateStore: function(data) {
                Vue.set(this.vueNewEmployeeApplicantEditSkillDataframe_data, 'parentData', data);
            },
            vueNewEmployeeSelfAssesmentDataframe_applicationSkill_editmethod: function(dataRecord) {

                this.vueNewEmployeeApplicantEditSkillDataframe_updateStore(dataRecord);
                this.vueNewEmployeeApplicantEditSkillDataframe_comp = "";
                this.vueNewEmployeeApplicantEditSkillDataframe_comp = "vueNewEmployeeApplicantEditSkillDataframe";
                var key = dataRecord.id ? dataRecord.id : (dataRecord.Id | dataRecord.ID);
                Vue.set(this.vueNewEmployeeApplicantEditSkillDataframe_data, 'key', key);
                Vue.set(this.vueNewEmployeeApplicantEditSkillDataframe_data, 'refreshInitialData', false);
                excon.saveToStore('vueNewEmployeeSelfAssesmentDataframe', 'vueNewEmployeeSelfAssesmentDataframe_applicationSkill_selectedrow', dataRecord);
                excon.setVisibility("vueNewEmployeeApplicantEditSkillDataframe", true);

            },
            getDefaultDataHeaders_vueNewEmployeeSelfAssesmentDataframe_applicationSkill: function() {

                var defaultDataHeaders = [{
                    "text": "AppId",
                    "keys": "id",
                    "value": "AppId",
                    "class": "hidden text-start",
                    "width": ""
                }, {
                    "text": "Id",
                    "keys": "id",
                    "value": "Id",
                    "class": "hidden text-start",
                    "width": ""
                }, {
                    "text": "Skill",
                    "keys": "skill",
                    "value": "Skill",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Level",
                    "keys": "level",
                    "value": "Level",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Comment",
                    "keys": "comment",
                    "value": "Comment",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Edit Skill",
                    "keys": "Edit Skill",
                    "value": "name",
                    "sortable": false
                }];
                this.state.vueNewEmployeeSelfAssesmentDataframe_applicationSkill_headers = defaultDataHeaders;
            },

            vueNewEmployeeSelfAssesmentDataframe_next: function() {
                excon.saveToStore('vueNewEmployeeApplicantDataframe', 'vueNewEmployeeApplicantDataframe_tab_model', 'vueNewEmployeeAddtionalQuestionsDataframe-tab-id');
            },

            vueNewEmployeeSelfAssesmentDataframe_addSkill: function() {

                //todo add if refDataframe exist but route is not defined. remove the following code if its scope is limited.
                excon.setVisibility("vueNewEmployeeApplicantAddSkillDataframe", true);
            },

            vueNewEmployeeSelfAssesmentDataframe_previous: function() {
                excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeUploadResumeDataframe-tab-id");

            },

            vueNewEmployeeSelfAssesmentDataframe_fillInitData: function() {
                excon.saveToStore('vueNewEmployeeSelfAssesmentDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueNewEmployeeSelfAssesmentDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueNewEmployeeSelfAssesmentDataframe_prop.key?this.vueNewEmployeeSelfAssesmentDataframe_prop.key:this.$store.state.vueNewEmployeeSelfAssesmentDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueNewEmployeeSelfAssesmentDataframe';
                allParams['id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe', 'key_vueNewEmployeeUploadResumeDataframe_application_id_id');

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueNewEmployeeSelfAssesmentDataframe";
                        vueNewEmployeeSelfAssesmentDataframeVar.updateState(response);
                        vueNewEmployeeSelfAssesmentDataframeVar.vueNewEmployeeSelfAssesmentDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueNewEmployeeSelfAssesmentDataframe_populateJSONData: function(response) {
            },
            tabClicked: function() {
                this.vueNewEmployeeSelfAssesmentDataframe_fillInitData();
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueNewEmployeeSelfAssesmentDataframe", false);
            },

            fillApplicationSkillTable() {
                var details = this.state.vueNewEmployeeSelfAssesmentDataframe
                console.log(details);
                var allParams = {};
                var self = this;
                allParams['id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe', 'key_vueNewEmployeeUploadResumeDataframe_application_id_id')

                allParams['dataframe'] = 'vueNewEmployeeSelfAssesmentDataframe';
                console.log(allParams)
                axios({
                    method: 'post',
                    url: '/EmployeeApplication/initiateSkillSet',
                    data: allParams
                }).then(function(responseData) {
                    self.vueNewEmployeeSelfAssesmentDataframe_fillInitData();

                    var response = responseData;
                    console.log(response)

                });

            },
        },
    }

    const vueNewEmployeeThankYouMessageAfterSaveDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12>
                               <v-row><v-col cols="4"></v-col><v-col cols="4"><v-card-actions class="justify-center"><h1>Thank You !!!</h1></v-card-actions></v-col><v-col cols="4"></v-col></v-row>
                               <v-row><v-col cols="4"></v-col><v-col cols="4"><v-flex class="text-center"><h3>{{vueNewEmployeeThankYouMessageAfterSaveDataframe_person_fullName}}, your application has been received.</h3></v-flex></v-col><v-col cols="4"></v-col></v-row>
                                <v-row><v-col cols="4"></v-col><v-col cols="4"><v-flex class="text-center"><h3>We will contact you shortly.</h3></v-flex></v-col><v-col cols="4"></v-col></v-row>
                                <v-row><v-col cols="4"></v-col><v-col cols="4"><v-flex class="text-center"><h5>If you have additional questions, please send us an email to contact:<h4>hr@elintegro.com</h4></h5></v-flex></v-col><v-col cols="4"></v-col></v-row>
                             <v-flex><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueNewEmployeeThankYouMessageAfterSaveDataframe-errorContainer'></div></font>
</v-flex>
                                </v-flex>`,
        data: function() {
            return {
                vueNewEmployeeThankYouMessageAfterSaveDataframe_person_firstName_rule: [v=>!!v || 'Firstname is required', ],
                vueNewEmployeeThankYouMessageAfterSaveDataframe_person_lastName_rule: [v=>!!v || 'Lastname is required', ],
                overlay_dataframe: false,
                vueNewEmployeeThankYouMessageAfterSaveDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueNewEmployeeThankYouMessageAfterSaveDataframe_prop', ],
        created() {
            this.vueNewEmployeeThankYouMessageAfterSaveDataframe_fillInitData();
            vueNewEmployeeThankYouMessageAfterSaveDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueNewEmployeeThankYouMessageAfterSaveDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueNewEmployeeThankYouMessageAfterSaveDataframe');
            },
            vueNewEmployeeThankYouMessageAfterSaveDataframe_person_fullName() {
                return excon.capitalize(this.state.vueNewEmployeeThankYouMessageAfterSaveDataframe_person_firstName) + " " + excon.capitalize(this.state.vueNewEmployeeThankYouMessageAfterSaveDataframe_person_lastName)
            }
        },
        watch: {
            vueNewEmployeeThankYouMessageAfterSaveDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueNewEmployeeThankYouMessageAfterSaveDataframe_fillInitData();
                    } else {
                        console.log("vueNewEmployeeThankYouMessageAfterSaveDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {

            vueNewEmployeeThankYouMessageAfterSaveDataframe_fillInitData: function() {
                excon.saveToStore('vueNewEmployeeThankYouMessageAfterSaveDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueNewEmployeeThankYouMessageAfterSaveDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueNewEmployeeThankYouMessageAfterSaveDataframe_prop.key?this.vueNewEmployeeThankYouMessageAfterSaveDataframe_prop.key:this.$store.state.vueNewEmployeeThankYouMessageAfterSaveDataframe.key";
                    }
                }
                allParams['id'] = this.$route.params.routeId ? this.$route.params.routeId : 1;
                allParams['dataframe'] = 'vueNewEmployeeThankYouMessageAfterSaveDataframe';
                allParams['id'] = excon.getFromStore('vueNewEmployeeAddtionalQuestionsDataframe', 'key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id');
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueNewEmployeeThankYouMessageAfterSaveDataframe";
                        vueNewEmployeeThankYouMessageAfterSaveDataframeVar.updateState(response);
                        vueNewEmployeeThankYouMessageAfterSaveDataframeVar.vueNewEmployeeThankYouMessageAfterSaveDataframe_populateJSONData(response);
                    }
                    setTimeout(function() {
                        self.$router.push("/home/0");
                        this.location.reload();
                    }, 10000);
                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueNewEmployeeThankYouMessageAfterSaveDataframe_populateJSONData: function(response) {

                this.key_vueNewEmployeeThankYouMessageAfterSaveDataframe_application_id_id = response['key_vueNewEmployeeThankYouMessageAfterSaveDataframe_application_id_id'] ? response['key_vueNewEmployeeThankYouMessageAfterSaveDataframe_application_id_id'] : ""
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueNewEmployeeThankYouMessageAfterSaveDataframe", false);
            },
        },
    }

    const vueNewEmployeeAddtionalQuestionsDataframeComp = {
        template: `<v-flex xs12 sm12 md6 lg6 xl6><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueNewEmployeeAddtionalQuestionsDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-textarea
          name="vueNewEmployeeAddtionalQuestionsDataframe_application_question1"
          label="1. What do you want to be in 5 years in your career?"
          v-model = "state.vueNewEmployeeAddtionalQuestionsDataframe_application_question1"




          rows=4
          auto-grow
          style="width:auto; height:auto; margin-bottom:auto"

        ></v-textarea></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-textarea
          name="vueNewEmployeeAddtionalQuestionsDataframe_application_question2"
          label="2. What are your hobbies?"
          v-model = "state.vueNewEmployeeAddtionalQuestionsDataframe_application_question2"




          rows=4
          auto-grow
          style="width:auto; height:auto; margin-bottom:auto"

        ></v-textarea></v-flex>
	 <div id='vueNewEmployeeAddtionalQuestionsDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs9 sm9 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueNewEmployeeAddtionalQuestionsDataframe-previous' @click.stop='vueNewEmployeeAddtionalQuestionsDataframe_previous' style='background-color:#1976D2; color:white;' >previous</v-btn>
</v-flex><v-flex xs3 sm3 md6 lg6 xl6 ><v-btn type='button' class='text-capitalize right' id='vueNewEmployeeAddtionalQuestionsDataframe-save' @click='vueNewEmployeeAddtionalQuestionsDataframe_save' style='background-color:#1976D2; color:white;'  :loading='vueNewEmployeeAddtionalQuestionsDataframe_save_loading' >Submit</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueNewEmployeeAddtionalQuestionsDataframe-errorContainer'></div></font>
</v-flex>`,
        data: function() {
            return {
                vueNewEmployeeAddtionalQuestionsDataframe_application_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                overlay_dataframe: false,
                vueNewEmployeeAddtionalQuestionsDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueNewEmployeeAddtionalQuestionsDataframe_tab_model: '',
            }
        },
        props: ['vueNewEmployeeAddtionalQuestionsDataframe_prop', ],
        created() {
            vueNewEmployeeAddtionalQuestionsDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueNewEmployeeAddtionalQuestionsDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueNewEmployeeAddtionalQuestionsDataframe');
            },

        },
        methods: {

            vueNewEmployeeAddtionalQuestionsDataframe_save: function() {
                let allParams = this.state;
                allParams["key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id"] = this.key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id;

                allParams['key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id'] = excon.getFromStore('vueNewEmployeeUploadResumeDataframe', 'key_vueNewEmployeeUploadResumeDataframe_application_id_id');
                allParams['dataframe'] = 'vueNewEmployeeAddtionalQuestionsDataframe';
                console.log(allParams)
                if (this.$refs.vueNewEmployeeAddtionalQuestionsDataframe_form.validate()) {
                    this.vueNewEmployeeAddtionalQuestionsDataframe_save_loading = true;
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/dataframe/ajaxSave',
                        data: allParams
                    }).then(function(responseData) {
                        self.vueNewEmployeeAddtionalQuestionsDataframe_save_loading = false;
                        var response = responseData.data;

                        var ajaxFileSave = vueNewEmployeeAddtionalQuestionsDataframeVar.params.ajaxFileSave;
                        if (ajaxFileSave) {
                            for (let i in ajaxFileSave) {
                                const value = ajaxFileSave[i];
                                allParams["key_vueNewEmployeeAddtionalQuestionsDataframe_application_id_id"] = response.nodeId[0];

                                self[value.fieldName + '_ajaxFileSave'](responseData, allParams);
                            }
                        }
                        var nodeArr = response.nodeId;
                        if (nodeArr && Array.isArray(nodeArr) && nodeArr.length) {
                            excon.saveToStore("vueNewEmployeeAddtionalQuestionsDataframe", "key", response.nodeId[0]);
                        }

                        excon.showAlertMessage(response);
                        if (response.success) {
                            self.$router.push("/thank-you-message/0");
                        }
                    }).catch(function(error) {
                        self.vueNewEmployeeAddtionalQuestionsDataframe_save_loading = false;
                        console.log(error);
                    });
                }

            },

            vueNewEmployeeAddtionalQuestionsDataframe_previous: function() {
                Vue.set(this.$store.state.vueNewEmployeeApplicantDataframe, "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeSelfAssesmentDataframe-tab-id");

            },

            tabClicked: function() {},
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueNewEmployeeAddtionalQuestionsDataframe", false);
            },
        },
    }

    const vueNewEmployeeApplicantDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card round >
                                <v-tabs color="white" slider-color="yellow"  background-color="blue darken-2" v-model="vueNewEmployeeApplicantDataframe_tab_model">
                                    <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueNewEmployeeBasicInformationDataframe-tab-id">Basic Information</v-tab>
                                    <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueNewEmployeeUploadResumeDataframe-tab-id">Upload Resume</v-tab>
                                     <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueNewEmployeeSelfAssesmentDataframe-tab-id">Self Assesment</v-tab>
                                   <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueNewEmployeeAddtionalQuestionsDataframe-tab-id">Additional Questions</v-tab>
                                 </v-tabs>

                                  <v-tabs-items v-model="vueNewEmployeeApplicantDataframe_tab_model">
                                    <v-tab-item value="vueNewEmployeeBasicInformationDataframe-tab-id">
                                     <vueNewEmployeeBasicInformationDataframe/>
                                     </v-tab-item>
                                     <v-tab-item value="vueNewEmployeeUploadResumeDataframe-tab-id"><vueNewEmployeeUploadResumeDataframe/></v-tab-item>
                                     <v-tab-item value="vueNewEmployeeSelfAssesmentDataframe-tab-id"><vueNewEmployeeSelfAssesmentDataframe/></v-tab-item>
                                     <v-tab-item value="vueNewEmployeeAddtionalQuestionsDataframe-tab-id"><vueNewEmployeeAddtionalQuestionsDataframe/></v-tab-item>
                                     </v-tabs-items></v-card></v-flex>
                                 </v-flex>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueNewEmployeeApplicantDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueNewEmployeeApplicantDataframe_tab_model: this.tabValue,
                vueNewEmployeeApplicantDataframe_display: true,
            }
        },
        props: ['vueNewEmployeeApplicantDataframe_prop', ],
        components: {
            'vueNewEmployeeBasicInformationDataframe': vueNewEmployeeBasicInformationDataframeComp,
            'vueNewEmployeeUploadResumeDataframe': vueNewEmployeeUploadResumeDataframeComp,
            'vueNewEmployeeSelfAssesmentDataframe': vueNewEmployeeSelfAssesmentDataframeComp,
            'vueNewEmployeeAddtionalQuestionsDataframe': vueNewEmployeeAddtionalQuestionsDataframeComp,
        },
        created() {
            vueNewEmployeeApplicantDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueNewEmployeeApplicantDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueNewEmployeeApplicantDataframe');
            },
            tabValue() {
                return this.$store.state.vueNewEmployeeApplicantDataframe.vueNewEmployeeApplicantDataframe_tab_model
            }
        },
        watch: {
            tabValue: {
                handler: function(val, oldVal) {
                    this.vueNewEmployeeApplicantDataframe_tab_model = val;
                }
            },
        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueNewEmployeeApplicantDataframe", false);
            },
        },
    }

    const vueElintegroResetPasswordDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' ><v-toolbar dark color="light-blue darken-2"><v-toolbar-title></v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroResetPasswordDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            v-model="state.vueElintegroResetPasswordDataframe_user_password"
            :append-icon="vueElintegroResetPasswordDataframe_user_password_show ? 'visibility_off' : 'visibility'"

            :type="vueElintegroResetPasswordDataframe_user_password_show ? 'text' : 'password'"
            name="input-10-1"
            label="User.password"
            autocomplete = off
            counter

            @click:append="vueElintegroResetPasswordDataframe_user_password_show = !vueElintegroResetPasswordDataframe_user_password_show"

            style="width:150; height:25;"

          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md6 lg6 xl6  ><v-text-field
            v-model="state.vueElintegroResetPasswordDataframe_password2"
            :append-icon="vueElintegroResetPasswordDataframe_password2_show ? 'visibility_off' : 'visibility'"

            :type="vueElintegroResetPasswordDataframe_password2_show ? 'text' : 'password'"
            name="input-10-1"
            label="Confirm Password"
            autocomplete = off
            counter

            @click:append="vueElintegroResetPasswordDataframe_password2_show = !vueElintegroResetPasswordDataframe_password2_show"

            style="width:150; height:25;"

          ></v-text-field></v-flex>
	 <div id='vueElintegroResetPasswordDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md4 lg4 xl4 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroResetPasswordDataframe-Cancel' @click.stop='vueElintegroResetPasswordDataframe_Cancel' style='background-color:#1976D2; color:white;' >Cancel</v-btn>
</v-flex><v-flex xs12 sm12 md4 lg4 xl4 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroResetPasswordDataframe-Submit' @click.stop='vueElintegroResetPasswordDataframe_Submit' style='background-color:#1976D2; color:white;'>Submit</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroResetPasswordDataframe-errorContainer'></div></font>
</v-card></v-flex>`,
        data: function() {
            return {

                vueElintegroResetPasswordDataframe_user_password_show: false,

                vueElintegroResetPasswordDataframe_password2_show: false,
                overlay_dataframe: false,
                vueElintegroResetPasswordDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroResetPasswordDataframe_prop', ],
        created() {
            this.vueElintegroResetPasswordDataframe_fillInitData();
            vueElintegroResetPasswordDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroResetPasswordDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroResetPasswordDataframe');
            },

        },
        watch: {
            vueElintegroResetPasswordDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroResetPasswordDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroResetPasswordDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {

            vueElintegroResetPasswordDataframe_Submit: function() {
                var allParams = {
                    'dataframe': 'vueElintegroResetPasswordDataframe'
                };
                var url = Dataframe.getUrl();
                var t = url.searchParams.get("token");
                if (t != undefined || t != null) {
                    allParams['t'] = t;
                }
                allParams['vueElintegroResetPasswordDataframe_user_email'] = jQuery("#vueElintegroUserProfileDataframe_person_email").val();

                allParams['id'] = 1;

                if (this.$refs.vueElintegroResetPasswordDataframe_form.validate()) {
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/register/resetUserPassword',
                        params: allParams
                    }).then(function(responseData) {
                        var response = responseData.data
                        if (response.success) {
                            if (response.msg) {
                                store.commit('alertMessage', {
                                    'snackbar': true,
                                    'alert_type': 'success',
                                    'alert_message': response.msg
                                });
                            }
                            if (response.data != null && response.data != '' && response.data != undefined) {
                                self.vueElintegroResetPasswordDataframe_populateJSONData(response.data);
                            }
                            if (data.redirect) {
                                window.location.href = data.redirectUrl;
                            }
                            jQuery('#resetPassword-Div').jqxWindow('close');
                        } else {
                            if (!response.error) {
                            }
                            if (response.msg) {
                                store.commit('alertMessage', {
                                    'snackbar': true,
                                    'alert_type': 'error',
                                    'alert_message': response.msg
                                })
                            }
                        }
                    }).catch(function(error) {
                        console.log(error);
                    });
                }

            },

            vueElintegroResetPasswordDataframe_Cancel: function() {
                $router.go(-1)
            },

            vueElintegroResetPasswordDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroResetPasswordDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroResetPasswordDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroResetPasswordDataframe_prop.key?this.vueElintegroResetPasswordDataframe_prop.key:this.$store.state.vueElintegroResetPasswordDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroResetPasswordDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroResetPasswordDataframe";
                        vueElintegroResetPasswordDataframeVar.updateState(response);
                        vueElintegroResetPasswordDataframeVar.vueElintegroResetPasswordDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroResetPasswordDataframe_populateJSONData: function(response) {
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroResetPasswordDataframe", false);
            },
        },
    }

    const vueElintegroUserProfileDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card round width='fit-content'><v-toolbar dark color="light-blue darken-2"><v-toolbar-title class="white--text">My Profile</v-toolbar-title><v-spacer></v-spacer></v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroUserProfileDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm6 md6 lg6 xl6  ><v-img
           id = "vueElintegroUserProfileDataframe_person_mainPicture"
          :src="state.vueElintegroUserProfileDataframe_person_mainPicture"

          :alt = "state.vueElintegroUserProfileDataframe_person_mainPicture_alt"
          aspect-ratio="2.5"

           height=200
           width=200
          contain ></v-img></v-flex>

						<v-flex xs12 sm6 md6 lg6 xl4  >
              <div >
               <v-eutil-image-upload

                  @upload-success="vueElintegroUserProfileDataframe_propertyImages_uploadImages"
                  @before-remove='vueElintegroUserProfileDataframe_propertyImages_beforeRemove'
                  @edit-image='vueElintegroUserProfileDataframe_propertyImages_editImage'
                  :edit-button=true
                  :delete-button=true

                >
               </v-eutil-image-upload></div>
               </v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  >
               <v-text-field
                 label="Email *"
                 placeholder = "Enter your email."
                 v-model="state.vueElintegroUserProfileDataframe_person_email"
                 :rules = 'vueElintegroUserProfileDataframe_person_email_rule'
                 readonly

                 style="width:auto; height:30px;"
                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm6 md6 lg6 xl4  ><v-text-field
            label="Firstname *"
            v-model = "state.vueElintegroUserProfileDataframe_person_firstName"
            :rules = 'vueElintegroUserProfileDataframe_person_firstName_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm6 md6 lg6 xl4  ><v-text-field
            label="Lastname *"
            v-model = "state.vueElintegroUserProfileDataframe_person_lastName"
            :rules = 'vueElintegroUserProfileDataframe_person_lastName_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm6 md6 lg12 xl4  >
                    <v-menu
                        ref="vueElintegroUserProfileDataframe_person_bday_menu"
                        v-model="vueElintegroUserProfileDataframe_person_bday_menu"
                        :close-on-content-click="false"
                        :return-value.sync="state.vueElintegroUserProfileDataframe_person_bday"
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="200px"
                        style="width:auto; height:auto;"

                >
                <template v-slot:activator="{ on }">
                    <v-text-field
                            v-model="vueElintegroUserProfileDataframe_person_bday_formatted"
                            label="Bday"

                            v-on="on"
                            hint="DD/MM/YYYY Format"
                            persistent-hint
                            prepend-icon="event"
                            @click:prepend="onIconClick_vueElintegroUserProfileDataframe_person_bday"
                            readonly
                            id="vueElintegroUserProfileDataframe_person_bday_id"


                    ></v-text-field>
                </template>
                <v-date-picker locale='en' v-model="state.vueElintegroUserProfileDataframe_person_bday"  no-title scrollable @input="$refs.vueElintegroUserProfileDataframe_person_bday_menu.save(state.vueElintegroUserProfileDataframe_person_bday)"></v-date-picker>
                </v-menu>
                </v-flex>

						<v-flex xs12 sm6 md6 lg6 xl4  >
               <v-text-field
                 label="Phone"
                 v-model="state.vueElintegroUserProfileDataframe_person_phone"
                 :rules = "vueElintegroUserProfileDataframe_person_phone_rule"


                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm6 md6 lg6 xl4  >
            <v-combobox
          v-model = "state.vueElintegroUserProfileDataframe_person_languages"
          :items="state.vueElintegroUserProfileDataframe_person_languages_items"

          label="Languages"

          item-text="ename"
          item-value="id"
          small-chips
          multiple
          hide-no-data
          hide-selected




        ></v-combobox>
        </v-flex>
	 <div id='vueElintegroUserProfileDataframe-errorContainer'></div>
<v-dialog v-model="visibility.vueElintegroResetPasswordDataframe"   width='initial' max-width='500px'><vueElintegroResetPasswordDataframe ref='vueElintegroResetPasswordDataframe_ref' :vueElintegroResetPasswordDataframe_prop='vueElintegroResetPasswordDataframe_data' resetForm=true></vueElintegroResetPasswordDataframe></v-dialog></v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row wrap pa-2><v-flex xs12 sm6 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroUserProfileDataframe-resetPassword' @click.stop='vueElintegroUserProfileDataframe_resetPassword' style='background-color:#1976D2; color:white;' >resetPassword</v-btn>
</v-flex><v-flex xs12 sm12 md4 lg4 xl4 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroUserProfileDataframe-submit' @click.stop='vueElintegroUserProfileDataframe_submit' style='background-color:#1976D2; color:white;' >submit</v-btn>
</v-flex></v-layout></v-container></v-card-actions></v-card></v-flex>`,
        data: function() {
            return {
                vueElintegroResetPasswordDataframe_data: {
                    key: ''
                },
                vueElintegroUserProfileDataframe_person_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],

                vueElintegroUserProfileDataframe_propertyImages_files: [],
                vueElintegroUserProfileDataframe_person_email_rule: [v=>!!v || 'Email is required', (v)=>/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Email is not valid.'],
                vueElintegroUserProfileDataframe_person_firstName_rule: [v=>!!v || 'Firstname is required', ],
                vueElintegroUserProfileDataframe_person_lastName_rule: [v=>!!v || 'Lastname is required', ],
                vueElintegroUserProfileDataframe_person_bday_menu: '',
                vueElintegroUserProfileDataframe_person_phone_rule: [v=>!!v || 'Phone is required', v=>!!v || 'Phone  is required.', v=>/[0-9]/.test(v) || 'Only numbers are allowed.', v=>(v && v.length >= 10 && v.length <= 15) || 'Phone number must be between 10 and 15', (v)=>/^(([(]?(\d{2,4})[)]?)|(\d{2,4})|([+1-9]+\d{1,2}))?[-\s]?(\d{2,3})?[-\s]?((\d{7,8})|(\d{3,4}[-\s]\d{3,4}))$/.test(v) || 'Phone number must be valid.'],

                vueElintegroUserProfileDataframe_person_languages_search: null,

                overlay_dataframe: false,
                vueElintegroUserProfileDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroUserProfileDataframe_prop', ],
        components: {
            'vueElintegroResetPasswordDataframe': vueElintegroResetPasswordDataframeComp,
        },
        created() {
            this.vueElintegroUserProfileDataframe_propertyImages_computedFileUploadParams();
            this.vueElintegroUserProfileDataframe_fillInitData();
            vueElintegroUserProfileDataframeVar = this;
            this.vueElintegroProfileMenuDataframeShow();
        },
        computed: {

            vueElintegroUserProfileDataframe_person_bday_formatted() {
                if (!this.state.vueElintegroUserProfileDataframe_person_bday)
                    return null;
                var d = new Date(this.state.vueElintegroUserProfileDataframe_person_bday);
                this.state.vueElintegroUserProfileDataframe_person_bday = d.toISOString();
                return d.format("dd/mm/yyyy")
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroUserProfileDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroUserProfileDataframe');
            },

        },
        watch: {
            vueElintegroUserProfileDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroUserProfileDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroUserProfileDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {

            vueElintegroUserProfileDataframe_propertyImages_uploadImages: function(event) {
                var detailData = event.detail;
                var fileList = detailData[3];
                this.vueElintegroUserProfileDataframe_propertyImages_files = fileList;
                excon.saveToStore('vueElintegroUserProfileDataframe', 'vueElintegroUserProfileDataframe_propertyImages', fileList[0].name);
            },

            vueElintegroUserProfileDataframe_propertyImages_beforeRemove: function(event) {
                var detailData = event.detail;
                var beforeRemove = detailData[1];
                var r = confirm("Remove image !");
                if (r == true) {
                    beforeRemove()
                    this.vueElintegroUserProfileDataframe_propertyImages_files = detailData[2];
                }
            },

            vueElintegroUserProfileDataframe_propertyImages_editImage: function(event) {
                var detailData = event.detail;
                this.vueElintegroUserProfileDataframe_propertyImages_files = detailData[3];
            },

            vueElintegroUserProfileDataframe_propertyImages_ajaxFileSave: function(data, allParams) {
                var fileList = this.vueElintegroUserProfileDataframe_propertyImages_files;
                if (fileList.length > 0) {
                    var picData = new FormData();
                    picData.append('fileSize', fileList.length);
                    picData.append('fileName', fileList[0].name);
                    picData.append('fieldnameToReload', 'vueElintegroUserProfileDataframe.person.mainPicture');
                    jQuery.each(allParams, function(key, value) {
                        picData.append(allParams, value);
                    });
                    picData.append('fldId', 'vueElintegroUserProfileDataframe_propertyImages');
                    for (var i = 0; i < fileList.length; i++) {
                        picData.append("vueElintegroUserProfileDataframe_propertyImages-file[" + i + "]", fileList[i]);
                    }
                    axios.post('/fileUpload/ajaxFileSave', picData).then(response=>{
                            console.log(response)
                        }
                    ).catch(function(error) {
                        console.log(error);
                    });
                }

            },

            vueElintegroUserProfileDataframe_propertyImages_computedFileUploadParams() {
                var refParams = this.params;
                var ajaxFileUploadParams = refParams['ajaxFileSave'];
                if (ajaxFileUploadParams) {
                    ajaxFileUploadParams.push({
                        fieldName: 'vueElintegroUserProfileDataframe_propertyImages'
                    })
                } else {
                    refParams['ajaxFileSave'] = [{
                        fieldName: 'vueElintegroUserProfileDataframe_propertyImages'
                    }];
                }
            },

            onIconClick_vueElintegroUserProfileDataframe_person_bday: function(e) {
                $('#vueElintegroUserProfileDataframe_person_bday_id').addClass('date-menu-position');
                this.vueElintegroUserProfileDataframe_person_bday_menu = !this.vueElintegroUserProfileDataframe_person_bday_menu;
            },

            vueElintegroUserProfileDataframe_submit: function() {
                this.editProfile();
            },

            vueElintegroUserProfileDataframe_resetPassword: function() {

                //todo add if refDataframe exist but route is not defined. remove the following code if its scope is limited.
                excon.setVisibility("vueElintegroResetPasswordDataframe", true);
            },

            vueElintegroUserProfileDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroUserProfileDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroUserProfileDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroUserProfileDataframe_prop.key?this.vueElintegroUserProfileDataframe_prop.key:this.$store.state.vueElintegroUserProfileDataframe.key";
                    }
                }
                allParams['id'] = this.$route.params.routeId ? this.$route.params.routeId : 1;
                allParams['dataframe'] = 'vueElintegroUserProfileDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroUserProfileDataframe";
                        vueElintegroUserProfileDataframeVar.updateState(response);
                        vueElintegroUserProfileDataframeVar.vueElintegroUserProfileDataframe_populateJSONData(response);
                    }
                    var imgSrc = "profileDetail/imageData";
                    excon.saveToStore('vueElintegroUserProfileDataframe', 'vueElintegroUserProfileDataframe_person_mainPicture', imgSrc);
                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroUserProfileDataframe_populateJSONData: function(response) {

                this.vueElintegroUserProfileDataframe_propertyImages = response['vueElintegroUserProfileDataframe.person.uploadPicture'] ? response['vueElintegroUserProfileDataframe.person.uploadPicture'] : "";

                this.key_vueElintegroUserProfileDataframe_person_id_id = response['key_vueElintegroUserProfileDataframe_person_id_id'] ? response['key_vueElintegroUserProfileDataframe_person_id_id'] : ""
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroUserProfileDataframe", false);
            },
            vueElintegroProfileMenuDataframeShow: function() {
                excon.setVisibility("vueElintegroProfileMenuDataframe", false)
            },

            editProfile: function() {
                var allParams = this.state;
                allParams['dataframe'] = 'vueNewEmployeeBasicInformationDataframe';
                var self = this;
                var imageName = this.state.vueElintegroUserProfileDataframe_propertyImages
                var imageUrl = '/opt/apache-tomcat-9.0.33/webapps/' + imageName
                if (this.$refs.vueElintegroUserProfileDataframe_form.validate()) {
                    axios({
                        method: 'post',
                        url: '/ProfileDetail/editProfileData',
                        data: allParams
                    }).then(function(responseData) {
                        var response = responseData;
                        self.vueElintegroUserProfileDataframe_propertyImages_ajaxFileSave(response, allParams);
                        setTimeout(function() {
                            this.location.reload();
                        }, 2000);
                    });
                } else {
                    alert("Error in saving")
                }

            }
            ,
        },
    }

    const vueElintegroApplicantGeneralInformationDataframeComp = {
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroApplicantGeneralInformationDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm6 md6 lg6 xl6  ><v-text-field
            label="FirstName *"
            v-model = "state.vueElintegroApplicantGeneralInformationDataframe_person_firstName"
            :rules = 'vueElintegroApplicantGeneralInformationDataframe_person_firstName_rule'

            readonly

            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm6 md6 lg6 xl6  ><v-text-field
            label="LastName *"
            v-model = "state.vueElintegroApplicantGeneralInformationDataframe_person_lastName"
            :rules = 'vueElintegroApplicantGeneralInformationDataframe_person_lastName_rule'

            readonly

            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm6 md6 lg6 xl6  >
               <v-text-field
                 label="Email *"
                 placeholder = "Enter your email."
                 v-model="state.vueElintegroApplicantGeneralInformationDataframe_person_email"
                 :rules = 'vueElintegroApplicantGeneralInformationDataframe_person_email_rule'
                 readonly

                 style="width:auto; height:30px;"
                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm6 md6 lg6 xl6  >
               <v-text-field
                 label="Phone"
                 v-model="state.vueElintegroApplicantGeneralInformationDataframe_person_phone"
                 :rules = "vueElintegroApplicantGeneralInformationDataframe_person_phone_rule"
                 readonly

                ></v-text-field>
               </v-flex>
	 <div id='vueElintegroApplicantGeneralInformationDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-flex class="text-right"><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs6 sm6 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroApplicantGeneralInformationDataframe-next' @click.stop='vueElintegroApplicantGeneralInformationDataframe_next' style='background-color:#1976D2; color:white;' >Next</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroApplicantGeneralInformationDataframe-errorContainer'></div></font>
</v-flex></div>`,
        data: function() {
            return {
                vueElintegroApplicantGeneralInformationDataframe_application_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                vueElintegroApplicantGeneralInformationDataframe_person_firstName_rule: [v=>!!v || 'FirstName is required', ],
                vueElintegroApplicantGeneralInformationDataframe_person_lastName_rule: [v=>!!v || 'LastName is required', ],
                vueElintegroApplicantGeneralInformationDataframe_person_email_rule: [v=>!!v || 'Email is required', (v)=>/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Email is not valid.'],
                vueElintegroApplicantGeneralInformationDataframe_person_phone_rule: [v=>!!v || '', ],
                overlay_dataframe: false,
                vueElintegroApplicantGeneralInformationDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueElintegroApplicantGeneralInformationDataframe_tab_model: '',
            }
        },
        props: ['vueElintegroApplicantGeneralInformationDataframe_prop', ],
        created() {
            this.vueElintegroApplicantGeneralInformationDataframe_fillInitData();
            vueElintegroApplicantGeneralInformationDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroApplicantGeneralInformationDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroApplicantGeneralInformationDataframe');
            },
            refreshVueElintegroApplicantGeneralInformationDataframe() {
                return this.vueElintegroApplicantGeneralInformationDataframe_prop.key
            },
        },
        watch: {
            vueElintegroApplicantGeneralInformationDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroApplicantGeneralInformationDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroApplicantGeneralInformationDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            refreshVueElintegroApplicantGeneralInformationDataframe: {
                handler: function(val, oldVal) {
                    this.vueElintegroApplicantGeneralInformationDataframe_fillInitData();
                }
            },
        },
        methods: {

            vueElintegroApplicantGeneralInformationDataframe_next: function() {
                excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model", "vueElintegroApplicantSelfAssessmentDataframe-tab-id");

            },

            vueElintegroApplicantGeneralInformationDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroApplicantGeneralInformationDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroApplicantGeneralInformationDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroApplicantGeneralInformationDataframe_prop.key?this.vueElintegroApplicantGeneralInformationDataframe_prop.key:this.$store.state.vueElintegroApplicantGeneralInformationDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroApplicantGeneralInformationDataframe';
                allParams['id'] = this.vueElintegroApplicantGeneralInformationDataframe_prop.key
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroApplicantGeneralInformationDataframe";
                        vueElintegroApplicantGeneralInformationDataframeVar.updateState(response);
                        vueElintegroApplicantGeneralInformationDataframeVar.vueElintegroApplicantGeneralInformationDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroApplicantGeneralInformationDataframe_populateJSONData: function(response) {

                this.key_vueElintegroApplicantGeneralInformationDataframe_application_id_id = response['key_vueElintegroApplicantGeneralInformationDataframe_application_id_id'] ? response['key_vueElintegroApplicantGeneralInformationDataframe_application_id_id'] : ""
            },
            tabClicked: function() {
                this.vueElintegroApplicantGeneralInformationDataframe_fillInitData();
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroApplicantGeneralInformationDataframe", false);
            },
        },
    }

    const vueElintegroApplicantSelfAssessmentDataframeComp = {
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroApplicantSelfAssessmentDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-card v-show="vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_display"><v-divider/>


       <v-data-table
            :headers="state.vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_headers"
            :items="state.vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_items"
            :items-per-page="-1"

            hide-default-footer

    >


        <template slot="item" slot-scope="props">
          <tr @click.stop=" " :key="props.item.AppId">

<td class='hidden text-start'>{{ props.item.AppId }}</td>
<td class='hidden text-start'>{{ props.item.Id }}</td>
<td class='text-start'>{{ props.item.Skill }}</td>
<td class='text-start'>{{ props.item.Level }}</td>
<td class='text-start'>{{ props.item.Comment }}</td>
          </tr>
        </template>

    </v-data-table></v-card>

</v-flex>
	 <div id='vueInitDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs9 sm9 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroApplicantSelfAssessmentDataframe-previous' @click.stop='vueElintegroApplicantSelfAssessmentDataframe_previous' style='background-color:#1976D2; color:white;' >previous</v-btn>
</v-flex><v-flex xs3 sm3 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroApplicantSelfAssessmentDataframe-next' @click.stop='vueElintegroApplicantSelfAssessmentDataframe_next' style='background-color:#1976D2; color:white;' >next</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroApplicantSelfAssessmentDataframe-errorContainer'></div></font>
</div>`,
        data: function() {
            return {

                drag: '',

                overlay_dataframe: false,
                vueElintegroApplicantSelfAssessmentDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueElintegroApplicantSelfAssessmentDataframe_tab_model: '',
            }
        },
        props: ['vueElintegroApplicantSelfAssessmentDataframe_prop', ],
        created() {
            this.vueElintegroApplicantSelfAssessmentDataframe_fillInitData();
            vueElintegroApplicantSelfAssessmentDataframeVar = this;
        },
        computed: {
            vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_display: function() {
                if (this.state.vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_items.length) {
                    return true;
                }
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroApplicantSelfAssessmentDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroApplicantSelfAssessmentDataframe');
            },
            refreshVueElintegroApplicantSelfAssessmentDataframe() {
                return this.vueElintegroApplicantSelfAssessmentDataframe_prop.key
            },
        },
        watch: {
            vueElintegroApplicantSelfAssessmentDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroApplicantSelfAssessmentDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroApplicantSelfAssessmentDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            refreshVueElintegroApplicantSelfAssessmentDataframe: {
                handler: function(val, oldVal) {
                    this.vueElintegroApplicantSelfAssessmentDataframe_fillInitData();
                }
            },
        },
        methods: {
            getDefaultDataHeaders_vueElintegroApplicantSelfAssessmentDataframe_applicationSkill: function() {

                var defaultDataHeaders = [{
                    "text": "AppId",
                    "keys": "id",
                    "value": "AppId",
                    "class": "hidden text-start",
                    "width": ""
                }, {
                    "text": "Id",
                    "keys": "id",
                    "value": "Id",
                    "class": "hidden text-start",
                    "width": ""
                }, {
                    "text": "Skill",
                    "keys": "skill",
                    "value": "Skill",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Level",
                    "keys": "level",
                    "value": "Level",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Comment",
                    "keys": "comment",
                    "value": "Comment",
                    "class": "text-start",
                    "width": ""
                }];
                this.state.vueElintegroApplicantSelfAssessmentDataframe_applicationSkill_headers = defaultDataHeaders;
            },

            vueElintegroApplicantSelfAssessmentDataframe_next: function() {
                excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model", "vueElintegroApplicantCVDataframe-tab-id");

            },

            vueElintegroApplicantSelfAssessmentDataframe_previous: function() {
                excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model", "vueElintegroApplicantGeneralInformationDataframe-tab-id");

            },

            vueElintegroApplicantSelfAssessmentDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroApplicantSelfAssessmentDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroApplicantSelfAssessmentDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroApplicantSelfAssessmentDataframe_prop.key?this.vueElintegroApplicantSelfAssessmentDataframe_prop.key:this.$store.state.vueElintegroApplicantSelfAssessmentDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroApplicantSelfAssessmentDataframe';
                allParams['id'] = this.vueElintegroApplicantSelfAssessmentDataframe_prop.key
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroApplicantSelfAssessmentDataframe";
                        vueElintegroApplicantSelfAssessmentDataframeVar.updateState(response);
                        vueElintegroApplicantSelfAssessmentDataframeVar.vueElintegroApplicantSelfAssessmentDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroApplicantSelfAssessmentDataframe_populateJSONData: function(response) {
            },
            tabClicked: function() {
                this.vueElintegroApplicantSelfAssessmentDataframe_fillInitData();
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroApplicantSelfAssessmentDataframe", false);
            },
        },
    }

    const vueElintegroApplicantCVDataframeComp = {
        template: `<div><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroApplicantCVDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm6 md6 lg6 xl6  ><div @click.stop="vueElintegroApplicantCVDataframe_files_fileName_url">
                     <v-img
                     id = "vueElintegroApplicantCVDataframe_files_fileName"
                     :src="state.vueElintegroApplicantCVDataframe_files_fileName"
                     :alt = "state.vueElintegroApplicantCVDataframe_files_fileName_alt"
                     aspect-ratio="1"

                     height=100
                      width=100
                       >
                      </v-img>
                      <span>{{state.vueElintegroApplicantCVDataframe_files_fileName_name}}</span>
                      </div>
                     </v-flex>

						<v-flex xs12 sm6 md6 lg6 xl6  ><v-img
           id = "vueElintegroApplicantCVDataframe_images_name"
          :src="state.vueElintegroApplicantCVDataframe_images_name"

          :alt = "state.vueElintegroApplicantCVDataframe_images_name_alt"
          aspect-ratio="2.5"

           height=200
           width=200
          contain ></v-img></v-flex>
	 <div id='vueInitDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs9 sm9 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroApplicantCVDataframe-previous' @click.stop='vueElintegroApplicantCVDataframe_previous' style='background-color:#1976D2; color:white;' >previous</v-btn>
</v-flex><v-flex xs3 sm3 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroApplicantCVDataframe-next' @click.stop='vueElintegroApplicantCVDataframe_next' style='background-color:#1976D2; color:white;' >next</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroApplicantCVDataframe-errorContainer'></div></font>
</div>`,
        data: function() {
            return {
                vueElintegroApplicantCVDataframe_application_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                vueElintegroApplicantCVDataframe_images_name_rule: [v=>!!v || 'Name is required', ],
                overlay_dataframe: false,
                vueElintegroApplicantCVDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueElintegroApplicantCVDataframe_tab_model: '',
            }
        },
        props: ['vueElintegroApplicantCVDataframe_prop', ],
        created() {
            this.vueElintegroApplicantCVDataframe_fillInitData();
            vueElintegroApplicantCVDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroApplicantCVDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroApplicantCVDataframe');
            },

        },
        watch: {
            vueElintegroApplicantCVDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroApplicantCVDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroApplicantCVDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {

            vueElintegroApplicantCVDataframe_files_fileName_url: function() {
                var fileName = this.state.vueElintegroApplicantCVDataframe_files_fileName_name;
                var fileURL = '/fileDownload/fileDownload/' + fileName
                var fileLink = document.createElement('a');
                fileLink.href = fileURL;
                fileLink.setAttribute('download', this.state.vueElintegroApplicantCVDataframe_files_fileName_name);
                document.body.appendChild(fileLink);
                fileLink.click();
            }
            ,
            vueElintegroApplicantCVDataframe_next: function() {
                excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model", "vueElintegroApplicantQuestionAnswerDataframe-tab-id");

            },

            vueElintegroApplicantCVDataframe_previous: function() {
                excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model", "vueElintegroApplicantSelfAssessmentDataframe-tab-id");

            },

            vueElintegroApplicantCVDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroApplicantCVDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroApplicantCVDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroApplicantCVDataframe_prop.key?this.vueElintegroApplicantCVDataframe_prop.key:this.$store.state.vueElintegroApplicantCVDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroApplicantCVDataframe';
                allParams['id'] = this.vueElintegroApplicantCVDataframe_prop.key
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroApplicantCVDataframe";
                        vueElintegroApplicantCVDataframeVar.updateState(response);
                        vueElintegroApplicantCVDataframeVar.vueElintegroApplicantCVDataframe_populateJSONData(response);
                    }
                    self.afterRefreshing(response);
                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroApplicantCVDataframe_populateJSONData: function(response) {

                this.key_vueElintegroApplicantCVDataframe_application_id_id = response['key_vueElintegroApplicantCVDataframe_application_id_id'] ? response['key_vueElintegroApplicantCVDataframe_application_id_id'] : ""
            },
            tabClicked: function() {
                this.vueElintegroApplicantCVDataframe_fillInitData();
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroApplicantCVDataframe", false);
            },
            afterRefreshing(response) {

                var params = response;
                var fileName = params.vueElintegroApplicantCVDataframe_files_fileName;
                var extension = fileName.split('.').pop();
                if (extension == 'pdf') {
                    var defaultImageUrlForPdf = 'assets/defaultPdfIcon.PNG'
                    excon.saveToStore('vueElintegroApplicantCVDataframe', 'vueElintegroApplicantCVDataframe_files_fileName', 'assets/defaultPdfIcon.PNG')
                } else if (extension == 'xlsx' || extension == 'xlsm' || extension == 'xlsb' || extension == 'xltx') {
                    excon.saveToStore('vueElintegroApplicantCVDataframe', 'vueElintegroApplicantCVDataframe_files_fileName', 'assets/defaultExcelFileIcon.PNG')

                } else if (extension == 'doc' || extension == 'docx') {
                    excon.saveToStore('vueElintegroApplicantCVDataframe', 'vueElintegroApplicantCVDataframe_files_fileName', 'assets/defaultDocFileIcon.PNG')
                } else if (extension == 'csv' || extension == 'CSV') {
                    excon.saveToStore('vueElintegroApplicantCVDataframe', 'vueElintegroApplicantCVDataframe_files_fileName', 'assets/defaultCsvIcon.PNG')
                }

                excon.saveToStore('vueElintegroApplicantCVDataframe', 'vueElintegroApplicantCVDataframe_files_fileName_name', fileName)

                var applicantId = response.vueElintegroApplicantCVDataframe_application_id;
                var imageSrc = "/fileDownload/imagePreview/" + applicantId;
                excon.saveToStore('vueElintegroApplicantCVDataframe', 'vueElintegroApplicantCVDataframe_images_name', imageSrc);

            },

        },
    }

    const vueElintegroApplicantQuestionAnswerDataframeComp = {
        template: `<v-flex xs12 sm12 md6 lg6 xl6><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroApplicantQuestionAnswerDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-textarea
          name="vueElintegroApplicantQuestionAnswerDataframe_application_question1"
          label="1. What do you want to be in 5 years in your career?"
          v-model = "state.vueElintegroApplicantQuestionAnswerDataframe_application_question1"


          readonly

          rows=4
          auto-grow
          style="width:auto; height:auto; margin-bottom:auto"

        ></v-textarea></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-textarea
          name="vueElintegroApplicantQuestionAnswerDataframe_application_question2"
          label="2. What are your hobbies?"
          v-model = "state.vueElintegroApplicantQuestionAnswerDataframe_application_question2"


          readonly

          rows=4
          auto-grow
          style="width:auto; height:auto; margin-bottom:auto"

        ></v-textarea></v-flex>
	 <div id='vueElintegroApplicantQuestionAnswerDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs9 sm9 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroApplicantQuestionAnswerDataframe-previous' @click.stop='vueElintegroApplicantQuestionAnswerDataframe_previous' style='background-color:#1976D2; color:white;' >previous</v-btn>
</v-flex><v-flex xs3 sm3 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroApplicantQuestionAnswerDataframe-next' @click.stop='vueElintegroApplicantQuestionAnswerDataframe_next' style='background-color:#1976D2; color:white;' >next</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroApplicantQuestionAnswerDataframe-errorContainer'></div></font>
</v-flex>`,
        data: function() {
            return {
                vueElintegroApplicantQuestionAnswerDataframe_application_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                overlay_dataframe: false,
                vueElintegroApplicantQuestionAnswerDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueElintegroApplicantQuestionAnswerDataframe_tab_model: '',
            }
        },
        props: ['vueElintegroApplicantQuestionAnswerDataframe_prop', ],
        created() {
            this.vueElintegroApplicantQuestionAnswerDataframe_fillInitData();
            vueElintegroApplicantQuestionAnswerDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroApplicantQuestionAnswerDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroApplicantQuestionAnswerDataframe');
            },
            refreshVueElintegroApplicantQuestionAnswerDataframe() {
                return this.vueElintegroApplicantQuestionAnswerDataframe_prop.key
            },
        },
        watch: {
            vueElintegroApplicantQuestionAnswerDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroApplicantQuestionAnswerDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroApplicantQuestionAnswerDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            refreshVueElintegroApplicantQuestionAnswerDataframe: {
                handler: function(val, oldVal) {
                    this.vueElintegroApplicantQuestionAnswerDataframe_fillInitData();
                }
            },
        },
        methods: {

            vueElintegroApplicantQuestionAnswerDataframe_next: function() {
                excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model", "vueElintegroCommentPageForApplicantDataframe-tab-id");

            },

            vueElintegroApplicantQuestionAnswerDataframe_previous: function() {
                excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model", "vueElintegroApplicantCVDataframe-tab-id");

            },

            vueElintegroApplicantQuestionAnswerDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroApplicantQuestionAnswerDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroApplicantQuestionAnswerDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroApplicantQuestionAnswerDataframe_prop.key?this.vueElintegroApplicantQuestionAnswerDataframe_prop.key:this.$store.state.vueElintegroApplicantQuestionAnswerDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroApplicantQuestionAnswerDataframe';
                allParams['id'] = this.vueElintegroApplicantQuestionAnswerDataframe_prop.key
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroApplicantQuestionAnswerDataframe";
                        vueElintegroApplicantQuestionAnswerDataframeVar.updateState(response);
                        vueElintegroApplicantQuestionAnswerDataframeVar.vueElintegroApplicantQuestionAnswerDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroApplicantQuestionAnswerDataframe_populateJSONData: function(response) {

                this.key_vueElintegroApplicantQuestionAnswerDataframe_application_id_id = response['key_vueElintegroApplicantQuestionAnswerDataframe_application_id_id'] ? response['key_vueElintegroApplicantQuestionAnswerDataframe_application_id_id'] : ""
            },
            tabClicked: function() {
                this.vueElintegroApplicantQuestionAnswerDataframe_fillInitData();
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroApplicantQuestionAnswerDataframe", false);
            },
        },
    }

    const vueElintegroCommentPageForApplicantDataframeComp = {
        template: `<v-flex xs12 sm12 md6 lg6 xl6><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroCommentPageForApplicantDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-textarea
          name="vueElintegroCommentPageForApplicantDataframe_application_comments"
          label="Comments"
          v-model = "state.vueElintegroCommentPageForApplicantDataframe_application_comments"


          readonly

          rows=4
          auto-grow
          style="width:auto; height:auto; margin-bottom:auto"

        ></v-textarea></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-textarea
          name="vueElintegroCommentPageForApplicantDataframe_application_lastComment"
          label="Your Comment"
          v-model = "state.vueElintegroCommentPageForApplicantDataframe_application_lastComment"




          rows=4
          auto-grow
          style="width:auto; height:auto; margin-bottom:auto"

        ></v-textarea></v-flex>
	 <div id='vueElintegroCommentPageForApplicantDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs6 sm6 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroCommentPageForApplicantDataframe-previous' @click.stop='vueElintegroCommentPageForApplicantDataframe_previous' >previous</v-btn>
</v-flex><v-flex xs6 sm6 md6 lg6 xl6 > <v-btn  class='text-capitalize ' type='button' id='vueElintegroCommentPageForApplicantDataframe-save' @click.stop='vueElintegroCommentPageForApplicantDataframe_save' >save</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroCommentPageForApplicantDataframe-errorContainer'></div></font>
</v-flex>`,
        data: function() {
            return {
                vueElintegroCommentPageForApplicantDataframe_application_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                overlay_dataframe: false,
                vueElintegroCommentPageForApplicantDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueElintegroCommentPageForApplicantDataframe_tab_model: '',
            }
        },
        props: ['vueElintegroCommentPageForApplicantDataframe_prop', ],
        created() {
            this.vueElintegroCommentPageForApplicantDataframe_fillInitData();
            vueElintegroCommentPageForApplicantDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroCommentPageForApplicantDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroCommentPageForApplicantDataframe');
            },

        },
        watch: {
            vueElintegroCommentPageForApplicantDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroCommentPageForApplicantDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroCommentPageForApplicantDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {

            vueElintegroCommentPageForApplicantDataframe_save: function() {
                this.addCommentsForApplicant();
            },

            vueElintegroCommentPageForApplicantDataframe_previous: function() {
                excon.saveToStore("vueElintegroApplicantDetailsDataframe", "vueElintegroApplicantDetailsDataframe_tab_model", "vueElintegroApplicantQuestionAnswerDataframe-tab-id");

            },

            vueElintegroCommentPageForApplicantDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroCommentPageForApplicantDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroCommentPageForApplicantDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroCommentPageForApplicantDataframe_prop.key?this.vueElintegroCommentPageForApplicantDataframe_prop.key:this.$store.state.vueElintegroCommentPageForApplicantDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroCommentPageForApplicantDataframe';
                allParams['id'] = this.vueElintegroCommentPageForApplicantDataframe_prop.key
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroCommentPageForApplicantDataframe";
                        vueElintegroCommentPageForApplicantDataframeVar.updateState(response);
                        vueElintegroCommentPageForApplicantDataframeVar.vueElintegroCommentPageForApplicantDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroCommentPageForApplicantDataframe_populateJSONData: function(response) {

                this.key_vueElintegroCommentPageForApplicantDataframe_application_id_id = response['key_vueElintegroCommentPageForApplicantDataframe_application_id_id'] ? response['key_vueElintegroCommentPageForApplicantDataframe_application_id_id'] : ""
            },
            tabClicked: function() {
                this.vueElintegroCommentPageForApplicantDataframe_fillInitData();
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroCommentPageForApplicantDataframe", false);
            },
            addCommentsForApplicant() {

                var allParams = this.state;
                var self = this;
                allParams['dataframe'] = 'vueElintegroCommentPageForApplicantDataframe';
                axios({
                    method: 'post',
                    url: '/EmployeeApplication/addComment',
                    data: allParams
                }).then(function(responseData) {
                    var response = responseData.data;
                    self.vueElintegroCommentPageForApplicantDataframe_fillInitData()
                });
            },
        },
    }

    const vueElintegroApplicantDetailsDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12 ><v-card round style ="overflow:hidden;" >
                                  <v-tabs color="white" slider-color="yellow"  background-color="blue darken-2" v-model="vueElintegroApplicantDetailsDataframe_tab_model">
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroApplicantGeneralInformationDataframe-tab-id">General Information</v-tab>
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroApplicantSelfAssessmentDataframe-tab-id">Self Assessment</v-tab>
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroApplicantCVDataframe-tab-id">CV</v-tab>
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroApplicantQuestionAnswerDataframe-tab-id">Questions/Answers</v-tab>
                                      <v-tab style ="text-transform:capitalize; color:white;" ripple href="#vueElintegroCommentPageForApplicantDataframe-tab-id">Comment</v-tab>
                                      <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                      </v-btn><span>Close</span></v-tooltip></v-flex>
                                  </v-tabs>
                                  <v-tabs-items v-model="vueElintegroApplicantDetailsDataframe_tab_model" >
                                      <v-tab-item value="vueElintegroApplicantGeneralInformationDataframe-tab-id" ><vueElintegroApplicantGeneralInformationDataframe :vueElintegroApplicantGeneralInformationDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                      <v-tab-item value="vueElintegroApplicantSelfAssessmentDataframe-tab-id"><vueElintegroApplicantSelfAssessmentDataframe :vueElintegroApplicantSelfAssessmentDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                      <v-tab-item value="vueElintegroApplicantCVDataframe-tab-id"><vueElintegroApplicantCVDataframe :vueElintegroApplicantCVDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                      <v-tab-item value="vueElintegroApplicantQuestionAnswerDataframe-tab-id"><vueElintegroApplicantQuestionAnswerDataframe :vueElintegroApplicantQuestionAnswerDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                      <v-tab-item value="vueElintegroCommentPageForApplicantDataframe-tab-id"><vueElintegroCommentPageForApplicantDataframe :vueElintegroCommentPageForApplicantDataframe_prop='this.vueElintegroApplicantDetailsDataframe_prop'/></v-tab-item>
                                 </v-tabs-items></v-card>
                                </v-flex>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueElintegroApplicantDetailsDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueElintegroApplicantDetailsDataframe_tab_model: '',
                vueElintegroApplicantDetailsDataframe_tab_model: this.tabValue,
                vueElintegroApplicantDetailsDataframe_display: true,
            }
        },
        props: ['vueElintegroApplicantDetailsDataframe_prop', ],
        components: {
            'vueElintegroApplicantGeneralInformationDataframe': vueElintegroApplicantGeneralInformationDataframeComp,
            'vueElintegroApplicantSelfAssessmentDataframe': vueElintegroApplicantSelfAssessmentDataframeComp,
            'vueElintegroApplicantCVDataframe': vueElintegroApplicantCVDataframeComp,
            'vueElintegroApplicantQuestionAnswerDataframe': vueElintegroApplicantQuestionAnswerDataframeComp,
            'vueElintegroCommentPageForApplicantDataframe': vueElintegroCommentPageForApplicantDataframeComp,
        },
        created() {
            this.vueElintegroApplicantDetailsDataframe_fillInitData();
            vueElintegroApplicantDetailsDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroApplicantDetailsDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroApplicantDetailsDataframe');
            },
            tabValue() {
                return this.$store.state.vueElintegroApplicantDetailsDataframe.vueElintegroApplicantDetailsDataframe_tab_model
            }
        },
        watch: {
            vueElintegroApplicantDetailsDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroApplicantDetailsDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroApplicantDetailsDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            tabValue: {
                handler: function(val, oldVal) {
                    this.vueElintegroApplicantDetailsDataframe_tab_model = val;
                }
            },
        },
        methods: {

            vueElintegroApplicantDetailsDataframe_save: function() {
                let allParams = this.state;

                if (this.$refs.hasOwnProperty("vueElintegroApplicantGeneralInformationDataframe_ref") && this.$refs.vueElintegroApplicantGeneralInformationDataframe_ref) {
                    for (var a in this.$refs.vueElintegroApplicantGeneralInformationDataframe_ref.$data) {

                        var dashA = a.split('_').join('-');
                        allParams[dashA] = this.$refs.vueElintegroApplicantGeneralInformationDataframe_ref.$data[a];
                    }
                }
                if (this.$refs.hasOwnProperty("vueElintegroApplicantSelfAssessmentDataframe_ref") && this.$refs.vueElintegroApplicantSelfAssessmentDataframe_ref) {
                    for (var a in this.$refs.vueElintegroApplicantSelfAssessmentDataframe_ref.$data) {

                        var dashA = a.split('_').join('-');
                        allParams[dashA] = this.$refs.vueElintegroApplicantSelfAssessmentDataframe_ref.$data[a];
                    }
                }
                if (this.$refs.hasOwnProperty("vueElintegroApplicantCVDataframe_ref") && this.$refs.vueElintegroApplicantCVDataframe_ref) {
                    for (var a in this.$refs.vueElintegroApplicantCVDataframe_ref.$data) {

                        var dashA = a.split('_').join('-');
                        allParams[dashA] = this.$refs.vueElintegroApplicantCVDataframe_ref.$data[a];
                    }
                }
                if (this.$refs.hasOwnProperty("vueElintegroApplicantQuestionAnswerDataframe_ref") && this.$refs.vueElintegroApplicantQuestionAnswerDataframe_ref) {
                    for (var a in this.$refs.vueElintegroApplicantQuestionAnswerDataframe_ref.$data) {

                        var dashA = a.split('_').join('-');
                        allParams[dashA] = this.$refs.vueElintegroApplicantQuestionAnswerDataframe_ref.$data[a];
                    }
                }
                if (this.$refs.hasOwnProperty("vueElintegroCommentPageForApplicantDataframe_ref") && this.$refs.vueElintegroCommentPageForApplicantDataframe_ref) {
                    for (var a in this.$refs.vueElintegroCommentPageForApplicantDataframe_ref.$data) {

                        var dashA = a.split('_').join('-');
                        allParams[dashA] = this.$refs.vueElintegroCommentPageForApplicantDataframe_ref.$data[a];
                    }
                }

                allParams['dataframe'] = 'vueElintegroApplicantDetailsDataframe';
                console.log(allParams)
                if (this.$refs.vueElintegroApplicantDetailsDataframe_form.validate()) {
                    this.vueElintegroApplicantDetailsDataframe_save_loading = true;
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/dataframe/ajaxSave',
                        data: allParams
                    }).then(function(responseData) {
                        self.vueElintegroApplicantDetailsDataframe_save_loading = false;
                        var response = responseData.data;

                        var ajaxFileSave = vueElintegroApplicantDetailsDataframeVar.params.ajaxFileSave;
                        if (ajaxFileSave) {
                            for (let i in ajaxFileSave) {
                                const value = ajaxFileSave[i];

                                self[value.fieldName + '_ajaxFileSave'](responseData, allParams);
                            }
                        }
                        var nodeArr = response.nodeId;
                        if (nodeArr && Array.isArray(nodeArr) && nodeArr.length) {
                            excon.saveToStore("vueElintegroApplicantDetailsDataframe", "key", response.nodeId[0]);
                        }

                        excon.showAlertMessage(response);
                        if (response.success) {
                        }
                    }).catch(function(error) {
                        self.vueElintegroApplicantDetailsDataframe_save_loading = false;
                        console.log(error);
                    });
                }

            },

            vueElintegroApplicantDetailsDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroApplicantDetailsDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroApplicantDetailsDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroApplicantDetailsDataframe_prop.key?this.vueElintegroApplicantDetailsDataframe_prop.key:this.$store.state.vueElintegroApplicantDetailsDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueElintegroApplicantDetailsDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroApplicantDetailsDataframe";
                        vueElintegroApplicantDetailsDataframeVar.updateState(response);
                        vueElintegroApplicantDetailsDataframeVar.vueElintegroApplicantDetailsDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroApplicantDetailsDataframe_populateJSONData: function(response) {},
            tabClicked: function() {
                this.vueElintegroApplicantDetailsDataframe_fillInitData();
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroApplicantDetailsDataframe", false);
            },
        },
    }

    const vueTechnologiesDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text"><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>Technologies</v-card-title></v-toolbar-title>
                                  </v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueTechnologiesDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_java"
          :src="state.vueTechnologiesDataframe_java"

          :alt = "state.vueTechnologiesDataframe_java_alt"
          aspect-ratio="1.5"

           height=auto
           width=auto
           ></v-img></v-flex>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_javascript"
          :src="state.vueTechnologiesDataframe_javascript"

          :alt = "state.vueTechnologiesDataframe_javascript_alt"
          aspect-ratio="1.5"

           height=auto
           width=auto
           ></v-img></v-flex>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_grails"
          :src="state.vueTechnologiesDataframe_grails"

          :alt = "state.vueTechnologiesDataframe_grails_alt"
          aspect-ratio="1.5"

           height=auto
           width=auto
           ></v-img></v-flex>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_vuejs"
          :src="state.vueTechnologiesDataframe_vuejs"

          :alt = "state.vueTechnologiesDataframe_vuejs_alt"
          aspect-ratio="1.0"

           height=auto
           width=auto
           ></v-img></v-flex>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_kafka"
          :src="state.vueTechnologiesDataframe_kafka"

          :alt = "state.vueTechnologiesDataframe_kafka_alt"
          aspect-ratio="1.0"

           height=auto
           width=auto
           ></v-img></v-flex>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_oracle"
          :src="state.vueTechnologiesDataframe_oracle"

          :alt = "state.vueTechnologiesDataframe_oracle_alt"
          aspect-ratio="1.0"

           height=auto
           width=auto
           ></v-img></v-flex>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_nodejs"
          :src="state.vueTechnologiesDataframe_nodejs"

          :alt = "state.vueTechnologiesDataframe_nodejs_alt"
          aspect-ratio="1.0"

           height=auto
           width=auto
           ></v-img></v-flex>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_kubernetes"
          :src="state.vueTechnologiesDataframe_kubernetes"

          :alt = "state.vueTechnologiesDataframe_kubernetes_alt"
          aspect-ratio="1.0"

           height=auto
           width=auto
           ></v-img></v-flex>

						<v-flex xs12 sm12 md4 lg4 xl4  ><v-img
           id = "vueTechnologiesDataframe_mysql"
          :src="state.vueTechnologiesDataframe_mysql"

          :alt = "state.vueTechnologiesDataframe_mysql_alt"
          aspect-ratio="1.0"

           height=auto
           width=auto
           ></v-img></v-flex>
	 <div id='vueTechnologiesDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueTechnologiesDataframe-errorContainer'></div></font>
</v-card></v-flex>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueTechnologiesDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueTechnologiesDataframe_prop', ],
        created() {
            vueTechnologiesDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueTechnologiesDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueTechnologiesDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueTechnologiesDataframe", false);
            },
        },
    }

    const vueClientProjectDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text"><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>Clients & Projects Details</v-card-title></v-toolbar-title>
                                  </v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueClientProjectDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueClientProjectDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueClientProjectDataframe-errorContainer'></div></font>

                                  <v-card class ="clientProject">
						<v-flex xs12 sm12 md12 lg12 xl12  ><v-card v-show="vueClientProjectDataframe_clientProject_display"><v-divider/>


       <v-data-table
            :headers="state.vueClientProjectDataframe_clientProject_headers"
            :items="state.vueClientProjectDataframe_clientProject_items"
            :items-per-page="-1"

            hide-default-footer

    >


        <template slot="item" slot-scope="props">
          <tr @click.stop=" " :key="props.item.null">

<td class='text-start'>{{ props.item.Clientname }}</td>
<td class='text-start'>{{ props.item.Projectname }}</td>
<td class='text-start'><v-img height='auto' width='400' :src='props.item.Logo' :alt ='props.item.Logo'></v-img></td>
<td class='text-start'>{{ props.item.Description }}</td>
<td class='text-start'><a :href='props.item.LinkToWebsite' target='_blank' style ='text-decoration :none !important;' >{{ props.item.LinkToWebsite }} </a></td>
          </tr>
        </template>

    </v-data-table></v-card>

</v-flex>
	</v-card></v-card></v-flex>`,
        data: function() {
            return {

                drag: '',

                overlay_dataframe: false,
                vueClientProjectDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueClientProjectDataframe_prop', ],
        created() {
            this.vueClientProjectDataframe_fillInitData();
            vueClientProjectDataframeVar = this;
        },
        computed: {
            vueClientProjectDataframe_clientProject_display: function() {
                if (this.state.vueClientProjectDataframe_clientProject_items.length) {
                    return true;
                }
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueClientProjectDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueClientProjectDataframe');
            },

        },
        watch: {
            vueClientProjectDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueClientProjectDataframe_fillInitData();
                    } else {
                        console.log("vueClientProjectDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {
            getDefaultDataHeaders_vueClientProjectDataframe_clientProject: function() {

                var defaultDataHeaders = [{
                    "text": "Client  ",
                    "keys": "clientName",
                    "value": "Client  ",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Project  ",
                    "keys": "projectName",
                    "value": "Project  ",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Logo",
                    "keys": "logo",
                    "value": "Logo",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Description",
                    "keys": "description",
                    "value": "Description",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Website",
                    "keys": "linkToWebsite",
                    "value": "Website",
                    "class": "text-start",
                    "width": ""
                }];
                this.state.vueClientProjectDataframe_clientProject_headers = defaultDataHeaders;
            },

            vueClientProjectDataframe_fillInitData: function() {
                excon.saveToStore('vueClientProjectDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueClientProjectDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueClientProjectDataframe_prop.key?this.vueClientProjectDataframe_prop.key:this.$store.state.vueClientProjectDataframe.key";
                    }
                }
                allParams['id'] = this.$route.params.routeId ? this.$route.params.routeId : 1;
                allParams['dataframe'] = 'vueClientProjectDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueClientProjectDataframe";
                        vueClientProjectDataframeVar.updateState(response);
                        vueClientProjectDataframeVar.vueClientProjectDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueClientProjectDataframe_populateJSONData: function(response) {
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueClientProjectDataframe", false);
            },
        },
    }

    const vueCreateProjectForTranslationDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl2><v-card round class='rounded-card' ><v-toolbar dark color="light-blue darken-2"><v-toolbar-title><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>New Project</v-card-title></v-toolbar-title>
                                <v-spacer></v-spacer><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="closeDataframe"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueCreateProjectForTranslationDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Project Name *"
            v-model = "state.vueCreateProjectForTranslationDataframe_project_name"
            :rules = 'vueCreateProjectForTranslationDataframe_project_name_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  >
            <v-combobox
          v-model = "state.vueCreateProjectForTranslationDataframe_project_sourceLanguage"
          :items="state.vueCreateProjectForTranslationDataframe_project_sourceLanguage_items"

          label="Source Language"

          item-text="ename"
          item-value="id"
          small-chips

          hide-no-data
          hide-selected




        ></v-combobox>
        </v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  >
              <div null>
               <v-file-input
                  label = "Source File"
                  multiple
                  @change = "vueCreateProjectForTranslationDataframe_project_sourceFile_uploadFile"

                >
               </v-file-input></div>
               </v-flex>
	 <div id='vueCreateProjectForTranslationDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md0 lg0 xl0 > <v-btn  class='text-capitalize ' type='button' id='vueCreateProjectForTranslationDataframe-save' @click.stop='vueCreateProjectForTranslationDataframe_save' style='background-color:#1976D2; color:white;' >save</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueCreateProjectForTranslationDataframe-errorContainer'></div></font>
</v-card></v-flex>`,
        data: function() {
            return {
                vueCreateProjectForTranslationDataframe_project_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                vueCreateProjectForTranslationDataframe_project_name_rule: [v=>!!v || 'Project Name is required', ],

                vueCreateProjectForTranslationDataframe_project_sourceLanguage_search: null,

                vueCreateProjectForTranslationDataframe_project_sourceFile_files: [],
                overlay_dataframe: false,
                vueCreateProjectForTranslationDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueCreateProjectForTranslationDataframe_prop', ],
        created() {
            this.vueCreateProjectForTranslationDataframe_project_sourceFile_computedFileUploadParams();
            vueCreateProjectForTranslationDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueCreateProjectForTranslationDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueCreateProjectForTranslationDataframe');
            },

        },
        methods: {

            vueCreateProjectForTranslationDataframe_project_sourceFile_uploadFile: function(event) {
                //TODO: for multi file this should be array and not sinfle file, change this code accordingly
                this.vueCreateProjectForTranslationDataframe_project_sourceFile_files = event;
                //this.state.vueCreateProjectForTranslationDataframe_project_sourceFile = fileToUpload.name; //TODO: once we find out why the v-model cannot get state.<fldName>, this line could be deleted!
                var fileToUpload = this.vueCreateProjectForTranslationDataframe_project_sourceFile_files;
                this.state.vueCreateProjectForTranslationDataframe_project_sourceFile = fileToUpload[0].name;
            },

            vueCreateProjectForTranslationDataframe_project_sourceFile_ajaxFileSave: function(data, allParams) {
                var fileList = this.vueCreateProjectForTranslationDataframe_project_sourceFile_files;
                if (fileList.length > 0) {
                    var fileData = new FormData();
                    for (var i = 0; i < fileList.length; i++) {
                        fileData.append('fileName', fileList[i].name);
                        fileData.append('fileStorageSize', fileList[i].size);
                        fileData.append('fileLastModified', fileList[i].lastModified);
                        fileData.append('fileLastModifiedDate', fileList[i].lastModifiedDate);
                        fileData.append('fileWebKitRelativePath', fileList[i].webKitRelativePath);
                        fileData.append('fileType', fileList[i].type);
                        fileData.append("vueCreateProjectForTranslationDataframe_project_sourceFile-file[" + i + "]", fileList[i]);
                    }
                    fileData.append('fileSize', fileList.length);
                    fileData.append('fldId', 'vueCreateProjectForTranslationDataframe_project_sourceFile');
                    if (data.params != null) {
                        fileData.append('allParams', data.params.id);
                    }
                }
                fetch('/translatorAssistant/fileUpload', {
                    method: 'POST',
                    body: fileData
                }).then(response=>{
                        console.log(response)
                    }
                ).catch(function(error) {
                    console.log(error)
                });
            },

            vueCreateProjectForTranslationDataframe_project_sourceFile_computedFileUploadParams() {
                var refParams = this.params;
                var ajaxFileUploadParams = refParams['ajaxFileSave'];
                if (ajaxFileUploadParams) {
                    ajaxFileUploadParams.push({
                        fieldName: 'vueCreateProjectForTranslationDataframe_project_sourceFile'
                    })
                } else {
                    refParams['ajaxFileSave'] = [{
                        fieldName: 'vueCreateProjectForTranslationDataframe_project_sourceFile'
                    }];
                }
            },

            vueCreateProjectForTranslationDataframe_save: function() {
                this.saveProject()
            },

            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueCreateProjectForTranslationDataframe", false);
            },
            saveProject() {
                var allParams = this.state;
                var self = this;
                allParams['dataframe'] = 'vueCreateProjectForTranslationDataframe';
                axios({
                    method: 'post',
                    url: '/translatorAssistant/saveProjectData',
                    data: allParams
                }).then(function(responseData) {
                    var response = responseData.data;
                    self.vueCreateProjectForTranslationDataframe_project_sourceFile_ajaxFileSave(response, allParams);
                    excon.saveToStore('vueTranslatorAssistantDataframe', 'vueTranslatorAssistantDataframe_project_list', response.params.name);
                    excon.saveToStore('vueTranslatorAssistantDataframe', 'currentProjectId', response.params.id)
                    excon.setVisibility('vueCreateProjectForTranslationDataframe', false);
                });
            },
        },
    }

    const vueEditTranslatedRecordsOfGridDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card round class='rounded-card' >
                                <v-flex class="text-right"><v-tooltip bottom><v-btn icon target="_blank" slot="activator" @click.prevent="confirmationMessage();"><v-icon medium >close</v-icon>
                                </v-btn><span>Close</span></v-tooltip></v-flex><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueEditTranslatedRecordsOfGridDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Key"
            v-model = "state.vueEditTranslatedRecordsOfGridDataframe_text__key"


            readonly

            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-textarea
          name="vueEditTranslatedRecordsOfGridDataframe_text_text"
          label="Text"
          v-model = "state.vueEditTranslatedRecordsOfGridDataframe_text_text"




          rows=4
          auto-grow
          style="width:auto; height:auto; margin-bottom:auto"

        ></v-textarea></v-flex>
	 <div id='vueEditTranslatedRecordsOfGridDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md2 lg2 xl2 > <v-btn  class='text-capitalize ' type='button' id='vueEditTranslatedRecordsOfGridDataframe-restore' @click.stop='vueEditTranslatedRecordsOfGridDataframe_restore' style='background-color:#1976D2; color:white;'>Restore</v-btn>
</v-flex><v-flex xs12 sm12 md4 lg4 xl4 > <v-btn  class='text-capitalize ' type='button' id='vueEditTranslatedRecordsOfGridDataframe-googleTranslate' @click.stop='vueEditTranslatedRecordsOfGridDataframe_googleTranslate' style='background-color:#1976D2; color:white;'>Google Translate</v-btn>
</v-flex><v-flex xs12 sm12 md2 lg2 xl2 ><v-btn type='button' class='text-capitalize right' id='vueEditTranslatedRecordsOfGridDataframe-save' @click='vueEditTranslatedRecordsOfGridDataframe_save' style='background-color:#1976D2; color:white;'  :loading='vueEditTranslatedRecordsOfGridDataframe_save_loading' >Save</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueEditTranslatedRecordsOfGridDataframe-errorContainer'></div></font>
</v-card></v-flex>`,
        data: function() {
            return {
                vueEditTranslatedRecordsOfGridDataframe_text_id_rule: [v=>!!v || 'Id is required', ],
                generalRule: [v=>/[0-9]/.test(v) || 'Only digits are allowed'],
                overlay_dataframe: false,
                vueEditTranslatedRecordsOfGridDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueEditTranslatedRecordsOfGridDataframe_prop', ],
        created() {
            this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();
            vueEditTranslatedRecordsOfGridDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueEditTranslatedRecordsOfGridDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueEditTranslatedRecordsOfGridDataframe');
            },
            refreshVueEditTranslatedRecordsOfGridDataframe() {
                return this.vueEditTranslatedRecordsOfGridDataframe_prop.key
            },
        },
        watch: {
            vueEditTranslatedRecordsOfGridDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();
                    } else {
                        console.log("vueEditTranslatedRecordsOfGridDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            refreshVueEditTranslatedRecordsOfGridDataframe: {
                handler: function(val, oldVal) {
                    this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();
                }
            },
        },
        methods: {

            vueEditTranslatedRecordsOfGridDataframe_save: function() {
                let allParams = this.state;
                allParams["key_vueEditTranslatedRecordsOfGridDataframe_text_id_id"] = this.key_vueEditTranslatedRecordsOfGridDataframe_text_id_id;

                allParams['key_vueEditTranslatedRecordsOfGridDataframe_text_id_id'] = this.vueEditTranslatedRecordsOfGridDataframe_prop.key
                allParams['dataframe'] = 'vueEditTranslatedRecordsOfGridDataframe';
                console.log(allParams)
                if (this.$refs.vueEditTranslatedRecordsOfGridDataframe_form.validate()) {
                    this.vueEditTranslatedRecordsOfGridDataframe_save_loading = true;
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/dataframe/ajaxSave',
                        data: allParams
                    }).then(function(responseData) {
                        self.vueEditTranslatedRecordsOfGridDataframe_save_loading = false;
                        var response = responseData.data;

                        var ajaxFileSave = vueEditTranslatedRecordsOfGridDataframeVar.params.ajaxFileSave;
                        if (ajaxFileSave) {
                            for (let i in ajaxFileSave) {
                                const value = ajaxFileSave[i];
                                allParams["key_vueEditTranslatedRecordsOfGridDataframe_text_id_id"] = response.nodeId[0];

                                self[value.fieldName + '_ajaxFileSave'](responseData, allParams);
                            }
                        }
                        null

                        excon.showAlertMessage(response);
                        if (response.success) {
                            excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                            excon.refreshDataForGrid(response, 'vueGridOfTranslatedTextDataframe', 'vueGridOfTranslatedTextDataframe_translatedText', 'U');

                        }
                    }).catch(function(error) {
                        self.vueEditTranslatedRecordsOfGridDataframe_save_loading = false;
                        console.log(error);
                    });
                }

            },

            vueEditTranslatedRecordsOfGridDataframe_googleTranslate: function() {
                this.googleTranslateForEachRecord();
            },

            vueEditTranslatedRecordsOfGridDataframe_restore: function() {
                this.vueEditTranslatedRecordsOfGridDataframe_fillInitData();
            },

            vueEditTranslatedRecordsOfGridDataframe_fillInitData: function() {
                excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueEditTranslatedRecordsOfGridDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueEditTranslatedRecordsOfGridDataframe_prop.key?this.vueEditTranslatedRecordsOfGridDataframe_prop.key:this.$store.state.vueEditTranslatedRecordsOfGridDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueEditTranslatedRecordsOfGridDataframe';
                allParams['id'] = this.vueEditTranslatedRecordsOfGridDataframe_prop.key
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueEditTranslatedRecordsOfGridDataframe";
                        vueEditTranslatedRecordsOfGridDataframeVar.updateState(response);
                        vueEditTranslatedRecordsOfGridDataframeVar.vueEditTranslatedRecordsOfGridDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueEditTranslatedRecordsOfGridDataframe_populateJSONData: function(response) {

                this.key_vueEditTranslatedRecordsOfGridDataframe_text_id_id = response['key_vueEditTranslatedRecordsOfGridDataframe_text_id_id'] ? response['key_vueEditTranslatedRecordsOfGridDataframe_text_id_id'] : ""
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
            },

            googleTranslateForEachRecord() {
                var allParams = this.state;
                var self = this;
                allParams['sourceLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe', 'sourceLanguage');
                allParams['targetLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe', 'targetLanguage');
                allParams['projectId'] = excon.getFromStore('vueGridOfTranslatedTextDataframe', 'projectId');
                allParams['dataframe'] = 'vueEditTranslatedRecordsOfGridDataframe';

                axios({
                    method: 'post',
                    url: '/translatorAssistant/translateEachRecordWithGoogle',
                    data: allParams
                }).then(function(responseData) {
                    var response = responseData.data;
                    excon.saveToStore('vueEditTranslatedRecordsOfGridDataframe', 'vueEditTranslatedRecordsOfGridDataframe_text_text', response.translatedText);
                });
            },

            confirmationMessage() {
                var result = confirm("Are you sure want to abandon the changes?");
                if (result) {
                    excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", false);
                }
                return false;
            },
        },
    }

    const vueGridOfTranslatedTextDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                 <v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueGridOfTranslatedTextDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-card v-show="vueGridOfTranslatedTextDataframe_translatedText_display"><v-divider/>


            <v-row><v-col cols='8'></v-col>
            <v-col cols="4">
                    <v-text-field
                    v-model="vueGridOfTranslatedTextDataframe_translatedText_search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
                    class='pa-3'
            ></v-text-field>
            </v-col></v-row>

       <v-data-table
            :headers="state.vueGridOfTranslatedTextDataframe_translatedText_headers"
            :items="state.vueGridOfTranslatedTextDataframe_translatedText_items"
            :items-per-page="-1"
            :search='vueGridOfTranslatedTextDataframe_translatedText_search'
            hide-default-footer

    >


        <template slot="item" slot-scope="props">
          <tr @click.stop="vueGridOfTranslatedTextDataframe_translatedText_showDetailvueEditTranslatedRecordsOfGridDataframe(props.item)" :key="props.item.Id">

<td class='hidden text-start'>{{ props.item.Id }}</td>
<td class='text-start'>{{ props.item.Key }}</td>
<td class='text-start'>{{ props.item.Text }}</td>
<td class='text-start layout' @click.stop=''><v-icon small
             v-tooltip="{
                content: 'Edit row',

            }"
         class="mr-2"  @click.stop=" vueGridOfTranslatedTextDataframe_translatedText_editmethod(props.item);">edit</v-icon></td>
          </tr>
        </template>

    </v-data-table></v-card>
        <v-dialog v-model="visibility.vueEditTranslatedRecordsOfGridDataframe" width='auto' max-width='500px' ><component :is='vueEditTranslatedRecordsOfGridDataframe_comp' ref='vueEditTranslatedRecordsOfGridDataframe_ref' :vueEditTranslatedRecordsOfGridDataframe_prop="vueEditTranslatedRecordsOfGridDataframe_data"></component></v-dialog><v-dialog v-model="visibility.vueEditTranslatedRecordsOfGridDataframe" width='auto' max-width='500' ><component :is='vueEditTranslatedRecordsOfGridDataframe_comp' ref='vueEditTranslatedRecordsOfGridDataframe_ref' :vueEditTranslatedRecordsOfGridDataframe_prop="vueEditTranslatedRecordsOfGridDataframe_data"></component></v-dialog>
</v-flex>
	 <div id='vueGridOfTranslatedTextDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>

                                 <v-card-actions class = "justify-center"><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md0 lg0 xl0 > <v-btn  class='text-capitalize ' type='button' id='vueGridOfTranslatedTextDataframe-downloadTargetPropertyFile' @click.stop='vueGridOfTranslatedTextDataframe_downloadTargetPropertyFile' style='background-color:#1976D2; color:white;' v-show = 'vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile' >Download</v-btn>
</v-flex><v-flex xs12 sm12 md0 lg0 xl0 > <v-btn  class='text-capitalize ' type='button' id='vueGridOfTranslatedTextDataframe-translateWithGoogle' @click.stop='vueGridOfTranslatedTextDataframe_translateWithGoogle' style='background-color:#1976D2; color:white;' v-show = 'vueGridOfTranslatedTextDataframe_button_translateWithGoogle' >Translate With Google</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueGridOfTranslatedTextDataframe-errorContainer'></div></font>
</v-card-actions>
                                 </v-card></v-flex>`,
        data: function() {
            return {
                vueEditTranslatedRecordsOfGridDataframe_data: {
                    key: '',
                    refreshGrid: true,
                    parentData: {}
                },
                vueEditTranslatedRecordsOfGridDataframe_comp: '',
                vueEditTranslatedRecordsOfGridDataframe_data: {
                    key: '',
                    refreshGrid: true,
                    parentData: {}
                },
                vueEditTranslatedRecordsOfGridDataframe_comp: '',

                drag: '',
                vueGridOfTranslatedTextDataframe_translatedText_search: '',
                gridDataframes: {
                    vueEditTranslatedRecordsOfGridDataframe_display: false,
                    vueEditTranslatedRecordsOfGridDataframe_display: false,
                },
                overlay_dataframe: false,
                vueGridOfTranslatedTextDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                vueGridOfTranslatedTextDataframe_button_translateWithGoogle: true,
                vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile: false
            }
        },
        props: ['vueGridOfTranslatedTextDataframe_prop', ],
        components: {
            'vueEditTranslatedRecordsOfGridDataframe': vueEditTranslatedRecordsOfGridDataframeComp,
        },
        created() {
            this.vueGridOfTranslatedTextDataframe_fillInitData();
            vueGridOfTranslatedTextDataframeVar = this;
        },
        computed: {
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            vueGridOfTranslatedTextDataframe_translatedText_display: function() {
                if (this.state.vueGridOfTranslatedTextDataframe_translatedText_items.length) {
                    return true;
                }
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueGridOfTranslatedTextDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueGridOfTranslatedTextDataframe');
            },
            refreshVueGridOfTranslatedTextDataframe() {
                var targetLanguage = excon.getFromStore('vueGridOfTranslatedTextDataframe', 'targetLanguage');
                return targetLanguage;
            }
        },
        watch: {
            vueGridOfTranslatedTextDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueGridOfTranslatedTextDataframe_fillInitData();
                    } else {
                        console.log("vueGridOfTranslatedTextDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            refreshVueGridOfTranslatedTextDataframe: {
                handler: function(val, oldVal) {
                    this.vueGridOfTranslatedTextDataframe_fillInitData();
                }
            },
        },
        methods: {
            vueEditTranslatedRecordsOfGridDataframe_updateStore: function(data) {
                Vue.set(this.vueEditTranslatedRecordsOfGridDataframe_data, 'parentData', data);
            },
            vueGridOfTranslatedTextDataframe_translatedText_showDetailvueEditTranslatedRecordsOfGridDataframe: function(dataRecord) {

                this.vueEditTranslatedRecordsOfGridDataframe_updateStore(dataRecord);
                this.vueEditTranslatedRecordsOfGridDataframe_comp = "";
                this.vueEditTranslatedRecordsOfGridDataframe_comp = "vueEditTranslatedRecordsOfGridDataframe";
                var key = dataRecord.id ? dataRecord.id : (dataRecord.Id | dataRecord.ID);
                Vue.set(this.vueEditTranslatedRecordsOfGridDataframe_data, 'key', key);
                Vue.set(this.vueEditTranslatedRecordsOfGridDataframe_data, 'refreshInitialData', false);
                excon.saveToStore('vueGridOfTranslatedTextDataframe', 'vueGridOfTranslatedTextDataframe_translatedText_selectedrow', dataRecord);
                excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", true);

            },
            vueEditTranslatedRecordsOfGridDataframe_updateStore: function(data) {
                Vue.set(this.vueEditTranslatedRecordsOfGridDataframe_data, 'parentData', data);
            },
            vueGridOfTranslatedTextDataframe_translatedText_editmethod: function(dataRecord) {

                this.vueEditTranslatedRecordsOfGridDataframe_updateStore(dataRecord);
                this.vueEditTranslatedRecordsOfGridDataframe_comp = "";
                this.vueEditTranslatedRecordsOfGridDataframe_comp = "vueEditTranslatedRecordsOfGridDataframe";
                var key = dataRecord.id ? dataRecord.id : (dataRecord.Id | dataRecord.ID);
                Vue.set(this.vueEditTranslatedRecordsOfGridDataframe_data, 'key', key);
                Vue.set(this.vueEditTranslatedRecordsOfGridDataframe_data, 'refreshInitialData', false);
                excon.saveToStore('vueGridOfTranslatedTextDataframe', 'vueGridOfTranslatedTextDataframe_translatedText_selectedrow', dataRecord);
                excon.setVisibility("vueEditTranslatedRecordsOfGridDataframe", true);

            },
            getDefaultDataHeaders_vueGridOfTranslatedTextDataframe_translatedText: function() {

                var defaultDataHeaders = [{
                    "text": "Id",
                    "keys": "id",
                    "value": "Id",
                    "class": "hidden text-start",
                    "width": ""
                }, {
                    "text": "Key",
                    "keys": "_key",
                    "value": "Key",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Text",
                    "keys": "text",
                    "value": "Text",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Edit Text",
                    "keys": "Edit Text",
                    "value": "name",
                    "sortable": false
                }];
                this.state.vueGridOfTranslatedTextDataframe_translatedText_headers = defaultDataHeaders;
            },

            vueGridOfTranslatedTextDataframe_translateWithGoogle: function() {
                this.retrieveTranslatedText()
            },

            vueGridOfTranslatedTextDataframe_downloadTargetPropertyFile: function() {
                this.downloadTargetFile()
            },

            vueGridOfTranslatedTextDataframe_fillInitData: function() {
                excon.saveToStore('vueGridOfTranslatedTextDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueGridOfTranslatedTextDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueGridOfTranslatedTextDataframe_prop.key?this.vueGridOfTranslatedTextDataframe_prop.key:this.$store.state.vueGridOfTranslatedTextDataframe.key";
                    }
                }
                allParams["id"] = eval(this.namedParamKey);

                allParams['dataframe'] = 'vueGridOfTranslatedTextDataframe';
                allParams['targetLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe', 'targetLanguage');
                allParams['projectId'] = excon.getFromStore('vueGridOfTranslatedTextDataframe', 'projectId');
                allParams['sourceLanguage'] = excon.getFromStore('vueGridOfTranslatedTextDataframe', 'sourceLanguage');
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueGridOfTranslatedTextDataframe";
                        vueGridOfTranslatedTextDataframeVar.updateState(response);
                        vueGridOfTranslatedTextDataframeVar.vueGridOfTranslatedTextDataframe_populateJSONData(response);
                    }
                    self.buttonShowHide(response);
                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueGridOfTranslatedTextDataframe_populateJSONData: function(response) {
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueGridOfTranslatedTextDataframe", false);
            },

            buttonShowHide(response) {
                var retrivedData = response.additionalData.vueGridOfTranslatedTextDataframe_translatedText.dictionary;
                if (retrivedData.length > 1) {
                    this.vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile = true;
                    this.vueGridOfTranslatedTextDataframe_button_translateWithGoogle = false;
                } else {
                    this.vueGridOfTranslatedTextDataframe_button_translateWithGoogle = true;
                    this.vueGridOfTranslatedTextDataframe_button_downloadTargetPropertyFile = false;
                }
            },

            retrieveTranslatedText() {
                var allParams = this.state;
                var self = this;
                axios({
                    method: 'post',
                    url: '/translatorAssistant/translateWithGoogle',
                    data: allParams
                }).then(function(responseData) {
                    self.vueGridOfTranslatedTextDataframe_fillInitData();
                    self.vueGridOfTranslatedTextDataframe_button_translateWithGoogle = false;
                    var response = responseData.data;
                });
            },

            downloadTargetFile() {
                var allParams = this.state;
                var self = this;
                //var fileName =
                var fileURL = '/translatorAssistant/downloadTargetFile/' + allParams.projectId + allParams.targetLanguage
                var fileLink = document.createElement('a');
                fileLink.href = fileURL;
                //fileLink.setAttribute('download');
                document.body.appendChild(fileLink);
                fileLink.click();
            },
        },
    }

    const vueTranslatorDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text"><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>Translator Page</v-card-title></v-toolbar-title>
                                  </v-toolbar>
                                  <v-row>
                                  <v-col cols="12" xs="12" sm="12" md="4" xl="4" lg="4"><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueTranslatorDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-row style="margin:auto;">Project Name:<v-text-field
            flat
            solo
            label="Project Name *"
            v-model = "state.vueTranslatorDataframe_project_name"

            readonly

            style="width:auto; height:40px; margin-top:-12px;null"
            background-color="white"

          ></v-text-field></v-row></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-row style="margin:auto;">Source Language:<v-text-field
            flat
            solo
            label="Source Language"
            v-model = "state.vueTranslatorDataframe_project_sourceLanguage"

            readonly

            style="width:auto; height:40px; margin-top:-12px;null"
            background-color="white"

          ></v-text-field></v-row></v-flex>

						<v-flex xs12 sm12 md10 lg10 xl10  >
            <v-combobox
          v-model = "state.vueTranslatorDataframe_project_languages"
          :items="state.vueTranslatorDataframe_project_languages_items"

          label="Target Languages"

          item-text="ename"
          item-value="id"
          small-chips
          multiple
          hide-no-data
          hide-selected




        ></v-combobox>
        </v-flex>

						<v-flex xs12 sm12 md1 lg1 xl1  ><v-btn style='background-color:#1976D2; color:white; margin-top:13px;'  :disabled="disableAddButton" id='vueTranslatorDataframe_add' @click.stop='vueTranslatorDataframe_add_method'>Add</v-btn>
</v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  >
           <v-list >
                  <v-subheader>Languages</v-subheader>
                  <v-list-item-group >
                       <v-list-item  v-for="(item, i) in state.vueTranslatorDataframe_project_language_items" :key="i">
                          <v-list-item-content>
                               <v-list-item-title v-model = "state.vueTranslatorDataframe_project_language" v-text="item.language"  @click.stop="translatedText(item)">

                               </v-list-item-title>
                          </v-list-item-content>
                       </v-list-item>
                  </v-list-item-group>
           </v-list>
        </v-flex>
	 <div id='vueTranslatorDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueTranslatorDataframe-errorContainer'></div></font>
</v-col>
                                  <v-col cols="12" xs="12" sm="12" md="8" xl="8" lg="8"><vueGridOfTranslatedTextDataframe v-if="isHidden"/></v-col></v-row>
                                  </v-card></v-flex>`,
        data: function() {
            return {
                vueTranslatorDataframe_project_name_rule: [v=>!!v || 'Project Name is required', ],

                vueTranslatorDataframe_project_languages_search: null,

                overlay_dataframe: false,
                vueTranslatorDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                isHidden: false,
                disableAddButton:true,
            }
        },
        props: ['vueTranslatorDataframe_prop', ],
        components: {
            'vueGridOfTranslatedTextDataframe': vueGridOfTranslatedTextDataframeComp,
        },
        created() {
            this.vueTranslatorDataframe_fillInitData();
            vueTranslatorDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueTranslatorDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueTranslatorDataframe');
            },
            disableWhenItemExistsComputed: function() {
                return this.state.vueTranslatorDataframe_project_languages;
            },
        },
        watch: {
            vueTranslatorDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueTranslatorDataframe_fillInitData();
                    } else {
                        console.log("vueTranslatorDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            disableWhenItemExistsComputed: {
                deep: true,
                handler: function(val, oldVal) {
                    this.disableAddButton();
                }

        },
        methods: {

            vueTranslatorDataframe_add_method: function(addressValue) {
                this.addLanguage()
            },

            vueTranslatorDataframe_fillInitData: function() {
                excon.saveToStore('vueTranslatorDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueTranslatorDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueTranslatorDataframe_prop.key?this.vueTranslatorDataframe_prop.key:this.$store.state.vueTranslatorDataframe.key";
                    }
                }
                allParams['id'] = this.$route.params.routeId ? this.$route.params.routeId : 1;
                allParams['dataframe'] = 'vueTranslatorDataframe';
                var projectDetails = excon.getFromStore('vueTranslatorAssistantDataframe', 'vueTranslatorAssistantDataframe_project_list')
                if (projectDetails.id == "" || projectDetails.id == undefined) {
                    allParams['projectId'] = excon.getFromStore('vueTranslatorAssistantDataframe', 'currentProjectId')
                } else {
                    allParams['projectId'] = projectDetails.id
                }
                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueTranslatorDataframe";
                        vueTranslatorDataframeVar.updateState(response);
                        vueTranslatorDataframeVar.vueTranslatorDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueTranslatorDataframe_populateJSONData: function(response) {

                this.key_vueTranslatorDataframe_project_id_projectId = response['key_vueTranslatorDataframe_project_id_projectId'] ? response['key_vueTranslatorDataframe_project_id_projectId'] : ""
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueTranslatorDataframe", false);
            },
            addLanguage() {
                var allParams = this.state;
                var self = this;
                allParams['dataframe'] = 'vueTranslatorDataframe';
                allParams['projectId'] = Number(this.state.keys.projectId);
                axios({
                    method: 'post',
                    url: '/translatorAssistant/addLanguage',
                    data: allParams
                }).then(function(responseData) {
                    self.vueTranslatorDataframe_fillInitData()
                    var response = responseData.data;
                });
            },
            disableAddButton() {

                if (this.state.vueTranslatorDataframe_project_languages == null || this.state.vueTranslatorDataframe_project_languages == undefined) {
                    this.disableAddButton = true;
                } else {
                    this.disableAddButton = false;
                }
            },

            translatedText(params) {
                var previouslyClickedValue = excon.getFromStore('vueGridOfTranslatedTextDataframe', 'targetLanguage')
                if (previouslyClickedValue == "") {
                    this.isHidden = !this.isHidden
                } else {
                    this.isHidden = true
                }
                excon.saveToStore('vueGridOfTranslatedTextDataframe', 'targetLanguage', params.language)
                excon.saveToStore('vueGridOfTranslatedTextDataframe', 'projectId', this.state.keys.projectId)
                excon.saveToStore('vueGridOfTranslatedTextDataframe', 'sourceLanguage', this.state.vueTranslatorDataframe_project_sourceLanguage)

            }
            ,
        },
    }

    const vueTranslatorAssistantDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text"><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>Translator Assistant</v-card-title></v-toolbar-title>
                                  </v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueTranslatorAssistantDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md4 lg4 xl4  >
            <v-combobox
          v-model = "state.vueTranslatorAssistantDataframe_project_list"
          :items="state.vueTranslatorAssistantDataframe_project_list_items"

          label="Project Lists"

          item-text="name"
          item-value="id"
          small-chips

          hide-no-data
          hide-selected


            @change='vueTranslatorAssistantDataframe_onSelect_vueTranslatorAssistantDataframe_project_list'

        ></v-combobox>
        </v-flex>
	 <div id='vueTranslatorAssistantDataframe-errorContainer'></div>
<div v-show="visibility.vueTranslatorDataframe" max-width="500px"><router-view name='vueTranslatorDataframe'></router-view></div><v-dialog v-model="visibility.vueCreateProjectForTranslationDataframe"   width='initial' max-width='500px'><vueCreateProjectForTranslationDataframe ref='vueCreateProjectForTranslationDataframe_ref' :vueCreateProjectForTranslationDataframe_prop='vueCreateProjectForTranslationDataframe_data' resetForm=true></vueCreateProjectForTranslationDataframe></v-dialog></v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md2 lg2 xl2 > <v-btn  class='text-capitalize ' type='button' id='vueTranslatorAssistantDataframe-createProject' @click.stop='vueTranslatorAssistantDataframe_createProject' style='background-color:#1976D2; color:white;' >New Project</v-btn>
</v-flex><v-flex xs12 sm12 md10 lg10 xl10 > <v-btn href='null' class='text-capitalize ' text id='vueTranslatorAssistantDataframe-translation' @click.prevent='vueTranslatorAssistantDataframe_translation' style='background-color:#1976D2; color:white;' :disabled='disableWhenItemExists' >translation</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueTranslatorAssistantDataframe-errorContainer'></div></font>

                                  </v-card></v-flex>`,
        data: function() {
            return {
                vueTranslatorDataframe_data: {
                    key: ''
                },
                vueCreateProjectForTranslationDataframe_data: {
                    key: ''
                },

                vueTranslatorAssistantDataframe_project_list_search: null,

                overlay_dataframe: false,
                vueTranslatorAssistantDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
                disableWhenItemExists: true,
            }
        },
        props: ['vueTranslatorAssistantDataframe_prop', ],
        components: {
            'vueTranslatorDataframe': vueTranslatorDataframeComp,
            'vueCreateProjectForTranslationDataframe': vueCreateProjectForTranslationDataframeComp,
            'vueGridOfTranslatedTextDataframe': vueGridOfTranslatedTextDataframeComp,
        },
        created() {
            this.vueTranslatorAssistantDataframe_fillInitData();
            vueTranslatorAssistantDataframeVar = this;
        },
        computed: {
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            randomKey: function() {
                excon.generateRandom();
            },
            visibility() {
                return this.$store.getters.getVisibilities;
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueTranslatorAssistantDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueTranslatorAssistantDataframe');
            },
            disableWhenItemExistsComputed: function() {
                return this.state.vueTranslatorAssistantDataframe_project_list;
            }
        },
        watch: {
            vueTranslatorAssistantDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueTranslatorAssistantDataframe_fillInitData();
                    } else {
                        console.log("vueTranslatorAssistantDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },
            disableWhenItemExistsComputed: {
                deep: true,
                handler: function(val, oldVal) {
                    this.disableTranslationButton();
                }
            }
        },
        methods: {
            vueTranslatorAssistantDataframe_onSelect_vueTranslatorAssistantDataframe_project_list: function(_params) {
                this.disableTranslationButton();
            },
            vueTranslatorAssistantDataframe_translation: function(_param) {

                var routeId = 0
                this.$router.push({
                    name: 'vueTranslatorDataframe',
                    path: 'vueTranslatorDataframe',
                    params: {
                        vueTranslatorDataframe: "test",
                        routeId: routeId
                    }
                })
            },
            vueTranslatorAssistantDataframe_createProject: function() {

                //todo add if refDataframe exist but route is not defined. remove the following code if its scope is limited.
                excon.setVisibility("vueCreateProjectForTranslationDataframe", true);
            },

            vueTranslatorAssistantDataframe_fillInitData: function() {
                excon.saveToStore('vueTranslatorAssistantDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueTranslatorAssistantDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueTranslatorAssistantDataframe_prop.key?this.vueTranslatorAssistantDataframe_prop.key:this.$store.state.vueTranslatorAssistantDataframe.key";
                    }
                }
                allParams['id'] = this.$route.params.routeId ? this.$route.params.routeId : 1;
                allParams['dataframe'] = 'vueTranslatorAssistantDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueTranslatorAssistantDataframe";
                        vueTranslatorAssistantDataframeVar.updateState(response);
                        vueTranslatorAssistantDataframeVar.vueTranslatorAssistantDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueTranslatorAssistantDataframe_populateJSONData: function(response) {
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueTranslatorAssistantDataframe", false);
            },
            disableTranslationButton() {
                if (this.state.vueTranslatorAssistantDataframe_project_list == null || this.state.vueTranslatorAssistantDataframe_project_list == undefined) {
                    this.disableWhenItemExists = true;
                } else {
                    this.disableWhenItemExists = false;
                }
            },
        },
    }

    const vueGettingStartedDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card>
                                 <v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text"><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>Getting Started</v-card-title></v-toolbar-title></v-toolbar>

                                 <v-container>
                                 <v-container class="firstContainer">
                                 <v-row>
                                 <v-flex xs12 sm12 md6 lg6 xl6><div class="hidden-sm-and-down"><div style="margin-top:20%;"></div></div><span style="font-size:170%;"><b>How to Choose an App Developer</b></span></v-flex>
                                 <v-flex xs12 sm12 md6 lg6 xl6><div class="hidden-md-and-up"><div style="margin-top:10%;"></div></div>
                                 <iframe width="100%" height="200%" src="https://cdn.embedly.com/widgets/media.html?src=https%3A%2F%2Fwww.youtube.com%2Fembed%2FjFLFVlBbLNA%3Ffeature%3Doembed&display_name=YouTube&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DjFLFVlBbLNA&image=https%3A%2F%2Fi.ytimg.com%2Fvi%2FjFLFVlBbLNA%2Fhqdefault.jpg&key=96f1f04c5f4143bcb0f2e68c87d65feb&type=text%2Fhtml&schema=youtube" scrolling="no" title="YouTube embed" frameborder="0" allow="autoplay; fullscreen" allowfullscreen="true"></iframe>
                                 </v-flex>
                                 </v-row><br><br><br><br><br><br><br><br><br><br><br><br>

                                 <v-row><v-col cols="4"></v-col><v-col cols="4"><v-img height ="90px" width="56px" src="assets/123.svg"></v-img></v-col>

                                 </v-row><br><br>
                                 </v-container>
                                 <v-spacer></v-spacer>

                                 <v-container class="solutionPage">
                                 <v-container class="secondContainer">
                                 <v-row>
                                 <v-col ><v-card-actions class ="justify-center"><h1><b>Solutions</b></h1></v-card-actions></v-col>
                                 </v-row>
                                 <v-row>
                                 <v-flex xs12 sm12 md6 lg6 xl6><v-img src="assets/FirstPagePhoto.svg"></v-img></v-flex>
                                 <v-flex xs12 sm12 md6 lg6 xl6><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Full Stack Web App</b></h1></v-card-actions><br><p>A Full stack developer is an engineer who can design and develop an end-to-end application independently by handling all the work of coding, databases, servers and platforms. Full stack projects can be further classified as web stack, mobile stack or native application stack depending on the solution stack being used.</p></v-flex>
                                 </v-row><br><br>
                                 </v-container>

                                 <v-container class="thirdContainer">
                                 <v-row>
                                 <v-flex xs12 sm12 md6 lg6 xl6><h1 style="color: #2c3442;"><b>Development</b></h1 style="color: #2c3442;"><br><p>Web development is the work involved in developing a web site for the Internet (World Wide Web) or an intranet (a private network). Web development can range from developing a simple single static page of plain text to complex web-based internet applications (web apps), electronic businesses, and social network services.</p></v-flex>
                                 <v-flex xs12 sm12 md6 lg6 xl6><v-img src="assets/DevelopmentPageFirstPhoto.svg"></v-img></v-flex>
                                 </v-row><br><br>
                                 </v-container>

                                 <v-container class="fourthContainer">
                                 <v-row>
                                 <v-flex xs12 sm12 md6 lg6 xl6><v-img src="assets/SaasPageFirstPhoto.svg"></v-img></v-flex>
                                 <v-flex xs12 sm12 md6 lg6 xl6><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Mobile Development (PWA) and SaaS develoment</b></h1></v-card-actions><br><p>Mobile app development and design Services are being used highly every day and the company is generating profits of these mobile apps platforms to maintain their customers and improve their business.</p></v-flex>
                                 </v-row><br><br>
                                 </v-container>
                                 </v-container><v-spacer></v-spacer>

                                 <v-container class="strategySession" style = "background-color: #f1f0ec;">
                                 <v-row><v-flex xs12 sm12 md12 lg12 xl12><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Schedule a Strategy Session</b></h1></v-card-actions></v-flex></v-row>
                                 <v-row><v-flex><v-card-actions class="justify-center"><v-btn class="text-capitalize"  style="background-color: #2c3442; color:white; border-radius:5px; width:25%; height:45px; ">Click Here</v-btn></v-card-actions></v-flex></v-row>
                                 <v-row><v-flex><v-card-actions class="justify-center"><p>We are happy to meet with you and discuss your project or idea in details. This session would help you and us to understand better the problem and possible solutions. Such session are highly beneficial and provide you with entry point for your project.</p></v-card-actions></v-flex></v-row>
                                 </v-container>

                                 <v-container class="methodology">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Methodology</b></h1></v-card-actions></v-col></v-row>
                                 <v-row>
                                 <v-col cols="3"><v-img src="assets/onBudget.svg"></v-img></v-col>
                                 <v-col cols="3"><v-img src="assets/fixedPrice.svg"></v-img></v-col>
                                 <v-col cols="3"><v-img src="assets/agile.svg"></v-img></v-col>
                                 <v-col cols="3"><v-img src="assets/onTime.svg"></v-img></v-col>
                                 </v-row>
                                 <v-row>
                                 <v-col cols="3"><v-card-actions class="justify-center">On Budget</v-card-actions></v-col>
                                 <v-col cols="3"><v-card-actions class="justify-center">Fixed Price</v-card-actions></v-col>
                                 <v-col cols="3"><v-card-actions class="justify-center">Agile</v-card-actions></v-col>
                                 <v-col cols="3"><v-card-actions class="justify-center">On Time</v-card-actions></v-col>
                                 </v-row>
                                 </v-container>



                                 <v-container class="technology">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Technology</b></h1></v-card-actions></v-col></v-row>
                                 <v-row><v-col cols="4"><v-img src="assets/java.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/javascript.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/grails.svg"></v-img></v-col></v-row>
                                 <v-row><v-col cols="4"><v-img src="assets/vueJS.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/kafka.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/oracle.svg"></v-img></v-col></v-row>
                                 <v-row><v-col cols="4"><v-img src="assets/nodeJS.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/kubernetes.svg"></v-img></v-col> <v-col cols="4"><v-img src="assets/mySql.svg"></v-img></v-col></v-row>
                                 </v-container>

                                 <v-container class="clients">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Clients</b></h1></v-card-actions></v-col></v-row>
                                 <v-row><v-flex xs12 sm12 md6 lg6 xl6><v-img src="assets/globeChalet.svg"></v-img></v-flex>      <v-flex xs12 sm12 md6 lg6 xl6><v-img src="assets/coachClone.svg"></v-img></v-flex></v-row>
                                 </v-container>

                                 <v-container class="careers">
                                 <v-row><v-flex><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Careers</b></h1></v-card-actions></v-flex></v-row>
                                 <v-row>
                                 <v-flex xs12 sm12 md6 lg6 xl6><p>Solving new challenges ?</p><br><p>Do you enjoy and familiar in programming in Java and/or Python and/or PHP and/or NodeJS and/or Javascript and/or Angular/React/Vue ?</p><br>
                                 <p>Are you a quick learner, interested in the next big challenge in your career ?</p><br><p>If so,then....</p><br><p>Apply for full-time employment/sub-contract or internship.</p></v-flex>

                                 <v-flex xs12 sm12 md6 lg6 xl6><v-img src="assets/careersPagePhoto.svg"></v-img></v-flex>
                                 </v-row>
                                 </v-container>

                                 <v-container class="aboutUs" style="background-image:url('assets/groupBackground.svg'); padding:70px;">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>About Us</b></h1></v-card-actions></v-col></v-row>
                                 <v-row><v-flex><v-card-actions class="justify-center"><p>We build and design beautiful Mobile Applications, Web Dashboards, SaaS Websites and Wordpress
                                 websites for companies and entrepreneurs who are looking to receive their software on time and on budget.
                                 For those who are interested in cost overruns or headaches dealing with rookie developers who will disappoint them, we normally recommend a freelance website such as upwork.</p></v-card-actions></v-flex></v-row>
                                 </v-container>

                                 <v-container class="contactUs" style = "background-color: #f1f0ec;">
                                 <v-row><v-col><v-card-actions class = "justify-center"><h1 style="color: #2c3442;"><b>Contact Us</b></h1></v-card-actions></v-col></v-row>
                                 <v-row no-gutters><v-col cols="2"></v-col><v-col cols="6"><v-text-field single-line outlined placeholder="Your email address"></v-text-field></v-col><v-col cols="2"><v-btn  style="background-color: #2c3442; color:white; height:55px; width:100%; border-top-right-radius:15px; border-bottom-right-radius:15px; border-top-left-radius:0px; border-bottom-left-radius:0px; ">Send</v-btn></v-col><v-col cols="2"></v-col></v-row>
                                 <v-row><v-col><v-card-actions class="justify-center"><p>Fill you email or phone and we will get back to you.</p></v-card-actions></v-col></v-row>
                                 </v-container>


                                 <v-container class="footerPage" style = "background-color: #f1f0ec; margin-top:100px;">

                                 <v-container class="contactDetails">
                                 <v-row>
                                 <v-flex xs6 sm6 md2 lg2 xl2><v-img src="assets/elintegroLogo.png" style="margin-bottom:20%;"></v-img></v-flex>

                                 <v-flex xs12 sm12 md2 lg2 xl2></v-flex>

                                 <v-flex xs12 sm12 md4 lg4 xl4>
                                 <v-row><v-card-actions class="justify-center>"<h3 style="color: #2c3442;"><b>CONTACT INFO</b></h3></v-card-actions></v-row><br>
                                 <v-row><v-flex xs2 sm2 md1 lg1 xl1><v-img src="assets/location.svg"></v-img></v-flex><v-flex xs1 sm1 md0 lg0 xl0></v-flex><v-flex xs9 sm9 md3 lg3 xl3>Locations:<br>Monetreal, Canada,Tel-Aviv, Israel,Kathmandu,Nepal</v-flex></v-row>
                                 <v-row><v-flex xs2 sm2 md1 lg1 xl1><v-img src="assets/phone.svg"></v-img></v-flex><v-flex xs1 sm1 md0 lg0 xl0></v-flex> <v-flex xs9 sm9 md3 lg3 xl3>Phone:<br>USA:+1 (603) 329-3195<br>CANADA:+1 (438)792 1079<br>UK:+44 (1273) 93 0312<br>IL:+972 (54) 557 9687 </v-flex></v-row>
                                 <v-row><v-flex xs2 sm2 md1 lg1 xl1><v-img src="assets/atTheRateIcon.svg"></v-img></v-flex><v-flex xs1 sm1 md0 lg0 xl0></v-flex> <v-flex xs9 sm9 md3 lg3 xl3>Email:<br>elintegroinc@gmail.com</v-flex></v-row>
                                 <v-row><v-flex xs2 sm2 md1 lg1 xl1><v-img src="assets/linkIcon.svg"></v-img></v-flex><v-flex xs1 sm1 md0 lg0 xl0></v-flex> <v-flex xs9 sm9 md3 lg3 xl3>Website:<br>http://elintegro.com </v-flex></v-row>
                                 </v-flex>
                                 <v-flex xs12 sm12 md4 lg4 xl4>
                                 <v-row><v-card-actions class="justify-center"><h3 style="color: #2c3442;"><b>SERVICES</b></h3></v-card-actions></v-row>
                                 <v-row><a href="#" style="color:inherit;">Mobile Applications</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Web Applications</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Custom Software</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Wordpress Sites</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Traffic Generation for Websites</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Sales Conversion for Websites</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Copy Writing for Websites</a></v-row><br>
                                 <v-row><a href="#" style="color:inherit;">Emotional and Practical Support for Entrepreneurs</a></v-row>
                                 </v-flex>
                                 </v-row><br><br>
                                 </v-container>

                                 <v-container class="menuButtons">
                                 <v-row>
                                 <v-flex xs12 sm12 md6 lg6 xl6>
                                 <v-row><h3 style="color: #2c3442; margin-left:15px;"><b>NEWSLETTER</b></h3><br><br><br></v-row>
                                 <v-row no-gutters>
                                 <v-flex xs8 sm8 md4 lg4 xl4><v-text-field single-line outlined placeholder="Your email address"></v-text-field></v-flex> <v-flex xs4 sm4 md2 lg2 xl2><v-btn  style="background-color: #2c3442; color:white; height:55px; width:70%; border-top-right-radius:15px; border-bottom-right-radius:15px; border-top-left-radius:0px; border-bottom-left-radius:0px;">Send</v-btn></v-flex>
                                 </v-row>
                                 </v-flex>
                                 <v-flex xs12 sm12 md6 lg6 xl6v>
                                 <v-row><v-flex xs2 sm2 md1 lg1 xl1><a href="http://localhost:8099/#/vueElintegroBannerDataframe/0" style="color:inherit">Home</a></v-flex> <v-flex xs2 sm2 md1 lg1 xl1></v-flex> <v-flex xs2 sm2 md1 lg1 xl1><a href="http://localhost:8099/#/vueTechnologiesDataframe/0" style="color:inherit">Technologies</a></v-flex> <v-flex xs2 sm2 md2 lg2 xl2></v-flex> <v-flex xs2 sm2 md1 lg1 xl1><a href="http://localhost:8099/#/vueCareersDataframe/0" style="color:inherit">Careers</a></v-flex></v-row>
                                 <v-row><v-flex xs2 sm2 md1 lg1 xl1><a href="http://localhost:8099/#/vueClientProjectDataframe/0" style="color:inherit">Clients</a></v-flex> <v-flex xs2 sm2 md1 lg1 xl1></v-flex> <v-flex xs2 sm2 md1 lg1 xl1><a href="http://localhost:8099/#/vueGettingStartedDataframe/0" style="color:inherit">Getting Started</a></v-flex> <v-flex xs2 sm2 md2 lg2 xl2></v-flex> <v-flex xs2 sm2 md1 lg1 xl1><a href="http://localhost:8099/#/vueContactUsPageDataframe/0" style="color:inherit">Contact Us</a></v-flex></v-row>
                                 </v-flex>
                                 </v-row><br><br><br>
                                 <v-row><v-img src="assets/straightLine.svg"></v-img></v-row><br><br><br>
                                 <v-row><span>&copy COPYRIGHT - 2019 ELINTEGRO INC. ALL RIGHTS RESERVED</span></v-row><br><br>
                                 <v-row>
                                 <p>We build and design beautiful Mobile Applications, Web Dashboards, SaaS Websites and Wordpress
                                 websites for companies and entrepreneurs who are looking to receive their software on time and on budget.
                                 For those who are interested in cost overruns or headaches dealing with rookie developers who will disappoint them,
                                 we normally recommend a freelance website such as upwork.
                                 </p>
                                 </v-row>
                                 </v-container>


                                 </v-container>

                                 </v-container>
                                 <v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueGettingStartedDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueGettingStartedDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueGettingStartedDataframe-errorContainer'></div></font>


                                 </v-card></v-flex>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueGettingStartedDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueGettingStartedDataframe_prop', ],
        created() {
            vueGettingStartedDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueGettingStartedDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueGettingStartedDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueGettingStartedDataframe", false);
            },
        },
    }

    const vueCareersDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text"><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>Careers</v-card-title></v-toolbar-title>
                                 <v-spacer></v-spacer>
                                 <vueCareersPageButtonDataframe/>
                                  </v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueCareersDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueCareersDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueCareersDataframe-errorContainer'></div></font>
</v-card></v-flex>`,
        data: function() {
            return {
                overlay_dataframe: false,
                vueCareersDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueCareersDataframe_prop', ],
        created() {
            vueCareersDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueCareersDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueCareersDataframe');
            },

        },
        methods: {
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueCareersDataframe", false);
            },
        },
    }

    const vueElintegroApplicantsDataframeComp = {
        template: `<v-flex xs12 sm12 md12 lg12 xl12><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;"><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>Applicants Detail</v-card-title></v-toolbar-title>
                                  </v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueElintegroApplicantsDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>
 <div id='vueElintegroApplicantsDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
</v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueElintegroApplicantsDataframe-errorContainer'></div></font>

                                  <v-card class ="applicant">
						<v-flex xs12 sm12 md12 lg12 xl12  ><v-card v-show="vueElintegroApplicantsDataframe_applicant_display"><v-divider/>


            <v-row><v-col cols='8'></v-col>
            <v-col cols="4">
                    <v-text-field
                    v-model="vueElintegroApplicantsDataframe_applicant_search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
                    class='pa-3'
            ></v-text-field>
            </v-col></v-row>

       <v-data-table
            :headers="state.vueElintegroApplicantsDataframe_applicant_headers"
            :items="state.vueElintegroApplicantsDataframe_applicant_items"
            :items-per-page="-1"
            :search='vueElintegroApplicantsDataframe_applicant_search'
            hide-default-footer

    >


        <template slot="item" slot-scope="props">
          <tr @click.stop="vueElintegroApplicantsDataframe_applicant_showDetailvueElintegroApplicantDetailsDataframe(props.item)" :key="props.item.Id">

<td class='hidden text-start'>{{ props.item.Id }}</td>
<td class='text-start'>{{ props.item.FirstName }}</td>
<td class='text-start'>{{ props.item.LastName }}</td>
<td class='text-start'>{{ props.item.Email }}</td>
<td class='text-start'>{{ props.item.Phone }}</td>
          </tr>
        </template>

    </v-data-table></v-card>
        <v-dialog v-model="visibility.vueElintegroApplicantDetailsDataframe" width='auto' max-width='800' ><component :is='vueElintegroApplicantDetailsDataframe_comp' ref='vueElintegroApplicantDetailsDataframe_ref' :vueElintegroApplicantDetailsDataframe_prop="vueElintegroApplicantDetailsDataframe_data"></component></v-dialog>
</v-flex>
	</v-card></v-card></v-flex>`,
        data: function() {
            return {
                vueElintegroApplicantDetailsDataframe_data: {
                    key: '',
                    refreshGrid: true,
                    parentData: {}
                },
                vueElintegroApplicantDetailsDataframe_comp: '',

                drag: '',
                vueElintegroApplicantsDataframe_applicant_search: '',
                gridDataframes: {
                    vueElintegroApplicantDetailsDataframe_display: false,
                },
                overlay_dataframe: false,
                vueElintegroApplicantsDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueElintegroApplicantsDataframe_prop', ],
        components: {
            'vueElintegroApplicantDetailsDataframe': vueElintegroApplicantDetailsDataframeComp,
        },
        created() {
            this.vueElintegroApplicantsDataframe_fillInitData();
            vueElintegroApplicantsDataframeVar = this;
        },
        computed: {
            visibility() {
                return this.$store.getters.getVisibilities;
            },
            vueElintegroApplicantsDataframe_applicant_display: function() {
                if (this.state.vueElintegroApplicantsDataframe_applicant_items.length) {
                    return true;
                }
            },

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueElintegroApplicantsDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueElintegroApplicantsDataframe');
            },

        },
        watch: {
            vueElintegroApplicantsDataframe_prop: {
                deep: true,
                handler: function(val, oldVal) {
                    if (val.refreshInitialData) {
                        this.vueElintegroApplicantsDataframe_fillInitData();
                    } else {
                        console.log("vueElintegroApplicantsDataframe_prop has refreshInitialData as false or undefined. Could not refresh.");
                    }
                }
            },

        },
        methods: {
            vueElintegroApplicantDetailsDataframe_updateStore: function(data) {
                Vue.set(this.vueElintegroApplicantDetailsDataframe_data, 'parentData', data);
            },
            vueElintegroApplicantsDataframe_applicant_showDetailvueElintegroApplicantDetailsDataframe: function(dataRecord) {

                this.vueElintegroApplicantDetailsDataframe_updateStore(dataRecord);
                this.vueElintegroApplicantDetailsDataframe_comp = "";
                this.vueElintegroApplicantDetailsDataframe_comp = "vueElintegroApplicantDetailsDataframe";
                var key = dataRecord.id ? dataRecord.id : (dataRecord.Id | dataRecord.ID);
                Vue.set(this.vueElintegroApplicantDetailsDataframe_data, 'key', key);
                Vue.set(this.vueElintegroApplicantDetailsDataframe_data, 'refreshInitialData', false);
                excon.saveToStore('vueElintegroApplicantsDataframe', 'vueElintegroApplicantsDataframe_applicant_selectedrow', dataRecord);
                excon.setVisibility("vueElintegroApplicantDetailsDataframe", true);

            },
            getDefaultDataHeaders_vueElintegroApplicantsDataframe_applicant: function() {

                var defaultDataHeaders = [{
                    "text": "Id",
                    "keys": "id",
                    "value": "Id",
                    "class": "hidden text-start",
                    "width": ""
                }, {
                    "text": "First Name",
                    "keys": "firstName",
                    "value": "First Name",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Last Name",
                    "keys": "lastName",
                    "value": "Last Name",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Email",
                    "keys": "email",
                    "value": "Email",
                    "class": "text-start",
                    "width": ""
                }, {
                    "text": "Phone",
                    "keys": "phone",
                    "value": "Phone",
                    "class": "text-start",
                    "width": ""
                }];
                this.state.vueElintegroApplicantsDataframe_applicant_headers = defaultDataHeaders;
            },

            vueElintegroApplicantsDataframe_fillInitData: function() {
                excon.saveToStore('vueElintegroApplicantsDataframe', 'doRefresh', false);
                let allParams = {};

                const propData = this.vueElintegroApplicantsDataframe_prop;
                if (propData) {
                    allParams = propData;
                    if (this.namedParamKey == '' || this.namedParamKey == undefined) {
                        this.namedParamKey = "this.vueElintegroApplicantsDataframe_prop.key?this.vueElintegroApplicantsDataframe_prop.key:this.$store.state.vueElintegroApplicantsDataframe.key";
                    }
                }
                allParams['id'] = this.$route.params.routeId ? this.$route.params.routeId : 1;
                allParams['dataframe'] = 'vueElintegroApplicantsDataframe';

                this.overlay_dataframe = true;
                let self = this;
                axios.get('/dataframe/ajaxValues', {
                    params: allParams
                }).then(function(responseData) {
                    let resData = responseData.data;
                    let response = resData ? resData.data : '';
                    if (response != null && response != '' && response != undefined) {
                        response["stateName"] = "vueElintegroApplicantsDataframe";
                        vueElintegroApplicantsDataframeVar.updateState(response);
                        vueElintegroApplicantsDataframeVar.vueElintegroApplicantsDataframe_populateJSONData(response);
                    }

                    self.overlay_dataframe = false;

                }).catch(function(error) {
                    console.log(error);
                });
            },

            vueElintegroApplicantsDataframe_populateJSONData: function(response) {
            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueElintegroApplicantsDataframe", false);
            },
        },
    }

    const vueContactUsPageDataframeComp = {
        template: `<v-container class="text-xs-center">
                                 <v-layout row child-flex justify-center align-center wrap>
                                 <v-flex xs12 sm12 md6 lg6 xl6><v-card><v-toolbar dark color="blue darken-2" height="100px" style="margin-bottom:30px;">
                                 <v-toolbar-title class="white--text" style="margin:100px;"><v-card-title class='title font-weight-light' style='justify-content: space-evenly;'>Contact Us</v-card-title></v-toolbar-title>
                                  </v-toolbar><v-flex xs12 sm12 md12 lg12 xl12><v-form  ref='vueContactUsPageDataframe_form'><v-container grid-list-xl fluid><v-layout wrap>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Name *"
            v-model = "state.vueContactUsPageDataframe_contactUs_name"
            :rules = 'vueContactUsPageDataframe_contactUs_name_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  >
               <v-text-field
                 label="Email *"
                 placeholder = "Enter your email."
                 v-model="state.vueContactUsPageDataframe_contactUs_email"
                 :rules = 'vueContactUsPageDataframe_contactUs_email_rule'


                 style="width:auto; height:30px;"
                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  >
               <v-text-field
                 label="Phone"
                 v-model="state.vueContactUsPageDataframe_contactUs_phone"
                 :rules = "vueContactUsPageDataframe_contactUs_phone_rule"


                ></v-text-field>
               </v-flex>

						<v-flex xs12 sm12 md12 lg12 xl12  ><v-text-field
            label="Messages *"
            v-model = "state.vueContactUsPageDataframe_contactUs_textOfMessage"
            :rules = 'vueContactUsPageDataframe_contactUs_textOfMessage_rule'



            style="width:auto; height:40px;"
            background-color="white"
            autocomplete = off


          ></v-text-field></v-flex>
	 <div id='vueContactUsPageDataframe-errorContainer'></div>
</v-layout></v-container></v-form></v-flex>
<v-flex class="text-right"><v-card-actions><v-container fluid grid-list-lg pa-0><v-layout row justify-center wrap pa-2>
<v-flex xs12 sm12 md12 lg12 xl12 ><v-btn type='button' class='text-capitalize right' id='vueContactUsPageDataframe-save' @click='vueContactUsPageDataframe_save'  style='background-color:#1976D2; color:white;'  :loading='vueContactUsPageDataframe_save_loading' >Submit</v-btn>
</v-flex></v-layout></v-container></v-card-actions>
 <font color='red'><div id='vueContactUsPageDataframe-errorContainer'></div></font>
</v-flex></v-card></v-flex>
                                 </v-layout></v-container>`,
        data: function() {
            return {
                vueContactUsPageDataframe_contactUs_name_rule: [v=>!!v || 'Name is required', ],
                vueContactUsPageDataframe_contactUs_email_rule: [v=>!!v || 'Email is required', (v)=>/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v) || 'Email is not valid.'],
                vueContactUsPageDataframe_contactUs_phone_rule: [(v)=>/^(([(]?(\d{2,4})[)]?)|(\d{2,4})|([+1-9]+\d{1,2}))?[-\s]?(\d{2,3})?[-\s]?((\d{7,8})|(\d{3,4}[-\s]\d{3,4}))$/.test(v) || 'Phone number must be valid.'],
                vueContactUsPageDataframe_contactUs_textOfMessage_rule: [v=>!!v || 'Messages is required', ],
                overlay_dataframe: false,
                vueContactUsPageDataframe_save_loading: false,
                namedParamKey: '',
                params: {},
            }
        },
        props: ['vueContactUsPageDataframe_prop', ],
        created() {
            vueContactUsPageDataframeVar = this;
        },
        computed: {

            checkResetFormProp: function() {
                if (this.$props.resetForm) {
                    this.$refs.vueContactUsPageDataframe_form.reset()
                }
            },

            state() {
                return this.$store.getters.getState('vueContactUsPageDataframe');
            },

        },
        methods: {

            vueContactUsPageDataframe_save: function() {
                let allParams = this.state;
                allParams['vueContactUsPageDataframe_contactUs_email'] = this.state.vueContactUsPageDataframe_contactUs_email;
                allParams['email'] = this.state.vueContactUsPageDataframe_contactUs_email;
                allParams["key_vueContactUsPageDataframe_contactUs_id_id"] = this.key_vueContactUsPageDataframe_contactUs_id_id;

                allParams['dataframe'] = 'vueContactUsPageDataframe';
                console.log(allParams)
                if (this.$refs.vueContactUsPageDataframe_form.validate()) {
                    this.vueContactUsPageDataframe_save_loading = true;
                    const self = this;
                    axios({
                        method: 'post',
                        url: '/dataframe/ajaxSave',
                        data: allParams
                    }).then(function(responseData) {
                        self.vueContactUsPageDataframe_save_loading = false;
                        var response = responseData.data;

                        var ajaxFileSave = vueContactUsPageDataframeVar.params.ajaxFileSave;
                        if (ajaxFileSave) {
                            for (let i in ajaxFileSave) {
                                const value = ajaxFileSave[i];
                                allParams["key_vueContactUsPageDataframe_contactUs_id_id"] = response.nodeId[0];

                                self[value.fieldName + '_ajaxFileSave'](responseData, allParams);
                            }
                        }
                        var nodeArr = response.nodeId;
                        if (nodeArr && Array.isArray(nodeArr) && nodeArr.length) {
                            excon.saveToStore("vueContactUsPageDataframe", "key", response.nodeId[0]);
                        }

                        excon.showAlertMessage(response);
                        if (response.success) {
                        }
                    }).catch(function(error) {
                        self.vueContactUsPageDataframe_save_loading = false;
                        console.log(error);
                    });
                }

            },
            updateState: function(response) {
                this.$store.commit("updateState", response)
            },

            closeDataframe: function() {
                excon.setVisibility("vueContactUsPageDataframe", false);
            },
        },
    }

    const vueElintegroNavigationLayoutComp = {
        template: `<v-flex><v-app-bar flat color="white"  tabs style="z-index:99;">
                                   <vueElintegroNavigationDrawerDataframe/>
                                   <v-toolbar-title style="position:relative;" ><vueElintegroLogoDataframe/></v-toolbar-title>

                                   <v-spacer></v-spacer>

                                        <div class="hidden-md-and-down"><vueElintegroAppBarDataframe/></div>

                                         <vueInitDataframe/>

                                         </v-app-bar>

         </v-flex>`,
        components: {},
    }
    const vueElintegroMidSectionLayoutComp = {
        template: `<v-flex style="margin-top:30px;"><v-content>
                             <router-view :key="$route.fullPath"></router-view>
                             </v-content>
                                </v-flex>
                            `,
        components: {},
    }
    const vueElintegroFooterLayoutComp = {
        template: ` `,
        components: {},
    }
    const sectionLayoutComp = {
        template: `<v-content>
                                  <vueElintegroNavigationLayout/>
                                  <vueElintegroMidSectionLayout/>
                                  <vueElintegroFooterLayout/>
                              </v-content>`,
        components: {
            'vueElintegroNavigationLayout': vueElintegroNavigationLayoutComp,
            'vueElintegroMidSectionLayout': vueElintegroMidSectionLayoutComp,
            'vueElintegroFooterLayout': vueElintegroFooterLayoutComp,
        },
    }
    const i18n = new VueI18n({
        locale: 'en',
        messages
    });
    const router = new VueRouter({
        routes: [{
            path: '/home/:routeId',
            name: 'vueElintegroBannerDataframe',
            component: vueElintegroBannerDataframeComp
        }, {
            path: '/thank-you-message/:routeId',
            name: 'vueNewEmployeeThankYouMessageAfterSaveDataframe',
            component: vueNewEmployeeThankYouMessageAfterSaveDataframeComp
        }, {
            path: '/new-employee-applicant/:routeId',
            name: 'vueNewEmployeeApplicantDataframe',
            component: vueNewEmployeeApplicantDataframeComp
        }, {
            path: '/user-profile/:routeId',
            name: 'vueElintegroUserProfileDataframe',
            component: vueElintegroUserProfileDataframeComp
        }, {
            path: '/technologies/:routeId',
            name: 'vueTechnologiesDataframe',
            component: vueTechnologiesDataframeComp
        }, {
            path: '/client-project/:routeId',
            name: 'vueClientProjectDataframe',
            component: vueClientProjectDataframeComp
        }, {
            path: '/translator/:routeId',
            name: 'vueTranslatorDataframe',
            component: vueTranslatorDataframeComp
        }, {
            path: '/translator-assistant/:routeId',
            name: 'vueTranslatorAssistantDataframe',
            component: vueTranslatorAssistantDataframeComp
        }, {
            path: '/getting-started/:routeId',
            name: 'vueGettingStartedDataframe',
            component: vueGettingStartedDataframeComp
        }, {
            path: '/careers/:routeId',
            name: 'vueCareersDataframe',
            component: vueCareersDataframeComp
        }, {
            path: '/applicants/:routeId',
            name: 'vueElintegroApplicantsDataframe',
            component: vueElintegroApplicantsDataframeComp
        }, {
            path: '/contact-us/:routeId',
            name: 'vueContactUsPageDataframe',
            component: vueContactUsPageDataframeComp
        }, ]
    })
    var app = new Vue({
        el: '#app',
        router,
        store,
        i18n,
        vuetify: new Vuetify(),
        data() {
            return {
                drawer: null,
            }
        },
        created() {},
        components: {
            'sectionlayout': sectionLayoutComp,

        },
        methods: {

            refreshDataForGrid: function(response, fldName, operation="U") {

                const newData = response.newData;
                if (!newData)
                    return;
                const selectedRow = this[fldName + '_selectedrow'];
                const editedIndex = this.state[fldName + '_items'].indexOf(selectedRow);
                let row = {};
                for (let key in newData) {
                    let dataMap = newData[key];
                    for (let j in dataMap) {
                        if (selectedRow) {
                            if (key in selectedRow) {
                                row[key] = dataMap[j];
                            }
                        } else {
                            row[key] = dataMap[j];
                        }

                    }
                }
                if (operation === "I") {
                    this.state[fldName + '_items'].push(row)
                } else {
                    Object.assign(this.state[fldName + '_items'][editedIndex], row)
                }
                //                          this.gridDataframes[refreshParams.dataframe] = false;
            },

        },
    })
</script>
<script>

    // import Vuetify from 'vuetify'
    // Vue.use(Vuetify,{
    //     rtl:true
    // })
</script>
</body>
</html>
