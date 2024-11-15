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
    private val formatCurrency: (Long) -> String
) : RecyclerView.Adapter<RekeningAdapter.RekeningViewHolder>() {

    var selectedPosition: Int = RecyclerView.NO_POSITION
        set(value) {
            val previousPosition = field
            field = value
            if (previousPosition != RecyclerView.NO_POSITION) notifyItemChanged(previousPosition)
            if (value != RecyclerView.NO_POSITION) notifyItemChanged(value)
        }

    class RekeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.rekening_name)
        val balanceTextView: TextView = itemView.findViewById(R.id.account_amount)
        val arrowIcon: View = itemView.findViewById(R.id.arrow_icon)
        val statusTextView: TextView = itemView.findViewById(R.id.rekening_status)
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

        val isSelected = holder.adapterPosition == selectedPosition
        holder.itemView.isSelected = isSelected
        holder.statusTextView.visibility = if (isSelected) View.VISIBLE else View.GONE
        holder.statusTextView.text = if (isSelected)
            holder.itemView.context.getString(R.string.status_active)
        else ""

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            if (previousPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(previousPosition)
            }
            notifyItemChanged(selectedPosition)
        }

        holder.arrowIcon.setOnClickListener {
            onArrowClick(rekeningList[holder.adapterPosition])
        }
    }

    override fun getItemCount() = rekeningList.size
}