package com.chichi.shippingapp.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.chichi.shippingapp.screens.home.AppBottomBar
import com.chichi.shippingapp.screens.home.AppTopBar


@Composable
fun MainScreen() {
    val screensNavigator = remember() {
        ScreensNavigator()
    }
    val isRootRoute = screensNavigator.isRootRoute.collectAsState()
    val currentBottomTab = screensNavigator.currentBottomTab.collectAsState()
    val currentRoute = screensNavigator.currentRoute.collectAsState()

    Scaffold(
    topBar = {
        AppTopBar(
            isRootRoute.value,
            onBackClicked = {
                screensNavigator.navigateBack()
            }
        )

    } ,
        bottomBar = {
            BottomAppBar(modifier = Modifier) {
                AppBottomBar(
                    bottomTabs = ScreensNavigator.BOTTOM_TABS,
                    currentBottomTab = currentBottomTab.value,
                    onTabClicked = { bottomTab ->
                        screensNavigator.toTab(bottomTab)
                    }
                )
            }
        },
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
) {
    val parentNavController = rememberNavController()
    screensNavigator.setParentNavController(parentNavController)
}