package com.abc.customerconnect

import Owner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OwnerListAdapter(
    private val owners: List<Owner>,
    private val onItemClick: (Owner) -> Unit
) : RecyclerView.Adapter<OwnerListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_owner, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val owner = owners[position]
        holder.bind(owner)
        holder.itemView.setOnClickListener {
            onItemClick(owner)
        }
    }

    override fun getItemCount(): Int {
        return owners.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ownerNameTextView: TextView = itemView.findViewById(R.id.owner_name)
        private val ownerEmailTextView: TextView = itemView.findViewById(R.id.owner_email)

        fun bind(owner: Owner) {
            ownerNameTextView.text = owner.ownerName
            ownerEmailTextView.text = owner.ownerEmail
        }
    }
}
