package com.elintegro.elintegrostartapp.hr

class Skills {
    String code
    String name
    String description
    static mapping = {description type: 'text'}

    static constraints = {
        code (nullable: false)
        name(nullable: false)
        description(nullable: false)
    }
}
