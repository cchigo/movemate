package com.chichi.shippingapp.screens.calculate


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chichi.shippingapp.R
import com.chichi.shippingapp.ui.theme.BlackFont4
import com.chichi.shippingapp.ui.theme.LightGray2
import com.chichi.shippingapp.ui.theme.LightGray3
import com.chichi.shippingapp.ui.theme.LightGrey1
import com.chichi.shippingapp.ui.theme.MediumTextStyle
import com.chichi.shippingapp.ui.theme.NormalTextStyle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun CalculateScreen() {
    val senderLocationText = remember { MutableStateFlow("") }

    val receiverLocationText = remember { MutableStateFlow("") }
    val weightText = remember { MutableStateFlow("") }
    val isFormValid = remember { mutableStateOf(false) }
    val userNameError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }

    var onScreenLaunch by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onScreenLaunch = true // Trigger animation when the screen launches
    }


    LaunchedEffect(senderLocationText, receiverLocationText, weightText) {
        combine(
            senderLocationText.debounce(300),
            receiverLocationText.debounce(300),
            weightText.debounce(300)

        ) { sLocation, rLocation, weightText ->
            Triple(sLocation, rLocation, weightText)
        }.collect { (s, r, w) ->
            isFormValid.value = s.isNotEmpty() && r.isNotEmpty() && r.isNotEmpty()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding(4.dp),
    ) { values ->
        Column() {
            Text(
                text = "Destination", style = MediumTextStyle.copy(color = BlackFont4)
            )
            Spacer(modifier = Modifier.height(20.dp))

            DestinationSection(values, senderLocationText, receiverLocationText, weightText)
            //  }
            Spacer(modifier = Modifier.height(20.dp))


            //Packaging
            AnimatedVisibility(
                visible = onScreenLaunch, enter = slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(600)
                )
            ) {
                Column {
                    Text(
                        text = "Packaging", style = MediumTextStyle.copy(color = BlackFont4)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "What are you sending?",
                        style = NormalTextStyle.copy(color = LightGray3)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }


    }
}




@Composable
private fun DestinationSection(
    values: PaddingValues,
    senderLocationText: MutableStateFlow<String>,
    receiverLocationText: MutableStateFlow<String>,
    weightText: MutableStateFlow<String>
) {
    Column(
        modifier = Modifier
            .padding(values)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Red)
            .wrapContentSize()
            .padding(12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedTextField(
            value = senderLocationText.collectAsState().value,
            onValueChange = { senderLocationText.value = it },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(LightGray2, shape = RoundedCornerShape(4.dp)),
            leadingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = R.drawable.ic_sender_loaction),
                        contentDescription = "Sender location icon",
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp)
                            .size(24.dp)
                    )
                    VerticalDivider(
                        color = Color.LightGray,
                        modifier = Modifier
                            .height(24.dp)
                            .padding(end = 0.dp)
                            .width(1.dp)
                    )
                }
            },
            placeholder = { Text("Sender location", color = LightGray3, style = NormalTextStyle) },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Transparent,
                disabledContainerColor = Transparent,
                focusedBorderColor = Transparent,
                unfocusedBorderColor = Transparent
            ),
            singleLine = true,
            textStyle = MediumTextStyle
        )


        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = receiverLocationText.collectAsState().value,
            onValueChange = { receiverLocationText.value = it },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(LightGray2, shape = RoundedCornerShape(4.dp)),
            leadingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = R.drawable.ic_receiver_loaction),
                        contentDescription = "Receiver location icon",
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp)
                            .size(24.dp)
                    )
                    VerticalDivider(
                        color = Color.LightGray,
                        modifier = Modifier
                            .height(24.dp)
                            .padding(end = 0.dp)
                            .width(1.dp)
                    )
                }
            },
            placeholder = {
                Text(
                    "Receiver location", color = LightGray3, style = NormalTextStyle
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Transparent,
                disabledContainerColor = Transparent,
                focusedBorderColor = Transparent,
                unfocusedBorderColor = Transparent
            ),
            singleLine = true,
            textStyle = MediumTextStyle
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = weightText.collectAsState().value,
            onValueChange = { weightText.value = it },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(LightGray2, shape = RoundedCornerShape(4.dp)),
            leadingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = R.drawable.ic_weight),
                        contentDescription = "weightText icon",
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp)
                            .size(24.dp)
                    )
                    VerticalDivider(
                        color = Color.LightGray,
                        modifier = Modifier
                            .height(24.dp)
                            .padding(end = 0.dp)
                            .width(1.dp)
                    )
                }
            },
            placeholder = { Text("Approx weight", color = LightGray3, style = NormalTextStyle) },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Transparent,
                disabledContainerColor = Transparent,
                focusedBorderColor = Transparent,
                unfocusedBorderColor = Transparent
            ),
            singleLine = true,
            textStyle = MediumTextStyle
        )
//            Button(
//                onClick = {
//                    if (isFormValid.value) {
//
//
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                enabled = isFormValid.value
//            ) {
//                Text(text = "Enter", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
//            }
    }
}

@Preview(showBackground = true)
@Composable
fun ShippingFormScreenPreview() {
    CalculateScreen()
}