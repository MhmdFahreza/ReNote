package com.piggybank.renote.ui.rekening

import android.view.View
import androidx.fragment.app.Fragment
import com.piggybank.renote.R

class TambahRekening : Fragment(R.layout.fragment_tambah_rekening) {

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<View>(R.id.nav_view)?.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE
    }
}
