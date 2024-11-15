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
    private lateinit var adapter: RekeningAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rekeningViewModel = ViewModelProvider(requireActivity()).get(RekeningViewModel::class.java)

        _binding = FragmentRekeningBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rekeningViewModel.totalSaldo.observe(viewLifecycleOwner) { totalSaldo ->
            binding.totalSaldo.text = rekeningViewModel.formatCurrency(totalSaldo)
        }

        rekeningViewModel.rekeningList.observe(viewLifecycleOwner) { rekeningList ->
            adapter = RekeningAdapter(
                rekeningList,
                { rekening ->
                    rekeningViewModel.setActiveRekening(rekening)
                    val action = RekeningFragmentDirections.actionRekeningFragmentToEditRekening(rekening)
                    findNavController().navigate(action)
                },
                rekeningViewModel::formatCurrency
            )

            rekeningViewModel.activeRekening.value?.let { activeRekening ->
                adapter.selectedPosition = rekeningList.indexOfFirst { it.name == activeRekening.name }
            }

            binding.rekeningList.layoutManager = LinearLayoutManager(requireContext())
            binding.rekeningList.adapter = adapter
        }

        rekeningViewModel.activeRekening.observe(viewLifecycleOwner) { activeRekening ->
            activeRekening?.let {
                val position = rekeningViewModel.rekeningList.value?.indexOf(it) ?: -1
                if (position != -1) {
                    adapter.selectedPosition = position
                    adapter.notifyDataSetChanged()
                }
            }
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