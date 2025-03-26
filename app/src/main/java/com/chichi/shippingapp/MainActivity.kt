package com.chichi.shippingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chichi.shippingapp.screens.MainNavController
import com.chichi.shippingapp.screens.calculate.CalculateScreen
import com.chichi.shippingapp.screens.calculate.ReceiptScreen
import com.chichi.shippingapp.screens.calculate.SearchScreen
import com.chichi.shippingapp.screens.home.BottomNavItem
import com.chichi.shippingapp.screens.home.HomeScreen
import com.chichi.shippingapp.screens.home.MainScreen
import com.chichi.shippingapp.screens.shipment.ShipmentScreen
import com.chichi.shippingapp.ui.theme.GreyBottomTabColor
import com.chichi.shippingapp.ui.theme.LightGray1
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.chichi.shippingapp.ui.theme.ShippingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShippingAppTheme {
                MainNavController()

            }
        }
    }
}


