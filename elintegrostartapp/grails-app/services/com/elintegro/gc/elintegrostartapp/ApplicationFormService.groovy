/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gc.elintegrostartapp

import com.elintegro.auth.Role
import com.elintegro.auth.User
import com.elintegro.auth.UserRole
import com.elintegro.register.RegisterController
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.ui.RegistrationCode
import grails.util.Holders
import org.springframework.context.i18n.LocaleContextHolder

@Transactional
class ApplicationFormService {

    def messageSource
    def emailService
    def grailsLinkGenerator
    def serviceMethod() {

    }

    def createUser(Map userParam, boolean sendEmailWithPassword){

        String email = userParam["email"]
        if(!email){
            return [success: false, msg: "email not present"]
        }
        String firstName = userParam["firstName"]?:""
        String lastName = userParam["lastName"]?:""
        User user = User.findByUsername(email)
        Map response = [:]
        if(!user){
            user = new User()
            user.username = email
            user.email = email
            user.firstName = firstName
            user.lastName = lastName
            String password = generatePassword(firstName)
            user.password = password

            if(user.save(flush:true)){
                Role applicantRole = Role.findByAuthority("ROLE_APPLICANT").save()
                if(applicantRole == null){
                        //Message
                }
                UserRole userRole = new UserRole(user: user, role: applicantRole).save(flush: true)
                if(sendEmailWithPassword){
                    try{
                        sendVerificationEmail(user, email, password)
                        response =[success: true, user:user, msg:"User created for you. Please check email for the temporary password"]

                    } catch (Exception e){
                        response = [success: true, msg: "User created but verification email could not be sent"]
                    }
                }
            }
        }else{
            response = [success: true, user:user, msg:"The User already exists"]
        }

        return response
    }

    def sendVerificationEmail(User user, String sendToEmail, String password){
        Map resultData
        String verificationEmailMessage =""
        RegisterController rc = new RegisterController()
        RegistrationCode registrationCode = rc.registrationCode(user)
        String url = grailsLinkGenerator.link(controller: 'register', action: 'verifyRegistration', params:[t: registrationCode.token], absolute: true)
        def conf =Holders.grailsApplication.config
        String emailBody = conf.emailService.emailWithPassword
        Map emailParams = [user: user, url: url, password:password]
        try{
            emailService.sendMail sendToEmail ,emailParams, emailBody
            verificationEmailMessage = messageSource.getMessage('registration.mail.success',null,LocaleContextHolder.getLocale())
            resultData = ['msg': verificationEmailMessage, 'success': true]
        }catch (Exception e){
            log.debug("Verification email with password could not be sent to Applicant: " + user.email)
            throw e
        }
        return resultData
    }

    String generatePassword(String fname){
        String firstname = fname?:""
        int randNum = (int)(Math.random()*9000)+1000
        String randomNumString = String.valueOf(randNum)
        return firstname+randomNumString
    }
}
