package com.chichi.shippingapp.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

//TODO: replace images
sealed class BottomTab(val icon: ImageVector?, var title: String) {
    data object Home : BottomTab(Icons.Rounded.Home, "Home")
    data object Calculate : BottomTab(Icons.Rounded.Home, "Calculate")
    data object Shipment : BottomTab(Icons.Rounded.Home, "Shipment")
    data object Profile : BottomTab(Icons.Rounded.Favorite, "Profile")
}