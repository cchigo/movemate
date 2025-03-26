package com.chichi.shippingapp.screens.calculate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.draw.clip
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
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun ReceiptScreen(navController: NavController) {
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







//@Composable
//fun ReceiptScreen(
//    navController: NavController
//) {
//    Column(Modifier.fillMaxSize()) {
//        Text(" receipt")
//
//        Button(onClick = {
//            navController.navigate(Route.MainScreen.routeName)
//        }) {
//            Text("Go Home")
//        }
//    }
//}


//@Composable
//fun SearchScreen(
//    navController: NavController
//) {
//
//    Column(Modifier.fillMaxSize()) {
//
//        Text("Hi search")
//
//        Button(onClick = {
//            navController.navigate(Route.MainScreen.routeName)
//        }) {
//            Text("Go Home")
//        }
//    }
//
//}


@Composable
fun ItemCard(item: Item) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shipment),
                contentDescription = "box",
                modifier = Modifier.size(22.dp)
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${item.code} • ${item.location}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.1f),
                thickness = 1.2.dp
            )
        }
    }
}

data class Item(val name: String, val code: String, val location: String)
