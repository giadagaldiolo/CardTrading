package ch.supsi.web.cardgames.Controller;

import org.springframework.ui.Model;
import ch.supsi.web.cardgames.Model.Card;
import ch.supsi.web.cardgames.Model.CardType;
import ch.supsi.web.cardgames.Service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class ThymeLeafCardController {

    private final CardService cardService;

    public ThymeLeafCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/not-found")
    public String getCardNotFoundPage(){
        return "cardNotFound";
    }


    @GetMapping("/card/new")
    public String newCardForm(Model model) {
        model.addAttribute("card", new Card());
        return "cardSaleForm";
    }

    @PostMapping("/card/new")
    public String createCard(@ModelAttribute Card card,
                             @RequestParam("image") MultipartFile imageFile) throws IOException, ParseException {
        if (!imageFile.isEmpty()) {
            card.setImage(imageFile.getBytes());
        }
        cardService.saveCard(card);
        return "redirect:/";
    }

    @GetMapping("{cardId}")
    public String cardDetail(@PathVariable int cardId, Model model) {
        Card card = cardService.getCardById(cardId);
        if(card == null){
            return "redirect:/card/not-found";
        }
        model.addAttribute("card", card);
        return "details";
    }

    @GetMapping("/{cardId}/edit")
    public String editCardForm(@PathVariable int cardId, Model model) {
        Card card = cardService.getCardById(cardId);
        if(card == null){
            return "redirect:/cards/not-found";
        }
        model.addAttribute("card", card);
        return "cardEditForm";
    }

    @PostMapping("/{cardId}/edit")
    public String updateCard(@PathVariable int cardId,@ModelAttribute Card card,
                             @RequestParam("image") MultipartFile imageFile) throws IOException, ParseException {
        Card existingCard = cardService.getCardById(cardId);
        if (existingCard == null) {
            return "redirect:/card/not-found";
        }
        if (!imageFile.isEmpty()) {
            card.setImage(imageFile.getBytes());
        } else {
            card.setImage(existingCard.getImage());
        }
        this.cardService.updateCard(card, existingCard);
        return "redirect:/card/" + cardId;
    }

    @GetMapping("/{cardId}/delete")
    public String deleteCard(@PathVariable int cardId) {
        Card card = cardService.getCardById(cardId);
        if(card == null){
            return "redirect:/card/not-found";
        }
        cardService.deleteCard(card);
        return "redirect:/";
    }

}
