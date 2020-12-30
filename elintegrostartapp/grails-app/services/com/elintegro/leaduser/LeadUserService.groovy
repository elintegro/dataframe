package com.elintegro.leaduser

import com.elintegro.elintegrostartapp.client.LeadUser
import grails.gorm.transactions.Transactional

@Transactional
class LeadUserService {

    def saveLeadUsers(LeadUser leadUser) {
        leadUser.save(flush:true, failOnError:true)
    }
}
