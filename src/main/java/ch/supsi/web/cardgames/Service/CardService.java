package ch.supsi.web.cardgames.Service;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.CardType;
import ch.supsi.web.cardgames.model.CardCondition;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {

    private final List<Card> cards = new ArrayList<>();


    @Value("${upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        if(cards.isEmpty()) {
            try {
                cards.add(new Card(UUID.randomUUID().hashCode(), "Pikachu", "Pikachu description", new Date(), "Ash", CardCondition.MINT, CardType.POKEMON, Files.readAllBytes(Paths.get(uploadPath, "pikachu.jpg"))));
                cards.add(new Card(UUID.randomUUID().hashCode(), "Bulbasaur", "Bulbasaur description", new Date(), "Ash", CardCondition.NEAR_MINT, CardType.POKEMON, Files.readAllBytes(Paths.get(uploadPath, "Bulbasaur.png"))));
                cards.add(new Card(UUID.randomUUID().hashCode(), "Charmander", "Charmander description", new Date(), "Ash", CardCondition.MINT, CardType.POKEMON, Files.readAllBytes(Paths.get(uploadPath, "Charmander.jpg"))));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load initial card images", e);
            }
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCardById(int id) {
        return cards.stream()
                .filter(card -> card.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void saveCard(Card card) {
        cards.add(card);
    }

    public void updateCard(Card newCard,Card oldCard) {
        this.cards.stream()
                .filter(card -> card.getId() == oldCard.getId())
                .findFirst()
                .ifPresent(card -> {
                    card.setCname(newCard.getCname());
                    card.setDescription(newCard.getDescription());
                    card.setDate(newCard.getDate());
                    card.setAuthor(newCard.getAuthor());
                    card.setCondition(newCard.getCondition());
                    card.setCardType(newCard.getCardType());
                });
    }


    public void deleteCard(Card card) {
        this.cards.removeIf(c -> c.getId() == card.getId());
    }

    public String saveImage(MultipartFile file){
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadPath, filename);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "/images/card-images/"+ filename;
    }

}
