package com.example.harrypotterproject.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.harrypotterproject.R
import com.example.harrypotterproject.databinding.CharacterInfoDialogBinding
import com.example.harrypotterproject.ui.viewmodel.HPViewModel
import com.example.harrypotterproject.ui.viewmodel.HPViewModelFactory

class CharacterDetailsDialogFragment : DialogFragment() {
    private var _binding: CharacterInfoDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HPViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharacterInfoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = HPViewModelFactory(context = requireContext())
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HPViewModel::class.java]


        viewModel.selectedCharacter.observe(viewLifecycleOwner) {
            setImageFromUrl(binding.characterImageView, it.image, requireContext())
            binding.characterInfo.text = it.toString()

        }

        binding.closeButton.setOnClickListener {
            dismiss() // Close the dialog
        }

        binding.swapHouseButton.setOnClickListener {
            val dialog = SwapHouseDialogFragment.newInstance(viewModel.selectedCharacter.value!!)
            dialog.show(childFragmentManager, "SwapHouseDialogFragment")
        }

    }

    private fun setImageFromUrl(imageView: ImageView, imageUrl: String, context: Context) =
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.wizard_placeholder)
            .into(imageView)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
