package com.chichi.shippingapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.OffsetEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun CircularImage(imageId: Int, bgColor: Color? = null) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(bgColor ?: Color.Gray)
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Picture",
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

