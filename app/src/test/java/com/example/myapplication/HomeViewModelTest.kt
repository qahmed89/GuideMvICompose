package com.example.myapplication

import app.cash.turbine.turbineScope
import com.example.myapplication.presentation.screen.home.HomeEffect
import com.example.myapplication.presentation.screen.home.HomeIntent
import com.example.myapplication.presentation.screen.home.HomeState
import com.example.myapplication.presentation.screen.home.HomeViewModel
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo


@ExtendWith(MockKExtension::class, MainCoroutineExtension::class)
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel


    @BeforeEach
    fun setup() {
        viewModel = HomeViewModel()
    }

    @Test
    fun `when ClickOnNavigateButton Intent emit HomeEffect NavigateToDetails `() = runTest {
        val expectedEffect = HomeEffect.NavigateToDetails
        turbineScope {
            viewModel.onIntent(HomeIntent.ClickOnNavigateButton)
            val effect = viewModel.uiEffect.testIn(backgroundScope, name = "effect").awaitItem()
            expectThat(effect).isEqualTo(expectedEffect)
        }
    }

    @Test
    fun `when ClickOnEnableToastButton Intent update HomeState`() = runTest {
        val expectedState = HomeState(
            isLoading = false,
            showToastButton = false,
            textEnableToastButton = "disableToastButton"
        )
        turbineScope {
            viewModel.onIntent(HomeIntent.ClickOnEnableToastButton)
            val state = viewModel.uiState.testIn(backgroundScope, name = "state").awaitItem()
            expectThat(state).isEqualTo(expectedState)
        }
    }

    @Test
    fun `when ClickOnToastButton Intent update HomeState`() = runTest {
        val expected = HomeEffect.ShowToastMessage("Hello from anther world")
        turbineScope {
            viewModel.onIntent(HomeIntent.ClickOnToastButton(""))
            val actual = viewModel.uiEffect.testIn(backgroundScope, name = "effect").awaitItem()
            expectThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `when ClickSnackBarButton Intent update HomeState`() = runTest {
        val expected = HomeEffect.ShowShowSnackBarMessage("")
        turbineScope {
            viewModel.onIntent(HomeIntent.ClickSnackBarButton(""))
            val actual = viewModel.uiEffect.testIn(backgroundScope, name = "effect").awaitItem()
            expectThat(actual).isEqualTo(expected)
        }
    }


}


