package com.elintegro.gc

import com.elintegro.auth.Role
import com.elintegro.auth.User
import com.elintegro.auth.UserRole
import com.elintegro.erf.dataframe.Dataframe
import com.elintegro.erf.dataframe.DataframeInstance
import com.elintegro.erf.dataframe.vue.DataframeVue
import com.elintegro.gerf.DataframeController
import com.elintegro.register.RegisterController
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.ui.RegistrationCode
import grails.util.Holders
import org.springframework.context.i18n.LocaleContextHolder

@Transactional
class DataPersistenceService {

    def messageSource
    def emailService
    def grailsLinkGenerator
    def facilityService
    def serviceMethod() {

    }

    def save(Map _params,  boolean doCommit = true){

        DataframeController dc = new DataframeController()
        String prmsPrintOut = DataframeInstance.reqParamPrintout(_params);

        log.debug("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");
        System.out.println("\n *******   Request Params when Saving : \n" + prmsPrintOut + "\n ***************\n");

        Dataframe dataframe = dc.getDataframe(_params)
        def dfInstance = new DataframeInstance(dataframe, _params)
        def operation = 'U'; //Update
        boolean isInsert;

        def result = dfInstance.save(doCommit);

        if(!doCommit){
            dfInstance.commit()
        }

        if(dfInstance.isInsertOccured()){
            operation = "I";
        }

        Map returnedMap = formatResult(_params, dfInstance, dataframe as DataframeVue, result)

        return returnedMap
    }

    public static Map formatResult(def _params, DataframeInstance dfInstance, DataframeVue dataframe, def result){

        Map savedResultMap = dfInstance.getSavedDomainsMap();

        Map<String, Map> resultAlias = [:]
        savedResultMap.each { domainAlias, domainInstance ->
            Map record = [:];
            def properties = getAllProperties(domainInstance)
            def className = domainInstance.metaClass.theClass.fields
            properties.each { fieldName, value ->
                String fldName = dataframe.buildKeyFieldParam(dataframe.dataframeName, String.valueOf(domainAlias), String.valueOf(fieldName))
                if(fldName && _params.containsKey(fldName)){
                    record.put(fldName, value);
                }
            }
            resultAlias.put(dataframe.dataframeName, record)
        }

        def generatedKeys = []
        result.each{ record->
            def _id = record.id
            generatedKeys.add record.id
        }
        _params.remove("controller")
        _params.remove("action")
        return [_params: _params, generatedKeys:generatedKeys, resultAlias:resultAlias, result: result]
    }
    public static Map getAllProperties(def instance){
        return instance.class.declaredFields.findAll { !it.synthetic }.collectEntries {
            [ (it.name):instance."$it.name" ]
        }
    }
    def createUser(Map userParam, Long roleId, boolean sendEmailWithPassword){

        String email = userParam["email"]
        if(!email){
            String message = GcUtil.getInternationalizedMessage("email.missing", null, "")
            return [success: false, user:null, msg: message]
        }
        assert roleId
        String firstName = userParam["firstName"]?:""
        String lastName = userParam["lastName"]?:""
        boolean enabled = userParam.containsKey("enabled")?userParam["enabled"]:true
        boolean accountLocked = userParam.containsKey("accountLocked")?userParam["accountLocked"]:false
        User user = User.findByUsername(email)
        Map response = [:]
        if(!user){
            user = new User()
            user.username = email
            user.email = email
            user.firstName = firstName
            user.lastName = lastName
            String password = generatePassword(firstName)
            user.password = email
            user.enabled = enabled
            user.accountLocked = accountLocked

            if(user.save(flush:true)){
                Role role1 = Role.findById(roleId).save()
                if(role1 == null){
                    log.debug("The provided role was not found. Role was not set")
                }
                new UserRole(user: user, role: role1).save(flush: true)
                if(sendEmailWithPassword){
                    try{
//                            sendVerificationEmail(user, email, password)
                        String message = GcUtil.getInternationalizedMessage("user.creation.success", null, "")
                        response =[success: true, user:user, msg: message]

                    } catch (Exception e){
                        String message = GcUtil.getInternationalizedMessage("email.verification.error", null, "")
                        response = [success: true, user: null, msg: message]
                    }
                }
            }
        }else{
            String message = GcUtil.getInternationalizedMessage("user.already.exist", null, "")
            response = [success: true, user:user, msg:message]
        }

        return response
    }

    def sendVerificationEmail(User user, String sendToEmail, String password){
        Map resultData
        String verificationEmailMessage =""
        RegisterController rc = new RegisterController()
        RegistrationCode registrationCode = rc.registrationCode(user)
        String url = grailsLinkGenerator.link(controller: 'register', action: 'verifyRegistration', params:[t: registrationCode.token], absolute: true)
        def conf =Holders.grailsApplication.config
        String emailBody = conf.emailService.emailWithPassword
        Map emailParams = [user: user, url: url, password:password]
        try{
            emailService.sendMail sendToEmail ,emailParams, emailBody
            verificationEmailMessage = messageSource.getMessage('registration.mail.success',null,LocaleContextHolder.getLocale())
            resultData = ['msg': verificationEmailMessage, 'success': true]
        }catch (Exception e){
            log.debug("Verification Email with password could not be sent to Applicant: " + user.email)
            throw e
        }
        return resultData
    }

    String generatePassword(String fname){
        String firstname = fname?:""
        int randNum = (int)(Math.random()*9000)+1000
        String randomNumString = String.valueOf(randNum)
        return firstname+randomNumString
    }

    private def rollbackTransaction(List toDeleteLists){
        toDeleteLists.each {
            if(it){
                it.delete(flush: true)
            }
        }

    }
}
