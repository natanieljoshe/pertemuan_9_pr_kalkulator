package com.example.kalkulator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    private val items: MutableList<HistoryItem> = mutableListOf()
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemContainer: LinearLayout = itemView.findViewById(R.id.itemContainer)
        val tvHistoryText: TextView = itemView.findViewById(R.id.tvHistoryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvHistoryText.text = item.expression
        if (item.isNegative) {
            // Merah apabila hasil kurang dari 0
            holder.itemContainer.setBackgroundColor(Color.parseColor("#E57373"))
            holder.tvHistoryText.setTextColor(Color.WHITE)
        } else {
            // Normal apabila hasil 0 atau lebih
            holder.itemContainer.setBackgroundColor(Color.parseColor("#F5F5F5"))
            holder.tvHistoryText.setTextColor(Color.parseColor("#212121"))
        }
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: HistoryItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun setItems(newItems: List<HistoryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
