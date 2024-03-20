package com.example.myapplication.presentation

import com.example.myapplication.utils.UiText

data class MainState(
    val  appBarStat: AppBarStat = AppBarStat()
)

data class AppBarStat(
    val showNavigationIcon: Boolean = false,
    val showExitIcon: Boolean = false,
        val title : UiText = UiText.DynamicString("")
)