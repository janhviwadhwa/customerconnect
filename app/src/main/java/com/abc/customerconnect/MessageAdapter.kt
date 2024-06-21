package com.abc.customerconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(
    private val ownerId: String,
    private val ownerName: String,
    private val ownerEmail: String,
    private var messages: MutableList<Message>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.message_text)
        val senderInfo: TextView = itemView.findViewById(R.id.sender_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        holder.messageText.text = message.content
        holder.senderInfo.text = "${message.senderName}: ${message.senderEmail}"
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun updateMessages(updatedMessages: List<Message>) {
        messages.clear()
        messages.addAll(updatedMessages)
        notifyDataSetChanged()
    }
}
