package pl.lodz.p.it.spjava.e12.appstore.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
 * Konfiguracja DB 
 */
@DataSourceDefinition(
        name = "java:app/jdbc/AppStoreJDBCJavaDB",
        className = "org.apache.derby.jdbc.ClientDataSource",
        serverName = "localhost",
        portNumber = 1527,
        databaseName = "AppStoreForYouDB",
        user = "AppStoreUser",
        password = "AppStorePassword")

@Stateless
public class JDBCConfig {

    @PersistenceContext(unitName = "AppStoreJavaDB_PU")
    private EntityManager em;

}
