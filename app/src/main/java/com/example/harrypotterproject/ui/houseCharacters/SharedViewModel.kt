package com.example.harrypotterproject.ui.houseCharacters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.retrofit.HPRepository
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    val charactersList = MutableLiveData<List<CharacterModel>>()

    private val _selectedHouse = MutableLiveData<String>()
    val selectedHouse: LiveData<String> get() = _selectedHouse

    fun selectHouse(houseName: String) {
        _selectedHouse.value = houseName
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {

            val response = HPRepository.getCharacters()
            val responseBody = response.body()!!

            if (response.isSuccessful) {
                charactersList.postValue(
                    responseBody.filter {
                        it.house.lowercase() == selectedHouse.value!!.lowercase()
                    })
            }

        }

    }

}