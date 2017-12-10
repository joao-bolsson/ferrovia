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
public class Train {

    private int id;

    private Engineer engineer;

    private Type type;

    private static final Map<Integer, Train> TRAINS = new HashMap<>();

    /**
     * Creates a new Train.
     *
     * @param engineer Train Engineer.
     * @param type Train type.
     */
    public Train(final Engineer engineer, final Type type) {
        this.id = 0;
        this.engineer = engineer;
        this.type = type;
    }

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
     * Gets the train by given id.
     *
     * @param id Train identifier.
     * @return The train.
     */
    public static Train getTrain(final int id) {
        if (TRAINS.isEmpty()) {
            getTrains();
        }
        return TRAINS.get(id);
    }

    /**
     * @return All engineers in database.
     */
    public static List<Train> getTrains() {
        List<Train> list = new ArrayList<>();
        TRAINS.clear();
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT id, engenheiro, tipo FROM trem;");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idEng = rs.getInt("engenheiro");
                    int idType = rs.getInt("tipo");

                    Engineer engineer = Engineer.getEngineer(idEng);
                    Train.Type type = Train.Type.getType(idType);

                    Train train = new Train(id, engineer, type);
                    list.add(train);
                    TRAINS.put(train.id, train);
                }
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("#");
        builder.append(id).append(" - ").append(type.name);

        return builder.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.engineer);
        hash = 59 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Train) {
            final Train other = (Train) obj;

            return other.id == id && other.engineer.equals(engineer) && other.type.equals(type);
        }
        return false;
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

        /**
         * @return Type id.
         */
        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 61 * hash + this.id;
            hash = 61 * hash + Objects.hashCode(this.name);
            return hash;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof Type) {
                final Type other = (Type) obj;
                return other.id == id && other.name.equals(name);
            }
            return false;
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
