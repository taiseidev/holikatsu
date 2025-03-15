package com.example.holikatsu.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.holikatsu.ui.theme.HoliKatsuTheme

@Composable
fun CountdownTimer(days: Int, hours: Int, minutes: Int, seconds: Int, digitWidth: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CountdownDigit(days, "Days", width = digitWidth)
        CountdownDigit(hours, "Hours", width = digitWidth)
        CountdownDigit(minutes, "Minutes", width = digitWidth)
        CountdownDigit(seconds, "Seconds", width = digitWidth)
    }
}

@Preview(showBackground = true)
@Composable
fun CountdownTimerPreview() {
    HoliKatsuTheme {
        CountdownTimer(
            days = 3,
            hours = 6,
            minutes = 23,
            seconds = 59,
            digitWidth = 68
        )
    }
}