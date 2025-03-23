package com.chichi.shippingapp.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.screens.ScreensNavigator
import com.chichi.shippingapp.screens.calculate.CalculateScreen
import com.chichi.shippingapp.screens.profile.ProfileScreen
import com.chichi.shippingapp.screens.shipment.ShipmentScreen
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.chichi.shippingapp.ui.theme.ShippingAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


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
            isRootRoute = isRootRoute.value,
            currentBottomTab = currentBottomTab.value,
            onBackClicked = {
                screensNavigator.toTab(BottomTab.Home)
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
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = PrimaryColor)
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
            composable(route = Route.HomeTab.routeName) { HomeScreen() }
            composable(route = Route.CalculateTab.routeName) { CalculateScreen() }
            composable(route = Route.ShipmentTab.routeName) { ShipmentScreen() }
            composable(route = Route.ProfileTab.routeName) { ProfileScreen() }
        }



    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShippingAppTheme {
        MainScreen()
    }
}


