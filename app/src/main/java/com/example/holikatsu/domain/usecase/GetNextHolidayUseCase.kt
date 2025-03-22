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
 * 次の祝日または土日または平日を取得するユースケース
 */
class GetNextHolidayUseCase @Inject constructor(
    private val holidayRepository: HolidayRepository
) {
    suspend operator fun invoke(): Long {

        // 本日の日付を取得
        val today = LocalDate.now()

        // 祝日リストを取得し、次の休日を取得
        val nextHoliday = holidayRepository.getHolidays().fold(onSuccess = { holidays ->
            // 次の休日を取得
            getNextHoliday(
                holidays = holidays, today = today
            )
        }, onFailure = {
            throw it
        })

        return nextHoliday.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    @VisibleForTesting
    internal fun getNextHoliday(
        holidays: List<HolidayResponse>, today: LocalDate
    ): LocalDate {
        // 祝日リストを`LocalDate`に変換し、過去の日付をスキップ
        val holidayLocalDates = holidays.map { LocalDate.parse(it.date, defaultDateFormatter) }
            .filter { it.isAfter(today) || it == today }

        // ① 今日が `土曜日 or 日曜日` の場合、本日を返す
        if (today.dayOfWeek == DayOfWeek.SATURDAY || today.dayOfWeek == DayOfWeek.SUNDAY) {
            return today
        }

        // ② 次の土曜日を取得
        val nextSaturday = getNextSaturday(today)

        // ③ 次の祝日を取得
        val nextHoliday = holidayLocalDates.minByOrNull { it }

        // ④ 祝日リストが空なら、次の土曜日を返す
        if (nextHoliday == null) {
            return nextSaturday
        }

        // ⑤ 次の土曜日が `nextHoliday` より早いなら `nextSaturday` を返す
        return if (nextSaturday.isBefore(nextHoliday)) nextSaturday else nextHoliday
    }

    // 今日以降の次の土曜日を取得
    @VisibleForTesting
    internal fun getNextSaturday(today: LocalDate): LocalDate {
        if (today.dayOfWeek == DayOfWeek.SATURDAY) return today

        var nextSaturday = today.plusDays(1)
        while (nextSaturday.dayOfWeek != DayOfWeek.SATURDAY) {
            nextSaturday = nextSaturday.plusDays(1)
        }
        return nextSaturday
    }
}