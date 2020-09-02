package com.elintegro.elintegrostartapp.hr

class QuestionTable {

    String questionKey
    String questionName

    static constraints = {
        questionKey(nullable:false)
        questionName(nullable: false)
    }

}
