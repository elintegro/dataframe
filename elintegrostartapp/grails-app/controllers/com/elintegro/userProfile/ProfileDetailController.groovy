package com.elintegro.userProfile

import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.hr.Position
import com.elintegro.ref.Language
import grails.converters.JSON
import grails.util.Holders

import java.text.SimpleDateFormat

class ProfileDetailController {
    def springSecurityService

    def imageData (){
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
                imageUrl ="/usr/local/tomcat/webapps/ROOT/assets/default_profile.jpg"
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
