package pl.lodz.p.it.spjava.e12.appstore.security;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@Named
@RequestScoped
public class LoginController implements Serializable {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    @Inject
    private HashGenerator hashGenerator;

    /*
     * Dokonuje programowo uwierzytelnienia na podstawie loginu i hasła. Dane
     * pochodzą z formularza uwierzytelniania. Dzięki samodzielnemu wywoływaniu
     * login() można przechwycić wyjątek który jest rzucany w przypadku
     * niepoprawnego uwierzytelnienia. Można to wykorzystać np. do blokowania
     * konta po pewnej liczbie nieudanych prób.
     */
    public String login() {
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(getRequest(), getResponse(), withParams().credential(credential));
        if (status.equals(AuthenticationStatus.SEND_CONTINUE)) {
            facesContext.responseComplete();
            return "main";
        } else if (status.equals(AuthenticationStatus.SEND_FAILURE)) {
            return "log_error";
        }
        return "main";
    }

    private HttpServletResponse getResponse() {
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashGenerator.generateHash(password);
    }
}
