package com.example.harrypotterproject.ui.houseCharacters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterproject.clicklisteners.OnCharacterClickListener
import com.example.harrypotterproject.databinding.FragmentCharactersBinding
import com.example.harrypotterproject.databinding.FragmentHouseCharactersBinding
import com.example.harrypotterproject.ui.characters.CharacterDetailsDialogFragment
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.recycleview.CharactersRvAdapter
import com.example.harrypotterproject.ui.characters.CharactersViewModel
import com.example.harrypotterproject.ui.characters.CharactersViewModelFactory

const val HOUSE_NAME_KEY = "houseName"

class HouseCharactersDialogFragment : DialogFragment(), OnCharacterClickListener {
    private var _binding: FragmentHouseCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var houseCharactersDialogViewModel: HouseCharactersDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHouseCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewModelFactory = HouseCharactersDialogViewModelFactory(context = requireContext())
        houseCharactersDialogViewModel =
            ViewModelProvider(this, viewModelFactory)[HouseCharactersDialogViewModel::class.java]


        val houseName = requireArguments().getString(HOUSE_NAME_KEY)

        val recyclerView: RecyclerView = binding.charactersRecycleView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns in the grid
        val adapter = CharactersRvAdapter(clickListener = this)
        recyclerView.adapter = adapter

        houseCharactersDialogViewModel.selectHouse(houseName!!)

        binding.charactersTitle.text = "$houseName's Characters"

        houseCharactersDialogViewModel.charactersList.observe(viewLifecycleOwner) { charactersList ->
            adapter.charactersList = charactersList
            adapter.notifyDataSetChanged()
        }

        binding.closeButton.setOnClickListener {
            dismiss() // Close the dialog
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onInfoButtonClick(character: CharacterModel) {
        val dialog = CharacterDetailsDialogFragment.newInstance(character)
        dialog.show(parentFragmentManager, "CharacterDetailsDialogFragment")
    }

    override fun onTeachSpellClick(characters: List<CharacterModel>, spells: List<SpellModel>) {
        TODO("Edit character dialog fragment")
    }

    override fun onSelectAllClick(adapter: CharactersRvAdapter) {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(houseName: String): HouseCharactersDialogFragment {
            val args = Bundle().apply { putString(HOUSE_NAME_KEY, houseName) }
            val fragment = HouseCharactersDialogFragment()
            fragment.arguments = args
            Log.d("MyLog", "Instance Created")
            return fragment
        }
    }
}