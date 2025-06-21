package com.example.project.data.remote

import com.example.project.data.remote.models.GroqResponse
import com.example.project.data.remote.models.GroqRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GroqApi {
    @POST("openai/v1/chat/completions")
    suspend fun chatCompletion(
        @Header("Authorization") authorization: String,
        @Body request: GroqRequest
    ): GroqResponse
}