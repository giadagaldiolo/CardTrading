package ch.supsi.web.cardgames.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Card {
    private int id;
    private String cname;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String author;
    private CardCondition cardCondition;
    private CardType cardType;
    private byte[] image;

    public String getBase64Image() {
        if (image == null) {
            return "";
        }
        return "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(image);
    }

}
