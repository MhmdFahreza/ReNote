package com.piggybank.renote.ui.catatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentEditCatatanBinding

class EditCatatan : Fragment() {

    private var _binding: FragmentEditCatatanBinding? = null
    private val binding get() = _binding!!
    private val catatanViewModel: CatatanViewModel by activityViewModels()
    private var currentKategoriType: String = "Pengeluaran" // Default to Pengeluaran

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCatatanBinding.inflate(inflater, container, false)

        val selectedCatatan = catatanViewModel.selectedCatatan
        currentKategoriType = if (selectedCatatan?.kategori == "Pemasukan") "Pemasukan" else "Pengeluaran"

        binding.inputAmount.setText(selectedCatatan?.nominal)
        binding.inputDescription.setText(selectedCatatan?.deskripsi)

        setupCategorySpinner(currentKategoriType)
        setupToggleGroup()

        binding.buttonChange.setOnClickListener {
            val updatedCatatan = Catatan(
                kategori = binding.spinnerCategory.selectedItem.toString(),
                nominal = binding.inputAmount.text.toString(),
                deskripsi = binding.inputDescription.text.toString()
            )
            catatanViewModel.updateCatatan(updatedCatatan)
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun setupCategorySpinner(kategoriType: String) {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            getCategories(kategoriType)
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = spinnerAdapter
    }

    private fun setupToggleGroup() {
        binding.radioPengeluaran.isChecked = currentKategoriType == "Pengeluaran"
        binding.radioPemasukan.isChecked = currentKategoriType == "Pemasukan"

        binding.toggleGroup.setOnCheckedChangeListener { _, checkedId ->
            currentKategoriType = if (checkedId == R.id.radio_pemasukan) "Pemasukan" else "Pengeluaran"
            setupCategorySpinner(currentKategoriType)
        }
    }

    private fun getCategories(kategori: String): List<String> {
        return if (kategori == "Pemasukan") {
            listOf("Gaji", "Investasi", "Paruh Waktu", "Lain-lain")
        } else {
            listOf("Belanja", "Makanan & Minuman", "Pulsa", "Pendidikan", "Kecantikan", "Top Up", "Donasi", "Lain-lain")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

