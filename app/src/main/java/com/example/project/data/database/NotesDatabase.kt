package com.example.project.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.project.data.database.entities.Note
import com.example.project.data.database.entities.AiConversation

@Database(
    entities = [Note::class, AiConversation::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun aiConversationDao(): AiConversationDao
}