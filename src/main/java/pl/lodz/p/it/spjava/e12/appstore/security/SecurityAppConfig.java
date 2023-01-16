package pl.lodz.p.it.spjava.e12.appstore.security;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

@FacesConfig
@ApplicationScoped
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/faces/login/log_in.xhtml",
                errorPage = "/faces/login/log_error.xhtml",
                useForwardToLogin = false
        ))
public class SecurityAppConfig {

}
