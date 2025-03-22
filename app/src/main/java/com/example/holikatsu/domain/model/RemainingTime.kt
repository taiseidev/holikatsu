package com.example.holikatsu.domain.model

/**
 * 残り時間を保持するデータクラス
 */
data class RemainingTime(
    val days: Long, val hours: Long, val minutes: Long, val seconds: Long
)

fun RemainingTime.toTimeState(): TimeState {
    return when {
        days == 0L && hours == 0L && minutes == 0L && seconds == 0L -> TimeState.ZeroTimeRemaining
        hours == 0L && minutes == 0L && seconds == 0L -> TimeState.TimeZero
        minutes == 0L && seconds == 0L -> TimeState.MinuteAndSecondZero
        seconds == 0L -> TimeState.SecondZero
        else -> TimeState.CountingDown
    }
}

fun RemainingTime.decrementDay(): RemainingTime = this.copy(
    days = this.days - 1, hours = 23, minutes = 59, seconds = 59
)

fun RemainingTime.decrementHour(): RemainingTime = this.copy(
    hours = this.hours - 1, minutes = 59, seconds = 59
)

fun RemainingTime.decrementMinute(): RemainingTime = this.copy(
    minutes = this.minutes - 1, seconds = 59
)

fun RemainingTime.decrementSecond(): RemainingTime = this.copy(
    seconds = this.seconds - 1
)