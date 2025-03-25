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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
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

enum class GradientType {
    BOTTOM_FADE, TOP_FADE, TOP_BOTTOM_FADE, LEFT_RIGHT_FADE, RADIAL_FADE
}




