package com.example.harrypotterproject.ui.houseCharacters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterproject.clicklisteners.OnCharacterClickListener
import com.example.harrypotterproject.databinding.FragmentHouseCharactersBinding
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.recycleview.CharactersRvAdapter
import com.example.harrypotterproject.ui.characters.CharacterDetailsDialogFragment
import com.example.harrypotterproject.ui.viewmodel.HPViewModel
import com.example.harrypotterproject.ui.viewmodel.HPViewModelFactory

class HouseCharactersDialogFragment : DialogFragment(), OnCharacterClickListener {
    private var _binding: FragmentHouseCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HPViewModel

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


        val viewModelFactory = HPViewModelFactory(context = requireContext())
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HPViewModel::class.java]

        val recyclerView: RecyclerView = binding.charactersRecycleView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns in the grid
        val adapter = CharactersRvAdapter(clickListener = this)
        recyclerView.adapter = adapter

        viewModel.charactersByHouse.observe(viewLifecycleOwner) { charactersList ->
            binding.charactersTitle.text = "${viewModel.selectedHouse.value}'s Characters"

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
        viewModel.getCharacterById(character.id)
        val dialog = CharacterDetailsDialogFragment()
        dialog.show(parentFragmentManager, "CharacterDetailsDialogFragment")
    }

    override fun onTeachSpellClick(characters: List<CharacterModel>, spells: List<SpellModel>) {
        TODO("Edit character dialog fragment")
    }

    override fun onSelectAllClick(adapter: CharactersRvAdapter) {
        TODO("Not yet implemented")
    }


}