package com.example.myapplication.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<UIState, Intent, Effect>() : ViewModel() {
    abstract val uiState: StateFlow<UIState>
    abstract val uiEffect: SharedFlow<Effect>
    abstract fun onIntent(intent: Intent)

}


