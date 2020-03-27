package com.elintegro.elintegrostartapp.hr

import com.elintegro.elintegrostartapp.client.Application

class Position {
    String _code
    String name
    String description
    Date validFrom
    Date expireAt
    static mapping = {description type:'text'}

    static constraints = {
        _code (nullable: false)
        name(nullable: false)
        description(nullable: false)
    }
}
