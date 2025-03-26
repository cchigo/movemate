package com.chichi.shippingapp.screens.calculate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.chichi.shippingapp.Route

@Composable
fun ReceiptScreen(
    navController: NavController
) {
    Column(Modifier.fillMaxSize()) {
        Text(" receipt")

        Button(onClick = {
            navController.navigate(Route.MainScreen.routeName)
        }) {
            Text("Go Home")
        }
    }
}


@Composable
fun SearchScreen(
    navController: NavController
) {

    Column(Modifier.fillMaxSize()) {

        Text("Hi search")

        Button(onClick = {
            navController.navigate(Route.MainScreen.routeName)
        }) {
            Text("Go Home")
        }
    }

}