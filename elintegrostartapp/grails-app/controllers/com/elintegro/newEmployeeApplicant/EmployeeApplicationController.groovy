package com.elintegro.newEmployeeApplicant

import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.elintegrostartapp.hr.Position

class EmployeeApplicationController {

    def index() { }


    def createApplicant(){
        println("inside create applicant")
        println (request.getJSON())
        def empData = request.getJSON()
        println(empData)
        println(empData.firstName)
        Person applicant = new Person()
        applicant.firstName = empData.firstName
        applicant.lastName = empData.lastName
        applicant.contactEmail = empData.email
        applicant.phone = empData.phone
        applicant.save()

        println("Printing the positions")
        println(empData.availablePosition.name)

        Application application = new Application()
        application.applicant = applicant
        application.linkedin = empData.linkedin
        println(application.linkedin)

        println("Saving available positions")
        for(item in empData.availablePosition) {
            Position availablePosition = Position.findById(item.id)
            println(availablePosition.id)
            application.addToAvailablePositions(availablePosition)
            application.save(flush: true)

        }
      render("success")
    }

}
