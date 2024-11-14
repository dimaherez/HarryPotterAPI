package com.example.harrypotterproject.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterproject.R
import com.example.harrypotterproject.databinding.FragmentCharactersBinding
import com.example.harrypotterproject.details.CharacterDetailsDialogFragment
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.recycleview.CharactersRvAdapter
import com.example.harrypotterproject.recycleview.OnCharacterClickListener
import com.example.harrypotterproject.room.AppDatabase
import com.example.harrypotterproject.room.DatabaseRepo

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterDao = AppDatabase.getDatabase(requireContext()).characterDao()
        val databaseRepository = DatabaseRepo(characterDao)

        val viewModelFactory = CharactersViewModelFactory(databaseRepository)
        val charactersViewModel = ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]

        val recyclerView: RecyclerView = view.findViewById(R.id.charactersRecycleView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns in the grid
        val adapter = CharactersRvAdapter(clickListener = OnCharacterClickListener {
            showCharacterDetails(it)
        })
        recyclerView.adapter = adapter

        charactersViewModel.allCharacters.observe(viewLifecycleOwner) { allCharacters ->
            adapter.charactersList = allCharacters
            adapter.notifyDataSetChanged()
        }




        charactersViewModel.refreshCharacters()

    }

    private fun showCharacterDetails(character: CharacterModel) {
        val dialog = CharacterDetailsDialogFragment.newInstance(character)
        dialog.show(parentFragmentManager, "CharacterDetailsDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
