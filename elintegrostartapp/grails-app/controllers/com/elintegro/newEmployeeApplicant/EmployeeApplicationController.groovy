package com.elintegro.newEmployeeApplicant

import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.elintegrostartapp.hr.ApplicationSkill
import com.elintegro.elintegrostartapp.hr.Position
import com.elintegro.elintegrostartapp.hr.Skills
import grails.converters.JSON

class EmployeeApplicationController {

    def index() { }

    def createApplicant(){
        def result
        try {
            def empData = request.getJSON()
            Person applicant = new Person()
            applicant.firstName = empData.vueNewEmployeeBasicInformationDataframe_person_firstName
            applicant.lastName = empData.vueNewEmployeeBasicInformationDataframe_person_lastName
            applicant.email = empData.vueNewEmployeeBasicInformationDataframe_person_email
            applicant.phone = empData.vueNewEmployeeBasicInformationDataframe_person_phone
            applicant.save()

            Application application = new Application()
            application.applicant = applicant
            application.linkedin = empData.vueNewEmployeeBasicInformationDataframe_application_linkedin

            for (item in empData.vueNewEmployeeBasicInformationDataframe_person_availablePosition) {
                Position availablePosition = Position.findById(item.id)
                application.addToAvailablePositions(availablePosition)
                application.save(flush: true)
            }
            result = [success: true, person_id: applicant.id, application_id: application.id]
        }catch(Exception e){
            def message = "New Employee introduction: Failed to save Person's data error = " + e
            result = [success: false, message: message]
            log.error(message)
        }
        render(result as JSON)
    }
    def initiateSkillSet() {
        println("Inside initiateSkillSet")
        println(request.getJSON())
        def param = request.getJSON()
        println(param.id)
        List<ApplicationSkill> appSkill = ApplicationSkill.findAllById(param.id)
        if ( appSkill.size() == 0) {
            println("Inside if condition")
           List<Skills> skillsList = Skills.findAll()
            for (item in skillsList) {
                println(item.name)
                new ApplicationSkill(skill: item.name, comment: "").save()
                println("After ApplicationSkill")
            }
        }
    }




}
