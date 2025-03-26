package com.chichi.shippingapp.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Refresh
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
    BottomNavItem("home_tab", Route.HomeTab.routeName, BottomTab.Home.title, Icons.Outlined.Home),
    BottomNavItem("calculate_tab", Route.CalculateTab.routeName, BottomTab.Calculate.title, Icons.Outlined.DateRange),
    BottomNavItem("shipment_tab", Route.ShipmentTab.routeName, BottomTab.Shipment.title, Icons.Outlined.Refresh),
    BottomNavItem("profile_tab", Route.ProfileTab.routeName, BottomTab.Profile.title, Icons.Outlined.Person)
)
