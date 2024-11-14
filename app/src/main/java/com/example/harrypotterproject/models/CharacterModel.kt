package com.example.harrypotterproject.models

data class CharacterModel(
    val actor: String,
    val alive: Boolean,
    val alternate_actors: List<Any>?,
    val alternate_names: List<String>?,
    val ancestry: String,
    val dateOfBirth: String,
    val eyeColour: String,
    val gender: String,
    val hairColour: String,
    val hogwartsStaff: Boolean,
    val hogwartsStudent: Boolean,
    val house: String,
    val id: String,
    val image: String,
    val name: String,
    val patronus: String,
    val species: String,
    val wand: Wand?,
    val wizard: Boolean,
    val yearOfBirth: Int,
    val knownSpells: List<SpellModel>?
) {
    data class Wand(
        val core: String?,
        val length: Double?,
        val wood: String?
    )

    override fun toString(): String {
        return """
            |Character Details:
            |------------------
            |Name: $name
            |Known Spells: ${knownSpells?.joinToString(", ") { it.name } ?: "None"}
            |Actor: $actor
            |Alive: ${if (alive) "Yes" else "No"}
            |Ancestry: $ancestry
            |Date of Birth: $dateOfBirth
            |Eye Colour: $eyeColour
            |Gender: $gender
            |Hair Colour: $hairColour
            |Hogwarts Staff: ${if (hogwartsStaff) "Yes" else "No"}
            |Hogwarts Student: ${if (hogwartsStudent) "Yes" else "No"}
            |House: $house
            |Patronus: $patronus
            |Species: $species
            |Wizard: ${if (wizard) "Yes" else "No"}
            |Year of Birth: $yearOfBirth
            |
            |Wand Details:
            |-------------
            |Core: ${wand?.core ?: "Unknown"}
            |Length: ${wand?.length ?: "Unknown"}
            |Wood: ${wand?.wood ?: "Unknown"}
            |
            |Alternate Names: ${alternate_names?.joinToString(", ") ?: "None"}
            |Alternate Actors: ${alternate_actors?.joinToString(", ") ?: "None"}
            |ID: $id
            |Image URL: $image
        """.trimMargin()
    }
}


