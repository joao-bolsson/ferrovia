package br.com.joao.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
@XStreamAlias("settings")
public class Settings {

    private final String server, database,
            user, password;

    /**
     * Creates an object to store database connection settings.
     *
     * @param server Database server name.
     * @param database Database name.
     * @param user User name.
     * @param password User password.
     */
    public Settings(String server, String database, String user, String password) {
        this.server = server;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    /**
     * @return The server.
     */
    public String getServer() {
        return server;
    }

    /**
     * @return The database name.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @return User name.
     */
    public String getUser() {
        return user;
    }

    /**
     * @return User password.
     */
    public String getPassword() {
        return password;
    }

}
