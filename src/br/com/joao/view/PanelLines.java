package br.com.joao.view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 08 Dec.
 */
public class PanelLines extends JPanel {

    private final JButton btnSearch, btnAdd, btnRemove, btnClear;
    private final JComboBox<String> comboStation, comboStationSearch, comboTrain, comboTrainSearch;
    private final JLabel lblDepartureTime, lblReturnTime, lblStation, lblStationSearch, lblTrain, lblTrainSearch;
    private final JPanel panelForm, panelSearch, panelTable;
    private final JScrollPane scrollTable;
    private final JTable table;
    private final JFormattedTextField txtDepartureTime, txtReturnTime;

    private static final Dimension MIN_SIZE = new Dimension(570, 489);

    /**
     * Creates a panel to show lines.
     */
    public PanelLines() {
        super(new GridBagLayout());

        panelForm = new JPanel(new GridBagLayout());
        lblTrain = new JLabel("Trem");
        comboTrain = new JComboBox<>(
                new DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        lblStation = new JLabel("Estação");
        comboStation = new JComboBox<>(
                new DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        lblDepartureTime = new JLabel("Horário de Ida");
        txtDepartureTime = new JFormattedTextField();
        lblReturnTime = new JLabel("Horário de Volta");
        txtReturnTime = new JFormattedTextField();
        btnAdd = new JButton("Adicionar");
        btnRemove = new JButton("Remover");
        btnClear = new JButton("Limpar");
        panelTable = new JPanel(new BorderLayout());
        scrollTable = new JScrollPane();
        table = new JTable(new DefaultTableModel(
                new Object[][]{
                    {"1", "1", "14:30", "15:30"}
                },
                new String[]{
                    "Trem", "Estação", "Hora Ida", "Hora Volta"
                }
        ));
        panelSearch = new JPanel(new GridBagLayout());
        lblTrainSearch = new JLabel("Trem");
        comboTrainSearch = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"Todos"}));
        lblStationSearch = new JLabel("Estação");
        comboStationSearch = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"Todas"}));
        btnSearch = new JButton("Pesquisar");

        init();
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
        cons.ipadx = 158;
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
        cons.ipadx = 192;
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

        txtDepartureTime.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("hh:mm"))));
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

        txtReturnTime.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("hh:mm"))));
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
        cons.ipadx = 514;
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
        cons.ipadx = 158;
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
        cons.ipadx = 190;
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
