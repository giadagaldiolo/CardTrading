package ch.supsi.web.cardgames.Service;

import ch.supsi.web.cardgames.Model.Card;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    private final List<Card> cards = new ArrayList<>();

    private Long nextId = 1L;

    public List<Card> getCards() {
        System.out.println("Fetching cards: " + cards);
        return cards;
    }

    public Card getCardById(Long id) {
        return cards.stream()
                .filter(card -> card.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void saveCard(Card card) {
        System.out.println("Saving card: " + card.getCname());
        card.setId(nextId++);
        cards.add(card);
    }

    public void updateCard(Long id, Card updatedCard) {
        Card card = getCardById(id);
        if (card != null) {
            // Usa i dati del form per aggiornare la carta
            card.setCname(updatedCard.getCname());
            card.setDescription(updatedCard.getDescription());
            card.setDate(updatedCard.getDate());
            card.setAuthor(updatedCard.getAuthor());
            card.setCardType(updatedCard.getCardType());
            card.setCondition(updatedCard.getCondition());
            card.setImage(updatedCard.getImage());

        }
    }


    public void deleteById(Long id) {
        Card card = getCardById(id);
        cards.remove(card);

    }
}
