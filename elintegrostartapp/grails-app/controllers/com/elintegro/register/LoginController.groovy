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

import com.elintegro.auth.User
import com.elintegro.crm.Person
import com.elintegro.gc.AuthenticationService
import com.elintegro.otpVerification.Otp
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Holders
import groovy.time.*

//import grails.plugin.springsecurity.rest.oauth.OauthUser
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.web.WebAttributes

import java.text.DecimalFormat

class LoginController extends grails.plugin.springsecurity.LoginController {

//    def springSecurityService
//    def messageSource
    def emailService
    def authenticationService
    def user = null

    def ajaxSuccess() {
        User userDetails
        boolean isOwner=false
        boolean isTenant=false
        boolean isPropManager=false
        boolean isServiceProvider=false
        boolean isAdmin=false
        boolean isGuestUser = false
        String firstname = ""
        Map userInfo =[:]
        if (springSecurityService.isLoggedIn()) {
            userDetails = User.get(springSecurityService?.principal?.id)
            isGuestUser = userDetails.guestUser
            firstname = userDetails.firstName
            userInfo = [success: true, loggedIn:true,  username: authentication.name, firstname:firstname, isAdmin:isAdmin]
        }else{
            userInfo = [success:true, loggedIn: false]
        }

        render(userInfo as JSON)
    }

    /** Callback after a failed login. Redirects to the auth page with a warning message. */
    def authfail() {

        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = messageSource.getMessage('springSecurity.errors.login.expired', null, "Account Expired", request.locale)
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = messageSource.getMessage('springSecurity.errors.login.passwordExpired', null, "Password Expired", request.locale)
            }
            else if (exception instanceof DisabledException) {
                msg = messageSource.getMessage('springSecurity.errors.login.disabled', null, "Account Disabled", request.locale)
            }
            else if (exception instanceof LockedException) {
                msg = messageSource.getMessage('springSecurity.errors.login.locked', null, "Account Locked", request.locale)
            }
            else if (exception instanceof BadCredentialsException) {
                msg = messageSource.getMessage('springSecurity.incorrect.username.password', null, "Bad Credentials", request.locale)
            }
            else {
                msg = messageSource.getMessage('springSecurity.errors.login.fail', null, "Authentication Failure", request.locale)
            }
        }

//        if (springSecurityService.isAjax(request)) {
            render([success: false,  msg: msg] as JSON)
