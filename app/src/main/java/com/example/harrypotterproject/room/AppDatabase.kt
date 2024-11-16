package com.example.harrypotterproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.room.converters.Converters
import com.example.harrypotterproject.room.converters.KnownSpellsConverter
import com.example.harrypotterproject.room.converters.WandConverter

@Database(entities = [CharacterModel::class], version = 1)
@TypeConverters(value = [Converters::class, KnownSpellsConverter::class, WandConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}






