package com.elintegro.onmange

import com.elintegro.auth.User
import com.elintegro.crm.Person

class Consumer {

    User user;
    Person person;

    static constraints = {
        person (nullable: false)
        user (nullable: true)
    }
}
