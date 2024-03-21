package com.example.myapplication.presentation.screen.details

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.screen.composable.LoadingScreen
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun DetailsScreen(
    state: DetailsState,
    onIntent: (DetailsIntent) -> Unit,
    detailsEffect: SharedFlow<DetailsEffect>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        onIntent(DetailsIntent.InitDetailsScreen)
        detailsEffect.collect {
            when (it) {
                is DetailsEffect.ShowToastWithItemClicked ->
                    Toast.makeText(context, it.number, Toast.LENGTH_SHORT).show()
            }
        }
    }
    LoadingScreen(isLoading = state.isLoading)
    Box(modifier = Modifier.fillMaxSize()) {
        val stateList = rememberLazyListState()
        LazyColumn(state = stateList) {
            items(state.listData) {
                Text(text = it, textAlign = TextAlign.Center, modifier = Modifier
                    .clickable {
                        onIntent(DetailsIntent.ClickOnItemList(it))
                    }
                    .fillMaxWidth()
                    .padding(vertical = 40.dp, horizontal = 16.dp)

                    .border(
                        width = 2.dp,
                        color = Color.Gray.copy(alpha = 0.9f),
                        shape = RectangleShape
                    )
                )
            }
        }
    }
}