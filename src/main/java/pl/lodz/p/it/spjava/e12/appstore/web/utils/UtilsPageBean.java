package pl.lodz.p.it.spjava.e12.appstore.web.utils;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

@Named(value = "utilsPageBean")
@RequestScoped
public class UtilsPageBean implements Serializable {

    public UtilsPageBean() {
    }

    public void viewPrivacyPolicyDialog() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(false)
                .build();

        PrimeFaces.current().dialog().openDynamic("privacyPolicy", options, null);

    }

    public String viewPrivacyPolicy() {
        return "privacyPolicy";
    }

}
