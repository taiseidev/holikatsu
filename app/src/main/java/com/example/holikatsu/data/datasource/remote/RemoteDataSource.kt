package com.example.holikatsu.data.datasource.remote

import com.example.holikatsu.BuildConfig
import com.example.holikatsu.data.datasource.remote.model.HolidayResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getHolidays(): List<HolidayResponse> = withContext(Dispatchers.IO) {
        val response = httpClient.get(BuildConfig.HOLIDAY_API_BASE_URL)
        val data = response.body() as Map<String, String>
        data.map {
            HolidayResponse(
                date = it.key, name = it.value
            )
        }
    }
}