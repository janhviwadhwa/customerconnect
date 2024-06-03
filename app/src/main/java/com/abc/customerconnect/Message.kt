data class Message(
    val content: String = "",
    val senderName: String = "",
    val timestamp: String = ""
) {
    // No-argument constructor required by Firebase for deserialization
    constructor() : this("", "", "")
}
