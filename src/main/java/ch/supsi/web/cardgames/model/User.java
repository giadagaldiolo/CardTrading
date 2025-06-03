package ch.supsi.web.cardgames.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String username;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String password;


    public User(String username, String firstName, String lastName, UserRole role, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
    }
}
