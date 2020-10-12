package com.elintegro.customerCare


import com.elintegro.gc.commonfield.ControlField

class ChatPerson extends ControlField{

    String chattingWith
    String createdAt
    String email
    String personId
    String nickname
    String photoUrl
    String aboutMe
    String role
    String pushToken

    static constraints = {
        personId unique: true
    }
}
