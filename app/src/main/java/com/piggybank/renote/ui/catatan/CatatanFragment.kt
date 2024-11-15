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
import java.text.NumberFormat
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

        catatanAdapter = CatatanAdapter { catatan ->
            catatanViewModel.selectedCatatan = catatan
            findNavController().navigate(R.id.navigation_editCatatan)
        }

        binding.transactionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = catatanAdapter
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(itemDecoration)
        }

        catatanViewModel.catatanList.observe(viewLifecycleOwner) { catatanList ->
            catatanAdapter.submitList(catatanList)
        }

        // Inside your observe blocks in CatatanFragment
        catatanViewModel.totalPemasukan.observe(viewLifecycleOwner) { pemasukan ->
            val formattedPemasukan = NumberFormat.getNumberInstance(Locale.getDefault()).format(pemasukan)
            binding.textPemasukan.text = getString(R.string.pemasukan_text, formattedPemasukan)
        }

        catatanViewModel.totalPengeluaran.observe(viewLifecycleOwner) { pengeluaran ->
            val formattedPengeluaran = NumberFormat.getNumberInstance(Locale.getDefault()).format(pengeluaran)
            binding.textPengeluaran.text = getString(R.string.pengeluaran_text, formattedPengeluaran)
        }

        catatanViewModel.totalSaldo.observe(viewLifecycleOwner) { saldo ->
            val formattedSaldo = NumberFormat.getNumberInstance(Locale.getDefault()).format(saldo)
            val saldoText = if (saldo < 0) {
                getString(R.string.negative_saldo_text, formattedSaldo)
            } else {
                getString(R.string.positive_saldo_text, formattedSaldo)
            }
            binding.textTotal.text = saldoText
        }

        binding.catatanAdd.setOnClickListener {
            catatanViewModel.clearSelectedCatatan()
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
