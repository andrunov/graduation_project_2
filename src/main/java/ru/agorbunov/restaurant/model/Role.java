package ru.agorbunov.restaurant.model;


import org.springframework.security.core.GrantedAuthority;

/**
 * Represents roles of user
 */
public enum Role implements GrantedAuthority {

    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
