package br.com.joao.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class ConnectionJ {

    private static String status = "Não conectou...";

    /**
     * @return A new connection to the database.
     */
    public static Connection getConnection() {
        try {
            Settings settings = SettingsIO.readSettingsFile(new File("/home/joao/CC/FBD/Ferrovia/src/settings.xml"));

            Connection connection;

            try {
                String driverName = "com.mysql.jdbc.Driver";
                Class.forName(driverName);
                String serverName = settings.getServer();
                String mydatabase = settings.getDatabase();
                String url = "jdbc:mysql://" + serverName + "/" + mydatabase + "?useSSL=false";
                String username = settings.getUser();
                String password = settings.getPassword();

                connection = DriverManager.getConnection(url, username, password);

                if (connection != null) {
                    status = ("STATUS--->Conectado com sucesso!");
                } else {
                    status = ("STATUS--->Não foi possivel realizar conexão");
                }

                return connection;
            } catch (final ClassNotFoundException e) {
                System.out.println("O driver expecificado nao foi encontrado." + e);
                return null;
            } catch (final SQLException e) {
                System.out.println("Nao foi possivel conectar ao Banco de Dados. " + e);
                return null;
            }
        } catch (final FileNotFoundException ex) {
            System.out.println(ex);
            return null;
        }
    }

    /**
     * @return Connection status.
     */
    public static String getStatus() {
        return status;
    }

    /**
     * @return If the connection was closed - true, else - false.
     */
    public static boolean close() {
        try {
            getConnection().close();
            return true;
        } catch (final SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public static Connection restart() {
        close();
        return getConnection();
    }

}
