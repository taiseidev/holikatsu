package com.example.holikatsu.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holikatsu.domain.model.DayType
import com.example.holikatsu.domain.model.Plan
import com.example.holikatsu.domain.model.RemainingTime
import com.example.holikatsu.domain.usecase.CalculateConsecutiveHolidaysUseCase
import com.example.holikatsu.domain.usecase.CalculateRemainingTimeUseCase
import com.example.holikatsu.domain.usecase.GetDayTypeUseCase
import com.example.holikatsu.domain.usecase.GetNextHolidayOrWeekendUseCase
import com.example.holikatsu.domain.usecase.GetNextPlansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calculateRemainingTimeUseCase: CalculateRemainingTimeUseCase,
    private val calculateConsecutiveHolidaysUseCase: CalculateConsecutiveHolidaysUseCase,
    private val getNextHolidayOrWeekendUseCase: GetNextHolidayOrWeekendUseCase,
    private val getNextPlansUseCase: GetNextPlansUseCase,
    private val getDayTypeUseCase: GetDayTypeUseCase
) : ViewModel() {
    private val _remainingTime = MutableStateFlow<RemainingTime?>(null)
    val remainingTime: StateFlow<RemainingTime?> = _remainingTime.asStateFlow()

    private val _nextHolidays = MutableStateFlow<List<String>>(emptyList())
    val nextHolidays: StateFlow<List<String>> = _nextHolidays.asStateFlow()

    private val _plans = MutableStateFlow<List<Plan>>(emptyList())
    val plans: StateFlow<List<Plan>> = _plans.asStateFlow()

    private val _dayType = MutableStateFlow<DayType?>(null)
    val dayType: StateFlow<DayType?> = _dayType.asStateFlow()

    init {
        initializeData()
        startCountdown()
    }

    private fun initializeData() {
        viewModelScope.launch {
            _dayType.value = getDayTypeUseCase()
            _nextHolidays.value = calculateConsecutiveHolidaysUseCase()
            _plans.value = getNextPlansUseCase()

            val nextHolidayMillis = getNextHolidayOrWeekendUseCase()
            _remainingTime.value =
                calculateRemainingTimeUseCase(nextHolidayMillis, System.currentTimeMillis())
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

    private fun updateRemainingTime() {
        val current = _remainingTime.value
        val newTime = when {
            current?.days?.toInt() == 0 && current.hours.toInt() == 0 && current.minutes.toInt() == 0 && current.seconds.toInt() == 0 -> {
                getNextHolidayOrWeekendUseCase().let {
                    calculateRemainingTimeUseCase(it, System.currentTimeMillis())
                }
            }

            current?.hours?.toInt() == 0 && current.minutes.toInt() == 0 && current.seconds.toInt() == 0 -> {
                current.copy(days = current.days - 1, hours = 23, minutes = 59, seconds = 59)
            }

            current?.minutes?.toInt() == 0 && current.seconds.toInt() == 0 -> {
                current.copy(hours = current.hours - 1, minutes = 59, seconds = 59)
            }

            current?.seconds?.toInt() == 0 -> {
                current.copy(minutes = current.minutes - 1, seconds = 59)
            }

            else -> {
                current?.copy(seconds = current.seconds - 1)
            }
        }
        _remainingTime.value = newTime
    }
}