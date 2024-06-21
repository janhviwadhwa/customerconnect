package com.abc.customerconnect

data class Message(
    val senderName: String = "",
    val senderEmail: String = "",
    val content: String = "",
    val timestamp: Long = 0
)
