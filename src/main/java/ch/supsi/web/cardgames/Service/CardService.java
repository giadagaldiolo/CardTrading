package ch.supsi.web.cardgames.Service;

import ch.supsi.web.cardgames.Model.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class CardService {

    List<Card> cards = new ArrayList<>();

    public void saveCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }
}
