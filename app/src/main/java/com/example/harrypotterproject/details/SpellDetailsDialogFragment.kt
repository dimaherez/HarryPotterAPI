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
import com.example.harrypotterproject.databinding.SpellInfoDialogBinding
import com.example.harrypotterproject.models.SpellModel
import com.google.gson.Gson

class SpellDetailsDialogFragment: DialogFragment() {
    private var _binding: SpellInfoDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SpellInfoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spellJson = arguments?.getString("spell")
        val spell = Gson().fromJson(spellJson, SpellModel::class.java)

        binding.spellInfo.text = spell.toString()

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
        fun newInstance(spell: SpellModel): SpellDetailsDialogFragment {
            val args = Bundle().apply { putString("spell", Gson().toJson(spell)) }
            val fragment = SpellDetailsDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}