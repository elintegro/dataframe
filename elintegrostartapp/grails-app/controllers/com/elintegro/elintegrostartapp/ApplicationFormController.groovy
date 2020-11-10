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
import com.elintegro.auth.User
import com.elintegro.auth.UserRole
import com.elintegro.crm.Person
import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.gerf.DataframeController
import com.elintegro.elintegrostartapp.client.Application
import com.elintegro.elintegrostartapp.client.Client
import com.elintegro.elintegrostartapp.client.MedicalRecord
import com.elintegro.elintegrostartapp.hr.Employee
import com.elintegro.elintegrostartapp.property.Property
import com.elintegro.elintegrostartapp.ref.ApplicationStatus
import com.elintegro.elintegrostartapp.ref.FacilityType
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import jdk.nashorn.api.scripting.JSObject

import java.awt.print.Book

class ApplicationFormController {
    def applicationFormService
    def springSecurityService
    def facilityService
    def index() { }

    def save(){
        def result = saveRaw(params)
        render result as JSON
    }

    def saveRaw(params){
        def _params = params
        String applicantEmail = params["vueContactDataframe-person-email"]
        String firstName = params["vueContactDataframe-person-firstName"]
        String lastName = params["vueContactDataframe-person-lastName"]
        assert applicantEmail
        params["vueApplicationFormDataframe-app-status"] = String.valueOf(ApplicationStatus.findByCode("APPLIED").id)
        Person person = Person.findByContactEmail(applicantEmail)
        if(person){
            params["vueContactDataframe-person-id"] = person.id
            params["key-vueContactDataframe-person-id-id"] = person.id
            Application app = Application.findByApplicant(person)
            if(app){
                params["vueApplicationFormDataframe-app-id"] = app.id
                params["key-vueApplicationFormDataframe-app-id-id"] = app.id
            }
        }

        DataframeController dc = new DataframeController()
        String prmsPrintOut = DataframeInstance.reqParamPrintout(_params);

        log.debug("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");

        Dataframe dataframe = dc.getDataframe(_params)

        def dfInstance = new DataframeInstance(dataframe, _params)
        def operation = 'U'; //Update
        boolean isInsert;

        //This is a way to populate fields others then those in HQL:
        List result = dfInstance.save(false); //Call Save with parameter false (not committed save)

        //Do changes in the domain Instances:
        facilityService.populateFacility(result, session, Application.class)

        //Now commit:
        dfInstance.commit()

        if(dfInstance.isInsertOccured()){
            operation = "I";
        }

        def resultData
        Map returnedMap = dc.formatResult(params, dfInstance, dataframe, result)
        if(result) {
            Map userCreationResponse = applicationFormService.createUser([email:applicantEmail, firstName:firstName, lastName: lastName], true)
            if(userCreationResponse.user){
                Person person1 = Person.findByContactEmail(applicantEmail)
                person1.user = userCreationResponse.user
                Application app = Application.findByApplicant(person1)
                app.user = userCreationResponse.user
                person1.save()
                app.save(flush:true)
            }
            String message = 'Data saved successfully. ' + userCreationResponse.msg?:""
            resultData = ['success': true, 'msg': message, generatedKeys:returnedMap.generatedKeys, nodeId: returnedMap.generatedKeys, newData:returnedMap.resultAlias, params:returnedMap._params]
        }else
            resultData = ['msg': 'Data you have provided for save is not valid.', 'success': false]

        return resultData
    }

