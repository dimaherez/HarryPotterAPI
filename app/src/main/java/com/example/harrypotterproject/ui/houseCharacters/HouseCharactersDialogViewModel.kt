package com.example.harrypotterproject.ui.houseCharacters

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.room.AppDatabase
import com.example.harrypotterproject.room.DatabaseRepo
import kotlinx.coroutines.launch

class HouseCharactersDialogViewModel(private val dbRepo: DatabaseRepo) : ViewModel() {
    val charactersList = MutableLiveData<List<CharacterModel>>()

    private val _selectedHouse = MutableLiveData<String>()

    fun selectHouse(houseName: String) {
        _selectedHouse.value = houseName
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            charactersList.postValue(
                dbRepo.getAllCharactersList()
                    .filter { it.house.lowercase() == _selectedHouse.value!!.lowercase() })
        }
    }
}

class HouseCharactersDialogViewModelFactory(
    private val context: Context,
    private val dbRepo: DatabaseRepo = DatabaseRepo(AppDatabase.getDatabase(context).characterDao())
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HouseCharactersDialogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HouseCharactersDialogViewModel(dbRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}