/* Elintegro Dataframe is a framework designed to accelerate the process of full-stack application development. 
We invite you to join the community of developers making it even more powerful!
For more information please visit  https://www.elintegro.com

Copyright © 2007-2019  Elinegro Inc. Eugene Lipkovich, Shai Lipkovich

This program is under the terms of the GNU General Public License as published by the Free Software Foundation, version 3.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
You are not required to accept this License, since you have not signed it. However, nothing else grants you permission to modify or distribute the Program or its derivative works. 
These actions are prohibited by law if you do not accept this License. Therefore, by modifying or distributing the Program or any work based on the Program, you indicate your acceptance of this License to do so, and all its terms and conditions for copying, distributing or modifying the Program or works based on it. */


package com.elintegro.elintegrostartapp

import com.elintegro.auth.Role
import com.elintegro.crm.Person
import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.gerf.DataframeController
import com.elintegro.elintegrostartapp.hr.Employee
import com.elintegro.elintegrostartapp.supplyChain.Vendor
import grails.converters.JSON

class UserManagementFormController {

    def userManagementFormService
    def facilityService

    def saveEmployee(params){
        def _params = params
        Map resultData
        String personId = params["personId"]
        String facilityId = params["facilityId"]
        String roleId = params["vueEmployeeAddDataframe-employee-role"]
        assert(personId)
//        assert facilityId
        _params["vueEmployeeAddDataframe-employee-person"] = personId
//        _params["vueEmployeeAddDataframe-employee-facility"] = facilityId
//            params["key-vueContactDataframe-person-id-id"] = person.id
        Map returnedData = userManagementFormService.save(_params, session, Employee.class, false)

        if(returnedData.resultAlias) {
            Person person = Person.get(Long.valueOf(personId))
            String message = ""
            if(person){
                Map userCreationResponse = userManagementFormService.createUser([email:person.email, firstName:person.firstName, lastName: person.lastName],Long.valueOf(roleId), true)
                if(userCreationResponse.user){
                    Person person1 = Person.findByContactEmail(person.email)
                    person1.user = userCreationResponse.user
                    person1.save()
//                  Facility facility = Facility.get(Long.valueOf(facilityId))
//                  facility.addToUsers(userCreationResponse.user)
//                  facility.save(flush: true)
                }
                message = 'Data saved successfully. ' + userCreationResponse.msg?:""
            }
            resultData = ['success': true, 'msg': message, nodeId: returnedData.generatedKeys, newData:returnedData.resultAlias, params:returnedData._params]
        }else
            resultData = ['msg': 'Data you have provided for save is not valid.', 'success': false]


        render resultData as JSON
    }
    def saveProvider(params){
        def _params = params
        Map resultData
        String personId = params["personId"]
//        String facilityId = params["facilityId"]
        assert(personId)
//        assert facilityId
        _params["vueProviderAddDataframe-provider-person"] = personId
        Long roleId = Role.findByAuthority("ROLE_SERVICE_PROVIDER").id
//        _params["vueEmployeeAddDataframe-employee-facility"] = facilityId
//            params["key-vueContactDataframe-person-id-id"] = person.id
        Map returnedData = userManagementFormService.save(_params, session, Provider.class, false)

        if(returnedData.resultAlias) {
            Person person = Person.get(Long.valueOf(personId))
            String message = ""
            if(person){
                Map userCreationResponse = userManagementFormService.createUser([email:person.email, firstName:person.firstName, lastName: person.lastName], roleId, true)
                if(userCreationResponse.user){
                    Person person1 = Person.findByContactEmail(person.email)
                    person1.user = userCreationResponse.user
                    person1.save()
//                  Facility facility = Facility.get(Long.valueOf(facilityId))
//                  facility.addToUsers(userCreationResponse.user)
//                  facility.save(flush: true)
                }
                message = 'Data saved successfully. ' + userCreationResponse.msg?:""
            }
            resultData = ['success': true, 'msg': message, nodeId: returnedData.generatedKeys, newData:returnedData.resultAlias, params:returnedData._params]
        }else
            resultData = ['msg': 'Data you have provided for save is not valid.', 'success': false]


        render resultData as JSON
    }
    def saveVendor(params){
        def _params = params
        Map resultData
        String personId = params["personId"]
//        String facilityId = params["facilityId"]
        Long roleId = Role.findByAuthority("ROLE_VENDOR").id
        assert(personId)
//        assert facilityId
        _params["vueVendorAddDataframe-vendor-person"] = personId
//        _params["vueEmployeeAddDataframe-employee-facility"] = facilityId
//            params["key-vueContactDataframe-person-id-id"] = person.id
        Map returnedData = userManagementFormService.save(_params, session, Vendor.class, false)

        if(returnedData.resultAlias) {
            Person person = Person.get(Long.valueOf(personId))
            String message = ""
            if(person){
                Map userCreationResponse = userManagementFormService.createUser([email:person.email, firstName:person.firstName, lastName: person.lastName], roleId, true)
                if(userCreationResponse.user){
                    Person person1 = Person.findByContactEmail(person.email)
                    person1.user = userCreationResponse.user
                    person1.save()
//                  Facility facility = Facility.get(Long.valueOf(facilityId))
//                  facility.addToUsers(userCreationResponse.user)
//                  facility.save(flush: true)
                }
                message = 'Data saved successfully. ' + userCreationResponse.msg?:""
            }
            resultData = ['success': true, 'msg': message, nodeId: returnedData.generatedKeys, newData:returnedData.resultAlias, params:returnedData._params]
        }else
            resultData = ['msg': 'Data you have provided for save is not valid.', 'success': false]


        render resultData as JSON
    }

    def saveContact(){
        Map responseData = saveContactRaw()
        render responseData as JSON
    }
    def saveContactRaw(){
        def _params = params
        def personId = params["personId"]

        String applicantEmail = params["email"]
        Person person = Person.findByContactEmail(applicantEmail)
        if(person){
            return [success: false, msg:"The email is already registered. Please use another email."]
        }
        Map returnedMap = userManagementFormService.save(_params, session)

        Map resultData = ['success': true, 'msg': "Contact Information save. email has been sent for verification", generatedKeys:returnedMap.generatedKeys, nodeId: returnedMap.generatedKeys, newData:returnedMap.resultAlias, params:returnedMap._params]
        return resultData
    }

    def saveAddress(){
        def _params = params
        def personId = params["personId"]
        Map returnedMap = userManagementFormService.save(_params, session)
        Map responseData
        if(returnedMap){
            def addressId = returnedMap.result.getAt(0)
            if(personId){
                Person person = Person.get(Long.valueOf(personId))
                if(person){
                    person.mainAddress = addressId
                    try{
                        person.save(flush: true)
                        responseData = [success: true, msg:"Form successfully saved", generatedKeys:returnedMap.generatedKeys, nodeId: returnedMap.generatedKeys]
                    }catch(Throwable ee){
                        log.error("Error saving " + person.toString() + "for params: " + params)
                        responseData = [success: false, msg: "Couldn't save the form. Please retry"]
                        userManagementFormService.rollbackTransaction([person, addressId])
                    }
                }
            } else {
                log.debug("Person Id not available for save.")
                userManagementFormService.rollbackTransaction([addressId])
                responseData = [success: false, msg:"There was an error saving Form. Please try again."]
            }

        }else {
            responseData = [success: false, msg:"There was an error saving Form. Please try again."]
        }

        render responseData as JSON
    }


}
