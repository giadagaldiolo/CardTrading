package ch.supsi.web.cardgames.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ;

    private String name;

    UserRole(String name) {
        this.name = name;
    }

}
