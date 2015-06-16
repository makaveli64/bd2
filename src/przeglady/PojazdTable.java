package przeglady;

import oracle.jdbc.OracleDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Created by user
 */
public class PojazdTable extends JPanel {
    JTable table;

    JButton updateButton;
    JButton deleteButton;

    String[] rejestracje;

    PojazdTable(JTable table) {
        super();

        this.table = table;
        this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.table.setFillsViewportHeight(true);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(this.table));

        updateButton = new JButton();
        deleteButton = new JButton();

        updateButton.setText("Modyfikuj");
        deleteButton.setText("Usuń");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel);
        rejestracje = new String[this.table.getRowCount()];

        for (int i = 0; i < rejestracje.length; i++)
            rejestracje[i] = this.table.getValueAt(i, 0).toString();

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
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private void update() throws SQLException {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;

        int[] selectedRows = table.getSelectedRows();

        for (int i = 0; i < selectedRows.length; i++) {
            String query = "UPDATE \"Pojazd\" SET \"rejestracja\" = ?, \"id_typu_pojazdu\" = ?, \"id_modelu\" = ?, \"data_produkcji\" = ? WHERE \"rejestracja\" = ?";
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
                ps.setString(3, table.getValueAt(selectedRows[i], 2).toString());
                ps.setString(4, rejestracje[selectedRows[i]]);
                ps.executeQuery();
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
    }

    private void delete() throws SQLException {
        JDBCConnector connector = new JDBCConnector();
        Connection connection;
        PreparedStatement ps;

        int[] selectedRows = table.getSelectedRows();

        for (int i = 0; i < selectedRows.length; i++) {
            String query = "DELETE FROM \"Pojazd\" WHERE \"rejestracja\" = ?";
            try {
                DriverManager.registerDriver(new OracleDriver());
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "Cannot find the proper driver");
                System.exit(-1);
            }

            try {
                connection = connector.getConnection();
                ps = connection.prepareStatement(query);
                ps.setString(1, rejestracje[selectedRows[i]]);
                ps.executeQuery();
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
    }

    private static void showTable(JTable table) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame frame = new JFrame("ModelTable");

        PojazdTable tablePane = new PojazdTable(table);
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
