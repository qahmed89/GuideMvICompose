package com.example.myapplication.presentation.screen.details

sealed interface DetailsEffect {
    data class ShowToastWithItemClicked(val number: String) : DetailsEffect
}