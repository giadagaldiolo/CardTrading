package ch.supsi.web.cardgames.service;


import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.User;
import ch.supsi.web.cardgames.model.UserRole;
import ch.supsi.web.cardgames.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder BCPasswordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws BadRequestException {
        checkUserValidity(user);
        // Set the role to USER by default
        if(user.getRole() == null)
            user.setRole(UserRole.USER);
        String encodedPw = BCPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPw);
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void checkUserValidity(User user) throws BadRequestException {
        if (user == null) {
            throw new BadRequestException("User cannot be null");
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new BadRequestException("First name cannot be empty");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new BadRequestException("Last name cannot be empty");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new BadRequestException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new BadRequestException("Password cannot be empty");
        }
        if (this.findUserByUsername(user.getUsername()) != null) {
            throw new BadRequestException("Username already exists");
        }
    }

    public void addToCart(User user, Card card) {
        if (!isCardInCart(user, card)) {
            user.getCart().add(card);
        }
        userRepository.save(user);
    }

    public void removeFromCart(User user, Card card) {
        if (isCardInCart(user, card)) {
            user.getCart().remove(card);
        }
        userRepository.save(user);
    }

    public List<Card> getCart(User user) {
        return user.getCart();
    }

    public boolean isCardInCart(User user, Card card) {
        return user.getCart().contains(card);
    }

}
