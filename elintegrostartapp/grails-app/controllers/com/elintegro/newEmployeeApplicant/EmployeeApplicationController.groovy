package com.elintegro.newEmployeeApplicant

import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.elintegrostartapp.hr.Position
import grails.converters.JSON

class EmployeeApplicationController {

    def index() { }


    def createApplicant(){
        def empData = request.getJSON()

        Person applicant = new Person()
        applicant.firstName = empData.firstName
        applicant.lastName = empData.lastName
        applicant.email = empData.email
        applicant.phone = empData.phone
        applicant.save()

        Application application = new Application()
        application.applicant = applicant
        application.linkedin = empData.linkedin

        for(item in empData.availablePosition) {
            Position availablePosition = Position.findById(item.id)
            application.addToAvailablePositions(availablePosition)
            application.save(flush: true)

        }
//        def basicInfo = application.id;
//        println(basicInfo.toString())
        def result = [success: true, id:application.id]
      render(result as JSON)
    }

}
