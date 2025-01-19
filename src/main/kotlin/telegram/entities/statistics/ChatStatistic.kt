package telegram.entities.statistics

data class ChatStatistic(
    var countTextMessages: Int = 0,
    var countAudioMessages: Int = 0,
    var countVideoMessages: Int = 0,
    var countAnotherMessages: Int = 0,
    var allMessages: Int = 0
) {
    private fun incrementAllMessages() = allMessages++

    fun incrementTextMessage() {
        countTextMessages++
        incrementAllMessages()
    }

    fun incrementAudioMessages() {
        countAudioMessages++
        incrementAllMessages()
    }

    fun incrementVideoMessages() {
        countVideoMessages++
        incrementAllMessages()
    }

    fun incrementAnotherMessages() {
        countAnotherMessages++
        incrementAllMessages()
    }
}
