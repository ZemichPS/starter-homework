package by.zemich.sessionmicroservice.service;

import by.zemich.sessionmicroservice.dao.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final Predicate<Session> sessionExpiredPredicate = session -> {
        LocalDateTime issuedAt = session.getIssuedAt();
        return Duration.between(issuedAt, LocalDateTime.now()).toHours()   >= 24;
    };

    private final SessionService sessionService;

    @Scheduled(fixedRate = 60_000)
    private void checkOnSessionExpired() {
        sessionService.findAll().stream()
                .filter(sessionExpiredPredicate)
                .forEach(sessionService::delete);
    }

}
