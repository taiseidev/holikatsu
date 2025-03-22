package com.example.holikatsu.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holikatsu.domain.model.DayType
import com.example.holikatsu.domain.model.Plan
import com.example.holikatsu.domain.model.RemainingTime
import com.example.holikatsu.domain.model.TimeState
import com.example.holikatsu.domain.model.decrementDay
import com.example.holikatsu.domain.model.decrementHour
import com.example.holikatsu.domain.model.decrementMinute
import com.example.holikatsu.domain.model.decrementSecond
import com.example.holikatsu.domain.model.toTimeState
import com.example.holikatsu.domain.usecase.CalculateRemainingTimeUseCase
import com.example.holikatsu.domain.usecase.GetDayTypeUseCase
import com.example.holikatsu.domain.usecase.GetNextHolidayUseCase
import com.example.holikatsu.domain.usecase.GetNextPlansUseCase
import com.example.holikatsu.domain.usecase.GetNextWeekday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calculateRemainingTimeUseCase: CalculateRemainingTimeUseCase,
    private val getNextHolidayUseCase: GetNextHolidayUseCase,
    private val getNextPlansUseCase: GetNextPlansUseCase,
    private val getDayTypeUseCase: GetDayTypeUseCase,
    private val getNextWeekday: GetNextWeekday,
) : ViewModel() {
    // 現在の日時から残り時間を計算し、表示するためのデータを保持する
    private val _remainingTime = MutableStateFlow<RemainingTime?>(null)
    val remainingTime: StateFlow<RemainingTime?> = _remainingTime.asStateFlow()

    // 次の予定を表示するためのデータを保持する
    private val _plans = MutableStateFlow<List<Plan>>(emptyList())
    val plans: StateFlow<List<Plan>> = _plans.asStateFlow()

    // 現在の日付が平日か休日かを保持する
    private val _dayType = MutableStateFlow<DayType?>(null)
    val dayType: StateFlow<DayType?> = _dayType.asStateFlow()

    init {
        initializeData()
        startCountdown()
    }

    private fun initializeData() {
        val today = LocalDate.now()

        viewModelScope.launch {
            _dayType.value = getDayTypeUseCase(today).getOrDefault(DayType.WEEKDAY)

            when (dayType.value) {
                /**
                 * 平日の場合
                 * - 次の休日を取得
                 * - 次の休日までの残り時間を計算
                 */
                DayType.WEEKDAY -> {
                    val nextHolidayMillis = getNextHolidayUseCase()
                    _remainingTime.value =
                        calculateRemainingTimeUseCase(nextHolidayMillis, System.currentTimeMillis())
                }

                /**
                 * 休日の場合
                 * - 次の平日を取得
                 * - 次の平日までの残り時間を計算
                 */
                DayType.Holiday -> {
                    val nextWeekdayMillis = getNextWeekday(today)
                    _remainingTime.value =
                        calculateRemainingTimeUseCase(nextWeekdayMillis, System.currentTimeMillis())
                }

                null -> {
                    // TODO: エラーハンドリング
                }
            }

            _plans.value = getNextPlansUseCase()

        }
    }

    private fun startCountdown() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                updateRemainingTime()
            }
        }
    }

    private suspend fun updateRemainingTime() {
        val currentRemainingTime = _remainingTime.value

        _remainingTime.value = when (currentRemainingTime?.toTimeState()) {
            TimeState.ZeroTimeRemaining -> {
                val today = LocalDate.now()
                val nextWeekday = getNextWeekday(today)
                withContext(Dispatchers.Default) {
                    calculateRemainingTimeUseCase(nextWeekday, System.currentTimeMillis())
                }.also {
                    _dayType.value = when (dayType.value) {
                        DayType.WEEKDAY -> DayType.Holiday
                        DayType.Holiday -> DayType.WEEKDAY
                        null -> return@also
                    }
                }
            }

            TimeState.TimeZero -> currentRemainingTime.decrementDay()
            TimeState.MinuteAndSecondZero -> currentRemainingTime.decrementHour()
            TimeState.SecondZero -> currentRemainingTime.decrementMinute()
            TimeState.CountingDown -> currentRemainingTime.decrementSecond()
            null -> {
                // TODO: エラーハンドリング
                throw Exception("RemainingTime is null")
            }
        }
    }
}