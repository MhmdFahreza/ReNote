package com.piggybank.renote.ui.rekening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentEditRekeningBinding

class EditRekening : Fragment(R.layout.fragment_edit_rekening) {

    private var _binding: FragmentEditRekeningBinding? = null
    private val binding get() = _binding!!

    // Menggunakan navArgs untuk menerima data dari fragment sebelumnya
    private val args: EditRekeningArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditRekeningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menyembunyikan BottomNavigationView
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.visibility = View.GONE

        // Menampilkan nama rekening yang dipilih di EditText yang hanya bisa dibaca
        binding.rekeningNameEdit.setText(args.rekening.name) // Nama rekening yang dipilih
        binding.rekeningNameEdit.isEnabled = false // Membuat EditText tidak bisa diedit

        binding.iconBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // Menampilkan kembali BottomNavigationView
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}
