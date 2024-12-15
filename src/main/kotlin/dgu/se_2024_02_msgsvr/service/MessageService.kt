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
        val relay = message.relay
        val url = when (relay) {
            "PARCEL" -> "http://localhost:3000/parcel"
            "AIR" -> "http://test2/air"
            "OCEAN" -> "http://test3/ocean"
            "LAND" -> "http://test4/land"
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
