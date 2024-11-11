package com.piggybank.renote.ui.catatan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piggybank.renote.databinding.ItemCatatanBinding

class CatatanAdapter : RecyclerView.Adapter<CatatanAdapter.CatatanViewHolder>() {

    private var catatanList = listOf<Catatan>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val binding = ItemCatatanBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CatatanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        val catatan = catatanList[position]
        holder.bind(catatan)
    }

    override fun getItemCount(): Int = catatanList.size

    fun submitList(newList: List<Catatan>) {
        catatanList = newList
        notifyDataSetChanged()
    }

    class CatatanViewHolder(private val binding: ItemCatatanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(catatan: Catatan) {
            binding.categoryTextView.text = catatan.category
            binding.descriptionTextView.text = catatan.description
            binding.amountTextView.text = catatan.amount.toString()
        }
    }
}
