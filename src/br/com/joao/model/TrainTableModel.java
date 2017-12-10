package br.com.joao.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class TrainTableModel extends AbstractTableModel {

    private final List<Train> lines;

    private final String[] columns = new String[]{"#ID", "Engenheiro", "Tipo"};

    /**
     * Column to show train id.
     */
    private static final int ID_COLUMN = 0;

    /**
     * Column to show train engineer.
     */
    private static final int ENGINEER_COLUMN = 1;

    /**
     * Column to show train type.
     */
    private static final int TYPE_COLUMN = 2;

    /**
     * Only used to gets the train object in a table line.
     */
    public static final int TRAIN_COLUMN = -1;

    private static TrainTableModel INSTANCE;

    /**
     * @return The single instance of this model.
     */
    public static TrainTableModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TrainTableModel();
        }
        return INSTANCE;
    }

    /**
     * Creates an empty table model.
     */
    public TrainTableModel() {
        lines = new ArrayList<>();

        init();
    }

    private void init() {
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
                    lines.add(train);
                }
            }
        } catch (final SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        Train train = lines.get(rowIndex);
        switch (columnIndex) {
            case ID_COLUMN:
                return train.getId();

            case ENGINEER_COLUMN:
                return train.getEngineer();

            case TYPE_COLUMN:
                return train.getType();

            case TRAIN_COLUMN:
                return train;

            default:
                throw new UnsupportedOperationException("Value not found.");
        }
    }

    @Override
    public String getColumnName(final int column) {
        return columns[column];
    }

    /**
     * Remove a train from this model.
     *
     * @param train Train to remove.
     */
    public void remove(final Train train) {
        lines.remove(train);
        fireTableDataChanged();
    }

    /**
     * Add a train in this model.
     *
     * @param train Train to add.
     */
    public void add(final Train train) {
        lines.add(train);
        fireTableDataChanged();
    }

}
