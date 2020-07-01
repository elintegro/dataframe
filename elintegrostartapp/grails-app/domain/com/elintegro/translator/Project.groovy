package com.elintegro.translator

import com.elintegro.auth.User
import com.elintegro.ref.Language

class Project {
    String name
    Date startDate
    Date expirationDate
    String sourceLanguage

    static 	hasMany = [users: User, languages: Language]

    static constraints = {
    }
}