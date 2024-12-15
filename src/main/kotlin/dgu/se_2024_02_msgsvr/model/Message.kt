package dgu.se_2024_02_msgsvr.model

data class Message(
    val relay: Relay?,
    val order_info: OrderInfo?,
    val items: List<Item>?,
    val insurance_info: List<InsuranceInfo>?,
    val customs_info: List<CustomsInfo>?,
    val shipment_status: List<ShipmentStatus>?,
    val bill_of_lading: BillOfLading?,
    val air_waybill: AirWaybill?
)

data class OrderInfo(
    val shipping_info: ShippingInfo?
)

data class ShippingInfo(
    val tracking_number: String?,
    val origin_address: Address?,
    val destination_address: Address?,
    val sender: User?,
    val receiver: User?
)

data class Address(
    val country: String?,
    val city: String?,
    val detail: String?,
    val postal_code: String?
)

data class User(
    val name: String?,
    val email: String?,
    val phone: String?,
    val customs_id: String?
)

data class Item(
    val tracking_number: String?,
    val name: String?,
    val is_imported: Boolean?,
    val hs_code: String?,
    val weight: Double?,
    val quantity: Int?,
    val x: Int?,
    val y: Int?,
    val z: Int?,
    val hazardous: Boolean?,
    val price: Int?
)

data class InsuranceInfo(
    val company: String?,
    val policy_number: String?,
    val coverage_amount: Int?,
    val coverage_scope: String?,
    val start_date: String?,
    val end_date: String?,
    val premium: Int?
)

data class CustomsInfo(
    val duty_amount: Int?,
    val fta_applicable: Boolean?,
    val payment_method: String?,
    val customs_declaration_number: String?
)

data class ShipmentStatus(
    val tracking_number: String?,
    val transport_vehicle_number: String?,
    val current_location: String?,
    val current_status: String?,
    val last_updated: String?,
    val remarks: String?
)

data class BillOfLading(
    val shipper: UserInfo?,
    val consignee: UserInfo?,
    val bl_number: String?,
    val loading_port: String?,
    val discharge_port: String?,
    val vessel_name: String?,
    val vessel_number: String?,
    val shipment_date: String?
)

data class AirWaybill(
    val shipper: UserInfo?,
    val consignee: UserInfo?,
    val awb_number: String?,
    val departure_airport: String?,
    val destination_airport: String?,
    val aircraft_name: String?,
    val aircraft_number: String?,
    val shipment_date: String?
)

data class UserInfo(
    val name: String?,
    val address: String?,
    val contact: String?
)

enum class Relay {
    AIR, OCEAN, LAND, PARCEL
}
