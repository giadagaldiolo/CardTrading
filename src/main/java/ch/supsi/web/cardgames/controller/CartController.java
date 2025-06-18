package ch.supsi.web.cardgames.controller;

import ch.qos.logback.core.model.Model;
import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.service.CardService;
import ch.supsi.web.cardgames.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    private UserService userService;
    private CardService cardService;

    public CartController(UserService userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @PostMapping("/add/{cardId}")
    public String addToCart(@PathVariable int cardId) {
        User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ch.supsi.web.cardgames.model.User loggedUser = this.userService.findUserByUsername(user.getUsername());

        Card card = cardService.getCardById(cardId);
        userService.addToCart(loggedUser, card);

        return "redirect:/card/" + cardId;
    }

    @PostMapping("/delete/{cardId}")
    public String removeFromCart(@PathVariable int cardId) {
        User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ch.supsi.web.cardgames.model.User loggedUser = this.userService.findUserByUsername(user.getUsername());

        Card card = cardService.getCardById(cardId);
        userService.removeFromCart(loggedUser, card);

        return "redirect:/cart" + cardId;
    }

}
