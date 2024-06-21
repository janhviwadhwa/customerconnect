// File: MessageSender.kt
package com.abc.customerconnect

import com.google.firebase.firestore.FirebaseFirestore


class MessageSender {
    private val db = FirebaseFirestore.getInstance()

    fun sendMessage(senderId: String, receiverId: String, message: String) {
        val messageObj = Message(senderId, receiverId, message) // Corrected variable name
        db.collection("messages").add(messageObj)
            .addOnSuccessListener {
                // Optionally send FCM notification here
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
