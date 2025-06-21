package com.example.project

import android.app.Application
import androidx.room.Room
import com.example.project.data.database.NotesDatabase
import com.example.project.data.remote.GroqApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SmartNotesApp : Application() {
    companion object {
        lateinit var database: NotesDatabase
        lateinit var groqApi: GroqApi
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes_database"
        ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.groq.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        groqApi = retrofit.create(GroqApi::class.java)
    }
}