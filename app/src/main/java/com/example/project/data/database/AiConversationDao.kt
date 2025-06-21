package com.example.project.data.database

import androidx.room.*
import com.example.project.data.database.entities.AiConversation
import kotlinx.coroutines.flow.Flow

@Dao
interface AiConversationDao {
    @Query("SELECT * FROM ai_conversations ORDER BY timestamp DESC")
    fun getAllConversations(): Flow<List<AiConversation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: AiConversation)

    @Delete
    suspend fun deleteConversation(conversation: AiConversation)
}