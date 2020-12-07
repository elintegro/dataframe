/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gc.notification

import com.elintegro.erf.notification.base.gateway.NotificationGatewayFactory
import com.elintegro.erf.notification.enums.MessengerConstant
import com.elintegro.erf.notification.enums.NotificationPreferences
import com.elintegro.erf.notification.impl.gateway.fb.FbHelper
import com.elintegro.erf.notification.impl.gateway.fb.Messenger
import com.elintegro.erf.notification.impl.gateway.fb.helperClass.MsgRequest
import com.elintegro.erf.notification.impl.parameter.MessengerRequestParams
import com.elintegro.gerf.DataframeController
import com.elintegro.notification.NotificationEvent
import com.elintegro.notification.Notification
import com.google.gson.Gson
import grails.converters.JSON
import grails.util.Holders
import org.springframework.context.i18n.LocaleContextHolder

import javax.servlet.http.HttpServletResponse

class NotificationController {

    def notificationService
    def messageSource
    def springSecurityService
    def dataframeService

    def notification(){
        DataframeController dataframeController = new DataframeController()
        def results = dataframeService.ajaxValuesRaw()
        def customDfrParamsToRefresh = [:]
        def notificationData = notificationService.getUserNotificationData()
        customDfrParamsToRefresh =  [
                'notificationData':notificationData
        ]
        results.put("customDfrParamsToRefresh", customDfrParamsToRefresh)
        render(results as JSON)
    }

    def settings(){
        def user = springSecurityService.currentUser
        def notification = Notification.findById(params.notification)
        if (notification){
            bindData(notification, params)
        }else {
            notification = new Notification()
            bindData(notification, params)
            notification.event = NotificationEvent.findById(params.eventId)
            notification.user = user
            notification.referralId = user.id
        }
        def result = [:]
        String message = ""
        try {
            notification.save(flush: true, failOnError: true)
            message = messageSource.getMessage("notification.settings.success", null, LocaleContextHolder.getLocale())
            result = ['msg': message, 'success': true, 'notificationId':notification.id]
        }catch (e){
            log.error("error on saving notification settings due to"+e.getMessage())
            message = messageSource.getMessage("notification.settings.fail", null, LocaleContextHolder.getLocale())
            result = ['msg': message, 'success': false]
        }
        render(result as JSON)

    }

    def isConnected(){
        def currentUser = springSecurityService.currentUser
        def info = com.elintegro.notification.fb.Messenger.findByReferralId(currentUser.id.toString())
        def result = [:]
        if (info){
            result = ['success': true, referalId: currentUser.id]
        }else {
            result = ['success': false, referalId: currentUser.id]
        }
        render(result as JSON)
    }

    def fbNotification() {
        if (request.method == 'GET') {
            if (request.getParameter(MessengerConstant.HUB_MODE) == MessengerConstant.HUB_MODE_Value &&
                    request.getParameter(MessengerConstant.HUB_VERIFY) == MessengerConstant.VERIFY_TOKEN) {
                log.info("Validating web hook");
                response.status = HttpServletResponse.SC_OK
                render(request.getParameter(MessengerConstant.HUB_CHALANGE))
            } else {
                log.error("Failed validation. Make sure the validation tokens match.");
                response.status = HttpServletResponse.SC_FORBIDDEN
            }
        }else {
            String body = FbHelper.read(request)
            MsgRequest msgRequest = new Gson().fromJson(body, MsgRequest.class)
            if (msgRequest == null) {
                log.info("request is null")
                response.status = 200
                return
            }
            if (msgRequest?.object == "page") {
                try {
                    def entry = msgRequest?.entry
                    entry.each { en ->
                        def messagings = en?.messaging
                        if (en?.id == grailsApplication.config.notification.facebook.pageId) {
                            notificationService.saveSenderInfo(messagings?.last())
                        }
                    }
                } catch (e) {
                    println 'exception in sending message' + e
                    response.status = 200
                    render(MessengerConstant.SUCCESS)
                    return
                }

                response.status = 200
                render(MessengerConstant.SUCCESS)
            }
        }
    }

    def getStartedButton(){
        MessengerRequestParams messengerRequestParams = new MessengerRequestParams()
        String fbUrl = MessengerConstant.FB_MSG_PROFILE + Holders.grailsApplication.config.notification.facebook.accessToken
        messengerRequestParams.url = fbUrl
        messengerRequestParams.messagePayload = MessengerConstant.GET_STARTED_PAYLOD
        Messenger messenger = NotificationGatewayFactory.getGateway(NotificationPreferences.FACEBOOK)
        def messengerResponse = messenger.setGetStartedButton(messengerRequestParams)
        Map result = [:]
        if (messengerResponse.success){
            result = ['success': true, 'msg': 'Success']
        }else {
            println "error message"+messengerResponse.errorMessage
            result = ['msg': messengerResponse.errorMessage, 'success': false]
        }
        render(result as JSON)
    }

}
