package br.com.joao.control;

import br.com.joao.model.ConnectionJ;
import br.com.joao.model.Station;
import br.com.joao.model.Train;
import br.com.joao.model.TrainLine;
import br.com.joao.model.TrainLinesTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
}
