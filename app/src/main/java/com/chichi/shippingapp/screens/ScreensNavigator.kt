package com.chichi.shippingapp.screens

import androidx.navigation.NavHostController
import com.chichi.shippingapp.BottomTab
import com.chichi.shippingapp.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ScreensNavigator {
    private val scope = CoroutineScope(Dispatchers.Main.immediate)
    private lateinit var parentNavController: NavHostController
    private lateinit var nestedNavController: NavHostController
    val currentBottomTab = MutableStateFlow<BottomTab?>(null)
    val currentRoute = MutableStateFlow<Route?>(null)
    val isRootRoute = MutableStateFlow(false)
    private var nestedNavControllerObserveJob: Job? = null
    private var parentNavControllerObserveJob: Job? = null


    fun navigateBack() {
        if (!nestedNavController.popBackStack()) {
            parentNavController.popBackStack()
        }
    }

    fun setParentNavController(navController: NavHostController) {
        parentNavController = navController

        parentNavControllerObserveJob?.cancel()
        parentNavControllerObserveJob = scope.launch {
            navController.currentBackStackEntryFlow.map { backStackEntry ->
                val bottomTab = when (val routeName = backStackEntry.destination.route) {
                    Route.HomeTab.routeName -> BottomTab.Home
                    Route.CalculateTab.routeName -> BottomTab.Calculate
                    Route.ShipmentTab.routeName -> BottomTab.Shipment
                    Route.ProfileTab.routeName -> BottomTab.Profile
                    null -> null
                    else -> throw RuntimeException("unsupported bottom tab: $routeName")
                }
                currentBottomTab.value = bottomTab
            }.collect()
        }
    }

    fun toTab(bottomTab: BottomTab) {
        val route = when(bottomTab) {
            BottomTab.Home -> Route.HomeTab
            BottomTab.Calculate -> Route.CalculateTab
            BottomTab.Shipment -> Route.ShipmentTab
            BottomTab.Profile -> Route.ProfileTab
        }
        parentNavController.navigate(route.routeName) {
            parentNavController.graph.startDestinationRoute?.let { startRoute ->
                popUpTo(startRoute) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }


    fun toRoute(route: Route) {
        nestedNavController.navigate(route.navCommand)
    }

    companion object {
        val BOTTOM_TABS = listOf(BottomTab.Home, BottomTab.Calculate, BottomTab.Shipment, BottomTab.Profile)
    }
}