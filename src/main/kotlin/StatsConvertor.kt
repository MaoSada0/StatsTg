import telegram.entities.input.ResultTelegram
import telegram.entities.statistics.ChatForMapping
import telegram.entities.statistics.ChatStatistic
import kotlinx.serialization.json.Json
import telegram.entities.statistics.StatisticsResult
import telegram.entities.statistics.StatisticsOfYear
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StatsConvertor {
    fun getStats(nickname: String, filePath: String): String {
        val result = readJson(filePath)
        val stats = StatisticsResult(nickname)
        val nicknameToItsChat: MutableMap<String, ChatForMapping> = mutableMapOf()

        result.chats.list.forEach { chat ->
            val chatForMapping =
                ChatForMapping(
                    name = chat.name ?: chat.type,
                    id = chat.id,
                    type = chat.type
                )

            chat.messages.forEach { message ->
                if ((message.from ?: "") == stats.username) {
                    val yearOfMessage = getYearOfMessage(message.date)
                    val statsByYear = chatForMapping.statsByYear
                    val chatStatistic = statsByYear.getOrPut(yearOfMessage) { ChatStatistic() }

                    when {
                        message.text is JsonArray && message.text.isNotEmpty() ->
                            chatStatistic.incrementTextMessage()

                        message.text is JsonPrimitive && message.text.isString && message.text.content.isNotBlank() ->
                            chatStatistic.incrementTextMessage()

                        message.media_type != null && message.media_type == "video_message" ->
                            chatStatistic.incrementVideoMessages()

                        message.media_type != null && message.media_type == "voice_message" ->
                            chatStatistic.incrementAudioMessages()

                        else -> chatStatistic.incrementAnotherMessages()
                    }

                    statsByYear.put(yearOfMessage, chatStatistic)
                }

            }

            nicknameToItsChat.put(chatForMapping.name, chatForMapping)
        }

        nicknameToItsChat.forEach { (nickname, chatForMapping) ->
            chatForMapping.statsByYear.forEach { (year, chatStatistic) ->
                val statisticsOfYear = stats.statisticsOfYear.getOrDefault(year, StatisticsOfYear(year))
                statisticsOfYear.chatStatistics.add(Pair(nickname, chatStatistic))
                stats.statisticsOfYear.put(year, statisticsOfYear)
            }
        }

        var ans = ""

        stats.statisticsOfYear.forEach { (year, statisticsOfYear) ->
            ans += "Год: ${year} \n"
            val st = statisticsOfYear.getSortedChatStatistics()

            var index = 1
            var totalByYear = 0

            st.forEach { p ->
                if (index < 11) {
                    ans +=
                        "Топ ${index++}: ${p.first} |" +
                            " гс: ${p.second.countAudioMessages}, " +
                            " видео: ${p.second.countVideoMessages}, " +
                            " текст: ${p.second.countTextMessages}, " +
                            " иное: ${p.second.countAnotherMessages}, " +
                            " итого: ${p.second.allMessages} \n"

                }

                totalByYear += p.second.allMessages

            }
            ans += "Итого за год: $totalByYear \n"
            ans += "------------- \n"
        }

        return ans
    }

    private fun getYearOfMessage(isoDate: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDate.parse(isoDate, formatter)

        return date.year
    }

    private val json = Json { ignoreUnknownKeys = true }

    private fun readJson(filePath: String): ResultTelegram {
        val jsonContent = File(filePath).readText()
        return json.decodeFromString(jsonContent)
    }
}