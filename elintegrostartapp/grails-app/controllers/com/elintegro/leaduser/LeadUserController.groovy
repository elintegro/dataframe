package com.elintegro.leaduser

import com.elintegro.elintegrostartapp.client.LeadUser
import grails.converters.JSON

class LeadUserController {
    def leadUserService

    def user() {
        def reqParams = request.getJSON()
        Map returnData = [success:true]
        String email = reqParams.email
        if (!email){
            returnData.success = false
        }else {
            try {
                LeadUser leadUser = LeadUser.findOrCreateByEmail(email)
                bindData(leadUser, reqParams)
                leadUserService.saveLeadUsers(leadUser)
            }catch(e){
                returnData.success = false
            }
        }
        return returnData as JSON
    }
}
