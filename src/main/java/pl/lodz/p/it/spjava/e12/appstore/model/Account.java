package pl.lodz.p.it.spjava.e12.appstore.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@NamedQuery(name = "Account.findLoginByUserId", query = "SELECT login FROM Account login WHERE login.userId = :userId")
public class Account extends AbstractEntity implements Serializable {

    @Id
    @Column(name = "USER_ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotEmpty
    @Size(min = 2, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(length = 32, nullable = false)
    private String name;

    @NotEmpty
    @Size(min = 2, max = 64, message = "{constraint.string.length.notinrange}")
    @Column(length = 64, nullable = false)
    private String surname;

    @Email(message = "{constraint.string.incorrectemail}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    @Column(length = 64, unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Pattern(regexp = "^[_a-zA-Z0-9-]*$")
    @Size(min = 3, max = 24, message = "{constraint.string.length.notinrange}")
    @Column(length = 24, unique = true, nullable = false, updatable = false)
    private String login;

    @NotEmpty
    @Size(min = 8, max = 64, message = "{constraint.string.length.notinrange}")
    @Column(length = 64, nullable = false)
    private String password;

    @OneToMany(mappedBy = "applicationAuthor", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Application> uploadedApplicationList = new ArrayList<Application>();

    public enum AccountType {
        ADMIN,
        MOD,
        USER
    }

    @Column(name = "ACCOUNT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

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

    public List<Application> getUploadedApplicationList() {
        return uploadedApplicationList;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Account() {
    }

    public Account(Long userId) {
        this.userId = userId;
    }

    public Account(String name, String surname, String email, String login, String password, AccountType accountType) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.accountType = accountType;
    }

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    protected Object getBusinessKey() {
        return login;
    }

}
