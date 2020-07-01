package com.elintegro.userProfile

import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.hr.Position
import com.elintegro.ref.Language
import grails.converters.JSON

import java.text.SimpleDateFormat

class ProfileDetailController {

    def editProfileData() {
        def profileData = request.getJSON()
        def resultData
        try{
        Person person = Person.findById(profileData.vueElintegroUserProfileDataframe_person_id)
        person.firstName = profileData.vueElintegroUserProfileDataframe_person_firstName
        person.lastName = profileData.vueElintegroUserProfileDataframe_person_lastName
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            Date date = inputFormat.parse(profileData.vueElintegroUserProfileDataframe_person_bday)
        person.bday = date
        person.mainPicture = profileData.vueElintegroUserProfileDataframe_propertyImages
        person.phone = profileData.vueElintegroUserProfileDataframe_person_phone
            for (item in profileData.vueElintegroUserProfileDataframe_person_languages) {
                Language languages = Language.findById(item.id)
                person.addToLanguages(languages)
             person.save(flush:true)
            }
        def profileDetail = [person: profileData]
         resultData = [success: true,params:profileData,newData: profileDetail]
    }
        catch (Exception e){
            log.error("Failed to edit profile details "+ e)
        }
        render(resultData as JSON)
    }
}
