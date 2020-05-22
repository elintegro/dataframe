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
import com.elintegro.crm.Person
import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.gerf.DataframeController
import com.elintegro.register.RegisterController
import grails.gorm.transactions.Transactional
import grails.util.Holders
import grails.plugin.springsecurity.ui.RegistrationCode
import org.springframework.context.i18n.LocaleContextHolder

import javax.servlet.http.HttpSession

@Transactional
class UserManagementFormService {

    def messageSource
    def emailService
    def grailsLinkGenerator
    def facilityService
    def serviceMethod() {

    }

def save(Map _params, HttpSession session){
        save(_params, session, null, true)
    }

    def save(Map _params, HttpSession session, Class classToSaveFaciltyTo, boolean doCommit = true){

        DataframeController dc = new DataframeController()
        String prmsPrintOut = DataframeInstance.reqParamPrintout(_params);

        log.debug("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");
        System.out.println("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");

        Dataframe dataframe = dc.getDataframe(_params)
        def dfInstance = new DataframeInstance(dataframe, _params)
        def operation = 'U'; //Update
        boolean isInsert;

        def result = dfInstance.save(doCommit);

        if(classToSaveFaciltyTo && !doCommit){
            facilityService.populateFacility(result, session, classToSaveFaciltyTo)
            dfInstance.commit()
        }

        if(dfInstance.isInsertOccured()){
            operation = "I";
        }

        Map returnedMap = dc.formatResult(_params, dfInstance, dataframe, result)

        return returnedMap
    }

    def createUser(Map userParam, Long roleId, boolean sendEmailWithPassword){

        String email = userParam["email"]
        if(!email){
            return [success: false, user:null, msg: "email not present"]
        }
        assert roleId
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
                Role role1 = Role.findById(roleId).save()
                if(role1 == null){
                    log.debug("The provided role was not found. Role was not set")
                }
                new UserRole(user: user, role: role1).save(flush: true)
                if(sendEmailWithPassword){
                    try{
                        sendVerificationEmail(user, email, password)
                        response =[success: true, user:user, msg:"User created for you. Please check email for the temporary password"]

                    } catch (Exception e){
                        response = [success: true, user: null, msg: "User created but verification email could not be sent"]
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

    private def rollbackTransaction(List toDeleteLists){
        toDeleteLists.each {
            if(it){
                it.delete(flush: true)
            }
        }

    }
}
