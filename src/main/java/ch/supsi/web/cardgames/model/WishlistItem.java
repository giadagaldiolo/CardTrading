package ch.supsi.web.cardgames.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Card card;

    public WishlistItem(User user, Card card) {
        this.user = user;
        this.card = card;
    }
}
