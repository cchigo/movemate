package com.chichi.shippingapp.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import com.chichi.shippingapp.Route


sealed class BottomTab(val title: String, val topBarTitle: String? = null) {
    data object Home : BottomTab("Home")
    data object Calculate : BottomTab("Calculate")
    data object Shipment : BottomTab("Shipment", "Shipment history")
    data object Profile : BottomTab("Profile")
}

// Data class for Bottom Navigation Items
data class BottomNavItem(val label: String, val routeName: String, val title: String, val icon: ImageVector)

// Bottom Navigation Tabs
val BottomTabs = listOf(
    BottomNavItem("home_tab", Route.HomeTab.routeName, BottomTab.Home.title, Icons.Filled.Home),
    BottomNavItem("calculate_tab", Route.CalculateTab.routeName, BottomTab.Calculate.title, Icons.Filled.List),
    BottomNavItem("shipment_tab", Route.ShipmentTab.routeName, BottomTab.Shipment.title, Icons.Filled.Person),
    BottomNavItem("profile_tab", Route.ProfileTab.routeName, BottomTab.Profile.title, Icons.Filled.Settings)
)
