package com.chichi.shippingapp

sealed class Route(val routeName: String) {
    data object HomeTab: Route("homeTab")
    data object CalculateTab: Route("calculateTab")
    data object ShipmentTab: Route("shipmentTap")
    data object ProfileTab: Route("profileTab")


    open val navCommand = routeName

}