package ch.supsi.web.cardgames.repository;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.cart c WHERE c.id = :cardId")
    List<User> findUsersByCartId(@Param("cardId") int cardId);
}
