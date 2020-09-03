/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.register

import com.elintegro.auth.Role
import com.elintegro.auth.User
import com.elintegro.auth.UserRole
import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.gc.data.DataInit
import com.elintegro.gerf.DataframeController
import com.elintegro.elintegrostartapp.Facility
import com.elintegro.elintegrostartapp.FacilityUserRegistration
import com.sun.org.apache.bcel.internal.generic.RETURN
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.ui.RegisterCommand
//import grails.plugin.springsecurity.ui.RegistrationCode
import grails.plugin.springsecurity.ui.SpringSecurityUiService
import grails.util.Holders
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.transaction.interceptor.TransactionAspectSupport
import sun.security.tools.keytool.Pair

@Transactional
class RegisterService{

    def messageSource
    def mailService
    def springSecurityService
    def emailService

    def registerUser(RegisterCommand command, Role role, Facility facility) {
        def serviceMessage
        DataframeController dataframeController = new DataframeController()
        com.elintegro.auth.User user = null
        if (!command.validate()) {
            def error =  command?.errors?.getFieldError()
            serviceMessage = message(error: error)?.toString()
            //serviceMessage = errorMessage //['msg': errorMessage, 'success': false]
            if(command.username == ""){
                serviceMessage = message(code: 'registration.username.empty')
                //resultData = ['msg': errMesg, 'success': false]
            }
        }else {
            def resultData = dataframeController.ajaxSaveRaw()
            Map domainInstanceMap = resultData?.dfInstance.getSavedDomainsMap();
            def savedDomainInstances = domainInstanceMap.values()

            if (savedDomainInstances) {
                user = getRegisterUserInstance(savedDomainInstances, user)
                UserRole.create(user, role)
                if (facility) {
                    facility.addToUsers(user)
                }
            }
        }

        return new grails.util.Pair(user, serviceMessage)
    }

    private static def getRegisterUserInstance(savedDomainInstances, user){
        String userDomainClassName = SpringSecurityUtils.securityConfig.userLookup.userDomainClassName
        Class clazz = Holders.grailsApplication.getDomainClass(userDomainClassName).clazz
        for (def instance:savedDomainInstances){
            if (instance.instanceOf(clazz)){
                user = instance
                break
            }
        }
        return user
    }

    def createLeadUser(def param){
        def result
        try{
            User user1 = new User()
            user1.email = param.vueElintegroSignUpQuizDataframe_person_email
            user1.username = param.vueElintegroSignUpQuizDataframe_person_email
            user1.firstName = param.vueElintegroSignUpQuizDataframe_person_firstName
            user1.lastName = param.vueElintegroSignUpQuizDataframe_person_lastName
            def password = new Random().toString().replaceAll("java.util.","")
            user1.password = password
            user1.enabled = true
            user1.accountLocked = false
            user1.save(flush:true)

            Role role = Role.findByName("ROLE_LEAD")
            UserRole.create(user1,role,true)

            Person applicant = new Person()
            applicant.firstName = param.vueElintegroSignUpQuizDataframe_person_firstName
            applicant.lastName = param.vueElintegroSignUpQuizDataframe_person_lastName
            applicant.email = param.vueElintegroSignUpQuizDataframe_person_email
            applicant.phone = param.vueElintegroSignUpQuizDataframe_person_phone
            applicant.user = user1
            applicant.save()

            Application application = new Application()
            application.applicant = applicant
            application.leadDescription = param.vueElintegroSignUpQuizDataframe_application_leadDescription['Answer']
            application.leadStage = param.vueElintegroSignUpQuizDataframe_application_leadStage['Answer']
            application.leadBudget = param.vueElintegroSignUpQuizDataframe_application_leadBudget['Answer']
            application.save()



            result = [success: true, person_id: applicant.id, application_id: application.id,userId: user1.id,user:user1,password:password]
        }
        catch(Exception e){
            def msg = " Failed to save Person's data error = " + e
            result = [success: false]
            log.error(msg)
        }
        return  result

    }
    def sendingEmailAfterSignUp(String firstName, String password, String email,String url) {
        def resultData
        def msg
        try {
            def conf = Holders.grailsApplication.config
            String emailBody = conf.registerService.emailInfoAfterSignUp
            String emailSubject = conf.registerService.emailSubjectAfterSignUp
            Map emailParams = [name: firstName, password: password, currentUser: email,url:url]
            msg = messageSource.getMessage( 'sign.up.success.mail',null,'Success',LocaleContextHolder.getLocale())
            emailService.sendingMailWithSubject(email, emailParams, emailBody, emailSubject)
            resultData = [success: true, msg:msg,alert_type: "success"]
        }catch(Exception e){
            msg = "Failed to send email"+e
            resultData = [success: false, msg:msg,alert_type:"error"]
            log.error("Error occured while sending mail "+e)
        }
        return resultData
    }
}
