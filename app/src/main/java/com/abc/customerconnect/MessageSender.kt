// File: MessageSender.kt
package com.abc.customerconnect
import Message
import com.google.firebase.firestore.FirebaseFirestore


class MessageSender {
    private val db = FirebaseFirestore.getInstance()

    fun sendMessage(senderId: String, receiverId: String, message: String) {
        val messageObj = Message(senderId, receiverId, message)
        db.collection("messages").add(messageObj)
            .addOnSuccessListener {
                // Optionally send FCM notification here
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
