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

import com.elintegro.erf.notification.base.gateway.NotificationGatewayFactory
import com.elintegro.erf.notification.enums.NotificationPreferences
import com.elintegro.erf.notification.impl.gateway.fb.FbHelper
import com.elintegro.erf.notification.impl.parameter.MessengerRequestParams
import org.grails.testing.GrailsUnitTest
import org.grails.web.json.JSONObject
import spock.lang.Shared
import spock.lang.Specification
import com.elintegro.erf.notification.base.gateway.Notification
import com.elintegro.erf.notification.impl.gateway.fb.helperClass.Message

class FbNotificationSpec extends Specification implements GrailsUnitTest {

    @Shared Notification notification = null
    @Shared JSONObject credential = null

    def setupSpec() {
    }

    def setup() {
        notification = NotificationGatewayFactory.getGateway(NotificationPreferences.FACEBOOK)
    }

    def cleanup() {
    }

    void "test send messenger notification"() {
        given:'messenger params'
        String senderId = "1203572013063964"
        String mesg = "this is test message"
        MessengerRequestParams messengerRequestParams = new MessengerRequestParams()
        Message message = FbHelper.createMessage(mesg)
        messengerRequestParams.messagePayload = FbHelper.createFbSendMessage(senderId, message)
        messengerRequestParams.url = "https://graph.facebook.com/v2.11/me/messages?access_token=EAAEOyZADSRZBIBAJisdG6TIntmW2tpIJTwqtvcL3jCtBF0UeUkr630Ua6v6cZBd5NerurYy1TBhYv9YPXiiRbpsIAvpwmgtkYs15MCGwXzZC94nX3SY0dlKFdZB2QTiDRy1VesV7PaLIRQJtkAyb3PSZClnhtwKf0U1afjuPFYzEcS0ZC88CD49"

        when:
        def messengerResponse = notification.send(null, messengerRequestParams)

        then:
        messengerResponse.success == true
    }
}