package com.elintegro.leaduser

import com.elintegro.elintegrostartapp.client.LeadUser
import grails.converters.JSON

class LeadUserController {
    def leadUserService

    def user(LeadUser leadUser) {
        Map returnData = [success:true]
        if (!leadUser){
            returnData.success = false
        }else {
            try {
                leadUserService.saveLeadUsers(leadUser)
            }catch(e){
                returnData.success = false
            }
        }
        return returnData as JSON
    }
}
