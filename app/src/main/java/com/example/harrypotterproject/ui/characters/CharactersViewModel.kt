package com.example.harrypotterproject.ui.characters

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.retrofit.HPRepository
import com.example.harrypotterproject.room.AppDatabase
import com.example.harrypotterproject.room.DatabaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(private val dbRepo: DatabaseRepo) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterModel>>()
    val characters: LiveData<List<CharacterModel>> get() = _characters

    val spellsList = MutableLiveData<List<SpellModel>>()

    init {
        getCharacters()
        getSpells()
    }

    fun getCharacters() {
        viewModelScope.launch {
            val localData = dbRepo.getAllCharactersList()

            if (localData.isEmpty()) {
                val response = withContext(Dispatchers.IO) {
                    HPRepository.getCharacters()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    withContext(Dispatchers.IO) {
                        dbRepo.insertAll(responseBody)
                    }
                    _characters.postValue(dbRepo.getAllCharactersLiveData().value)
                } else {
                    // Handle error case
                    _characters.postValue(emptyList())
                }
            } else {
                _characters.postValue(localData)
            }
        }
    }

    private fun getSpells() {
        viewModelScope.launch {
            val response = HPRepository.getSpells()
            val responseBody = response.body()!!

            if (response.isSuccessful) {
                spellsList.postValue(responseBody)
            }
        }
    }

    fun teachSpells(characters: List<CharacterModel>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRepo.updateAll(characters)
            }
        }
    }
}


class CharactersViewModelFactory(
    private val context: Context,
    private val dbRepo: DatabaseRepo = DatabaseRepo(AppDatabase.getDatabase(context).characterDao())
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharactersViewModel(dbRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
