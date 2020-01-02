package elintegroWebsite

class ContactUs {

    static constraints = {
        name(nullable:false)
        email(nullable:false)
        phone(nullable:true)
        textOfMessage(nullable:false)

    }
    String name
    String email
    String phone
    String textOfMessage
    Integer sendNo
    boolean resend = false
}
