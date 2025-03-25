package com.chichi.shippingapp.screens.calculate


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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

                    PackagingSection()

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            //Category
            AnimatedVisibility(
                visible = onScreenLaunch, enter = slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(600)
                )
            ) {
                Column {
                    Text(
                        text = "Categories", style = MediumTextStyle.copy(color = BlackFont4)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "What are you sending?",
                        style = NormalTextStyle.copy(color = LightGray3)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    CategoryTagGrid()

                }
            }
        }


    }
}
@Composable
fun PackagingSection() {
    var isActive by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Box") }

    Box {
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
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_shipment),
                        contentDescription = "Package",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 8.dp, bottom = 4.dp)
                    )

                    VerticalDivider(
                        color = Color.LightGray, modifier = Modifier
                            .height(24.dp)
                            .width(1.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        selectedOption, style = MediumTextStyle
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
                text = { Text("  📦  Box", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                onClick = {
                    selectedOption = "Box"
                    isActive = false
                },
                modifier = Modifier.padding(4.dp)
            )
            DropdownMenuItem(
                text = { Text("  📦  Package", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                onClick = {
                    selectedOption = "Package"
                    isActive = false
                },
                modifier = Modifier.padding(4.dp)
            )
            DropdownMenuItem(
                text = { Text("  🚚  Shipment", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                onClick = {
                    selectedOption = "Shipment"
                    isActive = false
                },
                modifier = Modifier.padding(4.dp)
            )
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

            CategoryTagItem(
                category = category,
                isSelected = isSelected,
                onClick = {
                    if (isSelected) {
                        selectedCategories.remove(category.category)
                    } else {
                        selectedCategories.add(category.category)
                    }
                }
            )
        }
    }
}


@Composable
fun CategoryTagItem(category: CategoryType, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        style = NormalTextStyle.copy(fontSize = 16.sp),
        text = category.category,
        modifier = Modifier
            .clickable { onClick() }
            .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray, RoundedCornerShape(8.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .wrapContentSize(),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black
    )
}

data class CategoryType(val category: String)
@Preview(showBackground = true)
@Composable
fun ShippingFormScreenPreview() {
    CategoryTagGrid()
}