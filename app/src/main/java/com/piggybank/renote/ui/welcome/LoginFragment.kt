package com.piggybank.renote.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.piggybank.renote.R
import com.piggybank.renote.ui.main.MainActivity

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set listener untuk grid_slider
        val gridSlider = view.findViewById<View>(R.id.grid_slider)
        gridSlider.setOnClickListener {
            navigateToMainActivity()
        }

        // Set listener untuk logo_card
        val logoCard = view.findViewById<View>(R.id.logo_card)
        logoCard.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}
