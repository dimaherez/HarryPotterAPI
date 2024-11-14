package com.example.harrypotterproject.models

data class SpellModel(
    val id: String,
    val name: String,
    val description: String
) {
    override fun toString(): String {
        return """
            |Spell Details:
            |--------------
            |Name: $name
            |Description: $description
        """.trimMargin()
    }
}
