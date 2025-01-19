package telegram.entities.input
import kotlinx.serialization.Serializable

@Serializable
data class ChatsContainer(
    val about: String,
    val list: List<Chat>
)
