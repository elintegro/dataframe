package elintegroWebsite

class contactUsMessageJob {
    def contactUsMessageService
    static triggers = {
        simple repeatInterval: 20000
    }

    def execute() {
      //  println("Job Run")
        contactUsMessageService.sendingMessages()

    }
}
