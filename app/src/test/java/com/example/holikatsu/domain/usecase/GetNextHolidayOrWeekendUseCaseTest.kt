package com.example.holikatsu.domain.usecase

import com.example.holikatsu.data.datasource.remote.model.HolidayResponse
import com.example.holikatsu.domain.repository.HolidayRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetNextHolidayOrWeekendUseCaseTest {

    private lateinit var getNextHolidayOrWeekendUseCase: GetNextHolidayUseCase

    @Before
    fun setUp() {
        val holidayRepository = mockk<HolidayRepository>()
        getNextHolidayOrWeekendUseCase = GetNextHolidayUseCase(holidayRepository)
    }

    @Test
    fun `test today is Saturday, should return today`() {
        val today = LocalDate.of(2025, 5, 3) // 土曜日
        val holidays = listOf(
            HolidayResponse(date = "2025-05-06", name = ""),
            HolidayResponse(date = "2025-05-10", name = "")
        )

        val result = getNextHolidayOrWeekendUseCase.getNextHoliday(holidays, today)
        assertThat(today).isEqualTo(result)
    }

    @Test
    fun `test today is Sunday, should return today`() {
        val today = LocalDate.of(2025, 5, 4) // 日曜日
        val holidays = listOf(
            HolidayResponse(date = "2025-05-06", name = ""),
            HolidayResponse(date = "2025-05-10", name = "")
        )

        val result = getNextHolidayOrWeekendUseCase.getNextHoliday(holidays, today)
        assertThat(today).isEqualTo(result)
    }

    @Test
    fun `test next holiday is earlier than next Saturday, should return next holiday`() {
        val today = LocalDate.of(2025, 5, 1) // 木曜日
        val holidays = listOf(
            HolidayResponse(date = "2025-05-02", name = ""), // 次の祝日（次の土曜日より早い）
            HolidayResponse(date = "2025-05-10", name = "")
        )

        val expected = LocalDate.of(2025, 5, 2)
        val result = getNextHolidayOrWeekendUseCase.getNextHoliday(holidays, today)
        assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `test next Saturday is earlier than next holiday, should return next Saturday`() {
        val today = LocalDate.of(2025, 5, 1) // 木曜日
        val holidays = listOf(
            HolidayResponse(date = "2025-05-05", name = ""), // 祝日（月曜日）
            HolidayResponse(date = "2025-05-10", name = "")
        )

        val expected = LocalDate.of(2025, 5, 3) // 次の土曜日
        val result = getNextHolidayOrWeekendUseCase.getNextHoliday(holidays, today)
        assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `test holidays list is empty, should return next Saturday`() {
        val today = LocalDate.of(2025, 5, 1) // 木曜日
        val holidays = emptyList<HolidayResponse>() // 祝日リストが空

        val expected = LocalDate.of(2025, 5, 3) // 次の土曜日
        val result = getNextHolidayOrWeekendUseCase.getNextHoliday(holidays, today)
        assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `test all holidays are in the past, should return next Saturday`() {
        val today = LocalDate.of(2025, 5, 10) // 土曜日
        val holidays = listOf(
            HolidayResponse(date = "2025-05-01", name = ""),
            HolidayResponse(date = "2025-05-05", name = "")
        ) // すべて過去の祝日

        val expected = today // 今日が土曜日なのでそのまま返す
        val result = getNextHolidayOrWeekendUseCase.getNextHoliday(holidays, today)
        assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `test next Saturday when today is Monday`() {
        val today = LocalDate.of(2025, 5, 5) // 月曜日
        val expectedSaturday = LocalDate.of(2025, 5, 10) // 土曜日
        val nextSaturday = getNextHolidayOrWeekendUseCase.getNextSaturday(today)
        assertThat(nextSaturday).isEqualTo(expectedSaturday)
    }

    @Test
    fun `test next Saturday when today is Thursday`() {
        val today = LocalDate.of(2025, 5, 1) // 木曜日
        val expectedSaturday = LocalDate.of(2025, 5, 3) // 土曜日
        val nextSaturday = getNextHolidayOrWeekendUseCase.getNextSaturday(today)
        assertThat(nextSaturday).isEqualTo(expectedSaturday)
    }

    @Test
    fun `test next Saturday when today is already Saturday`() {
        val today = LocalDate.of(2025, 5, 3) // 土曜日
        val expectedSaturday = LocalDate.of(2025, 5, 3) // 土曜日
        val nextSaturday = getNextHolidayOrWeekendUseCase.getNextSaturday(today)
        assertThat(nextSaturday).isEqualTo(expectedSaturday)
    }

    @Test
    fun `test next Saturday when today is Sunday`() {
        val today = LocalDate.of(2025, 5, 4) // 日曜日
        val expectedSaturday = LocalDate.of(2025, 5, 10) // 翌週の土曜日
        val nextSaturday = getNextHolidayOrWeekendUseCase.getNextSaturday(today)
        assertThat(nextSaturday).isEqualTo(expectedSaturday)
    }
}