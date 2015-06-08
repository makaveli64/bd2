package przeglady;

import oracle.jdbc.OracleDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Created by user
 */
public class Table extends JPanel {
    JTable table;

    JButton updateButton;
    JButton deleteButton;

    int[] ids;

    Table(JTable table) {
        super();

        this.table = table;
        this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.table.setFillsViewportHeight(true);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(this.table));
//        add(new JLabel("Operacje:"));
        updateButton = new JButton();
        deleteButton = new JButton();

        updateButton.setText("Modyfikuj");
        deleteButton.setText("Usuń");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel);
        ids = new int[this.table.getRowCount()];

        for (int i = 0; i < ids.length; i++)
            ids[i] = Integer.parseInt(this.table.getValueAt(i, 0).toString());

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    update();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    delete();
                } catch (Exception e) {
                    // TODO
                }
            }
        });

/*
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(this.table))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(updateButton)
                                                .addComponent(deleteButton)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(this.table))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(updateButton)
                                        .addComponent(deleteButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addContainerGap(21, Short.MAX_VALUE))
        );
*/

//        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);
//        boxLayout.addLayoutComponent();
//        add(boxLayout);
    }

    private void update() throws SQLException {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;

        int[] selectedRows = table.getSelectedRows();

        for (int i = 0; i < selectedRows.length; i++) {
/*
            for (int j = 0; j < table.getColumnCount(); j++)
                System.out.println(table.getValueAt(selectedRows[i], j));
*/
            String query = "UPDATE \"Model\" SET \"id_modelu\" = ?, \"nazwa\" = ? WHERE \"id_modelu\" = ?";
            try {
                DriverManager.registerDriver(new OracleDriver());
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Cannot find the proper driver");
                System.exit(-1);
            }

            try {
                connection = connector.getConnection();
                ps = connection.prepareStatement(query);
                ps.setString(1, table.getValueAt(selectedRows[i], 0).toString());
                ps.setString(2, table.getValueAt(selectedRows[i], 1).toString());
                ps.setInt(3, ids[selectedRows[i]]);
                ps.executeQuery();
/*
                System.out.println(query);
                System.out.println(table.getValueAt(selectedRows[i], 0).toString());
                System.out.println(table.getValueAt(selectedRows[i], 1).toString());
                for (int j = 0; j < selectedRows.length; j++)
                    System.out.println("Selected row: " + selectedRows[j]);
                for (int j = 0; j < selectedRows.length; j++)
                    System.out.println("Value at " + j + " : " + ids[selectedRows[j]]);
*/
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
/*
        for (int i = 0; i < selectedRows.length; i++)
            for (int j = 0; j < table.getColumnCount(); j++)
                System.out.println(table.getValueAt(table.getSelectedRows()[i], j));
*/
//                System.out.println(i + ". " + selectedRows[i]);
//        ResultSet rs;

//        int id = Integer.parseInt(idField.getText());
//        String name = nameField.getText();
    }

    private void delete() throws SQLException {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;

        int[] selectedRows = table.getSelectedRows();

        for (int i = 0; i < selectedRows.length; i++) {
            String query = "DELETE FROM \"Model\" WHERE \"id_modelu\" = ?";
            try {
                DriverManager.registerDriver(new OracleDriver());
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Cannot find the proper driver");
                System.exit(-1);
            }

            try {
                connection = connector.getConnection();
                ps = connection.prepareStatement(query);
                ps.setInt(1, ids[selectedRows[i]]);
                ps.executeQuery();
//                System.out.println(query);
//                System.out.println(ids[selectedRows[i]]);
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
    }

    private static void showTable(JTable table) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame frame = new JFrame("Table");

        Table tablePane = new Table(table);
        tablePane.setOpaque(true);

        frame.setContentPane(tablePane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void start(final JTable table) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                showTable(table);
            }
        });
    }
}
