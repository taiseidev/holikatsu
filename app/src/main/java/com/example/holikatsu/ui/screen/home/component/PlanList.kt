package com.example.holikatsu.ui.screen.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.holikatsu.domain.model.Plan
import com.example.holikatsu.ui.theme.HoliKatsuTheme

@Composable
fun PlanList(modifier: Modifier = Modifier, plans: List<Plan>) {
    Column(
        modifier = modifier
    ) {
        plans.take(3).forEach { PlanListItem(plan = it) }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanListPreview() {
    HoliKatsuTheme {
        PlanList(
            plans = listOf(
                Plan(
                    id = 1, title = "タイトル1", description = "説明1"
                ),
                Plan(
                    id = 2, title = "タイトル2", description = "説明2"
                ),
                Plan(
                    id = 3, title = "タイトル3", description = "説明3"
                ),
            )
        )
    }
}