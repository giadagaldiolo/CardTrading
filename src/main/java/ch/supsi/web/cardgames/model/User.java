package ch.supsi.web.cardgames.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String username;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private String password;

    @ManyToMany
    @JoinTable(
            name = "cart_cards",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cart = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(
//            name = "user_wishlist",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "card_id")
//    )
//    private List<Card> wishlist = new ArrayList<>();


    public User(String username, String firstName, String lastName, UserRole role, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
    }
}
