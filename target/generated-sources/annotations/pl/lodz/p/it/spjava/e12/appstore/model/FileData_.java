package pl.lodz.p.it.spjava.e12.appstore.model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.lodz.p.it.spjava.e12.appstore.model.Application;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-01-15T17:03:44", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(FileData.class)
public class FileData_ { 

    public static volatile SingularAttribute<FileData, String> fileName;
    public static volatile SingularAttribute<FileData, Application> file;
    public static volatile SingularAttribute<FileData, Long> fileSize;
    public static volatile SingularAttribute<FileData, Long> id;
    public static volatile SingularAttribute<FileData, String> fileType;

}