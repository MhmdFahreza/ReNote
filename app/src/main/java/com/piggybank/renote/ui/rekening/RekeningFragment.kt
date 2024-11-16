package com.piggybank.renote.ui.rekening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentRekeningBinding
import com.piggybank.renote.ui.catatan.CatatanViewModel

class RekeningFragment : Fragment() {

    private var _binding: FragmentRekeningBinding? = null
    private val binding get() = _binding!!

    private lateinit var rekeningViewModel: RekeningViewModel
    private val catatanViewModel: CatatanViewModel by activityViewModels()

    private lateinit var adapter: RekeningAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rekeningViewModel = ViewModelProvider(requireActivity()).get(RekeningViewModel::class.java)

        // Hubungkan saldoChangeListener dari CatatanViewModel ke RekeningViewModel
        catatanViewModel.saldoChangeListener = { change ->
            rekeningViewModel.updateTotalSaldo(change.toString())
        }

        _binding = FragmentRekeningBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observe total saldo
        rekeningViewModel.totalSaldo.observe(viewLifecycleOwner) { totalSaldo ->
            binding.totalSaldo.text = rekeningViewModel.formatCurrency(totalSaldo)
        }

        // Observe rekening list and update UI
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
