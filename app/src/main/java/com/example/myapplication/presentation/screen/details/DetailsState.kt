package com.example.myapplication.presentation.screen.details

data class DetailsState(
    val isLoading : Boolean = true,
    val listData : List<String> = emptyList()
)