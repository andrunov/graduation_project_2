package ru.agorbunov.restaurant.to;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.agorbunov.restaurant.HasIdAndEmail;
import ru.agorbunov.restaurant.util.validation.NoHtml;

import java.io.Serializable;

/**
 * class for works with User entity in some web forms
 */
public class UserTo extends BaseTo implements HasIdAndEmail, Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 128)
    @NoHtml
    private String name;

    @Email
    @NotBlank
    @Size(max = 128)
    @NoHtml
    private String email;

    @NotBlank
    @Size(min = 5, max = 20, message = "error.password")
    private String password;

    public UserTo() {
    }

    public UserTo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
