package by.zemich.sessionmicroservice.controller.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
public class SessionResponse {
    private String sessionId;
    private String userLogin;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
