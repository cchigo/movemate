package com.chichi.shippingapp.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chichi.shippingapp.Route
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

    Surface(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 12.dp),
    ) {

        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = parentNavController,
            enterTransition = { fadeIn(animationSpec = tween(200)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) },
            startDestination = Route.HomeTab.routeName,
        ) {
            composable(route = Route.HomeTab.routeName){}
            composable(route = Route.CalculateTab.routeName){}
            composable(route = Route.ShipmentTab.routeName){}
            composable(route = Route.ProfileTab.routeName){}



        }



    }
}