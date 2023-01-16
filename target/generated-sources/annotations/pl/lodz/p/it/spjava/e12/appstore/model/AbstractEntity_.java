package pl.lodz.p.it.spjava.e12.appstore.model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-01-15T17:03:44", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(AbstractEntity.class)
public abstract class AbstractEntity_ { 

    public static volatile SingularAttribute<AbstractEntity, Date> creationTimestamp;
    public static volatile SingularAttribute<AbstractEntity, Long> version;
    public static volatile SingularAttribute<AbstractEntity, Date> modificationTimestamp;

}