package com.example.harrypotterproject.ui.houseCharacters

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

class HouseCharactersFragment: Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

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

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val recyclerView: RecyclerView = view.findViewById(R.id.charactersRecycleView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns in the grid
        val adapter = CharactersRvAdapter(clickListener = OnCharacterClickListener {
            showCharacterDetails(it)
        })
        recyclerView.adapter = adapter

        binding.charactersTitle.text = "${sharedViewModel.selectedHouse.value}'s Characters"

        sharedViewModel.charactersList.observe(viewLifecycleOwner) { charactersList ->
            adapter.charactersList = charactersList
            adapter.notifyDataSetChanged()
        }

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