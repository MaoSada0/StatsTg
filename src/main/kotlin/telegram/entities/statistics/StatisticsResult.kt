package telegram.entities.statistics

data class StatisticsResult(
    var username: String,
    var statisticsOfYear: MutableMap<Int, StatisticsOfYear> = mutableMapOf()
)
