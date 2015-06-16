package przeglady;

import oracle.jdbc.driver.OracleDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        insertButton = new JButton();
        selectButton = new JButton();

        rejestracjaLabel.setText("Rejestracja pojazdu: ");
        modelLabel.setText("Identyfikator modelu: ");
        typPojazduLabel.setText("Identyfikator typu pojazdu: ");

        insertButton.setText("Dodaj");
        selectButton.setText("Wyszukaj");
//        updateButton.setText("Zmodyfikuj");
//        deleteButton.setText("Usuń");
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
/*
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
*/
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(modelLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(modelField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(typPojazduLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(typPojazduField))
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
                                        .addComponent(modelField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(modelLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(typPojazduField)
                                        .addComponent(typPojazduLabel))
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

        int id = Integer.parseInt(modelField.getText());
        String name = typPojazduField.getText();
        String query = "INSERT INTO \"Model\" (\"id_modelu\", \"nazwa\") VALUES (?, ?)";

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Cannot find the proper driver");
            System.exit(-1);
        }

        try {
            connection = connector.getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.executeQuery();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private void select() throws Exception {
        boolean isIdCorrect = false;
        boolean isNameCorrect = false;

        int id = 0;
        String name = "";

        if (modelField.getText() != null) {
            if (!modelField.getText().equals("")) {
//                System.out.println("id:");
                try {
                    id = Integer.parseInt(modelField.getText());
                    isIdCorrect = true;
                } catch (NumberFormatException e) {
                    throw new Exception("Incorrect value: " + modelField.getText());
                }
            }
        }

        if (typPojazduField.getText() != null) {
            if (!typPojazduField.getText().equals("")) {
                name = typPojazduField.getText();
                isNameCorrect = true;
            }
        }

        if (!isIdCorrect && !isNameCorrect)
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

        String query = "SELECT * FROM \"Model\" WHERE";

        try {
            connection = connector.getConnection();

            if (isIdCorrect && isNameCorrect) {
                query += " \"id_modelu\" = ? AND \"nazwa\" LIKE ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ps.setString(2, "%" + name + "%");
                rs = ps.executeQuery();
            } else if (isIdCorrect && !isNameCorrect) {
                query += " \"id_modelu\" = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
            } else if (!isIdCorrect && isNameCorrect) {
                query += " \"nazwa\" LIKE ?";
                ps = connection.prepareStatement(query);
                ps.setString(1, "%" + name + "%");
                rs = ps.executeQuery();
            } else
                throw new Exception("Incorrect values");

            columnNames = new Vector<String>();
            data = new Vector<Vector>();

            if (rs != null) {
                md = rs.getMetaData();
                Vector<Object> column;// = new Vector<Object>();

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
                ModelTable.start(table);
/*
                    throw new Exception("sfjkfkds");
                System.out.println(data.size());
                for (int i = 0; i < data.size(); i++)
                    System.out.println(data.get(i));
*/
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
