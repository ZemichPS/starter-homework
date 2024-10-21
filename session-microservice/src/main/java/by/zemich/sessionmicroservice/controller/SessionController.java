package by.zemich.sessionmicroservice.controller;

import by.zemich.sessionmicroservice.controller.request.GetSessionRequest;
import by.zemich.sessionmicroservice.controller.response.SessionResponse;
import by.zemich.sessionmicroservice.service.RestSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final RestSessionService restSessionService;

    @PostMapping
    public ResponseEntity<SessionResponse> getSession(@RequestBody GetSessionRequest request) {
        SessionResponse session = restSessionService.getSession(request);
        return ResponseEntity.ok(session);
    }

}
