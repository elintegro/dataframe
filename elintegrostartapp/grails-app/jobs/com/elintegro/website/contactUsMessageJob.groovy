package com.elintegro.website

class contactUsMessageJob {
    def contactUsMessageService
    static triggers = {
        simple repeatInterval: 1*60*60*1000 //send email once in 5 minutes
    }

    def execute() {
      //  println("Job Run")
        contactUsMessageService.sendingMessages()

    }
}
