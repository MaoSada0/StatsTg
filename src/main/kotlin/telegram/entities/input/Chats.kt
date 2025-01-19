package telegram.entities.input
import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val type: String,
    val id: Long,
    val name: String? = null,
    val messages: List<Message>
)
