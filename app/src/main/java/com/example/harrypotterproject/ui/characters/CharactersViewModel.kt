package com.example.harrypotterproject.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.retrofit.HPRepository
import com.example.harrypotterproject.room.CharacterEntity
import com.example.harrypotterproject.room.DatabaseRepo
import com.example.harrypotterproject.room.toModel
import kotlinx.coroutines.launch

class CharactersViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {

    val allCharacters: LiveData<List<CharacterModel>> =
        databaseRepo.allCharacters.map { characterEntities -> characterEntities.map { it.toModel() } }

    fun refreshCharacters() {
        viewModelScope.launch {
            try {
                // Characters from API
                val response = HPRepository.getCharacters()
                val responseBody = response.body()!!

                if (response.isSuccessful) {

                    // Retrieve existing characters from the database
                    val localCharacters = databaseRepo.getAllCharactersList()

                    // Merge API characters with local characters
                    val mergedCharacters = responseBody.map { apiCharacter ->
                        val localCharacter = localCharacters.find { it.id == apiCharacter.id }
                        if (localCharacter != null) {
                            localCharacter.copy(
                                id = apiCharacter.id,
                                house = localCharacter.house,
                                knownSpells = localCharacter.knownSpells
                            )
                        } else {
                            CharacterEntity(
                                id = apiCharacter.id,
                                name = apiCharacter.name,
                                actor = apiCharacter.actor,
                                alive = apiCharacter.alive,
                                ancestry = apiCharacter.ancestry,
                                dateOfBirth = apiCharacter.dateOfBirth,
                                eyeColour = apiCharacter.eyeColour,
                                gender = apiCharacter.gender,
                                hairColour = apiCharacter.hairColour,
                                hogwartsStaff = apiCharacter.hogwartsStaff,
                                hogwartsStudent = apiCharacter.hogwartsStudent,
                                house = apiCharacter.house,
                                image = apiCharacter.image,
                                patronus = apiCharacter.patronus,
                                species = apiCharacter.species,
                                wandCore = apiCharacter.wand?.core ?: "",
                                wandLength = apiCharacter.wand?.length ?: 0.0,
                                wandWood = apiCharacter.wand?.wood ?: "",
                                wizard = apiCharacter.wizard,
                                yearOfBirth = apiCharacter.yearOfBirth,
                                knownSpells = listOf()
                            )
                        }
                    }

                    databaseRepo.insertAll(mergedCharacters)
                }
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }
}
