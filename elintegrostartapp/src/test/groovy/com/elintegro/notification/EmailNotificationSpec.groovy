/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.notification

import com.elintegro.erf.notification.base.gateway.Notification
import com.elintegro.erf.notification.base.gateway.NotificationGatewayFactory
import com.elintegro.erf.notification.enums.NotificationPreferences
import com.elintegro.erf.notification.impl.parameter.EmailRequestParams
import com.elintegro.gc.notification.NotificationService
import grails.plugins.mail.MailService
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.grails.testing.GrailsUnitTest
import org.grails.web.json.JSONObject
import spock.lang.Shared
import spock.lang.Specification

class EmailNotificationSpec extends Specification implements DataTest {

    @Shared Notification notification = null
    @Shared JSONObject credential = null

    def setupSpec() {
    }

    def setup() {
        notification = NotificationGatewayFactory.getGateway(NotificationPreferences.EMAIL)
    }

    def cleanup() {
    }

    def "test send email notification"(){
        given:
        def mailService = Mock(MailService)
        EmailRequestParams emailRequestParams = new EmailRequestParams()
        emailRequestParams.from = "elintegro.himalaya@gmail.com"
        emailRequestParams.subject = "This Test Subject."
        emailRequestParams.to = "kumarsamip03sc@gmail.com"
        emailRequestParams.message = """Hi Kumar Chapagain,<br/>
<br/>
Thank you for choosing us. If you want to connect please visit the link
click&nbsp;<a href="https://www.chapagainkumar.com.np/">here</a>.
<br/><br/>"""
        emailRequestParams.async = true
        emailRequestParams.mailService = mailService
        emailRequestParams.groovyPageRenderer = null

        when:
        def emailsendResponse = notification.send(credential, emailRequestParams)

        then:
        emailsendResponse.success == true
    }
}