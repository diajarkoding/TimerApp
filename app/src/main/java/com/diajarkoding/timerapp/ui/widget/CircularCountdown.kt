package com.diajarkoding.timerapp.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.concurrent.TimeUnit

@Composable
fun CircularCountdown(
    remainingTimeInMillis: Long,
    totalTimeInMillis: Long
) {
    val progress = (remainingTimeInMillis.toFloat() / totalTimeInMillis.toFloat()).coerceIn(0f, 1f)

    val sweepAngle by animateFloatAsState(
        targetValue = 360f * progress,
        animationSpec = tween(durationMillis = 100),
        label = "Sweep Angle"
    )

    val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(remainingTimeInMillis) % 60
    val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(remainingTimeInMillis) % 60
    val remainingHours = TimeUnit.MILLISECONDS.toHours(remainingTimeInMillis)

    val formattedTime = String.format(
        "%02d:%02d:%02d",
        remainingHours, remainingMinutes, remainingSeconds
    )

    val strokeWidth = 20.dp
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(1f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color.Red.copy(alpha = 0.1f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
            drawArc(
                color = Color.Red,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formattedTime,
                fontSize = 48.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
            Text(
                text = "Timer",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}