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
        try {
            Person person = Person.findById(profileData.vueElintegroUserProfileDataframe_person_id)
            person.firstName = profileData.vueElintegroUserProfileDataframe_person_firstName
            person.lastName = profileData.vueElintegroUserProfileDataframe_person_lastName
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            Date date = inputFormat.parse(profileData.vueElintegroUserProfileDataframe_person_bday)
            person.bday = date
            if(profileData.vueElintegroUserProfileDataframe_propertyImages != null && profileData.vueElintegroUserProfileDataframe_propertyImages != "" ) {
                person.mainPicture = profileData.vueElintegroUserProfileDataframe_propertyImages
            }
            person.phone = profileData.vueElintegroUserProfileDataframe_person_phone
            for (item in profileData.vueElintegroUserProfileDataframe_person_languages) {
                Language languages = Language.findById(item.id)
                person.addToLanguages(languages)
            }
            person.save(flush: true)

            def profileDetail = [person: profileData]
            resultData = [success: true, params: profileData, newData: profileDetail]
        }
        catch (Exception e) {
            log.error("Failed to edit profile details " + e)
        }
        render(resultData as JSON)
    }
    def imageData = {
        def currentUser = springSecurityService.currentUser
        Person person = Person.findByEmail(currentUser)
        def imageName
        def imageUrl
        if (person.mainPicture != null) {
            imageName = person.mainPicture
            imageUrl = Holders.grailsApplication.config.images.storageLocation + "/images/" + imageName

        } else {
            def serverUrl = Holders.grailsApplication.config.grails.serverURL
            imageName = Holders.grailsApplication.config.images.defaultImageName
            if (serverUrl == "http://localhost:8099") {
                imageUrl = '/dev/github/elintegro/dataframe/elintegrostartapp/grails-app/assets/images/default_profile.jpg'
            } else {
                imageUrl = Holders.grailsApplication.config.images.storageLocation + "/ROOT/assets/default_profile.jpg"
            }

        }
        def file = new File(imageUrl)
        if (file.exists()) {
            try {
                def extension = imageName - ~/.*(?<=\.)/
                response.setContentType("application/" + extension)
                response.setContentLength(file.size().toInteger())
                OutputStream out = response.getOutputStream();
                out.write(file.bytes);
                out.close();
            }catch(Exception e){
                log.error("Image couldn't be generated"+e)
            }
        }
        else{
            log.error("Image doesn't exist")
        }
    }
}
