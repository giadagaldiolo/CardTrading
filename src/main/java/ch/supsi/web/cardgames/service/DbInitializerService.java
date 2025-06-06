package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.*;
import ch.supsi.web.cardgames.repository.CardRepository;
import ch.supsi.web.cardgames.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Service.html
    //https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/postconstruct-and-predestroy-annotations.html
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
                cardService.saveCard(new Card("Ash", "Bulbasaur", "Bulbasaur description", new Date(), Files.readAllBytes(Paths.get(uploadPath, "Bulbasaur.png")), CardCondition.MINT, CardType.POKEMON, users.get(1)));
                cardService.saveCard(new Card("Ash","Charmander", "Charmander description", new Date(), Files.readAllBytes(Paths.get(uploadPath, "Charmander.jpg")), CardCondition.NEAR_MINT, CardType.POKEMON, users.get(1)));
                cardService.saveCard(new Card("Kaiba", "Blue-Eyes White Dragon", "Blue-Eyes White Dragon descriptiobn", new Date(), Files.readAllBytes(Paths.get(uploadPath, "Blue-Eyes-Jet-Dragon.jpg")), CardCondition.MINT, CardType.YU_GI_OH, users.get(2)));
                cardService.saveCard(new Card("MtG Player", "Swords to Plowshares", "Swords to Plowshares description", new Date(), Files.readAllBytes(Paths.get(uploadPath, "swords-to-plowshares.jpg")), CardCondition.NEAR_MINT, CardType.MAGIC, users.get(2)));
                cardService.saveCard(new Card("Ash", "Pikachu", "Pikachu description", new Date(), Files.readAllBytes(Paths.get(uploadPath, "pikachu.jpg")), CardCondition.MINT, CardType.POKEMON, users.get(1)));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load initial card images", e);
            }
        }
    }
}
