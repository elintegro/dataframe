package com.elintegro.customerCare

import grails.converters.JSON

class CustomerCareController {

    def customerCareService

    def systemUser() {
        render(customerCareService.getSystemUsers() as JSON)
    }

    def chatUser(){
        def requestData = request.getJSON()
        Map returnData = [success:true]
        try {
            ChatPerson chatPerson = ChatPerson.findByPersonId(requestData.id)?:new ChatPerson(personId: requestData.id)
            bindData(chatPerson, requestData)
            customerCareService.saveData(chatPerson)
        }catch(e){
            returnData.success = false
        }
        render(returnData as JSON)
    }

    def chatMessage(){
        def requestData = request.getJSON()
        Map returnData = [success:true]
        try {
            ChatMessage message = new ChatMessage()
            bindData(message, requestData)
            customerCareService.saveData(message)
        }catch(e){
            returnData.success = false
        }
        render(returnData as JSON)
    }

    def updatePerson(){
        
    }
}
