package ch.supsi.web.cardgames.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Entity @Getter @Setter @NoArgsConstructor @ToString
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String author;
    private String cname;

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Enumerated(EnumType.STRING)
    private CardCondition cardCondition;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;


    public Card(String author, String cname, String description, Date date,
                byte[] image, CardCondition cardCondition,
                CardType cardType, User ownerUser) {
        this.author = author;
        this.cname = cname;
        this.description = description;
        this.date = date;
        this.image = image;
        this.cardCondition = cardCondition;
        this.cardType = cardType;
        this.ownerUser = ownerUser;
    }

    public String getBase64Image() {
        if (image == null) {
            return "";
        }
        return "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(image);
    }

}
