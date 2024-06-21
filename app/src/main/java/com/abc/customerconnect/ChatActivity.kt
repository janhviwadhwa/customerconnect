package com.abc.customerconnect

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var messageList: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var ownerId: String
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("messages")

        // Get owner details from intent extras
        ownerId = intent.getStringExtra("ownerId") ?: ""
        val ownerName = intent.getStringExtra("ownerName") ?: ""
        val ownerEmail = intent.getStringExtra("ownerEmail") ?: ""

        // Set up RecyclerView for messages
        messageList = findViewById(R.id.message_list)
        messageAdapter = MessageAdapter(ownerId, ownerName, ownerEmail, mutableListOf())
        messageList.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = messageAdapter
        }

        // Set up message input and send button
        messageInput = findViewById(R.id.message_input)
        sendButton = findViewById(R.id.send_button)
        sendButton.setOnClickListener { sendMessage() }

        // Retrieve and display messages for the selected owner
        retrieveMessages()
    }

    private fun retrieveMessages() {
        val currentUserEmail = auth.currentUser?.email

        databaseReference.child(ownerId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                for (messageSnapshot in dataSnapshot.children) {
                    val senderName = messageSnapshot.child("senderName").value?.toString() ?: ""
                    val senderEmail = messageSnapshot.child("senderEmail").value?.toString() ?: ""
                    val content = messageSnapshot.child("content").value?.toString() ?: ""
                    val timestamp = messageSnapshot.child("timestamp").getValue(Long::class.java) ?: 0

                    if (senderEmail == currentUserEmail) {
                        val message = Message(senderName, senderEmail, content, timestamp)
                        messages.add(message)
                    }
                }
                displayMessages(messages)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }

    private fun displayMessages(messages: List<Message>) {
        messageAdapter.updateMessages(messages)
        // Scroll to the bottom of the RecyclerView
        messageList.scrollToPosition(messages.size - 1)
    }

    private fun sendMessage() {
        val content = messageInput.text.toString().trim()
        if (content.isNotEmpty()) {
            val currentUser = auth.currentUser
            val senderName = currentUser?.displayName ?: "Anonymous"
            val senderEmail = currentUser?.email ?: "unknown@example.com"
            val timestamp = System.currentTimeMillis()

            val message = Message(senderName, senderEmail, content, timestamp)
            val newMessageRef = databaseReference.child(ownerId).push()
            newMessageRef.setValue(message)

            // Clear the input field after sending the message
            messageInput.text.clear()
        }
    }
}
