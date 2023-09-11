package ru.netology.nmedia.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Int = 0,
    val likes: Int = 0,
    val shares: Int = 0,
    val views: Int = 0,
    val videoLink: String? = null
) {

    fun likedByMeToBool(likedByMe: Int) = likedByMe == 1
    fun toDto() = Post(
        id,
        author,
        content,
        published,
        likedByMeToBool(likedByMe),
        likes,
        shares,
        views,
        videoLink)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.author,
                dto.content,
                dto.published,
                likedByMeToInt(dto.likedByMe),
                dto.likes,
                dto.shares,
                dto.views,
                dto.video)

        private fun likedByMeToInt(likedByMe: Boolean) = if (likedByMe) 1 else 0

    }
}