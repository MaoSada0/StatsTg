package telegram.entities.input
import kotlinx.serialization.Serializable

@Serializable
data class ResultTelegram (
    val about: String,
    val chats: ChatsContainer,
    val leftChats: ChatsContainer? = null,
)