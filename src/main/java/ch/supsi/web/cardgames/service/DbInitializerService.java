package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.*;
import ch.supsi.web.cardgames.repository.CardRepository;
import ch.supsi.web.cardgames.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DbInitializerService {

    @Value("${upload.path}")
    private String uploadPath;

    private final CardService cardService;

    private final UserService userService;


    public DbInitializerService(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }


    @PostConstruct
    public void init() {
        List<User> users = null;
        try {
            users = initUsers();
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
        initCards(users);
    }

    private List<User> initUsers()throws BadRequestException {
        long count = userService.getAllUsers().size();
        List<User> users = new ArrayList<>();
        if (count == 0) {
            users.add(userService.createUser(new User("admin", "Admin", "Admin", UserRole.ADMIN, "admin")));
            users.add(userService.createUser(new User("testuser", "Test", "User", UserRole.USER, "testuser")));
            users.add(userService.createUser(new User("testuser2", "Test2", "User2", UserRole.USER, "testuser2")));
        } else {
        users = userService.getAllUsers();
        }
        return users;
    }

    private void initCards(List<User> users){
        long count = cardService.getNumberOfCards();
        if(count == 0) {
            try {
                cardService.saveCard(new Card("Ash", "Bulbasaur", "Bulbasaur description", new Date(), readBytesFromResource("/static/images/Bulbasaur.png"), CardCondition.MINT, CardType.POKEMON, users.get(1), new BigDecimal("10.00")));
                cardService.saveCard(new Card("Ash","Charmander", "Charmander description", new Date(), readBytesFromResource("/static/images/Charmander.jpg"), CardCondition.NEAR_MINT, CardType.POKEMON, users.get(1), new BigDecimal("9.00")));
                cardService.saveCard(new Card("Kaiba", "Blue-Eyes White Dragon", "Blue-Eyes White Dragon descriptiobn", new Date(), readBytesFromResource("/static/images/Blue-Eyes-Jet-Dragon.jpg"), CardCondition.MINT, CardType.YU_GI_OH, users.get(2), new BigDecimal("8.00")));
                cardService.saveCard(new Card("MtG Player", "Swords to Plowshares", "Swords to Plowshares description", new Date(), readBytesFromResource("/static/images/swords-to-plowshares.jpg"), CardCondition.NEAR_MINT, CardType.MAGIC, users.get(2), new BigDecimal("7.00")));
                cardService.saveCard(new Card("Ash", "Pikachu", "Pikachu description", new Date(), readBytesFromResource("/static/images/pikachu.jpg"), CardCondition.MINT, CardType.POKEMON, users.get(1), new BigDecimal("6.00")));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load initial card images", e);
            }
        }
    }

    private byte[] readBytesFromResource(String resourcePath) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            return is.readAllBytes();
        }
    }
}
