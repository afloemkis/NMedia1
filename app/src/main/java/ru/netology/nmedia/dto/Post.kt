package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 999,
    var likedByMe: Boolean = false,
    var shares: Int = 18999,
    var views: Int = 8900658
)