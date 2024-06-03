package com.abc.customerconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OwnerListAdapter(private val onItemClick: (Owner) -> Unit) : RecyclerView.Adapter<OwnerListAdapter.ViewHolder>() {

    private var ownerList = mutableListOf<Owner>()

    fun submitList(list: List<Owner>) {
        ownerList.clear()
        ownerList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_owner, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val owner = ownerList[position]
        holder.bind(owner)
        holder.itemView.setOnClickListener { onItemClick(owner) }
    }

    override fun getItemCount() = ownerList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.ownerNameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.ownerEmailTextView)

        fun bind(owner: Owner) {
            nameTextView.text = owner.name
            emailTextView.text = owner.email
        }
    }
}
