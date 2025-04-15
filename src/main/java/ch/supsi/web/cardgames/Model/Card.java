package ch.supsi.web.cardgames.Model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Card {
    private String cname;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String author;
    private Condition condition;


}
