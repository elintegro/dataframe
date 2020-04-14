/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package gc

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import grails.core.GrailsApplication
import grails.gsp.PageRenderer
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired

class TestController {

    GrailsApplication grailsApplication
    def springSecurityService
    def springSecurityUiService
    def mailService
    def messageSource
    def treeWidgetDataframe
    def sessionFactory
    def gcMainPageVue
    PageRenderer groovyPageRenderer

    @Autowired
    ResultPageHtmlBuilder resultPageHtmlBuilder

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

        Map<String, String> struct = resultPageHtmlBuilder.getFinalHtmlandScript(gcMainPageVue)

        String securedCode = resultPageHtmlBuilder.applySecurityFilter(struct.get("finalScript"));
        struct.put("finalScript", securedCode)

        boolean reloadPage = false
        String msg = params.msg?:""
        if(params.reloadPage){
            reloadPage = params.reloadPage
        }
        render(view: '/newVue', model:[reloadPage:reloadPage, msg:msg, constructedPageHtml:struct.initHtml, constructedPageScript: struct.finalScript])
    }

    def test(){
        render(view: 'test1')
    }
}
