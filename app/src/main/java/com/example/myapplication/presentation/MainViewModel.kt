package com.example.myapplication.presentation

import androidx.lifecycle.viewModelScope
import com.example.myapplication.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel() : BaseViewModel<MainState, MainIntent, MainEffect>() {

    private val _mainStat = MutableStateFlow(MainState())
    private val _mainEffect = MutableSharedFlow<MainEffect>()



   
   override fun onIntent(intent: MainIntent) {
        viewModelScope.launch {
            when (intent) {
                is MainIntent.ChangeAppBarForDetailsScreen -> changeStateForAppBarDetails(intent)
                is MainIntent.ChangeAppBarForHomeScreen -> changeStateForAppBarHome(intent)
                MainIntent.ClickOnCloseJourney -> closeJourney()
                MainIntent.ClickOnNavigationIcon -> popToPreviousScreen()
            }
        }
    }


    private fun changeStateForAppBarHome(intent: MainIntent.ChangeAppBarForHomeScreen) {
        _mainStat.update {
            it.copy(appBarStat = intent.appBarStat)
        }
    }

    private fun changeStateForAppBarDetails(intent: MainIntent.ChangeAppBarForDetailsScreen) {
        _mainStat.update {
            it.copy(appBarStat = intent.appBarStat)
        }
    }

    private suspend fun popToPreviousScreen() {
        _mainEffect.emit(
            MainEffect.NavToPreviousScreen
        )
    }

    private suspend fun closeJourney() {
        _mainEffect.emit(
            MainEffect.CloseJourney
        )
    }

    override val uiState: StateFlow<MainState>
        get() = _mainStat
    override val uiEffect: SharedFlow<MainEffect>
        get() = _mainEffect
}