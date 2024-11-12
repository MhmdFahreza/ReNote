package com.piggybank.renote.ui.catatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentCatatanBinding

class CatatanFragment : Fragment() {

    private var _binding: FragmentCatatanBinding? = null
    private val binding get() = _binding!!
    private lateinit var catatanAdapter: CatatanAdapter
    private lateinit var catatanViewModel: CatatanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        catatanViewModel = ViewModelProvider(this).get(CatatanViewModel::class.java)

        // Pass a lambda function to navigate to the edit screen
        catatanAdapter = CatatanAdapter(navigateToEdit = {
            findNavController().navigate(R.id.navigation_editCatatan)
        })

        binding.transactionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = catatanAdapter
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(itemDecoration)
        }

        catatanViewModel.catatanList.observe(viewLifecycleOwner) { catatanList ->
            catatanAdapter.submitList(catatanList)
        }

        binding.catatanAdd.setOnClickListener {
            findNavController().navigate(R.id.navigation_tambahCatatan)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