    def saveAddress(){
        def _params = request.getJSON()
        def personId = _params.personId
        DataframeController dc = new DataframeController()
        Dataframe dataframe = dc.getDataframe(_params)
        def dfInstance = new DataframeInstance(dataframe, _params)
        def result = dfInstance.save();
        Map responseData
        if(result){
            def addressId = result[0]
            if(personId){
                Person person = Person.get(Long.valueOf(personId))
                if(person){
                    person.mainAddress = addressId
                    try{
                        person.save(flush: true)
                        def generatedKeys = []
                        result.each{ record->
                            def _id = record.id
                            generatedKeys.add record.id
                        }
                        _params.remove("controller")
                        _params.remove("action")

                        responseData = [success: true, msg:"Form successfully saved", generatedKeys:generatedKeys, nodeId: generatedKeys]
                    }catch(Throwable ee){
                        log.error("Error saving " + person.toString() + "for params: " + params)
                        responseData = [success: false, msg: "Couldn't save the form. Please retry"]
                        rollbackTransaction([person, addressId])
                    }
                }
            } else {
                log.debug("Person Id not available for save.")
                rollbackTransaction([addressId])
                responseData = [success: false, msg:"There was an error saving Form. Please try again."]
            }

        }else {
            responseData = [success: false, msg:"There was an error saving Form. Please try again."]
        }

        render responseData as JSON
    }

    public def saveMedications(){
        def _params = params
        def medicalRecordId = params["medicalRecordId"]
        DataframeController dc = new DataframeController()
        Dataframe dataframe = dc.getDataframe(_params)
        def dfInstance = new DataframeInstance(dataframe, _params)
        def result = dfInstance.save();
        Map responseData
        if(result){
            def medicationObj = result[0]
            if(medicalRecordId){
                MedicalRecord medicalRecord = MedicalRecord.get(Long.valueOf(medicalRecordId))
                if(medicalRecord){
                    medicalRecord.addToMedications(medicationObj)
                    try{
                        medicalRecord.save(flush: true)
                        def operation = 'U';
                        if(dfInstance.isInsertOccured()){
                            operation = "I";
                        }
                        Map returnedMap = dc.formatResult(params, dfInstance, dataframe, result)

                        responseData = [success: true, msg:"Form successfully saved",newData:returnedMap.resultAlias, operation:operation, generatedKeys:returnedMap.generatedKeys, nodeId: returnedMap.generatedKeys]
                    }catch(Throwable ee){
                        log.error("Error saving " + medicalRecord.toString() + "for params: " + params)
                        responseData = [success: false, msg: "Couldn't save the form. Please retry"]
                        rollbackTransaction([medicalRecord, medicationObj])
                    }
                }
            } else {
                log.debug("Person Id not available for save.")
                rollbackTransaction([medicationObj])
                responseData = [success: false, msg:"There was an error saving Form. Please try again."]
            }

        }else {
            responseData = [success: false, msg:"There was an error saving Form. Please try again."]
        }

        render responseData as JSON
    }

    public def saveMedicalRecord(){
        def _params = params
        _params.remove("ref-vueMedicalRecordDataframe-record-person")

        def applicationId = params["applicationId"]
        DataframeController dc = new DataframeController()
        String prmsPrintOut = DataframeInstance.reqParamPrintout(_params);

        log.debug("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");
        System.out.println("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");

        Dataframe dataframe = dc.getDataframe(_params)

        def dfInstance = new DataframeInstance(dataframe, _params)
        def operation = 'U'
        def result;
        result = dfInstance.save();
        if(dfInstance.isInsertOccured()){
            operation = "I";
        }

        def resultData
        if(result) {
            def medicalRecordObj = result[0]
            if(applicationId){
                Application application = Application.get(Long.valueOf(applicationId))
                if(application){
                    application.medicalRecord = medicalRecordObj
                    application.save(flush: true)
                }
            }
            Map returnedMap = dc.formatResult(params, dfInstance, dataframe, result)
            resultData = ['success': true, 'msg': "Medical Record Saved", generatedKeys:returnedMap.generatedKeys, nodeId: returnedMap.generatedKeys, newData:returnedMap.resultAlias, params:returnedMap._params]
        }else
            resultData = ['msg': 'Data you have provided for save is not valid.', 'success': false]

        render resultData as JSON
    }

    private def rollbackTransaction(List toDeleteLists){
        toDeleteLists.each {
            if(it){
                it.delete(flush: true)
            }
        }

    }

