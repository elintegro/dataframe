package com.elintegro.newEmployeeApplicant

import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.elintegrostartapp.hr.ApplicationSkill
import com.elintegro.elintegrostartapp.hr.Images
import com.elintegro.elintegrostartapp.hr.Position
import com.elintegro.elintegrostartapp.hr.Skills
import grails.converters.JSON

class EmployeeApplicationController {
    def springSecurityService
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
    def applicantDocuments(){
        def empDoc = request.getJSON()
        def resultData
        try {
            Application application = Application.findById(empDoc.vueNewEmployeeUploadResumeDataframe_application_id)
            application.resume = empDoc.vueNewEmployeeUploadResumeDataframe_application_resume

            for (item in empDoc.vueNewEmployeeUploadResumeDataframe_avatar) {
                Images images = new Images()
                images.name = item
                images.save()
                application.addToImages(images)
                application.save(flush: true)
            }
             resultData = [success: true,application_id: application.id,params:empDoc]
        }catch(Exception e){
            log.error("Failed to save new employee's documents.")
        }
        render (resultData as JSON)
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
    def addComment(){
        def currentUser = springSecurityService.currentUser
        def params = request.getJSON()
        def newComment = params.vueElintegroCommentPageForApplicantDataframe_application_lastComment
        Application application = Application.findById(params.vueElintegroCommentPageForApplicantDataframe_application_id)
        if(application.comments == null){
            application.comments = newComment+"\n\n"+ "\t\t\t\t\t\t"+"-"+" "+(currentUser.firstName).concat(" " + currentUser.lastName)
        }
        else {
            application.comments = application.comments.concat("\n\n" + newComment+"\n\n"+ "\t\t\t\t\t\t"+"-"+" "+(currentUser.firstName).concat(" " + currentUser.lastName))
        }
        application.save(flush:true)
        def resultData = [success: true,savedComment:application.comments]
        render(resultData as JSON)
    }






}
