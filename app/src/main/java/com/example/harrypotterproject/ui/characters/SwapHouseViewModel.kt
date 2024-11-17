package com.example.harrypotterproject.ui.characters

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.harrypotterproject.enums.House
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.room.AppDatabase
import com.example.harrypotterproject.room.DatabaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SwapHouseViewModel(private val dbRepo: DatabaseRepo) : ViewModel() {

    fun changeHouse(character: CharacterModel, newHouse: House) {
        character.house = when (newHouse) {
            House.GRYFFINDOR -> "Gryffindor"
            House.SLYTHERIN -> "Slytherin"
            House.HUFFLEPUFF -> "Hufflepuff"
            House.RAVENCLAW -> "Ravenclaw"
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRepo.updateCharacter(character)
            }
        }
    }
}

class SwapHouseViewModelFactory(
    private val context: Context,
    private val dbRepo: DatabaseRepo = DatabaseRepo(AppDatabase.getDatabase(context).characterDao())
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SwapHouseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SwapHouseViewModel(dbRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}