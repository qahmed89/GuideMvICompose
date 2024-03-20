package com.example.myapplication.presentation.screen.home



sealed interface HomeIntent  {
    data object ClickOnEnableToastButton : HomeIntent
    data object ClickOnNavigateButton : HomeIntent
    data class ClickOnToastButton(val message: String) : HomeIntent
    data class ClickSnackBarButton(val message: String) : HomeIntent

}