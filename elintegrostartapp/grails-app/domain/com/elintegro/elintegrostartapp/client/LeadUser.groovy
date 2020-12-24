package com.elintegro.elintegrostartapp.client

class LeadUser {
    String name
    String email
    String mobileNumber
    boolean isForUpdate

    static constraints = {
        email nullable: false, blank: false
    }
}
