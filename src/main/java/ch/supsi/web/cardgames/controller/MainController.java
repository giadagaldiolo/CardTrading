package ch.supsi.web.cardgames.controller;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    private final CardService cardService;

    public MainController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cards", cardService.getCards());
        return "index";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "userRegistrationForm";
    }

    @GetMapping("/api/cards")
    @ResponseBody
    public List<Card> index() throws IOException {
        return cardService.getCards();
    }
}