    def makeClient(){

        String key = params["vueApplicationFormEditDataframe-app-id"]
        String facilityId = params["facilityId"]
        assert key
        assert facilityId
        ApplicationStatus status = ApplicationStatus.findByCode("ACCEPTED")
        Application application = Application.get(Long.valueOf(key))
        Map resultData= [:]
        if(application){
            application.status = status
             boolean clientCreated = createClient(application, Long.valueOf(facilityId))

            if(clientCreated && application.save(flush: true))
            resultData = ['success': true, 'msg': "Client Status Updated to ACCEPTED"]
        } else {
            resultData = ['success': false, 'msg': "Could not find Application"]
        }

        render resultData as JSON
    }

    @Transactional
    private boolean createClient(Application application, Long facilityId){
        def person = application.applicant
        def user = application.user
        Facility facility = Facility.get(facilityId)
        Client client = new Client()
        client.application = application
        client.person = person
        client.user = user
        client.facility = facility
        if(client.save(flush: true)){
            UserRole userRole = UserRole.findByUser(user)
            userRole.role = Role.findByAuthority("ROLE_CLIENT")
            userRole.save(flush:true)
            return true
        }
        return false
    }

    def saveEvent(){
        def _params = params

        Employee employee = new Employee().save(flush:true) // todo change after Employee creation is completed
        params["vueRecordEventDataframe-event-reportingEmployee"] = String.valueOf(employee.id)
        DataframeController dc = new DataframeController()
        String prmsPrintOut = DataframeInstance.reqParamPrintout(_params);

        log.debug("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");
        System.out.println("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");

        Dataframe dataframe = dc.getDataframe(_params)

        def dfInstance = new DataframeInstance(dataframe, _params)
        def operation = 'U'
        def result;
        result = dfInstance.save();
        if(dfInstance.isInsertOccured()){
            operation = "I";
        }

        def resultData
        if(result) {
            Map returnedMap = dc.formatResult(params, dfInstance, dataframe, result)
            resultData = ['success': true, 'msg': "Event Created", generatedKeys:returnedMap.generatedKeys, nodeId: returnedMap.generatedKeys, newData:returnedMap.resultAlias, params:returnedMap._params]
        }else
            resultData = ['msg': 'Data you have provided for save is not valid.', 'success': false]

        render resultData as JSON
    }



    def ajaxExpire(){

        Map response = [:]
        if(!params.dataframe){
            println "Dataframe name missing"
            log.debug("Dataframe should be provided")
        }
        DataframeController dc = new DataframeController()
        params.dataframe = params.parentDataframe
        String key = params["id"]
        String fldName = params["fieldName"]
        fldName = buildFieldName(fldName)
        Dataframe dataframe = dc.getDataframe(params)
        if(dataframe && fldName && key){
            Map dataMap = dataframe.fields.dataMap
            String hql = !dataMap.isEmpty()?dataMap.get(fldName).hql:""
            parseHql(hql, key)
        }
        def dfInstance = new DataframeInstance(dataframe, params)

        def result = dfInstance.deleteExpire()

        def resultData
        def parentid = params.parentId
        def currentLevel =  dataframe.getFieldValueFromParametersAndName(params, "level")

        def ids = []
        ids.add params.parentNode

        resultData = ['success': true,'level':currentLevel,'nodeId':result, 'key':params.parentNode, 'deletedId':params.id,'parentNodeId':params.parentNodeId, 'parentLevel':params.parentLevel, 'parentFieldName':params.parentFieldName,  operation:'E']
        def converter = resultData as JSON
        converter.render(response)
        return false
    }

   String buildFieldName(String fldName){
        if(!fldName){
            return ""
        }

        return fldName.replace("_",".")
    }

    String parseHql(hql, String key){
        if(!hql){
            return ""
        }
        hql = hql.trim()
        if(hql.contains("where")){
            String namedParam = hql.substring(hql.indexOf(":"), hql.length)
            hql = hql.replace(namedParam, key)
        } else {
         hql = hql + " where "
        }

        return hql
    }
}
