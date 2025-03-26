package com.chichi.shippingapp.screens.calculate


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chichi.shippingapp.HorizontalSpacer
import com.chichi.shippingapp.R
import com.chichi.shippingapp.Route
import com.chichi.shippingapp.ui.theme.BlackFont3
import com.chichi.shippingapp.ui.theme.DarkBg
import com.chichi.shippingapp.ui.theme.LightGray2
import com.chichi.shippingapp.ui.theme.LightGray3
import com.chichi.shippingapp.ui.theme.LightGrey1
import com.chichi.shippingapp.ui.theme.MainBg
import com.chichi.shippingapp.ui.theme.MediumTextStyle
import com.chichi.shippingapp.ui.theme.NormalTextStyle
import com.chichi.shippingapp.ui.theme.OrangeBg
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun CalculateScreen(
    navController: NavController,
    onQuestionClicked: (() -> Unit)? = null // Nullable lambda
) {
    val senderLocationText = remember { MutableStateFlow("") }

    val receiverLocationText = remember { MutableStateFlow("") }
    val weightText = remember { MutableStateFlow("") }
    val isFormValid = remember { mutableStateOf(false) }

    val displayReceipt = remember { mutableStateOf(false) }
    val selectedPackaging = remember { mutableStateOf("Box") }

    val selectedOption by remember { mutableStateOf("Box") }


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
            isFormValid.value = s.isNotEmpty() && r.isNotEmpty() && w.isNotEmpty()
        }
    }

    Column(
        modifier = Modifier
            .background(MainBg)
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Destination", style = MediumTextStyle.copy(color = BlackFont3)
        )
        Spacer(modifier = Modifier.height(12.dp))

        DestinationSection(senderLocationText, receiverLocationText, weightText)
        HorizontalSpacer()

        //Packaging
        AnimatedVisibility(
            visible = onScreenLaunch, enter = slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(600)
            )
        ) {
            Column {
                Text(
                    text = "Packaging", style = MediumTextStyle.copy(color = DarkBg)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "What are you sending?", style = NormalTextStyle.copy(color = LightGray3)
                )

                HorizontalSpacer()
                PackagingDropdown(selectedPackaging)

            }
        }

        HorizontalSpacer()
        //Category
        AnimatedVisibility(
            visible = onScreenLaunch, enter = slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(600)
            )
        ) {
            Column {
                Text(
                    text = "Categories", style = MediumTextStyle.copy(color = DarkBg)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "What are you sending?", style = NormalTextStyle.copy(color = LightGray3)
                )
                HorizontalSpacer()
                CategoryTagGrid()

            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        SubmitButton(
            navController = navController,
            isFormValid = isFormValid,
            displayReceipt = displayReceipt,
            onQuestionClicked = null
        )
    }

}

@Composable
fun DisplayReceiptScreen() {

}

@Composable
fun PackagingDropdown(selectedPackaging: MutableState<String>) {
    var isActive by remember { mutableStateOf(false) }

    Card(elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Box() {
            OutlinedButton(
                onClick = { isActive = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.White,
                    disabledContainerColor = LightGrey1,
                    disabledContentColor = LightGrey1
                ),
                shape = RoundedCornerShape(12.dp),
                border = null
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_shipment),
                            contentDescription = "Package",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 8.dp, bottom = 4.dp)
                        )

                        VerticalDivider(
                            color = Color.LightGray, modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        //selected text state
                        Text(
                            selectedPackaging.value,
                            style = MediumTextStyle,
                            textAlign = TextAlign.Center
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = MaterialTheme.colorScheme.outlineVariant,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = isActive,
                onDismissRequest = { isActive = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .background(LightGray2)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            "  📦  Box",
                            style = MediumTextStyle,
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        selectedPackaging.value = "Box"
                        isActive = false
                    },
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            "  📦  Package",
                            style = MediumTextStyle
                        )
                    },
                    onClick = {
                        selectedPackaging.value = "Package"
                        isActive = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            "  🚚  Shipment",
                            style = MediumTextStyle
                        )
                    },
                    onClick = {
                        selectedPackaging.value = "Shipment"
                        isActive = false
                    }
                )
            }

        }
    }
}


@Composable
private fun DestinationSection(
    //  values: PaddingValues,
    senderLocationText: MutableStateFlow<String>,
    receiverLocationText: MutableStateFlow<String>,
    weightText: MutableStateFlow<String>
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .wrapContentSize()
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            OutlinedTextField(
                value = senderLocationText.collectAsState().value,
                onValueChange = { senderLocationText.value = it },
                modifier = Modifier

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
                placeholder = {
                    Text(
                        "Sender location", color = LightGray3, style = NormalTextStyle
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

            Spacer(modifier = Modifier.height(12.dp))
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
                placeholder = {
                    Text(
                        "Approx weight", color = LightGray3, style = NormalTextStyle
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
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryTagGrid() {
    val categories = listOf(
        CategoryType("Documents"),
        CategoryType("Glass"),
        CategoryType("Liquid"),
        CategoryType("Food"),
        CategoryType("Electronic"),
        CategoryType("Product"),
        CategoryType("Others")
    )
    val selectedCategories = remember { mutableStateListOf<String>() }


    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.forEach { category ->
            val isSelected = selectedCategories.contains(category.category)

            CategoryTagItem(category = category, isSelected = isSelected, onClick = {
                if (isSelected) {
                    selectedCategories.remove(category.category)
                } else {
                    selectedCategories.add(category.category)
                }
            })
        }
    }
}


@Composable
fun CategoryTagItem(category: CategoryType, isSelected: Boolean, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .background(
            if (isSelected) DarkBg else Transparent, RoundedCornerShape(8.dp)
        )
        .border(
            1.dp, if (isSelected) Transparent else DarkBg, RoundedCornerShape(8.dp)
        )
        .padding(horizontal = 14.dp, vertical = 10.dp)
        .clickable { onClick() }) {
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.round_done_24),
                modifier = Modifier.size(12.dp),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = category.category,
            style = NormalTextStyle.copy(fontSize = 16.sp),
            color = if (isSelected) Color.White else DarkBg
        )
    }
}

data class CategoryType(val category: String)

@Composable
fun SubmitButton(
    navController: NavController,
    isFormValid: MutableState<Boolean>,
    displayReceipt: MutableState<Boolean>,
    onQuestionClicked: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Button(
            onClick = {
                if (isFormValid.value) {
                    onQuestionClicked?.invoke() // Invoke the lambda if it's not null
                    navController.navigate(Route.ReceiptScreen.routeName) // Navigate to ReceiptScreen
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            enabled = isFormValid.value,
            contentPadding = PaddingValues(20.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = OrangeBg.copy(alpha = 0.4f),
                disabledContentColor = Color.White,
                containerColor = OrangeBg,
                contentColor = Color.White

            )
        ) {
            Text(
                text = "Calculate",
                fontSize = 18.sp,
                style = NormalTextStyle.copy(color = Color.White)
            )
        }



    }


}

@Composable
fun EstimateScreen() {
    Column (modifier = Modifier.fillMaxSize()){
        Text(text = "Calculate")
    }
}


@Preview(showBackground = true)
@Composable
fun ShippingFormScreenPreview() {
    // CalculateScreen(screensNavigator)
}