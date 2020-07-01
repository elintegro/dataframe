package com.elintegro.elintegrostartapp.hr

import com.elintegro.gc.commonfield.ControlField

class Images extends ControlField {
    String name
    String imageType
    Long imageSize


    static constraints = {
        name(nullable: false)
    }
}
