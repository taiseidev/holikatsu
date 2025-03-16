package com.example.holikatsu.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.holikatsu.ui.theme.HoliKatsuTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NextHoliday(modifier: Modifier = Modifier, nextHolidays: List<String>) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 4,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                nextHolidays.forEach { holiday ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEE7056)),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = holiday,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                nextHolidays.size.toString(),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.alignByBaseline()
            )
            Text(
                " 連休🎊",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NextHolidayPreview() {
    HoliKatsuTheme {
        NextHoliday(
            nextHolidays = listOf("12/03", "12/04", "12/03", "12/04", "12/03", "12/04")
        )
    }
}