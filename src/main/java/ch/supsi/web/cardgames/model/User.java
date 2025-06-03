package ch.supsi.web.cardgames.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String username;

    private String firstName;

    private String lastname;


    public User(String username, String firstName, String lastname) {
        this.username = username;
        this.firstName = firstName;
        this.lastname = lastname;
    }
}
