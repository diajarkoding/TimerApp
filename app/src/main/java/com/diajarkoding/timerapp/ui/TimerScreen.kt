package com.diajarkoding.timerapp.ui

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // ✅ pisahkan atas-bawah
        ) {
            Spacer(modifier = Modifier.height(1.dp)) // untuk jarak dari TopAppBar, opsional

            // TimerPicker di tengah
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TimerPicker()
            }

            // Start button di bawah
            StartButton(onClick = {})
        }
    }
}


@Composable
fun TimerPicker(modifier: Modifier = Modifier) {
    val hoursList = (0..23).map { it.toString().padStart(2, '0') }
    val minutesList = (0..59).map { it.toString().padStart(2, '0') }
    val secondsList = (0..59).map { it.toString().padStart(2, '0') }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimerPickerColumn(items = hoursList, label = "Hours", onValueChange = {})
        Spacer(modifier = Modifier.width(8.dp))
        TimerPickerColumn(items = minutesList, label = "Minutes", onValueChange = {})
        Spacer(modifier = Modifier.width(8.dp))
        TimerPickerColumn(items = secondsList, label = "Seconds", onValueChange = {})
    }
}

@Composable
fun TimerPickerColumn(
    items: List<String>,
    label: String,
    initialValue: String = "00",
    onValueChange: (String) -> Unit
) {
    val itemHeight = 48.dp
    val visibleItems = 3
    val listState = rememberLazyListState(items.indexOf(initialValue).coerceAtLeast(0))
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .height(itemHeight * visibleItems)
                .width(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = itemHeight),
            flingBehavior = flingBehavior
        ) {
            items(items.size) { index ->
                val isCenter = (listState.firstVisibleItemIndex + 1) == index
                Text(
                    text = items[index],
                    fontSize = 32.sp,
                    color = if (isCenter) Color.Black else Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun StartButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(percent = 50)
    ) {
        Text(text = "Start", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview() {
    TimerScreen()
}
