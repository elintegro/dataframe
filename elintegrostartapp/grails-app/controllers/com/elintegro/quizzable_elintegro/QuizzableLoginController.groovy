package com.elintegro.quizzable_elintegro

import grails.util.Holders

class QuizzableLoginController {
    def springSecurityService

    def quizzableLoginFromElintegro(){
        def currentUser = springSecurityService.currentUser
        def userName = currentUser.username
        def firstName = currentUser.firstName
        def lastName = currentUser.lastName
        def email = currentUser.email
        def param = request.getJSON()
        def quizzableUrl = Holders.grailsApplication.config.quizzableUrl
        println(param)

    }
}
