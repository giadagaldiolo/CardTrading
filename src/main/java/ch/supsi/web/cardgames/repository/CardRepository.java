package ch.supsi.web.cardgames.repository;

import ch.supsi.web.cardgames.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

}

