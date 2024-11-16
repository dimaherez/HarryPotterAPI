package com.example.harrypotterproject.room

import androidx.lifecycle.LiveData
import com.example.harrypotterproject.models.CharacterModel

class DatabaseRepo(private val characterDao: CharacterDao) {
    suspend fun getAllCharactersList(): List<CharacterModel> {
        return characterDao.getAllCharactersList()
    }

    suspend fun insertAll(characters: List<CharacterModel>) {
        characterDao.insertAll(characters)
    }
}

