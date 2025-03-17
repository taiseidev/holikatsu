package com.example.holikatsu.domain.usecase

import com.example.holikatsu.domain.model.RemainingTime
import java.util.concurrent.TimeUnit

/**
 * 指定した日時までの残り時間を計算するユースケース
 */
class CalculateRemainingTimeUseCase {
    operator fun invoke(
        targetMillis: Long,
        currentMillis: Long = System.currentTimeMillis()
    ): RemainingTime {
        val remainingMillis = maxOf(targetMillis - currentMillis, 0L)

        val days = TimeUnit.MILLISECONDS.toDays(remainingMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(remainingMillis) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMillis) % 60

        return RemainingTime(days, hours, minutes, seconds)
    }
}