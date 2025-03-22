package com.example.holikatsu.data.datasource.remote.model

import kotlinx.serialization.Serializable

/**
 *　Holidays JP APIから取得した祝日情報
 */
@Serializable
data class HolidayResponse(
    val date: String,
    val name: String,
)