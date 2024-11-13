package com.piggybank.renote.ui.catatan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentTambahBinding
import java.util.Calendar

class TambahCatatan : Fragment() {

    private var _binding: FragmentTambahBinding? = null
    private val binding get() = _binding!!

    private val catatanViewModel: CatatanViewModel by activityViewModels()

    private var selectedDate: Calendar? = null

    // Define categories for Income and Expense
    private val pemasukanCategory = listOf("Pilih Kategori", "Gaji", "Investasi", "Paruh Waktu", "Lain-lain")
    private val pengeluaranCategory = listOf("Pilih Kategori", "Belanja", "Makanan", "Minuman", "Pulsa", "Transportasi", "Lain-lain")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahBinding.inflate(inflater, container, false)

        // Menyembunyikan bottom navigation
        val bottomNavigationView = requireActivity().findViewById<View>(R.id.nav_view)
        bottomNavigationView.visibility = View.GONE

        binding.iconBack.setOnClickListener {
            // Menampilkan kembali bottom navigation saat kembali
            bottomNavigationView.visibility = View.VISIBLE
            findNavController().navigateUp()
        }

        binding.iconCalendar.setOnClickListener {
            showDatePickerDialog()
        }

        setupCategorySpinner(pengeluaranCategory)

        binding.toggleGroup.setOnCheckedChangeListener { _: RadioGroup, checkedId: Int ->
            if (checkedId == R.id.radio_pengeluaran) {
                setupCategorySpinner(pengeluaranCategory)
            } else if (checkedId == R.id.radio_pemasukan) {
                setupCategorySpinner(pemasukanCategory)
            }
        }

        binding.buttonCreate.setOnClickListener {
            val kategori = binding.spinnerCategory.selectedItem.toString()
            val nominal = binding.inputAmount.text.toString().toDoubleOrNull() ?: 0.0
            val deskripsi = binding.inputDescription.text.toString()
            val isPengeluaran = binding.toggleGroup.checkedRadioButtonId == R.id.radio_pengeluaran

            val formattedNominal = if (isPengeluaran) "- Rp $nominal" else "+ Rp $nominal"

            // Send data to CatatanFragment through ViewModel
            catatanViewModel.addCatatan(kategori, formattedNominal, deskripsi)

            // Menampilkan kembali bottom navigation sebelum pindah halaman
            bottomNavigationView.visibility = View.VISIBLE
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun setupCategorySpinner(categories: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }

    private fun showDatePickerDialog() {
        // Use selectedDate or current date if null
        val calendar = selectedDate ?: Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Set the selected date in selectedDate
                selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                // Update UI with the selected date
                binding.textDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
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
