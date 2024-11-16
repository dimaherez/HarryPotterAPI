package com.example.harrypotterproject.room.converters

import androidx.room.TypeConverter
import com.example.harrypotterproject.models.CharacterModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WandConverter {
    private val gson = Gson()

    @TypeConverter
    fun arrayListToJson(value: CharacterModel.Wand?): String? {
        return if(value == null) null else gson.toJson(value)
    }

    @TypeConverter
    fun jsonToArrayList(jsonData: String?): CharacterModel.Wand? {
        return if (jsonData == null) null else gson.fromJson(jsonData, object : TypeToken<CharacterModel.Wand?>() {}.type)
    }
}