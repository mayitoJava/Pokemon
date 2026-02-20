package PokeApi.Programacion.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import PokeApi.Programacion.JPA.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);
}
