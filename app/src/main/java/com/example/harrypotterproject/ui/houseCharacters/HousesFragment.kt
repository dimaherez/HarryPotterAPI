package com.example.harrypotterproject.ui.houseCharacters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.harrypotterproject.databinding.FragmentHousesBinding
import com.example.harrypotterproject.enums.House
import com.example.harrypotterproject.ui.viewmodel.HPViewModel
import com.example.harrypotterproject.ui.viewmodel.HPViewModelFactory

class HousesFragment : Fragment() {

    private var _binding: FragmentHousesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HPViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHousesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = HPViewModelFactory(context = requireContext())
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HPViewModel::class.java]

        binding.gryffindorIV.setOnClickListener { onHouseClick(House.GRYFFINDOR) }
        binding.slytherinIV.setOnClickListener { onHouseClick(House.SLYTHERIN) }
        binding.hufflepuffIV.setOnClickListener { onHouseClick(House.HUFFLEPUFF) }
        binding.ravenclawIV.setOnClickListener { onHouseClick(House.RAVENCLAW) }
    }

    private fun onHouseClick(house: House) {
        viewModel.getHouseCharacters(house)
        val dialog = HouseCharactersDialogFragment()
        dialog.show(childFragmentManager, "HouseCharactersDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}