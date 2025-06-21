package com.example.project.data.remote.models

import com.google.gson.annotations.SerializedName

data class GroqResponse(
    @SerializedName("choices")
    val choices: List<Choice>,
    @SerializedName("usage")
    val usage: Usage? = null
) {
    data class Choice(
        @SerializedName("message")
        val message: Message,
        @SerializedName("finish_reason")
        val finishReason: String? = null
    )

    data class Message(
        @SerializedName("role")
        val role: String,
        @SerializedName("content")
        val content: String
    )

    data class Usage(
        @SerializedName("prompt_tokens")
        val promptTokens: Int,
        @SerializedName("completion_tokens")
        val completionTokens: Int,
        @SerializedName("total_tokens")
        val totalTokens: Int
    )
}