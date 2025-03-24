package com.chichi.shippingapp.screens.shipment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chichi.shippingapp.ui.theme.LightGray
import com.chichi.shippingapp.ui.theme.LightTextStyle
import com.chichi.shippingapp.ui.theme.MediumTextStyle
import com.chichi.shippingapp.ui.theme.NormalTextStyle
import com.chichi.shippingapp.ui.theme.OrangeBg
import com.chichi.shippingapp.ui.theme.PrimaryColor
import com.chichi.shippingapp.ui.theme.ShippingAppTheme
import kotlin.random.Random

@Composable
fun ShipmentScreen() {
    val shipments = generateShipmentData()

    Scaffold (
        modifier = Modifier.fillMaxSize().background(Color.Green)

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

        var selectedTabIndex by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier
                .padding(values)
                .fillMaxWidth()
                .background(Color.Green),
            verticalArrangement = Arrangement.Top
        ) {
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
                }
            ) {
                tabs.forEachIndexed { index, (tabTitle, tabCount) ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)

                    ) {
                        // Text and count
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // Text
                            Text(
                                text = tabTitle,
                                style = if (selectedTabIndex == index) NormalTextStyle else LightTextStyle,
                                color = if (selectedTabIndex == index)
                                    Color.White
                                else
                                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )

                            // Number
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (selectedTabIndex == index)
                                            OrangeBg
                                        else
                                            LightGray.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(48)
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
}



fun generateShipmentData(count: Int = 12): List<ShipmentData> {
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
    Loading("loading"),
    InProgress("in-progress"),
    Pending("pending"),
    Completed("completed"),
    Canceled("canceled");
}
@Preview(showBackground = true)
@Composable
fun ShipmentScreenPreview() {
    ShippingAppTheme {
        ShipmentScreen()
    }
}