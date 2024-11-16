package com.example.harrypotterproject.retrofit

import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("characters")
    suspend fun getCharacters(): Response<List<CharacterModel>>

    @GET("spells")
    suspend fun getSpells(): Response<List<SpellModel>>

    @GET("characters/house/{houseName}")
    suspend fun getCharactersByHouse(@Path("houseName") houseName: String): Response<List<CharacterModel>>
}