//        }
//        else {
//            flash.message = msg
//            redirect action: 'auth', params: params
//        }
    }



    //    @Secured("isAuthenticated()")
    @Secured('permitAll')
    def getUserInfo(){
        String firstname = ""
        Map userInfo =[:]
        def loginWithSpringSecurity = grailsApplication.config.loginWithSpringSecurity?true:false
        if(loginWithSpringSecurity){
            if (springSecurityService.isLoggedIn()) {
                userInfo = getUserDetails(principal)

            }else{
                userInfo = [success:false, loggedIn: false]
            }
        } else {
            userInfo = getUserData()
        }

        render( userInfo  as JSON)

    }

    private def getUserDetails(key){
        def list = null
        User userDetails
        Map userInfo = [:]
        boolean isOwner=false
        boolean isTenant=false
        boolean isPropManager=false
        boolean isServiceProvider=false
        boolean isAdmin=false
        boolean isGuestUser = false
        /*if(key instanceof OauthUser){
            list = key.userProfile.attributes
            userInfo = [success: true, loggedIn: true, authentication:"oauth",isTenant: isTenant, isOwner: isOwner,  "name": list.displayName, "email":list.emails[0].email, "imageUrl":list?.get("image.url").toString()]
        }else{*/
            userDetails = User.get(springSecurityService?.principal?.id)
            isAdmin=userDetails.admin
            isGuestUser = userDetails.guestUser
//            firstname = userDetails.firstName
//            userInfo = [success: true, loggedIn:true, isTenant: isTenant,  username: authentication.name, firstname:firstname, isOwner:isOwner, isGuestUser:isGuestUser, isTenant:isTenant, isPropManager:isPropManager, isServiceProvider:isServiceProvider, isAdmin:isAdmin]

        Person person = Person.findByUser(userDetails)
        userInfo = [success: true, loggedIn: true, name: getFullName(userDetails), personId: person.id, authentication: "DAO"]
//        }
        return userInfo
    }

    private String getFullName(details){
        String fullname = details.firstName + " " + details.lastName
        return fullname
    }

    @Secured('permitAll')
    def success() {
//        String provider = params.provider
        /*if (token) {
            Cookie cookie = jwtCookie(token)
            response.addCookie(cookie)
        }*/
        String grailsServerUrl = grailsApplication.config.grails.serverURL
        Map parameters =[grailsServerUrl: grailsServerUrl,"reloadPage": true, "success": true, "msg": " Authentication Successful"]
        render(view:'/auth/success', model:parameters)
    }

    def loginUser(){
        String username = params.username
        String password = params.password
        boolean loginWithSpringSecurity = grailsApplication.config.loginWithSpringSecurity?true:false
        if(loginWithSpringSecurity){
            redirect(action: "elintegrostartapp/login/authenticate", params:params)
        }

        Map response = authenticationService.authenticate(username, password)
        if(response.user){
            user = response.user
        }
        render response as JSON
    }

    def logoutUser(){

        def session = AuthenticationService.getSession()
        session.loggedIn = false
        session.userId = 1
    }

    def getUserData(){

        Map userInfo = [:]
        def session = AuthenticationService.getSession()
        if(session.userid){
            User userDetails = User.get((long)session.userid)
            boolean loggedIn = (boolean)session.loggedIn
            Person person = Person.findByUser(userDetails)
            userInfo = [success: true, loggedIn: loggedIn, name: getFullName(userDetails), personId:person.id, authentication: "DAO"]
        }else {
            userInfo = [success: false, loggedIn: false]

        }

        return userInfo
    }
    def sendVerificationCodeForLoginWithOTP(){
        def params = request.getJSON();
        def result
        User user1 = User.findByUsername(params.transits.emailOrPhone.value)
        if(user1) {
            Otp otpAlreadyExist = Otp.findByUser(user1)
            if (user1 && !otpAlreadyExist) {
                String verificationCode = new DecimalFormat("000000").format(new Random().nextInt(999999));
                Otp otp = new Otp()
                def encodedVerificationCode = springSecurityService.encodePassword(verificationCode)
                otp.verificationCode = encodedVerificationCode
                otp.createTime = new Date()
                otp.expireTime = new Date().plus(1)
                otp.user = user1
                otp.save()
                def conf = Holders.grailsApplication.config
                String emailBody = conf.loginController.emailForLoginWithOTP
                Map emailParams = [verificationCode: verificationCode]
                try {
                    emailService.sendMail(user1.email, emailParams, emailBody)
                    result = [success: true, msg: "We.sent.verification.code", alert_type: "success"]
                } catch (Exception e) {
                    log.error("Email sending failed" + e)
                    result = [success: false, msg: "Couldnot.send.mail", alert_type: "error"]
                    println("email sending failed" + e)
                }

            } else {
                result = [success: true, msg: "Otp.code.already.sent", alert_type: "info"]
            }
        }else {
            result = [success: false, msg: "We.dont.have.user.with.this.email", alert_type: "error"]
        }
        render result as JSON
    }
    def loginWithOTP(){
        def param = request.getJSON()
        println(param)
        User user1 = User.findByUsername(param.transits.emailOrPhone.value)
        Otp otp = Otp.findByUser(user1)
        TimeDuration duration = TimeCategory.minus(new Date(), otp.createTime)
        def result
        if(otp && duration.hours <= 24){
            try {
                springSecurityService.reauthenticate(user1.username,otp.verificationCode)
                otp.delete(flush:true)
                result = [success: true,msg: "Login.successful",alert_type: "success"]
            }catch(Exception e){
                log.error("Couldn't authenticate this user.")
                result = [success: false,msg: "Couldnot.authenticate.this.user",alert_type: "error"]
            }
        }
        else{
            result = [success: false,msg:"This.code.has.been.expired",alert_type: "error"]

        }
        render result as JSON
    }
}
