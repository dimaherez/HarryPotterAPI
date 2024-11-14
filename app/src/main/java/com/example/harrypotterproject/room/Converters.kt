package com.example.harrypotterproject.room

import androidx.room.TypeConverter
import com.example.harrypotterproject.models.SpellModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromKnownSpellsList(value: List<SpellModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SpellModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toKnownSpellsList(value: String): List<SpellModel> {
        val gson = Gson()
        val type = object : TypeToken<List<SpellModel>>() {}.type
        return gson.fromJson(value, type)
    }
}
