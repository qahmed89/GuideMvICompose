package com.example.myapplication.presentation.screen.details

import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.useCase.GetDataUseCase
import com.example.myapplication.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel() : BaseViewModel<DetailsState, DetailsIntent, DetailsEffect>() {
    private val detailsState = MutableStateFlow(DetailsState())
    private val detailsEffect = MutableSharedFlow<DetailsEffect>()
    override val uiState: StateFlow<DetailsState>
        get() = detailsState.asStateFlow()
    override val uiEffect: SharedFlow<DetailsEffect>
        get() = detailsEffect.asSharedFlow()

    override fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.ClickOnItemList -> {
                emitEffectShowToastWithItemClicked(intent)
            }

            DetailsIntent.InitDetailsScreen -> {
                updateStateWithDataFromUseCase()
            }
        }
    }

    private fun updateStateWithDataFromUseCase() {
        viewModelScope.launch {
            detailsState.update {
                it.copy(isLoading = false, listData = GetDataUseCase().invoke())
            }
        }
    }

    private fun emitEffectShowToastWithItemClicked(intent: DetailsIntent.ClickOnItemList) {
        viewModelScope.launch {
            detailsEffect.emit(DetailsEffect.ShowToastWithItemClicked(intent.number))
        }
    }

}