package com.elintegro.login

import com.elintegro.auth.User
import com.elintegro.crm.Person
import com.elintegro.otpVerification.Otp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.ui.RegistrationCode
import grails.plugin.springsecurity.ui.SpringSecurityUiService
import grails.util.Holders
import groovy.time.TimeCategory
import groovy.time.TimeDuration
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.transaction.interceptor.TransactionAspectSupport

import javax.servlet.http.HttpSession
import java.text.DecimalFormat

@Transactional
class LoginService {
    def springSecurityService
    def passwordEncoder
    def emailService
    def messageSource
    def sendVerificationCodeForLoginWithOTP(def params){
        def result
        def message
        def currentRoute = params.currentRoute
        User user1 = User.findByUsername(params.transits.email.value)
        if(user1) {
            Otp otpAlreadyExist = Otp.findByUser(user1)
            RegistrationCode registrationCodeAlreadyExist = RegistrationCode.findByUsername(params.transits.email.value)
            TimeDuration duration
            if (otpAlreadyExist && registrationCodeAlreadyExist) {
                duration = TimeCategory.minus(new Date(), otpAlreadyExist.createTime)
                if (duration.hours > 24) {
                    otpAlreadyExist.delete(flush: true)
                    registrationCodeAlreadyExist.delete(flush: true)
                    RegistrationCode registrationCode = registrationCode(user1)
                    Otp otp = new Otp()
                    result = sendVerificationCode(user1, otp ,registrationCode.token, currentRoute)
                } else {
                    message = messageSource.getMessage( 'Otp.code.already.sent',null,'Failed', LocaleContextHolder.getLocale())
                    result = [success: true, msg: message, alert_type: "info",currentRoute:currentRoute,params:params]
                }
            } else {
                Otp otp = new Otp()
                RegistrationCode registrationCode = registrationCode(user1)
                result = sendVerificationCode(user1, otp, registrationCode.token, currentRoute)
            }
        }
        else {
            message = messageSource.getMessage( 'ask.for.registration',null,'Failed', LocaleContextHolder.getLocale())
            result = [success: false, msg: message, alert_type: "error", askForRegistration:true, params:params, currentRoute: currentRoute]
        }
        return result
    }
    def sendVerificationCodeAfterRegisterConfirmedWithOTP(def params){
        User user = new User()
        user.email = params.transits.email.value
        user.username = params.transits.email.value
        user.password = new Random().toString()
        user.save(flush:true)
        Person applicant = new Person()
        applicant.email = params.transits.email.value
        applicant.user = user
        applicant.save(flush:true)
        Otp otp = new Otp()
        RegistrationCode registrationCode = registrationCode(user)
        def result = sendVerificationCode(user, otp, registrationCode.token, "/elintegro-user-profile/$user.id")
        return result
    }
    def loginWithOTP(def param, HttpSession session){
        User user1 = User.findByUsername(param.transits.email.value)
        Otp otp = Otp.findByUser(user1)
        def result
        def msg
        if(otp) {
            def counter = session.getAttribute("NO_OF_ATTEMPTS_FOR_OTP")?:0;
            TimeDuration duration = TimeCategory.minus(new Date(), otp.createTime)
            if (otp && duration.hours <= 24 && counter<= 10) {
                def isOtpValid = passwordEncoder.isPasswordValid(otp.verificationCode, param.transits.verificationCode.value, null)
                if (isOtpValid == true) {
                    try {
                        RegistrationCode registrationCode = RegistrationCode.findByUsername(param.transits.email.value)
                        if (registrationCode) {
                            registrationCode.delete(flush: true)
                        }
                        springSecurityService.reauthenticate(user1.username, param.transits.verificationCode.value)
                        otp.delete(flush: true)
                        def routeId = 0
                        def currentLocationUrl = param.currentLocationUrl
                        if (param.currentLocationUrl == "/elintegro-user-profile/$user1.id") {
                            msg = messageSource.getMessage('Please.update.your.profile', null, 'Success', LocaleContextHolder.getLocale())
                            routeId = user1.id
                        } else {
                            msg = messageSource.getMessage('Login.successful', null, 'Success', LocaleContextHolder.getLocale())
                        }
                        result = [success: true, msg: msg, alert_type: "success", userId: user1.id, currentLocationUrl: currentLocationUrl, routeId: routeId]
                    } catch (Exception e) {
                        log.error("Couldn't authenticate this user." + e)
                        msg = messageSource.getMessage('Couldnot.authenticate.this.user', null, 'Failed', LocaleContextHolder.getLocale())
                        result = [success: false, msg: msg, alert_type: "error"]
                    }
                } else {
                    counter++
                    session.setAttribute("NO_OF_ATTEMPTS_FOR_OTP", counter)
                    log.error("Authentication failed, incorrect verification code.")
                    msg = messageSource.getMessage('Incorrect.verification.code', null, 'Failed', LocaleContextHolder.getLocale())
                    result = [success: false, msg: msg, alert_type: "error", incorrectVerificationCode:true]
                }

            } else {
                session.setAttribute("NO_OF_ATTEMPTS_FOR_OTP", 0)
                RegistrationCode registrationCode = RegistrationCode.findByUsername(param.transits.email.value)
                if (otp && registrationCode) {
                    otp.delete(flush: true)
                    registrationCode.delete(flush: true)
                }
                msg = messageSource.getMessage('This.code.has.been.expired', null, 'Failed', LocaleContextHolder.getLocale())
                result = [success: false, msg: msg, alert_type: "error"]

            }
        }else{
            msg = messageSource.getMessage('This.code.has.been.expired', null, 'Failed', LocaleContextHolder.getLocale())
            result = [success: false, msg: msg, alert_type: "error"]

        }
        return result
    }
    def resendOTPcodeAndLink(def param){
        User user1 = User.findByUsername(param.transits.email.value)
        Otp otp = Otp.findByUser(user1)
        RegistrationCode registrationCodeAlreadyExist = RegistrationCode.findByUsername(param.transits.email.value)
        def token
        if(registrationCodeAlreadyExist){
            registrationCodeAlreadyExist.delete(flush: true)
            RegistrationCode  registrationCode = registrationCode(user1)
            token = registrationCode.token
        }else {
            RegistrationCode  registrationCode = registrationCode(user1)
            token = registrationCode.token
        }
        def result
        def msg
        if(user1 && otp && token){
            result = sendVerificationCode(user1, otp, token, param.currentRoute)
        }
        else {
            msg = messageSource.getMessage( 'We.dont.have.user.with.this.email',null,'Failed', LocaleContextHolder.getLocale())
            result = [success: false, msg:msg, alert_type: "error"]
        }
        return result
    }

