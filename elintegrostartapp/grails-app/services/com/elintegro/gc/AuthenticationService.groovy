/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.gc

import grails.gorm.transactions.Transactional
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.grails.web.util.WebUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.util.regex.Matcher
import java.util.regex.Pattern

@Transactional
@Slf4j
class AuthenticationService {

    def springSecurityService
//    private static final def config = Holders.grailsApplication.config
    public def config = Holders.grailsApplication.config
    def serviceMethod() {

    }

    Map authenticate(String username, String password){
        if(!validate(username, password)){
            log.debug("username validation failed")
            return [success:false, loggedIn: false, msg:"username validation failed"]
        }

        Map response = authenticateUser(username, password)
        return response
    }

    private boolean validate(username, password){
        def config = Holders.grailsApplication.config
        if(!username && !password){
            log.debug("Username or Password is missing")
            return false
        }
        boolean validated = false
        String emailRegex = config.regex.email
        Pattern pattern = Pattern.compile(emailRegex)
        Matcher matcher = pattern.matcher(username)

        if(matcher.matches()){
            validated = true
        }

        return validated
    }

    private def authenticateUser(String username, String password) {
        def userClass = config.grails.plugin.springsecurity.userLookup.userDomainClassName
        def authorityJoinClass = config.grails.plugin.springsecurity.userLookup.authorityJoinClassName
        def authorityClass = config.grails.plugin.springsecurity.authority.className
        def user = null
        Map response
        if(!userClass){
            throw new RuntimeException("Please provide User domain class name in config as config.grails.plugin.springsecurity.userLookup.userDomainClassName = <UserClassName>")
        }
        try {
            Class clazz = Holders.grailsApplication.getDomainClass((String)userClass).clazz
            user = clazz.findByUsername(username)
        } catch(ClassNotFoundException e){
            log.debug("The class with name: "+userClass+" may not exist")
        }
        if(!user){
            return [success:false, msg:"Username Not Found"]
        }

//        String hashedPassword = getEncodedPassword(password)

//        println "hashed pass: " + hashedPassword
        println "db pass: " + user.password
        if(password.equals(user.password)){
            println "authentication successful "
            setSessionParameters(user)
            response = [user:user, success: true, loggedIn: true, msg:"Login Successful"]
        } else {
            println "authentication unsuccessful"
            response = [success: false, loggedIn: false, msg:"Passwords dont match"]
        }

        return response
    }

    private void setSessionParameters(def user){
        def session = getSession()
        println(session)
        session.setAttribute('userid', user.id)
        session.setAttribute("loggedIn", true)
    }

    public static getSession(){
        return WebUtils.retrieveGrailsWebRequest().session
    }
    private String getHashedPassword(String password){
        String hashingAlgorithm = config.grails.plugin.springsecurity.password.algorithm?:"bcrypt"
        int logRound = config.grails.plugin.springsecurity.password."$hashingAlgorithm".logrounds?:10
        int hashIterations = config.grails.plugin.springsecurity.password.hash.iterations?:10000

        BCryptPasswordEncoder bbc = new BCryptPasswordEncoder(logRound)
        String encryptedPassword = bbc.encode(password)
        return encryptedPassword
    }

    private String getEncodedPassword(String password) {
        springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }
}
