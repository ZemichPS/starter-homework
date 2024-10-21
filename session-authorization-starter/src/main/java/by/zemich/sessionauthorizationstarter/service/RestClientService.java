package by.zemich.sessionauthorizationstarter.service;

import by.zemich.sessionauthorizationstarter.dto.GetSessionRequest;
import by.zemich.sessionauthorizationstarter.dto.Session;
import by.zemich.sessionauthorizationstarter.dto.SessionResponse;
import by.zemich.sessionauthorizationstarter.properties.AuthorisationGlobalProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class RestClientService {
    private final RestTemplate restTemplate;
    private final AuthorisationGlobalProperties globalProperties;

    public Session requestSessionByLogin(String login) {
        String url = globalProperties.getSessionServiceHost() + "/sessions";
        SessionResponse response = null;
        try {
            response = restTemplate.postForEntity(url, new GetSessionRequest(login), SessionResponse.class).getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to request session", e);
        }
        assert response != null;
        return responseToSession(response);
    }


    private Session responseToSession(SessionResponse sessionResponse) {
        return Session.builder()
                .id(sessionResponse.getSessionId())
                .issuedAt(sessionResponse.getIssuedAt())
                .expiredAt(sessionResponse.getExpiredAt())
                .build();
    }
}
