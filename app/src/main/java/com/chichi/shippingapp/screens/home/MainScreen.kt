package com.chichi.shippingapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.getFadeInOpacity
import com.chichi.shippingapp.screens.calculate.CalculateScreen
import com.chichi.shippingapp.screens.calculate.ReceiptScreen
import com.chichi.shippingapp.screens.shipment.ShipmentScreen
import com.chichi.shippingapp.ui.theme.GreyBottomTabColor
import com.chichi.shippingapp.ui.theme.MainBg
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val bottomNavController = rememberNavController()
    var selectedIndex by remember { mutableStateOf(0) }
    val bottomTabs = BottomTabs

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
    var onScreenLaunch by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onScreenLaunch = true
    }
    val transitionState = remember { MutableTransitionState(false).apply { targetState = true } }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = PrimaryColor)
    // val bottomNavController = rememberNavController()

    Scaffold(

        topBar = {
            val isRootRoute = currentBottomTab.routeName == Route.HomeTab.routeName
            AnimatedVisibility(
                visibleState = transitionState, enter = fadeIn(
                    animationSpec = tween(durationMillis = 1200)
                ) + slideInVertically(
                    initialOffsetY = { -it }, animationSpec = tween(durationMillis = 1200)
                ), exit = fadeOut()
            ) {
                if (isRootRoute) {
                    UserInfoBar(navController)
                } else {
                    MyCenterAlignedTopAppBar(isRootRoute,
                        currentBottomTab = currentBottomTab,
                        onBackClicked = { navController.navigate(Route.MainScreen.routeName) })
                }
            }
        }, bottomBar = @androidx.compose.runtime.Composable {


            Column(

                verticalArrangement = Arrangement.Bottom
            ) {
                HorizontalIndicator(selectedIndex, bottomTabs.size)


                NavigationBar(
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    containerColor = MainBg,
                    modifier = Modifier.alpha(getFadeInOpacity(onScreenLaunch))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            //  .offset(getVerticalOffsetY(onScreenLaunch))
                            .alpha(getFadeInOpacity(onScreenLaunch)),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        bottomTabs.forEachIndexed { index, bottomTab ->
                            key(bottomTab.routeName) {
                                NavigationBarItem(
                                    alwaysShowLabel = true,
                                    icon = {
                                        Icon(
                                            bottomTab.icon!!, contentDescription = bottomTab.label
                                        )
                                    },
                                    label = { Text(bottomTab.title) },
                                    selected = currentBottomTab.routeName == bottomTab.routeName,
                                    onClick = {
                                        if (currentBottomTab.routeName != bottomTab.routeName) {
                                            selectedIndex = index
                                            currentBottomTab = bottomTab
                                            bottomNavController.navigate(bottomTab.routeName) {
                                                launchSingleTop = true
                                            }
                                        }
                                    },

                                    colors = NavigationBarItemDefaults.colors(
                                        selectedTextColor = PrimaryColor,
                                        unselectedTextColor = GreyBottomTabColor,
                                        selectedIconColor = PrimaryColor,
                                        unselectedIconColor = GreyBottomTabColor,
                                        indicatorColor = MainBg.copy(0.1f)
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }, content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = bottomNavController,
                    startDestination = Route.HomeTab.routeName,
                    Modifier.background(MainBg)
                ) {
                    composable(Route.HomeTab.routeName) { HomeScreen() }
                    composable(Route.CalculateTab.routeName) { CalculateScreen(navController) }
                    composable(Route.ShipmentTab.routeName) { ShipmentScreen() }
                    composable(Route.ReceiptScreen.routeName) { ReceiptScreen(navController) }
                    composable(Route.SearchScreen.routeName) { ReceiptScreen(navController) }
                    composable(Route.ProfileTab.routeName) {
                        // Profile Screen
                    }
                }
            }

        }

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCenterAlignedTopAppBar(
    isRootRoute: Boolean,
    currentBottomTab: BottomNavItem?, onBackClicked: () -> Unit
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
                    text = text, color = Color.White, textAlign = TextAlign.Start
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
    }
}

@Composable
fun HorizontalIndicator(selectedIndex: Int, tabCount: Int) {
    val density = LocalDensity.current
    var parentWidth by remember { mutableStateOf(0f) }

    val indicatorWidth = parentWidth / tabCount

    val animatedOffset by animateFloatAsState(
        targetValue = if (parentWidth > 0) {
            selectedIndex * indicatorWidth
        } else 0f, animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(4.dp)
        .background(MainBg)
        .onGloballyPositioned { coordinates ->
            parentWidth = coordinates.size.width.toFloat()
        }) {
        Box(modifier = Modifier
            .offset { IntOffset(animatedOffset.toInt(), 0) }
            .width(with(density) { indicatorWidth.toDp() })
            .height(4.dp)
            .background(PrimaryColor, shape = RoundedCornerShape(50))
        )
    }
}

