package com.example.harrypotterproject.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val actor: String,
    val alive: Boolean,
    val ancestry: String,
    val dateOfBirth: String?,
    val eyeColour: String,
    val gender: String,
    val hairColour: String,
    val hogwartsStaff: Boolean,
    val hogwartsStudent: Boolean,
    val house: String,
    val image: String,
    val patronus: String,
    val species: String,
    val wandCore: String,
    val wandLength: Double,
    val wandWood: String,
    val wizard: Boolean,
    val yearOfBirth: Int,
    val knownSpells: List<SpellModel>
)

fun CharacterEntity.toModel(): CharacterModel {
    return CharacterModel(
        actor = this.actor,
        alive = this.alive,
        alternate_actors = listOf(), // Assuming alternate_actors is not used in this example
        alternate_names = listOf(), // Assuming alternate_names is not used in this example
        ancestry = this.ancestry,
        dateOfBirth = this.dateOfBirth.toString(),
        eyeColour = this.eyeColour,
        gender = this.gender,
        hairColour = this.hairColour,
        hogwartsStaff = this.hogwartsStaff,
        hogwartsStudent = this.hogwartsStudent,
        house = this.house,
        id = this.id,
        image = this.image,
        name = this.name,
        patronus = this.patronus,
        species = this.species,
        wand = CharacterModel.Wand(core = this.wandCore, length = this.wandLength, wood = this.wandWood),
        wizard = this.wizard,
        yearOfBirth = this.yearOfBirth,
        knownSpells = this.knownSpells // Assuming the structure is compatible
    )
}



