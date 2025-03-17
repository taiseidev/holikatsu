package com.example.holikatsu.domain.usecase

import com.example.holikatsu.domain.model.Plan

/**
 * 次の休日の予定を取得するユースケース
 */
class GetNextPlansUseCase {
    operator fun invoke(): List<Plan> {
        // TODO: 次の休日の予定を取得する
        return listOf(
            Plan(id = 0, title = "映画鑑賞", description = "ららぽーとに行って映画を鑑賞する。"),
            Plan(id = 1, title = "プログラミング", description = "個人開発を行う。"),
            Plan(id = 2, title = "Netflix", description = "好きな映画を観る。")
        )
    }
}