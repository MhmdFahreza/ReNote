package com.piggybank.renote.ui.rekening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentRekeningBinding

class RekeningFragment : Fragment() {

    private var _binding: FragmentRekeningBinding? = null
    private val binding get() = _binding!!

    private lateinit var rekeningViewModel: RekeningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rekeningViewModel = ViewModelProvider(requireActivity()).get(RekeningViewModel::class.java)

        _binding = FragmentRekeningBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rekeningViewModel.totalSaldo.observe(viewLifecycleOwner) { totalSaldo ->
            binding.totalSaldo.text = rekeningViewModel.formatCurrency(totalSaldo) // Using currency format
        }

        rekeningViewModel.rekeningList.observe(viewLifecycleOwner) { rekeningList ->
            val adapter = RekeningAdapter(rekeningList, { rekening ->
                // Navigate using Safe Args to pass the rekening data
                val action = RekeningFragmentDirections.actionRekeningFragmentToEditRekening(rekening)
                findNavController().navigate(action)
            }, rekeningViewModel::formatCurrency) // Pass format function
            binding.rekeningList.layoutManager = LinearLayoutManager(requireContext())
            binding.rekeningList.adapter = adapter
        }

        binding.rekeningAdd.setOnClickListener {
            findNavController().navigate(R.id.action_rekeningFragment_to_tambahRekening)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
