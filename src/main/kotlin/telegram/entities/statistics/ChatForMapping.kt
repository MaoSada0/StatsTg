package telegram.entities.statistics

data class ChatForMapping(
    val type: String,
    val id: Long,
    val name: String,
    val statsByYear: MutableMap<Int, ChatStatistic> = mutableMapOf()
)
