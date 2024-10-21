package by.zemich.sessionauthorizationstarter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class GetSessionRequest {
    private String userLogin;
}
