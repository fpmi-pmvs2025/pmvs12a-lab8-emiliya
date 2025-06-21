package com.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project.data.database.entities.Note
import com.example.project.ui.components.NoteCard
import com.example.project.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onSearch: (String) -> Unit,
    onAddNote: () -> Unit,
    onNavigateToAI: () -> Unit,
    onNavigateToDraw: () -> Unit
) {
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Notes") },
                actions = {
                    IconButton(onClick = onNavigateToAI) {
                        Icon(Icons.Default.Chat, contentDescription = "AI Chat")
                    }
                    IconButton(onClick = onNavigateToDraw) {
                        Icon(Icons.Default.Draw, contentDescription = "Draw")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNote
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SearchBar(
                query = query,
                onQueryChange = {
                    query = it
                    onSearch(it)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (notes.isEmpty()) {
                Text(
                    text = "No notes yet. Create your first note!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(notes) { note ->
                        NoteCard(
                            note = note,
                            onClick = { onNoteClick(note) }
                        )
                    }
                }
            }
        }
    }
}