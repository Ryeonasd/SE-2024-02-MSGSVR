package dgu.se_2024_02_msgsvr.service

import dgu.se_2024_02_msgsvr.model.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.ResponseEntity

@Service
class MessageService {
    private val restTemplate = RestTemplate()

    fun forwardToDepartments(message: Message): String {
        val relay = message.relay
        val url = when (relay) {
            Relay.PARCEL -> "http://parcel/receive"
            Relay.AIR -> "http://air/receive"
            Relay.OCEAN -> "http://ocean/receive"
            Relay.LAND -> "http://land/receive"
            else -> return "잘못된 부서 정보: $relay"
        }

        return try {
            val response: ResponseEntity<String> = restTemplate.postForEntity(url, message, String::class.java)
            "${response.body} 메시지 전송 성공, URL: $url"
        } catch (e: Exception) {
            "메시지 전송 실패: ${e.message}"
        }
    }
}
