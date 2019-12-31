package elintegroWebsite

import grails.converters.JSON

class ElintegroWebsiteController {

    def index() {
        println("hello")
    }
//    def technologiesButton()
//    {
//        render(view: "/elintegroWebsite/technologies")
//    }
//
//    def renderUrlData(){
//        render([success:true, url:"/elintegroWebsite/technologiesButton"] as JSON)
//    }
    def ContactUs(){
        println("entry point to contact us")
        println(params)
        println(params.id)
        println(params.get("vueContactUsPageDataframe-name"))
        println(params.get("vueContactUsPageDataframe-email"))
        println(params.get("vueContactUsPageDataframe-phone"))
        println(params.get("vueContactUsPageDataframe-textOfMessage"))
        ContactUs contactUs = new ContactUs()
        contactUs.name = params.get("vueContactUsPageDataframe-name")
        contactUs.email = params.get("vueContactUsPageDataframe-email")
        contactUs.phone = params.get("vueContactUsPageDataframe-phone")
        contactUs.textOfMessage = params.get("vueContactUsPageDataframe-textOfMessage")
        contactUs.save()
        println("hello simon")

    }
}
