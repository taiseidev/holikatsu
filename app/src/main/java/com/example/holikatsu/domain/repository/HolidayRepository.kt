package com.example.holikatsu.domain.repository

import com.example.holikatsu.data.datasource.remote.model.HolidayResponse

interface HolidayRepository {
    suspend fun getHolidays(): Result<List<HolidayResponse>>
}