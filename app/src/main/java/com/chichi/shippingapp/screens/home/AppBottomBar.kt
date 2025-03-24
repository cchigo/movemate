package com.chichi.shippingapp.screens.home

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AppBottomBar(
    bottomTabs: List<BottomTab>,
    currentBottomTab: BottomTab?,
    onTabClicked: (BottomTab) -> Unit,
) {
    if(currentBottomTab == BottomTab.Home){
        NavigationBar {
            bottomTabs.forEachIndexed { _, bottomTab ->
                NavigationBarItem(
                    alwaysShowLabel = true,
                    icon = { Icon(bottomTab.icon!!, contentDescription = bottomTab.title) },
                    label = { Text(bottomTab.title) },
                    selected = currentBottomTab == bottomTab,
                    onClick = { onTabClicked(bottomTab) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                )
            }
        }
    }


}