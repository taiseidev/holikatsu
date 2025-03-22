package com.example.holikatsu.domain.usecase

import com.example.holikatsu.domain.model.DayType
import com.example.holikatsu.domain.repository.HolidayRepository
import com.example.holikatsu.util.defaultDateFormatter
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

/**
 * 現在の日付が平日か休日かを取得するユースケース
 */
class GetDayTypeUseCase @Inject constructor(
    private val holidayRepository: HolidayRepository
) {
    suspend operator fun invoke(
        today: LocalDate,
    ): Result<DayType> {
        return runCatching {
            // 祝日リストを取得し、次の休日を取得
            val holidays = holidayRepository.getHolidays().fold(onSuccess = { holidays ->
                holidays
            }, onFailure = {
                throw it
            })

            // 祝日リストにその日が含まれているか
            val isInHolidayList = holidays.any { holiday ->
                LocalDate.parse(holiday.date, defaultDateFormatter) == today
            }

            // 本日が土曜または日曜、祝日なら休日を返却
            if (today.dayOfWeek == DayOfWeek.SATURDAY || today.dayOfWeek == DayOfWeek.SUNDAY || !isInHolidayList) {
                DayType.Holiday
            } else {
                DayType.WEEKDAY
            }
        }
    }
}