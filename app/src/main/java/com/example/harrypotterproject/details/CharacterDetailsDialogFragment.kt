package com.example.harrypotterproject.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.harrypotterproject.R
import com.example.harrypotterproject.databinding.CharacterInfoDialogBinding
import com.example.harrypotterproject.models.CharacterModel
import com.google.gson.Gson

class CharacterDetailsDialogFragment : DialogFragment() {
    private var _binding: CharacterInfoDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharacterInfoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterJson = arguments?.getString("character")
        val character = Gson().fromJson(characterJson, CharacterModel::class.java)

        setImageFromUrl(binding.characterImageView, character.image, requireContext())
        binding.characterInfo.text = character.toString()

        binding.closeButton.setOnClickListener {
            dismiss() // Close the dialog
        }

    }

    private fun setImageFromUrl(imageView: ImageView, imageUrl: String, context: Context) =
        Glide.with(context).load(imageUrl).placeholder(R.drawable.wizard_placeholder).into(imageView)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(character: CharacterModel): CharacterDetailsDialogFragment {
            val args = Bundle().apply { putString("character", Gson().toJson(character)) }
            val fragment = CharacterDetailsDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
