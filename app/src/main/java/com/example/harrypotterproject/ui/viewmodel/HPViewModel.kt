package com.example.harrypotterproject.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.harrypotterproject.enums.House
import com.example.harrypotterproject.enums.houseToString
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.retrofit.HPRepository
import com.example.harrypotterproject.room.AppDatabase
import com.example.harrypotterproject.room.DatabaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HPViewModel(
    private val dbRepo: DatabaseRepo,
    private val apiRepo: HPRepository
) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterModel>>()
    val characters: LiveData<List<CharacterModel>> get() = _characters

    private val _charactersByHouse = MutableLiveData<List<CharacterModel>>()
    val charactersByHouse: LiveData<List<CharacterModel>> get() = _charactersByHouse

    private val _selectedCharacter = MutableLiveData<CharacterModel>()
    val selectedCharacter: LiveData<CharacterModel> get() = _selectedCharacter

    private val _selectedHouse = MutableLiveData<String>()
    val selectedHouse: LiveData<String> get() = _selectedHouse

    private val _spellsList = MutableLiveData<List<SpellModel>>()
    val spellsList: LiveData<List<SpellModel>> get() = _spellsList


    init {
        getCharacters()
        getSpells()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            val localData = dbRepo.getAllCharactersList()

            if (localData.isEmpty()) {
                val response = withContext(Dispatchers.IO) {
                    apiRepo.getCharacters()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    withContext(Dispatchers.IO) {
                        dbRepo.insertAll(responseBody)
                        _characters.postValue(responseBody)
                    }
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
            withContext(Dispatchers.IO) {
                val response = apiRepo.getSpells()
                val responseBody = response.body()!!

                if (response.isSuccessful) {
                    _spellsList.postValue(responseBody)
                }
            }

        }
    }

    fun teachSpell(characters: List<CharacterModel>, randomSpell: SpellModel) {
        characters.forEach {
            if (it.knownSpells != null) {
                if (!it.knownSpells!!.contains(randomSpell)){
                    it.knownSpells!!.add(randomSpell)
                }
            }
            else {
                it.knownSpells = mutableListOf(randomSpell)
            }
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRepo.updateAll(characters)
            }
        }
    }

    fun getHouseCharacters(houseName: House) {
        _selectedHouse.postValue(houseToString(houseName))

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _charactersByHouse.postValue(
                    dbRepo.getAllCharactersList().filter {
                        it.house.lowercase() == _selectedHouse.value!!.lowercase()
                    }
                )
            }

        }
    }

    fun getCharacterById(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _selectedCharacter.postValue(dbRepo.getCharacterById(id))
            }
        }
    }

    fun changeHouse(character: CharacterModel, newHouse: House) {
        val oldHouse = character.house
        character.house = houseToString(newHouse)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRepo.updateCharacter(character)

                getCharacters() // Update All characters list
                getHouseCharacters(House.valueOf(oldHouse.uppercase())) // Update characters list By House
                getCharacterById(character.id) // Update characters details
            }
        }
    }
}


class HPViewModelFactory(
    private val context: Context,
    private val dbRepo: DatabaseRepo = DatabaseRepo(
        AppDatabase.getDatabase(context).characterDao()
    ),
    private val apiRepo: HPRepository = HPRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HPViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HPViewModel(dbRepo, apiRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
