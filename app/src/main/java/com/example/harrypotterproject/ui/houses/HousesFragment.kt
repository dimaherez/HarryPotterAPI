package com.example.harrypotterproject.ui.houses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.harrypotterproject.R
import com.example.harrypotterproject.databinding.FragmentHousesBinding
import com.example.harrypotterproject.enums.House
import com.example.harrypotterproject.ui.houseCharacters.SharedViewModel

class HousesFragment : Fragment() {

    private var _binding: FragmentHousesBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHousesBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHouses
//        housesViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel =
            ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val gryffindorIV: ImageView = view.findViewById(R.id.gryffindorIV)
        val slytherinIV: ImageView = view.findViewById(R.id.slytherinIV)
        val hufflepuffIV: ImageView = view.findViewById(R.id.hufflepuffIV)
        val ravenclawIV: ImageView = view.findViewById(R.id.ravenclawIV)

        gryffindorIV.setOnClickListener { openCharactersFragment(House.GRYFFINDOR.name) }
        slytherinIV.setOnClickListener { openCharactersFragment(House.SLYTHERIN.name) }
        hufflepuffIV.setOnClickListener { openCharactersFragment(House.HUFFLEPUFF.name) }
        ravenclawIV.setOnClickListener { openCharactersFragment(House.RAVENCLAW.name) }

    }

    private fun openCharactersFragment(houseName: String) {
        sharedViewModel.selectHouse(houseName)
        findNavController().navigate(R.id.action_houses_to_housecharacters)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}