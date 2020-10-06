/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gc.data

import com.elintegro.auth.Role
import com.elintegro.auth.User
import com.elintegro.auth.UserRole
import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.Testimonials
import com.elintegro.elintegrostartapp.hr.AnswerTable
import com.elintegro.elintegrostartapp.hr.Position
import com.elintegro.elintegrostartapp.hr.QuestionTable
import com.elintegro.elintegrostartapp.hr.Skills
import com.elintegro.ref.Address
import com.elintegro.ref.Language
import com.elintegro.elintegrostartapp.Facility
import com.elintegro.elintegrostartapp.FacilityUserRegistration
import com.elintegro.elintegrostartapp.client.FrequencyUnit
import com.elintegro.elintegrostartapp.hr.Employee
import com.elintegro.elintegrostartapp.ref.*
import com.elintegro.elintegrostartapp.supplyChain.Vendor
import com.elintegro.website.ClientProject
import grails.util.Holders
import com.elintegro.elintegrostartapp.Provider
import com.elintegro.elintegrostartapp.property.Property


/**
 * Created with IntelliJ IDEA.
 * User: Shai
 * Date: 11/17/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
class DataInit {


	static def initFirstUser() {
		def groupHead, participant1, pnlPerson
		def roleProsumer, roleAdmin, roleGuest

		if (!Role.count) {
			roleAdmin = new Role(authority: "ROLE_SUPER_ADMIN").save(failOnError: true) // Has access to everything
			roleAdmin = new Role(authority: "ROLE_ADMIN").save(failOnError: true)
			roleGuest = new Role(authority: "ROLE_GUEST").save(failOnError: true)
			new Role(authority: "ROLE_CLIENT").save(failOnError: true)
			new Role(authority: "ROLE_EMPLOYEE", isEmployee: true).save(failOnError: true)
			new Role(authority: "ROLE_NURSE", isEmployee: true).save(failOnError: true)
			//Service provider is for clients
			new Role(authority: "ROLE_SERVICE_PROVIDER").save(failOnError: true)
			//Vendor is for the facility
			new Role(authority: "ROLE_VENDOR").save(failOnError: true) //Vendor is for facility or property
			new Role(authority: "ROLE_CLIENT_REPRESENTATIVE").save(failOnError: true) //Relative has less limited access to the client account
			new Role(authority: "ROLE_CLIENT_SECONDARY_CONTACT").save(failOnError: true) //Relative has less limited access to the client account
			new Role(authority: "ROLE_APPLICANT").save(failOnError: true)
			new Role(authority: "ROLE_RECEPTIONIST", isEmployee: true).save(failOnError: true)
			new Role(authority: "ROLE_CARE_GIVER", isEmployee: true).save(failOnError: true)
            new Role(authority: "ROLE_MANAGER", isEmployee: true).save(failOnError: true)
			new Role(authority: "ROLE_DIRECTOR", isEmployee: true).save(failOnError: true)
			new Role(authority: "ROLE_LEAD").save(failOnError: true)
		}

		User adminUser = User.findByUsername("eugenelip@gmail.com")
		User guestUser = User.findByUsername("elintegro.himalaya@gmail.com")
		String adminPawword = Holders.grailsApplication.config.adminPassword ?: "pasSw1rd"
		String guestPassword = Holders.grailsApplication.config.guestPassword ?: "himalaya@123"
		if (!adminUser) {
			String adminUsername = Holders.grailsApplication.config.adminUsername ?: "eugenelip@gmail.com"
			adminUser = new User(username: adminUsername, password: adminPawword, owner: true, admin: true, enabled: true, accountLocked: false).save()
		}
		if (!guestUser) {
			String guestUsername = Holders.grailsApplication.config.guestUsername ?: "elintegro.himalaya@gmail.com"
			guestUser = new User(username: guestUsername, password: guestPassword, owner: true, guestUser: true, enabled: true, accountLocked: false).save(failOnError: true)
		}
        def pnlwlust = new User(username: "pnlwlust@gmail.com", password:"pnlwlust@123", enabled: true, accountLocked: false).save()
		def userRole
		if (!UserRole.count) {
			userRole = new UserRole(user: adminUser, role: roleAdmin).save(failOnError: true)
			new UserRole(user: guestUser, role: roleAdmin).save()
			new UserRole(user: pnlwlust, role: roleAdmin).save()
		}

		if (!Person.count()) {
			groupHead = new Person(email:"eugenelip@gmail.com", firstName:"Eugene", lastName:"Lipkovich", user:adminUser).save(failOnError: true)
			participant1 = new Person(email: "daniel.lipkovich@gmail.com", firstName: "Daniel", lastName: "Lipkovich").save(failOnError: true)
		    pnlPerson = new Person(email: "pnlwlust@gmail.com", firstName: "Prakash", lastName: "subedi", user: pnlwlust).save()
		}

		return [users:[pnlwlust,adminUser, guestUser], persons:[participant1, groupHead, pnlPerson]]
	}

	static List<Language> initLanguage() {
		if (!Language.count()) {
			new Language(code: "en", ename: "English", name: "English", description: "English is used in many countries. Also it is a language of international communication.", inuse: true).save(failOnError: true)
			new Language(code: "ru", ename: "Russian", name: "русский", description: "Russian.", inuse: true).save(failOnError: true)
			new Language(code: "fr", ename: "French", name: "Français", description: "French.", inuse: true).save(failOnError: true)
			new Language(code: "he", ename: "Hebrew", name: "עברית", description: "Hebrew.", inuse: true).save(failOnError: true)
			new Language(code: "ar", ename: "Arabic", name: "عربى", description: "Arabic.", inuse: false).save(failOnError: true)
			new Language(code: "es", ename: "Spanish", name: "español", description: "Spanish.", inuse: false).save(failOnError: true)
			new Language(code: "ne", ename: "Nepali", name: "नेपाली", description: "Nepali.", inuse: false).save(failOnError: true)
		}

		List languages = Language.findAllByInuse(true);
		languages
	}

	static def initStructuresForRegisteredUser(User user) {
		Person person = new Person(email: user.email, firstName: user.firstName, lastName: user.lastName, user: user).save(flush: true)
	}
	static def initElintegroClientProject(){
		ClientProject clientProject = new ClientProject(clientName:"" ,projectName:"Globe Chalet" ,logo:"assets/clientsProjectImages/globeChalet.PNG" ,
				description:"Software to manage Associations\n" +
				"of real estate properties and communities\n" +
				"\n" ,linkToWebsite:"www.globeChalet.com" ).save()
		 new ClientProject(clientName:"Coach Clone" ,projectName:"Coach Clone application" ,logo:"assets/clientsProjectImages/coachClone.PNG" ,description:"All In One Lifestyle Coaching Tool for Fitness Pros" ,linkToWebsite:"https://www.coachclone.com/" ).save()
		ClientProject clientProject2 = new ClientProject(clientName:"Morgan Stanley" ,projectName:"" ,logo:"assets/clientsProjectImages/morganStanley.PNG" ,description:"Nothing here ",linkToWebsite:"https://www.morganstanley.com/" ).save()
		ClientProject clientProject3 = new ClientProject(clientName:"Yellow Pages" ,projectName:"" ,logo:"assets/clientsProjectImages/yellowPages.PNG" ,description:"Nothing here" ,linkToWebsite:"https://www.yellopages.com/").save()
	}
	static  def initTestimonials(){
		new Testimonials(quote:"From concept to development, it was a pleasure to work with Elintegro.They delivered my product on time and on budget. I’d definitely hire them again.",name:"Drasko Raicevic",title: "Quickbody Fitness",customerImage:"assets/home/avatar.png").save()
		new Testimonials(quote:"The Dating app Elintegro developed for me was perfect. It was exactly what I needed, and more.",name:"Lev",title: "Matchmaker",customerImage:"assets/home/avatar.png").save(flush:true)

	}
	static def initelintegrostartappSkills(){
		new Skills(code:"java",name:"Java",description: "Nothing").save()
		new Skills(code:"spring",name:"Spring Framework",description: "Nothing").save()
		new Skills(code:"hibernate",name:"Hibernate",description: "Nothing").save()
		new Skills(code:"sql",name:"SQL",description: "Nothing").save()
		new Skills(code:"js",name:"Javascript",description: "Nothing").save()
		new Skills(code:"jq",name:"JQuery",description: "Nothing").save()
		new Skills(code:"html",name:"HTML",description: "Nothing").save()
		new Skills(code:"css",name:"CSS",description: "Nothing").save()
		new Skills(code:"unix",name:"UNIX/Linux (including scripting)",description: "Nothing").save()
		new Skills(code:"mvn",name:"Maven",description: "Nothing").save()
		new Skills(code:"git",name:"Git",description: "Nothing").save()
		new Skills(code:"eclipse",name:"Eclipse",description: "Nothing").save()
		new Skills(code:"intellij",name:"Intellij",description: "Nothing").save()
		new Skills(code:"agile",name:"Agile",description: "Nothing").save()
		new Skills(code:"grails",name:"Grails",description: "Nothing").save()
		new Skills(code:"angular",name:"Angular",description: "Nothing").save()
		new Skills(code:"react",name:"React",description: "Nothing").save()
		new Skills(code:"python",name:"Python",description: "Nothing").save()
		new Skills(code:"php",name:"PHP",description: "Nothing").save()
		new Skills(code:"mongodb",name:"MongoDB",description: "Nothing").save()
		new Skills(code:"vuejs",name:"VueJs",description: "Nothing").save()
		new Skills(code:"jenkins",name:"Jenkins",description: "Nothing").save()

	}
	static def initelintegrostartappPosition(){
		new Position(_code:"be", name:"Back-end Java Developer",description: "Nothing").save()
		new Position(_code: "fe", name:"Front-end Developer",description: "Nothing").save()
		new Position(_code:"po", name:"Product Owner",description: "Nothing").save()
		new Position(_code:"sm", name:"Scrum Master",description: "Nothing").save()
		new Position(_code:"sa", name:"Site Adminstrator",description: "Nothing").save()
		new Position(_code:"do", name:"Developer",description: "Nothing").save()

	}
	static  def initelintegrostartappQuestionTable(){
		new QuestionTable(questionKey:"Which describes you best ?",questionName:"leadDescription").save()
		new QuestionTable(questionKey:"What stage is your app in ?",questionName:"leadStage").save()
		new QuestionTable(questionKey:"What is your budget ?",questionName:"leadBudget").save()
	}
	static def initelintegrostartappAnswerTable(){
		QuestionTable question = QuestionTable.findByQuestionName("leadDescription")
		new AnswerTable(question:question,answerKey:"I want to build an app for my business.").save()
		new AnswerTable(question:question,answerKey: "I work at a company who is looking to build an app.").save()
		new AnswerTable(question:question,answerKey: "We are a software company looking to outsource some technology.").save()

		QuestionTable question1 = QuestionTable.findByQuestionName("leadStage")
		new AnswerTable(question:question1,answerKey:"I have the spec all agile and ready !").save()
		new AnswerTable(question:question1,answerKey: "I don't have a detailed spec but I do have some documentation with requirements and I want help with a spec.").save()
		new AnswerTable(question:question1,answerKey: "What's a spec?").save()
		new AnswerTable(question:question1,answerKey: "I want to brainstorm ideas?").save()

		QuestionTable question2= QuestionTable.findByQuestionName("leadBudget")
		new AnswerTable(question:question2,answerKey:"\$250K and up").save()
		new AnswerTable(question:question2,answerKey: "\$100K - \$250K").save()
		new AnswerTable(question:question2,answerKey: "\$50K - \$100K").save()
		new AnswerTable(question:question2,answerKey: "\$25K - \$50K").save()
		new AnswerTable(question:question2,answerKey: "\$10K - \$25K").save()
		new AnswerTable(question:question2,answerKey: "Under \$10K").save()


	}


	static def initelintegrostartappReferences(def userList) {

		new ApplicationStatus(code: "ACCEPTED", name: "Accepted", description: "Application accepted and a new client is created").save(failOnError: true)
		new ApplicationStatus(code: "IN_REVIEW", name: "In Review", description: "Application in procee opf the review").save(failOnError: true)
		new ApplicationStatus(code: "WAITING_LIST", name: "Waiting List", description: "Application is in waiting list until an expiration date").save(failOnError: true)
		new ApplicationStatus(code: "REJECTED", name: "Rejected", description: "Application is rejected ...").save(failOnError: true)
		new ApplicationStatus(code: "APPLIED", name: "Applied", description: "Application is application ...").save(failOnError: true)


		new FacilityType(code: "nursing_home", name: "Nursing Home", description: "Nursing Home").save(failOnError: true)
		FacilityType retHouse = new FacilityType(code: "retirement_house", name: "Retirement House", description: "Retirement House").save(failOnError: true)
		new FacilityType(code: "early_active_retirement", name: "Early Active Retirement Community", description: "Early Active Retirement Community for luxary accomodation, resort tyoe dwelling ...").save(failOnError: true)

		new PropertyType(code: "rental_house", name: "Rental House", description: "Rental House").save(failOnError: true)
		new PropertyType(code: "condo_association", name: "Condo Association", description: "Condo Association").save(failOnError: true)

		new ProviderType(code: "medical_doctor", name: "Medical Doctor", description: "Medical Doctor").save(failOnError: true)
		new ProviderType(code: "yoga_teacher", name: "Yoga Teacher", description: "Yoga Teacher").save(failOnError: true)

		new ContactType(code: "client", contactClass: "Client", name: "Client", description: "Client is a person who is renting a unit from the Facility").save(failOnError: true)
		new ContactType(code: "provider", contactClass: "Provider", name: "Provider", description: "Provider is a person or company who is providing a serc=vice (s) to the Facilitie's clients").save(failOnError: true)
		new ContactType(code: "vendor", contactClass: "Vendor", name: "Vendor", description: "Vendor is a person or Company/Organisations that provides services and/or products to the Facility").save(failOnError: true)
		new ContactType(code: "employee", contactClass: "Employee", name: "Employee", description: "Employee is a person that employed by the facility to provide some specific work").save(failOnError: true)


		//...
		new EventType(code: "incident_fall", name: "Falling", description: "Falling down ...").save(failOnError: true)
		new EventType(code: "incident_rise", name: "Rising", description: "Rising up ...").save(failOnError: true)
		//...


		Address addressCourtSideCondo = new Address(addressText: "5555 Avenue Trent, Côte Saint-Luc, QC H4W 2V6, Canada",
		).save(failOnError: true)

		Property courtSideCondoProperty = new Property(description: "Court Side Condo property of Bolton Valley ski resourt", numberUnits: 20, address: addressCourtSideCondo).save(failOnError: true)

		Facility courtSideCondo = new Facility (type: retHouse, facilityName: "Court Side Condo Association", description: "Court Side Condo Association of Bolton Valley",
				main_address: addressCourtSideCondo,
		)

		def users1 = userList.users
		def persons = userList.persons
		courtSideCondo.addToRe_properties(courtSideCondoProperty)
		users1.each{courtSideCondo.addToUsers(it)}
		courtSideCondo.save(failOnError: true)


//todo remove after the contact type adding mechanism is implementd
		persons.each{
			new Employee(person: it, facility: courtSideCondo).save()
		}

		persons.each{
			new Provider(person: it, facility: courtSideCondo).save()
		}
		persons.each{
			new Vendor(person: it).save()
		}
		new FacilityUserRegistration(
				facilityUserName: "courtSideCondo-admin1",
				facility: courtSideCondo,
				expectedUser: "eugenelip+99@gmail.com",
				expectedRole: "ROLE_ADMIN"
		).save(failOnError: true)

		new FacilityUserRegistration(
				facilityUserName: "courtSideCondo-admin2",
				facility: courtSideCondo,
				expectedUser: "shai200@gmail.com",
				expectedRole: "ROLE_ADMIN"
		).save(failOnError: true)

		new FacilityUserRegistration(
				facilityUserName: "courtSideCondo-admin5",
				facility: courtSideCondo,
				expectedUser: "eugenelip+88@gmail.com",
				expectedRole: "ROLE_ADMIN"
		).save(failOnError: true)

		new FacilityUserRegistration(
				facilityUserName: "courtSideCondo-admin5",
				facility: courtSideCondo,
				expectedUser: "eugenelip+77@gmail.com",
				expectedRole: "ROLE_ADMIN"
		).save(failOnError: true)


		new FacilityUserRegistration(
				facilityUserName: "courtSideCondo-admin5",
				facility: courtSideCondo,
				expectedUser: "eugenelip+66@gmail.com",
				expectedRole: "ROLE_ADMIN"
		).save(failOnError: true)

	}

	static def initFrequencyUnit(){
		if (!FrequencyUnit.count()){
			new FrequencyUnit(name: "Day", description: "day").save()
			new FrequencyUnit(name: "Week", description: "week").save()
			new FrequencyUnit(name: "Month", description: "month").save()
			new FrequencyUnit(name: "Year", description: "year").save(flush:true)
		}
	}

}