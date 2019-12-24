package elintegroWebsite

import grails.converters.JSON

class ElintegroWebsiteController {

    def index() { }
    def technologiesButton()
    {
        println("Hello simon")
        render(view: "/elintegroWebsite/technologies")
    }

    def renderUrlData(){
        render([success:true, url:"/elintegroWebsite/technologiesButton"] as JSON)
    }

}
