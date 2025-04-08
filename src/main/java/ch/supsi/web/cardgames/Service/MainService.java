package ch.supsi.web.cardgames.Service;

import ch.supsi.web.cardgames.Model.Card;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;

public class MainService {
    public String getAndPopulateHTMLHomePage(List<Card> cards){
        Resource resource = new ClassPathResource("templates/index.html");
        String file = null;
        try {
            file = resource.getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cards);
        String cardLis = cards.stream().map(card -> "<li>"+card.getName()+"</li>").collect(Collectors.joining());
        file = file.replace("$$cards", cardLis);
        return file;
    }

    public Resource getNewCardFormPage(){
        return new ClassPathResource("templates/cardSaleForm.html");
    }
}
