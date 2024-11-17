package com.example.harrypotterproject.room

import androidx.lifecycle.LiveData
import com.example.harrypotterproject.models.CharacterModel
import kotlinx.coroutines.flow.Flow

class DatabaseRepo(private val characterDao: CharacterDao) {
    suspend fun getAllCharactersList(): List<CharacterModel> {
        return characterDao.getAllCharactersList()
    }

    fun getAllCharactersLiveData(): LiveData<List<CharacterModel>> {
        return characterDao.getAllCharactersLiveData()
    }

    suspend fun insertAll(characters: List<CharacterModel>) {
        characterDao.insertAll(characters)
    }

    suspend fun updateAll(characters: List<CharacterModel>) {
        characters.forEach {
            characterDao.updateCharacter(it)
        }
    }

    suspend fun updateCharacter(character: CharacterModel) {
        characterDao.updateCharacter(character)
    }
}

