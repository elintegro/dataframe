package com.elintegro.website

import grails.util.Holders

class ContactUsMessageService {
    def emailService
    def mailService
    def sendingMessages() {
        // println("Job Run Inside Service")
        def contactDetails = ContactUs.findAllWhere(sendNo:0 , resend : true)

        println(contactDetails.email)
        def conf = Holders.grailsApplication.config
        println("This is message from contact us")
        String emailBody = conf.contactUsEmailService.emailWithInformation
        String emails = conf.elintegro.contuctus.email.sendto
        println("this is entry point of  loop")
        for(item in contactDetails) {
            println("One email printed")
            println(item)
            Map emailParams =[name:item.name, phone:item.phone]
            println(item.name)
            emailService.sendMail(emails.split(","), emailParams, emailBody)
            item.sendNo++
            item.resend = false
            item.save()
        }

        println("Email Sent Successfully")


    }



}
