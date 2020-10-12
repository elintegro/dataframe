package com.elintegro.customerCare

import com.elintegro.gc.commonfield.ControlField

class ChatMessage extends ControlField{

    String content
    String idFrom
    String idTo
    String timestamp
    Integer type

    static mapping = {
        content type: 'text'
    }
    static constraints = {
        idFrom nullable: false
        idTo nullable: false
    }
}
