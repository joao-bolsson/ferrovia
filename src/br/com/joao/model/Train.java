package br.com.joao.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class Train {

    private int id;

    private Engineer engineer;

    private Type type;

    /**
     * Creates a train.
     *
     * @param id Train identifier.
     * @param engineer Train Engineer.
     * @param type Train type.
     */
    public Train(final int id, final Engineer engineer, final Type type) {
        this.id = id;
        this.engineer = engineer;
        this.type = type;
    }

    /**
     * @return Train id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets an id.
     *
     * @param id Identifier to set.
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * @return Train engineer.
     */
    public Engineer getEngineer() {
        return engineer;
    }

    /**
     * Sets an engineer.
     *
     * @param engineer Engineer to set.
     */
    public void setEngineer(final Engineer engineer) {
        this.engineer = engineer;
    }

    /**
     * @return Train type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets a type.
     *
     * @param type Type to set.
     */
    public void setType(final Type type) {
        this.type = type;
    }

    /**
     * Class that represents the train type.
     */
    public static class Type {

        private final int id;
        private final String name;

        private static final Map<Integer, Type> TYPES = new HashMap<>();

        /**
         * Train type.
         *
         * @param id Type id.
         * @param name Type name.
         */
        public Type(final int id, final String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        /**
         * Gets the train type by id.
         *
         * @param id Train type identifier.
         * @return If train type exists by id - the type, else - null.
         */
        public static Type getType(final int id) {
            if (TYPES.isEmpty()) {
                getTypes();
            }
            return TYPES.get(id);
        }

        /**
         * @return Return all the train types in DB.
         */
        public static List<Type> getTypes() {
            TYPES.clear();
            List<Type> list = new ArrayList<>();

            try {
                Connection conn = ConnectionJ.getConnection();
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT id, nome FROM tipo_trem;");
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("nome");

                        Type type = new Type(id, name);
                        TYPES.put(type.id, type);
                        list.add(type);
                    }
                }
            } catch (final SQLException ex) {
                System.out.println(ex);
            }

            return list;
        }

    }

}
