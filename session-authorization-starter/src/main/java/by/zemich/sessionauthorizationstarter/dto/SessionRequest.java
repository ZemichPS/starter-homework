package by.zemich.sessionauthorizationstarter.dto;

import lombok.Data;

@Data
public abstract class SessionRequest {
    private String login;
    private Session session;
}
