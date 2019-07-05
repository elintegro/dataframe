import com.elintegro.erf.widget.*

import com.elintegro.erf.dataframe.DataframeSuperBean
import com.elintegro.erf.layout.ColumnLayout
import com.elintegro.erf.layout.StandardLayout

beans {

	contactDfr(com.elintegro.erf.dataframe.Dataframe){ bean ->
		bean.parent = dataFrameSuper
		bean.constructorArgs = ['contactDfr']

		hql = "select person.id, person.salutation, person.firstName, person.lastName, person.contactEmail, person.phone, person.mainPicture, person.description from Person as person where person.id=:id"

		initOnPageLoad=true //false by default

		insertFields=["person.user":"session_userid"]

		//These are default values, they are here to demonstrate how to overwrite them with different Controller operation if required
		ajaxUrl = '/elintegrostartapp/dataframe/ajaxValues'
		ajaxSaveUrl = '/elintegrostartapp/dataframe/ajaxSave'
		ajaxDeleteUrl = '/elintegrostartapp/dataframe/ajaxDeleteExpire'
		ajaxInsertUrl = '/elintegrostartapp/dataframe/ajaxInsert'

		//These are values, that overrides the default ones
		saveButton = false
		deleteButton = false
		submitButton=false
		insertButton=false
		wrapInForm=false
		//Javascript to run after the save:
		doAfterSave="""
						//jQuery('#dataframejQTree').tree('reload');
                       //alert("Data has been saved successfully for id = "+data.generatedKeys[0]);
                    """

		addFieldDef =[ /*"salutation":  [widget:"JqxComboboxWidget", name:"salutation" ]*/
					   "person.mainPicture":[widget:"JqxPictureDisplayWidget",
											 name: "person.mainPicture",
											 url:"https://elintegro.s3.amazonaws.com",
											 "width":250,
											 "height":200,
											 defaultImage:"Screenshot from 2017-02-22 15-00-30.png",
											 borderRadius:"10px"],
					   "person.contactEmail":[widget: "JqxEmailWidget",
											  "placeHolder":"Enter your contact email"],
					   "person.phone":[widget: "JqxPhoneNumberWidget",
									   "placeHolder":"Enter your phone number"]
//					   "person.languages":  [ widget:"JqxMultiSelectComboboxWidget"
//											  , name:"person.languages"
//											  , hql: """select language.id as ID, language.ename as Ename from Language as language"""
//											  ,"displayMember":"Ename"
//					   ]
		]
		//addFieldDef =[ "salutation":  [widget:"JqxComboboxWidget", name:"salutation" ] ]

		//templateEngine = new SimpleTemplateEngine()
		//currentFrameLayout = new StandardLayout()

		currentFrameLayout = ref("contactFrameLayout")
	} //end of contactDfr


} //end of beans
