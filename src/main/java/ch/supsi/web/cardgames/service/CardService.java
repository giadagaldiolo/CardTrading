package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.CardType;
import ch.supsi.web.cardgames.model.CardCondition;
import ch.supsi.web.cardgames.repository.CardRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CardRepository cardRepository;


    public List<Card> getCards() {
        return cardRepository.findAll();
    }
    public Card getCardById(int id){
        return cardRepository.findById(id).orElse(null);
    }
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
    public void updateCard(Card newCard,Card oldCard){
        oldCard.setCname(newCard.getCname());
        oldCard.setDescription(newCard.getDescription());
        oldCard.setDate(newCard.getDate());
        oldCard.setAuthor(newCard.getAuthor());
        oldCard.setCardCondition(newCard.getCardCondition());
        oldCard.setCardType(newCard.getCardType());         ;
        oldCard.setImage(newCard.getImage());
        cardRepository.save(oldCard);
    }
    public void deleteCard(Card card){
        cardRepository.deleteById(card.getId());
    }


}
