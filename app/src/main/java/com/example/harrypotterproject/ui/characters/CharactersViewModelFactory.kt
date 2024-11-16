package com.example.harrypotterproject.ui.characters

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harrypotterproject.room.AppDatabase
import com.example.harrypotterproject.room.DatabaseRepo

class CharactersViewModelFactory(
    private val context: Context,
    private val dbRepo: DatabaseRepo = DatabaseRepo(AppDatabase.getDatabase(context).characterDao())
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharactersViewModel(dbRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
