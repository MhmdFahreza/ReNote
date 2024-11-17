package com.piggybank.renote.ui.laporan

import MonthAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentLaporanBinding

class LaporanFragment : Fragment() {

    private var _binding: FragmentLaporanBinding? = null
    private val binding get() = _binding!!
    private lateinit var laporanAdapter: LaporanAdapter
    private lateinit var laporanViewModel: LaporanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        laporanViewModel = ViewModelProvider(this).get(LaporanViewModel::class.java)

        _binding = FragmentLaporanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setup RecyclerView
        laporanAdapter = LaporanAdapter(emptyList())
        binding.laporanList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = laporanAdapter
        }

        // Observe category data
        laporanViewModel.categoryList.observe(viewLifecycleOwner) { categories ->
            laporanAdapter.updateData(categories)
        }

        // Observe selected date
        laporanViewModel.selectedDate.observe(viewLifecycleOwner) { (month, year) ->
            val selectedDate = "$month $year"
            binding.dateDropdown.text = selectedDate
        }

        // Setup Dropdown Click Listener
        binding.dateDropdown.setOnClickListener {
            showMonthYearPicker()
        }

        return root
    }

    private fun showMonthYearPicker() {
        val months = listOf(
            "Jan", "Feb", "Mar", "Apr", "Mei", "Jun",
            "Jul", "Agust", "Sept", "Okt", "Nov", "Des"
        )
        val years = (2010..2030).map { it.toString() }

        // Get previously selected date
        val currentSelection = laporanViewModel.selectedDate.value
        val defaultYear = currentSelection?.second ?: "2024"
        val defaultMonth = currentSelection?.first

        // Inflate layout dialog
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_month_picker, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.monthRecyclerView)
        val yearSpinner = dialogView.findViewById<Spinner>(R.id.yearSpinner)

        var selectedMonth: String? = defaultMonth
        var selectedYear: String? = defaultYear

        // Setup Spinner (Year Selector)
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter
        yearSpinner.setSelection(years.indexOf(defaultYear))

        // Setup RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = MonthAdapter(months) { month ->
            selectedMonth = month
            selectedYear = yearSpinner.selectedItem.toString()
        }

        // Show dialog
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                if (!selectedMonth.isNullOrEmpty() && !selectedYear.isNullOrEmpty()) {
                    val selectedDate = "$selectedMonth $selectedYear"
                    binding.dateDropdown.text = selectedDate
                    laporanViewModel.saveSelectedDate(selectedMonth!!, selectedYear!!)
                    Toast.makeText(context, "Dipilih: $selectedDate", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Silakan pilih bulan dan tahun.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
