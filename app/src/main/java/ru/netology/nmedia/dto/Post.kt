package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Int = 999,
    val shares: Int = 18999,
    val views: Int = 8900658,
    val video: String? = null
)