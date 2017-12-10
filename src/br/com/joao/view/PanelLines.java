package br.com.joao.view;

import br.com.joao.control.TrainLineControl;
import br.com.joao.model.Station;
import br.com.joao.model.Train;
import br.com.joao.model.TrainLine;
import br.com.joao.model.TrainLinesTableModel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 08 Dec.
 */
public class PanelLines extends JPanel {

    private final JButton btnSearch, btnAdd, btnRemove, btnClear;
    private final JComboBox<Station> comboStation, comboStationSearch;
    private final JComboBox<Train> comboTrain, comboTrainSearch;
    private final JLabel lblDepartureTime, lblReturnTime, lblStation, lblStationSearch, lblTrain, lblTrainSearch;
    private final JPanel panelForm, panelSearch, panelTable;
    private final JScrollPane scrollTable;
    private final JTable table;
    private final JFormattedTextField txtDepartureTime, txtReturnTime;

    private static final Dimension MIN_SIZE = new Dimension(570, 489);

    private static final List<Station> STATIONS = Station.getStations();

    private int rowSelected;

    private static final String UPDATE = "Atualizar";

    /**
     * Creates a panel to show lines.
     */
    public PanelLines() {
        super(new GridBagLayout());

        panelForm = new JPanel(new GridBagLayout());
        lblTrain = new JLabel("Trem");
        comboTrain = new JComboBox<>();
        lblStation = new JLabel("Estação");
        comboStation = new JComboBox<>();
        lblDepartureTime = new JLabel("Horário de Ida");
        txtDepartureTime = new JFormattedTextField();
        lblReturnTime = new JLabel("Horário de Volta");
        txtReturnTime = new JFormattedTextField();
        btnAdd = new JButton("Adicionar");
        btnRemove = new JButton("Remover");
        btnClear = new JButton("Limpar");
        panelTable = new JPanel(new BorderLayout());
        scrollTable = new JScrollPane();
        table = new JTable(TrainLinesTableModel.getInstance());
        panelSearch = new JPanel(new GridBagLayout());
        lblTrainSearch = new JLabel("Trem");
        comboTrainSearch = new JComboBox<>();
        lblStationSearch = new JLabel("Estação");
        comboStationSearch = new JComboBox<>();
        btnSearch = new JButton("Pesquisar");

        fillFields();
        addListeners();
        init();
    }

    private void fillStation(final Train train) {
        comboStation.removeAllItems();
        boolean express = train.getType().getId() == 1;
        for (Station station : STATIONS) {
            if (express) {
                // express trains stop only in express stations
                if (station.getType().getId() == 1) {
                    comboStation.addItem(station);
                }
            } else {
                // local trains stop in all stations
                comboStation.addItem(station);
            }
        }
    }

    private void fillStationSearch(final Train train) {
        comboStationSearch.removeAllItems();
        boolean express = train.getType().getId() == 1;
        for (Station station : STATIONS) {
            if (express) {
                // express trains stop only in express stations
                if (station.getType().getId() == 1) {
                    comboStationSearch.addItem(station);
                }
            } else {
                // local trains stop in all stations
                comboStationSearch.addItem(station);
            }
        }
    }

    private void clear() {
        comboTrain.setSelectedIndex(0);
        comboStation.setSelectedIndex(0);
        txtDepartureTime.setText("");
        txtReturnTime.setText("");
        btnClear.setEnabled(false);
        btnAdd.setText("Adicionar");
    }

