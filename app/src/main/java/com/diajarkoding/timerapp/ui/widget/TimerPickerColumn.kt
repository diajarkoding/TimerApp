package com.diajarkoding.timerapp.ui.widget

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerPickerColumn(
    items: List<String>,
    label: String,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    initialValue: String = "00"
) {
    val defaultIndex = items.indexOf(initialValue).coerceAtLeast(0)

    val middleIndex = Int.MAX_VALUE / 2
    val remainder = middleIndex % items.size
    val initialIndex = middleIndex - remainder + defaultIndex
    val listState = rememberLazyListState(initialIndex - 1)

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val centerIndex = listState.firstVisibleItemIndex + 1
            val actualIndex = (centerIndex % items.size + items.size) % items.size
            onValueChange(items[actualIndex].toInt())
        }
    }

    val flingBehavior = rememberSnapFlingBehavior(listState)
    val itemHeight = 60.dp
    val pickerHeight = itemHeight * 3

    Box(
        modifier = modifier
            .height(pickerHeight),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(Int.MAX_VALUE) { index ->
                val actualIndex = (index % items.size + items.size) % items.size
                val itemText = items[actualIndex]
                val isCenter = (listState.firstVisibleItemIndex + 1) == index

                Row(
                    modifier = Modifier.height(itemHeight),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = itemText,
                        fontSize = if (isCenter) 44.sp else 36.sp,
                        fontWeight = if (isCenter) FontWeight.Bold else FontWeight.Light,
                        color = if (isCenter) Color.Black else Color.LightGray,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCenter) Color.Black else Color.Transparent
                    )
                }
            }
        }
    }
}