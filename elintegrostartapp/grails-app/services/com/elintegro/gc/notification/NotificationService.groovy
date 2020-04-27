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

import com.elintegro.auth.User
import com.elintegro.erf.notification.base.gateway.NotificationGatewayFactory
import com.elintegro.erf.notification.enums.MessengerConstant
import com.elintegro.erf.notification.enums.NotificationPreferences
import com.elintegro.erf.notification.enums.NotificationStatus
import com.elintegro.erf.notification.impl.gateway.fb.FbHelper
import com.elintegro.erf.notification.impl.gateway.fb.helperClass.Messaging
import com.elintegro.erf.notification.impl.parameter.EmailRequestParams
import com.elintegro.erf.notification.impl.parameter.MessengerRequestParams
import com.elintegro.notification.NotificationEvent
import com.elintegro.notification.Message
import com.elintegro.notification.Notification
import com.elintegro.notification.NotificationInfo
import com.elintegro.erf.notification.base.gateway.Notification as GatewayNotification
import com.elintegro.erf.notification.impl.gateway.fb.helperClass.Message as FbMessage
import com.elintegro.notification.fb.Messenger
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.util.Holders
import groovy.text.SimpleTemplateEngine

@Transactional
class NotificationService {

    def mailService
    def springSecurityService
    def conf =Holders.grailsApplication.config

    def sendNotification(NotificationEvent event, Map binding = [:], User target, User source = null){
        Notification notification = Notification.findByEventAndUser(event, target)
        NotificationInfo notificationInfo = null
        if (notification){
            Message message = null
            String sourceId = source?.id?.toString()
            String targetId = target?.id?.toString()
            String messageTemplate = ""
            if (notification.email){
                message = Message.findByEventAndNotificationPreference(event, NotificationPreferences.email)
                messageTemplate = evaluateMessageBody(message, binding)
                notificationInfo = buildAndSendEmailNotification(notification, message, messageTemplate)
                notificationInfo.preference = NotificationPreferences.email.desc
                notificationInfo.messageSeverity = event.messageSeverity.desc
                saveNotificationInfo(notificationInfo, sourceId, targetId)
            }
            if (notification.fb){
                message = Message.findByEventAndNotificationPreference(event, NotificationPreferences.FACEBOOK)
                messageTemplate = evaluateMessageBody(message, binding)
                notificationInfo = buildAndSendFbNotification(notification, message, messageTemplate)
                notificationInfo.preference = NotificationPreferences.FACEBOOK.desc
                notificationInfo.messageSeverity = event.messageSeverity.desc
                saveNotificationInfo(notificationInfo, sourceId, targetId)
            }
        }
        return notificationInfo
    }

    private NotificationInfo buildAndSendEmailNotification(Notification notification, Message message, String messageTemplate){
        EmailRequestParams emailRequestParams = new EmailRequestParams()
        emailRequestParams.from = conf.grails.mail.username
        emailRequestParams.subject = message.subject
        emailRequestParams.to = notification.user.username
        emailRequestParams.message = messageTemplate
        emailRequestParams.async = true
        emailRequestParams.mailService = mailService
        emailRequestParams.groovyPageRenderer = null
        GatewayNotification notificationGateWay = NotificationGatewayFactory.getGateway(NotificationPreferences.email)
        def emailResponse = notificationGateWay.send(null, emailRequestParams)
        NotificationInfo notificationInfo = new NotificationInfo()
        notificationInfo.notification = notification
        notificationInfo.sentDate = new Date()
        if (emailResponse.success){
            notificationInfo.notificationStatus = NotificationStatus.SENT.desc
        }else {
            notificationInfo.notificationStatus = NotificationStatus.FAILED.desc
        }
        return notificationInfo
    }

    private NotificationInfo buildAndSendFbNotification(Notification notification, Message message, String messageTemplate){
        MessengerRequestParams messengerRequestParams = new MessengerRequestParams()
        Messenger messenger = Messenger.findByReferralId(notification.referralId)
        String fbUrl = MessengerConstant.FB_MSG_URL + Holders.grailsApplication.config.notification.facebook.accessToken
        NotificationInfo notificationInfo = null
        if (messenger){
            FbMessage mesg = FbHelper.createMessage(messageTemplate)
            messengerRequestParams.messagePayload = FbHelper.createFbSendMessage(messenger.senderId, mesg)
            messengerRequestParams.url = fbUrl
            GatewayNotification notificationGateWay = NotificationGatewayFactory.getGateway(NotificationPreferences.FACEBOOK)
            def messengerResponse = notificationGateWay.send(null, messengerRequestParams)
            notificationInfo = new NotificationInfo()
            notificationInfo.notification = notification
            notificationInfo.sentDate = new Date()
            if (messengerResponse.success){
                notificationInfo.notificationStatus = NotificationStatus.SENT.desc
            }else {
                notificationInfo.notificationStatus = NotificationStatus.FAILED.desc
            }
        }
        return notificationInfo
    }

    private def saveNotificationInfo(NotificationInfo notificationInfo, String source, String target){
        notificationInfo.source = source
        notificationInfo.target = target
        try {
            notificationInfo.save(flush: true, failOnError: true)
        }catch (e){
            log.info("error to save notification info due to "+e.getMessage())
        }
    }

    def evaluateMessageBody(Message message, binding, String template = null){
        def templateBody = ""
        if (!template){
            template = message.template
            if (message?.defaultValues){
                binding = binding + ((Map)JSON.parse(message.defaultValues))
            }
        }
        if (template.contains('$')){
            try {
                templateBody = new SimpleTemplateEngine().createTemplate(template).make(binding)
            }catch (e){
                log.error("binding data is incorrect to run script")
            }
            return templateBody?.toString()
        }else {
            return template
        }
    }

    def saveSenderInfo(Messaging event ){
        def senderId = event.sender.id
        String referralId = ""
        if (event?.postback?.referral){
            referralId = event?.postback?.referral?.ref
        }
        if (event?.referral){
            referralId = event?.referral?.ref
        }
        def  senderInfo = Messenger.findBySenderIdAndReferralId(senderId, referralId)
        if (!senderInfo && referralId){
            senderInfo = new Messenger()
            senderInfo.senderId = senderId
            senderInfo.timestamp = event?.timestamp
            senderInfo.referralId = referralId
            senderInfo.save(flush:true)
        }
    }

    def getUserNotificationData(){
        def events = NotificationEvent.list()
        def currentUser = springSecurityService.currentUser
        List resultData = []
        if (currentUser){
            events.each {event ->
                Map data = [:]
                def notification = Notification.findByEventAndUser(event, currentUser)
                data << ["eventId": event.id, "eventNames":event.name, "notification":notification?.id, "email":notification?.email?:false, "fb":notification?.fb?:false,
                         "whatsapp":notification?.whatsapp?:false, "sms":notification?.sms?:false]
                resultData.add(data)
            }
        }
        return resultData
    }
}
