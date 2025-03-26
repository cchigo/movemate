package com.chichi.shippingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chichi.shippingapp.screens.MainNavController
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


