package ch.supsi.web.cardgames.Controller;

import org.springframework.ui.Model;
import ch.supsi.web.cardgames.Model.Card;
import ch.supsi.web.cardgames.Model.CardType;
import ch.supsi.web.cardgames.Service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThymeLeafCardController {

    private final CardService cardService;

    public ThymeLeafCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cards", cardService.getCards());
        return "index";
    }

    @GetMapping("/card/new")
    public String newCardForm(Model model) {
        model.addAttribute("card", new Card());
        model.addAttribute("cardTypes", CardType.values());
        return "cardSaleForm";
    }

    @PostMapping("/card/new")
    public String createCard(@ModelAttribute Card card) {
        cardService.saveCard(card);
        return "redirect:/";
    }

    @GetMapping("/card/{id}")
    public String cardDetail(@PathVariable Long id, Model model) {
        model.addAttribute("card", cardService.getCardById(id));
        return "details";
    }

    @GetMapping("/card/{id}/edit")
    public String editCardForm(@PathVariable Long id, Model model) {
        model.addAttribute("card", cardService.getCardById(id));
        model.addAttribute("cardTypes", CardType.values());
        return "cardEditForm";
    }

    @PostMapping("/card/{id}/edit")
    public String updateCard(@PathVariable Long id, @ModelAttribute Card card) {
        cardService.updateCard(id, card);
        return "redirect:/";
    }

    @GetMapping("/card/{id}/delete")
    public String deleteCard(@PathVariable Long id) {
        cardService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "userRegistrationForm";
    }
}
