package com.example.myapplication.presentation.screen.home

import KeyStoreManager
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.banquemisr.bm.tokenization.data.utils.CypherManager
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(state: HomeState, navController: NavController,onIntent: (HomeIntent) -> Unit, effect: SharedFlow<HomeEffect>) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = Unit) {
        effect.collectLatest {
            when (it) {
                HomeEffect.NavigateToDetails -> {
                    navController.navigate("details")
                }

                is HomeEffect.ShowToastMessage -> Toast.makeText(
                    context,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()

                is HomeEffect.ShowShowSnackBarMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(it.message)
                    }
                }
            }
        }
    }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { padding ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            val cypherManager =KeyStoreManager()
          val x=   cypherManager.encrypt(data = "hello W")
            cypherManager.getPublicKeyAsString()
            Log.i("TAGX",x)
            Column {
                Button(onClick = { onIntent(HomeIntent.ClickOnEnableToastButton) }) {
                    Text(state.textEnableToastButton)
                }
                Spacer(modifier = Modifier.height(6.dp))
                AnimatedVisibility(state.showToastButton) {
                    Button(onClick = { onIntent(HomeIntent.ClickOnToastButton("Hello")) }) {
                        Text("Show Toast")
                    }
                }

                Button(onClick = { onIntent(HomeIntent.ClickSnackBarButton("Hello Snack Bar")) }) {
                    Text("Show Snack")
                }

                Button(onClick = { onIntent(HomeIntent.ClickOnNavigateButton) }) {
                    Text("Navigation Button")
                }


            }
        }


    }
}