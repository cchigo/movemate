package com.chichi.shippingapp.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chichi.shippingapp.CircularImage
import com.chichi.shippingapp.R
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.ui.theme.OrangeBg
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.chichi.shippingapp.ui.theme.WhiteTextStyleAlpha

@Composable
fun UserInfoBar(navController: NavController) {

    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(PrimaryColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            CircularImage(imageId = R.drawable.avatar_sample)


            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.graphicsLayer(alpha = 0.65f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location",
                        tint = Color.White,
                        modifier = Modifier
                            .height(16.dp)
                            .graphicsLayer(
                                rotationZ = -10f
                            )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Your location",
                        style = WhiteTextStyleAlpha,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        "Wertheimer, Illinois",
                        style = WhiteTextStyleAlpha
                    )
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .width(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape)
                    .padding(8.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        SearchReceiptBar(navController = navController)

    }
}


@Composable
fun SearchReceiptBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth().background(PrimaryColor)
            .clip(RoundedCornerShape(32.dp))

    ) {
        val searchQuery = remember { mutableStateOf("") }

        OutlinedTextField(

            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                navController.navigate("${Route.SearchScreen.routeName}/$it")
            },
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
                    tint = PrimaryColor,

                    )
            },

            textStyle = TextStyle(color = Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
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



