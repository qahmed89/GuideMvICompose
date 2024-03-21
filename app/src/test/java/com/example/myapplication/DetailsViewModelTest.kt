package com.example.myapplication

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.turbineScope
import com.example.myapplication.domain.useCase.GetDataUseCase
import com.example.myapplication.presentation.screen.details.DetailsEffect
import com.example.myapplication.presentation.screen.details.DetailsIntent
import com.example.myapplication.presentation.screen.details.DetailsState
import com.example.myapplication.presentation.screen.details.DetailsViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@ExtendWith(MockKExtension::class, MainCoroutineExtension::class)
class DetailsViewModelTest {
    lateinit var viewModel: DetailsViewModel

    @MockK
    lateinit var getDateUseCase: GetDataUseCase


    @BeforeEach
    fun setup() {
        viewModel = DetailsViewModel()
    }

    @Test
    fun `when ClickOnItemList Intent return with emit ShowToastWithItemClicked Effect`() = runTest {
        val expected = DetailsEffect.ShowToastWithItemClicked("")
        turbineScope {
            viewModel.onIntent(DetailsIntent.ClickOnItemList(""))
            val actual = viewModel.uiEffect.testIn(backgroundScope, name = "Effect").awaitItem()
            expectThat(actual).isEqualTo(expected)
        }
    }


    @Test
    fun `when InitDetailsScreen Intent return with update inside DetailsState `() = runTest {
        val listMocked = (1..100).map {
            it.toString()
        }
        coEvery { getDateUseCase() }.returns(listMocked)
        val expected = DetailsState(isLoading = false, listData = listMocked)
        turbineScope {
            viewModel.onIntent(DetailsIntent.InitDetailsScreen)
            val actual = viewModel.uiState.testIn(backgroundScope, name = "state")
            expectThat(actual.skipInitItem()).isEqualTo(expected)
        }
    }
}

suspend inline fun <reified T> ReceiveTurbine<T>.skipInitItem(): T {
    this.skipItems(1)
    return awaitItem()
}



