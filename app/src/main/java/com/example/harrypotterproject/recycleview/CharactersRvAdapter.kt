package com.example.harrypotterproject.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harrypotterproject.R
import com.example.harrypotterproject.models.CharacterModel

class CharactersRvAdapter(
    var charactersList: List<CharacterModel> = listOf(),
    private val clickListener: OnCharacterClickListener
) : RecyclerView.Adapter<CharactersRvAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.characterPictureIV)
        val textView: TextView = itemView.findViewById(R.id.characterNameTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.characters_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = charactersList[position]
        setImageFromUrl(
            imageView = holder.imageView,
            imageUrl = character.image,
            context = holder.itemView.context)
        holder.textView.text = character.name

        //view.setOnClickListener
        holder.itemView.setOnClickListener {
            clickListener.onClick(character)
        }
    }


    private fun setImageFromUrl(imageView: ImageView, imageUrl: String, context: Context) =
        Glide.with(context).load(imageUrl).placeholder(R.drawable.wizard_placeholder).into(imageView)

    override fun getItemCount(): Int {
        return charactersList.size
    }


}

class OnCharacterClickListener(val clickListener: (character: CharacterModel) -> Unit) {
    fun onClick(character: CharacterModel) = clickListener(character)
}
