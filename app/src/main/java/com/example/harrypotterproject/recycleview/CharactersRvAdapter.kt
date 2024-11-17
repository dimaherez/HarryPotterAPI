package com.example.harrypotterproject.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.harrypotterproject.R
import com.example.harrypotterproject.clicklisteners.OnCharacterClickListener
import com.example.harrypotterproject.databinding.CharactersRvItemBinding
import com.example.harrypotterproject.models.CharacterModel


class CharactersRvAdapter(
    var charactersList: List<CharacterModel> = listOf(),
    private val clickListener: OnCharacterClickListener
) : RecyclerView.Adapter<CharactersRvAdapter.ViewHolder>() {

    val selectedCharactersLiveData = MutableLiveData<List<CharacterModel>>()
    val selectedPositions: MutableList<Int> = mutableListOf()

    inner class ViewHolder(private val binding: CharactersRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterModel, position: Int) {
            binding.characterNameTV.text = character.name
            setImageFromUrl(
                imageView = binding.characterPictureIV,
                imageUrl = character.image,
                context = binding.root.context
            )
            binding.infoButton.setOnClickListener {
                clickListener.onInfoButtonClick(character)
            }

            binding.characterItemContainer.isSelected = selectedPositions.contains(position)

            binding.characterItemContainer.setOnClickListener {
                toggleSelection(adapterPosition)
            }
        }
    }

    fun toggleSelection(position: Int) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position)
        } else {
            selectedPositions.add(position)
        }
        selectedCharactersLiveData.postValue(getSelectedItems())
        notifyItemChanged(position)
    }

    private fun getSelectedItems(): List<CharacterModel> {
        return charactersList.filterIndexed { index, _ -> selectedPositions.contains(index) }
    }

    fun selectAll() {
        if (selectedPositions.size == charactersList.size) {
            deselectAll()
            return
        }

        charactersList.indices.forEach { position ->
            if (!selectedPositions.contains(position)) {
                toggleSelection(position)
            }
        }
    }

    fun deselectAll() {
        charactersList.indices.forEach { position ->
            if (selectedPositions.contains(position)) {
                toggleSelection(position)
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
        holder.bind(character, position)
    }

    private fun setImageFromUrl(imageView: ImageView, imageUrl: String, context: Context) =
        Glide.with(context)
            .load(imageUrl)
            //.apply(RequestOptions().override(1500, 700))
            .placeholder(R.drawable.wizard_placeholder)
            .into(imageView)

    override fun getItemCount(): Int {
        return charactersList.size
    }
}


