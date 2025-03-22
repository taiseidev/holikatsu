package com.example.holikatsu.ui.screen.home

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.holikatsu.R
import com.example.holikatsu.domain.model.DayType
import com.example.holikatsu.domain.model.Plan
import com.example.holikatsu.ui.component.CommonTopAppBar
import com.example.holikatsu.ui.screen.home.component.CountdownTimer
import com.example.holikatsu.ui.screen.home.component.PlanList
import com.example.holikatsu.ui.theme.HoliKatsuTheme
import com.example.holikatsu.ui.theme.ScaffoldBackground
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val remainingTime by viewModel.remainingTime.collectAsState()
    val plans by viewModel.plans.collectAsState()
    val dayType by viewModel.dayType.collectAsState()

    dayType?.let {
        HomeScreenContent(
            // TODO: Digitのサイズをハードコーディングしているので修正
            digitWidth = 70,
            days = remainingTime?.days?.toInt() ?: 0,
            hours = remainingTime?.hours?.toInt() ?: 0,
            minutes = remainingTime?.minutes?.toInt() ?: 0,
            seconds = remainingTime?.seconds?.toInt() ?: 0,
            plans = plans,
            dayType = it
        )
    }
}

@Composable
fun HomeScreenContent(
    digitWidth: Int = 100,
    days: Int,
    hours: Int,
    minutes: Int,
    seconds: Int,
    plans: List<Plan>,
    dayType: DayType
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScaffoldBackground)
    ) {
        Scaffold(
            containerColor = Color.Transparent, topBar = {
                CommonTopAppBar(
                    onActionIconClick = {
                        // TODO: アクションアイコンをタップした際の処理
                    })
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = if (dayType == DayType.WEEKDAY) R.string.home_motivation else R.string.enjoy_holiday),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    stringResource(id = if (dayType == DayType.WEEKDAY) R.string.home_next_holiday else R.string.until_finish_holiday),
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD4D4D4),
                )
                Spacer(modifier = Modifier.height(16.dp))
                CountdownTimer(
                    days = days,
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                    digitWidth = digitWidth
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    stringResource(id = R.string.home_plan_list),
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD4D4D4),
                )
                Spacer(modifier = Modifier.height(8.dp))
                PlanList(modifier = Modifier.weight(1f), plans = plans)
                Spacer(modifier = Modifier.height(16.dp))
                ElevatedButton(
                    onClick = {
                        // TODO: 休日の予定を追加
                    }, colors = ButtonColors(
                        containerColor = Color(0xFFEE7056),
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.White,
                    ), modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.home_add_plan))
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HolidayPreview() {
    HoliKatsuTheme {
        HomeScreenContent(
            // TODO: Digitのサイズをハードコーディングしているので修正
            digitWidth = 70, days = 6, hours = 12, minutes = 30, seconds = 45, plans = listOf(
                Plan(
                    id = 0, title = "映画鑑賞", description = "ららぽーとに行って映画を鑑賞する。"
                ),
                Plan(
                    id = 1, title = "プログラミング", description = "個人開発を行う。"
                ),
                Plan(
                    id = 2, title = "Netflix", description = "好きな映画を観る。"
                ),
            ), dayType = DayType.Holiday
        )
    }
}

@Composable
fun CircularCountdownClockStyle(
    totalTime: Int = 60, // 秒数
    modifier: Modifier = Modifier.size(200.dp),
    strokeColor: Color = Color(0xFFEF6C00),
    strokeWidth: Dp = 12.dp
) {
    val sweepAngle = remember { Animatable(120f) }
    var remainingTime by remember { mutableStateOf(totalTime) }

    // タイマー + アニメーション制御
    LaunchedEffect(key1 = remainingTime) {
        if (remainingTime > 0) {
            delay(1000L)
            remainingTime--

            val progress = (totalTime - remainingTime).toFloat() / totalTime.toFloat()
            val targetSweep = progress * 360f
            sweepAngle.animateTo(targetSweep)
        }
    }

    Box(
        contentAlignment = Alignment.Center, modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = strokeWidth.toPx()
            val size = size.minDimension
            val arcSize = Size(size, size)

            // 背景（薄い円）
            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokePx)
            )

            // 進行中のアーク
            drawArc(
                color = strokeColor,
                startAngle = -90f, // ← 12時から開始
                sweepAngle = sweepAngle.value,
                useCenter = false,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        // 中央に残り秒数表示（任意）
        Text(
            text = "06:12:30:41",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CircularCountdownTimerPreview() {
    HoliKatsuTheme {
        CircularCountdownClockStyle(totalTime = 60)
    }
}

@Preview(showBackground = true)
@Composable
fun WeekDayPreview() {
    HoliKatsuTheme {
        HomeScreenContent(
            // TODO: Digitのサイズをハードコーディングしているので修正
            digitWidth = 70, days = 6, hours = 12, minutes = 30, seconds = 45, plans = listOf(
                Plan(
                    id = 0, title = "映画鑑賞", description = "ららぽーとに行って映画を鑑賞する。"
                ),
                Plan(
                    id = 1, title = "プログラミング", description = "個人開発を行う。"
                ),
                Plan(
                    id = 2, title = "Netflix", description = "好きな映画を観る。"
                ),
            ), dayType = DayType.WEEKDAY
        )
    }
}