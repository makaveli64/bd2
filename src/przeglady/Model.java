package przeglady;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

import oracle.jdbc.driver.OracleDriver;

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
//    JLabel sqlLabel;
    JTable table;

    Vector<String> columnNames;
    Vector<Vector> data;

    public Model() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Model");
//        sqlLabel = new JLabel();
        idField = new JTextField();
        idLabel = new JLabel();

        nameField = new JTextField();
        nameLabel = new JLabel();

        insertButton = new JButton();
        selectButton = new JButton();

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

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {selectButton, nameField, idField});

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

        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
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

        if (idField.getText() != null)
            if (!idField.getText().equals(""))
                try {
                    id = Integer.parseInt(idField.getText());
                    isIdCorrect = true;
                } catch (NumberFormatException e) {
                    throw new Exception("Incorrect value: " + idField.getText());
                }

        if (nameField.getText() != null)
            if (!nameField.getText().equals("")) {
                name = nameField.getText();
                isNameCorrect = true;
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
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Model().setVisible(true);
            }
        });
    }
}
