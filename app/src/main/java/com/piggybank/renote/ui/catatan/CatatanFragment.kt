package com.piggybank.renote.ui.catatan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentCatatanBinding
import java.util.Calendar
import java.util.Locale

class CatatanFragment : Fragment() {

    private var selectedDate: Calendar? = null

    private var _binding: FragmentCatatanBinding? = null
    private val binding get() = _binding!!

    private lateinit var catatanAdapter: CatatanAdapter
    private val catatanViewModel: CatatanViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)

        catatanAdapter = CatatanAdapter {
            findNavController().navigate(R.id.navigation_editCatatan)
        }

        binding.transactionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = catatanAdapter
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(itemDecoration)
        }

        // Observe the ViewModel for updates to the catatanList
        catatanViewModel.catatanList.observe(viewLifecycleOwner) { catatanList ->
            catatanAdapter.submitList(catatanList)
        }

        // Observe for total pemasukan, pengeluaran, dan saldo
        catatanViewModel.totalPemasukan.observe(viewLifecycleOwner) { pemasukan ->
            binding.textPemasukan.text = getString(R.string.pemasukan_text, String.format(Locale.getDefault(), "%.1f", pemasukan))
        }

        catatanViewModel.totalPengeluaran.observe(viewLifecycleOwner) { pengeluaran ->
            binding.textPengeluaran.text = getString(R.string.pengeluaran_text, String.format(Locale.getDefault(), "%.1f", pengeluaran))
        }

        catatanViewModel.totalSaldo.observe(viewLifecycleOwner) { saldo ->
            val formattedSaldo = if (saldo < 0) {
                getString(R.string.negative_saldo_text, String.format(Locale.getDefault(), "%.1f", saldo))
            } else {
                getString(R.string.positive_saldo_text, String.format(Locale.getDefault(), "%.1f", saldo))
            }
            binding.textTotal.text = formattedSaldo
        }

        // Set a click listener for the "Add" button to navigate to the add screen
        binding.catatanAdd.setOnClickListener {
            findNavController().navigate(R.id.navigation_tambahCatatan)
        }

        binding.calendarButton.setOnClickListener {
            showDatePickerDialog()
        }

        return binding.root
    }

    private fun showDatePickerDialog() {
        val calendar = selectedDate ?: Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
