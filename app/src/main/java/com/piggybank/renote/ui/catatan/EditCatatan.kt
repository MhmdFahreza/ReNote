package com.piggybank.renote.ui.catatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.piggybank.renote.databinding.FragmentEditCatatanBinding

class EditCatatan : Fragment() {

    private var _binding: FragmentEditCatatanBinding? = null
    private val binding get() = _binding!!
    private val catatanViewModel: CatatanViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCatatanBinding.inflate(inflater, container, false)

        // Load selectedCatatan data
        val selectedCatatan = catatanViewModel.selectedCatatan
        if (selectedCatatan != null) {
            binding.inputAmount.setText(selectedCatatan.nominal)
            binding.inputDescription.setText(selectedCatatan.deskripsi)
        } else {
            Toast.makeText(requireContext(), "Tidak ada catatan yang dipilih!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        // Edit button functionality
        binding.buttonEdit.setOnClickListener {
            val newNominal = binding.inputAmount.text.toString()
            val newDeskripsi = binding.inputDescription.text.toString()

            if (newNominal.isNotBlank() && newDeskripsi.isNotBlank()) {
                catatanViewModel.editCatatan(newNominal, newDeskripsi)
                Toast.makeText(requireContext(), "Catatan berhasil diubah!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Nominal dan deskripsi tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete button functionality
        binding.deleteIcon.setOnClickListener {
            catatanViewModel.deleteSelectedCatatan()
            Toast.makeText(requireContext(), "Catatan berhasil dihapus!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        binding.topBar.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
