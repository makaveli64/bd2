package przeglady;

import oracle.jdbc.driver.OracleDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

/**
 * Created by user
 */
public class Pojazd extends JFrame {
    JTextField rejestracjaField;
    JLabel rejestracjaLabel;

    JTextField modelField;
    JLabel modelLabel;

    JTextField typPojazduField;
    JLabel typPojazduLabel;

    JTextField dataProdukcjiField;
    JLabel dataProdukcjiLabel;

    JButton selectButton;
    JButton insertButton;

    JTable table;

    Vector<String> columnNames;
    Vector<Vector> data;

    public Pojazd() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Pojazd");

        rejestracjaField = new JTextField();
        rejestracjaLabel = new JLabel();

        modelField = new JTextField();
        modelLabel = new JLabel();

        typPojazduField = new JTextField();
        typPojazduLabel = new JLabel();

        dataProdukcjiField = new JTextField();
        dataProdukcjiLabel = new JLabel();

        insertButton = new JButton();
        selectButton = new JButton();

        rejestracjaLabel.setText("Rejestracja pojazdu: ");
        modelLabel.setText("Identyfikator modelu: ");
        typPojazduLabel.setText("Identyfikator typu pojazdu: ");
        dataProdukcjiLabel.setText("Data produkcji pojazdu: ");

        insertButton.setText("Dodaj");
        selectButton.setText("Wyszukaj");

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
                                                .addComponent(modelField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(typPojazduLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(typPojazduField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(dataProdukcjiLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dataProdukcjiField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(insertButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(selectButton)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {selectButton, typPojazduField, modelField});

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(rejestracjaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rejestracjaLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(modelField)
                                        .addComponent(modelLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(typPojazduField)
                                        .addComponent(typPojazduLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(dataProdukcjiField)
                                        .addComponent(dataProdukcjiLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(insertButton)
                                        .addComponent(selectButton))
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
        int model = Integer.parseInt(modelField.getText());
        int typPojazdu = Integer.parseInt(typPojazduField.getText());
        String dataProdukcji = dataProdukcjiField.getText();
        String query = "INSERT INTO \"Pojazd\" (\"rejestracja\", \"id_modelu\", \"id_typu_pojazdu\", \"data_produkcji\") VALUES (?, ?, ?, ?)";

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
            ps.setInt(2, model);
            ps.setInt(3, typPojazdu);
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
        int model = 0;
        int typPojazdu = 0;
        String dataProdukcji = "";

        if (rejestracjaField.getText() != null) {
            if (!rejestracjaField.getText().equals("")) {
                rejestracja = rejestracjaField.getText();
                isRejestracjaCorrect = true;
            }
        }

        if (modelField.getText() != null) {
            if (!modelField.getText().equals("")) {
                try {
                    model = Integer.parseInt(modelField.getText());
                    isModelCorrect = true;
                } catch (NumberFormatException e) {
                    throw new Exception("Incorrect value: " + modelField.getText());
                }
            }
        }

        if (typPojazduField.getText() != null) {
            if (!typPojazduField.getText().equals("")) {
                try {
                    typPojazdu = Integer.parseInt(typPojazduField.getText());
                    isTypPojazduCorrect = true;
                } catch (NumberFormatException e) {
                    throw new Exception("Incorrect value: " + typPojazduField.getText());
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

        JDBCConnector connector = new JDBCConnector();
        Connection connection;

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
                query += " \"id_modelu\" = ?";
                columns[1] = true;
            }

            if (isTypPojazduCorrect) {
                if (isRejestracjaCorrect || isModelCorrect)
                    query += " AND ";
                query += " \"id_typu_pojazdu\" = ?";
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
                        ps.setInt(c++, model);
                    else if (i == 2)
                        ps.setInt(c++, typPojazdu);
                    else if (i == 3)
                        ps.setString(c++, "%" + dataProdukcji + "%");
                    else
                        break;

//            System.out.println(rejestracja);
//            System.out.println(query);
            rs = ps.executeQuery();
            columnNames = new Vector<String>();
            data = new Vector<Vector>();

            if (rs != null) {
                md = rs.getMetaData();
                Vector<Object> column;

                while (rs.next()) {
                    column = new Vector<Object>();
                    for (int i = 1; i < md.getColumnCount() + 1; i++)
                        column.add(rs.getString(i));
                    data.add(column);
                }

                for (int i = 1; i < md.getColumnCount() + 1; i++)
                    columnNames.add(md.getColumnName(i));

                if (!columnNames.isEmpty())
                    table = new JTable(data, columnNames);
                else
                    columnNames.add("No rows extracted");

                table = new JTable(data, columnNames);
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
