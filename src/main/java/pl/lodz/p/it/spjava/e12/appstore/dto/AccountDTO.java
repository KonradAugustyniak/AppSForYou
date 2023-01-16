package pl.lodz.p.it.spjava.e12.appstore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountDTO {

    private Long userId;

    @NotEmpty
    @Size(min = 2, max = 32, message = "{constraint.string.length.notinrange}")
    @NotNull(message = "{constraint.notnull}")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 64, message = "{constraint.string.length.notinrange}")
    @NotNull(message = "{constraint.notnull}")
    private String surname;

    @NotEmpty
    @Email(message = "{constraint.string.incorrectemail}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    @NotNull(message = "{constraint.notnull}")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^[_a-zA-Z0-9-]*$", message = "{constraint.string.incorrectchar}")
    @Size(min = 3, max = 24, message = "{constraint.string.length.notinrange}")
    @NotNull(message = "{constraint.notnull}")
    private String login;

    @NotEmpty
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @NotNull(message = "{constraint.notnull}")
    private String password;

    @NotEmpty
    @NotNull
    private String accountType;

    public AccountDTO() {
    }

    public AccountDTO(Long userId, String name, String surname, String email, String login, String accountType) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getUserId() {
        return userId;
    }

}
