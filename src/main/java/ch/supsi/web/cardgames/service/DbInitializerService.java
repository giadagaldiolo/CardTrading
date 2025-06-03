package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.CardCondition;
import ch.supsi.web.cardgames.model.CardType;
import ch.supsi.web.cardgames.model.User;
import ch.supsi.web.cardgames.repository.CardRepository;
import ch.supsi.web.cardgames.repository.UserRepository;
import jakarta.annotation.PostConstruct;
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

    private final CardRepository cardRepository;

    private final UserRepository userRepository;


    public DbInitializerService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Service.html
    //https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/postconstruct-and-predestroy-annotations.html
    @PostConstruct
    public void init() {
        List<User> users = initUsers();
        initCards(users);
    }

    private List<User> initUsers(){
        long count = userRepository.count();
        List<User> users = new ArrayList<>();
        if(count == 0) {
            // Initialize the database with some users
            users.add(userRepository.save(new User("admin", "Admin", "Admin")));
            users.add(userRepository.save(new User("testuser", "Test", "User")));
            users.add(userRepository.save(new User("testuser2", "Test2", "User2")));
        } else {
        users = userRepository.findAll(); // <-- Recupera utenti esistenti
        }
        return users;
    }

    private void initCards(List<User> users){
        long count = cardRepository.count();
        if(count == 0) {
            try {
                cardRepository.save(new Card("Ash", "Bulbasaur", "Bulbasaur description", new Date(), Files.readAllBytes(Paths.get(uploadPath, "Bulbasaur.png")), CardCondition.MINT, CardType.POKEMON, users.get(1)));
                cardRepository.save(new Card("Ash", "Pikachu", "Pikachu description", new Date(), Files.readAllBytes(Paths.get(uploadPath, "pikachu.jpg")), CardCondition.MINT, CardType.POKEMON, users.get(1)));
                cardRepository.save(new Card("Ash","Charmander", "Charmander description", new Date(), Files.readAllBytes(Paths.get(uploadPath, "Charmander.jpg")), CardCondition.NEAR_MINT, CardType.POKEMON, users.get(1)));
                cardRepository.save(new Card("Kaiba", "Blue-Eyes White Dragon", "Blue-Eyes White Dragon descriptiobn", new Date(), Files.readAllBytes(Paths.get(uploadPath, "Blue-Eyes-Jet-Dragon.jpg")), CardCondition.MINT, CardType.YU_GI_OH, users.get(2)));
                cardRepository.save(new Card("MtG Player", "Swords to Plowshares", "Swords to Plowshares description", new Date(), Files.readAllBytes(Paths.get(uploadPath, "swords-to-plowshares.jpg")), CardCondition.NEAR_MINT, CardType.MAGIC, users.get(2)));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load initial card images", e);
            }
        }
    }
}
