package br.com.joao.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class Engineer {

    private final int id;

    private final String name;

    private static final Map<Integer, Engineer> ENGINEERS = new HashMap<>();

    /**
     * Default construct.
     *
     * @param id Engineer identifier.
     * @param name Engineer name.
     */
    public Engineer(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return Engineer id.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Engineer name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Engineer) {
            final Engineer other = (Engineer) obj;
            return other.id == id && other.name.equals(name);
        }
        return false;
    }

    /**
     * Gets the engineer by id.
     *
     * @param id Engineer identifier.
     * @return If exists an engineer with id - the engineer, else - null.
     */
    public static Engineer getEngineer(final int id) {
        if (ENGINEERS.isEmpty()) {
            getEngineers();
        }
        return ENGINEERS.get(id);
    }

    /**
     * @return All engineers in database.
     */
    public static List<Engineer> getEngineers() {
        List<Engineer> list = new ArrayList<>();
        ENGINEERS.clear();
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT id, nome FROM engenheiro;");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("nome");

                    Engineer engineer = new Engineer(id, name);
                    list.add(engineer);
                    ENGINEERS.put(engineer.id, engineer);
                }
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

}
