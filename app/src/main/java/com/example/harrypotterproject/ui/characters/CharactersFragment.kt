package com.example.harrypotterproject.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterproject.R
import com.example.harrypotterproject.clicklisteners.OnCharacterClickListener
import com.example.harrypotterproject.databinding.FragmentCharactersBinding
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.recycleview.CharactersRvAdapter
import com.example.harrypotterproject.ui.viewmodel.HPViewModel
import com.example.harrypotterproject.ui.viewmodel.HPViewModelFactory

class CharactersFragment : Fragment(), OnCharacterClickListener {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HPViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewModelFactory = HPViewModelFactory(context = requireContext())
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HPViewModel::class.java]

        val recyclerView: RecyclerView = view.findViewById(R.id.charactersRecycleView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns in the grid
        val adapter = CharactersRvAdapter(clickListener = this)
        recyclerView.adapter = adapter


        viewModel.characters.observe(viewLifecycleOwner) { allCharacters ->
            adapter.charactersList = allCharacters
            adapter.notifyDataSetChanged()
        }

        adapter.selectedCharactersLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.teachSpellButton.visibility = View.GONE
                binding.selectAllButton.visibility = View.GONE
            } else {
                binding.teachSpellButton.visibility = View.VISIBLE
                binding.selectAllButton.visibility = View.VISIBLE
            }
        }

        binding.teachSpellButton.setOnClickListener {
            onTeachSpellClick(
                adapter.selectedCharactersLiveData.value!!,
                viewModel.spellsList.value!!
            )
            adapter.deselectAll()
        }

        binding.selectAllButton.setOnClickListener {
            onSelectAllClick(adapter)
        }

    }

    override fun onInfoButtonClick(character: CharacterModel) {
        viewModel.getCharacterById(character.id)
        val dialog = CharacterDetailsDialogFragment()
        dialog.show(childFragmentManager, "CharacterDetailsDialogFragment")
    }

    override fun onTeachSpellClick(characters: List<CharacterModel>, spells: List<SpellModel>) {
        val randomSpell = spells.random()
        viewModel.teachSpell(characters, randomSpell)
        Toast.makeText(
            requireContext(),
            "'${randomSpell.name}' learned!",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onSelectAllClick(adapter: CharactersRvAdapter) {
        adapter.selectAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
