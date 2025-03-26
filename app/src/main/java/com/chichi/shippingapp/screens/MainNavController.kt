package com.chichi.shippingapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.screens.calculate.ReceiptScreen
import com.chichi.shippingapp.screens.home.MainScreen
import com.chichi.shippingapp.screens.home.SearchScreen

@Composable
fun MainNavController(query: String ?= null) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.MainScreen.routeName) {
        composable(Route.MainScreen.routeName) { MainScreen(navController) } // MAIN
        composable(Route.ReceiptScreen.routeName) { ReceiptScreen(navController) } // RECEIPT
        composable(
            route = "${Route.SearchScreen.routeName}/{query}",
            arguments = listOf(navArgument("query") { defaultValue = "" })
        ) { backStackEntry ->
            val queryArg = backStackEntry.arguments?.getString("query") ?: ""
            SearchScreen(navController, queryArg) // SEARCH
        }
    }
}
