package elintegroWebsite

class ContactUsMessageService {
    def emailService
    def mailService
    def sendingMessages() {
        // println("Job Run Inside Service")
        def contactDetails = ContactUs.findAllWhere(sendNo:0)
       // def contactDetails = ContactUs.list(sendNo:1, resend:true)(where vitrako condition rakhda kam garena)
        println(contactDetails.email)
        println("This is message from contact us")
//        emailService.sendMail(email, emailParams, emailBody) {
//        }
//        mailService.sendMail{
//            from "elintegro.himalaya@gmail.com"
//            to "pangenirabindra5@gmail.com"
//            subject "This is a test mail"
//            body "Hello, This is a test mail, how are you?"
//        }
//        println( "Message sent at "+new Date())

    }



}
