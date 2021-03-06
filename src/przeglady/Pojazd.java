package przeglady;

import oracle.jdbc.driver.OracleDriver;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

/**
 * Created by user
 */
public class Pojazd extends JFrame {
    JTable table;

    JLabel modelLabel;
    JLabel rejestracjaLabel;
    JLabel typPojazduLabel;
    JLabel dataProdukcjiLabel;

    JTextField rejestracjaField;
    JTextField modelField;
    JTextField typPojazduField;
    JTextField dataProdukcjiField;

    JButton selectButton;
    JButton insertButton;
    JButton refreshButton;

    JComboBox modelComboBox;
    JComboBox typPojazduComboBox;

    Vector<String> columnNames;
    Vector<Vector> data;
    Vector<String> models;
    Vector<String> typyPojazdu;

    JDBCConnector connector = new JDBCConnector();
    Connection connection;

    public Pojazd() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Pojazd");

        rejestracjaField = new JTextField();
        modelField = new JTextField();
        typPojazduField = new JTextField();
        dataProdukcjiField = new JTextField();

        rejestracjaLabel = new JLabel();
        modelLabel = new JLabel();
        typPojazduLabel = new JLabel();
        dataProdukcjiLabel = new JLabel();

        insertButton = new JButton();
        selectButton = new JButton();
        refreshButton = new JButton();

        models = new Vector<String>();
        typyPojazdu = new Vector<String>();

        rejestracjaLabel.setText("Rejestracja pojazdu: ");
        modelLabel.setText("Identyfikator modelu: ");
        typPojazduLabel.setText("Identyfikator typu pojazdu: ");
        dataProdukcjiLabel.setText("Data produkcji pojazdu: ");

