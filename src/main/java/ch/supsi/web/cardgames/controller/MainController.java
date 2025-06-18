package ch.supsi.web.cardgames.controller;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.User;
import ch.supsi.web.cardgames.service.CardService;
import ch.supsi.web.cardgames.service.UserService;
import ch.supsi.web.cardgames.service.WishlistService;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class MainController {

    private final CardService cardService;
    private final UserService userService;
    private final WishlistService wishlistService;

    public MainController(CardService cardService, UserService userService, WishlistService wishlistService) {
        this.cardService = cardService;
        this.userService = userService;
        this.wishlistService = wishlistService;
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

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) throws BadRequestException {
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/news")
    public String getMobileNewsPage() {
        return "mobile-news";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/wishlist")
    public String getWishlist(Model model) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ch.supsi.web.cardgames.model.User loggedUser = userService.findUserByUsername(user.getUsername());

        List<Card> wishlistCards = wishlistService.getCardsInWishlistByUser(loggedUser);
        model.addAttribute("wishlistCards", wishlistCards);

        return "wishlist";
    }

    @GetMapping("/cart")
    public String cartPage(Model model) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ch.supsi.web.cardgames.model.User loggedUser = userService.findUserByUsername(user.getUsername());

        List<Card> cards = userService.getCart(loggedUser);

        BigDecimal totalPrice = cards.stream()
                .map(Card::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("cartCards", cards);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }
}
