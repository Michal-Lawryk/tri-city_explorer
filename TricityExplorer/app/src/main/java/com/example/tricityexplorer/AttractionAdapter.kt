package com.example.tricityexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tricityexplorer.databinding.ItemAttractionBinding
import com.example.tricityexplorer.models.Attraction

class AttractionAdapter(private val attractions: List<Attraction>, private val onItemClick: (Attraction) -> Unit) : RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val binding = ItemAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttractionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        val currentAttraction = attractions[position]
        holder.bind(currentAttraction)
        holder.itemView.setOnClickListener {
            onItemClick(currentAttraction)
        }
    }

    override fun getItemCount() = attractions.size

    class AttractionViewHolder(private val binding: ItemAttractionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(attraction: Attraction) {
            binding.nameTextView.text = attraction.name
            binding.descriptionTextView.text = attraction.description
        }
    }
}
