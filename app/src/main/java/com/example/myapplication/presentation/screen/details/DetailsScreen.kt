package com.example.myapplication.presentation.screen.details

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.screen.composable.LoadingScreen
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailsScreen(
    state: DetailsState,
    onIntent: (DetailsIntent) -> Unit,
    detailsEffect: SharedFlow<DetailsEffect>
) {
    LoadingScreen(isLoading = state.isLoading)
    Box(modifier = Modifier.fillMaxSize()) {
        val stateList = rememberLazyListState()
        LazyColumn (state = stateList){
                items( state.listData) {
                    Text(text = it, modifier = Modifier
                        .clickable {
                            //TODO Intent for clickable item inside list

                             }
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp))
            }
        }
    }
}