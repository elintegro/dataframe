package com.elintegro.website

import grails.util.Holders

class ContactUsMessageJob {
    def contactUsMessageService
    static triggers = {
        def conf = Holders.grailsApplication.config
        Long emailInterval = conf.elintegro.contuctus.email.interval
        simple repeatInterval: emailInterval //send email once in 20 seconds is a configuration
    }

    def execute() {
      //  println("Job Run")
        contactUsMessageService.sendingMessages()

    }
}
