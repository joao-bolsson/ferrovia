package br.com.joao.view;

import br.com.joao.control.TrainControl;
import br.com.joao.model.Engineer;
import br.com.joao.model.Train;
import br.com.joao.model.TrainTableModel;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 08 Dec.
 */
public class MainPanel extends JPanel {

    private final JButton btnAdd, btnClear, btnRemove, btnSearch, btnViewLines;
    private final JComboBox<Train.Type> comboType;
    private final JComboBox<Engineer> comboEng;
    private final JLabel lblEng, lblId, lblIdValue, lblType;
    private final JPanel panelForm, panelSearch, panelTrains;
    private final JScrollPane scrollTrains;
    private final JTable tableTrains;
    private final JTextField txtSearch;

    private static final Dimension MIN_SIZE = new Dimension(567, 489);
    private JDialog dialog;

    private static final String AUTO_INCREMENT = "Automático";

    private int rowSelected;

    /**
     * Creates the main panel.
     */
    public MainPanel() {
        super(new GridBagLayout());

        panelForm = new JPanel(new GridBagLayout());

        lblEng = new JLabel("Engenheiro:");
        comboEng = new JComboBox<>();

        lblId = new JLabel("#ID");
        lblIdValue = new JLabel(AUTO_INCREMENT);

        lblType = new JLabel("Tipo:");
        comboType = new JComboBox<>();

        btnAdd = new JButton("Adicionar");
        btnRemove = new JButton("Remover");
        btnClear = new JButton("Limpar");

        panelTrains = new JPanel(new BorderLayout());
        scrollTrains = new JScrollPane();
        tableTrains = new JTable(TrainTableModel.getInstance());
        panelSearch = new JPanel(new GridBagLayout());

        txtSearch = new JTextField();
        btnSearch = new JButton("Pesquisar");
        btnViewLines = new JButton("Ver Linhas e Horários");

        addListeners();
        fillFields();
        init();
    }

    private void fillFields() {
        List<Engineer> engineers = Engineer.getEngineers();
        for (Engineer eng : engineers) {
            comboEng.addItem(eng);
        }

        List<Train.Type> types = Train.Type.getTypes();
        for (Train.Type type : types) {
            comboType.addItem(type);
        }

    }

    private void addListeners() {
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                TrainControl.search(txtSearch.getText());
                tableTrains.repaint();
            }
        });

        btnViewLines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PanelLines panelLines = new PanelLines();
                panelLines.showAsDialog(dialog);
            }
        });

        tableTrains.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                int selectedRow = tableTrains.getSelectedRow();
                if (selectedRow >= 0) { // evita ArrayIndexOutOfBounds
                    rowSelected = selectedRow;
                }
                prepareToEdit(rowSelected);
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                clear();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Object valueAt = tableTrains.getValueAt(rowSelected, TrainTableModel.TRAIN_COLUMN);
                if (valueAt instanceof Train) {
                    Train train = (Train) valueAt;
                    TrainControl.remove(train);
                    tableTrains.repaint();
                    clear();
                }
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (lblIdValue.getText().equals(AUTO_INCREMENT)) {
                    Train train = new Train((Engineer) comboEng.getSelectedItem(),
                            (Train.Type) comboType.getSelectedItem());
                    TrainControl.add(train);
                } else {
                    Object valueAt = TrainTableModel.getInstance().getValueAt(rowSelected, TrainTableModel.TRAIN_COLUMN);
                    if (valueAt instanceof Train) {
                        Train train = (Train) valueAt;
                        train.setEngineer((Engineer) comboEng.getSelectedItem());
                        train.setType((Train.Type) comboType.getSelectedItem());

                        TrainControl.update(train);
                    }
                }
                tableTrains.repaint();
                clear();
            }
        });
    }

    private void clear() {
        lblIdValue.setText(AUTO_INCREMENT);
        comboEng.setSelectedIndex(0);
        comboType.setSelectedIndex(0);
        btnClear.setEnabled(false);
        btnAdd.setText("Adicionar");
    }

    private void prepareToEdit(final int row) {
        Object valueAt = tableTrains.getValueAt(row, TrainTableModel.TRAIN_COLUMN);
        if (valueAt instanceof Train) {
            Train train = (Train) valueAt;
            lblIdValue.setText(Integer.toString(train.getId()));
            comboEng.setSelectedItem(train.getEngineer());
            comboType.setSelectedItem(train.getType());
            btnClear.setEnabled(true);
            btnAdd.setText("Atualizar");
        }
    }

    private void init() {
        setMinimumSize(MIN_SIZE);
        panelForm.setBorder(BorderFactory.createEtchedBorder());
        panelForm.setMinimumSize(new Dimension(317, 117));

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(11, 14, 0, 0);
        panelForm.add(lblEng, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 3;
        cons.gridheight = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 12, 0, 14);
        panelForm.add(comboEng, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(11, 14, 0, 0);
        panelForm.add(lblType, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 2;
        cons.gridwidth = 3;
        cons.gridheight = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 12, 0, 14);
        panelForm.add(comboType, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(15, 14, 0, 0);
        panelForm.add(lblId, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 3;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(15, 12, 0, 14);
        panelForm.add(lblIdValue, cons);

        cons = new GridBagConstraints();
        cons.gridx = 3;
        cons.gridy = 3;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(18, 6, 14, 14);
        panelForm.add(btnAdd, cons);

        cons = new GridBagConstraints();
        cons.gridx = 2;
        cons.gridy = 3;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(18, 6, 14, 0);
        panelForm.add(btnRemove, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 3;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(18, 130, 14, 0);
        btnClear.setEnabled(false);
        panelForm.add(btnClear, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.ipadx = 226;
        cons.ipady = 4;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(12, 12, 0, 12);
        add(panelForm, cons);

        panelTrains.setBorder(BorderFactory.createTitledBorder("Trens"));

        scrollTrains.setViewportView(tableTrains);

        panelTrains.add(scrollTrains, BorderLayout.CENTER);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 2;
        cons.ipadx = 511;
        cons.ipady = 150;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 12, 9, 12);
        add(panelTrains, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 3;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(10, 10, 10, 10);
        add(btnViewLines, cons);

        panelSearch.setBorder(BorderFactory.createEtchedBorder());
        panelSearch.setMinimumSize(new Dimension(143, 53));
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.ipadx = 390;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(17, 14, 0, 0);
        panelSearch.add(txtSearch, cons);

        cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridheight = 2;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(14, 6, 14, 14);
        panelSearch.add(btnSearch, cons);

        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 1;
        cons.ipadx = 400;
        cons.ipady = 4;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(6, 12, 0, 12);
        add(panelSearch, cons);
    }

    private void showAsDialog() {
        dialog = new JDialog(null, Dialog.ModalityType.APPLICATION_MODAL);

        dialog.setTitle("Ferroviária");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.add(this);

        dialog.setSize(MIN_SIZE);
        dialog.setMinimumSize(MIN_SIZE);

        dialog.setVisible(true);
    }

    /**
     * Start method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        MainPanel panel = new MainPanel();
        panel.showAsDialog();
    }
}
