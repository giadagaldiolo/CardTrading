package ch.supsi.web.cardgames.repository;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findByCnameContainingIgnoreCase(String name);
    List<Card> findByCardType(CardType type);
    List<Card> findByCnameContainingIgnoreCaseAndCardType(String name, CardType type);

}

