package dgu.se_2024_02_msgsvr

import com.fasterxml.jackson.databind.ObjectMapper
import dgu.se_2024_02_msgsvr.model.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import dgu.se_2024_02_msgsvr.service.MessageService


@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var messageService: MessageService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun messageForwardingTest() {
        val message = Message(
            relay = "PARCEL",
            order_info = OrderInfo(
                shipping_info = ShippingInfo(
                    tracking_number = "TRK987654",
                    origin_address = Address(
                        country = "KR",
                        city = "서울",
                        detail = "서울특별시 중구 필동로1길 30",
                        postal_code = "90001"
                    ),
                    destination_address = Address(
                        country = "KR",
                        city = "고양",
                        detail = "경기도 고양시 일산동구 동국로 32",
                        postal_code = "04510"
                    ),
                    sender = User(
                        name = "동국이",
                        email = "sender@example.com",
                        phone = "010-0000-0000",
                        customs_id = "SENDER123"
                    ),
                    receiver = User(
                        name = "동국이",
                        email = "receiver@example.com",
                        phone = "010-0000-0000",
                        customs_id = "RECEIVER456"
                    )
                )
            ),
            items = listOf(
                Item(
                    tracking_number = "TRK987654",
                    name = "전자제품",
                    is_imported = true,
                    hs_code = "847130",
                    weight = 2.5,
                    quantity = 10,
                    x = 30,
                    y = 20,
                    z = 10,
                    hazardous = false,
                    price = 500000
                )
            ),
            insurance_info = listOf(
                InsuranceInfo(
                    company = "Insurance Co.",
                    policy_number = "POLICY123",
                    coverage_amount = 100000,
                    coverage_scope = "Global",
                    start_date = "2024-01-01",
                    end_date = "2024-12-31",
                    premium = 5000
                )
            ),
            customs_info = listOf(
                CustomsInfo(
                    duty_amount = 5000,
                    fta_applicable = true,
                    payment_method = "Credit",
                    customs_declaration_number = "CUS123"
                )
            ),
            shipment_status = listOf(
                ShipmentStatus(
                    tracking_number = "TRK987654",
                    transport_vehicle_number = "TRUCK1234",
                    current_location = "옥천 HUB 입하",
                    current_status = "운송중",
                    last_updated = "2024-11-19T08:00:00Z",
                    remarks = "태풍으로 인한 배송지연"
                ),
                ShipmentStatus(
                    tracking_number = "TRK987654",
                    transport_vehicle_number = "TRUCK1234",
                    current_location = "트럭 적재",
                    current_status = "운송 중",
                    last_updated = "2024-11-19T12:00:00Z",
                    remarks = "태풍으로 인한 배송지연"
                )
            ),
            bill_of_lading = BillOfLading(
                shipper = UserInfo(
                    name = "Shipper Inc.",
                    address = "서울특별시 중구 필동로1길 30, 90001, KR",
                    contact = "010-0000-0000"
                ),
                consignee = UserInfo(
                    name = "Consignee Co.",
                    address = "경기도 고양시 일산동구 동국로 32, 04510, KR",
                    contact = "010-0000-0000"
                ),
                bl_number = "BL12345",
                loading_port = "인천항",
                discharge_port = "부산항",
                vessel_name = "동국호",
                vessel_number = "OC1234",
                shipment_date = "2024-02-01"
            ),
            air_waybill = AirWaybill(
                shipper = UserInfo(
                    name = "Shipper Inc.",
                    address = "서울특별시 중구 필동로1길 30, 90001, KR",
                    contact = "010-0000-0000"
                ),
                consignee = UserInfo(
                    name = "Consignee Co.",
                    address = "경기도 고양시 일산동구 동국로 32, 04510, KR",
                    contact = "010-0000-0000"
                ),
                awb_number = "AWB12345",
                departure_airport = "GMP",
                destination_airport = "ICN",
                aircraft_name = "보잉 747",
                aircraft_number = "B747800",
                shipment_date = "2024-02-01"
            )
        )

        val messageJson = objectMapper.writeValueAsString(message)

        val deserializedMessage = objectMapper.readValue(messageJson, Message::class.java)
        val relayName = deserializedMessage.relay.lowercase()
        val expectedResponse = "$relayName 전송 성공"

        `when`(messageService.forwardToDepartments(deserializedMessage)).thenReturn(expectedResponse)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/message/receive")
                .contentType("application/json")
                .content(messageJson))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actualResponse = result.response.contentAsString
        println("status code: ${result.response.status}")
        println("response body: $actualResponse")
    }

}
