package com.example.project.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingScreen(
    onBack: () -> Unit
) {
    var paths by remember { mutableStateOf(mutableListOf<Pair<Path, DrawingSettings>>()) }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentSettings by remember {
        mutableStateOf(DrawingSettings(color = Color.Black, strokeWidth = 5f))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Drawing") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            paths.clear()
                            currentPath = Path()
                        }
                    ) {
                        Text("Clear")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Простая панель инструментов
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Цвета
                listOf(Color.Black, Color.Red, Color.Blue, Color.Green).forEach { color ->
                    Button(
                        onClick = { currentSettings = currentSettings.copy(color = color) },
                        colors = ButtonDefaults.buttonColors(containerColor = color),
                        modifier = Modifier.size(40.dp)
                    ) {}
                }
            }

            // Холст для рисования
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPath = Path().apply {
                                    moveTo(offset.x, offset.y)
                                }
                            },
                            onDrag = { change, _ ->
                                currentPath.lineTo(change.position.x, change.position.y)
                            },
                            onDragEnd = {
                                paths.add(Pair(currentPath, currentSettings.copy()))
                                currentPath = Path()
                            }
                        )
                    }
            ) {
                // Рисуем сохранённые пути
                paths.forEach { (path, settings) ->
                    drawPath(
                        path = path,
                        color = settings.color,
                        style = Stroke(
                            width = settings.strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                // Рисуем текущий путь
                drawPath(
                    path = currentPath,
                    color = currentSettings.color,
                    style = Stroke(
                        width = currentSettings.strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}

data class DrawingSettings(
    val color: Color,
    val strokeWidth: Float
)
