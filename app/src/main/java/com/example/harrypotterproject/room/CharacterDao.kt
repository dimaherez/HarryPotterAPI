package com.example.harrypotterproject.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.harrypotterproject.models.CharacterModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM CharacterModel")
    fun getAllCharactersLiveData(): LiveData<List<CharacterModel>>

    @Query("SELECT * FROM CharacterModel")
    suspend fun getAllCharactersList(): List<CharacterModel>  // Blocking call

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterModel>)

    @Update
    suspend fun updateCharacter(character: CharacterModel)
}



