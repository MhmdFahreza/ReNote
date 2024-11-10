package com.piggybank.renote.ui.rekening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.piggybank.renote.databinding.FragmentRekeningBinding

class RekeningFragment : Fragment() {

    private var _binding: FragmentRekeningBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rekeningViewModel =
            ViewModelProvider(this).get(RekeningViewModel::class.java)

        _binding = FragmentRekeningBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRekening
        rekeningViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}