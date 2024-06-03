package com.abc.customerconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomerListAdapter : RecyclerView.Adapter<CustomerListAdapter.ViewHolder>() {
    private var customerList = mutableListOf<Customer>()

    fun submitList(list: List<Customer>) {
        customerList.clear()
        customerList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = customerList[position]
        holder.bind(customer)
    }

    override fun getItemCount() = customerList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.customerNameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.customerEmailTextView)

        fun bind(customer: Customer) {
            nameTextView.text = customer.name
            emailTextView.text = customer.email
        }
    }
}