        insertButton.setText("Dodaj");
        selectButton.setText("Wyszukaj");
        refreshButton.setText("Odśwież");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    insert();
                } catch (SQLException e) {
                    System.out.println(e.getMessage() + "Unique constraint violated");
                }
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    select();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                initComponents();
            }
        });

        try {
            connection = connector.getConnection();
            PreparedStatement ps;
            ResultSet rs;

            String query = "SELECT * FROM \"Model\"";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
//            models = new Vector<String>();

            if (rs != null)
                while (rs.next())
                    models.add(rs.getString(2));
            models.add("*");
            modelComboBox = new JComboBox(models);

            query = "SELECT * FROM \"Typ_pojazdu\"";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
//            typyPojazdu = new Vector<String>();

            if (rs != null)
                while (rs.next())
                    typyPojazdu.add(rs.getString(2));
            typyPojazdu.add("*");
            typPojazduComboBox = new JComboBox(typyPojazdu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(rejestracjaLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rejestracjaField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(modelLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(modelComboBox))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(typPojazduLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(typPojazduComboBox))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(dataProdukcjiLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dataProdukcjiField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(insertButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(selectButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(refreshButton)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {selectButton, typPojazduComboBox, modelComboBox});

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(rejestracjaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rejestracjaLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(modelComboBox)
                                        .addComponent(modelLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(typPojazduComboBox)
                                        .addComponent(typPojazduLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(dataProdukcjiField)
                                        .addComponent(dataProdukcjiLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(insertButton)
                                        .addComponent(selectButton)
                                        .addComponent(refreshButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }

    private void insert() throws SQLException {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;

        String rejestracja = rejestracjaField.getText();
        String model = (String) modelComboBox.getSelectedItem();
        String typPojazdu = (String) typPojazduComboBox.getSelectedItem();
        String dataProdukcji = dataProdukcjiField.getText();
        String query = "INSERT INTO \"Pojazd\" (\"rejestracja\", \"id_modelu\", \"id_typu_pojazdu\", \"data_produkcji\")" +
                "VALUES (?," +
                "(SELECT \"id_modelu\" FROM \"Model\" WHERE \"nazwa\" LIKE ? AND ROWNUM = 1)," +
                "(SELECT \"id_typu_pojazdu\" FROM \"Typ_pojazdu\" WHERE \"typ\" LIKE ? AND ROWNUM = 1)," +
                "?)";

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Cannot find the proper driver");
            System.exit(-1);
        }

        try {
            connection = connector.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, rejestracja);
            ps.setString(2, "%" + model + "%");
            ps.setString(3, "%" + typPojazdu + "%");
            ps.setString(4, dataProdukcji);
            ps.executeQuery();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private void select() throws Exception {
        boolean isRejestracjaCorrect = false;
        boolean isModelCorrect = false;
        boolean isTypPojazduCorrect = false;
        boolean isDataProdukcjiCorrect = false;

        String rejestracja = "";
        String model = "";
        String typPojazdu = "";
        String dataProdukcji = "";

        if (rejestracjaField.getText() != null) {
            if (!rejestracjaField.getText().equals("")) {
                rejestracja = rejestracjaField.getText();
                isRejestracjaCorrect = true;
            }
        }

        if (modelComboBox.getSelectedItem() != null) {
            if (!modelComboBox.getSelectedItem().equals("") && !modelComboBox.getSelectedItem().equals("*")) {
                try {
                    model = (String) modelComboBox.getSelectedItem();
                    isModelCorrect = true;
                } catch (NumberFormatException e) {
                    throw new Exception("Incorrect value: " + modelComboBox.getSelectedItem());
                }
            }
        }

        if (typPojazduComboBox.getSelectedItem() != null) {
            if (!typPojazduComboBox.getSelectedItem().equals("") && !typPojazduComboBox.getSelectedItem().equals("*")) {
                try {
                    typPojazdu = (String) typPojazduComboBox.getSelectedItem();
                    isTypPojazduCorrect = true;
                } catch (NumberFormatException e) {
                    throw new Exception("Incorrect value: " + typPojazduComboBox.getSelectedItem());
                }
            }
        }

        if (dataProdukcjiField.getText() != null) {
            if (!dataProdukcjiField.getText().equals("")) {
                dataProdukcji = dataProdukcjiField.getText();
                isDataProdukcjiCorrect = true;
            }
        }

        if (!isRejestracjaCorrect && !isModelCorrect && !isTypPojazduCorrect && !isDataProdukcjiCorrect)
            throw new Exception("Incorrect values");

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Cannot find the proper driver");
            System.exit(-1);
        }

        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData md;

        String query = "SELECT * FROM \"Pojazd\" WHERE";

        try {
            connection = connector.getConnection();
            boolean[] columns = new boolean[4];

            if (isRejestracjaCorrect) {
                query += " \"rejestracja\" LIKE ?";
                columns[0] = true;
            }

            if (isModelCorrect) {
                if (isRejestracjaCorrect)
                    query += " AND ";
                query += " \"id_modelu\" = (SELECT \"id_modelu\" FROM \"Model\" WHERE \"nazwa\" LIKE ? AND ROWNUM = 1)";
                columns[1] = true;
            }

            if (isTypPojazduCorrect) {
                if (isRejestracjaCorrect || isModelCorrect)
                    query += " AND ";
                query += " \"id_typu_pojazdu\" = (SELECT \"id_typu_pojazdu\" FROM \"Typ_pojazdu\" WHERE \"typ\" LIKE ? AND ROWNUM = 1)";
                columns[2] = true;
            }

            if (isDataProdukcjiCorrect) {
                if (isRejestracjaCorrect || isModelCorrect || isDataProdukcjiCorrect)
                    query += " AND ";
                query += " \"data_produkcji\" LIKE ?";
                columns[3] = true;
            }

            ps = connection.prepareStatement(query);

            for (int i = 0, c = 1; i < columns.length; i++)
                if (columns[i])
                    if (i == 0)
                        ps.setString(c++, "%" + rejestracja + "%");
                    else if (i == 1)
                        ps.setString(c++, "%" + model + "%");
                    else if (i == 2)
                        ps.setString(c++, "%" + typPojazdu + "%");
                    else if (i == 3)
                        ps.setString(c++, "%" + dataProdukcji + "%");
                    else
                        break;

            rs = ps.executeQuery();
            columnNames = new Vector<String>();
            data = new Vector<Vector>();

            if (rs != null) {
                md = rs.getMetaData();
                Vector<Object> column;

                while (rs.next()) {
                    column = new Vector<Object>();
                    String idModelu = rs.getString(2);
                    query = "SELECT \"nazwa\" FROM \"Model\" WHERE \"id_modelu\" = ? AND ROWNUM = 1";
                    ps = connection.prepareStatement(query);
                    ps.setString(1, idModelu);
                    ResultSet modelResultSet = ps.executeQuery();
                    modelResultSet.next();

                    String idTypuPojazdu = rs.getString(3);
                    query = "SELECT \"typ\" FROM \"Typ_pojazdu\" WHERE \"id_typu_pojazdu\" = ? AND ROWNUM = 1";
                    ps = connection.prepareStatement(query);
                    ps.setString(1, idTypuPojazdu);
                    ResultSet typPojazduResultSet = ps.executeQuery();
                    typPojazduResultSet.next();

                    for (int i = 1; i < md.getColumnCount() + 1; i++) {
                        if (i == 2)
                            column.add(modelResultSet.getString(1));
                        else if (i == 3)
                            column.add(typPojazduResultSet.getString(1));
                        else
                            column.add(rs.getString(i));
                    }

                    data.add(column);
                }

                for (int i = 1; i < md.getColumnCount() + 1; i++)
                    columnNames.add(md.getColumnName(i));

                if (!columnNames.isEmpty())
                    table = new JTable(data, columnNames);
                else
                    columnNames.add("No rows extracted");

                columnNames.set(1, "model");
                columnNames.set(2, "typ pojazdu");

                table = new JTable(data, columnNames);
                TableColumn modelColumn = table.getColumnModel().getColumn(1);
                modelColumn.setCellEditor(new DefaultCellEditor(modelComboBox));
                TableColumn typPojazduColumn = table.getColumnModel().getColumn(2);
                typPojazduColumn.setCellEditor(new DefaultCellEditor(typPojazduComboBox));
                PojazdTable.start(table);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Pojazd().setVisible(true);
            }
        });
    }
}
