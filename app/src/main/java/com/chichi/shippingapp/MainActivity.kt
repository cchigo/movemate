package com.chichi.shippingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.chichi.shippingapp.screens.MainScreen
import com.chichi.shippingapp.ui.theme.ShippingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShippingAppTheme {
                MainScreen()
            }
        }
    }
}


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ShippingAppTheme {
            MainScreen()
        }
    }


