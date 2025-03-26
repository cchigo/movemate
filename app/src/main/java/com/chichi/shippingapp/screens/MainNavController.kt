package com.chichi.shippingapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.screens.calculate.ReceiptScreen
import com.chichi.shippingapp.screens.calculate.SearchScreen
import com.chichi.shippingapp.screens.home.MainScreen

@Composable
fun MainNavController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.MainScreen.routeName) {
        composable(Route.MainScreen.routeName) { MainScreen(navController) }//MAIN
        composable(Route.ReceiptScreen.routeName) { ReceiptScreen(navController) }//RECEIPT
        composable(Route.SearchScreen.routeName) { SearchScreen(navController) }//SEARCH
    }
}