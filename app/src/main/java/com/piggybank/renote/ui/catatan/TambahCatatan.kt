package com.piggybank.renote.ui.catatan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentTambahBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TambahCatatan : Fragment() {

    private var _binding: FragmentTambahBinding? = null
    private val binding get() = _binding!!
    private var selectedDateCalendar: Calendar? = null // Deklarasi variabel selectedDateCalendar

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

        binding.iconCalendar.setOnClickListener {
            showDatePickerDialog()
        }

        return binding.root
    }

    private fun showDatePickerDialog() {
        val calendar = selectedDateCalendar ?: Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDateCalendar = calendar

                val selectedDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar.time)
                binding.textDate.apply {
                    text = selectedDate
                    setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.ivory))
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.visibility = View.VISIBLE

        _binding = null
    }
}
