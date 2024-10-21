package by.zemich.sessionmicroservice.service;

import by.zemich.sessionmicroservice.dao.entity.Session;
import by.zemich.sessionmicroservice.dao.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class SessionService {

    private final SessionRepository sessionRepository;

    public Optional<Session> findByUserLogin(String userLogin) {
        return sessionRepository.findByUserLogin(userLogin);
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public void delete(Session session) {
        sessionRepository.delete(session);
    }

}
