package com.chichi.shippingapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.screens.calculate.CalculateScreen
import com.chichi.shippingapp.screens.shipment.ShipmentScreen
import com.chichi.shippingapp.ui.theme.GreyBottomTabColor
import com.chichi.shippingapp.ui.theme.LightGray1
import com.chichi.shippingapp.ui.theme.MainBg
import com.chichi.shippingapp.ui.theme.MediumTextStyle
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.chichi.shippingapp.ui.theme.ShippingAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val bottomNavController = rememberNavController()
    var currentBottomTab by remember {
        mutableStateOf(
            BottomNavItem(
                label = "home_tab",
                routeName = Route.HomeTab.routeName,
                title = BottomTab.Home.title,
                icon = Icons.Filled.Home
            )
        )
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = PrimaryColor)


    Column(
        modifier = Modifier
            .wrapContentSize()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        val isRootRoute = currentBottomTab.routeName == Route.HomeTab.routeName

        if (isRootRoute) {
            UserInfoBar()
        } else {
            MyCenterAlignedTopAppBar(isRootRoute,
                currentBottomTab = currentBottomTab,
                onBackClicked = { navController.navigate(Route.MainScreen.routeName) })
        }



        NavHost(
            navController = bottomNavController,
            startDestination = Route.HomeTab.routeName,
            Modifier
                .wrapContentHeight()
                .padding(top = 0.dp)
                .background(MainBg)
                .padding(0.dp)
        ) {
            composable(Route.HomeTab.routeName) { HomeScreen() }
            composable(Route.CalculateTab.routeName) { CalculateScreen(navController) }
            composable(Route.ShipmentTab.routeName) { ShipmentScreen() }
            composable(Route.ProfileTab.routeName) {
                // Profile Screen
            }
        }
        NavigationBar(
            windowInsets = WindowInsets(0, 0, 0, 0),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                val bottomTabs = BottomTabs

                bottomTabs.forEach { bottomTab ->
                    NavigationBarItem(
                        alwaysShowLabel = true,
                        icon = { Icon(bottomTab.icon!!, contentDescription = bottomTab.label) },
                        label = { Text(bottomTab.title) },
                        selected = currentBottomTab.routeName == bottomTab.routeName,
                        onClick = {
                            if (bottomTab.routeName != Route.ProfileTab.routeName) {
                                currentBottomTab = bottomTab
                                bottomNavController.navigate(bottomTab.routeName)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = PrimaryColor,
                            unselectedTextColor = GreyBottomTabColor,
                            selectedIconColor = PrimaryColor,
                            unselectedIconColor = GreyBottomTabColor,
                            indicatorColor = LightGray1.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCenterAlignedTopAppBar(
    isRootRoute: Boolean, currentBottomTab: BottomNavItem?, onBackClicked: () -> Unit
) {
    if (!isRootRoute) {
        CenterAlignedTopAppBar(title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            ) {
                val text = currentBottomTab?.title ?: BottomTab.Home.title

                Text(
                    text = text, color = Color.White,
                   textAlign = TextAlign.Center
                )
            }
        }, navigationIcon = {
            if (!isRootRoute) {
                IconButton(
                    onClick = onBackClicked
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        tint = Color.White,
                        contentDescription = "menu items"
                    )
                }
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryColor,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
        )
        )
    } else {
        CenterAlignedTopAppBar(
            title = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                ) {
                    Text(
                        text = "Home",
                        color = Color.White,
                         style = MediumTextStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryColor,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShippingAppTheme {
        MainScreen(navController = rememberNavController())
    }
}


