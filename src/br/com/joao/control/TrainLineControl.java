package br.com.joao.control;

import br.com.joao.model.ConnectionJ;
import br.com.joao.model.Station;
import br.com.joao.model.Train;
import br.com.joao.model.TrainLine;
import br.com.joao.model.TrainLinesTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 10 Dec.
 */
public class TrainLineControl {

    /**
     * Remove a line line.
     *
     * @param line Line to remove.
     */
    public static void remove(final TrainLine line) {
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                StringBuilder builder = new StringBuilder("DELETE FROM trem_estacao WHERE id_trem=");
                builder.append(line.getTrain().getId()).append(" AND id_estacao=").
                        append(line.getStation().getId()).append(";");
                stmt.executeUpdate(builder.toString());
                TrainLinesTableModel.getInstance().remove(line);
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Add a train line.
     *
     * @param line Line to add.
     */
    public static void add(final TrainLine line) {
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                StringBuilder builder = new StringBuilder("INSERT INTO trem_estacao VALUES(");
                builder.append(line.getTrain().getId()).append(", ").append(line.getStation().getId()).
                        append(", '").append(line.getDepartureTime()).append("', '").
                        append(line.getReturnTime()).append("');");
                stmt.executeUpdate(builder.toString());

                TrainLinesTableModel.getInstance().add(line);
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Update a line
     *
     * @param line Train to update.
     * @param oldTrain Old train of line.
     * @param oldStation Old station of line.
     */
    public static void update(final TrainLine line, final Train oldTrain, final Station oldStation) {
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                StringBuilder builder = new StringBuilder("UPDATE trem_estacao SET id_trem=");
                builder.append(line.getTrain().getId()).append(", id_estacao=").
                        append(line.getStation().getId()).append(", hora_ida='").
                        append(line.getDepartureTime()).append("', hora_volta='").
                        append(line.getReturnTime()).append("' WHERE id_trem=").append(oldTrain.getId()).
                        append(" AND id_estacao=").append(oldStation.getId()).append(";");

                stmt.executeUpdate(builder.toString());
                TrainLinesTableModel.getInstance().fireTableDataChanged();
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void search(final Train trainSearch, final Station stationSearch) {
        if (trainSearch == null || stationSearch == null) {
            System.out.println("Search for train line not accept null values.");
            return;
        }
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                String view_name = "viewTrain" + trainSearch.getId() + "Station" + stationSearch.getId();
                StringBuilder query = new StringBuilder("CREATE OR REPLACE VIEW ");
                query.append(view_name).append(" AS SELECT id_trem, id_estacao, hora_ida, hora_volta FROM trem_estacao");

                String whereTrain = "", whereStation = "";
                if (!trainSearch.equals(Train.ALL)) {
                    whereTrain = "id_trem = " + trainSearch.getId();
                }

                if (!stationSearch.equals(Station.ALL)) {
                    whereStation = "id_estacao = " + stationSearch.getId();
                }

                if (!whereTrain.isEmpty() || !whereStation.isEmpty()) {
                    query.append(" WHERE ").append(whereTrain);
                    if (!whereStation.isEmpty()) {
                        if (!whereTrain.isEmpty()) {
                            query.append(" AND ");
                        }
                        query.append(whereStation);
                    }
                }
                query.append(";");

                stmt.executeUpdate(query.toString());

                ResultSet rs = stmt.executeQuery("SELECT * FROM " + view_name);

                List<TrainLine> lines = new ArrayList<>();
                while (rs.next()) {
                    int idTrain = rs.getInt("id_trem");
                    int idStation = rs.getInt("id_estacao");
                    String departureTime = rs.getString("hora_ida");
                    String returnTime = rs.getString("hora_volta");

                    Train train = Train.getTrain(idTrain);
                    Station station = Station.getStation(idStation);

                    TrainLine trainLine = new TrainLine(station, train, departureTime, returnTime);

                    lines.add(trainLine);
                }

                TrainLinesTableModel.getInstance().addAll(lines);
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }

}
