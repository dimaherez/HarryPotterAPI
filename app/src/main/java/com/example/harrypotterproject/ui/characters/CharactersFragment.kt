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
import com.example.harrypotterproject.clicklisteners.OnCharacterClickListener
import com.example.harrypotterproject.databinding.FragmentCharactersBinding
import com.example.harrypotterproject.models.CharacterModel
import com.example.harrypotterproject.recycleview.CharactersRvAdapter

class CharactersFragment : Fragment(), OnCharacterClickListener {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

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
        val charactersViewModel = ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]

        val recyclerView: RecyclerView = view.findViewById(R.id.charactersRecycleView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns in the grid
        val adapter = CharactersRvAdapter(clickListener = this)
        recyclerView.adapter = adapter

        charactersViewModel.characters.observe(viewLifecycleOwner) { allCharacters ->
            adapter.charactersList = allCharacters
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCharacterClick(character: CharacterModel) {
        val dialog = CharacterDetailsDialogFragment.newInstance(character)
        dialog.show(childFragmentManager, "CharacterDetailsDialogFragment")
    }

    override fun onEditClick(character: CharacterModel) {
        TODO("Edit character dialog fragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
