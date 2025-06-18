package ch.supsi.web.cardgames.controller;

import ch.supsi.web.cardgames.model.WishlistItem;
import ch.supsi.web.cardgames.service.WishlistService;
import org.springframework.security.core.userdetails.User;
import ch.supsi.web.cardgames.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@Controller
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;
    private final UserService userService;
    private final WishlistService wishlistService;

    public CardController(CardService cardService, UserService userService, WishlistService wishlistService) {
        this.cardService = cardService;
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("/not-found")
    public String getCardNotFoundPage(){
        return "cardNotFound";
    }


    @GetMapping("/new")
    public String newCardForm(Model model) {
        model.addAttribute("card", new Card());
        return "cardSaleForm";
    }

    @PostMapping("/new")
    public String createCard(@ModelAttribute Card card,
                             @RequestParam("imageFile") MultipartFile imageFile) throws IOException, ParseException {
        if (!imageFile.isEmpty()) {
            card.setImage(imageFile.getBytes());
        }
        User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ch.supsi.web.cardgames.model.User loggedUser = this.userService.findUserByUsername(user.getUsername());
        card.setOwnerUser(loggedUser);

        this.cardService.saveCard(card);
        return "redirect:/";
    }

    @GetMapping("/{cardId}")
    public String cardDetail(@PathVariable int cardId, Model model) {
        Card card = cardService.getCardById(cardId);
        if (card == null){
            return "redirect:/card/not-found";
        }
        model.addAttribute("card", card);

        boolean inWishlist = false;
        boolean inCart = false;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User user) {
            ch.supsi.web.cardgames.model.User loggedUser = this.userService.findUserByUsername(user.getUsername());
            inWishlist = wishlistService.isCardInWishlist(loggedUser, card);
            inCart = userService.isCardInCart(loggedUser, card);
        }

        model.addAttribute("inWishlist", inWishlist);
        model.addAttribute("inCart", inCart);
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
                             @RequestParam("imageFile") MultipartFile imageFile) throws IOException, ParseException {
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
        wishlistService.deleteCardFromAllWishlists(cardId);
        userService.removeCardFromAllCarts(cardId);
        cardService.deleteCard(card);
        return "redirect:/";
    }

}
