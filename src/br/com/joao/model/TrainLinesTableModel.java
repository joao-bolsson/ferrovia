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
 * @since 2017, 10 Dec.
 */
public class TrainLinesTableModel extends AbstractTableModel {

    private final List<TrainLine> lines;

    private final String[] columns = new String[]{"Trem", "Estaçao", "Hora Ida", "Hora Volta"};

    private static final int TRAIN_COLUMN = 0, STATION_COLUMN = 1, DEPARTURE_COLUMN = 2, RETURN_COLUMN = 3;

    public static final int TRAIN_LINE_COLUMN = -1;

    private static TrainLinesTableModel INSTANCE;

    /**
     * @return The single instance of this class.
     */
    public static TrainLinesTableModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TrainLinesTableModel();
        }
        return INSTANCE;
    }

    /**
     * Creates an empty table model.
     */
    public TrainLinesTableModel() {
        this.lines = new ArrayList<>();

        init();
    }

    private void init() {
        try {
            Connection conn = ConnectionJ.getConnection();
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT id_trem, id_estacao, hora_ida, hora_volta FROM trem_estacao;");

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
        TrainLine trainLine = lines.get(rowIndex);

        switch (columnIndex) {
            case TRAIN_COLUMN:
                return trainLine.getTrain();

            case STATION_COLUMN:
                return trainLine.getStation();

            case DEPARTURE_COLUMN:
                return trainLine.getDepartureTime();

            case RETURN_COLUMN:
                return trainLine.getReturnTime();

            case TRAIN_LINE_COLUMN:
                return trainLine;

            default:
                throw new UnsupportedOperationException("Value not found.");
        }
    }

    /**
     * Remove a train line from this model.
     *
     * @param line Train line to remove.
     */
    public void remove(final TrainLine line) {
        lines.remove(line);
        fireTableDataChanged();
    }

    /**
     * Add a train line in this model.
     *
     * @param line Train line to add.
     */
    public void add(final TrainLine line) {
        lines.add(line);
        fireTableDataChanged();
    }

}
