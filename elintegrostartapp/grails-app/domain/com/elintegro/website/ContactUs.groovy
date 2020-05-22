package com.elintegro.website

class ContactUs {
    String name
    String email
    String phone
    String textOfMessage
    Integer sendNo=0
    boolean resend = true

    static constraints = {
        name(nullable:false)
        email(nullable:false)
        phone(nullable:true,size: 10..15)
        textOfMessage(nullable:false,length:4096)

    }
}
