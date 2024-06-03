package com.abc.customerconnect

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MessageActivity : AppCompatActivity() {

    private lateinit var ownerNameTextView: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        // Find views by their IDs
        ownerNameTextView = findViewById(R.id.ownerNameTextView)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        val ownerName = intent.getStringExtra("OWNER_NAME")
        val ownerEmail = intent.getStringExtra("OWNER_EMAIL")

        if (ownerName != null) {
            ownerNameTextView.text = ownerName
        } else {
            Toast.makeText(this, "Owner name not found", Toast.LENGTH_SHORT).show()
        }

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()
            if (message.isNotEmpty()) {
                // Implement the message sending logic here
                Toast.makeText(this, "Message sent to $ownerName", Toast.LENGTH_SHORT).show()
                // You can add logic to send the message, such as making a network request.
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
