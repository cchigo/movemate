package com.chichi.shippingapp.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chichi.shippingapp.CircularImage
import com.chichi.shippingapp.R
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.screens.calculate.Item
import com.chichi.shippingapp.ui.theme.LightGray1
import com.chichi.shippingapp.ui.theme.LightTextStyle
import com.chichi.shippingapp.ui.theme.MainBg
import com.chichi.shippingapp.ui.theme.NormalTextStyle
import com.chichi.shippingapp.ui.theme.OrangeBg
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SearchReceiptBar(searchQuery: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth().background(PrimaryColor) // for the searc
            .clip(RoundedCornerShape(32.dp))
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    "Enter the receipt number...",
                    color = Color.Gray.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = PrimaryColor
                )
            },
            textStyle = TextStyle(color = Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                disabledIndicatorColor = Color.White
            ),
            trailingIcon = {
                Surface(
                    modifier = Modifier.clip(CircleShape), color = OrangeBg
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_scan),
                        contentDescription = "Receipt",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                    )
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, query: String) {
    var searchQuery by remember { mutableStateOf(query) }
    var isVisible by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = PrimaryColor)


    LaunchedEffect(Unit) {
        isVisible = true
    }

    val items = listOf(
        Item("Summer linen jacket", "#NEJ2008993412231", "Barcelona → Paris"),
        Item("Tapered-fit jeans AW", "#NEJ35870264978659", "Colombia → Paris"),
        Item("Macbook pro M2", "#NE43857340857904", "Paris → Morocco"),
        Item("Office setup desk", "#NEJ23481570754963", "France → German")
    )

    val filteredItems = items.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.code.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainBg)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = PrimaryColor)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            navController.navigate(Route.MainScreen.routeName)
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                SearchReceiptBar(searchQuery = searchQuery, onValueChange = { searchQuery = it })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        ItemList(filteredItems)
    }
}

@Composable
fun ItemList(items: List<Item>) {
    val transitionState = remember { MutableTransitionState(false).apply { targetState = true } }

    if (items.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Item unavailable",
                style = TextStyle(fontSize = 18.sp, color = Color.Gray),
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        AnimatedVisibility(
            visibleState = transitionState,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 1500)
            ) + slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 1500)
            ),
            exit = fadeOut()
        ) {

            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier.wrapContentSize()
                ) {
                    items(items) { item ->
                        ItemRow(item)
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = LightGray1,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)

                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ItemRow(item: Item) {


        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            CircularImage(imageId = R.drawable.daily_boxes_6, bgColor = PrimaryColor)

            Box (Modifier.padding(horizontal = 8.dp)){  ShipmentItem(
                header = item.name,
                body = "${item.code} • ${item.location}",
                bodyTextStyle = LightTextStyle,
                headerTextStyle = NormalTextStyle
            )}

        }

}

