package dgu.se_2024_02_msgsvr.model

data class Message(
    val orderInfo: OrderInfo,
    val insuranceInfo: InsuranceInfo,
    val transportInfo: TransportInfo,
    val customsInfo: CustomsInfo,
    val shipmentStatus: List<ShipmentStatus>,
    val billOfLading: BillOfLading,
    val airWaybill: AirWaybill
)

data class OrderInfo(
    val orderNumber: String,
    val shippingInfo: ShippingInfo,
    val items: List<Item>,
    val trackingNumber: String,
    val containerNumber: String,
    val cargoNumber: String,
    val relay: Relay
)

data class ShippingInfo(
    val originCountry: String,
    val destinationCountry: String,
    val originAddress: Address,
    val destinationAddress: Address,
    val sender: Sender,
    val receiver: Receiver
)

data class Address(
    val city: String,
    val detail: String,
    val postalCode: String
)

data class Sender(
    val name: String,
    val email: String,
    val phone: String,
    val customsId: String
)

data class Receiver(
    val name: String,
    val email: String,
    val phone: String,
    val customsId: String
)

data class Item(
    val name: String,
    val isImported: Boolean,
    val hsCode: String,
    val weight: Double,
    val quantity: Int,
    val x: Int,
    val y: Int,
    val z: Int,
    val hazardous: Boolean,
    val price: Int
)

data class InsuranceInfo(
    val company: String,
    val policyNumber: String,
    val coverageAmount: Int,
    val coverageScope: String,
    val startDate: String,
    val endDate: String,
    val premium: Int
)

data class TransportInfo(
    val originAddress: String,
    val destinationAddress: String,
    val pickupTime: String,
    val estimatedArrivalTime: String,
    val trackingNumber: String
)

data class CustomsInfo(
    val dutyAmount: Int,
    val ftaApplicable: Boolean,
    val paymentMethod: String,
    val customsDeclarationNumber: String
)

data class ShipmentStatus(
    val trackingNumber: String,
    val transportVehicleNumber: String,
    val currentLocation: String,
    val currentStatus: String,
    val lastUpdated: String,
    val remarks: String
)

data class BillOfLading(
    val shipper: Shipper,
    val consignee: Consignee,
    val blNumber: String,
    val loadingPort: String,
    val dischargePort: String,
    val vesselName: String,
    val vesselNumber: String,
    val shipmentDate: String
)

data class AirWaybill(
    val shipper: Shipper,
    val consignee: Consignee,
    val awbNumber: String,
    val departureAirport: String,
    val destinationAirport: String,
    val aircraftName: String,
    val aircraftNumber: String,
)

data class Shipper(
    val name: String,
    val address: String,
    val contact: String
)

data class Consignee(
    val name: String,
    val address: String,
    val contact: String
)

enum class Relay {
    NONE, AIR, OCEAN, LAND, PARCEL
}