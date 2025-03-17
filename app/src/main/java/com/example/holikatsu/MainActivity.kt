package com.example.holikatsu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.holikatsu.domain.usecase.CalculateConsecutiveHolidaysUseCase
import com.example.holikatsu.domain.usecase.CalculateRemainingTimeUseCase
import com.example.holikatsu.domain.usecase.GetDayTypeUseCase
import com.example.holikatsu.domain.usecase.GetNextHolidayOrWeekendUseCase
import com.example.holikatsu.domain.usecase.GetNextPlansUseCase
import com.example.holikatsu.ui.screen.home.HomeScreen
import com.example.holikatsu.ui.screen.home.HomeViewModel
import com.example.holikatsu.ui.theme.HoliKatsuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HoliKatsuTheme {

                HomeScreen(
                    // TODO: HiltでDIする
                    HomeViewModel(
                        calculateRemainingTimeUseCase = CalculateRemainingTimeUseCase(),
                        calculateConsecutiveHolidaysUseCase = CalculateConsecutiveHolidaysUseCase(),
                        getNextHolidayOrWeekendUseCase = GetNextHolidayOrWeekendUseCase(),
                        getNextPlansUseCase = GetNextPlansUseCase(),
                        getDayTypeUseCase = GetDayTypeUseCase(),
                    )
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello World!", modifier = modifier
            .background(Color.Red)
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HoliKatsuTheme {
        Greeting("Android")
    }
}