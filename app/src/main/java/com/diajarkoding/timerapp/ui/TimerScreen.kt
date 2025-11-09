package com.diajarkoding.timerapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diajarkoding.timerapp.ui.widget.CircularCountdown
import com.diajarkoding.timerapp.ui.widget.ControlButtons
import com.diajarkoding.timerapp.ui.widget.StartButton
import com.diajarkoding.timerapp.ui.widget.TimerPicker
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {
    var isTimerRunning by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    var selectedHours by remember { mutableStateOf(0) }
    var selectedMinutes by remember { mutableStateOf(0) }
    var selectedSeconds by remember { mutableStateOf(0) }

    var totalTimeInMillis by remember { mutableStateOf(0L) }
    var remainingTimeInMillis by remember { mutableStateOf(0L) }
    var targetTime by remember { mutableStateOf(0L) }

    LaunchedEffect(key1 = isTimerRunning, key2 = isPaused) {
        if (isTimerRunning && !isPaused) {
            while (remainingTimeInMillis > 0) {
                delay(10)
                val newRemainingTime = (targetTime - System.currentTimeMillis()).coerceAtLeast(0L)
                remainingTimeInMillis = newRemainingTime

                if (newRemainingTime <= 0L) {
                    isTimerRunning = false
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    TextButton(onClick = {}) {
                        Text(
                            text = "Edit",
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                    }
                },
                title = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isTimerRunning) {
                RunningScreen(
                    remainingTimeInMillis = remainingTimeInMillis,
                    totalTimeInMillis = totalTimeInMillis,
                    isPaused = isPaused,
                    onCancelClick = {
                        isTimerRunning = false
                        remainingTimeInMillis = 0L
                    },
                    onPauseToggleClick = {
                        if (isPaused) {
                            targetTime = System.currentTimeMillis() + remainingTimeInMillis
                        }
                        isPaused = !isPaused
                    }
                )
            } else {
                SettingScreen(
                    onStartClick = {
                        val selectedTime = (selectedHours * 3600L + selectedMinutes * 60L + selectedSeconds) * 1000L
                        if (selectedTime > 0L) {
                            totalTimeInMillis = selectedTime
                            remainingTimeInMillis = selectedTime
                            targetTime = System.currentTimeMillis() + selectedTime
                            isTimerRunning = true
                            isPaused = false
                        }
                        print("Start clicked")
                    },
                    onHoursChange = { selectedHours = it },
                    onMinutesChange = { selectedMinutes = it },
                    onSecondsChange = { selectedSeconds = it }
                )
            }
        }
    }
}

@Composable
fun SettingScreen(
    onStartClick: () -> Unit,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit
) {
    Column {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            TimerPicker(
                onHoursChange = onHoursChange,
                onMinutesChange = onMinutesChange,
                onSecondsChange = onSecondsChange
            )
        }
        StartButton(onClick = onStartClick)
    }
}

@Composable
fun RunningScreen(
    remainingTimeInMillis: Long,
    totalTimeInMillis: Long,
    isPaused: Boolean,
    onCancelClick: () -> Unit,
    onPauseToggleClick: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CircularCountdown(
                remainingTimeInMillis = remainingTimeInMillis,
                totalTimeInMillis = totalTimeInMillis
            )
        }
        ControlButtons(
            isPaused = isPaused,
            onCancelClick = onCancelClick,
            onPauseToggleClick = onPauseToggleClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview() {
    TimerScreen()
}