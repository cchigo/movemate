package com.chichi.shippingapp.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chichi.shippingapp.ui.theme.GreyBottomTabColor
import com.chichi.shippingapp.ui.theme.LightGray
import com.chichi.shippingapp.ui.theme.PrimaryColor

@Composable
fun AppBottomBar(
    bottomTabs: List<BottomTab>,
    currentBottomTab: BottomTab?,
    onTabClicked: (BottomTab) -> Unit,
) {
    if(currentBottomTab == BottomTab.Home){
        NavigationBar(
            containerColor = LightGray) {
            bottomTabs.forEachIndexed { _, bottomTab ->
                NavigationBarItem(
                    alwaysShowLabel = true,
                    icon = { Icon(bottomTab.icon!!, contentDescription = bottomTab.title) },
                    label = { Text(bottomTab.title) },
                    selected = currentBottomTab == bottomTab,
                    onClick = { onTabClicked(bottomTab) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = PrimaryColor,
                        unselectedTextColor = GreyBottomTabColor,
                        selectedIconColor = PrimaryColor,
                        unselectedIconColor = GreyBottomTabColor,
                        indicatorColor = LightGray.copy(alpha = 0.5f)
                    )
                )
            }
        }
    }else{

        Box(modifier = Modifier.height(0.dp)) {}
    }


}