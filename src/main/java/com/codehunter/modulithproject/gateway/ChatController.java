package com.codehunter.modulithproject.gateway;

import com.codehunter.modulithproject.chat.MessageRequestDTO;
import com.codehunter.modulithproject.chat.MessageResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;

//@RestController
@Controller
public class ChatController {
    @GetMapping("/csrf")
    public @ResponseBody String getCsrfToken(HttpServletRequest request) {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return csrf.getToken();
    }
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
//    public ResponseEntity<ResponseDTO<MessageResponseDTO>> countdownTimerSayGreeting(MessageRequestDTO message) {
    public MessageResponseDTO countdownTimerSayGreeting(MessageRequestDTO message) {
        return new MessageResponseDTO("from", "text", Instant.now());
//        return ResponseFormatter.handleSingle(messageResponseDTO, new HttpHeaders(), HttpStatus.OK);
    }
}
