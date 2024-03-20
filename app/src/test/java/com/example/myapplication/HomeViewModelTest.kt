package com.example.myapplication

import app.cash.turbine.turbineScope
import com.example.myapplication.presentation.screen.home.HomeEffect
import com.example.myapplication.presentation.screen.home.HomeIntent
import com.example.myapplication.presentation.screen.home.HomeViewModel
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
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

    //TODO when ClickOnEnableToastButton Intent update State
    @Test
    fun `when ClickOnEnableToastButton Intent update HomeState`() = runTest {
        }

    //TODO when ClickOnToastButton Intent emit HomeEffect ShowToastMessage
    @Test
    fun `when ClickOnToastButton Intent update HomeState`() = runTest {

    }

    //TODO when ClickSnackBarButton Intent emit HomeEffect ShowShowSnackBarMessage
    @Test
    fun `when ClickSnackBarButton Intent update HomeState`() = runTest {
    }




}


