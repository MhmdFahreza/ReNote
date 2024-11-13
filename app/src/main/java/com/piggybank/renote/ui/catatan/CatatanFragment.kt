package com.piggybank.renote.ui.catatan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.piggybank.renote.R
import com.piggybank.renote.databinding.FragmentCatatanBinding
import java.util.Calendar

class CatatanFragment : Fragment() {

    private var _binding: FragmentCatatanBinding? = null
    private val binding get() = _binding!!
    private lateinit var catatanAdapter: CatatanAdapter
    private lateinit var catatanViewModel: CatatanViewModel

    // Variable to store the last selected date
    private var selectedDate: Calendar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        catatanViewModel = ViewModelProvider(this).get(CatatanViewModel::class.java)

        catatanAdapter = CatatanAdapter(navigateToEdit = {
            findNavController().navigate(R.id.navigation_editCatatan)
        })

        binding.transactionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = catatanAdapter
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(itemDecoration)
        }

        catatanViewModel.catatanList.observe(viewLifecycleOwner) { catatanList ->
            catatanAdapter.submitList(catatanList)
        }

        binding.catatanAdd.setOnClickListener {
            findNavController().navigate(R.id.navigation_tambahCatatan)
        }

        // Set up the DatePickerDialog for calendar_button
        binding.calendarButton.setOnClickListener {
            showDatePickerDialog()
        }

        return root
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
