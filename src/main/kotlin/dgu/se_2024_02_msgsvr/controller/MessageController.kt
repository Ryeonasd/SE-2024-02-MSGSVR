package dgu.se_2024_02_msgsvr.controller

import dgu.se_2024_02_msgsvr.service.MessageService
import dgu.se_2024_02_msgsvr.model.Message
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/message")
class MessageController(private val messageService: MessageService) {

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: Message): ResponseEntity<String> {
        try {
            val result = messageService.forwardToDepartments(message)
            return ResponseEntity.ok(result)
        }
        catch (e: Exception) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}

