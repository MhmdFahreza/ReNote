package com.piggybank.renote.ui.catatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.piggybank.renote.databinding.FragmentCatatanBinding

class CatatanFragment : Fragment() {

    private var _binding: FragmentCatatanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val catatanViewModel = ViewModelProvider(this).get(CatatanViewModel::class.java)

        _binding = FragmentCatatanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        catatanViewModel.text.observe(viewLifecycleOwner) { newText ->
            binding.roundedCanvasTextView.text = newText
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
