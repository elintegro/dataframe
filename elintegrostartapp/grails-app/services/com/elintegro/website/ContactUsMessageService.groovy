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
        Map emailParams =[name:contactDetails.name, phone:contactDetails.phone]
        String emailBody = conf.contactUsEmailService.emailWithInformation
        println("this is entry point of  loop")
        for(item in contactDetails.email) {
            println("One email printed")
            println(item)


            emailService.sendMail(item, emailParams, emailBody)
        }

        println("Email Sent Successfully")


    }



}
