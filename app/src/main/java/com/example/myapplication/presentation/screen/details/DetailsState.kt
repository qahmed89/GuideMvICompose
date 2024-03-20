package com.example.myapplication.presentation.screen.details

data class DetailsState(
    val isLoading : Boolean = false,
    val listData : List<String> = emptyList()
)