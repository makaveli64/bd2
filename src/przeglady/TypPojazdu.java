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
public class TypPojazdu extends JFrame {
    JTextField idField;
    JLabel idLabel;

    JTextField typField;
    JLabel typLabel;

    JButton selectButton;
    JButton insertButton;
//    JLabel sqlLabel;
    JTable table;

    Vector<String> columnNames;
    Vector<Vector> data;

    public TypPojazdu() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Typ pojazdu");
//        sqlLabel = new JLabel();
        idField = new JTextField();
        idLabel = new JLabel();

        typField = new JTextField();
        typLabel = new JLabel();

        insertButton = new JButton();
        selectButton = new JButton();

        idLabel.setText("Identyfikator typu pojazdu: ");
        typLabel.setText("Nazwa typu: ");

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
                                                .addComponent(typLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(typField))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(insertButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(selectButton)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {selectButton, typField, idField});

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(idLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(typField)
                                        .addComponent(typLabel))
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
        String typ = typField.getText();
        String query = "INSERT INTO \"Typ_pojazdu\" (\"id_typu_pojazdu\", \"typ\") VALUES (?, ?)";

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
            ps.setString(2, typ);
            ps.executeQuery();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private void select() throws Exception {
        boolean isIdCorrect = false;
        boolean isTypeCorrect = false;

        int id = 0;
        String typ = "";

        if (idField.getText() != null)
            if (!idField.getText().equals(""))
                try {
                    id = Integer.parseInt(idField.getText());
                    isIdCorrect = true;
                } catch (NumberFormatException e) {
                    throw new Exception("Incorrect value: " + idField.getText());
                }

        if (typField.getText() != null)
            if (!typField.getText().equals("")) {
                typ = typField.getText();
                isTypeCorrect = true;
            }

        if (!isIdCorrect && !isTypeCorrect)
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

        String query = "SELECT * FROM \"Typ_pojazdu\" WHERE";

        try {
            connection = connector.getConnection();

            if (isIdCorrect && isTypeCorrect) {
                query += " \"id_typu_pojazdu\" = ? AND \"typ\" LIKE ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ps.setString(2, "%" + typ + "%");
                rs = ps.executeQuery();
            } else if (isIdCorrect && !isTypeCorrect) {
                query += " \"id_typu_pojazdu\" = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
            } else if (!isIdCorrect && isTypeCorrect) {
                query += " \"typ\" LIKE ?";
                ps = connection.prepareStatement(query);
                ps.setString(1, "%" + typ + "%");
                rs = ps.executeQuery();
            } else
                throw new Exception("Incorrect values");

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
                TypPojazduTable.start(table);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TypPojazdu().setVisible(true);
            }
        });
    }
}
