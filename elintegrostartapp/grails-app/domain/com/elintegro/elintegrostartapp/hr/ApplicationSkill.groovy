package com.elintegro.elintegrostartapp.hr

import com.elintegro.elintegrostartapp.client.Application

class ApplicationSkill  {
    Application application
    String skill
    Short level /*1 to 5 */
    String comment

    static constraints = {
        application(nullable: false)
        skill(nullable: false,size:2..30)
        comment(nullable: true)
    }
}
