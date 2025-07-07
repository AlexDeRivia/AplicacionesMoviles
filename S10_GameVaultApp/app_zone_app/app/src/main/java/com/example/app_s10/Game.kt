package com.example.app_s10

// app/src/main/java/com/example/app_s10/Game.kt
data class Game(
    val id: String = "",
    val title: String = "",
    val genre: String = "",
    val platform: String = "",
    val rating: Float = 0f,
    val description: String = "",
    val releaseYear: Int = 0,
    val completed: Boolean = false,
    val userId: String = "",
    val createdAt: Long = System.currentTimeMillis()
)