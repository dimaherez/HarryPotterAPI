package com.example.harrypotterproject.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.harrypotterproject.databinding.FragmentSwapHouseBinding
import com.example.harrypotterproject.enums.House
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.ui.viewmodel.HPViewModel
import com.example.harrypotterproject.ui.viewmodel.HPViewModelFactory
import com.google.gson.Gson

class SwapHouseDialogFragment : DialogFragment() {

    private var _binding: FragmentSwapHouseBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HPViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwapHouseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = HPViewModelFactory(context = requireContext())
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HPViewModel::class.java]

        val characterJson = arguments?.getString("character")
        val character = Gson().fromJson(characterJson, CharacterModel::class.java)

        binding.gryffindorIV.setOnClickListener {
            onClick(character, House.GRYFFINDOR)
        }
        binding.slytherinIV.setOnClickListener {
            onClick(character, House.SLYTHERIN)
        }
        binding.hufflepuffIV.setOnClickListener {
            onClick(character, House.HUFFLEPUFF)
        }
        binding.ravenclawIV.setOnClickListener {
            onClick(character, House.RAVENCLAW)
        }
    }

    private fun onClick(character: CharacterModel, house: House) {
        viewModel.changeHouse(character, newHouse = house)
        Toast.makeText(
            requireContext(),
            "${character.name}'s house changed to $house",
            Toast.LENGTH_LONG
        ).show()

        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(character: CharacterModel): SwapHouseDialogFragment {
            val args = Bundle().apply { putString("character", Gson().toJson(character)) }
            val fragment = SwapHouseDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}