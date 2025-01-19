package telegram.entities.input
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Message(
    val id: Int,
    val type: String,
    val date: String, // ISO 8601
    val date_unixtime: String,
    val from: String? = null,
    val from_id: String? = null,
    val text: JsonElement,
    val text_entities: List<MessageEntity>,
    val message_id: Int? = null,
    val media_type: String? = null
)

@Serializable
data class MessageEntity(
    val type: String,
    val text: String
)

