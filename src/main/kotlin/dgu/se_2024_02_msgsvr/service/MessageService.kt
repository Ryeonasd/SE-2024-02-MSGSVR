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
            Relay.PARCEL -> "http://test1/parcel"
            Relay.AIR -> "http://test2/air"
            Relay.OCEAN -> "http://test3/ocean"
            Relay.LAND -> "http://192.168.219.108:8080"
            else -> return "잘못된 부서 정보: $relay"
        }

        val uri = when (relay) {
            Relay.PARCEL -> "/receive"
            Relay.AIR -> "/receive"
            Relay.OCEAN -> "/receive"
            Relay.LAND -> "/receive"
        }

        return try {
            val response: ResponseEntity<String> = restTemplate.postForEntity(url, message, String::class.java, uri)
            "${response.body} 메시지 전송 성공, URL: $url"
        } catch (e: Exception) {
            "메시지 전송 실패: ${e.message}"
        }
    }
}
