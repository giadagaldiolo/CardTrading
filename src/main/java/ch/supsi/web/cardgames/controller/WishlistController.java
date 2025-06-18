package ch.supsi.web.cardgames.controller;

import ch.qos.logback.core.model.Model;
import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.service.CardService;
import ch.supsi.web.cardgames.service.UserService;
import ch.supsi.web.cardgames.service.WishlistService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final CardService cardService;

    public WishlistController(WishlistService wishlistService, UserService userService, CardService cardService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
        this.cardService = cardService;
    }

    @PostMapping("/add/{cardId}")
    public String addToWishlist(@PathVariable int cardId) {
        User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ch.supsi.web.cardgames.model.User loggedUser = this.userService.findUserByUsername(user.getUsername());

        Card card = cardService.getCardById(cardId);

        wishlistService.addCardToWishlist(loggedUser, card);
        return "redirect:/card/" + cardId;
    }

    @PostMapping("/delete/{cardId}")
    public String deleteFromWishlist(@PathVariable int cardId) {
        User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ch.supsi.web.cardgames.model.User loggedUser = this.userService.findUserByUsername(user.getUsername());

        Card card = cardService.getCardById(cardId);

        wishlistService.deleteCardFromWishlist(loggedUser, card);
        return "redirect:/card/" + cardId;
    }




}
