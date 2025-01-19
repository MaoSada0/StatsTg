package telegram.entities.statistics

data class StatisticsOfYear(
    val year: Int,
    var chatStatistics: MutableList<Pair<String, ChatStatistic>> = mutableListOf(),

) {
    fun getSortedChatStatistics(): MutableList<Pair<String, ChatStatistic>> =
        chatStatistics
            .sortedByDescending { it.second.allMessages }
            .toMutableList()
}
