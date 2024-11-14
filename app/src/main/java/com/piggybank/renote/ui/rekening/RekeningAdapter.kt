package com.piggybank.renote.ui.rekening

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.piggybank.renote.R

class RekeningAdapter(
    private val rekeningList: List<Rekening>,
    private val onArrowClick: (Rekening) -> Unit,
    private val formatCurrency: (Long) -> String // Pass formatCurrency function
) : RecyclerView.Adapter<RekeningAdapter.RekeningViewHolder>() {

    class RekeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.rekening_name)
        val balanceTextView: TextView = itemView.findViewById(R.id.account_amount)
        val arrowIcon: View = itemView.findViewById(R.id.arrow_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RekeningViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rekening, parent, false)
        return RekeningViewHolder(view)
    }

    override fun onBindViewHolder(holder: RekeningViewHolder, position: Int) {
        val rekening = rekeningList[position]
        holder.nameTextView.text = rekening.name
        holder.balanceTextView.text = formatCurrency(rekening.uang)
        holder.arrowIcon.setOnClickListener { onArrowClick(rekening) }
    }

    override fun getItemCount() = rekeningList.size
}
