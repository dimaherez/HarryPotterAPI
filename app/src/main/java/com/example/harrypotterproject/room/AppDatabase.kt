package com.example.harrypotterproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CharacterEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `characters_new` (
                        `id` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `actor` TEXT NOT NULL,
                        `alive` INTEGER NOT NULL,
                        `ancestry` TEXT NOT NULL,
                        `dateOfBirth` TEXT,
                        `eyeColour` TEXT NOT NULL,
                        `gender` TEXT NOT NULL,
                        `hairColour` TEXT NOT NULL,
                        `hogwartsStaff` INTEGER NOT NULL,
                        `hogwartsStudent` INTEGER NOT NULL,
                        `house` TEXT NOT NULL,
                        `image` TEXT NOT NULL,
                        `patronus` TEXT NOT NULL,
                        `species` TEXT NOT NULL,
                        `wandCore` TEXT NOT NULL,
                        `wandLength` REAL NOT NULL,
                        `wandWood` TEXT NOT NULL,
                        `wizard` INTEGER NOT NULL,
                        `yearOfBirth` INTEGER NOT NULL,
                        `knownSpells` TEXT NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                """.trimIndent())

                database.execSQL("""
                    INSERT INTO `characters_new` (
                        `id`, `name`, `actor`, `alive`, `ancestry`, `dateOfBirth`, `eyeColour`, `gender`, `hairColour`,
                        `hogwartsStaff`, `hogwartsStudent`, `house`, `image`, `patronus`, `species`, `wandCore`,
                        `wandLength`, `wandWood`, `wizard`, `yearOfBirth`, `knownSpells`
                    )
                    SELECT `id`, `name`, `actor`, `alive`, `ancestry`, `dateOfBirth`, `eyeColour`, `gender`, `hairColour`,
                        `hogwartsStaff`, `hogwartsStudent`, `house`, `image`, `patronus`, `species`, `wandCore`,
                        `wandLength`, `wandWood`, `wizard`, `yearOfBirth`, `knownSpells`
                    FROM `characters`
                """.trimIndent())

                database.execSQL("DROP TABLE `characters`")
                database.execSQL("ALTER TABLE `characters_new` RENAME TO `characters`")
            }
        }
    }
}






