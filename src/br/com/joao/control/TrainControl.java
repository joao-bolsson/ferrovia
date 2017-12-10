package br.com.joao.control;

import br.com.joao.model.ConnectionJ;
import br.com.joao.model.Train;
import br.com.joao.model.TrainTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class TrainControl {

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
}
