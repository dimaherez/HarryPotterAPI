package com.example.harrypotterproject.ui.spells

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.retrofit.HPRepository
import kotlinx.coroutines.launch

class SpellsViewModel : ViewModel() {

    val spellsList = MutableLiveData<List<SpellModel>>()

    init {
        getSpells()
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
}