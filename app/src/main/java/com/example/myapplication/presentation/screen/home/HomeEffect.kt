package com.example.myapplication.presentation.screen.home

sealed interface HomeEffect  {
    data object NavigateToDetails : HomeEffect
    data class ShowToastMessage(val message: String) : HomeEffect
    data class ShowShowSnackBarMessage(val message: String) : HomeEffect

}