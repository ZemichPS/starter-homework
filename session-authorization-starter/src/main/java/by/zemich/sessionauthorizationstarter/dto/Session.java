package by.zemich.sessionauthorizationstarter.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
public class Session {
    private String id;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
