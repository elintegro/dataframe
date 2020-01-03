package elintegroWebsite

class ContactUsMessageService {
    def sendingMessages() {
        // println("Job Run Inside Service")
        def contactDetails = ContactUs.list()
        println(contactDetails.name)
        //println("This is message from contact us")


    }

}
