package com.example.harrypotterproject.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterproject.R
import com.example.harrypotterproject.clicklisteners.OnCharacterClickListener
import com.example.harrypotterproject.databinding.FragmentCharactersBinding
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.recycleview.CharactersRvAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CharactersFragment : Fragment(), OnCharacterClickListener {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    companion object {
        lateinit var charactersViewModel: CharactersViewModel
        fun refreshData() {
            charactersViewModel.getCharacters()
        }
    }


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


        val viewModelFactory = CharactersViewModelFactory(context = requireContext())
        charactersViewModel =
            ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]

        val recyclerView: RecyclerView = view.findViewById(R.id.charactersRecycleView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns in the grid
        val adapter = CharactersRvAdapter(clickListener = this)
        recyclerView.adapter = adapter

        charactersViewModel.characters.observe(viewLifecycleOwner) { allCharacters ->
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
                charactersViewModel.spellsList.value!!
            )
            adapter.deselectAll()
        }

        binding.selectAllButton.setOnClickListener {
            onSelectAllClick(adapter)
        }

    }

    override fun onInfoButtonClick(character: CharacterModel) {
        val dialog = CharacterDetailsDialogFragment.newInstance(character)
        dialog.show(childFragmentManager, "CharacterDetailsDialogFragment")
    }

    override fun onTeachSpellClick(characters: List<CharacterModel>, spells: List<SpellModel>) {
        val randomSpell = spells.random()
        characters.forEach {
            if (it.knownSpells == null) it.knownSpells = mutableListOf(randomSpell)
            else it.knownSpells!!.add(randomSpell)
        }
        charactersViewModel.teachSpells(characters)
        Toast.makeText(
            requireContext(),
            "Selected characters know '${randomSpell.name}' spell now!",
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
