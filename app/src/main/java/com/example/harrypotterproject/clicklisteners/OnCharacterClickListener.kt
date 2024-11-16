package com.example.harrypotterproject.clicklisteners

import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel

interface OnCharacterClickListener {
    fun onCharacterClick(character: CharacterModel)
    fun onEditClick(character: CharacterModel)
}

interface OnSpellClickListener {
    fun onClick(spell: SpellModel)
}