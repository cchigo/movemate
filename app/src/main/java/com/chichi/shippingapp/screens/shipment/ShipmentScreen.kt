package com.chichi.shippingapp.screens.shipment

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chichi.shippingapp.GradientType
import com.chichi.shippingapp.R
import com.chichi.shippingapp.fadingEdge
import com.chichi.shippingapp.getFadeInOpacity
import com.chichi.shippingapp.getHorizontalOffsetX
import com.chichi.shippingapp.ui.theme.BlackFont3
import com.chichi.shippingapp.ui.theme.GrayFont1
import com.chichi.shippingapp.ui.theme.GrayFont3
import com.chichi.shippingapp.ui.theme.LightGray
import com.chichi.shippingapp.ui.theme.LightTextStyle
import com.chichi.shippingapp.ui.theme.MainBg
import com.chichi.shippingapp.ui.theme.MediumTextStyle
import com.chichi.shippingapp.ui.theme.NormalTextStyle
import com.chichi.shippingapp.ui.theme.OrangeBg
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.chichi.shippingapp.ui.theme.ShippingAppTheme
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
fun ShipmentScreen() {
    val shipments = remember { generateShipmentData() }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var onScreenLaunch by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onScreenLaunch = true // Trigger animation when the screen launches
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { values ->
        val all = shipments.size
        val cancelled = shipments.count { it.status == ShipmentStatus.Canceled }
        val inProgress = shipments.count { it.status == ShipmentStatus.InProgress }
        val completed = shipments.count { it.status == ShipmentStatus.Completed }
        val pending = shipments.count { it.status == ShipmentStatus.Pending }

        val tabs = listOf(
            "All" to all,
            "Completed" to completed,
            "In progress" to inProgress,
            "Pending" to pending,
            "Cancelled" to cancelled
        )

        Column(
            modifier = Modifier
                .padding(values)
                .background(MainBg)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Box (modifier = Modifier.background(PrimaryColor)){


                Box(modifier = Modifier.fillMaxWidth()
                    .offset(getHorizontalOffsetX(onScreenLaunch))
                    .alpha(getFadeInOpacity(onScreenLaunch))
                    .background(PrimaryColor)) {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        edgePadding = 20.dp,
                        containerColor = PrimaryColor,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                    .width(tabPositions[selectedTabIndex].contentWidth),
                                color = OrangeBg
                            )
                        })

                    {

                        tabs.forEachIndexed { index, (tabTitle, tabCount) ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = tabTitle,
                                        style = if (selectedTabIndex == index) NormalTextStyle else LightTextStyle,
                                        color = if (selectedTabIndex == index) Color.White else MaterialTheme.colorScheme.onPrimary.copy(
                                            alpha = 0.7f
                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = if (selectedTabIndex == index) OrangeBg else LightGray.copy(
                                                    alpha = 0.2f
                                                ), shape = RoundedCornerShape(48)
                                            )
                                            .padding(horizontal = 8.dp)
                                    ) {
                                        Text(
                                            text = tabCount.toString(),
                                            style = NormalTextStyle,
                                            color = if (selectedTabIndex == index) Color.White else MaterialTheme.colorScheme.onPrimary.copy(
                                                alpha = 0.7f
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Shipment List
            ShippingList(selectedTabIndex, shipments)
        }
    }
}

@Composable
private fun ShippingList(
    selectedTabIndex: Int, shipments: List<ShipmentData>
) {
    val listState = rememberLazyListState()
    var startAnimation by remember { mutableStateOf(false) }

    // Start animation when the tab changes
    LaunchedEffect(selectedTabIndex) {
        startAnimation = false
        delay(100)
        startAnimation = true
    }


    val filteredShipments by remember(selectedTabIndex) {
        derivedStateOf {
            when (selectedTabIndex) {
                0 -> shipments
                1 -> shipments.filter { it.status == ShipmentStatus.Completed }
                2 -> shipments.filter { it.status == ShipmentStatus.InProgress }
                3 -> shipments.filter { it.status == ShipmentStatus.Pending }
                4 -> shipments.filter { it.status == ShipmentStatus.Canceled }
                else -> emptyList()
            }
        }
    }

    Box(
        modifier = Modifier
            .fadingEdge(GradientType.BOTTOM_FADE)
            .background(MainBg)
    ) {

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 12.dp)
        ) {

            item {
                Text(
                    "Shipments",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            itemsIndexed(filteredShipments) { index, shipment ->
                val animationProgress = remember { Animatable(0f) }

                LaunchedEffect(startAnimation) {
                    if (startAnimation) {
                        delay(index * 5L)
                        animationProgress.animateTo(
                            targetValue = 1f, animationSpec = tween(
                                durationMillis = 1000, easing = FastOutLinearInEasing
                            )
                        )
                    }
                }

                ShipmentHistoryItem(shipmentData = shipment, modifier = Modifier.graphicsLayer {
                    alpha = animationProgress.value
                    translationY = (1f - animationProgress.value) * 30f
                })
            }
        }
    }
}


fun generateShipmentData(count: Int = 20): List<ShipmentData> {
    val statuses = ShipmentStatus.entries
    return List(count) {
        ShipmentData(
            title = "Arriving today!",
            description = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today!",
            amount = "$1400 USD",
            date = "Mar 24, 2025",
            status = statuses[Random.nextInt(statuses.size)]
        )
    }
}


data class ShipmentData(
    val id: Long = Random.nextLong(),
    val title: String,
    val description: String,
    val date: String,
    val amount: String,
    val status: ShipmentStatus
)

enum class ShipmentStatus(val data: String) {
    Loading("loading"), InProgress("in-progress"), Pending("pending"), Completed("completed"), Canceled(
        "canceled"
    );
}


@Composable
fun ShipmentHistoryItem(
    shipmentData: ShipmentData, modifier: Modifier = Modifier, alpha: Float = 1f
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(shipmentData.title, style = MediumTextStyle, color = BlackFont3)
                Text(
                    shipmentData.description,
                    style = LightTextStyle,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(shipmentData.amount, style = MediumTextStyle, color = PrimaryColor)
                    Text(
                        "●",
                        color = GrayFont1,
                        modifier = Modifier.padding(horizontal = 6.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(shipmentData.date, color = GrayFont3)
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_shipment),
                contentDescription = "Shipment image",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShipmentScreenPreview() {
    //   ShipmentScreen()
    ShippingAppTheme {

        ShipmentHistoryItem(
            shipmentData = ShipmentData(

                title = "Arriving today!",
                description = "Your delivery, #NEJ20089934122231 from Atlanta, is arriving today!",
                amount = "$400 USD",
                date = "Mar 24, 2025",
                status = ShipmentStatus.Loading
            )
        )
    }
}