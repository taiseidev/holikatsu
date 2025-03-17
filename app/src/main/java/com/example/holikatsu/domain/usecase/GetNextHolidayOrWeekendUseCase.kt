package com.example.holikatsu.domain.usecase

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 次の祝日または土日または平日を取得するユースケース
 */
class GetNextHolidayOrWeekendUseCase {
    operator fun invoke(): Long {
        // TODO: 修正する
        /**
         * 1. APIから祝日リストを取得
         * 2. 次の祝日を取得
         * 3. 次の土曜日を取得
         * 4. 祝日と土曜日のうち、早い方を選択して返す
         * 5. ただし、祝日が土日の場合は土曜日を返す
         * 6.　本日が祝日や土日の場合も考慮する（次の平日までの時間を計算する）
         */
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = dateFormat.parse("2025/03/20 00:00:00")
        return date?.time ?: System.currentTimeMillis()
    }

}