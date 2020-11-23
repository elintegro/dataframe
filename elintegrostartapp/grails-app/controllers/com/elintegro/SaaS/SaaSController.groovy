package com.elintegro.SaaS

import com.elintegro.erf.dataframe.DataframeException
import com.elintegro.erf.dataframe.ResultPageHtmlBuilder
import grails.converters.JSON
import grails.core.GrailsApplication
import grails.gsp.PageRenderer
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Autowired

class SaaSController {

    GrailsApplication grailsApplication
    def springSecurityService
    def gcMainPageVue
    @Autowired
    ResultPageHtmlBuilder resultPageHtmlBuilder

    def index() { }

    @Secured("permitAll")
    def getHtml(){

        def currentUser = springSecurityService.currentUser
        Long userId = currentUser?.id
        if(!userId){
            if(!grailsApplication.config.guestUserId){
                throw new DataframeException("Please set 'guestUserId' value in Config")
            }
            userId = (long)grailsApplication.config.guestUserId
        }
        session.setAttribute("userid",userId)
        Map<String, String> struct = resultPageHtmlBuilder.getFinalHtmlandScript(gcMainPageVue)

        String securedCode = resultPageHtmlBuilder.applySecurityFilter(struct.get("finalScript"));
        struct.put("finalScript", securedCode)

        render([html:struct.initHtml, javascript: struct.finalScript] as JSON)
//        render([html:"html", javascript:"this is js"] as JSON)
    }
}
