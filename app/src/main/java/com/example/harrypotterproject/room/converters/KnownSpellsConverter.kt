package com.example.harrypotterproject.room.converters

import androidx.room.TypeConverter
import com.example.harrypotterproject.models.SpellModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object KnownSpellsConverter {
    private val gson = Gson()

    @TypeConverter
    fun arrayListToJson(list: List<SpellModel>?): String? {
        return if(list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun jsonToArrayList(jsonData: String?): List<SpellModel>? {
        return if (jsonData == null) null else gson.fromJson(jsonData, object : TypeToken<List<SpellModel>?>() {}.type)
    }
}