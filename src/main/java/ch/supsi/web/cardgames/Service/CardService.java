package ch.supsi.web.cardgames.Service;

import ch.supsi.web.cardgames.Model.Card;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    private final List<Card> cards = new ArrayList<>();

    public void saveCard(Card card) {
        System.out.println("Saving card: " + card.getCname());
        cards.add(card);
    }

    public List<Card> getCards() {
        System.out.println("Fetching cards: " + cards);
        return cards;
    }
}