    private void addListeners() {
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                clear();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Object valueAt = table.getValueAt(rowSelected, TrainLinesTableModel.TRAIN_LINE_COLUMN);
                if (valueAt instanceof TrainLine) {
                    TrainLine line = (TrainLine) valueAt;
                    TrainLineControl.remove(line);
                    table.repaint();
                    clear();
                }
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Train train = (Train) comboTrain.getSelectedItem();
                Station station = (Station) comboStation.getSelectedItem();
                String departureTime = txtDepartureTime.getText();
                String returnTime = txtReturnTime.getText();
                if (!departureTime.isEmpty() && !returnTime.isEmpty()) {
                    if (btnAdd.getText().equals(UPDATE)) {
                        // atualizar
                        Object valueAt = TrainLinesTableModel.getInstance().getValueAt(rowSelected,
                                TrainLinesTableModel.TRAIN_LINE_COLUMN);
                        if (valueAt instanceof TrainLine) {
                            TrainLine line = (TrainLine) valueAt;

                            Train oldTrain = line.getTrain();
                            Station oldStation = line.getStation();

                            line.setTrain(train);
                            line.setStation(station);
                            line.setDepartureTime(departureTime);
                            line.setReturnTime(returnTime);

                            TrainLineControl.update(line, oldTrain, oldStation);
                        }
                    } else {
                        // adicionar
                        TrainLine trainLine = new TrainLine(station, train, departureTime, returnTime);
                        TrainLineControl.add(trainLine);
                    }

                    table.repaint();
                    clear();
                }
            }
        });

        comboTrain.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                Object item = e.getItem();
                if (item instanceof Train) {
                    Train train = (Train) item;
                    fillStation(train);
                }
            }
        });

        comboTrainSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                Object item = e.getItem();
                if (item instanceof Train) {
                    Train train = (Train) item;

                    fillStationSearch(train);
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) { // evita ArrayIndexOutOfBounds
                    rowSelected = selectedRow;
                }
                prepareToEdit(rowSelected);
            }
        });
    }

    private void prepareToEdit(final int row) {
        Object valueAt = table.getValueAt(row, TrainLinesTableModel.TRAIN_LINE_COLUMN);
        if (valueAt instanceof TrainLine) {
            TrainLine line = (TrainLine) valueAt;
            comboTrain.setSelectedItem(line.getTrain());
            comboStation.setSelectedItem(line.getStation());
            txtDepartureTime.setText(line.getDepartureTime());
            txtReturnTime.setText(line.getReturnTime());

            btnClear.setEnabled(true);
            btnAdd.setText(UPDATE);
        }
    }

    private void fillFields() {
        List<Train> trains = Train.getTrains();
        for (Train train : trains) {
            comboTrain.addItem(train);
            comboTrainSearch.addItem(train);
        }

        fillStation(trains.get(0));
        fillStationSearch(trains.get(0));
    }

    private void init() {
        setMinimumSize(MIN_SIZE);
        panelForm.setBorder(BorderFactory.createEtchedBorder());

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(14, 14, 0, 0);
        panelForm.add(lblTrain, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 14, 0, 0);
        panelForm.add(comboTrain, cons);

        cons = new GridBagConstraints();
        cons.gridx = 5;
        cons.gridy = 0;
        cons.gridwidth = 5;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(14, 14, 0, 0);
        panelForm.add(lblStation, cons);

        cons = new GridBagConstraints();
        cons.gridx = 5;
        cons.gridy = 1;
        cons.gridwidth = 13;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 12, 0, 14);
        panelForm.add(comboStation, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 2;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(12, 14, 0, 0);
        panelForm.add(lblDepartureTime, cons);

        txtDepartureTime.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(
                new SimpleDateFormat("HH:mm"))));
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.gridwidth = 3;
        cons.ipadx = 225;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 14, 0, 0);
        panelForm.add(txtDepartureTime, cons);

        cons = new GridBagConstraints();
        cons.gridx = 5;
        cons.gridy = 2;
        cons.gridwidth = 6;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(12, 12, 0, 0);
        panelForm.add(lblReturnTime, cons);

        txtReturnTime.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(
                new SimpleDateFormat("HH:mm"))));
        cons = new GridBagConstraints();
        cons.gridx = 5;
        cons.gridy = 3;
        cons.gridwidth = 13;
        cons.ipadx = 259;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 12, 0, 14);
        panelForm.add(txtReturnTime, cons);

        cons = new GridBagConstraints();
        cons.gridx = 17;
        cons.gridy = 4;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 6, 14, 14);
        panelForm.add(btnAdd, cons);

        cons = new GridBagConstraints();
        cons.gridx = 10;
        cons.gridy = 4;
        cons.gridwidth = 7;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 4, 14, 0);
        panelForm.add(btnRemove, cons);

        cons = new GridBagConstraints();
        cons.gridx = 2;
        cons.gridy = 4;
        cons.gridwidth = 4;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 136, 14, 0);
        btnClear.setEnabled(false);
        panelForm.add(btnClear, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.ipadx = 10;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(12, 12, 0, 12);
        add(panelForm, cons);

        panelTable.setBorder(BorderFactory.createTitledBorder("Linhas"));

        scrollTable.setViewportView(table);

        panelTable.add(scrollTable, BorderLayout.CENTER);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.ipady = 95;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(12, 12, 12, 12);
        add(panelTable, cons);

        panelSearch.setBorder(BorderFactory.createTitledBorder("Pesquisa"));

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(29, 17, 0, 0);
        panelSearch.add(lblTrainSearch, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 17, 0, 0);
        panelSearch.add(comboTrainSearch, cons);

        cons = new GridBagConstraints();
        cons.gridx = 2;
        cons.gridy = 0;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(29, 12, 0, 0);
        panelSearch.add(lblStationSearch, cons);

        cons = new GridBagConstraints();
        cons.gridx = 2;
        cons.gridy = 1;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 12, 0, 17);
        panelSearch.add(comboStationSearch, cons);

        cons = new GridBagConstraints();
        cons.gridx = 3;
        cons.gridy = 2;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(12, 100, 17, 17);
        panelSearch.add(btnSearch, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.ipady = -6;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 12, 0, 12);
        add(panelSearch, cons);
    }

    /**
     * Show this panel as a dialog.
     *
     * @param parent Dialog parent.
     */
    public void showAsDialog(final Window parent) {
        JDialog dialog = new JDialog(parent, Dialog.ModalityType.APPLICATION_MODAL);

        dialog.setTitle("Linhas e Horários");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.add(this);

        dialog.setSize(MIN_SIZE);
        dialog.setMinimumSize(MIN_SIZE);

        dialog.setVisible(true);
    }

}
