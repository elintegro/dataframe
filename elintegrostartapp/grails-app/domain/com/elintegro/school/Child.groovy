package com.elintegro.school

class Child {

    String name

    static belongsTo = [parent: Parent]

    static constraints = {
    }
}