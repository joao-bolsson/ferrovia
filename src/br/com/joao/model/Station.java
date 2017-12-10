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
public class Station {

    private int id;

    private String name, address;

    private Type type;

    private static final Map<Integer, Station> STATIONS = new HashMap<>();

    /**
     * Creates a station.
     *
     * @param id Station identifies.
     * @param name Station name.
     * @param address Station address.
     * @param type Station type.
     */
    public Station(final int id, final String name, final String address, final Type type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    /**
     * @return Station id.
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
     * @return Station name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a name.
     *
     * @param name Station name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return Station address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets an address.
     *
     * @param address Address to set.
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * @return Station type.
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.address);
        hash = 71 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Station) {
            final Station other = (Station) obj;

            return other.id == id && other.address.equals(address) && other.name.equals(name)
                    && other.type.equals(type);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets the station by given id.
     *
     * @param id Station identifier.
     * @return The station.
     */
    public static Station getStation(final int id) {
        if (STATIONS.isEmpty()) {
            getStations();
        }
        return STATIONS.get(id);
    }

    /**
     * @return All Stations in database.
     */
    public static List<Station> getStations() {
        List<Station> list = new ArrayList<>();
        STATIONS.clear();
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT id, nome, endereco, tipo FROM estacao;");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("nome");
                    String address = rs.getString("endereco");
                    int idType = rs.getInt("tipo");

                    Type type = Type.getType(idType);

                    Station station = new Station(id, name, address, type);
                    list.add(station);
                    STATIONS.put(station.id, station);
                }
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    /**
     * Class that represents the station type.
     */
    public static class Type {

        private final int id;
        private final String name;

        private static final Map<Integer, Type> TYPES = new HashMap<>();

        /**
         * Station type.
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
         * Gets the station type by id.
         *
         * @param id Station type identifier.
         * @return If station type exists by id - the type, else - null.
         */
        public static Type getType(final int id) {
            if (TYPES.isEmpty()) {
                getTypes();
            }
            return TYPES.get(id);
        }

        /**
         * @return Return all the station types in DB.
         */
        public static List<Type> getTypes() {
            TYPES.clear();
            List<Type> list = new ArrayList<>();

            try {
                Connection conn = ConnectionJ.getConnection();
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT id, nome FROM tipo_estacao;");
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
