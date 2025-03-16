package com.example.holikatsu.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.holikatsu.domain.model.Plan
import com.example.holikatsu.ui.theme.HoliKatsuTheme
import com.example.holikatsu.ui.theme.LightGrayText
import com.example.holikatsu.ui.theme.PrimaryColor
import com.example.holikatsu.ui.theme.TextPrimaryColor

@Composable
fun PlanListItem(plan: Plan) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = PrimaryColor), contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    modifier = Modifier.size(36.dp),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        headlineContent = {
            Text(plan.title, color = TextPrimaryColor)
        },
        supportingContent = {
            Text(plan.description, color = LightGrayText)
        },
    )
}


@Preview(showBackground = true)
@Composable
fun PlanListItemPreview() {
    HoliKatsuTheme {
        PlanListItem(
            Plan(
                id = 0,
                title = "タイトル",
                description = "説明",
            )
        )
    }
}