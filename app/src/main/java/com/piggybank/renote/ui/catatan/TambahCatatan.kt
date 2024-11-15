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
import com.piggybank.renote.ui.rekening.RekeningViewModel
import java.util.Calendar

class TambahCatatan : Fragment() {

    private var _binding: FragmentTambahBinding? = null
    private val binding get() = _binding!!

    private val catatanViewModel: CatatanViewModel by activityViewModels()
    private val rekeningViewModel: RekeningViewModel by activityViewModels() // Tambahkan ini

    private var selectedDate: Calendar? = null

    private val pemasukanCategory = listOf("Pilih Kategori", "Gaji", "Investasi", "Paruh Waktu", "Lain-lain")
    private val pengeluaranCategory = listOf("Pilih Kategori", "Belanja", "Makanan", "Minuman", "Pulsa", "Transportasi", "Lain-lain")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahBinding.inflate(inflater, container, false)

        val bottomNavigationView = requireActivity().findViewById<View>(R.id.nav_view)
        bottomNavigationView.visibility = View.GONE

        binding.iconBack.setOnClickListener {
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

            val adjustedNominal = if (isPengeluaran) -nominal else nominal

            // Kirim data ke ViewModel
            catatanViewModel.addCatatan(kategori, adjustedNominal.toString(), deskripsi)

            // Perbarui saldo rekening
            rekeningViewModel.updateTotalSaldo(adjustedNominal)

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
