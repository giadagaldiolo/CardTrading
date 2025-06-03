package ch.supsi.web.cardgames.repository;

import ch.supsi.web.cardgames.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
