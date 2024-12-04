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
            orderInfo = OrderInfo(
                orderNumber = "ORD123456",
                shippingInfo = ShippingInfo(
                    originCountry = "KR",
                    destinationCountry = "KR",
                    originAddress = Address(
                        city = "서울",
                        detail = "서울특별시 중구 필동로1길 30",
                        postalCode = "90001"
                    ),
                    destinationAddress = Address(
                        city = "고양",
                        detail = "경기도 고양시 일산동구 동국로 32",
                        postalCode = "04510"
                    ),
                    sender = Sender(
                        name = "동국이",
                        email = "sender@example.com",
                        phone = "010-0000-0000",
                        customsId = "SENDER123"
                    ),
                    receiver = Receiver(
                        name = "동국이",
                        email = "receiver@example.com",
                        phone = "010-0000-0000",
                        customsId = "RECEIVER456"
                    )
                ),
                items = emptyList(),
                trackingNumber = "TRK987654",
                containerNumber = "CONT56789",
                cargoNumber = "CARGO12345",
                relay = Relay.PARCEL
            ),
            insuranceInfo = InsuranceInfo(
                company = "Insurance Co.",
                policyNumber = "POLICY123",
                coverageAmount = 100000,
                coverageScope = "Global",
                startDate = "2024-01-01",
                endDate = "2024-12-31",
                premium = 5000
            ),
            transportInfo = TransportInfo(
                originAddress = "서울",
                destinationAddress = "고양",
                pickupTime = "2024-02-01 10:00",
                estimatedArrivalTime = "2024-02-02 12:00",
                trackingNumber = "TRK987654"
            ),
            customsInfo = CustomsInfo(
                dutyAmount = 5000,
                ftaApplicable = true,
                paymentMethod = "Credit",
                customsDeclarationNumber = "CUS123"
            ),
            shipmentStatus = emptyList(),
            billOfLading = BillOfLading(
                shipper = Shipper(
                    name = "Shipper Inc.",
                    address = "Shipper Address",
                    contact = "Shipper Contact"
                ),
                consignee = Consignee(
                    name = "Consignee Co.",
                    address = "Consignee Address",
                    contact = "Consignee Contact"
                ),
                blNumber = "BL12345",
                loadingPort = "Port1",
                dischargePort = "Port2",
                vesselName = "Vessel1",
                vesselNumber = "Vessel123",
                shipmentDate = "2024-02-01"
            ),
            airWaybill = AirWaybill(
                shipper = Shipper(
                    name = "Shipper Inc.",
                    address = "Shipper Address",
                    contact = "Shipper Contact"
                ),
                consignee = Consignee(
                    name = "Consignee Co.",
                    address = "Consignee Address",
                    contact = "Consignee Contact"
                ),
                awbNumber = "AWB12345",
                departureAirport = "Airport1",
                destinationAirport = "Airport2",
                aircraftName = "Aircraft1",
                aircraftNumber = "Aircraft123"
            )
        )

        val expectedResponse = "메시지 수신 완료"

        `when`(messageService.forwardToDepartments(message)).thenReturn("메시지 수신 완료")

        val messageJson = objectMapper.writeValueAsString(message)

        mockMvc.perform(MockMvcRequestBuilders.post("/message/receive")
            .contentType("application/json")
            .content(messageJson))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(expectedResponse))
    }
}
