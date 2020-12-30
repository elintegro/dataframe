package com.elintegro.onmange

class Season {

    String seasonName
    String seasonDescription
    Date startSeason
    Date endSeason

    static constraints = {
        seasonName (nullable: false)
        seasonDescription (nullable: false)
        startSeason (nullable: false)
        endSeason (nullable: false)
    }
}
