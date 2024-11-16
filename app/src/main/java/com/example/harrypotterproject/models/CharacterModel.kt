package com.example.harrypotterproject.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterModel(
    @PrimaryKey
    val id: String,

    val actor: String,
    val alive: Boolean,
    val alternate_actors: List<String>,
    val alternate_names: List<String>,
    val ancestry: String,
    @ColumnInfo(name = "dateOfBirth", defaultValue = "")
    val dateOfBirth: String?,
    val eyeColour: String,
    val gender: String,
    val hairColour: String,
    val hogwartsStaff: Boolean,
    val hogwartsStudent: Boolean,
    val house: String,
    val image: String,
    val name: String,
    val patronus: String,
    val species: String,
    val wand: Wand?,
    val wizard: Boolean,
    val yearOfBirth: Int,
    @ColumnInfo(name = "knownSpells", defaultValue = "")
    val knownSpells: List<SpellModel>?
) {
    data class Wand(
        val core: String?,
        val length: Double?,
        val wood: String?
    ) {
        override fun toString(): String {
            return "core=$core, length=$length, wood=$wood"
        }
    }

    override fun toString(): String {
        return """
            |Character Details:
            |------------------
            |  Name: $name
            |  Alternate Names: ${alternate_names.joinToString(", ")}
            |  House: $house
            |  Known Spells: ${knownSpells?.joinToString(", ") { it.name } ?: ""}
            |  Actor: $actor
            |  Alternate Actors: ${alternate_actors.joinToString(", ")}
            |  Alive: ${if (alive) "Yes" else "No"}
            |  Ancestry: $ancestry
            |  Date of Birth: $dateOfBirth
            |  Year of Birth: $yearOfBirth
            |  Gender: $gender
            |  Eye Colour: $eyeColour
            |  Hair Colour: $hairColour
            |  Hogwarts Staff: ${if (hogwartsStaff) "Yes" else "No"}
            |  Hogwarts Student: ${if (hogwartsStudent) "Yes" else "No"}
            |  Patronus: $patronus
            |  Species: $species
            |  Wizard: ${if (wizard) "Yes" else "No"}
            |  Wand Details: ${wand.toString()}
        """.trimMargin()
    }
}


