package com.elintegro.userProfile

import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.hr.Position
import com.elintegro.ref.Language
import grails.converters.JSON
import grails.util.Holders

import java.text.SimpleDateFormat

class ProfileDetailController {
    def springSecurityService

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
    def imageData = {
        def currentUser = springSecurityService.currentUser
        Person person = Person.findById(currentUser.id)
        def imageName
        def imageUrl
        if(person.mainPicture != null){
            imageName = person.mainPicture
            imageUrl = Holders.grailsApplication.config.images.storageLocation + "/images/" + imageName

        }
        else{
            imageUrl = 'C:/dev/github/elintegro/dataframe/elintegrostartapp/grails-app/assets/images/default_profile.jpg'
            imageName = Holders.grailsApplication.config.images.defaultImageName
        }
        def file = new File(imageUrl)
        def extension = imageName - ~/.*(?<=\.)/
        response.setContentType("application/"+extension)
        response.setContentLength(file.size().toInteger())
        OutputStream out = response.getOutputStream();
        out.write(file.bytes);
        out.close();
    }
}
