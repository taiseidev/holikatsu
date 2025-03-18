package com.example.holikatsu.domain.usecase

import javax.inject.Inject

/**
 * 連続する祝日を計算するユースケース
 */
class CalculateConsecutiveHolidaysUseCase @Inject constructor() {
    // TODO: 連続する祝日を計算する
    operator fun invoke(): List<String> {
        return listOf(
            "12/03", "12/04", "12/03", "12/04", "12/03", "12/04", "12/03", "12/04"
        )
    }

}