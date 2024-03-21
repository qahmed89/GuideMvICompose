package com.example.myapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.screen.composable.AppBarWithNavigationAndExitIcon
import com.example.myapplication.presentation.screen.details.DetailsScreen
import com.example.myapplication.presentation.screen.details.DetailsViewModel
import com.example.myapplication.presentation.screen.home.HomeScreen
import com.example.myapplication.presentation.screen.home.HomeViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val mainViewMode: MainViewModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val mainState by mainViewMode.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(key1 = Unit) {
                    mainViewMode.uiEffect.collectLatest {
                        when (it) {
                            MainEffect.CloseJourney -> finish()
                            MainEffect.NavToPreviousScreen -> navController.popBackStack()
                        }
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppBarWithNavigationAndExitIcon(
                            state = mainState,
                            onIntent = {
                                mainViewMode.onIntent(it)
                            })
                    }
                ) { padding ->
                    NavHost(
                        startDestination = "home",
                        navController = navController,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable("Home") {
                            mainViewMode.onIntent(
                                MainIntent.ChangeAppBarForHomeScreen(

                                )
                            )
                            val viewmodel: HomeViewModel = viewModel()
                            val state by viewmodel.uiState.collectAsStateWithLifecycle()
                            HomeScreen(
                                state = state,
                                onIntent = viewmodel::onIntent,
                                navController = navController,
                                effect = viewmodel.uiEffect
                            )
                        }

                        composable("details") {
                            mainViewMode.onIntent(
                                MainIntent.ChangeAppBarForDetailsScreen()
                            )
                            val viewModel = viewModel<DetailsViewModel>()
                            val state by viewModel.uiState.collectAsStateWithLifecycle()
                            DetailsScreen(state = state, onIntent = {
                                viewModel.onIntent(it)
                            }, detailsEffect = viewModel.uiEffect)
                        }
                    }
                }
            }
        }
    }

}