package by.zemich.sessionmicroservice.dao.repository;

import by.zemich.sessionmicroservice.dao.entity.Session;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Session.class, idClass = String.class)
public interface SessionRepository {
    Optional<Session> findByUserLogin(String userLogin);

    Session save(Session session);

    void delete(Session session);

    List<Session> findAll();

}
