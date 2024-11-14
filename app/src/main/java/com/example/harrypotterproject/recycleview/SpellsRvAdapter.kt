package com.example.harrypotterproject.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterproject.R
import com.example.harrypotterproject.details.CharacterDetailsDialogFragment
import com.example.harrypotterproject.details.SpellDetailsDialogFragment
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel

class SpellsRvAdapter(
    private var spellsList: List<SpellModel> = listOf(),
    private val clickListener: OnSpellClickListener
) : RecyclerView.Adapter<SpellsRvAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.spellTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.spells_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spell = spellsList[position]
        holder.textView.text = spell.name

        //view.setOnClickListener
        holder.itemView.setOnClickListener {
            clickListener.onClick(spell)
        }
    }

    fun setSpellsList(newSpellsList: List<SpellModel>) {
        this.spellsList = newSpellsList
    }

    override fun getItemCount(): Int {
        return spellsList.size
    }
}

class OnSpellClickListener(val clickListener: (spell: SpellModel) -> Unit) {
    fun onClick(spell: SpellModel) = clickListener(spell)
}

