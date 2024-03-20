package com.example.myapplication.presentation

sealed interface MainEffect  {
    data object CloseJourney : MainEffect
    data object NavToPreviousScreen : MainEffect
}