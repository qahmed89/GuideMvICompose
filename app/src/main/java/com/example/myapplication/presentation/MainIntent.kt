package com.example.myapplication.presentation

import com.example.myapplication.R
import com.example.myapplication.utils.UiText

sealed interface MainIntent {
    data class ChangeAppBarForHomeScreen(
        val appBarStat: AppBarStat = AppBarStat(showNavigationIcon = false, showExitIcon = false , title = UiText.StringResource(
            R.string.home_title_screen
        ))
    ) : MainIntent

    data class ChangeAppBarForDetailsScreen(
        val appBarStat: AppBarStat = AppBarStat(showNavigationIcon = true, showExitIcon = false , title = UiText.StringResource(
            R.string.details_title_screen
        ))
    ) : MainIntent

    object ClickOnNavigationIcon : MainIntent
    object ClickOnCloseJourney : MainIntent
}