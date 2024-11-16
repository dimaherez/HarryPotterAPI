package com.example.harrypotterproject.ui.houseCharacters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.harrypotterproject.databinding.FragmentHousesBinding
import com.example.harrypotterproject.enums.House

class HousesFragment : Fragment() {

    private var _binding: FragmentHousesBinding? = null
    private val binding get() = _binding!!

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

        binding.gryffindorIV.setOnClickListener { onHouseClick(House.GRYFFINDOR.name) }
        binding.slytherinIV.setOnClickListener { onHouseClick(House.SLYTHERIN.name) }
        binding.hufflepuffIV.setOnClickListener { onHouseClick(House.HUFFLEPUFF.name) }
        binding.ravenclawIV.setOnClickListener { onHouseClick(House.RAVENCLAW.name) }
    }

    private fun onHouseClick(houseName: String) {
        Log.d("MyLog", "onHouseClick clicked")
        val dialog = HouseCharactersDialogFragment.newInstance(houseName)
        dialog.show(childFragmentManager, "HouseCharactersDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}