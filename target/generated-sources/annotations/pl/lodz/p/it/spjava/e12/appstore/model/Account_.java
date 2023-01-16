package pl.lodz.p.it.spjava.e12.appstore.model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.lodz.p.it.spjava.e12.appstore.model.Account.AccountType;
import pl.lodz.p.it.spjava.e12.appstore.model.Application;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-01-15T17:03:44", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Account.class)
public class Account_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Account, String> password;
    public static volatile SingularAttribute<Account, String> surname;
    public static volatile SingularAttribute<Account, AccountType> accountType;
    public static volatile SingularAttribute<Account, String> name;
    public static volatile SingularAttribute<Account, String> login;
    public static volatile ListAttribute<Account, Application> uploadedApplicationList;
    public static volatile SingularAttribute<Account, Long> userId;
    public static volatile SingularAttribute<Account, String> email;

}