package com.elintegro.customerCare

import grails.gorm.transactions.Transactional
import grails.web.databinding.DataBinder

@Transactional
class CustomerCareService implements DataBinder{

    def getSystemUsers(){
        List<Map> systemUsers = ChatPerson.executeQuery("select new Map(cp.email as email, cp.role as role) from ChatPerson cp where cp.role=:admin or cp.role=:teamMember or cp.role=:sales",
                [admin: ChatPersonRole.SUPER_USER.role, teamMember:ChatPersonRole.TEAM_MEMBER.role, sales:ChatPersonRole.SALES.role])
        return [systemUsers:systemUsers]
    }


    def saveData(def object){
        return object.save(flush:true, failOnError:true)
    }
}
