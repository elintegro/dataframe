package com.elintegro.translator

class Text {
    Project project
    String language
    String _key
    String text

    static mapping = {
        text sqlType: 'longText'
    }

    static constraints = {
        text(size: 1..15000)
    }
}
