package com.example.project.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_conversations")
data class AiConversation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userMessage: String,
    val aiResponse: String,
    val timestamp: Long = System.currentTimeMillis()
)