    def sendVerificationCode(User user1, Otp otp, String token, String currentRoute){
        String verificationCode = new DecimalFormat("000000").format(new Random().nextInt(999999));
        def encodedVerificationCode = springSecurityService.encodePassword(verificationCode)
        otp.verificationCode = encodedVerificationCode
        otp.createTime = new Date()
        otp.expireTime = new Date().plus(1)
        otp.user = user1
        otp.save()
        def conf = Holders.grailsApplication.config
        String url = conf.grails.serverURL+"/login/verifyLoginRegisterWithOtpByToken/$token?location=$currentRoute"
        String emailBody = messageSource.getMessage( 'email.message.for.otp.login.register',null,'Email sent', LocaleContextHolder.getLocale())
        Map emailParams = [verificationCode: verificationCode, url:url]
        def result
        def msg
        try {
            emailService.sendMail(user1.email, emailParams, emailBody)
            msg = messageSource.getMessage( 'We.sent.verification.code',null,'Success', LocaleContextHolder.getLocale())
            result = [success: true, msg:msg, alert_type: "success",currentRoute:currentRoute, userId: user1.id]
        } catch (Exception e) {
            log.error("Email sending failed" + e)
            msg = messageSource.getMessage( 'Couldnot.send.mail',null,'Success', LocaleContextHolder.getLocale())
            result = [success: false, msg:msg, alert_type: "error"]
        }
        return  result

    }
    RegistrationCode registrationCode(user) {
        String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName
        def registrationCode = new RegistrationCode(username: user."$usernameFieldName")
        if (!registrationCode.save()) {
            new SpringSecurityUiService().warnErrors(registrationCode, messageSource)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
        }
        return registrationCode
    }


}
