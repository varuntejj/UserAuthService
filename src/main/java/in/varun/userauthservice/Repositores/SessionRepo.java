package in.varun.userauthservice.Repositores;

import in.varun.userauthservice.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<Session, Long> {
    Optional<Session> findByToken(String token);
}
