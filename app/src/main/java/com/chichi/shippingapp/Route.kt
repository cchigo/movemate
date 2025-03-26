package com.chichi.shippingapp

sealed class Route(val routeName: String) {
    data object MainScreen: Route("mainTab")
    data object HomeTab: Route("homeTab")
    data object CalculateTab: Route("calculateTab")
    data object ShipmentTab: Route("shipmentTap")
    data object ProfileTab: Route("profileTab")
    data object ReceiptScreen: Route("receiptScreen")
    data object SearchScreen: Route("searchScreen")


    open val navCommand = routeName

}