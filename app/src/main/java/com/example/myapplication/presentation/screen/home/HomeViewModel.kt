package com.example.myapplication.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.myapplication.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel() : BaseViewModel<HomeState, HomeIntent, HomeEffect>() {
    private val homeState = MutableStateFlow(HomeState())
    private val homeEffect = MutableSharedFlow<HomeEffect>()

    override val uiState: StateFlow<HomeState>
        get() = homeState

    override val uiEffect: SharedFlow<HomeEffect>
        get() = homeEffect.asSharedFlow()

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ClickOnEnableToastButton -> {
                updateStateToReleatedToastButtonController()
            }
            is HomeIntent.ClickOnNavigateButton -> emitEffectToNavigationToDetails()

            is HomeIntent.ClickOnToastButton -> emitEffectToshowToastButton()
            is HomeIntent.ClickSnackBarButton -> emitEffectToShowSnackBarMessage(intent.message)
        }
    }

    private fun emitEffectToNavigationToDetails() {
        viewModelScope.launch {
            homeEffect.emit(HomeEffect.NavigateToDetails)
        }
    }

    private fun emitEffectToShowSnackBarMessage(message:String) {
        viewModelScope.launch {
            homeEffect.emit(HomeEffect.ShowShowSnackBarMessage(message))
        }
    }

    private fun emitEffectToshowToastButton() {
        viewModelScope.launch {
            homeEffect.emit(HomeEffect.ShowToastMessage("Hello from anther world"))
        }
    }

    private fun updateStateToReleatedToastButtonController() {
        homeState.update { it.copy(showToastButton = !it.showToastButton) }
    }

}



data class HomeState(
    val isLoading: Boolean = false,
    val showToastButton: Boolean = true,
    val textEnableToastButton: String = if (showToastButton) "disableToastButton" else "EnableToast-button"
)
