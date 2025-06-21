package com.example.project.data.remote.models

import com.google.gson.annotations.SerializedName

data class GroqRequest(
    @SerializedName("model")
    val model: String,
    @SerializedName("messages")
    val messages: List<Message>,
    @SerializedName("max_tokens")
    val maxTokens: Int = 1000,
    @SerializedName("temperature")
    val temperature: Double = 0.7
) {
    data class Message(
        @SerializedName("role")
        val role: String,
        @SerializedName("content")
        val content: String
    )
}