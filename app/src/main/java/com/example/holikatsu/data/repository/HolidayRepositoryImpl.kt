package com.example.holikatsu.data.repository

import com.example.holikatsu.data.datasource.remote.RemoteDataSource
import com.example.holikatsu.data.datasource.remote.model.HolidayResponse
import com.example.holikatsu.domain.repository.HolidayRepository
import javax.inject.Inject

class HolidayRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : HolidayRepository {
    override suspend fun getHolidays(): Result<List<HolidayResponse>> =
        runCatching {
            remoteDataSource.getHolidays()
        }
}