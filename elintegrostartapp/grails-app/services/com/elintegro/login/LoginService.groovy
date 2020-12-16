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
        User user1 = User.findByUsername(params.transits.emailOrPhone.value)
        if(user1) {
            Otp otpAlreadyExist = Otp.findByUser(user1)
            RegistrationCode registrationCodeAlreadyExist = RegistrationCode.findByUsername(params.transits.emailOrPhone.value)
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
                    result = [success: true, msg: message, alert_type: "info"]
                }
            } else {
                Otp otp = new Otp()
                RegistrationCode registrationCode = registrationCode(user1)
                result = sendVerificationCode(user1, otp, registrationCode.token, currentRoute)
            }
        }
        else {
            User user = new User()
            user.email = params.transits.emailOrPhone.value
            user.username = params.transits.emailOrPhone.value
            user.password = new Random().toString()
            user.save(flush:true)
            Person applicant = new Person()
            applicant.firstName = params.transits.emailOrPhone.value
            applicant.lastName = params.transits.emailOrPhone.value
            applicant.email = params.transits.emailOrPhone.value
            applicant.user = user
            applicant.save(flush:true)
            Otp otp = new Otp()
            RegistrationCode registrationCode = registrationCode(user)
            result = sendVerificationCode(user, otp, registrationCode.token, "/elintegro-user-profile/$user.id")
        }
        return result
    }
    def loginWithOTP(def param){
        User user1 = User.findByUsername(param.transits.emailOrPhone.value)
        Otp otp = Otp.findByUser(user1)
        TimeDuration duration = TimeCategory.minus(new Date(), otp.createTime)
        def result
        def msg
        if(otp && duration.hours <= 24){
            def isOtpValid = passwordEncoder.isPasswordValid(otp.verificationCode, param.transits.verificationCode.value, null)
            if(isOtpValid == true) {
                try {
                    RegistrationCode registrationCode = RegistrationCode.findByUsername(param.transits.emailOrPhone.value)
                    if(registrationCode){
                        registrationCode.delete(flush:true)
                    }
                    springSecurityService.reauthenticate(user1.username, param.transits.verificationCode.value)
                    otp.delete(flush: true)
                    msg = messageSource.getMessage( 'Login.successful',null,'Success', LocaleContextHolder.getLocale())
                    result = [success: true, msg: msg, alert_type: "success"]
                } catch (Exception e) {
                    log.error("Couldn't authenticate this user." + e)
                    msg = messageSource.getMessage( 'Couldnot.authenticate.this.user',null,'Failed', LocaleContextHolder.getLocale())
                    result = [success: false, msg: msg, alert_type: "error"]
                }
            }
            else {
                log.error("Authentication failed, incorrect verification code.")
                msg = messageSource.getMessage( 'Incorrect.verification.code',null,'Failed', LocaleContextHolder.getLocale())
                result = [success: false, msg:msg, alert_type: "error"]
            }

        }
        else{
            if(otp){
                otp.delete(flush: true)
            }
            msg = messageSource.getMessage( 'This.code.has.been.expired',null,'Failed', LocaleContextHolder.getLocale())
            result = [success: false,msg:msg,alert_type: "error"]

        }
        return result
    }
    def resendOTPcodeAndLink(def param){
        User user1 = User.findByUsername(param.transits.emailOrPhone.value)
        Otp otp = Otp.findByUser(user1)
        RegistrationCode registrationCode = RegistrationCode.findByUsername(param.transits.emailOrPhone.value)
        def result
        def msg
        if(user1 && otp && registrationCode){
          result = sendVerificationCode(user1, otp, registrationCode.token, param.currentRoute)
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
        String emailBody = conf.loginController.emailForLoginWithOTP
        Map emailParams = [verificationCode: verificationCode, url:url]
        def result
        def msg
        try {
            emailService.sendMail(user1.email, emailParams, emailBody)
            msg = messageSource.getMessage( 'We.sent.verification.code',null,'Success', LocaleContextHolder.getLocale())
            result = [success: true, msg:msg, alert_type: "success"]
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
