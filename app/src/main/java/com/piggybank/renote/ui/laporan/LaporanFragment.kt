package com.piggybank.renote.ui.laporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.piggybank.renote.databinding.FragmentLaporanBinding

class LaporanFragment : Fragment() {

    private var _binding: FragmentLaporanBinding? = null
    private val binding get() = _binding!!
    private lateinit var laporanAdapter: LaporanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val laporanViewModel =
            ViewModelProvider(this).get(LaporanViewModel::class.java)

        _binding = FragmentLaporanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize adapter with an empty list
        laporanAdapter = LaporanAdapter(emptyList())
        binding.laporanList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = laporanAdapter
        }

        // Observe ViewModel's category list and update adapter
        laporanViewModel.categoryList.observe(viewLifecycleOwner) { categories ->
            laporanAdapter.updateData(categories)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
