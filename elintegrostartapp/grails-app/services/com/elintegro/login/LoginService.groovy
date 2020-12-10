package com.elintegro.login

import com.elintegro.auth.User
import com.elintegro.otpVerification.Otp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.util.Holders
import groovy.time.TimeCategory
import groovy.time.TimeDuration
import org.springframework.context.i18n.LocaleContextHolder

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
        User user1 = User.findByUsername(params.transits.emailOrPhone.value)
        if(user1) {
            Otp otpAlreadyExist = Otp.findByUser(user1)
            TimeDuration duration
            if (otpAlreadyExist) {
                duration = TimeCategory.minus(new Date(), otpAlreadyExist.createTime)
                if (duration.hours > 24) {
                    otpAlreadyExist.delete(flush: true)
                    Otp otp = new Otp()
                    result = sendVerificationCode(user1, otp)
                } else {
                    message = messageSource.getMessage( 'Otp.code.already.sent',null,'Failed', LocaleContextHolder.getLocale())
                    result = [success: true, msg: message, alert_type: "info"]
                }
            } else {
                Otp otp = new Otp()
                result = sendVerificationCode(user1, otp)
            }
        }
        else {
            message = messageSource.getMessage( 'We.dont.have.user.with.this.email',null,'Failed', LocaleContextHolder.getLocale())
            result = [success: false, msg: message, alert_type: "error"]
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
    def resendOTPcode(def param){
        User user1 = User.findByUsername(param.transits.emailOrPhone.value)
        Otp otp = Otp.findByUser(user1)
        def result
        def msg
        if(user1 && otp){
          result = sendVerificationCode(user1, otp)
        }
        else {
            msg = messageSource.getMessage( 'We.dont.have.user.with.this.email',null,'Failed', LocaleContextHolder.getLocale())
            result = [success: false, msg:msg, alert_type: "error"]
        }
        return result
    }
    def sendVerificationCode(User user1, Otp otp){
        String verificationCode = new DecimalFormat("000000").format(new Random().nextInt(999999));
        def encodedVerificationCode = springSecurityService.encodePassword(verificationCode)
        otp.verificationCode = encodedVerificationCode
        otp.createTime = new Date()
        otp.expireTime = new Date().plus(1)
        otp.user = user1
        otp.save()
        def conf = Holders.grailsApplication.config
        String emailBody = conf.loginController.emailForLoginWithOTP
        Map emailParams = [verificationCode: verificationCode]
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


}
