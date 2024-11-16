package com.example.harrypotterproject.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harrypotterproject.R
import com.example.harrypotterproject.clicklisteners.OnCharacterClickListener
import com.example.harrypotterproject.databinding.CharactersRvItemBinding
import com.example.harrypotterproject.models.CharacterModel

class CharactersRvAdapter(
    var charactersList: List<CharacterModel> = listOf(),
    private val clickListener: OnCharacterClickListener
) : RecyclerView.Adapter<CharactersRvAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CharactersRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterModel) {
            binding.characterNameTV.text = character.name
            setImageFromUrl(
                imageView = binding.characterPictureIV,
                imageUrl = character.image,
                context = binding.root.context
            )
            binding.characterItemContainer.setOnClickListener {
                clickListener.onCharacterClick(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharactersRvItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = charactersList[position]
        holder.bind(character)
    }

    private fun setImageFromUrl(imageView: ImageView, imageUrl: String, context: Context) =
        Glide.with(context).load(imageUrl).placeholder(R.drawable.wizard_placeholder)
            .into(imageView)

    override fun getItemCount(): Int {
        return charactersList.size
    }
}


