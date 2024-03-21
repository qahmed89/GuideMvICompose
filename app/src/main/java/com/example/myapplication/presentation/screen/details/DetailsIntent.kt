package com.example.myapplication.presentation.screen.details

sealed interface DetailsIntent {
    data object InitDetailsScreen : DetailsIntent
    data class ClickOnItemList(val number: String) : DetailsIntent
}