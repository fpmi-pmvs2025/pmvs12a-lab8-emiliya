package com.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.project.data.database.entities.Note
import com.example.project.ui.screens.*
import com.example.project.viewmodel.MainViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    isDarkTheme: Boolean = false,
    onThemeChange: () -> Unit = {}
) {
    val viewModel: MainViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val notes by viewModel.notes.collectAsState()
            HomeScreen(
                notes = notes,
                onNoteClick = { selectedNote ->
                    navController.navigate("edit/${selectedNote.id}")
                },
                onSearch = { query ->
                    viewModel.searchNotes(query)
                },
                onAddNote = {
                    navController.navigate("edit/0")
                },
                onNavigateToAI = {
                    navController.navigate("ai")
                },
                onNavigateToDraw = {
                    navController.navigate("draw")
                }
            )
        }

        composable(
            "edit/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val notes by viewModel.notes.collectAsState()
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0

            val note = if (noteId == 0) {
                Note(title = "", content = "")
            } else {
                notes.find { it.id == noteId } ?: Note(title = "", content = "")
            }

            NoteEditScreen(
                note = note,
                onSave = {
                    viewModel.saveNote(it)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        composable("ai") {
            val conversations by viewModel.conversations.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            AIChatScreen(
                conversations = conversations,
                isLoading = isLoading,
                onSend = { message ->
                    viewModel.sendMessageToAI(message)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("draw") {
            DrawingScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}