package com.example.harrypotterproject.enums

enum class House {
    GRYFFINDOR,
    RAVENCLAW,
    HUFFLEPUFF,
    SLYTHERIN
}

fun houseToString(house: House): String =
    when (house) {
        House.GRYFFINDOR -> "Gryffindor"
        House.SLYTHERIN -> "Slytherin"
        House.HUFFLEPUFF -> "Hufflepuff"
        House.RAVENCLAW -> "Ravenclaw"
    }
