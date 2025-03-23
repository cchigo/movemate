package com.chichi.shippingapp.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.chichi.shippingapp.screens.home.TopBar


@Composable
fun MainScreen() {
    val screensNavigator = remember() {
        ScreensNavigator()
    }
    val isRootRoute = screensNavigator.isRootRoute.collectAsState()

    Scaffold(
    topBar = {
        TopBar(
            isRootRoute.value,
            onBackClicked = {
                screensNavigator.navigateBack()
            }
        )

    } ,
        content = { paddingValues ->
            MainScreenContent(
                padding = paddingValues,
                screensNavigator = screensNavigator
            )


        }

    )

}

@Composable
private fun MainScreenContent(
    padding: PaddingValues,
    screensNavigator: ScreensNavigator,
) {}