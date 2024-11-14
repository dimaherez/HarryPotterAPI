package com.example.harrypotterproject.retrofit

import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import retrofit2.Response

object HPRepository {

    suspend fun getCharacters(): Response<List<CharacterModel>> = RetrofitInstance.api.getCharacters()

    suspend fun getSpells(): Response<List<SpellModel>> = RetrofitInstance.api.getSpells()



}