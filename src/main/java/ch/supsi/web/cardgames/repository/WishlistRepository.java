package ch.supsi.web.cardgames.repository;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.User;
import ch.supsi.web.cardgames.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistItem, Integer> {
    List<WishlistItem> findByUser(User user);
    boolean existsByUserAndCard(User user, Card card);
    void deleteByUserAndCard(User user, Card card);

    @Query("SELECT w.card FROM WishlistItem w WHERE w.user = :user")
    List<Card> findCardsByUser(@Param("user") User user);

    void deleteByCardId(int cardId);
}
