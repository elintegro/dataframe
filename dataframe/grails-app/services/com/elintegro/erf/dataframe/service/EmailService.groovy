/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.erf.dataframe.service

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.ui.RegisterController
import grails.plugin.springsecurity.ui.RegistrationCode
import grails.util.Holders
import groovy.text.SimpleTemplateEngine

@Transactional
class EmailService {

    def mailService
    def serviceMethod() {

    }

    def sendMail(email, emailParams, emailBody){
                def conf =Holders.grailsApplication.config
                if (emailBody.contains('$')) {
                    emailBody = evaluate(emailBody, emailParams)
                }
                mailService.sendMail {
                    async true
                    to email
                    from conf.grails.mail.username
                    subject conf.ui.register.emailSubject?:"Elintegro Inc"
                    html emailBody.toString()
                }
    }

    protected String evaluate(s, emailParams) {
        new SimpleTemplateEngine().createTemplate(s).make(emailParams)
    }
}
