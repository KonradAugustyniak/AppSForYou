package pl.lodz.p.it.spjava.e12.appstore.model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.lodz.p.it.spjava.e12.appstore.model.Account;
import pl.lodz.p.it.spjava.e12.appstore.model.Application.OperatingSystem;
import pl.lodz.p.it.spjava.e12.appstore.model.FileData;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-01-15T17:03:44", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Application.class)
public class Application_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Application, String> applicationVersion;
    public static volatile SingularAttribute<Application, FileData> fileData;
    public static volatile SingularAttribute<Application, String> description;
    public static volatile SingularAttribute<Application, Account> applicationAuthor;
    public static volatile SingularAttribute<Application, Long> applicationId;
    public static volatile SingularAttribute<Application, OperatingSystem> operatingSystem;
    public static volatile SingularAttribute<Application, String> applicationName;
    public static volatile SingularAttribute<Application, byte[]> applicationFile;

}