package com.chichi.shippingapp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chichi.shippingapp.screens.shipment.ShipmentData
import com.chichi.shippingapp.screens.shipment.ShipmentStatus
import com.chichi.shippingapp.screens.shipment.ShippingStatusText
import com.chichi.shippingapp.ui.theme.DarkGreen2
import com.chichi.shippingapp.ui.theme.GreenText2
import com.chichi.shippingapp.ui.theme.LightRed
import com.chichi.shippingapp.ui.theme.LoadingText2
import com.chichi.shippingapp.ui.theme.PendingText


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

@Composable
fun HorizontalSpacer(){
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun Modifier.fadingEdge(gradientType: GradientType): Modifier {
    val brush = getGradientBrush(gradientType)

    return this
        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        .drawWithContent {
            drawContent()
            drawRect(brush = brush, blendMode = BlendMode.DstIn)
        }
}


fun getGradientBrush(gradientType: GradientType): Brush {
    return when (gradientType) {
        GradientType.BOTTOM_FADE -> Brush.verticalGradient(
            0.8f to Color.Red, 1f to Color.Transparent
        )

        GradientType.TOP_FADE -> Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.Red)
        GradientType.TOP_BOTTOM_FADE -> Brush.verticalGradient(
            0f to Color.Transparent, 0.3f to Color.Red, 0.7f to Color.Red, 1f to Color.Transparent
        )

        GradientType.LEFT_RIGHT_FADE -> Brush.horizontalGradient(
            0f to Color.Transparent, 0.1f to Color.Red, 0.9f to Color.Red, 1f to Color.Transparent
        )

        GradientType.RADIAL_FADE -> Brush.radialGradient(0f to Color.Red, 0.5f to Color.Transparent)
    }
}

@Composable
fun getHorizontalOffsetX(onScreenLaunch: Boolean = false): Dp {
    val offsetX by animateDpAsState(
        targetValue = if (onScreenLaunch) 0.dp else (300).dp,
        animationSpec = tween(durationMillis = 1500)
    )

    return offsetX
}
@Composable
fun getFadeInOpacity(onScreenLaunch: Boolean): Float {
    val opacity by animateFloatAsState(
        targetValue = if (onScreenLaunch) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
    return opacity
}

@Composable
 fun getShippingStatusText(shipmentData: ShipmentData): ShippingStatusText {
    val statusData: ShippingStatusText = when (shipmentData.status) {
        ShipmentStatus.Completed -> {
            ShippingStatusText("completed", DarkGreen2, R.drawable.ic_completed)
        }
        ShipmentStatus.InProgress -> {
            ShippingStatusText("in-progress", GreenText2, R.drawable.ic_progress_state)
        }
        ShipmentStatus.Pending -> {
            ShippingStatusText("pending", PendingText, R.drawable.ic_pending_state)
        }
        ShipmentStatus.Canceled -> {
            ShippingStatusText("canceled", LightRed, R.drawable.ic_canceled)
        }
        ShipmentStatus.Loading -> {
            ShippingStatusText("loading", LoadingText2, R.drawable.ic_loading_state)
        }
    }
    return statusData
}

enum class GradientType {
    BOTTOM_FADE, TOP_FADE, TOP_BOTTOM_FADE, LEFT_RIGHT_FADE, RADIAL_FADE
}




