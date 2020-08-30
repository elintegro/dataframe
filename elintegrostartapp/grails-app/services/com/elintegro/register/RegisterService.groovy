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
import com.elintegro.auth.UserRole
import com.elintegro.erf.dataframe.service.DataframeService
import com.elintegro.gerf.DataframeController
import com.elintegro.elintegrostartapp.Facility
import com.elintegro.model.DataframeResponse
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.ui.RegisterCommand
//import grails.plugin.springsecurity.ui.RegistrationCode
@Transactional
class RegisterService{

    def messageSource
    def mailService
    def springSecurityService
    DataframeService dataframeService

    def registerUser(request, RegisterCommand command, Role role, Facility facility) {
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
            DataframeResponse resultData = dataframeService.saveRaw(request)
            user  = resultData?.dataframeInstance?.savedDomainsMap?.user[1]
            //Map domainInstanceMap = resultData?.dfInstance.getSavedDomainsMap();
            //def savedDomainInstances = domainInstanceMap.values()

            //if (savedDomainInstances) {
                //user = getRegisterUserInstance(savedDomainInstances, user)
                UserRole.create(user, role)
                //if (facility) {
                //    facility.addToUsers(user)
                //}
            //}
        }

        return new grails.util.Pair(user, serviceMessage)
    }

/*    private static def getRegisterUserInstance(savedDomainInstances, user){
        String userDomainClassName = SpringSecurityUtils.securityConfig.userLookup.userDomainClassName
        Class clazz = Holders.grailsApplication.getDomainClass(userDomainClassName).clazz
        for (def instance:savedDomainInstances){
            if (instance.instanceOf(clazz)){
                user = instance
                break
            }
        }
        return user
    }*/
}
