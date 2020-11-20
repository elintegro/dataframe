package com.elintegro.elintegrostartapp.hr

class AnswerTable {

    QuestionTable question
    String answerKey

    static constraints = {
        question(nullable: false)
        answerKey(nullable: false)
    }
}
