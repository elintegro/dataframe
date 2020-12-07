package com.elintegro.school

class Parent {

    String name

    static hasMany = [children: Child]

    static constraints = {
    }
}
