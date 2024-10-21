package by.zemich.sessionmicroservice.service;

import by.zemich.sessionmicroservice.controller.request.GetSessionRequest;
import by.zemich.sessionmicroservice.controller.response.SessionResponse;
import by.zemich.sessionmicroservice.dao.entity.Session;
import by.zemich.sessionmicroservice.properties.SessionConfigProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class RestSessionService {
    private final SessionService sessionService;
    private final SessionConfigProperties sessionConfigProperties;

    public SessionResponse getSession(GetSessionRequest request) {
        return sessionService.findByUserLogin(request.getUserLogin())
                .map(this::documentToResponse)
                .orElseGet(() -> {
                    Session newSession = requestToSession(request);
                    Session savedSession = sessionService.save(newSession);
                    log.info("Значение: {}", sessionConfigProperties.getValidInHours() );
                    return documentToResponse(savedSession);
                });
    }

    private SessionResponse documentToResponse(Session session) {
        return SessionResponse.builder()
                .userLogin(session.getUserLogin())
                .sessionId(session.getId().toString())
                .issuedAt(session.getIssuedAt())
                .expiredAt(session.getExpiredAt())
                .build();
    }

    private Session requestToSession(GetSessionRequest request) {
        return Session.builder()
                .userLogin(request.getUserLogin())
                .issuedAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(sessionConfigProperties.getValidInHours()))
                .build();
    }
}
