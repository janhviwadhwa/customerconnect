package com.abc.customerconnect

import Message
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var messageList: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var sendButton: Button
    private lateinit var messageEditText: EditText
    private lateinit var ownerName: String
    private lateinit var ownerEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        auth = FirebaseAuth.getInstance()
        ownerName = intent.getStringExtra("ownerName") ?: ""
        ownerEmail = intent.getStringExtra("ownerEmail") ?: ""

        messageList = findViewById(R.id.message_list)
        sendButton = findViewById(R.id.send_button)
        messageEditText = findViewById(R.id.message_edit_text)

        messageAdapter = MessageAdapter()
        messageList.layoutManager = LinearLayoutManager(this)
        messageList.adapter = messageAdapter

        databaseReference = FirebaseDatabase.getInstance().getReference("messages").child(ownerName)

        sendButton.setOnClickListener {
            sendMessage()
        }

        listenForMessages()
    }

    private fun sendMessage() {
        val messageText = messageEditText.text.toString().trim()
        if (messageText.isNotEmpty()) {
            val currentUser = auth.currentUser
            currentUser?.let { user ->
                val senderName = user.displayName ?: "Anonymous"
                val senderEmail = user.email ?: "unknown@example.com"

                val message = Message(messageText, senderName, System.currentTimeMillis().toString())
                databaseReference.push().setValue(message)

                messageEditText.text.clear()
            }
        }
    }

    private fun listenForMessages() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                for (data in snapshot.children) {
                    val message = data.getValue(Message::class.java)
                    message?.let { messages.add(it) }
                }
                messageAdapter.submitList(messages)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}
