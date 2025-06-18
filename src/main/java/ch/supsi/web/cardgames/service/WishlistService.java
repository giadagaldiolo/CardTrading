package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.WishlistItem;
import ch.supsi.web.cardgames.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.supsi.web.cardgames.model.User;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;


    public void addCardToWishlist(User user, Card card) {
        if (!isCardInWishlist(user,card)) {
            WishlistItem item = new WishlistItem(user, card);
            wishlistRepository.save(item);
        }
    }

    @Transactional
    public void deleteCardFromWishlist(User user, Card card) {
        if (isCardInWishlist(user,card)) {
            wishlistRepository.deleteByUserAndCard(user, card);
        }
    }

    public boolean isCardInWishlist(User user, Card card) {
        return wishlistRepository.existsByUserAndCard(user, card);
    }

    public List<Card> getCardsInWishlistByUser(User loggedUser) {
        return wishlistRepository.findCardsByUser(loggedUser);
    }
}
