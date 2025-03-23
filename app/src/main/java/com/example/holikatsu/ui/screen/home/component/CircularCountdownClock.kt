package com.example.holikatsu.ui.screen.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.holikatsu.R
import com.example.holikatsu.domain.model.DayType
import com.example.holikatsu.ui.theme.HoliKatsuTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularCountdownClock(
    modifier: Modifier = Modifier,
    angle: Double,
    dayType: DayType,
    days: String,
    hours: String,
    minutes: String,
    seconds: String,
) {
    Box(
        modifier = modifier.size(280.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val strokeWidth = 0.dp.toPx()
            val dotRadius = 0.dp.toPx()

            val radius = size.minDimension / 2 - strokeWidth / 2 - dotRadius
            val ang = angle - 90f

            val dotX = centerX + radius * cos(Math.toRadians(ang)).toFloat()
            val dotY = centerY + radius * sin(Math.toRadians(ang)).toFloat()

            drawArc(
                color = Color.Gray.copy(alpha = 0.1f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 40.dp.toPx())
            )

            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 30.dp.toPx())
            )

            drawArc(
                color = Color(0xFFA9A9A4),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx())
            )

            drawArc(
                color = Color(0xFFE57373),
                startAngle = -90f,
                sweepAngle = angle.toFloat(),
                useCenter = false,
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )

            drawCircle(
                color = Color(0xFFE57373), radius = 25.dp.toPx(), center = Offset(dotX, dotY)
            )

            drawCircle(
                color = Color(0xFFFFFFFF), radius = 15.dp.toPx(), center = Offset(dotX, dotY)
            )

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(id = if (dayType == DayType.WEEKDAY) R.string.home_next_holiday else R.string.until_finish_holiday),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF777777),
            )
            Text(
                "$days:$hours:$minutes:$seconds",
                color = Color(0xFF333333),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularCountdownClockPreview() {
    HoliKatsuTheme {
        CircularCountdownClock(
            dayType = DayType.WEEKDAY,
            days = "1",
            hours = "23",
            minutes = "59",
            seconds = "59",
            angle = 180.0
        )
    }
}

