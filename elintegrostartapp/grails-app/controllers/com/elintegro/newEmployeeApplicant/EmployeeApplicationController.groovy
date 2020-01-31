package com.elintegro.newEmployeeApplicant

import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.elintegrostartapp.hr.Position

class EmployeeApplicationController {

    def index() { }


    def createApplicant(){
        println("inside create applicant")
        println (params)
        println (params.firstName)
        Person applicant = new Person()
        applicant.firstName = params.firstName
        applicant.lastName = params.lastName
        applicant.contactEmail = params.email
        applicant.phone = params.phone
        println("Printing the positions")
        println(params.get("availablePosition[]"))
        println("printing array list")
        ArrayList arrayList = params.get("availablePosition[]")
        println(arrayList)
//        println("Entry point for the loop")
//        arrayList.each {println it * 1}
        println("Entry point for for loops ")
        for (int i = 0; i < arrayList.size();i++)
        {
            println(arrayList.get(i));

        }
        println(applicant.firstName)
        Application application = new Application()
        application.applicant = applicant
        application.linkedin = params.linkedin
        println(application.linkedin)
        println("Saving available positions")
//        application.availablePositions = application.availablePositions.add(arrayList)
        application.save()
//        for (int i = 0; i < arrayList.size();i++)
//        {
//            application.availablePositions[] = arrayList.get(i)
//
//        }

       applicant.save()

//        def a = Application.get(1)
//        for (availablePosition in a.availablePositions) {
//            println (availablePosition.name)
//        }

    }
}
