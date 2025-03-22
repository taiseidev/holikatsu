package com.example.holikatsu.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.holikatsu.R
import com.example.holikatsu.ui.theme.HoliKatsuTheme
import com.example.holikatsu.ui.theme.TextPrimaryColor
import com.example.holikatsu.ui.theme.TopAppBarBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    modifier: Modifier = Modifier,
    onActionIconClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier, title = {
            Text(stringResource(R.string.app_name), fontWeight = FontWeight.Bold)
        }, actions = {
            IconButton(
                onClick = {
                    onActionIconClick()
                }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle, contentDescription = null
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopAppBarBackground,
            titleContentColor = TextPrimaryColor,
            actionIconContentColor = TextPrimaryColor
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CommonTopAppBarPreview() {
    HoliKatsuTheme {
        CommonTopAppBar(
            onActionIconClick = { })
    }
}