package com.chichi.shippingapp.screens.calculate

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.chichi.shippingapp.R
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.ui.theme.DarkGreen2
import com.chichi.shippingapp.ui.theme.MainBg
import com.chichi.shippingapp.ui.theme.MediumTextStyle
import com.chichi.shippingapp.ui.theme.NormalTextStyle
import com.chichi.shippingapp.ui.theme.OrangeBg


@Composable
fun ReceiptScreen(
    navController: NavController
   // navigate: (String) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    if (!isVisible) return

    val slideOffset = remember { Animatable(800f) }
    val fadeAlpha = remember { Animatable(0f) }
    val finalAmount = 1460

    var hasStarted by remember { mutableStateOf(false) }
    val displayedAmount by animateIntAsState(
        targetValue = if (hasStarted) finalAmount else 1200,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        label = "receipt"
    )

    LaunchedEffect(isVisible) {
        slideOffset.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
        fadeAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
        hasStarted = true
    }

    Dialog(
        onDismissRequest = {
            isVisible = false
            navController.navigate(Route.MainScreen.routeName)
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_movemate_icon),
                    contentDescription = "Receipt Icon",
                    modifier = Modifier
                        .height(85.dp)
                        .width(210.dp)
                        .offset(y = slideOffset.value.dp)
                )

                Spacer(modifier = Modifier.height(35.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_shipment),
                    contentDescription = "Summary Icon",
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = slideOffset.value.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Total Estimated Amount",
                    style = MediumTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .alpha(fadeAlpha.value)
                        .offset(y = slideOffset.value.dp)
                )

                Text(
                    text = "$ $displayedAmount USD",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = DarkGreen2,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .alpha(fadeAlpha.value)
                        .offset(y = slideOffset.value.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "This amount is estimated and will vary \n if you change location or weight",
                    style = NormalTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp)
                        .alpha(0.5f)
                        .offset(y = slideOffset.value.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(

                    onClick = {
                        isVisible = false
                        navController.navigate(Route.MainScreen.routeName)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .alpha(fadeAlpha.value)
                        .offset(y = slideOffset.value.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = OrangeBg.copy(alpha = 0.4f),
                        disabledContentColor = Color.White,
                        containerColor = OrangeBg,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Back to home",
                        modifier = Modifier.padding(6.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


data class Item(val name: String, val code: String, val location: String)
