package com.piggybank.renote.ui.catatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentTambahBinding

class TambahCatatan : Fragment() {

    private var _binding: FragmentTambahBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahBinding.inflate(inflater, container, false)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)

        bottomNavigationView?.visibility = View.GONE

        binding.iconBack.setOnClickListener {
            findNavController().navigate(R.id.navigation_catatan)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.visibility = View.VISIBLE

        _binding = null
    }
}
