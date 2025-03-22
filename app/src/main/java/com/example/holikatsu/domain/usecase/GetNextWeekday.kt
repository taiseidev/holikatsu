package com.example.holikatsu.domain.usecase

import androidx.annotation.VisibleForTesting
import com.example.holikatsu.data.datasource.remote.model.HolidayResponse
import com.example.holikatsu.domain.repository.HolidayRepository
import com.example.holikatsu.util.defaultDateFormatter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

/**
 * 次の平日を計算するユースケース
 */
class GetNextWeekday @Inject constructor(
    private val holidayRepository: HolidayRepository
) {
    suspend operator fun invoke(
        today: LocalDate
    ): Long {

        // 祝日リストを取得し、次の休日を取得
        val holidays = holidayRepository.getHolidays().fold(onSuccess = { holidays ->
            getHolidays(
                holidays = holidays, today = today
            )
        }, onFailure = {
            // TODO: エラーハンドリング
            throw it
        })

        var date = today.plusDays(1)
        
        // LocalDateに変換して、土日または祝日の場合は次の日に進める
        while (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY || date in holidays) {
            date = date.plusDays(1)
        }

        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    /**
     * 本日以降の祝日を取得
     */
    @VisibleForTesting
    internal fun getHolidays(
        holidays: List<HolidayResponse>, today: LocalDate
    ): List<LocalDate> {
        return holidays.map { LocalDate.parse(it.date, defaultDateFormatter) }
            .filter { it.isAfter(today) || it == today }
    }

}