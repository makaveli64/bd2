package przeglady;

import oracle.jdbc.driver.OracleDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Created by user
 */
public class Model extends JFrame {
    JTextField idField;
    JLabel idLabel;

    JTextField nameField;
    JLabel nameLabel;

    JButton selectButton;
    JButton insertButton;

    public Model() {
        initComponents();
    }

    private void initComponents() {
        idField = new JTextField();
        idLabel = new JLabel();

        nameField = new JTextField();
        nameLabel = new JLabel();

        insertButton = new JButton();
        selectButton = new JButton();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Model");

        idLabel.setText("Identyfikator modelu: ");
        nameLabel.setText("Nazwa modelu: ");

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
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
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
                                                .addComponent(idLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(idField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(nameLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nameField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(insertButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(selectButton)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {insertButton, nameField, idField});

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(idLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameField)
                                        .addComponent(nameLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(insertButton)
                                        .addComponent(selectButton))
                                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }

    private void insert() throws SQLException {
        JDBCConnector connector = new JDBCConnector();

        PreparedStatement ps;
        connector.connection = null;

        String username = "idzemian";
        String password = "idzemian";

        String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
        String query = "INSERT INTO \"Model\" (\"id_modelu\", \"nazwa\") VALUES (?, ?)";

        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();

        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Cannot find the proper driver");
            System.exit(-1);
        }

        try {
            connector.connection = DriverManager.getConnection(url, username, password);
            ps = connector.connection.prepareStatement(query);
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
        int params = 0;

        int id = 0;
        String name = "";

        if (idField.getText() != null) {
            if (!idField.getText().equals("")) {
                id = Integer.parseInt(idField.getText());
                isIdCorrect = true;
            }
        }

        if (nameField.getText() != null) {
            if (!nameField.getText().equals("")) {
                name = nameField.getText();
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
        PreparedStatement ps;
        ResultSet rs = null;
        connector.connection = null;

        String username = "idzemian";
        String password = "idzemian";

        String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
        String query = "SELECT * FROM \"Model\" WHERE";

        try {
            connector.connection = DriverManager.getConnection(url, username, password);

            if (isIdCorrect && isNameCorrect) {
                query += " \"id_modelu\" = ? AND \"nazwa\" LIKE ?";
                ps = connector.connection.prepareStatement(query);
                ps.setInt(1, id);
                ps.setString(2, name);
                rs = ps.executeQuery();
            } else if (isIdCorrect && !isNameCorrect) {
                query += " \"id_modelu\" = ?";
                ps = connector.connection.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
            } else if (!isIdCorrect && isNameCorrect) {
                query += " \"nazwa\" LIKE ?";
                ps = connector.connection.prepareStatement(query);
                ps.setString(1, "%" + name + "%");
                rs = ps.executeQuery();
            } else
                throw new Exception("Incorrect values");

            if (rs != null) {
                rs.next();
                System.out.println(rs.getString(2));
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public static void start() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Model().setVisible(true);
            }
        });
    }
}