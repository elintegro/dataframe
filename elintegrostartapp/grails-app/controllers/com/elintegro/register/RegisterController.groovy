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
import com.elintegro.crm.Person
import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.auth.User
import com.elintegro.auth.UserRole
import com.elintegro.gc.data.DataInit
import com.elintegro.gerf.DataframeController
import com.elintegro.elintegrostartapp.Facility
import com.elintegro.elintegrostartapp.FacilityUserRegistration
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.ui.*
import grails.util.Holders
import org.apache.commons.lang.StringUtils
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.interceptor.TransactionAspectSupport
import sun.security.tools.keytool.Pair

class RegisterController extends grails.plugin.springsecurity.ui.RegisterController {

    def messageSource
    def mailService
    def springSecurityService
    def registerService
    def passwordEncoder

    //@Transactional(propagation=Propagation.REQUIRES_NEW)
    def register() {

        def requestParams = request.getJSON()
        def resultData
//        String dfrName = requestParams["dataframe"] + "_user_email"
        //FacilityUserRegistration facilityUserReg = FacilityUserRegistration.findByExpectedUser(requestParams[dfrName])

        String expectedRole = requestParams["role"]
        String verificationEmailMessage

        //If we donot send any parameterm the role will be ROLE_ADMIN
        //TODO: probably it is a security breach, let us stricken it out by always providing role in the request params!
        if (StringUtils.isEmpty(expectedRole)) {
            expectedRole = "ROLE_ADMIN"
        }
        Role regRole = Role.findByAuthority(expectedRole)

//        if (!facilityUserReg || !regRole) {
//            def facilityErrorMessage = message(code: 'registration.facility.notexpected')
//            log.error(facilityErrorMessage)
//            resultData = ['msg': facilityErrorMessage, 'success': false]
//
//        }else{ //expected User is registering for the facility and expected role is correct

        RegisterCommand command = getRegisterValidationObj(requestParams)
        grails.util.Pair result = registerService.registerUser(request, command, regRole, null)

        com.elintegro.auth.User user = result.getaValue()
        def returnedMessage = result.getbValue()
        def errorMessage = message(code: returnedMessage)

        if (user == null) {
            resultData = ['msg': errorMessage, 'success': false]
            def converter = resultData as JSON
            converter.render(response)
            return
        }


        DataInit.initStructuresForRegisteredUser(user)
        RegistrationCode registrationCode = registrationCode(user)
        if (registrationCode == null || registrationCode.hasErrors()) {
            flash.error = message(code: 'spring.security.ui.register.miscError')
            flash.chainedParams = params
            return
        }

        try {
            sendVerifyRegistrationMail registrationCode, user, command.email
            verificationEmailMessage = message(code: 'registration.mail.success')
            resultData = ['msg': verificationEmailMessage, 'success': true, data:requestParams, alert_type: 'success']
        } catch (Exception e) {
            log.error(e)
            verificationEmailMessage = message(code: 'registration.mail.noConnection')
            resultData = ['msg': verificationEmailMessage, 'success': false]
        }

        def converter = resultData as JSON
        converter.render(response)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    def verifyRegistration() {
        String token = params.t

        RegistrationCode registrationCode = token ? RegistrationCode.findByToken(token) : null
        if (!registrationCode) {
            flash.error = message(code: 'spring.security.ui.register.badCode')
            redirect uri: successHandlerDefaultTargetUrl
            return
        }

        //boolean isNew = TransactionAspectSupport.currentTransactionStatus().isNewTransaction() //setRollbackOnly()
        def user = uiRegistrationCodeStrategy.finishRegistration(registrationCode)

        if (!user) {
            flash.error = message(code: 'spring.security.ui.register.badCode')
            redirect uri: successHandlerDefaultTargetUrl
            return
        }

        if (user.hasErrors()) {
            // expected to be handled already by ErrorsStrategy.handleValidationErrors
            return
        }

//        Person p = new Person(firstName:user.firstName, lastName:user.lastName, email:user.username, user:user).save(flush:true)

        flash.message = message(code: 'spring.security.ui.register.complete')

        redirect uri: registerPostRegisterUrl ?: successHandlerDefaultTargetUrl
        ////////////////////////////////////////////
    }


    def forgotPassword() {
        def params = request.getJSON()

        ForgotPasswordCommand forgotPasswordCommand = getForgetPasswordCommandObject(params.persisters.user.email.value)
        def user = findUserByUsername(forgotPasswordCommand.username)
        def jsonMap
        String msg
        if (!user) {
            msg = getMessageFromCode('register.forgotPassword.email.not.found')

            jsonMap = [success: false, error: true,alert_type: "error", msg: msg, forgotPasswordCommand: forgotPasswordCommand]
        }
        else {

            String email = uiPropertiesStrategy.getProperty(user, 'email')
            if (!email) {
                msg = getMessageFromCode('register.forgotPassword.email.not.found')

                jsonMap = [success: false,alert_type: "error", error: true, msg: msg, forgotPasswordCommand: forgotPasswordCommand]
            }
            else {
                if (user && email) {
                    RegistrationCode registrationCode = uiRegistrationCodeStrategy.sendForgotPasswordMail(
                            forgotPasswordCommand.username, email) { String registrationCodeToken ->

                        String urlToChangePW = Holders.grailsApplication.config.grails.serverURL + "/#/change-forget-password/0?$registrationCodeToken"
                        String body = forgotPasswordEmailBody
                        if (body.contains('$')) {
                            body = evaluate(body, [user: user, url: urlToChangePW])
                        }

                        body
                    }
                    msg = getMessageFromCode('register.forgotPassword.email.sent.success')
                    jsonMap = [success: true, msg: msg, emailSent: true, alert_type: "success", forgotPasswordCommand: forgotPasswordCommand]
                } else {
                    msg = getMessageFromCode('register.forgotPassword.email.not.found')
                    jsonMap = [success: false, msg: msg, emailSent: false, alert_type: "error"]

                }
            }
        }
        def converter = jsonMap as JSON
        converter.render(response)
    }

    private String getMessageFromCode(String msgCode) {
        return messageSource.getMessage(msgCode, null, LocaleContextHolder.getLocale())

    }

    private getForgetPasswordCommandObject(email) {
        ForgotPasswordCommand forgotPasswordCommand = new ForgotPasswordCommand()
        forgotPasswordCommand.username = email
        return forgotPasswordCommand
    }
   def changeForgotPassword() {
       def resultData
       def msg
       def param = request.getJSON()
       RegistrationCode registrationCode = RegistrationCode.findByToken(param.token)
       if (registrationCode) {
           try {
               User user1 = User.findByUsername(registrationCode.username)
               user1.password = param.transits.newPassword.value
               user1.save(flush: true)
               registrationCode.delete(flush: true)
               msg = message(code: 'password.changed.successfully')
               resultData = [success: true, msg: msg, alert_type: "success"]
           } catch (Exception e) {
               log.error("Couldn't find this user" + e)
               msg = message(code: "something.is.went.wrong")
               resultData = [success: false, msg: msg, alert_type: "error"]
           }
       } else {
           msg = message(code: 'unable.to.change.password')
           resultData = [success: false, msg: msg, alert_type: "error"]
       }

       render(resultData as JSON)
   }

    public def openResetPasswordPage() {
        String token = params.t
        String userId = params.userId
        /*Calendar cal = Calendar.getInstance()
          if ((tokn.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "token expired";
        }*/
        redirect(controller: "main", action: "show", params: [token: token])
    }

    @Transactional
    public def resetUserPassword() {
        println params
        Map resultData = [:]
        def errorMessage
        String token = params.t
        if (token) {
            ResetPasswordCommand resetPasswordCommand = getResetPasswordValidationObj(params)
            resultData = resetPassword(resetPasswordCommand)
        } else {
            ResetPasswordCommand resetPasswordCommand = getResetPasswordValidationObj(params)
            resetPasswordCommand.validate()

            if (!resetPasswordCommand.validate()) {
                def error = resetPasswordCommand?.errors?.getFieldError()
                errorMessage = message(error: error)?.toString()
                resultData = ['msg': errorMessage, 'success': false]
            } else {
                def user = findUserByUsername(resetPasswordCommand.username)
                user.password = resetPasswordCommand.password
                user.save(flush: true)
                if (user.hasErrors()) {
                    errorMessage = message(code: 'vueElintegroUserProfileDataframe.resetPassword.failure')
                    resultData = ['msg': errorMessage, 'success': false]
                } else {
                    errorMessage = message(code: 'vueElintegroUserProfileDataframe.resetPassword.success')
                    resultData = ['msg': errorMessage, 'success': true]

                }
            }

        }
        def converter = resultData as JSON
        converter.render(response)
//        redirect(controller: 'main', action: 'show')
    }

    def resetPassword(ResetPasswordCommand resetPasswordCommand) {

        String token = params.t
        Map resultData = [:]
        def errorMessage
        def registrationCode = token ? RegistrationCode.findByToken(token) : null
        if (!registrationCode) {
            errorMessage = message(code: 'spring.security.ui.resetPassword.badCode')
            resultData = ['msg': errorMessage, 'success': false]
//            redirect uri: successHandlerDefaultTargetUrl
            return resultData
        }

        if (!request.post) {
            return [token: token, resetPasswordCommand: new ResetPasswordCommand()]
        }

        resetPasswordCommand.username = registrationCode.username
        if (!resetPasswordCommand.validate()) {
            def error = resetPasswordCommand?.errors?.getFieldError()
            errorMessage = message(error: error)?.toString()
            resultData = ['msg': errorMessage, 'success': false]
        } else {
            def user = uiRegistrationCodeStrategy.resetPassword(resetPasswordCommand, registrationCode)
            if (user.hasErrors()) {
                errorMessage = message(code: 'vueElintegroUserProfileDataframe.resetPassword.failure')
                resultData = ['msg': errorMessage, 'success': false]
            } else {
                errorMessage = message(code: 'vueElintegroUserProfileDataframe.resetPassword.success')
                def url = createLink(controller: 'main', action: 'show')
                resultData = ['msg': errorMessage, 'success': true, 'redirect': true, 'redirectUrl': "$url"]

            }

        }
//        redirect uri: registerPostResetUrl ?: successHandlerDefaultTargetUrl
        return resultData
    }

    private static ResetPasswordCommand getResetPasswordValidationObj(requestParams) {
        ResetPasswordCommand command = new ResetPasswordCommand()
        command.username = requestParams.get("vueElintegroResetPasswordDataframe_user_email")
        command.password = requestParams.get("vueElintegroResetPasswordDataframe_user_password")
        command.password2 = requestParams.get("vueElintegroResetPasswordDataframe_password2")
        return command
    }

    RegistrationCode registrationCode(user) {
        //todo:commented code may be useful here got error need to look once
//        String salt = saltSource instanceof NullSaltSource ? null : registerCommand.username
//        RegistrationCode registrationCode = uiRegistrationCodeStrategy.register(user, registerCommand.password, salt)

        String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName
        def registrationCode = new RegistrationCode(username: user."$usernameFieldName")
        if (!registrationCode.save()) {
            new SpringSecurityUiService().warnErrors(registrationCode, messageSource)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        return registrationCode
    }

    private static RegisterCommand getRegisterValidationObj(requestParams) {
        String dataframeName = requestParams.dataframe
        RegisterCommand command = new RegisterCommand()
        command.email = requestParams.persisters.user.email.value
        command.username = command.email
        command.password = requestParams.persisters.user.password.value
        command.password2 = requestParams.transits.password2.value
        return command
    }

    def createLeadUser() {
        def param = request.getJSON()
        def resultData
        User user1 = User.findByUsername(param.persisters.person.email.value)
        Role role = Role.findByName("ROLE_LEAD")
        UserRole userRole = UserRole.findByRoleAndUser(role, user1)
        if (!userRole) {
            def result = registerService.createLeadUser(param)
            if(result.user) {
                RegistrationCode registrationCode = registrationCode(result.user)
                String url = generateLink('verifyRegistration', [t: registrationCode.token])
                if (registrationCode == null || registrationCode.hasErrors()) {
                    flash.error = message(code: 'spring.security.ui.register.miscError') as Object
                    flash.chainedParams = params
                    return
                }
                resultData = registerService.sendingEmailAfterSignUp(result.user.firstName, result.password, result.user.email, url, registrationCode.token)
            }
            else {
                resultData = [success:false,msg:message(code: "user.not.found.could.not.proceed.request"),alert_type: "error"]
            }


        }
        else{
            resultData = [success: false, msg: message(code: "request.has.been.already.submitted"),alert_type: "info"]
        }
        render(resultData as JSON)
    }

    def changePassword() {
        def resultData
        def msg
        def param = request.getJSON()
        RegistrationCode registrationCode = RegistrationCode.findByToken(param.token)
        if(registrationCode) {
            User user1 = User.findByUsername(registrationCode.username)
            def isCurrentPasswordValid = passwordEncoder.isPasswordValid(user1.password, param.transits.currentPassword.value, null)
            if (isCurrentPasswordValid == true) {
                user1.password = param.transits.newPassword.value
                user1.save(flush: true)

                try {
                    springSecurityService.reauthenticate(user1.username, user1.password)
                    registrationCode.delete(flush: true)
                    msg = message(code: 'password.changed.successfully')
                    resultData = [success: true, msg:msg, alert_type: "success"]
                } catch (Exception e) {
                    log.error("Couldn't authenticate this user" + e)
                    msg = message(code:  "password.cannot.be.changed")
                    resultData = [success: false, msg:msg , alert_type: "error"]
                }


            } else {
                msg = message(code: 'incorrect.current.password')
                resultData = [success: false, msg: msg, alert_type: "error"]
            }
        }else{
            msg = message(code: 'this.link.has.been.expired')
            resultData = [success: false, msg: msg, alert_type: "error"]
        }

        render(resultData as JSON)
    }
}
