package dgu.se_2024_02_msgsvr.service

import dgu.se_2024_02_msgsvr.model.Message
import dgu.se_2024_02_msgsvr.model.Relay
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.ResponseEntity

@Service
class MessageService {
    private val restTemplate = RestTemplate()

    fun forwardToDepartments(message: Message): String {
        val relay = message.orderInfo.relay
        val url = when (relay) {
            Relay.NONE -> return "Relay가 None 입니다."
            Relay.PARCEL -> "http://test1/parcel"
            Relay.AIR -> "http://test2/air"
            Relay.OCEAN -> "http://test3/ocean"
            Relay.LAND -> "http://test4/land"
        }

        return try {
            val response: ResponseEntity<String> = restTemplate.postForEntity(url, message, String::class.java)
            "${response.body} 전송 성공"
        } catch (e: Exception) {
            "${e.message} 전송 실패"
        }
    }
}
