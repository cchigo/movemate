package com.chichi.shippingapp.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chichi.shippingapp.R
import com.chichi.shippingapp.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    isRootRoute: Boolean,
    currentBottomTab: BottomTab?,
    onBackClicked: () -> Unit
) {
   // ReceiptSearchScreen()
    if(!isRootRoute){
        CenterAlignedTopAppBar(
            title = {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = currentBottomTab?.title ?: BottomTab.Home.title,
                        color = Color.White
                    )


                }
            },
            navigationIcon = {
                if (!isRootRoute) {
                    IconButton(
                        onClick = onBackClicked
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "menu items"
                        )
                    }
                }
            },

            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryColor,
                titleContentColor = Color.Black,
                navigationIconContentColor = Color.Black,
            ),

            )
    }else{
        //displays Receipt Search top bar if current tap is home
        ReceiptSearchTopBar()
    }

}


@Composable
fun ReceiptSearchTopBar() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(PrimaryColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_calculate_24), // Replace with your profile image
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Your location",
                    style = TextStyle(color = Color.White, fontSize = 12.sp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Wertheimer, Illinois",
                        style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold)
                    )
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.MailOutline,
                    contentDescription = "Receipt",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Enter the receipt number...",
                    style = TextStyle(color = Color.Gray)
                )
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    modifier = Modifier.clip(CircleShape),
                    color = Color(0xFFFF9800) // Orange color
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Receipt",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
            }
        }
    }
}
