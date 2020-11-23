package com.elintegro.elintegrostartapp.client

import com.elintegro.crm.Person

class Lead {
    Person applicant
    String leadDescription
    String leadStage
    String leadBudget
    String nameOfProject
    String descriptionOfProject
    Date deadline


    static constraints = {
        applicant(nullable: true)
        nameOfProject(maxSize: 25)
        descriptionOfProject(length:4096)
    }
}
