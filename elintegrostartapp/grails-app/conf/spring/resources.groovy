import com.elintegro.auth.UserPasswordEncoderListener

// Place your Spring DSL code here

/**
*Author: eugenel
*
**/
import com.elintegro.erf.dataframe.DataframeSuperBean
import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import com.elintegro.erf.layout.ColumnLayout
import grails.plugin.springsecurity.SpringSecurityService
import grails.util.Environment
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler

beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)

    //tag::tokenReader[]
    /*tokenReader(JwtCookieTokenReader) {
        cookieName = 'jwt'
    }*/
    //end::tokenReader[]
    //tag::cookieClearing[]
    cookieClearingLogoutHandler(CookieClearingLogoutHandler, ['jwt'])
    //end::cookieClearing[]
   /* restAuthenticationSuccessHandler(AuthSuccessHandlerController) {
        renderer = ref('accessTokenJsonRenderer')
    }*/
    dataFrameSuper(DataframeSuperBean){

        grailsApplication  =  ref('grailsApplication')   // TODO externalize
        sessionFactory = ref('sessionFactory')           // TODO externalize
        messageSource = ref('messageSource')             // TODO externalize

    }

    def loadFromFile = { name ->
        importBeans("file:grails-app/conf/spring/"+name)
    }


    def loadFromWar = { name ->
        def resource = application.parentContext.getResource("WEB-INF/classes/spring/"+name)
        loadBeans(resource)
    }
    def loadResource = application.isWarDeployed() ? loadFromWar : loadFromFile

    loadResource "resourcesWidget.groovy"
    loadResource "resourcesPages.groovy"
    loadResource "globech/resourcesScriptBean.groovy"
    loadResource "globech/resourcesLayout.groovy"
    loadResource "globech/resourcesLayoutVue.groovy"
    loadResource "globech/resourcesVue.groovy"
    loadResource "globech/applicationManagement/resourcesApplicationForm.groovy"
    loadResource "globech/applicationManagement/resourcesApplicationManagement.groovy"
    loadResource "globech/userManagement/resourcesUserManagement.groovy"
    loadResource "globech/elintegroWebsite/resourcesElintegroVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesElintegroLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesNavigationButtonLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesMidSectionLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesSubContainerLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesCareersPageLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesNewEmployeeApplicantPageLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesClientProjectPageLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesContactUsPageLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesGettingStartedPageLayoutVue.groovy"
    loadResource "globech/elintegroWebsite/resourcesRefactorTest.groovy"
    if (Environment.current == Environment.DEVELOPMENT){
//        loadResource "resourcesEditor.groovy"
//        editorBeanLoading(EditorBeanLoading){ bean -> }
    }


    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("en","CA")
        java.util.Locale.setDefault(defaultLocale)
    }

    javascriptService(com.elintegro.erf.dataframe.service.JavascriptService){

        servletContext = ref("servletContext")//for running integration test this injection is not available
        messageSource = ref ("messageSource")

    }
	/**
	 * This should not be touched
	 */
	metaFieldService(com.elintegro.erf.dataframe.service.MetaFieldService){
		grailsApplication  =  ref('grailsApplication')   // TODO externalize
		sessionFactory = ref('sessionFactory')           // TODO externalize
	}




    currentFrameLayout(ColumnLayout){	bean->
		layoutBeanName = bean.name

		//layoutPlaceHolder = """
        //						<div style="padding:0px 0px 0px 0px;"><div style="float:left;padding:0px 30px 0px 0px;" >[firstName]</div><div style="float:left" >[lastName]</div></div>
        //			"""

        numColumns = 5
    }


	currentFrameLayout1(ColumnLayout){	bean->
		layoutBeanName = bean.name
		layoutPlaceHolder = """
				<script ="text/javascript">
			        jQuery(document).ready(function () {
//			           var theme = getTheme();
			           // Create jqxTripTabs.
			           jQuery('#ownerDetailTab').jqxTabs({ position:'top',theme:"energyBlue",selectionTracker: true});
			           jQuery('#settings div').css('margin-top', '1px');

			        });
				</script>
				<div id="ownerDetailTab" style="min-height: 380px; margin-left:20px; margin-right:20px; float:none;">
						<ul>
							<li>
								Owner
							</li><li>
								Contact
							</li>
							<li>
							    Bills
							</li>
							<li>
							    Account
							</li>
						</ul>
					
						<div><!-- Tab 1 -->
								[ALL_OTHER_FIELDS]
						</div><!-- Tab 1 -->
						<div><!-- Tab2 -->
								[owner.person]
						</div>
						<div><!-- Tab 3 -->
                            [owner.bills]
                        </div>	
                        <div><!-- Tab 4 -->	
                            [owner.account]
                        </div>		
				</div>
"""
		numColumns = 2;
	}


    resultPageHtmlBuilder( com.elintegro.erf.dataframe.ResultPageHtmlBuilder ){ bean ->
        SpringSecurityService springSecurityService

    }


	ownerDataFrame1(com.elintegro.erf.dataframe.Dataframe){ bean ->

				bean.parent = dataFrameSuper
				bean.constructorArgs = ['ownerDataFrame1']

		//hql = """select owner.id, owner.description, owner.person, owner.ownerType from Owner as owner LEFT OUTER JOIN Person as person LEFT OUTER JOIN GenericType as ownerType where owner.id=:id"""
		//hql = """select owner.id, owner.description, owner.person, owner.ownerType from Owner as owner LEFT OUTER JOIN owner.person as person LEFT OUTER JOIN owner.ownerType as GenericType where owner.id=:id"""
		/*
				hql = """select owner.id, owner.description, owner.person, owner.ownerType
							 from Owner as owner
							 LEFT OUTER JOIN owner.person as person
							 LEFT OUTER JOIN owner.ownerType as GenericType
							 where owner.id=:id"""
		*/
				 hql = """select owner.id, owner.description, owner.person
			     	from Owner as owner 
			     	LEFT OUTER JOIN owner.person as person  
			     	where owner.id=:owner_id"""


        //sql = "select owner.id, owner.description, owner.person_id, owner.owner_type_id from Owner as owner where owner.id=:id"

        initOnPageLoad=true //false by default

        //These are default values, they are here to demonstrate how to overwrite them with different Controller operation if required
        ajaxUrl = '/elintegrostartapp/dataframe/ajaxValues'
        ajaxSaveUrl = '/elintegrostartapp/dataframe/ajaxSave'
        ajaxDeleteUrl = '/elintegrostartapp/dataframe/ajaxDeleteExpire'
        ajaxInsertUrl = '/elintegrostartapp/dataframe/ajaxInsert'

        //These are default values, they are here to demonstrate how to overwrite it with different button combination, if required
        saveButton = true
        deleteButton = true
        submitButton=false
        insertButton=true
        wrapInForm=true

        //addFieldDef =[ "person":  ["widget":"DataframeWidget", "name":"person", "dataframe": ref("contactDfr")],
        //				"ownerType": ["widget":"JqxComboboxWidget", "name":"ownerType", "hql":"select id, name from GenericType where type = 'owner'" ] ]

        //addFieldDef =[ "person":  ["widget":"DataframeWidget", "name":"person", "dataframe": ref("contactDfr")]

        //TODO: change Temporary: combobox persons straightforward!!!
        addFieldDef =[ "person":  ["widget":"JqxComboboxWidget", "name":"person", "hql":"select person.id, concat(person.firstName, ' ', person.lastName) from Person as person"]
        ]
        //addFieldDef =[ "ownerType": ["widget":"JqxComboboxWidget", "name":"ownerType", "hql":"select id, name from GenericType where type = 'owner'" ] ]


        def addButton = 'addProperty'
        dataframeButtons = [ addProperty: [name:"addProperty", type: "button", url: "", script: "", refDataframe: ref("propertyDataFrame")] ]

        // javascript to run after the save:
        doAfterSave="""
						//jQuery('#dataframejQTree').tree('reload');
                       //alert("Data has been saved successfully for id = "+data.generatedKeys[0]);
                    """

				//templateEngine = new SimpleTemplateEngine()
				//currentFrameLayout = new StandardLayout()

				currentFrameLayout = ref("currentFrameLayout")

				/*
				currentFrameLayout = { StandardLayout l->

						l.layoutPlaceHolder = """
										<div style="border: 1px solid #000; padding:0px 10px 0px 0px;"><div style="float:left; padding:0px 10px 0px 0px;" ><firstName></div><div style="float:left" ><lastName></div></div>
							"""
						l.df = this
				}
				*/
			}







}
