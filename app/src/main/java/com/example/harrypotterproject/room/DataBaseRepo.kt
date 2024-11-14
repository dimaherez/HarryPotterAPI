package com.example.harrypotterproject.room

import androidx.lifecycle.LiveData

class DatabaseRepo(private val characterDao: CharacterDao) {

    val allCharacters: LiveData<List<CharacterEntity>> = characterDao.getAllCharacters()

    suspend fun getAllCharactersList(): List<CharacterEntity> {
        return characterDao.getAllCharactersList()
    }

    suspend fun insertAll(characters: List<CharacterEntity>) {
        characterDao.insertAll(characters)
    }
}

