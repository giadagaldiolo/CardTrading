package ch.supsi.web.cardgames.Controller;

import org.springframework.ui.Model;
import ch.supsi.web.cardgames.Model.Card;
import ch.supsi.web.cardgames.Model.CardType;
import ch.supsi.web.cardgames.Service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThymeLeafCardController {

    private final CardService cardService;

    public ThymeLeafCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/not-found")
    public String getCardNotFoundPage(){
        return "card-not-found";
    }


    @GetMapping("/card/new")
    public String newCardForm(Model model) {
        model.addAttribute("card", new Card());
        model.addAttribute("cardTypes", CardType.values());
        return "cardSaleForm";
    }

    @PostMapping("/card/new")
    public String createCard(Card card) {
        cardService.saveCard(card);
        return "redirect:/";
    }

    @GetMapping("/card/{id}")
    public String cardDetail(@PathVariable int id, Model model) {
        Card card = cardService.getCardById(id);
        if(card == null){
            return "redirect:/card/not-found";
        }
        model.addAttribute("card", card);
        return "details";
    }

    @GetMapping("/card/{id}/edit")
    public String editCardForm(@PathVariable int id, Model model) {
        Card card = cardService.getCardById(id);
        if(card == null){
            return "redirect:/cards/not-found";
        }
        model.addAttribute("card", card);
        return "cardEditForm";
    }

    @PostMapping("/card/{id}/edit")
    public String updateCard(@PathVariable int id, Card card) {
        Card existingCard = cardService.getCardById(id);
        if(existingCard == null){
            return "redirect:/card/not-found";
        }
        cardService.updateCard(card, existingCard);
        return "redirect:/card/" + id;
    }

    @GetMapping("/card/{id}/delete")
    public String deleteCard(@PathVariable int id) {
        Card card = cardService.getCardById(id);
        if(card == null){
            return "redirect:/card/not-found";
        }
        cardService.deleteCard(card);
        return "redirect:/";
    }

}
