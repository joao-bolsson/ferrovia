package br.com.joao.control;

import br.com.joao.model.ConnectionJ;
import br.com.joao.model.Engineer;
import br.com.joao.model.Train;
import br.com.joao.model.TrainTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class TrainControl {

    /**
     * Update a train
     *
     * @param train Train to update.
     */
    public static void update(final Train train) {
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                StringBuilder builder = new StringBuilder("UPDATE trem SET engenheiro=");
                builder.append(train.getEngineer().getId()).append(", tipo=").
                        append(train.getType().getId()).append(" WHERE id=").append(train.getId());

                stmt.executeUpdate(builder.toString());
                TrainTableModel.getInstance().fireTableDataChanged();
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Remove a train.
     *
     * @param train Train to remove.
     */
    public static void remove(final Train train) {
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM trem WHERE id = " + train.getId());
                TrainTableModel.getInstance().remove(train);
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Add a train.
     *
     * @param train Train to add.
     */
    public static void add(final Train train) {
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                StringBuilder builder = new StringBuilder("INSERT INTO trem VALUES(NULL, ");
                builder.append(train.getEngineer().getId()).append(", ").
                        append(train.getType().getId()).append(");");

                stmt.executeUpdate(builder.toString());

                ResultSet rs = stmt.executeQuery("SELECT MAX(id) as id FROM trem;");
                if (rs != null && rs.next()) {
                    int lastId = rs.getInt("id");

                    train.setId(lastId);
                    TrainTableModel.getInstance().add(train);
                }
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void search(final String search) {
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                String query = "SELECT id, engenheiro, tipo FROM trem;";
                if (!search.isEmpty()) {
                    query = "SELECT trem.id, trem.engenheiro, trem.tipo FROM trem, engenheiro, tipo_trem "
                            + "WHERE trem.engenheiro = engenheiro.id AND trem.tipo = tipo_trem.id AND "
                            + "(engenheiro.nome LIKE '%" + search + "%' OR tipo_trem.nome LIKE '%" + search + "%');";
                }
                ResultSet rs = stmt.executeQuery(query);

                List<Train> lines = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idEng = rs.getInt("engenheiro");
                    int idType = rs.getInt("tipo");

                    Engineer engineer = Engineer.getEngineer(idEng);
                    Train.Type type = Train.Type.getType(idType);

                    Train train = new Train(id, engineer, type);
                    lines.add(train);
                }

                TrainTableModel.getInstance().addAll(lines);
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }
}
