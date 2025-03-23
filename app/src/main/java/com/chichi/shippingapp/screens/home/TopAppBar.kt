package com.chichi.shippingapp.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Notifications
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chichi.shippingapp.R
import com.chichi.shippingapp.ui.theme.OrangeBg
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.chichi.shippingapp.ui.theme.ShippingAppTheme
import com.chichi.shippingapp.ui.theme.WhiteTextStyleAlpha


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    isRootRoute: Boolean, currentBottomTab: BottomTab?, onBackClicked: () -> Unit
) {
    if (!isRootRoute) {
        CenterAlignedTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentBottomTab?.title ?: BottomTab.Home.title, color = Color.White
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
    } else {
        //displays Receipt Search top bar if current bottom tab is home ie, default
        UserInfoBar()
    }

}


@Composable
fun UserInfoBar() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(PrimaryColor)
            .padding(16.dp)
    ) {


    }
}


@Composable
fun SearchReceiptBar(){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp)), color = Color.White
    ) {

    }
}


@Composable
fun CircularProfileImage(imageId: Int) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .padding(top = 2.dp)
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    ShippingAppTheme {
        UserInfoBar()
    }
}

