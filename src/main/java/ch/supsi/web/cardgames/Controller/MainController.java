package ch.supsi.web.cardgames.Controller;

import ch.supsi.web.cardgames.Model.Card;
import ch.supsi.web.cardgames.Service.CardService;
import ch.supsi.web.cardgames.Service.MainService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class MainController {

    CardService cardService = new CardService();
    MainService mainService = new MainService();

    @GetMapping("/")
    public ResponseEntity<String> getIndexPage() throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(this.mainService.getAndPopulateHTMLHomePage(cardService.getCards()));
    }

    @GetMapping("card/new")
    public ResponseEntity<Resource> getCardSaleFormPage() {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(this.mainService.getNewCardFormPage());
    }

    @PostMapping("card/new")
    public String saleNewCard(Card card) {
        this.cardService.saveCard(card);
        return "redirect:/";
    }
}
