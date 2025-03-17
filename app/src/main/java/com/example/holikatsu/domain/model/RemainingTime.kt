package com.example.holikatsu.domain.model

/**
 * 残り時間を保持するデータクラス
 */
data class RemainingTime(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
)