package com.chichi.shippingapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = LightPurpleFont
    )
)
val WhiteTextStyleAlpha = TextStyle(
    color = Color.White,
    fontSize = 16.sp
)
val LightTextStyle = TextStyle(
    color = Color.Gray.copy(alpha = 0.7f),
    fontSize = 14.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal
)
val NormalTextStyle = TextStyle(
    color = Color.Black,
    fontSize = 16.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal
)
val MediumTextStyle = TextStyle(
    color = MediumBlack,
    fontSize = 20.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium
)

val BoldTextStyle = TextStyle(
    color = MediumBlack,
    fontSize = 20.sp,
    fontFamily = FontFamily.SansSerif,

    fontWeight = FontWeight.SemiBold
)


