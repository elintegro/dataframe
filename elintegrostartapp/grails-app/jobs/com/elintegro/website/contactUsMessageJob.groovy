package com.elintegro.website

class contactUsMessageJob {
    def contactUsMessageService
    static triggers = {
        simple repeatInterval: 5*60*1000 //send email once in 5 minutes
        simple repeatInterval: 20000 //send email once in 20 seconds
    }

    def execute() {
      //  println("Job Run")
        contactUsMessageService.sendingMessages()

    }
}
