package com.chichi.shippingapp.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chichi.shippingapp.CircularImage
import com.chichi.shippingapp.R
import com.chichi.shippingapp.ui.theme.BoldTextStyle
import com.chichi.shippingapp.ui.theme.LightGray1
import com.chichi.shippingapp.ui.theme.LightGreen
import com.chichi.shippingapp.ui.theme.LightPeach
import com.chichi.shippingapp.ui.theme.LightTextStyle
import com.chichi.shippingapp.ui.theme.MediumTextStyle
import com.chichi.shippingapp.ui.theme.NormalTextStyle
import com.chichi.shippingapp.ui.theme.OrangeText


@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
               .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Tracking()

           Spacer(modifier = Modifier.height(12.dp))

            AvailableVehicles()


        }

    }
}

@Composable
fun Tracking() {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 0.dp)
    ) {
        Text("Tracking", style = MediumTextStyle)
        Spacer(Modifier.height(16.dp))
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    ShipmentItem(
                        header = "Shipment Number",
                        body = "NEJ20089934122231",
                        bodyTextStyle = BoldTextStyle,
                        headerTextStyle = LightTextStyle
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_cargo_),
                        contentDescription = "ForkLift",
                        modifier = Modifier
                            .size(40.dp)
                            .rotate(-14f)
                            .padding(end = 8.dp)
                    )

                }

                HorizontalDivider(
                    thickness = 1.dp,
                    color = LightGray1,
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {

                    CircularImage(imageId = R.drawable.ic_sender, bgColor = LightPeach)
                    Spacer(Modifier.width(8.dp))
                    ShipmentItem(
                        header = "Sender",
                        body = "Atlanta, 5243",
                        headerTextStyle = LightTextStyle,
                        bodyTextStyle = NormalTextStyle,
                        modifier = Modifier.weight(1f)
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Time", style = LightTextStyle)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_circle_24),
                                contentDescription = "Time image",
                                modifier = Modifier.size(8.dp),
                                tint = Color.Unspecified
                            )

                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "2 day - 3 days", style = NormalTextStyle
                            )
                        }


                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    CircularImage(imageId = R.drawable.ic_receiver, bgColor = LightGreen)
                    Spacer(Modifier.width(8.dp))
                    ShipmentItem(
                        header = "Receiver",
                        body = "Chicago, 6342",
                        headerTextStyle = LightTextStyle,
                        bodyTextStyle = NormalTextStyle,
                        modifier = Modifier.weight(1f)
                    )

                    ShipmentItem(
                        header = "Status",
                        body = "Waiting to collect",
                        headerTextStyle = LightTextStyle,
                        bodyTextStyle = NormalTextStyle,
                        modifier = Modifier.weight(1f)
                    )
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = LightGray1,
                    modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                )


                Text(
                    text = "+ Add Stop",
                    textAlign = TextAlign.Center,
                    color = OrangeText,
                    style = MediumTextStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                )


            }

        }

    }


}

@Composable
fun AvailableVehicles(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .padding(top = 16.dp, start = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column {
            Text("Available vehicles", textAlign = TextAlign.Start, style = MediumTextStyle)
            Spacer(Modifier.height(16.dp))
            val availableVehicles = listOf(
                VehicleDetails("Ocean freight", "International", R.drawable.ic_ocean_vehicle, 1),
                VehicleDetails("Cargo freight", "Reliable", R.drawable.ic_cargo_, 2),
                VehicleDetails("Air freight", "International", R.drawable.ic_air, 3)


            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {

                items(availableVehicles, key = {
                    it.id
                }) { details ->
                    VehicleItem(details.header, details.description, details.image)
                    Spacer(modifier = Modifier.width(12.dp))
                }

            }
        }
    }

}

@Composable
fun ShipmentItem(
    header: String,
    body: String,
    bodyTextStyle: TextStyle,
    headerTextStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(header, style = headerTextStyle)
        Spacer(modifier = Modifier.height(8.dp))

        Text(body, style = bodyTextStyle)
    }
}

data class VehicleDetails(
    val header: String, val description: String, val image: Int, val id: Int
)

@Composable
fun VehicleItem(
    headerText: String, bodyText: String, imageResId: Int
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(158.dp)
            .height(200.dp)

    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
            )

            ShipmentItem(
                header = headerText,
                body = bodyText,
                bodyTextStyle = LightTextStyle,
                headerTextStyle = NormalTextStyle
            )

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color.White)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

