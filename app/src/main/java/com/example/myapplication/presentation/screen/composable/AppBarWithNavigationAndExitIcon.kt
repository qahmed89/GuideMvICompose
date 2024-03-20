package com.example.myapplication.presentation.screen.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.presentation.MainIntent
import com.example.myapplication.presentation.MainState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithNavigationAndExitIcon(
    text: String = "",
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    state: MainState,
    onIntent: (MainIntent) -> Unit
) {
    val navigationIcon: @Composable (() -> Unit) = {
        IconButton(onClick = { onIntent(MainIntent.ClickOnNavigationIcon) }) {
            Icon(startIcon ?: Icons.Filled.ArrowBack, null)
        }
    }

    val action :@Composable RowScope.() -> Unit= {
        if (state.appBarStat.showExitIcon) {
            IconButton(onClick = { onIntent(MainIntent.ClickOnCloseJourney) }) {
                Icon(endIcon ?: Icons.Filled.Close, null)
            }
        }
    }


        TopAppBar(
            title = { Text(state.appBarStat.title.asString()) },
            navigationIcon = {if (state.appBarStat.showNavigationIcon) {
                navigationIcon()
            } else null},
            actions =  action,
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)

        )
    }