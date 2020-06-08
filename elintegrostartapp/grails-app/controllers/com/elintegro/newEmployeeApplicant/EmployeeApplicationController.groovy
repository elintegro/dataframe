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
            applicant.firstName = empData.persisters.Person.firstName.value
            applicant.lastName = empData.persisters.Person.lastName.value
            applicant.email = empData.persisters.Person.email.value
            applicant.phone = empData.persisters.Person.phone.value
            applicant.save()

            Application application = new Application()
            application.applicant = applicant
            application.linkedin = empData.persisters.Application.linkedin.value
            //for (item in empData.persisters.Person.availablePosition) { //TODO: it will be in persisters!!
            for (item in empData.transits.Person.availablePosition) {
                    Position availablePosition = Position.findById(item.id)
                    application.addToAvailablePositions(availablePosition)
            }
            application.save(flush: true)

            empData.doamin_keys.Person.id = applicant.id
            empData.doamin_keys.Application.id = application.id

            //Fill JSON data structure with keys and send it back!

            result = [success: true, data: empData]

        }catch(Exception e){
            def message = "New Employee introduction: Failed to save Person's data error = " + e
            result = [success: false, message: message]
            log.error(message)
        }
        render(result as JSON)
    }
    def initiateSkillSet() {
        def param = request.getJSON()
        List<ApplicationSkill> appSkill = ApplicationSkill.findAllByApplication(Application.findById(param.id))
        if ( appSkill.size() == 0) {
           List<Skills> skillsList = Skills.findAll()
            for (item in skillsList) {
                new ApplicationSkill(application: param.id, skill: item.name,level: 0, comment: "").save()

            }
        }
        def resultData = [success: true,application_id: param.id]
        render (resultData as JSON)
    }

    def addNewSkillSet(){
        def newSkill = request.getJSON()
        ApplicationSkill appSkill = new ApplicationSkill()
        appSkill.application = Application.findById(newSkill.vueNewEmployeeApplicantAddSkillDataframe_application_id)
        appSkill.comment = newSkill.vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_comment
        appSkill.level = newSkill.vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_level.toInteger()
        appSkill.skill = newSkill.vueNewEmployeeApplicantAddSkillDataframe_applicationSkill_skill
        appSkill.save()
        def newSkillsAfterSave = [Skill:appSkill.skill,Id:appSkill.id,Comment: appSkill.comment, Level:appSkill.level]
        def resultData = [success: true,newData:[applicationSkill:newSkillsAfterSave]]
        render (resultData as JSON)
    }
}
