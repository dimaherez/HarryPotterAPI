package com.example.harrypotterproject.ui.spells

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterproject.clicklisteners.OnSpellClickListener
import com.example.harrypotterproject.databinding.FragmentSpellsBinding
import com.example.harrypotterproject.models.SpellModel
import com.example.harrypotterproject.recycleview.SpellsRvAdapter
import com.example.harrypotterproject.ui.viewmodel.HPViewModel
import com.example.harrypotterproject.ui.viewmodel.HPViewModelFactory

class SpellsFragment : Fragment(), OnSpellClickListener {

    private var _binding: FragmentSpellsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpellsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = HPViewModelFactory(context = requireContext())
        val viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HPViewModel::class.java]

        val spellsRV: RecyclerView = binding.spellsRecyclerView
        spellsRV.layoutManager = LinearLayoutManager(requireContext())
        val spellsRvAdapter = SpellsRvAdapter(clickListener = this)
        spellsRV.adapter = spellsRvAdapter

        viewModel.spellsList.observe(viewLifecycleOwner) { spellsList ->
            spellsRvAdapter.setSpellsList(spellsList)
            spellsRvAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(spell: SpellModel) {
        val dialog = SpellDetailsDialogFragment.newInstance(spell)
        dialog.show(parentFragmentManager, "SpellDetailsDialogFragment")
    }
}