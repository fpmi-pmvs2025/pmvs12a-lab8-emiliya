package com.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.SmartNotesApp
import com.example.project.data.database.entities.AiConversation
import com.example.project.data.database.entities.Note
import com.example.project.data.remote.models.GroqRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val noteDao = SmartNotesApp.database.noteDao()
    private val aiConversationDao = SmartNotesApp.database.aiConversationDao()
    private val groqApi = SmartNotesApp.groqApi

    private val apiKey = "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"

    private val currentModel = "llama3-8b-8192"

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _conversations = MutableStateFlow<List<AiConversation>>(emptyList())
    val conversations: StateFlow<List<AiConversation>> = _conversations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadNotes()
        loadConversations()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            noteDao.getAllNotes().collect {
                _notes.value = it
            }
        }
    }

    fun searchNotes(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                noteDao.getAllNotes().collect {
                    _notes.value = it
                }
            } else {
                noteDao.searchNotes(query).collect {
                    _notes.value = it
                }
            }
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            if (note.id == 0) {
                noteDao.insertNote(note)
            } else {
                noteDao.updateNote(note)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.deleteNote(note)
        }
    }

    private fun loadConversations() {
        viewModelScope.launch {
            aiConversationDao.getAllConversations().collect {
                _conversations.value = it
            }
        }
    }

    fun sendMessageToAI(message: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = GroqRequest(
                    model = currentModel,
                    messages = listOf(
                        GroqRequest.Message(
                            role = "system",
                            content = "You are a helpful assistant for a note-taking app."
                        ),
                        GroqRequest.Message(
                            role = "user",
                            content = message
                        )
                    )
                )

                val response = groqApi.chatCompletion(
                    authorization = "Bearer $apiKey",
                    request = request
                )

                val aiResponse = response.choices.firstOrNull()
                    ?.message?.content ?: "No response received"

                val conversation = AiConversation(
                    userMessage = message,
                    aiResponse = aiResponse
                )

                aiConversationDao.insertConversation(conversation)

            } catch (e: Exception) {
                val conversation = AiConversation(
                    userMessage = message,
                    aiResponse = "Error: ${e.message}"
                )
                aiConversationDao.insertConversation(conversation)
            } finally {
                _isLoading.value = false
            }
        }
    }
}