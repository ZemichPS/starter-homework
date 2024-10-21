package by.zemich.sessionauthorizationstarter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
    private String sessionId;
    private String userLogin;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
