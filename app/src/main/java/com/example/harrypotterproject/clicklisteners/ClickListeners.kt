package com.example.harrypotterproject.clicklisteners

import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.recycleview.CharactersRvAdapter

interface OnCharacterClickListener {
    fun onInfoButtonClick(character: CharacterModel)
    fun onTeachSpellClick(characters: List<CharacterModel>, spells: List<SpellModel>)
    fun onSelectAllClick(adapter: CharactersRvAdapter)
}

interface OnSpellClickListener {
    fun onClick(spell: SpellModel)
}