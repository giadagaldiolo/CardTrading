package ch.supsi.web.cardgames.Service;

import ch.supsi.web.cardgames.Model.Card;
import lombok.Setter;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class MainService {
    public String getAndPopulateHTMLHomePage(List<Card> cards){
        Resource resource = new ClassPathResource("templates/index.html");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        String file = null;
        try {
            file = resource.getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cards);
        String cardLis = cards.stream().map(card -> "<li>"+card.getCname()+"</li>").collect(Collectors.joining());
        file = file.replace("$$cards", cardLis);
        return file;
    }

    public Resource getNewCardFormPage(){
        return new ClassPathResource("templates/cardSaleForm.html");
    }
}
