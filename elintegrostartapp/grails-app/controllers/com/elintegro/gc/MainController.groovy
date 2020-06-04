/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gc

import com.elintegro.auth.User
import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import com.elintegro.erf.dfEditor.DfEditor
import com.elintegro.gc.data.DataInit
import grails.core.GrailsApplication
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Holders
import org.hibernate.Session
import org.hibernate.engine.spi.SessionFactoryImplementor
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory
import org.hibernate.hql.spi.QueryTranslator
import org.hibernate.hql.spi.QueryTranslatorFactory
import org.springframework.beans.factory.annotation.Autowired

/**
* Author: eugenel
*/
//@Secured(["ROLE_PROSUMER"])
class MainController {
    //Bean injections:
    GrailsApplication grailsApplication
    def springSecurityService
    def springSecurityUiService
    def mailService
    def messageSource
	def treeWidgetDataframe
	def sessionFactory
	def gcMainPageVue

	@Autowired
	ResultPageHtmlBuilder resultPageHtmlBuilder

    def index() { }

	@Secured(["permitAll"])
    def show() {
		def currentUser = springSecurityService.currentUser
		Long userId = currentUser?.id
		if(!userId){
			if(!grailsApplication.config.guestUserId){
				throw new DataframeException("Please set 'guestUserId' value in Config")
			}
			userId = (long)grailsApplication.config.guestUserId
		}
		String firstname = currentUser?.firstName?currentUser?.firstName.toLowerCase().capitalize():""
		session.setAttribute("userid",userId)
		def struct = resultPageHtmlBuilder.getFinalHtmlandScript(gcMainPageVue)
		boolean reloadPage = false
		String msg = params.msg?:""
		if(params.reloadPage){
			reloadPage = params.reloadPage
		}
		render(view: '/main/show', model:[reloadPage:reloadPage, msg:msg, constructedPageHtml:struct.initHtml, constructedPageScript: struct.finalScript])
	}

    def test() {

        treeWidgetDataframe.init()
        [dataframe:treeWidgetDataframe]

        //render(view: "t")
        render(view: "test1")
    }

	def test1() {

		render(view: "/test/test1")
	}

	@Secured(["permitAll"])
	def hqlTest(){

		render(view: "/test/hqlTest")
	}

	@Secured(["permitAll"])
	def getSqlfromHql(){
		def String result = null;
		def hql = params.hql
		def statuscode =200;
		def types=[];
		def objectProps=[];
		def data=[];
		def noRecords=0;
		def maxSize = Holders.getFlatConfig().get("garils.hql.test.maxSize") == null ? 10 : Holders.getFlatConfig().get("garils.hql.test.maxSize")
		//def maxPageSize =parms.sizeVal;
		try{
			if("".equals(hql) || null == hql)
			{
				throw new Exception("Hql is required")
			}
			else{

				SessionFactoryImplementor factory = (SessionFactoryImplementor) sessionFactory;
				QueryTranslatorFactory ast = new ASTQueryTranslatorFactory();
				QueryTranslator queryTranslator = ast.createQueryTranslator( hql , hql , Collections.EMPTY_MAP, factory, null );
				queryTranslator.compile( Collections.EMPTY_MAP, true );
				result =queryTranslator.getSQLString();
				//types=factory.getReturnTypes(hql);
				Session hibernateSession = sessionFactory.openSession()
				def hqlQuery =hibernateSession.createQuery(hql)
				types= hqlQuery.returnTypes
				hqlQuery.setMaxResults(maxSize)
				Iterator iterator=hqlQuery.iterate();
				while(iterator.hasNext())
				{
					noRecords++;
					data.push(iterator.next());


				}
			}
		}
		catch(Exception e)
		{
			result = e.getMessage();
			statuscode=500
			log.error("Error while converting ",e)
		}
		def map = [result:result,status:statuscode,"hql":hql,"returnTypes":types,"columns":objectProps,"data":data,"noRecords":noRecords]
		render (view: "/test/hqlTest",model:map);
	}

	def dfEditor(){
		DfEditor dfEditor = new DfEditor()
		dfEditor.init()
		render(view: "dfEditor", model: [dfEditor:dfEditor])
	}

	def privacyPolicy(){
		render(view: "privacyPolicy")
	}

	def delete(){
		if(params.username){

				def user = User.findByUsernameAndAccountExpired(params.username, false)
			if(user){
				/*def person = Person.findByUser(user)
				if(person){
					def owner = Owner.findByPerson(person)
					def prop = Property.findByOwner(owner)
					if(prop)
						prop.delete()
					if(owner){
						def acc = Account.findByOwner(owner)
						if(acc)
							acc.delete(flush: true)
						owner.delete(flush:true)
					}

					person.delete(flush:true)
				}*/
				user.accountExpired = true
				user.save(flush:true)
				redirect(action:"show", params:[msg:"User deleted"])
			}else{
				redirect(action:"show", params:[msg:"User not found"])
			}
		}
		render("Please provide username in correct format. ")
	}

	def create(){
		params.remove("controller")
		params.remove("action")
		params.remove("format")
		String username = params.username
		if(!username){
			render("Please provide username in correct format. ")
		}
		if(!params.password){
			params.password = username
		}
		User user = User.findByUsername(username)
		if(user){
			if(user.accountExpired){
				user.accountExpired = false
			}else {
				render("Username already exists. ")
			}
		}else{
			user = new User()
			user.username = username
			user.password = params.password
			params.remove("username")
			params.remove("password")
			for(def item : params){
				user.putAt(item.key, item.value)
			}
			user.accountLocked = false
			user.enabled = true
		}
		def usr = user.save(failOnError:true, flush:true)
		if(params.containsKey('owner') && params.get('owner')){
			DataInit.initTreeStructureForOwner(user)
		}

		redirect(action:"show", params:[msg:"New user created"])

	}

}
