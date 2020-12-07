package com.elintegro.website

import grails.util.Holders

class ContactUsMessageService {
    def emailService
    def mailService
    def sendingMessages() {
        def contactDetails = ContactUs.findAllWhere(sendNo:0 , resend : true)
        def conf = Holders.grailsApplication.config
        String emailBody = conf.contactUsEmailService.emailWithInformation
        for(item in contactDetails) {
            Map emailParams =[name:item.name, phone:item.phone]
            emailService.sendMail(item.email, emailParams, emailBody)
            item.sendNo++
            item.resend = false
            item.save()
        }
    }

}
