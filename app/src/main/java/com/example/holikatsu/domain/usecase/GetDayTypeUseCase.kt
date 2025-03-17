package com.example.holikatsu.domain.usecase

import com.example.holikatsu.domain.model.DayType

/**
 * 現在の日時が平日か休日かを取得するユースケース
 */
class GetDayTypeUseCase {
    operator fun invoke(): DayType {
        // TODO: 現在の日時が平日か休日かを判定する
        return DayType.Holiday
    }
}