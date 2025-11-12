package com.diajarkoding.timerapp.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimerPicker(
    modifier: Modifier = Modifier,
    initialHours: Int = 0,
    initialMinutes: Int = 0,
    initialSeconds: Int = 0,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit
) {
    val hoursList = (0..23).map { it.toString().padStart(2, '0') }
    val minutesList = (0..59).map { it.toString().padStart(2, '0') }
    val secondsList = (0..59).map { it.toString().padStart(2, '0') }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimerPickerColumn(
            items = hoursList,
            label = "hours",
            initialValue = hoursList.find { it.toInt() == initialHours } ?: "00",
            onValueChange = { onHoursChange(it) },
            modifier = Modifier.weight(1f)
        )
        TimerPickerColumn(
            items = minutesList,
            label = "min",
            initialValue = minutesList.find { it.toInt() == initialMinutes } ?: "00",
            onValueChange = { onMinutesChange(it) },
            modifier = Modifier.weight(1f)
        )
        TimerPickerColumn(
            items = secondsList,
            label = "sec",
            initialValue = secondsList.find { it.toInt() == initialSeconds } ?: "00",
            onValueChange = { onSecondsChange(it) },
            modifier = Modifier.weight(1f)
        )
    }
}