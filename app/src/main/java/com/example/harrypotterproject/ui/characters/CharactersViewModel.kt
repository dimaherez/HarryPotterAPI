package com.example.harrypotterproject.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.retrofit.HPRepository
import com.example.harrypotterproject.room.AppDatabase
import com.example.harrypotterproject.room.DatabaseRepo
import kotlinx.coroutines.launch

class CharactersViewModel(private val dbRepo: DatabaseRepo) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterModel>>()
    val characters: LiveData<List<CharacterModel>>
        get() = _characters


    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            // Characters from API
            val response = HPRepository.getCharacters()
            val responseBody = response.body()!!

            if (response.isSuccessful) {
                dbRepo.insertAll(responseBody)
                _characters.postValue(responseBody)
            }
        }
    }
}
