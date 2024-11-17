package com.example.harrypotterproject.room

import com.example.harrypotterproject.models.CharacterModel
import kotlinx.coroutines.flow.Flow

class DatabaseRepo(private val characterDao: CharacterDao) {
    suspend fun getAllCharactersList(): List<CharacterModel> {
        return characterDao.getAllCharactersList()
    }

    fun getAllCharactersFlow(): Flow<List<CharacterModel>> {
        return characterDao.getAllCharactersFlow()
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

